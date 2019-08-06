import java.util.*;

public class PathfindingUtil {

    /**
     * Checks whether or not there is a valid path between start and goal
     * @param board     The board in it's current state
     * @param start     The start position
     * @param goal      The goal position
     * @return          Whether or not there is a path between the two points or not
     */
    public static boolean findPath(Board board, Position start, Position goal){
        Set<Node> visitedNodes = new HashSet<>();
        Queue<Node> frontier = new PriorityQueue<>();
        Map<Position, Node> mappings = new HashMap<>();

        Tile[][] tiles = board.getBoard();

        //First add everything to the frontier queue
        for(int y = 0; y < tiles.length; y++){
            for(int x = 0; x < tiles[0].length; x++){
                if(tiles[y][x] instanceof EmptyTile || tiles[y][x] instanceof RoomTile){
                    Node node;
                    if(tiles[y][x].getPosition().equals(start)){
                        node = new Node(tiles[y][x], 0);
                    }else{
                        node = new Node(tiles[y][x], 10000);
                    }

                    frontier.offer(node);
                    mappings.put(tiles[y][x].getPosition(), node);
                }
            }
        }

        while(!frontier.isEmpty()){
            Node node = frontier.poll();
            visitedNodes.add(node);

            if(node.getTile().getPosition().equals(goal)){
                return true;
            }

            Node[] neighbours = getNeighbours(node, mappings);

            for(int i = 0; i < 4; i++){
                Node childNode = neighbours[i];
                if(childNode == null || visitedNodes.contains(childNode)) continue;

                childNode.setDistanceToRoot(node.distanceToRoot + 1);
                frontier.offer(childNode);
            }
        }

        return false;
    }

    /**
     * Gets all of the neighbours of a given node
     * @param node      The node to get neighbours of
     * @param mappings  The node mappings
     * @return          The list of nodes
     */
    private static Node[] getNeighbours(Node node, Map<Position, Node> mappings){
        //UP, RIGHT, DOWN, LEFT
        Position position = node.getTile().getPosition();

        Node[] neighbours = new Node[4];

        //UP
        if(mappings.containsKey(position.add(0, -1))) neighbours[0] = mappings.get(position.add(0, -1));

        //RIGHT
        if(mappings.containsKey(position.add(1, 0))) neighbours[1] = mappings.get(position.add(1, 0));

        //DOWN
        if(mappings.containsKey(position.add(0, 1))) neighbours[2] = mappings.get(position.add(0, 1));

        //LEFT
        if(mappings.containsKey(position.add(-1, 0))) neighbours[3] = mappings.get(position.add(-1, 0));

        return neighbours;
    }

    private static class Node implements Comparable<Node>{
        private Tile tile;
        private int distanceToRoot;

        public Node(Tile tile, int distanceToRoot) {
            this.tile = tile;
            this.distanceToRoot = distanceToRoot;
        }


        public Tile getTile() {
            return tile;
        }

        public void setTile(Tile tile) {
            this.tile = tile;
        }


        public int getDistanceToRoot() {
            return distanceToRoot;
        }

        public void setDistanceToRoot(int distanceToRoot) {
            this.distanceToRoot = distanceToRoot;
        }

        @Override
        public int compareTo(Node node) {
            if(node.getDistanceToRoot() == this.distanceToRoot) return 0;
            return node.getDistanceToRoot() < this.distanceToRoot ? 1 : -1;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "tile=" + tile +
                    ", distanceToRoot=" + distanceToRoot +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return Objects.equals(tile, node.tile);
        }

        @Override
        public int hashCode() {
            return Objects.hash(tile);
        }
    }
}
