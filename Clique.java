package project;

import java.util.ArrayList;
import java.util.Objects;

public class Clique {
    private ArrayList<User> users = new ArrayList<>();


    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public void addUser(User user){
        users.add(user);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Clique clique = (Clique) o;
        for(User u: clique.getUsers()){
            if(!users.contains(u)){
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        ArrayList<User> hashUsers = User.sortArrayListofUserbyId(users);
        return Objects.hash(hashUsers);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Clique size: " + users.size() + " Users: ");
        for (User u: users){
            sb.append(u.getId() + " ");
        }
        return sb.toString();
    }
}
