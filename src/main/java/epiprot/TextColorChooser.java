package epiprot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class TextColorChooser extends JPanel implements ChangeListener {
		 
	protected JColorChooser tcc;
	protected JLabel banner;
	public epiprot.Presenter.View view;
	
	public TextColorChooser(epiprot.Presenter.View view) {
		super(new BorderLayout());
		
		this.view = view;
		
		//Create and set up the window.
		JFrame frame = new JFrame("Text Color Chooser");
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		//Set up the banner at the top of the window
		banner = new JLabel("Text", JLabel.CENTER);
		banner.setForeground(Color.black);
		banner.setBackground(Color.white);
		banner.setOpaque(true);
		banner.setFont(new Font("SansSerif", Font.BOLD, 24));
		banner.setPreferredSize(new Dimension(100, 65));
		
		JPanel bannerPanel = new JPanel(new BorderLayout());
		bannerPanel.add(banner, BorderLayout.CENTER);
		bannerPanel.setBorder(BorderFactory.createTitledBorder("Banner"));
		
		//Set up color chooser for setting text color
		tcc = new JColorChooser(banner.getForeground());
		tcc.getSelectionModel().addChangeListener(this);
		tcc.setBorder(BorderFactory.createTitledBorder("Choose Text Color"));
		
		add(bannerPanel, BorderLayout.CENTER);
		add(tcc, BorderLayout.PAGE_END);
		
		this.setOpaque(true); //content panes must be opaque
		frame.setContentPane(this);
		
		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}
	
	public void stateChanged(ChangeEvent e) {
		Color newColor = tcc.getColor();
		banner.setForeground(newColor);
				
		StyledDocument doc = (StyledDocument) view.editorPane().getDocument();
		SimpleAttributeSet atts = new SimpleAttributeSet();
		StyleConstants.setForeground( atts, newColor );

		doc.setCharacterAttributes( view.editorPane().getSelectionStart(), view.editorPane().getSelectionEnd() - view.editorPane().getSelectionStart(), atts, false );

	    
	}
	
	public static void main (String[]args) {
		//ColorChooser cc = new ColorChooser();
	}
}
