package pk.ito.method;

import pk.ito.problem.NPuzzle;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/* class containing IDA* algorithm implementation */
public class IDAStar {

  /* node containing solution */
  private static Node solved = null;
  /* heuristic used as h(n) estimation Function */
  private static Function<Node, Integer> estimationFunction;

  /* statistics: */
  private static int iterationCount = 0;
  private static int checkedNodes = 0;

  /* solving method */
  public static Solution solve(NPuzzle startingPuzzle, Function<Node, Integer> estimateCost) {
    estimationFunction = estimateCost;

    double start = System.currentTimeMillis();

    Node init = new Node(null, startingPuzzle, 0, estimateCost);
    int fThreshold = init.getTotalCost();
    do {
      fThreshold = search(init, fThreshold);
      iterationCount++;
      System.out.println("Iteration: " + iterationCount + ", nodes checked: " + checkedNodes + "...");
    } while (solved == null);

    double end = System.currentTimeMillis();
    double time = (end-start)/1000;

    return new Solution(
        time,
        ("iterations=" + iterationCount
            + ", checked nodes=" + checkedNodes
            + ", solution cost=" + solved.getTotalCost()),
        solved);
  }

  /* helper method containing recursive part of algorithm */
  public static int search(Node node, int fThreshold) {
    Set<Integer> exceedingThresholds = new HashSet<>();
    if (node.isSolved()) {
      solved = node;
      return fThreshold;
    }
    if (node.getTotalCost() > fThreshold) {
      return node.getTotalCost();
    }
    node.getState().generateChildren().stream()
        .map(nPuzzle -> new Node(node, nPuzzle, node.getCurrentCostG() + 1, estimationFunction))
        .forEach(childNode -> {
          checkedNodes++;
          exceedingThresholds.add(search(childNode, fThreshold));
        });
    return Collections.min(exceedingThresholds);
  }
}
