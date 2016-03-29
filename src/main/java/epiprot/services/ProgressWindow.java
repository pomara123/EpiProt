package epiprot.services;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class ProgressWindow extends JFrame {
	
	JLabel jLabel = new JLabel();

	public ProgressWindow() {		
		// TODO Auto-generated constructor stub
	}
	
	public void createUI() {
	    //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    JProgressBar aJProgressBar = new JProgressBar(JProgressBar.HORIZONTAL);
	    aJProgressBar.setStringPainted(true);
	    aJProgressBar.setIndeterminate(true);
	    
	    add(jLabel,BorderLayout.NORTH);
	    add(aJProgressBar, BorderLayout.CENTER);
	    setSize(300, 80);
	    setVisible(true);
	}
	
	public void setProgressText(String text) {
		jLabel.setText(text);
	}
	
	public void setTitleName(String serviceName) {
		setTitle(serviceName+ " Progress");
	}
	
	public static void main (String[]args) {
		ProgressWindow pw = new ProgressWindow();
		pw.setTitleName("Hello");
		pw.setProgressText("Hello");
		pw.createUI();

	}

}
