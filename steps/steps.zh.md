<p align="center">
  <img width="320" src="https://tronscan.org/static/media/tron-banner-1.e40b3379.png">
</p>

简体中文 | [English](https://github.com/wubinTron/one-click-deployment/blob/master/steps/steps.md)

### 1.准备工作

```
  ssh免密登录在实际工作中有重要的作用
  免密登录，需要先在本机生成公钥，然后将公钥拷贝到远程主机，拷贝的过程，既可以手动（在远程主机根目录下创建.ssh目录，然后将公钥存入该目录下authorized_keys文件中即可），
  也可以直接命令操作ssh-copy-id，这个操作做完了，即可免密登录远程主机。
  这里假定我们需要在node01上远程登录node02机器，这样的话，默认ssh登录，需要输入node02上root用户的密码。如下图所示:
  一、生成类型rsa免密的公钥。

    (1)命令
    #ssh-keygen -t rsa -P ""
      遇到提示，一路回车即可。

    (2)查看刚刚生成的公钥id_rsa.pub:
    cat id_rsa.pub
  二、将公钥通过命令ssh-copy-id放入node02机器上。
    (1)命令
    #ssh-copy-id root@node02
    执行这一步操作，需要输入node02上root用户登录的密码
    (2)查看ssh和authorized_keys
    执行成功之后，我们在node02上查看/root/.ssh目录已经生成，并且多了一个名为authorized_keys的文件，里面保存的正是node01上ssh-keygen生成的id_rsa.pub的内容。
    cd .ssh
    cat authorized_keys
  三、远程登录node02，无需密码，直接登录成功
    ssh node2

    另外：免密登录本机也是可以的，默认是需要密码的，只需要将id_rsa.pub拷贝一份保存为authorized_keys。接着ssh登录本机，直接登录成功。

```

### 2.一键发链首页

主要展示一键发链的内容操作步骤及相关文档说明  
（1）一键发链介绍  
（2）新手指南  
（3）帮助文档  
（4）点击开始按钮，一键部署<sup style="color:blue">[1]</sup>

### 3.添加节点

点击选择左上角的添加节点，给一键发链网络添加 Witness 节点和 FullNode 节点

必填字段包括：

|                  必填字段                   |                  解释                  |
| :-----------------------------------------: | :------------------------------------: |
|    ID <sup style="color:blue">[2]</sup>     |              节点唯一编号              |
|                  用户名称                   |               ssh 用户名               |
|                     IP                      |                节点 Ip                 |
|                    PORT                     |               节点 端口                |
|                     URL                     |        Url （为 Witness 必填）         |
|                  voteCount                  |    对应投票数量 （为 Witness 必填）    |
| privateKey<sup style="color:blue">[3]</sup> | 保存本地 64 位私钥 （为 Witness 必填） |

如图：

![](https://github.com/wubinTron/one-click-deployment/blob/develop/steps/img/add_node.png)

(1) 点击保存按钮<sup style="color:blue">[4]</sup> 保存当前节点信息  
(2) 节点列表 <sup style="color:blue">[5]</sup> 节点列表展示所有添加的节点 如下图所示：  
(3) 点击下一步将会进入配置页面

![](https://github.com/wubinTron/one-click-deployment/blob/master/steps/img/nodeList.png)

### 4.配置管理

如图：

![](https://github.com/wubinTron/one-click-deployment/blob/master/steps/img/setting.png)

(1)创世信息配置

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

网络链接配置点击下一步按钮保存当前配置

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
