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
    public Targets[] balloon;
    public BufferStrategy bufferStrategy;
    public boolean gameStart = false;

    public long startTime;
    public long currentTime;
    public long elapsedTime;
    public int timeRemaining;

    public int score;
    public int highScore;

    public boolean startTimer;

    public AirBalloon airBalloon;
    public Image airBalloonPic;
    public Image balloonPic;

    public Intruder[] intruder;
    public Image intruderPic;

    public Image backgroundPicOne;

    public Image backgroundPic;

    public SoundFile themeMusic;



    public static void main(String[] args) {
        BalloonWorld myGame = new BalloonWorld ();
        new Thread(myGame).start();
    }


    public BalloonWorld(){
        setUpGraphics();
        canvas.addKeyListener(this);
        canvas.addMouseListener(this);

        airBalloonPic = Toolkit.getDefaultToolkit().getImage("airballoon.png");
        airBalloon = new AirBalloon(0,0,airBalloonPic); // constructing air balloon
        backgroundPicOne = Toolkit.getDefaultToolkit().getImage("background start pic.jpeg"); // constructing backgroudn pic

        balloonPic = Toolkit.getDefaultToolkit().getImage("balloonobstacle.png");
        balloon = new Targets[10000]; // constructing amount of blue ballons taht are part of the array


        intruderPic = Toolkit.getDefaultToolkit().getImage("intruder.png");
        intruder = new Intruder[1000]; // construct birds and the amount that is part of the array

        reset(); // construct birds and little balloons

        backgroundPic = Toolkit.getDefaultToolkit().getImage("mountain.jpeg");

        themeMusic = new SoundFile("Lake Shore on a Windy Day.wav");

        themeMusic.loop(); // sound affects

    }




    public void run(){
        while(true){
            if(gameStart == true) {
                triggerBalloon();
                triggerIntruder();
                moveThings();
                checkIntersections();
                startTimer = true;
                currentTime =  System.currentTimeMillis();
                elapsedTime = currentTime - startTime; // calculating elapsed time
                timeRemaining = (int)(45-elapsedTime/1000);
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

        for (int i = 0; i < intruder.length; i++) {
            intruder[i].move();
        }
    }

    public void triggerBalloon(){ // calling the array to start so that the balloons float upward
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

    public void triggerIntruder(){ // calling the array of birds to start so they float upwards
        for(int i=0; i<intruder.length; i++){
            if(intruder[i].isAlive == false){
                double r = Math.random();
                if(r>0.99999){
                    intruder[i].isAlive = true;
                    intruder[i].dy = -10;
                }
            }
        }
    }



    public void checkIntersections(){ // the intersections between the blue balloons, air balloon, and the blue birds
        for(int x=0; x<balloon.length; x++){
            if(balloon[x].rec.intersects(airBalloon.rec) && balloon[x].isCrashing == false) {
                balloon[x].isAlive = false;
                score++;
                balloon[x].isCrashing = true;
            }
            if(!balloon[x].rec.intersects(airBalloon.rec)){
                balloon[x].isCrashing = false;
            }
        }

        for(int i=0; i<intruder.length; i++){
            if(intruder[i].rec.intersects(airBalloon.rec)){
              gameStart = false;
              reset();
            }
        }

        if(highScore < score){ // the game will have a high score that updates if beaten
            highScore = score;
        }
    }

    public void reset() { // resets balloons and birds to the bottom of screen
        for(int x=0; x<balloon.length; x++){
            balloon[x] = new Targets( (int)(Math.random()*1000),700, 0,0, balloonPic);
        }

        for (int i=0; i<intruder.length; i++) {
            intruder[i] = new Intruder((int) (Math.random() * 1000), 700, 0, 0, intruderPic);
        }
    }

    public void render(){
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        if(gameStart == false){
            System.out.println("Rendering intro screen, gameStart: " + gameStart);
            g.drawImage(backgroundPicOne,0,0,WIDTH, HEIGHT, null);
            g.setFont(new Font ("TimesRoman", Font.BOLD, 25));
            g.drawString("WELCOME TO POP BALLOONS", 300,250);
            g.drawString("Try to collect as many ballons as", 350, 300);
            g.drawString("you can in 45 seconds", + 350, 325);
            g.drawString("Press Enter to Start", 400, 400);
            g.setFont(new Font ("TimesRoman", Font.BOLD, 20));
            // information inside air balloon that tells how to play game
            g.drawString("Score: " + score , 800,100);
            startTimer = false;
            g.setFont(new Font ("TimesRoman", Font.BOLD, 20));
            g.drawString("High Score: " + highScore, 800,450);
        } // draw start screen

        else if (gameStart == true) {
            System.out.println("Actual game playing");
            g.drawImage(backgroundPic, 0, 0, WIDTH, HEIGHT, null);
            g.drawImage(airBalloonPic, (int) airBalloon.xpos, (int) airBalloon.ypos, airBalloon.width, airBalloon.height, null);

            g.setFont(new Font ("TimesRoman", Font.BOLD, 20));
            g.drawString("Score: " + score, 800,100); // scoreboard and time

            for (int x = 0; x < balloon.length; x++) {
                if (balloon[x].isAlive == true) {
                    g.drawImage(balloon[x].pic, balloon[x].xpos, balloon[x].ypos, balloon[x].width, balloon[x].height, null);
                }
            } // balloons activate

            for (int i = 0; i < intruder.length; i++) {
                if (intruder[i].isAlive == true) {
                    g.drawImage(intruder[i].pic, intruder[i].xpos, intruder[i].ypos, intruder[i].width, intruder[i].height, null);
                }
            } // birds activate

            g.drawString("Time Remaining: " + timeRemaining, 700, 50); // timer

            if (timeRemaining == 0) {
                gameStart = false;
                g.drawString("Score: " + score,800,100);
                for (int x = 0; x < balloon.length; x++) {
                    balloon[x].isAlive = false;
                }
            } // when times up, game returns to start screen
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

        if(keyCode == 10){ // Enter --> start game key
            gameStart = true;
            score=0;
            startTime = System.currentTimeMillis();

            System.out.println("GameStart: " + gameStart);

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