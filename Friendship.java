package project;

public class Friendship {
	// Models a friendship relationship between two users
	private User user1;
	private User user2;
	private boolean read = false;

	public Friendship(project.User u1, project.User u2) {
		user1 = u1;
		user2 = u2;
	}

	public boolean isFriend(User u) {
		return (u.getId().equals(user1.getId()) || u.getId().equals(user2.getId()));
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean b) {
		read = b;
	}

	public User getFriend1() {
		return user1;
	}

	public User getFriend2() {
		return user2;
	}

	@Override
	public String toString() {
		return user1.getId() + "," + user2.getId();
	}
}
