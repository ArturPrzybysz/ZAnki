package FiszkasOperations;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.StoreConfig;

import java.io.File;

public class DeckStore {

    private static final String dbName = "DeckStore";
    private static Environment env;
    private static EntityStore store;
    private static deckAccessor deckAccessor;

    private DeckStore() {
    } //for bindings

    public static void init(File envHome) throws DatabaseException {

        try {
            envHome.mkdirs();
        } catch (Exception e) {
            e.printStackTrace();
        }

        EnvironmentConfig envConfig = new EnvironmentConfig();
        envConfig.setAllowCreate(true);
        envConfig.setTransactional(true);
        env = new Environment(envHome, envConfig);

        StoreConfig storeConfig = new StoreConfig();
        storeConfig.setAllowCreate(true);
        storeConfig.setTransactional(true);
        store = new EntityStore(env, dbName, storeConfig);

        deckAccessor = new deckAccessor(store);
    }

    public static void close() {
        if (store != null) {
            try {
                store.close();
            } catch (DatabaseException dbe) {
                System.err.println("Error closing store: " +
                        dbe.toString());
                System.exit(-1);
            }
        }

        if (env != null) {
            try {
                env.close();
            } catch (DatabaseException dbe) {
                System.err.println("Error closing env: " +
                        dbe.toString());
                System.exit(-1);
            }
        }
    }

    public static PrimaryIndex<String, Deck> getDeckAccessorPrimaryIndex() {
        return deckAccessor.pi;
    }

    private static class deckAccessor {

        PrimaryIndex<String, Deck> pi;

        deckAccessor(EntityStore store) throws DatabaseException {
            pi = store.getPrimaryIndex(String.class, Deck.class);
        }
    }
}
