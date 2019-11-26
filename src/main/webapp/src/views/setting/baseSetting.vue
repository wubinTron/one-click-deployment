/* * @Author: lxm * @Date: 2019-10-15 11:03:42 * @Last Modified by: lxm * @Last
Modified time: 2019-11-12 14:34:08 * @setting base setting */

<template>
    <div class="viewBranchDialog">
        <el-form
            ref="baseSettingDialogForm"
            :rules="baseRules"
            :model="baseSettingForm"
            label-width="230px"
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
                            {{ $t("tronSettingBase") }}
                        </div>
                        <div v-if="baseContentShow">
                            <el-form-item label="chainId" prop="chainId" class="baseFormItem mgt20">
                                <el-input
                                    size="small"
                                    :maxlength="50"
                                    v-model.trim="baseSettingForm.chainId"
                                    :placeholder="$t('tronChainIdPlaceholder')"
                                ></el-input>
                            </el-form-item>
                            <el-form-item label="chainName" prop="chainName" class="baseFormItem">
                                <el-input
                                    size="small"
                                    :maxlength="50"
                                    v-model.trim="baseSettingForm.chainName"
                                    :placeholder="$t('tronChainNamePlaceholder')"
                                ></el-input>
                            </el-form-item>

                            <el-form-item
                                class="baseFormItem"
                                label="MaintenanceTimeInterval"
                                prop="block_maintenanceTimeInterval"
                            >
                                <el-input
                                    size="small"
                                    :maxlength="50"
                                    v-model.trim="baseSettingForm.block_maintenanceTimeInterval"
                                    :placeholder="
                    $t('tronblockBlockMaintenanceTimeIntervalPlaceholder')
                  "
                                ></el-input>
                            </el-form-item>
                            <el-button
                                type="text"
                                size="small"
                                @click="moreSetting = !moreSetting"
                            >{{ $t("tronMoreSetting") }}</el-button>
                            <div v-if="moreSetting">
                                <el-form-item
                                    label="blockProposalExpireTime"
                                    prop="block_proposalExpireTime"
                                    class="baseFormItem"
                                >
                                    <el-input
                                        size="small"
                                        :maxlength="50"
                                        v-model.trim="baseSettingForm.block_proposalExpireTime"
                                        :placeholder="$t('tronblockProducedTimeOutePlaceholder')"
                                    ></el-input>
                                </el-form-item>
                                <el-form-item
                                    class="baseFormItem"
                                    label="nodeBlockProducedTimeOut"
                                    prop="node_blockProducedTimeOut"
                                >
                                    <el-input
                                        size="small"
                                        :maxlength="50"
                                        v-model.trim="baseSettingForm.node_blockProducedTimeOut"
                                        :placeholder="
                      $t('tronblockNodeBlockProducedTimeOutPlaceholder')
                    "
                                    ></el-input>
                                </el-form-item>
                                <el-form-item
                                    class="baseFormItem"
                                    label="nodeMinParticipationRate"
                                    prop="node_minParticipationRate"
                                >
                                    <el-input
                                        size="small"
                                        :maxlength="50"
                                        v-model.trim="baseSettingForm.node_minParticipationRate"
                                        :placeholder="
                      $t('tronblockNodeMinParticipationRatePlaceholder')
                    "
                                    ></el-input>
                                </el-form-item>
                            </div>
                        </div>
                    </el-card>
                </el-col>
            </el-row>
            <el-form-item label-width="0" class="textRight">
                <el-button type="primary" size="small" @click="previousStepFun">
                    {{
                    $t("tronSettingPreviousStep")
                    }}
                </el-button>
                <el-button
                    type="primary"
                    size="small"
                    @click="saveData('baseSettingDialogForm')"
                >{{ $t("tronSettingNextStep") }}</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
