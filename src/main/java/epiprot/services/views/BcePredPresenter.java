package epiprot.services.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import epiprot.Presenter;
import epiprot.Protein;
import epiprot.services.epitopePrediction.BcePredAminoAcid;
import epiprot.services.epitopePrediction.BcePredService;
import epiprot.services.phosphosite.PhosphoSiteAminoAcid;
import epiprot.services.phosphosite.PhosphoSitePTM;
import epiprot.services.phosphosite.PhosphoSiteService;
import epiprot.services.uniprot.UniProtAminoAcid;
import epiprot.services.uniprot.UniProtPTM;

public class BcePredPresenter {
	
	Presenter presenter;
	
	private String hydro = "";
	private String flexi = "";
	private String access = "";
	private String turns = "";
	private String surface = "";
	private String polar = "";
	private String antipro = "";
	private String max = "";
	private String min = "";
	private String average = "";
	
	interface View {
		 JTextField hydrophilicityTextField();
		 JTextField accessibilityTextField();
		 JTextField exposedSurfaceTextField();
		 JTextField antegenicPropensityTextField();
		 JTextField flexibilityTextField();
		 JTextField turnsTextField();
		 JTextField polarityTextField();
		 JTextField combinedTextField();
		 JCheckBox hydrophilicityCheckBox();
		 JCheckBox accessibilityCheckBox();
		 JCheckBox exposedSurfaceCheckBox();
		 JCheckBox antegenicPropensityCheckBox();
		 JCheckBox flexibilityCheckBox();
		 JCheckBox turnsCheckBox();
		 JCheckBox polarityCheckBox();
		 JCheckBox combinedCheckBox();
		 JCheckBox chckbxSelectAll();
		 JButton btnSubmit();
	}
	
	interface SummaryView {
		JTextPane textPane();
		void appendString(String str);
	}

	public BcePredPresenter(Presenter presenter) {
		// TODO Auto-generated constructor stub
		this.presenter = presenter;
		bindHandlers();
		
	}
	
	View view = new BcePredView();
	
