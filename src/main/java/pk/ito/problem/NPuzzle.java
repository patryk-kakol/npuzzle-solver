package pk.ito.problem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class NPuzzle {

  /* puzzle variant parameter, common values: 8, 15, 24 */
  private int n;
  /* number of puzzles on the side of the board, a = sqrt(n+1) */
  private int a;
  /* current board state */
  private ArrayList<Integer> board = new ArrayList<>();
  /* action that led to creation of current state */
  private CreatingAction creatingAction;

  /* empty constructor - used for purpose buildChild method */
  private NPuzzle() {}

  /* generating constructor - generates random board state */
  public NPuzzle(int n) {
    this.creatingAction = CreatingAction.GENERATION;
    this.n = n;
    this.a = (int) Math.sqrt(n + 1);
    IntStream.rangeClosed(0, n).forEach(i -> this.board.add(i));
    do {
      Collections.shuffle(board);
    }
    while (!isSolvable(board));
  }

  /* input constructor - takes input board state */
  public NPuzzle(int n, ArrayList<Integer> userInputBoard) {
    this.creatingAction = CreatingAction.INPUT;
    this.n = n;
    this.a = (int) Math.sqrt(n + 1);
    this.board = new ArrayList<>(userInputBoard);
  }

  /* creates child object based on current object, creating action and new board state */
  public NPuzzle buildChild(ArrayList<Integer> newBoard, CreatingAction creatingAction) {
    NPuzzle puzzle = new NPuzzle();
    puzzle.setN(n);
    puzzle.setA(a);
    puzzle.setBoard(newBoard);
    puzzle.setCreatingAction(creatingAction);
    return puzzle;
  }

  /* generates all possible next states */
  public List<NPuzzle> generateChildren() {
    List<NPuzzle> children = new ArrayList<>();
    int emptyIndex = board.indexOf(0);
    if ((emptyIndex - 1) > 0 && emptyIndex % a != 0) {
      ArrayList<Integer> newBoard = new ArrayList<>(board);
      Collections.swap(newBoard, emptyIndex, emptyIndex - 1);
      children.add(buildChild(newBoard, CreatingAction.MOVE_LEFT));
    }
    if ((emptyIndex + 1) < board.size() && (emptyIndex + 1) % a != 0) {
      ArrayList<Integer> newBoard = new ArrayList<>(board);
      Collections.swap(newBoard, emptyIndex, emptyIndex + 1);
      children.add(buildChild(newBoard, CreatingAction.MOVE_RIGHT));
    }
    if ((emptyIndex - a) >= 0) {
      ArrayList<Integer> newBoard = new ArrayList<>(board);
      Collections.swap(newBoard, emptyIndex, emptyIndex - a);
      children.add(buildChild(newBoard, CreatingAction.MOVE_UP));
    }
    if ((emptyIndex + a) < board.size()) {
      ArrayList<Integer> newBoard = new ArrayList<>(board);
      Collections.swap(newBoard, emptyIndex, emptyIndex + a);
      children.add(buildChild(newBoard, CreatingAction.MOVE_DOWN));
    }
    return children;
  }

  /* verifies if given puzzle can be solved */
  public boolean isSolvable(ArrayList<Integer> board) {
    int a = (int) Math.sqrt(board.size());
    int inversions = countBoardInversions(board);
    if (isEven(a)) {
      if (isEven(find0positionFromBottom(board, a))) {
        return !isEven(inversions);
      } else {
        return isEven(inversions);
      }
    } else {
      return isEven(inversions);
    }
  }

  /* verifies if number is even */
  private boolean isEven(int x) {
    return x % 2 == 0;
  }

  /* finds empty tile position counting from bottom */
  public int find0positionFromBottom(ArrayList<Integer> board, int a) {
    int position = 0;
    int index = board.indexOf(0);
    for (int i = board.size() - 1; i >= 0; i = i - a) {
      position++;
      if (index > (i - a) && index <= i) {
        break;
      }
    }
    return position;
  }

  /* counts all board inversions, where inversion is a pair of numbers (a,b) that:
   * a > b and board.indexOf(a) < board.indexOf(b) and a != b != 0 */
  public int countBoardInversions(ArrayList<Integer> board) {
    int inversions = 0;
    for (int i = 0; i < board.size() - 1; i++) {
      for (int j = i + 1; j < board.size(); j++) {
        if (board.get(i) > board.get(j)  && board.get(j) != 0) {
          inversions++;
        }
      }
    }
    return inversions;
  }

  /* checks if puzzle is solved */
  public boolean isSolved() {
    for (int i = 1; i < board.size(); i++) {
      if (board.get(i - 1) != i) {
        return false;
      }
    }
    return board.get(board.size() - 1) == 0;
  }

  /* returns easy to console-print two dimensional state representation */
  public String getPrettyStringBoard() {
    StringBuilder output = new StringBuilder("");
    for (int i = 0; i < board.size(); i++) {
      output.append(board.get(i)).append("\t");
      if ((i + 1) % a == 0) {
       output.append("\n");
      }
    }
    return output.toString();
  }

  /**** getters and setters ****/
  public int getN() {
    return n;
  }

  public int getA() {
    return a;
  }

  public ArrayList<Integer> getBoard() {
    return board;
  }

  public CreatingAction getCreatingAction() {
    return creatingAction;
  }

  public void setN(int n) {
    this.n = n;
  }

  public void setA(int a) {
    this.a = a;
  }

  public void setBoard(ArrayList<Integer> board) {
    this.board = board;
  }

  public void setCreatingAction(CreatingAction creatingAction) {
    this.creatingAction = creatingAction;
  }
}
