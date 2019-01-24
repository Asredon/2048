package game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Board {

    private int width;
    private int height;
    private boolean win;
    private boolean lose;
    private int score;
    private int highScore;
    private int[][] tiles;

    /**
     * Creates a board with with at least 4x4 spaces.
     *
     * @param width - has to be at least a value of 4.
     * @param height - has to be at least a value of 4.
     */
    public Board(int height, int width){
        if(width > 4 && height > 4) {
            this.height = height;
            this.width = width;
        } else {
            this.height = 4;
            this.width = 4;
        }
        this.score = 0;
        this.tiles = new int[this.height][this.width];
        addTile();
    }

    /**
     * Clears the board, sets a new random tile to play a new game and
     * if the score of the previous game is higher than the current high score the high score will be updated.
     *
     */
    public void resetBoard(){
        if(this.score > this.highScore){
            this.highScore = this.score;
        }
        this.score = 0;
        this.win = false;
        this.lose = false;
        for(int x = 0; x < this.height; x++){
            for(int y = 0; y < this.width; y++){
                tiles[x][y] = 0;
            }
        }
        addTile();
        System.out.println("up = e , left = s , down = d , right = f , restart game = r");
    }

    /**
     * Adds the coordinates of ever empty space to a list.
     *
     * @return - returns a List<Integer[]> with the coordinates of the free spaces.
     */
    public List<Integer[]> checkSpace(){
        List<Integer[]> freeSpace = new ArrayList<Integer[]>(this.height *this.width);
        for(int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                if (tiles[y][x] == 0) {
                    Integer[] coordinates = {y,x};
                    freeSpace.add(coordinates);
                }
            }
        }
        return freeSpace;
    }

    /**
     * Checks if the board is full.
     *
     * @return - returns true if the list of freeSpaces is 0.
     */
    public boolean isBoardFull(){
        return checkSpace().size() == 0;
    }

    /**
     * Checks if possible moves are still available.
     *
     * @return - return true if the is not full or two of the same number are next to each other.
     */
    public boolean movePossible(){
        if (!isBoardFull()){
            return true;
        }
        for(int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                int tile = tiles[y][x];
                if(y < this.height - 1 && tile == tiles[y+1][x] ||
                        x < this.width - 1 && tile == tiles[y][x+1]){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Moves every tile to the left and merge tiles of the same value.
     * A new tile is only added if the move was a valid move that changed the board.
     *
     */
    public void left(){
        boolean needAddTile = false;
        for (int y = 0; y < this.height; y++){
            Integer[] line = getLine(y);
            Integer[] merged = mergeLine(moveLine(line));
            setLine(y, merged);
            if(!needAddTile && !compareLine(line, merged)){
                needAddTile = true;
            }
        }
        if(needAddTile){
            addTile();
        }
    }

    /**
     * Rotates the board clockwise to a left orientation.
     *
     */
    public void up(){
        tiles = rotate();
        tiles = rotate();
        tiles = rotate();
        left();
        tiles = rotate();
    }

    /**
     * Rotates the board clockwise to a left orientation.
     *
     */
    public void right(){
        tiles = rotate();
        tiles = rotate();
        left();
        tiles = rotate();
        tiles = rotate();
    }

    /**
     * Rotates the board clockwise to a left orientation.
     *
     */
    public void down(){
        tiles = rotate();
        left();
        tiles = rotate();
        tiles = rotate();
        tiles = rotate();
    }

    /**
     * Rotates the board clockwise once.
     *
     * @return - int[][] of the rotated board.
     */
    public int[][] rotate() {
        int[][] newTiles = new int[this.height][this.width];
            for (int y = 0; y < this.height; y++) {
                for (int x = 0; x < this.width; x++) {
                    newTiles[y][x] = tiles[this.width - x - 1][y];
                }
            }
        return tiles = newTiles;
    }

    /**
     * Adds a new tiles at a random position with a 90% chance for a 2
     * and a 10% for a 4.
     *
     */
    public void addTile(){
        List<Integer[]> list = checkSpace();
        if(!list.isEmpty()){
            int i = (int) ((Math.random() * list.size()) % list.size());
            Integer[] coordinates = list.get(i);
            int newValue = (Math.random() < 0.9 ? 2 : 4);
            tiles[coordinates[0]][coordinates[1]] = newValue;
        }
    }

    /**
     * Compares a line from before and after a merge to check if a valid move was made.
     *
     * @param line1 - line before merge
     * @param line2 - line merged line
     * @return
     */
    public boolean compareLine(Integer[] line1, Integer[] line2){
        if(line1 == line2){
            return true;
        } else if (line1.length != line2.length){
            return false;
        }
        for (int x = 0; x < line1.length; x++){
            if (line1[x].intValue() != line2[x].intValue()){
                return false;
            }
        }
        return true;
    }

    /**
     * Moves all tiles in a line to the left.
     *
     * @param line -
     * @return
     */
    public Integer[] moveLine(Integer[] line){
        LinkedList<Integer> moveList = new LinkedList<Integer>();
        for (int x = 0; x < this.height; x++){
            if (line[x] != 0) {
                moveList.addLast(line[x]);
            }
        }
        if(moveList.size() == 0){
            return line;
        } else {
            Integer[] newLine = new Integer[this.height];
            ensureSize(moveList, this.height);
            for (int x = 0; x < this.height; x++){
                newLine[x] = moveList.removeFirst();
            }
            return newLine;
        }
    }

    /**
     * Merges two tiles if they are next to each other after having been moved.
     *
     * @param line -
     * @return
     */
    public Integer[] mergeLine(Integer[] line){
        LinkedList<Integer> mergeList = new LinkedList<Integer>();
        for(int x = 0; x < this.height && line[x] != 0; x++) {
            int tileValue = line[x];
            if(x < this.height -1 && line[x].intValue() == line[x+1].intValue()){
                tileValue *= 2;
                isWin();
                this.score += tileValue;
                int target = 2048;
                if (tileValue == target){
                    this.win = true;
                }
                x++;
            }
            int newTile = tileValue;
            mergeList.add(newTile);
        }
        if(mergeList.size() == 0){
            return line;
        } else {
            ensureSize(mergeList, this.height);
            return mergeList.toArray(new Integer[this.height]);
        }

    }

    public Integer[] getLine(int y){
        Integer[] line = new Integer[this.height];
        for(int x = 0; x < this.width; x++){
            line[x] = tiles[y][x];
        }
        return line;
    }

    public void setLine(int y, Integer[] line){
        for(int x = 0; x < this.width; x++){
            tiles[y][x] = line[x];
        }
    }

    public static void ensureSize(java.util.List<Integer> l, int s) {
        while (l.size() < s) {
            l.add(0);
        }
    }

    /**
     * Checks if a tiles with a value of 2048 exists.
     *
     * @return
     */
    public boolean isWin() {
        for(int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                if (tiles[y][x] == 2048) {
                    this.win = true;
                    break;
                }
            }
        }
        return this.win;
    }

    /**
     * Checks if valid moves a possible.
     *
     * @return - true if no valid move is possible, and player has not won.
     */
    public boolean isLose(){
        if(!isWin() && !movePossible()){
           this.lose = true;
        }
        return this.lose;
    }

    public int getScore(){
        return this.score;
    }

    public int getHighScore() {
        return this.highScore;
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

    public void printBoard(){
      for(int y = 0; y < this.height; y++){
          for(int x = 0; x < this.width; x++){
              System.out.print(tiles[y][x] + "  ");
          }
          System.out.println();
      }
    }

    public int[][] getTiles(){
        return this.tiles;
    }

    public void setTiles(int y, int x, int value){
        tiles[y][x] = value;
    }
}
