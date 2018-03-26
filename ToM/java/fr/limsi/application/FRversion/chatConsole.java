package fr.limsi.application.FRversion;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javafx.scene.control.TextArea;

public class chatConsole extends OutputStream{
	private TextArea    output;
	private int idPrec = 0;
	boolean retour;
	public chatConsole(TextArea ta)
	{	
		this.output = ta ;
		output.setEditable(false);
		retour = false;

		//this.output.setDisable(true);

	}

	@SuppressWarnings("static-access")
	@Override
	public void write(int i) throws IOException
	{
		String local = output.getText();
		String value = String.valueOf((char) i);
		if(idPrec == -61)
			value = value.replaceAll("ﾠ", "à");
		value = value.replaceAll("ￃ", ""); // -61
		value = value.replaceAll("ﾈ", "è"); // -120
		value = value.replaceAll("ﾹ", "ù");
		value = value.replaceAll("ﾩ", "é");
		value = value.replaceAll("ﾧ", "ç");	
		
		output.appendText(value) ;
		output.setStyle("-fx-font-size: 16px;");
		idPrec = i;

	}
}
