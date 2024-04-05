# API使用指南

## Minecraft文字颜色

你可以使用HexUtils类来将16进制颜色代码转换为Minecraft颜色代码。或将&符号转换为Minecraft颜色代码。

```java
public void test() {
    HexUtils.colorify("&#00FFE0我&#4EAAEA的&#9D55F5世&#EB00FF界"); //Minecraft1.16+
    HexUtils.legacyColorify("&3我&2的&4世&6界"); //Minecraft1.16-
}
```

本api还支持渐变文字与彩虹文字
使用方法

```java
public void test() {
    HexUtils.colorify("<r>我是彩虹"); //<r>标签可以填入过度速度比如<r:0.5>
    HexUtils.legacyColorify("<g:#000000:#FFFFFF>渐变文字");
}
```

## 命令注册

本api提供一个无需在plugin.yml中注册的命令注册器,
比如注册一个test指令

```java
public void test() {
    CommandProcessor.registerCommand(this)
            .setName("test") //指令名
            .setDescription("Test command") //指令描述
            .setPermission("test.permission") //指令权限 
            .setPermissionMessage(HexUtils.legacyColorify("&cYou do not have permission to use this command")) //无权限提示
            .setUsage("/test") //当执行器返回false时显示的用法
            .setAliases(Collections.singletonList("t")) //指令别名
            .setExecutor((sender, command, label, args) -> { //指令执行器
                sender.sendMessage(HexUtils.legacyColorify("&aTest command executed!"));
                return true;
            })
            .build();
}
```

## 事件订阅器

HybridAPI为基于Bukkit的提供了一个动态事件订阅器，无需实现Listener接口
您可以使用一行简单的代码来订阅事件

```java
public void test() {
    BukkitEvents.subscribe(this, PlayerJoinEvent.class, EventPriority.MONITOR).handler(event -> event.getPlayer().sendMessage(HexUtils.legacyColorify("&a还原来到本服务器!")));
}
```

同样本api也为混合端Forge事件提供了一个动态事件订阅器，无需关心使用的是什么服务端,它可以支持所有基于forge + bukkit实现的服务端

```java
public void test() {
    ForgeEvents.subscribe(Pixelmon.EVENT_BUS, LegendarySpawnEvent.DoSpawn.class, EventPriority.HIGH).handler(event -> System.out.println("刷新了" + event.getLegendary().getLocalizedName()));
}
```

事件订阅器还支持条件过滤
比如这样

```java
public void test() {
    BukkitEvents.subscribe(plugin, PlayerJoinEvent.class)
            .filter(event -> event.getPlayer().getName().equals("yyeerai"))
            .handler(event -> event.getPlayer().sendMessage("你好"));
}
```

forge事件还可以使用注解方式监听

```java
@ForgeEventListener(value = LegendarySpawnEvent.DoSpawn.class,priority = EventPriority.HIGHEST)
public void test(LegendarySpawnEvent.DoSpawn event) {
    plugin.getLogger().info("刷新的神兽是: " + event.getLegendary().getLocalizedName());
}
```

注解方式需要注册,请在插件的onEnable方法中调用

```java
/**
 * 注册forge事件
 * 第一个参数为带有@ForgeEventListener注解的方法所在的类实例
 * 第二个参数为事件总线(宝可梦就使用Pixelmon.EVENT_BUS,forge自带的事件就使用MinecraftForge.EVENT_BUS)
 */
public void onEnable() {
    ForgeEventListenerProcessor.register(this,Pixelmon.EVENT_BUS);
}
```

### 随机选择器

随机选择器它可以帮助你快速实现一些随机功能,并且支持权重功能

一个简单的随机选择器使用方法

```java
public void test(){
    RandomSelector<String> randomSelector = RandomSelector.uniform(Arrays.asList("a", "b", "c"));
    String pick = randomSelector.pick();
    System.out.println(pick);
}
```

带有权重的随机选择器使用方法,你需要在你的对象里面实现Weighted接口

实现接口的类:

