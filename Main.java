import java.util.Random;

import com.sun.scenario.effect.impl.prism.ps.PPSBlend_ADDPeer;

/*
 * TCSS 342 - HW 2 
 */

/**
 * @author Vecheka Chhourn
 * @version 04/06/2018
 *
 */
public class Main {

	/** Default Genomes population numbers.*/
	private static final int POPULATION_NUMBER = 100;
	/** Default mutation rate of Genome.*/
	private static final double MUTATION_RATE = 0.05;
	/** Format milliseconds to seconds.*/
	private static final double SECOND = 1000.0;
	
	
	/**
	 * Main method to execute the program.
	 * @param theArgs command line arguments
	 */
	public static void main(final String[] theArgs) {
		
		final long startTime = System.currentTimeMillis();
		
		final Population genomePopulations = new Population(POPULATION_NUMBER, MUTATION_RATE);
		Genome mostFit = genomePopulations.getMostFit();
		int generations = 0;
		do {
			genomePopulations.day();
			mostFit = genomePopulations.getMostFit();
			System.out.println(mostFit.toString());
			generations++;
		} while (mostFit.fitness() > 0);
		
		final long endTime = System.currentTimeMillis();
		System.out.println("Generations: " + generations);
		
		// in milliseconds
		//		System.out.println("Running Time: " + (endTime - startTime)  + 
//				" milliseconds.");
		
		// in seconds
		System.out.println("Running Time: " + (String.format("%.2f", 
				(endTime - startTime) / SECOND)) + 
				" seconds.");
		
		/* Testing Genome Class. */
//		testGenome();
		
		/* Testing Population Class. */
//		testPopulation();
		
	}

	/**
	 * Test Population class
	 */
	private static void testPopulation() {
		final Population pop = new Population(POPULATION_NUMBER, MUTATION_RATE);
		pop.day();
		pop.day();
		System.out.println("Population after the second day: \n" + pop.toString());
		
	}


	/**
	 * Test Genome Class
	 */
	private static void testGenome() {
		final Genome gene = new Genome(MUTATION_RATE);
		System.out.println("First Initailize Value and Fitness Level: \n" + gene.toString());
		gene.mutate();
		System.out.println("Value after the first mutation: \n" + gene.toString());
		final Genome otherGene = new Genome(MUTATION_RATE);
		gene.crossover(otherGene);
		gene.mutate();
		System.out.println("Value after crossing with other Genome: \n" + gene.toString());
		
	}


}
