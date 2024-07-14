package top.szzz666.LobbyManage.tools;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.TextFormat;
import top.szzz666.LobbyManage.LobbyManageMain;
import top.szzz666.LobbyManage.config.LmConfig;
import top.szzz666.LobbyManage.entity.Nbt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

        for (Nbt.Enchantment ench :tag.getEnch()) {
            if(ench.getId()!=-1){
                enchantment = Enchantment.get(ench.getId()).setLevel(ench.getLvl());
            }
        }
        if(enchantment!=null) {
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
}
