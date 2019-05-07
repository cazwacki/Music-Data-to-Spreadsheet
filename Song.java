package caz.mp3thingey;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.Mp3File;

public class Song {
	String name, artist, album, year, trackNum, bitrate, filetype, length, genre, finaltag;
	int _name, _artist, _album, _year, _trackNum, _bitrate, _filetype,  _length, _genre;
	public Song(Mp3File song) {
		if(song.hasId3v1Tag()) {
			ID3v1 data = song.getId3v1Tag();
			try {
				this.genre = data.getGenreDescription();	
			} catch(Throwable t) {
				this.genre = "EMPTY";
			}
			try {
				this.name = data.getTitle();	
			} catch(Throwable t) {
				this.name = "EMPTY";
			}
			try {
				this.artist = data.getArtist();	
			} catch(Throwable t) {
				this.artist = "EMPTY";
			}
			try {
				this.album = data.getAlbum();	
			} catch(Throwable t) {
				this.album = "EMPTY";
			}
			try {
				this.year = data.getYear();	
			} catch(Throwable t) {
				this.year = "EMPTY";
			}
			try {
				this.trackNum = data.getTrack();	
			} catch(Throwable t) {
				this.trackNum = "EMPTY";
			}
			try {
				this.bitrate = song.getBitrate() + " kbps " + (song.isVbr() ? "(VBR)" : "(CBR)");	
			} catch(Throwable t) {
				this.bitrate = "EMPTY";
			}
			try {
				this.filetype = "MP3";	
			} catch(Throwable t) {
				this.filetype = "EMPTY";
			}
			try {
				this.length = String.format(song.getLengthInSeconds()/60 + ":%02d", (song.getLengthInSeconds() - (int)(song.getLengthInSeconds()/60)*60));	
			} catch(Throwable t) {
				this.length = "EMPTY";
			}
		}
	}
}
