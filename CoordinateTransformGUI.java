/*
Name: CoordinateTransformGUI.java
Description: GUI for interacting with the user
*/

import java.awt.*;
import javax.swing.*;
import javax.swing.JComboBox;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;


@SuppressWarnings("serial")
public class CoordinateTransformGUI extends JFrame{
	final static int WIDTH = 750;	//Frame Wifth
	final static int HEIGHT = 225;	//Frame Height
	private JPanel pnlSelection, pnlInput, pnlConvert; // Upper, middle, lower panels respectively
	private GridLayout gridSelection, gridInput, gridConvert; // grid layouts
	private JComboBox<String> fromCombo, toCombo; //JComboBox for user coordinate selections
	private final String[] frameSelctionStrings = {"LLA", "ECEF", "ENU"}; // Selectables
	private JButton convertButton; //Button for conversion
	private JTextField convertTextField; //Textfield for results

	//Frame titles
	TitledBorder frameSelectionTitle, llaTitle, ecefTitle, enuTitle, convertTitle;

	JPanel[] standardInput;	//Standard user input
	JPanel[] refPtInput;	//Reference point input

	JTextField[] standardInputTextField;	//Text fields for the inputs
	JTextField[] refPtTextField;

	JLabel[] standardInputLabels; //Labels for the standard input

