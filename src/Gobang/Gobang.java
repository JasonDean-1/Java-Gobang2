package Gobang;
import java.io.IOException;

import javax.swing.JFrame;

/**
 * @author jason dean( jason-dean@outlook.com)
 * @version 1.0
 */
public class Gobang
{
	/**
	 * ¿ªÊ¼º¯Êý
	 * 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException
	{
		ChessBoardFrame gobangFrame = new ChessBoardFrame();
		gobangFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
