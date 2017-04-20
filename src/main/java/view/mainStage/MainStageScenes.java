package view.mainStage;

import FiszkasOperations.Fiszka;
import FiszkasOperations.Manager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import view.createDeck.CreationScene;
import view.displayDecks.LearningStage;
import view.displayDecks.TestStage;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class MainStageScenes {

    private static Button setButtonCreate(double WIDTH, MainStage stage) {
        Button buttonCreate = new Button("Nowa talia");
        buttonCreate.setPrefSize(WIDTH, 20);
        buttonCreate.setOnAction(e -> stage.setCreateScene());

        return buttonCreate;
    }

    private static Button setButtonLearn(double WIDTH, MainStage stage) {
        Button buttonLearn = new Button("Nauka");
        buttonLearn.setPrefSize(WIDTH, 20);
        buttonLearn.setOnAction(e -> stage.setLearningScene());

        return buttonLearn;
    }

    public Scene setBeginCreationScene(double WIDTH, double HEIGHT, MainStage parentStage) {
        GridPane gp = new GridPane();
        Scene scene = new Scene(gp, WIDTH, HEIGHT);

        gp.add(setButtonLearn(WIDTH, parentStage), 0, 0);
        gp.add(setButtonCreate(WIDTH, parentStage), 1, 0);
        gp.add(new Label(), 0, 1);
        gp.add(new Label(), 0, 2);
        gp.add(new Label(), 0, 3);

        TextField titleTF = new TextField();
        titleTF.setPromptText("Tytuł talii");
        gp.add(titleTF, 0, 4, 2, 1);

        Button button = new Button("Rozpocznij tworzenie");
        gp.add(button, 0, 5);

        button.setOnAction(event -> {
            if (!titleTF.getText().isEmpty() && !titleRepeats(titleTF.getText())) {
                parentStage.setScene(new CreationScene
                        (new GridPane(), WIDTH, HEIGHT, parentStage, titleTF.getText().toString()));
                parentStage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Achtung!");
                alert.setHeaderText(null);
                alert.setContentText("Zmień tytuł talii");

                alert.showAndWait();
            }
        });
        return scene;
    }

    public Scene setLearnScene(double WIDTH, double HEIGHT, MainStage parentStage) {
        Manager manager = new Manager();
        GridPane gp = new GridPane();
        Scene scene = new Scene(gp, WIDTH, HEIGHT);

        TableView table = new TableView();
        table.setEditable(true);
        table.setPrefHeight(700);

        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn titleColumn = new TableColumn("Tytuł");
        TableColumn authorColumn = new TableColumn("Autor");
        TableColumn dateColumn = new TableColumn("Data");
        TableColumn hardColumn = new TableColumn("Oznaczony");

        table.getColumns().addAll(titleColumn, authorColumn, dateColumn, hardColumn);

        titleColumn.setCellValueFactory(new PropertyValueFactory<Fiszka, String>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<Fiszka, String>("author"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Fiszka, String>("date"));
        hardColumn.setCellValueFactory(new PropertyValueFactory<Fiszka, String>("hard"));

        ObservableList<String> titles = FXCollections.observableArrayList(manager.getAllDecksNames());
        ComboBox comboBox = new ComboBox(titles);
        comboBox.setPromptText("Talia");
        comboBox.setPrefWidth(550);
        comboBox.setOnAction(event -> {
            String title = comboBox.getSelectionModel().getSelectedItem().toString();
            ObservableList<Fiszka> decks = FXCollections.observableArrayList(manager.getDeck(title).getFiszkas());
            table.setItems(decks);
        });

        Button buttonEditDeck = new Button("Edytuj talię");
        buttonEditDeck.setPrefWidth(550);
        buttonEditDeck.setOnAction(event -> {
            //@TODO Create edition of deck scene
            //Stage stage = new Stage();
            //stage.setScene(createdScene);
            //stage.show();
        });

        Button buttonStartLearning = new Button("Rozpocznij naukę");
        buttonStartLearning.setPrefWidth(550);

        buttonStartLearning.setOnAction(event -> {
            String title;

            if (comboBox.getSelectionModel().getSelectedItem() == null) {
                return;
            } else {
                title = comboBox.getSelectionModel().getSelectedItem().toString();
            }

            ObservableList<Fiszka> itemsSelectedFromTable = table.getSelectionModel().getSelectedItems();
            if (itemsSelectedFromTable.size() == 0) {
                return;
            }

            Stage stageLearn = new LearningStage(itemsSelectedFromTable, title, WIDTH, HEIGHT);
            stageLearn.show();
        });

        Button buttonStartTest = new Button("Rozpocznij test");
        buttonStartTest.setPrefWidth(550);
        buttonStartTest.setOnAction(event -> {
            if (Objects.nonNull(comboBox.getSelectionModel().getSelectedItem())) {
                String title = comboBox.getSelectionModel().getSelectedItem().toString();
                ObservableList<Fiszka> deck = FXCollections.observableArrayList(manager.getDeck(title).getFiszkas());
                ObservableList<Fiszka> selectedFromDeck = table.getSelectionModel().getSelectedItems();
                if(selectedFromDeck.size()!=0){
                Stage test = new TestStage(deck, selectedFromDeck, title, WIDTH, HEIGHT);
                test.show();}
            }

        });

        gp.add(setButtonLearn(WIDTH, parentStage), 0, 0);
        gp.add(setButtonCreate(WIDTH, parentStage), 1, 0);

        gp.add(comboBox, 0, 2);
        gp.add(buttonEditDeck, 1, 2);

        gp.add(table, 0, 3, 2, 1);

        gp.add(new Label(), 0, 1);
        gp.add(new Label(), 0, 5);

        gp.add(buttonStartLearning, 0, 7);
        gp.add(buttonStartTest, 1, 7);

        return scene;
    }

    private boolean titleRepeats(String newTitle) {
        List<String> titles = Manager.getAllDecksNames();
        return titles.stream().anyMatch(Predicate.isEqual(newTitle));
    }
}