package game;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BoardTest {

    Board board;

    @Test
    public void isBoardFullTest(){
        board = new Board(4,4);
        for(int y = 0; y < board.getHeight(); y++){
            for(int x = 0; x < board.getWidth(); x++){
                board.setTiles(y, x, 2);
            }
        }
        assert(board.isBoardFull());
        board.setTiles(0,0,0);
        assert(!board.isBoardFull());
    }

    @Test
    public void movePossibleTest(){
        board = new Board(4,4);
        int value1 = 2;
        int value2 = 32;
        for(int y = 0; y < board.getHeight(); y++){
            for(int x = 0; x < board.getWidth(); x++){
                board.setTiles(y, x, value1);
            }
        }
        assert(board.movePossible());
        for(int y = 0; y < board.getHeight(); y++){

            for(int x = 0; x < board.getWidth(); x++){
                board.setTiles(y, x, value1);
                board.setTiles(y, x+1, value2);
                x++;
            }
            value1 *= 2;
            value2 *= 2;
        }
        assert(!board.movePossible());
    }

    @Test
    public void moveLineTest(){
        board = new Board(4,4);
        for(int y = 0; y < board.getHeight(); y++){
            for(int x = 0; x < board.getWidth(); x++) {
                board.setTiles(y,x,0);
            }
        }
        board.setTiles(0,3,2);
        int[] expectedLine = new int[4];
        Integer[] movedLine = board.moveLine(board.getLine(0));
        expectedLine[0] = 2;
        for(int x = 0; x < board.getWidth(); x++){
            assert(movedLine[x] == expectedLine[x]);
        }
    }

    @Test
    public void mergeLineTest(){
        board = new Board(4,4);
        for(int y = 0; y < board.getHeight(); y++){
            for(int x = 0; x < board.getWidth(); x++) {
                board.setTiles(y,x,0);
            }
        }
        board.setTiles(0,1,2);
        board.setTiles(0,2,2);
        board.setTiles(0,3,2);
        int[] expectedLine = new int[4];
        expectedLine[0] = 4;
        expectedLine[1] = 2;
        Integer[] mergedLine = board.mergeLine(board.moveLine(board.getLine(0)));
        for(int x = 0; x < board.getWidth(); x++){
            assert(mergedLine[x] == expectedLine[x]);
        }
    }

    @Test
    public void rotateTest(){
        board = new Board(4,4);
        for(int y = 0; y < board.getHeight(); y++){
            for(int x = 0; x < board.getWidth(); x++) {
                board.setTiles(y,x,0);
            }
        }
        board.setTiles(0,1,2);
        board.setTiles(0,2,2);
        int[][] expectedBoard = new int[4][4];
        expectedBoard[1][3] = 2;
        expectedBoard[2][3] = 2;
        board.rotate();
        int[][] rotatedBoard = board.getTiles();
        for(int y = 0; y < board.getHeight(); y++){
            for(int x = 0; x < board.getWidth(); x++){
                assert(rotatedBoard[y][x] == expectedBoard[y][x]);
            }
        }
    }

    @Test
    public void compareLineTest(){
        board = new Board(4,4);
        for(int y = 0; y < board.getHeight(); y++){
            for(int x = 0; x < board.getWidth(); x++) {
                board.setTiles(y,x,0);
            }
        }
        board.setTiles(0,3,2);
        Integer[] expectedLine = new Integer[4];
        expectedLine[0] = 0;
        expectedLine[1] = 0;
        expectedLine[2] = 0;
        expectedLine[3] = 2;
        Integer[] compareLine = board.getLine(0);
        assert(board.compareLine(compareLine, expectedLine));
        compareLine = board.mergeLine(board.moveLine(board.getLine(0)));
        assert(!board.compareLine(compareLine,expectedLine));
    }

    @Test
    public void addTileTest(){
        List<Integer[]> testList = new ArrayList<>();
        board = new Board(4,4);
        for(int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                board.setTiles(y, x, 0);
            }
        }
        testList = board.checkSpace();
        assert(testList.size() == 16);
        board.addTile();
        testList = board.checkSpace();
        assert(testList.size() < 16);
    }
}
