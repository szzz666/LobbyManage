package top.szzz666.LobbyManage;

import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;
import top.szzz666.LobbyManage.command.OpFormCommand;
import top.szzz666.LobbyManage.command.ReLobbyCommand;
import top.szzz666.LobbyManage.config.LangConfig;
import top.szzz666.LobbyManage.config.LmConfig;
import top.szzz666.LobbyManage.event.Listeners;
import top.szzz666.LobbyManage.task.TimeTask;
import top.szzz666.LobbyManage.tools.pluginUtil;



public class LobbyManageMain extends PluginBase {
    public static Plugin plugin;
    public static Server nkServer;
    public static CommandSender consoleObjects;
    public static String ConfigPath;

    public void onLoad() {
        nkServer = this.getServer();
        plugin = this;
        consoleObjects = this.getServer().getConsoleSender();
        ConfigPath = this.getDataFolder().getPath();
        LmConfig.loadConfig();
        LangConfig.loadLangConfig();
        LmConfig.loadNbts();
        pluginUtil.nkConsole("&bLobbyManage插件读取...");
    }

    public void onEnable() {
        LmConfig.addProtectLevel();
        this.getServer().getPluginManager().registerEvents(new Listeners(), this);
        this.getServer().getCommandMap().register(this.getName(), new ReLobbyCommand());
        this.getServer().getCommandMap().register(this.getName(), new OpFormCommand());
        new TimeTask();
        pluginUtil.nkConsole("&bLobbyManage插件开启");
    }

    public void onDisable() {
        pluginUtil.nkConsole("&bLobbyManage插件关闭");
    }
}
