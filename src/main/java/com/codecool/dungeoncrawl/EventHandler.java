package com.codecool.dungeoncrawl;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

public class EventHandler {
    public static void displaySaveButton() {
        TextField nameInput = new TextField();
        Button save = new Button("Save");
        Button cancel = new Button("Cancel");
        VBox layout = new VBox(2);
        layout.getChildren().addAll(nameInput, save, cancel);
        Scene saveScene = new Scene(layout, 350, 150);
        Stage saveStage = new Stage();
        saveStage.setTitle("Save game");
        saveStage.setScene(saveScene);
        saveStage.show();
        save.setOnAction(e -> saveGame(saveStage, nameInput));
        cancel.setOnAction(e -> saveStage.close());

    }

    private static void saveGame(Stage saveStage, TextField nameInput) {
        String saveName = nameInput.getText();
        System.out.println(saveName);
        saveStage.close();
    }

    public static void displayLoadButton() {
        Optional<String> choice = getSaveChoiceFromUser();
        System.out.println(choice);
    }

    public static Optional<String> getSaveChoiceFromUser() {
        ChoiceDialog<String> choiceDialog = new ChoiceDialog<>();
        choiceDialog.setTitle("Load Game");
        choiceDialog.getItems().addAll("a","v");
        choiceDialog.setHeaderText("Choose a save to load!");
        choiceDialog.getDialogPane().setContentText("Save name: ");
        Optional<String> choice = choiceDialog.showAndWait();
        return choice;
    }
}
