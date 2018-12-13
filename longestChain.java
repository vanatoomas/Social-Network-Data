package project;

import java.util.ArrayList;

//Uses depth first search to find the longest path between two users
public class longestChain {

    static int max = 0;
    public static int findLongest (User start, User end){
        FriendNode startNode = new FriendNode(start);
        backTrackDFS(startNode,end);
        return max;
    }

    public static void backTrackDFS (FriendNode start, User end){
        if(start.getUser().equals(end)){
            if (start.getLength() > max){
                max = start.getLength();
            }
        }
        else {
            ArrayList<FriendNode> suitableFriends = compute(start);
            while (!suitableFriends.isEmpty()) {
                FriendNode node = suitableFriends.get(0);
                suitableFriends.remove(node);
                backTrackDFS(node, end);
            }
        }
    }

    //Finds all suitable friends, suitable friends mean that user has not occurred
    //in the chain before
    public static ArrayList<FriendNode> compute(FriendNode start){
        ArrayList<User> friends = start.getUser().getFriendsUsers();
        ArrayList<User> prevFriends = new ArrayList<>();
        ArrayList<FriendNode> friendNodes = new ArrayList<>();
        FriendNode iterate = start;
        //Find all the users already contained in the chain
        while (iterate.getPrevious() != null){
            prevFriends.add(iterate.getUser());
            iterate = iterate.getPrevious();
        }
        if(iterate != null){
            prevFriends.add(iterate.getUser());
        }
        //Finding all the friends of user and making friendnodes for them
        //if they have not occurred before
        for (User u:friends) {
            if (!prevFriends.contains(u)) {
                FriendNode uNode = new FriendNode(u);
                uNode.setPrevious(start);
                uNode.setLength(prevFriends.size());
                friendNodes.add(uNode);
            }
        }
        return friendNodes;
    }

    public static class FriendNode{
    private User user;
    private FriendNode previous;
    private int length = 0;

   public FriendNode(User user) {
        this.user = user;
    }

        public User getUser() {
            return user;
        }

        public FriendNode getPrevious() {
            return previous;
        }

        public void setPrevious(FriendNode previous) {
            this.previous = previous;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }
    }
}
