package project;

import java.util.ArrayList;

public class MovieProfiles {
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<String > movies = new ArrayList<>();

    public MovieProfiles(ArrayList<String> movies) {
        this.movies = movies;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void addUsers(User user) {
        this.users.add(user);
    }

    public ArrayList<String> getMovies() {
        movies.sort(String::compareToIgnoreCase);
        return movies;
    }

    public void setMovies(ArrayList<String> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Users: ");
        for (User u : users){
            result.append(u.getId() + ", ");
        }
        result.append(" Movies: " + movies.toString());
        return result.toString();
    }

}
