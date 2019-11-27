/* * @Author: lxm * @Date: 2019-10-15 11:03:42 * @Last Modified by: lxm * @Last
Modified time: 2019-11-15 20:23:28 * @setting cross setting */

<template>
    <div class="viewBranchDialog">
        <el-form
            ref="crossSettingDialogForm"
            :rules="crossChainRules"
            :model="baseSettingForm"
            label-width="200px"
            class="tronbaseSettingForm"
            label-position="left"
        >
            <el-row :gutter="12">
                <el-col :span="24">
                    <el-card shadow="hover">
                        <div @click="baseContentShow = !baseContentShow">
                            <i
                                :class="
                  baseContentShow ? 'el-icon-arrow-down' : 'el-icon-arrow-right'
                "
                            ></i>
                            {{ $t("tronCrossChain") }}
                        </div>
                        <div v-if="baseContentShow">
                            <el-form-item
                                label="enableCrossChain"
                                prop="enableCrossChain"
                                class="baseFormItem mgt20"
                            >
                                <el-switch
                                    disabled
                                    size="small"
                                    v-model="baseSettingForm.enableCrossChain"
                                ></el-switch>
                            </el-form-item>
                            <el-form-item
                                label="maxValidatorNumber"
                                prop="maxValidatorNumber"
                                class="baseFormItem"
                            >
                                <el-input
                                    size="small"
                                    :maxlength="50"
                                    v-model.trim="baseSettingForm.maxValidatorNumber"
                                    :placeholder="$t('tronMaxValidatorNumberPlaceholder')"
                                    disabled
                                ></el-input>
                            </el-form-item>
                            <el-form-item
                                label="minValidatorNumber"
                                prop="minValidatorNumber"
                                class="baseFormItem"
                            >
                                <el-input
                                    size="small"
                                    :maxlength="50"
                                    v-model.trim="baseSettingForm.minValidatorNumber"
                                    :placeholder="$t('tronMinValidatorNumberPlaceholder')"
                                    disabled
                                ></el-input>
                            </el-form-item>

                            <el-form-item
                                label="crossChainFee"
                                prop="crossChainFee"
                                class="baseFormItem"
                            >
                                <el-input-number
                                    size="small"
                                    controls-position="right"
                                    :min="0"
                                    :step="0.01"
                                    :maxlength="50"
                                    v-model.trim="baseSettingForm.crossChainFee"
                                    :placeholder="$t('tronSettingPlaceholder')"
                                    disabled
                                ></el-input-number>
                            </el-form-item>
                        </div>
                    </el-card>
                </el-col>
            </el-row>
            <el-form-item label-width="0" class="textRight">
                <el-button size="small" type="primary" @click="previousStepFun">
                    {{
                    $t("tronSettingPreviousStep")
                    }}
                </el-button>
                <el-button
                    size="small"
                    type="primary"
                    @click="saveData('crossSettingDialogForm')"
                >{{ $t("tronSettingNextStep") }}</el-button>
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
    computed: {
        crossChainRules() {
            const validNum = (rule, value, callback) => {
                if (!isvalidateNum(value)) {
                    callback(
                        new Error(this.$t("tronSettingNumberPlaceholder"))
                    );
                } else {
                    callback();
                }
            };
            const validMaxNumRange = (rule, value, callback) => {
                if (value > 2147483647) {
                    callback(new Error(this.$t("tronNumberPlaceholder")));
                } else {
                    callback();
                }
            };
            const validMaxNum = (rule, value, callback) => {
                if (
                    this.baseSettingForm.minValidatorNumber &&
                    value < this.baseSettingForm.minValidatorNumber
                ) {
                    callback(
                        new Error(this.$t("tronSettingMaxNumberPlaceholder"))
                    );
                } else {
                    callback();
                }
            };
            const validMinNum = (rule, value, callback) => {
                if (
                    this.baseSettingForm.maxValidatorNumber &&
                    value > this.baseSettingForm.maxValidatorNumber
                ) {
                    callback(
                        new Error(this.$t("tronSettingMinNumberPlaceholder"))
                    );
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
            const rules = {
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
                        message: this.$t("tronMaxValidatorNumberPlaceholder"),
                        trigger: "change"
                    },
                    {
                        message: this.$t("tronSettingNumberPlaceholder"),
                        validator: validNum,
                        trigger: "blur"
                    },
                    {
                        message: this.$t("tronSettingMaxNumberPlaceholder"),
                        validator: validMaxNum,
                        trigger: "blur"
                    },
                    {
                        required: true,
                        validator: validMaxNumRange,
                        trigger: "blur"
                    }
                ],
                minValidatorNumber: [
                    {
                        required: true,
                        message: this.$t("tronMinValidatorNumberPlaceholder"),
                        trigger: "change"
                    },
                    {
                        message: this.$t("tronSettingNumberPlaceholder"),
                        validator: validNum,
                        trigger: "blur"
                    },
                    {
                        message: this.$t("tronSettingMinNumberPlaceholder"),
                        validator: validMinNum,
                        trigger: "blur"
                    },
                    {
                        required: true,
                        validator: validMaxNumRange,
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
                    },
                    {
                        required: true,
                        validator: validMaxNumRange,
                        trigger: "blur"
                    }
                ]
            };
            return rules;
        }
    },
    data() {
        return {
            baseContentShow: true,
            baseSettingForm: {}
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
                        .then(async response => {
                            this.$emit("addSettingSuccess", true);

                            this.$message.success(
                                this.$t("tronSettingCrossChainSaveSuccess")
                            );
                            await this.$store
                                .dispatch("user/changeRoles", "plugin")
                                .then(res => {
                                    console.log(res);
                                });
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
