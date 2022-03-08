package me.lewin.dellunasystem.commads;

import me.lewin.dellunasystem.Reference;
import me.lewin.dellunasystem.database.WhitelistDB;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VacationCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String args[]) {
        if (sender instanceof Player && sender.isOp()) {
            if (args.length != 2) {
                sender.sendMessage(Reference.FAIL + "잘못된 입력입니다.");
                return true;
            }

            String uuid = Bukkit.getOfflinePlayer(args[1]).getUniqueId().toString();
            switch (args[0]){
                case "add":
                    if (WhitelistDB.exists(uuid)) {
                        sender.sendMessage(Reference.FAIL + "이미 휴가자 목록에 존재하는 유저입니다.");
                        break;
                    }
                    WhitelistDB.addVacation(uuid);
                    sender.sendMessage(Reference.SUCCESS + args[1] + "님이 휴가자 목록에 추가되었습니다.");
                    break;

                case "remove":
                    if (!WhitelistDB.exists(uuid)) {
                        sender.sendMessage(Reference.FAIL + "휴가자 목록에 존재하지 않는 유저입니다.");
                        break;
                    }
                    WhitelistDB.removeVacation(uuid);
                    sender.sendMessage(Reference.SUCCESS + "휴가자 목록에서 제거되었습니다.");
                    break;
            }
        }
        return true;
    }
}