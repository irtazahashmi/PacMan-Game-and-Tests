package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.PacmanConfigurationException;
import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.Ghost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testing the MapParser class.
 */
public class MapParserTest {

    private MapParser mapParser;

    private static final int BOARD_CHARACTERS = 4;

    @Mock
    private LevelFactory levelFactoryMockObject;

    @Mock
    private BoardFactory boardFactoryMockObject;

    @Mock
    private Square squareMockObject;

    @Mock
    private Ghost ghostMockObject;

    @Mock
    private Pellet pelletMockObject;

    @Mock
    private Board boardMockObject;

    @Mock
    private Level levelMockObject;

    /**
     * Setting up all the mocks that will be used
     * to test the MapParser class.
     */
    @BeforeEach
    void setUp() {
        levelFactoryMockObject = Mockito.mock(LevelFactory.class);
        boardFactoryMockObject = Mockito.mock(BoardFactory.class);
        squareMockObject = Mockito.mock(Square.class);
        ghostMockObject = Mockito.mock(Ghost.class);
        pelletMockObject = Mockito.mock(Pellet.class);
        boardMockObject = Mockito.mock(Board.class);
        levelMockObject = Mockito.mock(Level.class);

        mapParser = new MapParser(levelFactoryMockObject, boardFactoryMockObject);
    }

    /**
     * A good weather test case is to see what would happen
     * if we give 4 ground characters as input.
     * The expected result should be that the createGround() is called
     * 4 times
     */
    @Test
    void parseGround() {
        char[][] map = {
            {' ', ' '},
            {' ', ' '}
        };

        Mockito.when(boardFactoryMockObject.createGround()).thenReturn(squareMockObject);
        Mockito.when(boardFactoryMockObject.createBoard(Mockito.any())).thenReturn(boardMockObject);
        Mockito.when(levelFactoryMockObject.createLevel(Mockito.any(), Mockito.anyList(),
            Mockito.anyList())).thenReturn(levelMockObject);

        assertThat(mapParser.parseMap(map)).isEqualTo(levelMockObject);
        Mockito.verify(boardFactoryMockObject, Mockito.times(BOARD_CHARACTERS)).createGround();
    }

    /**
     * A good weather test case would be to see what would happen
     * if we give 4 wall characters as input.
     * The expected result should be that the createWall() is called four times.
     */
    @Test
    void parseWall() {
        char[][] map = {
            {'#', '#'},
            {'#', '#'}
        };

        Mockito.when(boardFactoryMockObject.createGround()).thenReturn(squareMockObject);
        Mockito.when(boardFactoryMockObject.createBoard(Mockito.any())).thenReturn(boardMockObject);
        Mockito.when(levelFactoryMockObject.createLevel(Mockito.any(), Mockito.anyList(),
            Mockito.anyList())).thenReturn(levelMockObject);

        assertThat(mapParser.parseMap(map)).isEqualTo(levelMockObject);
        Mockito.verify(boardFactoryMockObject, Mockito.times(BOARD_CHARACTERS)).createWall();
    }

    /**
     * Another good weather test case would be to see what would happen
     * if we give 4 pellet characters as input, the expected result
     * should be that the createGround() and createPellet() is called four times.
     */
    @Test
    void parsePellet() {
        char[][] map = {
            {'.', '.'},
            {'.', '.'}
        };

        Mockito.when(boardFactoryMockObject.createGround()).thenReturn(squareMockObject);
        Mockito.when(levelFactoryMockObject.createPellet()).thenReturn(pelletMockObject);
        Mockito.doNothing().when(pelletMockObject).occupy(squareMockObject);
        Mockito.when(boardFactoryMockObject.createBoard(Mockito.any())).thenReturn(boardMockObject);
        Mockito.when(levelFactoryMockObject.createLevel(Mockito.any(), Mockito.anyList(),
            Mockito.anyList())).thenReturn(levelMockObject);

        assertThat(mapParser.parseMap(map)).isEqualTo(levelMockObject);
        Mockito.verify(boardFactoryMockObject, Mockito.times(BOARD_CHARACTERS)).createGround();
        Mockito.verify(levelFactoryMockObject, Mockito.times(BOARD_CHARACTERS)).createPellet();

        Mockito.verify(pelletMockObject, Mockito.times(BOARD_CHARACTERS)).occupy(squareMockObject);
    }

