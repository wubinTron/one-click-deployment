/* * @Author: lxm * @Date: 2019-10-15 11:03:42 * @Last Modified by: lxm * @Last
Modified time: 2019-11-12 14:35:02 * @setting cross setting */

<template>
    <div class="viewBranchDialog">
        <el-form
            ref="crossSettingDialogForm"
            :rules="networkRules"
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
                            {{ $t("tronSettingHttp") }}
                        </div>
                        <div v-if="baseContentShow">
                            <el-form-item
                                label="maxHttpConnectNumber"
                                prop="node_maxHttpConnectNumber"
                                class="baseFormItem mgt20"
                            >
                                <el-input
                                    size="small"
                                    :maxlength="50"
                                    v-model.trim="baseSettingForm.node_maxHttpConnectNumber"
                                    :placeholder="$t('tronmaxHttpConnectNumberPlaceholder')"
                                ></el-input>
                            </el-form-item>
                            <el-form-item
                                class="baseFormItem"
                                label="httpFullNodePort"
                                prop="node_http_fullNodePort"
                            >
                                <el-input
                                    size="small"
                                    :maxlength="50"
                                    v-model.trim="baseSettingForm.node_http_fullNodePort"
                                    :placeholder="$t('tronhttpFullNodePortPlaceholder')"
                                ></el-input>
                            </el-form-item>
                            <el-form-item
                                class="baseFormItem"
                                label="httpSolidityPort"
                                prop="node_http_solidityPort"
                            >
                                <el-input
                                    size="small"
                                    :maxlength="50"
                                    v-model.trim="baseSettingForm.node_http_solidityPort"
                                    :placeholder="$t('tronhttpHttpSolidityPortPlaceholder')"
                                ></el-input>
                            </el-form-item>

                            <el-form-item class="baseFormItem" label="rpcPort" prop="node_rpc_port">
                                <el-input
                                    size="small"
                                    :maxlength="50"
                                    v-model.trim="baseSettingForm.node_rpc_port"
                                    :placeholder="$t('tronhttpRpcPortPlaceholder')"
                                ></el-input>
                            </el-form-item>
                            <el-form-item
                                class="baseFormItem"
                                label="rpcSolidityPort"
                                prop="node_rpc_solidityPort"
                            >
                                <el-input
                                    size="small"
                                    :maxlength="50"
                                    v-model.trim="baseSettingForm.node_rpc_solidityPort"
                                    :placeholder="$t('tronhttpRpcSolidityPortPlaceholder')"
                                ></el-input>
                            </el-form-item>
                            <!-- <el-form-item
                class="baseFormItem"
                label="listenPort"
                prop="node_listen_port"
              >
                <el-input
                  size="small"
                  :maxlength="50"
                  v-model.trim="baseSettingForm.node_listen_port"
                  :placeholder="$t('tronhttpRpcListenPortPlaceholder')"
                            ></el-input>-->
                            <!-- </el-form-item> -->
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
import { networkSettingApi } from "@/api/settingApi";
import { isvalidateNum } from "@/utils/validate.js";
export default {
    name: "networkSetting",
    props: ["detailInfoData"],
    computed: {
        networkRules() {
            const validNum = (rule, value, callback) => {
                if (!isvalidateNum(value)) {
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
            const validPortNum = (rule, value, callback) => {
                if (value > 65535) {
                    callback(new Error(this.$t("tronPortNumberPlaceholder")));
                } else {
                    callback();
                }
            }
            const rules = {
                node_maxHttpConnectNumber: [
                    {
                        required: true,
                        message: this.$t("tronmaxHttpConnectNumberPlaceholder"),
                        trigger: "blur"
                    },
                    {
                        message: this.$t("tronSettingNumberPlaceholder"),
                        validator: validNum,
                        trigger: "blur"
                    },
                    {
                        required: true,
                        validator: validMaxNum,
                        trigger: "blur"
                    }
                ],
                node_http_fullNodePort: [
                    {
                        required: true,
                        message: this.$t("tronhttpFullNodePortPlaceholder"),
                        trigger: "blur"
                    },
                    {
                        message: this.$t("tronSettingNumberPlaceholder"),
                        validator: validNum,
                        trigger: "blur"
                    },
                    {
                        required: true,
                        validator: validPortNum,
                        trigger: "blur"
                    }
                ],
                node_http_solidityPort: [
                    {
                        required: true,
                        message: this.$t("tronhttpHttpSolidityPortPlaceholder"),
                        trigger: "blur"
                    },
                    {
                        message: this.$t("tronSettingNumberPlaceholder"),
                        validator: validNum,
                        trigger: "blur"
                    },
                    {
                        required: true,
                        validator: validPortNum,
                        trigger: "blur"
                    }
                ],
                node_rpc_port: [
                    {
                        required: true,
                        message: this.$t("tronhttpRpcPortPlaceholder"),
                        trigger: "blur"
                    },
                    {
                        message: this.$t("tronSettingNumberPlaceholder"),
                        validator: validNum,
                        trigger: "blur"
                    },
                    {
                        required: true,
                        validator: validPortNum,
                        trigger: "blur"
                    }
                ],
                node_rpc_solidityPort: [
                    {
                        required: true,
                        message: this.$t("tronhttpRpcSolidityPortPlaceholder"),
                        trigger: "blur"
                    },
                    {
                        message: this.$t("tronSettingNumberPlaceholder"),
                        validator: validNum,
                        trigger: "blur"
                    },
                    {
                        required: true,
                        validator: validPortNum,
                        trigger: "blur"
                    }
                ]
                // node_listen_port: [
                //   {
                //     required: true,
                //     message: this.$t("tronhttpRpcListenPortPlaceholder"),
                //     trigger: "blur"
                //   },
                //   {
                //     message: this.$t("tronSettingNumberPlaceholder"),
                //     validator: validNum,
                //     trigger: "blur"
                //   },
                //   {
                //     required: true,
                //     validator: validMaxNum,
                //     trigger: "blur"
                //   }
                // ]
            };
            return rules;
        }
    },
    data() {
        return {
            baseContentShow: true,
            baseSettingForm: {
                node_maxHttpConnectNumber: "",
                node_http_solidityPort: "",
                node_http_fullNodePort: "",
                node_rpc_port: "",
                node_rpc_solidityPort: ""
                // listenPort: ""
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
                    const newSettingForm = {
                        maxHttpConnectNumber: this.baseSettingForm
                            .node_maxHttpConnectNumber,
                        rpcPort: this.baseSettingForm.node_rpc_port,
                        rpcSolidityPort: this.baseSettingForm
                            .node_rpc_solidityPort,
                        httpFullNodePort: this.baseSettingForm
                            .node_http_fullNodePort,
                        httpSolidityPort: this.baseSettingForm
                            .node_http_solidityPort
                        // listenPort: this.baseSettingForm.node_listen_port
                    };
                    networkSettingApi(newSettingForm)
                        .then(response => {
                            this.$emit("addSettingSuccess", true);
                            // this.$refs.crossSettingDialogForm.resetFields();
                            this.$message.success(
                                this.$t("tronSettingNetworkSaveSuccess")
                            );
                            this.dialogVisible = false;
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
    width: 600px;
}
</style>
