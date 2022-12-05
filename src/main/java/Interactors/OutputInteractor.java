package Interactors;

import Entities.Output;
import Entities.State;
import UseCases.UseCaseInteractor;

import java.util.ArrayList;

/***
 * OutputInteractor is a class that controls the data in the Output Entity and interacts with the UseCaseInteractor
 * to present output the user based on the current state of the game
 */
public class OutputInteractor {
    /**
     *  InsatanceVar output: an Output object that keeps track and updates the output depending on the state of the game
     *  InstanceVar currentState: a State object that helps with determining the output that should be presented to the user
     */
    private Output output;
    private State currentState;

    /**
     * The Constructor for the OutputInteractor Class
     * @param interactor: gets the initial state of the game
     */
    public OutputInteractor(UseCaseInteractor interactor){
        this.output = new Output();
        StateOutputReader createInitOutput = new StateOutputReader();
        createInitOutput.initStateHash();
        this.output.setInitStateHash(createInitOutput.getStateMap());
        this.currentState = interactor.getInitialState();
    }

    /**
     * setFinalOutput updates the string to be presented to the user by updating all the output strings that should
     * be presented to the user based on the state and presented all the options based on the state
     */
    public void setFinalOutput(){
        updateLogicStates(this.currentState.getId());
        this.output.setFinalOutput(this.output.getStateOutput(this.currentState.getId()));
        addOptionStrings();
    }

    /**
     * This function deals with all the states that need to be updated periodically based on the current state of the game
     * @param state: the current state that game is in
     */
    public void updateLogicStates(String state){
        StringBuilder currString = new StringBuilder();
        switch (state) {
            case "MainTree":
                updateMainTree();
                break;
            case "SendTrade":
                updateSendTrade();
                break;
            case "BuildProperty":
                updateBuildProperty();
                break;
            case "CallAction":
                updateCallAction();
                break;
            case "EmptyPropertySpace":
                updateEmptyPropertySpace();
                break;
            case "AuctionTree":
                updateAuctionTree();
                break;
            case "Fold":
                updateFold();
                break;
        }
    }

    /**
     * All the functions below are helper update functions
     */
    public void updateMainTree(){
        String currString = this.currentState.getPlayer().getName() + " It's your turn! What do you want to do? You currently have " +
                this.currentState.getPlayer().getMoney() + " dollars";
        this.output.modifyStateOutput("MainTree", currString);
    }

    public void updateSendTrade(){
        String currString = currentState.getTradingOpponent().getName() + ", Incoming trade from player " +
                currentState.getPlayer().getName() + " requesting for " + currentState.getTradingPlayerProperty().getName() +
                " in return for " + currentState.getCurrentPlayerProperty().getName();
        this.output.modifyStateOutput("SendTrade", currString);
    }
    public void updateBuildProperty(){
        String currString = currentState.getCurrentPlayerProperty().getHouses() + " houses built on this property";
        this.output.modifyStateOutput("BuildProperty", currString);
    }

    public void updateCallAction(){
        String currString =  "You rolled a " + currentState.getRoll()+ currentState.getDescription();
        this.output.modifyStateOutput("CallAction", currString);
    }
    public void updateEmptyPropertySpace(){
        String currString = "You rolled a " + currentState.getRoll() + " You have landed on " +
                currentState.getCurrentPlayerProperty().getName() + " and no ones owns this. It costs " +
                currentState.getCurrentPlayerProperty().getPrice() + " What do you want to do?";
        this.output.modifyStateOutput("EmptyPropertySpace", currString);
    }
    public void updateAuctionTree(){
        String currString = currentState.getPlayer().getName() + ", we are bidding on " + currentState.getBiddingProperty().getName() +
                " with the current pot being " + currentState.getBiddingPot();
        this.output.modifyStateOutput("AuctionTree", currString);
    }
    public void updateFold(){
        String currString = currentState.getPlayer().getName() + " won the auction for " + currentState.getBiddingPot() + " dollars";
        this.output.modifyStateOutput("Fold", currString.toString());
    }

    /**
     * Function to get the options the user has based on the state and concatenate those options to present to the user
     */
    public void addOptionStrings(){
        StringBuilder currOptions = new StringBuilder("\n");
        ArrayList<String> options = this.currentState.getOptions();
        for (int i = 0; i < options.size(); i++)
            currOptions.append(options.get(i)).append("(").append(i).append("), ");
        if (this.currentState.isBackEnable())
            currOptions.append("back").append("(").append(options.size()).append(")");
        this.output.addToFinalOutput(currOptions.toString());
    }

    /**
     * @return the final output string that is to be presented to the user
     */
    public String getOutput(){
        return this.output.getFinalOutput();
    }

    /**
     * Update the current state based on changes on the previous state from the user's input
     * @param state: the updated state
     */
    public void updateState(State state){
        this.currentState = state;
    }
}