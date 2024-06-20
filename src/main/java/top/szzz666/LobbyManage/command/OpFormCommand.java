package top.szzz666.LobbyManage.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

import static top.szzz666.LobbyManage.config.LangConfig.OpFormCmd_description;
import static top.szzz666.LobbyManage.config.LmConfig.OpFormCmd;
import static top.szzz666.LobbyManage.form.LmForm.mainForm;

public class OpFormCommand extends Command {
    public OpFormCommand() {
        super(OpFormCmd, OpFormCmd_description);
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (sender.isOp()) {
            Player player = (Player) sender;
            mainForm(player);
        }
        return false;
    }


}
