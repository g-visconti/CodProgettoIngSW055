package start;



//import gui.ViewAccesso;
import gui.*;

public class Main {

	
	public static void main(String[] args) {
		
		//ViewAccesso frame = new ViewAccesso();
		
		ViewDashboard frame = new ViewDashboard("c.gallo@mail.it");
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
