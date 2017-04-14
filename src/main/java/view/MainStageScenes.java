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

public class MainStageScenes {
    static Scene setBeginCreationScene(double WIDTH, double HEIGHT, MainStage parentStage) {
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
            if (!titleTF.getText().toString().isEmpty()) {
                parentStage.setScene(new CreationScene
                        (new GridPane(), WIDTH, HEIGHT, parentStage, titleTF.getText().toString()));
                parentStage.show();
            }
        });
        return scene;
    }

    static Scene setLearnScene(double WIDTH, double HEIGHT, MainStage parentStage) {
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

        gp.add(setButtonLearn(WIDTH, parentStage), 0, 0);
        gp.add(setButtonCreate(WIDTH, parentStage), 1, 0);
        gp.add(table, 0, 3, 2, 1);
        gp.add(buttonStartLearning, 0, 7);

        gp.add(new Label(), 0, 1);
        gp.add(new Label(), 0, 5);

        return scene;
    }

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
}
