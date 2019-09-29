package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.points.PointCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * A test class to test all the different types of collisions.
 */
abstract class CollisionMapTest {

    private CollisionMap collisionMap;

    /**
     * An interface to return the correct implementation of the CollisionMap.
     * @param pointCalculator is the PointCalculator to use.
     * @return an instance of the implemented CollisionMap under test.
     */
    protected abstract CollisionMap createMap(PointCalculator pointCalculator);

    @Mock
    private Player playerMockObject;

    @Mock
    private Ghost ghostMockedObject;

    @Mock
    private Pellet pelletMockedObject;

    @Mock
    private Unit unitMockedObject;

    @Mock
    private PointCalculator pointCalculatorMockedObject;

    /**
     * Setting up the for testing.
     */
    @BeforeEach
    void setUp() {
        playerMockObject = Mockito.mock(Player.class);
        ghostMockedObject = Mockito.mock(Ghost.class);
        pelletMockedObject = Mockito.mock(Pellet.class);
        unitMockedObject = Mockito.mock(Unit.class);
        pointCalculatorMockedObject = Mockito.mock(PointCalculator.class);
        collisionMap = createMap(pointCalculatorMockedObject);
    }

    /**
     * Testing what happens when the player collides with the
     * pellet, should result with the pellet disappearing.
     */
    @Test
    void playerCollidingWithPellet() {
        collisionMap.collide(playerMockObject, pelletMockedObject);
        Mockito.verify(pointCalculatorMockedObject).consumedAPellet(
            playerMockObject, pelletMockedObject);
        Mockito.verify(pelletMockedObject).leaveSquare();
    }

    /**
     * Testing what happens when the player collides with an
     * empty square. Nothing should happen.
     */
    @Test
    void playerCollidingWithEmptySquare() {
        collisionMap.collide(playerMockObject, unitMockedObject);
        Mockito.verify(pointCalculatorMockedObject, Mockito.never()).consumedAPellet(
            playerMockObject, pelletMockedObject);
        Mockito.verify(playerMockObject, Mockito.never()).setAlive(false);
        Mockito.verify(playerMockObject, Mockito.never()).setKiller(ghostMockedObject);
    }

    /**
     * Testing what happens when the player collides with a
     * ghost, expecting the ghost to kill the player.
     */
    @Test
    void playerCollidingWithGhost() {
        collisionMap.collide(playerMockObject, ghostMockedObject);
        Mockito.verify(pointCalculatorMockedObject).collidedWithAGhost(
            playerMockObject, ghostMockedObject);
        Mockito.verify(playerMockObject).setAlive(false);
        Mockito.verify(playerMockObject).setKiller(ghostMockedObject);
    }

    /**
     * Testing what happens when the ghost collides with a
     * player, expecting the player to die.
     */
    @Test
    void ghostCollidingWithPlayer() {
        collisionMap.collide(ghostMockedObject, playerMockObject);
        Mockito.verify(pointCalculatorMockedObject).collidedWithAGhost(
            playerMockObject, ghostMockedObject);
        Mockito.verify(playerMockObject).setAlive(false);
        Mockito.verify(playerMockObject).setKiller(ghostMockedObject);
    }

    /**
     * Testing what happens when the ghost collides with a
     * an empty square, expecting nothing to happen.
     */
    @Test
    void ghostCollidingWithEmptySquare() {
        collisionMap.collide(ghostMockedObject, unitMockedObject);
        Mockito.verify(pointCalculatorMockedObject,
            Mockito.never()).collidedWithAGhost(
            playerMockObject, ghostMockedObject);
        Mockito.verify(playerMockObject, Mockito.never()).setAlive(false);
        Mockito.verify(playerMockObject, Mockito.never()).setKiller(ghostMockedObject);
    }

    /**
     * Testing what happens when the ghost collides with a
     * a pellet, expecting nothing to happen.
     */
    @Test
    void ghostCollidingWithPellet() {
        collisionMap.collide(ghostMockedObject, pelletMockedObject);
        Mockito.verify(pointCalculatorMockedObject,
            Mockito.never()).collidedWithAGhost(
            playerMockObject, ghostMockedObject);
        Mockito.verify(playerMockObject, Mockito.never()).setAlive(false);
        Mockito.verify(playerMockObject, Mockito.never()).setKiller(ghostMockedObject);
    }

    /**
     * Testing what happens when the pellet collides with a
     * a player, expecting nothing to happen.
     */
    @Test
    void pelletCollidingWithPlayer() {
        collisionMap.collide(pelletMockedObject, playerMockObject);
        Mockito.verify(pointCalculatorMockedObject).consumedAPellet(
            playerMockObject, pelletMockedObject);
        Mockito.verify(pelletMockedObject).leaveSquare();
    }

    /**
     * Testing what happens when the pellet collides with a
     * an empty square, expecting nothing to happen.
     */
    @Test
    void pelletCollidingWithEmptySquare() {
        collisionMap.collide(pelletMockedObject, unitMockedObject);
        Mockito.verify(pointCalculatorMockedObject, Mockito.never()).consumedAPellet(
            playerMockObject, pelletMockedObject);
        Mockito.verify(pelletMockedObject, Mockito.never()).leaveSquare();
    }

    /**
     * Testing what happens when the empty square collides with a
     * an empty square, expecting nothing to happen.
     */
    @Test
    void emptySquareCollidingWithEmptySquare() {
        collisionMap.collide(unitMockedObject, unitMockedObject);
        Mockito.verify(pointCalculatorMockedObject, Mockito.never()).consumedAPellet(
            playerMockObject, pelletMockedObject);
        Mockito.verify(pelletMockedObject, Mockito.never()).leaveSquare();
        Mockito.verify(playerMockObject, Mockito.never()).setAlive(false);
    }
}
