package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {

    public static void main(String [] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        Board gameBoard = new Board(4,4);
        gameBoard.addTile();
        gameBoard.printBoard();
        System.out.println("up = e , left = s , down = d , right = f , restart game = r");
        while(!gameBoard.isWin()) {
            if (gameBoard.isWin()){
                System.out.println("You won!!!");
            } else if (gameBoard.isLose()){
                System.out.println("You lost, try again with pressing r");
            }
            line = br.readLine();
            if (line.charAt(0) == 'r') {
                gameBoard = new Board(4, 4);
                gameBoard.isWin();
                gameBoard.isLose();
                gameBoard.addTile();
                gameBoard.printBoard();
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
                System.out.println("Score: " + gameBoard.getScore());
                gameBoard.printBoard();
            }
        }
    }

}

