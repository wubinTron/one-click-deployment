/*
 * @Author: lxm 
 * @Date: 2019-08-28 15:27:13 
 * @Last Modified by: lxm
 * @Last Modified time: 2019-11-06 14:11:33
 * @tron plugin list  
 */
<template>
    <div class="app-container">
        <div class="tron-content">
            <div class="tron-filter-section">
                <div>
                    <el-steps :active="currentStep" align-center type="mini">
                        <el-step
                            @click.native="stepClickFun(1)"
                            :title="$t('tronPluginConsensusModule')"
                        ></el-step>
                        <el-step
                            @click.native="stepClickFun(2)"
                            :title="$t('tronPluginTransactionModule')"
                        ></el-step>
                        <el-step
                            @click.native="stepClickFun(3)"
                            :title="$t('tronPluginDatabaseModule')"
                        ></el-step>
                    </el-steps>
                </div>
            </div>
            <el-form
                ref="pluginOnsensusDialogForm"
                :model="pluginOnsensusForm"
                :rules="pluginRules"
                class="pluginSettingForm"
                label-position="left"
                v-if="currentStep == 1"
            >
                <el-row :gutter="12">
                    <el-col :span="24">
                        <el-card shadow="hover">
                            <div @click="baseContentShow = !baseContentShow">
                                <i
                                    :class="baseContentShow?'el-icon-arrow-down': 'el-icon-arrow-right'"
                                ></i>
                                {{$t('tronPluginConsensusModule')}}
                            </div>
                            <div v-if="baseContentShow">
                                <el-form-item prop="consensus" class="baseFormItem mgt20">
                                    <el-radio-group v-model="pluginOnsensusForm.consensus">
                                        <el-radio :label="'dpos'">DPOS</el-radio>
                                        <el-radio :label="'pbft'">PBFT</el-radio>
                                        <br />
                                        <el-radio
                                            style="margin:15px 0"
                                            :label="3"
                                        >{{$t('tronPluginCustomConsensusModule')}}：</el-radio>
                                    </el-radio-group>
                                    <el-input
                                        :maxlength="50"
                                        v-model="pluginOnsensusForm.consensusContent"
                                        :placeholder="$t('tronSettingPlaceholder')"
                                    ></el-input>
                                </el-form-item>
                            </div>
                        </el-card>
                    </el-col>
                </el-row>
                <el-form-item label-width="0" class="textRight">
                    <router-link :to="{path:'/setting/list'}">
                        <el-button type="primary">{{$t('tronSettingPreviousStep')}}</el-button>
                    </router-link>

                    <el-button
                        type="primary"
                        @click="saveData('pluginOnsensusDialogForm')"
                    >{{$t('tronSettingNextStep')}}</el-button>
                </el-form-item>
            </el-form>
            <el-form
                ref="transcationDialogForm"
                class="transcationDialogForm"
                :model="pluginTransactionForm"
                :rules="transcationRules"
                label-position="left"
                v-if="currentStep == 2"
            >
                <el-row :gutter="12">
                    <el-col :span="24">
                        <el-card shadow="hover">
                            <div @click="transcationContentShow = !transcationContentShow">
                                <i
                                    :class="transcationContentShow?'el-icon-arrow-down': 'el-icon-arrow-right'"
                                ></i>
                                {{$t('tronPluginTransactionModule')}}
                            </div>
                            <div v-if="transcationContentShow">
                                <el-form-item prop="transaction" class="baseFormItem mgt20">
                                    <el-checkbox-group v-model="pluginTransactionForm.transaction">
                                        <el-checkbox
                                            :label="item.value"
                                            :key="ind"
                                            v-for="(item,ind) in transactionCheckNodeAry"
                                        >{{item.label}}</el-checkbox>

                                        <br />
                                        <el-checkbox
                                            style="margin:15px 0"
                                            :label="9"
                                        >{{$t('tronPluginCustomTradingModule')}}</el-checkbox>
                                        <el-input
                                            :maxlength="50"
                                            v-model="pluginTransactionForm.transactionContent"
                                            :placeholder="$t('tronSettingPlaceholder')"
                                        ></el-input>
                                    </el-checkbox-group>
                                </el-form-item>
                            </div>
                        </el-card>
                    </el-col>
                </el-row>
                <el-form-item label-width="0" class="textRight">
                    <el-button
                        type="primary"
                        @click="previousStepFun()"
                    >{{$t('tronSettingPreviousStep')}}</el-button>
                    <el-button
                        type="primary"
                        @click="saveTranstionData('transcationDialogForm')"
                    >{{$t('tronSettingNextStep')}}</el-button>
                </el-form-item>
            </el-form>
            <el-form
                ref="dbFormDialogForm"
                :model="plugindbForm"
                :rules="plugindbRules"
                label-width="200px"
                class="dbSettingForm"
                label-position="left"
                v-if="currentStep == 3"
            >
                <el-row :gutter="12" v-if="currentStep == 3">
                    <el-col :span="24">
                        <el-card shadow="hover">
                            <div @click="dbsettingContentShow = !dbsettingContentShow">
                                <i
                                    :class="dbsettingContentShow?'el-icon-arrow-down': 'el-icon-arrow-right'"
                                ></i>
                                {{$t('tronPluginTransactionModule')}}
                            </div>
                            <div v-if="dbsettingContentShow">
                                <el-form-item
                                    label="dbsetting"
                                    prop="dbsetting"
                                    class="baseFormItem mgt20"
                                >
                                    <el-radio-group v-model="plugindbForm.dbsetting">
                                        <el-radio :label="'leveldb'">leveldb</el-radio>
                                        <el-radio :label="'rockdb'">rockdb</el-radio>
                                        <br />
                                        <el-radio
                                            style="margin:15px 0"
                                            :label="3"
                                        >{{$t('tronPluginCustomDatabaseModule')}}</el-radio>
                                    </el-radio-group>
                                    <el-input
                                        :maxlength="50"
                                        v-model="plugindbForm.dbsettingContent"
                                        :placeholder="$t('tronSettingPlaceholder')"
                                    ></el-input>
                                </el-form-item>
                            </div>
                        </el-card>
                    </el-col>
                </el-row>
                <el-form-item label-width="0" class="textRight">
                    <el-button
                        type="primary"
                        @click="previousStepFun()"
                    >{{$t('tronSettingPreviousStep')}}</el-button>
                    <el-button
                        type="primary"
                        @click="saveDBData('dbFormDialogForm')"
                    >{{$t('tronSettingNextStep')}}</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>
