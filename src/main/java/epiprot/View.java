package epiprot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.Keymap;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;

public class View extends JFrame implements Presenter.View, ActionListener {
    
    public static final String LINE_BREAK_ATTRIBUTE_NAME="line_break_attribute";
	
    JFileChooser fileDialog;  // File dialog for use in doOpen() and doSave(). 
    File currentFile;  // The file, if any that is currently being edited.
    File editorTempFile = new File("editorTempFile.html");
    File headerTempFile = new File("headerTempFile.html");
    HTMLDocument editorDocument;
    HTMLDocument headerDocument;
	//JTextPane headerPane = new JTextPane();
	//JTextPane editorPane = new JTextPane();
    
	/** Listener for the edits on the current document. */
	protected UndoableEditListener undoHandler = new UndoHandler();

	/** UndoManager that we add edits to. */
	protected UndoManager undo = new UndoManager();
		
	private UndoAction undoAction = new UndoAction();
	private RedoAction redoAction = new RedoAction();
		
	private Action cutAction = new DefaultEditorKit.CutAction();
	private Action copyAction = new DefaultEditorKit.CopyAction();
	private Action pasteAction = new DefaultEditorKit.PasteAction();

	private Action boldAction = new StyledEditorKit.BoldAction();
	private Action underlineAction = new StyledEditorKit.UnderlineAction();
	private Action italicAction = new StyledEditorKit.ItalicAction();
		
	private Action insertBreakAction = new DefaultEditorKit.InsertBreakAction();
	private HTMLEditorKit.InsertHTMLTextAction unorderedListAction 
		= new HTMLEditorKit.InsertHTMLTextAction("Bullets", "<ul><li> </li></ul>",HTML.Tag.P,HTML.Tag.UL);
	private HTMLEditorKit.InsertHTMLTextAction bulletAction 
		= new HTMLEditorKit.InsertHTMLTextAction("Bullets", "<li> </li>",HTML.Tag.UL,HTML.Tag.LI);
	
    JPanel proteinPanel = new JPanel();
	JEditorPane headerPane = new JEditorPane("text/html","");
	JEditorPane editorPane = new JEditorPane("text/html","");
	JTextField searchField = new JTextField();
	JButton searchButton = new JButton("Enter");
	JButton msaButton = new JButton("MSA");
    JButton blastButton = new JButton("BLAST");
    String proteinAcc;
    //String newLines = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
    
    JMenuItem blast = new JMenuItem("BLAST");
    JMenuItem msa = new JMenuItem("Alignment");
    JMenuItem iedbPrediction = new JMenuItem("IEDB Epitope Prediction");
    JMenuItem abcpred = new JMenuItem("ABCPred Epitope Prediction");
    JMenuItem bcepred = new JMenuItem("BcePred Epitope Prediction");
    JMenuItem discotopePred = new JMenuItem("Discotope Epitope Prediction");
    JMenuItem elliproPred = new JMenuItem("Ellipro Epitope Prediction");
    JMenuItem psipred = new JMenuItem("PsiPred Secondary Structure Prediction");
    JMenuItem jpred = new JMenuItem("JPred Secondary Structure Prediction");
    JMenuItem pdbStructure = new JMenuItem("PDB Structure from SIFTS");
    JMenuItem ptms = new JMenuItem("Post-Translational Modifications");
    
    JMenuItem foregroundColor = new JMenuItem("Text Color");
    JMenuItem backgroundColor = new JMenuItem("Background Color");
    
    JMenuItem clearPanes = new JMenuItem("Clear View");
    
	public View() {
		//setUIFont (new javax.swing.plaf.FontUIResource("monospaced",Font.PLAIN,10));
		HTMLEditorKit editorKit = new HTMLEditorKit();
		editorDocument = (HTMLDocument)editorKit.createDefaultDocument();
		headerDocument = (HTMLDocument)editorKit.createDefaultDocument();
		// Force SwingSet to come up in the Cross Platform L&F
		try {
			//UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			// If you want the System L&F instead, comment out the above line and
			// uncomment the following:
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			//UIManager.put("TextPane.font", new Font("Currier", Font.PLAIN,10));
		} catch (Exception exc) {
			    System.err.println("Error loading L&F: " + exc);
		}
		createUI();			 
	}

