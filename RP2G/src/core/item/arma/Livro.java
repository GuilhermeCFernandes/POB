package core.item.arma;

import core.personagem.Personagem;
import core.personagem.Profissao;
import core.personagem.Personagem.Stat;
import core.utils.Dado;

public class Livro extends Arma {

	public Livro(String nome, int danoBase, int alcance){
		super(nome, danoBase, alcance);
	}
	
	@Override
	public void atacar(Personagem pA, Personagem pB) {
		Dado d20 = new Dado(20);
		d20.rolar();
		
		//Funcao linear onde f(1) = 1/3 e f(20) = 3
		double bonus = (((3.0 - (1.0/3.0)) / 19.0) * d20.getLado()) + ((1.0/3.0) - ((3.0 - (1.0/3.0)) / 19.0));
		int dano = (int) Math.ceil(this.calcularDano(pA, pB) * bonus);
		pB.danificar(dano);
	}

	@Override
	public int calcularDano(Personagem pA, Personagem pB) {
		//(((ForcaA - ForcaB) / 5) * danoBase) + danoBase;
		return (((pA.getStat(Stat.ST) - pB.getStat(Stat.ST))/5)+1) * this.danoBase;
	}

	@Override
	public boolean isEquipavel(Profissao p) {
		return (p == Profissao.GUERREIRO);
	}

}
