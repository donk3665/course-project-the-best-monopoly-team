package TreeHandlers;

import Entities.Player;
import Entities.State;

import java.util.ArrayList;

/**
 * This class represents a use case where a Player instance chooses to steal money from another Player instance.
 */
public class Steal extends TreeHandler implements NodeLogic {

    /**
     * This method creates a States object and adds all the possible list of players that the current player can steal
     * from as options. Then it returns the State object.
     * @param input An integer that represents the input of the user. However, this parameter is not used in this method.
     * @return A State object containing options which are a list of all possible that the current player can steal from.
     * This excludes the current player.
     */
    @Override
    public State create_state(int input) {
        State currentState = new State();
        currentState.setBackEnable(true);
        currentState.setId(gameLogicInteractor.getCurrentTree().getName());
        //provide options of which players we can steal from
        ArrayList<Player> playerCopy = new ArrayList<Player>(board.getPlayers());
        playerCopy.remove(currentPlayer);
        for(int i = 0; i < playerCopy.size(); i++){
            currentState.addOptions(playerCopy.get(i).getName());
        }
        return currentState;
    }
}