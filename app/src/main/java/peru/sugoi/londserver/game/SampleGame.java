package peru.sugoi.londserver.game;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * 自分で好きな試合を作る(実装する)ときの参考。
 */
public class SampleGame implements Game {
    private static String NAME = "サンプルゲーム";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void onJoin(OfflinePlayer player) throws IllegalStateException {
        if (Bukkit.getOnlinePlayers().size() > 5)
            throw new IllegalStateException("サーバー人数が5人を超えていると参加できません");

        if (!player.isOnline()) return;
        Player entityPlayer = player.getPlayer();

        entityPlayer.teleport(Bukkit.getWorld("SampleGameWorld").getSpawnLocation());
    }

    @Override
    public void onLeave(OfflinePlayer player) throws IllegalStateException {
        if (player.getName().length() > 12)
            throw new IllegalStateException("名前が12文字を超えている人は退出できません");

        if (!player.isOnline()) return;
        Player entityPlayer = player.getPlayer();

        entityPlayer.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
    }
    
}
