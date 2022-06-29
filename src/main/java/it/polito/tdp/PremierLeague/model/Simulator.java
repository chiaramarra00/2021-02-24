package it.polito.tdp.PremierLeague.model;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import it.polito.tdp.PremierLeague.model.Event.EventType;

public class Simulator {
	
	// Modello
	private String t1;
	private String t2;
	private Map<String, Integer> giocatoriPerSquadra;
	private String teamGiocatoreMigliore;
	private String nonTeamGiocatoreMigliore;
	
	// Parametri della simulazione
	private int N;
	private int NUM_GIOCATORI_INIZIALI;

	// Coda degli eventi
	private PriorityQueue<Event> queue;

	// Statistiche
	private Statistiche statistiche;

	public void init(Match m, int n, String tm) {
		this.N = n;
		t1 = m.teamHomeNAME;
		t2 = m.teamAwayNAME;
		this.giocatoriPerSquadra = new HashMap<>();
		this.queue = new PriorityQueue<Event>();
		this.statistiche = new Statistiche(t1,t2);
		assegnaGiocatoriSquadra();
		this.teamGiocatoreMigliore = tm;
		if (!t1.equals(teamGiocatoreMigliore)) {
			nonTeamGiocatoreMigliore = t1;
		}
		if (!t2.equals(teamGiocatoreMigliore)) {
			nonTeamGiocatoreMigliore = t2;
		}
		creaEventi(N);
	}
	
	private void assegnaGiocatoriSquadra() {
		giocatoriPerSquadra.put(t1, NUM_GIOCATORI_INIZIALI);
		giocatoriPerSquadra.put(t2, NUM_GIOCATORI_INIZIALI);
	}

	private void creaEventi(int n) {
		int tempo=0;
		for (int i = 0; i < n; i++) {
			double random = Math.random();
			if (random<0.5) {
				Event e = new Event(tempo,EventType.GOAL);
				this.queue.add(e);
				tempo++;
			}
			if (random>=0.5 && random<0.8) {
				Event e = new Event(tempo,EventType.ESPULSIONE);
				this.queue.add(e);
				tempo++;
			}
			if (random>=0.8) {
				Event e = new Event(tempo,EventType.INFORTUNIO);
				this.queue.add(e);
				tempo++;
			} 
		}
	}

	public void run() {
		while(!queue.isEmpty()) {
			Event e = queue.poll();
			processaEvento(e);
		}
	}

	private void processaEvento(Event e) {
		switch (e.getTipo()) {
		case GOAL:
			if (giocatoriPerSquadra.get(t1)>giocatoriPerSquadra.get(t2)) {
				statistiche.addGoal(t1);
			}
			else if (giocatoriPerSquadra.get(t1)<giocatoriPerSquadra.get(t2)) {
				statistiche.addGoal(t2);
			}
			else {
				statistiche.addGoal(teamGiocatoreMigliore);
			}
			break;
		case ESPULSIONE:
			double random = Math.random();
			if (random<0.6) {
				statistiche.addEspulsione(teamGiocatoreMigliore);
				giocatoriPerSquadra.put(teamGiocatoreMigliore, giocatoriPerSquadra.get(teamGiocatoreMigliore)-1);
			}
			else {
				statistiche.addEspulsione(nonTeamGiocatoreMigliore);
				giocatoriPerSquadra.put(nonTeamGiocatoreMigliore, giocatoriPerSquadra.get(nonTeamGiocatoreMigliore)-1);
			}
			break;
		case INFORTUNIO:
			double num = Math.random();
			if (num < 0.5) {
				creaEventi(2);
			}
			else {
				creaEventi(3);
			}
			break;
		}
	}

	public Statistiche getStatistiche() {
		return this.statistiche;
	}

}
