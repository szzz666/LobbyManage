package top.szzz666.LobbyManage.event;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.block.BlockUpdateEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.level.WeatherChangeEvent;
import cn.nukkit.event.player.*;
import cn.nukkit.item.Item;
import cn.nukkit.scheduler.AsyncTask;
import top.szzz666.LobbyManage.LobbyManageMain;
import top.szzz666.LobbyManage.config.LmConfig;
import top.szzz666.LobbyManage.tools.pluginUtil;

import java.io.File;

import static java.lang.Thread.sleep;
import static top.szzz666.LobbyManage.LobbyManageMain.nkServer;
import static top.szzz666.LobbyManage.LobbyManageMain.plugin;
import static top.szzz666.LobbyManage.config.LmConfig.*;

public class Listeners implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        LmConfig.CommandCoolTick.put(player, LobbyManageMain.nkServer.getTick());
        if (!LmConfig.ItemCmdStr.isEmpty() & !LmConfig.JoinTp) {
            pluginUtil.JoinItem(LmConfig.ItemCmdStr, player);
        }

        player.teleport(LmConfig.getLobbySpawn());
        if (!LmConfig.JoinMsg.isEmpty()) {
            event.setJoinMessage(LmConfig.JoinMsg.replace("%player%", player.getName()));
        }

        if (!LmConfig.JoinTitle.isEmpty()) {
            for (Player p : Server.getInstance().getOnlinePlayers().values()) {
                p.sendTitle(LmConfig.JoinTitle.split("&")[0].replace("%player%", player.getName()), LmConfig.JoinTitle.split("&")[1].replace("%player%", player.getName()), Integer.parseInt(LmConfig.JoinTitle.split("&")[2].split(",")[0]), Integer.parseInt(LmConfig.JoinTitle.split("&")[2].split(",")[1]), Integer.parseInt(LmConfig.JoinTitle.split("&")[2].split(",")[2]));
            }
        }


        if (!LmConfig.JoinConsoleCmd.isEmpty()) {

            for (String cmd : LmConfig.JoinConsoleCmd) {
                LobbyManageMain.nkServer.dispatchCommand(LobbyManageMain.consoleObjects, cmd.replace("%player%", player.getName()));
            }
        }

        if (!LmConfig.JoinPlayerCmd.isEmpty()) {


            for (String cmd : LmConfig.JoinPlayerCmd) {
                if (cmd.startsWith("op#")) {
                    cmd = cmd.replace("op#", "");
                    player.setOp(true);
                    LobbyManageMain.nkServer.dispatchCommand(player, cmd.replace("%player%", player.getName()));
                    player.setOp(false);
                } else {
                    LobbyManageMain.nkServer.dispatchCommand(player, cmd.replace("%player%", player.getName()));
                }
            }
        }

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (!LmConfig.QuitMsg.isEmpty()) {
            event.setQuitMessage(LmConfig.QuitMsg.replace("%player%", player.getName()));
        }

        if (!LmConfig.QuitTitle.isEmpty()) {
            for (Player p : Server.getInstance().getOnlinePlayers().values()) {
                p.sendTitle(LmConfig.QuitTitle.split("&")[0].replace("%player%", player.getName()), LmConfig.QuitTitle.split("&")[1].replace("%player%", player.getName()), Integer.parseInt(LmConfig.QuitTitle.split("&")[2].split(",")[0]), Integer.parseInt(LmConfig.QuitTitle.split("&")[2].split(",")[1]), Integer.parseInt(LmConfig.QuitTitle.split("&")[2].split(",")[2]));
            }
        }
        if (LmConfig.QuitClear) {
            File me = new File(plugin.getDataFolder().getParentFile().getParent() + "\\players\\" + player.getUniqueId() + ".dat");
            if (me.exists()) {
                nkServer.getScheduler().scheduleAsyncTask(plugin, new AsyncTask() {
                    @Override
                    public void onRun() {
                        try {
                            sleep(250);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        me.delete();
                    }
                });

            }
        }
        LmConfig.CommandCoolTick.remove(player);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (LmConfig.VoidTp && player.getLocation().getY() < 0.0 && player.getLevel().equals(LmConfig.getLobbyLevel())) {
            player.teleport(LmConfig.getLobbySpawn());
        }

    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        if (LmConfig.DisableWeather && event.getLevel().equals(LmConfig.getLobbyLevel())) {
            event.setCancelled();
        }

    }

    @EventHandler
    public void playerHunger(PlayerFoodLevelChangeEvent event) {
        if (LmConfig.DisableHunger && event.getPlayer().getLevel().equals(LmConfig.getLobbyLevel())) {
            event.setCancelled();
        }

    }

    @EventHandler
    public void playerTp(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        if (event.getTo().level.equals(LmConfig.getLobbyLevel())) {
            if (!LmConfig.ItemCmdStr.isEmpty()) {
                pluginUtil.JoinItem(LmConfig.ItemCmdStr, player);
            }
        }
    }

    @EventHandler
    public void playerDamage(EntityDamageEvent event) {
        if (LmConfig.DisableDamage && event.getEntity() instanceof Player && event.getEntity().getLevel().equals(LmConfig.getLobbyLevel())) {
            event.setCancelled();
        }

    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (LmConfig.DisableBreak && player.getLevel().equals(LmConfig.getLobbyLevel()) && (!player.isOp() || LmConfig.ConstraintOp)) {
            event.setCancelled();
        }

    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (LmConfig.DisablePlace && player.getLevel().equals(LmConfig.getLobbyLevel()) && (!player.isOp() || LmConfig.ConstraintOp)) {
            event.setCancelled();
        }

    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!ItemCmd.isEmpty()) {
            Item itemV = null;
            for (Item item : ItemCmd.keySet()) {
                if (item.equals(player.getInventory().getItemInHand())) {
                    itemV = item;
                }
            }
            if (itemV != null) {
                int tick = nkServer.getTick();
                if (tick - CommandCoolTick.getOrDefault(player, 0) <= 10) {
                    return;
                }
                CommandCoolTick.put(player, tick);
                for (String cmd : ItemCmd.get(itemV)) {
                    cmd = cmd.replace("%player%", player.getName());
                    if (cmd.startsWith("op#")) {
                        cmd = cmd.replace("op#", "");
                        try {
                            player.setOp(true);
                            nkServer.dispatchCommand(player, cmd);
                        } finally {
                            player.setOp(false);
                        }
                    } else if (cmd.startsWith("console#")) {
                        cmd = cmd.replace("console#", "");
                        nkServer.dispatchCommand(nkServer.getConsoleSender(), cmd);
                    } else {
                        nkServer.dispatchCommand(player, cmd);
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
        if (LmConfig.DisableBlockUpdate && event.getBlock().level.equals(LmConfig.getLobbyLevel())) {
            event.setCancelled();
        }

    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (LmConfig.DisableItemDrop && player.getLevel().equals(LmConfig.getLobbyLevel()) && (!player.isOp() || LmConfig.ConstraintOp)) {
            event.setCancelled();
        }

    }
}
