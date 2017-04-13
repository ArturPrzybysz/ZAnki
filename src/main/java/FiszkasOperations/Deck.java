package FiszkasOperations;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Artur on 2017-02-28.
 */
@Entity
public class Deck {
    @PrimaryKey
    String title;

    private String serializedFiszkas;

    //private ArrayList<Fiszka> fiszkas;
    public Deck(String title, List<Fiszka> fiszkas) {
        this.title = title;
        ObjectMapper mapper = new ObjectMapper();

        try {
            serializedFiszkas = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(fiszkas);
        } catch (Exception e) {
            System.out.println("Error during serialization of fiszkas List: " + fiszkas);
        }

    }

    Deck() {
    }

    private ArrayList<Fiszka> deserializedList() {

        ObjectMapper mapper = new ObjectMapper();

        AnnotationIntrospector introspector = new JacksonAnnotationIntrospector();

        mapper.getDeserializationConfig().with(introspector);

        mapper.getSerializationConfig().with(introspector);
        JavaType type = mapper.getTypeFactory().
                constructCollectionType(ArrayList.class, Fiszka.class);
        try {
            ArrayList<Fiszka> temp = mapper.readValue(serializedFiszkas, type);
            return temp;
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<Fiszka> getHardFiszkas() throws IOException {

        ArrayList<Fiszka> hard = new ArrayList<>(), fiszkas = deserializedList();
        for (Fiszka f : fiszkas) {
            if (f.isHard()) {
                hard.add(f);
            }
        }
        return hard;
    }

    public ArrayList<Fiszka> getFiszkas() {
        return deserializedList();
    }

    public String getTitle() {
        return title;
    }
}
