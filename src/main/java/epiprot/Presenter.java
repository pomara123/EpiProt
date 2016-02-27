package epiprot;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Presenter {
		
	public interface View {
	    JEditorPane editorPane();        
	    JEditorPane headerPane();
	    JPanel proteinPanel();
	    JTextField searchField();
	    JButton searchButton();
		JButton msaButton();
	    JButton blastButton();
	    
	    void insertLine(String header, String line);
	    void addProteinSelecter(SelectProteinView selectProtein);
		
		void createUI();	
	}
	
	public interface Model {
		Line fetchProteinLine(String proteinId);	
	}
	
	View view;
	Model model;
	
	public Presenter(View view, Model model) {
		this.view = view;
		this.model = model;
		bindHandlers();
	}
	
	public void bindHandlers() {
		view.searchButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				insertLine(view.searchField().getText());
			}		
		});
	}
	
	public void display() {
		
	}
	
	public void insertLine(String proteinId) {
		Line line = model.fetchProteinLine(proteinId);
		view.insertLine(line.getHeader(), line.getLine());
	}

}
