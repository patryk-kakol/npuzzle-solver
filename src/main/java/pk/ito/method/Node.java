package pk.ito.method;

import pk.ito.problem.NPuzzle;

import java.util.function.Function;

public class Node {

  /* reference to parent Node */
  private final Node parent;
  /* current board state */
  private final NPuzzle state;
  /* g(n) - cost of getting to this node */
  private final int currentCostG;
  /* h(n) - estimated cost of traveling from this node to solved node */
  private final int estimatedCostH;

  /* constructor taking first 3 parameters directly as field values
   * and estimation function in order to calculated estimatedCostH */
  public Node(Node parent, NPuzzle state, int currentCostG,
              Function<Node, Integer> estimationFunction) {
    this.parent = parent;
    this.state = state;
    this.currentCostG = currentCostG;
    this.estimatedCostH = estimationFunction.apply(this);
  }

  /* returns total cost f(n) */
  public int getTotalCost() {
    return currentCostG + estimatedCostH;
  }

  /* checks if node contains solution */
  public boolean isSolved() {
    return this.state.isSolved();
  }

  /* overridden equals method - checks equality only by board state */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Node that = (Node) o;
    return this.getState().getBoard().equals(that.getState().getBoard());
  }

  /**** getters ****/
  public Node getParent() {
    return parent;
  }

  public NPuzzle getState() {
    return state;
  }

  public int getCurrentCostG() {
    return currentCostG;
  }

  public int getEstimatedCostH() {
    return estimatedCostH;
  }
}
