package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Start extends JPanel {

	private JButton next = new JButton("NEXT -->");
	public Color c5 = new Color(0, 0, 0);
	public Color c6 = new Color(255, 215, 0);

	public Start(JFrame jFrame, int delay, int s) {
		Plot drawPanel = new Plot(jFrame, s);
		setSize(1500, 700);
		setBackground(Color.BLACK);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JLabel title[] = { new JLabel(" "), new JLabel("OOM Project"), new JLabel("Interactive polygon area calculator"),
				new JLabel(" ") };
		JLabel members[] = { new JLabel("Neel Ghule - IIT2024266"), new JLabel("Gauri Joshi - IIB2024003"),
				new JLabel("Nitya Varma - IIT2024217"), new JLabel(" "), new JLabel(" ") };
		for (int i = 0; i < 2; i++) {
			title[i].setAlignmentX(CENTER_ALIGNMENT);
			title[i].setAlignmentY(CENTER_ALIGNMENT);
			title[i].setFont(new Font("Sans-serif", Font.ITALIC, 80));
			title[i].setForeground(drawPanel.c6);
			add(title[i]);
		}

		for (int i = 2; i < 4; i++) {
			title[i].setAlignmentX(CENTER_ALIGNMENT);
			title[i].setAlignmentY(CENTER_ALIGNMENT);
			title[i].setFont(new Font("Sans-serif", Font.BOLD, 40));
			title[i].setForeground(drawPanel.c6);
			add(title[i]);
		}

		for (int i = 0; i < 4; i++) {
			members[i].setAlignmentX(CENTER_ALIGNMENT);
			members[i].setFont(new Font("Sans-serif", Font.BOLD, 30));
			members[i].setAlignmentY(CENTER_ALIGNMENT);
			members[i].setForeground(drawPanel.c6);
			add(members[i]);
		}

		next.setFont(new Font("Sans-serif", Font.BOLD, 30));
		next.setForeground(c5);
		next.setBackground(c6);
		next.setSize(200, 50);
		add(next, BorderLayout.EAST);
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jFrame.getContentPane().removeAll();
				jFrame.setSize(drawPanel.getSize());
				jFrame.getContentPane().add(drawPanel);
				jFrame.revalidate();
				jFrame.repaint();
			}
		});

		int delayTime = delay * 1000;

		// Only start timer if delay is positive (not when clicking "GO BACK")
		if (delay > 0) {
			ActionListener taskPerformer = new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					jFrame.getContentPane().removeAll();
					jFrame.setSize(drawPanel.getSize());
					jFrame.getContentPane().add(drawPanel);
					jFrame.revalidate();
					jFrame.repaint();
				}
			};
			Timer timer = new Timer(delayTime, taskPerformer);
			timer.start();
			timer.setRepeats(false);
		}
	}

	public static void main(String[] args) {
		// Use SwingUtilities to be thread-safe
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame jFrame = new JFrame();
				jFrame.setTitle("Area Calculator");
				jFrame.setSize(1500, 700);
				jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				jFrame.setLocationRelativeTo(null);

				// --- CHANGED: Start with Login screen ---
				Login loginScreen = new Login(jFrame);
				jFrame.getContentPane().add(loginScreen);
				// ----------------------------------------

				jFrame.setVisible(true);
			}
		});
	}
}