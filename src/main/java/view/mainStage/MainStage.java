package view.mainStage;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainStage extends Stage {
    private final static double HEIGHT = 650, WIDTH = 650;
    private Scene learnScene, createScene;

    public MainStage() {
        setTitle("ZuzAnki");
        MainStageScenes m = new MainStageScenes();
        learnScene = m.setLearnScene(WIDTH, HEIGHT, this);
        createScene = m.setBeginCreationScene(WIDTH, HEIGHT, this);

        setLearningScene();
    }

    public void setLearningScene() {
        MainStageScenes scenes = new MainStageScenes();
        setScene(scenes.setLearnScene(WIDTH,HEIGHT,this));
    }

    public void setCreateScene() {
        setScene(createScene);
    }
}
