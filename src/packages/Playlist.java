package packages;

import java.lang.reflect.InaccessibleObjectException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Playlist {
    private ArrayList<Music> playlist;
    private User owner;
    private String title;

    public Playlist (User owner, String title) throws InvalidOperationException {
        if (owner == null)
            throw new InvalidOperationException("Owner dose not exist.");

        if (title.isEmpty() || title == null)
            throw new InvalidOperationException("Title can not be empty.");

        this.title = title;
        this.owner = owner;
        playlist = new ArrayList<>();
    }

    private void validatePassword (String password) throws InvalidOperationException {
        if (!owner.getPassword().equals(password))
            throw new InvalidOperationException("The password is not correct.");
    }

    public void editTitle (String password, String newTitle) throws InvalidOperationException {
        validatePassword(password);

        if (newTitle.isEmpty() || newTitle == null)
            throw  new InvalidOperationException("Title can not be empty.");

        title = newTitle;
    }

    public void addMusic (String password, Music music) throws InvalidOperationException {
        validatePassword(password);

        for (Music m : playlist)
            if (m.getTitle().equals(music.getTitle()) && m.getSinger() == music.getSinger())
                throw new InvalidOperationException("Duplicate music error.");

        playlist.add(music);
    }

    public void removeMusic (String password, Music music) throws InvalidOperationException {
        validatePassword(password);

        for (Music m : playlist)
            if (m.getTitle().equals(music.getTitle()) && m.getSinger() == music.getSinger()) {
                playlist.remove(music);
                return;
            }

        throw new InvalidOperationException("this music dose not exist.");
    }

    public Music searchInPlaylist (String name) {
        for (Music music : playlist)
            if (music.getTitle().equals(name))
                return music;

        return null;
    }

    public Music searchInPlaylist (String name, String singer) {
        for (Music music : playlist)
            if (music.getTitle().equals(name) && music.getSinger().getUsername().equals(singer))
                return music;

        return null;
    }

    public void playPlaylist () {
        Scanner scn = new Scanner(System.in);

        for (Music music : playlist) {
            music.play();
            if (!scn.nextLine().equalsIgnoreCase("next"))
                break;
        }
    }

    public void shuffle () {
        Scanner scn = new Scanner(System.in);

        Random random = new Random();
        int max = playlist.size() - 1;
        int min = 0;

        while (!scn.nextLine().equalsIgnoreCase("next")) {
            int randomNumber = (max - min + 1) + min;

            playlist.get(randomNumber).play();
        }
    }

    public ArrayList<Music> getPlaylist() {
        return playlist;
    }
}
