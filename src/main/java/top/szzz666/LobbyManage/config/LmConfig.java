package top.szzz666.LobbyManage.config;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.utils.Config;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import top.szzz666.LobbyManage.LobbyManageMain;
import top.szzz666.LobbyManage.entity.Nbt;
import top.szzz666.LobbyManage.tools.pluginUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LmConfig {
    public static String Language;
    public static String LobbySpawn;
    public static String ReLobbyCmd;
    public static String OpFormCmd;
    public static boolean ConstraintOp;
    public static boolean VoidTp;
    public static boolean JoinTp;
    public static boolean QuitClear;
    public static String JoinMsg;
    public static String JoinTitle;
    public static String QuitMsg;
    public static String QuitTitle;
    public static List<String> JoinConsoleCmd;
    public static List<String> JoinPlayerCmd;
    public static boolean DisableWeather;
    public static int FixedTime;
    public static boolean TimeSync;
    public static String TimeZone;
    public static String Latitude;
    public static String Longitude;
    public static int TaskDelay;
    public static HashMap<String,String> EffectBlock;
    public static boolean DisableHunger;
    public static boolean DisableDamage;
    public static boolean DisablePlace;
    public static boolean DisableBreak;
    public static boolean DisableInteract;
    public static boolean DoubleJump;
    public static boolean DisableBlockUpdate;
    public static boolean DisableItemDrop;
    public static HashMap<String, ArrayList<String>> ItemCmdStr = new HashMap<>();
    public static HashMap<Item, ArrayList<String>> ItemCmd = new HashMap<>();
    public static HashMap<String, Nbt> nbtMap = new HashMap<>();
    public static HashMap<Player, Integer> CommandCoolTick = new HashMap<>();

    public static boolean loadConfig() {
        LobbyManageMain.plugin.saveResource("config.yml");
        Config config = new Config(LobbyManageMain.ConfigPath + "/config.yml", 2);
        Language = config.getString("Language");
        LobbySpawn = config.getString("LobbySpawn");
        ReLobbyCmd = config.getString("ReLobbyCmd");
        OpFormCmd = config.getString("OpFormCmd");
        ConstraintOp = config.getBoolean("ConstraintOp");
        VoidTp = config.getBoolean("VoidTp");
        JoinTp = config.getBoolean("JoinTp");
        QuitClear = config.getBoolean("QuitClear");
        JoinMsg = config.getString("JoinMsg");
        JoinTitle = config.getString("JoinTitle");
        QuitMsg = config.getString("QuitMsg");
        QuitTitle = config.getString("QuitTitle");
        JoinConsoleCmd = config.getStringList("JoinConsoleCmd");
        JoinPlayerCmd = config.getStringList("JoinPlayerCmd");
        FixedTime = config.getInt("FixedTime");
        TimeSync = config.getBoolean("RealTime.TimeSync");
        TimeZone= config.getString("RealTime.TimeZone");
        Latitude= config.getString("RealTime.Latitude");
        Longitude= config.getString("RealTime.Longitude");
        TaskDelay=config.getInt("TaskDelay");
        EffectBlock=(HashMap<String, String>) config.get("EffectBlock");
        DisableWeather = config.getBoolean("DisableWeather");
        DisableHunger = config.getBoolean("DisableHunger");
        DisableDamage = config.getBoolean("DisableDamage");
        DisablePlace = config.getBoolean("DisablePlace");
        DisableBreak = config.getBoolean("DisableBreak");
        DisableInteract = config.getBoolean("DisableInteract");
        DoubleJump = config.getBoolean("DoubleJump");
        DisableBlockUpdate = config.getBoolean("DisableBlockUpdate");
        DisableItemDrop = config.getBoolean("DisableItemDrop");
        ItemCmdStr = (HashMap<String, ArrayList<String>>) config.get("ItemCmd");
        config.save();
        return true;
    }

    public static boolean saveConfig() {
        Config config = new Config(LobbyManageMain.ConfigPath + "/config.yml", 2);
        config.set("Language", Language);
        config.set("LobbySpawn", LobbySpawn);
        config.set("ReLobbyCmd", ReLobbyCmd);
        config.set("OpFormCmd", OpFormCmd);
        config.set("ConstraintOp", ConstraintOp);
        config.set("VoidTp", VoidTp);
        config.set("JoinTp", JoinTp);
        config.set("QuitClear", QuitClear);
        config.set("JoinMsg", JoinMsg);
        config.set("JoinTitle", JoinTitle);
        config.set("QuitMsg", QuitMsg);
        config.set("QuitTitle", QuitTitle);
        config.set("JoinConsoleCmd", JoinConsoleCmd);
        config.set("JoinPlayerCmd", JoinPlayerCmd);
        config.set("FixedTime", FixedTime);
        config.set("RealTime.TimeSync", TimeSync);
        config.set("RealTime.TimeZone", TimeZone);
        config.set("RealTime.Latitude", Latitude);
        config.set("RealTime.Longitude", Longitude);
        config.set("TaskDelay",TaskDelay);
        config.set("EffectBlock",EffectBlock);
        config.set("DisableWeather", DisableWeather);
        config.set("DisableHunger", DisableHunger);
        config.set("DisableDamage", DisableDamage);
        config.set("DisablePlace", DisablePlace);
        config.set("DisableBreak", DisableBreak);
        config.set("DisableInteract", DisableInteract);
        config.set("DoubleJump", DoubleJump);
        config.set("DisableBlockUpdate", DisableBlockUpdate);
        config.set("DisableItemDrop", DisableItemDrop);
        config.save();
        return true;
    }

    public static boolean loadNbts() {
        LobbyManageMain.plugin.saveResource("nbts.json");
        String nbtJson = pluginUtil.readFile(LobbyManageMain.ConfigPath + "/nbts.json");
        Gson gson = new Gson();
        Type type = (new TypeToken<HashMap<String, Nbt>>() {
        }).getType();
        nbtMap = gson.fromJson(nbtJson, type);
        pluginUtil.nkConsole(nbtMap.get("nbt1").toString());
        return true;
    }

    public static Position getLobbySpawn() {
        String[] split = LobbySpawn.split("&");
        String[] split1 = split[0].split(",");
        return new Position(Double.parseDouble(split1[0]), Double.parseDouble(split1[1]), Double.parseDouble(split1[2]), LobbyManageMain.nkServer.getLevelByName(split[1]));
    }

    public static Level getLobbyLevel() {
        return LobbyManageMain.nkServer.getLevelByName(LobbySpawn.split("&")[1]);
    }
}
