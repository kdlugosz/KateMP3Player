package MP3Player;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

/**
 * Provide basic playing of MP3 files via the javazoom library.
 * See http://www.javazoom.net/
 *
 * @author David J. Barnes and Michael Kölling.
 * @version 2011.07.31
 */
public class MusicPlayer
{
    // The current player. It might be null.
    private AdvancedPlayer player;
    boolean paused = false;
    int pausedOnFrame = 0;
    String myFile;


    /**
     * Constructor for objects of class MusicFilePlayer
     */
    public MusicPlayer() {
        player = null;
        paused = false;
    }

    /**
     * Play a part of the given file.
     * The method returns once it has finished playing.
     * @param filename The file to be played.
     */
    public void playSample(String filename)
    {
        try {
            setupPlayer(filename);
            player.play();
        }
        catch(JavaLayerException e) {
            reportProblem(filename);
        }
        finally {
            killPlayer();
        }
    }

    /**
     * Start playing the given audio file.
     * The method returns once the playing has been started.
     * @param filename The file to be played.
     */
    public void startPlaying(final String filename) {

            try {
                myFile = filename;
                setupPlayer(filename);
                player.setPlayBackListener(new PlaybackListener() {
                    public void playbackFinished(PlaybackEvent event) {
                        pausedOnFrame = event.getFrame();
                        System.out.print("Paused");
                    }
                });
                Thread t = new Thread() {
                    public void run() {
                        try {
                            player.play();
                        } catch (JavaLayerException e) {
                            reportProblem(filename);
                        } finally {
                            killPlayer();
                        }
                    }
                };
                t.setDaemon(true);      // (my addition)
                t.start();
                paused = false;
            } catch (Exception ex) {
                reportProblem(filename);
            }
    }

    public void resumePlaying()
    {
        paused = false;
        setupPlayer(myFile);
        player.setPlayBackListener(new PlaybackListener() {
            public void playbackFinished(PlaybackEvent event) {
                pausedOnFrame = event.getFrame();
                System.out.print("Paused");
            }
        });
        Thread t = new Thread() {
            public void run() {
                try {
                    player.play();
                } catch (JavaLayerException e) {
                    reportProblem(myFile);
                } finally {
                    killPlayer();
                }
            }
        };
    }

    public void pause()
    {
        paused = true;
        killPlayer();
    }


    public void stopPlaying()
    {
        killPlayer();
        pausedOnFrame = 0;
    }

    /**
     * Set up the player ready to play the given file.
     * @param filename The name of the file to play.
     */
    private void setupPlayer(String filename)
    {
        try {
            InputStream is = getInputStream(filename);
            player = new AdvancedPlayer(is, createAudioDevice());
        }
        catch (IOException e) {
            reportProblem(filename);
            killPlayer();
        }
        catch(JavaLayerException e) {
            reportProblem(filename);
            killPlayer();
        }
    }

    /**
     * Return an InputStream for the given file.
     * @param filename The file to be opened.
     * @throws IOException If the file cannot be opened.
     * @return An input stream for the file.
     */
    private InputStream getInputStream(String filename)
            throws IOException
    {
        return new BufferedInputStream(
                new FileInputStream(filename));
    }

    /**
     * Create an audio device.
     * @throws JavaLayerException if the device cannot be created.
     * @return An audio device.
     */
    private AudioDevice createAudioDevice()
            throws JavaLayerException
    {
        return FactoryRegistry.systemRegistry().createAudioDevice();
    }

    /**
     * Terminate the player, if there is one.
     */
    private void killPlayer()
    {
        synchronized(this) {
            if(player != null) {
                player.stop();
                player = null;
            }
        }
    }

    /**
     * Report a problem playing the given file.
     * @param filename The file being played.
     */
    private void reportProblem(String filename)
    {
        System.out.println("There was a problem playing: " + filename);
    }

    public boolean isPaused(){
        return !paused;
    }
}