    /**
     * Another good weather test case would be to see what would happen
     * if we give 4 ghost characters as input.
     * The expected result should be that the createGhost() createGround()
     * are each called four times.
     */
    @Test
    void parseGhosts() {
        char[][] map = {
            {'G', 'G'},
            {'G', 'G'}
        };

        Mockito.when(boardFactoryMockObject.createGround()).thenReturn(squareMockObject);
        Mockito.when(levelFactoryMockObject.createGhost()).thenReturn(ghostMockObject);
        Mockito.doNothing().when(ghostMockObject).occupy(squareMockObject);
        Mockito.when(boardFactoryMockObject.createBoard(Mockito.any())).thenReturn(boardMockObject);
        Mockito.when(levelFactoryMockObject.createLevel(Mockito.any(), Mockito.anyList(),
            Mockito.anyList())).thenReturn(levelMockObject);

        assertThat(mapParser.parseMap(map)).isEqualTo(levelMockObject);
        Mockito.verify(boardFactoryMockObject, Mockito.times(BOARD_CHARACTERS)).createGround();
        Mockito.verify(levelFactoryMockObject, Mockito.times(BOARD_CHARACTERS)).createGhost();

        Mockito.verify(ghostMockObject, Mockito.times(BOARD_CHARACTERS)).occupy(squareMockObject);
    }

    /**
     * Another good weather test case would be to see what would happen
     * if we give 4 player characters as input.
     * The expected result should be that the createGround() is called four times.
     */
    @Test
    void parsePlayer() {
        char[][] map = {
            {'P', 'P'},
            {'P', 'P'}
        };

        Mockito.when(boardFactoryMockObject.createGround()).thenReturn(squareMockObject);
        Mockito.when(boardFactoryMockObject.createBoard(Mockito.any())).thenReturn(boardMockObject);
        Mockito.when(levelFactoryMockObject.createLevel(Mockito.any(), Mockito.anyList(),
            Mockito.anyList())).thenReturn(levelMockObject);

        assertThat(mapParser.parseMap(map)).isEqualTo(levelMockObject);

        Mockito.verify(boardFactoryMockObject, Mockito.times(BOARD_CHARACTERS)).createGround();
    }

    /**
     *  Another good weather test case would be to see what happens
     *  if we parse the correct map.
     */
    @Test
    void parseCorrectMap() {
        List<String> list = new ArrayList<>();
        list.add("  ");
        list.add("  ");

        Mockito.when(boardFactoryMockObject.createGround()).thenReturn(squareMockObject);
        Mockito.when(boardFactoryMockObject.createBoard(Mockito.any())).thenReturn(boardMockObject);
        Mockito.when(levelFactoryMockObject.createLevel(Mockito.any(), Mockito.anyList(),
            Mockito.anyList())).thenReturn(levelMockObject);

        assertThat(mapParser.parseMap(list)).isEqualTo(levelMockObject);

        Mockito.verify(boardFactoryMockObject, Mockito.times(BOARD_CHARACTERS)).createGround();
    }

    /**
     * Another good weather test case would be to see what happens if we parse the correct line.
     */
    @Test
    void parseCorrectLine() {
        List<String> list = new ArrayList<>();
        list.add("    ");

        Mockito.when(boardFactoryMockObject.createGround()).thenReturn(squareMockObject);
        Mockito.when(boardFactoryMockObject.createBoard(Mockito.any())).thenReturn(boardMockObject);
        Mockito.when(levelFactoryMockObject.createLevel(Mockito.any(), Mockito.anyList(),
            Mockito.anyList())).thenReturn(levelMockObject);

        assertThat(mapParser.parseMap(list)).isEqualTo(levelMockObject);

        Mockito.verify(boardFactoryMockObject, Mockito.times(BOARD_CHARACTERS)).createGround();
    }

