import javax.swing.JFrame;
//import java.awt.event.ActionListener;
public class GameFrame extends JFrame{

	GameFrame(){
		/*GamePanel panel = new GamePanel(); //creating reference
		this.add(panel);
		*/
		
		this.add(new GamePanel());
		this.setTitle("Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false); //false
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null); //this will keep our frame at the middle
		
		
	}
}