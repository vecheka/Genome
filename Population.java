import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * TCSS 342 - HW 2 
 */

/**
 * @author Vecheka Chhourn
 * @version 04/06/2018
 *
 */
public class Population {
	
	/** Most fit Genome.*/
	private Genome mostFit;
	/** List to store the number of Genomes.*/
	private List<Genome> myPopulationList;
	
	
	/** 
	 * Constructor that accepts number of Genomes to initialize a list of Genome, 
	 * and mutationRate.
	 * @param numGenomes number of Genomes in the population
	 * @param mutationRate rate of breeding
	 */
	public Population(final Integer numGenomes, final double mutationRate) {
		if (mutationRate >= 1 || mutationRate < 0) {
			throw new IllegalArgumentException("Please enter a number between 0 and 1.");
		}
		mostFit = new Genome(mutationRate);
		myPopulationList = new ArrayList<Genome>();
		for (int i = 0; i < numGenomes; i++) {
			myPopulationList.add(new Genome(mutationRate));
		}	
	}
	
	/**
	 * To breed new Genomes in the population, update the most fit 
	 * Genome, and delete least fit half of the population.
	 */
	public void day() {
		
		getMostFitGenome();
		deleteLeastFits();
		createNewGenomes();
		
		
	}

	// Added method(s)
	/**
	 * To get the most fit Genome in the populations.
	 * @return most fit Genome.
	 */
	public Genome getMostFit() {
		return mostFit;
	}
	
	
	
	
	// helper methods
	/**
	 * Get random position in the list.
	 * @param theSize size of the list.
	 * @return a random position
	 */
	private int getRandomIndex(final int theSize) {
		return new Random().nextInt(theSize);
	}
	
	
	/**
	 * Getting rid of least fit half of the populations.
	 */
	private void deleteLeastFits() {
		int size = myPopulationList.size() / 2;
		int index = 0;
		while (size > 0) {
			Genome leastFit = myPopulationList.get(0);
			for (int i = 1; i < myPopulationList.size(); i++) {
				if (leastFit.fitness() <= 
						myPopulationList.get(i).fitness()) {
					leastFit = myPopulationList.get(i);
					index = i;
				}
			}
			myPopulationList.remove(index);
			index = 0;
			size--;	
		}
		
	}

	
	/**
	 * To find the most fit Genome in the population.
	 */
	private void getMostFitGenome() {
		mostFit = myPopulationList.get(0);
		for (int i = 1; i < myPopulationList.size(); i++) {
			mostFit = mostFit.fitness() > myPopulationList.get(i).fitness() ? myPopulationList.get(i) : mostFit;
		}
		
	}
	
	/**
	 * Create new Genomes in the population.
	 */
	private void createNewGenomes() {
		int size = myPopulationList.size();
		int runTime = 0;

		while (size > 0) {
			int index = getRandomIndex(size);
			final Genome newGenome = new Genome(myPopulationList.get(index));
			
			if (runTime % 2 != 0) {
				index = getRandomIndex(size);
				newGenome.crossover(new Genome(myPopulationList.get(index)));
			} 
			newGenome.mutate();
			myPopulationList.add(newGenome);
			runTime++;
			size--;
		}
	}
	
}
