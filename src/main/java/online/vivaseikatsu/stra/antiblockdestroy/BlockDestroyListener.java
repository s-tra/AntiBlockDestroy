package online.vivaseikatsu.stra.antiblockdestroy;


import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;


public class BlockDestroyListener implements Listener {

    private final AntiBlockDestroy plg;

    public BlockDestroyListener(AntiBlockDestroy plg_){
        plg = plg_;
    }



    // ====ここからイベント別の処理たち====



    // エンティティ爆発時の処理
    @EventHandler
    private void onEntityExplodeEvent(EntityExplodeEvent e){

        // ワールド名(文字列)を取得
        String world_name = e.getEntity().getWorld().getName();
        // エンティティの種類(文字列)を取得
        String entity_type = e.getEntity().getType().toString();

        // 例外かどうかの判定、trueなら終了
        if(ExceptionChecker(world_name,entity_type)) return;

        // 破壊されるブロックのリストを空に
        e.blockList().clear();

    // EntityExplodeEvent ここまで
    }


    // ブロック爆発時の処理
    @EventHandler
    private void onBlockExplodeEvent(BlockExplodeEvent e){

        // ワールド名(文字列)を取得
        String world_name = e.getBlock().getWorld().getName();

        // 例外かどうかの判定、trueなら終了
        if(ExceptionChecker(world_name,"BED")) return;
        if(ExceptionChecker(world_name,"RESPAWN_ANCHOR")) return;

        // 破壊されるブロックのリストを空に
        e.blockList().clear();

    // BlockExplodeEvent ここまで
    }


    // エンティティによるブロック編集時の処理
    @EventHandler
    private void onEntityChangeBlockEvent(EntityChangeBlockEvent e){

        // ワールド名(文字列)を取得
        String world_name = e.getEntity().getWorld().getName();
        // エンティティの種類(文字列)を取得
        String entity_type = e.getEntity().getType().toString();

        // 例外かどうかの判定、trueなら終了
        if(ExceptionChecker(world_name,entity_type)) return;

        // 無効化するMOBの場合(イベントの対象が幅広いため、以下のMOBのみ対象)
        if(e.getEntityType() == EntityType.ENDERMAN ||
                e.getEntityType() == EntityType.ENDER_DRAGON ||
                e.getEntityType() == EntityType.RAVAGER ||
                e.getEntityType() == EntityType.WITHER){

            // イベントをキャンセル
            e.setCancelled(true);

        }

    // EntityChangeBlockEvent ここまで
    }


    // Configを参照し、そのワールドのそのエンティティが例外かどうかを返す
    private boolean ExceptionChecker(String world_name,String entity_type){

        // Configファイルを取得
        FileConfiguration config = plg.config;

        // 例外設定を参照
        for(String key : config.getConfigurationSection("exceptions").getKeys(false)){

            // 例外ワールドだった場合
            if(key.equalsIgnoreCase(world_name)){

                // 例外Entityだった場合はtrueを返す、例外ワールドにALLが指定されていた場合もtrue
                for(String ent : config.getStringList("exceptions." + key)){
                    if(ent.equalsIgnoreCase(entity_type)) return true;
                    if(ent.equalsIgnoreCase("ALL")) return true;
                }

            }

        }


        return false;
    // ExceptionChecker ここまで
    }


}
