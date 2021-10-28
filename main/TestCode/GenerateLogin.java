package com.example.sportify;

import required classes and packages
import javax.swing.*;
import java.lang.Exception;


class GenerateLogin {

	//main() method start
	public static void main(String[] args) {
		  
        try {
            //create instance of the CreateLoginForm  
            Login form = new Login();  
            form.setSize(300,100);  				//set size of the frame  
            form.setVisible(true);  				//make form visible to the user  
        } 
        
        catch(Exception e)  
        {     
            //handle exception   
            JOptionPane.showMessageDialog(null, e.getMessage());  
        }

	}

}