import { baseSettingApi } from "@/api/settingApi";
import { isvalidateNum, isvalidateIntegerNum } from "@/utils/validate.js";
export default {
    name: "baseSetting",
    props: ["detailInfoData", "editStatus"],
    computed: {
        baseRules() {
            const validateNum = (rule, value, callback) => {
                if (!isvalidateNum(value)) {
                    callback(
                        new Error(this.$t("tronSettingNumberZeroPlaceholder"))
                    );
                } else {
                    callback();
                }
            };
            const validNum = (rule, value, callback) => {
                if (!isvalidateIntegerNum(value)) {
                    callback(
                        new Error(this.$t("tronSettingNumberPlaceholder"))
                    );
                } else {
                    callback();
                }
            };
            const validMaxNum = (rule, value, callback) => {
                if (value > 2147483647) {
                    callback(new Error(this.$t("tronNumberPlaceholder")));
                } else {
                    callback();
                }
            };
            const rules = {
                chainId: [
                    {
                        required: true,
                        message: this.$t("tronChainIdPlaceholder"),
                        trigger: "blur"
                    },
                    {
                        message: this.$t("tronSettingNumberPlaceholder"),
                        validator: validateNum,
                        trigger: "blur"
                    },
                    {
                        required: true,
                        validator: validMaxNum,
                        trigger: "blur"
                    }
                ],
                chainName: [
                    {
                        required: true,
                        message: this.$t("tronChainNamePlaceholder"),
                        trigger: "blur"
                    }
                ],
                block_proposalExpireTime: [
                    {
                        required: true,
                        message: this.$t(
                            "tronblockProducedTimeOutePlaceholder"
                        ),
                        trigger: "blur"
                    },
                    {
                        message: this.$t("tronSettingNumberPlaceholder"),
                        validator: validateNum,
                        trigger: "blur"
                    },
                    {
                        required: true,
                        validator: validMaxNum,
                        trigger: "blur"
                    }
                ],
                block_maintenanceTimeInterval: [
                    {
                        required: true,
                        message: this.$t(
                            "tronblockBlockMaintenanceTimeIntervalPlaceholder"
                        ),
                        trigger: "blur"
                    },
                    {
                        message: this.$t("tronSettingNumberPlaceholder"),
                        validator: validateNum,
                        trigger: "blur"
                    },
                    {
                        required: true,
                        validator: validMaxNum,
                        trigger: "blur"
                    }
                ],
                node_blockProducedTimeOut: [
                    {
                        required: true,
                        message: this.$t(
                            "tronblockNodeBlockProducedTimeOutPlaceholder"
                        ),
                        trigger: "blur"
                    },
                    {
                        message: this.$t("tronSettingNumberPlaceholder"),
                        validator: validateNum,
                        trigger: "blur"
                    },
                    {
                        required: true,
                        validator: validMaxNum,
                        trigger: "blur"
                    }
                ],
                node_minParticipationRate: [
                    {
                        required: true,
                        message: this.$t(
                            "tronblockNodeMinParticipationRatePlaceholder"
                        ),
                        trigger: "blur"
                    },
                    {
                        message: this.$t("tronSettingNumberZeroPlaceholder"),
                        validator: validNum,
                        trigger: "blur"
                    },
                    {
                        required: true,
                        validator: validMaxNum,
                        trigger: "blur"
                    }
                ]
            };
            return rules;
        }
    },
    data() {
        return {
            classLoading: false,
            moreSetting: false,
            baseSettingForm: {},
            baseContentShow: true
        };
    },
    methods: {
        previousStepFun() {
            this.$emit("previousSettingStep", true);
        },
        saveData(formName) {
            this.$refs[formName].validate(valid => {
                if (valid) {
                    console.log(this.baseSettingForm);
                    const newBaseSettingForm = {
                        chainId: this.baseSettingForm.chainId,
                        chainName: this.baseSettingForm.chainName,
                        blockProducedTimeOut: this.baseSettingForm
                            .node_blockProducedTimeOut,
                        maintenanceTimeInterval: this.baseSettingForm
                            .block_maintenanceTimeInterval,
                        proposalExpireTime: this.baseSettingForm
                            .block_proposalExpireTime,
                        minParticipationRate: this.baseSettingForm
                            .node_minParticipationRate
                    };
                    baseSettingApi(newBaseSettingForm)
                        .then(response => {
                            this.$emit("addSettingSuccess", true);
                            // this.$refs.baseSettingDialogForm.resetFields();
                            this.$message.success(
                                this.$t("tronSettingBaseSaveSuccess")
                            );
                        })
                        .catch(error => {
                            // this.listLoading = false;
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
.textRight {
    margin-top: 40px;
    text-align: right;
}
.tronbaseSettingForm {
    padding: 0 80px 0 0;
}
.baseFormItem {
    width: 580px;
}
</style>
