package main.java; /**
 * @author Matteo
 *
 */

//import required classes and packages  
import javax.swing.*;  
  
//create NewPage class to create a new page on which user will navigate  
class NewPage extends JFrame {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -2340680044802135489L;
	/**
	 * 
	 */
	
	//constructor  
    NewPage()  
    {  
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);  
        setTitle("Welcome");  
        setSize(400, 200);  
    } 
}
