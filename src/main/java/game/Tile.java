package game;

public class Tile {

    private int value;
    private boolean merged;

    public Tile(){

    }

    public boolean isMerged() {
        return this.merged;
    }

    public int getValue() {
        return this.value;
    }


    public void setMerged(boolean merged) {
        this.merged = merged;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
