package org.przybysz.artur.fiszki;

import FiszkasOperations.DeckStore;
import javafx.application.Application;
import javafx.stage.Stage;
import view.MainStage;

import java.io.File;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage ignoredStage) throws Exception {

        DeckStore.init(new File("/fiszki/dataBaseNajnowsza"));

        MainStage stage = new MainStage();

        stage.showLearnScene();
        stage.show();
    }
}

