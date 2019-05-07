package caz.mp3thingey;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class WriteToSpreadSheet {
	
	ArrayList<File> toBeParsed;
	String path;
	DefaultListModel<String> priority;
	ArrayList<String> genres, albums, artists, names, years, bitrates, trackNums, filetypes, lengths;
	ArrayList<Song> songs = new ArrayList<Song>();
	ArrayList<Song> sorted = new ArrayList<Song>();
	WritableWorkbook target;
	WritableSheet sheet;
	
	ArrayList<Mp3File> mp3s = new ArrayList<Mp3File>();
	
	public ArrayList<Mp3File> getMP3s() {
		return mp3s;
	}
	
	public void attemptToMP3() throws UnsupportedTagException, InvalidDataException, IOException {
		for(File mp3scan : toBeParsed) {
			System.out.println("File being scanned: " + mp3scan.getName());
			if(mp3scan.getName().endsWith(".mp3")) {
				mp3s.add(new Mp3File(mp3scan));
			}
		}
	}
	
	public void testForAdd(ArrayList<String> list, String test) {
		boolean exists = false;
		try {
			int length = test.length();
		} catch(NullPointerException npe) {
			test = "EMPTY";
		}
		for(String value : list) {
			try {
				int length = value.length();
			} catch(NullPointerException npe) {
				value = "EMPTY";
			}
			if(value.equals(test)) {
				exists = true;
			}
		}
		if(!exists) {
			list.add(test);
			System.out.println("Added " + test);
		} else {
			System.out.println(test + " already exists in " + list);
		}
	}
	
	public void pullFromSongs() {
		System.out.println("Pulling From Songs");
		genres = new ArrayList<String>();
		artists = new ArrayList<String>();
		albums = new ArrayList<String>();
		names = new ArrayList<String>();
		years = new ArrayList<String>();
		bitrates = new ArrayList<String>();
		trackNums = new ArrayList<String>();
		filetypes = new ArrayList<String>();
		lengths = new ArrayList<String>();
		
		for(Mp3File currentFile : mp3s) {
			songs.add(new Song(currentFile));
			testForAdd(names, songs.get(songs.size()-1).name);
			testForAdd(genres, songs.get(songs.size()-1).genre);
			testForAdd(artists, songs.get(songs.size()-1).artist);
			testForAdd(albums, songs.get(songs.size()-1).album);
			testForAdd(years, songs.get(songs.size()-1).year);
			testForAdd(bitrates, songs.get(songs.size()-1).bitrate);
			testForAdd(trackNums, songs.get(songs.size()-1).trackNum);
			testForAdd(filetypes, songs.get(songs.size()-1).filetype);
			testForAdd(lengths, songs.get(songs.size()-1).length);
			
		}
		Collections.sort(genres);
		Collections.sort(artists);
		Collections.sort(albums);
		Collections.sort(names);
		Collections.sort(years);
		Collections.sort(bitrates);
		Collections.sort(trackNums);
		Collections.sort(filetypes);
		Collections.sort(lengths);
		
		
		for(int i = 0; i < songs.size(); i++) {
			String temp;
			String scanned;
			for(int a = 0; a < genres.size(); a++) {
				try {
					temp = genres.get(a);
					if(temp.equals("") || temp.equals(" ")) {
						temp = "EMPTY";
					}
				} catch(Throwable t) {
					temp = "EMPTY";
				}
				try {
					scanned = songs.get(i).genre;
					if(scanned.equals("") || scanned.equals(" ")) {
						scanned = "EMPTY";
					}
				} catch(Throwable t) {
					scanned = "EMPTY";
				}
				if(scanned.equals(temp)) {
					songs.get(i)._genre = a;
				}
			}
			for(int a = 0; a < artists.size(); a++) {
				try {
					temp = artists.get(a);
					if(temp.equals("") || temp.equals(" ")) {
						temp = "EMPTY";
					}
				} catch(Throwable t) {
					temp = "EMPTY";
				}
				try {
					scanned = songs.get(i).artist;
					if(scanned.equals("") || scanned.equals(" ")) {
						scanned = "EMPTY";
					}
				} catch(Throwable t) {
					scanned = "EMPTY";
				}
				if(scanned.equals(temp)) {
					songs.get(i)._artist = a;
				}
			}
			for(int a = 0; a < albums.size(); a++) {
				try {
					temp = albums.get(a);
					if(temp.equals("") || temp.equals(" ")) {
						temp = "EMPTY";
					}
				} catch(Throwable t) {
					temp = "EMPTY";
				}
				try {
					scanned = songs.get(i).album;
					if(scanned.equals("") || scanned.equals(" ")) {
						scanned = "EMPTY";
					}
				} catch(Throwable t) {
					scanned = "EMPTY";
				}
				if(scanned.equals(temp)) {
					songs.get(i)._album = a;
				}
			}
			for(int a = 0; a < names.size(); a++) {
				try {
					temp = names.get(a);
					if(temp.equals("") || temp.equals(" ")) {
						temp = "EMPTY";
					}
				} catch(Throwable t) {
					temp = "EMPTY";
				}
				try {
					scanned = songs.get(i).name;
					if(scanned.equals("") || scanned.equals(" ")) {
						scanned = "EMPTY";
					}
				} catch(Throwable t) {
					scanned = "EMPTY";
				}
				if(scanned.equals(temp)) {
					songs.get(i)._name = a;
				}
			}
			for(int a = 0; a < years.size(); a++) {
				try {
					temp = years.get(a);
					if(temp.equals("") || temp.equals(" ")) {
						temp = "EMPTY";
					}
				} catch(Throwable t) {
					temp = "EMPTY";
				}
				try {
					scanned = songs.get(i).year;
					if(scanned.equals("") || scanned.equals(" ")) {
						scanned = "EMPTY";
					}
				} catch(Throwable t) {
					scanned = "EMPTY";
				}
				if(scanned.equals(temp)) {
					songs.get(i)._year = a;
				}
			}
			for(int a = 0; a < bitrates.size(); a++) {
				try {
					temp = bitrates.get(a);
					if(temp.equals("") || temp.equals(" ")) {
						temp = "EMPTY";
					}
				} catch(Throwable t) {
					temp = "EMPTY";
				}
				try {
					scanned = songs.get(i).bitrate;
					if(scanned.equals("") || scanned.equals(" ")) {
						scanned = "EMPTY";
					}
				} catch(Throwable t) {
					scanned = "EMPTY";
				}
				if(scanned.equals(temp)) {
					songs.get(i)._bitrate = a;
				}
			}
			for(int a = 0; a < trackNums.size(); a++) {
				try {
					temp = trackNums.get(a);
					if(temp.equals("") || temp.equals(" ")) {
						temp = "EMPTY";
					}
				} catch(Throwable t) {
					temp = "EMPTY";
				}
				try {
					scanned = songs.get(i).trackNum;
					if(scanned.equals("") || scanned.equals(" ")) {
						scanned = "EMPTY";
					}
				} catch(Throwable t) {
					scanned = "EMPTY";
				}
				if(scanned.equals(temp)) {
					songs.get(i)._trackNum = a;
				}
			}
			for(int a = 0; a < filetypes.size(); a++) {
				try {
					temp = filetypes.get(a);
					if(temp.equals("") || temp.equals(" ")) {
						temp = "EMPTY";
					}
				} catch(Throwable t) {
					temp = "EMPTY";
				}
				try {
					scanned = songs.get(i).filetype;
					if(scanned.equals("") || scanned.equals(" ")) {
						scanned = "EMPTY";
					}
				} catch(Throwable t) {
					scanned = "EMPTY";
				}
				if(scanned.equals(temp)) {
					songs.get(i)._filetype = a;
				}
			}
			for(int a = 0; a < lengths.size(); a++) {
				try {
					temp = lengths.get(a);
					if(temp.equals("") || temp.equals(" ")) {
						temp = "EMPTY";
					}
				} catch(Throwable t) {
					temp = "EMPTY";
				}
				try {
					scanned = songs.get(i).length;
					if(scanned.equals("") || scanned.equals(" ")) {
						scanned = "EMPTY";
					}
				} catch(Throwable t) {
					scanned = "EMPTY";
				}
				if(scanned.equals(temp)) {
					songs.get(i)._length = a;
				}
			}
			
		}
	}
	
	public void toSheet() throws RowsExceededException, WriteException {
		pullFromSongs();
		System.out.println("writer.toSheet() ran with " + songs.size() + " songs and " + priority.size() + " priorities");
		for(Song song : songs) {
			String finaltag = "";
			for(int i = 0; i < priority.size(); i++) {
				if(priority.getElementAt(i).equals("Genre")) {
					finaltag = finaltag + " " + song._genre;
				}
				if(priority.getElementAt(i).equals("Artist Name")) {
					finaltag = finaltag + " " + song._artist;
				}
				if(priority.getElementAt(i).equals("Album Name")) {
					finaltag = finaltag + " " + song._album;
				}
				if(priority.getElementAt(i).equals("Song Name")) {
					finaltag = finaltag + " " + song._name;
				}
				if(priority.getElementAt(i).equals("Year Produced")) {
					finaltag = finaltag + " " + song._year;
				}
				if(priority.getElementAt(i).equals("Bitrate")) {
					finaltag = finaltag + " " + song._bitrate;
				}
				if(priority.getElementAt(i).equals("Track Number")) {
					finaltag = finaltag + " " + song._trackNum;
				}
				if(priority.getElementAt(i).equals("File Type")) {
					finaltag = finaltag + " " + song._filetype;
				}
				if(priority.getElementAt(i).equals("Song Length")) {
					finaltag = finaltag + " " + song._length;
				}
			}
			song.finaltag = finaltag;
			System.out.println("Final Tag for " + song.name + ": " + song.finaltag);
		}
	}
	
	public void initializeSystem() throws IOException {
		target = Workbook.createWorkbook(new File(path));
		sheet = target.createSheet("First Sheet", 0);
	}
	
	public void testMessage() throws RowsExceededException, WriteException {
		System.out.println("Writing Now!");
		for(int i = 0; i < priority.size(); i++) {
			sheet.addCell(new Label(i, 0, priority.getElementAt(i)));
		}
	}
	
	public void writeFinal() throws RowsExceededException, WriteException, IOException {
		ArrayList<Integer[]> songTag = new ArrayList<Integer[]>();
		for(Song song : songs) {
			String[] split = song.finaltag.split(" ");
			Integer[] tags = new Integer[split.length - 1];
			for(int i = 0; i < tags.length; i++) {
				tags[i] = Integer.parseInt(split[i + 1]);
			}
			songTag.add(tags);
		}
		Collections.sort(songTag, new Comparator<Integer[]>() {
            public int compare(Integer[] nums, Integer[] otherNums) {
                return nums[1].compareTo(otherNums[1]);
            }
        });
		for(int i = 0; i < songTag.size(); i++) {
			for(int a = 0; a < priority.size(); a++) {
				if(priority.getElementAt(a).equals("Genre")) {
					sheet.addCell(new Label(a, i + 1, genres.get(songTag.get(i)[a])));
					System.out.println("Added Genre at " + a + ", " + (i + 1));
				}
				if(priority.getElementAt(a).equals("Artist Name")) {
					sheet.addCell(new Label(a, i + 1, artists.get(songTag.get(i)[a])));
					System.out.println("Added Artist at " + a + ", " + (i + 1));
				}
				if(priority.getElementAt(a).equals("Album Name")) {
					sheet.addCell(new Label(a, i + 1, albums.get(songTag.get(i)[a])));
					System.out.println("Added Album at " + a + ", " + (i + 1));
				}
				if(priority.getElementAt(a).equals("Song Name")) {
					sheet.addCell(new Label(a, i + 1, names.get(songTag.get(i)[a])));
					System.out.println("Added Name at " + a + ", " + (i + 1));
				}
				if(priority.getElementAt(a).equals("Year Produced")) {
					sheet.addCell(new Label(a, i + 1, years.get(songTag.get(i)[a])));
					System.out.println("Added Year at " + a + ", " + (i + 1));
				}
				if(priority.getElementAt(a).equals("Bitrate")) {
					sheet.addCell(new Label(a, i + 1, bitrates.get(songTag.get(i)[a])));
					System.out.println("Added Bitrate at " + a + ", " + (i + 1));
				}
				if(priority.getElementAt(a).equals("Track Number")) {
					sheet.addCell(new Label(a, i + 1, trackNums.get(songTag.get(i)[a])));
					System.out.println("Added Number at " + a + ", " + (i + 1));
				}
				if(priority.getElementAt(a).equals("File Type")) {
					sheet.addCell(new Label(a, i + 1, filetypes.get(songTag.get(i)[a])));
					System.out.println("Added Type at " + a + ", " + (i + 1));
				}
				if(priority.getElementAt(a).equals("Song Length")) {
					sheet.addCell(new Label(a, i + 1, lengths.get(songTag.get(i)[a])));
					System.out.println("Added Length at " + a + ", " + (i + 1));
				}
			}
		}
		target.write();
		target.close();
		JOptionPane.showMessageDialog(new JFrame(), "Writing Complete!");
		System.exit(0);
	}
	
	public WriteToSpreadSheet(String path, ArrayList<File> files, DefaultListModel<String> priority) {
		this.path = path;
		toBeParsed = files;
		this.priority = priority;
		System.out.println("Root is: " + priority.getElementAt(0));
		for(int i = 1; i < priority.size(); i++) {
			System.out.println("Child of " + priority.getElementAt(i - 1) + ": " + priority.getElementAt(i));
		}
	}
	
}
