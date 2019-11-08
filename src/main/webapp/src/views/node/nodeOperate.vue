/*
 * @Author: lxm 
 * @Date: 2019-10-15 11:03:42 
 * @Last Modified by: lxm
 * @Last Modified time: 2019-11-06 17:15:24
 * @operation node 
 */

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
            center
        >
            <el-form
                ref="nodeDialogForm"
                :rules="nodeRules"
                :model="nodeForm"
                label-width="155px"
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
                        :maxlength="50"
                        v-model="nodeForm.id"
                        :placeholder="$t('tronNodeIDPlaceholder')"
                        :disabled="editStatus ==1"
                    ></el-input>
                </el-form-item>
                <el-form-item prop="userName">
                    <span slot="label">
                        {{$t('tronNodeName')}}
                        <el-tooltip
                            class="item"
                            effect="dark"
                            :content="$t('deploymentNodeUsernameTips')"
                            placement="top"
                        >
                            <i class="iconfont icon-iconset0143"></i>
                        </el-tooltip>
                    </span>
                    <el-input
                        :maxlength="50"
                        v-model="nodeForm.userName"
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
                        :maxlength="50"
                        v-model="nodeForm.ip"
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
                        :maxlength="50"
                        v-model="nodeForm.port"
                        :placeholder="$t('tronNodePortPlaceholder')"
                    ></el-input>
                </el-form-item>
                <el-form-item prop="isSR">
                    <span slot="label">
                        {{$t('tronNodeWhetherIsSR')}}
                        <el-tooltip
                            class="item"
                            effect="dark"
                            :content="$t('deploymentNodeSrTips')"
                            placement="top"
                        >
                            <i class="iconfont icon-iconset0143"></i>
                        </el-tooltip>
                    </span>
                    <el-select v-model="nodeForm.isSR" :placeholder="$t('tronNodeSRPlaceholder')">
                        <el-option
                            v-for="item in srAry"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="url" prop="url" v-if="nodeForm.isSR">
                    <el-input
                        :maxlength="100"
                        v-model="nodeForm.url"
                        :placeholder="$t('tronNodeUrlPlaceholder')"
                    ></el-input>
                </el-form-item>
                <el-form-item prop="voteNumber" v-if="nodeForm.isSR">
                    <span slot="label">
                        voteNumber
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
                        :maxlength="100"
                        v-model="nodeForm.voteNumber"
                        :placeholder="$t('tronNodeVoteNumberPlaceholder')"
                    ></el-input>
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
                        type="textarea"
                        :maxlength="1000"
                        v-model="nodeForm.privateKey"
                        :placeholder="$t('tronNodePrivateKeyPlaceholder')"
                    ></el-input>
                </el-form-item>

                <el-form-item label-width="0" class="textCenter">
                    <el-button
                        type="primary"
                        @click="saveData('nodeDialogForm')"
                        :loading="saveLoading"
                    >{{$t('tronNodeSave')}}</el-button>
                    <el-button @click="cancelFun">{{$t('tronNodeCancel')}}</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>
<script>
import { addNote, editNote } from "@/api/nodeApi";
import { isvalidateNum } from "@/utils/validate.js";
import TronWeb from "tronweb";
export default {
    name: "operationNode",
    props: ["nodeDialogVisible", "detailInfoData", "editStatus"],
    data() {
        const validNum = (rule, value, callback) => {
            if (!isvalidateNum(value)) {
                callback(new Error(this.$t("tronSettingNumberPlaceholder")));
            } else {
                callback();
            }
        };
        const validPrivateKey = (rule, value, callback) => {
            console.log(value, "value");
            const address = TronWeb.address.fromPrivateKey(value);
            console.log(address);
            if (!TronWeb.isAddress(address)) {
                callback(new Error(this.$t("tronSettingAddressPlaceholder")));
            } else {
                callback();
            }
        };
        return {
            classLoading: false,
            saveLoading: false,
            dialogVisible: this.nodeDialogVisible,
            dialogTitle:
                this.editStatus == 1
                    ? this.$t("tronNodeEditTitle")
                    : this.$t("tronNodeAdd"),
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
            nodeRules: {
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
                    }
                ],
                userName: [
                    {
                        required: true,
                        message: this.$t("tronNodeNamePlaceholder"),
                        trigger: "blur"
                    }
                ],
                ip: {
                    required: true,
                    message: this.$t("tronNodeIpPlaceholder"),
                    trigger: "blur"
                },
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
                    }
                ],
                isSR: {
                    required: true,
                    message: this.$t("tronNodeSRPlaceholder"),
                    trigger: "change"
                },
                url: {
                    required: true,
                    message: this.$t("tronNodeUrlPlaceholder"),
                    trigger: "blur"
                },
                voteNumber: [
                    {
                        required: true,
                        message: this.$t("tronNodeVoteNumberTips"),
                        trigger: "blur"
                    },
                    {
                        required: true,
                        validator: validNum,
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
            }
        };
    },
    methods: {
        openDialogFun() {},
        closeFun() {
            // this.$refs.nodeDialogForm.resetFields();
            this.dialogVisible = false;
            this.$emit("addNodeSuccess", true);
        },
        cancelFun() {
            // this.$refs.nodeDialogForm.resetFields();
            this.dialogVisible = false;
            this.$emit("addNodeSuccess", true);
        },
        saveData(formName) {
            this.saveLoading = true;
            this.$refs[formName].validate(valid => {
                if (valid) {
                    let newForm;
                    console.log(this.nodeForm.url);
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
                    console.log(newForm);

                    if (this.editStatus == 1) {
                        // delete newForm.privateKey;
                        delete newForm.publicKey;
                        editNote(newForm)
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
                } else {
                    console.log("error submit!!");
                    return false;
                }
            });
        }
    },
    watch: {
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
.nodeDialogForm {
    padding: 0 20px;
}
</style>

