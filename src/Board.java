/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/


import java.util.*;
import java.util.stream.Collectors;

// line 57 "model.ump"
// line 166 "model.ump"
public class Board {

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
                    "|  |  |  |  |  |  |  |DR|__|__|--|--|--|--|--|__|__|__|  |  |  |  |  |  |\n" +
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

    // - NULL
    //

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Board Attributes
    private final int nPlayers;

    //Board State Machines
    private Type currentTurn;

    private Turn turn;

    //Board Associations
    private Map<Type, Room> rooms;
    private Map<Type, Player> players;
    private Card[] solution;
    private Tile[][] board;

    public Board(int aNPlayers) {
        currentTurn = Type.MISS_SCARLETT;
        nPlayers = aNPlayers;

        generateRooms();
        generatePlayers();
        dealCards();
        board = RoomParse.makeRoom(room, rooms, players, aNPlayers);
        spawnPlayers();
    }

    private void spawnPlayers(){

    }

    public Tile[][] getBoard() {
        return board;
    }

    public Player getCurrentPlayer(){
        return players.get(currentTurn);
    }

    public Player getPlayer(Type type){
        return players.get(type);
    }

    public Room getRoom(Type type){
        return rooms.get(type);
    }


    public boolean processTurn(Turn turn){
        return turn.execute(this);
    }

