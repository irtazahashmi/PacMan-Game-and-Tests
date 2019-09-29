package nl.tudelft.jpacman.npc.ghost;

import com.google.common.collect.Lists;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.points.DefaultPointCalculator;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Test class for the class Clyde.
 */
public class ClydeTest {
    /**
     * The Map parser that is used to construct the board.
     */
    private static GhostMapParser ghostMapParser;

    /**
     * Creating the player.
     */
    private static Player player;

    /**
     * Setting up the map parser for the test.
     */
    @BeforeAll
    static void setup() {
        PacManSprites pacManSprites = new PacManSprites();
        GhostFactory ghostFactory = new GhostFactory(pacManSprites);
        PointCalculator pointCalculator = new DefaultPointCalculator();
        LevelFactory levelFactory = new LevelFactory(pacManSprites, ghostFactory, pointCalculator);
        BoardFactory factory = new BoardFactory(pacManSprites);

        ghostMapParser = new GhostMapParser(levelFactory, factory, ghostFactory);
        player = new PlayerFactory(pacManSprites).createPacMan();
    }

    /**
     * Testing that there is no player on the game board.
     * ####
     * #C##
     * ####
     */
    @Test
    public void noPlayerOnTheBoard() {
        ArrayList<String> map = Lists.newArrayList("####", "#C##", "####");
        Level level = ghostMapParser.parseMap(map);
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertThat(clyde.nextAiMove()).isEqualTo(Optional.empty());
    }

    /**
     * Testing for when there is no path between the player and Clyde.
     * ####
     * #C##
     * ##P#
     */
    @Test
    public void noPathBetweenPlayerAndClyde() {
        ArrayList<String> map = Lists.newArrayList(
            "####", "#C##", "##P#");
        Level level = ghostMapParser.parseMap(map);
        level.registerPlayer(player);
        player.setDirection(Direction.EAST);
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertThat(clyde.nextAiMove()).isEqualTo(Optional.empty());
    }

    /**
     * Testing what Clyde should do when it is more than 8 spaces
     * away from the player. It should try to get away.
     * ###############
     * #P         C  #
     * ###############
     */
    @Test
    public void moreThanEightStepsAway() {
        ArrayList<String> map = Lists.newArrayList(
            "###############", "#P         C  #", "###############");
        Level level = ghostMapParser.parseMap(map);
        level.registerPlayer(player);
        player.setDirection(Direction.EAST);
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertThat(clyde.nextAiMove()).isEqualTo(Optional.ofNullable(Direction.WEST));
    }

    /**
     * Testing what Clyde should do when it is less than 8 spaces
     * away from the player. It should try to get away.
     * ###############
     * #P     C      #
     * ###############
     */
    @Test
    public void lessThanEightStepsAway() {
        ArrayList<String> map = Lists.newArrayList(
            "###############", "#P     C      #", "###############");
        Level level = ghostMapParser.parseMap(map);
        level.registerPlayer(player);
        player.setDirection(Direction.EAST);
        Clyde clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());
        assertThat(clyde.nextAiMove()).isEqualTo(Optional.ofNullable(Direction.EAST));
    }
}
