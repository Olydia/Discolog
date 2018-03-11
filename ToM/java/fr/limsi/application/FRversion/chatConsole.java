package fr.limsi.application.FRversion;
import java.io.IOException;
import java.io.OutputStream;

import fr.limsi.negotiate.StringToUTF8;
import javafx.scene.control.TextArea;

public class chatConsole extends OutputStream{
	   private TextArea    output;

	    public chatConsole(TextArea ta)
	    {	
	        this.output = ta ;
	        output.setEditable(false);
	        //this.output.setDisable(true);
	       
	    }

	    @Override
	    public void write(int i) throws IOException
	    {
	    	//String value =StringToUTF8.convertToUTF8();
	    	
	        output.appendText(String.valueOf((char) i)) ;
	        output.setStyle("-fx-font-size: 16px;");


	    }
}
