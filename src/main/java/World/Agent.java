package World;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

public class Agent {

	double Q[][];
	int startState;
	int goalState;
	double gamma;
	ArrayList<Integer> path = new ArrayList<Integer>();

	public void train(Maze maze, int startState, int goalState, int iteration, double gamma) {
		this.startState = startState;
		this.goalState = goalState;
		this.Q = new double[maze.R.length][maze.R.length];
		this.gamma = gamma;
		int currentState;
		int chosenState;

		Random rand = new Random();

		for (int i = 0; i < iteration; i++) {

			currentState = rand.nextInt(maze.R.length);

			do {
				chosenState = chooseAction(maze.getNode(currentState).possibleActions());
				getReward(maze, currentState, chosenState);
				currentState = chosenState;
			} while (!maze.getNode(currentState).isGoal);

			getReward(maze, currentState, chosenState);

		}

	}

	public int chooseAction(int possibleActions[]) {
		Random random = new Random();
		int action = random.nextInt(possibleActions.length);

		return possibleActions[action];
	}

	public void getReward(Maze maze, int currentState, int chosenState) {
		Q[currentState][chosenState] = (maze.getAction(currentState, chosenState)
				+ (gamma * maxQ(maze, chosenState, false)));

	}

	public int maxQ(Maze maze, int chosenState, boolean returnIndex) {
		double max = Q[chosenState][maze.getNode(chosenState).connections[0]];
		int index = 0;

		for (int i = 1; i < maze.getNode(chosenState).connections.length; i++) {
			if (Q[chosenState][maze.getNode(chosenState).connections[i]] > max) {
				max = Q[chosenState][maze.getNode(chosenState).connections[i]];
				index = i;
			}
		}
		if (returnIndex)
			return index;
		else
			return (int) max;
	}

	public void printQ(String outdir) {

		NumberFormat formatter = new DecimalFormat("#.#");

		for (int i = 0; i < Q.length; i++) {
			for (int j = 0; j < Q.length; j++) {
				System.out.print(formatter.format(Q[i][j]) + " ");
			}
			System.out.println();
		}

		try {
			FileWriter fw;
			fw = new FileWriter(outdir);
			BufferedWriter write = new BufferedWriter(fw);

			write.write("Q Matrix");
			write.newLine();
			write.write("------------");
			write.newLine();

			for (int i = 0; i < Q.length; i++) {
				for (int j = 0; j < Q.length; j++) {
					write.write(formatter.format(Q[i][j]) + " ");
				}
				write.newLine();
			}

			write.close();
		} catch (IOException e) {
			JOptionPane.showConfirmDialog(null, "Error: " + e.getMessage());
		}
	}

	public void printPath(Maze maze, String outdir) {
		int state = this.startState;

		do {
			path.add(maze.getNode(state).id);
			System.out.print(maze.getNode(state).id + " - ");
			state = maze.getNode(state).possibleActions()[(int) maxQ(maze, state, true)];

		} while (!maze.getNode(state).isGoal);
		path.add(maze.getNode(state).id);
		System.out.println(maze.getNode(state).id);

		try {
			FileWriter fw;
			fw = new FileWriter(outdir);
			BufferedWriter write = new BufferedWriter(fw);

			write.write("Path");
			write.newLine();
			write.write("------------");
			write.newLine();

			for (Integer i : path) {
				write.write(i + " ");
			}

			write.close();

		} catch (IOException e) {
			JOptionPane.showConfirmDialog(null, "Error: " + e.getMessage());
		}

	}

}
