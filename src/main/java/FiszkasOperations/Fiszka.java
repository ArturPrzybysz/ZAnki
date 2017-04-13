package FiszkasOperations;

import com.sleepycat.persist.model.PrimaryKey;

import java.io.File;
import java.io.Serializable;

public class Fiszka implements Serializable {

    @PrimaryKey
    private
    String title;
    private String author;
    private String date;
    private Boolean hard;
    private File image;

    public Fiszka(String title, String author, String date) {
        this.title = title;

        this.author = author;
        this.date = date;
        hard = false;
    }

    public Fiszka(String title, String author, String date, File image) {
        this.title = title;
        this.author = author;
        this.date = date;
        this.image = image;
        hard = false;
    }

    public Fiszka() {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getHard() {
        return hard;
    }

//    public Image getImage() {
//        return image;
//    }

    public File getImage() {
        return image;
    }

//    public void setImage(Image image) {
//        this.image = image;
//    }

    public void setImage(File image) {
        this.image = image;
    }

    public void setHard() {
        hard = true;
    }

    public void setEasy() {
        hard = false;
    }

    public Boolean isHard() {
        return hard;
    }


}
