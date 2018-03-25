package fr.limsi.application.FRversion;
import java.io.IOException;
import java.io.OutputStream;
import javafx.scene.control.TextArea;

public class chatConsole extends OutputStream{
	private TextArea    output;
	private int idPrec = 0; 
	public chatConsole(TextArea ta)
	{	
		this.output = ta ;
		output.setEditable(false);
		//this.output.setDisable(true);

	}

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
		
		//if(idPrec == 10 && ("BAK".indexOf(value.charAt(0)) != -1))
		if(idPrec == 10)
			System.err.println("retour chariot " + value);
		output.appendText(value) ;
		output.setStyle("-fx-font-size: 16px;");
		idPrec = i;

	}
}
