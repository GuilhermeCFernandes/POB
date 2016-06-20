package core;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import struct.ListaCircular;
import core.item.Item;
import core.mapa.Mapa;
import core.mapa.Posicao;
import core.personagem.Personagem;
import core.personagem.Personagem.Stat;

public class Jogo implements Serializable {
	
	private static final long serialVersionUID = -4082863311966571070L;
	
	public static final int NRO_JOGADORES = 2;
	
	private Mapa mapa;
	private boolean toggleador = true;
	private ListaCircular<Personagem> personagens1;
	private ListaCircular<Personagem> personagens2;
	private Personagem pAtual;
	private ListIterator<Personagem> pIter1;
	private ListIterator<Personagem> pIter2;
	// TODO controlar times diferentes
	// TODO conseguir verificar se o jogo acabou

	public Jogo(Mapa m) {
		this.mapa = m;
		List<Personagem> p1 = m.getPersonagensTime1();
		List<Personagem> p2 = m.getPersonagensTime2();
		Collections.shuffle(p1);
		Collections.shuffle(p2);
		this.personagens1 = new ListaCircular<>(p1);
		this.personagens2 = new ListaCircular<>(p2);
		this.pIter1 = this.personagens1.listIterator();
		this.pIter2 = this.personagens2.listIterator();
		this.pAtual = pIter1.next();
	}
	
	public Personagem proximoPersonagem() {
		toggleador = !toggleador;
		if(toggleador)
			this.pAtual = pIter1.next();
		else
			this.pAtual = pIter2.next();
		return this.pAtual;
	}
	
	public Personagem personagemAtual() {
		return this.pAtual;
	}
	
	public boolean mover(Posicao nova) {
		if (this.mapa.alcancavel(this.pAtual.getPosicao(), nova, this.pAtual.getStat(Stat.VEL))) {
			this.mapa.mover(this.pAtual.getPosicao(), nova);
			return true;
		}
		return false;
	}
	
	public boolean atacar(Posicao alvo) {
		if (!this.mapa.isOcupado(alvo) || this.pAtual.getArma() == null)
			return false;
		
		if(alvo.distancia(this.pAtual.getPosicao()) > this.pAtual.getArma().getAlcance()){
			pAtual.atacar(this.mapa.getQuadrado(alvo).getOcupante());
			if(this.mapa.getQuadrado(alvo).getOcupante().isMorto())
				this.removePersonagem(this.mapa.getQuadrado(alvo).getOcupante());
			return true;
		}
		return false;
	}
	
	public boolean usar(Item item) {
		return this.pAtual.usar(item);
	}
	
	public boolean isFim(){
		return personagens1.isEmpty() || personagens2.isEmpty();
	}
	/*
	 * 0 Ninguem 
	 * 1 Time 1
	 * 2 Time 2
	 */
	public int whoGanhou(){
		if(personagens1.isEmpty())
			return 2;
		if(personagens2.isEmpty())
			return 1;
		return 0;
	}
	
	private void removePersonagem(Personagem p){
		if (!personagens1.remove(p))
			personagens2.remove(p);
	}
	
	public boolean usar(String item) {
		return this.pAtual.usar(item);
	}

}
