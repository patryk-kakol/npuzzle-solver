package pk.ito.problem;

import pk.ito.method.Node;

import java.util.ArrayList;
import java.util.function.Function;

/* Class containing functions that are used to estimate future cost - h(n) */
public class Heuristics {

  /* Applied to node counts number of state elements that are not in target indexes */
  public static Function<Node, Integer> tilesOffPlace = node -> {
    int cost = 0;
    ArrayList<Integer> state = node.getState().getBoard();
    for (int i = 0; i < state.size(); i++) {
      int tile = state.get(i);
      if (tile != 0 && (tile - 1) != i) {
        cost++;
      }
    }
    return cost;
  };

  /* Applied to node counts sum of horizontal and vertical distances between element
   * current and target index */
  public static Function<Node, Integer> manhattanDistance = node -> {
    int cost = 0;
    ArrayList<Integer> state = node.getState().getBoard();
    int dimension = node.getState().getA();
    for (int i = 1; i < state.size(); i++) {
      int[] currentIndex = Heuristics.getXYIndex(state.indexOf(i), dimension);
      int[] targetIndex = Heuristics.getXYIndex(i - 1, dimension);
      cost = cost + Math.abs(currentIndex[0] - targetIndex[0]);
      cost = cost + Math.abs(currentIndex[1] - targetIndex[1]);
      }
    return cost;
  };

  /* calculates 2-dimensional array index based on 1-dimensional index
   * and 2-dimensional array width/height */
  private static int[] getXYIndex(int XIndex, int dimension) {
    return new int[] {XIndex / dimension, XIndex % dimension};
  }
}

