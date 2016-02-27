package epiprot;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JViewport;
import javax.swing.KeyStroke;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JInternalFrame;
import javax.swing.JSplitPane;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Keymap;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;

import java.awt.Color;
import java.awt.Component;
import javax.swing.border.MatteBorder;

public class View extends JFrame implements Presenter.View {
    
    public static final String LINE_BREAK_ATTRIBUTE_NAME="line_break_attribute";
	
	JPanel proteinPanel = new JPanel();
	JEditorPane headerPane = new JEditorPane();
	JEditorPane editorPane = new JEditorPane();
	JTextField searchField = new JTextField();
	JButton searchButton = new JButton("Enter");
	JButton msaButton = new JButton("MSA");
    JButton blastButton = new JButton("BLAST");
	
	public View() {
		createUI();			 
	}

	@Override
	public void createUI() {
		// TODO Auto-generated method stub
		setTitle("EpiProt");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1000, 600));
        getContentPane().setLayout(new BorderLayout(0, 0));        
        JToolBar toolBar = new JToolBar("Tools",JToolBar.HORIZONTAL);
        toolBar.add(blastButton);
        
        toolBar.add(msaButton);
        getContentPane().add(toolBar, BorderLayout.NORTH);
        
        JSplitPane splitPane = new JSplitPane();
        splitPane.setDividerLocation(200);;
        getContentPane().add(splitPane, BorderLayout.CENTER);
        
        editorPane.setEditorKit(new WrapEditorKit());
        initKeyMap(editorPane);
        headerPane.setEditorKit(new WrapEditorKit());
        initKeyMap(headerPane);
        
        
        JSplitPane editorSplitPane = new JSplitPane();
        editorSplitPane.setDividerLocation(200);
        final JScrollPane editorScrollPane = new JScrollPane();
        editorSplitPane.setRightComponent(editorScrollPane);        
        editorScrollPane.setViewportView(editorPane);
        final JScrollPane headerScrollPane = new JScrollPane();
        editorSplitPane.setLeftComponent(headerScrollPane);        
        headerScrollPane.setViewportView(headerPane);
        
        //links scrolling of editor pane to header
        headerScrollPane.getViewport().addChangeListener(new ChangeListener() {
        	@Override
        	public void stateChanged(ChangeEvent e) {
        	    //  Sync the scroll pane scrollbar with the row header
        		System.out.println("test");
        	    JViewport viewport = (JViewport) e.getSource();
        	    editorScrollPane.getVerticalScrollBar().setValue(viewport.getViewPosition().y);
        	}
        });
        
             
        JScrollPane scrollPane_1 = new JScrollPane();
        splitPane.setLeftComponent(scrollPane_1); 
        splitPane.setRightComponent(editorSplitPane);
        scrollPane_1.setViewportView(proteinPanel);
        proteinPanel.setLayout(new BoxLayout(proteinPanel, BoxLayout.Y_AXIS));
        JPanel searchPanel = new JPanel();
        searchPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        searchPanel.setMaximumSize(new Dimension(32767, 40));
        searchPanel.setMinimumSize(new Dimension(10, 30));
        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(120, 35));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        proteinPanel.add(searchPanel);        
        setVisible(true);	
	}
	
	protected void insertLineBreak(JEditorPane edit) {
        try {
            int offs = edit.getCaretPosition();
            Document doc = edit.getDocument();
            SimpleAttributeSet attrs;
            if (doc instanceof StyledDocument) {
                attrs = new SimpleAttributeSet( ( (StyledDocument) doc).getCharacterElement(offs).getAttributes());
            }
            else {
                attrs = new SimpleAttributeSet();
            }
            attrs.addAttribute(LINE_BREAK_ATTRIBUTE_NAME,Boolean.TRUE);
            doc.insertString(offs, "\r", attrs);
            edit.setCaretPosition(offs+1);
        }
        catch (BadLocationException ex) {
            //should never happens
            ex.printStackTrace();
        }
    }

    protected void initKeyMap(final JEditorPane edit) {
        Keymap kMap=edit.getKeymap();
        Action a=new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                insertLineBreak(edit);
            }
        };
        kMap.addActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,KeyEvent.SHIFT_MASK),a);
    }
	
	@Override
	public void insertLine(String header, String line) {
		headerPane.setText(header);
		editorPane.setText(line);
		
	}
	
	@Override
	public void addProteinSelecter(SelectProteinView selectProtein) {
		// TODO Auto-generated method stub
		proteinPanel.add(selectProtein);
	}
	
	public void setPresenter(Presenter presenter) {
		
	}

	@Override
	public JEditorPane editorPane() {
		// TODO Auto-generated method stub
		return editorPane;
	}

	@Override
	public JEditorPane headerPane() {
		// TODO Auto-generated method stub
		return headerPane;
	}

	@Override
	public JPanel proteinPanel() {
		// TODO Auto-generated method stub
		return proteinPanel;
	}

	@Override
	public JTextField searchField() {
		// TODO Auto-generated method stub
		return searchField;
	}

	@Override
	public JButton searchButton() {
		// TODO Auto-generated method stub
		return searchButton;
	}

	@Override
	public JButton msaButton() {
		// TODO Auto-generated method stub
		return msaButton;
	}

	@Override
	public JButton blastButton() {
		// TODO Auto-generated method stub
		return blastButton;
	}

}
