package Interactors;

import Entities.Game.Player;
import Entities.Game.Property;
import Logic.GeneralGameNode;
import Logic.MainTreeNodeLogic.MainGameNode;

/***
 * This class turns the objects of the game needed for data persistence into their needed forms for
 * delivery to the SaveAccess methods.
 */
public class SavePackager {

    /**
     * This method converts the states array into the form needed for saving
     * in the SaveAccess interface
     * @return an int array which is formatted correctly for the SaveAccess interface
     */
    public int[] getStates(){
        int[] saveStates = new int[7];
        int[] gameStates = MainGameNode.getStates();
        saveStates[0] = GeneralGameNode.getBoard().getPlayers().size();
        saveStates[1] = gameStates[4];
        saveStates[2] = gameStates[2];
        saveStates[3] = gameStates[3];
        saveStates[4] = GeneralGameNode.getCurrentPlayerIndex();
        saveStates[5] = gameStates[1];
        saveStates[6] = gameStates[5];

        return saveStates;
    }

    /**
     * This method converts the board in the program to an array of strings for the saving function in the
     * SaveAccess interface
     * @return an array of strings which is in the correct format to be passed into a SaveAccess interface.
     */
    public String[][] getPlayerPropertyData(){
        int numberOfLines = 0;
        for (Player player: GeneralGameNode.getBoard().getPlayers()){
            numberOfLines += 1;
            numberOfLines += player.getProperties().size();
        }
        String[][] data = new String[numberOfLines][];
        int counter = 0;
        String[] temp;
        for (Player player: GeneralGameNode.getBoard().getPlayers()){
            temp = new String[]{player.getName(),String.valueOf(player.getMoney()), String.valueOf(player.isInJail()),
                    String.valueOf(player.getJailCards()), String.valueOf(player.getPosition())};
            data[counter] = temp;
            counter += 1;
            for (Property property: player.getProperties()){
                temp = new String[]{property.getName(),property.getColour(),String.valueOf(property.getPrice()),
                        String.valueOf(property.getHouseCost()),String.valueOf(property.getRentSave(0)),String.valueOf(property.getRentSave(1))
                        ,String.valueOf(property.getRentSave(2)),String.valueOf(property.getRentSave(3)),String.valueOf(property.getRentSave(4)),
                        String.valueOf(property.getRentSave(5)), property.getOwner().getName(), String.valueOf(property.getMortgageValue()),
                        String.valueOf(property.getHouses()), String.valueOf(property.getMortgageStatus())};
                data[counter] = temp;
                counter += 1;
            }
        }
        return data;
    }
}
