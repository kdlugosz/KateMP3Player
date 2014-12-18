package MP3Player;
import java.awt.Desktop;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.*;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.collections.*;
import javafx.beans.value.*;
import javafx.event.*;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public class Controller implements Initializable {

    @FXML ListView<Track> showFiles = new ListView<Track>();
    MusicOrganizer organizeMyMusic = new MusicOrganizer();
    ArrayList<Track> myTrackListing = organizeMyMusic.getTracks();
    ObservableList<Track> libListing = FXCollections.observableArrayList();
    @FXML Label nowPlaying = new Label();
    private Stage s = new Stage();



    public void initialize(URL url, ResourceBundle resourceBundle) {
        // sets up Tracks as ListView for selecting
        libListing.setAll(myTrackListing);
        showFiles.setItems(libListing);
        showFiles.setPrefWidth(500);

        showFiles.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent click) {
                // plays song when it's double-clicked in ListView
                if (click.getClickCount() == 2) {
                    ObservableList<Integer> songToPlay = showFiles.getSelectionModel().getSelectedIndices();
                    System.out.print(songToPlay);
                    int i = songToPlay.get(0);
                    Track trackPlaying = organizeMyMusic.getTrack(i);
                    nowPlaying.setText("Now Playing: " + trackPlaying.toString());
                    organizeMyMusic.stopPlaying();
                    organizeMyMusic.playTrack(i);
                }
            }
        });

        // makes sure music stops playing when the window is closed
        s.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent close) {
                        organizeMyMusic.stopPlaying();
            }
        });

    }

    /**
     * Opens file chooser when Browse button is clicked
     * @param event click on Browse button
     */
    @FXML protected void handleBrowseAction(ActionEvent event) {
        FileChooser findFiles = new FileChooser();
        findFiles.setTitle("Add MP3 files to library");
        findFiles.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(".mp3", "*.mp3"));
        Stage temp = new Stage();
        List<File> mp3 = findFiles.showOpenMultipleDialog(temp);  // tracks selected stored in list

        if (mp3 != null) {
            for (File file : mp3) {
                organizeMyMusic.addFile(file);
            }
            libListing.setAll(organizeMyMusic.getTracks());
        }

    }

    @FXML protected void handleBrowseAction(ActionEvent event) {
        FileChooser findFiles = new FileChooser();
        findFiles.setTitle("Add MP3 files to library");
        findFiles.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(".mp3", "*.mp3"));
        Stage temp = new Stage();
        List<File> mp3 = findFiles.showOpenMultipleDialog(temp);  // tracks selected stored in list

        if (mp3 != null) {
            for (File file : mp3) {
                organizeMyMusic.addFile(file);
            }
            libListing.setAll(organizeMyMusic.getTracks());
        }

    }

    /**
     * Plays selected song when Play button is pressed
     * @param event click on Play button
     */
    @FXML protected void handlePlayAction(ActionEvent event) {
        ObservableList<Integer> songToPlay = showFiles.getSelectionModel().getSelectedIndices();
        int index = songToPlay.get(0);
        System.out.print(songToPlay);
        Track trackPlaying = organizeMyMusic.getTrack(index);
        nowPlaying.setText("Now Playing: " + trackPlaying.toString());
        organizeMyMusic.stopPlaying();
        organizeMyMusic.playTrack(index);}


    /**
     * Handles pause button being pressed (Didn't actually use it)
     */
    @FXML protected void handlePauseAction(ActionEvent event) {

        organizeMyMusic.pause();
    }

    /**
     * Stops track when Stop button is pressed
     * @param event click on Stop Button
     */
    @FXML protected void handleStopAction(ActionEvent event) {

        organizeMyMusic.stopPlaying();
        nowPlaying.setText("Now Playing:");
    }

    /**
     * Stops music and closes window
     * @param event click on Exit button
     */
    @FXML protected void handleExitAction(ActionEvent event) {

        organizeMyMusic.stopPlaying();
        nowPlaying.getScene().getWindow().hide();
    }

    /**
     * Sets the value of Stage s
     * @param stage a stage
     */
    public void setStage(Stage stage) {
        s = stage;
    }

    /**
     * Gets the player associated with the controller's music organizer
     * @return MusicPlayer associated with organizeMyMusic
     */
    public MusicPlayer getPlayer() {
        return organizeMyMusic.getPlayer();
    }
}
