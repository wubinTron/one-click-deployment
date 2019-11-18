<p align="center">
  <img width="320" src="https://tronscan.org/static/media/tron-banner-1.e40b3379.png">
</p>

简体中文 | [English](./README.md)

### 1.一键发链首页

主要展示一键发链的内容操作步骤及相关文档说明
（1）一键发链
（2）新手必看
（3）帮助文档
（4）点击开始一键部署按钮，展示添加节点菜单权限，开始一键部署
（注：每次点击一键部署按钮重置节点菜单权限，只展示节点菜单权限）

### 2.添加节点

(1) 点击选择左上角的添加节点，给一键发链网络添加 Witness 节点和 FullNode 节点
(注:id 和 ip 地址均不能相同)

必填字段包括：

|  必填字段  |               解释                |
| :--------: | :-------------------------------: |
|     ID     |            ssh 用户名             |
|     IP     |            FullNode Ip            |
|    PORT    |           FullNode 端口           |
|    URL     |    Witness Url （为 SR 必填）     |
| voteCount  |    对应投票数量 （为 SR 必填）    |
| privateKey | 保存本地 64 位私钥 （为 SR 必填） |

如下图所示：
![](https://github.com/wubinTron/one-click-deployment/blob/develop/steps/img/add_node.png)

(2）点击保存 当前节点列表展示当前添加的节点信息 下一步按钮可以点击
（注：每次点击下一步按钮重置配置管理菜单权限，只展示配置管理菜单权限）
(3) 节点列表  
 节点列表展示所有添加的节点
当节点列表为空时候默认下一步按钮不可以点击
当节点列表都删除除时没有下一步菜单权限
节点列表最多支持 20 个节点

### 3.配置管理

(1)创世信息配置

创世信息配置主要包括 asset 配置和 witeness 配置
asset 配置 可以新增和修改当前 asset 配置信息
witeness 配置 可以查看当前 witeness 配置信息

asset 配置必填字段包括：

|  必填字段   |               解释                |
| :---------: | :-------------------------------: |
| accountName |              账户名               |
| accountType | 账户类型： AssetIssue 或 Contract |
|   address   |               地址                |
|   balance   |               余额                |

(2)基础配置

基础配置必填字段包括：

| 必填字段  |  解释  |
| :-------: | :----: |
|  chainId  | 链 ID  |
| chainName | 链名称 |

高级配置字段包括：
| 高级配置字段 | 解释 |
| :----------------------: | :--------------: |
| MaintenanceTimeInterval | 维护时间间隔 |
| blockProposalExpireTime | 区块提案到期时间 |
| nodeBlockProducedTimeOut | 节点区块产生超时时间 |
| nodeMinParticipationRate | 节点最小参与率 |

(3)网络链接配置(http/rpc)

网络链接配置必填字段包括：

|       必填字段       |       解释        |
| :------------------: | :---------------: |
| maxHttpConnectNumber | 最大 http 连接数  |
|   httpFullNodePort   | httpFullNode 端口 |
|   httpSolidityPort   | httpFullNode 端口 |
|       rpcPort        |     rpc 端口      |
|   rpcSolidityPort    | rpcSolidity 端口  |

(4)数据库基础配置
数据库基础配置必填字段包括：

|       必填字段       |  解释  |
| :------------------: | :----: |
|     是否同步写入     | 是或否 |
| 是否打开 transaction | 是或否 |
|    选择数据库配置    | 是或否 |
| 是否打开 index 开关  | 是或否 |
| 是否需要更新 assets  | 是或否 |

(5)p2p 配置
p2p 配置必填字段包括：
| 必填字段 | 解释 |
| :------------------: | :----: |
| p2pVersion | p2p 版本 |
| listenPort | listen 端口 |
| seedNode | seedNode |

高级配置字段包括：
| 高级配置字段 | 解释 |
| :----------------------: | :--------------: |
| maxActiveNodes | 最大链接数 |
| maxActiveNodesWithSameIp | 同一 ip 最大连接数 |
| activeConnectFactor | activeConnectFactor |
| connectFactor | connectFactor |

(6)跨链配置
跨链配置必填字段包括：
| 必填字段 | 解释 |
| :------------------: | :----: |
| enableCrossChain | 是否允许跨链|
| maxValidatorNumber | 最大验证数 |
| minValidatorNumber | 最小验证数 |
| crossChainFee | crossChainFee |

### 4.导入插件

(1)共识模块
目前支持 DPOS

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

(3)数据库模块

支持 leveldb 和 rockdb

### 5.节点部署

(1)批量部署

(2)查看日志

## License

Copyright (c) 2019 Tron
