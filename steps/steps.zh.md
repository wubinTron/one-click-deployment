<p align="center">
  <img width="320" src="https://tronscan.org/static/media/tron-banner-1.e40b3379.png">
</p>

简体中文 | [English](./steps/steps.md)

### 1.准备工作

需要提前实现[免密登陆](https://www.jianshu.com/p/13919b5ba8a2)

部署节点需要 [java-tron-1.0.0.zip 包编译](https://github.com/tronprotocol/java-tron/blob/develop/build.md)

### 2.一键发链首页

主要展示一键发链的内容操作步骤及相关文档说明  
（1）一键发链介绍  
（2）新手指南  
（3）帮助文档  
（4）点击开始按钮，一键部署<sup style="color:blue">[1]</sup>

### 3.添加节点

点击选择左上角的添加节点，给一键发链网络添加 Witness 节点和 FullNode 节点,[如图所示](./steps/img/add_node.png)

必填字段包括

|                  必填字段                   |                  解释                  |
| :-----------------------------------------: | :------------------------------------: |
|    ID <sup style="color:blue">[2]</sup>     |              节点唯一编号              |
|                  用户名称                   |               ssh 用户名               |
|                     IP                      |                节点 Ip                 |
|                    PORT                     |               节点 端口                |
|                     URL                     |        Url （为 Witness 必填）         |
|                  voteCount                  |    对应投票数量 （为 Witness 必填）    |
| privateKey<sup style="color:blue">[3]</sup> | 保存本地 64 位私钥 （为 Witness 必填） |

(1) 点击保存按钮<sup style="color:blue">[4]</sup> 保存当前节点信息  
(2) 节点列表[如图所示](./steps/img/nodeList.png) <sup style="color:blue">[5]</sup> 展示所有添加的节点  
(3) 点击下一步将会进入配置页面

### 4.配置管理

配置管理页面[如图所示](./steps/img/setting.png), 其主要包括: 创世信息配置, 基础配置, 网络链接配置, 数据库基础配置, p2p 配置和跨链配置

#### 创世信息配置

创世信息配置主要包括 asset 配置和 witeness 配置  
asset 配置 可以新增和修改当前 asset 配置信息  
witeness 配置 可以查看当前 witeness 配置信息

asset 配置必填字段包括：

|                 必填字段                  |               解释                |
| :---------------------------------------: | :-------------------------------: |
|                accountName                |              账户名               |
|                accountType                | 账户类型： AssetIssue 或 Contract |
|                  address                  |               地址                |
| balance <sup style="color:blue">[6]</sup> |               余额                |

创世信息配置点击下一步按钮保存当前配置

#### 基础配置

基础配置必填字段包括：

| 必填字段  |  解释  |
| :-------: | :----: |
|  chainId  | 链 ID  |
| chainName | 链名称 |

高级配置字段包括：

|       高级配置字段       |         解释         |
| :----------------------: | :------------------: |
| MaintenanceTimeInterval  |     维护时间间隔     |
| blockProposalExpireTime  |   区块提案到期时间   |
| nodeBlockProducedTimeOut | 节点区块产生超时时间 |
| nodeMinParticipationRate |    节点最小参与率    |

基础配置点击下一步按钮保存当前配置

#### 网络链接配置(http/rpc)

网络链接配置必填字段包括：

|       必填字段       |       解释        |
| :------------------: | :---------------: |
| maxHttpConnectNumber | 最大 http 连接数  |
|   httpFullNodePort   | httpFullNode 端口 |
|   httpSolidityPort   | httpFullNode 端口 |
|       rpcPort        |     rpc 端口      |
|   rpcSolidityPort    | rpcSolidity 端口  |

网络链接配置点击下一步按钮保存当前配置

#### 数据库基础配置

数据库基础配置必填字段包括：

|       必填字段       |  解释  |
| :------------------: | :----: |
|     是否同步写入     | 是或否 |
| 是否打开 transaction | 是或否 |
|    选择数据库配置    | 是或否 |
| 是否打开 index 开关  | 是或否 |
| 是否需要更新 assets  | 是或否 |

数据库基础配置点击下一步按钮保存当前配置

#### p2p 配置

p2p 配置必填字段包括：

|  必填字段  |    解释     |
| :--------: | :---------: |
| p2pVersion |  p2p 版本   |
| listenPort | listen 端口 |
|  seedNode  |  seedNode   |

高级配置字段包括：

|       高级配置字段       |        解释         |
| :----------------------: | :-----------------: |
|      maxActiveNodes      |     最大链接数      |
| maxActiveNodesWithSameIp | 同一 ip 最大连接数  |
|   activeConnectFactor    | activeConnectFactor |
|      connectFactor       |    connectFactor    |

p2p 配置点击下一步按钮保存当前配置

#### 跨链配置

跨链配置必填字段包括：

|      必填字段      |     解释      |
| :----------------: | :-----------: |
|  enableCrossChain  | 是否允许跨链  |
| maxValidatorNumber |  最大验证数   |
| minValidatorNumber |  最小验证数   |
|   crossChainFee    | crossChainFee |

跨链配置点击下一步按钮保存当前跨链配置<sup style="color:blue">[7]</sup>

### 5.导入插件

导入插件页面[如图所示](./steps/img/plugins.png), 其主要包括: 共识模块, 交易模块和数据库模块

#### 共识模块

目前支持 DPOS

共识模块点击下一步按钮保存当前共识模块设置

#### 交易模块

目前支持的模块如下，也可以自定义模块  
AccountPermissionUpdate  
AssetIssue  
ClearABIContract  
CreateAccount  
ExchangeCreate  
ExchangeInject  
ExchangeTransaction  
ExchangeWithdraw  
FreezeBalance  
ParticipateAssetIssue  
ProposalApprove  
ProposalCreate  
ProposalDelete  
SetAccountId  
ShieldedTransfer  
Transfer  
TransferAsset  
UnfreezeAsset  
UnfreezeBalance  
UpdateAccount  
UpdateAsset  
UpdateBrokerage  
UpdateEnergyLimit  
UpdateSettingContract  
VoteWitness  
WithdrawBalance  
WitnessCreate  
WitnessUpdate  
还可以选择自定义上传自定义交易  
交易模块点击下一步按钮保存当前交易模块设置

#### 数据库模块

支持 leveldb 和 rocksdb

数据库模块点击下一步保存当前数据库模块<sup style="color:blue">[8]</sup>

### 6.节点部署

#### 批量部署

[节点部署](./steps/img/deploy.png)支持单个节点部署和多个节点批量部署  
点击列表勾选按钮，勾选对应需要部署的节点，此时部署按钮为绿色  
点击批量部署按钮，输入对应的 zip 包路径，如/home/user/java-tron/build/distributions/java-tron-1.0.0.zip  
点击保存开始部署，在部署期间批量部署按钮为 loading 状态，此时可以点击查看日志查看部署详情，部署完成后批量部署按钮结束 loading 状态。

#### 查看日志

点击查看日志可以查看当前节点的日志。  
如果没有则会提示暂无节点日志, 如果部署完成点击查看日志, 日志信息将一直保存直到下次部署后覆盖。  
日志会自动刷新,直至出现 deploy finish，则为部署完成。

## 脚注

[1] 注：点击开始一键部署按钮，获取节点列表菜单权限，每次点击会重置菜单权限

[2] 注：id 和 ip 地址均不能相同

[3] 注：每次添加的节点 privateKey 不能重复

[4] 注：点击节点列表下一步按钮重置配置管理菜单权限，只展示配置管理菜单权限

[5] 注：当节点列表为空时候默认下一步按钮不可以点击，当节点列表都删除除时没有下一步菜单权限，节点列表最多支持 20 个节点

[6] 注：balance 范围在-9223372036854775808 和 9223372036854775807 之间，为保证部署成功，请按照取值内范围填写

[7] 注：点击配置管理最后一步骤跨链配置的下一步时，获取导入插件菜单权限，每次点击重置菜单权限

[8] 注：点击导入插件最后一步数据库模块的下一步时点击获取节点部署菜单权限，每次点击重置菜单权限

## License

Copyright (c) 2019 Tron
