package sample;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;


class ColorCell extends Button {

    private final int xCoord;

    private final int yCoord;

    private int color;

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
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> leftCkickEvent());
//        this.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> Controller.controllerListener(this));
    }


    private void leftCkickEvent() {
        System.out.println("i'am button with coords: " + xCoord + " " + yCoord + ". My owner is " + owner);

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
