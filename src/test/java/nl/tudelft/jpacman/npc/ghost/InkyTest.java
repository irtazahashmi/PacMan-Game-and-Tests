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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testing the Inky class.
 */
public class InkyTest {

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
     * #I##
     * ####
     */
    @Test
    public void noPlayerOnTheBoard() {
        ArrayList<String> map = Lists.newArrayList("####", "#I##", "####");
        Level level = ghostMapParser.parseMap(map);
        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(inky.nextAiMove()).isEqualTo(Optional.empty());
    }

    /**
     * Testing for when there is no path between the player and Inky.
     * ####
     * #I##
     * ##P#
     */
    @Test
    public void noPathBetweenPlayerAndInky() {
        ArrayList<String> map = Lists.newArrayList(
            "####", "#I##", "##P#");
        Level level = ghostMapParser.parseMap(map);
        level.registerPlayer(player);
        player.setDirection(Direction.EAST);
        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(inky.nextAiMove()).isEqualTo(Optional.empty());
    }

    /**
     * Testing for when there is no path between the player and Inky.
     * ####
     * ##B#
     * #I##
     * ##P#
     */
    @Test
    public void noPathBetweenPlayerAndInkyAndBlinky() {
        ArrayList<String> map = Lists.newArrayList(
            "####", "#I##", "##P#");
        Level level = ghostMapParser.parseMap(map);
        level.registerPlayer(player);
        player.setDirection(Direction.EAST);
        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(inky.nextAiMove()).isEqualTo(Optional.empty());
    }

    /**
     * Testing for when Blinky is beside Inky (they follow PacMan).
     * ############
     * #IB     P  #
     * ############
     */
    @Test
    public void blinkyBesideInky() {
        ArrayList<String> map = Lists.newArrayList(
            "############", "#IB     P  #", "############");
        Level level = ghostMapParser.parseMap(map);
        level.registerPlayer(player);
        player.setDirection(Direction.WEST);
        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(inky.nextAiMove()).isEqualTo(Optional.of(Direction.EAST));
    }

    /**
     * Testing for when Inky is in front of Pac-Man and Blinky is far behind him.
     * ###########################
     * #B                 P  I   #
     * ###########################
     */
    @Test
    public void inkyInFrontOfPacMan() {
        ArrayList<String> map = Lists.newArrayList(
            "###########################", "#B                 P  I   #",
            "###########################");
        Level level = ghostMapParser.parseMap(map);
        level.registerPlayer(player);
        player.setDirection(Direction.WEST);
        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(inky.nextAiMove()).isEqualTo(Optional.of(Direction.WEST));
    }

    /**
     * Testing for when Blinky is two squares ahead of PacMan.
     * #############
     * #I      P  B#
     * #############
     */
    @Test
    public void blinkyTwoSquaresAheadOfPacMan() {
        ArrayList<String> map = Lists.newArrayList(
            "#############", "#I      P  B#", "#############");
        Level level = ghostMapParser.parseMap(map);
        level.registerPlayer(player);
        player.setDirection(Direction.WEST);
        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(inky.nextAiMove()).isEqualTo(Optional.empty());
    }

    /**
     * Testing for when Blinky is beside Inky (they follow PacMan).
     * ############
     * #I      P  #
     * ############
     */
    @Test
    public void noBlinky() {
        ArrayList<String> map = Lists.newArrayList(
            "############", "#I      P  #", "############");
        Level level = ghostMapParser.parseMap(map);
        level.registerPlayer(player);
        player.setDirection(Direction.WEST);
        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(inky.nextAiMove()).isEqualTo(Optional.empty());
    }
}
