<p align="center">
  <img width="320" src="https://tronscan.org/static/media/tron-banner-1.e40b3379.png">
</p>

English | [简体中文](https://github.com/wubinTron/one-click-deployment/blob/master/steps/steps.zh.md)

### 1.Ready to work

Need to implement in advance [free login](https://www.jianshu.com/p/13919b5ba8a2)

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

(2)Basic configuration

The basic configuration required fields include：

| Required field | Explanation |
| :------------: | :---------: |
|    chainId     |  chain ID   |
|   chainName    | chain Name  |

Advanced configuration fields include：

| Advanced configuration field |          Explanation           |
| :--------------------------: | :----------------------------: |
|   MaintenanceTimeInterval    |      Maintenance interval      |
|   blockProposalExpireTime    | Block proposal expiration time |
|   nodeBlockProducedTimeOut   |  node Block Produced TimeOut   |
|   nodeMinParticipationRate   |   node MinParticipationRate    |

Basic configuration Click the Next button to save the current configuration

(3)Network link configuration(http/rpc)

Required fields for network link configuration include:

|    Required field    |       Explanation       |
| :------------------: | :---------------------: |
| maxHttpConnectNumber | max http Connect Number |
|   httpFullNodePort   |    httpFullNode Port    |
|   httpSolidityPort   |    httpFullNode Port    |
|       rpcPort        |        rpc Port         |
|   rpcSolidityPort    |    rpcSolidity Port     |

Network link configuration Click the Next button to save the current configuration

(4)Database basic configuration

Database base configuration required fields include：

|          Required field          | Explanation |
| :------------------------------: | :---------: |
|  Whether to write synchronously  |  Yes or no  |
| Whether to open the transaction  |  Yes or no  |
|  Select database configuration   |  Yes or no  |
| Whether to open the index switch |  Yes or no  |
|      Need to update assets       |  Yes or no  |

Database basic configuration Click the Next button to save the current configuration

(5)P2p configuration

P2p configuration required fields include：

| Required field | Explanation |
| :------------: | :---------: |
|   p2pVersion   | p2p Version |
|   listenPort   | listen Port |
|    seedNode    |  seedNode   |

Advanced configuration fields include：

| Advanced configuration field |        Explanation         |
| :--------------------------: | :------------------------: |
|        maxActiveNodes        |      Max Active Nodes      |
|   maxActiveNodesWithSameIp   | max Active NodesWithSameIp |
|     activeConnectFactor      |    activeConnectFactor     |
|        connectFactor         |       connectFactor        |

P2p configuration Click the Next button to save the current configuration

(6)Cross-chain configuration

Required fields for cross-chain configuration include：

|   Required field   |     Explanation     |
| :----------------: | :-----------------: |
|  enableCrossChain  |  enable CrossChain  |
| maxValidatorNumber | max ValidatorNumber |
| minValidatorNumber | min ValidatorNumber |
|   crossChainFee    |    crossChainFee    |

Cross-chain configuration Click the Next button to save the current cross-chain configuration<sup style="color:blue">[7]</sup>

### 5.Import plugin

As shown：

![](https://github.com/wubinTron/one-click-deployment/blob/master/steps/img/plugins.png)

(1)Consensus module

Currently supports DPOS

Consensus module click the Next button to save the current consensus module settings

(2)Transaction module

Currently supported modules are as follows, you can also customize the module
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

Trading module click the Next button to save the current consensus module settings

(3)Database module

Support for leveldb and rockdb

Database module click Next to save the current database module<sup style="color:blue">[8]</sup>

### 6.Node deployment

(1)Bulk deployment

Node deployment supports single node deployment and multiple node bulk deployment
Click the checkbox in the list and select the node that needs to be deployed. The deployment button is green.
Click the bulk deployment button and enter the corresponding zip package path, such as/home/user/java-tron/build/distributions/java-tron-1.0.0.zip
Click Save to start the deployment. During the deployment, the batch deployment button is in the loading state. You can click View Log to view the deployment details. After the deployment is complete, the batch deployment button cancels the loading status.

(2)View log

Click to view the log to view the current node's log. If not, it will prompt the no-node log.
If the deployment is complete, click to view the log. The log will be automatically refreshed until the deploy finish appears, then the deployment is complete.

![](https://github.com/wubinTron/one-click-deployment/blob/master/steps/img/deploy.png)

## Footnote

[1] Note: Click the Start One-Click Deployment button to get the node list menu permissions. Each click will reset the menu permissions.

[2] Note: id and ip addresses cannot be the same

[3] Note: The node privateKey added each time cannot be repeated

[4] Note: Click the node list next button to reset the configuration management menu permissions, only show configuration management menu permissions

[5] Note: When the node list is empty, the default next button cannot be clicked. When the node list is deleted, there is no next menu permission. The node list supports up to 20 nodes.

[6] Note: The balance range is between -9223372036854775808 and 9223372036854775807. To ensure successful deployment, please fill in the range of values.

[7] Note: Click the last step of the configuration management cross-chain configuration next step, get the import plug-in menu permissions, each time click reset menu permissions

[8] Note: Click the button to get the node deployment menu right when you click the next step of the import plugin. Click the reset menu permission each time.

## License

Copyright (c) 2019 Tron
