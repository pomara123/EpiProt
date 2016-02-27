package epiprot;

public class EpiProt {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
    	View view = new View();
    	Model model = new Model();
    	Presenter presenter = new Presenter(view,model);
    	view.setVisible(true);
    }
}
