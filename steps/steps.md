<p align="center">
  <img width="320" src="https://tronscan.org/static/media/tron-banner-1.e40b3379.png">
</p>

English | [简体中文](./steps/steps.zh.md)

### 1.Ready to work

Need to implement in advance [free login](https://www.jianshu.com/p/13919b5ba8a2)

Deploy node needs [java-tron-1.0.0.zip build](https://github.com/tronprotocol/java-tron/blob/develop/build.md)

If front-end browser and the back-end server not in one computer, we needs to modified the corresponding variable VUE_APP_BASE_API in src/main/webapp/.env.development.

### 2.Home

Mainly show the content operation steps of the one-click deployment and related documentation

（1）One-click deployment introduction  
（2）Guide  
（3）Documentation  
（4）Click the start button and deploy with one click<sup style="color:blue">[1]</sup>

### 3.Add node

Click on the Add node in the [upper left corner](./steps/img/add_node.png) to add a Witness node and a FullNode node to the one-click chain network.

Required fields include：

|               Required field                |                                    Explanation                                     |
| :-----------------------------------------: | :--------------------------------------------------------------------------------: |
|    ID <sup style="color:blue">[2]</sup>     | Node unique number (Number is a positive integer maximum support value 2147483647) |
|                  user name                  |                                    ssh username                                    |
|                     IP                      |                                      Node Ip                                       |
|                    PORT                     |     Node Port (Number is a positive integer maximum support value 2147483647)      |
|                     URL                     |                            Url （Required for Witness）                            |
|                  voteCount                  |               Number of corresponding votes （Required for Witness）               |
| privateKey<sup style="color:blue">[3]</sup> |               Save local 64-bit private key （Required for Witness）               |

(1) Click the save button<sup style="color:blue">[4]</sup> to save current node information

(2) [Node list](./steps/img/nodeList.png) <sup style="color:blue">[5]</sup> shows all the added nodes

(3) Click next to enter the configuration page

### 4.Configuration management

The configuration management page [as shown](./steps/img/setting.png), mainly including: genesis information, basic configuration, network configuration, database configuration, p2p configuration and cross-chain configuration

(1)Genesis Information Configuration

Genesis information configuration mainly includes asset configuration and witeness configuration.  
Asset configuration can add and modify current asset configuration information.  
Witeness configuration to view current witeness configuration information.

The required fields for asset configuration include:

|              Required field               |              Explanation              |
| :---------------------------------------: | :-----------------------------------: |
|                accountName                |             account name              |
|                accountType                | account type： AssetIssue or Contract |
|                  address                  |                address                |
| balance <sup style="color:blue">[6]</sup> |                balance                |

Genesis Information Configuration click next button to save the current configuration

(2)Basic configuration

The basic configuration required fields include：

| Required field |                               Explanation                                |
| :------------: | :----------------------------------------------------------------------: |
|    chainId     | chain ID (Number is a positive integer maximum support value 2147483647) |
|   chainName    |                                chain Name                                |

Advanced configuration fields include：

| Advanced configuration field |                                          Explanation                                           |
| :--------------------------: | :--------------------------------------------------------------------------------------------: |
|   MaintenanceTimeInterval    |      Maintenance interval (Number is a positive integer maximum support value 2147483647)      |
|   blockProposalExpireTime    | Block proposal expiration time (Number is a positive integer maximum support value 2147483647) |
|   nodeBlockProducedTimeOut   |  node Block Produced TimeOut (Number is a positive integer maximum support value 2147483647)   |
|   nodeMinParticipationRate   |   node MinParticipationRate (Number 0 or positive integer maximum support value 2147483647)    |

Basic configuration click the next button to save the current configuration

(3)Network link configuration(http/rpc)

Required fields for network link configuration include:

|    Required field    |                                       Explanation                                       |
| :------------------: | :-------------------------------------------------------------------------------------: |
| maxHttpConnectNumber | max http Connect Number (Number is a positive integer maximum support value 2147483647) |
|   httpFullNodePort   |    httpFullNode Port (Number is a positive integer maximum support value 2147483647)    |
|   httpSolidityPort   |    httpFullNode Port (Number is a positive integer maximum support value 2147483647)    |
|       rpcPort        |        rpc Port (Number is a positive integer maximum support value 2147483647)         |
|   rpcSolidityPort    |    rpcSolidity Port (Number is a positive integer maximum support value 2147483647)     |

Network link configuration click the next button to save the current configuration

(4)Database configuration

Database configuration required fields include：

|          Required field          | Explanation |
| :------------------------------: | :---------: |
|  Whether to write synchronously  |  Yes or no  |
| Whether to open the transaction  |  Yes or no  |
|  Select database configuration   |  Yes or no  |
| Whether to open the index switch |  Yes or no  |
|      Need to update assets       |  Yes or no  |

Database configuration click the next button to save the current configuration

(5)P2p configuration

P2p configuration required fields include：

| Required field |                                 Explanation                                 |
| :------------: | :-------------------------------------------------------------------------: |
|   p2pVersion   | p2p Version (Number is a positive integer maximum support value 2147483647) |
|   listenPort   | listen Port (Number is a positive integer maximum support value 2147483647) |
|    seedNode    |                              seedNode (check)                               |

Advanced configuration fields include：

| Advanced configuration field |                                        Explanation                                         |
| :--------------------------: | :----------------------------------------------------------------------------------------: |
|        maxActiveNodes        |      Max Active Nodes (Number is a positive integer maximum support value 2147483647)      |
|   maxActiveNodesWithSameIp   | max Active NodesWithSameIp (Number is a positive integer maximum support value 2147483647) |
|     activeConnectFactor      |         activeConnectFactor (Number and positive maximum support value 2147483647)         |
|        connectFactor         |            connectFactor (Number and positive maximum support value 2147483647)            |

P2p configuration click the next button to save the current configuration

(6)Cross-chain configuration

Required fields for cross-chain configuration include：

|   Required field   |                                                                      Explanation                                                                      |
| :----------------: | :---------------------------------------------------------------------------------------------------------------------------------------------------: |
|  enableCrossChain  |                                                        enable CrossChain (check the selection)                                                        |
| maxValidatorNumber |               max ValidatorNumber (Number is a positive integer and greater than minValidatorNumber maximum supported value 2147483647)               |
| minValidatorNumber | min ValidatorNumber Minimum number of validations (numbers are positive integers and less than minValidatorNumber maximum supported value 2147483647) |
|   crossChainFee    |                                         crossChainFee (Number and positive maximum support value 2147483647)                                          |

Cross-chain configuration click the next button to save the current cross-chain configuration<sup style="color:blue">[7]</sup>

### 5.Import plugin

Import plugin page [as shown](./steps/img/plugins.png), mainly including: consensus module, transaction module and database module

(1)Consensus module

Currently supports DPOS

Consensus module click the next button to save the current consensus module settings

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
Also could customize uploading transactions.
Upload jar which not related customize transaction may affect normal running  
Trading module click the next button to save the current transaction module settings.

(3)Database module

Support for leveldb and rocksdb

Database module click next to save the current database module<sup style="color:blue">[8]</sup>

### 6.Node deployment

(1)Bulk deployment

Node deployment supports single node deployment and multiple node bulk deployment  
Select the node which needs to be deployed. The deployment button will be green.  
Click the bulk deployment button, it will show [input box](./steps/img/deploy.png) and enter the corresponding zip package path, such as /home/user/java-tron/build/distributions/java-tron-1.0.0.zip  
Click save and start to the deployment. During the deployment, the batch deployment button is in the loading state.  
You can click view log to check the deployment log. After the deployment is complete, the batch deployment button end loading status.

(2)View log

Click the log button to check the current node's log. If not, it will response the no-node log.  
If the deployment is complete, click to view the log.  
The log will be automatically refreshed until the deploy finish.

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
