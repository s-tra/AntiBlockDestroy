package online.vivaseikatsu.stra.antiblockdestroy;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;


public final class AntiBlockDestroy extends JavaPlugin {

    public FileConfiguration config;

    @Override
    public void onEnable() {
        // Plugin startup logic

        // listenerの処理を行うクラスを宣言
        getServer().getPluginManager().registerEvents(new BlockDestroyListener(this),this);

        // config.ymlの準備
        // config.ymlがない場合はファイルを出力
        saveDefaultConfig();

        // config.ymlの読み込み
        config = getConfig();

        getLogger().info("プラグインが有効になりました。");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("プラグインが無効になりました。");
    }


    // コマンドの処理
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // コマンド"antibd"
        if (command.getName().equalsIgnoreCase("antibd")) {

            // オプションが指定されている場合の処理
            if(args.length != 0){

                // reloadオプションの処理
                if(args[0].equalsIgnoreCase("reload")){

                    // senderが権限を持っていない場合は終了
                    if (!sender.hasPermission("antiblockdestroy.reload")) {
                        sender.sendMessage(ChatColor.GRAY+"[AntiBlockDestroy] "+ChatColor.RED+"権限がありません！");
                        return true;
                    }

                    // config.ymlのリロード
                    reloadConfig();
                    // config.ymlの読み込み
                    config = getConfig();

                    // リロードしたことの通知
                    sender.sendMessage(ChatColor.GRAY+"[AntiBlockDestroy] "+ChatColor.GREEN+"config.ymlの再読み込みが完了しました。");

                    return true;

                    // reload ここまで
                }
            // オプションここまで
            }


            sender.sendMessage(ChatColor.GRAY+"[AntiBlockDestroy] "+ChatColor.RED+"有効なオプションを指定してください！");
            return true;

        // コマンド"antibd"ここまで
        }

        return true;

    // コマンドの処理ここまで
    }


}
