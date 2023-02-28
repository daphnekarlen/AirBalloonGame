import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;

public class BalloonWorld implements Runnable, KeyListener, MouseListener {


    final int WIDTH = 1000;
    final int HEIGHT = 650;


    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;
    public Obstacles[] balloon;
    public BufferStrategy bufferStrategy;
    public boolean gameStart = false;

    public long startTime;
    public long currentTime;
    public long elapsedTime;

    public boolean startTimer;

    public boolean gameEnd = false;

    public AirBalloon airBalloon;
    public Image airBalloonPic;
    public Image balloonPic;

    public Image backgroundPic;



    public static void main(String[] args) {
        BalloonWorld myGame = new BalloonWorld ();
        new Thread(myGame).start();
    }


    public BalloonWorld(){
        setUpGraphics();
        canvas.addKeyListener(this);
        canvas.addMouseListener(this);

        airBalloonPic = Toolkit.getDefaultToolkit().getImage("airballoon.png");
        airBalloon = new AirBalloon(0,0,airBalloonPic);

        balloonPic = Toolkit.getDefaultToolkit().getImage("balloonobstacle.png");
        balloon = new Obstacles[10000];
        for(int x=0; x<balloon.length; x++){
            balloon[x] = new Obstacles( (int)(Math.random()*1000),700, 0,0, balloonPic);
        }

        backgroundPic = Toolkit.getDefaultToolkit().getImage("mountain.jpeg");

    }




    public void run(){
        while(true){
            if(gameStart == true) {
                triggerBalloon();
                moveThings();
                checkIntersections();
                currentTime = System.currentTimeMillis();
                elapsedTime = currentTime - startTime; // calculating elapsed time
            }

            render();
            pause(20);
        }

    }



    public void moveThings() {
        airBalloon.move();

        for (int x = 0; x < balloon.length; x++) {
            balloon[x].move();
        }
    }

    public void triggerBalloon(){
        for(int x=0; x<balloon.length; x++){
          if(balloon[x].isAlive == false){
              double r = Math.random();
              if(r>0.99999){
                  balloon[x].isAlive = true;
                  balloon[x].dy = -8;
              }
          }


        }
    }



    public void checkIntersections(){
        for(int x=0; x<balloon.length; x++){
            if(balloon[x].rec.intersects(airBalloon.rec)) {
                balloon[x].isAlive = false;
            }
        }
    }

    public void render(){
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        if(gameStart == false){
            g.setColor(Color.BLUE);
            g.fillRect(0,0, WIDTH, HEIGHT);
            g.setColor(Color.BLACK);
            g.setFont(new Font ("TimesRoman", Font.BOLD, 25));
            g.drawString("WELCOME TO POP BALLOONS", 350,250);
            g.drawString("Try to collect as many ballons as you can in 1 minute", 350, 300);
            g.drawString("Press Enter to Start", 350, 350);
        } // draw start screen

        else {
            g.drawImage(backgroundPic, 0, 0, WIDTH, HEIGHT, null);
            g.drawImage(airBalloonPic, (int) airBalloon.xpos, (int) airBalloon.ypos, airBalloon.width, airBalloon.height, null);
//        g.drawRect(airBalloon.xpos, airBalloon.ypos, airBalloon.width, airBalloon.height);

            for (int x = 0; x < balloon.length; x++) {
                if (balloon[x].isAlive == true) {
                    g.drawImage(balloon[x].pic, balloon[x].xpos, balloon[x].ypos, balloon[x].width, balloon[x].height, null);
                }
            }
            g.drawString(45-elapsedTime/1000+"", 700,50);

        }
        g.dispose();
        bufferStrategy.show();
    }

    @Override
    public void keyTyped(KeyEvent event) {

    }

    public void keyPressed(KeyEvent event) {
        char key = event.getKeyChar();     //gets the character of the key pressed
        int keyCode = event.getKeyCode();//gets the keyCode (an integer) of the key pressed
        System.out.println("Key Pressed: " + key + "  Code: " + keyCode);

        if(keyCode == 10){
            gameStart = true;
        }

        if(keyCode == 39){ // R arrow
            airBalloon.right = true;
            airBalloon.dx=6;
            System.out.println("setting dx to 5");
        }

        if(keyCode == 37){ // L arrow
            airBalloon.left = true;
            airBalloon.dx=-6;
        }

        if(keyCode == 38){ // Up arrow
            airBalloon.up = true;
            airBalloon.dy=-6;
        }

        if(keyCode == 40){ // Down arrow
            airBalloon.down = true;
            airBalloon.dy=6;
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        char key = event.getKeyChar();
        int keyCode = event.getKeyCode();


        if(keyCode == 39){ // R arrow
            airBalloon.right = false;
            airBalloon.dx=0;
        }

        if(keyCode == 37){ // L arrow
            airBalloon.left = false;
            airBalloon.dx=0;
        }

        if(keyCode == 38){ // Up arrow
            airBalloon.up = false;
            airBalloon.dy=0;
        }

        if(keyCode == 40){ // Down arrow
            airBalloon.down = false;
            airBalloon.dy=0;
        }



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
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
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