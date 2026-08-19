// FindTheNpe.java - SOLUTION
//
// All three bugs fixed. Each one was a different way for a null to appear:
//   BUG 1  a missing 'this.' meant a field was never assigned
//   BUG 2  a constructor took a parameter and then ignored it
//   BUG 3  a method had a path that fell through and returned null
//
// Run it with:  java FindTheNpe.java

public class FindTheNpe {

    public static void main(String[] args) {
        System.out.println("=== Song ===");
        Song blue = new Song("Blue Monday", "New Order");
        System.out.println("  title  : " + blue.getTitle());
        System.out.println("  artist : " + blue.getArtist().toUpperCase());

        System.out.println();
        System.out.println("=== Playlist ===");
        Playlist party = new Playlist("Party Mix", blue);
        System.out.println("  name     : " + party.getName());
        System.out.println("  featured : " + party.getFeatured().getTitle());

        System.out.println();
        System.out.println("=== Ratings ===");
        System.out.println("  5 stars: " + starLabel(5).toUpperCase());
        System.out.println("  3 stars: " + starLabel(3).toUpperCase());
        System.out.println("  1 star : " + starLabel(1).toUpperCase());
    }

    // FIX 3: every path now returns a real String. A method that can return
    // null forces every caller to remember to check - so where there is a
    // sensible ordinary answer, return it instead.
    static String starLabel(int stars) {
        if (stars >= 4) {
            return "great";
        }
        if (stars >= 2) {
            return "ok";
        }
        return "poor";
    }
}

class Song {
    private String title;
    private String artist;

    Song(String title, String artist) {
        this.title = title;
        // FIX 1: was 'artist = artist;', which assigned the parameter to
        // itself and left the field null.
        this.artist = artist;
    }

    String getTitle() {
        return title;
    }

    String getArtist() {
        return artist;
    }
}

class Playlist {
    private String name;
    private Song featured;

    Playlist(String name, Song featured) {
        this.name = name;
        // FIX 2: the constructor accepted a featured song and then never
        // stored it, so the field kept its default of null.
        this.featured = featured;
    }

    String getName() {
        return name;
    }

    Song getFeatured() {
        return featured;
    }
}
