package campaign.state;

import campaign.QuestEvent;

public class NotStartedState implements QuestState {
    @Override
    public void start(QuestEvent event) {
        System.out.println("Starting the quest: " + event.getQuestEventName());
        // Transition to the next state
        event.setState(new InProgressState());
    }

    @Override
    public void complete(QuestEvent event) {
        System.out.println("Cannot complete a quest that hasn't started yet!");
    }

    @Override
    public void fail(QuestEvent event) {
        System.out.println("Cannot fail a quest that hasn't started yet!");
    }

    @Override
    public String getStatusString() {
        return "Not Started";
    }
}