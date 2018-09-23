import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides a shortestPath method for finding routes between two points
 * on the map. Start by using Dijkstra's, and if your code isn't fast enough for your
 * satisfaction (or the autograder), upgrade your implementation by switching it to A*.
 * Your code will probably not be fast enough to pass the autograder unless you use A*.
 * The difference between A* and Dijkstra's is only a couple of lines of code, and boils
 * down to the priority you use to order your vertices.
 */
public class Router {
    /**
     * Return a List of longs representing the shortest path from the node
     * closest to a start location and the node closest to the destination
     * location.
     * @param g The graph to use.
     * @param stlon The longitude of the start location.
     * @param stlat The latitude of the start location.
     * @param destlon The longitude of the destination location.
     * @param destlat The latitude of the destination location.
     * @return A list of node id's in the order visited on the shortest path.
     */

    // Bad Approach
  /*  static class Node{
        GraphDB.Vertex v;
        double distance;
        Node prev;
        double expectDistance;
        Node(GraphDB.Vertex v, double dis, Node prev, double expectDistance){
            this.v = v;
            this.distance = dis;
            this.prev = prev;
            this.expectDistance = expectDistance;
        }
    }

    public static List<Long> shortestPath(GraphDB g, double stlon, double stlat,
                                          double destlon, double destlat) {
        GraphDB.Vertex s = g.vertex.get(g.closest(stlon,stlat));
        GraphDB.Vertex t = g.vertex.get(g.closest(destlon,destlat));
        Comparator<Node> cmp = new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                 if(o1.distance + o1.expectDistance < o2.distance + o2.expectDistance){
                     return -1;
                 }else if(o1.distance + o1.expectDistance == o2.distance + o2.expectDistance){
                     return 0;
                 }else{
                     return 1;
                 }
            }
        };
        PriorityQueue<Node> pq = new PriorityQueue(cmp);

        pq.add(new Node(s,0, null, g.distance(s.id,t.id)));
        Node n = pq.poll();

        while(n.v.id != t.id){
            for (long id:g.adjacent(n.v.id)){
                if(n.prev != null){
                    if(id == n.prev.v.id){
                        continue;
                    }
                }
                GraphDB.Vertex v = g.vertex.get(id);
                pq.add(new Node(v,n.distance + g.distance(id,n.v.id),n, g.distance(id,t.id)));
            }
            n = pq.poll();
        }
        List<Long> a = new ArrayList<>();
        Stack<Long> temp = new Stack<>();
        while(n != null){
            temp.push(n.v.id);
            n = n.prev;
        }
        while(!temp.empty()){
            a.add(temp.pop());
        }
        return a;
    }
*/


    public static List<Long> shortestPath(GraphDB g, double stlon, double stlat,
                                          double destlon, double destlat){
        GraphDB.Vertex s = g.vertex.get(g.closest(stlon,stlat));
        GraphDB.Vertex t = g.vertex.get(g.closest(destlon,destlat));
        Iterator<Long> i = g.vertex.keySet().iterator();
        Map<Long,Double> passedDistance = new HashMap<>();
        Map<Long,Double> estimateDistance = new HashMap<>();
        List<Long> returnList = new ArrayList<>();
        while(i.hasNext()){
            Long vID = i.next();
            passedDistance.put(vID,Double.POSITIVE_INFINITY);
            estimateDistance.put(vID,g.distance(vID,t.id));
        }
        Map<Long,Long> edgeTo = new HashMap<>();
        Set<Long> marked = new HashSet<>();
        Comparator<Long> cmp = new Comparator<Long>() {
            @Override
            public int compare(Long o1, Long o2) {
                if(passedDistance.get(o1) + estimateDistance.get(o1) <
                        passedDistance.get(o2) + estimateDistance.get(o2)){
                    return -1;
                }else if (passedDistance.get(o1) + estimateDistance.get(o1) ==
                        passedDistance.get(o2) + estimateDistance.get(o2)){
                    return  0;
                }else{
                    return 1;
                }
            }
        };
        PriorityQueue<Long> pq = new PriorityQueue<Long>(cmp);

        passedDistance.replace(s.id,0.0);
        pq.add(s.id);
        long vID = pq.poll();
        marked.add(vID);

        while(vID != t.id){
            for (long wID : g.adjacent(vID)){
                if(passedDistance.get(vID) + g.distance(vID,wID) < passedDistance.get(wID)){
                    passedDistance.replace(wID,passedDistance.get(vID) + g.distance(wID,vID));
                    edgeTo.put(wID,vID);
                }
                pq.add(wID);
            }
            vID = pq.poll();
            while(marked.contains(vID)){
                vID = pq.poll();
            }
            marked.add(vID);
        }
        Stack<Long> stack = new Stack<>();

        Long id = t.id;
        stack.add(id);
        while(id != s.id){
            stack.add(edgeTo.get(id));
            id = edgeTo.get(id);
        }
        while(!stack.empty()){
            returnList.add(stack.pop());
        }
        return returnList;
    }



