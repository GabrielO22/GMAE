package sharing;

import campaign.QuestEvent;
import user.User;

public class EventShare {
    private final QuestEvent event;
    private final User user;
    private PermissionLevel permissionLevel;

    public EventShare(QuestEvent event, User user, PermissionLevel permissionLevel) {
        this.event = event;
        this.user = user;
        this.permissionLevel = permissionLevel;
    }

    public User getUser() { return user; }
}