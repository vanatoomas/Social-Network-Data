package project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//Uses depth first search to find the longest path between two users
public class longestChain {

    public static int DFS(User user,User end, ArrayList<User> visited){
        Map<User,Integer> distanceTo = new HashMap<>();
        int max = 0;
        visited.add(user);
        distanceTo.put(user, 0);
        ArrayList<User> friends = user.getFriendsUsers();
        if (user.equals(end)){
            return 0;
        }
        for(User u: friends){
            if (!visited.contains(u)){
                distanceTo.put(u,DFS(u,end,visited));
                if(distanceTo.get(u)>max){
                    max = distanceTo.get(u);
                }
            }
        }
        return max+1;
    }
}