    private void dealCards() {
        solution = new Card[3];

        //WEAPON, PLAYER, ROOM
        List<WeaponCard> weapons = Type.getTypes(Type.SubType.WEAPON).stream().map(WeaponCard::new).collect(Collectors.toList());
        List<PlayerCard> players = this.players.keySet().stream().map(PlayerCard::new).collect(Collectors.toList());
        List<RoomCard> rooms = Type.getTypes(Type.SubType.ROOM).stream().map(RoomCard::new).collect(Collectors.toList());

        Collections.shuffle(weapons);
        Collections.shuffle(players);
        Collections.shuffle(rooms);

        solution[0] = weapons.get(0);
        solution[1] = players.get(0);
        solution[2] = rooms.get(0);

        weapons.remove(0);
        players.remove(0);
        rooms.remove(0);

        List<Card> everything = new ArrayList<>();
        everything.addAll(weapons);
        everything.addAll(players);
        everything.addAll(rooms);

        Collections.shuffle(everything);

        List<Player> tempPlayers = new ArrayList<>(this.players.values());

        for (int i = 0; i < everything.size(); i++) {
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

    public Type getCurrentTurn() {
        return currentTurn;
    }

    //------------------------
    // INTERFACE
    //------------------------

    public int getNPlayers() {
        return nPlayers;
    }

    public String getTurnFullName() {
        String answer = turn.toString();
        return answer;
    }

    public Turn getTurn() {
        return turn;
    }

    /* Code from template association_GetMany */
    public Room getRoom(int index) {
        Room aRoom = rooms.get(index);
        return aRoom;
    }

    public Collection<Room> getRooms() {

        return Collections.unmodifiableCollection(rooms.values());
    }

    public int numberOfRooms() {
        int number = rooms.size();
        return number;
    }

    public boolean hasRooms() {
        boolean has = rooms.size() > 0;
        return has;
    }

    public int indexOfRoom(Room aRoom) {
//        int index = rooms.indexOf(aRoom);
        return 0;
    }

    /* Code from template association_GetMany */
//    public Card getSolution(int index) {
//        Card aSolution = solution.get(index);
//        return aSolution;
//    }
//
//    public List<Card> getSolution() {
//        List<Card> newSolution = Collections.unmodifiableList(solution);
//        return newSolution;
//    }
//
//    public int numberOfSolution() {
//        int number = solution.size();
//        return number;
//    }
//
//    public boolean hasSolution() {
//        boolean has = solution.size() > 0;
//        return has;
//    }
//
//    public int indexOfSolution(Card aSolution) {
//        int index = solution.indexOf(aSolution);
//        return index;
//    }

//    /* Code from template association_GetMany */
//    public Tile getBoard(int index) {
//        Tile aBoard = board.get(index);
//        return aBoard;
//    }
//
//    public List<Tile> getBoard() {
//        List<Tile> newBoard = Collections.unmodifiableList(board);
//        return newBoard;
//    }
//
//    public int numberOfBoard() {
//        int number = board.size();
//        return number;
//    }
//
//    public boolean hasBoard() {
//        boolean has = board.size() > 0;
//        return has;
//    }
//
//    public int indexOfBoard(Tile aBoard) {
//        int index = board.indexOf(aBoard);
//        return index;
//    }

    /* Code from template association_RequiredNumberOfMethod */
    public static int requiredNumberOfRooms() {
        return 9;
    }

    /* Code from template association_MinimumNumberOfMethod */
    public static int minimumNumberOfRooms() {
        return 9;
    }

    /* Code from template association_MaximumNumberOfMethod */
    public static int maximumNumberOfRooms() {
        return 9;
    }

    /* Code from template association_SetUnidirectionalN */
    public boolean setRooms(Room... newRooms) {
        boolean wasSet = false;
        ArrayList<Room> verifiedRooms = new ArrayList<Room>();
        for (Room aRoom : newRooms) {
            if (verifiedRooms.contains(aRoom)) {
                continue;
            }
            verifiedRooms.add(aRoom);
        }

        if (verifiedRooms.size() != newRooms.length || verifiedRooms.size() != requiredNumberOfRooms()) {
            return wasSet;
        }

        rooms.clear();
        verifiedRooms.forEach((r) -> rooms.put(r.getType(), r));
        wasSet = true;
        return wasSet;
    }

    /* Code from template association_RequiredNumberOfMethod */
    public static int requiredNumberOfSolution() {
        return 3;
    }

    /* Code from template association_MinimumNumberOfMethod */
    public static int minimumNumberOfSolution() {
        return 3;
    }

    /* Code from template association_MaximumNumberOfMethod */
    public static int maximumNumberOfSolution() {
        return 3;
    }

    /* Code from template association_SetUnidirectionalN */
//    public boolean setSolution(Card... newSolution) {
//        boolean wasSet = false;
//        ArrayList<Card> verifiedSolution = new ArrayList<Card>();
//        for (Card aSolution : newSolution) {
//            if (verifiedSolution.contains(aSolution)) {
//                continue;
//            }
//            verifiedSolution.add(aSolution);
//        }
//
//        if (verifiedSolution.size() != newSolution.length || verifiedSolution.size() != requiredNumberOfSolution()) {
//            return wasSet;
//        }
//
//        solution.clear();
//        solution.addAll(verifiedSolution);
//        wasSet = true;
//        return wasSet;
//    }

    /* Code from template association_RequiredNumberOfMethod */
    public static int requiredNumberOfBoard() {
        return 600;
    }

    /* Code from template association_MinimumNumberOfMethod */
    public static int minimumNumberOfBoard() {
        return 600;
    }

    /* Code from template association_MaximumNumberOfMethod */
    public static int maximumNumberOfBoard() {
        return 600;
    }

    /* Code from template association_SetUnidirectionalN */
//    public boolean setBoard(Tile... newBoard) {
//        boolean wasSet = false;
//        ArrayList<Tile> verifiedBoard = new ArrayList<Tile>();
//        for (Tile aBoard : newBoard) {
//            if (verifiedBoard.contains(aBoard)) {
//                continue;
//            }
//            verifiedBoard.add(aBoard);
//        }
//
//        if (verifiedBoard.size() != newBoard.length || verifiedBoard.size() != requiredNumberOfBoard()) {
//            return wasSet;
//        }
//
//        board.clear();
//        board.addAll(verifiedBoard);
//        wasSet = true;
//        return wasSet;
//    }

//    public void delete() {
//        rooms.clear();
//        solution.clear();
//        board.clear();
//    }

    // line 72 "model.ump"
    public void suggest(WeaponCard w, PlayerCard p, RoomCard r) {

    }

    // line 75 "model.ump"
    public boolean accuse(Player plantiff, WeaponCard w, PlayerCard p, RoomCard r) {
        return false;
        //TODO: DO
    }

    // line 80 "model.ump"
    public boolean move(Player p, int x, int y) {
        return false;
        //TODO: Do
    }


    public String toString() {
        StringBuilder ret = new StringBuilder();
        int begin = (int) 'A';

        for (int y = 0; y < board.length + 1; y++) {
            ret.append("|");
//            System.out.print("|");
            for (int x = 0; x < board[0].length + 1; x++) {
                if (y == 0) {
                    if (x != 0) {
                        ret.append(cellify(String.valueOf(x)));
//                        System.out.print(cellify(String.valueOf(x)));
                    } else {
                        ret.append("  |");
//                        System.out.print("  |");
                    }
                    continue;
                }
                if (x == 0 && y != 0) {
                    ret.append(cellify(String.valueOf((char) (begin + y - 1))));
//                    System.out.print(cellify(String.valueOf((char) (begin + y - 1))));
                    continue;
                }

            }
            if (y > 0){
                Tile[] row = board[y-1];
                ret.append(rowToString(row));
//                System.out.print(rowToString(row));
            }

            ret.append("\n");
//            System.out.print("\n");
        }

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
        String padding = " ";
        return padding.repeat(2 - text.length()) + text + "|";

    }
}