package org.przybysz.artur.fiszki;

import FiszkasOperations.DeckStore;
import FiszkasOperations.FileAddress;
import javafx.application.Application;
import javafx.stage.Stage;
import view.mainStage.MainStage;

import java.io.File;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage ignoredStage) throws Exception {

        DeckStore.init(new File(FileAddress.getDBAdress()));

        MainStage stage = new MainStage();

        stage.show();
    }
}

