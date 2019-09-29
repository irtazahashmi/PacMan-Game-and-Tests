package nl.tudelft.jpacman.integration.suspension;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.level.Pellet;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.npc.Ghost;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * A class to do systems test for PacMan Scenario 2.
 */
public class PacManSystemTestScenario2 {

    private Launcher launcher;

    private Player player;

    private static final int VARIABLE = 3;

    /**
     * Starting the launcher with a custom-map.
     * #####
     * #G  #
     * #P..#
     * #   #
     * #####
     */
    @BeforeEach
    public void setUp() {
        launcher = new Launcher().withMapFile("/gameMap.txt");
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
     * Scenario S2.1: The player consumes
     * Given the game has started,
     *  and  my Pacman is next to a square containing a pellet;
     * When  I press an arrow key towards that square;
     * Then  my Pacman can move to that square,
     *  and  I earn the points for the pellet,
     *  and  the pellet disappears from that square.
     */
    @Test
    public void playerConsumesPellet() {
        int previousScore = player.getScore();
        Square square = launcher.getGame().getLevel().getBoard().
            squareAt(2, 2);

        assertThat(square.getOccupants().get(0)).isInstanceOf(Pellet.class);

        Pellet eat = (Pellet) square.getOccupants().get(0);
        int newScore = eat.getValue();
        launcher.getGame().move(player, Direction.EAST);

        assertThat(square.getOccupants().get(0)).isEqualTo(player);
        assertThat(player.getScore()).isEqualTo(previousScore + newScore);
        assertThat(square.getOccupants().get(0)).isNotInstanceOf(Pellet.class);
    }

    /**
     * Scenario S2.2: The player moves on empty square
     * Given the game has started,
     *  and  my Pacman is next to an empty square;
     * When  I press an arrow key towards that square;
     * Then  my Pacman can move to that square
     *  and  my points remain the same.
     */
    @Test
    public void playerMovesToAnEmptySquare() {
        int previousScore = player.getScore();
        Square square = launcher.getGame().getLevel().getBoard().
            squareAt(1, VARIABLE);

        assertThat(square.getOccupants().isEmpty()).isTrue();

        launcher.getGame().move(player, Direction.SOUTH);

        assertThat(square.getOccupants().get(0)).isEqualTo(player);
        assertThat(player.getScore()).isEqualTo(previousScore);
    }

    /**
     * Scenario S2.3: The move fails
     * Given the game has started,
     *   and my Pacman is next to a cell containing a wall;
     * When  I press an arrow key towards that cell;
     * Then  the move is not conducted.
     */
    @Test
    public void playerMovesToAWall() {
        Square previousSquare = player.getSquare();
        launcher.getGame().move(player, Direction.WEST);
        assertThat(player.getSquare()).isEqualTo(previousSquare);
    }

    /**
     * Scenario S2.4: The player dies
     * Given the game has started,
     *  and  my Pacman is next to a cell containing a ghost;
     * When  I press an arrow key towards that square;
     * Then  my Pacman dies,
     *  and  the game is over.
     */
    @Test
    public void playerDies() {
        Square square = launcher.getGame().getLevel().getBoard()
            .squareAt(1, 1);

        assertThat(square.getOccupants().get(0)).isInstanceOf(Ghost.class);

        launcher.getGame().move(player, Direction.NORTH);

        assertThat(player.isAlive()).isFalse();
        assertThat(launcher.getGame().isInProgress()).isFalse();
    }

    /**
     * Scenario S2.5: Player wins, extends S2.1
     * When  I have eaten the last pellet;
     * Then  I win the game.
     */
    @Test
    public void playerWins() {
        launcher.getGame().move(player, Direction.EAST);
        launcher.getGame().move(player, Direction.EAST);

        assertThat(player.isAlive()).isTrue();
        assertThat(launcher.getGame().isInProgress()).isFalse();
    }
}
