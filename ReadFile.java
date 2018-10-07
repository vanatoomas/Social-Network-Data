package dataStructures;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ReadFile {

    //Reads the given file, crates all users, returns list of users
    public static ArrayList<User> readUsers(String filename) throws IOException {
        String groupcode = "", id = "", name = "", surname = "", birthplace = "", home = "", gender = "";
        Date birthday = null;
        ArrayList<String> movies = new ArrayList<String>(),studiedAt = new ArrayList<String>(),workplaces = new ArrayList<String>();
        ArrayList<User> allUsers = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
        //Assuming that the order in People file is always the same, we skip fist line
        br.readLine();
        String line;
        while ((line = br.readLine()) != null){
            String[] pices = line.split(",");
            //Check every time if the string is not empty
            for (int i = 0; i < pices.length; i++) {
                if (!pices[i].equals("")){
                    if (i==0){
                        id = pices[0];
                    }
                    else if (i==1){
                        name = pices[1];
                    }
                    else if (i==2){
                        surname = pices[2];
                    }
                    else if (i==3){
                        birthday = convertDate(pices[3]);
                    }
                    else if (i==4){
                        gender = pices[4];
                    }
                    else if (i==5){
                        birthplace = pices[5];
                    }
                    else if (i==6){
                        home = pices[6];
                    }
                    else if (i==7){
                        String[] studylist = pices[7].split(";");
                        ArrayList<String> studylist1 = new ArrayList<String>(Arrays.asList(studylist));
                        studiedAt = studylist1;
                    }
                    else if (i==8){
                        String[] worklist = pices[8].split(";");
                        ArrayList<String> worklist1 = new ArrayList<String>(Arrays.asList(worklist));
                        workplaces = worklist1;
                    }else if (i==9){
                        String[] filmlist = pices[9].split(";");
                        ArrayList<String> filmlist1 = new ArrayList<String>(Arrays.asList(filmlist));
                        movies = filmlist1;
                    }
                    else if (i==10){
                        groupcode = pices[10];
                    }
                }
            }
            User user = (initializeUser(groupcode,id,name,surname,birthplace,home,gender, studiedAt, workplaces, movies, birthday));
            allUsers.add(user);
        }
        return allUsers;
    }

    /*On the social network: take the data from a person and add him/her to the network.
This function does not read any data from Console. This function receives the data as
parameters.
*/
    public static User initializeUser(String groupcode, String id, String name, String surname,
                               String birthplace, String home, String gender, ArrayList<String> studiedAt,
                               ArrayList<String> workplaces, ArrayList<String> movies, Date birthday){
        User user = new User(id,name,surname,birthday,gender,birthplace,home,studiedAt,
                workplaces,movies,groupcode);
        return user;
    }

    public static Date convertDate (String input){
        LocalDate midresult = null;
        String[] dateparts = input.split("-");
        if (input.length() == 10) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            midresult = LocalDate.parse(input, formatter);
        }
        else if(dateparts[0].length() == 1 && dateparts[1].length() == 1){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yyyy");
            midresult = LocalDate.parse(input, formatter);
        }
        else if(dateparts[0].length() == 1 && dateparts[1].length() == 2){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy");
            midresult = LocalDate.parse(input, formatter);
        }
        else if(dateparts[0].length() == 2 && dateparts[1].length() == 1){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-M-yyyy");
            midresult = LocalDate.parse(input, formatter);
        }
        Date result = Date.from(midresult.atStartOfDay(ZoneId.systemDefault()).toInstant());

        return result;
    }

    //Read friends file, every user has ArrayList of friends
    //setFriends takes input as only one friend not an array
    public static void readFriends(String filename, Map<String, User> users) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
        br.readLine();
        String line;
        while ((line = br.readLine()) != null){
            String[] pices = line.split(",");
            User first = users.get(pices[0]);
            User second = users.get(pices[1]);
            first.setFriends(second);
        }
    }
}
