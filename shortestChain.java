package project;
import java.util.*;

//This class uses Breadth First search to find shortest
//path between two users.
public class shortestChain {

    public static int BFS(User start, User end){
        LinkedList<User> queue = new LinkedList<>();
        ArrayList<User> visited = new ArrayList<>();
        Map<User,Integer> distanceTo = new HashMap<>();
        visited.add(start);
        distanceTo.put(start, 0);
        queue.addLast(start);

        while(!queue.isEmpty()){
            User user = queue.removeFirst();
            ArrayList<User> friends = user.getFriendsUsers();
            for(User u: friends){
                if (!visited.contains(u)){
                    visited.add(u);
                    distanceTo.put(u, distanceTo.get(user)+1);
                    queue.addLast(u);
                    if(u.getId().equals(end.getId())){
                        return distanceTo.get(u);
                    }
                }
            }
        }
        return 0;
    }
}
