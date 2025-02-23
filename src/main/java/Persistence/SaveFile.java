package Persistence;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class which is responsible for the saving of files into text files
 */
public class SaveFile implements SaveAccess {
    private File file;
    private final String saveLocation;

    private ArrayList<String> tempLines;

    /**
     * Constructor for SaveFile which implements load and save game functionality.
     * The UseCaseInteractor handles implementation with respect to DataAccessInterface.
     *
     * @param file  is the file used to load or save game data to.
     */
    public SaveFile(File file) {
        this.file = file;
        this.saveLocation = file.getAbsolutePath();
        tempLines = new ArrayList<>();
    }


    public File getFile() {
        return this.file;
    }

    /**
     * Check if the save file exists
     * @return true if the file does exist, otherwise return false
     */
    public boolean checkFile() {
        return this.file.isFile();
    }

    public void addTempLine(String line) {
        tempLines.add(line);

    }
    public void writeTempFile() throws IOException{
        File tempFile = new File(saveLocation + "/temp.txt");
        if (!tempFile.createNewFile()){
            tempFile.delete();
            tempFile.createNewFile();
        }
        FileWriter fos = new FileWriter(tempFile.getPath());
        for (String line: tempLines){
            fos.write(line);
        }
        fos.close();
        tempLines.clear();
    }

    public String saveGameNewFile(String[][] playerData, int[] mainStates) throws IOException{
        LoadAccess load = new LoadFile(file);
        String[] allSaves = load.checkSaves();
        int numberOfFiles = allSaves.length;
        String path = saveLocation + "/save" + numberOfFiles +".txt";
        file = new File(path);
        return saveGame(playerData, mainStates);
    }
    /**
     * Given game data to save, save the information to txt file this.file.
     *
     * @param playerData each subarray of playerData represents a Player instance or Property instance
     *                   playerData should be ordered with a Player followed by the Properties they own
     *                   for each Player instance all values should be Strings:
     *                   index [0] name, [1] money, [2] booleanInJail ("true" or "false), [3] jailCards, [4] position
     *                   for each Property instance all values should be Strings:
     *                   index [0] name, [1] colour, [2] cost, [3] houseCost, [4] rent, [5] rent1H,
     *                   [6] rent2H, [7] rent3H, [8] rent4H, [9] rentHotel, [10] playerOwnerName,
     *                   [11] mortgageValue, [12] numHouses, [13] booleanMortgaged ("true" or "false")
     *
     * @param mainStates an Integer[] array of ints representing main states for Trees
     * @throws IOException in the case that there was an error writing the data in the sub arrays to the file
     */
    public String saveGame(String[][] playerData, int[] mainStates) throws IOException {

        FileWriter writer = new FileWriter(file.getPath());

        writer.write("playerStart\n");
        // loop through playerData and save each Player instance as a line
        saveArray(playerData, writer);
        writer.write("playerEnd\n");
        writer.write("treeStart\n");

        // save the Integer[] mainStates as a line
        StringBuilder newLine = new StringBuilder();
        for (int stateDatum : mainStates) {
            newLine.append(stateDatum).append(",");
        }
        newLine.deleteCharAt(newLine.length() - 1);
        writer.write(newLine + "\n");
        writer.write("treeEnd\n");

        writer.close();
        return file.getName();
    }

    /**
     * Loops through each subarray of data and stores it as a line in a txt file.
     *
     * @param data      a 2D array where each subarray stores information of an instance of an Entity to be saved
     * @param writer    a FileWriter instance with the save file
     * @throws IOException  in case the writer was unable to write to the file
     */
    private void saveArray(String[][] data, FileWriter writer) throws IOException {
        for (String[] datum : data) {
            StringBuilder newLine = new StringBuilder();
            for (String s : datum) {
                newLine.append(s).append(",");
            }
            newLine.deleteCharAt(newLine.length() - 1);
            writer.write(newLine + "\n");
        }
    }

}
