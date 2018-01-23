package World;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class Maze {

	int R[][];
	List<Node> nodes;
	int goal;

	class Node {
		int id;
		boolean isGoal;
		int connections[];

		public Node(int id, boolean isGoal, int connections[]) {
			this.id = id;
			this.isGoal = isGoal;
			this.connections = connections;

		}

		public boolean contains(int value) {
			for (int c : connections) {
				if (value == c)
					return true;
			}
			return false;
		}

		public int[] possibleActions() {
			return connections;
		}

	}

	public Maze(int size, List<List<Integer>> nodes, int goal) {
		R = new int[size][size];
		this.goal = goal;
		this.nodes = new ArrayList<Node>();
		boolean isGoal = false;

		for (List<Integer> node : nodes) {

			int connections[];

			if (nodes.indexOf(node) == goal) {
				isGoal = true;
				connections = new int[node.size() + 1];

				for (int i = 0; i < node.size(); i++) {
					connections[i] = node.get(i);
				}
				connections[node.size()] = nodes.indexOf(node);
			} else {
				isGoal = false;
				connections = new int[node.size()];

				for (int i = 0; i < node.size(); i++) {
					connections[i] = node.get(i);

				}
			}

			this.nodes.add(new Node(nodes.indexOf(node), isGoal, connections));

		}

		for (int i = 0; i < R.length; i++) {
			for (int j = 0; j < R.length; j++) {
				if (i == j && this.nodes.get(j).isGoal)
					R[i][j] = 100;
				else if (i == j)
					R[i][j] = -1;
				else {
					if (this.nodes.get(i).contains(j)) {
						if (this.nodes.get(j).isGoal)
							R[i][j] = 100;
						else
							R[i][j] = 0;
					} else {
						R[i][j] = -1;
					}
				}
			}
		}

	}

	public void printR(String outdir) {

		for (int i = 0; i < R.length; i++) {
			for (int j = 0; j < R.length; j++) {
				System.out.print(R[i][j] + " ");
			}
			System.out.println();
		}

		try {
			FileWriter fw;
			fw = new FileWriter(outdir);
			BufferedWriter write = new BufferedWriter(fw);

			write.write("R Matrix");
			write.newLine();
			write.write("------------");
			write.newLine();

			for (int i = 0; i < R.length; i++) {
				for (int j = 0; j < R.length; j++) {
					write.write(R[i][j] + " ");
				}
				write.newLine();
			}
			write.close();

		} catch (IOException e) {
			JOptionPane.showConfirmDialog(null, "Error: " + e.getMessage());
		}

	}

	public Node getNode(int value) {
		return nodes.get(value);
	}

	public int getAction(int x, int y) {
		return R[x][y];
	}

}
