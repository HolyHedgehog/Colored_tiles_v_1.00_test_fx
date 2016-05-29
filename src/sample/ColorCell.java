package sample;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.util.Random;


public class ColorCell extends Button {
    private int XCoord;
    private int YCoord;
    private int Color;
    private int Owner;


    public ColorCell(int x, int y, int color) {
        super();
        XCoord = x;
        YCoord = y;
        Color = color;
        this.setPrefSize(40, 40);
        Random r = new Random();
        switch (r.nextInt(6)) {
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
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> click());
    }


    private void click() {
        System.out.println("i'am button with coords: " + XCoord + " " + YCoord);

    }

}
