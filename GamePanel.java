import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import javax.swing.JPanel;

public class GamePanel  extends JPanel implements ActionListener {
    static final int ScreenHeight=600;
    static final int ScreenWidth=600;
    static final int UnitSize=25;   // size of objects in game (how big?)
    static final int GameUnits= (ScreenWidth*ScreenHeight)/UnitSize; // no of object that can fit on screen
    static final int Delay= 75; // speed of the game -->higher the no. of delay the slower the game is
    final int x[] = new int[GameUnits];  //x coordinates of body of snake
    final int y[] = new int[GameUnits];// y coordinate of body of snake
    int bodyParts=6;
    int foodEaten;
    int foodX;
    int foodY;
    char direction='R';
    boolean running= false;
    Timer timer;
    Random random;
    JButton j;

    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(ScreenWidth,ScreenHeight));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame(){
        newFood();
        running=true;
        timer= new Timer(Delay,this); // used this coz we are using ActionListener interface
        timer.start();

    }
    public void paintComponent(Graphics g){ // Graphics in awt package
        super.paintComponent(g);
        Draw(g);
    }
    public void Draw(Graphics g){
        if(running){
           /* for(int i=0;i<ScreenHeight/UnitSize;i++){
                // x1,y1--> start coordinate, x2,y2--> end coordinate
                g.drawLine(i*UnitSize,0,i*UnitSize,ScreenHeight); // vertical lines
                g.drawLine(0,i*UnitSize,ScreenHeight,i*UnitSize);// horizontal lines
            }*/
            g.setColor(Color.red);
            g.fillOval(foodX,foodY,UnitSize,UnitSize);// to set coordinates and size (x,y,width,height)
            for(int i=0;i<bodyParts;i++){
                if(i==0){
                    g.setColor(new Color(45,100,100));
                    g.fillRect(x[i],y[i],UnitSize,UnitSize);  // (x,y,width,height)
                }
                else{
                    g.setColor(Color.green);
                    g.fillRect(x[i],y[i],UnitSize,UnitSize);
                }
            }
            g.setColor(Color.white); // for Score
            g.setFont(new Font("Ink Free",Font.BOLD,30));
            FontMetrics metrics= getFontMetrics(g.getFont()); // for lining up text in the centre of screen
            g.drawString("Score : "+ foodEaten,(ScreenWidth-metrics.stringWidth("Score : "+ foodEaten))/2,30); // sets text at centre of screen

        }
        else{
            gameOver(g);
        }
    }
    public void newFood(){ // generate food
        foodX= random.nextInt((ScreenWidth/UnitSize))*UnitSize; // multiply by unitSize coz food should be placed in one of the grids
        foodY= random.nextInt((ScreenHeight/UnitSize))*UnitSize;

    }
    public void Move(){
        for(int i=bodyParts;i>0;i--){
           x[i]=x[i-1];
           y[i]= y[i-1];
        }
        switch(direction){
            case 'U':
                y[0]= y[0]- UnitSize;
                break;
            case 'D' :
                y[0]=y[0]+UnitSize;
                break;
            case 'L':
                x[0]=x[0]-UnitSize;
                break;
            case 'R':
                x[0]=x[0]+UnitSize;
                break;
        }


    }
    public void checkFood(){
        if((x[0]==foodX)&& (y[0]==foodY)){
            bodyParts++;
            foodEaten++;
            newFood();
        }

    }
    public void checkCollisions(){
        for(int i=bodyParts;i>0;i--){
            if((x[0]==x[i])&&(y[0]==y[i])){   // checks if head collides with body
                running=false;
            }
        }
        if(x[0]<0){  // checks if head touches left border
            running=false;
        }
        if(x[0]>ScreenWidth){   // checks if head touches right border
            running= false;
        };
        if(y[0]<0){   // checks if head touches top border
            running=false;
        }
        if(y[0]>ScreenHeight){ // checks if head touches the bottom border
            running= false;
        }
        if(!running){    // ie running == false
            timer.stop();
        }

    }
    public void gameOver(Graphics g){
        // Game Over text
        g.setColor(Color.white);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics= getFontMetrics(g.getFont()); // for lining up text in the centre of screen
        g.drawString("Game Over",(ScreenWidth-metrics.stringWidth("Game Over"))/2,ScreenHeight/2); // sets text at centre of screen
        // Score text
        g.setColor(Color.white);
        g.setFont(new Font("Ink Free",Font.BOLD,30));
        FontMetrics metrics2= getFontMetrics(g.getFont()); // for lining up text in the centre of screen
        g.drawString("Score : "+ foodEaten,(ScreenWidth-metrics2.stringWidth("Score : "+ foodEaten))/2,30); // sets text at centre of screen
        j= new JButton("RETRY");
        j.setBounds(250,500,100,30);
        j.setBackground(Color.white);
        j.setForeground(Color.black);
        j.addActionListener(this);
        this.add(j);
    }


    @Override
    public void actionPerformed(ActionEvent e){  // abstract method in ActionListener
        if(e.getSource()==j){
            new GameFrame();
        }
        if(running){
            Move();
            checkFood();
            checkCollisions();
        }
        repaint(); // to reload the frame

    }

    public class MyKeyAdapter extends KeyAdapter {  // inner class
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){   // so that it doesn't turn by 180 degree and collide into itself
                        direction= 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction= 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction !='D'){
                        direction= 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction= 'D';
                    }
                    break;
            }

        }
    }

}

