package top.szzz666.LobbyManage;

import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.scheduler.Task;
import cn.nukkit.scheduler.TaskHandler;
import top.szzz666.LobbyManage.command.OpFormCommand;
import top.szzz666.LobbyManage.command.ReLobbyCommand;
import top.szzz666.LobbyManage.event.Listeners;

import static top.szzz666.LobbyManage.config.LangConfig.loadLangConfig;
import static top.szzz666.LobbyManage.config.LmConfig.*;
import static top.szzz666.LobbyManage.tools.pluginUtil.nkConsole;


public class LobbyManageMain extends PluginBase {
    public static Plugin plugin;
    public static Server nkServer;
    public static CommandSender consoleObjects;
    public static String ConfigPath;
    private TaskHandler setTimeTask;

    @Override
    public void onLoad() {
        //插件读取
        nkServer = getServer();
        plugin = this;
        consoleObjects = getServer().getConsoleSender();
        ConfigPath = getDataFolder().getPath();
        loadConfig();
        loadLangConfig();
        loadNbts();
        nkConsole("&bLobbyManage插件读取...");
    }

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new Listeners(), this);
        this.getServer().getCommandMap().register(this.getName(), new ReLobbyCommand());
        this.getServer().getCommandMap().register(this.getName(), new OpFormCommand());
        setTimeTask = getServer().getScheduler().scheduleRepeatingTask(this, new Task() {
            @Override
            public void onRun(int currentTick) {
                if (FixedTime > 0) {
                    getLobbySpawn().getLevel().setTime(FixedTime);
                }
            }
        }, 20 * 60);
        nkConsole("&bLobbyManage插件开启");
    }

    @Override
    public void onDisable() {
        if (setTimeTask != null) {
            setTimeTask.cancel();
        }
        //插件关闭
        nkConsole("&bLobbyManage插件关闭");
    }

}
