import java.awt.*;

public class AirBalloon {

    public int xpos;                //the x position
    public int ypos;                //the y position
    public int width;
    public int height;
    public boolean isAlive;
    public int dx;
    public int dy;
    public Rectangle rec;
    public Image pic;

//    //movement booleans
//    public boolean right;
//    public boolean down;
//    public boolean left;
//    public boolean up;



    public AirBalloon (int dxParameter, int dyParameter, Image picParameter) {

        xpos = 300;
        ypos = 200;
        width = 50;
        height = 50;
        dx=dxParameter;
        dy=dyParameter;
        pic = picParameter;
        isAlive = true;
        rec=new Rectangle(xpos, ypos, width, height);

    }

    public void move () {
        xpos= xpos+dx;
        ypos = ypos+dy;

    }





}
