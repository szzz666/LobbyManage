package top.szzz666.LobbyManage.tools;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.TextFormat;
import top.szzz666.LobbyManage.entity.Nbt;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static top.szzz666.LobbyManage.LobbyManageMain.plugin;
import static top.szzz666.LobbyManage.config.LmConfig.ItemCmd;
import static top.szzz666.LobbyManage.config.LmConfig.nbtMap;

public class pluginUtil {

    public static void JoinItem(HashMap<String, ArrayList<String>> itemCmd, Player player) {
        if (!itemCmd.isEmpty()) {
            player.getInventory().clearAll();
            //遍历ItemCmd
            for (String key : itemCmd.keySet()) {
                String[] split = key.split(":");
                Item item = Item.get(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
                if (split[4].split("=")[0].equals("nbt")) {
                    String nbtString = split[4].replace("nbt=", "");
                    addNBTToItem(nbtString, item);
                } else {
                    String diyName = split[4].replace("name=", "");
                    item.setCustomName(diyName);
                }
                player.getInventory().setItem(Integer.parseInt(split[3]), item);
                ArrayList<String> cmd = itemCmd.get(key);
                ItemCmd.put(item, cmd);
            }
        }
    }

    //使用nk插件的addNBTToItem
//    public static String getItemNBTString(Item item) {
//        CompoundTag nbt = item.getNamedTag();
//        if (nbt == null) {
//            return null;
//        }
//        try {
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
//            NBTIO.write(nbt, dataOutputStream);
//            byte[] bytes = byteArrayOutputStream.toByteArray();
//            return Base64.encode(bytes).toString(); // 将字节数组转换为Base64;
//        } catch (IOException e) {
//            e.fillInStackTrace();
//            return null; // 如果发生异常，返回空NBT字符串
//        }
//    }


    //使用nk插件的addNBTToItem
    public static void addNBTToItem(String nbtString, Item item) {
        Nbt nbt = nbtMap.get(nbtString);
        Nbt.Tag tag = nbt.getTag();
        //设置自定义物品名称
        item.setCustomName(tag.getDisplay().getName());
        //添加描述
        List<String> lore = tag.getDisplay().getLore();
        for (String s : lore) {
            item.setLore(s);
        }
        //添加附魔
        Enchantment enchantment = null;
        for (Nbt.Enchantment ench : tag.getEnch()) {
            enchantment = Enchantment.get(ench.getId()).setLevel(ench.getLvl());
        }
        item.addEnchantment(enchantment);
        // 创建CompoundTag来存储额外的数据
        CompoundTag itemTag = item.getNamedTag();
        itemTag.putBoolean("minecraft:item_lock", tag.isMinecraft_itemLock());
        item.setNamedTag(itemTag);
    }


    //读取一个文件并将其内容转换为一个字符串
    public static String readFile(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.fillInStackTrace();
        }
        return content.toString();
    }

    //使用nk插件的控制台输出
    public static void nkConsole(String msg) {
        plugin.getLogger().info(TextFormat.colorize('&', msg));
    }

    public static void nkConsole(String msg, int typeNum) {
        if (typeNum == 1) {
            plugin.getLogger().warning(TextFormat.colorize('&', msg));
        } else if (typeNum == 2) {
            plugin.getLogger().error(TextFormat.colorize('&', msg));
        } else {
            plugin.getLogger().info(TextFormat.colorize('&', msg));
        }
    }
}
