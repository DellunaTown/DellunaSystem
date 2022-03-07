package me.lewin.dellunasystem.database;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatDB {
    /** Player Village Map < Player , VillageName > **/
    public static Map<Player, String> playerVillageMap = new HashMap<>();

    /** Player Chatmode Map < Player , Chatmode > **/
    public static Map<Player, Integer> playerChatmodeMap = new HashMap<>();

    /** Player Invite Map < Player , VillageName > **/
    public static Map<Player, String> playerInviteMap = new HashMap<>();

    /** Village Color Map < VillageName , ColorCode > **/
    public static Map<String, String> colorMap = new HashMap<>();

    /** Village Member Map < VillageName , MemberList > **/
    private static Map<String, ArrayList<Player>> villageMemberMap = new HashMap<>();
    public static void addVillageMemberMap(String village, Player player) {
        ArrayList<Player> memberList = new ArrayList<>();
        if (villageMemberMap.containsKey(village)) {
            memberList = villageMemberMap.get(village);
        }
        memberList.add(player);
        villageMemberMap.put(village, memberList);
    }
    public static void removeVillageMemberMap(String village, Player player) {
        if (villageMemberMap.get(village).size() == 1) {
            villageMemberMap.remove(village);
        }
        else {
            ArrayList<Player> memberList = new ArrayList<>();
            memberList.remove(player);
            villageMemberMap.put(village, memberList);
        }
    }

    /** Player Party Map < Player , PartyName > **/
    public static Map<Player, Integer> playerPartyMap = new HashMap<>();

    /** Party Chat List **/
    public static ArrayList<Player> party1 = new ArrayList<>();
    public static ArrayList<Player> party2 = new ArrayList<>();
    public static ArrayList<Player> party3 = new ArrayList<>();
    public static ArrayList<Player> party4 = new ArrayList<>();

    /** GM List **/
    public static ArrayList<Player> GMList = new ArrayList<>();
}
