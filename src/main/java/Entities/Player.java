package Entities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.Map;

public class Player {
    // Represents a player in the game

    public String name;
    public int money;
    public ArrayList<Property> properties;
    public boolean inJail;
    public int jailCards;
    public int position;
    // public Map<Integer, Integer> actions;

    public Player(String name){
        this.name = name;
        this.money = 1500;
        this.properties = new ArrayList<Property>();
        this.inJail = false;
        this.jailCards = 0;
        this.position = 0;
    }

    public String trade(Player tradee, int money, ArrayList<Property> properties, int jailcards){
        if (money > this.money) {
            return "Inadequate amount of money";
        } else {
            this.money -= money;
            tradee.money += money;
            tradee.properties.addAll(properties);
            this.properties.removeAll(properties);
            this.jailCards -= jailcards;
            tradee.jailCards += jailCards;
            return "Trade successful";
        }
    }

    public void changeJailStatus(Player player){
        this.inJail = !this.inJail;
    }

    // public getPossibleActions(Player player){}

    // public auction(Property property){}

    public String steal(Player victim){
        double success = Math.random();
        if (success <= 0.3) {
            this.money += 100;
            victim.money -= 100;
            return this.name + " stole money from " + victim.name;
        } else {
            System.out.println("The police are looking for " + this.name);
            double jail = Math.random();
            if (jail <= 0.6) {
                this.inJail = true;
                return this.name + " is put in jail";
            } else {
                return this.name + " escaped from the police";
            }
        }
    }

    public void save() throws IOException {
        File savefile = new File("Save.txt");

        FileWriter fw = new FileWriter(savefile);

        PrintWriter pw = new PrintWriter(fw);
        pw.println(name);
        pw.println(money);
        pw.println(inJail);
        pw.println(jailCards);
        pw.println(position);
        pw.println(properties);
        pw.println();
        pw.close();
    }

    // public void load(){}

}
