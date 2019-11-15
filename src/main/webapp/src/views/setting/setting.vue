/*
 * @Author: lxm 
 * @Date: 2019-08-28 15:27:13 
 * @Last Modified by: lxm
 * @Last Modified time: 2019-11-15 17:34:04
 * @tron setting default  
 */
<template>
    <div class="app-container">
        <div class="tron-content">
            <div class="tron-filter-section">
                <div>
                    <el-steps :active="currentStep" align-center type="mini">
                        <el-step @click.native="stepClickFun(1)" :title="$t('tronSettingGenesis')"></el-step>
                        <el-step @click.native="stepClickFun(2)" :title="$t('tronSettingBase')"></el-step>
                        <el-step @click.native="stepClickFun(3)" :title="$t('tronSettingHttp')"></el-step>
                        <el-step @click.native="stepClickFun(4)" :title="$t('tronSettingDb')"></el-step>
                        <el-step @click.native="stepClickFun(5)" :title="$t('tronSettingP2p')"></el-step>
                        <el-step @click.native="stepClickFun(6)" :title="$t('tronCrossChain')"></el-step>
                    </el-steps>
                </div>
            </div>
            <div class="filter-container tron-table tabSection">
                <!--tron form-->
                <genesis-setting v-if="currentStep == 1" @addSettingSuccess="addSettingSuccessFun"></genesis-setting>
                <base-setting
                    v-if="currentStep == 2"
                    :detailInfoData="baseSetting.detail"
                    @previousSettingStep="previousSettingStepFun"
                    @addSettingSuccess="addSettingSuccessFun"
                ></base-setting>
                <network-setting
                    v-if="currentStep == 3"
                    @previousSettingStep="previousSettingStepFun"
                    :detailInfoData="networkSetting.detail"
                    @addSettingSuccess="addSettingSuccessFun"
                ></network-setting>
                <databaseSetting
                    v-if="currentStep == 4"
                    @previousSettingStep="previousSettingStepFun"
                    :detailInfoData="databaseSettingForm.detail"
                    @addSettingSuccess="addSettingSuccessFun"
                ></databaseSetting>
                <p2p-setting
                    v-if="currentStep == 5"
                    :seedNodeIpListAry="seedNodeIpListData"
                    :detailInfoData="p2pSetting.detail"
                    @previousSettingStep="previousSettingStepFun"
                    @addSettingSuccess="addSettingSuccessFun"
                ></p2p-setting>
                <cross-chain
                    v-if="currentStep == 6"
                    @previousSettingStep="previousSettingStepFun"
                    :detailInfoData="crossChainSetting.detail"
                    @addSettingSuccess="addSettingSuccessFun"
                ></cross-chain>
            </div>
        </div>
    </div>
