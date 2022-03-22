package pk.ito.problem;

/* Enumeration of actions that led to creation of nPuzzle board state */
public enum CreatingAction {
  GENERATION("randomly generated"),
  INPUT("user input"),
  MOVE_UP("move up"),
  MOVE_RIGHT("move right"),
  MOVE_DOWN("move down"),
  MOVE_LEFT("move left");

  private final String description;

  CreatingAction(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
