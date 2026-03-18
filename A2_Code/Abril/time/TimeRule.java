package time;

public interface TimeRule {
    int convertWorldToLocal(int worldTime);
    String getRuleDescription();
}