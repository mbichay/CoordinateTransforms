/*
Name: Driver.java
Description:Driver for the program.

Please refer to ReadMe.txt
*/

class Driver{
	public static void main(String[] args){
		CoordinateTransformGUI view = new CoordinateTransformGUI();
		CoordinateManager model = new CoordinateManager();
		CoordinateTransformController controller = new CoordinateTransformController(view, model);
	}
}