package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

public class Controller {

    private static final int COLORQUANTITY = 6;

    private static final int FIELDSIZE = 10;

    // стартовая Ячейка опонента.
    private static ColorCell oponentStartCell;

    // стартовая ячейка игрока
    private static ColorCell playerStartCell;

    private static ColorCell[][] gameFieldButtons = new ColorCell[FIELDSIZE][FIELDSIZE];

    private static int[] perfmove = new int[COLORQUANTITY];

    // корневой компонент
    public Pane parrentPane;

    // компонент игрового поля
    public FlowPane gameField;

    // компонент органов управления
    public Pane controlButtonsPane;

    // компонента с кнопками вариантов хода
    public FlowPane moveButtons;

    // кнопка из controlButtonsPane
    public Button initializeField;

    // тестовый лейбл. вывод информации по партии.
    public Label label1;

    /*
     * Создаём кнопки поля, и помещяем их на панель.
     */
    public void newGameStart() {
        moveCounter = 0;
        playerCellsCounter = 1;
        oponentCellsCounter = 1;
        gameField.getChildren().removeAll(gameField.getChildren());

        Random randGen = new Random();

        for (int i = 0; i < FIELDSIZE; i++) {
            for (int j = 0; j < FIELDSIZE; j++) {

                ColorCell newCell = new ColorCell(i, j, randGen.nextInt(COLORQUANTITY));
                gameFieldButtons[i][j] = newCell;
                newCell.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> onClickButton(newCell));
                gameField.getChildren().add(newCell);

            }
        }
        ((ColorCell) gameField.getChildren().get(0)).setOwner(1);
        ((ColorCell) gameField.getChildren().get(gameField.getChildren().size() - 1)).setOwner(2);

        playerStartCell = gameFieldButtons[0][0];
        oponentStartCell = gameFieldButtons[9][9];

        if (playerStartCell.getColor() == oponentStartCell.getColor()) {
            oponentStartCell.setColor(randomColor());
            oponentStartCell.refresh();
        }

