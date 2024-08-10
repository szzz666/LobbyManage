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
import cn.nukkit.level.Position;
import cn.nukkit.potion.Effect;
import cn.nukkit.scheduler.AsyncTask;
import top.szzz666.LobbyManage.LobbyManageMain;
import top.szzz666.LobbyManage.tools.pluginUtil;

import java.io.File;

import static io.netty.util.ResourceLeakDetector.getLevel;
import static java.lang.Thread.sleep;
import static top.szzz666.LobbyManage.LobbyManageMain.nkServer;
import static top.szzz666.LobbyManage.LobbyManageMain.plugin;
import static top.szzz666.LobbyManage.config.LmConfig.*;

public class Listeners implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        CommandCoolTick.put(player, LobbyManageMain.nkServer.getTick());
        if (!ItemCmdStr.isEmpty() & !JoinTp) {
            pluginUtil.JoinItem(ItemCmdStr, player);
        }

        player.teleport(getLobbySpawn());
        if (!JoinMsg.isEmpty()) {
            event.setJoinMessage(JoinMsg.replace("%player%", player.getName()));
        }
        if (!JoinTitle.isEmpty()) {
            for (Player p : Server.getInstance().getOnlinePlayers().values()) {
                p.sendTitle(JoinTitle.split("&")[0].replace("%player%", player.getName()), JoinTitle.split("&")[1].replace("%player%", player.getName()), Integer.parseInt(JoinTitle.split("&")[2].split(",")[0]), Integer.parseInt(JoinTitle.split("&")[2].split(",")[1]), Integer.parseInt(JoinTitle.split("&")[2].split(",")[2]));
            }
        }
        if (!JoinConsoleCmd.isEmpty()) {
            for (String cmd : JoinConsoleCmd) {
                if (!cmd.isEmpty()) {
                    LobbyManageMain.nkServer.dispatchCommand(LobbyManageMain.consoleObjects, cmd.replace("%player%", player.getName()));
                }
            }
        }
        if (!JoinPlayerCmd.isEmpty()) {
            for (String cmd : JoinPlayerCmd) {
                if (!cmd.isEmpty()) {
                    if (cmd.startsWith("op#")) {
                        cmd = cmd.replace("op#", "");
                        if (!player.isOp()) {
                            try {
                                player.setOp(true);
                                LobbyManageMain.nkServer.dispatchCommand(player, cmd.replace("%player%", player.getName()));
                            } finally {
                                player.setOp(false);
                            }
                            continue;
                        }
                    }
                    LobbyManageMain.nkServer.dispatchCommand(player, cmd.replace("%player%", player.getName()));
                }
            }
        }

    }

    @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();
        if (DoubleJump && player.getLevel().equals(getLobbyLevel()) && !player.isCreative()) {
            int tick = nkServer.getTick();
            if (tick - JJumpCoolTick.getOrDefault(player, 0) <= 20) {
                event.setCancelled(true);
                player.setAllowFlight(false);
                return;
            }

            JJumpCoolTick.put(player, tick);
            event.setCancelled(true);
            player.setAllowFlight(false);
            player.setMotion(player.getLocation().getDirectionVector().multiply(2).add(0.0, 0.8, 0.0));
        }
    }


    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (DoubleJump) {
            JJumpCoolTick.remove(player);
        }
        if (!QuitMsg.isEmpty()) {
            event.setQuitMessage(QuitMsg.replace("%player%", player.getName()));
        }

        if (!QuitTitle.isEmpty()) {
            for (Player p : Server.getInstance().getOnlinePlayers().values()) {
                p.sendTitle(QuitTitle.split("&")[0].replace("%player%", player.getName()), QuitTitle.split("&")[1].replace("%player%", player.getName()), Integer.parseInt(QuitTitle.split("&")[2].split(",")[0]), Integer.parseInt(QuitTitle.split("&")[2].split(",")[1]), Integer.parseInt(QuitTitle.split("&")[2].split(",")[2]));
            }
        }
        if (QuitClear) {
            File me = new File(plugin.getDataFolder().getParentFile().getParent() + "/players/" + player.getUniqueId() + ".dat");
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
        CommandCoolTick.remove(player);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (ProtectLevel.contains(player.getLevel())) {
            if (VoidTp && player.getLocation().getY() < 0.0) {
                player.teleport(getLobbySpawn());
            }
        }
        if (DoubleJump && !player.isCreative() && getLevel().equals(getLobbyLevel()) && player.isOnGround() && !player.getAllowFlight()) {
            player.setAllowFlight(true);
        }
        String id = String.valueOf(event.getTo().add(0, -1, 0).getLevelBlock().getId());
        if (!EffectBlock.isEmpty() && EffectBlock.containsKey(id)) {
            String[] effectBlock = EffectBlock.get(id).split(":");
            player.addEffect(Effect.getEffect(Integer.parseInt(effectBlock[0])).setDuration(Integer.parseInt(effectBlock[1])).setAmplifier(Integer.parseInt(effectBlock[2])));
        }

    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        if (DisableWeather && ProtectLevel.contains(event.getLevel())) {
            event.setCancelled();
        }

    }

    @EventHandler
    public void playerHunger(PlayerFoodLevelChangeEvent event) {
        if (DisableHunger && ProtectLevel.contains(event.getPlayer().getLevel())) {
            event.setCancelled();
        }

    }

    @EventHandler
    public void playerTp(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        Position newPosition = event.getTo();
        if (ProtectLevel.contains(newPosition.getLevel())) {
            if (!ItemCmdStr.isEmpty()) {
                pluginUtil.JoinItem(ItemCmdStr, player);
            }
        }
    }

    @EventHandler
    public void playerDamage(EntityDamageEvent event) {
        if (DisableDamage && event.getEntity() instanceof Player && ProtectLevel.contains(event.getEntity().getLevel())) {
            event.setCancelled();
        }

    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (DisableBreak && ProtectLevel.contains(player.getLevel()) && (!player.isOp() || ConstraintOp)) {
            event.setCancelled();
        }

    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (DisablePlace && ProtectLevel.contains(player.getLevel()) && (!player.isOp() || ConstraintOp)) {
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
                        if (!player.isOp()) {
                            try {
                                player.setOp(true);
                                nkServer.dispatchCommand(player, cmd);
                            } finally {
                                player.setOp(false);
                            }
                            continue;
                        }
                    } else if (cmd.startsWith("console#")) {
                        cmd = cmd.replace("console#", "");
                        nkServer.dispatchCommand(nkServer.getConsoleSender(), cmd);
                        continue;
                    }
                    nkServer.dispatchCommand(player, cmd);
                }
            }
        }
        if (DisableInteract && ProtectLevel.contains(event.getPlayer().getLevel())) {
            if (!player.isOp() || ConstraintOp) {
                event.setCancelled();
            }
        }
    }

    @EventHandler
    public void onBlockUpdate(BlockUpdateEvent event) {
        if (DisableBlockUpdate && ProtectLevel.contains(event.getBlock().getLevel())) {
            event.setCancelled();
        }

    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (DisableItemDrop && ProtectLevel.contains(player.getLevel()) && (!player.isOp() || ConstraintOp)) {
            event.setCancelled();
        }

    }
}
