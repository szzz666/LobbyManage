package top.szzz666.LobbyManage.event;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.block.BlockUpdateEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.inventory.InventoryEvent;
import cn.nukkit.event.level.WeatherChangeEvent;
import cn.nukkit.event.player.*;
import cn.nukkit.item.Item;

import static top.szzz666.LobbyManage.LobbyManageMain.consoleObjects;
import static top.szzz666.LobbyManage.LobbyManageMain.nkServer;
import static top.szzz666.LobbyManage.config.LmConfig.*;
import static top.szzz666.LobbyManage.tools.pluginUtil.JoinItem;


public class Listeners implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        CommandCoolTick.put(player, nkServer.getTick());
        if (!ItemCmdStr.isEmpty()) {
            JoinItem(ItemCmdStr, player);
        }
        player.teleport(getLobbySpawn());
        if (!JoinMsg.isEmpty()) {
            player.sendMessage(JoinMsg.replace("%player%", player.getName()));
        }
        if (!JoinTitle.isEmpty()) {
            player.sendTitle(JoinTitle.split("&")[0].replace("%player%", player.getName()),
                    JoinTitle.split("&")[1].replace("%player%", player.getName()),
                    Integer.parseInt(JoinTitle.split("&")[2].split(",")[0]),
                    Integer.parseInt(JoinTitle.split("&")[2].split(",")[1]),
                    Integer.parseInt(JoinTitle.split("&")[2].split(",")[2]));
        }
        if (!JoinConsoleCmd.isEmpty()) {
            for (String cmd : JoinConsoleCmd) {
                nkServer.dispatchCommand(consoleObjects, cmd.replace("%player%", player.getName()));
            }
        }
        if (!JoinPlayerCmd.isEmpty()) {
            for (String cmd : JoinPlayerCmd) {
                nkServer.dispatchCommand(player, cmd.replace("%player%", player.getName()));
            }
        }
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        CommandCoolTick.remove(player);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (VoidTp) {
            if (player.getLocation().getY() < 0 && player.getLevel().equals(getLobbyLevel())) {
                player.teleport(getLobbySpawn());
            }
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        if (DisableWeather && event.getLevel().equals(getLobbyLevel())) {
            event.setCancelled();
        }
    }

    @EventHandler
    public void playerHunger(PlayerFoodLevelChangeEvent event) {
        if (DisableHunger && event.getPlayer().getLevel().equals(getLobbyLevel())) {
            event.setCancelled();
        }
    }

    @EventHandler
    public void playerDamage(EntityDamageEvent event) {
        if (DisableDamage && event.getEntity() instanceof Player && event.getEntity().getLevel().equals(getLobbyLevel())) {
            event.setCancelled();
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (DisableBreak && player.getLevel().equals(getLobbyLevel())) {
            if (!player.isOp() || ConstraintOp) {
                event.setCancelled();
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (DisablePlace && player.getLevel().equals(getLobbyLevel())) {
            if (!player.isOp() || ConstraintOp) {
                event.setCancelled();
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!ItemCmd.isEmpty()) {
            Item itemInHand = player.getInventory().getItemInHand();
            for (Item item : ItemCmd.keySet()) {
                if (item.equals(itemInHand)) {
                    for (String cmd : ItemCmd.get(item)) {
                        if (!CommandCoolTick.isEmpty()) {
                            int tick = nkServer.getTick();
                            if (tick - CommandCoolTick.get(player) > 5) {
                                nkServer.dispatchCommand(player, cmd);
                            }
                            CommandCoolTick.put(player, tick);
                        }else {
                            CommandCoolTick.put(player, nkServer.getTick());
                        }
                    }
                }
            }
        }
        if (DisableInteract && event.getPlayer().getLevel().equals(getLobbyLevel())) {
            if (!player.isOp() || ConstraintOp) {
                event.setCancelled();
            }
        }
    }

    @EventHandler
    public void onBlockUpdate(BlockUpdateEvent event) {
        if (DisableBlockUpdate && event.getBlock().level.equals(getLobbyLevel())) {
            event.setCancelled();
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (DisableItemDrop && player.getLevel().equals(getLobbyLevel())) {
            if (!player.isOp() || ConstraintOp) {
                event.setCancelled();
            }
        }
    }
}
