package World;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IOUtility {

	public Maze ReadInput(String path, int g) throws IOException {

		int goal = g;

		List<String> lines = Files.readAllLines(Paths.get(path));

		List<List<Integer>> nodes = new ArrayList<List<Integer>>();

		List<Integer> node;

		for (String line : lines) {
			node = new ArrayList<Integer>();

			for (String s : Arrays.asList(line.split(","))) {
				node.add(Integer.valueOf(s));
			}

			nodes.add(node);
		}

		return new Maze(nodes.size(), nodes, goal);

	}

}
