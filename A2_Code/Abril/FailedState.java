public class FailedState implements QuestState {
    @Override
    public void start(QuestEvent event) {
        System.out.println("Restarting the failed quest.");
        event.setState(new InProgressState()); // Optionally allow retries
    }
    @Override
    public void complete(QuestEvent event) { System.out.println("Cannot complete a failed quest."); }
    @Override
    public void fail(QuestEvent event) { System.out.println("Quest already failed."); }
    @Override
    public String getStatusString() { return "Failed"; }
}