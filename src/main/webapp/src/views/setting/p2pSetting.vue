/*
 * @Author: lxm 
 * @Date: 2019-10-15 11:03:42 
 * @Last Modified by: lxm
 * @Last Modified time: 2019-11-06 14:34:50
 * @setting p2p setting 
 */

<template>
    <div class="tronp2pSettingForm">
        <el-form
            ref="p2pSettingDialogForm"
            :rules="p2pSettingRules"
            :model="p2pSettingForm"
            label-width="250px"
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
                                label="p2pVersion"
                                prop="node_p2p_version"
                                class="baseFormItem mgt20"
                            >
                                <el-input
                                    :maxlength="50"
                                    v-model="p2pSettingForm.node_p2p_version"
                                    :placeholder="$t('tronSettingPlaceholder')"
                                ></el-input>
                            </el-form-item>
                            <el-form-item
                                label="maxActionNodes"
                                prop="node_maxActiveNodes"
                                class="baseFormItem"
                            >
                                <el-input
                                    :maxlength="50"
                                    v-model="p2pSettingForm.node_maxActiveNodes"
                                    :placeholder="$t('tronSettingPlaceholder')"
                                ></el-input>
                            </el-form-item>
                            <el-form-item
                                class="baseFormItem"
                                label="node_maxActiveNodesWithSameIp"
                                prop="node_maxActiveNodesWithSameIp"
                            >
                                <el-input
                                    :maxlength="50"
                                    v-model="p2pSettingForm.node_maxActiveNodesWithSameIp"
                                    :placeholder="$t('tronSettingPlaceholder')"
                                ></el-input>
                            </el-form-item>
                            <el-form-item
                                class="baseFormItem"
                                label="activeConnectFactor"
                                prop="node_activeConnectFactor"
                            >
                                <el-input-number
                                    controls-position="right"
                                    :min="0"
                                    :step="0.1"
                                    :maxlength="50"
                                    v-model="p2pSettingForm.node_activeConnectFactor"
                                    :placeholder="$t('tronSettingPlaceholder')"
                                ></el-input-number>
                            </el-form-item>
                            <el-form-item
                                class="baseFormItem"
                                label="connectFactor"
                                prop="connectFactor"
                            >
                                <el-input-number
                                    controls-position="right"
                                    :min="0"
                                    :step="0.1"
                                    :maxlength="50"
                                    v-model="p2pSettingForm.connectFactor"
                                    :placeholder="$t('tronSettingPlaceholder')"
                                ></el-input-number>
                            </el-form-item>
                            <el-form-item class="baseFormItem" label="seedNode" prop="seedNode">
                                <el-checkbox-group
                                    v-model="p2pSettingForm.defalutSelectedIp"
                                    @change="checkBoxChangeFun"
                                >
                                    <el-checkbox
                                        class="checkBox"
                                        v-for="(item,ind) in seedNodeIpList"
                                        :key="ind"
                                        :label="item.ip"
                                    >
                                        <el-input
                                            size="small"
                                            :placeholder="$t('tronSettingPortPlaceholder')"
                                            v-model="item.port"
                                            disabled
                                        >
                                            <template slot="prepend" style="width:100px">{{item.ip}}</template>
                                        </el-input>
                                    </el-checkbox>
                                </el-checkbox-group>
                            </el-form-item>
                        </div>
                    </el-card>
                </el-col>
            </el-row>
            <el-form-item label-width="0" class="textRight">
                <el-button type="primary" @click="previousStepFun">{{$t('tronSettingPreviousStep')}}</el-button>
                <el-button
                    type="primary"
                    @click="saveData('p2pSettingDialogForm')"
                >{{$t('tronSettingNextStep')}}</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
import { mapGetters } from "vuex";
import { p2pSettingApi } from "@/api/settingApi";
import { isvalidateNum } from "@/utils/validate.js";
export default {
    name: "p2pSettingDialog",
    props: ["detailInfoData", "seedNodeIpList"],
    computed: {
        ...mapGetters(["tronSetting"])
    },
    data() {
        const validNum = (rule, value, callback) => {
            if (!isvalidateNum(value)) {
                callback(new Error(this.$t("tronSettingNumberPlaceholder")));
            } else {
                callback();
            }
        };
        return {
            baseContentShow: true,
            p2pSettingForm: {
                node_p2p_version: "",
                node_maxActiveNodes: "",
                node_maxActiveNodesWithSameIp: "",
                connectFactor: "",
                node_activeConnectFactor: ""
            },

            checkedSeedNodeList: [],
            p2pSettingRules: {
                node_p2p_version: [
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
                node_maxActiveNodes: [
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
                node_maxActiveNodesWithSameIp: [
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
                node_activeConnectFactor: [
                    {
                        required: true,
                        message: this.$t("tronSettingPlaceholder"),
                        trigger: "blur"
                    }
                ],
                connectFactor: [
                    {
                        required: true,
                        message: this.$t("tronSettingPlaceholder"),
                        trigger: "blur"
                    }
                ]
            }
        };
    },
    // created() {
    //     // this.getOriginSettingFun();
    // },
    methods: {
        previousStepFun() {
            this.$emit("previousSettingStep", true);
        },

        checkBoxChangeFun(val) {
            console.log(val);
            this.checkedSeedNodeList = val;
        },

        saveData(formName) {
            this.$refs[formName].validate(valid => {
                if (valid) {
                    const newp2pForm = {
                        p2pVersion: this.p2pSettingForm.node_p2p_version,
                        maxActiveNodes: this.p2pSettingForm.node_maxActiveNodes,
                        nodeActiveConnectFactor: this.p2pSettingForm
                            .node_activeConnectFactor,
                        nodeMaxActiveNodesWithSameIp: this.p2pSettingForm
                            .node_maxActiveNodesWithSameIp,
                        connectFactor: this.p2pSettingForm.connectFactor
                    };

                    let nodeList = this.p2pSettingForm.seed_node_ip_list;
                    let passNodeData = [];
                    let currentCheckAry = [];
                    if (nodeList && nodeList != null) {
                        nodeList.forEach(item => {
                            currentCheckAry.push(item.split(":")[0]);
                        });
                        this.checkedSeedNodeList = Array.from(
                            new Set(
                                this.checkedSeedNodeList.concat(currentCheckAry)
                            )
                        );
                    }

                    this.seedNodeIpList.forEach(item => {
                        this.checkedSeedNodeList.forEach(checkedItem => {
                            if (checkedItem == item.ip) {
                                passNodeData.push(item);
                            }
                        });
                    });

                    let newPassNodeData = [];
                    passNodeData.forEach(item => {
                        newPassNodeData.push(`${item.ip}":"${item.port}`);
                    });
                    p2pSettingApi(newp2pForm, newPassNodeData)
                        .then(response => {
                            this.$emit("addSettingSuccess", true);
                            this.$message.success(
                                this.$t("tronSettingp2pSaveSuccess")
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
            this.p2pSettingForm = {
                ...this.detailInfoData
            };
            console.log(this.p2pSettingForm);
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
.tronp2pSettingForm {
    padding: 0 70px 0 0;
}
.checkBox {
    min-width: 180px;
}
.baseFormItem {
    width: 600px;
}
</style>

