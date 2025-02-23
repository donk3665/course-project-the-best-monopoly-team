package Interactors;

import Entities.Game.*;
import Persistence.LoadAccess;
import Persistence.LoadFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class is used to initialize or create a Monopoly board.
 */

public class GameCreation {
    /** Initializing a Board instance with default values.
     *
     * @param playersName an Arraylist of Strings which denote each player's name.
     * @param properties  an Arraylist of String[] arrays, each subarray contains the default instance attributes of a Property.
     * @return a Board instance initialized with default Properties and Players with names given by playersName.
     * @throws IOException in case there was an error with creating an ActionSpace in createCells
     */
    public Board createNewBoard(ArrayList<String> playersName, ArrayList<String[]> properties) throws IOException {
        // This method is to create a brand-new game and initialize a new Board.
        ArrayList<Player> players = new ArrayList<>();

        for (int i = 0; i<playersName.size(); i++){
            Player newPlayer = new Player(playersName.get(i),i);
            players.add(newPlayer);
        }
        return createNewBoardHelper(players, properties);
    }
    public Board createNewBoardHelper(ArrayList<Player> players, ArrayList<String[]> properties) throws IOException {
        // This method is to create a brand-new game and initialize a new Board.
        ArrayList<Cell> propertiesSoFar;
        propertiesSoFar = parsePropertyData(properties);

        List<Cell> cells = createCells(propertiesSoFar, propertiesSoFar);

        return new Board(players, cells);
    }

    /**
     * Based on the given integer and board, mutate the board's objects.
     * That is, change the objects relevant to the board to fit the specific mode.
     * @param board the board to be altered
     * @param mode the mode of the board
     */
    public void initializeMode(Board board, int mode){
        if (mode == 1){
            for (Player player: board.getPlayers()){
                player.changeMoney(1000);
            }
        }
    }


    /** Loads a saved game by creating a Board instance with save data from gameData
     *
     * @param gameData an Arraylist with a sub Arraylists of String[] arrays where each array represents Player or Property instance data
     * @param newProperties an Arraylist where each sub String[] array contains default Property instance data
     * @return a Board instance initialized with gameData represented as their respective Entities
     * @throws IOException in case there was an error with creating an ActionSpace in createCells
     */
    public Board createSavedBoard(ArrayList<ArrayList<String[]>> gameData, ArrayList<String[]> newProperties) throws IOException {
        ArrayList<Player> players = new ArrayList<>();
        ArrayList<Cell> properties = new ArrayList<>();
        ArrayList<Cell> standardProperties = parsePropertyData(newProperties);

        int playerIndexCounter = 0;
        // playerData
        for (String[] instance : gameData.get(0)){
            if (instance.length == 5){
                // Player instance
                boolean inJail = instance[2].equals("true");

                Player player = new Player(instance[0], Integer.parseInt(instance[1]), inJail,
                        Integer.parseInt(instance[3]), Integer.parseInt(instance[4]), playerIndexCounter);
                players.add(player);
                playerIndexCounter +=1;
            } else {
                // Property instance
                boolean mortgaged = instance[13].equals("true");
                Player owner = getOwner(players, instance[10]);

                int[] rentValues = new int[] {Integer.parseInt(instance[4]), Integer.parseInt(instance[5]),
                        Integer.parseInt(instance[6]), Integer.parseInt(instance[7]),
                        Integer.parseInt(instance[8]), Integer.parseInt(instance[9])};

                Property property = new Property(instance[0], instance[1], Integer.parseInt(instance[2]),
                        Integer.parseInt(instance[3]), rentValues, owner, Integer.parseInt(instance[11]),
                        Integer.parseInt(instance[12]), mortgaged);

                assert owner != null;
                owner.addProperty(property);
                properties.add(property);
                }
            }

        List<Cell> cells = createCells(properties, standardProperties);
        return new Board(players, cells);
    }

    /**
     * Loads the initial states stored in a save file
     * @param gameData an ArrayList containing 2 sub ArrayLists;
     *                 the first of Players and their owned Properties
     *                 the second of initial states required for the Tree
     * @return an Integer[] of all the saved initial states
     */
    public int[] getInitialStates(ArrayList<ArrayList<String[]>> gameData) {
        int[] initialStates = new int[gameData.get(1).get(0).length];
        for (int i = 0; i < initialStates.length; i++) {
            initialStates[i] = Integer.parseInt(gameData.get(1).get(0)[i]);
        }

        return initialStates;
    }

