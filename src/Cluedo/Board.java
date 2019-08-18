package Cluedo;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/


import Cluedo.Exceptions.InvalidInputException;
import Cluedo.GameObjects.Player;
import Cluedo.GameObjects.Room;
import Cluedo.Helpers.Type;
import Cluedo.Moves.Turn;
import Cluedo.Tiles.Tile;
import Cluedo.Util.RoomParse;

import java.util.*;

// line 57 "model.ump"
// line 166 "model.ump"
public class Board {

    //This giant string is the hard coded room map which is then parsed into a 2d array of tile objects
    public static final String room =
                    "|--|--|--|--|--|--|--|--|--|WH|--|--|--|--|GR|--|--|--|--|--|--|--|--|--|\n" +
                    "|  |  |  |  |  |  |--|__|__|__|  |  |  |  |__|__|__|--|  |  |  |  |  |  |\n" +
                    "|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |\n" +
                    "|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |\n" +
                    "|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |  |__|__|CT|  |  |  |  |  |\n" +
                    "|  |  |  |  |  |  |__|__|BR|  |  |  |  |  |  |BR|__|__|__|  |  |  |  |--|\n" +
                    "|--|  |  |  |KT|  |__|__|  |  |  |  |  |  |  |  |__|__|__|__|__|__|__|PC|\n" +
                    "|__|__|__|__|__|__|__|__|  |BR|  |  |  |  |BR|  |__|__|__|__|__|__|__|--|\n" +
                    "|--|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|  |  |  |  |  |  |\n" +
                    "|  |  |  |  |  |__|__|__|__|__|__|__|__|__|__|__|__|__|BL|  |  |  |  |  |\n" +
                    "|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|  |  |  |  |  |  |\n" +
                    "|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|  |  |  |  |  |  |\n" +
                    "|  |  |  |  |  |  |  |DR|__|__|--|--|--|--|--|__|__|__|  |  |  |  |BL|  |\n" +
                    "|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|__|__|__|__|__|--|\n" +
                    "|  |  |  |  |  |  |  |  |__|__|--|--|--|--|--|__|__|__|  |  |LB|  |  |--|\n" +
                    "|  |  |  |  |  |  |DR|  |__|__|--|--|--|--|--|__|__|  |  |  |  |  |  |  |\n" +
                    "|--|__|__|__|__|__|__|__|__|__|--|--|--|--|--|__|__|LB|  |  |  |  |  |  |\n" +
                    "|MU|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|  |  |  |  |  |  |  |\n" +
                    "|--|__|__|__|__|__|__|__|__|  |  |HL|HL|  |  |__|__|__|  |  |  |  |  |--|\n" +
                    "|  |  |  |  |  |  |LG|__|__|  |  |  |  |  |  |__|__|__|__|__|__|__|__|PL|\n" +
                    "|  |  |  |  |  |  |  |__|__|  |  |  |  |  |HL|__|__|__|__|__|__|__|__|--|\n" +
                    "|  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |__|__|ST|  |  |  |  |  |  |\n" +
                    "|  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |\n" +
                    "|  |  |  |  |  |  |  |__|__|  |  |  |  |  |  |__|__|  |  |  |  |  |  |  |\n" +
                    "|  |  |  |  |  |  |--|SC|--|  |  |  |  |  |  |--|__|--|  |  |  |  |  |  |";

