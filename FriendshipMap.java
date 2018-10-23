package project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FriendshipMap {
	public static class Friends implements Comparable<Friends> {
		// Object to act as a key for the FriendshipMap
		private String id1, id2;

		public String getId1() {
			return id1;
		}

		public String getId2() {
			return id2;
		}

		public Friends(String id1, String id2) {
			this.id1 = id1;
			this.id2 = id2;
		}

		@Override
		public int compareTo(Friends arg0) {
			if (id1.equals(arg0.getId1()) && id2.equals(arg0.getId2()))
				return 0;
			if (id1.equals(arg0.getId2()) && id2.equals(arg0.getId1()))
				return 0;
			else
				return -1;
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Friends)) {
				return false;
			}
			if (id1.equals(((Friends) o).getId1()) && id2.equals(((Friends) o).getId2()))
				return true;
			if (id1.equals(((Friends) o).getId2()) && id2.equals(((Friends) o).getId1()))
				return true;
			return false;
		}

		private int stringToint(String s) {
			int out = 0;
			for (char c : s.toCharArray()) {
				out += c;
			}
			return out;
		}

		@Override
		public int hashCode() {
			return stringToint(id1) + stringToint(id2);

		}
	}

	// Main data structure
	static private Map<Friends, Friendship> friendships = new HashMap<Friends, Friendship>();

	// Returns true if the given users are friends
	public static boolean areFriends(String id1, String id2) {
		return friendships.containsKey(new Friends(id1, id2));
	}

	public static boolean areFriends(User u1, User u2) {
		return areFriends(u1.getId(), u2.getId());
	}

	// Adds a friendship.Returns true if it is successfully added
	public static boolean addFriendship(User u1, User u2) {
		Friends aux1 = new Friends(u1.getId(), u2.getId());
		if (friendships.containsKey(aux1)) {
			return false;
		}
		Friendship temp = new Friendship(u1, u2);
		friendships.put(aux1, temp);
		u1.addFriend(temp);
		u2.addFriend(temp);
		return true;
	}

	// Adds arraylist<Friendship> to the map.Returns count of Friendships
	// successfully added
	public static int addFriendship(ArrayList<Friendship> in) {
		int count = 0;
		for (Friendship f : in) {
			if (addFriendship(f.getFriend1(), f.getFriend2()))
				count++;
		}
		return count;
	}

	public static void resetReadFlag() {
		for (Friendship f : friendships.values()) {
			f.setRead(true);
		}
	}

	public static String print() {
		String output = "friend1,friend2";
		for (Friendship f : friendships.values()) {
			output += "\n" + f.toString();
		}
		return output;
	}

}
