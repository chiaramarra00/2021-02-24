package it.polito.tdp.PremierLeague;

import it.polito.tdp.PremierLeague.model.Player;

public class GiocatoreMigliore {
	
	private Player giocatore;
	private double deltaEfficienza;
	
	public GiocatoreMigliore(Player giocatore, double deltaEfficienza) {
		super();
		this.giocatore = giocatore;
		this.deltaEfficienza = deltaEfficienza;
	}
	
	public Player getGiocatore() {
		return giocatore;
	}
	public double getDeltaEfficienza() {
		return deltaEfficienza;
	}

}
