/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcpews.util;

import java.awt.Color;

/**
 *
 * @author Jocopa3
 */
public class ChatFormatCode {
    public static final char ESCAPE_CHAR = '\u00a7';
    public static final String ESCAPE_STRING = Character.toString(ESCAPE_CHAR);
    
    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color DARK_BLUE = new Color(0, 0, 170);
    public static final Color DARK_GREEN = new Color(0, 170, 0);
    public static final Color DARK_AQUA = new Color(0, 170, 170);
    public static final Color DARK_RED = new Color(170, 0, 0);
    public static final Color DARK_PURPLE = new Color(170, 0, 170);
    public static final Color GOLD = new Color(255, 170, 0);
    public static final Color GRAY = new Color(170, 170, 170);
    public static final Color DARK_GRAY = new Color(85, 85, 85);
    public static final Color BLUE = new Color(85, 85, 255);
    public static final Color GREEN = new Color(85, 255, 85);
    public static final Color AQUA = new Color(85, 255, 255);
    public static final Color RED = new Color(255, 85, 85);
    public static final Color LIGHT_PURPLE = new Color(255, 85, 255);
    public static final Color YELLOW = new Color(255, 255, 85);
    public static final Color WHITE = new Color(255, 255, 255);
    
    public static final char BLACK_CHAR         = '0';
    public static final char DARK_BLUE_CHAR     = '1';
    public static final char DARK_GREEN_CHAR    = '2';
    public static final char DARK_AQUA_CHAR     = '3';
    public static final char DARK_RED_CHAR      = '4';
    public static final char DARK_PURPLE_CHAR   = '5';
    public static final char GOLD_CHAR          = '6';
    public static final char GRAY_CHAR          = '7';
    public static final char DARK_GRAY_CHAR     = '8';
    public static final char BLUE_CHAR          = '9';
    public static final char GREEN_CHAR         = 'a';
    public static final char AQUA_CHAR          = 'b';
    public static final char RED_CHAR           = 'c';
    public static final char LIGHT_PURPLE_CHAR  = 'd';
    public static final char YELLOW_CHAR        = 'e';
    public static final char WHITE_CHAR         = 'f';
    
    public static final char OBFUSCATED_CHAR    = 'k';
    public static final char BOLD_CHAR          = 'l';
    public static final char STRIKETHROUGH_CHAR = 'm';
    public static final char UNDERLINE_CHAR     = 'n';
    public static final char ITALIC_CHAR        = 'o';
    public static final char RESET_CHAR         = 'r';
    
    public static final String BLACK_STRING         = ESCAPE_CHAR + "0";
    public static final String DARK_BLUE_STRING     = ESCAPE_CHAR + "1";
    public static final String DARK_GREEN_STRING    = ESCAPE_CHAR + "2";
    public static final String DARK_AQUA_STRING     = ESCAPE_CHAR + "3";
    public static final String DARK_RED_STRING      = ESCAPE_CHAR + "4";
    public static final String DARK_PURPLE_STRING   = ESCAPE_CHAR + "5";
    public static final String GOLD_STRING          = ESCAPE_CHAR + "6";
    public static final String GRAY_STRING          = ESCAPE_CHAR + "7";
    public static final String DARK_GRAY_STRING     = ESCAPE_CHAR + "8";
    public static final String BLUE_STRING          = ESCAPE_CHAR + "9";
    public static final String GREEN_STRING         = ESCAPE_CHAR + "a";
    public static final String AQUA_STRING          = ESCAPE_CHAR + "b";
    public static final String RED_STRING           = ESCAPE_CHAR + "c";
    public static final String LIGHT_PURPLE_STRING  = ESCAPE_CHAR + "d";
    public static final String YELLOW_STRING        = ESCAPE_CHAR + "e";
    public static final String WHITE_STRING         = ESCAPE_CHAR + "f";
    
    public static final String OBFUSCATED_STRING    = ESCAPE_CHAR + "k";
    public static final String BOLD_STRING          = ESCAPE_CHAR + "l";
    public static final String STRIKETHROUGH_STRING = ESCAPE_CHAR + "m";
    public static final String UNDERLINE_STRING     = ESCAPE_CHAR + "n";
    public static final String ITALIC_STRING        = ESCAPE_CHAR + "o";
    public static final String RESET_STRING         = ESCAPE_CHAR + "r";
    
    public static String formatString(String format, String s) {
        return s.replaceAll(format, ESCAPE_STRING);
    }
}
