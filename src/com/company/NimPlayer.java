import java.util.Scanner;  // uses scanner for imports

/**
 * Ben Grass
 * Date: 4/12/13
 * Time: 8:49 PM
 * This Program: Plays the Nim game using functions from Nim.java, and using Tuple.java
 * @see Nim
 * @see Tuple
 * Note: ported from Nim.py
 */
public class NimPlayer {
    public static void main(String[] args) throws InterruptedException{
        
        Scanner scanner = new Scanner(System.in); // Scanner for input of information
        int[] game = {1,3,5,7}; // A game state, each number represents the number of pieces in each row.
        Nim.printBoard(game);  // Prints the board to start off.
        while(true){  // enters the repetitive portion of the game.
            boolean bool = true; // a boolean to control entering a valid move
            while(bool){  // loop to control entering a valid move
                System.out.print("Your Move:\nRow? ");   // asks for a row
                int x = scanner.nextInt() -1; // enters a row
                System.out.print("Amount? "); // asks for an amount
                int y = scanner.nextInt();   // enters an amount
                System.out.println();   // skips a line, for neatness sake.
                Tuple<Integer,Integer> action = new Tuple<Integer, Integer>(x,y);  // creates a Tuple to represent the move,
                /**
                  * @see Tuple
                  */
                if(Nim.validMove(game,action)){   // detects whether "action" represents a valid move.
                    game = Nim.move(game,action); // if the move is valid, executes the move.
                    bool = false; // exits the loop
                }else{   // if it isn't valid, it prints that it isn't and the loop continues.
                    System.out.println("Invalid Move!\n");
                }
            }
            Nim.printBoard(game); // prints the board after the player move has been made.
            if(Nim.win(game)){ // detects a win, and if there is a win, it tells that the player has won.
                Nim.showWinner("Player");
                return;  // exits the program.
            }
            System.out.println("Computer is making a move... \n"); // tries to make the illusion that the computer reallly has to work in order to win.
            Thread.sleep(1500); // waits to achieve the aforementioned goal.
            Tuple<Integer,Integer> compact = Nim.bestMove(game); // creates a tuple that takes whatever move has been spawned by bestMove.
            /**
             * @see Nim.bestMove()
              */

            if(Nim.validMove(game,compact)){   // just as a precaution, because compact should always be valid, it checks if compact is valid
                game = Nim.move(game,compact);  // if it is valid, it executes the move
            }
            Nim.printBoard(game);  // prints the board
            if(Nim.win(game)){   // if there is a win, it prints computer
                Nim.showWinner("Computer");
                return;  // and exits
            }
        }

    }
}
