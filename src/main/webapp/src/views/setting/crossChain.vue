/*
 * @Author: lxm 
 * @Date: 2019-10-15 11:03:42 
 * @Last Modified by: lxm
 * @Last Modified time: 2019-11-05 20:53:13
 * @setting cross setting
 */

<template>
    <div class="viewBranchDialog">
        <el-form
            ref="crossSettingDialogForm"
            :rules="branchRules"
            :model="baseSettingForm"
            label-width="200px"
            class="tronbaseSettingForm"
            label-position="left"
        >
            <el-row :gutter="12">
                <el-col :span="24">
                    <el-card shadow="hover">
                        <div @click="baseContentShow = !baseContentShow">
                            <i :class="baseContentShow?'el-icon-arrow-down': 'el-icon-arrow-right'"></i>
                            {{$t('tronSettingP2p')}}
                        </div>
                        <div v-if="baseContentShow">
                            <el-form-item
                                label="enableCrossChain"
                                prop="enableCrossChain"
                                class="baseFormItem mgt20"
                            >
                                <el-switch
                                    v-model="baseSettingForm.enableCrossChain"
                                    active-color="#13ce66"
                                    inactive-color="#ff4949"
                                ></el-switch>
                            </el-form-item>
                            <el-form-item
                                label="maxValidatorNumber"
                                prop="maxValidatorNumber"
                                class="baseFormItem"
                            >
                                <el-input
                                    :maxlength="50"
                                    v-model="baseSettingForm.maxValidatorNumber"
                                    :placeholder="$t('tronSettingPlaceholder')"
                                ></el-input>
                            </el-form-item>
                            <el-form-item
                                label="minValidatorNumber"
                                prop="minValidatorNumber"
                                class="baseFormItem"
                            >
                                <el-input
                                    :maxlength="50"
                                    v-model="baseSettingForm.minValidatorNumber"
                                    :placeholder="$t('tronSettingPlaceholder')"
                                ></el-input>
                            </el-form-item>

                            <el-form-item
                                label="crossChainFee"
                                prop="crossChainFee"
                                class="baseFormItem"
                            >
                                <el-input-number
                                    controls-position="right"
                                    :min="0"
                                    :step="0.01"
                                    :maxlength="50"
                                    v-model="baseSettingForm.crossChainFee"
                                    :placeholder="$t('tronSettingPlaceholder')"
                                ></el-input-number>
                            </el-form-item>
                        </div>
                    </el-card>
                </el-col>
            </el-row>
            <el-form-item label-width="0" class="textRight">
                <el-button type="primary" @click="previousStepFun">{{$t('tronSettingPreviousStep')}}</el-button>
                <el-button
                    type="primary"
                    @click="saveData('crossSettingDialogForm')"
                >{{$t('tronSettingNextStep')}}</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
import { crossChainSettingApi } from "@/api/settingApi";
import { isvalidateNum, twoDecimal } from "@/utils/validate.js";
export default {
    name: "corssChain",
    props: ["detailInfoData"],
    data() {
        const validNum = (rule, value, callback) => {
            if (!isvalidateNum(value)) {
                callback(new Error(this.$t("tronSettingNumberPlaceholder")));
            } else {
                callback();
            }
        };
        const validTwoDecimalFun = (rule, value, callback) => {
            if (!twoDecimal(value)) {
                callback(new Error(this.$t("validTwoDecimal")));
            } else {
                callback();
            }
        };
        return {
            baseContentShow: true,
            baseSettingForm: {},
            branchRules: {
                enableCrossChain: [
                    {
                        required: true,
                        message: this.$t("tronSettingPlaceholder"),
                        trigger: "change"
                    }
                ],
                maxValidatorNumber: [
                    {
                        required: true,
                        message: this.$t("tronSettingPlaceholder"),
                        trigger: "change"
                    },
                    {
                        message: this.$t("tronSettingNumberPlaceholder"),
                        validator: validNum,
                        trigger: "blur"
                    }
                ],
                minValidatorNumber: [
                    {
                        required: true,
                        message: this.$t("tronSettingPlaceholder"),
                        trigger: "change"
                    },
                    {
                        message: this.$t("tronSettingNumberPlaceholder"),
                        validator: validNum,
                        trigger: "blur"
                    }
                ],
                crossChainFee: [
                    {
                        required: true,
                        message: this.$t("tronSettingPlaceholder"),
                        trigger: "change"
                    },
                    {
                        message: this.$t("tronSettingNumberTwoDecimal"),
                        validator: validTwoDecimalFun,
                        trigger: "blur"
                    }
                ]
            }
        };
    },
    methods: {
        previousStepFun() {
            this.$emit("previousSettingStep", true);
        },
        saveData(formName) {
            this.$refs[formName].validate(valid => {
                if (valid) {
                    crossChainSettingApi(this.baseSettingForm)
                        .then(response => {
                            this.$emit("addSettingSuccess", true);

                            this.$message.success(
                                this.$t("tronSettingCrossChainSaveSuccess")
                            );
                            this.$router.push({ path: "/plugin/list" });
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
        }
    }
};
</script>
<style lang="scss" rel="stylesheet/scss" scoped>
.textCenter {
    text-align: center;
}
.baseFormItem {
    width: 600px;
}
.textRight {
    margin-top: 40px;
    text-align: right;
}
.tronbaseSettingForm {
    padding: 0 80px 0 0;
}
</style>

