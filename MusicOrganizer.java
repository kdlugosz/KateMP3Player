package MP3Player;

import java.io.File;
import java.util.ArrayList;

/**
 * A class to hold details of audio tracks.
 * Individual tracks may be played.
 *
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public class MusicOrganizer
{
    // An ArrayList for storing music tracks.
    private ArrayList<Track> tracks;
    // A player for the music tracks.
    private MusicPlayer player;
    // A reader that can read music files and load them as tracks.
    private TrackReader reader;
    boolean paused = false;
    int lastPlayed;

    /**
     * Create a MusicOrganizer
     */
    public MusicOrganizer()
    {
        tracks = new ArrayList<Track>();
        player = new MusicPlayer();
        reader = new TrackReader();
        readLibrary();
        System.out.println("Music library loaded. " + getNumberOfTracks() + " tracks.");
        System.out.println();
    }



    /**
     * Returns a track
     * @param i index of the track to get
     * @return The track you want to obtain
     */
    public Track getTrack(int i) {
        return tracks.get(i);
    }

    /**
     * Add a track file to the collection based on String
     * @param filename The file name of the track to be added.
     */
    public void addFile(String filename)
    {
        tracks.add(new Track(filename));
    }



    /**
     * Add a track to the collection.
     * @param track The track to be added.
     */
    public void addTrack(Track track)
    {
        tracks.add(track);
    }


    /**
     * Play a track in the collection.
     * @param index The index of the track to be played.
     */
    public void playTrack(int index)
    {
        lastPlayed = index;
        if(indexValid(index)) {
            Track track = tracks.get(index);
            player.startPlaying(track.getFilename());
            System.out.println("Now playing: " + track.getArtist() + " - " + track.getTitle());
        }

    }

    /**
     * Return the number of tracks in the collection.
     * @return The number of tracks in the collection.
     */
    public int getNumberOfTracks()
    {
        return tracks.size();
    }

    /**
     * List a track from the collection.
     * @param index The index of the track to be listed.
     */
    public void listTrack(int index)
    {
        System.out.print("Track " + index + ": ");
        Track track = tracks.get(index);
        System.out.println(track.getDetails());
    }

    /**
     * Show a list of all the tracks in the collection.
     */
    public void listAllTracks()
    {
        System.out.println("Track listing: ");

        for(Track track : tracks) {
            System.out.println(track.getDetails());
        }
        System.out.println();
    }

    /**
     * List all tracks by the given artist.
     * @param artist The artist's name.
     */
    public void listByArtist(String artist)
    {
        for(Track track : tracks) {
            if(track.getArtist().contains(artist)) {
                System.out.println(track.getDetails());
            }
        }
    }

    /**
     * Remove a track from the collection.
     * @param index The index of the track to be removed.
     */
    public void removeTrack(int index)
    {
        if(indexValid(index)) {
            tracks.remove(index);
        }
    }

    /**
     * Play the first track in the collection, if there is one.
     */
    public void playFirst()
    {
        if(tracks.size() > 0) {
            player.startPlaying(tracks.get(0).getFilename());
        }
    }

    /**
     * Stop the player.
     */
    public void stopPlaying()
    {
        player.stopPlaying();
    }

    /**
     * Determine whether the given index is valid for the collection.
     * Print an error message if it is not.
     * @param index The index to be checked.
     * @return true if the index is valid, false otherwise.
     */
    private boolean indexValid(int index)
    {
        // The return value.
        // Set according to whether the index is valid or not.
        boolean valid;

        if(index < 0) {
            System.out.println("Index cannot be negative: " + index);
            valid = false;
        }
        else if(index >= tracks.size()) {
            System.out.println("Index is too large: " + index);
            valid = false;
        }
        else {
            valid = true;
        }
        return valid;
    }

    /**
     * (Original function by textbook authors)
     * Adds .mp3 files contained in a folder to the track list
     * @param folderName name of folder to read from
     */
    private void readLibraryFromFolder(String folderName)
    {
        ArrayList<Track> tempTracks = reader.readTracks(folderName, ".mp3");

        // Put all the tracks into the organizer.
        for(Track track : tempTracks) {
            addTrack(track);
        }
    }

    /**
     * (Slight variation by KD)
     * Adds .mp3 files in the local directory to the track list.
     */
    private void readLibrary()
    {
        String localDir = System.getProperty("user.dir");       // my alteration: always read local directory
        ArrayList<Track> tempTracks = reader.readTracks(localDir, ".mp3");

        // Put all the tracks into the organizer.
        for(Track track : tempTracks) {
            addTrack(track);
        }
    }

    /**
     * (Addition by KD)
     * Gets the MusicPlayer associated with this MusicOrganizer
     * @return player
     */
    public MusicPlayer getPlayer(){
        return player;
    }

    /**
     * (Addition by KD)
     * Checks if player is paused (didn't use)
     * @return true if the player is paused
     */
    public boolean isPaused(){
        return player.isPaused();
    }

    /**
     * (Addition by KD)
     * Resumes playing if paused (didn't use)
     */
    public void resume(){
        player.resumePlaying();
    }

    /**
     * Returns the array list containing the tracks.
     * (Added by Kate Dlugosz)
     * @return the arrayList containing the tracks
     */
    public ArrayList<Track> getTracks() { return tracks;}

    /**
     * Add a track file to the collection.
     * (Added by Kate Dlugosz)
     * @param file The file to be added.
     */
    public void addFile(File file) {
        Track temp = reader.decodeDetails(file);
        addTrack(temp);
    }

    /**
     * (Added by KD)
     * Returns the index of the paused song
     * @return the index of the song that is paused
     */
    public int getPaused(){
        return lastPlayed;
    }

    /**
     * (Added by KD)
     * Pauses the MusicPlayer associated with this organizer.
     */
    public void pause() {
        player.pause();
    }
}
