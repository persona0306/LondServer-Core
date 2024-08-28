package peru.sugoi.londserver;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import peru.sugoi.londserver.event.GameJoinEvent;
import peru.sugoi.londserver.event.GameLeaveEvent;
import peru.sugoi.londserver.game.Game;

/**
 * ロンド鯖の中心プラグイン。
 * @author ぺる
 */
public class LondServerAPI {
    private static final LondServerAPI instance = new LondServerAPI();
    private LondServerAPI() {}

    private HashMap<UUID, Game> gameMap = new HashMap<>();

    /**
     * 指定したプレイヤーが現在参加している試合を返す。
     * @param player 試合を取得するプレイヤー
     * @return 指定したプレイヤーが参加している試合。どの試合にも参加していない場合は、null
     */
    public static Game getGame(@Nonnull OfflinePlayer player) {
        return instance.gameMap.get(player.getUniqueId());
    }

    /**
     * このメソッドを実行した時点で、指定した試合に参加しているプレイヤーの集合を返す。
     * 指定した試合に誰も参加していない場合、空の集合を返す(nullではない)。
     * このメソッドを実行した後にプレイヤーが移動しても、戻り値の集合には反映されない。
     * 最新の情報が必要な場合、その都度実行する必要がある。
     * @param game プレイヤーの集合を取得する試合
     * @return 指定した試合に参加しているプレイヤーの集合
     */
    public static Set<OfflinePlayer> getPlayersInGame(@Nonnull Game game) {
        HashSet<OfflinePlayer> playerSet = new HashSet<>();

        instance.gameMap.entrySet().stream()
            .filter(entry -> entry.getValue().equals(game))
            .forEach(entry -> playerSet.add(Bukkit.getOfflinePlayer(entry.getKey())));

        return Collections.unmodifiableSet(playerSet);
    }

    /**
     * 指定したプレイヤーを指定した試合に参加させる。
     * @param player 参加するプレイヤー
     * @param game 参加する試合。nullを指定した場合、joinGameは実行されず、代わりにleaveGame(player)する。
     * @throws IllegalStateException 指定したプレイヤーがすでに何らかの試合に参加している場合。もしくは、指定した試合に参加できない状態にある場合
     */
    public static void joinGame(@Nonnull OfflinePlayer player, @Nullable Game game) throws IllegalStateException {
        if (game == null) {
            leaveGame(player);
            return;
        }

        HashMap<UUID, Game> gameMap = instance.gameMap;

        if (gameMap.containsKey(player.getUniqueId()))
            throw new IllegalStateException("プレイヤーはすでに何らかの試合に参加しています。");

        GameJoinEvent gameJoinEvent = new GameJoinEvent(game, player);
        Bukkit.getPluginManager().callEvent(gameJoinEvent);

        if (gameJoinEvent.isCancelled()) {
            Bukkit.getLogger().info("プレイヤーの試合参加は取りやめになりました。 プレイヤー: " + player.getName() + ", 試合: " + game.getName());
            return;
        }

        game.onJoin(player);
        gameMap.put(player.getUniqueId(), game);

        Bukkit.getLogger().info("プレイヤーが試合に参加しました。 プレイヤー: " + player.getName() + ", 試合: " + game.getName());
    }

    /**
     * 指定したプレイヤーを、そのプレイヤーが参加している試合から退出させる。
     * @param player 退出させるプレイヤー
     * @throws IllegalStateException 指定したプレイヤーが現在参加している試合から退出できない状態にある場合
     */
    public static void leaveGame(@Nonnull OfflinePlayer player) throws IllegalStateException {

        HashMap<UUID, Game> gameMap = instance.gameMap;

        if (!gameMap.containsKey(player.getUniqueId())) return;

        Game game = gameMap.get(player.getUniqueId());

        GameLeaveEvent gameLeaveEvent = new GameLeaveEvent(game, player);
        Bukkit.getPluginManager().callEvent(gameLeaveEvent);

        if (gameLeaveEvent.isCancelled()) {
            Bukkit.getLogger().info("プレイヤーの試合退出は取りやめになりました。 プレイヤー: " + player.getName() + ", 試合: " + game.getName());
            return;
        }

        game.onLeave(player);
        gameMap.remove(player.getUniqueId());

        Bukkit.getLogger().info("プレイヤーが試合から退出しました。 プレイヤー: " + player.getName() + ", 試合: " + game.getName());
        
    }

}
