package com.example.karizp.reproductormusica;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by karizp on 17/03/2018.
 */

public class Player {

    private static Player instance = null;

    private int songNumber = 21;
    private String[] songs = new String[songNumber];
    private String[] songLyrics = new String[songNumber];
    private String[] artist = new String[songNumber];
    private int currentSong = 0;
    private String currentSongName = "";
    private List<Integer> resourceIdList = new ArrayList<Integer>();

    Player()
    {

    }

    public static Player getInstance()
    {
        if(instance==null)
            instance=new Player();

        return instance;
    }

    public List<Integer> getResourceIdList() {
        return resourceIdList;
    }

    public void setResourceIdList(List<Integer> resourceIdList) {
        this.resourceIdList = resourceIdList;
    }

    public String[] getArtist() {
        return artist;
    }

    public void setArtist(String[] artist) {
        this.artist = artist;
    }

    public int getSongNumber() {
        return songNumber;
    }

    public void setSongNumber(int songNumber) {
        this.songNumber = songNumber;
    }

    public String[] getSongs() {
        return songs;
    }

    public void setSongs(String[] songs) {
        this.songs = songs;
    }

    public String[] getSongLyrics() {
        return songLyrics;
    }

    public void setSongLyrics(String[] songLyrics) {
        this.songLyrics = songLyrics;
    }

    public int getCurrentSong() {
        return currentSong;
    }

    public void setCurrentSong(int currentSong) {
        this.currentSong = currentSong;
    }

    public String getCurrentSongName() {
        return currentSongName;
    }

    public void setCurrentSongName(String currentSongName) {
        this.currentSongName = currentSongName;
    }
}