    /**
     * Create the list of directions corresponding to a route on the graph.
     * @param g The graph to use.
     * @param route The route to translate into directions. Each element
     *              corresponds to a node from the graph in the route.
     * @return A list of NavigatiionDirection objects corresponding to the input
     * route.
     */
    public static List<NavigationDirection> routeDirections(GraphDB g, List<Long> route) {
        return null;
    }

    /**
     * Class to represent a navigation direction, which consists of 3 attributes:
     * a direction to go, a way, and the distance to travel for.
     */
    public static class NavigationDirection {

        /** Integer constants representing directions. */
        public static final int START = 0;
        public static final int STRAIGHT = 1;
        public static final int SLIGHT_LEFT = 2;
        public static final int SLIGHT_RIGHT = 3;
        public static final int RIGHT = 4;
        public static final int LEFT = 5;
        public static final int SHARP_LEFT = 6;
        public static final int SHARP_RIGHT = 7;

        /** Number of directions supported. */
        public static final int NUM_DIRECTIONS = 8;

        /** A mapping of integer values to directions.*/
        public static final String[] DIRECTIONS = new String[NUM_DIRECTIONS];

        /** Default name for an unknown way. */
        public static final String UNKNOWN_ROAD = "unknown road";
        
        /** Static initializer. */
        static {
            DIRECTIONS[START] = "Start";
            DIRECTIONS[STRAIGHT] = "Go straight";
            DIRECTIONS[SLIGHT_LEFT] = "Slight left";
            DIRECTIONS[SLIGHT_RIGHT] = "Slight right";
            DIRECTIONS[LEFT] = "Turn left";
            DIRECTIONS[RIGHT] = "Turn right";
            DIRECTIONS[SHARP_LEFT] = "Sharp left";
            DIRECTIONS[SHARP_RIGHT] = "Sharp right";
        }

        /** The direction a given NavigationDirection represents.*/
        int direction;
        /** The name of the way I represent. */
        String way;
        /** The distance along this way I represent. */
        double distance;

        /**
         * Create a default, anonymous NavigationDirection.
         */
        public NavigationDirection() {
            this.direction = STRAIGHT;
            this.way = UNKNOWN_ROAD;
            this.distance = 0.0;
        }

        public String toString() {
            return String.format("%s on %s and continue for %.3f miles.",
                    DIRECTIONS[direction], way, distance);
        }

        /**
         * Takes the string representation of a navigation direction and converts it into
         * a Navigation Direction object.
         * @param dirAsString The string representation of the NavigationDirection.
         * @return A NavigationDirection object representing the input string.
         */
        public static NavigationDirection fromString(String dirAsString) {
            String regex = "([a-zA-Z\\s]+) on ([\\w\\s]*) and continue for ([0-9\\.]+) miles\\.";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(dirAsString);
            NavigationDirection nd = new NavigationDirection();
            if (m.matches()) {
                String direction = m.group(1);
                if (direction.equals("Start")) {
                    nd.direction = NavigationDirection.START;
                } else if (direction.equals("Go straight")) {
                    nd.direction = NavigationDirection.STRAIGHT;
                } else if (direction.equals("Slight left")) {
                    nd.direction = NavigationDirection.SLIGHT_LEFT;
                } else if (direction.equals("Slight right")) {
                    nd.direction = NavigationDirection.SLIGHT_RIGHT;
                } else if (direction.equals("Turn right")) {
                    nd.direction = NavigationDirection.RIGHT;
                } else if (direction.equals("Turn left")) {
                    nd.direction = NavigationDirection.LEFT;
                } else if (direction.equals("Sharp left")) {
                    nd.direction = NavigationDirection.SHARP_LEFT;
                } else if (direction.equals("Sharp right")) {
                    nd.direction = NavigationDirection.SHARP_RIGHT;
                } else {
                    return null;
                }

                nd.way = m.group(2);
                try {
                    nd.distance = Double.parseDouble(m.group(3));
                } catch (NumberFormatException e) {
                    return null;
                }
                return nd;
            } else {
                // not a valid nd
                return null;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof NavigationDirection) {
                return direction == ((NavigationDirection) o).direction
                    && way.equals(((NavigationDirection) o).way)
                    && distance == ((NavigationDirection) o).distance;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(direction, way, distance);
        }
    }
}
