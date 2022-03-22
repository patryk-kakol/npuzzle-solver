package pk.ito.control;

import pk.ito.problem.NPuzzle;
import pk.ito.method.AStar;
import pk.ito.problem.Heuristics;
import pk.ito.method.IDAStar;
import pk.ito.method.Node;
import pk.ito.method.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

  /* generated or given by user puzzle state to solve */
  private NPuzzle startingPuzzle;

  public Scanner scanner = new Scanner(System.in);

  /* application main menu */
  public void start() {
    System.out.println("PROBLEM MENU. Choose:" +
        "\n\t 1 - to generate puzzle" +
        "\n\t 2 - to input puzzle" +
        "\n\t 0 - to exit");
    switch (getUserInput()) {
      case 1: generationStart(); break;
      case 2: inputStart(); break;
      case 0: System.exit(0);
      default: notifyAndExit("Incorrect input.");
    }
    System.out.println("SOLUTION MENU. Choose:" +
        "\n\t 1 - to solve using A* (estimated cost function h(n) - number of tiles off target place)" +
        "\n\t 2 - to solve using A* (estimated cost function h(n) - sum of manhattan distances)" +
        "\n\t 3 - to solve using IDA* (estimated cost function h(n) - number of tiles off target place)" +
        "\n\t 4 - to solve using IDA* (estimated cost function h(n) - sum of manhattan distances)" +
        "\n\t 0 - to exit");
    switch (getUserInput()) {
      case 1: printFinalReport("A* (estimated cost function h(n) - number of tiles off target place)",
          AStar.solve(startingPuzzle, Heuristics.tilesOffPlace)); break;
      case 2: printFinalReport("A* (estimated cost function h(n) - sum of manhattan distances)",
          AStar.solve(startingPuzzle, Heuristics.manhattanDistance)); break;
      case 3: printFinalReport("IDA* (estimated cost function h(n) - number of tiles off target place)",
          IDAStar.solve(startingPuzzle, Heuristics.tilesOffPlace)); break;
      case 4: printFinalReport("IDA* (estimated cost function h(n) - sum of manhattan distances)",
          IDAStar.solve(startingPuzzle, Heuristics.manhattanDistance)); break;
      case 0: System.exit(0);
      default: notifyAndExit("Incorrect input.");
    }
    System.out.print("Press Enter to quit...");
    scanner.nextLine();
  }

  /* guides user through generating puzzle board state */
  private void generationStart() {
    System.out.println("\nPROBLEM GENERATION MENU. Selected random puzzle generation." +
        "\n\t Please input N number. Where sqrt(N+1) has to be positive integer (common N examples: 8, 15, 24...)");
    int selection = getUserInput();
    double a = Math.sqrt(selection+1);
    if (a < 2 || (a % 1) != 0) notifyAndExit("Incorrect input.");
    startingPuzzle = new NPuzzle(selection);
    System.out.println("\nPuzzle generated: ");
    System.out.println(startingPuzzle.getPrettyStringBoard());
  }

  /* guides user through process of inputting puzzle board state */
  private void inputStart() {
    System.out.println("\nPROBLEM INPUT MENU. Selected input puzzle." +
        "\n\t First please input N number. Where sqrt(N+1) has to be positive integer (common N examples: 8, 15, 24...)");
    int n = getUserInput();
    double a = Math.sqrt(n+1);
    if (a < 2 || (a % 1) != 0) notifyAndExit("Incorrect input.");
    System.out.println("\nPROBLEM INPUT MENU. Selected input puzzle." +
        "\n\t Input " + (n + 1) + " unique puzzle values one by one. " +
        "\n\t Row by row. From left to right and from top to bottom. Use 0 to input empty value.");
    ArrayList<Integer> userInputBoard = new ArrayList<>();
    for (int i = 0; i <= n; i++) {
      int inputValue = getUserInput();
      if (inputValue == -1) {
        notifyAndExit("Incorrect value.");
      } else if (inputValue > n) {
        notifyAndExit("Value too big for N=" + n + ".");
      } else if (userInputBoard.contains(inputValue)) {
        notifyAndExit("Value " + inputValue + " is not unique.");
      } else {
        userInputBoard.add(inputValue);
      }
    }
    NPuzzle userInputPuzzle = new NPuzzle(n, userInputBoard);
    if (!userInputPuzzle.isSolvable(userInputPuzzle.getBoard())) {
      notifyAndExit("This board is not solvable.");
    } else {
      startingPuzzle = userInputPuzzle;
    }
    System.out.println("\nPuzzle inputed: ");
    System.out.println(startingPuzzle.getPrettyStringBoard());
  }

  /* prints final report, containing solution and statistics */
  private void printFinalReport(String algorithm, Solution solution) {

    StringBuilder fullSolutionDescription = new StringBuilder(
        "Solved using " + algorithm
        + "\n" + solution.getSummary()
        + "\nin time: " + solution.getTime() + "sec\n");

    List<NPuzzle> solutionSteps = new ArrayList<>();
    Node currentStep = solution.getFinalNode();
    do {
      solutionSteps.add(currentStep.getState());
      currentStep = currentStep.getParent();
    } while (currentStep != null);

    for (int i = 0; i < solutionSteps.size(); i++) {
      int index = solutionSteps.size() - i - 1;
      fullSolutionDescription.append("\n").append(i).append(". action: ")
          .append(solutionSteps.get(index).getCreatingAction().getDescription())
          .append("\n").append(solutionSteps.get(index).getPrettyStringBoard());
    }
    System.out.println(fullSolutionDescription);
    String fileName = FilePrinter.printToFile(fullSolutionDescription.toString());
    System.out.println("Full solution saved in file: " + fileName);
  }

  /* collects user input and parse it to int */
  private int getUserInput() {
    System.out.println("Your input: ");
    var input = scanner.next();
    scanner.nextLine();
    int selection = -1;
    try {
      selection = Integer.parseInt(input);
    } catch (Exception ignored) {}
    return selection;
  }

  /* prints error notification and exits application */
  private void notifyAndExit(String reason) {
    System.out.println(reason + " Application will close.");
    System.exit(0);
  }
}
