package project;


import javax.naming.directory.InvalidAttributesException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Residential {

    //read all users from a file to an array
    public static void findPeopleHometown (String filename)throws IOException{
        ArrayList<String> identifiers = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
        String line;
        while ((line = br.readLine()) != null) {
            identifiers.add(line.trim());
        }
        for (String user:identifiers) {
            peopleBorn(user);
        }
    }

    //Prints out
    public static void peopleBorn (String user){
        if (UserMap.verifyUser(user)) {
            User userInitial = UserMap.get(user);
            String hometown = userInitial.getHome();
            ArrayList<User> results = new ArrayList<>();
            try {
                results = UserMap.findValue("birthplace", hometown);
            } catch (InvalidAttributesException e) {
                e.printStackTrace();
            }
            if (!results.isEmpty()) {
                System.out.println("User: " + user + "'s hometown is " + hometown);
                System.out.println("In the same town are born " + results.size() + " people");
                for (User u : results) {
                    if (u != null)
                        System.out.println(u.getName()+" "+u.getSurname()+ " " +u.getBirthplace()+ " " +u.getStudiedAt());
                }
                System.out.println("\n");
            }
        }
    }
}
