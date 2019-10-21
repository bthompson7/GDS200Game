package ui;
import javax.swing.JFrame;
import java.awt.EventQueue;
import board.Board;

public class Main extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -96818073291765422L;

	private String title = MainMenu.getGameTitle();
	public Main() {
		initUI();
	}
	
	private void initUI() {
		add(new Board());
		setTitle(title);
        setSize(1000,1000);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
	}
}
