/* * @Author: lxm * @Date: 2019-08-28 15:27:13 * @Last Modified by: lxm * @Last
Modified time: 2019-11-14 15:13:54 * @tron plugin list */
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
                        <el-step
                          @click.native="stepClickFun(4)"
                          :title="$t('tronPluginCryptoModule')"
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
                                    :class="
                    baseContentShow
                      ? 'el-icon-arrow-down'
                      : 'el-icon-arrow-right'
                  "
                                ></i>
                                {{ $t("tronPluginConsensusModule") }}
                            </div>
                            <div v-if="baseContentShow" class="pluginSettingFormContent">
                                <el-form-item prop="consensus" class="baseFormItem mgt30">
                                    <el-radio-group v-model="pluginOnsensusForm.consensus">
                                        <el-radio :label="'dpos'">DPOS</el-radio>
                                        <!-- <el-radio :label="'pbft'">PBFT</el-radio>
                                        <br />
                                        <el-radio
                                            style="margin:15px 0"
                                            :label="3"
                                        >{{$t('tronPluginCustomConsensusModule')}}：</el-radio>-->
                                    </el-radio-group>
                                    <!-- <br />
                                    <el-input
                                        size="small"
                                        class="pluginSettingFormInput"
                                        :maxlength="50"
                                        v-model.trim="pluginOnsensusForm.consensusContent"
                                        :placeholder="$t('tronPluginCustomConsensusModulePlaceholder')"
                                    ></el-input>-->
                                </el-form-item>
                            </div>
                        </el-card>
                    </el-col>
                </el-row>
                <el-form-item label-width="0" class="textRight">
                    <router-link :to="{ path: '/setting/list' }">
                        <el-button size="small" type="primary">
                            {{
                            $t("tronSettingPreviousStep")
                            }}
                        </el-button>
                    </router-link>

                    <el-button
                        size="small"
                        type="primary"
                        @click="saveData('pluginOnsensusDialogForm')"
                    >{{ $t("tronSettingNextStep") }}</el-button>
                </el-form-item>
            </el-form>
            <!-- transcation -->
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
                                    :class="
                    transcationContentShow
                      ? 'el-icon-arrow-down'
                      : 'el-icon-arrow-right'
                  "
                                ></i>
                                {{ $t("tronPluginTransactionModule") }}
                            </div>
                            <div v-if="transcationContentShow" class="transcationDialogFormContent">
                                <el-form-item prop="transaction" class="baseFormItem mgt20">
                                    <el-checkbox-group v-model="pluginTransactionForm.transaction">
                                        <el-checkbox
                                            :label="item.value"
                                            :key="ind"
                                            v-for="(item, ind) in transactionCheckNodeAry"
                                        >{{ item.label }}</el-checkbox>
                                        <br />
                                        <el-button
                                            type="text"
                                            size="small"
                                            @click="moreSetting = !moreSetting"
                                        >{{ $t("tronMoreSetting") }}</el-button>
                                        <div v-if="moreSetting">
                                            <el-checkbox style="margin:0" :label="9">
                                                {{
                                                $t("tronPluginCustomTradingModule")
                                                }}
                                            </el-checkbox>
                                            <br />
                                            <el-input
                                                :autosize="{ minRows: 2, maxRows: 4 }"
                                                type="textarea"
                                                class="singlePluginSettingFormInput"
                                                size="small"
                                                :maxlength="300"
                                                v-model.trim="pluginTransactionForm.transactionContent"
                                                :placeholder="
                          $t('tronPluginCustomTradingModulePlaceholder')
                        "
                                            ></el-input>
                                        </div>
                                    </el-checkbox-group>
                                </el-form-item>
                            </div>
                        </el-card>
                    </el-col>
                </el-row>
                <el-form-item label-width="0" class="textRight">
                    <el-button size="small" type="primary" @click="previousStepFun()">
                        {{
                        $t("tronSettingPreviousStep")
                        }}
                    </el-button>
                    <el-button
                        size="small"
                        type="primary"
                        @click="saveTranstionData('transcationDialogForm')"
                    >{{ $t("tronSettingNextStep") }}</el-button>
                </el-form-item>
            </el-form>
            <!-- db form -->
            <el-form
                ref="dbFormDialogForm"
                :model="plugindbForm"
                :rules="plugindbRules"
                class="dbSettingForm"
                label-position="left"
                v-if="currentStep == 3"
            >
                <el-row :gutter="12" v-if="currentStep == 3">
                    <el-col :span="24">
                        <el-card shadow="hover">
                            <div @click="dbsettingContentShow = !dbsettingContentShow">
                                <i
                                    :class="
                    dbsettingContentShow
                      ? 'el-icon-arrow-down'
                      : 'el-icon-arrow-right'
                  "
                                ></i>
                                {{ $t("tronPluginDatabaseModule") }}
                            </div>
                            <div v-if="dbsettingContentShow" style="padding-left:20px">
                                <el-form-item prop="dbsetting" class="baseFormItem mgt30">
                                    <el-radio-group v-model="plugindbForm.dbsetting">
                                        <el-radio disabled :label="'leveldb'">leveldb</el-radio>
                                        <el-radio disabled :label="'rocksdb'">rocksdb</el-radio>
                                        <!-- <br /> -->
                                        <!-- <el-radio
                                            style="margin:15px 0"
                                            :label="3"
                                        >{{$t('tronPluginCustomDatabaseModule')}}</el-radio>-->
                                    </el-radio-group>
                                    <!-- <br /> -->
                                    <!-- <el-input
                                        size="small"
                                        class="pluginSettingFormInput"
                                        :maxlength="50"
                                        v-model.trim="plugindbForm.dbsettingContent"
                                        :placeholder="$t('tronPluginCustomDatabaseModulePlaceholder')"
                                    ></el-input>-->
                                </el-form-item>
                            </div>
                        </el-card>
                    </el-col>
                </el-row>
                <el-form-item label-width="0" class="textRight">
                    <el-button size="small" type="primary" @click="previousStepFun()">
                        {{
                        $t("tronSettingPreviousStep")
                        }}
                    </el-button>
                    <el-button
                        size="small"
                        type="primary"
                        @click="saveDBData('dbFormDialogForm')"
                    >{{ $t("tronSettingNextStep") }}</el-button>
                </el-form-item>
            </el-form>
            <!-- crypto form -->
            <el-form
              ref="cryptoFormDialogForm"
              :model="cryptoForm"
              :rules="cryptoRules"
              class="cryptoForm"
              label-position="left"
              v-if="currentStep === 4">
              <el-row :gutter="12" v-if="currentStep == 4">
                <el-col :span="24">
                  <el-card shadow="hover">
                    <div @click="cryptoContentShow = !cryptoContentShow">
                      <i
                        :class="
                    cryptoContentShow
                      ? 'el-icon-arrow-down'
                      : 'el-icon-arrow-right'
                  "
                      ></i>
                      {{ $t("tronPluginCryptoModule") }}
                    </div>
                    <div v-if="cryptoContentShow" style="padding-left:20px">
                      <el-form-item prop="crypto" class="baseFormItem mgt30">
                        <el-radio-group v-model="cryptoForm.crypto">
                          <el-radio :label="'eckey'">eckey</el-radio>
                          <el-radio :label="'sm2'">sm2</el-radio>
                        </el-radio-group>
                      </el-form-item>
                    </div>
                  </el-card>
                </el-col>
              </el-row>
              <el-form-item label-width="0" class="textRight">
                <el-button size="small" type="primary" @click="previousStepFun()">
                  {{
                  $t("tronSettingPreviousStep")
                  }}
                </el-button>
                <el-button
                  size="small"
                  type="primary"
                  @click="saveCryptoData('cryptoFormDialogForm')"
                >{{ $t("tronSettingNextStep") }}</el-button>
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
    pluginConfigApi,
    cryptoApi,
} from "@/api/pluginApi";
export default {
    name: "pluginlist",
    computed: {
        pluginRules() {
            const rules = {
                consensus: [
                    {
                        required: true,
                        message: this.$t("tronSettingPlaceholder"),
                        trigger: "blur"
                    }
                ]
            };
            return rules;
        },
        transcationRules() {
            const rules = {
                transaction: [
                    {
                        required: true,
                        message: this.$t("tronSettingPlaceholder"),
                        trigger: "blur"
                    }
                ]
            };
            return rules;
        },
        plugindbRules() {
            const rules = {
                dbsetting: [
                    {
                        required: true,
                        message: this.$t("tronSettingPlaceholder"),
                        trigger: "blur"
                    }
                ]
            };
            return rules;
        },
        cryptoRules() {
          const rules = {
            crypto: [
              {
                required: true,
                message: this.$t("tronSettingPlaceholder"),
                trigger: "blur"
              }
            ]
          };
          return rules;
        }
    },
    data() {
        return {
            currentStep: 1,
            baseContentShow: true,
            moreSetting: false,
            transcationContentShow: true,
            dbsettingContentShow: true,
            cryptoContentShow: true,
            pluginOnsensusForm: {
                consensus: "dpos",
                consensusContent: ""
            },
            pluginTransactionForm: {
                transaction: [],
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
            cryptoForm: {
              crypto: 'eckey',
              cryptoContent: '',
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
                    if (
                        res.dbEngine != "leveldb" &&
                        res.dbEngine != "rocksdb"
                    ) {
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

                    if (!['sm2', 'eckey'].includes(res.crypto)) {
                      this.cryptoForm = {
                        crypto: 3,
                        cryptoContent: res.crypto
                      };
                    } else {
                      this.cryptoForm = {
                        crypto: res.crypto,
                        cryptoContent: '',
                      }
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
                    if (this.pluginOnsensusForm.consensus == 3) {
                        if (this.pluginOnsensusForm.consensusContent == "") {
                            this.$message.success(
                                this.$t(
                                    "tronPluginCustomConsensusModulePlaceholder"
                                )
                            );
                            return;
                        }
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
                    let checkStatus = 0;
                    if (this.pluginTransactionForm.transactionContent != "") {
                        this.pluginTransactionForm.transaction.forEach(
                            (item, ind) => {
                                if (item === 9) {
                                    if (
                                        this.pluginTransactionForm
                                            .transactionContent.length > 4 &&
                                        this.pluginTransactionForm.transactionContent.slice(
                                            -4
                                        ) == ".jar"
                                    ) {
                                        checkStatus = 1;
                                    } else {
                                        checkStatus = 2;
                                        // this.pluginTransactionForm.transaction.splice(
                                        //     ind,
                                        //     1
                                        // );
                                    }
                                } else {
                                    checkStatus = 3;
                                }
                            }
                        );
                    } else {
                        if (
                            this.pluginTransactionForm.transaction.indexOf(9) >
                            0
                        ) {
                            checkStatus = 4;
                            this.$message.warning(
                                this.$t(
                                    "tronPluginCustomTradingModulePlaceholder"
                                )
                            );
                        }
                    }
                    console.log(checkStatus);
                    if (checkStatus == 1) {
                        this.pluginTransactionForm.transaction.forEach(
                            (item, ind) => {
                                if (item === 9) {
                                    this.pluginTransactionForm.transaction.splice(
                                        ind,
                                        1
                                    );
                                } else {
                                    checkStatus = 3;
                                }
                            }
                        );
                    } else if (checkStatus == 2) {
                        this.$message({
                            type: "warning",
                            message: this.$t(
                                "tronPluginDeploymentCorrectPathPlaceholder"
                            )
                        });
                        return;
                    } else if (checkStatus == 3) {
                        this.$message({
                            type: "warning",
                            message: this.$t(
                                "tronPluginDeploymentCheckPathPlaceholder"
                            )
                        });
                        return;
                    } else if (checkStatus == 4) {
                        return;
                    }

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
                        if (this.plugindbForm.dbsettingContent == "") {
                            this.$message.success(
                                this.$t(
                                    "tronPluginCustomDatabaseModulePlaceholder"
                                )
                            );
                            return;
                        }
                        this.plugindbForm.dbsetting = this.plugindbForm.dbsettingContent;
                    }
                    dbEngineApi({ dbEngine: this.plugindbForm.dbsetting })
                        .then(async response => {
                            this.$message.success(
                                this.$t("tronPluginDatabaseSaveSuccess")
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
      saveCryptoData(formName) {
        this.$refs[formName].validate(valid => {
          if (valid) {
            if (this.cryptoForm.crypto === 3) {
              if (!this.cryptoForm.cryptoContent) {
                this.$message.success(
                  this.$t("tronPluginCustomCryptoModulePlaceholder")
                );
                return;
              }
              this.cryptoForm.crypto = this.cryptoForm.cryptoContent;
            }
            cryptoApi({ crypto: this.cryptoForm.crypto })
              .then(async response => {
                this.$message.success(
                  this.$t("tronPluginInputSaveSuccess")
                );
                await this.$store
                  .dispatch("user/changeRoles", "deploy")
                  .then(res => {
                    console.log(res);
                  });
                this.$router.push({
                  path: "/deploy/list"
                  // query: { deploy: 1 }
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
.pluginSettingFormInput {
    width: 400px;
}
.singlePluginSettingFormInput {
    width: 715px;
}
</style>
