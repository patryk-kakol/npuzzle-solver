package pk.ito.method;

public class Solution {

  /* time of finding the solution */
  private final double time;
  /* summary containing basic statistics */
  private final String summary;
  /* solved Node */
  private final Node finalNode;

  /* all fields constructor */
  public Solution(double time, String summary, Node finalNode) {
    this.time = time;
    this.summary = summary;
    this.finalNode = finalNode;
  }

  /**** getters ****/
  public double getTime() {
    return time;
  }

  public String getSummary() {
    return summary;
  }

  public Node getFinalNode() {
    return finalNode;
  }
}
