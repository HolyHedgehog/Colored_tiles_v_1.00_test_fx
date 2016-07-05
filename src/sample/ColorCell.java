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

    public ColorCell(int x, int y, int color) {
        super();
        xCoord = x;
        yCoord = y;
        this.color = color;

        this.setPrefSize(40, 40);
        switch (color) {
            case 0:
                this.setStyle("-fx-background-color:red");
                break;
            case 1:
                this.setStyle("-fx-background-color:green");
                break;
            case 2:
                this.setStyle("-fx-background-color:blue");
                break;
            case 3:
                this.setStyle("-fx-background-color:teal");
                break;
            case 4:
                this.setStyle("-fx-background-color:magenta");
                break;
            case 5:
                this.setStyle("-fx-background-color:yellow");
                break;
            default:
                this.setStyle("-fx-background-color:black");
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
}
