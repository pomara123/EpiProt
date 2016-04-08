package epiprot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.StyledEditorKit.StyledTextAction;
import javax.swing.text.html.HTML;

public class BackgroundColorChooser extends JPanel implements ChangeListener {
		 
	protected JColorChooser tcc;
	protected JLabel banner;
	public epiprot.Presenter.View view;
	
	public BackgroundColorChooser(epiprot.Presenter.View view) {
		super(new BorderLayout());
		
		this.view = view;
		
		//Create and set up the window.
		JFrame frame = new JFrame("Background Color Chooser");
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
		banner.setBackground(newColor);
		
        if (view.editorPane() == null) {
            return;
        }
        
        String htmlStyle = "background-color:" + getHTMLColor(newColor);
        System.out.println(htmlStyle);
        SimpleAttributeSet attr = new SimpleAttributeSet();
        attr.addAttribute(HTML.Attribute.STYLE, htmlStyle);
        MutableAttributeSet outerAttr = new SimpleAttributeSet();
        outerAttr.addAttribute(HTML.Tag.SPAN, attr);
        //Next line is just an instruction to editor to change color
        //StyleConstants.setBackground(outerAttr, newColor);
        //setCharacterAttributes(view.editorPane(), outerAttr, false);
		
        StyledDocument doc = (StyledDocument) view.editorPane().getDocument();
        StyleConstants.setBackground( outerAttr, newColor );
		doc.setCharacterAttributes( view.editorPane().getSelectionStart(), view.editorPane().getSelectionEnd() - view.editorPane().getSelectionStart(), outerAttr, false );

	}
	
	public class BackgroundColorAction extends StyledEditorKit.StyledTextAction {

	    private Color color;

	    public BackgroundColorAction(Color color) {
	        super(StyleConstants.Background.toString());
	        this.color = color;
	    }

	    @Override
	    public void actionPerformed(ActionEvent ae) {
	        if (view.editorPane() == null) {
	            return;
	        }
	        //Add span Tag
	        String htmlStyle = "background-color:" + getHTMLColor(color);
	        System.out.println(htmlStyle);
	        SimpleAttributeSet attr = new SimpleAttributeSet();
	        attr.addAttribute(HTML.Attribute.STYLE, htmlStyle);
	        MutableAttributeSet outerAttr = new SimpleAttributeSet();
	        outerAttr.addAttribute(HTML.Tag.SPAN, attr);
	        //Next line is just an instruction to editor to change color
	        StyleConstants.setBackground(outerAttr, this.color);
	        setCharacterAttributes(view.editorPane(), outerAttr, false);
	    }
	}
	
	public static String getHTMLColor(Color color) {
	    if (color == null) {
	        return "#000000";
	    }
	    return "#" + Integer.toHexString(color.getRGB()).substring(2).toUpperCase();
	}
	
	public static void main (String[]args) {
		//ColorChooser cc = new ColorChooser();
	}
}
