package peru.sugoi.londserver.game;

import org.bukkit.OfflinePlayer;

/**
 * 試合自体を表す。これを実装したクラスを作ってメソッドの中身を書くと、好きな試合を作れる。
 * 参加/退出はLondServerAPIから。
 */
public interface Game {

    /**
     * 試合の名前を取得する。
     * @return 試合の名前
     */
    String getName();

    /**
     * プレイヤーが参加する前の処理。GameJoinEventがCancelされなかった場合に、その後に実行される。
     * @param player 参加しようとしているプレイヤー
     * @throws IllegalStateException 試合に参加できない状態にある場合
     */
    void onJoin(OfflinePlayer player) throws IllegalStateException;

    /**
     * プレイヤーが退出する前の処理。GameLeaveEventがCancelされなかった場合に、その後に実行される。
     * @param player 退出しようとしているプレイヤー
     * @throws IllegalStateException 試合から退出できない状態にある場合
     */
    void onLeave(OfflinePlayer player) throws IllegalStateException;
}
