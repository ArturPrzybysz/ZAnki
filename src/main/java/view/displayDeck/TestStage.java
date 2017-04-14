package view.displayDeck;

import FiszkasOperations.Fiszka;
import FiszkasOperations.Manager;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

public class TestStage extends Stage {
    private String title;
    private ObservableList<Fiszka> fiszkas;
    private Scene sceneQuestion;
    private GridPane gpQuestion = new GridPane();
    private ImageView view = new ImageView();
    private TextField tfTitle;
    private TextField tfAuthor;
    private TextField tfDate;
    private int iterator = 0;
    private boolean[] availableFiszkas;
    private int answered = 0;

    public TestStage(ObservableList list, String title, double WIDTH, double HEIGHT) {
        title = title;
        setWidth(WIDTH);
        setHeight(HEIGHT);
        gpQuestion.setPadding(new Insets(10.0D, 50.0D, 20.0D, 50.0D));
        gpQuestion.setAlignment(Pos.CENTER);
        sceneQuestion = new Scene(gpQuestion);
        fiszkas = list;
        availableFiszkas = new boolean[fiszkas.size()];
        setTitle(title);
        updateQuestionScene();
        setScene(sceneQuestion);
    }

    void updateQuestionScene() {
        iterator = newIteratorValue();

        view.setImage(new Image((fiszkas.get(iterator % fiszkas.size())).getImage().toURI().toString()));
        view.setFitWidth(300);
        view.setPreserveRatio(true);

        tfTitle = new TextField();
        tfAuthor = new TextField();
        tfDate = new TextField();

        tfTitle.setPromptText("Tytuł");
        tfAuthor.setPromptText("Autor");
        tfDate.setPromptText("Data");

        tfAuthor.setAlignment(Pos.CENTER);
        tfTitle.setAlignment(Pos.CENTER);
        tfDate.setAlignment(Pos.CENTER);
        Button btShowAnswer = new Button("Sprawdź");
        btShowAnswer.setPrefSize(350.0D, 25.0D);
        btShowAnswer.setOnAction(event -> {
            setScene(setAnswerScene(tfTitle.getText(), tfAuthor.getText(), tfDate.getText()));
        });
        gpQuestion.add(view, 0, 0, 2, 1);
        gpQuestion.add(new Separator(), 0, 1, 3, 1);
        gpQuestion.add(tfTitle, 0, 2, 2, 1);
        gpQuestion.add(tfAuthor, 0, 3, 2, 1);
        gpQuestion.add(tfDate, 0, 4, 2, 1);
        gpQuestion.add(btShowAnswer, 0, 5);
    }

    Scene setAnswerScene(String title, String author, String date) {
        GridPane gpResult = new GridPane();

        gpResult.setPadding(new Insets(10.0D, 50.0D, 20.0D, 50.0D));
        gpResult.setAlignment(Pos.CENTER);

        Scene scene = new Scene(gpResult);

        boolean titleResult = answerEvaluation(title, (fiszkas.get(iterator % fiszkas.size())).getTitle());
        boolean authorResult = answerEvaluation(author, (fiszkas.get(iterator % fiszkas.size())).getAuthor());
        boolean dateResult = answerEvaluation(date, (fiszkas.get(iterator % fiszkas.size())).getDate());

        gpResult.add(view, 0, 0, 2, 1);
        gpResult.add(new Separator(), 0, 1, 2, 1);

        Label lbTitle = new Label(title);
        Label lbAuthor = new Label(author);
        Label lbDate = new Label(date);

        setColorsOnText(titleResult, authorResult, dateResult, lbTitle, lbAuthor, lbDate);

        Button nextPage = new Button("Nastęna");
        nextPage.setPrefSize(350, 25);
        nextPage.setOnAction((event) -> {
            nextPageAction(titleResult, authorResult, dateResult);
        });
        ToggleButton setDifficultyButton = new ToggleButton();
        setDifficultyButton.setAlignment(Pos.TOP_RIGHT);
        if ((fiszkas.get(iterator % fiszkas.size())).isHard().booleanValue()) {
            setDifficultyButton.setText("Oznaczony");
        } else {
            setDifficultyButton.setText("Nieoznaczony");
        }

        setDifficultyButton.setOnAction(event -> {
            difficultyButtonAction(setDifficultyButton);
        });

        gpResult.add(setDifficultyButton, 1, 6);
        gpResult.add(lbTitle, 1, 2);
        gpResult.add(new Label(((Fiszka) fiszkas.get(iterator % fiszkas.size())).getTitle()), 2, 2);
        gpResult.add(lbAuthor, 1, 3);
        gpResult.add(new Label(((Fiszka) fiszkas.get(iterator % fiszkas.size())).getAuthor()), 2, 3);
        gpResult.add(lbDate, 1, 4);
        gpResult.add(new Label(((Fiszka) fiszkas.get(iterator % fiszkas.size())).getDate()), 2, 4);
        gpResult.add(nextPage, 1, 5);
        return scene;
    }

    private void setColorsOnText(boolean titleResult, boolean authorResult, boolean dateResult, Label lbTitle, Label lbAuthor, Label lbDate) {
        if (!titleResult) {
            lbTitle.setTextFill(Color.RED);
        } else {
            lbTitle.setTextFill(Color.GREEN);
        }

        if (!authorResult) {
            lbAuthor.setTextFill(Color.RED);
        } else {
            lbAuthor.setTextFill(Color.GREEN);
        }

        if (!dateResult) {
            lbDate.setTextFill(Color.RED);
        } else {
            lbDate.setTextFill(Color.GREEN);
        }
    }

    private void updateDB() {
        Manager.addToStore(title, new ArrayList(fiszkas));
    }

    private void reverseDifficulty(Fiszka fiszka) {
        if (fiszka.isHard()) {
            fiszka.setEasy();
        } else {
            fiszka.setHard();
        }

    }

    private boolean answerEvaluation(String a, String b) {
        a = simplifiedString(a);
        b = simplifiedString(b);
        return a.equals(b);
    }

    private int newIteratorValue() {
        do {
            iterator += 5;
        } while (availableFiszkas[iterator % fiszkas.size()]);

        return iterator;
    }

    private String simplifiedString(String text) {
        String result = text.trim().toLowerCase();
        result = result
                .replace("ą", "a")
                .replace("ć", "c")
                .replace("ę", "e")
                .replace("ł", "l")
                .replace("ń", "n")
                .replace("ó", "o")
                .replace("ś", "s")
                .replace("ź", "z")
                .replace("ż", "z")
                .replace(" ", "")
                .replace(",", "")
                .replace(".", "")
                .replace("-", "")
                .replace("=", "")
                .replace("/", "");
        return result;
    }

    private void nextPageAction(boolean titleResult, boolean authorResult, boolean dateResult) {
        if (titleResult && authorResult && dateResult) {
            availableFiszkas[iterator % fiszkas.size()] = true;
            answered++;
            if (answered == fiszkas.size()) {
                close();
                updateDB();
                return;
            }
        }

        updateQuestionScene();
        setScene(sceneQuestion);
    }

    private void difficultyButtonAction(ToggleButton setDifficultyButton) {
        reverseDifficulty(fiszkas.get(iterator % fiszkas.size()));
        if ((fiszkas.get(iterator % fiszkas.size())).isHard()) {
            setDifficultyButton.setText("Oznaczony");
        } else {
            setDifficultyButton.setText("Nieoznaczony");
        }

        updateDB();
    }
}
