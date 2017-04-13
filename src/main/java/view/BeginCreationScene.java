package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;


class BeginCreationScene extends Scene {

    BeginCreationScene(GridPane gp, double width, double height, MainStage stage) {
        super(gp, width, height);

        gp.add(stage.setButtonLearn(), 0, 0);
        gp.add(stage.setButtonCreate(), 1, 0);

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
                stage.setScene(new CreationScene
                        (new GridPane(), width, height, stage, titleTF.getText().toString()));
            }
        });
    }
}
