// FindTheNpe.java
//
// GOAL: this one is different. There are no TODOs to fill in and nothing is
// missing. The file compiles perfectly - and then it crashes.
//
// There are THREE bugs, and every one of them produces a
// NullPointerException. Run the program, read the crash, fix that one bug,
// run again. Repeat until all three are gone. That loop - run, read, fix,
// run - is the actual skill this exercise is teaching.
//
// HOW TO READ THE CRASH. You will see something shaped like this:
//
//   Exception in thread "main" java.lang.NullPointerException: Cannot invoke
//   "String.toUpperCase()" because the return value of "Song.getArtist()" is null
//       at FindTheNpe.main(FindTheNpe.java:53)
//
// Read it in three pieces:
//   - "Cannot invoke ..."  the method you tried to call
//   - "because ... is null" WHICH thing was null. This is the useful half.
//   - "at ...(File.java:52)" the exact line. Start there, then work backwards
//     to ask why that thing was ever null.
//
// The line number tells you where it EXPLODED. The bug is usually somewhere
// earlier - wherever that value was supposed to be given a real value.
//
// A tip before you start: none of the three fixes belong in main. Each one is
// inside a class further down, at the place where a field or a return value
// should have been set and was not.
//
// Run it with:  java FindTheNpe.java
//
// EXPECTED OUTPUT once all three are fixed:
//
// === Song ===
//   title  : Blue Monday
//   artist : NEW ORDER
//
// === Playlist ===
//   name     : Party Mix
//   featured : Blue Monday
//
// === Ratings ===
//   5 stars: GREAT
//   3 stars: OK
//   1 star : POOR

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

    static String starLabel(int stars) {
        if (stars >= 4) {
            return "great";
        }
        if (stars >= 2) {
            return "ok";
        }
        return null;
    }
}

class Song {
    private String title;
    private String artist;

    Song(String title, String artist) {
        this.title = title;
        artist = artist;
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
    }

    String getName() {
        return name;
    }

    Song getFeatured() {
        return featured;
    }
}
