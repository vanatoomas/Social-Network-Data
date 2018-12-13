package project;

import java.util.ArrayList;

public class findCliques {

    static ArrayList<Clique> allCliques = new ArrayList<>();


    public static ArrayList<Clique> findCliq (User user){
        cliNode userNode = new cliNode(user);
        finder(userNode);
        return allCliques;
    }

    public static void finder(cliNode user){
        if(user.getLevel() > 4){
            cliNode iterator = user;
            Clique clique = new Clique();
            while (iterator.previous != null){
                clique.addUser(iterator.getUser());
                iterator = iterator.getPrevious();
            }
            if (iterator != null){
                clique.addUser(iterator.getUser());
            }
            allCliques.add(clique);
        }
        ArrayList<cliNode> suitable = findSuitable(user);
        while (!suitable.isEmpty()){
            cliNode node = suitable.get(0);
            suitable.remove(0);
            finder(node);
        }
    }

    //Finds all users that are friends with all previously occurred users
    public static ArrayList<cliNode> findSuitable(cliNode user){
        ArrayList<User> friends = user.getUser().getFriendsUsers();
        ArrayList<User> prevFriends = new ArrayList<>();
        ArrayList<cliNode> friendNodes = new ArrayList<>();
        cliNode iterate = user;
        //Find all the users already contained in the chain
        while (iterate.getPrevious() != null){
            prevFriends.add(iterate.getUser());
            iterate = iterate.getPrevious();
        }
        if(iterate != null){
            prevFriends.add(iterate.getUser());
        }
        //Finding all the friends of user and making cliNodes for them
        //if they have not occurred before and if they are friends with
        //all of the people occurred previously
        for (User u:friends) {
            boolean isFriend = true;
            if (!prevFriends.contains(u)) {
                ArrayList<User> friendsOfu = u.getFriendsUsers();
                //Checking every previously occurred user and given user
                for (User prev: prevFriends){
                    if (!friendsOfu.contains(prev)){
                        isFriend = false;
                    }
                }
                //Create a new cliNode since user belongs to the clique
                if (isFriend){
                    cliNode uNode = new cliNode(u);
                    uNode.setPrevious(user);
                    uNode.setLevel(prevFriends.size()+1);
                    friendNodes.add(uNode);
                }
            }
        }
        return friendNodes;
    }

    public static class cliNode{
        private User user;
        private int level = 1;
        private cliNode previous;

        public cliNode(User user) {
            this.user = user;
        }

        public User getUser() {
            return user;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public cliNode getPrevious() {
            return previous;
        }

        public void setPrevious(cliNode previous) {
            this.previous = previous;
        }
    }
}
