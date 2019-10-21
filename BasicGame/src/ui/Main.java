package ui;
import javax.swing.JFrame;
import java.awt.EventQueue;
import board.Board;

public class Main extends JFrame {
	public Main() {
		initUI();
	}
	
	private void initUI() {
		add(new Board());
		setTitle("One Lucky Day");
        setSize(1000,1000);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
	}
}
