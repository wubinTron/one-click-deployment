/*
 * @Author: lxm 
 * @Date: 2019-10-15 11:03:42 
 * @Last Modified by: lxm
 * @Last Modified time: 2019-11-08 20:27:30
 * @setting genesis setting
 */

<template>
    <div class="viewBranchDialog">
        <el-dialog
            :title="dialogTitle"
            @open="openDialogFun"
            @close="closeFun"
            :visible.sync="dialogVisible"
            :close-on-click-modal="false"
            :close-on-press-escape="false"
            v-loading="classLoading"
            width="800px"
            center
        >
            <el-form
                ref="pluginsDialogForm"
                label-width="200px"
                :rules="pluginSettingRules"
                :model="pluginForm"
                class="tronbaseSettingForm"
                label-position="left"
            >
                <div class="onsensus">
                    <el-button class="newonsensus">{{$t('tronPluginConsensusModule')}}</el-button>
                    <el-radio-group v-model="pluginForm.onsensus">
                        <el-radio :label="'dpos'">DPOS</el-radio>
                        <el-radio :label="'PBFT'">PBFT</el-radio>
                        <br />
                        <el-radio
                            style="margin:15px 0"
                            :label="3"
                        >{{$t('tronPluginCustomConsensusModule')}}：</el-radio>
                    </el-radio-group>
                    <el-input
                        :maxlength="50"
                        v-model="pluginForm.onsensusContent"
                        :placeholder="$t('tronSettingPlaceholder')"
                    ></el-input>
                </div>
                <el-form class="transaction" prop="transaction">
                    <el-button class="newtransaction">{{$t('tronPluginTransactionModule')}}</el-button>
                    <el-checkbox-group v-model="pluginForm.transaction">
                        <el-checkbox label="AccountUpdate"></el-checkbox>
                        <el-checkbox style="margin:0 66px" label="TransferaSSET"></el-checkbox>
                        <el-checkbox label="VoteWitness"></el-checkbox>
                        <el-checkbox style="margin:10px 0" label="AccountCreate"></el-checkbox>
                        <el-checkbox style="margin:10px 68px 10px 100px" label="UnfreezeAsset"></el-checkbox>
                        <el-checkbox label="FreezeBalance"></el-checkbox>
                        <el-checkbox label="UpdateAsset"></el-checkbox>
                        <el-checkbox style="margin:0 82px" label="SetAccountId"></el-checkbox>
                        <br />
                        <el-checkbox
                            style="margin:15px 0"
                            :label="9"
                        >{{$t('tronPluginCustomTradingModule')}}</el-checkbox>
                        <el-input
                            :maxlength="50"
                            v-model="pluginForm.transactionContent"
                            :placeholder="$t('tronSettingPlaceholder')"
                        ></el-input>
                    </el-checkbox-group>
                </el-form>
                <div class="dbModal">
                    <el-button class="newBbModal">{{$t('tronPluginDatabaseModule')}}</el-button>
                    <el-radio-group v-model="pluginForm.dbsetting">
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
                        v-model="pluginForm.dbsettingContent"
                        :placeholder="$t('tronSettingPlaceholder')"
                    ></el-input>
                </div>
                <el-form-item label-width="0" class="textRight">
                    <el-button
                        size="small"
                        type="primary"
                        @click="saveData('pluginsDialogForm')"
                    >{{$t('tronPluginInputSave')}}</el-button>
                    <el-button size="small" @click="cancelFun">{{$t('tronPluginInputCancel')}}</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>
<script>
import { dbEngineApi, transactionApi, consensusApi } from "@/api/pluginApi";
export default {
    name: "baseSetting",
    props: ["genesisDialogVisible", "detailInfoData"],
    data() {
        return {
            classLoading: false,
            dialogVisible: this.genesisDialogVisible,
            dialogTitle: this.$t("tronPluginImport"),
            pluginForm: {
                onsensus: "dpos",
                transaction: [],
                dbsetting: "leveldb",
                onsensusContent: "",
                transactionContent: "",
                dbsettingContent: ""
            },
            pluginSettingRules: {
                transaction: [
                    {
                        required: true,
                        message: this.$t("tronSettingPlaceholder"),
                        trigger: "blur"
                    }
                ]
            }
        };
    },
    methods: {
        openDialogFun() {},
        closeFun() {
            // this.$refs.pluginsDialogForm.resetFields();
            this.dialogVisible = false;
        },
        cancelFun() {
            // this.$refs.pluginsDialogForm.resetFields();
            this.dialogVisible = false;
        },
        saveData(formName) {
            this.$refs[formName].validate(async valid => {
                if (valid) {
                    if (this.pluginForm.onsensus == 3) {
                        this.pluginForm.onsensus = this.pluginForm.onsensusContent;
                    }
                    if (this.pluginForm.transactionContent != "") {
                        this.pluginForm.transaction.forEach((item, ind) => {
                            if (item === 9) {
                                this.pluginForm.transaction.splice(ind, 1);
                            }
                        });
                        this.pluginForm.transaction.push(
                            this.pluginForm.transactionContent
                        );
                    }
                    if (this.pluginForm.dbsetting == 3) {
                        this.pluginForm.dbsetting = this.pluginForm.dbsettingContent;
                    }
                    console.log(this.pluginForm);
                    await consensusApi({ onsensus: this.pluginForm.onsensus })
                        .then(response => {})
                        .catch(error => {
                            console.log(error);
                        });
                    await transactionApi(this.pluginForm.transaction)
                        .then(response => {})
                        .catch(error => {
                            console.log(error);
                        });
                    dbEngineApi({ dbEngine: this.pluginForm.dbsetting })
                        .then(response => {
                            this.$message.success(
                                this.$t("tronPluginInputSaveSuccess")
                            );
                            this.dialogVisible = false;
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
    },
    watch: {
        detailInfoData(val) {
            this.baseSettingForm = this.detailInfoData;
        },
        genesisDialogVisible(val) {
            this.dialogVisible = val;
        },
        dialogVisible(val) {
            this.$emit("dialog", val);
        }
    }
};
</script>
<style lang="scss" rel="stylesheet/scss" scoped>
.textCenter {
    text-align: center;
    margin-top: 20px;
}
.textRight {
    text-align: right;
    margin-top: 30px;
}
.tronbaseSettingForm {
    padding: 0;
}
.onsensus,
.transaction,
.dbModal {
    position: relative;
    margin: 0 auto;
    width: 600px;
    padding: 30px 15px;
    border: 1px solid #ccc;
    button {
        margin-top: 15px;
    }
}
.transaction {
    margin: 50px auto 50px;
}
.onsensus {
    .newonsensus {
        position: absolute;
        left: 0;
        right: 0;
        margin: auto;
        top: -20px;
    }
}
.transaction {
    .newtransaction {
        position: absolute;
        left: 0;
        right: 0;
        margin: auto;
        top: -20px;
    }
}
.dbModal {
    .newBbModal {
        position: absolute;
        left: 0;
        right: 0;
        margin: auto;
        top: -20px;
    }
}
</style>

