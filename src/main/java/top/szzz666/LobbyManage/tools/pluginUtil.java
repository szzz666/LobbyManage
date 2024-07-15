package top.szzz666.LobbyManage.tools;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.level.Level;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.scheduler.Task;
import cn.nukkit.utils.TextFormat;
import top.szzz666.LobbyManage.LobbyManageMain;
import top.szzz666.LobbyManage.config.LmConfig;
import top.szzz666.LobbyManage.entity.Nbt;

import javax.naming.ConfigurationException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.*;

import static java.util.TimeZone.getTimeZone;
import static top.szzz666.LobbyManage.LobbyManageMain.nkServer;
import static top.szzz666.LobbyManage.LobbyManageMain.plugin;
import static top.szzz666.LobbyManage.config.LmConfig.TaskDelay;
import static top.szzz666.LobbyManage.config.LmConfig.TimeSync;

public class pluginUtil {
    public static void JoinItem(HashMap<String, ArrayList<String>> itemCmd, Player player) {
        if (!itemCmd.isEmpty()) {
            player.getInventory().clearAll();
            for (String key : itemCmd.keySet()) {
                String[] split = key.split(":");
                Item item = Item.get(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
                String nbtString;
                if (split[4].split("=")[0].equals("nbt")) {
                    nbtString = split[4].replace("nbt=", "");
                    addNBTToItem(nbtString, item);
                } else {
                    nbtString = split[4].replace("name=", "");
                    item.setCustomName(nbtString);
                }

                player.getInventory().setItem(Integer.parseInt(split[3]), item);
                ArrayList<String> cmd = itemCmd.get(key);
                LmConfig.ItemCmd.put(item, cmd);
            }
        }

    }

    public static void addNBTToItem(String nbtString, Item item) {
        Nbt nbt = LmConfig.nbtMap.get(nbtString);
        Nbt.Tag tag = nbt.getTag();
        item.setCustomName(tag.getDisplay().getName());
        List<String> lore = tag.getDisplay().getLore();

        for (String s : lore) {
            item.setLore(s);
        }

        Enchantment enchantment = null;

        for (Nbt.Enchantment ench : tag.getEnch()) {
            if (ench.getId() != -1) {
                enchantment = Enchantment.get(ench.getId()).setLevel(ench.getLvl());
            }
        }
        if (enchantment != null) {
            item.addEnchantment(enchantment);
        }
        CompoundTag itemTag = item.getNamedTag();
        itemTag.putBoolean("minecraft:item_lock", tag.isMinecraft_itemLock());
        item.setNamedTag(itemTag);
    }

    public static String readFile(String filePath) {
        StringBuilder content = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
            } catch (Throwable var6) {
                try {
                    reader.close();
                } catch (Throwable var5) {
                    var6.addSuppressed(var5);
                }

                throw var6;
            }

            reader.close();
        } catch (IOException e) {
            e.fillInStackTrace();
        }

        return content.toString();
    }

    public static void nkConsole(String msg) {
        LobbyManageMain.plugin.getLogger().info(TextFormat.colorize('&', msg));
    }

    public static void nkConsole(String msg, int typeNum) {
        if (typeNum == 1) {
            LobbyManageMain.plugin.getLogger().warning(TextFormat.colorize('&', msg));
        } else if (typeNum == 2) {
            LobbyManageMain.plugin.getLogger().error(TextFormat.colorize('&', msg));
        } else {
            LobbyManageMain.plugin.getLogger().info(TextFormat.colorize('&', msg));
        }

    }

    public static void setupTime() {
            nkServer.getScheduler().scheduleRepeatingTask(plugin, new Task() {
                public void onRun(int currentTick) {
                    if(TimeSync){
                        SunriseSunsetRequestObject sunriseSunset = null;
                        try {
                            sunriseSunset = new SunriseSunsetRequestObject(getTimeZone(LmConfig.TimeZone), LmConfig.Latitude, LmConfig.Longitude);
                        } catch (IOException | ConfigurationException e) {
                            throw new RuntimeException(e);
                        }
                        int time =calculateWorldTime(Calendar.getInstance(getTimeZone(LmConfig.TimeZone)), sunriseSunset.getSunriseTime(), sunriseSunset.getSunsetTime());
                        for (Level world : nkServer.getLevels().values()) {
                            world.setTime(time);
                        }
                    }else if (LmConfig.FixedTime > 0 ) {
                        for (Level l : nkServer.getLevels().values()) {
                            l.setTime(LmConfig.FixedTime);
                        }
                    }
                }
            }, TaskDelay);
    }

    private static int calculateWorldTime(Calendar cal, String sunriseTime, String sunsetTime) {
        String[] sunriseTimeSplit = sunriseTime.split(":");
        String[] sunsetTimeSplit = sunsetTime.split(":");
        int sunriseMinutes = Integer.parseInt(sunriseTimeSplit[0]) * 60 + Integer.parseInt(sunriseTimeSplit[1]) + Integer.parseInt(sunriseTimeSplit[2].substring(0, 2)) / 60;
        int sunsetMinutes = Integer.parseInt(sunsetTimeSplit[0]) * 60 + Integer.parseInt(sunsetTimeSplit[1]) + Integer.parseInt(sunsetTimeSplit[2].substring(0, 2)) / 60;
        if (sunriseTimeSplit[2].substring(3).equalsIgnoreCase("PM")) {
            sunriseMinutes += 720;
        }
        if (sunsetTimeSplit[2].substring(3).equalsIgnoreCase("PM")) {
            sunsetMinutes += 720;
        }
        LocalTime currentTime = LocalTime.of(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
        int currentMinutes = currentTime.getHour() * 60 + currentTime.getMinute();
        if (currentMinutes >= sunriseMinutes && currentMinutes < sunsetMinutes) {
            return (currentMinutes - sunriseMinutes) / (sunsetMinutes - sunriseMinutes) * 13569 + 23041;
        } else {
            if (currentMinutes < sunriseMinutes) {
                currentMinutes += 1440;
            }
            return (currentMinutes - sunsetMinutes) / (1440 - sunsetMinutes + sunriseMinutes) * 13569 + 12610;
        }
    }
}
