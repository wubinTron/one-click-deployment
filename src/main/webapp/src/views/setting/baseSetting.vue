/*
 * @Author: lxm 
 * @Date: 2019-10-15 11:03:42 
 * @Last Modified by: lxm
 * @Last Modified time: 2019-11-03 17:17:51
 * @setting base setting 
 */

<template>
    <div class="viewBranchDialog">
        <el-form
            ref="baseSettingDialogForm"
            :rules="branchRules"
            :model="baseSettingForm"
            label-width="230px"
            class="tronbaseSettingForm"
            label-position="left"
        >
            <el-row :gutter="12">
                <el-col :span="24">
                    <el-card shadow="hover">
                        <div @click="baseContentShow = !baseContentShow">
                            <i :class="baseContentShow?'el-icon-arrow-down': 'el-icon-arrow-right'"></i>
                            {{$t('tronSettingBase')}}
                        </div>
                        <div v-if="baseContentShow">
                            <el-form-item label="chainId" prop="chainId" class="baseFormItem mgt20">
                                <el-input
                                    :maxlength="50"
                                    v-model="baseSettingForm.chainId"
                                    :placeholder="$t('tronSettingPlaceholder')"
                                ></el-input>
                            </el-form-item>
                            <el-form-item label="chainName" prop="chainName" class="baseFormItem">
                                <el-input
                                    :maxlength="50"
                                    v-model="baseSettingForm.chainName"
                                    :placeholder="$t('tronSettingPlaceholder')"
                                ></el-input>
                            </el-form-item>
                            <el-form-item
                                label="blockProducedTimeOut"
                                prop="block_proposalExpireTime"
                                class="baseFormItem"
                            >
                                <el-input
                                    :maxlength="50"
                                    v-model="baseSettingForm.block_proposalExpireTime"
                                    :placeholder="$t('tronSettingPlaceholder')"
                                ></el-input>
                            </el-form-item>
                            <el-form-item
                                class="baseFormItem"
                                label="blockMaintenanceTimeInterval"
                                prop="block_maintenanceTimeInterval"
                            >
                                <el-input
                                    :maxlength="50"
                                    v-model="baseSettingForm.block_maintenanceTimeInterval"
                                    :placeholder="$t('tronSettingPlaceholder')"
                                ></el-input>
                            </el-form-item>
                            <el-form-item
                                class="baseFormItem"
                                label="nodeBlockProducedTimeOut"
                                prop="node_blockProducedTimeOut"
                            >
                                <el-input
                                    :maxlength="50"
                                    v-model="baseSettingForm.node_blockProducedTimeOut"
                                    :placeholder="$t('tronSettingPlaceholder')"
                                ></el-input>
                            </el-form-item>
                            <el-form-item
                                class="baseFormItem"
                                label="nodeMinParticipationRate"
                                prop="node_minParticipationRate"
                            >
                                <el-input
                                    :maxlength="50"
                                    v-model="baseSettingForm.node_minParticipationRate"
                                    :placeholder="$t('tronSettingPlaceholder')"
                                ></el-input>
                            </el-form-item>
                        </div>
                    </el-card>
                </el-col>
            </el-row>
            <el-form-item label-width="0" class="textRight">
                <el-button type="primary" @click="previousStepFun">{{$t('tronSettingPreviousStep')}}</el-button>
                <el-button
                    type="primary"
                    @click="saveData('baseSettingDialogForm')"
                >{{$t('tronSettingNextStep')}}</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
import { baseSettingApi } from "@/api/settingApi";
import { isvalidateNum } from "@/utils/validate.js";
export default {
    name: "baseSetting",
    props: ["detailInfoData", "editStatus"],
    data() {
        const validNum = (rule, value, callback) => {
            if (!isvalidateNum(value)) {
                callback(new Error(this.$t("tronSettingNumberPlaceholder")));
            } else {
                callback();
            }
        };
        return {
            classLoading: false,
            baseSettingForm: {},
            baseContentShow: true,
            branchRules: {
                chainId: [
                    {
                        required: true,
                        message: this.$t("tronSettingPlaceholder"),
                        trigger: "blur"
                    },
                    {
                        message: this.$t("tronSettingNumberPlaceholder"),
                        validator: validNum,
                        trigger: "blur"
                    }
                ],
                chainName: [
                    {
                        required: true,
                        message: this.$t("tronSettingPlaceholder"),
                        trigger: "blur"
                    }
                ],
                block_proposalExpireTime: [
                    {
                        required: true,
                        message: this.$t("tronSettingPlaceholder"),
                        trigger: "blur"
                    },
                    {
                        message: this.$t("tronSettingNumberPlaceholder"),
                        validator: validNum,
                        trigger: "blur"
                    }
                ],
                block_maintenanceTimeInterval: [
                    {
                        required: true,
                        message: this.$t("tronSettingPlaceholder"),
                        trigger: "blur"
                    },
                    {
                        message: this.$t("tronSettingNumberPlaceholder"),
                        validator: validNum,
                        trigger: "blur"
                    }
                ],
                node_blockProducedTimeOut: [
                    {
                        required: true,
                        message: this.$t("tronSettingPlaceholder"),
                        trigger: "blur"
                    },
                    {
                        message: this.$t("tronSettingNumberPlaceholder"),
                        validator: validNum,
                        trigger: "blur"
                    }
                ],
                node_minParticipationRate: [
                    {
                        required: true,
                        message: this.$t("tronSettingPlaceholder"),
                        trigger: "blur"
                    },
                    {
                        message: this.$t("tronSettingNumberPlaceholder"),
                        validator: validNum,
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
    width: 500px;
}
</style>

