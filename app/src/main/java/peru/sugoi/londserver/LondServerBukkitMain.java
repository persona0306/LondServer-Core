package peru.sugoi.londserver;

import org.bukkit.plugin.java.JavaPlugin;

import peru.sugoi.londserver.command.GameCommand;

/**
 * ロンド鯖の中心プラグイン。
 * 機能の呼び出しはLondServerAPIからできる。
 */
public class LondServerBukkitMain extends JavaPlugin {

    private static LondServerBukkitMain instance = null;

    public static LondServerBukkitMain instance() {
        return instance;
    }

    public LondServerBukkitMain() {
        if (instance == null) instance = this;
    }

    @Override
    public void onEnable() {
        getCommand("game").setExecutor(new GameCommand());
    }

}
