public class InProgressState implements QuestState {
    @Override
    public void start(QuestEvent event) {
        System.out.println("Quest is already in progress!");
    }

    @Override
    public void complete(QuestEvent event) {
        System.out.println("Quest completed successfully: " + event.getQuestEventName());

        // This is the perfect place to safely trigger loot distribution exactly once!
        event.distributeLoot();

        // Transition to terminal state
        event.setState(new CompletedState());
    }

    @Override
    public void fail(QuestEvent event) {
        System.out.println("Quest failed: " + event.getQuestEventName());
        event.setState(new FailedState());
    }

    @Override
    public String getStatusString() {
        return "In Progress";
    }
}