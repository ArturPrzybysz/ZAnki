package view.createDeck;

import FiszkasOperations.Fiszka;
import FiszkasOperations.Manager;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import view.mainStage.MainStage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CreationScene extends Scene {

    private File file = new File("D:\\Coding\\git\\ZAnki\\src\\main\\java\\view\\default.png");

    private File imageContainer;

    //@Todo change before running outside IDE
    private Image imageDefault = new Image(file.toURI().toString());
    private FileChooser fileChooser = new FileChooser();

    private List<Fiszka> fiszkas = new ArrayList<>();
    private boolean pictureAdded = false;


    public CreationScene(GridPane gp, double width, double height, MainStage stage, String title) {
        super(gp, width, height);

        stage.setTitle("Tworzenie talii " + title);
        ImageView imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(350);
        imageView.setImage(imageDefault);

        Button fileButton = new Button("Wybierz obraz");
        fileButton.setOnAction(event -> {
            File file = fileChooser.showOpenDialog(stage);
            imageContainer = file;
            if (file != null) {
                Image img = new Image(file.toURI().toString());
                imageView.setImage(img);
                pictureAdded = true;
            }
        });

        TextField tfTitle = new TextField();
        tfTitle.setPromptText("Tytuł");
        TextField tfAuthor = new TextField();
        tfAuthor.setPromptText("Autor");
        TextField tfDate = new TextField();
        tfDate.setPromptText("Data");

        Button buttonCancel = new Button("Anuluj");
        buttonCancel.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Ostrzeżenie");
            alert.setHeaderText("Możesz utracić postęp tworzenia talii.");
            alert.setContentText("Czy chcesz kontynuować?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                stage.setCreateScene();
            }
        });

        Button nextFiszka = new Button("Następny");
        nextFiszka.setOnAction(event -> {
            if (!empty(tfTitle, tfAuthor, tfDate)) {
                if (!titleRepeats(tfTitle.getText())) {
                    fiszkas.add(new Fiszka(tfTitle.getText(), tfAuthor.getText(), tfDate.getText(), imageContainer));
                    tfAuthor.setText("");
                    tfDate.setText("");
                    tfTitle.setText("");
                    imageView.setImage(imageDefault);
                    pictureAdded = false;
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Informacja");
                    alert.setHeaderText(null);
                    alert.setContentText("Zmień tytuł dzieła.");

                    alert.showAndWait();
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Informacja");
                alert.setHeaderText(null);
                alert.setContentText("Uzupełnij wszystkie dane.");

                alert.showAndWait();
            }
        });

        Button createDeck = new Button("Dodaj jako ostatnią");
        createDeck.setOnAction(event -> {
            if (!empty(tfTitle, tfAuthor, tfDate)) {
                if (!titleRepeats(tfTitle.getText())) {
                    fiszkas.add(new Fiszka(tfTitle.getText(), tfAuthor.getText(), tfDate.getText(), imageContainer));
                    Manager.addToStore(title, (ArrayList<Fiszka>) fiszkas);

                    stage.setCreateScene();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Informacja");
                    alert.setHeaderText(null);
                    alert.setContentText("Zmień tytuł dzieła.");

                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Informacja");
                alert.setHeaderText(null);
                alert.setContentText("Uzupełnij wszystkie informacje.");

                alert.showAndWait();
            }

        });

        gp.add(imageView, 0, 0);
        gp.add(fileButton, 0, 1);
        gp.add(tfTitle, 0, 2);
        gp.add(tfAuthor, 0, 3);
        gp.add(tfDate, 0, 4);
        gp.add(nextFiszka, 0, 5);
        gp.add(createDeck, 0, 6);
        gp.add(buttonCancel, 0, 7);

    }

    private boolean titleRepeats(String newTitle) {
        return fiszkas.stream().anyMatch(fiszka -> Objects.equals(fiszka.getTitle(), newTitle));
    }

    public void reloadScenes(){

    }

    private boolean empty(TextField tfTitle, TextField tfAuthor, TextField tfDate) {
        return tfTitle.getText().equals("") || tfAuthor.getText().equals("") || tfDate.getText().equals("") || !pictureAdded;
    }
}
