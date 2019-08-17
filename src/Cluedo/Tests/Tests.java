package Cluedo.Tests;

import Cluedo.Board;
import Cluedo.Helpers.Die;
import Cluedo.Helpers.Position;
import Cluedo.Helpers.Type;
import Cluedo.Moves.Accuse;
import Cluedo.Moves.Move;
import Cluedo.Moves.Suggest;
import Cluedo.Tiles.Tile;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class Tests {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    private void setInput(String data){
        InputStream testInput = new ByteArrayInputStream( data.getBytes() );
        System.setIn(testInput);
    }

    @Test
    public void test01(){
        //Cluedo.Tests.Tests simple movement of Miss Scarlet
        Board b = new Board(3);
        String solution =   "|  | 1| 2| 3| 4| 5| 6| 7| 8| 9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|\n" +
                            "| A|--|--|--|--|--|--|--|--|--|WH|--|--|--|--|__|--|--|--|--|--|--|--|--|--|\n" +
                            "| B|  |  |  |  |  |  |--|__|__|__|  |  |  |  |__|__|__|--|  |  |  |  |  |  |\n" +
                            "| C|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |\n" +
                            "| D|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |\n" +
                            "| E|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |  |__|__|CT|  |  |  |  |  |\n" +
                            "| F|  |  |  |  |  |  |__|__|BR|  |  |  |  |  |  |BR|__|__|__|  |  |  |  |--|\n" +
                            "| G|--|  |  |  |KT|  |__|__|  |  |  |  |  |  |  |  |__|__|__|__|__|__|__|__|\n" +
                            "| H|__|__|__|__|__|__|__|__|  |BR|  |  |  |  |BR|  |__|__|__|__|__|__|__|--|\n" +
                            "| I|--|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|  |  |  |  |  |  |\n" +
                            "| J|  |  |  |  |  |__|__|__|__|__|__|__|__|__|__|__|__|__|BL|  |  |  |  |  |\n" +
                            "| K|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|  |  |  |  |  |  |\n" +
                            "| L|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|  |  |  |  |  |  |\n" +
                            "| M|  |  |  |  |  |  |  |DR|__|__|--|--|--|--|--|__|__|__|  |  |  |  |  |  |\n" +
                            "| N|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|__|__|__|__|__|--|\n" +
                            "| O|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|  |  |LB|  |  |--|\n" +
                            "| P|  |  |  |  |  |  |DR|  |__|__|--|--|--|--|--|__|__|  |  |  |  |  |  |  |\n" +
                            "| Q|--|__|__|__|__|__|__|__|__|__|--|--|--|--|--|__|__|LB|  |  |  |  |  |  |\n" +
                            "| R|MU|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|  |  |  |  |  |  |  |\n" +
                            "| S|--|__|__|__|__|__|__|__|__|  |  |HL|HL|  |  |__|__|__|  |  |  |  |  |--|\n" +
                            "| T|  |  |  |  |  |  |LG|__|__|  |  |  |  |  |  |__|__|__|__|__|__|__|__|__|\n" +
                            "| U|  |  |  |  |  |  |  |__|__|  |  |  |  |  |HL|__|__|__|__|__|__|__|__|--|\n" +
                            "| V|  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |__|__|ST|  |  |  |  |  |  |\n" +
                            "| W|  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |\n" +
                            "| X|  |  |  |  |  |  |  |__|SC|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |\n" +
                            "| Y|  |  |  |  |  |  |--|__|--|  |  |  |  |  |  |--|__|--|  |  |  |  |  |  |\n";

        b.processTurn(new Move(Position.positionFromString("9,x"), 100));

        Assert.assertEquals(solution, b.toString());
    }

    @Test
    public void test02(){
        //Cluedo.Tests.Tests simple movement of Miss Scarlet
        Board b = new Board(3);
        String solution =   "|  | 1| 2| 3| 4| 5| 6| 7| 8| 9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|\n" +
                            "| A|--|--|--|--|--|--|--|--|--|WH|--|--|--|--|__|--|--|--|--|--|--|--|--|--|\n" +
                            "| B|  |  |  |  |  |  |--|__|__|__|  |  |  |  |__|__|__|--|  |  |  |  |  |  |\n" +
                            "| C|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |\n" +
                            "| D|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |\n" +
                            "| E|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |  |__|__|CT|  |  |  |  |  |\n" +
                            "| F|  |  |  |  |  |  |__|__|BR|  |  |  |  |  |  |BR|__|__|__|  |  |  |  |--|\n" +
                            "| G|--|  |  |  |KT|  |__|__|  |  |  |  |  |  |  |  |__|__|__|__|__|__|__|__|\n" +
                            "| H|__|__|__|__|__|__|__|__|  |BR|  |  |  |  |BR|  |__|__|__|__|__|__|__|--|\n" +
                            "| I|--|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|  |  |  |  |  |  |\n" +
                            "| J|  |  |  |  |  |__|__|__|__|__|__|__|__|__|__|__|__|__|BL|  |  |  |  |  |\n" +
                            "| K|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|  |  |  |  |  |  |\n" +
                            "| L|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|  |  |  |  |  |  |\n" +
                            "| M|  |  |  |  |  |  |  |DR|__|__|--|--|--|--|--|__|__|__|  |  |  |  |  |  |\n" +
                            "| N|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|__|__|__|__|__|--|\n" +
                            "| O|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|  |  |LB|  |  |--|\n" +
                            "| P|  |  |  |  |  |  |DR|  |__|__|--|--|--|--|--|__|__|  |  |  |  |  |  |  |\n" +
                            "| Q|--|__|__|__|__|__|__|__|__|__|--|--|--|--|--|__|__|LB|  |  |  |  |  |  |\n" +
                            "| R|MU|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|  |  |  |  |  |  |  |\n" +
                            "| S|--|__|__|__|__|__|__|SC|__|  |  |HL|HL|  |  |__|__|__|  |  |  |  |  |--|\n" +
                            "| T|  |  |  |  |  |  |LG|__|__|  |  |  |  |  |  |__|__|__|__|__|__|__|__|__|\n" +
                            "| U|  |  |  |  |  |  |  |__|__|  |  |  |  |  |HL|__|__|__|__|__|__|__|__|--|\n" +
                            "| V|  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |__|__|ST|  |  |  |  |  |  |\n" +
                            "| W|  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |\n" +
                            "| X|  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |\n" +
                            "| Y|  |  |  |  |  |  |--|__|--|  |  |  |  |  |  |--|__|--|  |  |  |  |  |  |\n";

        b.processTurn(new Move(Position.positionFromString("8,s"), 100));

        Assert.assertEquals(solution, b.toString());
    }

    @Test
    public void test03(){
        //Cluedo.Tests.Tests room entrance
        Board b = new Board(3);
        String solution =   "|  | 1| 2| 3| 4| 5| 6| 7| 8| 9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|\n" +
                            "| A|--|--|--|--|--|--|--|--|--|WH|--|--|--|--|__|--|--|--|--|--|--|--|--|--|\n" +
                            "| B|  |  |  |  |  |  |--|__|__|__|  |  |  |  |__|__|__|--|  |  |  |  |  |  |\n" +
                            "| C|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |\n" +
                            "| D|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |\n" +
                            "| E|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |  |__|__|CT|  |  |  |  |  |\n" +
                            "| F|  |  |  |  |  |  |__|__|BR|  |  |  |  |  |  |BR|__|__|__|  |  |  |  |--|\n" +
                            "| G|--|  |  |  |KT|  |__|__|  |  |  |  |  |  |  |  |__|__|__|__|__|__|__|__|\n" +
                            "| H|__|__|__|__|__|__|__|__|  |BR|  |  |  |  |BR|  |__|__|__|__|__|__|__|--|\n" +
                            "| I|--|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|  |  |  |  |  |  |\n" +
                            "| J|  |  |  |  |  |__|__|__|__|__|__|__|__|__|__|__|__|__|BL|  |  |  |  |  |\n" +
                            "| K|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|  |  |  |  |  |  |\n" +
                            "| L|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|  |  |  |  |  |  |\n" +
                            "| M|  |  |  |  |  |  |  |DR|__|__|--|--|--|--|--|__|__|__|  |  |  |  |  |  |\n" +
                            "| N|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|__|__|__|__|__|--|\n" +
                            "| O|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|  |  |LB|  |  |--|\n" +
                            "| P|  |  |  |  |  |  |DR|  |__|__|--|--|--|--|--|__|__|  |  |  |  |  |  |  |\n" +
                            "| Q|--|__|__|__|__|__|__|__|__|__|--|--|--|--|--|__|__|LB|  |  |  |  |  |  |\n" +
                            "| R|MU|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|  |  |  |  |  |  |  |\n" +
                            "| S|--|__|__|__|__|__|__|__|__|  |  |HL|HL|  |  |__|__|__|  |  |  |  |  |--|\n" +
                            "| T|  |  |  |  |  |  |LG|__|__|  |  |  |  |  |  |__|__|__|__|__|__|__|__|__|\n" +
                            "| U|  |  |  |  |  |  |  |__|__|  |  |  |  |  |HL|__|__|__|__|__|__|__|__|--|\n" +
                            "| V|  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |__|__|ST|  |  |  |  |  |  |\n" +
                            "| W|  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |\n" +
                            "| X|  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |\n" +
                            "| Y|  |  |  |  |  |  |--|__|--|  |  |  |  |  |  |--|__|--|  |  |  |  |  |  |\n";


        setInput("n");
        b.processTurn(new Move(Position.positionFromString("7,t"), 100));

        Assert.assertEquals(solution, b.justBoard());
    }

    @Test
    public void test04(){
        //Cluedo.Tests.Tests room entrance and simple movement
        Board b = new Board(3);
        String solution =   "|  | 1| 2| 3| 4| 5| 6| 7| 8| 9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|\n" +
                            "| A|--|--|--|--|--|--|--|--|--|WH|--|--|--|--|__|--|--|--|--|--|--|--|--|--|\n" +
                            "| B|  |  |  |  |  |  |--|__|__|__|  |  |  |  |__|__|__|--|  |  |  |  |  |  |\n" +
                            "| C|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |\n" +
                            "| D|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |\n" +
                            "| E|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |  |__|__|CT|  |  |  |  |  |\n" +
                            "| F|  |  |  |  |  |  |__|__|BR|  |  |  |  |  |  |BR|__|__|__|  |  |  |  |--|\n" +
                            "| G|--|  |  |  |KT|  |__|__|  |  |  |  |  |  |  |  |__|__|__|__|__|__|__|__|\n" +
                            "| H|__|__|__|__|__|__|__|__|  |BR|  |  |  |  |BR|  |__|__|__|__|__|__|__|--|\n" +
                            "| I|--|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|  |  |  |  |  |  |\n" +
                            "| J|  |  |  |  |  |__|__|__|__|__|__|__|__|__|__|__|__|__|BL|  |  |  |  |  |\n" +
                            "| K|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|  |  |  |  |  |  |\n" +
                            "| L|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|  |  |  |  |  |  |\n" +
                            "| M|  |  |  |  |  |  |  |DR|__|__|--|--|--|--|--|__|__|__|  |  |  |  |  |  |\n" +
                            "| N|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|__|__|__|__|__|--|\n" +
                            "| O|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|  |  |LB|  |  |--|\n" +
                            "| P|  |  |  |  |  |  |DR|  |__|__|--|--|--|--|--|__|__|  |  |  |  |  |  |  |\n" +
                            "| Q|--|__|__|__|__|__|__|__|__|__|--|--|--|--|--|__|__|LB|  |  |  |  |  |  |\n" +
                            "| R|__|__|__|__|MU|__|__|__|__|__|__|__|__|__|__|__|__|  |  |  |  |  |  |  |\n" +
                            "| S|--|__|__|__|__|__|__|__|__|  |  |HL|HL|  |  |__|__|__|  |  |  |  |  |--|\n" +
                            "| T|  |  |  |  |  |  |LG|__|__|  |  |  |  |  |  |__|__|__|__|__|__|__|__|__|\n" +
                            "| U|  |  |  |  |  |  |  |__|__|  |  |  |  |  |HL|__|__|__|__|__|__|__|__|--|\n" +
                            "| V|  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |__|__|ST|  |  |  |  |  |  |\n" +
                            "| W|  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |\n" +
                            "| X|  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |\n" +
                            "| Y|  |  |  |  |  |  |--|__|--|  |  |  |  |  |  |--|__|--|  |  |  |  |  |  |\n";


        setInput("n");
        b.processTurn(new Move(Position.positionFromString("7,t"), 100));
        b.completeTurn();

        b.processTurn(new Move(Position.positionFromString("5,r"), 100));

        Assert.assertEquals(solution, b.justBoard());
    }

    @Test
    public void test05(){
        Board b = new Board(3);
        String solution =   "|  | 1| 2| 3| 4| 5| 6| 7| 8| 9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|\n" +
                            "| A|--|--|--|--|--|--|--|--|--|WH|--|--|--|--|__|--|--|--|--|--|--|--|--|--|\n" +
                            "| B|  |  |  |  |  |  |--|__|__|__|  |  |  |  |__|__|__|--|  |  |  |  |  |  |\n" +
                            "| C|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |\n" +
                            "| D|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |\n" +
                            "| E|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |  |__|__|CT|  |  |  |  |  |\n" +
                            "| F|  |  |  |  |  |  |__|__|BR|  |  |  |  |  |  |BR|__|__|__|  |  |  |  |--|\n" +
                            "| G|--|  |  |  |KT|  |__|__|  |  |  |  |  |  |  |  |__|__|__|__|__|__|__|__|\n" +
                            "| H|__|__|__|__|__|__|__|__|  |BR|  |  |  |  |BR|  |__|__|__|__|__|__|__|--|\n" +
                            "| I|--|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|  |  |  |  |  |  |\n" +
                            "| J|  |  |  |  |  |__|__|__|__|__|__|__|__|__|__|__|__|__|BL|  |  |  |  |  |\n" +
                            "| K|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|  |  |  |  |  |  |\n" +
                            "| L|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|  |  |  |  |  |  |\n" +
                            "| M|  |  |  |  |  |  |  |DR|__|__|--|--|--|--|--|__|__|__|  |  |  |  |  |  |\n" +
                            "| N|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|__|__|__|__|__|--|\n" +
                            "| O|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|  |  |LB|  |  |--|\n" +
                            "| P|  |  |  |  |  |  |DR|  |__|__|--|--|--|--|--|__|__|  |  |  |  |  |  |  |\n" +
                            "| Q|--|__|__|__|__|__|__|__|__|__|--|--|--|--|--|__|__|LB|  |  |  |  |  |  |\n" +
                            "| R|MU|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|  |  |  |  |  |  |  |\n" +
                            "| S|--|__|__|__|__|__|__|__|__|  |  |HL|HL|  |  |__|__|__|  |  |  |  |  |--|\n" +
                            "| T|  |  |  |  |  |  |LG|__|__|  |  |  |  |  |  |__|__|__|__|__|__|__|__|__|\n" +
                            "| U|  |  |  |  |  |  |  |__|__|  |  |  |  |  |HL|__|__|__|__|__|__|__|__|--|\n" +
                            "| V|  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |__|__|ST|  |  |  |  |  |  |\n" +
                            "| W|  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |\n" +
                            "| X|  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |\n" +
                            "| Y|  |  |  |  |  |  |--|SC|--|  |  |  |  |  |  |--|__|--|  |  |  |  |  |  |\n";

        b.processTurn(new Move(Position.positionFromString("7,t"), 1));
        b.completeTurn();


        Assert.assertEquals(solution, b.toString());
    }

    @Test
    public void test06(){
        Board b = new Board(3);
        String solution =   "|  | 1| 2| 3| 4| 5| 6| 7| 8| 9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|\n" +
                            "| A|--|--|--|--|--|--|--|--|--|__|--|--|--|--|__|--|--|--|--|--|--|--|--|--|\n" +
                            "| B|  |  |  |  |  |  |--|__|__|__|  |  |  |  |__|__|__|--|  |  |  |  |  |  |\n" +
                            "| C|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |\n" +
                            "| D|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |\n" +
                            "| E|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |  |__|__|CT|  |  |  |  |  |\n" +
                            "| F|  |  |  |  |  |  |__|__|BR|  |  |  |  |  |  |BR|__|__|__|  |  |  |  |--|\n" +
                            "| G|--|  |  |  |KT|  |__|__|  |  |  |  |  |  |  |  |__|__|__|__|__|__|__|__|\n" +
                            "| H|__|__|__|__|__|__|WH|__|  |BR|  |  |  |  |BR|  |__|__|__|__|__|__|__|--|\n" +
                            "| I|--|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|  |  |  |  |  |  |\n" +
                            "| J|  |  |  |  |  |__|__|__|__|__|__|__|__|__|__|__|__|__|BL|  |  |  |  |  |\n" +
                            "| K|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|  |  |  |  |  |  |\n" +
                            "| L|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|  |  |  |  |  |  |\n" +
                            "| M|  |  |  |  |  |  |  |DR|__|__|--|--|--|--|--|__|__|__|  |  |  |  |  |  |\n" +
                            "| N|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|__|__|__|__|__|--|\n" +
                            "| O|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|  |  |LB|  |  |--|\n" +
                            "| P|  |  |  |  |  |  |DR|  |__|__|--|--|--|--|--|__|__|  |  |  |  |  |  |  |\n" +
                            "| Q|--|__|__|__|__|__|__|__|__|__|--|--|--|--|--|__|__|LB|  |  |  |  |  |  |\n" +
                            "| R|__|__|__|__|__|__|__|__|SC|__|__|__|__|__|__|__|__|  |  |  |  |  |  |  |\n" +
                            "| S|--|__|__|__|__|__|__|__|__|  |  |HL|HL|  |  |__|__|__|  |  |  |  |  |--|\n" +
                            "| T|  |  |  |  |  |  |LG|__|__|  |  |  |  |  |  |__|__|__|__|__|__|__|__|__|\n" +
                            "| U|  |  |  |  |  |  |  |__|__|  |  |  |  |  |HL|__|__|__|__|__|__|__|__|--|\n" +
                            "| V|  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |__|__|ST|  |  |  |  |  |  |\n" +
                            "| W|  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |\n" +
                            "| X|  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |\n" +
                            "| Y|  |  |  |  |  |  |--|__|--|  |  |  |  |  |  |--|__|--|  |  |  |  |  |  |\n";

        b.processTurn(new Move(Position.positionFromString("9,r"), 100));
        b.completeTurn();

        setInput("n");
        b.processTurn(new Move(Position.positionFromString("7,t"), 100));
        b.completeTurn();

        b.processTurn(new Move(Position.positionFromString("7,h"), 100));

        Assert.assertEquals(solution, b.justBoard());
    }

    @Test
    public void test07(){
        Board b = new Board(3);
        String solution =   "|  | 1| 2| 3| 4| 5| 6| 7| 8| 9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|\n" +
                            "| A|--|--|--|--|--|--|--|--|--|WH|--|--|--|--|__|--|--|--|--|--|--|--|--|--|\n" +
                            "| B|  |  |  |  |  |  |--|__|__|__|  |  |  |  |__|__|__|--|  |  |  |  |  |  |\n" +
                            "| C|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |\n" +
                            "| D|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |\n" +
                            "| E|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |  |__|__|CT|  |  |  |  |  |\n" +
                            "| F|  |  |  |  |  |  |__|__|BR|  |  |  |  |  |  |BR|__|__|__|  |  |  |  |--|\n" +
                            "| G|--|  |  |  |KT|  |__|__|  |  |  |  |  |  |  |  |__|__|__|__|__|__|__|__|\n" +
                            "| H|__|__|__|__|__|__|__|__|  |BR|  |  |  |  |BR|  |__|__|__|__|__|__|__|--|\n" +
                            "| I|--|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|  |  |  |  |  |  |\n" +
                            "| J|  |  |  |  |  |__|__|__|__|__|__|__|__|__|__|__|__|__|BL|  |  |  |  |  |\n" +
                            "| K|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|  |  |  |  |  |  |\n" +
                            "| L|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|  |  |  |  |  |  |\n" +
                            "| M|  |  |  |  |  |  |  |DR|__|__|--|--|--|--|--|__|__|__|  |  |  |  |  |  |\n" +
                            "| N|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|__|__|__|__|__|--|\n" +
                            "| O|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|  |  |LB|  |  |--|\n" +
                            "| P|  |  |  |  |  |  |DR|  |__|__|--|--|--|--|--|__|__|  |  |  |  |  |  |  |\n" +
                            "| Q|--|__|__|__|__|__|__|__|__|__|--|--|--|--|--|__|__|LB|  |  |  |  |  |  |\n" +
                            "| R|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|  |  |  |  |  |  |  |\n" +
                            "| S|--|__|__|__|__|__|__|__|__|  |  |HL|HL|  |  |__|__|__|  |  |  |  |  |--|\n" +
                            "| T|  |  |  |  |  |  |LG|__|__|  |  |  |  |  |  |__|__|__|__|__|__|__|__|__|\n" +
                            "| U|  |  |  |  |  |  |  |__|__|  |  |  |  |  |HL|__|__|__|__|__|__|__|__|--|\n" +
                            "| V|  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |__|__|ST|  |  |  |  |  |  |\n" +
                            "| W|  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |\n" +
                            "| X|  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |\n" +
                            "| Y|  |  |  |  |  |  |--|__|--|  |  |  |  |  |  |--|__|--|  |  |  |  |  |  |\n";

        setInput("n");
        b.processTurn(new Move(Position.positionFromString("7,t"), 100));
        b.completeTurn();

        setInput("n");
        b.processTurn(new Move(Position.positionFromString("7,t"), 100));
        b.completeTurn();

        Assert.assertEquals(solution, b.justBoard());
    }

    @Test
    public void test08(){

        Board b = new Board(3);

        b.setSolution(new Type[]{Type.REVOLVER, Type.MISS_SCARLETT, Type.LOUNGE});
        b.clearHands();
        setInput("n");
        b.processTurn(new Move(Position.positionFromString("7,t"), 100));
        setInput("n");
        b.processTurn(new Suggest(Type.REVOLVER, Type.MISS_SCARLETT));
        b.processTurn(new Accuse(Type.REVOLVER, Type.MISS_SCARLETT));

        assertTrue(b.isHasWon());
    }

    @Test
    public void test09(){
        Board b = new Board(3);

        b.setSolution(new Type[]{Type.REVOLVER, Type.MISS_SCARLETT, Type.LOUNGE});
        b.clearHands();
        b.givePlayerType(Type.COL_MUSTARD, Type.LOUNGE);
        setInput("n");
        b.processTurn(new Move(Position.positionFromString("7,t"), 100));
        setInput("n");
        b.processTurn(new Suggest(Type.REVOLVER, Type.MISS_SCARLETT));

        assertFalse(b.isHasWon());
    }

    @Test
    public void test10(){
        Board b = new Board(3);

        setInput("n");
        b.processTurn(new Move(Position.positionFromString("7,t"), 100));
        b.completeTurn();

        setInput("n");

        b.processTurn(new Move(Position.positionFromString("7,t"), 100));
        b.completeTurn();

        Assert.assertEquals(b.getRooms().get(Type.LOUNGE).getEntities().size(), 2);
    }

    @Test
    public void test11(){
        Board b = new Board(3);

        b.setSolution(new Type[]{Type.LEAD_PIPE, Type.MISS_SCARLETT, Type.LOUNGE});
        b.clearHands();
        setInput("n");
        b.processTurn(new Move(Position.positionFromString("7,t"), 100));
        setInput("n");
        b.processTurn(new Suggest(Type.REVOLVER, Type.MISS_SCARLETT));
        b.processTurn(new Accuse(Type.REVOLVER, Type.MISS_SCARLETT));

        assertFalse(b.isHasWon());

    }

    @Test
    public void test12(){
        Board b = new Board(3);

        b.processTurn(new Suggest(Type.LEAD_PIPE, Type.MISS_SCARLETT));

        assertEquals("You cannot make a suggestion without being in a room!\n", outContent.toString());
    }

    @Test
    public void test13(){
        Board b = new Board(3);

        b.processTurn(new Move(Position.positionFromString("6,y"), 100));

        assertEquals("Invalid path.\n", outContent.toString());
    }

    @Test
    public void test14(){
        Board b = new Board(3);

        b.processTurn(new Move(Position.positionFromString("1,r"), 2));

        assertEquals("You can't move that far!\n", outContent.toString());
    }

    //Check accused players are actually moved to the tile(room) they're accused in
    @Test
    public void test15(){
        Board b = new Board(3);

        b.setSolution(new Type[]{Type.ROPE, Type.COL_MUSTARD, Type.LOUNGE});
        b.clearHands();
        setInput("n");
        b.processTurn(new Move(Position.positionFromString("7,t"), 100));
        setInput("n");
        b.processTurn(new Suggest(Type.ROPE, Type.MISS_SCARLETT));

        //current player is where the accused needs to go
        Position room = b.getCurrentPlayer().getPosition();

        System.out.println(b.toString());
        //check that the player is in the position of the room that they're supposedly in
        Assert.assertEquals(room, b.getPlayer(Type.MISS_SCARLETT).getPosition());

    }

    //making accusations without being in a room
    @Test
    public void test16AccusationNotInRoom(){
        Board b = new Board(3);

        b.setSolution(new Type[]{Type.ROPE, Type.COL_MUSTARD, Type.BILLARD_ROOM});
        b.clearHands();
        setInput("n");
        b.processTurn(new Move(Position.positionFromString("17,j"), 100));
        setInput("n");
        b.processTurn(new Suggest(Type.ROPE, Type.COL_MUSTARD));
        b.processTurn(new Accuse(Type.ROPE, Type.COL_MUSTARD));

        //wasn't in a room, therefore can't suggest/accuse -> win
        assertFalse(b.isHasWon());
    }

    //winning
    @Test
    public void test17TestValidWin(){
        Board b = new Board(3);

        b.setSolution(new Type[]{Type.ROPE, Type.COL_MUSTARD, Type.BILLARD_ROOM});
        b.clearHands();
        setInput("n");
        b.processTurn(new Move(Position.positionFromString("19,j"), 100));
        setInput("n");
        b.processTurn(new Suggest(Type.ROPE, Type.COL_MUSTARD));
        b.processTurn(new Accuse(Type.ROPE, Type.COL_MUSTARD));

        assertTrue(b.isHasWon());
    }

    @Test
    public void test18IncorrectAccusation(){
        Board b = new Board(3);

        b.setSolution(new Type[]{Type.ROPE, Type.COL_MUSTARD, Type.BILLARD_ROOM});
        b.clearHands();
        setInput("n");
        b.processTurn(new Move(Position.positionFromString("19,j"), 100));
        setInput("n");
        b.processTurn(new Suggest(Type.ROPE, Type.MISS_SCARLETT));
//        b.processTurn(new Cluedo.Moves.Accuse(Cluedo.Helpers.Type.ROPE, Cluedo.Helpers.Type.COL_MUSTARD));

        assertFalse(b.isHasWon());
    }

    //may not move on top another player
    @Test
    public void test19(){
        Board b = new Board(3);
        String solution =   "|  | 1| 2| 3| 4| 5| 6| 7| 8| 9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|\n" +
                            "| A|--|--|--|--|--|--|--|--|--|WH|--|--|--|--|__|--|--|--|--|--|--|--|--|--|\n" +
                            "| B|  |  |  |  |  |  |--|__|__|__|  |  |  |  |__|__|__|--|  |  |  |  |  |  |\n" +
                            "| C|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |\n" +
                            "| D|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |\n" +
                            "| E|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |  |__|__|CT|  |  |  |  |  |\n" +
                            "| F|  |  |  |  |  |  |__|__|BR|  |  |  |  |  |  |BR|__|__|__|  |  |  |  |--|\n" +
                            "| G|--|  |  |  |KT|  |__|__|  |  |  |  |  |  |  |  |__|__|__|__|__|__|__|__|\n" +
                            "| H|__|__|__|__|__|__|__|__|  |BR|  |  |  |  |BR|  |__|__|__|__|__|__|__|--|\n" +
                            "| I|--|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|  |  |  |  |  |  |\n" +
                            "| J|  |  |  |  |  |__|__|__|__|__|__|__|__|__|__|__|__|__|BL|  |  |  |  |  |\n" +
                            "| K|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|  |  |  |  |  |  |\n" +
                            "| L|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|  |  |  |  |  |  |\n" +
                            "| M|  |  |  |  |  |  |  |DR|__|__|--|--|--|--|--|__|__|__|  |  |  |  |  |  |\n" +
                            "| N|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|__|__|__|__|__|--|\n" +
                            "| O|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|  |  |LB|  |  |--|\n" +
                            "| P|  |  |  |  |  |  |DR|  |__|__|--|--|--|--|--|__|__|  |  |  |  |  |  |  |\n" +
                            "| Q|--|__|__|__|__|__|__|__|__|__|--|--|--|--|--|__|__|LB|  |  |  |  |  |  |\n" +
                            "| R|MU|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|  |  |  |  |  |  |  |\n" +
                            "| S|--|__|__|__|__|__|__|__|__|  |  |HL|HL|  |  |__|__|__|  |  |  |  |  |--|\n" +
                            "| T|  |  |  |  |  |  |LG|__|__|  |  |  |  |  |  |__|__|__|__|__|__|__|__|__|\n" +
                            "| U|  |  |  |  |  |  |  |__|__|  |  |  |  |  |HL|__|__|__|__|__|__|__|__|--|\n" +
                            "| V|  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |__|__|ST|  |  |  |  |  |  |\n" +
                            "| W|  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |\n" +
                            "| X|  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |\n" +
                            "| Y|  |  |  |  |  |  |--|SC|--|  |  |  |  |  |  |--|__|--|  |  |  |  |  |  |\n";
        setInput("n");
        b.processTurn(new Move(Position.positionFromString("1,r"), 1000));


        Assert.assertEquals(solution, b.justBoard());
    }


    @Test
    public void test20CheckRowLengthEquality(){
        Board b = new Board(3);

        //check all rows are of the same length
        int rowLength = b.getBoard()[0].length;

        for (int y = 0; y < b.getBoard().length; y++) {
            Assert.assertEquals(rowLength, b.getBoard()[y].length);
        }
    }

    @Test
    public void test21CheckBoardValidity(){
        Board b = new Board(3);
        Tile[][] board = b.getBoard();


        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[0].length; x++) {
                Tile tile = board[y][x];

                Position pos = tile.getPosition();

                Assert.assertEquals(x, pos.getX());
                Assert.assertEquals(y, pos.getY());
            }
        }

    }

    @Test
    public void test22DieRollBoundaries(){
        int numberofRolls = 100000;
        for (int i = 0; i < numberofRolls; i++) {
            assertEquals(true, Die.roll() <= 12);
            assertEquals(true, Die.roll() >= 2);
        }
    }

    @Test
    public void test23BoundaryNumberOfPlayers(){
        Board b = new Board(6);
        Assert.assertEquals(6, b.getPlayers().size());

        b = new Board(3);
        Assert.assertEquals(3,b.getPlayers().size());
    }

    @Test
    public void test24ValidMovement(){
        //Cluedo.Tests.Tests room entrance and simple movement
        Board b = new Board(3);
        String solution =   "|  | 1| 2| 3| 4| 5| 6| 7| 8| 9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|\n" +
                            "| A|--|--|--|--|--|--|--|--|--|WH|--|--|--|--|__|--|--|--|--|--|--|--|--|--|\n" +
                            "| B|  |  |  |  |  |  |--|__|__|__|  |  |  |  |__|__|__|--|  |  |  |  |  |  |\n" +
                            "| C|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |\n" +
                            "| D|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |\n" +
                            "| E|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |  |__|__|CT|  |  |  |  |  |\n" +
                            "| F|  |  |  |  |  |  |__|__|BR|  |  |  |  |  |  |BR|__|__|__|  |  |  |  |--|\n" +
                            "| G|--|  |  |  |KT|  |__|__|  |  |  |  |  |  |  |  |__|__|__|__|__|__|__|__|\n" +
                            "| H|__|__|__|__|__|__|__|__|  |BR|  |  |  |  |BR|  |__|SC|__|__|__|__|__|--|\n" +
                            "| I|--|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|  |  |  |  |  |  |\n" +
                            "| J|  |  |  |  |  |__|__|__|__|__|__|__|__|__|__|__|__|__|BL|  |  |  |  |  |\n" +
                            "| K|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|  |  |  |  |  |  |\n" +
                            "| L|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|  |  |  |  |  |  |\n" +
                            "| M|  |  |  |  |  |  |  |DR|__|__|--|--|--|--|--|__|__|__|  |  |  |  |  |  |\n" +
                            "| N|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|__|__|__|__|__|--|\n" +
                            "| O|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|  |  |LB|  |  |--|\n" +
                            "| P|  |  |  |  |  |  |DR|  |__|__|--|--|--|--|--|__|__|  |  |  |  |  |  |  |\n" +
                            "| Q|--|__|__|__|__|__|__|__|__|__|--|--|--|--|--|__|__|LB|  |  |  |  |  |  |\n" +
                            "| R|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|  |  |  |  |  |  |  |\n" +
                            "| S|--|__|__|MU|__|__|__|__|__|  |  |HL|HL|  |  |__|__|__|  |  |  |  |  |--|\n" +
                            "| T|  |  |  |  |  |  |LG|__|__|  |  |  |  |  |  |__|__|__|__|__|__|__|__|__|\n" +
                            "| U|  |  |  |  |  |  |  |__|__|  |  |  |  |  |HL|__|__|__|__|__|__|__|__|--|\n" +
                            "| V|  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |__|__|ST|  |  |  |  |  |  |\n" +
                            "| W|  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |\n" +
                            "| X|  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |\n" +
                            "| Y|  |  |  |  |  |  |--|__|--|  |  |  |  |  |  |--|__|--|  |  |  |  |  |  |\n";


        setInput("n");
        b.processTurn(new Move(Position.positionFromString("18,h"), 1000));
        b.completeTurn();

        b.processTurn(new Move(Position.positionFromString("4,s"), 1000));

        Assert.assertEquals(solution, b.justBoard());
    }

    @Test
    public void test25InvalidMovement(){
        //Cluedo.Tests.Tests room entrance and invalid simple movement
        Board b = new Board(3);
        String solution =   "|  | 1| 2| 3| 4| 5| 6| 7| 8| 9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|\n" +
                "| A|--|--|--|--|--|--|--|--|--|WH|--|--|--|--|__|--|--|--|--|--|--|--|--|--|\n" +
                "| B|  |  |  |  |  |  |--|__|__|__|  |  |  |  |__|__|__|--|  |  |  |  |  |  |\n" +
                "| C|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |\n" +
                "| D|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |\n" +
                "| E|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |  |__|__|CT|  |  |  |  |  |\n" +
                "| F|  |  |  |  |  |  |__|__|BR|  |  |  |  |  |  |BR|__|__|__|  |  |  |  |--|\n" +
                "| G|--|  |  |  |KT|  |__|__|  |  |  |  |  |  |  |  |__|__|__|__|__|__|__|__|\n" +
                "| H|__|__|__|__|__|__|__|__|  |BR|  |  |  |  |BR|  |__|__|__|__|__|__|__|--|\n" +
                "| I|--|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|  |  |  |  |  |  |\n" +
                "| J|  |  |  |  |  |__|__|__|__|__|__|__|__|__|__|__|__|__|BL|  |  |  |  |  |\n" +
                "| K|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|  |  |  |  |  |  |\n" +
                "| L|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|  |  |  |  |  |  |\n" +
                "| M|  |  |  |  |  |  |  |DR|__|__|--|--|--|--|--|__|__|__|  |  |  |  |  |  |\n" +
                "| N|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|__|__|__|__|__|--|\n" +
                "| O|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|  |  |LB|  |  |--|\n" +
                "| P|  |  |  |  |  |  |DR|  |__|__|--|--|--|--|--|__|__|  |  |  |  |  |  |  |\n" +
                "| Q|--|__|__|__|__|__|__|__|__|__|--|--|--|--|--|__|__|LB|  |  |  |  |  |  |\n" +
                "| R|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|  |  |  |  |  |  |  |\n" +
                "| S|--|__|__|__|__|__|__|__|__|  |  |HL|HL|  |  |__|__|__|  |  |  |  |  |--|\n" +
                "| T|  |  |  |  |  |  |LG|__|__|  |  |  |  |  |  |__|__|__|__|__|__|__|__|__|\n" +
                "| U|  |  |  |  |  |  |  |__|__|  |  |  |  |  |HL|__|__|__|__|__|__|__|__|--|\n" +
                "| V|  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |__|__|ST|  |  |  |  |  |  |\n" +
                "| W|  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |\n" +
                "| X|  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |\n" +
                "| Y|  |  |  |  |  |  |--|SC|--|  |  |  |  |  |  |--|__|--|  |  |  |  |  |  |\n" +
                "Dining Cluedo.GameObjects.Room: Col. Mustard\n";


        setInput("n");
        b.processTurn(new Move(Position.positionFromString("7,q"), 5));
        b.completeTurn();

        b.processTurn(new Move(Position.positionFromString("7,p"), 100));
        b.completeTurn();

        Assert.assertEquals(solution, b.toString());
    }
}
