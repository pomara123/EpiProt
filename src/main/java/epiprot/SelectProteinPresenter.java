package epiprot;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

public class SelectProteinPresenter {

	public interface View {
		JCheckBox checkBox();

		String getUniProtAcc();

		void createUI();
	}

	View view;

	void bindHandler() {
		view.checkBox().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (view.checkBox().isSelected()) {
					Presenter.msaProteinList.add(view.getUniProtAcc());
				} else {
					if (Presenter.msaProteinList.contains(view.getUniProtAcc())) {
						Presenter.msaProteinList.remove(view.getUniProtAcc());
					}
				}
			}
		});
	}

	public SelectProteinPresenter(View view) {
		// TODO Auto-generated constructor stub
		this.view = view;
		bindHandler();
	}

}
