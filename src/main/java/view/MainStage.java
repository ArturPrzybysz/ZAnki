package view;

import FiszkasOperations.Manager;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainStage extends Stage {
    private final static double HEIGHT = 550, WIDTH = 670;
    private Scene learnScene, createScene; //'main' scenes
    private Manager manager = new Manager();

    public MainStage() {
        learnScene = MainStageScenes.setLearnScene(WIDTH, HEIGHT, this);
        createScene = MainStageScenes.setBeginCreationScene(WIDTH, HEIGHT, this);
    }

    public void showLearnScene() {
        setScene(learnScene);
    }

    void setLearningScene() {
        this.setScene(learnScene);
    }

    void setCreateScene() {
        this.setScene(createScene);
    }

}
