/* * @Author: lxm * @Date: 2019-10-15 11:03:42 * @Last Modified by: lxm * @Last
Modified time: 2019-11-12 14:40:09 * @operation node */

<template>
    <div class="viewnodeDialog">
        <el-dialog
            :title="dialogTitle"
            @open="openDialogFun"
            @close="closeFun"
            :visible.sync="dialogVisible"
            :close-on-click-modal="false"
            :close-on-press-escape="false"
            v-loading="classLoading"
            width="680px"
            top="8vh"
            center
        >
            <el-form
                ref="nodeDialogForm"
                :rules="nodeRules"
                :model="nodeForm"
                label-width="150px"
                label-position="left"
                class="nodeDialogForm"
            >
                <el-form-item prop="id">
                    <span slot="label">
                        ID
                        <el-tooltip
                            class="item"
                            effect="dark"
                            :content="$t('deploymentNodeIdTips')"
                            placement="top"
                        >
                            <i class="iconfont icon-iconset0143"></i>
                        </el-tooltip>
                    </span>
                    <el-input
                        size="small"
                        :maxlength="50"
                        v-model.trim="nodeForm.id"
                        :placeholder="$t('tronNodeIDPlaceholder')"
                        :disabled="editStatus == 1"
                    ></el-input>
                </el-form-item>
                <el-form-item prop="userName">
                    <span slot="label">
                        {{ $t("tronNodeName") }}
                        <el-tooltip
                            size="small"
                            class="item"
                            effect="dark"
                            :content="$t('deploymentNodeUsernameTips')"
                            placement="top"
                        >
                            <i class="iconfont icon-iconset0143"></i>
                        </el-tooltip>
                    </span>
                    <el-input
                        size="small"
                        :maxlength="50"
                        v-model.trim="nodeForm.userName"
                        :placeholder="$t('tronNodeNamePlaceholder')"
                    ></el-input>
                </el-form-item>
                <el-form-item prop="ip">
                    <span slot="label">
                        IP
                        <el-tooltip
                            class="item"
                            effect="dark"
                            :content="$t('deploymentNodeIpTips')"
                            placement="top"
                        >
                            <i class="iconfont icon-iconset0143"></i>
                        </el-tooltip>
                    </span>
                    <el-input
                        size="small"
                        :maxlength="50"
                        v-model.trim="nodeForm.ip"
                        :placeholder="$t('tronNodeIpPlaceholder')"
                    ></el-input>
                </el-form-item>
                <el-form-item prop="port">
                    <span slot="label">
                        PORT
                        <el-tooltip
                            class="item"
                            effect="dark"
                            :content="$t('deploymentNodePortTips')"
                            placement="top"
                        >
                            <i class="iconfont icon-iconset0143"></i>
                        </el-tooltip>
                    </span>
                    <el-input
                        size="small"
                        :maxlength="50"
                        v-model.trim="nodeForm.port"
                        :placeholder="$t('tronNodePortPlaceholder')"
                    ></el-input>
                </el-form-item>
                <el-form-item prop="isSR">
                    <span slot="label">
                        {{ $t("tronNodeWhetherIsSR") }}
                        <el-tooltip
                            class="item"
                            effect="dark"
                            :content="$t('deploymentNodeSrTips')"
                            placement="top"
                        >
                            <i class="iconfont icon-iconset0143"></i>
                        </el-tooltip>
                    </span>
                    <el-select
                        size="small"
                        v-model="nodeForm.isSR"
                        :placeholder="$t('tronNodeSRPlaceholder')"
                    >
                        <el-option
                            v-for="item in srAry"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item prop="needSyncCheck" v-if="nodeForm.isSR">
                    <span slot="label">
                        needSyncCheck
                        <el-tooltip
                            class="item"
                            effect="dark"
                            :content="$t('deploymentNodeSyncCheckTips')"
                            placement="top"
                        >
                            <i class="iconfont icon-iconset0143"></i>
                        </el-tooltip>
                    </span>
                    <el-select
                        size="small"
                        v-model="nodeForm.needSyncCheck"
                        :placeholder="$t('tronNodeSyncCheckPlaceholder')"
                    >
                        <el-option
                            v-for="item in syncCheckAry"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item prop="url" v-if="nodeForm.isSR">
                    <span slot="label">
                        URL
                        <el-tooltip
                            class="item"
                            effect="dark"
                            :content="$t('deploymentNodeUrlTips')"
                            placement="top"
                        >
                            <i class="iconfont icon-iconset0143"></i>
                        </el-tooltip>
                    </span>

                    <el-input
                        size="small"
                        :maxlength="100"
                        v-model.trim="nodeForm.url"
                        :placeholder="$t('tronNodeUrlPlaceholder')"
                    ></el-input>
                </el-form-item>
                <el-form-item prop="voteCount" v-if="nodeForm.isSR">
                    <span slot="label">
                        voteCount
                        <el-tooltip
                            class="item"
                            effect="dark"
                            :content="$t('tronNodeVoteNumberTips')"
                            placement="top"
                        >
                            <i class="iconfont icon-iconset0143"></i>
                        </el-tooltip>
                    </span>
                    <el-input
                        size="small"
                        :maxlength="20"
                        v-model.trim="nodeForm.voteCount"
                        :placeholder="$t('tronNodeVoteNumberPlaceholder')"
                    ></el-input>
                </el-form-item>
                <el-form-item v-if="editStatus == 1 && nodeForm.isSR" class="publickey">
                    <span slot="label" style="padding-left:12px">
                        publicKey
                        <el-tooltip
                            class="item"
                            effect="dark"
                            :content="$t('deploymentNodePublickKeyTips')"
                            placement="top"
                        >
                            <i class="iconfont icon-iconset0143"></i>
                        </el-tooltip>
                    </span>
                    {{ nodeForm.publicKey }}
                </el-form-item>
                <el-form-item prop="privateKey" v-if="nodeForm.isSR">
                    <span slot="label">
                        privateKey
                        <el-tooltip
                            class="item"
                            effect="dark"
                            :content="$t('deploymentNodePrivateKeyTips')"
                            placement="top"
                        >
                            <i class="iconfont icon-iconset0143"></i>
                        </el-tooltip>
                    </span>
                    <el-input
                        size="small"
                        type="textarea"
                        :maxlength="1000"
                        v-model.trim="nodeForm.privateKey"
                        :placeholder="$t('tronNodePrivateKeyPlaceholder')"
                    ></el-input>
                </el-form-item>

                <el-form-item label-width="0" class="textRight">
                    <el-button
                        size="small"
                        type="primary"
                        @click="saveData('nodeDialogForm')"
                        :loading="saveLoading"
                    >{{ $t("tronNodeSave") }}</el-button>
                    <el-button size="small" @click="cancelFun">{{ $t("tronNodeCancel") }}</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>
