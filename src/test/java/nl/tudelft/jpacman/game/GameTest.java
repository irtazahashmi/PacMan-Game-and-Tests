package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.points.PointCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Testing the Game Test class.
 */
public class GameTest {

    @Mock
    private Player playerMockObject;

    @Mock
    private Level levelMockObject;

    @Mock
    private PointCalculator pointCalculatorMockObject;


    private SinglePlayerGame singlePlayerGame;


    /**
     * Setting up the mocks to test the Game Test class.
     */
    @BeforeEach
    void setUp() {
        playerMockObject = Mockito.mock(Player.class);
        levelMockObject = Mockito.mock(Level.class);
        pointCalculatorMockObject = Mockito.mock(PointCalculator.class);
        singlePlayerGame = new SinglePlayerGame(playerMockObject,
            levelMockObject, pointCalculatorMockObject);
    }

    /**
     * Testing the first return statement.
     */
    @Test
    void firstReturn() {
        Mockito.when(levelMockObject.isAnyPlayerAlive()).thenReturn(true);
        Mockito.when(levelMockObject.remainingPellets()).thenReturn(1);

        singlePlayerGame.start();
        singlePlayerGame.start();

        assertThat(singlePlayerGame.isInProgress()).isEqualTo(true);
    }

    /**
     * Testing when the Player is alive is equal to true, remaining pellets
     * is one and the assertion is true.
     */
    @Test
    void trueOneTrue() {
        Mockito.when(levelMockObject.isAnyPlayerAlive()).thenReturn(true);
        Mockito.when(levelMockObject.remainingPellets()).thenReturn(1);

        singlePlayerGame.start();
        assertThat(singlePlayerGame.isInProgress()).isTrue();
    }

    /**
     * Testing when the Player is alive is equal to false, remaining pellets
     * is one and the assertion is false.
     */
    @Test
    void falseOneFalse() {
        Mockito.when(levelMockObject.isAnyPlayerAlive()).thenReturn(false);
        Mockito.when(levelMockObject.remainingPellets()).thenReturn(1);

        singlePlayerGame.start();
        assertThat(singlePlayerGame.isInProgress()).isFalse();
    }

    /**
     * Testing when the Player is alive is equal to false, remaining pellets
     * is zero and the assertion is false.
     */
    @Test
    void falseZeroFalse() {
        Mockito.when(levelMockObject.isAnyPlayerAlive()).thenReturn(false);
        Mockito.when(levelMockObject.remainingPellets()).thenReturn(0);

        singlePlayerGame.start();
        assertThat(singlePlayerGame.isInProgress()).isFalse();
    }

    /**
     * Testing when the Player is alive is equal to true, remaining pellets
     * is zero and the assertion is false.
     */
    @Test
    void trueZeroFalse() {
        Mockito.when(levelMockObject.isAnyPlayerAlive()).thenReturn(true);
        Mockito.when(levelMockObject.remainingPellets()).thenReturn(0);

        singlePlayerGame.start();
        assertThat(singlePlayerGame.isInProgress()).isFalse();
    }
}
