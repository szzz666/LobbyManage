package top.szzz666.LobbyManage.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import top.szzz666.LobbyManage.config.LangConfig;
import top.szzz666.LobbyManage.config.LmConfig;
import top.szzz666.LobbyManage.form.LmForm;

public class OpFormCommand extends Command {
   public OpFormCommand() {
      super(LmConfig.OpFormCmd, LangConfig.OpFormCmd_description);
   }

   public boolean execute(CommandSender sender, String label, String[] args) {
      if (sender.isOp()) {
         Player player = (Player)sender;
         LmForm.mainForm(player);
      }

      return false;
   }
}
