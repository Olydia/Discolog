package fr.limsi.application.SaisiePref;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class Element extends JPanel {
	private JLabel elem;
	private JTextField classement;
	
	public Element( String name) {
		//this.setBackground(Color.CYAN);

		elem = new JLabel(name);
		elem.setHorizontalAlignment(JLabel.CENTER);
		elem.setFont(new Font("Serif", Font.BOLD, 17));
		classement = new JTextField();
		GridLayout g  = new GridLayout(2, 1);
		this.setLayout(g);
		this.add(elem);
		this.add(classement);
	}
		
		public int getValue(){
			if(classement.getText().equals("") || !isNumeric(classement.getText())){
                Border border = BorderFactory.createLineBorder(Color.red);
                classement.setBorder(border);
				classement.setText("Specifiez une valeur");
				return -1;
			}
			else
				// verfier que c'est bien un chiffre qui est rentré
				return Integer.parseInt(classement.getText());
		}
		
		public static boolean isNumeric(String str)  
		{  
		  try  
		  {  
		    int d = Integer.parseInt(str);  
		  }  
		  catch(NumberFormatException nfe)  
		  {  
		    return false;  
		  }  
		  return true;  
		}
}
