import java.awt.*;

public class Intruder {

    public int xpos;                //the x position
    public int ypos;                //the y position
    public int width;
    public int height;
    public boolean isAlive;
    public int dx;
    public int dy;

    public Rectangle rec;
    public Image pic;

    public boolean isCrashing;

    public Intruder (int pXpos, int pYpos, int dxParameter, int dyParameter, Image picParameter){

        xpos = pXpos;
        ypos = pYpos;
        width = 50;
        height = 50;
        dx=dxParameter;
        dy=0;
        pic = picParameter;
        isAlive = false;
        rec=new Rectangle(xpos, ypos, width, height);
        isCrashing = false;


    }

    public void move (){
        xpos = xpos + dx;
        ypos = ypos + dy;

        rec=new Rectangle(xpos, ypos, width, height);

    }

}
