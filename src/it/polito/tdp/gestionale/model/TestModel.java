package it.polito.tdp.gestionale.model;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();
		
		model.generaGrafo();
		
		System.out.println(model.getStatCorsi());
		
		int counter = 0;
		for(Integer i : model.getStatCorsi()){
			System.out.format("Numero studenti che frequentano %d corsi: %d\n", counter,i);
			counter++;
		}
		
	}

}
