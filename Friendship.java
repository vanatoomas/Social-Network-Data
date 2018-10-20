package project;

public class Friendship {
	project.User user1;
	private project.User user2;
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
}
