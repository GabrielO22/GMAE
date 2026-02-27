import java.util.*;public class GuildQuestController {
    private final GuildQuest guildQuest;
    private UUID currentPlayerID;
    private UUID currentCampaignID;
    public GuildQuestController(GuildQuest guildQuest) {
        this.guildQuest = guildQuest;
        this.currentPlayerID = null;
        this.currentCampaignID = null;
    }

    public void handleCommand(String input) {
        String commandName;
        String[] argumentList = new String[0];
        if (!input.contains(" "))
            commandName = input;
        else {
            commandName = input.substring(0, input.indexOf(" "));
            argumentList = input.substring(input.indexOf(" ") + 1).split("\\|");
            for (int i = 0; i < argumentList.length; i++) {
                argumentList[i] = argumentList[i].strip().toLowerCase();
            }
            System.out.println(Arrays.toString(argumentList)); // DELETE THIS LINE !!!
        }
        switch (commandName) {
            case "help":
                helpCommand(argumentList);
                break;
            case "create-user":
                createUser(argumentList);
                break;
            case "remove-user":
                removeUser(argumentList);
                break;
            case "list-users":
                listUsers(argumentList);
                break;
            case "login":
                login(argumentList);
                break;
            case "whoami":
                whoAmI(argumentList);
                break;
            case "logout":
                logout(argumentList);
                break;
            case "create-realm":
                createRealm(argumentList);
                break;
            case "remove-realm":
                removeRealm(argumentList);
                break;
            case "list-realms":
                listRealms(argumentList);
                break;
            case "show-realm":
                showRealm(argumentList);
                break;
            case "create-campaign":
                createCampaign(argumentList);
                break;
            case "remove-campaign":
                removeCampaign(argumentList);
                break;
            case "list-campaigns":
                listCampaigns(argumentList);
                break;
            case "show-campaign":
                showCampaign(argumentList);
                break;
            case "set-campaign-visibility":
                setCampaignVisibility(argumentList);
                break;
            case "enter-campaign":
                enterCampaign(argumentList);
                break;
            case "leave-campaign":
                leaveCampaign(argumentList);
                break;
            case "whereami":
                whereAmI(argumentList);
                break;
            case "share-campaign":
                shareCampaign(argumentList);
                break;
            case "unshare-campaign":
                unshareCampaign(argumentList);
                break;
            case "add-event":
                addEvent(argumentList);
                break;
            case "remove-event":
                removeEvent(argumentList);
                break;
            case "list-events":
                listEvents(argumentList);
                break;
            case "show-event":
                showEvent(argumentList);
                break;
            case "share-event":
                shareEvent(argumentList);
                break;
            case "unshare-event":
                unshareEvent(argumentList);
                break;
            case "create-character":
                createCharacter(argumentList);
                break;
            case "delete-character":
                deleteCharacter(argumentList);
                break;
            case "add-character":
                addCharacter(argumentList);
                break;
            case "remove-character":
                removeCharacter(argumentList);
                break;
            case "list-characters":
                listCharacters(argumentList);
                break;
            case "show-character":
                showCharacter(argumentList);
                break;
            case "create-item":
                createItem(argumentList);
                break;
            case "list-items":
                listItems(argumentList);
                break;
            case "show-item":
                showItem(argumentList);
                break;
            case "add-item":
                addItem(argumentList);
                break;
            case "remove-item":
                removeItem(argumentList);
                break;
            case "view-inventory":
                viewInventory(argumentList);
                break;
            default:
                System.out.println("!-- Command not recognized. Type \"help\" to see a list of commands --!");
        }
    }

    public void helpCommand(String[] arguments) {
        if (arguments.length != 0) {
            System.out.println("!-- Requires 0 argument, " + arguments.length + " arguments provided --!");
            return;
        }

        int w1 = 60;
        int w2 = 20;

        System.out.println();
        System.out.println("---------- Global / Help ----------");
        System.out.printf("%-" + w1 + "s %-" + w2 + "s%n", "help", "Prints all commands + brief usage");
        System.out.printf("%-" + w1 + "s %-" + w2 + "s%n", "exit", "Exits program cleanly");

        System.out.println();
        System.out.println("---------- User Commands ----------");
        System.out.printf("%-" + w1 + "s %-" + w2 + "s%n", "create-user <username>", "Creates a new user");
        System.out.printf("%-" + w1 + "s %-" + w2 + "s%n", "remove-user <username>", "Removes an existing user if they have no campaigns");
        System.out.printf("%-" + w1 + "s %-" + w2 + "s%n", "list-users", "Lists all users' usernames");
        System.out.printf("%-" + w1 + "s %-" + w2 + "s%n", "login <username>", "Sets the current owner for permission/ownership checks");
        System.out.printf("%-" + w1 + "s %-" + w2 + "s%n", "whoami", "Prints the current logged-in user");
        System.out.printf("%-" + w1 + "s %-" + w2 + "s%n", "logout", "Clears current user");

        System.out.println();
        System.out.println("---------- Realm Commands ----------");
        System.out.printf("%-" + w1 + "s %-" + w2 + "s%n", "create-realm <name> | <description?> | <x?> | <y?>", "Create a new realm. Description and x/y are optional (must have either both x/y or neither");
        System.out.printf("%-" + w1 + "s %-" + w2 + "s%n", "remove-realm <name>", "Remove existing realm if no campaigns are attached to it");
        System.out.printf("%-" + w1 + "s %-" + w2 + "s%n", "list-realms", "Lists all realms' names");
        System.out.printf("%-" + w1 + "s %-" + w2 + "s%n", "show-realm <name>", "Shows full details for realm");

        System.out.println();
        System.out.println("---------- Campaign Commands ----------");
        System.out.printf("%-" + w1 + "s %-" + w2 + "s%n", "create-campaign <title> | <PRIVATE/PUBLIC?>", "Creates a Campaign owned by current user and optionally sets its visibility (private by default)");
        System.out.printf("%-" + w1 + "s %-" + w2 + "s%n", "remove-campaign <title>", "Removes an existing campaign");
        System.out.printf("%-" + w1 + "s %-" + w2 + "s%n", "list-campaigns", "Lists campaigns visible to the current user");
        System.out.printf("%-" + w1 + "s %-" + w2 + "s%n", "show-campaign <title>", "Shows campaign details (realm, owner, visibility, etc.)");
        System.out.printf("%-" + w1 + "s %-" + w2 + "s%n", "set-campaign-visibility <title> | <PUBLIC/PRIVATE>", "Updates Visibility on the campaign.");
        System.out.printf("%-" + w1 + "s %-" + w2 + "s%n", "enter-campaign <title>", "Enter a campaign to run event/character/inventory commands");
        System.out.printf("%-" + w1 + "s %-" + w2 + "s%n", "leave-campaign", "Exit the currently selected campaign");
        System.out.printf("%-" + w1 + "s %-" + w2 + "s%n", "whereami", "Prints the current logged-in user and selected campaign");

        System.out.println();
        System.out.println("---------- Campaign Sharing/Permissions ----------");
        System.out.printf("%-" + w1 + "s %-" + w2 + "s%n", "share-campaign <title> | <username> | <VIEW/COLLAB>", "Adds/updates sharing for a user on a campaign");
        System.out.printf("%-" + w1 + "s %-" + w2 + "s%n", "unshare-campaign <title> | <username>", "Removed sharing status of a user the campaign is shared with");

        System.out.println();
        System.out.println("---------- Quest Event Commands ----------");
        System.out.printf("%-" + w1 + "s %-" + w2 + "s%n", "add-event <title> | <realm>", "Creates a QuestEvent in the entered campaign, with a realm attatched");
        System.out.printf("%-" + w1 + "s %-" + w2 + "s%n", "remove-event <title>", "Deletes a QuestEvent");
        System.out.printf("%-" + w1 + "s %-" + w2 + "s%n", "list-events", "Lists all events in the entered campaign");
        System.out.printf("%-" + w1 + "s %-" + w2 + "s%n", "show-event <title>", "Shows full event details");

        System.out.println();
        System.out.println("---------- Quest Event Sharing ----------");
        System.out.printf("%-" + w1 + "s %-" + w2 + "s%n", "share-event <title> | <username> | <VIEW/COLLAB>", "Shares a specific event");
        System.out.printf("%-" + w1 + "s %-" + w2 + "s%n", "unshare-event <title> | <username>", "Removed sharing status of a user the event is shared with");

        System.out.println();
        System.out.println("---------- General Character Commands ----------");
        System.out.printf("%-" + w1 + "s %-" + w2 + "s%n", "create-character <name> | <classType>", "Creates a character in an event, with the specified class type");
        System.out.printf("%-" + w1 + "s %-" + w2 + "s%n", "delete-character <name>", "Deletes a character");
        System.out.printf("%-" + w1 + "s %-" + w2 + "s%n", "add-character <name> | <eventName>", "Adds character to an event");
        System.out.printf("%-" + w1 + "s %-" + w2 + "s%n", "remove-character <name> | <eventName>", "Removes character from an event");
        System.out.printf("%-" + w1 + "s %-" + w2 + "s%n", "list-characters <eventName?>", "Lists all characters in the event, or just print all characters for the current user");
        System.out.printf("%-" + w1 + "s %-" + w2 + "s%n", "show-character <name>", "Shows character details");

        System.out.println();
        System.out.println("---------- Inventory/Item Commands ----------");
        System.out.printf("%-" + w1 + "s %-" + w2 + "s%n", "create-item <name> | <rarity> | <description?>", "Creates a new Item type for global usage, with an optional description");
        System.out.printf("%-" + w1 + "s %-" + w2 + "s%n", "list-items", "Lists all items that exist");
        System.out.printf("%-" + w1 + "s %-" + w2 + "s%n", "show-item <name>", "Shows details for given item");
        System.out.printf("%-" + w1 + "s %-" + w2 + "s%n", "add-item <characterName> | <itemName> | <quantity>", "Adds quantity of item to given character in the entered campaign");
        System.out.printf("%-" + w1 + "s %-" + w2 + "s%n", "remove-item <characterName> | <itemName> | <quantity>", "Removes quantity of item from given character in the entered campaign");
        System.out.printf("%-" + w1 + "s %-" + w2 + "s%n", "view-inventory <characterName>", "Lists the inventory of given character in the entered campaign");
        System.out.println();
    }


    private void createUser(String[] arguments) {
        if (arguments.length != 1) {
            System.out.println("!-- Requires 1 argument, " + arguments.length + " given --!");
            return;
        }
        try {
            guildQuest.createUser(arguments[0]);
        } catch (IllegalArgumentException e) {
            System.out.println("!-- Username must be unique! --!");
            return;
        }
        System.out.println("< User " + arguments[0] + " has been successfully created >");
    }

    private void removeUser(String[] arguments) {
        if (arguments.length != 1) {
            System.out.println("!-- Requires 1 argument, " + arguments.length + " given --!");
            return;
        }
        try {
            UUID id = guildQuest.getUsersByUsername().get(arguments[0]).getID();
            if (guildQuest.removeUser(arguments[0])) {
                System.out.println("< User " + (arguments[0]) + " has been successfully removed >");
                if (currentPlayerID == id) {
                    System.out.println("< Logged out of " + arguments[0] + " due to deletion >");
                    logout(new String[0]);
                }
            }
            else
                System.out.println("~?~ User " + (arguments[0]) + " does not exist ~?~");
        } catch (IllegalStateException e) {
            System.out.println("!-- User cannot have campaigns attached to them --!");
        }
    }

    private void listUsers(String[] arguments) {
        if (arguments.length != 0) {
            System.out.println("!-- Requires 0 arguments, " + arguments.length + " arguments provided --!");
            return;
        }
        if (guildQuest.getUsersByUsername().isEmpty()) {
            System.out.println("~?~ No users created ~?~");
        }

        for (Map.Entry<String, User> entry : guildQuest.getUsersByUsername().entrySet()) {
            System.out.println("--> " + entry.getKey());
        }
    }

    private void login(String[] arguments) {
        if (arguments.length != 1) {
            System.out.println("!-- Requires 1 argument, " + arguments.length + " given --!");
            return;
        }
        User user = guildQuest.getUsersByUsername().get(arguments[0]);
        if (user == null) {
            System.out.println("!-- User " + arguments[0] + " does not exist --!");
            return;
        }
        currentPlayerID = user.getID();
        System.out.println("< Successfully logged into " + arguments[0] + " >");
    }

    private void whoAmI(String[] arguments) {
        if (arguments.length != 0) {
            System.out.println("!-- Requires 0 arguments, " + arguments.length + " arguments provided --!");
            return;
        }
        if (currentPlayerID == null) {
            System.out.println("!-- You are not logged in --!");
            return;
        }
        System.out.println("< Currently logged in as " + guildQuest.getUsersByID().get(currentPlayerID).getName() + " >");
    }
    private void logout(String[] arguments) {
        if (arguments.length != 0) {
            System.out.println("!-- Requires 0 arguments, " + arguments.length + " arguments provided --!");
            return;
        }
        if (currentPlayerID == null) {
            System.out.println("~?~ Not currently logged in ~?~");
            return;
        }
        System.out.println("< Successfully logged out >");
        currentPlayerID = null;
        currentCampaignID = null;
    }
    private void createRealm(String[] arguments) {
        for (int i = 0; i < arguments.length; i++) {
            arguments[i] = arguments[i].toLowerCase().strip();
        }
        try {
            if (arguments.length == 1) {
                guildQuest.createRealm(arguments[0], null, null, null);
                System.out.println("< Successfully created realm " + arguments[0] + ">");
            } else if (arguments.length == 2) {
                guildQuest.createRealm(arguments[0], arguments[1], null, null);
                System.out.println("< Successfully created realm " + arguments[0] + ">");
            } else if (arguments.length == 3) {
                guildQuest.createRealm(arguments[0], null, Integer.parseInt(arguments[1].strip()), Integer.parseInt(arguments[2].strip()));
                System.out.println("< Successfully created realm " + arguments[0] + " at (" + arguments[1] + ", " + arguments[2] + ") >");
            } else if (arguments.length == 4) {
                guildQuest.createRealm(arguments[0], arguments[1], Integer.parseInt(arguments[2].strip()), Integer.parseInt(arguments[3].strip()));
                System.out.println("< Successfully created realm " + arguments[0] + " at (" + arguments[2] + ", " + arguments[3] + ") >");
            } else {
                System.out.println("!-- Requires 1, 2, 3, or 4 arguments, " + arguments.length + " arguments provided");
            }
        } catch (IllegalStateException e) {
            System.out.println("!-- Realm with this name already exists --!");
        } catch (NumberFormatException e) {
            System.out.println("!-- Coordinates must be integer value numbers --!");
        } catch (IllegalArgumentException e) {
            System.out.println("!-- Invalid arguments provided --!");
        }
    }
    private void removeRealm(String[] arguments) {
        if (arguments.length != 1) {
            System.out.println("!-- Requires 1 argument, " + arguments.length + " arguments provided --!");
            return;
        }
        String realmTitle = arguments[0];
        UUID realmID = guildQuest.getRealmsByTitle().get(realmTitle).getID();
        if (realmID == null) {
            System.out.println("!-- Realm not found with that name --!");
            return;
        }
        guildQuest.deleteRealm(realmID);
        System.out.println("< Realm successfully deleted >");
    }
    private void listRealms(String[] arguments) {
        if (arguments.length != 0) {
            System.out.println("!-- Requires 0 arguments, " + arguments.length + " arguments provided --!");
            return;
        }
        if (guildQuest.getRealmsByTitle().isEmpty()) {
            System.out.println("~?~ No realms created ~?~");
        }
        for (Map.Entry<String, Realm> entry : guildQuest.getRealmsByTitle().entrySet()) {
            System.out.println("--> " + entry.getKey());
        }
    }
    public void showRealm(String[] arguments) {
        if (arguments.length != 1) {
            System.out.println("!-- Requires 1 argument, " + arguments.length + " arguments provided --!");
            return;
        }
        Realm realm = guildQuest.getRealmsByTitle().get(arguments[0].strip());
        if (realm == null) {
            System.out.println("ERROR: Realm not found.");
            return;
        }

        System.out.println("< ----- Realm Information ----- >");
        System.out.println("\tName: " + realm.getTitle());

        if (realm.getDescription() != null)
            System.out.println("\tDescription: " + realm.getDescription());

        if (realm.getXCoordinate() != null && realm.getYCoordinate() != null)
            System.out.println("\tCoordinates: (" + realm.getXCoordinate() + ", " + realm.getYCoordinate() + ")");

        System.out.println("< ----------------------------- >");
    }
    private void createCampaign(String[] arguments) {
        User currentUser = guildQuest.getUsersByID().get(currentPlayerID);
        if (currentUser == null) {
            System.out.println("!-- Must be logged in to create a campaign --!");
            return;
        }
        try {
            if (arguments.length == 1) {
                guildQuest.createCampaign(currentUser, arguments[0], "private");
                System.out.println("< Campaign " + arguments[0] + " successfully created >");
            } else if (arguments.length == 2) {
                guildQuest.createCampaign(currentUser, arguments[0], arguments[1]);
                System.out.println("< Campaign " + arguments[0] + " successfully created >");
            } else {
                System.out.println("!-- Requires 1 or 2 arguments, " + arguments.length + " arguments provided --!");
            }
        } catch (IllegalStateException e) {
            System.out.println("!-- Visibility must be PUBLIC or PRIVATE --!");
        } catch (IllegalArgumentException e) {
            System.out.println("!-- Invalid parameters --!");
        } catch (IllegalAccessError e) {
            System.out.println("!-- Campaign name is not unique --!");
        }
    }
    private void removeCampaign(String[] arguments) {
        User currentUser = guildQuest.getUsersByID().get(currentPlayerID);
        try {
            if (arguments.length != 1) {
                System.out.println("!-- Requires 1 argument, " + arguments.length + " arguments provided --!");
                return;
            }
            UUID campaignID = guildQuest.getCampaignsByTitle().get(arguments[0]).getID();
            if (campaignID == null || currentUser.getOwnedCampaignIDs().contains(campaignID)) {
                System.out.println("!-- Campaign with that title could not be found for this user");
                return;
            }
            guildQuest.deleteCampaign(campaignID);
            currentUser.removeCampaignByID(campaignID);
        } catch (IllegalArgumentException e) {
            System.out.println("!-- Campaign could not be deleted --!");
        }
    }
    private void listCampaigns(String[] arguments) {
        if (arguments.length != 0) {
            System.out.println("!-- Requires 0 arguments, " + arguments.length + " arguments provided --!");
            return;
        }
        AuthorizationService authorizationService = guildQuest.getAuthorizationService();
        User currentUser = guildQuest.getUsersByID().get(currentPlayerID);
        int count = 0;
        for (Map.Entry<String, Campaign> entry : guildQuest.getCampaignsByTitle().entrySet()) {
            if (authorizationService.canView(currentUser, entry.getValue(), null)) {
                System.out.println("--> " + entry.getKey());
                count ++;
            }
        }
        if (count == 0) {
            System.out.println("~?~ No campaigns available for this user ~?~");
        }
    }
    private void showCampaign(String[] arguments) {
        if (arguments.length != 1) {
            System.out.println("!-- Requires 1 argument, " + arguments.length + " arguments provided --!");
            return;
        }
        AuthorizationService authorizationService = guildQuest.getAuthorizationService();
        User currentUser = guildQuest.getUsersByID().get(currentPlayerID);
        Campaign currentCampaign = guildQuest.getCampaignsByTitle().get(arguments[0]);
        if (currentCampaign == null) {
            System.out.println("!-- Campaign not found --!");
            return;
        }
        if (!authorizationService.canView(currentUser, currentCampaign, null)) {
            System.out.println("!-- You cannot access this Campaign --!");
            return;
        }

        System.out.println("< ----- Campaign Information ----- >");
        System.out.println("\tName: " + currentCampaign.getName());
        System.out.println("\tOwned by: " + guildQuest.getUsersByID().get(currentCampaign.getOwnerUserID()).getName());
        System.out.println("\tVisibility: " + currentCampaign.getVisibility());

        List<QuestEvent> questEvents = currentCampaign.getEvents();
        if (!questEvents.isEmpty()) {
            System.out.println("\t< ----- Campaign Events ----- >");
            for (QuestEvent event : questEvents) {
                System.out.println("\t\t" + event.getName());
            }
            System.out.println("\t< --------------------------- >");
        }

        Map<UUID, PermissionLevel> permissionLevels = currentCampaign.getSharedWith();
        if (!permissionLevels.isEmpty()) {
            System.out.println("\t< ----- User Permissions ----- >");
            for (Map.Entry<UUID, PermissionLevel> entry : permissionLevels.entrySet()) {
                User user = guildQuest.getUsersByID().get(entry.getKey());
                System.out.println("\t\tUser: " + user.getName() + " | Permission Level: " + entry.getValue());
            }
            System.out.println("\t< ---------------------------- >");
        }
        System.out.println("< -------------------------------- >");
    }
    private void setCampaignVisibility(String[] arguments) {
        if (arguments.length != 2) {
            System.out.println("!-- Requires 2 arguments, " + arguments.length + " arguments provided --!");
            return;
        }
        AuthorizationService authorizationService = guildQuest.getAuthorizationService();
        User currentUser = guildQuest.getUsersByID().get(currentPlayerID);
        Campaign currentCampaign = guildQuest.getCampaignsByTitle().get(arguments[0]);
        if (currentCampaign == null) {
            System.out.println("!-- Campaign not found --!");
            return;
        }
        if (!authorizationService.canEdit(currentUser, currentCampaign, null)) {
            System.out.println("!-- You cannot edit this Campaign --!");
            return;
        }
        if (arguments[1].equalsIgnoreCase("public")) {
            currentCampaign.setVisibility(Visibility.PUBLIC);
            System.out.println("< Visibility set to public >");
        } else if (arguments[1].equalsIgnoreCase("private")) {
            currentCampaign.setVisibility(Visibility.PRIVATE);
            System.out.println("< Visibility set to private >");
        } else {
            System.out.println("!-- Visibility must be either PUBLIC or PRIVATE --!");
        }
    }
    private void enterCampaign(String[] arguments) {
        if (arguments.length != 1) {
            System.out.println("!-- Requires 1 argument, " + arguments.length + " arguments provided --!");
            return;
        }
        User user = guildQuest.getUsersByID().get(currentPlayerID);
        AuthorizationService authorizationService = guildQuest.getAuthorizationService();
        Campaign campaign = guildQuest.getCampaignsByTitle().get(arguments[0]);
        if (user == null) {
            System.out.println("!-- Must be logged in to enter a campaign --!");
            return;
        }
        if (campaign == null) {
            System.out.println("!-- Cannot find a campaign of that name --!");
            return;
        }
        if (!authorizationService.canView(user, campaign, null)) {
            System.out.println("!-- Cannot access this campaign --!");
            return;
        }
        currentCampaignID = campaign.getID();
        System.out.println("< Successfully entered into " + campaign.getName() + " >");
    }
    private void leaveCampaign(String[] arguments) {
        if (arguments.length != 0) {
            System.out.println("!-- Requires 0 arguments, " + arguments.length + " arguments provided --!");
            return;
        }
        if (currentCampaignID == null) {
            System.out.println("~?~ Not currently in a campaign ~?~");
            return;
        }
        System.out.println("< Successfully exited campaign >");
        currentCampaignID = null;
    }
    private void whereAmI(String[] arguments) {
        if (arguments.length != 0) {
            System.out.println("!-- Requires 0 arguments, " + arguments.length + " arguments provided --!");
            return;
        }
        if (currentCampaignID == null) {
            System.out.println("!-- You are not currently in a campaign --!");
            return;
        }
        System.out.println("< Currently in " + guildQuest.getCampaignsByID().get(currentCampaignID).getName() + " >");
    }
    private void shareCampaign(String[] arguments) {
        if (arguments.length != 3) {
            System.out.println("!-- Requires 3 arguments, " + arguments.length + " arguments provided --!");
            return;
        }
        User user = guildQuest.getUsersByID().get(currentPlayerID);
        AuthorizationService authorizationService = guildQuest.getAuthorizationService();
        Campaign campaign = guildQuest.getCampaignsByTitle().get(arguments[0]);
        User recipientUser = guildQuest.getUsersByUsername().get(arguments[1]);
        PermissionLevel permissionLevel;

        if (user == null) {
            System.out.println("!-- Must be logged in to share a campaign --!");
            return;
        }
        if (campaign == null) {
            System.out.println("!-- Cannot find a campaign with this name --!");
            return;
        }
        if (!authorizationService.canEdit(user, campaign, null)) {
            System.out.println("!-- You cannot edit this campaign --!");
            return;
        }
        if (recipientUser == null) {
            System.out.println("!-- Could not find user with that username --!");
            return;
        }
        if (arguments[2].equalsIgnoreCase("view")) {
            permissionLevel = PermissionLevel.VIEW_ONLY;
        } else if (arguments[2].equalsIgnoreCase("collab")) {
            permissionLevel = PermissionLevel.COLLABORATIVE;
        } else {
            System.out.println("!-- Permission level must be VIEW or COLLAB --!");
            return;
        }
        campaign.grantAccess(recipientUser.getID(), permissionLevel);
        System.out.println("< " + recipientUser.getName() + " granted " + permissionLevel.toString().toLowerCase() + " access >");
    }
    private void unshareCampaign(String[] arguments) {
        if (arguments.length != 2) {
            System.out.println("!-- Requires 2 arguments, " + arguments.length + " arguments provided --!");
            return;
        }
        User user = guildQuest.getUsersByID().get(currentPlayerID);
        AuthorizationService authorizationService = guildQuest.getAuthorizationService();
        Campaign campaign = guildQuest.getCampaignsByTitle().get(arguments[0]);
        User recipientUser = guildQuest.getUsersByUsername().get(arguments[1]);

        if (user == null) {
            System.out.println("!-- Must be logged in to unshare a campaign --!");
            return;
        }
        if (campaign == null) {
            System.out.println("!-- Cannot find a campaign with this name --!");
            return;
        }
        if (!authorizationService.canEdit(user, campaign, null)) {
            System.out.println("!-- You cannot edit this campaign --!");
            return;
        }
        campaign.revokeAccess(recipientUser.getID());
        System.out.println("< Revoked " + recipientUser.getName() + "'s access >");
    }
    private void addEvent(String[] arguments) {
        if (arguments.length != 2) {
            System.out.println("!-- Requires 2 arguments, " + arguments.length + " arguments provided --!");
            return;
        }
        if (currentCampaignID == null) {
            System.out.println("!-- Must enter a campaign to create a quest event --!");
            return;
        }
        Campaign currentCampaign = guildQuest.getCampaignsByID().get(currentCampaignID);
        Realm realm = guildQuest.getRealmsByTitle().get(arguments[1]);
        AuthorizationService authorizationService = guildQuest.getAuthorizationService();
        User user = guildQuest.getUsersByID().get(currentPlayerID);
        if (!authorizationService.canEdit(user, currentCampaign, null)) {
            System.out.println("!-- You do not have permission to edit this campaign --!");
            return;
        }
        if (realm == null) {
            System.out.println("!-- Realm with that name could not be found --!");
            return;
        }
        QuestEvent event = new QuestEvent(arguments[0], realm);
        currentCampaign.addEvent(event);
        System.out.println("< Quest event " + event.getName() + " successfully created in " + currentCampaign.getName() + " >");
    }
    private void removeEvent(String[] arguments) {
        if (arguments.length != 1) {
            System.out.println("!-- Requires 1 argument, " + arguments.length + " arguments provided --!");
            return;
        }
        if (currentCampaignID == null) {
            System.out.println("!-- Must enter a campaign to remove a quest --!");
            return;
        }
        Campaign campaign = guildQuest.getCampaignsByID().get(currentCampaignID);
        QuestEvent event = campaign.getEventByName(arguments[0]);
        AuthorizationService authorizationService = guildQuest.getAuthorizationService();
        User user = guildQuest.getUsersByID().get(currentPlayerID);
        if (!authorizationService.canEdit(user, campaign, null)) {
            System.out.println("!-- You do not have permission to edit this campaign --!");
            return;
        }

        if (event == null) {
            System.out.println("!-- Event with that name could not be found --!");
            return;
        }
        campaign.removeEvent(event);
        System.out.println("< Event " + event.getName() + " successfully removed >");
    }
    private void listEvents(String[] arguments) {
        if (arguments.length != 0) {
            System.out.println("!-- Requires 0 arguments, " + arguments.length + " arguments provided --!");
            return;
        }
        if (currentCampaignID == null) {
            System.out.println("!-- Must enter a campaign to view it's events --!");
            return;
        }
        User user = guildQuest.getUsersByID().get(currentPlayerID);
        Campaign campaign = guildQuest.getCampaignsByID().get(currentCampaignID);
        AuthorizationService authorizationService = guildQuest.getAuthorizationService();
        int count = 0;
        for (QuestEvent event : campaign.getEvents()) {
            if (authorizationService.canView(user, campaign, event)) {
                System.out.println("--> " + event.getName());
                count ++;
            }
        }
        if (count == 0) {
            System.out.println("~?~ No events found in this campaign ~?~");
        }
    }
    private void showEvent(String[] arguments) {
        if (arguments.length != 1) {
            System.out.println("!-- Requires 1 argument, " + arguments.length + " arguments provided --!");
            return;
        }
        AuthorizationService authorizationService = guildQuest.getAuthorizationService();
        User currentUser = guildQuest.getUsersByID().get(currentPlayerID);
        Campaign currentCampaign = guildQuest.getCampaignsByID().get(currentCampaignID);
        if (currentCampaign == null) {
            System.out.println("!-- Must be in a campaign to get event details --!");
            return;
        }
        QuestEvent event = currentCampaign.getEventByName(arguments[0]);
        if (event == null) {
            System.out.println("!-- Cannot find an event with that name --!");
            return;
        }
        if (!authorizationService.canView(currentUser, currentCampaign, event)) {
            System.out.println("!-- You do not have access to this event --!");
            return;
        }
        System.out.println("< ----- Quest Event Information ----- >");
        System.out.println("\tName: " + event.getName());
        System.out.println("\tRealm: " + event.getRealm().getTitle());
        System.out.println("\tCampaign: " + currentCampaign.getName());

        if (!event.getParticipantIDs().isEmpty()) {
            System.out.println("\t< ----- Participating Characters ----- >");
            for (Character c : currentUser.getCharacters()) {
                UUID id = c.getID();
                if (event.getParticipantIDs().contains(id)) {
                    System.out.println("\t\t" + c.getName());
                }
            }
            System.out.println("\t< ------------------------------------ >");
        }

        Map<UUID, PermissionLevel> permissionLevels = event.getSharedWith();
        if (!permissionLevels.isEmpty()) {
            System.out.println("\t< ----- User Permissions ----- >");
            for (Map.Entry<UUID, PermissionLevel> entry : permissionLevels.entrySet()) {
                User user = guildQuest.getUsersByID().get(entry.getKey());
                System.out.println("\t\tUser: " + user.getName() + " | Permission Level: " + entry.getValue());
            }
            System.out.println("\t< ---------------------------- >");
        }
        System.out.println("< ----------------------------------- >");
    }
    private void shareEvent(String[] arguments) {
        if (arguments.length != 3) {
            System.out.println("!-- Requires 3 arguments, " + arguments.length + " arguments provided --!");
            return;
        }
        if (currentCampaignID == null) {
            System.out.println("!-- Must enter a campaign before sharing an event --!");
            return;
        }
        User user = guildQuest.getUsersByID().get(currentPlayerID);
        AuthorizationService authorizationService = guildQuest.getAuthorizationService();
        Campaign campaign = guildQuest.getCampaignsByID().get(currentCampaignID);
        QuestEvent event = campaign.getEventByName(arguments[0]);
        User recipientUser = guildQuest.getUsersByUsername().get(arguments[1]);
        PermissionLevel permissionLevel;
        if (event == null) {
            System.out.println("!-- Event with that name not found --!");
            return;
        }
        if (!authorizationService.canEdit(user, campaign, event)) {
            System.out.println("!-- You cannot edit this event --!");
            return;
        }
        if (recipientUser == null) {
            System.out.println("!-- User " + arguments[1] + " not found --!");
            return;
        }
        if (arguments[2].equalsIgnoreCase("view")) {
            permissionLevel = PermissionLevel.VIEW_ONLY;
        } else if (arguments[2].equalsIgnoreCase("collab")) {
            permissionLevel = PermissionLevel.COLLABORATIVE;
        } else {
            System.out.println("!-- Permission level must be VIEW or COLLAB --!");
            return;
        }
        event.grantAccess(recipientUser.getID(), permissionLevel);
        System.out.println("< " + recipientUser.getName() + " granted " + permissionLevel.toString().toLowerCase() + " access >");
    }
    private void unshareEvent(String[] arguments) {
        if (arguments.length != 2) {
            System.out.println("!-- Requires 2 arguments, " + arguments.length + " arguments provided --!");
            return;
        }
        if (currentCampaignID == null) {
            System.out.println("!-- Must enter a campaign to unshare an event --!");
            return;
        }
        User user = guildQuest.getUsersByID().get(currentPlayerID);
        AuthorizationService authorizationService = guildQuest.getAuthorizationService();
        Campaign campaign = guildQuest.getCampaignsByID().get(currentCampaignID);
        User recipientUser = guildQuest.getUsersByUsername().get(arguments[1]);
        QuestEvent event = campaign.getEventByName(arguments[0]);
        if (event == null) {
            System.out.println("!-- Event with that name not found --!");
            return;
        }
        if (!authorizationService.canEdit(user, campaign, event)) {
            System.out.println("!-- You cannot edit this campaign --!");
            return;
        }
        event.revokeAccess(recipientUser.getID());
        System.out.println("< Revoked " + recipientUser.getName() + "'s access >");
    }
    private void createCharacter(String[] arguments) {
        if (arguments.length != 2) {
            System.out.println("!-- Requires 2 arguments, " + arguments.length + " arguments provided --!");
            return;
        }
        if (currentPlayerID == null) {
            System.out.println("!-- Must be logged in to create a character --!");
            return;
        }
        User user = guildQuest.getUsersByID().get(currentPlayerID);
        Character character = new Character(arguments[0], arguments[1]);
        user.addCharacter(character);
        System.out.println("Successfully created " + character.getName() + " >");
    }
    private void deleteCharacter(String[] arguments) {
        if (arguments.length != 1) {
            System.out.println("!-- Requires 1 argument, " + arguments.length + " arguments provided --!");
            return;
        }
        if (currentPlayerID == null) {
            System.out.println("!-- Must be logged in to delete a character --!");
            return;
        }
        User user = guildQuest.getUsersByID().get(currentPlayerID);
        Character character = user.getCharactersByName(arguments[0]);
        if (character == null) {
            System.out.println("!-- Could not find character with this name --!");
            return;
        }
        user.deleteCharacter(character);
        System.out.println("< Successfully deleted character " + character.getName() + " >");
    }
    private void addCharacter(String[] arguments) {
        if (arguments.length != 2) {
            System.out.println("!-- Requires 2 arguments, " + arguments.length + " arguments provided >");
            return;
        }
        if (currentCampaignID == null) {
            System.out.println("!-- Must enter a campaign to add a character to an event --!");
            return;
        }
        User user = guildQuest.getUsersByID().get(currentPlayerID);
        AuthorizationService authorizationService = guildQuest.getAuthorizationService();
        Campaign campaign = guildQuest.getCampaignsByID().get(currentCampaignID);
        QuestEvent event = campaign.getEventByName(arguments[1]);
        Character character = user.getCharactersByName(arguments[0]);
        if (character == null) {
            System.out.println("!-- Could not find character with that name --!");
            return;
        }
        if (event == null) {
            System.out.println("!-- Could not find event with that name --!");
            return;
        }
        if (!authorizationService.canEdit(user, campaign, event)) {
            System.out.println("!-- You do not have access to edit this event --!");
            return;
        }
        if (event.getParticipantIDs().contains(character.getID())) {
            System.out.println("~?~ Character already joined this event ~?~");
            return;
        }
        event.addParticipant(character.getID());
        System.out.println("< Character added to event >");
    }
    private void removeCharacter(String[] arguments) {
        if (arguments.length != 2) {
            System.out.println("!-- Requires 2 arguments, " + arguments.length + " arguments provided >");
            return;
        }
        if (currentCampaignID == null) {
            System.out.println("!-- Must enter a campaign to remove a character from an event --!");
            return;
        }
        User user = guildQuest.getUsersByID().get(currentPlayerID);
        AuthorizationService authorizationService = guildQuest.getAuthorizationService();
        Campaign campaign = guildQuest.getCampaignsByID().get(currentCampaignID);
        QuestEvent event = campaign.getEventByName(arguments[1]);
        Character character = user.getCharactersByName(arguments[0]);
        if (character == null) {
            System.out.println("!-- Could not find character with that name --!");
            return;
        }
        if (event == null) {
            System.out.println("!-- Could not find event with that name --!");
            return;
        }
        if (!authorizationService.canEdit(user, campaign, event)) {
            System.out.println("!-- You do not have access to edit this event --!");
            return;
        }
        event.removeParticipant(user.getID());
        System.out.println("< Character removed from event >");
    }
    private void listCharacters(String[] arguments) {
        if (currentPlayerID == null) {
            System.out.println("!-- Must be logged in to list characters --!");
            return;
        }
        User user = guildQuest.getUsersByID().get(currentPlayerID);

        if (arguments.length == 0) {
            for (Character c : user.getCharacters()) {
                System.out.println("-->" + c.getName());
            }
        } else if (arguments.length == 1) {
            if (currentCampaignID == null) {
                System.out.println("!-- Must enter a campaign to list it's characters --!");
            }
            Campaign campaign = guildQuest.getCampaignsByID().get(currentCampaignID);
            QuestEvent event = campaign.getEventByName(arguments[0]);
            if (event == null) {
                System.out.println("!-- Cannot find an event with that name --!");
                return;
            }
            AuthorizationService authorizationService = guildQuest.getAuthorizationService();
            if (!authorizationService.canView(user, campaign, event)) {
                System.out.println("!-- You do not have permission to view details of this event --!");
                return;
            }
            for (UUID id : event.getParticipantIDs()) {
                Character c = user.getCharactersByID(id);
                System.out.println("-->" + c.getName());
            }
        } else {
            System.out.println("!-- Requires 0 or 1 arguments, " + arguments.length + " arguments provided --!");
        }
    }
    private void showCharacter(String[] arguments) {
        if (arguments.length != 1) {
            System.out.println("!-- Requires 1 argument, " + arguments.length + " arguments provided --!");
            return;
        }
        if (currentPlayerID == null) {
            System.out.println("!-- Must be logged in to view character details --!");
            return;
        }
        User user = guildQuest.getUsersByID().get(currentPlayerID);
        Character character = user.getCharactersByName(arguments[0]);
        if (character == null) {
            System.out.println("!-- Could not find character of that name --!");
            return;
        }

        System.out.println("< ----- Character Details ----- >");
        System.out.println("\tName: " + character.getName());
        System.out.println("\tClass: " + character.getClassType());
        Inventory inventory = character.getInventory();
        if (!inventory.isEmpty()) {
            System.out.println("\t< ----- Inventory ----- >");
            for (Map.Entry<String, Integer> item : inventory.getItems().entrySet()) {
                System.out.println("\t\t" + item.getKey() + " | " + item.getValue());
            }
        }
        System.out.println("< ----------------------------- >");
    }
    private void createItem(String[] arguments) {
        if (arguments.length == 2) {
            guildQuest.getItemsByName().put(arguments[0], new Item(arguments[0], arguments[1]));
        } else if (arguments.length == 3) {
            guildQuest.getItemsByName().put(arguments[0], new Item(arguments[0], arguments[1], arguments[2]));
        } else {
            System.out.println("!-- Requires 2 or 3 arguments, " + arguments.length + " arguments provided --!");
            return;
        }
        System.out.println("< Item successfully created >");
    }
    private void listItems(String[] arguments) {
        if (arguments.length != 0) {
            System.out.println("!-- Requires 0 arguments, " + arguments.length + " arguments provided --!");
            return;
        }
        if (guildQuest.getItemsByName().isEmpty()) {
            System.out.println("~?~ No items have been created ~?~");
            return;
        }
        for (String itemName : guildQuest.getItemsByName().keySet()) {
            System.out.println("-->" + itemName);
        }
    }
    private void showItem(String[] arguments) {
        if (arguments.length != 1) {
            System.out.println("!-- Requires 1 argument, " + arguments.length + " arguments provided --!");
            return;
        }
        Item item = guildQuest.getItemsByName().get(arguments[0]);
        if (item == null) {
            System.out.println("!-- Could not find an item by that name --!");
            return;
        }
        System.out.println("< ----- Item Details ----- >");
        System.out.println("\tName: " + item.getName());
        System.out.println("\tRarity: " + item.getRarity());
        if (item.getDescription() != null) {
            System.out.println("\tDescription: " + item.getDescription());
        }
        System.out.println("< ------------------------ >");
    }
    private void addItem(String[] arguments) {
        if (arguments.length != 3) {
            System.out.println("!-- Requires 3 arguments, " + arguments.length + " arguments provided --!");
            return;
        }
        if (currentPlayerID == null) {
            System.out.println("!-- Must be logged in to edit character data --!");
            return;
        }
        User user = guildQuest.getUsersByID().get(currentPlayerID);
        Character character = user.getCharactersByName(arguments[0]);
        if (character == null) {
            System.out.println("!-- Could not find a character with that name --!");
            return;
        }
        Item item = guildQuest.getItemsByName().get(arguments[1]);
        if (item == null) {
            System.out.println("!-- Item with that name has not been created --!");
            return;
        }
        try {
            int quantity = Integer.parseInt(arguments[2]);
            character.getInventory().addItem(item, quantity);
            System.out.println("< Item added to inventory >");
        } catch (NumberFormatException e) {
            System.out.println("!-- Quantity value must be an integer --!");
        }
    }
    private void removeItem(String[] arguments) {
        if (arguments.length != 3) {
            System.out.println("!-- Requires 3 arguments, " + arguments.length + " arguments provided --!");
            return;
        }
        if (currentPlayerID == null) {
            System.out.println("!-- Must be logged in to edit character data --!");
            return;
        }
        User user = guildQuest.getUsersByID().get(currentPlayerID);
        Character character = user.getCharactersByName(arguments[0]);
        if (character == null) {
            System.out.println("!-- Could not find a character with that name --!");
            return;
        }
        Item item = guildQuest.getItemsByName().get(arguments[1]);
        if (item == null) {
            System.out.println("!-- Item with that name has not been created --!");
            return;
        }
        try {
            int quantity = Integer.parseInt(arguments[2]);
            character.getInventory().removeItem(item, quantity);
            System.out.println("< Item removed from inventory >");
        } catch (NumberFormatException e) {
            System.out.println("!-- Quantity value must be an integer --!");
        }
    }
    private void viewInventory(String[] arguments) {
        if (arguments.length != 1) {
            System.out.println("!-- Requires 1 argument, " + arguments.length + " arguments provided --!");
            return;
        }
        if (currentPlayerID == null) {
            System.out.println("!-- Must be logged in to view inventories --!");
            return;
        }
        User user = guildQuest.getUsersByID().get(currentPlayerID);
        Character character = user.getCharactersByName(arguments[0]);
        if (character == null) {
            System.out.println("!-- Cannot find a character with that name --!");
            return;
        }
        Inventory inventory = character.getInventory();
        if (inventory.isEmpty()) {
            System.out.println("~?~ Inventory is empty ~?~");
        }
        System.out.println("< ----- Inventory Details ----- >");
        for (Map.Entry<String, Integer> itemMapEntry : inventory.getItems().entrySet()) {
            Item item = guildQuest.getItemsByName().get(itemMapEntry.getKey());
            System.out.println(item.getName() + " | " + item.getRarity() + " | " + itemMapEntry.getValue());
        }
        System.out.println("< ----------------------------- >");

    }
}