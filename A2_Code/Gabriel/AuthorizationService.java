import java.util.*;
public class AuthorizationService {
    public boolean canView(User user, Campaign campaign, QuestEvent event) {
        if (campaign == null) {
            return false;
        }

        if (event == null) {
            if (campaign.getVisibility() == Visibility.PUBLIC) {
                return true;
            }
        }

        if (user == null) {
            return false;
        }

        UUID userId = user.getID();
        UUID ownerId = campaign.getOwnerUserID();

        if (ownerId != null && ownerId.equals(userId)) {
            return true;
        }

        if (event != null) {
            PermissionLevel eventLevel = getPermission(event.getSharedWith(), userId);
            if (eventLevel != null) {
                return eventLevel == PermissionLevel.VIEW_ONLY
                        || eventLevel == PermissionLevel.COLLABORATIVE;
            }
        }

        PermissionLevel campaignLevel = getPermission(campaign.getSharedWith(), userId);

        if (campaignLevel == PermissionLevel.VIEW_ONLY || campaignLevel == PermissionLevel.COLLABORATIVE) {
            return true;
        }
        return campaign.getVisibility() == Visibility.PUBLIC;
    }

    public boolean canEdit(User user, Campaign campaign, QuestEvent event) {
        if (campaign == null) return false;
        if (user == null) return false;

        UUID userId = user.getID();
        UUID ownerId = campaign.getOwnerUserID();

        if (ownerId != null && ownerId.equals(userId))
            return true;
        if (event != null) {
            PermissionLevel eventLevel = getPermission(event.getSharedWith(), userId);
            if (eventLevel != null) {
                return eventLevel == PermissionLevel.COLLABORATIVE;
            }
        }
        PermissionLevel campaignLevel = getPermission(campaign.getSharedWith(), userId);
        return campaignLevel == PermissionLevel.COLLABORATIVE;
    }

    private PermissionLevel getPermission(Map<UUID, PermissionLevel> sharedWith, UUID userId) {
        if (sharedWith == null || userId == null)
            return null;
        return sharedWith.get(userId);
    }
}
