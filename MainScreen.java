package caz.mp3thingey;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainScreen extends JPanel implements ActionListener {

	 private static final String sourceClass = MP3Thingy.class.getSimpleName();
	 private static final String sourcePackage = MP3Thingy.class.getPackage().getName();
	 private static final String LOGGER_NAME = sourcePackage + "." + sourceClass;
	 private static Logger _logger = Logger.getLogger(LOGGER_NAME);
	
	JButton addDir, addFile, remove, removeall, export;
	JLabel itemCount, info;
	SpringLayout layout = new SpringLayout();
	JFileChooser fc;
	static ArrayList<File> files = new ArrayList<File>();
	DefaultListModel<String> dirModel = new DefaultListModel<String>();
	JList<String> directories = new JList<String>(dirModel);
	
	JScrollPane myScrollPane;
	
	public MainScreen() {
		fc = new JFileChooser();
		setLayout(layout);
		createButtons();
		directories.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		myScrollPane = new JScrollPane();
		myScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		myScrollPane.setViewportView(directories);
		
		layout.putConstraint(SpringLayout.WEST, myScrollPane, 20, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, myScrollPane, -20, SpringLayout.EAST, this);
		add(myScrollPane);
		
		itemCount = new JLabel("Number of Songs Added: " + files.size());
		layout.putConstraint(SpringLayout.WEST, itemCount, 20, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, itemCount, -20, SpringLayout.VERTICAL_CENTER, this);
		add(itemCount);
		
		info = new JLabel("");
		layout.putConstraint(SpringLayout.WEST, info, 20, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, info, -5, SpringLayout.VERTICAL_CENTER, this);
		add(info);
		
		layout.putConstraint(SpringLayout.WEST, addDir, 20, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.SOUTH, addDir, -20, SpringLayout.SOUTH, this);
		add(addDir);
		
		layout.putConstraint(SpringLayout.WEST, addFile, 20, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.SOUTH, addFile, -60, SpringLayout.SOUTH, this);
		add(addFile);	
		
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, remove, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.SOUTH, remove, -60, SpringLayout.SOUTH, this);
		add(remove);
		
		layout.putConstraint(SpringLayout.EAST, export, -20, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, export, -20, SpringLayout.SOUTH, this);
		add(export);
		
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, removeall, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.SOUTH, removeall, -20, SpringLayout.SOUTH, this);
		add(removeall);
	}

	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == addDir) {
           try {
           	files.addAll(handleSelection_DIR());
   			info.setText("Successfully Added Directory(ies)!");	
           } catch(Exception e) { info.setText("");}
		}
		if (arg0.getSource() == addFile) {
			try {
				files.addAll(handleSelection_FILE());
				info.setText("Successfully Added File(s)!");
			} catch (Exception e) { info.setText(""); }
			
		}
		if (arg0.getSource() == remove) {
			int[] selections = directories.getSelectedIndices();
			ArrayList<Integer> toBeRemoved = new ArrayList<Integer>();
			for(int currentScan : selections) {
				for(File scannedObject : files) {
					if(scannedObject.getAbsolutePath().contains(directories.getModel().getElementAt(currentScan))) {
						toBeRemoved.add(files.indexOf(scannedObject));
						_logger.finer("Removing " + scannedObject.getPath());
					}
				}
			}
			for(int i = selections.length - 1; i >= 0; i--) {
				dirModel.remove(selections[i]);
			}
			for(int i = toBeRemoved.size() - 1; i >= 0; i--) {
				files.remove(files.get(toBeRemoved.get(i)));
				info.setText("Successfully Removed!");
			}	
		}
		if(arg0.getSource() == removeall) {
			int n = JOptionPane.showConfirmDialog (null, "Are you sure you want to remove all directories and files?", "Ensure Removal", JOptionPane.WARNING_MESSAGE); 
			if(n == JOptionPane.YES_OPTION) {
				for(int i = files.size() - 1; i >= 0; i--) {
					files.remove(i);
				}
				info.setText("Removed all!");
				dirModel.removeAllElements();
			}
		}
		if(arg0.getSource() == export) {
			if(files.size() < 1) {
				info.setText("Add some songs first!");
			} else {
				new PrioritizeScreen(new JFrame(), "Prioritize");
			}
		}
		updateNumOfSongs();
	}
	
	public void updateNumOfSongs() {
		itemCount.setText("Number of Songs Added: " + files.size());
	}
	
	public JButton createButton(String text, String icon) {
		JButton result = new JButton(text, new ImageIcon(((new ImageIcon(icon)).getImage()).getScaledInstance(20,
	        	20, java.awt.Image.SCALE_SMOOTH)));
		result.addActionListener(this);
		return result;
	}
	
	public void createButtons() {
		addDir = createButton("Add Directory...", "resrc/opendir.png");
		addFile = createButton("Add File...", "resrc/openfile.png");
		remove = createButton("Remove Selection", "resrc/remove.png");
		removeall = createButton("Remove All", "resrc/remove.png");
		export = createButton("Export Songs...", "resrc/export.png");
	}
	
	public static void prepareChooser(JFileChooser chooser) {
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setMultiSelectionEnabled(true);
	}
	
	public File[] selectFiles() {
		JFileChooser fileSelect = new JFileChooser();
		prepareChooser(fileSelect);
		fileSelect.setDialogTitle("Open File(s)");
		fileSelect.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileSelect.addChoosableFileFilter(new FileNameExtensionFilter(".mp3 (MP3 Files)", "mp3")); 
		fileSelect.setMultiSelectionEnabled(true);
		fileSelect.setAcceptAllFileFilterUsed(true);
		File[] uploadFile;
		if(fileSelect.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			uploadFile = fileSelect.getSelectedFiles();
			return uploadFile;
		} else {
			info.setText("Cancelled Addition");
		}
		return null;
	}
	
	public File[] selectDirs() {
		JFileChooser dirSelect = new JFileChooser();
		prepareChooser(dirSelect);
		dirSelect.setDialogTitle("Open Folder(s)");
		dirSelect.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		dirSelect.setMultiSelectionEnabled(true);
		dirSelect.setAcceptAllFileFilterUsed(false);
		File[] uploadDir;
		if (dirSelect.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			uploadDir = dirSelect.getSelectedFiles();
	        return uploadDir;
	    } else {
           info.setText("Cancelled Addition");
       }
		return null;
	}
	
	public ArrayList<File> handleSelection_FILE() throws Exception {
		ArrayList<File> toBeScanned = new ArrayList<File>();
       File[] handler = selectFiles();
       for(final File file : handler) {
       	dirModel.addElement(file.getAbsolutePath());
       }
       if(handler != null) {
	        for (final File file : handler) {
	            if(file.getAbsolutePath().endsWith(".mp3")) {
	            	System.out.println("Added " + file.getAbsolutePath() + "! (toBeScanned)");
		           	toBeScanned.add(file);
	            } else {
	            	System.out.println(file.getAbsolutePath() + " isn't a compatible file!");
	            }
	        }
       }
       System.out.println("Returning " + toBeScanned.size() + " files.");
		return toBeScanned; 
	}
	
	public ArrayList<File> handleSelection_DIR() throws NullPointerException {
		ArrayList<File> toBeScanned = new ArrayList<File>();
       File[] handler = selectDirs();
       for(final File file : handler) {
       	dirModel.addElement(file.getAbsolutePath());
       }
       if(handler != null) {
	        for (final File file : handler) {
	            if (file.isDirectory()) {
	            	toBeScanned.addAll(listFilesForFolder(file));
	            } else {
	            	if(file.getAbsolutePath().endsWith(".mp3")) {
	            		System.out.println("Added " + file.getAbsolutePath() + "! (toBeScanned)");
		            	toBeScanned.add(file);
	            	} else {
	            		System.out.println(file.getAbsolutePath() + " isn't a compatible file!");
	            	}
	            }
	        }
       }
       System.out.println("Returning " + toBeScanned.size() + " files.");
		return toBeScanned;
	}
	
	public void listFilesForFolder_recursive(ArrayList<File> output, final File folder) {
		final String sourceMethod = "listFilesForFolder_recursive";

		try {
			_logger.entering(sourceClass, sourceMethod, new Object[] { output, folder });
			for (final File file : folder.listFiles()) {
				try {
					if (file.isDirectory()) {
						listFilesForFolder_recursive(output, file);
					} else {
						if (file.getAbsolutePath().endsWith(".mp3")) {
							output.add(file);
							_logger.finest("Adding " + file.getAbsolutePath());
						} else {
							_logger.finest("Discarding " + file.getAbsolutePath());
						}
					}
				} catch (Throwable t) {
					_logger.logp(Level.SEVERE, sourceClass, sourceMethod, "Exception caught trying to scan the files in the " + folder.getAbsolutePath() + " folder.", t);
				}
			}
		} finally {
			_logger.exiting(sourceClass, sourceMethod);
		}
	}
	
	public ArrayList<File> listFilesForFolder(final File folder) {
		ArrayList<File> children = new ArrayList<File>();
		listFilesForFolder_recursive(children, folder);
	    return children;
	}

}
