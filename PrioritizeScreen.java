package caz.mp3thingey;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import jxl.write.WriteException;

public class PrioritizeScreen extends JDialog implements ActionListener, ItemListener {
	
	DefaultListModel<String> sortModel = new DefaultListModel<String>();
	JList<String> sortList = new JList<String>(sortModel);
	JScrollPane container = new JScrollPane();
	JButton up, down, export;
	JCheckBox genre, artist, album, year, trackNum, bitrate, type, length, name;
	JLabel cap, min, include;
	WriteToSpreadSheet writer;
	
	public JCheckBox createCheckBox(String title, boolean selected) {
		JCheckBox result = new JCheckBox(title);
		result.setSelected(selected);
		result.addItemListener(this);
		return result;
	}
	
	public void prepareComponent(Component c, int northShift, int westShift) {
		layout.putConstraint(SpringLayout.NORTH, c, northShift, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, c, westShift, SpringLayout.NORTH, this);
		orderPane.add(c);
	}
	
	public void prepareAllComponents() {
		prepareComponent(name, 40, 400);
		prepareComponent(genre, 60, 400);
		prepareComponent(artist, 80, 400);
		prepareComponent(album, 100, 400);
		prepareComponent(year, 120, 400);
		prepareComponent(trackNum, 140, 400);
		prepareComponent(bitrate, 160, 400);
		prepareComponent(type, 180, 400);
		prepareComponent(cap, 2, 80);
		prepareComponent(min, 300, 80);
		prepareComponent(up, 20, 240);
		prepareComponent(down, 60, 240);
		prepareComponent(include, 20, 400);
		prepareComponent(length, 200, 400);
		prepareComponent(export, 150, 600);
	}
	
	SpringLayout layout;
	JPanel orderPane;
	
	public PrioritizeScreen(JFrame parent, String title) {
		super(parent, title, Dialog.ModalityType.APPLICATION_MODAL);
		setLocation(400, 400);
		setSize(700, 350);
		setResizable(false);
		
		genre = createCheckBox("Genre", true);
		artist = createCheckBox("Artist Name", true);
		album = createCheckBox("Album Name", true);
		year = createCheckBox("Year Produced", true);
		trackNum = createCheckBox("Track Number", true);
		bitrate = createCheckBox("Bit Rate", true);
		type = createCheckBox("File Type", true);
		length = createCheckBox("Song Length", false);
		name = createCheckBox("Song Name", true);
		
		sortList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		container.setViewportView(sortList);
		sortModel.addElement("Genre");
		sortModel.addElement("Artist Name");
		sortModel.addElement("Album Name");
		sortModel.addElement("Song Name");
		sortModel.addElement("Year Produced");
		sortModel.addElement("Track Number");
		sortModel.addElement("Bitrate");
		sortModel.addElement("File Type");

		cap = new JLabel("Most Important");
		min = new JLabel("Least Important");
		include = new JLabel("Include:");
		up = new JButton("Slide Up");
		down = new JButton("Slide Down");
		export = new JButton("Export");
		orderPane = new JPanel();
		layout = new SpringLayout();
		orderPane.setLayout(layout);
		
		up.addActionListener(this);
		down.addActionListener(this);
		export.addActionListener(this);
		
		container.setPreferredSize(new Dimension(200, 280));
		layout.putConstraint(SpringLayout.WEST, container, 20, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.NORTH, container, 20, SpringLayout.NORTH, this);
		
		orderPane.add(container);
		prepareAllComponents();

		getContentPane().add(orderPane);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == up) {
			if(!(sortList.getSelectedIndex() <= 0) && sortList.getSelectedIndex() != -1) {
				try {
				String temp = sortModel.getElementAt(sortList.getSelectedIndex());
				sortModel.set(sortList.getSelectedIndex(), sortModel.get(sortList.getSelectedIndex() - 1));
				sortModel.set(sortList.getSelectedIndex() - 1, temp);
				sortList.setSelectedIndex(sortList.getSelectedIndex() - 1);
				} catch(Throwable t) { t.printStackTrace(); }
			}
		}
		if(arg0.getSource() == down) {
			if(!(sortList.getSelectedIndex() >= sortModel.size() - 1) && sortList.getSelectedIndex() != -1) {
				try {
				String temp = sortModel.getElementAt(sortList.getSelectedIndex());
				sortModel.set(sortList.getSelectedIndex(), sortModel.get(sortList.getSelectedIndex() + 1));
				sortModel.set(sortList.getSelectedIndex() + 1, temp);
				sortList.setSelectedIndex(sortList.getSelectedIndex() + 1);
				} catch(Throwable t) { t.printStackTrace(); }
			}
		}
		if(arg0.getSource() == export) {
			System.out.println("I exist!");
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Save As...");
			fc.addChoosableFileFilter(new FileNameExtensionFilter(".xls (Excel Spreadsheet)", "xls")); 
			fc.setAcceptAllFileFilterUsed(false);
			if(fc.showSaveDialog(new JFrame()) == JFileChooser.APPROVE_OPTION) {
				String target = fc.getSelectedFile().getAbsolutePath();
				if(!target.endsWith(".xls")) {
					target = target + ".xls";					
				}
				System.out.println("Target: " + target);
				writer = new WriteToSpreadSheet(target, MainScreen.files, sortModel);
				try {
					writer.initializeSystem();
					writer.testMessage();
					writer.attemptToMP3();
					writer.toSheet();
					writer.writeFinal(); 
				} catch (Exception e) {	System.out.println("Could not Write Final"); e.printStackTrace(); }
			}
		}
	}
	
	int index;
	public boolean existsInList(String given) {
		index = 0;
		for(int i = 0; i < sortModel.size(); i++) {
			if(given.equals(sortModel.getElementAt(i)))
				return true;
			index++;
		}
		return false;
	}
	
	public void handleCheckBox(ItemEvent e, String searchItem, JCheckBox handled) {
		if(e.getItemSelectable() == handled) {
			if(e.getStateChange() == ItemEvent.DESELECTED) {
				if(existsInList(searchItem)) {
					sortModel.removeElementAt(index);
				}
			}
			if(e.getStateChange() == ItemEvent.SELECTED) {
				if(!existsInList(searchItem)) {
					sortModel.addElement(searchItem);
				}
			}
		}
	}
	
	public void itemStateChanged(ItemEvent e) {
		handleCheckBox(e, "Genre", genre);
		handleCheckBox(e, "Artist Name", artist);
		handleCheckBox(e, "Album Name", album);
		handleCheckBox(e, "Year Produced", year);
		handleCheckBox(e, "Bitrate", bitrate);
		handleCheckBox(e, "Track Number", trackNum);
		handleCheckBox(e, "File Type", type);
		handleCheckBox(e, "Song Length", length);
		handleCheckBox(e, "Song Name", name);
	}
	
}
