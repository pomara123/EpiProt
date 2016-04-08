package epiprot;

import java.awt.Color;

public class Utils {
  public static String getHTMLColorString(Color color) {
    String red = Integer.toHexString(color.getRed());
    String green = Integer.toHexString(color.getGreen());
    String blue = Integer.toHexString(color.getBlue());

    return "#" + 
            (red.length() == 1? "0" + red : red) +
            (green.length() == 1? "0" + green : green) +
            (blue.length() == 1? "0" + blue : blue);        
}
}