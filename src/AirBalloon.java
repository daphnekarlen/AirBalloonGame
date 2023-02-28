import java.awt.*;

public class AirBalloon {

    public double xpos;                //the x position
    public double ypos;                //the y position
    public int width;
    public int height;
    public boolean isAlive;
    public double dx;
    public double dy;
//    public double ddx = .1;
//    public double ddy = .1;

    public Rectangle rec;
    public Image pic;

    public boolean right;
    public boolean left;
    public boolean up;
    public boolean down;



    public AirBalloon (int dxParameter, int dyParameter, Image picParameter) {

        xpos = 300;
        ypos = 200;
        width = 100;
        height = 200;
        dx=dxParameter;
        dy=dyParameter;
//        ddx = .1;

        pic = picParameter;
        isAlive = true;
        rec=new Rectangle((int)xpos, (int)ypos, width, height);

    }

    public void move () {
        xpos = xpos+dx;
        System.out.println("dx = " + dx);
        ypos = ypos+dy;

        if(right == true){
            dx=6;
        }

        if(left == true){
            dx=-6;
        }

        if(up == true){
            dy=-6;
        }

        if(down == true){
            dy=6;
        }


        rec=new Rectangle((int)xpos, (int)ypos, width, height);

    }





}
