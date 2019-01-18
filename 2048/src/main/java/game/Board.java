package game;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private int height;
    private int width;
    private boolean win;
    private Tile[][] tiles;

    public Board(int width, int height){
        if (width < 4 || height < 4) {
            throw new AssertionError("The board cannot be smaller than 4x4");
        }

        this.tiles = new Tile[width][height];
    }

    public List<Tile> checkSpace(){
        List<Tile> freeSpace = new ArrayList<Tile>(16);
        for(int x = 0; x < this.width; x++){
            for (Tile[] tile: tiles) {
                for (Tile t: tile ){
                    if (t.getValue() == 0) {
                        freeSpace.add(t);
                    }
                }
            }
        }
        return freeSpace;
    }

    public boolean isBoardFull(){
        return checkSpace().size() == 0;
    }

    public boolean isWin() {
        for(int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                if (tiles[x][y].getValue() == 2048) {
                    return win = true;
                }
            }
        }
            return win = false;
    }

    public boolean canMove(){
        if (!isBoardFull()){
            return true;
        }
        for(int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                Tile tile = tiles[x][y];
                if(x < 3 && tile.getValue() == tiles[x+1][y].getValue() ||
                        y < 3 && tile.getValue() == tiles[x][y+1].getValue()){
                    return true;
                }
            }
        }
        return false;
    }

    public void addTile(){
        List<Tile> list = checkSpace();
        if(!checkSpace().isEmpty()){
            int i = (int) ((Math.random() * list.size()) % list.size());
            Tile newTile = list.get(i);
            newTile.setValue(Math.random() < 0.9 ? 2 : 4);
        }
    }

    public void moveLine(){

    }

    public void mergeLine(){

    }

    public Tile[] getLine(int y){
        Tile[] line = new Tile[width];
        for(int x = 0; x < width; x++){
            line[x] = tiles[x][y];
        }
        return line;
    }

}