	public CoordinateTransformGUI(){
		super("Coordinate Frame Conversion Tool -- Matt Bichay");

		//Initial setup
		setSize(WIDTH,HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		//Title setup
		frameSelectionTitle = BorderFactory.createTitledBorder("Select Coordinate Frame "+
																"~~~~~~~~~~~~~ From --> To"+
																"~~~~~ World Geodetic System 84 (Meters only)");
		llaTitle = BorderFactory.createTitledBorder("LLA - Latitude, Longitude, Altitude");
		ecefTitle = BorderFactory.createTitledBorder("ECEF - Earth-Centered, Earth-Fixed");
		enuTitle = BorderFactory.createTitledBorder("ENU - East, North, Up");
		convertTitle = BorderFactory.createTitledBorder("Coordinate Frame Transformation Results");
		

		//Combo Box Selection Setup
		gridSelection = new GridLayout(1,2);
		pnlSelection = new JPanel();
		pnlSelection.setLayout(gridSelection);
		pnlSelection.setBorder(frameSelectionTitle);
		fromCombo = new JComboBox<>(frameSelctionStrings);
		fromCombo.setEnabled(true);
		toCombo = new JComboBox<>(frameSelctionStrings);
		toCombo.setEnabled(true);
		toCombo.setSelectedIndex(1);
		pnlSelection.add(fromCombo);
		pnlSelection.add(toCombo);
		add(pnlSelection, BorderLayout.NORTH);


		//Convert Panel setup
		gridConvert = new GridLayout(2,1);
		pnlConvert = new JPanel();
		pnlConvert.setLayout(gridConvert);
		pnlConvert.setBorder(convertTitle);
		convertButton = new JButton("Convert");
		convertTextField = new JTextField("");
		convertTextField.setEditable(false);
		pnlConvert.add(convertButton);
		pnlConvert.add(convertTextField);
		convertTextField.setHorizontalAlignment(JTextField.CENTER);
		add(pnlConvert, BorderLayout.SOUTH);


		//Input Panel Setup
		createInputPanel();
		add(pnlInput, BorderLayout.CENTER);

		//Input Panel defaults
		setInputPanel("LLA", "ECEF");

		//Visible GUI
		setVisible(true);
	}


	//Initial input panel creation and setup
	public void createInputPanel(){

		//Initialization / Layout selection
		gridInput = new GridLayout(2,3);
		pnlInput = new JPanel();
		pnlInput.setLayout(gridInput);
		standardInput = new JPanel[3];
		refPtInput = new JPanel[3];
		standardInputTextField = new JTextField[3];
		refPtTextField = new JTextField[3];
		standardInputLabels = new JLabel[3];


		//Initialization / Default selection
		for (int i = 0; i < 3; ++i){
			standardInput[i] = new JPanel();
			refPtInput[i] = new JPanel();
			standardInputTextField[i] = new JTextField();
			refPtTextField[i] = new JTextField();
			standardInputLabels[i] = new JLabel("");

			standardInput[i].setLayout(new BorderLayout());
			standardInput[i].add(standardInputLabels[i], BorderLayout.WEST);
			standardInput[i].add(standardInputTextField[i], BorderLayout.CENTER);

			refPtInput[i].setLayout(new BorderLayout());
			refPtInput[i].add(refPtTextField[i], BorderLayout.CENTER);
		}

		//Constant ref pt labels
		refPtInput[0].add(new JLabel("Ref Lat:"), BorderLayout.WEST);
		refPtInput[1].add(new JLabel("Ref Lon:"), BorderLayout.WEST);
		refPtInput[2].add(new JLabel("Ref Alt:"), BorderLayout.WEST);


		//Specific order for panel addition
		pnlInput.add(standardInput[0]);
		pnlInput.add(standardInput[1]);
		pnlInput.add(standardInput[2]);
		pnlInput.add(refPtInput[0]);
		pnlInput.add(refPtInput[1]);
		pnlInput.add(refPtInput[2]);
	}


	//Sets visible panel based upon ComboBox selections
	public void setInputPanel(String input, String output){

		//Reference point box availability set
		if (input.equals("ENU") || output.equals("ENU")){
			for (int i = 0; i < 3; ++i){
				refPtTextField[i].setEditable(true);
				refPtTextField[i].setText("");
			}
		} else{
			for (int i = 0; i < 3; ++i){
				refPtTextField[i].setEditable(false);
				refPtTextField[i].setText("0");
			}
		}


		//Input as LLA, ECEF, or ENU
		//Label setup and panel setup
		if (input.equals("LLA")){
			standardInputLabels[0].setText("Lat:");
			standardInputLabels[1].setText("Lon:");
			standardInputLabels[2].setText("Alt:");
			pnlInput.setBorder(llaTitle);
		} else if (input.equals("ECEF")){
			standardInputLabels[0].setText("X:");
			standardInputLabels[1].setText("Y:");
			standardInputLabels[2].setText("Z:");
			pnlInput.setBorder(ecefTitle);
		} else {
			standardInputLabels[0].setText("E:");
			standardInputLabels[1].setText("N:");
			standardInputLabels[2].setText("U:");
			pnlInput.setBorder(enuTitle);
		}


		//Show new changes
		setVisible(true);
	}


	//Display results of conversion string
	public void setConvertText(String text){
		convertTextField.setText(text);
	}


	//Gets selection from the From combo box
	public String getFromComboSelection(){
		return fromCombo.getSelectedItem().toString();
	}


	//Gets selection from the To combo box
	public String getToComboSelection(){
		return toCombo.getSelectedItem().toString();
	}


	//Adds From combobox action listener
	public void addFromComboBoxListener(ActionListener comboAction){
		fromCombo.addActionListener(comboAction);
	}


	//Adds To combobox action listener
	public void addToComboBoxListener(ActionListener comboAction){
		toCombo.addActionListener(comboAction);
	}


	//Adds convert button action listener
	public void addConvertButtonListener(ActionListener convertAction){
		convertButton.addActionListener(convertAction);
	}


	//Retrieves input from StandardInput
	//Checks for user-input garbage
	public Double[] getStandardInput(){
		Double[] input = new Double[3];
		try{
			input[0] = Double.parseDouble(standardInputTextField[0].getText());
			input[1] = Double.parseDouble(standardInputTextField[1].getText());
			input[2] = Double.parseDouble(standardInputTextField[2].getText());
			return input;
		} catch(NumberFormatException nfe){
			setConvertText("Please enter numeric values.");
			return input;
		}
	}


	//Retrieves input from RefPtInput
	//Checks for user-input garbage
	public Double[] getRefPtInput(){
		Double[] refInput = new Double[3];
		try{
			refInput[0] = Double.parseDouble(refPtTextField[0].getText());
			refInput[1] = Double.parseDouble(refPtTextField[1].getText());
			refInput[2] = Double.parseDouble(refPtTextField[2].getText());
			return refInput;
		} catch(NumberFormatException nfe){
			setConvertText("Please enter numeric values.");
			return refInput;
		}
	}

}