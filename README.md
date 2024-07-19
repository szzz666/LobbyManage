# LobbyManage
### nukkit上功能强大的主城管理插件
只需要一个插件就可以帮你管理主城，你可以使用`/lm`命令直接在游戏内配置好插件
#### 配置文件内容
config.yml
```yaml
# 设置插件语言
Language: "chs.yml"

# 设置主城出生点。
LobbySpawn: "0,100,0&world"

# 回城命令
ReLobbyCmd: "hub"

# 打开主城管理菜单
OpFormCmd: "lm"

# 是否限制Op破坏放置方块和交互以及丢弃物品
ConstraintOp: false

# 开启虚空传送。
VoidTp: true

# 进服回城
JoinTp: true

# 退出后清除玩家数据(推荐小游戏服务器开启)
QuitClear: true

# 设置玩家加入游戏时显示的消息。(为空不启用)
JoinMsg: "欢迎 %player% 来到 Server"
# 设置玩家退出游戏时显示的消息。(为空不启用)
QuitMsg: "%player% 退出了 Server"
# 设置玩家加入游戏时显示的标题。(为空不启用)
JoinTitle: "欢迎 %player% 来到 Server&副标题&20,20,20"
# 设置玩家退出游戏时显示的标题。(为空不启用)
QuitTitle: "%player% 退出了 Server&副标题&20,20,20"

# 定义玩家加入游戏时在控制台执行的命令。(为空不启用)
JoinConsoleCmd:
  - "clean %player%"
  - "say %player%"

# 定义玩家加入游戏时对玩家执行的命令。(为空不启用)
JoinPlayerCmd:
  - "op#say hello"
  - "say hello2"

#现实时间
RealTime:
  # 同步现实时间
  TimeSync: true
  # 时间偏移
  TimeOffset: "+8"

# 固定服务器时间(为-1不启用)
# 如果启用同步现实时间,会自动关闭此功能
FixedTime: 6000

# 时间设置任务的执行间隔
TaskDelay: 1200

# 效果方块 [EffectId:Tick:Level]
EffectBlock:
  "222": "1:60:2"

# 禁止天气变化。
DisableWeather: true

# 禁止饥饿度减少。

DisableHunger: true

# 禁止玩家受到伤害。
DisableDamage: true

# 禁止玩家放置方块。
DisablePlace: true

# 禁止玩家破坏方块。
DisableBreak: true

# 禁止玩家交互。
DisableInteract: true

# 双跳。
DoubleJump: true

# 禁止方块更新。
DisableBlockUpdate: true

# 禁止丢物品。
DisableItemDrop: true


# "Id:Data:数量:物品栏位置:name=自定义物品名字": "命令"
# 或者 "Id:Data:数量:物品栏位置:nbt": "命令"
# 为空不启用
ItemCmd:
  #  "1:0:1:2:name=点击回城": "hub"
  "345:0:1:2:nbt=nbt1": [ "form show 小游戏" ]
  "397:3:1:3:nbt=nbt2": [ "team" ]
  "2:0:1:4:nbt=nbt3": [ "form show 星空生存" ]
  "355:0:1:5:nbt=nbt4": [ "op#form show 返回出生点" ]
  "399:0:1:6:nbt=nbt5": [ "console#givemoney %player% 6" ]


```
nbts.json
```json
{

  "nbt1": {
    "tag": {
      "ench": [
        {
          "id": 0,
          "lvl": 1
        }
      ],
      "minecraft_itemLock": true,
      "display": {
        "Lore": [
          "§u§l左键点击或点击地面打开小游戏大全菜单~"
        ],
        "Name": "§e§l小游戏大全"
      }
    }
  },

  "nbt2": {
    "tag": {
      "ench": [
        {
          "id": 0,
          "lvl": 1
        }
      ],
      "minecraft_itemLock": true,
      "display": {
        "Lore": [
          "§u§l左键点击或点击地面打开组队菜单~"
        ],
        "Name": "§e§l组队"
      }
    }
  },

  "nbt3": {
    "tag": {
      "ench": [
        {
          "id": 0,
          "lvl": 1
        }
      ],
      "minecraft_itemLock": true,
      "display": {
        "Lore": [
          "§u§l左键点击或点击地面去往星空生存~"
        ],
        "Name": "§e§l星空生存"
      }
    }
  },

  "nbt4": {
    "tag": {
      "ench": [
        {
          "id": 0,
          "lvl": 1
        }
      ],
      "minecraft_itemLock": true,
      "display": {
        "Lore": [
          "§u§l左键点击或点击地面返回出生点~"
        ],
        "Name": "§e§l返回出生点"
      }
    }
  },

  "nbt5": {
    "tag": {
      "ench": [
        {
          "id": 0,
          "lvl": 1
        }
      ],
      "minecraft_itemLock": true,
      "display": {
        "Lore": [
          "§u§l左键点击或点击地面打开其他菜单~"
        ],
        "Name": "§e§l其他"
      }
    }
  }

}
```
下载：https://github.com/szzz666/LobbyManage/releases
#### 反馈渠道
如果遇到任何bug，请加入Q群进行反馈：
- **Q群号码**: 894279534
