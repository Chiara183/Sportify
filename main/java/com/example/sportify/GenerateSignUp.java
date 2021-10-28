package main.java;

import javax.swing.*;
import java.lang.Exception;

/**
 * @author Chiara
 *
 */
public class GenerateSignUp {

	//main() method start
	public static void main(String[] args) {
		  
        try {  
            //create instance of the CreateLoginForm  
            SignUpUser form = new SignUpUser();  
            form.setSize(500,500);  				//set size of the frame  
            form.setVisible(true);  				//make form visible to the user  
        } 
        
        catch(Exception e)  
        {     
            //handle exception   
            JOptionPane.showMessageDialog(null, e.getMessage());  
        }  

	}

}
