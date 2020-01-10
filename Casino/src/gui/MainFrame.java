package gui;

import java.awt.Color;

import javax.swing.JFrame;

public class MainFrame {
	JFrame frame = null;
	private final String gameTitle = "Poker - Casino Project";
	private final int WIDTH = 800;
	private final int HEIGHT = 800;
	
	public MainFrame() {
		frame = new JFrame(gameTitle);
		
		frame.setSize(WIDTH, WIDTH);
		frame.setBackground(new Color(35,135,40));
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
	}
}
