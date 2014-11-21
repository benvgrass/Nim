import java.util.HashMap;

/**
 * Ben Grass
 * Date: 2/13/13
 * Time: 10:19 AM
 * This Program: Methods to play Nim, used within the class and in
 * @see NimPlayer
 * Uses:
 * @see Tuple
 * Note: This should be used as a utility class.
 */
public class Nim {

    public static int[] move(int[] gameState, Tuple<Integer,Integer> action){ // executes a move
        /**
         * @param gameState - represents the gameState
         * @param action - Tuple that represents an action
         */
        int[] game = gameState.clone(); // clones the game to avoid problems with by-reference passing
        game[action.getX()] -= action.getY();  //subtracts the amount fromt the row
        return game;  // returns the appended game-state
    }

    public static void printBoard(int[] game){  // prints the board
        /**
         * @param game - represents the gameState
         */
        System.out.println("\n"); // Creates some space, runs a loop to print a specific number of asterisks.
        for(int i = 0; i <  game.length; i++){    // for each row
            System.out.print("Row " + (i+1) + ": ");     //print row
            for(int j = 0; j < game[i]; j++){   // for length of row
                System.out.print("*");   // print an asterisk
            }
            System.out.println();  // go to next line so that it is nice and formatted
        }



    }

    public static void showWinner(String player){ // prints that a player has won.
        /**
         * @param player - is a string that is a player, ex) if player = Computer, it will print "Computer" has won
         */
        System.out.println(player + " has won!");

    }

    private static Tuple<Integer,Integer>[] possibleActions(int[] game){  // returns an array of possible moves for the computer to execute
        /**
         * @param game - represents the gameState
         */
        Tuple[] actions; // end result
        int total= 0;   // number of possible actions
        for(int i: game){
        /* calculates the number of possible actions through a simple formula, basically just adding the
        number of elements, because you can't take zero, but you can take all the elements
         */
            total += i;
        }
        actions = new Tuple[total]; // creates an array with the appropriate length.
        int counter = 0;  // counter to track which element of the array is being added
        for(int i = 0; i < game.length; i++){   // for the length of the array
            for(int j = 1; j <= game[i]; j++){  // for the length of each element
                Tuple<Integer, Integer> action = new Tuple<Integer, Integer>(i,j);  // new move is created as a tuple.
                actions[counter] = action; // new tuple is added
                counter ++;   // counter is increased
            }
        }

        return actions; // return the tuple
    }

    public static boolean win(int[] game){  // checks to see if a win has occurred
        /**
         * @param game - represents the gameState
         */
        boolean total = true;  // boolean that represents whether all the rows are zero or not
        for(int i: game){
            total &= (i == 0);  // if all the elements of game are zero it remains true, otherwise it is false
        }

        return total; // return total
    }

    private static HashMap<String,Boolean> valueCache = new HashMap<String, Boolean>();
    // a cache of gameStates, current players, and their values according to method gameValue

    private static boolean gameValue(int[] game, boolean player){
        /**
         * @param game - represents the gameState
         * @param player - represents the player that is playing, true for computer, false for player
         */
        Tuple value = new Tuple(game,player); // creates tuple to represent the game/player combo
        if(!(valueCache.containsKey(Tuple.toString(value)))){
            /**
             * if value isn't in its toString form is in the valueCache, execute the following code
             * @see Tuple
             */

            if(Nim.win(game)){
                /*
                 *
                 * if there is a win, put the toString version of the tuple and the value of the opposite of player,
                 *  because if the there is a win and then it becomes the computers turn, that is bad for the computer, so it returns
                 *  false.
                 */

                valueCache.put(Tuple.toString(value),!player);
            }else{   // if there isn't a win
                Tuple[] actions = Nim.possibleActions(game);  // get the possible actions for game
                int[][] newGames = new int[actions.length][4];  // creates an array to hold the new game-state
                for(int x = 0; x < actions.length; x++){  // executes all the moves on all of the game-states
                    newGames[x] = Nim.move(game,actions[x]);
                }
                boolean[] values = new boolean[newGames.length];  // creates an array to hold the value of the new gameStates
                for(int x = 0; x < values.length; x++){  // gets all of the values
                    values[x] = Nim.gameValue(newGames[x].clone(),!player); // recursive call to get all the new values
                }

                if(player){  // if it is the players turn
                    /*
                    if it is players turn (and the moves have been executed, so really it is now the computers turn
                    and any of values is true (so if any values lead to a win),it will be true,
                     but no matter what it is, put this situation in valueCache
                    */
                    valueCache.put(Tuple.toString(value),Booleans.any(values));
                }else{ // if it is computers turn
                    /*
                    if it is computers turn (and moves have been executed so it is really players turn), and
                    all of the values are true (so if all values don't lead to a loss for the computer), it will be true,
                    but no matter, put this situation in valueCache
                     */
                    valueCache.put(Tuple.toString(value),Booleans.all(values));
                }
                /**
                 * for both of the above methods any and all,
                 * @see Booleans
                 */
            }
        }
        return valueCache.get(Tuple.toString(value)); // return whatever this situation is in the valueCache
    }

    public static Tuple<Integer,Integer> bestMove(int[] game) { // gets the best move according to value
        /**
         * @param game - represents the gameState
         */
        Tuple<Integer,Integer>[] actions = Nim.possibleActions(game); // gets the possible actions
        // all of this is basically the first part of bestMove without the recursive call, basically just to get it started
        int[][] newGames = new int[actions.length][4]; // creates a list of new games
        for(int x = 0; x < actions.length; x++){  // get new games
            newGames[x] = Nim.move(game,actions[x]);
        }
        boolean[] values = new boolean[newGames.length];    // creates array for values
        for(int x = 0; x < values.length; x++){   // gets the values
            values[x] = Nim.gameValue(newGames[x].clone(),false);
        }
        for(int x = (values.length-1); x >= 0; x--){  // if there is a true situation, get the most aggressive answer
            // getting the most aggressive answer is just a matter of going backwards through the possible actions array
            if(values[x])
                return actions[x];
        }
        return actions[0]; // if nothing has been returned, return the smallest thing possible, or whatever was added last
        // making a small move is done in order to see if the other person might make a mistake, which the person will
        // then never be able to recover from
        // This part will only ever be used if the computer has to go first
    }

    public static boolean validMove(int[] game, Tuple<Integer,Integer> action){  // determines whether a move is valid
        /**
         * @param gameState - represents the gameState
         * @param action - Tuple that represents an action
         */
        boolean[] total = new boolean[2];  // create an array of booleans with length two
        total[0] = ((action.getX() >= 0) && (action.getX() < 4)); // the first part is whether or not you've selected an actual row.
        if(total[0]){
            total[1] = ((action.getY() <= game[action.getX()]) && (action.getY() != 0));   // whether you've selected less than what
            // or what is in the row, and you haven't selected zero.
        }
        return Booleans.all(total); // return the all of this array of boolean
    }

}