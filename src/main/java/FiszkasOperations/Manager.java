package FiszkasOperations;

import com.sleepycat.persist.PrimaryIndex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Manager {

    public static void addToStore(String title, List<Fiszka> fiszkaArrayList) {
        PrimaryIndex<String, Deck> pi = DeckStore.getDeckAccessorPrimaryIndex();
        Deck deck = new Deck(title, fiszkaArrayList);
        pi.putNoReturn(deck);
    }

    public static List<String> getAllDecksNames() {
        PrimaryIndex<String, Deck> pi = DeckStore.getDeckAccessorPrimaryIndex();

        Set<String> stringSet = new HashSet<>();

        //@Todo Check is there any better way to get all primary keys values than:
        for (Deck d : pi.entities()) {
            stringSet.add(d.getTitle());
        }
        return new ArrayList<>(stringSet);
    }

    public Deck getDeck(String title) {
        PrimaryIndex<String, Deck> pi = DeckStore.getDeckAccessorPrimaryIndex();
        return pi.get(title);
    }

}
