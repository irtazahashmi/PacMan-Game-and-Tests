package nl.tudelft.jpacman.board;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Testing for the Board class.
 */
public class BoardTest {

    /**
     * This tests a basic board that has 1x1 dimensions.
     */
    @Test
    void squareAtTestBasic() {
        BasicSquare squareOne = new BasicSquare();
        Square[][] squareArray = new Square[1][1];
        squareArray[0][0] = squareOne;
        Board board = new Board(squareArray);
        Square temp = board.squareAt(0, 0);
        assertThat(temp).isEqualTo(squareOne);
    }

    /**
     * A parametarized test for boundry testing for the method withinBorders.
     * @param x - the x value.
     * @param y - the y value.
     * @param result - the expected result.a
     */
    @ParameterizedTest
    @CsvSource({
        "0, 0, true",
        "1, 1, true",
        "0, 1, true",
        "1, 0, true",
        "-1, -1, false",
        "5, 5, false",
        "2, 2, false",
    })
    void withinBordersTest(int x, int y, boolean result) {
        Square[][] squareArray = new Square[2][2];
        for (int i = 0; i < squareArray.length; i++) {
            for (int j = 0; j < squareArray[0].length; j++) {
                squareArray[i][j] = new BasicSquare();
            }
        }
        Board board = new Board(squareArray);
        assertThat(board.withinBorders(x, y)).isEqualTo(result);
    }

}
