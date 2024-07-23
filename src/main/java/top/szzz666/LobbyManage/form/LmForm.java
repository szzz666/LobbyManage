package top.szzz666.LobbyManage.form;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementToggle;
import cn.nukkit.form.handler.FormResponseHandler;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowSimple;
import top.szzz666.LobbyManage.config.LmConfig;

import java.util.ArrayList;
import java.util.Arrays;

import static top.szzz666.LobbyManage.config.LangConfig.*;
import static top.szzz666.LobbyManage.config.LmConfig.*;

public class LmForm {
    public static void mainForm(Player player) {
        FormWindowSimple form = new FormWindowSimple(mainForm_title, mainForm_content);
        form.addButton(new ElementButton(mainForm_button1));
        form.addButton(new ElementButton(mainForm_button2));
        form.addHandler(FormResponseHandler.withoutPlayer((ignored) -> {
            if (!form.wasClosed()) {
                int buttonIndex = form.getResponse().getClickedButtonId();
                if (buttonIndex == 0) {
                    Form0(player);
                } else {
                    loadConfig();
                    loadLangConfig();
                    player.sendMessage(mainForm_sendMessage);
                }

            }
        }));
        player.showFormWindow(form);
    }

    public static void Form0(Player player) {
        FormWindowCustom form = new FormWindowCustom(Form0_title);
        form.addElement(new ElementInput(Form0_Element0, Language, Language));
        form.addElement(new ElementInput(Form0_Element1, LobbySpawn, LobbySpawn));
        form.addElement(new ElementInput(Form0_Element2, ReLobbyCmd, ReLobbyCmd));
        form.addElement(new ElementInput(Form0_Element3, OpFormCmd, OpFormCmd));
        form.addElement(new ElementToggle(Form0_Element4, ConstraintOp));
        form.addElement(new ElementToggle(Form0_Element5, VoidTp));
        form.addElement(new ElementToggle(Form0_Element6, JoinTp));
        form.addElement(new ElementInput(Form0_Element7, JoinMsg, JoinMsg));
        form.addElement(new ElementInput(Form0_Element8, JoinTitle, JoinTitle));
        form.addElement(new ElementInput(Form0_Element9, JoinConsoleCmd.toString().replaceAll("\\[", "").replace("]", ""), JoinConsoleCmd.toString().replaceAll("\\[", "").replace("]", "")));
        form.addElement(new ElementInput(Form0_Element10, JoinPlayerCmd.toString().replaceAll("\\[", "").replace("]", ""), JoinPlayerCmd.toString().replaceAll("\\[", "").replace("]", "")));
        form.addElement(new ElementInput(Form0_Element11, String.valueOf(FixedTime), String.valueOf(FixedTime)));
        form.addElement(new ElementToggle(Form0_Element12, DisableWeather));
        form.addElement(new ElementToggle(Form0_Element13, DisableHunger));
        form.addElement(new ElementToggle(Form0_Element14, DisableDamage));
        form.addElement(new ElementToggle(Form0_Element15, DisablePlace));
        form.addElement(new ElementToggle(Form0_Element16, DisableBreak));
        form.addElement(new ElementToggle(Form0_Element17, DisableInteract));
        form.addElement(new ElementToggle(Form0_Element18, DisableBlockUpdate));
        form.addElement(new ElementToggle(Form0_Element19, DisableItemDrop));
        form.addElement(new ElementToggle(Form0_Element20, QuitClear));
        form.addElement(new ElementInput(Form0_Element21, QuitMsg,QuitMsg));
        form.addElement(new ElementInput(Form0_Element22, QuitTitle, QuitTitle));
        form.addElement(new ElementToggle(Form0_Element23, TimeSync));
        form.addElement(new ElementInput(Form0_Element24, Latitude,Latitude));
        form.addElement(new ElementInput(Form0_Element25, Longitude,Longitude));
        form.addElement(new ElementInput(Form0_Element26, String.valueOf(TaskDelay),String.valueOf(TaskDelay)));
        form.addElement(new ElementToggle(Form0_Element27, DoubleJump));
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
                String Latitude= form.getResponse().getInputResponse(24);
                String Longitude= form.getResponse().getInputResponse(25);
                String TaskDelay= form.getResponse().getInputResponse(26);
                boolean DoubleJump= form.getResponse().getToggleResponse(27);
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
                LmConfig.DoubleJump=DoubleJump;
                LmConfig.DisableBlockUpdate = DisableBlockUpdate;
                LmConfig.DisableItemDrop = DisableItemDrop;
                saveConfig();
                loadLangConfig();
                player.sendMessage(Form0_sendMessage);
            }
        }));
        player.showFormWindow(form);
    }
}
