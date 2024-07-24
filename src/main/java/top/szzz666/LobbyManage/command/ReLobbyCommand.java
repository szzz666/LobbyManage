package top.szzz666.LobbyManage.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import top.szzz666.LobbyManage.config.LangConfig;
import top.szzz666.LobbyManage.config.LmConfig;

import static top.szzz666.LobbyManage.config.LangConfig.ReLobbyMessage;

public class ReLobbyCommand extends Command {
   public ReLobbyCommand() {
      super(LmConfig.ReLobbyCmd, LangConfig.ReLobbyCmd_description);
   }

   public boolean execute(CommandSender sender, String label, String[] args) {
      if (sender.isPlayer()) {
         Player player = (Player)sender;
         LmConfig.getLobbySpawn(player);
         player.sendMessage(ReLobbyMessage);
      }
      return false;
   }
}
