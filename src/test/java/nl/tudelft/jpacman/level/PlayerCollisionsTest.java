package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.points.PointCalculator;

/**
 * A test class to test all the different types of collisions extending
 * CollisionMapTest class.
 */
class PlayerCollisionsTest extends CollisionMapTest {

    /**
     * The setup function with the correct CollisionMap.
     * @param pointCalculator is the PointCalculator to use.
     * @return an instance of PlayerCollisions.
     */
    @Override
    protected CollisionMap createMap(PointCalculator pointCalculator) {
        return new PlayerCollisions(pointCalculator);
    }

}
