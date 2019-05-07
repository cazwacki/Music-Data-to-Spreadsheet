package caz.mp3thingey;
import java.io.IOException;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

public class MP3Thingy {

	 private static final String sourceClass = MP3Thingy.class.getSimpleName();
	 private static final String sourcePackage = MP3Thingy.class.getPackage().getName();
	 private static final String LOGGER_NAME = sourcePackage + "." + sourceClass;
	 private static Logger _logger = Logger.getLogger(LOGGER_NAME);
	
	public static void main(String[] args) throws UnsupportedTagException, InvalidDataException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(
	            UIManager.getSystemLookAndFeelClassName());
		JFrame frame = new JFrame();
		frame = new JFrame("Song List to Spreadsheet - Courtesy of Charles Zawacki");
	  	JPanel paint_panel = new MainScreen();
	  	frame.setVisible(true);
	  	frame.setSize(700, 350);
	  	frame.setResizable(false);
	  	frame.setLocation(100, 100);
	  	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  	frame.setContentPane(paint_panel);
		
	}
}
