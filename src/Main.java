import packages.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Create users
            User user1 = new User("ali123", "password123");
            User user2 = new User("sara89", "saraPass123");
            User artist = new User("shadmehr", "artistPass123");

            // Test music creation
            Music song1 = new Music("Gole Yakh", artist);
            Music song2 = new Music("Mantra", artist);
            Music song3 = new Music("Mantra", user2); // Should succeed (different artist)

            // Test regular user behavior
            System.out.println("\n--- Testing Regular User ---");
            testRegularUser(user1, song1);

            // Test premium upgrade
            System.out.println("\n--- Testing Premium Upgrade ---");
            testPremiumUpgrade(user1);

            // Test premium user behavior
            System.out.println("\n--- Testing Premium User ---");
            testPremiumUser(user1, song2);

            // Test following system
            System.out.println("\n--- Testing Follow System ---");
            testFollow(user1, user2);

            // Test playlist functionality
            System.out.println("\n--- Testing Playlist ---");
            testPlaylist(user1, song1, song2);

            // Test error cases
            System.out.println("\n--- Testing Error Cases ---");
            testErrorCases();

        } catch (InvalidOperationException e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    private static void testRegularUser(User user, Music music) throws InvalidOperationException {
        try {
            System.out.println("Attempting to create playlist as regular user:");
            user.createPlaylist("My Fav", user);
        } catch (InvalidOperationException e) {
            System.out.println("Expected error: " + e.getMessage());
        }

        // Test play limit
        System.out.println("\nPlaying 5 songs:");
        for (int i = 0; i < 5; i++) {
            user.playMusic(music);
        }

        System.out.println("\nAttempting to play 6th song:");
        try {
            user.playMusic(music);
        } catch (InvalidOperationException e) {
            System.out.println("Expected error: " + e.getMessage());
        }
    }

    private static void testPremiumUpgrade(User user) throws InvalidOperationException {
        System.out.println("Purchasing premium (6 months):");
        user.buyPremium(user, 6);
        System.out.println("Account type after upgrade: " +
                (user.getBehavior() instanceof PremiumBehavior ? "Premium" : "Regular"));
    }

    private static void testPremiumUser(User user, Music music) throws InvalidOperationException {
        System.out.println("Creating playlist as premium user:");
        user.createPlaylist("Pro Playlist", user);
        System.out.println("Number of playlists: " + user.getPlaylists().size());

        System.out.println("\nUnlimited playback test:");
        for (int i = 0; i < 7; i++) {
            user.playMusic(music);
        }
    }

    private static void testFollow(User user1, User user2) throws InvalidOperationException {
        user1.follow(user2);
        System.out.println(user1.getUsername() + "'s following count: " +
                user1.getFollowingList().size());

        try {
            System.out.println("\nAttempting self-follow:");
            user1.follow(user1);
        } catch (InvalidOperationException e) {
            System.out.println("Expected error: " + e.getMessage());
        }
    }

    private static void testPlaylist(User user, Music... songs) throws InvalidOperationException {
        Playlist pl = user.getPlaylists().get(0);

        System.out.println("Adding songs to playlist:");
        pl.addMusic(user.getPassword(), songs[0]);
        pl.addMusic(user.getPassword(), songs[1]);

        System.out.println("Total songs: " + pl.getPlaylist().size());

        System.out.println("\nSearching for song:");
        Music found = pl.searchInPlaylist("Mantra", "shadmehr");
        System.out.println("Found song: " + (found != null ? found.getTitle() : "null"));

        System.out.println("\nRemoving song:");
        pl.removeMusic(user.getPassword(), songs[1]);
        System.out.println("Count after removal: " + pl.getPlaylist().size());
    }

    private static void testErrorCases() {
        try {
            System.out.println("Attempting short password:");
            new User("test", "short");
        } catch (InvalidOperationException e) {
            System.out.println("Expected error: " + e.getMessage());
        }

        try {
            System.out.println("\nAttempting duplicate music:");
            User artist = new User("beeptunes", "pass123456");
            new Music("Dance Mix", artist);
            new Music("Dance Mix", artist);
        } catch (InvalidOperationException e) {
            System.out.println("Expected error: " + e.getMessage());
        }
    }
}