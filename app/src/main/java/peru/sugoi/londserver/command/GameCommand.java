package peru.sugoi.londserver.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import peru.sugoi.londserver.LondServerAPI;

/**
 * 試合に関する操作をするコマンド。
 */
public class GameCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            listGame(sender);
            return true;
        }

        switch (args[0]) {
            case "leave":
                leaveGame(sender);
                break;

            default:
                help(sender);
        }

        return true;

    }

    private void help(CommandSender sender) {

        sender.sendMessage("/" + ChatColor.AQUA + "game" + ChatColor.GRAY + ": 試合の一覧を表示");
        
        if (sender instanceof Player) {

            sender.sendMessage("/" + ChatColor.AQUA + "game leave" + ChatColor.GRAY + ": 試合から退出");

        }
    }

    private void leaveGame(CommandSender sender) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "プレイヤー専用の操作です");
            help(sender);
            return;
        }

        Player player = (Player) sender;

        if (LondServerAPI.getGame(player) == null) {
            player.sendMessage("試合に参加していません");
            return;
        }

        try {
            LondServerAPI.leaveGame(player);

        }catch (IllegalStateException e) {
            e.printStackTrace();
            player.sendMessage(ChatColor.RED + "試合から退出できませんでした");
        }

    }

    private void listGame(CommandSender sender) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("未実装。試合の一覧をログに書き出す予定");
            return;
        }
        
        sender.sendMessage("未実装。ここでメニューが開いて入りたい試合を選ぶ予定");
        
    }
    
}
