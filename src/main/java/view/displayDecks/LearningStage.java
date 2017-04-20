package view.displayDecks;

import FiszkasOperations.Fiszka;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.util.Objects;

public class LearningStage extends Stage {
    private Label author = new Label(), title = new Label(), date = new Label();
    private ObservableList<Fiszka> fiszkas;
    private Scene scene;
    private GridPane gp = new GridPane();
    private int iterator = 0;
    private ImageView view = new ImageView();

    public LearningStage(ObservableList list, String pageTitle, double WIDTH, double HEIGHT) {
        fiszkas = list;
        this.setTitle("Nauka talii: " + pageTitle);
        scene = new Scene(gp, WIDTH, HEIGHT);
        setLabels();
        setScene(scene);

        view.setImage(new Image(fiszkas.get(iterator).getImage().toURI().toString()));
        view.setPreserveRatio(true);
        view.setFitWidth(400);

        Button nextImage = new Button("Następna");
        nextImage.setOnAction(event -> {
            iterator++;
            updatePage(fiszkas.get(iterator % fiszkas.size()));
        });

        ComboBox<Fiszka> fiszkaSelection = new ComboBox<>();
        fiszkaSelection.setItems(fiszkas);

        // Define rendering of the list of values in ComboBox drop down.
        fiszkaSelection.setCellFactory((comboBox) -> {
            return new ListCell<Fiszka>() {
                @Override
                protected void updateItem(Fiszka item, boolean empty) {
                    super.updateItem(item, empty);
                    if (Objects.isNull(item) || empty) {
                        setText(null);
                    } else {
                        setText(item.getTitle());
                    }
                }
            };
        });

        // Define rendering of selected value shown in ComboBox.
        fiszkaSelection.setConverter(new StringConverter<Fiszka>() {
            @Override
            public String toString(Fiszka item) {
                if (Objects.isNull(item)) {
                    return null;
                } else {
                    updatePage(item);
                    return item.getTitle();
                }
            }

            @Override
            public Fiszka fromString(String personString) {
                return null; // No conversion fromString needed.
            }
        });

        gp.add(view, 0, 0);
        gp.add(title,0,1);
        gp.add(author,0,2);
        gp.add(date,0,3);
        gp.add(nextImage,0,4);
        gp.add(fiszkaSelection,0,5);



    }

    private void updatePage(Fiszka fiszka) {
        view.setImage(new Image(fiszka.getImage().toURI().toString()));
        setLabels();
    }

    private void setLabels() {
        title.setText(fiszkas.get(iterator % fiszkas.size()).getTitle());
        author.setText(fiszkas.get(iterator % fiszkas.size()).getAuthor());
        date.setText(fiszkas.get(iterator % fiszkas.size()).getDate());
    }


}
