package checkpoint1;
import java.awt.Color;

class Walk {
	public static void main(final String[] args) {
		if (args.length != 4) {
			System.err.println("Arguments: width height depth steps/draw");
			return;
		}
		int W = Math.max(1, Integer.parseInt(args[0]));
		int H = Math.max(1, Integer.parseInt(args[1]));
		int S = Math.max(1, Integer.parseInt(args[3]));

		int[][] grid = new int[W][H];
		Visualization vis = new Visualization(W, H);

		int col = (int)(0.5*W);
		int row = (int)(0.5*H);
		
		for (int i = 0; true; i++) {
		

			
			
			if (i%S == 0) visualize(vis, grid, W, H);

		}
	}

	static void visualize(Visualization vis, int[][] grid, int W, int H) {
		for (int col = 0; col < W; col++) for (int row = 0; row < H; row++) vis.set(col, row, grid[col][row] == 1 ? Color.BLACK : Color.white);

		vis.draw();
	}
}
