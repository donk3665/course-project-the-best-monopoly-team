package Entities.Game;

import Entities.ExternalDataTransfer.BasicBoard;
import Entities.ExternalDataTransfer.BasicPlayer;
import Logic.MainTreeNodeLogic.RollBranch.EmptyPropertyBranch.AuctionNodeLogic.AuctionTreeNode;

import java.util.*;
/**
 * Represents a Monopoly board.
 */
public class Board implements BasicBoard {

    private final List<Player> players;
    private final List<Cell> cells;

    /**
     * This is the constructor which creates a Board instance.
     * @param players A List of Player instances that we wish to be a part of the board.
     * @param cells A List of Cell instances that we wish to be a part of the board.
     */
    public Board(List<Player> players, List<Cell> cells){
        this.players = players;
        this.cells = cells;
    }
    public Property getPropertyFromName(String name){
        for (int i = 0; i<cells.size(); i++){
            if (cells.get(i) instanceof Property){
                if (((Property) cells.get(i)).getName().equals(name)){
                    return (Property) cells.get(i);
                }
            }
        }
        System.err.println("Could not find property");
        return null;
    }


    /**
     * This method is used to return a List of all the Player instances that are in Board.
     * @return A List of all the Player instances stored in Board.
     */
    public List<Player> getPlayers(){
        return this.players;
    }



    /**
     * This method is used to remove a player from Board.
     * @param player The player that must be removed.
     */
    public void removePlayer(Player player){
        this.players.remove(player);
    }

    @Override
    public List<BasicPlayer> getBasicPlayers() {
        return new ArrayList<>(getPlayers());
    }

    /**
     * This method is used to return a List of all the Cell instances on Board.
     * @return This returns cells, a List of all the Cell instances on Board.
     */
    public List<Cell> getCells(){return this.cells;}

    @Override
    public int[] getAuctionStates() {
        return AuctionTreeNode.getAuctionStates();
    }

    /**
     * This method is used to return a Cell at a specific position represented by an int value.
     * @param position The position of the cell.
     * @return This returns the Cell at the int position.
     */
    public Cell getCell(int position){return this.cells.get(position);}

    /**
     * This method is used to return the Cell instance that a Player instance is on.
     * @param player This is a Player instance that we want to know which Cell it is on.
     * @return This returns a Cell which the Player instance is on.
     */
    public Cell getPlayerCell(Player player){
        int position = player.getPosition();
        return this.getCell(position);
    }

}