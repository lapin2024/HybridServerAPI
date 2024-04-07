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
| %hsa_pokemoney%                      | 玩家的宝可梦币                                          |
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

```
  name: 宝可梦名称
  level,type, form, growth,nature, mintnature, ability,gender(等级,类型,形态, 体型,性格, 薄荷性格,特性,性别)
  evhp,evattack,evdefence,evsattack,evsdefence,evspeed,evtotal,(努力值)
  ivhp,ivattack,ivdefence, ivsattack,ivsdefence,ivspeed,ivtotal,(个体值)
  hthp,htattack,htdefence, htsattack,htsdefence,htspeed,httotal,(王冠个体值)
  hp,attack,defence,sattack,sdefence, speed,total(种族值)
  hp,nattack,ndefence,nsattack,nsdefence,nspeed,ntotal(当前属性值)
  friendship(亲密度),helditem(携带道具),palette(精灵皮肤)

```