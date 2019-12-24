package ui;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
        howTo.setSize(400,200);
        howTo.setLineWrap(true);
        howTo.setToolTipText("info!");;
        howTo.setAlignmentX(CENTER_ALIGNMENT);
        howTo.setEditable(false);
        String about ="Your objective is to score as many points as possible while\n dodging enemies";
        
        JButton backBtn = new JButton("Back");
        howTo.setText(about);
        JPanel panel = new JPanel();

		JLabel jl = new JLabel();

		URL url = MainMenu.class.getResource("/resources/controls2.png");
		jl.setIcon(new ImageIcon(url));
		jl.setAlignmentX(CENTER_ALIGNMENT);
		panel.add(jl);
        panel.add(howTo);
        panel.add(backBtn);

        backBtn.addActionListener((e) -> {
			setVisible(false);
		});
		add(panel);
		
		
	}
	

}
