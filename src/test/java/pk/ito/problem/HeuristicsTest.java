package pk.ito.problem;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pk.ito.method.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class HeuristicsTest {

  private static Stream<Arguments> heuristicsTestSetProducer() {
    return Stream.of(
        Arguments.of(8, new int[] {1, 2, 3, 4, 5, 6, 7, 8, 0}, 0, 0),
        Arguments.of(8, new int[] {1, 3, 6, 7, 0, 8, 2, 4, 5}, 7, 12),
        Arguments.of(15, new int[] {1, 12, 4, 10, 5, 7, 15, 2, 14, 9, 0, 11, 3, 8, 13, 6}, 13, 34));
  }

  @ParameterizedTest
  @MethodSource("heuristicsTestSetProducer")
  void heuristicsTest(int n, int[] board, int expectedTilesOffPlace, int expectedManhattanDistance) {
    ArrayList<Integer> testBoard = new ArrayList<>();
    Arrays.stream(board).forEach(testBoard::add);
    NPuzzle puzzle = new NPuzzle(n, testBoard);
    Node node = new Node(null, puzzle, 0, Heuristics.manhattanDistance);
    assertEquals(expectedTilesOffPlace, Heuristics.tilesOffPlace.apply(node));
    assertEquals(expectedManhattanDistance, Heuristics.manhattanDistance.apply(node));
  }

}