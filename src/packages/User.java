package packages;

import java.util.ArrayList;


public class User {
    private String username;
    private String password;
    private ArrayList<User> followerList;
    private ArrayList<User> followingList;
    private UserBehavior behavior;
    private ArrayList<Playlist> playlists;
    private static ArrayList<User> allUsers = new ArrayList<>();

    public User (String username, String password) throws InvalidOperationException {
        if (username.isEmpty() || username == null)
            throw new InvalidOperationException("Username can not be empty.");

        if (password.isEmpty() || password == null)
            throw new InvalidOperationException("Pasword can not be empty.");

        if (password.length() < 8)
            throw new InvalidOperationException("The length of password must be at least 8 characters.");

        for (User user : allUsers)
            if (user.getUsername().equals(username))
                throw new InvalidOperationException("This username is already exists.");

        this.username = username;
        this.password = password;
        followerList = new ArrayList<>();
        followingList = new ArrayList<>();
        playlists = new ArrayList<>();
        behavior = new RegularBehavior();
        allUsers.add(this);
    }

    public void follow (User user) throws InvalidOperationException {
        if (user.getUsername().equals(username))
            throw new InvalidOperationException("You can not follow yourself.");

        followingList.add(user);
        System.out.println("User successfully added to the following list.");
    }

    public void createPlaylist (String title) throws InvalidOperationException{
        this.behavior.createPlaylist(title, this);
    }

    public void playMusic (Music music) throws InvalidOperationException {
        this.behavior.playMusic(music);
    }

    public void buyPremium (User owner, int month) throws InvalidOperationException {
        this.behavior.buyPremium(owner, month);
    }

    public String getUsername () {
        return username;
    }

    public String getPassword () {
        return password;
    }

    public void setBehavior (PremiumBehavior premiumAccount) {
        this.behavior = premiumAccount;
    }

    public PremiumBehavior getBehavior () throws InvalidOperationException {
        if (behavior instanceof PremiumBehavior)
            return (PremiumBehavior) behavior;

        throw new InvalidOperationException("You are not a premium user");
    }

    public ArrayList<Playlist> getPlaylists () {
        return playlists;
    }

    public ArrayList<User> getFollowingList() {
        return followingList;
    }
}
