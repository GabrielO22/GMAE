// Introduce parameter object refactoring added here
package GuildQuest.core;

import java.util.Scanner;

public class Session {
    private final User user;
    private final Scanner scanner;

    public Session(User user, Scanner scanner) {
        this.user = user;
        this.scanner = scanner;
    }

    public User getUser() { return user; }
    public Scanner getScanner() { return scanner; }

}
