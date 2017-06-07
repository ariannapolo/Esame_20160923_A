package it.polito.tdp.gestionale.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.gestionale.db.DidatticaDAO;

public class Model {

	private List<Corso> corsi;
	private List<Studente> studenti;
	private DidatticaDAO didatticaDAO;
	private UndirectedGraph<Nodo,DefaultEdge> graph;
	private Map<Integer, Studente> mappaStudenti;
	
	public Model() {
		graph = new SimpleGraph<>(DefaultEdge.class);
		didatticaDAO = new DidatticaDAO();
		this.mappaStudenti = new HashMap<Integer, Studente>();
	}
	
	public void generaGrafo(){
		studenti = getTuttiStudenti();
		System.out.println("Studenti #: "+studenti.size());
		corsi = getTuttiCorsi();
		System.out.println("Corsi #: "+corsi.size());
		//aggiungo vertici
		Graphs.addAllVertices(graph, studenti);
		Graphs.addAllVertices(graph, corsi);
		System.out.println("Numero vertici: "+graph.vertexSet().size());
		//aggiungo archi
		for(Corso c : corsi){
			for(Studente s : c.getStudenti()){
				graph.addEdge(c, s);
			}
		}
		System.out.println("Numero archi: "+graph.edgeSet().size());
		System.out.println("Grafo CREATO!");
		
	}

	public List<Corso> getTuttiCorsi() {
		if(corsi == null){
			corsi = didatticaDAO.getTuttiICorsi();
			getTuttiStudenti();
			for(Corso c : corsi){
				didatticaDAO.setStudentiIscrittiAlCorso(c, mappaStudenti);
			}
			
		}
		return corsi;
	}

	public List<Studente> getTuttiStudenti() {
		if(studenti == null){
			studenti = didatticaDAO.getTuttiStudenti();
			for(Studente s : studenti){
				this.mappaStudenti.put(s.getMatricola(), s);
			}
		}
		return studenti;
	}
	
	public List<Integer> getStatCorsi(){
		List<Integer> stat = new ArrayList<>();
		//inizializzo la struttura dove salvare le statistiche
		for(int i=0; i<corsi.size()+1; i++){
			stat.add(0);
		}
		for(Studente s: studenti){
			int nCorsi = Graphs.neighborListOf(graph, s).size();
			int counter = stat.get(nCorsi);
			counter++;
			stat.set(nCorsi, counter);
		}
		return stat;
	}
}
