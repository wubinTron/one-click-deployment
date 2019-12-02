/* * @Author: lxm * @Date: 2019-08-28 15:27:13 * @Last Modified by: lxm * @Last
Modified time: 2019-11-12 11:30:38 * @tron node list */
<template>
    <div class="app-container">
        <div class="tron-content">
            <div class="tron-filter-section">
                <el-button
                    icon="el-icon-plus"
                    size="small"
                    @click="addNodeFun()"
                    type="primary"
                >{{ $t("tronNodeAdd") }}</el-button>
                <el-button
                    size="small"
                    v-if="isDeploy == 1"
                    :loading="allNodeDeployLoading"
                    style="float:right"
                    :type="checkType"
                    @click="bulkDeploymentFun"
                >{{ $t("tronNodeBulkDeployment") }}</el-button>
            </div>
            <div class="filter-container tron-table" id="tronTable">
                <!--tron table-->
                <el-table
                    v-loading="listLoading"
                    ref="multipleTable"
                    :empty-text="$t('tronNodesNoData')"
                    :data="list"
                    tooltip-effect="dark"
                    style="width: 100%"
                    border
                    @selection-change="handleSelectionChange"
                >
                    <el-table-column v-if="isDeploy == 1" type="selection" width="55"></el-table-column>
                    <el-table-column prop="id" label="ID" align="center"></el-table-column>
                    <el-table-column prop="userName" :label="$t('tronNodeName')" align="center"></el-table-column>
                    <el-table-column prop="ip" label="IP/HOST" align="center"></el-table-column>
                    <el-table-column prop="port" label="SSH PORT" align="center"></el-table-column>
                    <el-table-column :label="$t('tronNodeWhetherIsSR')" align="center">
                        <template slot-scope="scope">
                            <el-tag type="success" v-if="scope.row.isSR">yes</el-tag>
                            <el-tag type="danger" v-else>no</el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column :label="$t('tronNodeOperate')" align="center" width="200">
                        <template slot-scope="scope">
                            <el-button
                                size="small"
                                type="text"
                                @click="operateNodeFun(scope.row)"
                            >{{ $t("tronNodeModify") }}</el-button>
                            <el-divider direction="vertical"></el-divider>
                            <el-button
                                size="small"
                                type="text"
                                @click="deleteNodeListFun(scope.row.id)"
                            >{{ $t("tronNodeDelete") }}</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <div class="mgt20" v-if="isDeploy != 1">
                <el-button
                    size="small"
                    style="float:right"
                    :type="allStepsBtnType"
                    :disabled="allStepsBtnDisable"
                    @click="nextStepFun"
                >{{ $t("tronNodeNextStep") }}</el-button>
            </div>
        </div>
        <!--tron Node Bulk Deployment  -->
        <el-dialog
            :title="$t('tronNodeBulkDeployment')"
            :visible.sync="deploymentDialogVisible"
            width="600px"
            center
        >
            <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 4 }" v-model="currentPath"></el-input>
            <div class="el-upload__tip">{{ $t("deploymentUpload") }}</div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="deploymentDialogVisible = false">
                    {{
                    $t("tronNodeCancel")
                    }}
                </el-button>
                <el-button
                    type="primary"
                    @click="deployMentFun"
                    :loading="deplogUploadLoading"
                >{{ $t("tronNodeSave") }}</el-button>
            </span>
        </el-dialog>
        <!-- node log  -->
        <el-dialog
            :title="$t('tronNodeStatus')"
            :visible.sync="currentLogDialog"
            @close="currentNodeLogEnd"
            width="600px"
            center
        >
            <el-timeline v-if="currentlogInfoData.length > 0">
                <el-timeline-item
                    type="primary"
                    v-for="(activity, index) in currentlogInfoData"
                    :key="index"
                >{{ activity }}</el-timeline-item>
            </el-timeline>
            <el-button
                type="text"
                style="margin-left: 30px;"
                :loading="deplogUploadLoading"
                v-if="deploymentLoadingTips"
            >{{ deploymentLoadingText }}</el-button>
        </el-dialog>
        <!-- node add and edit  -->
        <operate-node
            :allNodes="list"
            :nodeDialogVisible="nodeObj.visible"
            :editStatus="nodeObj.status"
            :detailInfoData="nodeObj.detail"
            @dialog="nodeDetailFun"
            @addNodeSuccess="addNodeSuccessFun"
        ></operate-node>
    </div>
