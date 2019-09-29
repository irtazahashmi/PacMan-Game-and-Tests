package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Game class unit test.
 */
public abstract class GameUnitTest {

    private Launcher launcher;

    /**
     * An abstract method that must be implemented in the
     * subclasses of GameUnitTest.
     * @return amn instance of Launcher object.
     */
    public abstract Launcher getLauncher();

    private Player  player;

    /** Starting the launcher with a custom-map.
     * #####
     * #G  #
     * #P. #
     * #   #
     * #####
     */
    @BeforeEach
    public void setUp() {
        launcher = getLauncher();
        launcher.launch();
        launcher.getGame().start();
        player = launcher.getGame().getPlayers().get(0);
    }

    /**
     * When the game ends, close the interface.
     */
    @AfterEach
    public void after() {
        launcher.dispose();
    }

    /**
     * A method to launch a game with the given map.
     * @param mapName mapname.
     * @return a Game.
     */
    protected Game launchGame(String mapName) {
        Launcher myLauncher = new Launcher();
        myLauncher.withMapFile(mapName).launch();
        Game game = myLauncher.getGame();
        game.start();
        return game;
    }

    /**
     * Game is started.
     * Game is now in progress.
     */
    @Test
    public void startGame() {
        assertThat(launcher.getGame().isInProgress()).isTrue();
    }


    /**
     * Game is started and in progress. Player ends game.
     * Game is now not in progress.
     */
    @Test
    public void endGame() {
        launcher.getGame().stop();
        assertThat(launcher.getGame().isInProgress()).isFalse();
    }


    /**
     * Game is in progress and player clicks on pause.
     * Game goes into pause and progress ends.
     */
    @Test
    public void pauseGame() {
        launcher.getGame().stop();
        assertThat(launcher.getGame().isInProgress()).isFalse();
    }


    /**
     * Game is in paused and player clicks to resume.
     * Game is now in progress.
     */
    @Test
    public void resumeGame() {
        launcher.getGame().stop();
        launcher.getGame().start();
        assertThat(launcher.getGame().isInProgress()).isTrue();
    }

    /**
     * Player eats the final pellet.
     * Game is won.
     */
    @Test
    public void finalPellet() {
        launcher.getGame().move(player, Direction.EAST);
        assertThat(player.isAlive()).isTrue();
        assertThat(launcher.getGame().isInProgress()).isFalse();
    }

    /**
     * Ghost kills the player.
     * Game is lost.
     */
    @Test
    public void playerKilled() {
        launcher.getGame().move(player, Direction.NORTH);
        assertThat(player.isAlive()).isFalse();
        assertThat(launcher.getGame().isInProgress()).isFalse();
    }
}
