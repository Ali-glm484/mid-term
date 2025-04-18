package packages;

public class PremiumBehavior implements UserBehavior {
    private int month;

    public PremiumBehavior (int month) throws InvalidOperationException {
        if (month <= 0)
            throw new InvalidOperationException("Month can not be zero or negative.");

        this.month = month;
    }

    public void createPlaylist (String title, User owner) throws InvalidOperationException {
        Playlist playlist = new Playlist(owner, title);

        owner.getPlaylists().add(playlist);
    }

    public void playMusic (Music music) {
        music.play();
    }

    public void buyPremium (User owner, int month) throws InvalidOperationException {
        owner.getBehavior().month += month;
    }
}
