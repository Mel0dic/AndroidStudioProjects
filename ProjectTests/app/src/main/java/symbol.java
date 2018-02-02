import android.graphics.Color;

/**
 * Created by bengr on 01/02/2018.
 */

public class symbol{

    private int FONT_SIZE;
    private int row = 0;
    private int column = 0;
    private int switchInterval = 0;
    private String value = "";


    //Initialise symbol
    Symbol(int fontSize){
        FONT_SIZE = fontSize;
    }

    public void setPosition(int symbolColumn, int position, int yPos){
        //Set the row and column positions
        row = (yPos*(FONT_SIZE / 2)) - (position * FONT_SIZE);
        column = symbolColumn*FONT_SIZE;
    }

    public void setRandomSymbol(){
        //If switchInterval is < 5 set new char in label set the text 5% chance
        if(switchInterval < 5){
            value = "" + (char)(0x30A0 + (Math.random() * 96));
            //TODO set text to random char symbol
        }
        //Generate new random number between 0 and 100
        switchInterval = (int)(Math.random() * 100);
    }

    public void rain(int resetPos, int speed){
        //If the label leaves the bottom of the panel set is position to resetPos else add speed to its position
        row = row > /*TODO getHeight method here*/ ? resetPos : (row + speed);
        //Set the position
        //TODO set position
        //Set new Symbol
        setRandomSymbol();
    }







    public int getRow(){
        return row;
    }

    public int getColumn(){
        return column;
    }

}
