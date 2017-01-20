package checkpoint1;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Ising {

	public static int L = 0;
	public static double T = 0;
	public static double J = 0;
	public static final double kb = 1.0; // m^2 kg s^-2 K^-1
	public static int[][] grid = new int[L][L];

	public static void main(String[] args) throws FileNotFoundException {

		String input = "input";

		Scanner scanner = new Scanner(new FileReader(input));
		L = scanner.nextInt();
		T = scanner.nextDouble();
		J = scanner.nextDouble();
		scanner.close();

		// Initialise grid with spin values
		initialiseGrid();

		int n = 0;
		while (n < 1000) {

			glauberUpdate();

			for (int i = 0; i < grid.length; i++) {
				System.out.println("\n");
				for (int j = 0; j < grid.length; j++) {
					System.out.print("  " + grid[i][j]);
				}
			}
			System.out.println("\n");
			n++;
		}

	}

	public static double localEnergy(int i, int j, boolean flipped) {

		int L = grid[0].length;
		int S = flipped ? -grid[i][j] : grid[i][j];
		int E = 0;

		if (S == grid[(i + 1) % L][j])
			E++;
		else
			E--;

		if (S == grid[(i - 1 + L) % L][j])
			E++;
		else
			E--;

		if (S == grid[i][(j + 1) % L])
			E++;
		else
			E--;

		if (S == grid[i][(j - 1 + L) % L])
			E++;
		else
			E--;

		return E * -1.0 * J;

	}

	public static void initialiseGrid() {
		grid = new int[L][L];
		for (int i = 0; i < L; i++) {
			for (int j = 0; j < L; j++) {
				double random = Math.random();
				if (random >= 0.5) {
					grid[i][j] = 1;
				} else {
					grid[i][j] = -1;
				}
			}
		}
	}

	public static double totalEnergy() {
		double E = 0;
		boolean flipped = true;

		for (int i = 0; i < L; i++) {
			for (int j = 0; j < L; j++) {
				E += localEnergy(i, j, flipped);
			}
		}
		return E / 2.0;
	}

	public static void glauberUpdate() {

		for (int i = 0; i < L * L; i++) {
			// Choose a random grid point
			int randI = (int) (Math.random() * L);
			int randJ = (int) (Math.random() * L);

			// Calculate the energy difference cause by flipping the spin state
			double deltaE = localEnergy(randI, randJ, true)
					- localEnergy(randI, randJ, false);

			double r = Math.random();

			boolean test = r <= Math.min(1.0,
					Math.exp(-1.0 * deltaE / (kb * T)));

			if (test)
				grid[randI][randJ] *= -1;
		}
	}

	public static void kawasakiUpdate() {

		for (int i = 0; i < L * L; i++) {
			// Choose a random grid point
			int i1 = (int) (Math.random() * L);
			int j1 = (int) (Math.random() * L);

			int i2 = (int) (Math.random() * L);
			int j2 = (int) (Math.random() * L);

			if (i1 == i2 && j1 == j2)
				continue;

			if (grid[i1][j1] == grid[i2][j2])
				continue;

			// Calculate the energy difference cause by flipping the spin state
			double deltaE = localEnergy(i1, j1, true)
					- localEnergy(i1, j1, false)
					+ localEnergy(i2, j2, true)
					- localEnergy(i2, j2, false);

			double r = Math.random();

			boolean test = r <= Math.min(1.0,
					Math.exp(-1.0 * deltaE / (kb * T)));

			if (test) {
				grid[i1][j1] *= -1;
				grid[i2][j2] *= -1;
			}
		}
	}
}