```java
public class RandomString implements Weighted {

    private final String value;
    private final double weight;
    
    public RandomString(String value, double weight) {
        this.value = value;
        this.weight = weight;
    }

    @Override
    public double getWeight() {
        return 0;
    }
}
```

使用方法:

```java
public void test(){
        List<RandomString> randomStrings = new ArrayList<>();
        RandomSelector<RandomString> randomSelector = RandomSelector.weighted(randomStrings);
        RandomString pick = randomSelector.pick();
        System.out.println(pick.getValue() + " " + pick.getWeight());
    }
```

### 菜单构建器

菜单构建器它可以帮助你快速实现构建一个菜单


一个简单的菜单构建器使用方法

```java
public void createMenu(Player player) {
        //菜单必须提供标题,大小和按钮
        String title = "测试菜单";
        int size = 9;
        List<Button> buttons = new ArrayList<>();
        //创建一个Button,Button必须提供图标,位置
        Button button = new ButtonBuilder()
                .setSlot(0)
                .setIcon(new ItemStack(Material.DIAMOND))
                .build();
        buttons.add(button);

        Menu menu = new MenuBuilder(title, size, buttons).build();
        menu.open(player);
    }
```

菜单还有更多特性,比如: 需求条件,点击执行器等,请查看源码

### 配置文件注册器

本api封装了开源的Boostedyaml,并对Bukkit的物品,坐标等实现了序列化和反序列化,使用方法如下:

```java
public void test() {
    // 创建配置文件管理器,如果文件不存在则新建
    ConfigManager configManager = RegisterConfig.registerConfig(plugin, "test.yml",true);
    // 获取配置文件
    YamlDocument config = configManager.getConfig();
    //写入数据
    config.set("test", "test");
    //保存数据
    configManager.saveConfig();
}
```

### Pixelmon mod API

本api封装了Pixelmon mod的一些常用方法,使用方法如下:

```java
public void test(Player player) {
    //从字符串创建一个pokemon 对象
    Pokemon pokemon = pokemonApi.getPokemon("Mew");
    //获得Pokemon照片
    ItemStack pokemonPhoto = pokemonApi.getPokemonPhoto(pokemon);
    //获得玩家的宝可梦队伍
    PlayerPartyStorage playerPartyStorage = pokemonApi.getPlayerPartyStorage(player);
    //获得玩家的宝可梦PC
    PCStorage playerPCStorage = pokemonApi.getPCStorage(player);
    //更多API请查看PokemonApi类
}
```

### 基础API

本api提供很多Bukkit对象和forge对象互相转换或互相操作的方法,一些使用方法如下:

```java
public void test(Player player) {
    // 将bukkit的物品转换为minecraft的物品
    net.minecraft.item.ItemStack minecraftItemStack = BaseApi.getMinecraftItemStack(new ItemStack(Material.DIAMOND));
    // 将minecraft的物品转换为bukkit的物品
    ItemStack bukkitItemStack = BaseApi.getBukkitItemStack(minecraftItemStack);
    // 将Bukkit的玩家转换为minecraft的玩家
    EntityPlayer minecraftPlayer = BaseApi.getMinecraftPlayer(player);
    // 将Bukkit的实体转换为minecraft的实体
    Entity minecraftEntity = BaseApi.getMinecraftEntity(player);
    // 将minecraft的实体转换为bukkit的实体
    org.bukkit.entity.Entity bukkitEntity = BaseApi.getBukkitEntity(minecraftEntity);
    // 更多API请查看BaseApi类
}
```

### 宝可梦模组Placeholders变量

本API提供了Pokemon mod的一些,这些变量可以被PlaceholderAPI解析:

#### 变量

##### 属性列表

