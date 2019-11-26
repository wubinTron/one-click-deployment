/* * @Author: lxm * @Date: 2019-10-15 11:03:42 * @Last Modified by: lxm * @Last
Modified time: 2019-11-12 19:01:59 * @setting genesis setting */

<template>
    <div class="viewBranchDialog">
        <div class="tronbaseSettingForm">
            <el-card shadow="hover">
                <div @click="genesisContentShow = !genesisContentShow">
                    <i
                        :class="
              genesisContentShow ? 'el-icon-arrow-down' : 'el-icon-arrow-right'
            "
                    ></i>
                    {{ $t("tronSettingGenesis") }}
                </div>
                <el-form
                    v-if="genesisContentShow"
                    ref="genesisSettingDialogForm"
                    label-width="200px"
                    class="tronbaseSettingForm"
                    label-position="left"
                >
                    <el-row :gutter="30">
                        <el-col :span="12">
                            <div class="asset">
                                <el-button class="newAsset" @click="newAssetFun()">new asset</el-button>
                                <el-row
                                    v-for="(item, index) in genesisSetting.genesis_block_assets"
                                    :key="index"
                                >
                                    <div class="assetList">
                                        <el-button
                                            @click="currentAssetFun(item, index)"
                                            style="width:100%"
                                        >{{ item.accountName }}</el-button>
                                        <i
                                            class="closeIcon el-icon-circle-close"
                                            @click="deleteCurrentAsset(index)"
                                        ></i>
                                    </div>
                                </el-row>
                            </div>
                        </el-col>
                        <el-col :span="12">
                            <div class="witeness">
                                <el-button class="newWiteness" disabled>witeness</el-button>
                                <el-row
                                    v-for="(item,
                  index) in genesisSetting.genesis_block_witnesses"
                                    :key="index"
                                >
                                    <el-button
                                        @click="currenWitenessFun(item, index)"
                                        style="width:100%"
                                    >{{ item.address }}</el-button>
                                </el-row>
                            </div>
                        </el-col>
                    </el-row>
                </el-form>
            </el-card>

            <div label-width="0" class="textRight">
                <el-button size="small" type="primary" @click="saveAllData()">
                    {{
                    $t("tronSettingNextStep")
                    }}
                </el-button>
            </div>
        </div>
        <asset-dialog
            :assetDialogVisible="innerAssetVisible"
            :editStatus="assetDialog.status"
            :detailInfoData="assetDialog.detail"
            :currentIndex="assetDialog.currentInd"
            :genesisBlockAssets="genesisSetting.genesis_block_assets"
            @dialog="assetDetailFun"
            @addAssetSuccess="addAssetSuccessFun"
        ></asset-dialog>
        <el-dialog
            center
            width="700px"
            :title="$t('tronWitenessSetting')"
            :visible.sync="innerWitenessVisible"
        >
            <el-form
                ref="witenessDialogForm"
                :model="witenessForm"
                label-width="120px"
                label-position="left"
            >
                <el-form-item label="address" prop="address">
                    {{
                    witenessForm.address
                    }}
                </el-form-item>
                <el-form-item label="url" prop="url">
                    {{
                    witenessForm.url
                    }}
                </el-form-item>
                <el-form-item label="voteCount" prop="voteCount">
                    {{
                    witenessForm.voteCount
                    }}
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>
<script>
import { genesisSettingApi } from "@/api/settingApi";
import assetDialog from "./components/assetDialog";

export default {
    name: "genesisSetting",
    components: { assetDialog },
    data() {
        return {
            classLoading: false,
            genesisContentShow: true,
            assetEditStatus: 0,

            currentWitenessEditInd: "",
            witenessEditStatus: 0,
            innerAssetVisible: false,
            innerWitenessVisible: false,
            genesisSetting: {
                genesis_block_assets: [],
                genesis_block_witnesses: []
            },
            assetDialog: {
                status: 0,
                detail: {},
                currentInd: ""
            },
            witenessForm: {
                address: "",
                url: "",
                voteCount: "",
                privateKey: ""
            }
        };
    },
    created() {
        this.getCurrentSettingFun();
    },
    methods: {
        deleteCurrentAsset(ind) {
            //delet current asset
            let assetAry = this.genesisSetting.genesis_block_assets;
            assetAry.splice(ind, 1);
            console.log(assetAry);
            const newSettingForm = {
                assets: assetAry
            };
            genesisSettingApi(newSettingForm)
                .then(response => {
                    this.$message.success(
                        this.$t("tronSettingGenesisSaveSuccess")
                    );
                })
                .catch(error => {
                    console.log(error);
                });
        },
        getCurrentSettingFun() {
            this.$store
                .dispatch("tronSetting/getConfigSetting")
                .then(response => {
                    this.genesisSetting.genesis_block_assets =
                        response.genesisAssetConfig.genesis_block_assets;
                    this.genesisSetting.genesis_block_witnesses =
                        response.genesisWitnessConfig.genesis_block_witnesses;
                })
                .catch(error => {
                    console.log(error);
                });
        },
        newAssetFun() {
            // new asset
            this.assetDialog.status = 0;
            this.assetDialog.detail = {
                accountName: "",
                accountType: "",
                address: "",
                balance: ""
            };
            this.innerAssetVisible = true;
        },
        currentAssetFun(item, ind) {
            this.assetDialog.status = 1;
            this.assetDialog.detail = { ...item };
            this.assetDialog.currentInd = ind;
            this.innerAssetVisible = true;
        },
        addAssetSuccessFun(val) {
            if (val) {
                this.getCurrentSettingFun();
            }
        },
        assetDetailFun(val) {
            this.innerAssetVisible = val;
        },

        currenWitenessFun(item, ind) {
            this.witenessForm = item;
            this.witenessEditStatus = 1;
            this.currentWitenessEditInd = ind;
            this.innerWitenessVisible = true;
        },

        saveAllData() {
            // save current data
            const newSettingForm = {
                assets: this.genesisSetting.genesis_block_assets
                // witness: this.genesisSetting.genesis_block_witnesses
            };
            // newSettingForm.witness.forEach(item => {
            //     console.log(`"${item.url}"`);
            //     item.url = `"${item.url}"`;
            // });
            genesisSettingApi(newSettingForm)
                .then(response => {
                    this.$emit("addSettingSuccess", true);
                    this.$message.success(
                        this.$t("tronSettingGenesisSaveSuccess")
                    );
                })
                .catch(error => {
                    console.log(error);
                });
        }
    }
};
</script>
<style lang="scss" rel="stylesheet/scss" scoped>
.textCenter {
    text-align: center;
}
.textRight {
    margin-top: 40px;
    text-align: right;
}
.tronbaseSettingForm {
    margin-top: 40px;
    padding: 0 80px 0 0;
}

.asset,
.witeness {
    position: relative;
    margin: 0 auto;
    // max-width: 600px;
    min-height: 200px;
    padding: 20px 15px;
    border: 1px solid #ccc;
    button {
        margin-top: 15px;
    }
}

.asset {
    .newAsset {
        position: absolute;
        left: 0;
        right: 0;
        margin: auto;
        top: -20px;
    }
}
.witeness {
    .newWiteness {
        position: absolute;
        left: 0;
        right: 0;
        margin: auto;
        top: -20px;
    }
}
.assetDialogForm {
    padding: 0 30px 0 20px;
}
.assetList {
    position: relative;
    // background: pink;
    .closeIcon {
        position: absolute;
        right: -8px;
        top: 8px;
    }
}
</style>
