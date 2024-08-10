package top.szzz666.LobbyManage.task;


import cn.nukkit.level.Level;

import static top.szzz666.LobbyManage.LobbyManageMain.nkServer;
import static top.szzz666.LobbyManage.LobbyManageMain.plugin;
import static top.szzz666.LobbyManage.config.LmConfig.*;
import static top.szzz666.LobbyManage.tools.pluginUtil.getGameTimeFromRealTime;

public class TimeTask {
    public TimeTask() {
        nkServer.getScheduler().scheduleRepeatingTask(plugin, () -> {
            Level lobbyLevel = getLobbyLevel();
            if(RealTime){
                lobbyLevel.setTime(getGameTimeFromRealTime());
                return;
            }
            if(FixedTime != -1){
                lobbyLevel.setTime(FixedTime);
            }
        },TaskDelay,true);
    }
}
