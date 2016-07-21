package epiprot.services.views;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

public class PhosphoSiteSummaryView extends JFrame implements PhosphoSitePresenter.SummaryView {
	
	private JTextPane textPane;

	public PhosphoSiteSummaryView() {
		
		setTitle("PhosphoSite Summary");
		setSize(new Dimension(400, 600));
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		
		setVisible(true);
	}

	@Override
	public JTextPane textPane() {
		// TODO Auto-generated method stub
		return textPane;
	}
	
	@Override
	public void appendString(String str) {
		StyledDocument document = (StyledDocument) textPane.getDocument();
	    try {
			document.insertString(document.getLength(), str, null);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
