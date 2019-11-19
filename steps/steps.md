<p align="center">
  <img width="320" src="https://tronscan.org/static/media/tron-banner-1.e40b3379.png">
</p>

English | [简体中文](https://github.com/wubinTron/one-click-deployment/blob/master/steps/steps.zh.md)

### 1.准备工作

### 2.Home

Mainly show the content operation steps of the one-click deployment and related documentation  
（1）One-click deployment introduction
（2）Guide
（3）Documentation  
（4）Click the start button and deploy with one click<sup style="color:blue">[1]</sup>

### 3.Add node

Click on the Add node in the upper left corner to add a Witness node and a FullNode node to the one-click chain network.

Required fields include：

|               Required field                |                      Explanation                       |
| :-----------------------------------------: | :----------------------------------------------------: |
|    ID <sup style="color:blue">[2]</sup>     |                   Node unique number                   |
|                  user name                  |                      ssh username                      |
|                     IP                      |                        Node Ip                         |
|                    PORT                     |                       Node Port                        |
|                     URL                     |              Url （Required for Witness）              |
|                  voteCount                  | Number of corresponding votes （Required for Witness） |
| privateKey<sup style="color:blue">[3]</sup> | Save local 64-bit private key （Required for Witness） |

As shown：

![](https://github.com/wubinTron/one-click-deployment/blob/develop/steps/img/add_node.png)

(1) Click the save button<sup style="color:blue">[4]</sup> Save current node information
(2) Node list <sup style="color:blue">[5]</sup> The node list shows all the added nodes as shown below:
(3) Click Next to enter the configuration page

![](https://github.com/wubinTron/one-click-deployment/blob/master/steps/img/nodeList.png)

### 4.Configuration management

As shown：

![](https://github.com/wubinTron/one-click-deployment/blob/master/steps/img/setting.png)

(1)Creation Information Configuration

Genesis information configuration mainly includes asset configuration and witeness configuration
Asset configuration can add and modify current asset configuration information
Witeness configuration to view current witeness configuration information

The required fields for asset configuration include:

|              Required field               |              Explanation              |
| :---------------------------------------: | :-----------------------------------: |
|                accountName                |             account name              |
|                accountType                | account type： AssetIssue or Contract |
|                  address                  |                address                |
| balance <sup style="color:blue">[6]</sup> |                balance                |

Creation Information Configuration Click Next button to save the current configuration

(2)基础配置

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

(3)网络链接配置(http/rpc)

网络链接配置必填字段包括：

|       必填字段       |       解释        |
| :------------------: | :---------------: |
| maxHttpConnectNumber | 最大 http 连接数  |
|   httpFullNodePort   | httpFullNode 端口 |
|   httpSolidityPort   | httpFullNode 端口 |
|       rpcPort        |     rpc 端口      |
|   rpcSolidityPort    | rpcSolidity 端口  |

络链接配置点击下一步按钮保存当前配置

(4)数据库基础配置

数据库基础配置必填字段包括：

|       必填字段       |  解释  |
| :------------------: | :----: |
|     是否同步写入     | 是或否 |
| 是否打开 transaction | 是或否 |
|    选择数据库配置    | 是或否 |
| 是否打开 index 开关  | 是或否 |
| 是否需要更新 assets  | 是或否 |

数据库基础配置点击下一步按钮保存当前配置

(5)p2p 配置

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

(6)跨链配置

跨链配置必填字段包括：

|      必填字段      |     解释      |
| :----------------: | :-----------: |
|  enableCrossChain  | 是否允许跨链  |
| maxValidatorNumber |  最大验证数   |
| minValidatorNumber |  最小验证数   |
|   crossChainFee    | crossChainFee |

跨链配置点击下一步按钮保存当前跨链配置<sup style="color:blue">[7]</sup>

### 5.导入插件

如图：

![](https://github.com/wubinTron/one-click-deployment/blob/master/steps/img/plugins.png)

(1)共识模块

目前支持 DPOS

共识模块点击下一步按钮保存当前共识模块设置

(2)交易模块

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

交易模块点击下一步按钮保存当前共识模块设置

(3)数据库模块

支持 leveldb 和 rockdb

数据库模块点击下一步保存当前数据库模块<sup style="color:blue">[8]</sup>

### 6.节点部署

(1)批量部署

节点部署支持单个节点部署和多个节点批量部署
点击列表勾选按钮，勾选对应需要部署的节点，此时部署按钮为绿色
点击批量部署按钮，输入对应的 zip 包路径，如/home/user/java-tron/build/distributions/java-tron-1.0.0.zip
点击保存开始部署，在部署期间批量部署按钮为 loading 状态，此时可以点击查看日志查看部署详情，部署完成后批量部署按钮取消 loading 状态。

(2)查看日志

点击查看日志可以查看当前节点的日志 如果没有则会提示暂无节点日志
如果部署完成点击查看日志 日志会自动刷新 直至出现 deploy finish，则为部署完成

![](https://github.com/wubinTron/one-click-deployment/blob/master/steps/img/deploy.png)

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
