package ui;
import java.io.IOException;
import java.net.URL;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class MainMenu extends JFrame {
	private static final long serialVersionUID = -3088890058631223710L;

	private static String gameTitle = "One Lucky Day - Co-Op";
	private void initUI(){

		setTitle("One Lucky Day - Co-Op");
        setSize(500,500);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        JPanel panel = new JPanel();
        add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		JButton play = new JButton("Play");
		JButton howToPlay = new JButton ("How To Play");
		
		panel.add(play);
		panel.add(howToPlay);
		play.setAlignmentX(CENTER_ALIGNMENT);
		howToPlay.setAlignmentX(CENTER_ALIGNMENT);
		play.addActionListener((e) -> {
			Main m = new Main();
		});
		howToPlay.addActionListener((e) -> {
			HowToPlayWindow htp = new HowToPlayWindow();
			System.out.println("Pressed howto button");
		});
		JLabel jl = new JLabel();
		
		URL url = MainMenu.class.getResource("/resources/title_picture2.gif");
		jl.setIcon(new ImageIcon(url));
		panel.add(jl);


	}
	public MainMenu() {
		initUI();
	}
	
	public static void main(String[] args){
		@SuppressWarnings("unused")
		MainMenu menu = new MainMenu();
	}
	public static String getGameTitle() {
		return gameTitle;
	}


}
