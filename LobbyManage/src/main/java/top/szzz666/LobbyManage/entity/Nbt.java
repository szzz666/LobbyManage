package top.szzz666.LobbyManage.entity;

import java.util.List;

public class Nbt {
   private Tag tag;

   public Tag getTag() {
      return this.tag;
   }

   public void setTag(Tag tag) {
      this.tag = tag;
   }

   public static class Tag {
      private List<Enchantment> ench;
      private boolean minecraft_itemLock;
      private Display display;

      public List<Enchantment> getEnch() {
         return this.ench;
      }

      public void setEnch(List<Enchantment> ench) {
         this.ench = ench;
      }

      public boolean isMinecraft_itemLock() {
         return this.minecraft_itemLock;
      }

      public void setMinecraft_itemLock(boolean minecraft_itemLock) {
         this.minecraft_itemLock = minecraft_itemLock;
      }

      public Display getDisplay() {
         return this.display;
      }

      public void setDisplay(Display display) {
         this.display = display;
      }
   }

   public static class Display {
      private List<String> Lore;
      private String Name;

      public List<String> getLore() {
         return this.Lore;
      }

      public void setLore(List<String> lore) {
         this.Lore = lore;
      }

      public String getName() {
         return this.Name;
      }

      public void setName(String name) {
         this.Name = name;
      }
   }

   public static class Enchantment {
      private int id;
      private int lvl;

      public int getId() {
         return this.id;
      }

      public void setId(int id) {
         this.id = id;
      }

      public int getLvl() {
         return this.lvl;
      }

      public void setLvl(int lvl) {
         this.lvl = lvl;
      }
   }
}
