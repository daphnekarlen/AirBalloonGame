import java.awt.*;

public class Obstacles {


    public int xpos;                //the x position
    public int ypos;                //the y position
    public int width;
    public int height;
    public boolean isAlive;
    public int dx;
    public int dy;
    public Rectangle rec;
    public Image pic;

    public Obstacles (int pXpos, int pYpos, int dxParameter, int dyParameter, Image picParameter){

        xpos = pXpos;
        ypos = pYpos;
        width = 50;
        height = 50;
        dx=dxParameter;
        dy=0;
        pic = picParameter;
        isAlive = true;
        rec=new Rectangle(xpos, ypos, width, height);


    }

    public void move (){
//        balloon = new int[200];
//
//        for (int i = 0; i < balloon.length; i++) {
//            balloon[i] = (int) (Math.random() * 100);
//        }

        }


    }
