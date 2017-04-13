package view;

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
        this.title = title;
        this.setWidth(WIDTH);
        this.setHeight(HEIGHT);
        this.gpQuestion.setPadding(new Insets(10.0D, 50.0D, 20.0D, 50.0D));
        this.gpQuestion.setAlignment(Pos.CENTER);
        this.sceneQuestion = new Scene(this.gpQuestion);
        this.fiszkas = list;
        this.availableFiszkas = new boolean[this.fiszkas.size()];
        this.setTitle(title);
        this.updateQuestionScene();
        this.setScene(this.sceneQuestion);
    }

    void updateQuestionScene() {
        this.iterator = this.newIteratorValue();
        this.view.setImage(new Image((this.fiszkas.get(this.iterator % this.fiszkas.size())).getImage().toURI().toString()));
        this.view.setFitWidth(300.0D);
        this.view.setPreserveRatio(true);
        this.tfTitle = new TextField();
        this.tfAuthor = new TextField();
        this.tfDate = new TextField();
        this.tfTitle.setPromptText("Tytuł");
        this.tfAuthor.setPromptText("Autor");
        this.tfDate.setPromptText("Data");
        this.tfAuthor.setAlignment(Pos.CENTER);
        this.tfTitle.setAlignment(Pos.CENTER);
        this.tfDate.setAlignment(Pos.CENTER);
        Button btShowAnswer = new Button("Sprawdź");
        btShowAnswer.setPrefSize(350.0D, 25.0D);
        btShowAnswer.setOnAction(event -> {
            this.setScene(this.setAnswerScene(this.tfTitle.getText(), this.tfAuthor.getText(), this.tfDate.getText()));
        });
        this.gpQuestion.add(this.view, 0, 0, 2, 1);
        this.gpQuestion.add(new Separator(), 0, 1, 3, 1);
        this.gpQuestion.add(this.tfTitle, 0, 2, 2, 1);
        this.gpQuestion.add(this.tfAuthor, 0, 3, 2, 1);
        this.gpQuestion.add(this.tfDate, 0, 4, 2, 1);
        this.gpQuestion.add(btShowAnswer, 0, 5);
    }

    Scene setAnswerScene(String title, String author, String date) {
        GridPane gpResult = new GridPane();
        gpResult.setPadding(new Insets(10.0D, 50.0D, 20.0D, 50.0D));
        gpResult.setAlignment(Pos.CENTER);
        Scene scene = new Scene(gpResult);
        boolean titleResult = this.answerEvaluation(title, ((Fiszka) this.fiszkas.get(this.iterator % this.fiszkas.size())).getTitle());
        boolean authorResult = this.answerEvaluation(author, ((Fiszka) this.fiszkas.get(this.iterator % this.fiszkas.size())).getAuthor());
        boolean dateResult = this.answerEvaluation(date, ((Fiszka) this.fiszkas.get(this.iterator % this.fiszkas.size())).getDate());
        gpResult.add(this.view, 0, 0, 2, 1);
        gpResult.add(new Separator(), 0, 1, 2, 1);
        Label lbTitle = new Label(title);
        Label lbAuthor = new Label(author);
        Label lbDate = new Label(date);
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

        Button nextPage = new Button("Nastęna");
        nextPage.setPrefSize(350.0D, 25.0D);
        nextPage.setOnAction((event) -> {
            this.nextPageAction(titleResult, authorResult, dateResult);
        });
        ToggleButton setDifficultyButton = new ToggleButton();
        setDifficultyButton.setAlignment(Pos.TOP_RIGHT);
        if ((this.fiszkas.get(this.iterator % this.fiszkas.size())).isHard().booleanValue()) {
            setDifficultyButton.setText("Oznaczony");
        } else {
            setDifficultyButton.setText("Nieoznaczony");
        }

        setDifficultyButton.setOnAction(event -> {
            this.difficultyButtonAction(setDifficultyButton);
        });
        gpResult.add(setDifficultyButton, 1, 6);
        gpResult.add(lbTitle, 1, 2);
        gpResult.add(new Label(((Fiszka) this.fiszkas.get(this.iterator % this.fiszkas.size())).getTitle()), 2, 2);
        gpResult.add(lbAuthor, 1, 3);
        gpResult.add(new Label(((Fiszka) this.fiszkas.get(this.iterator % this.fiszkas.size())).getAuthor()), 2, 3);
        gpResult.add(lbDate, 1, 4);
        gpResult.add(new Label(((Fiszka) this.fiszkas.get(this.iterator % this.fiszkas.size())).getDate()), 2, 4);
        gpResult.add(nextPage, 1, 5);
        return scene;
    }

    private void updateDB() {
        Manager.addToStore(this.title, new ArrayList(this.fiszkas));
    }

    private void reverseDifficulty(Fiszka fiszka) {
        if (fiszka.isHard()) {
            fiszka.setEasy();
        } else {
            fiszka.setHard();
        }

    }

    private boolean answerEvaluation(String a, String b) {
        a = this.simplifiedString(a);
        b = this.simplifiedString(b);
        return a.equals(b);
    }

    private int newIteratorValue() {
        do {
            this.iterator += 5;
        } while (this.availableFiszkas[this.iterator % this.fiszkas.size()]);

        return this.iterator;
    }

    private String simplifiedString(String text) {
        String result = text.trim().toLowerCase();
        result = result.replace("ą", "a").replace("ć", "c").replace("ę", "e").replace("ł", "l").replace("ń", "n").replace("ó", "o").replace("ś", "s").replace("ź", "z").replace("ż", "z").replace(" ", "").replace(",", "").replace(".", "").replace("-", "").replace("=", "").replace("/", "");
        return result;
    }

    private void nextPageAction(boolean titleResult, boolean authorResult, boolean dateResult) {
        if (titleResult && authorResult && dateResult) {
            this.availableFiszkas[this.iterator % this.fiszkas.size()] = true;
            ++this.answered;
            if (this.answered == this.fiszkas.size()) {
                this.close();
                this.updateDB();
                return;
            }
        }

        this.updateQuestionScene();
        this.setScene(this.sceneQuestion);
    }

    private void difficultyButtonAction(ToggleButton setDifficultyButton) {
        this.reverseDifficulty(this.fiszkas.get(this.iterator % this.fiszkas.size()));
        if ((this.fiszkas.get(this.iterator % this.fiszkas.size())).isHard()) {
            setDifficultyButton.setText("Oznaczony");
        } else {
            setDifficultyButton.setText("Nieoznaczony");
        }

        this.updateDB();
    }
}
