package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.Launcher;

/**
 * A launcher game class test.
 */
class LauncherGameTest extends GameUnitTest {

    /**
     * A method that returns a game map launcher.
     * @return an instance of Launcher.
     */
    @Override
    public Launcher getLauncher() {
        return new Launcher().withMapFile("/gameMap.txt");
    }
}
