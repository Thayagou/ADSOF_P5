package visualizers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

import decisiontrees.DecisionTree;
import visitorPattern.Visitor;

public class GraphvizVisualizer implements Visitor{
	private String file;
	private String graphName;
	
	private static String OTHERWISE_LABEL = "Otherwise";
	private static String FOLDER = "visualizations";
	
	public GraphvizVisualizer(String file, String graphName) {
		this.file = FOLDER + "/" + file;
		this.graphName = graphName;
	}
	@Override
	public void visit(DecisionTree<?> tree) {
		Queue<DecisionTree<?>> cola = new LinkedList<>();
		cola.add(tree);
		
		try (PrintWriter output = new PrintWriter(new FileOutputStream(file));) {
			output.println("digraph " + graphName + "{\n");
			while(!cola.isEmpty()) {
				DecisionTree<?> curr = cola.poll();
				String currName = curr.getName().replace("\n", "\\n");
				
				for (DecisionTree<?> child : curr.getChildren()) {
					output.println("\""+currName + "\"" + " -> " + "\"" + child.getName().replace("\n", "\\n") + "\"" + "\n");
					cola.add(child);
				}

				DecisionTree<?> otherwise = curr.getOtherwise();
				if (otherwise != null) {
					output.println("\""+currName + "\"" + " -> " + "\"" + otherwise.getName().replace("\n", "\\n") + "\"" + "[label=" + OTHERWISE_LABEL + "]"+ "\n");
					cola.add(otherwise);
				}
			}
			output.println("}\n");
		} catch (IOException e) {
			System.out.println("Error: " + e);
			return;
		}
	}

	
	

}
