package epiprot.services.views;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class SelectPDBView extends JPanel implements SelectPDBPresenter.View {

	private String proteinAcc;
	private String pdbId;
	private double resolution;
	private String method;
	private String chain;
	private JCheckBox checkBox;
	private String positions;
	

	public SelectPDBView(String proteinAcc, String pdbId, String method, double resolution, String chain, String positions) {
		setSize(new Dimension(500, 30));
		this.proteinAcc = proteinAcc;
		this.pdbId = pdbId;
		this.method = method;
		this.resolution = resolution;
		this.chain = chain;
		this.positions = positions;
		createUI();
	}
	
	public SelectPDBView(String proteinAcc, String pdbId, String method, double resolution, String positions) {
		setSize(new Dimension(500, 30));
		this.proteinAcc = proteinAcc;
		this.pdbId = pdbId;
		this.method = method;
		this.resolution = resolution;
		this.positions = positions;
		createUI();
	}
	
	@Override
	public void createUI() {
		setPreferredSize(new Dimension(500, 30));
		setMaximumSize(new Dimension(32767, 30));
		setMinimumSize(new Dimension(10, 30));
		FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT, 5, 5);
		setLayout(flowLayout);
		
		checkBox = new JCheckBox(pdbId+" "+method+" "+resolution+" "+positions);
		checkBox.setPreferredSize(new Dimension(500, 23));
		add(checkBox);
	
	}

	@Override
	public JCheckBox checkBox() {
		// TODO Auto-generated method stub
		return checkBox;
	}
	
	@Override
	public String getCheckBoxText() {
		return checkBox.getText();
	}
	
	public static void main (String[]args) {
		SelectPDBView view = new SelectPDBView("P31749","3OCB","X-ray",2.70,"A/B","144-480");
		System.out.println(view.getCheckBoxText());
	}

}
