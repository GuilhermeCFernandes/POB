package core.item.arma;

import core.personagem.Personagem;
import core.personagem.Personagem.Stat;
import core.personagem.Profissao;
import exception.NomeRepetidoException;

public class Livro extends Arma {

	private static final long serialVersionUID = 1807934292753343307L;

	public Livro(String nome, int danoBase, int alcance) throws NomeRepetidoException {
		super(nome, danoBase, alcance, Profissao.MAGO);
	}

	@Override
	public int calcularDano(Personagem pA, Personagem pB) {
		double dif = (pA.getStat(Stat.INT) - pB.getStat(Stat.INT))/5.0;
		if (dif>=0) return (int)Math.ceil(this.getDanoBase() * (dif+1));
		else return (int)Math.ceil(this.getDanoBase() /(-(dif-1))); 
	}

}
