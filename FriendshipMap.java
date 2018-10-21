package project;

import java.util.HashMap;
import java.util.Map;

public class FriendshipMap {
	private class Friends {
		private String id1, id2;

		public Friends(String id1, String id2) {
			this.id1 = id1;
			this.id2 = id2;
		}

	}

	static private Map<Friends, Friendship> friendships = new HashMap<Friends, Friendship>();

	public boolean areFriends(String id1, String id2) {
		return friendships.containsKey(new Friends(id1, id2));
	}

	public boolean areFriends(User u1, User u2) {
		return areFriends(u1.getId(), u2.getId());
	}

	public void addFriendship(User u1, User u2) {
		Friendship aux = new Friendship(u1, u2);
		friendships.put(new Friends(u1.getId(), u2.getId()), aux);
		u1.addFriend(aux);
		u2.addFriend(aux);
	}
	
	public static void resetReadFlag() {
		for (Friendship f : friendships.values()) {
			f.setRead(true);
		}
	}
}
