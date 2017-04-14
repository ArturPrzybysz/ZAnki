package view.TestStage;

import FiszkasOperations.Fiszka;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LearningStage extends Stage {
    private ObservableList<Fiszka> fiszkas;
    private Scene scene;
    private GridPane gp = new GridPane();
    private TextField title, author, date;
    private int iterator = 0;
    private ImageView view = new ImageView();

    public LearningStage(ObservableList list, String title, double WIDTH, double HEIGHT) {
        fiszkas = list;
        this.setTitle("Nauka talii: " + title);
        scene = new Scene(gp, WIDTH, HEIGHT);
        setLabels();
        setScene(scene);
        setTextFields();

        view.setImage(new Image(fiszkas.get(iterator).getImage().toURI().toString()));
        gp.add(view, 0, 0);
    }

    private void setTextFields() {
        title = new TextField();
        author = new TextField();
        date = new TextField();
        gp.add(title, 0, 1);
        gp.add(author, 0, 2);
        gp.add(date, 0, 3);
    }

    private void setLabels() {
        Label title = new Label("Tytuł: ");
        Label author = new Label("Autor: ");
        Label date = new Label("Data: ");
        gp.add(title, 0, 1);
        gp.add(author, 0, 2);
        gp.add(date, 0, 3);
    }
}
