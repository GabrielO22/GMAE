package sharing;


import campaign.Campaign;
import user.User;

public class CampaignShare {
    private final Campaign campaign;
    private final User user;
    private PermissionLevel permissionLevel;

    public CampaignShare(Campaign campaign, User user, PermissionLevel permissionLevel) {
        this.campaign = campaign;
        this.user = user;
        this.permissionLevel = permissionLevel;
    }

    public User getUser() { return user; }
    public PermissionLevel getPermissionLevel() { return permissionLevel; }
    public void setPermissionLevel(PermissionLevel level) { this.permissionLevel = level; }
}