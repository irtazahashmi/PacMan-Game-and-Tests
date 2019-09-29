package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.MultiLevelLauncher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A multi level game class test.
 */
public class MultiLevelGameTest extends GameUnitTest {

    /**
     * A method that returns a game map launcher.
     * @return an instance of MultiLevelLauncher.
     */
    @Override
    public Launcher getLauncher() {
        return new MultiLevelLauncher().withMapFile("/gameMap.txt");
    }

    @Override
    protected Game launchGame(String mapName) {
        Launcher myLauncher = new MultiLevelLauncher();
        myLauncher.withMapFile(mapName).launch();
        Game game = myLauncher.getGame();
        game.start();
        return game;
    }

    /**
     * This method tests if players move up levels
     * if they win the game.
     */
    @Test
    public void gameLevelOneTest() {
        Game game = launchGame("/levelOne.txt");

        Level levelOne = game.getLevel();
        game.move(game.getPlayers().get(0), Direction.EAST);
        assertThat(game.getLevel()).isNotEqualTo(levelOne);
        game.start();

        Level levelTwo = game.getLevel();
        game.move(game.getPlayers().get(0), Direction.EAST);
        assertThat(game.getLevel()).isNotEqualTo(levelTwo);
        game.start();

        Level levelThree = game.getLevel();
        game.move(game.getPlayers().get(0), Direction.WEST);
        assertThat(game.getLevel()).isNotEqualTo(levelThree);
        game.start();
    }

    /**
     * Testing if the player does not level up when killed.
     */
    @Test
    public void playerDoesNotLevelUpWhenKilled() {
        Game game = launchGame("/playerAndGhostMap.txt");

        Level level = game.getLevel();
        game.move(game.getPlayers().get(0), Direction.EAST);
        assertThat(game.getLevel()).isEqualTo(level);
        game.start();

        assertThat(game.isInProgress()).isFalse();
    }

}
