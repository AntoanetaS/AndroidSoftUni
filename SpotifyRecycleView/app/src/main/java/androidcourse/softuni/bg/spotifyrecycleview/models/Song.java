package androidcourse.softuni.bg.spotifyrecycleview.models;

public class Song {
    private String title;
    private String author;
    private int sondRawFileId;

    public Song(String title, String time, int soundFileId) {
        this.title = title;
        this.author = time;
        this.sondRawFileId = soundFileId;
    }

    public int getSondRawFileId() {
        return sondRawFileId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Song)) return false;

        Song song = (Song) o;

        if (getTitle() != null ? !getTitle().equals(song.getTitle()) : song.getTitle() != null)
            return false;
        return getAuthor() != null ? getAuthor().equals(song.getAuthor()) : song.getAuthor() == null;

    }

    @Override
    public int hashCode() {
        int result = getTitle() != null ? getTitle().hashCode() : 0;
        result = 31 * result + (getAuthor() != null ? getAuthor().hashCode() : 0);
        return result;
    }
}

