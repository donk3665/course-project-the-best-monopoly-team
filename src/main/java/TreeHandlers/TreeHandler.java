package TreeHandlers;

import Entities.*;
import Interactors.GameLogic;

import java.util.HashMap;

/** This class is the parent class of the three tree handlers used during the game phase of the application.
 *  <br> Each of the subclasses coordinate the game logic of the application.
 */
public class TreeHandler {

    //Static variables used by all of the subclasses
    static GameLogic gameLogicInteractor;
    static Player currentPlayer;
    static Board board;
    static HashMap<String, Integer> selectedOptions = new HashMap<String, Integer>();
    static int returnPlayerIndex = -1;
    static Player[] players;
    static GameLogicTree returnTree;
    static String description;


    /**
     * This method initializes variables used by the tree handlers
     * @param currentPlayer1 - The current player of the game
     * @param board1 - the current board being used in the game
     */
    public void initialize(Player currentPlayer1, Board board1){
        players = board.getPlayers();
        currentPlayer = currentPlayer1;
        board = board1;
    }

    /**
     * This method gives the TreeHandlers a reference to the GameLogicInteractor
     * @param interactor - the gameLogicInteractor
     */
    public void setGameLogicInteractor(GameLogic interactor){
        gameLogicInteractor = interactor;
    }

    /**
     * This method gets the current state object of the program.
     * @return the current state of the program.
     */
    public State getInitialState(){
        State currentState = new State();
        currentState.setDescription(gameLogicInteractor.getCurrentTree().getPrompt());
        for (MenuTree tree: gameLogicInteractor.getCurrentTree().getChildren()){
            currentState.addOptions(tree.getName());
        }
        return currentState;
    }

    /**
     * This method mutates the current state object by adding all the subtrees of the current tree as options.
     * @param currentState - the state object to be mutated
     */
    public void addSwitchOptions(State currentState){
        for (MenuTree tree: gameLogicInteractor.getCurrentTree().getChildren()){
            currentState.addOptions(tree.getName());
        }
    }
    public int getCurrentPlayerIndex(){
        for (int i = 0; i< players.length; i++){
            if (currentPlayer == players[i]){
                return i;
            }
        }
        throw new RuntimeException("Player not in array");
    }
    public Object[][] playersToArray(){
        Object[][] playersArray = new Object[players.length][6];
        for(int i = 0; i < players.length; i++){
            playersArray[i][0] = players[i].getName();
            playersArray[i][1] = players[i].getMoney();
            playersArray[i][2] = players[i].getProperties();
            playersArray[i][3] = players[i].isInJail();
            playersArray[i][4] = players[i].getJailCards();
            playersArray[i][5] = players[i].getPosition();
        }
        return playersArray;
    }

    public Object[] boardToArray(){
        Object[] boardArray = new Object[4];
        boardArray[0] = board.getPlayers();
        boardArray[1] = board.getCells();
        boardArray[2] = board.getPlayerPositions();
        boardArray[3] = board.getProperties();
        return boardArray;
    }


    public void movePlayer(int cell_number){
        int total_squares = 40;
        int current_player_position = currentPlayer.getPosition();
        if (cell_number - current_player_position < 0) {
            currentPlayer.setMoney(currentPlayer.getMoney() + 200);
        }
        currentPlayer.setPosition(cell_number);
        board.updatePlayerPosition(currentPlayer);
    }

    public void setCurrentPlayer(Player player){currentPlayer = player;
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }
    public GameLogicTree getReturnTree() {
        return returnTree;
    }

    public void setReturnTree(GameLogicTree returnTree1) {
        returnTree = returnTree1;
    }

}
