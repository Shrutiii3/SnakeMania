import javax.swing.JFrame;
public class GameFrame extends JFrame{
    GameFrame(){

        this.add(new GamePanel()); // add game panel to game frame
        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();// packs all the added components on the frame
        this.setVisible(true);
        this.setLocationRelativeTo(null);  // to set the screen in the centre
    }
}

