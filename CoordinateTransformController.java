/*
Name: CoordinateTransformController.java
Description: Manages the view actions and interacts with the CoordinateManager
*/

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CoordinateTransformController{

	CoordinateTransformGUI view;	//CoordinateTransform GUI
	CoordinateManager model;		//CoordinateTransform request manager


	public CoordinateTransformController(CoordinateTransformGUI view, CoordinateManager model){
		this.view = view;
		this.model = model;
		CoordinateTransformGUISetup();
	}

	//Adds the listeners to the view
	public void CoordinateTransformGUISetup(){
		view.addFromComboBoxListener(new comboBoxListener());
		view.addToComboBoxListener(new comboBoxListener());
		view.addConvertButtonListener(new convertButtonListener());
	}


	//Updates the center view where users can input coordinates
	//based upon the change in selection of the combo boxes
	class comboBoxListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			view.setInputPanel(view.getFromComboSelection(), view.getToComboSelection());
		}
	}


	//Updates the convertText results field in the GUI, this is the brain
	//of the GUI in terms of input ingestion and sanitization.
	class convertButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			 String from = view.getFromComboSelection(); 	//Retrieves the From selection
			 String to = view.getToComboSelection();		//Retrieves the To selection
			 Double[] input = view.getStandardInput();		//Retrieves the user input for standard inputs
			 Double[] inputRef = view.getRefPtInput();		//Retrieves the user input for reference points

			 //Blank input check
			 if (containsNullInput(input)|| containsNullInput(inputRef)){
			 	return;
			 }


			 //Selection Conditionals
			 if (from.equals(to)){
			 	if (from.equals("LLA")){
			 		//LLA<->LLA
			 		view.setConvertText(model.lla(input));
			 	} else if (from.equals("ECEF")){
			 		//ECEF<->ECEF
			 		view.setConvertText(model.ecef(input));
			 	}else{
			 		//ENU<->ENU
			 		view.setConvertText(model.enu(input));
			 	}
			 } else{
			 	if (from.equals("LLA")){
			 		if (to.equals("ECEF")){
			 			//LLA<->ECEF
			 			view.setConvertText(model.lla2ecef(input));
			 		} else {
			 			//LLA<->ENU
			 			view.setConvertText(model.lla2enu(input, inputRef));
			 		}
			 	} else if(from.equals("ECEF")){
			 		if(to.equals("LLA")){
			 			//ECEF<->LLA
			 			view.setConvertText(model.ecef2lla(input));
			 		} else {
			 			//ECEF<->ENU
			 			view.setConvertText(model.ecef2enu(input,inputRef));
			 		}
			 	} else {
			 		if (to.equals("LLA")) {
			 			//ENU<->LLA
			 			view.setConvertText(model.enu2lla(input, inputRef));
			 		} else {
			 			//ENU<->ECEF
			 			view.setConvertText(model.enu2ecef(input, inputRef));
			 		}
			 	}
			 }
		}


		//Parses through double array to check for empty/null indices
		public Boolean containsNullInput(Double[] input){
			for (int i = 0; i < input.length; ++i)
				if (input[i] == null)
					return true;
			return false;
		}

	}

}