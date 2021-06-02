import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class BattleStar extends JComponent implements ActionListener, MouseMotionListener, MouseListener, KeyListener {
    
    /**
     *author : saumya bhandary
     */
    
    private static final long serialVersionUID = 1L;

    static Random rand = new Random(); 

    static int height = 800;
    static int width = 900;
    static BattleStar game = new BattleStar();
    static JFrame frame = new JFrame();
    
    int starnum = 500;
    int[] starx = new int[starnum];
    int[] stary = new int[starnum];
    int planetDiameter = 40;
    int rocketx = width/2;
    int rockety = height + 130;
    int bulletx;
    int bullety = rockety;
    int vy = 10;
    int alienx = rand.nextInt(width - 114);
    int alieny = -70;
    int alienvx = 4;
    int alienvy = 2;
    int randomwidth;
    int randomwidth2;
    int distancetravelled = 0;
    int bulletduration = 0;
    int hitcount = 0;
    int score = 0;
    int alienbulletx = rocketx;
    int alienbullety = alieny;
    int hitrocketcount = 0;
    int moveRocket = 3;
    int keyPressed;
    boolean fired = false;
    boolean hit = false;
    boolean explosion = false;
    boolean alienfire = false;
    boolean hitrocket = false;
    boolean explosion2 = false;
    boolean bothexplode = false;


    

    public static void main(String[] args) {    
        
        frame.setTitle("Battle Star game by Saumya Bhandari");
        frame.setSize(width,height);
        frame.setLocation(300,20);
        frame.add(game);

        frame.setVisible(true);
        int option = JOptionPane.showConfirmDialog(frame, "Do you want to enable mouse controls??", "Enter your game mode", JOptionPane.YES_NO_OPTION, 3);
        if(option == JOptionPane.YES_OPTION){
                Timer t = new Timer(10,game);
                t.start();
                frame.addMouseMotionListener(game);
                frame.addMouseListener(game);
        }
        if(option == JOptionPane.NO_OPTION){
                Timer t = new Timer(10,game);
                t.start();
                frame.addKeyListener(game);
        }

        //creating different stars
        for(int i=0; i<game.starnum; i++){
            game.starx[i] = rand.nextInt(width);
            game.stary[i] = rand.nextInt(height);
        }
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    @Override
    protected void paintComponent(Graphics g) {
        
        //creating a background environment
        g.setColor(Color.decode("#07072b"));
        g.fillRect(0, 0, width, height);

        for(int i=0; i<starnum; i++){
            g.setColor(Color.white);
            g.fillOval(starx[i], stary[i], 1, 1);
        }

        //creating a spaceship
        ImageIcon rocket = new ImageIcon("rocket.png");
        rocket.paintIcon(this, g, rocketx, rockety);

        
        
        if(hitrocketcount >= 2 ){
            ImageIcon rocket2 = new ImageIcon("tornrocket.png");
            rocket2.paintIcon(this, g, rocketx, rockety);
        }

        if(hitrocketcount == 5 || bothexplode == true) {
            ImageIcon explodeRocket = new ImageIcon("explosion.png");
            explodeRocket.paintIcon(this, g, rocketx - 20, rockety- 30);
        }
        
        //creating a bullet
        if(fired == true){
            g.setColor(Color.decode("#fad5b4"));
            g.drawOval(bulletx,bullety,4,15);
            g.setColor(Color.decode("#ff7700"));
            g.fillOval(bulletx, bullety, 4,15);
        }

        //creating a bullet for alienship
        if(alienfire == true){
            g.setColor(Color.decode("#e4ffd6"));
            g.drawOval(alienbulletx,alienbullety,4,15);
            g.setColor(Color.decode("#55ff00"));
            g.fillOval(alienbulletx, alienbullety, 4,15);
        }

        //creating an alien ship
        ImageIcon alienship = new ImageIcon("unnamed.gif");
        alienship.paintIcon(this, g, alienx, alieny);

        //creating an alienship that has been hit
        if(hitcount == 1){    
            ImageIcon alienship2 = new ImageIcon("alien3.gif");
            alienship2.paintIcon(this, g, alienx, alieny);
        }

        //creatin a situation for explosion of the alienship
        if(hitcount == 2 || bothexplode == true){
            ImageIcon explode = new ImageIcon("explosion.png");
            explode.paintIcon(this, g, alienx - 40, alieny - 50);
        }


        //drawing a place to show the score
        String scoreString = "Score: " + score;
        g.setColor(Color.white);
        g.drawString(scoreString, 20, 20);

        //drawing a place to show number of damage taken
        String damageTaken  = "Damage Taken: " + hitrocketcount;
        g.setColor(Color.white);
        g.drawString(damageTaken, width - 150, 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        repaint();

        if(rockety > height/2 + 250){
            rockety -= 2;
            bullety = alieny;
        }
        
        for(int i=0; i<starnum; i++){
            stary[i]+= 1;
            if(stary[i] > height){
                stary[i] = rand.nextInt(5) - 10;
            }
        }        

        //making a bullet fire
        if(fired == true){
            bullety -= vy;
        }
        if(bullety <= 0){
            fired = false;
            bullety = rockety;
        }


        //creating movement for our alienship
        randomwidth = rand.nextInt(width);
        randomwidth2 = rand.nextInt(width);
        
        if(rockety == height/2 + 250){
            if(alieny < 50){
                alieny++;
           }
        }
        if(alienx + 115 >= width || alienx <= 0){
            alienvx = -alienvx;
            int count = 0;
            if(distancetravelled >= width){
                while(count < 20){
                    alieny += alienvy;
                    count++;
                }
                count = 0;
                distancetravelled = 0;
                
            }

            if(explosion == true){
                alienx = rand.nextInt(width - 120);
                alieny = -60;
                alienvx += 1/2;
                explosion = false;
                hitcount = 0;
            }
        }else if(alienx == randomwidth){
            alienvx = -alienvx;
            if(explosion == true){
                alienx = rand.nextInt(width - 120);
                alieny = rand.nextInt(60) - 160;;
                alienvx += 1/2;
                explosion = false;
                hitcount = 0;
            }
        }else if(alienx == randomwidth2){
            alienvx = -alienvx;
            if(explosion == true){
                alienx = rand.nextInt(width - 120);
                alieny = rand.nextInt(60) - 160;
                alienvx += 1/2;
                explosion = false;
                hitcount = 0;
            }
        }

        if((alieny + 80 >= rockety && alienx >= rocketx) || hitrocketcount == 5){
            if((alieny + 80 >= rockety && alienx >= rocketx)){
                bothexplode = true;
            }
           int replay = JOptionPane.showConfirmDialog(frame, "Game over ! Do you want to play again?","Game Over!",JOptionPane.YES_NO_OPTION,0);
           if(replay == JOptionPane.YES_OPTION){
                alienx = rand.nextInt(width);
                alieny = rand.nextInt(60) - 160;
                explosion = false;
                hitcount = 0;
                hitrocketcount = 0;
                bothexplode = false;
           }else{
               System.exit(1);
           }
        }

        alienx += alienvx;
        distancetravelled ++;
        bulletduration++;

        //creating the situation when bullet hits the alienship
        if((bulletx >= alienx && bulletx <= alienx + 114) && (bullety == alieny )){
            hit = true;
            bullety = rockety;
            fired = false;
            if(hit = true){
                hitcount += 1;
            }
            hit = false;
            if(hitcount == 2){
                explosion = true;
                score++;
            }

        }
        
        //when alienship fires the bullet
        if(bulletduration == width/40){
            alienbulletx = alienx+50;
            alienfire = true;
        }
        if(alienfire == true){
            alienbullety += 10;
        }
        if(explosion != true){
            if(alienbullety >= height){
                alienfire = false;
                alienbullety = alieny;
                bulletduration = 0;
            }
        }

        //when bullet from alienship hits our rocket
        if((alienbulletx >= rocketx && alienbulletx <= rocketx + 60) && (alienbullety == rockety)){
            
            hitrocket = true;
            if(hitrocket = true){
                hitrocketcount += 1;
            }
            hitrocket = false;
            if(hitrocketcount == 5){
                explosion2 = true;
            }
        }

        if(keyPressed == KeyEvent.VK_RIGHT && rocketx + 60 <= width){
            rocketx += moveRocket;
        }
        if(keyPressed == KeyEvent.VK_LEFT && rocketx >= 0){
            rocketx -= moveRocket;
        }
        
        
        repaint();
        
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        keyPressed = e.getKeyCode();
        if(keyPressed == KeyEvent.VK_SPACE){
            fired = true;
            bulletx = rocketx + 30;
        }
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
            rocketx = e.getX() - 40;
    }
    
    @Override
    public void mousePressed(MouseEvent e) {     
            fired = true;
            bulletx = e.getX() - 15;
    }
    


    
    @Override
    public void mouseDragged(MouseEvent e) {}
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }

   



}