package World;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class MazeGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	public Maze maze;
	public Agent agent;

	public MazeGUI(Maze maze, Agent agent) {
		setTitle("Maze");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(550, 100, 450, 300);
		this.maze = maze;
		this.agent = agent;
	}

	public void display() {
		add(new CustomComponent(maze, agent));
		pack();
		setMinimumSize(getSize());// enforces the minimum size of both frame and
									// component
		setVisible(true);
	}

}

class CustomComponent extends JComponent {

	private static final long serialVersionUID = 1L;
	Maze maze;
	Agent agent;

	public CustomComponent(Maze maze, Agent agent) {
		this.maze = maze;
		this.agent = agent;
	}

	@Override
	public Dimension getMinimumSize() {
		return new Dimension(300, 300);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(700, 600);
	}

	@Override
	public void paintComponent(Graphics g2) {
		Graphics2D g = (Graphics2D) g2;

		int nodeSize = 50;
		int mazeSize = (int) Math.sqrt(maze.R.length);
		super.paintComponent(g);

		g.setStroke(new BasicStroke(10));
		g.setColor(Color.BLACK);
		int x, y;
		x = 0;
		y = 0;

		g.drawRect(x + nodeSize, y + nodeSize,
				(int) ((Math.sqrt(maze.R.length) + (Math.sqrt(maze.R.length) - 1)) * nodeSize),
				(int) ((Math.sqrt(maze.R.length) + (Math.sqrt(maze.R.length) - 1)) * nodeSize));

		for (int i = 0; i < mazeSize; i++) {
			x = 0;
			y += nodeSize;

			for (int j = 0; j < mazeSize; j++) {
				x += nodeSize;
				g.setColor(new Color(252, 252, 252));
				g.fillRect(x, y, nodeSize, nodeSize);

				if (j != mazeSize - 1) {
					if (!maze.getNode(j + i * mazeSize).contains((j + i * mazeSize) + 1)) {
						x += nodeSize;
						g.setColor(Color.BLACK);
						g.fillRect(x, y, nodeSize, nodeSize);
					} else {
						x += nodeSize;
						g.setColor(new Color(252, 252, 252));
						g.fillRect(x, y, nodeSize, nodeSize);
					}
				}

				if (i != mazeSize - 1 && j == mazeSize - 1) {
					if (!maze.getNode(j + i * mazeSize).contains(j + i * mazeSize + mazeSize)) {
						y += nodeSize;
						g.setColor(Color.BLACK);
						g.fillRect(x, y, nodeSize, nodeSize);
					} else {
						y += nodeSize;
						g.setColor(new Color(252, 252, 252));
						g.fillRect(x, y, nodeSize, nodeSize);
					}

				}

				if (i != mazeSize - 1) {
					if (!maze.getNode(j + i * mazeSize).contains(j + i * mazeSize + mazeSize)) {
						x -= nodeSize;
						y += nodeSize;
						g.setColor(Color.BLACK);
						g.fillRect(x, y, nodeSize * 2, nodeSize);
					} else {
						x -= nodeSize;
						y += nodeSize;
						g.setColor(new Color(252, 252, 252));
						g.fillRect(x, y, nodeSize, nodeSize);
						g.setColor(Color.BLACK);
						g.fillRect(x + nodeSize, y, nodeSize, nodeSize);
					}
					x += nodeSize;
					y -= nodeSize;
				}

			}

		}

		int x1, x2, y1, y2;

		g.setStroke(new BasicStroke(5));

		for (int i = 0; agent.path.get(i) != agent.goalState; i++) {

			x1 = ((agent.path.get(i) % (int) Math.sqrt(agent.Q.length)) * nodeSize * 2 + 75);
			y1 = ((agent.path.get(i) / (int) Math.sqrt(agent.Q.length)) * nodeSize * 2 + 75);

			x2 = ((agent.path.get(i + 1) % (int) Math.sqrt(agent.Q.length)) * nodeSize * 2 + 75);
			y2 = ((agent.path.get(i + 1) / (int) Math.sqrt(agent.Q.length)) * nodeSize * 2 + 75);

			g.setColor(new Color(175, 242, 169));
			g.drawLine(x1, y1, x2, y2);

			if (agent.path.get(i) == agent.startState) {
				g.setColor(Color.RED);
				g.drawString("Start", x1 + 5, y1 + 5);

			}

			if (agent.path.get(i + 1) == agent.goalState) {
				g.setColor(Color.RED);
				g.drawString("Goal", x2 + 5, y2 + 5);

			}
		}

	}
}