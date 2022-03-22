package pk.ito.method;

import pk.ito.problem.NPuzzle;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/* class containing A* algorithm implementation */
public class AStar {

  /* OPEN nodes set */
  private static final Set<Node> open = new HashSet<>();
  /* CLOSED nodes set */
  private static final Set<Node> closed = new HashSet<>();
  /* node containing solution */
  private static Node solved = null;

  /* solving method */
  public static Solution solve(NPuzzle startingPuzzle, Function<Node, Integer> estimateCost) {
    double start = System.currentTimeMillis();
    open.add(new Node(null, startingPuzzle, 0, estimateCost));
    do {
      Node current = open.stream()
          .min(Comparator.comparing(Node::getTotalCost)).orElseThrow(NoSuchElementException::new);
      open.remove(current);
      closed.add(current);
      if (current.isSolved()) {
        solved = current;
        break;
      }
      current.getState().generateChildren().stream()
          .map(nPuzzle -> new Node(current, nPuzzle, current.getCurrentCostG() + 1, estimateCost))
          .forEach(childNode -> {
            if (!open.contains(childNode) && !closed.contains(childNode)) {
              open.add(childNode);
            } else if (open.contains(childNode)) {
              Set<Node> worse = open.stream()
                  .filter(childNode::equals)
                  .filter(node -> node.getCurrentCostG() > current.getCurrentCostG())
                  .collect(Collectors.toSet());
              open.removeAll(worse);
              open.add(childNode);
            } else if (closed.contains(childNode)) {
              Set<Node> worse = closed.stream()
                  .filter(childNode::equals)
                  .filter(node -> node.getCurrentCostG() > current.getCurrentCostG())
                  .collect(Collectors.toSet());
              closed.removeAll(worse);
              open.add(childNode);
            }
          });
      if (closed.size() % 1000 == 0) {
        System.out.println("Open nodes: " + open.size() + ", closed nodes: " + closed.size());
      }
    } while (solved == null);
    double end = System.currentTimeMillis();
    double time = (end - start) / 1000;
    return new Solution(
        time,
        ("closed nodes=" + closed.size()
            + ", open nodes=" + open.size()
            + ", solution cost=" + solved.getTotalCost()),
        solved);
  }

}