    /** Gets the Player instance based on a String of a Player's name.
     *
     * @param players an Arraylist of Player instances.
     * @param playerName a String of a Player's name.
     * @return the Player instance which has the name playerName, or null if such a Player does not exist
     */
    private Player getOwner(ArrayList<Player> players, String playerName) {
        for (Player player : players) {
            if (player.getName().equals(playerName)) {
                return player;
            }
        }
        return null;
    }

    /** Creates all the Cells to be used to create a Board class. Properties which are owned
     *  by Players (which potentially have houses built already) are inserted in place of
     *  default Property instance.
     *
     * @param propertiesSoFar an Arraylist of Property instances which are owned by Players
     * @param standardProperties an Arraylist of all Property instances with default values
     * @return a List of all Cells on the Monopoly board
     * @throws IOException in case there was an error with creating an ActionSpace
     */
    public List<Cell> createCells(ArrayList<Cell> propertiesSoFar, ArrayList<Cell> standardProperties) throws IOException {

        CornerTiles go = new PassGo();
        CornerTiles jail = new JailSpace();
        CornerTiles freeParking = new FreeParking();
        CornerTiles goJail = new GoToJail();


        LoadAccess loadAccess = new LoadFile(null);

        loadAccess.loadCards("/save/cards.txt");

        ActionSpaceCreationInteractor actionSpaceCreationInteractor = new ActionSpaceCreationInteractor(loadAccess);
        ActionSpace communityChest = actionSpaceCreationInteractor.loadComChestCards();
        ActionSpace chance = actionSpaceCreationInteractor.loadChanceCards();
        ActionSpace luxuryTax = new ActionSpace(new Card("tax","You need to pay $100 in luxury tax!",ActionTypeEnum.TAX,100));
        ActionSpace incomeTax = new ActionSpace(new Card("tax","You need to pay $200 in income tax!",ActionTypeEnum.TAX,200));

        standardProperties.add(0, go);
        standardProperties.add(2, communityChest);
        standardProperties.add(4, incomeTax);
        standardProperties.add(7, chance);
        standardProperties.add(10, jail);
        standardProperties.add(17, communityChest);
        standardProperties.add(20, freeParking);
        standardProperties.add(22, chance);
        standardProperties.add(30, goJail);
        standardProperties.add(33, communityChest);
        standardProperties.add(36, chance);
        standardProperties.add(38, luxuryTax);

        if (!propertiesSoFar.equals(standardProperties)) {
            for (Cell property : propertiesSoFar) {
                Property cProperty = (Property) property;
                for (int i = 0; i < 40; i++) {
                    if (standardProperties.get(i) instanceof Property) {
                        Property currentCell = (Property) standardProperties.get(i);
                        if (currentCell.getName().equals(cProperty.getName())) {
                            standardProperties.remove(i);
                            standardProperties.add(i, cProperty);
                        }
                    }
                }
            }
        }

        return standardProperties;
    }

    /** Creates an Arraylist of Property instances with default values.
     *
     * @param propertyData an Arraylist of String[] arrays where each array contains default values for a Property instance.
     * @return an Arraylist of Property instances initialized with their default values.
     */
    public ArrayList<Cell> parsePropertyData(ArrayList<String[]> propertyData) {
        // for unowned Property instances:
        // index [10] is int mortgageValue, [11] is int houses,
        // String playerOwnerName and booleanMortaged are not stored by default

        ArrayList<Cell> newProperties = new ArrayList<>();

        for (String[] data : propertyData) {
            int[] rentValues = new int[]{Integer.parseInt(data[4]), Integer.parseInt(data[5]),
                    Integer.parseInt(data[6]), Integer.parseInt(data[7]),
                    Integer.parseInt(data[8]), Integer.parseInt(data[9])};

            Property property = new Property(data[0], data[1], Integer.parseInt(data[2]),
                    Integer.parseInt(data[3]), rentValues, null, Integer.parseInt(data[10]),
                    Integer.parseInt(data[11]), false);

            newProperties.add(property);
        }
        return newProperties;
    }
}