	public void bindHandlers() {
		view.btnSubmit().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				for(int i = 0; i < presenter.protein.getSequence().length(); i++) {
					addDashToAllLines();
				}
				BcePredService bps = new BcePredService(presenter.proteinAcc, view.combinedTextField().getText(), view.polarityTextField().getText(),
						view.turnsTextField().getText(), view.flexibilityTextField().getText(), view.antegenicPropensityTextField().getText(),
						view.exposedSurfaceTextField().getText(), view.hydrophilicityTextField().getText(), view.accessibilityTextField().getText());
				bps.setSelectAccess(view.accessibilityCheckBox().isSelected());
				bps.setSelectAntipro(view.antegenicPropensityCheckBox().isSelected());
				bps.setSelectFlexi(view.flexibilityCheckBox().isSelected());
				bps.setSelectHydro(view.hydrophilicityCheckBox().isSelected());
				bps.setSelectPolar(view.polarityCheckBox().isSelected());
				bps.setSelectSurface(view.exposedSurfaceCheckBox().isSelected());
				bps.setSelectTurns(view.turnsCheckBox().isSelected());				
				bps.run();
				
				ArrayList<BcePredAminoAcid> aaList = bps.getAminoAcids();
				
				ArrayList<LineElement> accessLineList = getLineList();
				ArrayList<LineElement> antiproLineList = getLineList();
				ArrayList<LineElement> averageLineList = getLineList();
				ArrayList<LineElement> flexiLineList = getLineList();
				ArrayList<LineElement> hydroLineList = getLineList();
				ArrayList<LineElement> maxLineList = getLineList();
				ArrayList<LineElement> minLineList = getLineList();
				ArrayList<LineElement> polarLineList = getLineList();
				ArrayList<LineElement> surfaceLineList = getLineList();
				ArrayList<LineElement> turnsLineList = getLineList();
				
				for(int i = 0; i < aaList.size(); i++) {
					BcePredAminoAcid aa = aaList.get(i);
					System.out.println(aa.toString());
					if(aa.getAccessRelative() != -1 && aa.getAccess() > Double.parseDouble(view.accessibilityTextField().getText())) {
						accessLineList.get(i).setCharacter( aa.getAccessRelative());
						accessLineList.get(i).setColor("blue");
					}
					else if(aa.getAccessRelative() != -1) {
						accessLineList.get(i).setCharacter( aa.getAccessRelative());
					}
					
					if(aa.getAntiProRelative() != -1 && aa.getAntiPro() > Double.parseDouble(view.antegenicPropensityTextField().getText())) {
						antiproLineList.get(i).setCharacter( aa.getAntiProRelative());
						antiproLineList.get(i).setColor("blue");
					}
					else if(aa.getAntiProRelative() != -1) {
						antiproLineList.get(i).setCharacter( aa.getAntiProRelative());
					}
					
					if(aa.getAverageRelative() != -1 && aa.getAverage() > Double.parseDouble(view.combinedTextField().getText())) {
						averageLineList.get(i).setCharacter( aa.getAverageRelative());
						averageLineList.get(i).setColor("blue");
					}
					else if(aa.getAverageRelative() != -1) {
						averageLineList.get(i).setCharacter( aa.getAverageRelative());
					}
					
					if(aa.getFlexiRelative() != -1 && aa.getFlexi() > Double.parseDouble(view.flexibilityTextField().getText())) {
						flexiLineList.get(i).setCharacter( aa.getFlexiRelative());
						flexiLineList.get(i).setColor("blue");
					}
					else if(aa.getFlexiRelative() != -1) {
						flexiLineList.get(i).setCharacter( aa.getFlexiRelative());
					}
					
					if(aa.getHydroRelative() != -1 && aa.getHydro() > Double.parseDouble(view.hydrophilicityTextField().getText())) {
						hydroLineList.get(i).setCharacter( aa.getHydroRelative());
						hydroLineList.get(i).setColor("blue");
					}
					else if(aa.getHydroRelative() != -1) {
						hydroLineList.get(i).setCharacter( aa.getHydroRelative());
					}
					
					if(aa.getMaxRelative() != -1 && aa.getMax() > Double.parseDouble(view.combinedTextField().getText())) {
						maxLineList.get(i).setCharacter( aa.getMaxRelative());
						maxLineList.get(i).setColor("blue");
					}
					else if(aa.getMaxRelative() != -1) {
						maxLineList.get(i).setCharacter( aa.getMaxRelative());
					}
					
					if(aa.getMinRelative() != -1 && aa.getMin() > Double.parseDouble(view.combinedTextField().getText())) {
						minLineList.get(i).setCharacter( aa.getMinRelative());
						minLineList.get(i).setColor("blue");
					}
					else if(aa.getMinRelative() != -1) {
						minLineList.get(i).setCharacter( aa.getMinRelative());
					}
					
					if(aa.getPolarRelative() != -1 && aa.getPolar() > Double.parseDouble(view.polarityTextField().getText())) {
						polarLineList.get(i).setCharacter( aa.getPolarRelative());
						polarLineList.get(i).setColor("blue");
					}
					else if(aa.getPolarRelative() != -1) {
						polarLineList.get(i).setCharacter( aa.getPolarRelative());
					}
					
					if(aa.getSurfaceRelative() != -1 && aa.getSurface() > Double.parseDouble(view.exposedSurfaceTextField().getText())) {
						surfaceLineList.get(i).setCharacter(aa.getSurfaceRelative());
						surfaceLineList.get(i).setColor("blue");
					}
					else if(aa.getSurfaceRelative() != -1) {
						surfaceLineList.get(i).setCharacter( aa.getSurfaceRelative());
					}
					
					if(aa.getTurnsRelative() != -1 && aa.getTurns() > Double.parseDouble(view.turnsTextField().getText())) {
						turnsLineList.get(i).setCharacter( aa.getTurnsRelative());
						turnsLineList.get(i).setColor("blue");
					}
					else if(aa.getTurnsRelative() != -1) {
						turnsLineList.get(i).setCharacter( aa.getTurnsRelative());
					}
				}
				
				access = getLine(accessLineList);
				antipro = getLine(antiproLineList);
				flexi = getLine(flexiLineList);
				hydro = getLine(hydroLineList);
				polar = getLine(polarLineList);
				surface = getLine(surfaceLineList);
				turns = getLine(turnsLineList);
				average = getLine(averageLineList);
				min = getLine(minLineList);
				max = getLine(maxLineList);
				
				System.out.println("access"+access);
				System.out.println("antipro"+antipro);
				System.out.println("flexi"+flexi);
				System.out.println("hydro"+hydro);
				System.out.println("polar"+polar);
				System.out.println("surface"+surface);
				System.out.println("turns"+turns);
				System.out.println("average"+average);
				System.out.println("min"+min);
				System.out.println("max"+max);
				
				
				if (containsAnythingButDash(access)) {
					presenter.insertLineAboveTarget("BcePred: Accessible (Emini)", getInsertLine(access));
				}
				if (containsAnythingButDash(antipro)) {
					presenter.insertLineAboveTarget("BcePred: Antigenic Propensity (Kolaskar)", getInsertLine(antipro));
				}
				if (containsAnythingButDash(flexi)) {
					presenter.insertLineAboveTarget("BcePred: Flexibility (Karplus)", getInsertLine(flexi));
				}
				if (containsAnythingButDash(hydro)) {
					presenter.insertLineAboveTarget("BcePred: Hydrophilicity (Parker)", getInsertLine(hydro));
				}
				if (containsAnythingButDash(polar)) {
					presenter.insertLineAboveTarget("BcePred: Polarity (Ponnuswamy)", getInsertLine(polar));
				}
				if (containsAnythingButDash(surface)) {
					presenter.insertLineAboveTarget("BcePred: Exposed Surface (Janin)", getInsertLine(surface));
				}
				if (containsAnythingButDash(turns)) {
					presenter.insertLineAboveTarget("BcePred: Turns (Pellequer)", getInsertLine(turns));
				}
				if (containsAnythingButDash(average)) {
					presenter.insertLineAboveTarget("BcePred: Average", getInsertLine(average));
				}
				if (containsAnythingButDash(min)) {
					presenter.insertLineAboveTarget("BcePred: Min", getInsertLine(min));
				}
				if (containsAnythingButDash(max)) {
					presenter.insertLineAboveTarget("BcePred: Max", getInsertLine(max));
				}
			}
		});
		
		view.accessibilityTextField().getDocument().addDocumentListener(new SubmitButtonListener(view.accessibilityTextField()));		
		view.antegenicPropensityTextField().getDocument().addDocumentListener(new SubmitButtonListener(view.antegenicPropensityTextField()));		
		view.combinedTextField().getDocument().addDocumentListener(new SubmitButtonListener(view.combinedTextField()));		
		view.exposedSurfaceTextField().getDocument().addDocumentListener(new SubmitButtonListener(view.exposedSurfaceTextField()));		
		view.flexibilityTextField().getDocument().addDocumentListener(new SubmitButtonListener(view.flexibilityTextField()));		
		view.hydrophilicityTextField().getDocument().addDocumentListener(new SubmitButtonListener(view.hydrophilicityTextField()));		
		view.polarityTextField().getDocument().addDocumentListener(new SubmitButtonListener(view.polarityTextField()));
		view.turnsTextField().getDocument().addDocumentListener(new SubmitButtonListener(view.turnsTextField()));
		
		view.accessibilityCheckBox().addActionListener(new CheckBoxListener(view));
		view.antegenicPropensityCheckBox().addActionListener(new CheckBoxListener(view));
		view.combinedCheckBox().addActionListener(new CheckBoxListener(view));
		view.exposedSurfaceCheckBox().addActionListener(new CheckBoxListener(view));
		view.flexibilityCheckBox().addActionListener(new CheckBoxListener(view));
		view.hydrophilicityCheckBox().addActionListener(new CheckBoxListener(view));
		view.polarityCheckBox().addActionListener(new CheckBoxListener(view));
		view.turnsCheckBox().addActionListener(new CheckBoxListener(view));		
		view.chckbxSelectAll().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (view.chckbxSelectAll().isSelected()) {
					view.accessibilityCheckBox().setSelected(true);
					view.combinedCheckBox().setSelected(true);
					view.antegenicPropensityCheckBox().setSelected(true);
					view.exposedSurfaceCheckBox().setSelected(true);
					view.flexibilityCheckBox().setSelected(true);
					view.hydrophilicityCheckBox().setSelected(true);
					view.polarityCheckBox().setSelected(true);
					view.turnsCheckBox().setSelected(true);
					view.btnSubmit().setEnabled(true);
				}
				else {
					view.accessibilityCheckBox().setSelected(false);
					view.combinedCheckBox().setSelected(false);
					view.antegenicPropensityCheckBox().setSelected(false);
					view.exposedSurfaceCheckBox().setSelected(false);
					view.flexibilityCheckBox().setSelected(false);
					view.hydrophilicityCheckBox().setSelected(false);
					view.polarityCheckBox().setSelected(false);
					view.turnsCheckBox().setSelected(false);
					view.btnSubmit().setEnabled(false);
				}
			}
			
		});
	}
	
	private String getLine(ArrayList<LineElement> lineList) {
		String line = "";
		for (LineElement le : lineList) {
			if(le.color == null) {
				line = line + le.character;
			}
			else {
				line = line + getBlueColoredText(le.character);
			}
		}
		return line;
	}
	
	private String getBlueColoredText(int text) {
		return "<font color=\"blue\">"+text+"</font>";
	}
	private boolean containsAnythingButDash(String s) {
		for(int i = 0; i < s.length(); i++) {
			if (s.charAt(i) != '-') {return true;}
		}
		return false;
	}
	
	private String getUniProtLine() {
		String line = "";
		for(int i = 0; i < presenter.protein.getSequence().length(); i++) {
			line = line + "-";
		}
		return line;
	}
	
	private boolean isInteger(String s) {
		try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    // only got here if we didn't return false
	    return true;
	}
	
	public void checkTextField(String text) {
		if(isInteger(text) && Integer.parseInt(text) >= 0) {
			view.btnSubmit().setEnabled(true);
		}
		else {
			view.btnSubmit().setEnabled(false);
		}
	}
	
	public String getInsertLine(String inputLine) {
		String mainLine = presenter.getMainLine();
		for(int i = 0; i < mainLine.length(); i++) {
			if(mainLine.charAt(i) == '-') {
				inputLine = new StringBuilder(inputLine).insert(i, " ").toString();
			}
		}
		return inputLine;
	}
	
	public ArrayList<UniProtAminoAcid> getAminoAcids() {
		ArrayList<UniProtAminoAcid> aaList = new ArrayList<UniProtAminoAcid>();
		for (int i = 0; i< presenter.protein.getSequence().length(); i++) {
			aaList.add(new UniProtAminoAcid());			
		}
		return aaList;
	}
	
	private void addDashToAllLines() {
		hydro = hydro + "-";
		flexi = flexi +"-";
		access = access+"-";
	    turns = turns+"-";
		surface = surface+"-";
		polar = polar+"-";
		antipro = antipro+"-";
		max = max+"-";
		min = min+"-";
		average = average +"-";
	}
	
	private ArrayList<LineElement> getLineList() {
		ArrayList<LineElement> lineList = new ArrayList<LineElement>();
		for(int i = 0; i < presenter.protein.getSequence().length(); i++) {
			LineElement le = new LineElement();
			lineList.add(le);
		}
		return lineList;
	}
	
	public class SubmitButtonListener implements DocumentListener {
		
		JTextField textField;
		
		public SubmitButtonListener(JTextField textField){
			this.textField = textField;
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			checkTextField(textField.getText());
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			checkTextField(textField.getText());
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			checkTextField(textField.getText());
		}
		
	}
	
	public class CheckBoxListener implements ActionListener {
		
		View phosphoSiteView;
		
		public CheckBoxListener(View phosphoSiteView) {
			this.phosphoSiteView = phosphoSiteView;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (phosphoSiteView.accessibilityCheckBox().isSelected() || phosphoSiteView.antegenicPropensityCheckBox().isSelected() || phosphoSiteView.combinedCheckBox().isSelected() ||
					phosphoSiteView.exposedSurfaceCheckBox().isSelected() || phosphoSiteView.flexibilityCheckBox().isSelected() || 
					phosphoSiteView.hydrophilicityCheckBox().isSelected() || phosphoSiteView.polarityCheckBox().isSelected() || phosphoSiteView.turnsCheckBox().isSelected()) {
				view.btnSubmit().setEnabled(true);
			}
			else {
				view.btnSubmit().setEnabled(false);
			}
		}
		
	}
	
	private class LineElement {
    	private int character;
    	private int positionInLine;
    	private String color;
    	public LineElement(){}
    	public LineElement(int character){
    		this.character = character;
    	}
    	public LineElement(int character, int positionInLine, String color){
    		this.setCharacter(character);
    		this.setPositionInLine(positionInLine);
    		this.setColor(color);
    	}
    	public int getCharacter() {
			return character;
		}
		public void setCharacter(int character) {
			this.character = character;
		}
		public String getColor() {
			return color;
		}
		public void setColor(String color) {
			this.color = color;
		}
		public int getPositionInLine() {
			return positionInLine;
		}
		public void setPositionInLine(int positionInLine) {
			this.positionInLine = positionInLine;
		}
		public String toString() {
			return character+"|"+color;
		}
    }
    
    private class ListPlusPosition {
    	public String line;
    	public int position;
    	public ListPlusPosition(){}
    	public String toString() {
    		return line+position;
    	}
    }
}
