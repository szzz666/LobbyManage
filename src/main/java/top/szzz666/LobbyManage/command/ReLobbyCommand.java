package top.szzz666.LobbyManage.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

import static top.szzz666.LobbyManage.config.LangConfig.ReLobbyCmd_description;
import static top.szzz666.LobbyManage.config.LmConfig.ReLobbyCmd;
import static top.szzz666.LobbyManage.config.LmConfig.getLobbySpawn;

public class ReLobbyCommand extends Command {
    public ReLobbyCommand() {
        super(ReLobbyCmd, ReLobbyCmd_description);
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (sender.isPlayer()) {
            Player player = (Player) sender;
            player.teleport(getLobbySpawn());
        }
        return false;
    }


}