<script>
import { addNote, editNote } from "@/api/nodeApi";
import { checkBalanceApi } from "@/api/settingApi";
import {
    isvalidateNum,
    isCorrectIp,
    isvalidateIntegerNum
} from "@/utils/validate.js";
import TronWeb from "tronweb";
export default {
    name: "operationNode",
    props: ["nodeDialogVisible", "detailInfoData", "editStatus", "allNodes"],
    computed: {
        nodeRules() {
            const validNum = (rule, value, callback) => {
                if (!isvalidateNum(value)) {
                    callback(
                        new Error(this.$t("tronSettingNumberPlaceholder"))
                    );
                } else {
                    callback();
                }
            };
            const validvVoteNum = (rule, value, callback) => {
                if (!isvalidateIntegerNum(value)) {
                    callback(
                        new Error(this.$t("tronSettingNumberZeroPlaceholder"))
                    );
                } else {
                    callback();
                }
            };
            const validIpRule = (rule, value, callback) => {
                if (!isCorrectIp(value)) {
                    callback(new Error(this.$t("tronNodeIpValidate")));
                } else {
                    callback();
                }
            };
            const validLocalRule = (rule, value, callback) => {
                if (value == "127.0.0.1") {
                    callback(new Error(this.$t("tronNodeIpLocalValidate")));
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
            const validPrivateKey = (rule, value, callback) => {
                if (value.length != 64) {
                    callback(
                        new Error(
                            this.$t("tronNodeCorrectPrivateKeyPlaceholder")
                        )
                    );
                } else {
                    callback();
                }
                const address = TronWeb.address.fromPrivateKey(value);
                // if (!TronWeb.isAddress(address)) {
                //     callback(new Error(this.$t("tronSettingAddressPlaceholder")));
                // } else {
                //     callback();
                // }
            };
            const rules = {
                id: [
                    {
                        required: true,
                        message: this.$t("tronNodeIDPlaceholder"),
                        trigger: "blur"
                    },
                    {
                        required: true,
                        validator: validNum,
                        trigger: "blur"
                    },
                    {
                        required: true,
                        validator: validMaxNum,
                        trigger: "blur"
                    }
                ],
                userName: [
                    {
                        required: true,
                        message: this.$t("tronNodeNamePlaceholder"),
                        trigger: "blur"
                    }
                ],
                ip: [
                    {
                        required: true,
                        message: this.$t("tronNodeIpPlaceholder"),
                        trigger: "blur"
                    },
                    {
                        required: true,
                        validator: validIpRule,
                        trigger: "blur"
                    },
                    {
                        required: true,
                        validator: validLocalRule,
                        trigger: "blur"
                    }
                ],
                port: [
                    {
                        required: true,
                        message: this.$t("tronNodePortPlaceholder"),
                        trigger: "blur"
                    },
                    {
                        required: true,
                        validator: validNum,
                        trigger: "blur"
                    },
                    {
                        required: true,
                        validator: validPortNum,
                        trigger: "blur"
                    }
                ],
                isSR: {
                    required: true,
                    message: this.$t("tronNodeSRPlaceholder"),
                    trigger: "change"
                },
                needSyncCheck: {
                    required: true,
                    message: this.$t("tronNodeSyncCheckPlaceholder"),
                    trigger: "change"
                },
                url: [
                    {
                        required: true,
                        message: this.$t("tronNodeUrlPlaceholder"),
                        trigger: "blur"
                    }
                ],
                voteCount: [
                    {
                        required: true,
                        message: this.$t("tronNodeVoteNumberTips"),
                        trigger: "blur"
                    },
                    {
                        required: true,
                        validator: validvVoteNum,
                        trigger: "blur"
                    }
                ],
                privateKey: [
                    {
                        required: true,
                        message: this.$t("tronNodePrivateKeyPlaceholder"),
                        trigger: "blur"
                    },
                    {
                        required: true,
                        validator: validPrivateKey,
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
            saveLoading: false,
            dialogVisible: this.nodeDialogVisible,
            dialogTitle: this.$t("tronNodeAdd"),
            nodeForm: {
                id: "",
                userName: "",
                ip: "",
                port: "",
                isSR: ""
            },

            srAry: [
                { id: 0, label: this.$t("tronNodeSrIs"), value: true },
                { id: 1, label: this.$t("tronNodeSrNo"), value: false }
            ],
            syncCheckAry: [
                { id: 0, label: this.$t("tronNodeSrIs"), value: true },
                { id: 1, label: this.$t("tronNodeSrNo"), value: false }
            ]
        };
    },
    methods: {
        openDialogFun() {
            this.$refs.nodeDialogForm.resetFields();
        },
        closeFun() {
            this.$refs.nodeDialogForm.resetFields();
            this.dialogVisible = false;
            this.$emit("addNodeSuccess", true);
        },
        cancelFun() {
            this.$refs.nodeDialogForm.resetFields();
            this.dialogVisible = false;
            this.$emit("addNodeSuccess", true);
        },
        saveData(formName) {
            this.$refs[formName].validate(async valid => {
                if (valid) {
                    this.saveLoading = true;
                    let newForm;
                    if (this.nodeForm.url != undefined) {
                        newForm = {
                            url: `"${this.nodeForm.url}"`,
                            ...this.nodeForm
                        };
                        newForm.url = `"${newForm.url}"`;
                    } else {
                        newForm = {
                            ...this.nodeForm
                        };
                    }
                    let isValidateBalance = false;
                    await checkBalanceApi({ balance: newForm.voteCount })
                        .then(response => {
                            if (response.data) {
                                return response.data;
                            }
                        })
                        .then(res => {
                            if (res.result) {
                                isValidateBalance = true;
                            } else {
                                this.$message.error(
                                    this.$t(
                                        "tronNodevoteCountNumberMaxPlaceholder"
                                    )
                                );
                                this.saveLoading = false;
                                isValidateBalance = false;
                                return;
                            }
                        })
                        .catch(error => {
                            console.log(error);
                        });
                    if (isValidateBalance) {
                        if (
                            newForm.privateKey ==
                            "****************************************************************"
                        ) {
                            delete newForm.privateKey;
                        }
                        let isSameIp = false;

                        let allNodeAry = [];
                        if (this.allNodes.length > 0) {
                            this.allNodes.forEach(item => {
                                allNodeAry.push(item.ip);
                            });
                        }

                        if (this.editStatus == 1) {
                            // delete newForm.publicKey;
                            let allNodeSet = new Set(allNodeAry);
                            let currentNodeAry = new Set([
                                sessionStorage.getItem("currentnode")
                            ]);
                            // 差集
                            let differenceAry = new Set(
                                [...allNodeSet].filter(
                                    x => !currentNodeAry.has(x)
                                )
                            );
                            Array.from(differenceAry).forEach(item => {
                                if (item == newForm.ip) {
                                    isSameIp = true;
                                    this.$message.warning(
                                        this.$t("tronNodesIpNoSame")
                                    );
                                }
                            });
                            if (isSameIp) {
                                this.saveLoading = false;
                                return;
                            }
                            editNote(newForm)
                                .then(response => {
                                    this.$emit("addNodeSuccess", true);
                                    this.$refs.nodeDialogForm.resetFields();
                                    this.$message.success(
                                        this.$t("tronNodeEditSuccess")
                                    );
                                    this.dialogVisible = false;
                                    this.saveLoading = false;
                                })
                                .catch(error => {
                                    console.log(error);
                                    this.saveLoading = false;
                                });
                            return;
                        }

                        if (this.allNodes.length > 0) {
                            this.allNodes.forEach(item => {
                                if (item.ip == newForm.ip) {
                                    this.$message.warning(
                                        this.$t("tronNodesIpNoSame")
                                    );
                                    isSameIp = true;
                                    return;
                                }
                            });
                        }

                        if (isSameIp) {
                            this.saveLoading = false;
                            return;
                        }
                        addNote(newForm)
                            .then(response => {
                                this.$emit("addNodeSuccess", true);
                                this.$refs.nodeDialogForm.resetFields();
                                this.$message.success(
                                    this.$t("tronNodeAddSuccess")
                                );
                                this.dialogVisible = false;

                                this.saveLoading = false;
                            })
                            .catch(error => {
                                console.log(error);
                                this.saveLoading = false;
                            });
                    }
                } else {
                    console.log("error submit!!");
                    return false;
                }
            });
        }
    },
    watch: {
        editStatus(val) {
            val == 1
                ? (this.dialogTitle = this.$t("tronNodeEditTitle"))
                : (this.dialogTitle = this.$t("tronNodeAdd"));
        },
        detailInfoData(val) {
            this.nodeForm = this.detailInfoData;
        },
        nodeDialogVisible(val) {
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
