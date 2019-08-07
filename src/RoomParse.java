import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class RoomParse {


    /**
     * Will return a 2d array of tiles object from a string defining the map
     * @param roomIn the map
     * @param rooms rooms available
     * @param players players available
     * @param nPlayers how many players should actually be made on the map
     * @return the 2d array of tiles
     */
    public static Tile[][] makeRoom(String roomIn, Map<Type, Room> rooms, Map<Type, Player> players, int nPlayers) {
        int playersMade = 0;

        //NB: Y X
        Tile[][] board = new Tile[25][24];
        Scanner scan = new Scanner(roomIn);
        scan.useDelimiter("\\|\\n|\\|");

//        int i = 0;
        int x = 0;
        int y = 0;
        while (scan.hasNext()) {
            String next = scan.next();
            if (x >= board[0].length){
                y++;
                x = 0;
                continue;
            }
            board[y][x] = makeTile(next, x, y, rooms, players);

            if (playersMade >= nPlayers && board[y][x] != null && board[y][x] instanceof EmptyTile){
                Tile tile = board[y][x];
                ((EmptyTile)(tile)).setPlayer(null);
            }

            //increment number of players on the board
            if (board[y][x] != null && board[y][x].isPlayer()) {
                playersMade++;
            }


            x++;
        }

        return board;
    }

    /**
     * Will return a Tile object from a string
     * @param in the string
     * @param x position in x dimension
     * @param y position in y dimension
     * @param rooms map of the rooms available
     * @param players map of the players available
     * @return the new tile object
     */
    public static Tile makeTile(String in, int x, int y, Map<Type, Room> rooms, Map<Type, Player> players) {
        if (in.length() > 2) {
            System.out.println("Length greater than 2 in makeTile:" + in + ":");
            throw new InvalidInputException();
        }

        Position pos = new Position(x, y);

        if (isEntrance(in)) {
            return new RoomTile(pos, roomFromString(in, rooms));
        } else if (isPlayer(in)) {
            return new EmptyTile(pos, playerFromString(in, players));
        } else if (in.equals("__")){
            return new EmptyTile(pos, null);
        } else if (in.equals("  ") | in.equals("--")){
            return new VanityTile(pos, in);
        } else if (in.equals("\n")){
            return new VanityTile(pos, "A");
        }

        System.out.println("Invalid parameters provided to makeTile! :" + in + ":");
        throw new InvalidInputException();
    }

    /**
     * performs regex to check whether the given is a room
     * @param in the string to check
     * @return whether the string is a room or not
     */
    public static boolean isEntrance(String in) {
        return Pattern.matches("BR|KT|DR|HL|CT|BL|ST|LB|LG", in);
    }

    /**
     * performs regex to check whether the given is a player
     * @param in the string to check
     * @return whether the string is a player or not
     */
    public static boolean isPlayer(String in) {
        return Pattern.matches("WH|GR|PC|PL|MU|SC", in);
    }

    /**
     * needs to be thrown in a util class
     *
     * @param in the string being read in
     * @return the player being returned
     */
    public static Player playerFromString(String in, Map<Type, Player> players) {
        switch (in) {
            case "WH":
                return players.get(Type.MRS_WHITE);
            case "GR":
                return players.get(Type.MR_GREEN);
            case "PC":
                return players.get(Type.MRS_PEACOCK);
            case "PL":
                return players.get(Type.PROF_PLUM);
            case "MU":
                return players.get(Type.COL_MUSTARD);
            case "SC":
                return players.get(Type.MISS_SCARLETT);
            default:
                throw new InvalidInputException();
        }
    }


    /**
     * needs to be thrown in a util class
     *
     * @param in the string being read in
     * @return the room being returned
     */
    public static Room roomFromString(String in, Map<Type, Room> rooms) {
        switch (in) {
            case "BR":
                return rooms.get(Type.BALL_ROOM);
            case "KT":
                return rooms.get(Type.KITCHEN);
            case "DR":
                return rooms.get(Type.DINING_ROOM);
            case "LG":
                return rooms.get(Type.LOUNGE);
            case "HL":
                return rooms.get(Type.HALL);
            case "CT":
                return rooms.get(Type.CONSERVATORY);
            case "BL":
                return rooms.get(Type.BILLARD_ROOM);
            case "ST":
                return rooms.get(Type.STUDY);
            case "LB":
                return rooms.get(Type.LIBRARY);
            default:
                throw new InvalidInputException();
        }

    }
}
