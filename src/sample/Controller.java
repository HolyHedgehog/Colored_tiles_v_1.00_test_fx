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
    private static ColorCell oponentStartCell;
    private static ColorCell playerStartCell;
    private static ColorCell[][] gameFieldButtons = new ColorCell[10][10];
    //корневой компонент
    public Pane parrentPane;
    //компонент игрового поля
    public FlowPane gameField;
    //компонент органов управления
    public Pane controlButtonsPane;
    //компонента с кнопками вариантов хода
    public FlowPane moveButtons;
    //кнопка из controlButtonsPane
    public Button initializeField;

    /* Создаём кнопки поля, и помещяем их на панель.
     */
    public void newGameStart() {
        gameField.getChildren().removeAll(gameField.getChildren());
        Random randGen = new Random();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                ColorCell newCell = new ColorCell(i, j, randGen.nextInt(6));
                gameFieldButtons[i][j] = newCell;
                newCell.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onClickButton(newCell));
                gameField.getChildren().add(newCell);
            }
        }
        ((ColorCell) gameField.getChildren().get(0)).setOwner(1);
        ((ColorCell) gameField.getChildren().get(gameField.getChildren().size() - 1)).setOwner(2);

        playerStartCell = (ColorCell) gameField.getChildren().get(0);
        oponentStartCell = (ColorCell) gameField.getChildren().get(gameField.getChildren().size() - 1);

    }

    private FlowPane getGameField() {
        return gameField;
    }


    // обработчик нажатия на кнопки поля. запускается просчет хода игрока с цветом нажатой кнопки
    private void onClickButton(ColorCell clickedCell) {
//        List<ColorCell> cellsForMove = getCellsForPlayer(1);

//        for (ColorCell cell : cellsForMove) {
//            move(cell, clickedCell.getColor(), clickedCell.getOwner());
//        }
        move(playerStartCell, clickedCell.getColor(), 1);

    }

    private List<ColorCell> getCellsForPlayer(int owner) {

        List<ColorCell> result = new ArrayList<>();
        for (Node x : getGameField().getChildren()) {
            if ((x instanceof ColorCell) && (((ColorCell) x).getOwner() == owner)) {
                result.add((ColorCell) x);
            }
        }
        return result;
    }

    private void move(ColorCell cell, int color, int owner) {
        if (cell.isChanged()) {
            return;
        }
        cell.setColor(color);
        cell.setOwner(owner);
        cell.setChanged(true);
        //дальше определяем какие соседние клетки можно отожрать.
        cell.refresh();
        unchanged(cell);



    }

    private void unchanged(ColorCell cell) {
        cell.setChanged(false);
    }

}