        recurseMove(playerStartCell, playerStartCell.getColor(), ColorCell.Property.PLAYER);
        recurseMove(oponentStartCell, oponentStartCell.getColor(), ColorCell.Property.OPONENT);
    }

    private FlowPane getGameField() {
        return gameField;
    }

    // обработчик нажатия на кнопки поля. запускается просчет хода игрока с цветом нажатой кнопки
    private void onClickButton(final ColorCell clickedCell) {
        if ((clickedCell.getColor() != playerStartCell.getColor())
                && (clickedCell.getColor() != oponentStartCell.getColor())) {
            moveCounter++;
            preRecurseMove(clickedCell.getColor(), ColorCell.Property.PLAYER);
            preRecurseMove(getPerfMove(), ColorCell.Property.OPONENT);
            printStatus();
        }
    }

    private List<ColorCell> getCellsForPlayer(final int owner) {

        List<ColorCell> result = new ArrayList<>();
        for (Node x : getGameField().getChildren()) {
            if ((x instanceof ColorCell) && (((ColorCell) x).getOwner() == owner)) {
                result.add((ColorCell) x);
            }
        }
        return result;
    }

    // первая половина хода. не вышло уместить в 1 метод.
    private void preRecurseMove(final int color, final int owner) {
        for (ColorCell cell : getCellsForPlayer(owner)) {
            cell.setColor(color);
            cell.setChanged(true);
            cell.refresh();
            recurseMove(cell, color, owner);
        }
        setAllCellsUnchanged();
    }

    // дальше определяем какие соседние клетки можно отожрать.
    private void recurseMove(final ColorCell cell, final int color, final int owner) {

        int x = cell.getxCoord();
        int y = cell.getyCoord();

        if ((x - 1 >= 0) && CellCanChange(x - 1, y, color)) {
            CellChange(x - 1, y, color, owner);
        }
        if ((x + 1 <= 9) && CellCanChange(x + 1, y, color)) {
            CellChange(x + 1, y, color, owner);
        }

        if ((y - 1 >= 0) && CellCanChange(x, y - 1, color)) {
            CellChange(x, y - 1, color, owner);
        }

        if ((y + 1 <= 9) && CellCanChange(x, y + 1, color)) {
            CellChange(x, y + 1, color, owner);
        }
    }

    private void setAllCellsUnchanged() {
        for (Node cell : getGameField().getChildren()) {
            ColorCell colorCell = (ColorCell) cell;
            colorCell.setChanged(false);
        }
    }

    private int randomColor() {
        Random rand = new Random();
        int result;
        do {
            result = rand.nextInt(COLORQUANTITY);
        } while (result == playerStartCell.getColor() || result == oponentStartCell.getColor());
        return result;
    }

    private boolean CellCanChange(final int x, final int y, final int color) {

        return (gameFieldButtons[x][y].getOwner() == ColorCell.Property.NEUTRAL)
                && (gameFieldButtons[x][y].getColor() == color)
                && !(gameFieldButtons[x][y].isChanged());
    }

    private void CellChange(final int x, final int y, final int color, final int owner) {
        switch (owner) {
            case 1:
                playerCellsCounter++;
                break;
            case 2:
                oponentCellsCounter++;
                break;
            default:
                break;
        }
        perfmove[color]++;
        gameFieldButtons[x][y].setChanged(true);
        gameFieldButtons[x][y].setOwner(owner);
        recurseMove(gameFieldButtons[x][y], color, owner);
    }

    private int getPerfMove() {
        int result = 0;
        int index;
        perfectMove();
        setAllCellsUnchanged();
        int ciclecounter = 0; /// костыль от вечных циклов

        do {
            index = 0; //
            for (int i = 0; i < COLORQUANTITY; i++) {
                if (perfmove[i] > result) {
                    result = perfmove[i];
                    index = i;
                }
            }

            if ((index == playerStartCell.getColor()) || (index == oponentStartCell.getColor())) {
                perfmove[index] = 0;
                result = 0;
            }

            if (ciclecounter == 10) { // если насчитали 10 итераций - выход с рандомным значением.
                return randomColor();
            }

            ciclecounter++;

        } while ((index == playerStartCell.getColor()) || (index == oponentStartCell.getColor()));

        return index;
    }

    private void perfectMove() {
        perfmove = new int[COLORQUANTITY];
        for (int i = 0; i < COLORQUANTITY; i++) {
            for (ColorCell cell : getCellsForPlayer(oponentStartCell.getOwner())) {
                recurseMove(cell, i, ColorCell.Property.NEUTRAL);
            }
        }
    }

    // отсчитываем кол-во ходов.
    private static int moveCounter = 0;

    // отсчитываем кол-во захваченных ячеек.
    private static int playerCellsCounter;

    // отсчитываем кол-во захваченных ячеек.
    private static int oponentCellsCounter;

    private void printStatus() {
        // вариант рабочий, но в целях оптимизации попробую пробросить из рекурсмува.
        // получилось. не высчитываем каждый раз количество ячеек, а берём значение формирующиеся с учетом вызовов
        // CellChange()
        // int a = 0, oponentCellsCounter = 0, c = 0;
        // for (Node x : gameField.getChildren()) {
        // if (x instanceof ColorCell) {
        // switch (((ColorCell) x).getOwner()) {
        // case 1:
        // a++;
        // break;
        // case 2:
        // oponentCellsCounter++;
        // break;
        // case 0:
        // c++;
        // break;
        // default:
        // System.out.println("ерунда какая то. не должно так быть.Controller#printStatus");
        // }
        // }
        // }
        System.out.println("Move №" + moveCounter);
        System.out.println("Player - "
                + playerCellsCounter
                + "\n\r"
                + "Comp - "
                + oponentCellsCounter
                + "\n\r"
                + "Neutral - "
                + (100 - (oponentCellsCounter + playerCellsCounter)));
        System.out.println("--- ✄ -----------------------");
        label1.setText("Player - "
                + playerCellsCounter
                + "\n\r"
                + "Comp - "
                + oponentCellsCounter
                + "\n\r"
                + "Neutral - "
                + (100 - (oponentCellsCounter + playerCellsCounter)));

    }

}