</template>
<script>
import {
    allNodeInfo,
    deleteNote,
    deployNodeApi,
    deployLogInfoApi,
    nodeInfoApi,
    initConfigApi
} from "@/api/nodeApi.js";
import operateNode from "./nodeOperate";

//let nodeTimer = null;
let deploymentNode = null;
export default {
    name: "nodelist",
    components: {
        operateNode
    },
    data() {
        return {
            list: [],
            isDeploy: this.$route.query.deploy,
            currentlogInfoData: [],
            deplogUploadLoading: false,
            allNodeDeployLoading: false,
            deploymentDialogVisible: false,
            currentLogDialog: false,
            deploymentLoadingTips: true,
            deploymentLoadingText: this.$t("deploymentSearchLoading"),
            listLoading: false,
            currentPath: "",
            checkType: "info",
            allStepsBtnType: "info",
            allStepsBtnDisable: true,
            listQuery: {
                limit: 20,
                page: 1,
                total: 0
            },
            nodeObj: {
                visible: false,
                detail: {},
                status: 0
            },
            logInfoData: [],
            multipleSelection: [],
            deployedAry: []
        };
    },
    computed: {
        multipleSelectionIds() {
            let arr = [];
            this.multipleSelection.map(item => {
                arr.push({
                    id: item.id,
                    path: this.currentPath,
                    isSR: item.isSR
                });
            });
            return arr;
        }
    },
    created() {
        this.getDataListFun();
        this.timer = null;
    },
    beforeDestroy() {
        window.clearInterval(this.timer);
        window.clearInterval(deploymentNode);
        this.timer = null;
        deploymentNode = null;
    },
    methods: {
        sortIdFun(a, b) {
            return a.id - b.id;
        },
        addNodeFun() {
            if (this.list.length > 20) {
                this.$message({
                    type: "info",
                    message: this.$t("tronNodesMaxTips")
                });
                return;
            }
            this.nodeObj.detail = {
                url: "http://"
            };
            this.nodeObj.status = 0;
            this.nodeObj.visible = true;
        },
        operateNodeFun(val) {
            this.nodeObj.detail = {
                ...val,
                url: JSON.stringify(val.url)
                    .slice(3)
                    .slice(0, -3),
                privateKey:
                    "****************************************************************"
            };
            sessionStorage.setItem("currentnode", val.ip);
            this.nodeObj.status = 1;
            this.nodeObj.visible = true;
        },
        viewCurrentLogFun(_id) {
            console.log(this.timer);
            clearInterval(this.timer);
            this.logInfoData = [];
            this.currentLogDialog = true;
            this.viewLogFun(_id);
            this.timer = setInterval(() => {
                this.viewLogFun(_id);
            }, 1000 * 5);
        },
        currentNodeLogEnd() {
            clearInterval(this.timer);
            this.timer = null;
        },
        viewLogFun(_id, _type) {
            this.deploymentLoadingText = this.$t("deploymentSearchLoading");
            deployLogInfoApi({ id: _id })
                .then(response => {
                    return response.data;
                })
                .then(res => {
                    this.currentlogInfoData = res.logInfo;
                    this.deplogUploadLoading = true;
                    this.deploymentLoadingTips = true;
                    this.currentlogInfoData.forEach(async item => {
                        if (item.indexOf("deploy finish") > -1) {
                            this.deplogUploadLoading = false;
                            this.deploymentDialogVisible = false;
                            this.deploymentLoadingText = this.$t(
                                "deploymentDone"
                            );
                        } else if (item == "ssh connect failed") {
                            this.deplogUploadLoading = false;
                            this.deploymentDialogVisible = false;
                            this.deploymentLoadingText = this.$t(
                                "deploymentFail"
                            );
                        }
                    });
                })
                .catch(err => {});
        },
        deployMentFun() {
            // deploy
            this.deplogUploadLoading = true;
            this.allNodeDeployLoading = true;
            if (this.currentPath != "") {
                this.multipleSelectionIds.forEach(async item => {
                    await this.deployNodeApiFun(item);
                });
                this.$message({
                    type: "success",
                    message: this.$t("deploymentLoading")
                });
                let idAry = [];
                this.multipleSelectionIds.forEach(item => {
                    idAry.push(item.id);
                });
                this.getDataListFun(1, idAry);
            } else {
                this.deplogUploadLoading = false;
                this.$message({
                    type: "warning",
                    message: this.$t("deploymentPath")
                });
            }
        },
        deployNodeApiFun(item) {
            deployNodeApi(item)
                .then(res => {
                    this.deplogUploadLoading = false;
                    this.deploymentDialogVisible = false;
                })
                .catch(err => {
                    this.deplogUploadLoading = false;
                    this.$message({
                        type: "info",
                        message: this.$t("deploymentFail")
                    });
                });
        },
        bulkDeploymentFun() {
            // console.log(this.multipleSelectionIds.length);
            if (this.multipleSelectionIds.length > 0) {
                // let count = 0;
                // this.multipleSelectionIds.find(item => {
                //     item.isSR ? count++ : "";
                // });
                // if (count == 0) {
                //     this.$message({
                //         type: "warning",
                //         message: this.$t("deploymentSRSelectTips")
                //     });
                //     return;
                // }
                this.logInfoData = [];
                this.deploymentDialogVisible = true;
            } else {
                this.$message({
                    type: "warning",
                    message: this.$t("deploymentSelectTips")
                });
            }
        },
        getSelectedDeploymentFun(ids) {
            this.deploymentNode = setInterval(() => {
                nodeInfoApi(ids)
                    .then(response => {
                        return response.data;
                    })
                    .then(res => {
                        let newRes = Object.values(res);
                        let newInd = 0;
                        newRes.forEach(item => {
                            if (item != "deploy finish") {
                                newInd++;
                            }
                        });
                        if (newInd == 0) {
                            this.allNodeDeployLoading = false;
                            clearInterval(this.deploymentNode);
                        }
                    })
                    .catch(error => {
                        console.log(error);
                    });
            }, 1000 * 10);
        },
        getDataListFun(log, ids) {
            // defalut data list
            allNodeInfo()
                .then(response => {
                    return response.data;
                })
                .then(async res => {
                    if (res.length > 0) {
                        this.allStepsBtnType = "primary";
                        this.allStepsBtnDisable = false;
                    } else {
                        this.allStepsBtnType = "info";
                        this.allStepsBtnDisable = true;
                    }
                    let resData = res;
                    if (resData.length == 0) {
                        await this.$store
                            .dispatch("user/changeRoles", "node")
                            .then(res => {
                                console.log(res);
                            });
                    }
                    if (log == 1) {
                        resData.forEach(item => {
                            this.multipleSelectionIds.forEach(selectedVal => {
                                if (selectedVal.id == item.id) {
                                    item.logLoading = true;
                                }
                            });
                        });
                        this.getSelectedDeploymentFun(ids);
                    }
                    this.list = resData.sort(this.sortIdFun);
                })
                .catch(error => {
                    console.log(error);
                });
        },
        handleSelectionChange(val) {
            // change select
            this.multipleSelection = val;
            if (val.length > 0) {
                this.checkType = "success";
            } else {
                this.checkType = "info";
            }
        },
        handleSizeChange(val) {
            this.listQuery.limit = val;
            this.getDataListFun();
        },
        handleCurrentChange(val) {
            this.listQuery.page = val;
            this.getDataListFun();
        },
        deleteNodeListFun(_id) {
            this.$confirm(
                this.$t("tronNodesDeleteTips"),
                this.$t("tronNodesDeleteTipsTitle"),
                {
                    confirmButtonText: this.$t("tronNodeDetermine"),
                    cancelButtonText: this.$t("tronNodeCancel"),
                    type: "warning"
                }
            )
                .then(() => {
                    deleteNote({ id: _id })
                        .then(response => {
                            this.$message.success(
                                this.$t("tronNodesDeleteSuccess")
                            );
                            this.getDataListFun();
                        })
                        .catch(err => {});
                })
                .catch(() => {
                    this.$message({
                        type: "info",
                        message: this.$t("tronNodesCancelDelete")
                    });
                });
        },
        nodeDetailFun(val) {
            this.nodeObj.visible = val;
        },
        addNodeSuccessFun(val) {
            if (val) {
                this.getDataListFun();
            }
        },
        nextStepFun() {
            initConfigApi()
                .then(async response => {
                    console.log(response);
                    if (response.code === 204) {
                        await this.$store
                            .dispatch("user/changeRoles", "setting")
                            .then(res => {
                                console.log(res);
                            });
                        this.$router.push({ path: "/setting/list" });
                    }
                })
                .catch(err => {
                    console.log(err);
                });
        }
    }
};
</script>
<style lang="scss" rel="stylesheet/scss" scoped></style>
