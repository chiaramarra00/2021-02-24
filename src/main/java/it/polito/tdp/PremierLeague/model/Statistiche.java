package it.polito.tdp.PremierLeague.model;

import java.util.HashMap;
import java.util.Map;

public class Statistiche {
	
	private Map<String,Integer> goalPerSquadra;
	private Map<String,Integer> espulsiPerSquadra;
	
	public Statistiche(String t1, String t2) {
		this.goalPerSquadra = new HashMap<>();
		goalPerSquadra.put(t1, 0);
		goalPerSquadra.put(t2, 0);
		this.espulsiPerSquadra = new HashMap<>();
		espulsiPerSquadra.put(t1, 0);
		espulsiPerSquadra.put(t2, 0);
	}

	public Map<String, Integer> getGoalPerSquadra() {
		return goalPerSquadra;
	}


	public Map<String, Integer> getEspulsiPerSquadra() {
		return espulsiPerSquadra;
	}

	public void addGoal(String t) {
		goalPerSquadra.put(t, goalPerSquadra.get(t)+1);
	}

	public void addEspulsione(String t) {
		espulsiPerSquadra.put(t, espulsiPerSquadra.get(t)+1);
	}

}
