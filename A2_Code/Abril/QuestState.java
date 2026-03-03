public interface QuestState {
    void start(QuestEvent event);
    void complete(QuestEvent event);
    void fail(QuestEvent event);
    String getStatusString();
}