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
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.Keymap;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;

import org.apache.commons.lang.StringUtils;

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
    String proteinAcc;
    String newLines = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
    
    JMenuItem blast = new JMenuItem("BLAST");
    JMenuItem msa = new JMenuItem("Alignment");
    JMenuItem iedbPrediction = new JMenuItem("IEDB Epitope Prediction");
    JMenuItem abcpred = new JMenuItem("ABCPred Epitope Prediction");
    JMenuItem bcepred = new JMenuItem("BcePred Epitope Prediction");
    JMenuItem discotopePred = new JMenuItem("Discotope Epitope Prediction");
    JMenuItem elliproPred = new JMenuItem("Ellipro Epitope Prediction");
    JMenuItem psipred = new JMenuItem("PsiPred Secondary Structure Prediction");
    JMenuItem jpred = new JMenuItem("JPred Secondary Structure Prediction");
	
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
        
        createMenuBar();
                
        setVisible(true);	
	}
	
	private void createMenuBar() {

		JMenuBar menubar = new JMenuBar();
        
		JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenu servicesMenu = new JMenu("Services");
        
		//file
        ImageIcon iconOpen = new ImageIcon("open.png");
        ImageIcon iconSave = new ImageIcon("save.png");
        ImageIcon iconSaveAs = new ImageIcon("saveas.png");
        
        //edit
        ImageIcon iconCut = new ImageIcon("cut.png");
        ImageIcon iconCopy = new ImageIcon("copy.png");
        ImageIcon iconPaste = new ImageIcon("paste.png");        

        JMenu impMenu = new JMenu("Import");
        
        //file
        JMenuItem openMi = new JMenuItem("Open", iconOpen);
        JMenuItem saveMi = new JMenuItem("Save", iconSave);
        JMenuItem saveAsMi = new JMenuItem("Save As", iconSaveAs);
        
        fileMenu.add(openMi);
        fileMenu.add(saveMi);
        fileMenu.add(saveAsMi);
        fileMenu.addSeparator();
        fileMenu.add(impMenu);
        fileMenu.addSeparator();
        
        //edit
        JMenuItem cutMi = new JMenuItem("Cut", iconCut);
        JMenuItem copyMi = new JMenuItem("Copy", iconCopy);
        JMenuItem pasteMi = new JMenuItem("Paste", iconPaste);
        
        editMenu.add(cutMi);
        editMenu.add(copyMi);
        editMenu.add(pasteMi);
        
        //Services
        servicesMenu.add(blast);
        servicesMenu.addSeparator();
        servicesMenu.add(msa);
        servicesMenu.addSeparator();
        servicesMenu.add(iedbPrediction);
        servicesMenu.add(abcpred);
        servicesMenu.add(bcepred);
        servicesMenu.add(discotopePred);
        servicesMenu.add(elliproPred);
        servicesMenu.addSeparator();
        servicesMenu.add(psipred);
        servicesMenu.add(jpred);        

        menubar.add(fileMenu);
        menubar.add(editMenu);
        menubar.add(servicesMenu);

        setJMenuBar(menubar);        
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
    public void insertLineMiddle(String header, String line) {
    	insertLine(newLines+header,newLines+line,0,0);
    }
    
    @Override
    public void insertLineAboveTarget(String header, String line) {
    	String [] headers = headerPane.getText().split("\n");
    	String [] lines = editorPane.getText().split("\n");
    	int headerPosition = 0;
    	int linePosition = 0;
    	System.out.println("************"+proteinAcc);
    	
    	loop:
    	for (int i = 0; i < headers.length; i++) {    		
    		if(headers[i].contains(proteinAcc+"|")) {
    			//headerPosition = headerPosition + headers[i].length();
        		//linePosition = linePosition + lines[i].length();
    			break loop;
    		}   
    		headerPosition = headerPosition + headers[i].length() + 1;
    		linePosition = linePosition + lines[i].length() + 1;
    		System.out.println("+++"+headers[i]+" "+headerPosition+" "+linePosition);
    	}
    	
    	insertLine(header,line,headerPosition,linePosition);
    }
    
    @Override
    public void insertLineBelowTarget(String header, String line) {
    	String [] headers = headerPane.getText().split("\n");
    	String [] lines = editorPane.getText().split("\n");
    	int headerPosition = 0;
    	int linePosition = 0;
    	System.out.println("************"+proteinAcc);
    	
    	loop:
    	for (int i = 0; i < headers.length; i++) {
    		headerPosition = headerPosition + headers[i].length() + 1;
    		linePosition = linePosition + lines[i].length() + 1;
    		System.out.println("+++"+headers[i]+" "+headerPosition+" "+linePosition);
    		if(headers[i].contains(proteinAcc+"|")) {
    			//headerPosition = headerPosition + headers[i].length();
        		//linePosition = linePosition + lines[i].length();
    			break loop;
    		}    		
    	}
    	
    	insertLine(header,line,headerPosition,linePosition);
    }
	
	@Override
	public void insertLine(String header, String line, int headerPosition, int editorPosition) {
		Document headerDoc = headerPane.getDocument();
		Document editorDoc = editorPane.getDocument();
		try {
			headerDoc.insertString(headerPosition, header+"\n", null);
			editorDoc.insertString(editorPosition, line+"\n", null);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	@Override
	public void proteinAcc(String proteinAcc) {
		// TODO Auto-generated method stub
		this.proteinAcc = proteinAcc;
	}
	
	@Override
	public void deleteLineByProteinAcc(String proteinAcc) {
		// TODO Auto-generated method stub
		String [] headers = headerPane.getText().split("\r\n");
    	String [] lines = editorPane.getText().split("\r\n");
    	int headerPosition = 0;
    	int linePosition = 0;
    	proteinAcc = proteinAcc +"|";
    	loop:
    	for (int i = 0; i < headers.length; i++) {
    		headerPosition = headerPosition + headers[i].length();
    		linePosition = linePosition + lines[i].length();
    		String [] headerArr = headers[i].split("\\|");
    		if(proteinAcc.contains(headerArr[0])) {
    			try {
					headerPane.getDocument().remove(headerPosition, headers[i].length());
					editorPane.getDocument().remove(linePosition, lines[i].length());
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

    			break loop;
    		}
    	}
	}

	@Override
	public void deleteLineByHeader(String header) {
		// TODO Auto-generated method stub
		String[] headers = StringUtils.split(headerPane.getText(), "\r\n");
		String[] lines = StringUtils.split(editorPane.getText(), "\r\n");
		//String [] headers = headerPane.getText().split("\r\n");
    	//String [] lines = editorPane.getText().split("\r\n");
    	int headerPosition = newLines.length();
    	int linePosition = newLines.length();
    	loop:
    	for (int i = 0; i < headers.length; i++) {
    		
    		System.out.println("*"+headers[i].length()+"|"+headers[i]);
    		if(header.equals(headers[i])) {
    			try {
    				System.out.println(headerPosition+"|"+headerPosition+headers[i].length());
    				System.out.println(linePosition+"|"+linePosition+lines[i].length());
					headerPane.getDocument().remove(headerPosition-10, headerPosition+headers[i].length());
					editorPane.getDocument().remove(linePosition-lines[i].length(), linePosition+lines[i].length());
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

    			break loop;
    		}
    		headerPosition = headerPosition + headers[i].length();
    		headerPosition++;
    		linePosition = linePosition + lines[i].length();
    		linePosition++;
    	}
   	}

	@Override
	public JMenuItem blast() {
		// TODO Auto-generated method stub
		return blast;
	}

	@Override
	public JMenuItem msa() {
		// TODO Auto-generated method stub
		return msa;
	}

	@Override
	public JMenuItem iedbPrediction() {
		// TODO Auto-generated method stub
		return iedbPrediction;
	}

	@Override
	public JMenuItem abcpred() {
		// TODO Auto-generated method stub
		return abcpred;
	}

	@Override
	public JMenuItem bcepred() {
		// TODO Auto-generated method stub
		return bcepred;
	}

	@Override
	public JMenuItem discotopePred() {
		// TODO Auto-generated method stub
		return discotopePred;
	}

	@Override
	public JMenuItem elliproPred() {
		// TODO Auto-generated method stub
		return elliproPred;
	}

	@Override
	public JMenuItem psipred() {
		// TODO Auto-generated method stub
		return psipred;
	}

	@Override
	public JMenuItem jpred() {
		// TODO Auto-generated method stub
		return jpred;
	}
}
