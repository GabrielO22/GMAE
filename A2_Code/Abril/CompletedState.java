public class CompletedState implements QuestState {
    @Override
    public void start(QuestEvent event) { System.out.println("Quest already completed."); }
    @Override
    public void complete(QuestEvent event) { System.out.println("Quest already completed."); }
    @Override
    public void fail(QuestEvent event) { System.out.println("Cannot fail a completed quest."); }
    @Override
    public String getStatusString() { return "Completed"; }
}