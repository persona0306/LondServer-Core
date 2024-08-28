package peru.sugoi.londserver.event;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import peru.sugoi.londserver.game.Game;

/**
 * プレイヤーが試合から退出しようとするときのイベント。
 * Cancelされなかった場合、この後に対象のGameのonLeave()が実行される。
 */
public class GameLeaveEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    /**
     * Bukkitのお作法的なメソッド。
     */
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    /**
     * Bukkitのお作法的なメソッド。
     */
    public static HandlerList getHandlerList() {
        return handlers;
    }

    private final Game game;
    private final OfflinePlayer player;
    private boolean isCancelled = false;

    /**
     * 
     * @param game 退出しようとしている試合
     * @param player 退出しようとしているプレイヤー
     */
    public GameLeaveEvent(Game game, OfflinePlayer player) {
        this.game = game;
        this.player = player;
    }

    /**
     * 退出しようとしている試合を取得する。
     * @return 退出しようとしている試合
     */
    public Game getGame() {
        return game;
    }

    /**
     * 試合から退出しようとしているプレイヤーを取得する。
     * @return 試合から退出しようとしているプレイヤー
     */
    public OfflinePlayer getPlayer() {
        return player;
    }

    /**
     * 試合の退出を取りやめるかどうかのフラグを取得する。
     * @return 試合の退出を取りやめるかどうか
     */
    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    /**
     * 試合の退出を取りやめるかどうかを指定する。
     * すべてのEventHandlerの処理が終わったときにtrueの場合、試合の退出を取りやめる。
     * @param cancel 試合の退出をやめるかどうか
     */
    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }
    
}
