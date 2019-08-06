/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/


import java.util.*;

// line 110 "model.ump"
// line 199 "model.ump"
public class Player extends Entity {

    //------------------------
    // ENUMERATIONS
    //------------------------


    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Player Attributes
    private boolean isInPlay = true;

    //Player Associations
    private List<Card> hand;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Player(Type aType) {
        super(aType);
        hand = new ArrayList<Card>();
    }

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setIsInPlay(boolean aIsInPlay) {
        boolean wasSet = false;
        isInPlay = aIsInPlay;
        wasSet = true;
        return wasSet;
    }

    public boolean getIsInPlay() {
        return isInPlay;
    }

    /* Code from template association_GetMany */
    public Card getHand(int index) {
        Card aHand = hand.get(index);
        return aHand;
    }

    public List<Card> getHand() {
        List<Card> newHand = Collections.unmodifiableList(hand);
        return newHand;
    }

    public int numberOfHand() {
        int number = hand.size();
        return number;
    }

    public boolean hasHand() {
        boolean has = hand.size() > 0;
        return has;
    }

    public int indexOfHand(Card aHand) {
        int index = hand.indexOf(aHand);
        return index;
    }

    /* Code from template association_MinimumNumberOfMethod */
    public static int minimumNumberOfHand() {
        return 0;
    }

    /* Code from template association_AddUnidirectionalMany */
    public boolean addHand(Card aHand) {
        boolean wasAdded = false;
        if (hand.contains(aHand)) {
            return false;
        }
        hand.add(aHand);
        wasAdded = true;
        return wasAdded;
    }

    public boolean removeHand(Card aHand) {
        boolean wasRemoved = false;
        if (hand.contains(aHand)) {
            hand.remove(aHand);
            wasRemoved = true;
        }
        return wasRemoved;
    }

    /* Code from template association_AddIndexControlFunctions */
    public boolean addHandAt(Card aHand, int index) {
        boolean wasAdded = false;
        if (addHand(aHand)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfHand()) {
                index = numberOfHand() - 1;
            }
            hand.remove(aHand);
            hand.add(index, aHand);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMoveHandAt(Card aHand, int index) {
        boolean wasAdded = false;
        if (hand.contains(aHand)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfHand()) {
                index = numberOfHand() - 1;
            }
            hand.remove(aHand);
            hand.add(index, aHand);
            wasAdded = true;
        } else {
            wasAdded = addHandAt(aHand, index);
        }
        return wasAdded;
    }

    public void delete() {
        hand.clear();
        super.delete();
    }

    // line 115 "model.ump"
    public Type getType() {
        return type;
    }


    public String toString() {
        return super.toString() + "[" +
                "isInPlay" + ":" + getIsInPlay() + "]";
    }
}