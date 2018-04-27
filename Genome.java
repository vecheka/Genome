import java.util.Random;

/*
 * TCSS 342 - HW 2 
 */

/**
 * @author Vecheka Chhourn
 * @version 04/06/2018
 *
 */
public class Genome {

	/** Default set of characters in the Genome.*/
	private static final String GENOME_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ_-'";

	/** Target string to evolve to.*/
	private static final String target = "PAULO SERGIO LICCIARDI MESSEDER BARRETO";
	
	/** Mutation Rate.*/
	private double myMutationRate;
	/** Initial Genome value*/
	private String myGenome;

	/**
	 * Constructor to initialize Genome's value, and to accept
	 * a mutation rate.
	 * @param mutationRate a rate use to select random character in a given set
	 */
	public Genome(final double mutationRate) {
		myMutationRate = mutationRate;
		myGenome = "A";
	}


	/**
	 * Copy constructor to initialize this Genome to another Genome.
	 * @param gene another type of Genome
	 */
	public Genome(final Genome gene) {
		this.myGenome = gene.myGenome;
	}


	/** 
	 * Mutate Genome by adding, removing, and replacing a character in the Genome
	 * with a randomly selected character from the set
	 */
	public void mutate() {
		char randomChar = spaceCharacter(GENOME_SET.charAt(getRandomIndex(GENOME_SET.length())));

		// adding a character
		int index = getRandomIndex(myGenome.length());
		if (isMutable() 
				&& myGenome.length() < target.length()) {
			if (index == 0) {
				myGenome = randomChar + myGenome;
			} else if (index == myGenome.length() - 1) {
				myGenome += randomChar;
			} else {
				final String temp = myGenome.substring(index, myGenome.length());
				myGenome = myGenome.substring(0, index) + randomChar + temp;
			}
		}


		// deleting a character
		if (!isMutable() && myGenome.length() > 2) {
			
			index = getRandomIndex(myGenome.length());
			String temp = "";
			for (int i = 0; i < myGenome.length(); i++) { 
				myGenome = i != index ? temp += myGenome.charAt(i) : temp;
			}

			myGenome = temp;
		}


		// replacing characters
		for (int i = 0; i < myGenome.length(); i++) {
			if (isMutable() && myGenome.charAt(i) != target.charAt(i)) {
				randomChar = spaceCharacter(GENOME_SET.charAt(getRandomIndex(GENOME_SET.length())));
				if (i == 0) {
					myGenome = randomChar + myGenome.substring(1);
				} else if (i == myGenome.length() - 1) {
					myGenome = myGenome.substring(0, i) + randomChar;
				} else {
					myGenome = myGenome.substring(0, i) + randomChar + myGenome.substring(i + 1);
				}

			}
		}


	}


	/**
	 * Breeding this Genome with another Genome to create a new one.
	 * @param other the other Genome to breed with
	 */
	public void crossover(final Genome other) {
		final int length =  Math.max(myGenome.length(), other.myGenome.length());
		final String[] parentStr = {myGenome, other.myGenome};
		String newBreed = "";
		final String temp = parentStr[getRandomIndex(parentStr.length)];
		for (int i = 0; i < length; i++) {
			myGenome = i < temp.length() ? newBreed += temp.charAt(i) : newBreed; 
		}

	}


	/**
	 * Get the fitness level of the Genome.
	 * @return Genome's fitness level.
	 */
	public Integer fitness() {
		final int n = myGenome.length();
		final int m = target.length();
		final int L = Math.max(n, m);
		int f = Math.abs(m - n);

		for (int i = 0; i < L; i++) {
			if (i <= n - 1 
					&& i <= m  - 1) {
				f = myGenome.charAt(i) != target.charAt(i) ? f += 1: f;
			} else {
				f++;
			}
		}

		return f;
	}

//		/**
//		 * Optional Method for finding the fitness level of a Genome.
//		 * @return Genome's fitness level.
//		 */
//		public Integer fitness() {
//			final int n = myGenome.length();
//			final int m = target.length();
//			final int[][] D = new int[n + 1][m + 1];
//			
//			for (int i = 1; i < n; i++) {
//				for (int j = 1; j < m; j++) {
//					if (myGenome.charAt(i - 1) == target.charAt(j - 1)) {
//						D[i][j] = D[i -1][ j - 1];
//					} else {
//						final int temp = Math.min(D[i - 1][j] + 1, D[i][j - 1] + 1);
//						D[i][j] = Math.min(temp, D[i - 1][j - 1]);
//					}
//				}
//			}
//			
//			return D[n][m] + (Math.abs(n-m) + 1) / 2;
//		}


	@Override
	public String toString() {
		return "(\"" + myGenome + "\", " + fitness() + ")";
	}



	// helper methods
	/**
	 * Check if it's a chance for the Genome to mutate.
	 * @return true if it is lucky enough to mutate
	 */
	private boolean isMutable() {
		return new Random().nextLong() > myMutationRate;
	}

	/**
	 * Check if a character is equivalent to a space character.
	 * @param theChar character to check 
	 * @return a character
	 */
	private char spaceCharacter(final char theChar) {
		return theChar == '_' ? ' ' : theChar;
	}

	/**
	 * Generate a random index.
	 * @param theLength length of a string
	 * @return random position of the string
	 */
	private int getRandomIndex(final int theLength) {
		return new Random().nextInt(theLength);
	}


}
