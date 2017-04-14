package view.mainStage;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainStage extends Stage {
    private final static double HEIGHT = 550, WIDTH = 670;
    private Scene learnScene, createScene;

    public MainStage() {
        learnScene = MainStageScenes.setLearnScene(WIDTH, HEIGHT, this);
        createScene = MainStageScenes.setBeginCreationScene(WIDTH, HEIGHT, this);

        setLearningScene();
    }

    void setLearningScene() {
        setScene(learnScene);
    }

    void setCreateScene() {
        setScene(createScene);
    }
}
