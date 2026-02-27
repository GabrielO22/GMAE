import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        GuildQuestController controller = new GuildQuestController(new GuildQuest());
        System.out.println("< Welcome to GuildQuest! >");
        System.out.println("< Enter your commands below to use the software >");
        System.out.println("< (Hint: type \"help\" to see a list of all commands) >");
        while (true) {
            System.out.print("> ");
            String input = keyboard.nextLine().toLowerCase().strip();
            if (input.equals("exit"))
                break;
            else
                controller.handleCommand(input);
        }

        System.out.println("< Thank you for using GuildQuest! >");
    }
}
