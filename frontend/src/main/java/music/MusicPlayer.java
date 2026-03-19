package music;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

public class MusicPlayer {
    private static MediaPlayer currentPlayer = null;
    private static String currentTrack = null;

    public static void play(String resourcePath) {
        // Already playing this exact track — don't restart it
        if (resourcePath.equals(currentTrack) && currentPlayer != null
                && currentPlayer.getStatus() == MediaPlayer.Status.PLAYING)
            return;

        stop(); // stop whatever was playing before

        try {
            URL url = MusicPlayer.class.getResource(resourcePath);
            if (url == null) {
                System.err.println("MusicPlayer: track not found -> " + resourcePath);
                return;
            }

            Media media = new Media(url.toExternalForm());
            currentPlayer = new MediaPlayer(media);
            currentPlayer.setVolume(0.5);          // 50% volume — not intrusive
            currentPlayer.setCycleCount(MediaPlayer.INDEFINITE); // loop forever
            currentPlayer.setOnError(() ->
                    System.err.println("MusicPlayer error: " + currentPlayer.getError())
            );
            currentPlayer.play();
            currentTrack = resourcePath;

        } catch (Exception e) {
            System.err.println("MusicPlayer: failed to load " + resourcePath);
            e.printStackTrace();
        }
    }

    public static void stop() {
        if (currentPlayer != null) {
            currentPlayer.stop();
            currentPlayer.dispose();
            currentPlayer = null;
            currentTrack = null;
        }
    }

    public static void pause() {
        if (currentPlayer != null) currentPlayer.pause();
    }

    public static void resume() {
        if (currentPlayer != null) currentPlayer.play();
    }

    public static void setVolume(double volume) {
        if (currentPlayer != null) currentPlayer.setVolume(volume);
    }
}