<script>
import {
    dbEngineApi,
    transactionApi,
    consensusApi,
    pluginConfigApi
} from "@/api/pluginApi";
export default {
    name: "pluginlist",
    data() {
        return {
            currentStep: 1,
            baseContentShow: true,
            transcationContentShow: true,
            dbsettingContentShow: true,
            pluginOnsensusForm: {
                consensus: "dpos",
                consensusContent: ""
            },
            pluginTransactionForm: {
                transaction: [
                    // "AccountPermissionUpdateActuator",
                    // "AssetIssueActuator",
                    // "ClearABIContractActuator",
                    // "CreateAccountActuator",
                    // "ExchangeCreateActuator",
                    // "ExchangeInjectActuator",
                    // "ExchangeTransactionActuator",
                    // "ExchangeWithdrawActuator",
                    // "FreezeBalanceActuator",
                    // "ParticipateAssetIssueActuator",
                    // "ProposalApproveActuator",
                    // "ProposalCreateActuator",
                    // "ProposalDeleteActuator",
                    // "SetAccountIdActuator",
                    // "ShieldedTransferActuator",
                    // "TransferActuator",
                    // "TransferAssetActuator",
                    // "UnfreezeAssetActuator",
                    // "UnfreezeBalanceActuator",
                    // "UpdateAccountActuator",
                    // "UpdateAssetActuator",
                    // "UpdateBrokerageActuator",
                    // "UpdateEnergyLimitContractActuator",
                    // "UpdateSettingContractActuator",
                    // "VoteWitnessActuator",
                    // "WithdrawBalanceActuator",
                    // "WitnessCreateActuator",
                    // "WitnessUpdateActuator"
                ],
                transactionContent: ""
            },
            transactionCheckNodeAry: [
                {
                    label: "AccountPermissionUpdate",
                    value: "AccountPermissionUpdateActuator"
                },
                { label: "AssetIssue", value: "AssetIssueActuator" },
                {
                    label: "ClearABIContract",
                    value: "ClearABIContractActuator"
                },
                {
                    label: "CreateAccount",
                    value: "CreateAccountActuator"
                },
                {
                    label: "ExchangeCreate",
                    value: "ExchangeCreateActuator"
                },
                {
                    label: "ExchangeInject",
                    value: "ExchangeInjectActuator"
                },
                {
                    label: "ExchangeTransaction",
                    value: "ExchangeTransactionActuator"
                },
                {
                    label: "ExchangeWithdraw",
                    value: "ExchangeWithdrawActuator"
                },
                {
                    label: "FreezeBalance",
                    value: "FreezeBalanceActuator"
                },
                {
                    label: "ParticipateAssetIssue",
                    value: "ParticipateAssetIssueActuator"
                },
                {
                    label: "ProposalApprove",
                    value: "ProposalApproveActuator"
                },
                {
                    label: "ProposalCreate",
                    value: "ProposalCreateActuator"
                },
                {
                    label: "ProposalDelete",
                    value: "ProposalDeleteActuator"
                },
                {
                    label: "SetAccountId",
                    value: "SetAccountIdActuator"
                },
                {
                    label: "ShieldedTransfer",
                    value: "ShieldedTransferActuator"
                },
                {
                    label: "Transfer",
                    value: "TransferActuator"
                },
                {
                    label: "TransferAsset",
                    value: "TransferAssetActuator"
                },
                {
                    label: "UnfreezeAsset",
                    value: "UnfreezeAssetActuator"
                },
                {
                    label: "UnfreezeBalance",
                    value: "UnfreezeBalanceActuator"
                },
                {
                    label: "UpdateAccount",
                    value: "UpdateAccountActuator"
                },
                {
                    label: "UpdateAsset",
                    value: "UpdateAssetActuator"
                },
                {
                    label: "UpdateBrokerage",
                    value: "UpdateBrokerageActuator"
                },
                {
                    label: "UpdateEnergyLimit",
                    value: "UpdateEnergyLimitContractActuator"
                },
                {
                    label: "UpdateSettingContract",
                    value: "UpdateSettingContractActuator"
                },
                {
                    label: "VoteWitness",
                    value: "VoteWitnessActuator"
                },
                {
                    label: "WithdrawBalance",
                    value: "WithdrawBalanceActuator"
                },
                {
                    label: "WitnessCreate",
                    value: "WitnessCreateActuator"
                },
                {
                    label: "WitnessUpdate",
                    value: "WitnessUpdateActuator"
                }
            ],
            plugindbForm: {
                dbsetting: "leveldb",
                dbsettingContent: ""
            },
            pluginRules: {
                consensus: [
                    {
                        required: true,
                        message: this.$t("tronSettingPlaceholder"),
                        trigger: "blur"
                    }
                ]
            },
            transcationRules: {
                transaction: [
                    {
                        required: true,
                        message: this.$t("tronSettingPlaceholder"),
                        trigger: "blur"
                    }
                ]
            },
            plugindbRules: {
                dbsetting: [
                    {
                        required: true,
                        message: this.$t("tronSettingPlaceholder"),
                        trigger: "blur"
                    }
                ]
            }
        };
    },
    created() {
        this.pluginConfigFun();
        this.getCurrentStepFun();
    },
    methods: {
        stepClickFun(step) {
            this.currentStep = step;
            this.$store.dispatch("tronSetting/getCurrentPluginStepConfig", {
                step
            });
            this.pluginConfigFun();
        },
        pluginConfigFun() {
            pluginConfigApi()
                .then(response => {
                    return response.data;
                })
                .then(res => {
                    if (res.consensus != "dpos" && res.consensus != "pbft") {
                        this.pluginOnsensusForm = {
                            consensus: 3,
                            consensusContent: res.consensus
                        };
                    } else {
                        this.pluginOnsensusForm = {
                            consensus: res.consensus,
                            consensusContent: ""
                        };
                    }

                    if (res.customTransaction != "") {
                        this.pluginTransactionForm.transactionContent =
                            res.customTransaction;
                        this.pluginTransactionForm.transaction = [
                            ...res.transaction,
                            9
                        ];
                    } else {
                        this.pluginTransactionForm.transaction =
                            res.transaction;
                    }
                    if (res.dbEngine != "leveldb" && res.dbEngine != "rockdb") {
                        this.plugindbForm = {
                            dbsetting: 3,
                            dbsettingContent: res.dbEngine
                        };
                    } else {
                        this.plugindbForm = {
                            dbsetting: res.dbEngine,
                            dbsettingContent: ""
                        };
                    }
                })
                .catch(error => {
                    console.log(error);
                });
        },
        getCurrentStepFun() {
            let step = sessionStorage.getItem("pluginstep") || 1;
            if (this.currentStep != 0) {
                step = step || this.currentStep;
            }
            this.currentStep = Number(step);
            this.$store.dispatch("tronSetting/getCurrentPluginStepConfig", {
                step: this.currentStep
            });
        },
        previousStepFun(val) {
            this.currentStep = this.currentStep - 1;
            this.$store.dispatch("tronSetting/getCurrentPluginStepConfig", {
                step: this.currentStep
            });
            this.pluginConfigFun();
        },
        saveData(formName) {
            this.$refs[formName].validate(valid => {
                if (valid) {
                    console.log(this.pluginOnsensusForm);
                    if (this.pluginOnsensusForm.consensus == 3) {
                        this.pluginOnsensusForm.consensus = this.pluginOnsensusForm.consensusContent;
                    }
                    consensusApi({
                        consensus: this.pluginOnsensusForm.consensus
                    })
                        .then(response => {
                            this.$message.success(
                                this.$t("tronPluginConsensusSaveSuccess")
                            );
                            this.currentStep = this.currentStep + 1;
                            this.$store.dispatch(
                                "tronSetting/getCurrentPluginStepConfig",
                                {
                                    step: this.currentStep
                                }
                            );
                            this.pluginConfigFun();
                        })
                        .catch(error => {
                            console.log(error);
                        });
                } else {
                    console.log("error submit!!");
                    return false;
                }
            });
        },
        saveTranstionData(formName) {
            this.$refs[formName].validate(valid => {
                if (valid) {
                    if (this.pluginTransactionForm.transactionContent != "") {
                        this.pluginTransactionForm.transaction.forEach(
                            (item, ind) => {
                                if (item === 9) {
                                    this.pluginTransactionForm.transaction.splice(
                                        ind,
                                        1
                                    );
                                }
                            }
                        );
                    }
                    console.log(this.pluginTransactionForm);
                    transactionApi({
                        transaction: this.pluginTransactionForm.transaction,
                        customTransaction: this.pluginTransactionForm
                            .transactionContent
                    })
                        .then(response => {
                            this.$message.success(
                                this.$t("tronPluginInputSaveSuccess")
                            );
                            this.currentStep = this.currentStep + 1;
                            this.$store.dispatch(
                                "tronSetting/getCurrentPluginStepConfig",
                                {
                                    step: this.currentStep
                                }
                            );
                            this.pluginConfigFun();
                        })
                        .catch(error => {
                            console.log(error);
                        });
                }
            });
        },
        saveDBData(formName) {
            this.$refs[formName].validate(valid => {
                if (valid) {
                    if (this.plugindbForm.dbsetting == 3) {
                        this.plugindbForm.dbsetting = this.plugindbForm.dbsettingContent;
                    }
                    dbEngineApi({ dbEngine: this.plugindbForm.dbsetting })
                        .then(response => {
                            this.$message.success(
                                this.$t("tronPluginInputSaveSuccess")
                            );
                            this.$router.push({
                                path: "/node/list",
                                query: { deploy: 1 }
                            });
                        })
                        .catch(error => {
                            console.log(error);
                        });
                } else {
                    console.log("error submit!!");
                    return false;
                }
            });
        }
    }
};
</script>
<style lang="scss" rel="stylesheet/scss" scoped>
.textRight {
    margin-top: 40px;
    text-align: right;
}
</style>
