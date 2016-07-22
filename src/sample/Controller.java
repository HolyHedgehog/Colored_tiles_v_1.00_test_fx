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

        playerStartCell = gameFieldButtons[0][0];
        oponentStartCell = gameFieldButtons[9][9];

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
        move(clickedCell.getColor(), ColorCell.PLAYER);
        unchanged();
//        move(oponentStartCell,randomColor(),ColorCell.OPONENT);

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

    private void move(int color, int owner) {
        for (ColorCell xxx : getCellsForPlayer(ColorCell.PLAYER)) {

            xxx.setColor(color);
            xxx.setChanged(true);
            xxx.refresh();
            move2(xxx, color, owner);
        }
    }

    //дальше определяем какие соседние клетки можно отожрать.
    private void move2(ColorCell cell, int color, int owner) {

        int x = cell.getxCoord();
        int y = cell.getyCoord();


        if ((x - 1 >= 0) && (gameFieldButtons[x - 1][y].getOwner() == 0) && (gameFieldButtons[x - 1][y].getColor() == cell.getColor()) && !(gameFieldButtons[x - 1][y].isChanged())) {
            gameFieldButtons[x - 1][y].setChanged(true);
            gameFieldButtons[x - 1][y].setColor(color);
            gameFieldButtons[x - 1][y].setOwner(owner);

            move2(gameFieldButtons[x - 1][y], cell.getColor(), cell.getOwner());
        }
        if ((x + 1 <= 9) && (gameFieldButtons[x + 1][y].getOwner() == 0) && (gameFieldButtons[x + 1][y].getColor() == cell.getColor()) && !(gameFieldButtons[x + 1][y].isChanged())) {
            gameFieldButtons[x + 1][y].setChanged(true);
            gameFieldButtons[x + 1][y].setColor(color);
            gameFieldButtons[x + 1][y].setOwner(owner);
            move2(gameFieldButtons[x + 1][y], cell.getColor(), cell.getOwner());
        }
        if ((y - 1 >= 0) && (gameFieldButtons[x][y - 1].getOwner() == 0) && (gameFieldButtons[x][y - 1].getColor() == cell.getColor()) && !(gameFieldButtons[x][y - 1].isChanged())) {
            gameFieldButtons[x][y - 1].setChanged(true);
            gameFieldButtons[x][y - 1].setColor(color);
            gameFieldButtons[x][y - 1].setOwner(owner);
            move2(gameFieldButtons[x][y - 1], cell.getColor(), cell.getOwner());
        }
        if ((y + 1 <= 9) && (gameFieldButtons[x][y + 1].getOwner() == 0) && (gameFieldButtons[x][y + 1].getColor() == cell.getColor()) && !(gameFieldButtons[x][y + 1].isChanged())) {
            gameFieldButtons[x][y + 1].setChanged(true);
            gameFieldButtons[x][y + 1].setColor(color);
            gameFieldButtons[x][y + 1].setOwner(owner);
            move2(gameFieldButtons[x][y + 1], cell.getColor(), cell.getOwner());
        }

    }

    private void unchanged() {
        for (Node cell : getGameField().getChildren()) {
            ColorCell colorCell = (ColorCell) cell;
            colorCell.setChanged(false);
        }
    }

}