    //This giant string is the hard coded room map which is then parsed into a 2d array of tile objects
    public static final String room2 =
                    "|--|--|--|--|--|--|--|--|--|WH|--|--|--|--|GR|--|--|--|--|--|--|--|--|--|\n" +
                    "|kt|kt|kt|kt|kt|kt|--|__|__|__|  |  |  |  |__|__|__|--|ct|ct|ct|ct|ct|ct|\n" +
                    "|kt|kt|kt|kt|kt|kt|__|__|  |  |  |  |  |  |  |  |__|__|ct|ct|ct|ct|ct|ct|\n" +
                    "|kt|kt|kt|kt|kt|kt|__|__|  |  |  |  |  |  |  |  |__|__|ct|ct|ct|ct|ct|ct|\n" +
                    "|kt|kt|kt|kt|kt|kt|__|__|  |  |  |  |  |  |  |  |__|__|CT|ct|ct|ct|ct|ct|\n" +
                    "|kt|kt|kt|kt|kt|kt|__|__|BR|  |  |  |  |  |  |BR|__|__|__|ct|ct|ct|ct|--|\n" +
                    "|--|kt|kt|kt|KT|kt|__|__|  |  |  |  |  |  |  |  |__|__|__|__|__|__|__|PC|\n" +
                    "|__|__|__|__|__|__|__|__|  |BR|  |  |  |  |BR|  |__|__|__|__|__|__|__|--|\n" +
                    "|--|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|bl|bl|bl|bl|bl|bl|\n" +
                    "|dr|dr|dr|dr|dr|__|__|__|__|__|__|__|__|__|__|__|__|__|BL|bl|bl|bl|bl|bl|\n" +
                    "|dr|dr|dr|dr|dr|dr|dr|dr|__|__|--|--|--|--|--|__|__|__|bl|bl|bl|bl|bl|bl|\n" +
                    "|dr|dr|dr|dr|dr|dr|dr|dr|__|__|--|--|--|--|--|__|__|__|bl|bl|bl|bl|bl|bl|\n" +
                    "|dr|dr|dr|dr|dr|dr|dr|DR|__|__|--|--|--|--|--|__|__|__|bl|bl|bl|bl|bl|bl|\n" +
                    "|dr|dr|dr|dr|dr|dr|dr|dr|__|__|--|--|--|--|--|__|__|__|__|__|__|__|__|--|\n" +
                    "|dr|dr|dr|dr|dr|dr|dr|dr|__|__|--|--|--|--|--|__|__|__|lb|lb|LB|lb|lb|--|\n" +
                    "|dr|dr|dr|dr|dr|dr|DR|dr|__|__|--|--|--|--|--|__|__|lb|lb|lb|lb|lb|lb|lb|\n" +
                    "|--|__|__|__|__|__|__|__|__|__|--|--|--|--|--|__|__|LB|lb|lb|lb|lb|lb|lb|\n" +
                    "|MU|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|__|lb|lb|lb|lb|lb|lb|lb|\n" +
                    "|--|__|__|__|__|__|__|__|__|hl|hl|HL|HL|hl|hl|__|__|__|lb|lb|lb|lb|lb|--|\n" +
                    "|lg|lg|lg|lg|lg|lg|LG|__|__|hl|hl|hl|hl|hl|hl|__|__|__|__|__|__|__|__|PL|\n" +
                    "|lg|lg|lg|lg|lg|lg|lg|__|__|hl|hl|hl|hl|hl|HL|__|__|__|__|__|__|__|__|--|\n" +
                    "|lg|lg|lg|lg|lg|lg|lg|__|__|hl|hl|hl|hl|hl|hl|__|__|ST|st|st|st|st|st|st|\n" +
                    "|lg|lg|lg|lg|lg|lg|lg|__|__|hl|hl|hl|hl|hl|hl|__|__|st|st|st|st|st|st|st|\n" +
                    "|lg|lg|lg|lg|lg|lg|lg|__|__|hl|hl|hl|hl|hl|hl|__|__|st|st|st|st|st|st|st|\n" +
                    "|lg|lg|lg|lg|lg|lg|--|SC|--|hl|hl|hl|hl|hl|hl|--|__|--|st|st|st|st|st|st|";

    private final int nPlayers;
    private Type currentTurn; //Who's player is currently in turn
    private boolean hasWon;

    private Map<Type, Room> rooms;
    private Map<Type, Player> players;
    private Type[] solution;
    private Tile[][] board;

    public Board(int aNPlayers) {
        currentTurn = Type.MISS_SCARLETT;
        nPlayers = aNPlayers;

        generateRooms();
        generatePlayers();
        dealTypes(Type.getTypes(Type.SubType.PLAYER));
        board = RoomParse.makeRoom(room, rooms, players, aNPlayers);
    }

    public Board(List<Type> players){
        nPlayers = players.size();

        currentTurn = Type.MISS_SCARLETT;

        generateRooms();
        generatePlayers();
        dealTypes(players);
        board = RoomParse.makeRoom(room, rooms, this.players, nPlayers);
    }

    /**
     * Completes the current players turn and changes it to the next players.
     */
    public void completeTurn(){
        List<Type> types = Type.getTypes(Type.SubType.PLAYER);
        int index = types.indexOf(currentTurn) + 1;

        currentTurn = types.get(index % nPlayers);
    }

    /**
     * Sets the games solution
     * @param solution  The games solution to set
     */
    public void setSolution(Type[] solution) {
        this.solution = solution;
    }

    /**
     * Clears the hands of every player in play
     */
    public void clearHands(){
        for(Player p : players.values()){
            p.clearHand();
        }
    }

    /**
     * Gets the map of all the rooms
     * @return  The map of all the rooms
     */
    public Map<Type, Room> getRooms(){
        return rooms;
    }

    /**
     *  Gets the current tile board
     * @return The tile board
     */
    public Tile[][] getBoard() {
        return board;
    }

    /**
     * Gets the players whose turn it is currently
     * @return The player
     */
    public Player getCurrentPlayer(){
        return players.get(currentTurn);
    }

    /**
     * Gets a player specified by the type provided
     * @param type  The type of player we wish to get
     * @return      Returns the player that we wanted
     */
    public Player getPlayer(Type type){
        return players.get(type);
    }

    /**
     * Gets the solution to the game
     * @return  The solution to the game
     */
    public Type[] getSolution(){
        return this.solution;
    }

    /**
     * Processes a turn, this turn could be a move, suggestion or accusation. The turn executed is dependant on the turn object
     * passed onto the function.
     * @param turn  The turn in which to execute.
     * @return  Whether or not the turn was a valid turn or not.
     */
    public boolean processTurn(Turn turn){
        boolean access = turn.execute(this);
        return access;
    }

