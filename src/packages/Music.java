package packages;

import java.util.ArrayList;

public class Music {
    private String title;
    private User singer;
    private int numberOfStream;
    private static ArrayList<Music> allMusics = new ArrayList<>();

    public Music (String title, User singer) throws InvalidOperationException {
        if (title.isEmpty() || title == null)
            throw new InvalidOperationException("Title can not be empty.");

        if (singer == null)
            throw new InvalidOperationException("Singer dose not exist.");

        for (Music music : allMusics)
            if (music.title.equals(title) && music.singer == singer)
                throw new InvalidOperationException("Duplicate music error.");

        this.title = title;
        this.singer = singer;
        numberOfStream = 0;
        allMusics.add(this);
    }

    public void play () {
        System.out.println("The tile: " + title);
        System.out.println("The singer's name: " + singer.getUsername());
        System.out.println("The music is playing...");
        numberOfStream++;
    }

    public Music Search (String name) {
        for (Music music : allMusics)
            if (music.title.equals(name))
                return music;

        return null;
    }

    public Music Search (String name, String singer) {
        for (Music music : allMusics)
            if (music.title.equals(name) && music.singer.getUsername().equals(singer))
                return music;

        return null;
    }

    public String getTitle() {
        return title;
    }

    public User getSinger () {
        return singer;
    }
}
