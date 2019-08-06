import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class RoomParse {



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

    public static boolean isEntrance(String in) {
        return Pattern.matches("BR|KT|DR|HL|CT|BL|ST|LB|LG", in);
    }

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
