package game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Board {

    private int y;
    private int x;
    private boolean win;
    private boolean lose;
    private int score;
    private int highScore;
    private int[][] tiles;

    public Board(int x, int y){
        if (x < 4 || y < 4) {
            throw new AssertionError("The board cannot be smaller than 4x4");
        }
        this.x = x;
        this.y = y;
        this.score = 0;
        this.tiles = new int[this.x][this.y];
        addTile();
    }

    public void resetBoard(){
        if(this.score > this.highScore){
            this.highScore = this.score;
        }
        this.score = 0;
        this.win = false;
        this.lose = false;
        for(int x = 0; x < this.x; x++){
            for(int y = 0; y < this.y; y++){
                tiles[x][y] = 0;
            }
        }
        addTile();
        System.out.println("up = e , left = s , down = d , right = f , restart game = r");
    }

    private List<Integer[]> checkSpace(){
        List<Integer[]> freeSpace = new ArrayList<Integer[]>(this.x *this.y);
        for(int x = 0; x < this.x; x++) {
            for (int y = 0; y < this.y; y++) {
                if (tiles[x][y] == 0) {
                    Integer[] coordinates = {x,y};
                    freeSpace.add(coordinates);
                }
            }
        }
        return freeSpace;
    }

    private boolean isBoardFull(){
        return checkSpace().size() == 0;
    }

    private boolean movePossible(){
        if (!isBoardFull()){
            return true;
        }
        for(int x = 0; x < this.x; x++) {
            for (int y = 0; y < this.y; y++) {
                int tile = tiles[x][y];
                if(x < 3 && tile == tiles[x+1][y] ||
                        y < 3 && tile == tiles[x][y+1]){
                    return true;
                }
            }
        }
        return false;
    }

    public void left(){
        boolean needAddTile = false;
        for (int x = 0; x < this.x; x++){
            Integer[] line = getLine(x);
            Integer[] merged = mergeLine(moveLine(line));
            setLine(x, merged);
            if(!needAddTile && !compareLine(line, merged)){
                needAddTile = true;
            }
        }
        if(needAddTile){
            addTile();
        }
    }

    public void up(){
        tiles = rotate();
        tiles = rotate();
        tiles = rotate();
        left();
        tiles = rotate();
    }

    public void right(){
        tiles = rotate();
        tiles = rotate();
        left();
        tiles = rotate();
        tiles = rotate();
    }

    public void down(){
        tiles = rotate();
        left();
        tiles = rotate();
        tiles = rotate();
        tiles = rotate();
    }

    private int[][] rotate() {
        int[][] newTiles = new int[this.x][this.y];
            for (int x = 0; x < this.x; x++) {
                for (int y = 0; y < this.y; y++) {
                    newTiles[x][y] = tiles[this.y - y - 1][x];
                }
            }
        return newTiles;
    }

    private void addTile(){
        List<Integer[]> list = checkSpace();
        if(!list.isEmpty()){
            int i = (int) ((Math.random() * list.size()) % list.size());
            Integer[] coordinates = list.get(i);
            int newValue = (Math.random() < 0.9 ? 2 : 4);
            tiles[coordinates[0]][coordinates[1]] = newValue;
        }
    }

    private boolean compareLine(Integer[] line1, Integer[] line2){
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

    private Integer[] moveLine(Integer[] line){
        LinkedList<Integer> moveList = new LinkedList<Integer>();
        for (int x = 0; x < this.x; x++){
            if (line[x] != 0) {
                moveList.addLast(line[x]);
            }
        }
        if(moveList.size() == 0){
            return line;
        } else {
            Integer[] newLine = new Integer[this.x];
            ensureSize(moveList, this.x);
            for (int x = 0; x < this.x; x++){
                newLine[x] = moveList.removeFirst();
            }
            return newLine;
        }
    }

    private Integer[] mergeLine(Integer[] line){
        LinkedList<Integer> mergeList = new LinkedList<Integer>();
        for(int x = 0; x < this.x && line[x] != 0; x++) {
            int tileValue = line[x];
            if(x < this.x -1 && line[x].intValue() == line[x+1].intValue()){
                tileValue *= 2;
                isWin();
                this.score += tileValue;
                int target = 2048;
                if (score == target){
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
            ensureSize(mergeList, this.x);
            return mergeList.toArray(new Integer[this.x]);
        }

    }

    private Integer[] getLine(int x){
        Integer[] line = new Integer[this.x];
        for(int y = 0; y < this.y; y++){
            line[y] = tiles[x][y];
        }
        return line;
    }

    private void setLine(int x, Integer[] line){
        for(int y = 0; y < this.y; y++){
            tiles[x][y] = line[y];
        }
    }

    private static void ensureSize(java.util.List<Integer> l, int s) {
        while (l.size() != s) {
            l.add(0);
        }
    }

    public boolean isWin() {
        for(int x = 0; x < this.x; x++) {
            for (int y = 0; y < this.y; y++) {
                if (tiles[x][y] == 2048) {
                    return win = true;
                }
            }
        }
        return win = false;
    }

    public boolean isLose(){
        if(!isWin() && !movePossible()){
            return this.lose = true;
        }
        return false;
    }

    public int getScore(){
        return this.score;
    }

    public int getHighScore() {
        return highScore;
    }

    public void printBoard(){
        for(int[] x: tiles){
            System.out.format("%5d%5d%5d%5d%n", x[0], x[1], x[2], x[3]);
        }
        System.out.format("%n");
    }
}
