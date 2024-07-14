package top.szzz666.LobbyManage.form;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementToggle;
import cn.nukkit.form.handler.FormResponseHandler;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowSimple;
import top.szzz666.LobbyManage.config.LangConfig;
import top.szzz666.LobbyManage.config.LmConfig;

import java.util.ArrayList;
import java.util.Arrays;

public class LmForm {
    public static void mainForm(Player player) {
        FormWindowSimple form = new FormWindowSimple(LangConfig.mainForm_title, LangConfig.mainForm_content);
        form.addButton(new ElementButton(LangConfig.mainForm_button1));
        form.addButton(new ElementButton(LangConfig.mainForm_button2));
        form.addHandler(FormResponseHandler.withoutPlayer((ignored) -> {
            if (!form.wasClosed()) {
                int buttonIndex = form.getResponse().getClickedButtonId();
                if (buttonIndex == 0) {
                    Form0(player);
                } else {
                    LmConfig.loadConfig();
                    LangConfig.loadLangConfig();
                    player.sendMessage(LangConfig.mainForm_sendMessage);
                }

            }
        }));
        player.showFormWindow(form);
    }

    public static void Form0(Player player) {
        FormWindowCustom form = new FormWindowCustom(LangConfig.Form0_title);
        form.addElement(new ElementInput(LangConfig.Form0_Element0, LmConfig.Language, LmConfig.Language));
        form.addElement(new ElementInput(LangConfig.Form0_Element1, LmConfig.LobbySpawn, LmConfig.LobbySpawn));
        form.addElement(new ElementInput(LangConfig.Form0_Element2, LmConfig.ReLobbyCmd, LmConfig.ReLobbyCmd));
        form.addElement(new ElementInput(LangConfig.Form0_Element3, LmConfig.OpFormCmd, LmConfig.OpFormCmd));
        form.addElement(new ElementToggle(LangConfig.Form0_Element4, LmConfig.ConstraintOp));
        form.addElement(new ElementToggle(LangConfig.Form0_Element5, LmConfig.VoidTp));
        form.addElement(new ElementToggle(LangConfig.Form0_Element6, LmConfig.JoinTp));
        form.addElement(new ElementInput(LangConfig.Form0_Element7, LmConfig.JoinMsg, LmConfig.JoinMsg));
        form.addElement(new ElementInput(LangConfig.Form0_Element8, LmConfig.JoinTitle, LmConfig.JoinTitle));
        form.addElement(new ElementInput(LangConfig.Form0_Element9, LmConfig.JoinConsoleCmd.toString().replaceAll("\\[", "").replace("]", ""), LmConfig.JoinConsoleCmd.toString().replaceAll("\\[", "").replace("]", "")));
        form.addElement(new ElementInput(LangConfig.Form0_Element10, LmConfig.JoinPlayerCmd.toString().replaceAll("\\[", "").replace("]", ""), LmConfig.JoinPlayerCmd.toString().replaceAll("\\[", "").replace("]", "")));
        form.addElement(new ElementInput(LangConfig.Form0_Element11, String.valueOf(LmConfig.FixedTime), String.valueOf(LmConfig.FixedTime)));
        form.addElement(new ElementToggle(LangConfig.Form0_Element12, LmConfig.DisableWeather));
        form.addElement(new ElementToggle(LangConfig.Form0_Element13, LmConfig.DisableHunger));
        form.addElement(new ElementToggle(LangConfig.Form0_Element14, LmConfig.DisableDamage));
        form.addElement(new ElementToggle(LangConfig.Form0_Element15, LmConfig.DisablePlace));
        form.addElement(new ElementToggle(LangConfig.Form0_Element16, LmConfig.DisableBreak));
        form.addElement(new ElementToggle(LangConfig.Form0_Element17, LmConfig.DisableInteract));
        form.addElement(new ElementToggle(LangConfig.Form0_Element18, LmConfig.DisableBlockUpdate));
        form.addElement(new ElementToggle(LangConfig.Form0_Element19, LmConfig.DisableItemDrop));
        form.addElement(new ElementToggle(LangConfig.Form0_Element20, LmConfig.QuitClear));
        form.addElement(new ElementInput(LangConfig.Form0_Element21, LmConfig.QuitMsg,LmConfig.QuitMsg));
        form.addElement(new ElementInput(LangConfig.Form0_Element22, LmConfig.QuitTitle, LmConfig.QuitTitle));
        form.addElement(new ElementToggle(LangConfig.Form0_Element23, LmConfig.TimeSync));
        form.addElement(new ElementInput(LangConfig.Form0_Element24, LmConfig.TimeZone,LmConfig.TimeZone));
        form.addElement(new ElementInput(LangConfig.Form0_Element25, LmConfig.Latitude,LmConfig.Latitude));
        form.addElement(new ElementInput(LangConfig.Form0_Element26, LmConfig.Longitude,LmConfig.Longitude));
        form.addElement(new ElementInput(LangConfig.Form0_Element27, String.valueOf(LmConfig.TaskDelay),String.valueOf(LmConfig.TaskDelay)));
        form.addHandler(FormResponseHandler.withoutPlayer((ignored) -> {
            if (!form.wasClosed()) {
                String Language = form.getResponse().getInputResponse(0);
                String LobbySpawn = form.getResponse().getInputResponse(1);
                String ReLobbyCmd = form.getResponse().getInputResponse(2);
                String OpFormCmd = form.getResponse().getInputResponse(3);
                boolean ConstraintOp = form.getResponse().getToggleResponse(4);
                boolean VoidTp = form.getResponse().getToggleResponse(5);
                boolean JoinTp = form.getResponse().getToggleResponse(6);
                String JoinMsg = form.getResponse().getInputResponse(7);
                String JoinTitle = form.getResponse().getInputResponse(8);
                ArrayList<String> JoinConsoleCmd = new ArrayList<>(Arrays.asList(form.getResponse().getInputResponse(9).split(", ")));
                ArrayList<String> JoinPlayerCmd = new ArrayList<>(Arrays.asList(form.getResponse().getInputResponse(10).split(", ")));
                String FixedTime = form.getResponse().getInputResponse(11);
                boolean DisableWeather = form.getResponse().getToggleResponse(12);
                boolean DisableHunger = form.getResponse().getToggleResponse(13);
                boolean DisableDamage = form.getResponse().getToggleResponse(14);
                boolean DisablePlace = form.getResponse().getToggleResponse(15);
                boolean DisableBreak = form.getResponse().getToggleResponse(16);
                boolean DisableInteract = form.getResponse().getToggleResponse(17);
                boolean DisableBlockUpdate = form.getResponse().getToggleResponse(18);
                boolean DisableItemDrop = form.getResponse().getToggleResponse(19);
                boolean QuitClear = form.getResponse().getToggleResponse(20);
                String QuitMsg = form.getResponse().getInputResponse(21);
                String QuitTitle = form.getResponse().getInputResponse(22);
                boolean TimeSync = form.getResponse().getToggleResponse(23);
                String TimeZone= form.getResponse().getInputResponse(24);
                String Latitude= form.getResponse().getInputResponse(25);
                String Longitude= form.getResponse().getInputResponse(26);
                String TaskDelay= form.getResponse().getInputResponse(27);
                LmConfig.Language = Language;
                LmConfig.LobbySpawn = LobbySpawn;
                LmConfig.ReLobbyCmd = ReLobbyCmd;
                LmConfig.OpFormCmd = OpFormCmd;
                LmConfig.ConstraintOp = ConstraintOp;
                LmConfig.VoidTp = VoidTp;
                LmConfig.JoinTp = JoinTp;
                LmConfig.QuitClear = QuitClear;
                LmConfig.JoinMsg = JoinMsg;
                LmConfig.JoinTitle = JoinTitle;
                LmConfig.QuitMsg = QuitMsg;
                LmConfig.QuitTitle = QuitTitle;
                LmConfig.JoinConsoleCmd = JoinConsoleCmd;
                LmConfig.JoinPlayerCmd = JoinPlayerCmd;
                LmConfig.TimeSync=TimeSync;
                LmConfig.TimeZone=TimeZone;
                LmConfig.Latitude=Latitude;
                LmConfig.Longitude=Longitude;
                LmConfig.FixedTime = Integer.parseInt(FixedTime);
                LmConfig.TaskDelay=Integer.parseInt(TaskDelay);
                LmConfig.DisableWeather = DisableWeather;
                LmConfig.DisableHunger = DisableHunger;
                LmConfig.DisableDamage = DisableDamage;
                LmConfig.DisablePlace = DisablePlace;
                LmConfig.DisableBreak = DisableBreak;
                LmConfig.DisableInteract = DisableInteract;
                LmConfig.DisableBlockUpdate = DisableBlockUpdate;
                LmConfig.DisableItemDrop = DisableItemDrop;
                LmConfig.saveConfig();
                LangConfig.loadLangConfig();
                player.sendMessage(LangConfig.Form0_sendMessage);
            }
        }));
        player.showFormWindow(form);
    }
}
