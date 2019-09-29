package nl.tudelft.jpacman.integration.suspension;

import nl.tudelft.jpacman.Launcher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * A class to do systems test for PacMan Scenario 4.
 */
public class PacManSystemTestScenario4 {

    private Launcher launcher;

    /**
     * A launcher to start the game displaying the user interface.
     */
    @BeforeEach
    public void setUp() {
        launcher = new Launcher();
    }

    /**
     * Closing the launcher to close the user interface.
     */
    @AfterEach
    public void after() {
        launcher.dispose();
    }

    /**
     * Testing for when the player clicks the "Stop" button;
     * Then all moves from ghosts and the player are suspended.
     */
    @Test
    public void gameSuspend() {
        launcher.launch();
        launcher.getGame().start();
        assertThat(launcher.getGame().isInProgress()).isTrue();
        launcher.getGame().stop();
        assertThat(launcher.getGame().isInProgress()).isFalse();
    }

    /**
     * Testing for given the game is suspended;
     * When the player hits the "Start" button;
     * Then the game is resumed.
     */
    @Test
    public void gameRestart() {
        launcher.launch();
        launcher.getGame().start();
        launcher.getGame().stop();
        assertThat(launcher.getGame().isInProgress()).isFalse();
        launcher.getGame().start();
        assertThat(launcher.getGame().isInProgress()).isTrue();
    }
}
