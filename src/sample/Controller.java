package sample;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Controller {

    public Pane parrentPane;

    public FlowPane gameField;

    public Pane controlButtonsPane;

    public FlowPane moveButtons;

    public Button initializeField;

    /* Создаём кнопки поля, и помещяем их на панель.
     */
    public void newGameStart() {
        gameField.getChildren().removeAll(gameField.getChildren());
        Random randGen = new Random();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                ColorCell newCell = new ColorCell(i, j, randGen.nextInt(6));
                newCell.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> move(newCell));
                gameField.getChildren().add(newCell);
            }
        }
        ((ColorCell) gameField.getChildren().get(0)).setOwner(1);
        ((ColorCell) gameField.getChildren().get(gameField.getChildren().size() - 1)).setOwner(2);


    }

    private FlowPane getGameField() {
        return gameField;
    }

    private void move(ColorCell cell) {
        getCellsForPlayer(cell);

    }

    private List<ColorCell> getCellsForPlayer(ColorCell cell) {
        System.out.println("i'am button with coords: " + cell.getxCoord() + " " + cell.getyCoord() + ". My owner is " + cell.getOwner());

        List<ColorCell> e = new ArrayList<>();
        for (Node x : getGameField().getChildren()) {
            if ((x instanceof ColorCell) && (((ColorCell) x).getOwner() == 1)) {
                e.add((ColorCell) x);
            }
        }


        return null;
    }

    private void isReady(ColorCell cell) {

    }

}
