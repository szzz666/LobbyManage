package top.szzz666.LobbyManage.config;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.utils.Config;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import top.szzz666.LobbyManage.entity.Nbt;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import static top.szzz666.LobbyManage.LobbyManageMain.*;
import static top.szzz666.LobbyManage.tools.pluginUtil.nkConsole;
import static top.szzz666.LobbyManage.tools.pluginUtil.readFile;

public class LmConfig {
    public static String Language;
    public static String LobbySpawn;
    public static String ReLobbyCmd;
    public static String OpFormCmd;
    public static boolean ConstraintOp;
    public static boolean VoidTp;
    public static boolean JoinTp;
    public static String JoinMsg;
    public static String JoinTitle;
    public static ArrayList<String> JoinConsoleCmd;
    public static ArrayList<String> JoinPlayerCmd;
    public static int FixedTime;
    public static boolean DisableWeather;
    public static boolean DisableHunger;
    public static boolean DisableDamage;
    public static boolean DisablePlace;
    public static boolean DisableBreak;
    public static boolean DisableInteract;
    public static boolean DisableBlockUpdate;
    public static boolean DisableItemDrop;
    public static HashMap<String, ArrayList<String>> ItemCmdStr = new HashMap<>();
    public static HashMap<Item, ArrayList<String>> ItemCmd = new HashMap<>();
    public static HashMap<String, Nbt> nbtMap = new HashMap<>();
    public static HashMap<Player, Integer> CommandCoolTick = new HashMap<>();


    public static boolean loadConfig() {
        plugin.saveResource("config.yml");
        Config config = new Config(ConfigPath + "/config.yml", Config.YAML);
        Language = config.getString("Language");
        LobbySpawn = config.getString("LobbySpawn");
        ReLobbyCmd = config.getString("ReLobbyCmd");
        OpFormCmd = config.getString("OpFormCmd");
        ConstraintOp = config.getBoolean("ConstraintOp");
        VoidTp = config.getBoolean("VoidTp");
        JoinTp = config.getBoolean("JoinTp");
        JoinMsg = config.getString("JoinMsg");
        JoinTitle = config.getString("JoinTitle");
        JoinConsoleCmd = (ArrayList<String>) config.get("JoinConsoleCmd");
        JoinPlayerCmd = (ArrayList<String>) config.get("JoinPlayerCmd");
        FixedTime = config.getInt("FixedTime");
        DisableWeather = config.getBoolean("DisableWeather");
        DisableHunger = config.getBoolean("DisableHunger");
        DisableDamage = config.getBoolean("DisableDamage");
        DisablePlace = config.getBoolean("DisablePlace");
        DisableBreak = config.getBoolean("DisableBreak");
        DisableInteract = config.getBoolean("DisableInteract");
        DisableBlockUpdate = config.getBoolean("DisableBlockUpdate");
        DisableItemDrop = config.getBoolean("DisableItemDrop");
        ItemCmdStr = (HashMap<String, ArrayList<String>>) config.get("ItemCmd");
        config.save();
        return true;
    }

    public static boolean saveConfig() {
        Config config = new Config(ConfigPath + "/config.yml", Config.YAML);
        config.set("Language", Language);
        config.set("LobbySpawn", LobbySpawn);
        config.set("ReLobbyCmd", ReLobbyCmd);
        config.set("OpFormCmd", OpFormCmd);
        config.set("ConstraintOp", ConstraintOp);
        config.set("VoidTp", VoidTp);
        config.set("JoinTp", JoinTp);
        config.set("JoinMsg", JoinMsg);
        config.set("JoinTitle", JoinTitle);
        config.set("JoinConsoleCmd", JoinConsoleCmd);
        config.set("JoinPlayerCmd", JoinPlayerCmd);
        config.set("FixedTime", FixedTime);
        config.set("DisableWeather", DisableWeather);
        config.set("DisableHunger", DisableHunger);
        config.set("DisableDamage", DisableDamage);
        config.set("DisablePlace", DisablePlace);
        config.set("DisableBreak", DisableBreak);
        config.set("DisableInteract", DisableInteract);
        config.set("DisableBlockUpdate", DisableBlockUpdate);
        config.set("DisableItemDrop", DisableItemDrop);
        config.save();
        return true;
    }

    public static boolean loadNbts() {
        plugin.saveResource("nbts.json");
        String nbtJson = readFile(ConfigPath + "/nbts.json");
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, Nbt>>() {
        }.getType();
        nbtMap = gson.fromJson(nbtJson, type);
        nkConsole(nbtMap.get("nbt1").toString());
        return true;
    }

    public static Position getLobbySpawn() {
        String[] split = LobbySpawn.split("&");
        String[] split1 = split[0].split(",");
        return new Position(Double.parseDouble(split1[0]),
                Double.parseDouble(split1[1]),
                Double.parseDouble(split1[2]),
                nkServer.getLevelByName(split[1]));
    }

    public static Level getLobbyLevel() {
        return nkServer.getLevelByName(LobbySpawn.split("&")[1]);
    }

}
