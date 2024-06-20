package top.szzz666.LobbyManage.entity;

import java.util.List;

public class Nbt {
    private Tag tag;

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public static class Tag {
        private List<Enchantment> ench;


        private boolean minecraft_itemLock;
        private Display display;

        public List<Enchantment> getEnch() {
            return ench;
        }

        public void setEnch(List<Enchantment> ench) {
            this.ench = ench;
        }

        public boolean isMinecraft_itemLock() {
            return minecraft_itemLock;
        }

        public void setMinecraft_itemLock(boolean minecraft_itemLock) {
            this.minecraft_itemLock = minecraft_itemLock;
        }


        public Display getDisplay() {
            return display;
        }

        public void setDisplay(Display display) {
            this.display = display;
        }
    }

    public static class Enchantment {
        private int id;
        private int lvl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLvl() {
            return lvl;
        }

        public void setLvl(int lvl) {
            this.lvl = lvl;
        }
    }

    public static class Display {
        private List<String> Lore;
        private String Name;

        public List<String> getLore() {
            return Lore;
        }

        public void setLore(List<String> lore) {
            Lore = lore;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }
    }

}
