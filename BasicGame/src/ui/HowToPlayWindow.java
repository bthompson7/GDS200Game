package ui;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class HowToPlayWindow extends JFrame {
	private static final long serialVersionUID = -7428990481596388940L;
	
	
	
	
	public HowToPlayWindow() {
		initUI();
	}




	private void initUI() {
		setTitle("How To Play");
		setSize(500,500);
		setVisible(true);
        setLocationRelativeTo(null);

        JTextArea howTo = new JTextArea();
        howTo.setSize(200, 200);
        howTo.setLineWrap(true);
        howTo.setToolTipText("lol");;
        howTo.setAlignmentX(CENTER_ALIGNMENT);
        howTo.setEditable(false);
        String about = "You play as a blue square and your goal is to collect as many red squares as possible while dodging the enemies\n"
        		+ "You control your character using the WASD keys ";
        
        JButton backBtn = new JButton("Back");
        howTo.setText(about);
        JPanel panel = new JPanel();
        panel.add(howTo);
        panel.add(backBtn);

        backBtn.addActionListener((e) -> {
			setVisible(false);
			System.out.println("Pressed howto button");
		});


		add(panel);
		
		
	}
	

}