    /**
     * Another good weather test case would be to test the getter method of MapParser.
     */
    @Test
    void testGetter() {
        assertThat(mapParser.getBoardCreator()).isEqualTo(boardFactoryMockObject);
    }

    /**
     * A bad weather test case would be to test what happens when we parse invalid characters.
     */
    @Test
    void parseIncorrectChar() {
        char[][] map = {
            {'A', 'B'},
            {'A', 'B'}
        };

        try {
            mapParser.parseMap(map);
        } catch (PacmanConfigurationException e) {
            assertThat(e.getMessage()).isEqualTo("Invalid character at 0,0: A");
        }
    }

    /**
     * Another case would be to test what happens when the map we parse is null.
     */
    @Test
    void parseNullMap() {
        List<String> map = null;

        try {
            mapParser.parseMap(map);
        } catch (PacmanConfigurationException e) {
            assertThat(e.getMessage()).isEqualTo("Input text cannot be null.");
        }
    }

    /**
     * Another case would be to test what happens when the map we parse is empty.
     */
    @Test
    void parseEmptyMap() {
        List<String> map = new ArrayList<>();

        try {
            mapParser.parseMap(map);
        } catch (PacmanConfigurationException e) {
            assertThat(e.getMessage()).isEqualTo("Input text must consist of at least 1 row.");
        }
    }

    /**
     * Another case would be to test that the first line of the map is empty that we parse.
     */
    @Test
    void parseEmptyLine() {
        List<String> map = new ArrayList<>();
        map.add("");

        try {
            mapParser.parseMap(map);
        } catch (PacmanConfigurationException e) {
            assertThat(e.getMessage()).isEqualTo("Input text lines cannot be empty.");
        }
    }

    /**
     * Another case would be to test what happens when the parsed lines are different length.
     */
    @Test
    void parseDifferentLineLengths() {
        List<String> map = new ArrayList<>();
        map.add("  ");
        map.add("   ");

        try {
            mapParser.parseMap(map);
        } catch (PacmanConfigurationException e) {
            assertThat(e.getMessage()).isEqualTo("Input text lines are not of equal width.");
        }
    }

    /**
     * Testing the InputStream parser from a correct string of walls.
     */
    @Test
    void parseCorrectInputStream() {
        String map = "####";
        InputStream input = new ByteArrayInputStream(map.getBytes(Charset.defaultCharset()));

        Mockito.when(boardFactoryMockObject.createWall()).thenReturn(squareMockObject);
        Mockito.when(boardFactoryMockObject.createBoard(Mockito.any())).thenReturn(boardMockObject);
        Mockito.when(levelFactoryMockObject.createLevel(Mockito.any(),
            Mockito.anyList(), Mockito.anyList())).thenReturn(levelMockObject);

        try {
            assertThat(mapParser.parseMap(input)).isEqualTo(levelMockObject);
        } catch (IOException e) {
            assertThat(e.getMessage()).isNull();
        }
    }

    /**
     * Another case would be to test what happens when we have the incorrect map name.
     */
    @Test
    void parseIncorrectMapName() {

        try {
            mapParser.parseMap("Invalid Map");
        } catch (PacmanConfigurationException e) {
            assertThat(e.getMessage()).isEqualTo("Could not get resource for: Invalid Map");
        } catch (IOException e) {
            assertThat(e.getMessage()).isNull();
        }
    }

    /**
     * Testing that the valid map doesn't throw an exception.
     */
    @Test
    void parseCorrectMapName() {
        Level result = null;

        Mockito.when(boardFactoryMockObject.createWall()).thenReturn(squareMockObject);
        Mockito.when(boardFactoryMockObject.createBoard(Mockito.any())).thenReturn(boardMockObject);
        Mockito.when(levelFactoryMockObject.createLevel(Mockito.any(),
            Mockito.anyList(), Mockito.anyList())).thenReturn(levelMockObject);

        try {
            result = mapParser.parseMap("/simplemap.txt");
        } catch (IOException | PacmanConfigurationException e) {
            assertThat(e.getMessage()).isNull();
        }

        assertThat(result).isEqualTo(levelMockObject);
    }
}