	@Override
	public void createUI() {
		// TODO Auto-generated method stub
		StyleSheet styleSheet = new StyleSheet();
		styleSheet.addRule("body {line-height: 0px}");
		editorPane.setFont(new Font("Courier New", Font.PLAIN, 12));
		headerPane.setFont(new Font("Courier New", Font.PLAIN, 12));
		editorPane.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);
		headerPane.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);
		
		addWindowListener(new FrameListener());
		
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
        
        HTMLEditorKit editorEditorKit = new HTMLEditorKit();
        editorEditorKit.setStyleSheet(styleSheet);
        editorPane.setEditorKit(new HTMLEditorKit());
        editorPane.setDocument(new HTMLDocument());
        initKeyMap(editorPane);
        
        HTMLEditorKit headerEditorKit = new HTMLEditorKit();
        headerEditorKit.setStyleSheet(styleSheet);
        headerPane.setEditorKit(new HTMLEditorKit());
        headerPane.setDocument(new HTMLDocument());
        initKeyMap(headerPane);        
        
        JSplitPane editorSplitPane = new JSplitPane();
        editorSplitPane.setDividerLocation(200);
        
        final JScrollPane editorScrollPane = new JScrollPane();
        editorSplitPane.setRightComponent(editorScrollPane);
        JPanel editorPanel = new JPanel(new BorderLayout());
        editorScrollPane.setViewportView(editorPanel);
        editorPanel.add(editorPane, BorderLayout.CENTER);
        
        final JScrollPane headerScrollPane = new JScrollPane();
        editorSplitPane.setLeftComponent(headerScrollPane); 
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerScrollPane.setViewportView(headerPanel);
        headerPanel.add(headerPane, BorderLayout.CENTER);
        
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
        
        editorPane.setDocument(editorDocument);
        editorPane.setContentType("text/html");
        headerPane.setDocument(headerDocument);
        headerPane.setContentType("text/html");
                
        setVisible(true);	
	}
	
	private void createMenuBar() {

		//menu bar
		JMenuBar menubar = new JMenuBar();
		
		setJMenuBar(menubar); 
		
		//main options
		JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenu formatMenu = new JMenu("Format");
        JMenu servicesMenu = new JMenu("Services");
        
        menubar.add(fileMenu);
        menubar.add(editMenu);
        menubar.add(formatMenu);
        menubar.add(servicesMenu);
		        
		//file
		JMenuItem newItem = new JMenuItem("New", new ImageIcon("whatsnew-bang.gif"));
		newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		JMenuItem openItem = new JMenuItem("Open",new ImageIcon("open.png"));
		openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		JMenuItem saveItem = new JMenuItem("Save",new ImageIcon("save.png"));
		saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		JMenuItem saveAsItem = new JMenuItem("Save As",new ImageIcon("saveas.png"));
		JMenuItem exitItem = new JMenuItem("Exit",new ImageIcon("exit.gif"));
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		
		newItem.addActionListener(this);
		openItem.addActionListener(this);
		saveItem.addActionListener(this);
		saveAsItem.addActionListener(this);
		exitItem.addActionListener(this);
        
		fileMenu.add(newItem);
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(saveAsItem);
		fileMenu.add(exitItem);
        
        //edit
		JMenuItem undoItem = new JMenuItem(undoAction);
		undoAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Z,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		JMenuItem redoItem = new JMenuItem(redoAction);
		JMenuItem cutItem = new JMenuItem(cutAction);
        cutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_X,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		JMenuItem copyItem = new JMenuItem(copyAction);
		copyAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_C,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		JMenuItem pasteItem = new JMenuItem(pasteAction);
		pasteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_V,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		JMenuItem clearItem = new JMenuItem("Clear");
		JMenuItem selectAllItem = new JMenuItem("Select All");
		
		cutItem.setText("Cut");
		copyItem.setText("Copy");
		pasteItem.setText("Paste");
		cutItem.setIcon(new ImageIcon("cut.png"));
		copyItem.setIcon(new ImageIcon("copy.png"));
		pasteItem.setIcon(new ImageIcon("paste.png"));
		
        JMenu impMenu = new JMenu("Import");
        
        clearItem.addActionListener(this);
		selectAllItem.addActionListener(this);
		
		editMenu.add(undoItem);
		editMenu.add(redoItem);
		editMenu.add(cutItem);
		editMenu.add(copyItem);
		editMenu.add(pasteItem);
		editMenu.add(clearItem);
		editMenu.add(selectAllItem);
		
        //format menu
		JMenuItem boldMenuItem = new JMenuItem(boldAction);
        boldAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_B,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		JMenuItem underlineMenuItem = new JMenuItem(underlineAction);
        underlineAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_U,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		JMenuItem italicMenuItem = new JMenuItem(italicAction);
        italicAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_I, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

		boldMenuItem.setText("Bold");
		underlineMenuItem.setText("Underline");
		italicMenuItem.setText("Italic");

		boldMenuItem.setIcon(new ImageIcon("bold.png"));
		underlineMenuItem.setIcon(new ImageIcon("underline.png"));
		italicMenuItem.setIcon(new ImageIcon("italic.png"));
		
		formatMenu.add(boldMenuItem);
		formatMenu.add(underlineMenuItem);
		formatMenu.add(italicMenuItem);
        formatMenu.addSeparator();
        
        //text color
        JMenu colorMenu = new JMenu("Color");
        JMenuItem redTextItem = new JMenuItem(new StyledEditorKit.ForegroundAction("Red",Color.red));
		JMenuItem orangeTextItem = new JMenuItem(new StyledEditorKit.ForegroundAction("Orange",Color.orange));
		JMenuItem yellowTextItem = new JMenuItem(new StyledEditorKit.ForegroundAction("Yellow",Color.yellow));
		JMenuItem greenTextItem = new JMenuItem(new StyledEditorKit.ForegroundAction("Green",Color.green));
		JMenuItem blueTextItem = new JMenuItem(new StyledEditorKit.ForegroundAction("Blue",Color.blue));
		JMenuItem cyanTextItem = new JMenuItem(new StyledEditorKit.ForegroundAction("Cyan",Color.cyan));
		JMenuItem magentaTextItem = new JMenuItem(new StyledEditorKit.ForegroundAction("Magenta",Color.magenta));
		JMenuItem blackTextItem = new JMenuItem(new StyledEditorKit.ForegroundAction("Black",Color.black));

		redTextItem.setIcon(new ImageIcon("red.png"));
		orangeTextItem.setIcon(new ImageIcon("orange.png"));
		yellowTextItem.setIcon(new ImageIcon("yellow.png"));
		greenTextItem.setIcon(new ImageIcon("green.png"));
		blueTextItem.setIcon(new ImageIcon("blue.png"));
		cyanTextItem.setIcon(new ImageIcon("cyan.png"));
		magentaTextItem.setIcon(new ImageIcon("magenta.png"));
		blackTextItem.setIcon(new ImageIcon("black.png"));

		colorMenu.add(redTextItem);
		colorMenu.add(orangeTextItem);
		colorMenu.add(yellowTextItem);
		colorMenu.add(greenTextItem);
		colorMenu.add(blueTextItem);
		colorMenu.add(cyanTextItem);
		colorMenu.add(magentaTextItem);
		colorMenu.add(blackTextItem);
	
        formatMenu.add(colorMenu);
		
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
        servicesMenu.addSeparator();
        servicesMenu.add(pdbStructure);  
        servicesMenu.addSeparator();
        servicesMenu.add(ptms);
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
    
    private void insertNewLine(int numOfLines, JEditorPane pane) {	
    	HTMLDocument doc = (HTMLDocument) pane.getDocument();
		HTMLEditorKit kit = (HTMLEditorKit) pane.getEditorKit();
		insertNewLine(numOfLines,doc,kit);
    }
    
    private void insertNewLine(int numOfLines, HTMLDocument doc, HTMLEditorKit kit) {		
		try {
			for (int i = 0; i < numOfLines; i++) {
				kit.insertHTML(doc, doc.getLength(), "<br>", 0, 0, null);
			}
		} catch (BadLocationException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Override
    public void insertLineMiddle(String header, String line) {
    	insertNewLine(15,editorPane);
    	insertNewLine(15,headerPane);
    	insertLine(proteinAcc,header,line,headerPane.getDocument().getLength(),editorPane.getDocument().getLength());
    }
    
    @Override
    public void insertLineAboveTarget(String header, String line) {
    	HTMLDocument headerDoc = (HTMLDocument) headerPane.getDocument();
    	Element headerElement = headerDoc.getElement(proteinAcc);
    	
    	HTMLDocument editorDoc = (HTMLDocument) editorPane.getDocument();
    	Element editorElement = editorDoc.getElement(proteinAcc);
    	
    	insertLine(header,header,line,headerElement.getStartOffset(),editorElement.getStartOffset());
    }
    
    @Override
    public void insertLineBelowTarget(String header, String line) {
    	
    	HTMLDocument headerDoc = (HTMLDocument) headerPane.getDocument();
    	Element headerElement = headerDoc.getElement(proteinAcc);
    	
    	HTMLDocument editorDoc = (HTMLDocument) editorPane.getDocument();
    	Element editorElement = editorDoc.getElement(proteinAcc);
    	
    	insertLine(header,header,line,headerElement.getEndOffset(),editorElement.getEndOffset());
    }
	
	@Override
	public void insertLine(String id, String header, String line, int headerPosition, int editorPosition) {
		HTMLDocument headerDoc = (HTMLDocument) headerPane.getDocument();
		HTMLDocument editorDoc = (HTMLDocument) editorPane.getDocument();
		HTMLEditorKit headerPaneKit = (HTMLEditorKit) headerPane.getEditorKit();
		HTMLEditorKit editorPaneKit = (HTMLEditorKit) editorPane.getEditorKit();
		try {
			headerPaneKit.insertHTML(headerDoc, headerPosition, getHTML("pre",id,header,header), 0, 0, null);
			editorPaneKit.insertHTML(editorDoc, editorPosition, getHTML("pre",id,header,line), 0, 0, null);
		} catch (BadLocationException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String getHTML(String tag, String id, String header, String text) {
		String html = "<"+tag+" style=\"LINE-HEIGHT:0px;\" id=\""+id+"\" name=\""+header+"\">"+text+"</"+tag+">";
		System.out.println("$$$"+html);
		return html;
	}
	
	@Override
	public void addProteinSelecter(SelectProteinView selectProtein) {
		// TODO Auto-generated method stub
		proteinPanel.add(selectProtein);
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
		String [] headers = headerPane.getText().split(System.getProperty("line.separator"));
    	String [] lines = editorPane.getText().split(System.getProperty("line.separator"));
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
		String[] headers = StringUtils.split(headerPane.getText(), System.getProperty("line.separator"));
		String[] lines = StringUtils.split(editorPane.getText(), System.getProperty("line.separator"));
		//String [] headers = headerPane.getText().split("\r\n");
    	//String [] lines = editorPane.getText().split("\r\n");
    	int headerPosition = 0;//newLines.length();
    	int linePosition = 0;//newLines.length();
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

	@Override
	public JMenuItem pdbStructure() {
		// TODO Auto-generated method stub
		return pdbStructure;
	}
	
	@Override
	public JMenuItem ptms() {
		// TODO Auto-generated method stub
		return ptms;
	}

	@Override
	public JMenuItem foregroundColor() {
		// TODO Auto-generated method stub
		return foregroundColor;
	}

	@Override
	public JMenuItem backgroundColor() {
		// TODO Auto-generated method stub
		return backgroundColor;
	}

	@Override
	public JMenuItem clearPanes() {
		// TODO Auto-generated method stub
		return clearPanes;
	}
	
	class UndoHandler implements UndoableEditListener {

		/**
		 * Messaged when the Document has created an edit, the edit is
		 * added to <code>undo</code>, an instance of UndoManager.
		 */
		public void undoableEditHappened(UndoableEditEvent e) {
			undo.addEdit(e.getEdit());
			undoAction.update();
			redoAction.update();
		}
	}
	
	class UndoAction extends AbstractAction {
		public UndoAction() {
			super("Undo");
			setEnabled(false);
		}

		public void actionPerformed(ActionEvent e) {
			try {
				undo.undo();
			} catch (CannotUndoException ex) {
				System.out.println("Unable to undo: " + ex);
				ex.printStackTrace();
			}
			update();
			redoAction.update();
		}

		protected void update() {
			if(undo.canUndo()) {
				setEnabled(true);
				putValue(Action.NAME, undo.getUndoPresentationName());
			}else {
				setEnabled(false);
				putValue(Action.NAME, "Undo");
			}
		}
	}

	class RedoAction extends AbstractAction {
		
		public RedoAction() {
			super("Redo");
			setEnabled(false);
		}

		public void actionPerformed(ActionEvent e) {
			try {
				undo.redo();
			} catch (CannotRedoException ex) {
				System.err.println("Unable to redo: " + ex);
				ex.printStackTrace();
			}
			update();
			undoAction.update();
		}
	
		protected void update() {
			if(undo.canRedo()) {
				setEnabled(true);
				putValue(Action.NAME, undo.getRedoPresentationName());
			}else {
				setEnabled(false);
				putValue(Action.NAME, "Redo");
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String actionCommand = e.getActionCommand();
		
		if (actionCommand.compareTo("New") == 0){
			startNewDocument();
		} else if (actionCommand.compareTo("Open") == 0){
			openDocument();
		} else if (actionCommand.compareTo("Save") == 0){
			saveDocument();
		} else if (actionCommand.compareTo("Save As") == 0){
		        saveDocumentAs();
		} else if (actionCommand.compareTo("Exit") == 0){
			exit();
		} else if (actionCommand.compareTo("Clear") == 0){
			clear();
		} else if (actionCommand.compareTo("Select All") == 0){
			selectAll();
		}
	}
	
	protected void resetUndoManager() {
		undo.discardAllEdits();
		undoAction.update();
		redoAction.update();
	}
	
	public void startNewDocument(){
		Document oldDoc = editorPane.getDocument();
		if(oldDoc != null)
			oldDoc.removeUndoableEditListener(undoHandler);
		HTMLEditorKit editorKit = new HTMLEditorKit();
		editorDocument = (HTMLDocument)editorKit.createDefaultDocument();
		editorPane.setDocument(editorDocument);	
		headerDocument = (HTMLDocument)editorKit.createDefaultDocument();
		headerPane.setDocument(headerDocument);	
		currentFile = null;
		editorPane.getDocument().addUndoableEditListener(undoHandler);
		headerPane.getDocument().addUndoableEditListener(undoHandler);
		resetUndoManager();
	}

	public void openDocument(){
		File current = new File(".");
		JFileChooser chooser = new JFileChooser(current);
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setFileFilter(new HTMLFileFilter());
		int approval = chooser.showOpenDialog(this);
		if (approval == JFileChooser.APPROVE_OPTION){
			currentFile = chooser.getSelectedFile();
			editorTempFile = chooser.getSelectedFile();
			loadDocument();
		}

	}
	
	@Override
	public void reloadDocument() {
		tempSaveDocument();
		loadDocument();
	}
	
	public void tempSaveDocument(){
		try{
			FileWriter fw = new FileWriter(editorTempFile);
			fw.write(editorPane.getText());
			fw.close();
		}catch(FileNotFoundException fnfe){
			System.err.println("FileNotFoundException: " + fnfe.getMessage());			
		}catch(IOException ioe){
			System.err.println("IOException: " + ioe.getMessage());
		}			
	}
	
	public void loadDocument() {
		FileReader fr;
		try {
			fr = new FileReader(editorTempFile);
			Document oldDoc = editorPane.getDocument();
			if(oldDoc != null)
				    oldDoc.removeUndoableEditListener(undoHandler);
			HTMLEditorKit editorKit = new HTMLEditorKit();
			editorDocument = (HTMLDocument)editorKit.createDefaultDocument();
			editorKit.read(fr,editorDocument,0);
			editorDocument.addUndoableEditListener(undoHandler);
			editorPane.setDocument(editorDocument);
			
			ArrayList<Header> headers = new ArrayList<Header>();
			org.jsoup.nodes.Document doc = Jsoup.parse(editorTempFile,null);
			org.jsoup.select.Elements pres = doc.select("pre");
			int i = 0;
			for (org.jsoup.nodes.Element pre : pres) {
			    System.out.format("pre #%d:\n", ++i);
			    Header header = new Header();
			    for(Attribute attr : pre.attributes()) {
			    	if (attr.getKey().equals("name")) {
			    		header.setName(attr.getValue());
			    		System.out.format("%s = %s\n", attr.getKey(), attr.getValue());
			        }
			    	if (attr.getKey().equals("id")) {
			    		header.setId(attr.getValue());
			    		System.out.format("%s = %s\n", attr.getKey(), attr.getValue());
			        }
			    }
			    headers.add(header);
			}
			
			oldDoc = headerPane.getDocument();
			if(oldDoc != null)
				    oldDoc.removeUndoableEditListener(undoHandler);
			HTMLEditorKit headerPaneKit = new HTMLEditorKit();
			headerDocument = (HTMLDocument)headerPaneKit.createDefaultDocument();
			
			insertNewLine(14,headerDocument,headerPaneKit);
			
			for (Header header: headers) {
				headerPaneKit.insertHTML(headerDocument, headerDocument.getLength(), getHTML("pre",header.id,header.name,header.name), 0, 0, null);
			}
			headerDocument.addUndoableEditListener(undoHandler);
			headerPane.setDocument(headerDocument);
			
			resetUndoManager();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void saveDocument(){
		if (currentFile != null){
			try{
				FileWriter fw = new FileWriter(currentFile);
				fw.write(editorPane.getText());
				fw.close();
			}catch(FileNotFoundException fnfe){
				System.err.println("FileNotFoundException: " + fnfe.getMessage());			
			}catch(IOException ioe){
				System.err.println("IOException: " + ioe.getMessage());
			}	
		}else{
			saveDocumentAs();
		}			
	}

	public void saveDocumentAs(){
		try{
			File current = new File(".");
			JFileChooser chooser = new JFileChooser(current);
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			chooser.setFileFilter(new HTMLFileFilter());
			int approval = chooser.showSaveDialog(this);
			if (approval == JFileChooser.APPROVE_OPTION){
				File newFile = chooser.getSelectedFile();
				if (newFile.exists()){
					String message = newFile.getAbsolutePath() 
						+ " already exists. \n"
						+ "Do you want to replace it?";
					if (JOptionPane.showConfirmDialog(this, message) == JOptionPane.YES_OPTION){	
						currentFile = newFile;
						setTitle(currentFile.getName());	
						FileWriter fw = new FileWriter(currentFile);
						fw.write(editorPane.getText());
						fw.close();
					}
				}else{
					currentFile = new File(newFile.getAbsolutePath());
					setTitle(currentFile.getName());	
					FileWriter fw = new FileWriter(currentFile);
					fw.write(editorPane.getText());
					fw.close();
				}
			}
		}catch(FileNotFoundException fnfe){
			System.err.println("FileNotFoundException: " + fnfe.getMessage());			
		}catch(IOException ioe){
			System.err.println("IOException: " + ioe.getMessage());
		}
	}

	public void exit(){
		String exitMessage = "Are you sure you want to exit?";
		if (JOptionPane.showConfirmDialog(this, exitMessage) == JOptionPane.YES_OPTION){
			System.exit(0);
		}
	}

	public void clear(){
		startNewDocument();
	}
	
	public void selectAll(){
		editorPane.selectAll();
	}
	
	class HTMLFileFilter extends javax.swing.filechooser.FileFilter{
		
		public boolean accept(File f){
			return ((f.isDirectory()) ||(f.getName().toLowerCase().indexOf(".htm") > 0));
		}
		
		public String getDescription(){
			return "html";
		}
	}
	
	class FrameListener extends WindowAdapter{
		public void windowClosing(WindowEvent we){
			exit();
		}
	}
	
	public static void setUIFont (javax.swing.plaf.FontUIResource f){
	    java.util.Enumeration keys = UIManager.getDefaults().keys();
	    while (keys.hasMoreElements()) {
	      Object key = keys.nextElement();
	      Object value = UIManager.get (key);
	      if (value != null && value instanceof javax.swing.plaf.FontUIResource)
	        UIManager.put (key, f);
	    }
	} 
	
	private class Header {
		private String id;
		private String name;
		public Header () {}
		public Header (String id, String name) {
			this.setId(id);
			this.setName(name);
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
}
