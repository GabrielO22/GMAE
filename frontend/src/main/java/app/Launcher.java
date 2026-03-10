package app;

public class Launcher {
    public static void main(String[] args) {
        // This tricks the JVM into bypassing the JavaFX runtime check
        Main.main(args);
    }
}