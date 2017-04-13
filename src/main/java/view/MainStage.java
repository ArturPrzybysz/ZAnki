package view;

import FiszkasOperations.Fiszka;
import FiszkasOperations.Manager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainStage extends Stage {
    private final static double HEIGHT = 550, WIDTH = 670;
    private Scene learnScene, createScene; //'main' scenes
    private Manager manager = new Manager();

    public MainStage() {
        learnScene = setLearnScene();
        createScene = new BeginCreationScene(new GridPane(), WIDTH, HEIGHT, this);
    }

    public void showLearnScene() {
        setScene(learnScene);
    }

    private Scene setLearnScene() {
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
        comboBox.setOnAction(event -> {
            String title = comboBox.getSelectionModel().getSelectedItem().toString();
            ObservableList<Fiszka> decks = FXCollections.observableArrayList(manager.getDeck(title).getFiszkas());
            table.setItems(decks);
        });
        gp.add(comboBox, 0, 2);

        Button buttonStartLearning = new Button("Wybierz i rozpocznij naukę");

        buttonStartLearning.setOnAction(event -> {
            String title;

            if (comboBox.getSelectionModel().getSelectedItem() == null) {
                return;
            } else {
                title = comboBox.getSelectionModel().getSelectedItem().toString();
            }

            ObservableList<Fiszka> SelectedItemsOfTable = table.getSelectionModel().getSelectedItems();
            if (SelectedItemsOfTable.size() == 0) {
                return;
            }

            Stage stageLearn = new LearningStage(SelectedItemsOfTable, title, WIDTH, HEIGHT);
            stageLearn.show();
        });

        gp.add(setButtonLearn(), 0, 0);
        gp.add(setButtonCreate(), 1, 0);
        gp.add(table, 0, 3, 2, 1);
        gp.add(buttonStartLearning, 0, 7);

        gp.add(new Label(), 0, 1);
        gp.add(new Label(), 0, 5);

        return scene;
    }

    Button setButtonCreate() {
        Button buttonCreate = new Button("Nowa talia");
        buttonCreate.setPrefSize(WIDTH, 20);
        buttonCreate.setOnAction(e -> setScene(createScene));

        return buttonCreate;
    }

    Button setButtonLearn() {
        Button buttonLearn = new Button("Nauka");
        buttonLearn.setPrefSize(WIDTH, 20);
        buttonLearn.setOnAction(e -> setScene(learnScene));

        return buttonLearn;
    }


}