| 属性                                   | 说明                                               |
|--------------------------------------|--------------------------------------------------|
| %hsa_wins%                           | 玩家对战胜场                                           |
| %hsa_losses%                         | 玩家对战败场                                           |
| %hsa_totalbattle%                    | 玩家对战总计场次                                         |
| %hsa_totalexp%                       | 玩家总计获得经验                                         |
| %hsa_totalkills%                     | 玩家总计击杀                                           |
| %hsa_currentexp%                     | 玩家当前经验                                           |
| %hsa_currentkills%                   | 玩家当前击杀                                           |
| %hsa_totalbred%                      | 玩家总计繁殖                                           |
| %hsa_totalhatched%                   | 玩家总计孵蛋                                           |
| %hsa_totalevolved%                   | 玩家总计进化                                           |
| %hsa_seen%                           | 玩家看见的宝可梦数量                                       |
| %hsa_caught%                         | 返回玩家抓的宝可梦总数                                      |
| %hsa_size%                           | 图鉴大小                                             |
| %hsa_percent%                        | 图鉴收集完成度                                          |
| %hsa_percent_format%                 | 图鉴收集完成度格式化                                       |
| %hsa_haspokemon_[pokename]%          | 在队伍中是否有指定name的宝可梦,有返回位置没有返回false                 |
| %hsa_original_haspokemon_[pokename]% | 在队伍中或pc中是否有原始训练师是当前玩家的指定name的宝可梦,有返回位置,没有返回false |
| %hsa_name_[slot]%                    | 指定队伍位置的宝可梦种类                                     |
| %hsa_localname_[slot]%               | 指定队伍位置的宝可梦本地名称                                   |
| %hsa_nickname_[slot]%                | 指定队伍位置的宝可梦昵称(没有昵称返回本地名称)                         |
| %hsa_form_[slot]%                    | 指定队伍位置的宝可梦形态                                     |
| %hsa_original_[slot]%                | 队伍指定位置的宝可梦的原始训练师                                 |
| %hsa_egggroup_[slot]%                | 队伍指定位置的宝可梦的蛋组                                    |
| %hsa_isegg_[slot]%                   | 队伍指定位置的宝可梦是否是蛋                                   |
| %hsa_eggcycles_[slot]%               | 队伍指定位置的蛋的剩余孵化周期                                  |
| %hsa_shiny_[slot]%                   | 指定队伍位置宝可梦是否是闪光                                   |
| %hsa_pokeball_[slot]%                | 指定队伍位置宝可梦的精灵球                                    |
| %hsa_cantrade_[slot]%                | 指定队伍位置宝可梦能否用于交易                                  |
| %hsa_canbreed_[slot]%                | 指定队伍位置宝可梦能否繁殖                                    |
| %hsa_customtexture_[slot]%           | 指定队伍位置宝可梦的自定义材质                                  |
| %hsa_pokedex_[slot]%                 | 指定队伍位置宝可梦的图鉴值                                    |
| %hsa_islegendary_[slot]%             | 指定位置是否是神兽                                        |
| %hsa_isultrabeast_[slot]%            | 指定位置是否是异兽                                        |
| %hsa_move_[slot]_[index]%            | 指定队伍宝可梦指定栏位的技能名称 index范围[1,4],没有技能返回空            |
| %hsa_partysize%                      | 队伍中宝可梦的数量                                        |
| %hsa_partyegg%                       | 队伍中宝可梦的蛋数量                                       |
| %hsa_info_[slot]_[信息名称]%             | 指定队伍位置的宝可梦的指定信息                                  |

## 信息名称列表

```yaml
  # name: 宝可梦名称
  # level,type,growth,nature, mintnature, ability,gender(等级,类型,体型,性格, 薄荷性格,特性,性别)
  # evhp,evattack,evdefence,evsattack,evsdefence,evspeed,evtotal,(努力值)
  # ivhp,ivattack,ivdefence, ivsattack,ivsdefence,ivspeed,ivtotal,(个体值)
  # hthp,htattack,htdefence, htsattack,htsdefence,htspeed,httotal,(王冠个体值)
  # hp,attack,defence,sattack,sdefence, speed,total(种族值)
  # hp,nattack,ndefence,nsattack,nsdefence,nspeed,ntotal(当前属性值)
  # friendship(亲密度),helditem(携带道具),palette(精灵皮肤)

```

### 数据库连接池

### json解析器

### 网络请求工具

### sponge Configurate