package top.szzz666.LobbyManage.config;

import cn.nukkit.utils.Config;
import top.szzz666.LobbyManage.LobbyManageMain;

public class LangConfig {
   public static String OpFormCmd_description;
   public static String ReLobbyCmd_description;
   public static String ReLobbyMessage;
   public static String mainForm_title;
   public static String mainForm_content;
   public static String mainForm_button1;
   public static String mainForm_button2;
   public static String mainForm_sendMessage;
   public static String Form0_title;
   public static String Form0_Element0;
   public static String Form0_Element1;
   public static String Form0_Element2;
   public static String Form0_Element3;
   public static String Form0_Element4;
   public static String Form0_Element5;
   public static String Form0_Element6;
   public static String Form0_Element20;
   public static String Form0_Element21;
   public static String Form0_Element22;
   public static String Form0_Element23;
   public static String Form0_Element7;
   public static String Form0_Element8;
   public static String Form0_Element9;
   public static String Form0_Element10;
   public static String Form0_Element11;
   public static String Form0_Element12;
   public static String Form0_Element13;
   public static String Form0_Element14;
   public static String Form0_Element15;
   public static String Form0_Element16;
   public static String Form0_Element17;
   public static String Form0_Element18;
   public static String Form0_Element19;
   public static String Form0_sendMessage;

   public static boolean loadLangConfig() {
      LobbyManageMain.plugin.saveResource("language/chs.yml");
      LobbyManageMain.plugin.saveResource("language/eng.yml");
      Config LangConfig = new Config(LobbyManageMain.ConfigPath + "/language/" + LmConfig.Language, 2);
      OpFormCmd_description = LangConfig.getString("OpFormCmd_description");
      ReLobbyCmd_description = LangConfig.getString("ReLobbyCmd_description");
      ReLobbyMessage = LangConfig.getString("ReLobbyMessage");
      mainForm_title = LangConfig.getString("mainForm_title");
      mainForm_content = LangConfig.getString("mainForm_content");
      mainForm_button1 = LangConfig.getString("mainForm_button1");
      mainForm_button2 = LangConfig.getString("mainForm_button2");
      mainForm_sendMessage = LangConfig.getString("mainForm_sendMessage");
      Form0_title = LangConfig.getString("Form0_title");
      Form0_Element0 = LangConfig.getString("Form0_Element0");
      Form0_Element1 = LangConfig.getString("Form0_Element1");
      Form0_Element2 = LangConfig.getString("Form0_Element2");
      Form0_Element3 = LangConfig.getString("Form0_Element3");
      Form0_Element4 = LangConfig.getString("Form0_Element4");
      Form0_Element5 = LangConfig.getString("Form0_Element5");
      Form0_Element6 = LangConfig.getString("Form0_Element6");
      Form0_Element20 = LangConfig.getString("Form0_Element20");
      Form0_Element21 = LangConfig.getString("Form0_Element21");
      Form0_Element22 = LangConfig.getString("Form0_Element22");
      Form0_Element7 = LangConfig.getString("Form0_Element7");
      Form0_Element8 = LangConfig.getString("Form0_Element8");
      Form0_Element9 = LangConfig.getString("Form0_Element9");
      Form0_Element10 = LangConfig.getString("Form0_Element10");
      Form0_Element23=LangConfig.getString("Form0_Element23");
      Form0_Element11 = LangConfig.getString("Form0_Element11");
      Form0_Element12 = LangConfig.getString("Form0_Element12");
      Form0_Element13 = LangConfig.getString("Form0_Element13");
      Form0_Element14 = LangConfig.getString("Form0_Element14");
      Form0_Element15 = LangConfig.getString("Form0_Element15");
      Form0_Element16 = LangConfig.getString("Form0_Element16");
      Form0_Element17 = LangConfig.getString("Form0_Element17");
      Form0_Element18 = LangConfig.getString("Form0_Element18");
      Form0_Element19 = LangConfig.getString("Form0_Element19");
      Form0_sendMessage = LangConfig.getString("Form0_sendMessage");
      LangConfig.save();
      return true;
   }
}
