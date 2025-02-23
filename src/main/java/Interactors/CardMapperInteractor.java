package Interactors;

import java.util.List;

import Entities.Game.ActionTypeEnum;
import Entities.Game.Card;
import UseCases.CardMapperUseCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * This class maps out the game cards into a HashMap.
 */
public class CardMapperInteractor implements CardMapperUseCase {
    
    /**
     * This method puts all the jail cards in a hash map.
     * @param lst the list of cards before they are mapped
     * @return the list of jail cards map to the Card Entity
     */
    @Override
    public HashMap<String,List<Card>> cardMapperJailCards(List<String> lst) {
        List<Card> cards = new ArrayList<>();
        HashMap<String,List<Card>> jailCards = new HashMap<>();
        for (String s : lst) {
            String[] arr = s.split(":");
            String type = arr[0];
            if (Objects.equals(type, "jail")) {
                String action = arr[1];
                ActionTypeEnum actionType = ActionTypeEnum.valueOf(arr[2]);
                Integer amount = Integer.parseInt(arr[3]);
                Card card = new Card(type, action, actionType, amount);
                cards.add(card);
            }
        }
        jailCards.put("jail", cards);
        return jailCards;
    }

    /**
     * This method puts all the cards into a HashMap.
     * @param lst the list of cards before they are mapped
     * @return the list of chance cards map to the Card Entity
     */
    @Override
    public HashMap<String, List<Card>> cardMapperChanceCards(List<String> lst) {
        List<Card> cards = new ArrayList<>();
        HashMap<String,List<Card>> chanceCards = new HashMap<>();
        for (String s : lst) {
            String[] arr = s.split(":");
            String type = arr[0];
            if (Objects.equals(type, "chance")) {
                String action = arr[1];
                ActionTypeEnum actionType = ActionTypeEnum.valueOf(arr[2]);
                Integer amount = Integer.parseInt(arr[3]);
                Card card = new Card(type, action, actionType, amount);
                cards.add(card);
            }
        }
        chanceCards.put("chance", cards);
        return chanceCards;
    }

    /**
     * This method maps all the community chest cards into a HashMap.
     * @param lst the list of cards before they are mapped
     * @return the list of community chest cards map to the Card Entity
     */
    @Override
    public HashMap<String, List<Card>> cardMapperComChest(List<String> lst) {
        List<Card> cards = new ArrayList<>();
        HashMap<String,List<Card>> comChestCards = new HashMap<>();
        for (String s : lst) {
            String[] arr = s.split(":");
            String type = arr[0];
            if (Objects.equals(type, "comchest")) {
                String action = arr[1];
                ActionTypeEnum actionType = ActionTypeEnum.valueOf(arr[2]);
                Integer amount = Integer.parseInt(arr[3]);
                Card card = new Card(type, action, actionType, amount);
                cards.add(card);
            }
        }
        comChestCards.put("comchest", cards);
        return comChestCards;
    }
}
