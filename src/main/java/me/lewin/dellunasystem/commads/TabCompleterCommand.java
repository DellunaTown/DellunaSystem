package me.lewin.dellunasystem.commads;

import me.lewin.dellunasystem.database.ChatDB;
import me.lewin.dellunasystem.database.PlayerDB;
import me.lewin.dellunasystem.database.VillageDB;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TabCompleterCommand implements TabCompleter {
    List<String> empty = new ArrayList<String>() {{ add(""); }};
    String[] en_commandsA = { "info", "accept", "list", "create", "remove", "color", "name", "search", "rcadd", "rcremove" };
    String[] ko_commandsA = { "정보", "수락", "목록", "생성", "제거", "색상", "이름", "검색", "기록추가", "기록삭제" };

    String[] en_commandsB = { "help", "info", "search", "rcadd", "rcremove" };
    String[] ko_commandsB = { "도움말", "정보", "검색", "기록추가", "기록삭제" };

    String[] en_commandsC = { "help", "info", "accept", "search", "rcadd", "rcremove"};
    String[] ko_commandsC = { "도움말", "정보", "수락", "검색", "기록추가", "기록삭제"};

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
    {
        if (args.length > 0) {
            switch (args[0]) {
                case "rcadd":
                case "기록추가":
                    return tabCommandrcadd(sender, args);

                case "rcremove":
                case "기록삭제":
                    return tabCommandrcremove(sender, args);

                case "name":
                case "이름":
                    return tabCommandName(sender, args);

                case "info":
                case "정보":
                    return tabCommandInfo(sender, args);

                case "remove":
                case "제거":
                    return tabCommandRemove(sender, args);

                case "create":
                case "생성":
                    return tabCommandCreate(sender, args);

                case "search":
                case "검색":
                    return tabCommandSearch(sender, args);

                case "color":
                case "색상":
                    return tabCommandColor(sender, alias, args);

                default:
                    return tabCommandMain(sender, alias, args[0]);
            }
        } else {
            return empty;
        }
    }

    private List<String> tabCommandMain(CommandSender sender, String alias, String args) {
        if (alias.equals("마을")) {
            List<String> list_ko = new ArrayList<>(Arrays.asList(ko_commandsA));

            if (!(sender.isOp())) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;

                    if (!ChatDB.playerInviteMap.containsKey(player)) {
                        List<String> listB = new ArrayList<>(Arrays.asList(ko_commandsB));

                        return tabCompleteSort(listB, args);
                    } else {
                        List<String> listC = new ArrayList<>(Arrays.asList(ko_commandsC));

                        return tabCompleteSort(listC, args);
                    }
                }
            }

            return tabCompleteSort(list_ko, args);
        }
        else {
            List<String> list_en = new ArrayList<>(Arrays.asList(en_commandsA));

            if (!(sender.isOp())) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;

                    if (!ChatDB.playerInviteMap.containsKey(player)) {
                        List<String> listB = new ArrayList<>(Arrays.asList(en_commandsB));

                        return tabCompleteSort(listB, args);
                    } else {
                        List<String> listC = new ArrayList<>(Arrays.asList(en_commandsC));

                        return tabCompleteSort(listC, args);
                    }
                }
            }

            return tabCompleteSort(list_en, args);
        }
    }
    private List<String> tabCompleteSort(List<String> list, String args) {
        List<String> sortList = new ArrayList<>();
        for (String s : list)
        {
            if (args.isEmpty()) return list;

            if (s.toLowerCase().startsWith(args.toLowerCase()))
                sortList.add(s);
        }
        return sortList;
    }

    private List<String> tabCommandRemove(CommandSender sender, String[] args) {
        if (sender.isOp()) {
            if (args.length == 2)
                return tabCompleteSort(tabCommandVillageList(), args[1]);
            else
                return empty;
        } else {
            return empty;
        }
    }
    private List<String> tabCommandSearch(CommandSender sender, String[] args) {
        if (sender.isOp()) {
            if (args.length == 2)
                return tabCompleteSort(tabCommandPlayerList(), args[1]);
            else
                return empty;
        } else {
            return empty;
        }
    }
    private List<String> tabCommandName(CommandSender sender, String[] args) {
        if (sender.isOp()) {
            if (args.length == 2)
                return tabCompleteSort(tabCommandVillageList(), args[1]);
            else
                return empty;
        } else {
            return empty;
        }
    }
    private List<String> tabCommandCreate(CommandSender sender, String[] args) {
        if (sender.isOp()) {
            if (args.length == 2)
                return empty;
            else if (args.length == 3) {
                if (args[1].isEmpty())
                    return empty;
                else
                {
                    return tabCompleteSort(tabCommandPlayerList(), args[1]);
                }
            }
            else
                return empty;
        } else {
            return empty;
        }
    }
    private List<String> tabCommandInfo(CommandSender sender, String[] args) {
        if (sender.isOp()) {
            if (args.length == 2)
                return tabCompleteSort(tabCommandVillageList(), args[1]);
            else
                return empty;
        } else {
            return empty;
        }
    }
    private List<String> tabCommandrcadd(CommandSender sender, String[] args) {
        if (sender.isOp()) {
            if (args.length == 2)
                return new ArrayList<String>() {{ add("<기록 내용>"); }};
            else
                return empty;
        } else {
            return empty;
        }
    }
    private List<String> tabCommandrcremove(CommandSender sender, String[] args) {
        if (sender.isOp()) {
            if (args.length == 2)
                return empty;
            else
                return empty;
        } else {
            return empty;
        }
    }
    private List<String> tabCommandColor(CommandSender sender, String alias, String[] args) {
        if (sender.isOp()) {
            if (args.length == 2)
                return tabCompleteSort(tabCommandVillageList(), args[1]);
            else if (args.length == 3) {
                if (args[1].isEmpty())
                    return empty;
                else
                {
                    if (alias.equals("색상")) {
                        return new ArrayList<String>() {{ add("(색상코드)"); }};
                    } else {
                        return new ArrayList<String>() {{ add("(ColorCode)"); }};
                    }
                }
            } else
                return empty;
        } else {
            return empty;
        }
    }

    private List<String> tabCommandVillageList() {
        List<String> list = new ArrayList<>();

        for (String village : VillageDB.getVillages()) {
            list.add(village);
        }

        Collections.sort(list);

        return list;
    }
    private List<String> tabCommandPlayerList() {
        List<String> list = new ArrayList<>();

        for (Player player : Bukkit.getOnlinePlayers()) {
            list.add(player.getName());
        }

        Collections.sort(list);

        return list;
    }
}
