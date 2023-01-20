import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.ImageObserver;

public class BalloonWorld implements Runnable, KeyListener, MouseListener {


    final int WIDTH = 1000;
    final int HEIGHT = 650;


    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;

    public static void main(String[] args) {
        BalloonWorld myGame = new BalloonWorld ();
        new Thread(myGame).start();
    }


    public BalloonWorld(){
        setUpGraphics();
        canvas.addKeyListener(this);
        canvas.addMouseListener(this);


    }

    public void run(){
        while(true){
            moveThings();
            checkIntersections();
            render();
            pause(20);
        }

    }


    public void moveThings(){
        airBalloon.move();
        balloon1.move();
        balloon2.move();
        balloon3.move();
        balloon4.move();
        balloon5.move();
        balloon6.move();

    }


    public BufferStrategy bufferStrategy;

    public AirBalloon airBalloon;
    public Obstacles balloon1;
    public Obstacles balloon2;
    public Obstacles balloon3;
    public Obstacles balloon4;
    public Obstacles balloon5;
    public Obstacles balloon6;


    public void checkIntersections(){}

    public void render(){
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);


        g.dispose();
        bufferStrategy.show();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent event) {}

    @Override
    public void keyReleased(KeyEvent e) {
    }
    public void pause(int time) {
        //sleep
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
    }

    public void setUpGraphics() {

        frame = new JFrame("BalloonWorld");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension());  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");

    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}