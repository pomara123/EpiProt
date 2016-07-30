package epiprot.services.views;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import java.awt.BorderLayout;

public class ABCPredSummaryView extends JFrame implements ABCPredPresenter.SummaryView {

	private JTextPane textPane;
	
	public ABCPredSummaryView() {
		setTitle("ABCPred Summary");
		setSize(new Dimension(400, 600));
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		
		appendString(String.format("%1$-10s %2$-25s %3$-5s %4$10s\n", "Rank", "Sequence", "Start", "Score"));
		
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
