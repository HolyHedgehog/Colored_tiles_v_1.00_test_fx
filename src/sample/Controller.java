package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

public class Controller {

    public Pane parrentPane;
    public FlowPane gameField;
    public Pane controlButtonsPane;
    public FlowPane moveButtons;

    public Button initializeField;

    public void InitializeFieldButton(ActionEvent actionEvent) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                gameField.getChildren().add(new ColorCell(i, j, 0));
            }
        }
        System.out.println(actionEvent);
    }
}
