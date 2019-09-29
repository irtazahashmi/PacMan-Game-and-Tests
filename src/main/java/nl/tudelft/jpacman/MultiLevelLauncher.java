package nl.tudelft.jpacman;


import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.game.MultiLevelGame;
import nl.tudelft.jpacman.level.MapParser;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.points.PointCalculatorLoader;
import nl.tudelft.jpacman.sprite.PacManSprites;

/**
 * Multilevel launcher, a subclass of launcher class.
 */
public class MultiLevelLauncher extends Launcher {

    private MultiLevelGame multiLevelGame;

    /**
     * Launching the launcher with a map.
     * @param arguments arguments
     */
    public static void main(String[] arguments) {
        new MultiLevelLauncher().withMapFile("/playerConsumesPellet.txt").launch();
    }

    @Override
    public Game getGame() {
        return multiLevelGame;
    }

    @Override
    public Game makeGame() {
        MapParser mapParser = getMapParser();
        MultiLevelGame game = new MultiLevelGame(
            loadPointCalculator(),
            new PlayerFactory(new PacManSprites()).createPacMan(),
            makeLevel(), mapParser
        );

        this.multiLevelGame = game;
        return game;
    }

    private PointCalculator loadPointCalculator() {
        return new PointCalculatorLoader().load();
    }
}
