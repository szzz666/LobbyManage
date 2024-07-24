package top.szzz666.LobbyManage.event;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.EntityMetadata;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.block.BlockUpdateEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.level.WeatherChangeEvent;
import cn.nukkit.event.player.*;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.level.Sound;
import cn.nukkit.network.protocol.*;
import cn.nukkit.potion.Effect;
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
        CommandCoolTick.put(player, LobbyManageMain.nkServer.getTick());
        if (!ItemCmdStr.isEmpty() & !JoinTp) {
            pluginUtil.JoinItem(ItemCmdStr, player);
        }
        if(JoinTp) {
            getLobbySpawn(player);
        }
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
                LobbyManageMain.nkServer.dispatchCommand(LobbyManageMain.consoleObjects, cmd.replace("%player%", player.getName()));
            }
        }
        if (!JoinPlayerCmd.isEmpty()) {
            for (String cmd : JoinPlayerCmd) {
                if (cmd.startsWith("op#")) {
                    cmd = cmd.replace("op#", "");
                    if(!player.isOp()) {
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
            Level level = player.getLevel();
            level.addSound(player.getLocation(), Sound.MOB_ENDERDRAGON_FLAP);
        }
    }


    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        long playerId = player.getId();
        if (onChair.containsKey(playerId)) {
            RemoveEntityPacket removeEntityPacket = new RemoveEntityPacket();
            removeEntityPacket.eid = onChair.remove(playerId);
            RemoveEntityPacket removeTagblockPacket = new RemoveEntityPacket();
            removeTagblockPacket.eid = tagBlock.remove(playerId);
            BeSeated.remove(playerId);
            nkServer.getOnlinePlayers().values().forEach((p) -> {
                p.dataPacket(removeEntityPacket);
                p.dataPacket(removeTagblockPacket);
            });
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
        CommandCoolTick.remove(player);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.getLevel().equals(getLobbyLevel())) {
            if (VoidTp && player.getLocation().getY() < -30.0) {
                getLobbySpawn(player);
            }
            if (DoubleJump && !player.isCreative()&&player.level.equals(LmConfig.getLobbyLevel()) && player.isOnGround() && !player.getAllowFlight()) {
                player.setAllowFlight(true);
            }
            String id = String.valueOf(event.getTo().add(0, -1, 0).getLevelBlock().getId());
            if (!EffectBlock.isEmpty() && EffectBlock.containsKey(id)) {
                String[] effectBlock = EffectBlock.get(id).split(":");
                player.addEffect(Effect.getEffect(Integer.parseInt(effectBlock[0])).setDuration(Integer.parseInt(effectBlock[1])).setAmplifier(Integer.parseInt(effectBlock[2])));
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
    public void playerTp(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        if (event.getTo().level.equals(getLobbyLevel())) {
            if (!ItemCmdStr.isEmpty()) {
                pluginUtil.JoinItem(ItemCmdStr, player);
            }
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
        if (DisableBreak && player.getLevel().equals(getLobbyLevel()) && (!player.isOp() || ConstraintOp)) {
            event.setCancelled();
        }

    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (DisablePlace && player.getLevel().equals(getLobbyLevel()) && (!player.isOp() || ConstraintOp)) {
            event.setCancelled();
        }

    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(!ChairPos.isEmpty()) {
            Block b=event.getBlock();
            Long playerId=player.getId();
            if (b != null && event.getAction().equals(PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) && b.isNormalBlock() && !(b.y > player.y + 2.0) && !(b.y < player.y - 2.0) && !b.up().isSolid()) {
                String pos = b.getFloorX() + ":" + b.getFloorY() + ":" + b.getFloorZ();
                if (ChairPos.contains(pos)&&!BeSeated.containsValue(pos)) {
                    int tick = nkServer.getTick();
                    if (tick - ChairCoolTick.getOrDefault(player, 0) <= 20) {
                        return;
                    }
                    ChairCoolTick.put(player, tick);
                    BeSeated.put(playerId,pos);
                    AddEntityPacket addTagblockPacket = new AddEntityPacket();
                    long eid = Entity.entityCount++;
                    tagBlock.put(playerId, eid);
                    addTagblockPacket.entityRuntimeId = eid;
                    addTagblockPacket.entityUniqueId = eid;
                    addTagblockPacket.speedX = 0.0F;
                    addTagblockPacket.speedY = 0.0F;
                    addTagblockPacket.speedZ = 0.0F;
                    addTagblockPacket.pitch = 0.0F;
                    addTagblockPacket.yaw = 0.0F;
                    addTagblockPacket.x = (float) (b.getX() + 0.5);
                    addTagblockPacket.y = (float) (b.getY() + 0.3);
                    addTagblockPacket.z = (float) (b.getZ() + 0.5);
                    addTagblockPacket.type = 84;
                    long flags = 0L;
                    flags |= 16384L;
                    flags |= 32L;
                    flags |= 65536L;
                    flags |= 32768L;
                    addTagblockPacket.metadata = (new EntityMetadata()).putLong(0, flags).putShort(7, 400).putShort(42, 400).putString(4, "").putLong(37, -1L).putFloat(38, 1.0E-4F);
                    MoveEntityAbsolutePacket moveTagblockPacket = new MoveEntityAbsolutePacket();
                    moveTagblockPacket.eid = eid;
                    moveTagblockPacket.x = b.getX() + 0.5;
                    moveTagblockPacket.y = b.getY() + 0.7;
                    moveTagblockPacket.z = b.getZ() + 0.5;
                    AddEntityPacket addEntityPacket = new AddEntityPacket();
                    eid = Entity.entityCount++;
                    onChair.put(playerId, eid);
                    addEntityPacket.entityRuntimeId = eid;
                    addEntityPacket.entityUniqueId = eid;
                    addEntityPacket.speedX = 0.0F;
                    addEntityPacket.speedY = 0.0F;
                    addEntityPacket.speedZ = 0.0F;
                    addEntityPacket.pitch = 0.0F;
                    addEntityPacket.yaw = (float) faces[event.getBlock().getDamage()];
                    addEntityPacket.x = (float) (b.getX() + 0.5);
                    addEntityPacket.y = (float) (b.getY() + 1.6);
                    addEntityPacket.z = (float) (b.getZ() + 0.5);
                    addEntityPacket.type = 84;
                    flags = 0L;
                    flags |= 16384L;
                    flags |= 32L;
                    flags |= 65536L;
                    flags |= 32768L;
                    addEntityPacket.metadata = (new EntityMetadata()).putLong(0, flags).putShort(7, 400).putShort(42, 400).putLong(37, -1L).putFloat(38, 1.0E-4F);
                    MoveEntityAbsolutePacket moveEntityPacket = new MoveEntityAbsolutePacket();
                    moveEntityPacket.eid = eid;
                    moveEntityPacket.x = b.getX() + 0.5;
                    moveEntityPacket.y = b.getY() + 1.6;
                    moveEntityPacket.z = b.getZ() + 0.5;
                    moveEntityPacket.yaw = faces[event.getBlock().getDamage()];
                    moveEntityPacket.headYaw = faces[event.getBlock().getDamage()];
                    moveEntityPacket.pitch = 0.0;
                    SetEntityLinkPacket setEntityLinkPacket = new SetEntityLinkPacket();
                    setEntityLinkPacket.vehicleUniqueId = eid;
                    setEntityLinkPacket.riderUniqueId = player.getId();
                    setEntityLinkPacket.type = 2;
                    nkServer.getOnlinePlayers().values().forEach((target) -> {
                        target.dataPacket(addEntityPacket);
                        target.dataPacket(moveEntityPacket);
                        target.dataPacket(addTagblockPacket);
                        target.dataPacket(moveTagblockPacket);
                        target.dataPacket(setEntityLinkPacket);
                    });
                    player.setDataFlag(0, 2, true);
                }
            }
        }
        if (!ItemCmd.isEmpty()) {
            Item itemV = null;
            for (Item item : ItemCmd.keySet()) {
                if (item.equals(player.getInventory().getItemInHand())) {
                    itemV = item;
                }
            }
            if (itemV != null) {
                int tick = nkServer.getTick();
                if (tick - CommandCoolTick.getOrDefault(player, 0) <= 20) {
                    return;
                }
                CommandCoolTick.put(player, tick);
                for (String cmd : ItemCmd.get(itemV)) {
                    cmd = cmd.replace("%player%", player.getName());
                    if (cmd.startsWith("op#")) {
                        cmd = cmd.replace("op#", "");
                        if(!player.isOp()) {
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
        if (DisableInteract && event.getPlayer().getLevel().equals(getLobbyLevel())) {
            if (!player.isOp() || ConstraintOp) {
                event.setCancelled();
            }
        }
    }
    @EventHandler
    public void onExit(DataPacketReceiveEvent event) {
        if (event.getPacket() instanceof InteractPacket&& ((InteractPacket)event.getPacket()).action == 3) {
            long playerId = event.getPlayer().getId();
            if (onChair.containsKey(playerId)) {
                RemoveEntityPacket removeEntityPacket = new RemoveEntityPacket();
                removeEntityPacket.eid = onChair.remove(playerId);
                RemoveEntityPacket removeTagblockPacket = new RemoveEntityPacket();
                removeTagblockPacket.eid = tagBlock.remove(playerId);
                BeSeated.remove(playerId);
                nkServer.getOnlinePlayers().values().forEach((p) -> {
                    p.dataPacket(removeEntityPacket);
                    p.dataPacket(removeTagblockPacket);
                });
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
        if (DisableItemDrop && player.getLevel().equals(getLobbyLevel()) && (!player.isOp() || ConstraintOp)) {
            event.setCancelled();
        }

    }
}
