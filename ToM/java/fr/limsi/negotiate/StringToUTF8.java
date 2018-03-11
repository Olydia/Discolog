package fr.limsi.negotiate;

import java.io.UnsupportedEncodingException;

public class StringToUTF8 {
//	   public static String convertToUTF8(String s) {
//	        String out = null;
//	        try {
//	            out = new String(s.getBytes("UTF-8"), "ISO-8859-1");
//	        } catch (java.io.UnsupportedEncodingException e) {
//	            return s;
//	        }
//	        return out;
//	    }
	   
	   public static final String convertToUTF8(String s )
	   {
		    byte bytes[] = null;
			try {
				bytes = s.getBytes("UTF8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	       try
	       {
	           return new String( bytes, "UTF-8" );
	       }
	       catch ( UnsupportedEncodingException uee )
	       {
	           return "";
	       }
	   }
}
