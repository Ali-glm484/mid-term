package packages;

public class RegularBehavior implements UserBehavior {
    private int playingLimit ;

    public RegularBehavior () {
        playingLimit = 5;
    }

    public void createPlaylist (String Title, User Owner) throws InvalidOperationException {
        throw new InvalidOperationException("A normal user cannot create a playlist.");
    }

    public void playMusic (Music music) throws InvalidOperationException {
        if (playingLimit == 0)
            throw new InvalidOperationException("Your playing limit is zero. You have to by premium account.");

        music.play();
        playingLimit--;
    }

    public void buyPremium (User owner, int month) throws InvalidOperationException {
        owner.setBehavior(new PremiumBehavior(month));
    }
}