</template>
<script>
import { mapGetters } from "vuex";
import { originSettingApi, configSettingfdApi } from "@/api/settingApi.js";
import genesisSetting from "./genesisSetting";
import baseSetting from "./baseSetting";
import p2pSetting from "./p2pSetting";
import crossChain from "./crossChain";
import databaseSetting from "./dbSetting";
import networkSetting from "./networkSetting";
export default {
    name: "settingList",
    components: {
        genesisSetting,
        baseSetting,
        p2pSetting,
        crossChain,
        databaseSetting,
        networkSetting
    },
    computed: {
        ...mapGetters(["settingStep"])
    },
    data() {
        return {
            currentStep: 1,
            genesisSetting: {
                detail: {}
            },
            baseSetting: {
                detail: {}
            },
            p2pSetting: {
                detail: {}
            },
            crossChainSetting: {
                detail: {}
            },
            databaseSettingForm: {
                detail: {}
            },
            networkSetting: {
                detail: {}
            },
            originSettingObj: {},
            seedNodeIpListData: [],
            currentListenPort: ""
        };
    },
    created() {
        this.getCurrentSettingFun();
        this.getCurrentStepFun();
    },
    methods: {
        stepClickFun(step) {
            if (step == 6) {
                if (this.p2pSetting.detail.seed_node_ip_list == null) {
                    this.$message.warning(
                        this.$t("tronP2pSeedNodeSelectNextTips")
                    );
                    return;
                }
                if (this.p2pSetting.detail.seed_node_ip_list == 0) {
                    this.$message.warning(
                        this.$t("tronP2pSeedNodeSelectNextTips")
                    );
                    return;
                }
            }
            this.currentStep = step;
            this.$store.dispatch("tronSetting/getCurrentStepConfig", {
                step
            });
            this.getCurrentSettingFun();
        },
        getCurrentStepFun() {
            let step = sessionStorage.getItem("currentstep") || 1;
            if (this.settingStep != 0) {
                step = this.settingStep;
            }
            // console.log("current", step);
            this.currentStep = Number(step);
            this.$store.dispatch("tronSetting/getCurrentStepConfig", { step });
        },

        getCurrentSettingFun() {
            this.$store
                .dispatch("tronSetting/getConfigSetting")
                .then(response => {
                    this.genesisSetting.detail = {
                        genesis_block_assets:
                            response.genesisAssetConfig.genesis_block_assets,
                        genesis_block_witnesses:
                            response.genesisWitnessConfig
                                .genesis_block_witnesses
                    };
                    // response.genesisSetting;
                    this.baseSetting.detail = response.baseSettingConfig;
                    let newIpList = [];
                    if (
                        response.p2pConfig.seed_node_ip_list &&
                        response.p2pConfig.seed_node_ip_list != null
                    ) {
                        response.p2pConfig.seed_node_ip_list.forEach(item => {
                            newIpList.push(item.split(":")[0]);
                        });
                    }
                    this.p2pSetting.detail = {
                        ...response.p2pConfig,
                        defalutSelectedIp: newIpList || []
                    };
                    this.databaseSettingForm.detail = response.dbConfig;
                    this.networkSetting.detail = response.networkConfig;
                    this.crossChainSetting.detail = response.crossChainConfig;
                    this.currentListenPort =
                        response.p2pConfig.node_listen_port;
                    if (response.p2pConfig.allNodes != null) {
                        let newIpList = [];
                        response.p2pConfig.allNodes.forEach(item => {
                            newIpList.push({
                                ip: item,
                                port: this.currentListenPort
                            });
                        });
                        this.seedNodeIpListData = newIpList || [];
                    } else {
                        this.seedNodeIpListData = [];
                    }
                })
                .catch(error => {
                    console.log(error);
                });
        },

        resetSettingFun() {
            // rest setting
            this.genesisSetting.detail = this.originSettingObj.genesisSetting;
            this.baseSetting.detail = this.originSettingObj.baseSettingConfig;
            this.p2pSetting.detail = this.originSettingObj.p2pConfig;
            this.databaseSettingForm.detial = this.originSettingObj.dbConfig;
            this.networkSetting.detail = this.originSettingObj.networkConfig;
        },

        // base setting
        baseSettingDetailFun(val) {
            this.baseSetting.visible = val;
        },

        addSettingSuccessFun(val) {
            if (val) {
                this.currentStep = this.currentStep + 1;
                if (this.currentStep > 6) {
                    // TODO
                    this.currentStep = 6;
                    this.$store.dispatch("tronSetting/getCurrentStepConfig", {
                        step: 6
                    });
                } else {
                    this.$store.dispatch("tronSetting/getCurrentStepConfig", {
                        step: this.currentStep
                    });
                }
                this.getCurrentSettingFun();
            }
        },
        previousSettingStepFun(val) {
            if (val) {
                this.currentStep = this.currentStep - 1;
                this.$store.dispatch("tronSetting/getCurrentStepConfig", {
                    step: this.currentStep
                });
                if (this.currentStep < 1) {
                    // TODO
                    this.$store.dispatch("tronSetting/getCurrentStepConfig", {
                        step: 1
                    });
                }
                this.getCurrentSettingFun();
            }
        }
    }
};
</script>
<style lang="scss" rel="stylesheet/scss" scoped>
.tabSection {
    table {
        border-left: 1px solid #ebeef5;
        border-top: 1px solid #ebeef5;
    }
    td {
        padding: 12px 0;
        border-bottom: 1px solid #ebeef5;
        border-right: 1px solid #ebeef5;
    }
    th {
        padding: 12px 0;
        border-bottom: 1px solid #ebeef5;
        border-right: 1px solid #ebeef5;
    }
}
</style>
