import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

//Clique by group G611837
public class Clique {

	public static void main(String[] args) throws FileNotFoundException {
		String[] ids = { "Clinton650", "Jane755", "Beatrice91", "Veronica830" };
		File f = new File("C://Users//Markel Cortazar//Desktop//Apuntes//Curso 2//DSA//friends.dsa");
		clique(ids, f);
	}

	public static void clique(String[] ids, File f) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(f);
		pw.println("friend1,friend2");
		for (int i = 0; i < ids.length - 1; i++) {
			for (int j = i + 1; j < ids.length; j++) {
				pw.println(ids[i] + "," + ids[j]);
			}
		}
		pw.close();
	}
}
