package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.GiocatoreMigliore;
import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	private PremierLeagueDAO dao;

	private Graph<Player, DefaultWeightedEdge> grafo;
	private Map<Integer, Player> idMap;
	
	private Simulator sim;
	
	public Model() {
		dao = new PremierLeagueDAO();
		idMap = new HashMap<>();
		
		this.dao.getAllPlayers(idMap);
		
		sim = new Simulator();
	}
	
	public void creaGrafo(Match m) {
		//creo il grafo
		this.grafo = 
				new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		//aggiungo i vertici
		Graphs.addAllVertices(this.grafo, 
				this.dao.getVertici(m, this.idMap));
		
		//aggiungo gli archi
		for(Adiacenza a : this.dao.getArchi(m, idMap)) {
			Graphs.addEdgeWithVertices(this.grafo, a.getP1(), 
					a.getP2(), a.getPeso());
		}
		
		System.out.println("Grafo creato!");
		System.out.println(String.format("# Vertici: %d", 
				this.grafo.vertexSet().size()));
		System.out.println(String.format("# Archi: %d", 
				this.grafo.edgeSet().size()));
	}
	
	public List<Player> getVertici(){
		return new ArrayList<>(this.grafo.vertexSet());
	}
	
	public boolean grafoCreato() {
		if(this.grafo == null)
			return false;
		else 
			return true;
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Match> getMatches(){
		return dao.listAllMatches();
	}

	public GiocatoreMigliore getGiocatoreMigliore() {
		double delta;
		double max=0;
		GiocatoreMigliore gm = null;
		for (Player p : grafo.vertexSet()) {
			delta = 0;
			for (DefaultWeightedEdge e : grafo.outgoingEdgesOf(p)) {
				delta += grafo.getEdgeWeight(e);
			}
			for (DefaultWeightedEdge e : grafo.incomingEdgesOf(p)) {
				delta -= grafo.getEdgeWeight(e);
			}
			if (delta>max) {
				gm = new GiocatoreMigliore(p,delta);
				max=delta;
			}
		}
		return gm;
	}
	
	public Statistiche simula (Match m, int n) {
		Player gm = getGiocatoreMigliore().getGiocatore();
		String tm = dao.getSquadraGiocatore(gm);
		sim.init(m,n,tm);
		sim.run();
		return sim.getStatistiche();
	}
	
}
