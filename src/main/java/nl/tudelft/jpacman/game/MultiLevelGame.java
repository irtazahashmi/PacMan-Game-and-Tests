package nl.tudelft.jpacman.game;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import nl.tudelft.jpacman.PacmanConfigurationException;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.MapParser;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.points.PointCalculator;

import java.io.IOException;
import java.util.List;

/**
 * MultiLevelGame class extends game.
 */
public class MultiLevelGame extends Game {

    private static final List<String> LEVELS = Lists.newArrayList(
        "/levelOne.txt",
        "/levelTwo.txt", "/levelThree.txt"
    );

    private static final int MAX_NUMBER_INDEX = LEVELS.size();
    private final Player player;
    private final MapParser mapParser;
    private int currentLevelNumber = 0;
    private Level currentLevel;

    /**
     * Creates a new game.
     *
     * @param pointCalculator The way to calculate points upon collisions.
     * @param player pacman
     * @param level level
     * @param mapParser parser of levels
     * @throws IOException
     */
    public MultiLevelGame(PointCalculator pointCalculator, Player player, Level level,
                          MapParser mapParser) {

        super(pointCalculator);

        assert player != null;
        assert mapParser != null;
        assert level != null;

        this.player = player;
        this.mapParser = mapParser;
        this.currentLevel = level;
        this.currentLevel.registerPlayer(player);
    }

    @Override
    public void levelWon() {
        if (currentLevelNumber < MAX_NUMBER_INDEX) {
            try {
                if (currentLevel.remainingPellets() == 0) {
                    this.currentLevel.stop();
                    this.stop();
                    this.currentLevel = mapParser.parseMap(LEVELS.get(currentLevelNumber++));
                    this.currentLevel.registerPlayer(this.player);
                }
            } catch (IOException e) {
                throw new PacmanConfigurationException(
                    "Unable to create level: " + LEVELS.get(currentLevelNumber), e);
            }
        }

        if (MAX_NUMBER_INDEX == currentLevelNumber) {
            stop();
        }
    }

    @Override
    public List<Player> getPlayers() {
        return ImmutableList.of(player);
    }

    @Override
    public Level getLevel() {
        return currentLevel;
    }
}
