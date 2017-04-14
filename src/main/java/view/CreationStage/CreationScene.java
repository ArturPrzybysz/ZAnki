package view.CreationStage;

import FiszkasOperations.Fiszka;
import FiszkasOperations.Manager;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import view.MainStage.MainStage;

import java.io.File;
import java.util.ArrayList;

public class CreationScene extends Scene {

    private File file = new File("D:\\Coding\\JAVA\\Workspace\\projektOdNowa" +
            "\\fiszki-master\\fiszki-master\\src\\main\\java\\default.png");

    private File container;

    //@Todo change before running outside IDE
    private Image imageDefault = new Image(file.toURI().toString());
    private FileChooser fileChooser = new FileChooser();
    private ArrayList<Fiszka> fiszkas = new ArrayList<>();


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
            container = file;
            if (file != null) {
                Image img = new Image(file.toURI().toString());
                imageView.setImage(img);
            }
        });

        TextField tfTitle = new TextField();
        tfTitle.setPromptText("Tytuł");
        TextField tfAuthor = new TextField();
        tfAuthor.setPromptText("Autor");
        TextField tfDate = new TextField();
        tfDate.setPromptText("Date");

        Button nextFiszka = new Button("Następny");
        nextFiszka.setOnAction(event -> {
            Fiszka f = new Fiszka(tfTitle.getText(), tfAuthor.getText(), tfDate.getText(), container);
            fiszkas.add(f);
            tfAuthor.setText("");
            tfDate.setText("");
            tfTitle.setText("");
            imageView.setImage(imageDefault);
        });

        Button createDeck = new Button("Zakończ tworzenie");
        createDeck.setOnAction(event -> {
            Manager.addToStore(title, fiszkas);
        });

        gp.add(imageView, 0, 0);
        gp.add(fileButton, 0, 1);
        gp.add(tfTitle, 0, 2);
        gp.add(tfAuthor, 0, 3);
        gp.add(tfDate, 0, 4);
        gp.add(nextFiszka, 0, 5);
        gp.add(createDeck, 0, 6);

    }
}
