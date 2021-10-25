/**
 * @author Matteo
 *
 */

//import required classes and packages  
import javax.swing.*;  
import java.awt.*;  
import java.awt.event.*;
//import javax.swing.ImageIcon;
/*create Login class to create login form  
class extends JFrame to create a window where our component add  
class implements ActionListener to perform an action on button click*/ 
class Login extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8071224135365594907L;
	/**

	 *
	 */
	
	//initialize button, panel, label, and text field
	JButton b1;
	JPanel newPanel;
	JLabel userLabel, passLabel, logoLabel;
	final JTextField textField1, textField2;
	
	//calling constructor
	Login() {
		
		setLayout(new FlowLayout());
		
		//create image variable
		//ImageIcon logo = new ImageIcon(getClass().getResource("./src/logo.png"));
		
		//create label for logo
		//logoLabel = new JLabel(logo);
		
		//create label for username
		userLabel = new JLabel();
		userLabel.setText("Username");											//set label value for textField1
		
		//create text field to get username from the user
		textField1 = new JTextField(15);										//set length of the text
		
		//create label for password
		passLabel =new JLabel();
		passLabel.setText("Password");											//set label value for textField2
		
		//create text field to get password from user
		textField2 = new JPasswordField(15);									//set length for the password
		
		//create submit button
		b1 = new JButton("SUBMIT");												//set label to button
		
		//create panel to put form elements
		newPanel = new JPanel(new GridLayout(4,2,10,10));
		//newPanel.add(logoLabel);
		newPanel.add(userLabel);												//set username label to panel
		newPanel.add(textField1);												//set text field to panel
		newPanel.add(passLabel);												//set password label to panel
		newPanel.add(textField2);												//set text field to panel
		newPanel.add(b1);														//set button to panel
		
		//set border to panel
		add(newPanel, BorderLayout.CENTER);
		
		//perform action on button click
		b1.addActionListener(this);												//add action listener to button
		setTitle("LOGIN FORM");													//set title to login form
	}
	
	//define abstract method actionPerformed() which will be called on button click
	public void actionPerformed(ActionEvent ae) {								//pass action listener as a parameter
		
		String userValue = textField1.getText(); 								//get user entered username from the textField1
		String passValue = textField2.getText(); 								//get user entered password from the textField2
		
		//check whether the credentials are authentic or not
		if (userValue.equals("test1@gmail.com") && passValue.equals("test")){	//if authentic, navigate user to a new page
			
			//create instance of the NewPage
			NewPage page = new NewPage();
			
			//make page visible to the user
			page.setVisible(true);
			
			//create a welcome label and set it to the new page
			JLabel wel_label = new JLabel("Welcome " + userValue);
			page.getContentPane().add(wel_label);
		}
		else{
			//show error message
			System.out.println("Please enter valid username and password");
		}
	}
}
