package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {

    public static void main(String [] args) throws IOException {
        System.out.println("Please choose a board size. 4 = 4x4 (Default) , 5 = 5x5 , ...");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        line = br.readLine();
        final Board gameBoard = new Board(Integer.parseInt(line),Integer.parseInt(line));
        System.out.println("up = e , left = s , down = d , right = f , restart game = r");
        gameBoard.setTiles(0,0,1);
        gameBoard.setTiles(0,1,3);
        gameBoard.setTiles(1,0,5);
        gameBoard.printBoard();
        gameBoard.rotate();
        gameBoard.printBoard();
        while(!gameBoard.isWin() || !gameBoard.isLose()) {
            line = br.readLine();
            if(!line.isEmpty()) {
                if (line.charAt(0) == 'r') {
                    gameBoard.resetBoard();
                }
                if (!gameBoard.isWin() && !gameBoard.isLose()) {
                    switch (line.charAt(0)) {
                        case 's':
                            gameBoard.left();
                            break;
                        case 'f':
                            gameBoard.right();
                            break;
                        case 'e':
                            gameBoard.up();
                            break;
                        case 'd':
                            gameBoard.down();
                            break;
                    }
                    if (gameBoard.isWin()) {
                        System.out.println("You won!!!");
                    } else if (gameBoard.isLose()) {
                        System.out.println("You lost, try again with pressing r");
                    }
                    System.out.println("Score: " + gameBoard.getScore() + " High Score: " + gameBoard.getHighScore());
                    gameBoard.printBoard();
                }
            }
        }
    }

}

