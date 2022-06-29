package it.polito.tdp.PremierLeague.model;

public class Event implements Comparable<Event>{
	
	public enum EventType {
		GOAL,
		ESPULSIONE,
		INFORTUNIO
	}
	
	private int tempo;
	private EventType tipo;

	public Event(int tempo, EventType tipo) {
		super();
		this.tempo = tempo;
		this.tipo = tipo;
	}

	public int getTempo() {
		return tempo;
	}


	public EventType getTipo() {
		return tipo;
	}

	@Override
	public int compareTo(Event o) {
		return tempo-o.tempo;
	}
	
}