    /**
     * Gets a list of all players in play.
     * @return  The players in play
     */
    public List<Type> getPlayerTypes(){
        return new ArrayList<>(players.keySet());
    }

    /**
     * Gets a list of all player instances that are in play
     * @return  as an ArrayList
     */
    public List<Player> getPlayerInstances(){return new ArrayList<>(players.values());}

    /**
     * Completes the game!
     */
    public void completeGame(){
        hasWon = true;
    }

    /**
     * Returns whether or not the game has been won
     * @return  Has the game been won?
     */
    public boolean hasWon() {
        return hasWon;
    }

    /**
     * Gives a player a certain type in their hand
     * @param player    The player to give the type to
     * @param type      The type to give them
     */
    public void givePlayerType(Type player, Type type){
        players.get(player).addHand(type);
    }

    /**
     * Checks if a certain player is in the game
     * @param player    The player to check for
     * @return          Whether or not that player is in play
     */
    public boolean hasPlayer(Type player){
        return players.containsKey(player);
    }

    /**
     * Deals out all of the types to their correct places, three random one as the solution and the rest evenly distributed to each player
     */
    private void dealTypes(List<Type> players) {
        solution = new Type[3];

        //WEAPON, PLAYER, ROOM
        //Gets all of the weapons, players and rooms in play
        List<Type> weapons = Type.getTypes(Type.SubType.WEAPON);
        List<Type> rooms = Type.getTypes(Type.SubType.ROOM);

        //Shuffle them all
        Collections.shuffle(weapons);
        Collections.shuffle(players);
        Collections.shuffle(rooms);

        //Randomly select a solution
        solution[0] = weapons.get(0);
        solution[1] = players.get(0);
        solution[2] = rooms.get(0);

        //Solution types should not be included in the distribution of types
        weapons.remove(0);
        players.remove(0);
        rooms.remove(0);

        //Combine everything together and then shuffle
        List<Type> everything = new ArrayList<>();
        everything.addAll(weapons);
        everything.addAll(players);
        everything.addAll(rooms);

        Collections.shuffle(everything);

        List<Player> tempPlayers = new ArrayList<>(this.players.values());

        for (int i = 0; i < everything.size(); i++) {   //And finally we evenly distribute all of the types
            tempPlayers.get(i % nPlayers).addHand(everything.get(i));
        }
    }

    /**
     * Generates all of the players required
     */
    private void generatePlayers() {
        players = new HashMap<>();
        List<Type> tempPlayers = Type.getTypes(Type.SubType.PLAYER);
        for (int i = 0; i < nPlayers; i++) {
            players.put(tempPlayers.get(i), new Player(tempPlayers.get(i)));
        }
    }

    /**
     * Generates all of the required rooms
     */
    private void generateRooms() {
        rooms = new HashMap<>();
        for (Type type : Type.getTypes(Type.SubType.ROOM)) {
            rooms.put(type, new Room(type));
        }
    }

    /**
     * Gets the type of player whose current turn it is
     * @return
     */
    public Type getCurrentTurn() {
        return currentTurn;
    }


    /**
     * Returns the string for just the board output
     * @return  Just the board output
     */
    public String justBoard(){
        StringBuilder ret = new StringBuilder();
        int begin = 'A';

        for (int y = 0; y < board.length + 1; y++) {
            ret.append("|");
            for (int x = 0; x < board[0].length + 1; x++) {
                if (y == 0) {
                    if (x != 0) {
                        ret.append(cellify(String.valueOf(x)));
                    } else {
                        ret.append("  |");
                    }
                    continue;
                }
                if (x == 0) {
                    ret.append(cellify(String.valueOf((char) (begin + y - 1))));
                }

            }
            if (y > 0){
                Tile[] row = board[y-1];
                ret.append(rowToString(row));
            }

            ret.append("\n");
        }

        return ret.toString();
    }

    /**
     * Prints out and returns the board as a string!
     * @return The board as a string
     */
    public String toString() {

        StringBuilder ret = new StringBuilder(justBoard());

        for (Room room : rooms.values()) {
            if (!room.isEmpty()){
                ret.append(room.toString());
            }
        }

        return ret.toString();
    }

    private static String rowToString(Tile[] row){
        StringBuilder str = new StringBuilder();
        for (Tile tile : row) {
            str.append(cellify(tile.toString()));
        }

        return str.toString();
    }

    private static String cellify(String text) {
        if (text.length() > 2) {
            throw new InvalidInputException();
        }

        String out;
        if (text.length() == 2){
            out = text + "|";
        } else if (text.length() == 1){
            out = " " + text + "|";
        } else {
            out = "  |";
        }
        //FIXME ecs systems get can't find the repeat method?
//        String padding = " ";
//        String out = padding.repeat(2 - text.length()) + text + "|";
        return out;
    }
}