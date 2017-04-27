package sample;

import javafx.scene.control.Button;


class ColorCell extends Button {
    //координата по иксу
    private final int xCoord;
    //координата по игрику
    private final int yCoord;
    //цвет кнопки
    private int color;
    //принадлежность к какому либо игроку. 1 - игрок. 2 - комп(противник)
    private int owner;
    //маркер. вызывался ли Controller.move() для этой ячейки.
    private boolean changed;

    public ColorCell(int x, int y, int color) {
        super();
        xCoord = x;
        yCoord = y;
        this.color = color;

        this.setPrefSize(40, 40);
        switch (color) {
            case 0:
                this.getStyleClass().add("RedCell");
                break;
            case 1:
                this.getStyleClass().add("GreenCell");
                break;
            case 2:
                this.getStyleClass().add("BlueCell");
                break;
            case 3:
                this.getStyleClass().add("CyanCell");
                break;
            case 4:
                this.getStyleClass().add("MagentaCell");
                break;
            case 5:
                this.getStyleClass().add("YellowCell");
                break;
            default:
                this.getStyleClass().add("DefaultCell");
                break;
        }
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(final int owner) {
        this.owner = owner;
    }

    public int getColor() {
        return color;
    }

    public void setColor(final int color) {
        this.color = color;
    }

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public void refresh() {
        this.getStyleClass().clear();
        switch (color) {
            case 0:
                this.getStyleClass().add("RedCell");
                break;
            case 1:
                this.getStyleClass().add("GreenCell");
                break;
            case 2:
                this.getStyleClass().add("BlueCell");
                break;
            case 3:
                this.getStyleClass().add("CyanCell");
                break;
            case 4:
                this.getStyleClass().add("MagentaCell");
                break;
            case 5:
                this.getStyleClass().add("YellowCell");
                break;
            default:
                this.getStyleClass().add("DefaultCell");
                break;
        }
    }

    public class Property {
        public static final int NEUTRAL = 0;
        public static final int PLAYER = 1;
        public static final int OPONENT = 2;
    }
}
