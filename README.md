# 简单菜单配置说明

本 Wiki 提供了示例菜单配置选项的详细解释。

## 目录

- [简单菜单]
  - [简介](#简介)
  - [菜单属性](#菜单属性)
    - [权限](#permission)
    - [打开声音](#opensound)
    - [打开信息](#openmessage)
    - [打开指令](#opencommands)
    - [打开物品](#openitem)
    - [标题](#title)
    - [布局](#layout)
    - [按钮](#buttons)
  - [按钮属性](#按钮属性)
    - [显示物品](#icon)
    - [点击声音](#sound)
    - [查看权限](#viewpermission)
    - [需求](#require)
    - [动作](#action)
  - [变量](#变量)
  - [点击类型](#点击类型)
  - [范例](#范例)

## 简介

示例菜单是一个可配置的菜单系统，具有各种按钮，根据用户的交互执行不同的操作。它被设计为灵活和可定制，适用于不同的用例。

## 菜单属性

### permission
  
- **描述**：查看菜单所需的权限
- **格式**：字符串。
- **示例**：

  ```yaml
  permission: example.menu.view
  ```

### openSound
  
- **描述**：打开菜单时播放的声音效果。
- **格式**：
- `type`：声音效果的类型。
- `volume`：音量级别。
- `pitch`：音调级别。
- **示例**：

  ```yaml
  openSound:
    type: ENTITY_EXPERIENCE_ORB_PICKUP
    volume: 1
    pitch: 1
   ```

### openMessage
  
- **描述**：打开菜单时的提示。
- **格式**：字符串。
- **示例**：

  ```yaml
  openMessage: '&a%player_name%打开了菜单'
  ```

### openCommands
  
- **描述**：可以使用那些命令打开菜单。
- **格式**：字符串列表。
- **示例**：

    ```yaml
    openCommands:
      - 'menu'
      - 'cmd'
    ```
  
### openItem
  
- **描述**：打开菜单的物品。
- **格式**：
  - `type`：物品的类型。
  - `name`：物品的名称。
  - `lore`：物品的描述信息。
  - `amount`：数量。
- **示例**：

    ```yaml
    openItem:
      type: 'WATCH'
      name: '&6示例菜单'
      lore:
        - '&7右键点击打开'
    ```
  
### title
  
- **描述**：菜单顶部显示的标题
- **格式**：字符串。
- **示例**：

    ```yaml
    title: '&c&lExample Menu'
    ```
  
### layout
  
- **描述**：菜单网格的布局。
- **格式**：菜单行的字符串列表。（请注意这里的字符串只能单个字符，并且每行不能超过9个,总不能超过6行）
- **示例**：

    ```yaml
    layout:
      - 'AAAAAAAAA'
      - 'OPQRSTUVW'
      - 'AAAAAAAAA'
    ```
  
### buttons
  
- **描述**：菜单中显示的按钮。
- **格式**：键值对，按钮的名称必须包括layout中的字符，比如你要放到A位置，可以命名为AA1，或AA等等

## 按钮属性

### icon

- **描述**：按钮显示的图标。
- **属性**：
  - `type`：图标的类型。
  - `damage`（可选）：子ID。
  - `name`：图标的名称。
  - `lore`：图标的描述信息。
  - `amount`：数量。
  - `nbt`：NBT 数据。
- **示例**:

    ```yaml
        icon:
          type: DIAMOND
          name: '&b&lDiamond'
          lore:
            - '&7This is a diamond.'
          amount: 1
          nbt: '{display:{Name:"{\"text\":\"Diamond\"}"}}'
     ```
  
### sound

- **描述**：按钮被点击时播放的声音效果。
- **属性**：
  - `type`：声音效果的类型。
  - `volume`：音量级别。
  - `pitch`：音调级别。
- **示例**：
  
  ```yaml
    sound:
    type: ENTITY_EXPERIENCE_ORB_PICKUP
    volume: 1
    pitch: 1
  ```
  
### viewPermission

- **描述**：查看按钮所需的权限。
- **格式**：权限字符串。
- **示例**：`example.permission`
  
### require

- **描述**：执行按钮动作所需的条件。
- **格式**：

     ```yaml
     require:
       default: #按钮点击类型,请参考点击类型
         money: #需求类型,请参考需求类型
           value: 100 #需求值
           message: '&cYou don''t have enough money!' #需求不满足时的提示
     ```

#### 需求类型

- **money**
- **作用**：检查玩家是否有足够的金钱, 需要vault插件支持
- **示例**：

    ```yaml
    require:
      default:
        money:
          value: 100
          message: '&cYou don''t have enough money!'
    ```

- **item**
- **作用**：检查玩家背包是否有足够的物品
- **示例**：

    ```yaml
    require:
      default:
        item:
          value:
            type: DIAMOND
            amount: 1
          message: '&cYou don''t have enough diamonds!'
    ```

- **permission**
- **作用**：检查玩家是否有指定的权限
- **示例**：

    ```yaml
    require:
      default:
        permission:
          value: 'example.permission'
          message: '&cYou don''t have permission!'
     ```

- **points**
- **作用**：检查玩家是否有足够的点券, 需要PlayerPoints插件支持
- **示例**：

    ```yaml
    require:
      default:
        points:
          value: 100
          message: '&cYou don''t have enough points!'
    ```

- **stringEquals**
- **作用**：对比两个字符串是否相等
- **示例**：

    ```yaml
    require:
      default:
        stringEquals:
          value: 
            compare: 'lapin_boy' #需要对比的字符串
            input: '%player_name%' #输入的字符串
          message: '&cYou don''t input hello!'
    ```

- **stringEqualsIgnoreCase**
- **作用**：对比两个字符串是否相等(忽略大小写)
- **示例**：

    ```yaml
    require:
      default:
        stringEqualsIgnoreCase:
          value:
            compare: 'LAPIN_boy' #需要对比的字符串
            input: '%player_name%' #输入的字符串
          message: '&cYou don''t input hello!'
    ```

- **stringContains**
- **作用**：检查字符串是否包含指定的字符串
- **示例**：

    ```yaml
    require:
     default:
       stringContains:
         value:
           compare: 'hello' #需要包含的字符串
           input: '%catch_text%' #输入的字符串
         message: '&cYou don''t input hello!'
     ```

- **numberEquals**
- **作用**：对比两个数字
- **示例**：

    ```yaml
    require:
      default:
        numberEquals:
          value:
            type: equal #对比类型,可选: equal(等于),greater(大于),less(小于),greaterOrEqual(大于等于), lessOrEqual(小于等于),notEqual(不等于)
            compare: 100 #需要对比的数字
            input: 100 #输入的数字
          message: '&cYou don''t input 100!'
    ```
  
### action

- **描述**：按钮被点击时执行的操作。
- **格式**：

  ```yaml
  action:
    default: #按钮点击类型,请参考点击类型
      giveItem: #action类型,请参考动作类型
        type: DIAMOND
        amount: 1
  ```

#### 动作类型

- **giveItem**（给予物品）
- **作用**：给予玩家物品
- **示例**：

    ```yaml
    action:
      default:
        giveItem:
          type: DIAMOND
          amount: 1
    ```

- **rmoveItem**（移除物品）
- **作用**：移除玩家物品
- **示例**：

    ```yaml
    action:
      default:
        removeItem:
          type: DIAMOND
          amount: 1
    ```

- **giveMoney**（给予金钱）
- **作用**：给予玩家金钱, 需要vault插件支持
- **示例**：

    ```yaml
    action:
      default:
      giveMoney: 100
    ```

- **takeMoney**（扣除金钱）
- **作用**：扣除玩家金钱, 需要vault插件支持
- **示例**：

    ```yaml
    action:
      default:
      takeMoney: 100
    ```

- **consoleCommands**（控制台执行指令）
- **作用**：控制台执行指令
- **示例**：

    ```yaml
    action:
      default:
        consoleCommands:
          - 'give %player% diamond 1'
    ```

- **playerCommands**（玩家执行指令）
- **作用**：玩家执行指令
- **示例**：

    ```yaml
    action:
      default:
        playerCommands:
          - 'say Hello!'
    ```

- **givePoints**（给予点券）
- **作用**：给予玩家点券, 需要PlayerPoints插件支持
- **示例**：

    ```yaml
    action:
      default:
        givePoints: 100
    ```

- **takePoints**（扣除点券）
- **作用**：扣除玩家点券, 需要PlayerPoints插件支持
- **示例**：

    ```yaml
    action:
      default:
        takePoints: 100
    ```

- **message**（发送消息）
- **作用**：发送消息给玩家
- **示例**：

    ```yaml
    action:
      default:
        message: '&6Hello World!'
    ```

- **broadcast**（广播消息）
- **作用**：广播消息
- **示例**：

    ```yaml
    action:
      default:
        broadcast: '&6Hello World!'
    ```

- **openMenu**（打开菜单）
- **作用**：打开子菜单
- **示例**：

    ```yaml
    action:
      default:
        openMenu: 'example_menu 1' #打开子菜单，指令后面可以跟着一个参数支持papi变量,这个参数会传递给子菜单可选
    ```

- **closeMenu**（关闭菜单）
- **作用**：关闭菜单
- **示例**：

    ```yaml
    action:
      default:
        closeMenu: true
    ```

- **catchExecutor**（捕获输入）
- **作用**：捕获玩家输入的参数并执行动作
- **示例**：

    ```yaml
    action:
      default:
        catchExecutor:
          text: '&6请输入文字: '
          action: #执行的动作
            consoleCommands: #控制台命令
              - 'say %catch_text%'
    ```

- **delay**（延迟执行）
- **作用**：延迟执行动作
- **示例**：

    ```yaml
    action:
      default:
        delay:
          time: 5
          action: #执行的动作
            consoleCommands: #控制台命令
              - 'say 5秒后执行'
    ```

## 变量

- **%arg%**
  - **作用**：主菜单传递给子菜单的参数
  - **示例**：

  主菜单

    ```yaml
      action:
        default:
          openMenu: 'menu1 你好' 传递参数给子菜单
    ```

  子菜单

    ```yaml
    require:
      default:
        stringEquals:
          value: 
            compare: '你好' #需要对比的字符串
            input: '%arg%' #输入的字符串
          message: '&cYou don''t input 你好!'
    ```

- **%catchArg%**
  - **作用**：捕获玩家输入的参数
  - **示例**：

  主菜单

    ```yaml
    action:
      default:
        catchExecutor:
          text: '&6请输入文字: '
          action: #执行的动作
            message: '&6你输入的文字是: %catchArg%'
    ```

## 点击类型

- **default**（默认）
  - **作用**：任何点击都执行
  - **示例**：

      ```yaml
      action:
        default:
          giveItem:
            type: DIAMOND
            amount: 1
      ```

- **left**（左键点击）
  - **作用**：左键点击执行
  - **示例**：

      ```yaml
      action:
      left:
          giveItem:
            type: DIAMOND
            amount: 1
      ```

- **right**（右键点击）
  - **作用**：右键点击执行
  - **示例**：

      ```yaml
      action:
      right:
          giveItem:
            type: DIAMOND
            amount: 1
      ```

- **middle**（中键点击）
  - **作用**：中键点击执行
  - **示例**：

      ```yaml
      action:
      middle:
          giveItem:
            type: DIAMOND
            amount: 1
      ```

- **shift_left**（shift+左键点击）
  - **作用**：shift+左键点击执行
  - **示例**：

      ```yaml
      action:
      shift_left:
          giveItem:
            type: DIAMOND
            amount: 1
      ```

- **shift_right**（shift+右键点击）
  - **作用**：shift+右键点击执行
  - **示例**：

      ```yaml
      action:
      shift_right:
          giveItem:
            type: DIAMOND
            amount: 1
      ```

## 范例

<details>

```yaml
# 示例菜单
# 菜单的标题 (必须)
title: "&6示例菜单"
#菜单的布局 (必须)
layout:
  - 'AAAAAAAAA'
  - 'OPQRSTUVW'
  - 'AAAAAAAAA'
# 打开菜单的权限,如果没有这个权限则无法打开菜单 (非必须)
permission: 'example.permission.open'
# 打开菜单时的音效 ,这个节点可以不存在 (非必须)
openSound:
  type: 'BLOCK_ENDERCHEST_OPEN'
  volume: 1
  pitch: 1
openMessage: '&6打开了示例菜单' # 打开菜单时的提示 (非必须)
openCommands: # 可以使用那些命令打开菜单 (非必须)
  - 'menu'
  - 'cmd'
openItem: # 打开菜单的物品 (非必须)
  type: 'WATCH'
  name: '&6示例菜单'
  lore:
    - '&7右键点击打开'
# ===================================================================
# 按钮 (非必须,没有按钮则打开一个空菜单)
buttons:
  ################################################################################
  # 按钮的名称必须以layout中的字符开始，然后可以跟上任意字符
  # 比如你要放到A位置，可以命名为A或者A1，或AA等等
  # (如果相同位置有多个按钮后面的会覆盖前面的，会根据按钮权限选择)
  A:
    ICON: #显示的图标 (必须)
      type: "stained_glass_pane"
      damage: 15
      name: " "
    SOUND: #点击按钮时的音效 (非必须)
      type: 'UI_BUTTON_CLICK'
      volume: 1
      pitch: 1
  ################################################################################
  A1:
    ICON:
      type: "stained_glass_pane"
      damage: 11
      name: "&2V&4I&eP"
    SOUND:
      type: 'UI_BUTTON_CLICK'
      volume: 1
      pitch: 1
    VIEW_PERMISSION: 'example.permission.button' #查看菜单的权限,如果没有这个权限则不显示这个按钮 (非必须)
  ################################################################################
  O:
    ICON:
      type: 'ENDER_PEARL'
      name: '&6购买物品'
      amount: 16
      lore:
        - '&7示例菜单'
        - '&7购买一组末影珍珠'
        - '&7价格: 100'
        - '&7左键点击购买'
    REQUIRE: #执行action所需的条件  (非必须)
      left: #左键点击条件 (非必须)
        money: #需要的金钱 (必须,这个条件名字必须是已经注册的条件)
          value: 100 #需要的金钱(必须)
          message: '&cYou don''t have enough money' #条件不满足时的提示(非必须)
    ACTION: #执行的动作（非必须）
      left: #左键点击动作（非必须）
        giveItem: #给予物品（必须，这个动作名字必须是已经注册的动作）
          type: 'ENDER_PEARL'
          amount: 16
  ################################################################################
  P:
    ICON:
      type: 'DIAMOND'
      name: '&6出售物品'
      lore:
        - '&7示例菜单'
        - '&7出售一颗钻石'
        - '&7价格: 100'
        - '&7右键点击出售'
    REQUIRE:
      right:
        item:
          value:
            type: 'DIAMOND'
            amount: 1
          message: '&cYou don''t have enough diamond'
    ACTION:
      right:
        removeItem:
          type: 'DIAMOND'
          amount: 1
        giveMoney: 100
  ################################################################################
  Q:
    ICON:
      type: 'GOLD_INGOT'
      name: '&6控制台指令'
      lore:
        - '&7示例菜单'
        - '&7控制台执行指令'
        - '&7中键点击执行'
    ACTION:
      middle:
        consoleCommands:
          - 'say 你好世界'
  ################################################################################
  R:
    ICON:
      type: 'IRON_INGOT'
      name: '&6玩家指令'
      lore:
        - '&7示例菜单'
        - '&7玩家执行指令'
        - '&7shift+左键点击执行'
    ACTION:
      shift_left:
        playerCommands:
          - 'tell %player_name% &aHello World'
  ################################################################################
  S:
    ICON:
      type: 'EMERALD'
      name: '&6给予点券'
      lore:
        - '&7示例菜单'
        - '&7出售一颗绿宝石'
        - '&7价格: 100'
        - '&7shift+右键点击执行'
    REQUIRE:
      shift_right:
        item:
          value:
            type: 'EMERALD'
            amount: 1
          message: '&cYou don''t have enough emerald'
    ACTION:
      shift_right:
        givePoints: 100
  ################################################################################
  T:
    ICON:
      type: 'DIAMOND_BLOCK'
      name: '&6打开子菜单'
      lore:
        - '&7示例菜单'
        - '&7打开子菜单'
        - '&7左键打开'
    ACTION:
      left:
        closeMenu: true
        openMenu: 'exampleSubMenu 1'
  ################################################################################
  U:
    ICON:
      type: 'GOLD_BLOCK'
      name: '&6捕捉条件'
      lore:
        - '&7示例菜单'
        - '&7执行此按钮会让玩家输入参数来信执行action'
        - '&7任何点击都执行'
    ACTION:
      default:
        closeMenu: true
        catch:
          text: '&6请输入文字: '
          ACTION:
            consoleCommands:
              - 'say %catch_text%'
  ################################################################################
  V:
    ICON:
      type: 'IRON_BLOCK'
      name: '&6检查权限'
      lore:
        - '&7示例菜单'
        - '&7检查权限'
        - '&7有权限控制台执行指令'
        - '&7任何点击都执行'
    REQUIRE:
      default:
        permission:
          value: 'example.permission'
          message: '&cYou don''t have permission'
    ACTION:
      default:
        consoleCommands:
          - 'say 你好世界'
        closeMenu: true
  ################################################################################
  W:
    ICON:
      type: 'REDSTONE_BLOCK'
      name: '&6延迟执行'
      amount: 64
      lore:
        - '&7示例菜单'
        - '&7延迟执行action'
        - '&7任何点击都执行'
    ACTION:
      default:
        closeMenu: true
        delay:
          time: 5
          ACTION:
            consoleCommands:
              - 'say 你好世界'
  ################################################################################
```

</details>