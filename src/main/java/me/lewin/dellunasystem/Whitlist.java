package me.lewin.dellunasystem;

import me.lewin.dellunasystem.database.PlayerDB;
import me.lewin.dellunasystem.database.VillageDB;
import me.lewin.dellunasystem.database.WhitelistDB;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Whitlist {
    public static void findAll() {
        if (!WhitelistDB.getWhitelistSettingFile().exists()) {
            WhitelistDB.create();
        }
        if (!WhitelistDB.getBool()) return;

        Integer limitDay = WhitelistDB.getLimit();

        ArrayList<String> vacationList = WhitelistDB.getVacations();
        ArrayList<String> logList = new ArrayList<>();

        Set<OfflinePlayer> set = Bukkit.getWhitelistedPlayers();
        for (OfflinePlayer player : set) {
            long currentTime = System.currentTimeMillis();
            long lastPlayed = player.getLastPlayed();
            long limit = TimeUnit.DAYS.toMillis(limitDay);

            if (currentTime - lastPlayed > limit && !vacationList.contains(player.getUniqueId().toString())) {
                logList.add(player.getUniqueId().toString());
                player.setWhitelisted(false);

                String uuid = player.getUniqueId().toString();
                if (PlayerDB.exists(uuid) && !PlayerDB.getVillage(uuid).equals("델루나")) {

                    String village = PlayerDB.getVillage(uuid);
                    if (VillageDB.getMember(village).size() < 2){
                        VillageDB.getMember(village).remove(uuid);
                        String memberuuid = (String) VillageDB.getMember(village).get(0);
                        PlayerDB.setVillage(memberuuid, "델루나");
                        PlayerDB.setHeader(memberuuid, false);
                        VillageDB.removeVillageFile(village);
                    }
                    else {
                        VillageDB.removeMember(village, uuid);
                        if (PlayerDB.getHeader(uuid)) {
                            if (VillageDB.getHeader(village).equals(uuid)) {
                                if (VillageDB.getSubheader(village).equals("공석")) {
                                    VillageDB.setHeader(village, (String) VillageDB.getMember(village).get(0));
                                }
                                else {
                                    VillageDB.setHeader(village, VillageDB.getSubheader(village));
                                    VillageDB.setSubheader(village, "공석");
                                }
                            }
                            else if (VillageDB.getSubheader(village).equals(uuid)) {
                                VillageDB.setSubheader(village, "공석");
                            }
                        }
                    }

                    PlayerDB.setVillage(uuid, "델루나");
                    PlayerDB.setHeader(uuid, false);
                }
            }
        }
        System.out.println(Reference.SUCCESS + "WHITELIST REMOVED!! =====================");
        for (String uuid : logList) {
            System.out.println(uuid);
        }
        System.out.println(Reference.SUCCESS + "=========================================");
    }
}
