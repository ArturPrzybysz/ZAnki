package view.mainStage;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainStage extends Stage {
    private final static double HEIGHT = 650, WIDTH = 650;
    private Scene learnScene, createScene;

    public MainStage() {
        MainStageScenes m = new MainStageScenes();
        learnScene = m.setLearnScene(WIDTH, HEIGHT, this);
        createScene = m.setBeginCreationScene(WIDTH, HEIGHT, this);

        setLearningScene();
    }

    public void setLearningScene() {
        setScene(learnScene);
    }

    public void setCreateScene() {
        setScene(createScene);
    }
}
