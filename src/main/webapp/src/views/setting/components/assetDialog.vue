<template>
    <div class="viewnodeDialog">
        <el-dialog
            center
            width="600px"
            :title="$t('tronAssetSetting')"
            :visible.sync="dialogVisible"
        >
            <el-form
                class="assetDialogForm"
                ref="assetDialogForm"
                :rules="assetRules"
                :model="assetForm"
                label-width="120px"
                label-position="left"
            >
                <el-form-item label="accountName" prop="accountName">
                    <el-input
                        size="small"
                        :maxlength="50"
                        v-model.trim="assetForm.accountName"
                        :placeholder="$t('tronAccountNamePlaceholder')"
                    ></el-input>
                </el-form-item>
                <el-form-item label="accountType" prop="accountType">
                    <el-select
                        size="small"
                        v-model="assetForm.accountType"
                        :placeholder="$t('tronNodeSRPlaceholder')"
                    >
                        <el-option
                            v-for="item in accountTypeOptions"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="address" prop="address">
                    <el-input
                        size="small"
                        :maxlength="20"
                        v-model.trim="assetForm.address"
                        :placeholder="$t('tronAddressPlaceholder')"
                    ></el-input>
                </el-form-item>
                <el-form-item label="balance" prop="balance">
                    <el-input
                        size="small"
                        :maxlength="50"
                        v-model.trim="assetForm.balance"
                        :placeholder="$t('tronBalancePlaceholder')"
                    ></el-input>
                </el-form-item>
                <el-form-item label-width="0" class="textRight">
                    <el-button
                        size="small"
                        type="primary"
                        @click="saveData('assetDialogForm')"
                    >{{ $t("tronSettingSave") }}</el-button>
                    <el-button size="small" @click="tronSettingCancel()">
                        {{
                        $t("tronSettingCancel")
                        }}
                    </el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>
<script>
import TronWeb from "tronweb";
import { genesisSettingApi } from "@/api/settingApi";
import { isvalidateNum } from "@/utils/validate.js";
export default {
    props: [
        "assetDialogVisible",
        "detailInfoData",
        "editStatus",
        "genesisBlockAssets",
        "currentIndex"
    ],
    data() {
        return {
            nodeRules: {},
            dialogVisible: this.assetDialogVisible,
            assetForm: {
                accountName: "",
                accountType: "",
                address: "",
                balance: ""
            },
            accountTypeOptions: [
                {
                    value: "AssetIssue",
                    label: "AssetIssue"
                },
                {
                    value: "Contract",
                    label: "Contract"
                }
            ]
        };
    },
    computed: {
        assetRules() {
            const validNormalNum = (rule, value, callback) => {
                if (!isvalidateNum(value)) {
                    callback(
                        new Error(this.$t("tronSettingNumberPlaceholder"))
                    );
                } else {
                    callback();
                }
            };
            const validNum = (rule, value, callback) => {
                if (
                    Number(value) < 9223372036854775807 &&
                    Number(value) > -9223372036854775808
                ) {
                    callback();
                } else {
                    callback(
                        new Error(this.$t("tronSettingNumberMaxPlaceholder"))
                    );
                }
            };
            const validAddress = (rule, value, callback) => {
                if (!TronWeb.isAddress(value)) {
                    callback(
                        new Error(this.$t("tronSettingAddressPlaceholder"))
                    );
                } else {
                    callback();
                }
            };
            const validPrivateKey = (rule, value, callback) => {
                const address = TronWeb.address.fromPrivateKey(value);
                console.log(address);
                if (!TronWeb.isAddress(address)) {
                    callback(
                        new Error(this.$t("tronSettingAddressPlaceholder"))
                    );
                } else {
                    callback();
                }
            };
            const rules = {
                accountName: [
                    {
                        required: true,
                        message: this.$t("tronAccountNamePlaceholder"),
                        trigger: "blur"
                    }
                ],
                accountType: [
                    {
                        required: true,
                        message: this.$t("tronAccountTypePlaceholder"),
                        trigger: "blur"
                    }
                ],
                address: [
                    {
                        required: true,
                        message: this.$t("tronAddressPlaceholder"),
                        trigger: "blur"
                    },
                    {
                        required: true,
                        validator: validAddress,
                        trigger: "blur"
                    }
                ],
                balance: [
                    {
                        required: true,
                        message: this.$t("tronBalancePlaceholder"),
                        trigger: "blur"
                    }
                ]
            };
            return rules;
        }
    },
    methods: {
        tronSettingCancel() {
            // cancel fun
            this.dialogVisible = false;
            this.$emit("addNodeSuccess", true);
        },
        saveData(formName) {
            this.$refs[formName].validate(valid => {
                if (valid) {
                    let genesisBlockAssetsAry = this.genesisBlockAssets;
                    if (this.editStatus == 0) {
                        genesisBlockAssetsAry.push(this.assetForm);
                    } else {
                        genesisBlockAssetsAry[
                            this.currentIndex
                        ] = this.assetForm;
                    }
                    // console.log(genesisBlockAssetsAry);
                    const newSettingForm = {
                        assets: genesisBlockAssetsAry
                    };
                    genesisSettingApi(newSettingForm)
                        .then(response => {
                            this.$emit("addAssetSuccess", true);
                            this.$message.success(
                                this.$t("tronSettingGenesisSaveSuccess")
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
            this.assetForm = val;
        },
        assetDialogVisible(val) {
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
}
.textRight {
    text-align: right;
    margin-top: 30px;
}
.nodeDialogForm {
    padding: 0 20px;
}
.item {
    i {
        font-size: 12px;
    }
}
</style>
