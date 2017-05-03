package view.modifyDeck;

import FiszkasOperations.Fiszka;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class ModifyDeck {
    private ObservableList<Fiszka> editedCards;

    private Stage stage = new Stage();
    private GridPane gp = new GridPane();
    private Scene scene;

    private ImageView imageView = new ImageView();

    private TableView<Fiszka> tableView = new TableView(); // with titles
    private TableColumn<Fiszka, String> column;

    private TextField title = new TextField(), author = new TextField(), date = new TextField();
    private Button saveChanges = new Button("Zapisz zmiany");

    public ModifyDeck(ObservableList<Fiszka> givenCards, double WIDTH, double HEIGHT) {
        editedCards = givenCards;
        scene = new Scene(gp, WIDTH, HEIGHT);
        setUpTableView();

        tableView.setOnMouseClicked(event -> {
            Fiszka selected = tableView.getSelectionModel().getSelectedItem();
            updateScene(selected);
        });
    }

    private void setUpTableView() {
        column.setCellValueFactory(new PropertyValueFactory<>("title"));
        tableView.getColumns().add(column);
    }

    private void updateScene(Fiszka card) {
/*if(edited){Alert alert = new Alert(Alert.AlertType.CONFIRMATION){}}*/

        title.setText(card.getTitle());
        author.setText(card.getAuthor());
        date.setText(card.getDate());
        imageView.setImage(new Image(card.getImage().toURI().toString()));


        //edited = false;
    }


}
