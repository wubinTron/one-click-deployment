/*
 * @Author: lxm 
 * @Date: 2019-08-28 15:27:13 
 * @Last Modified by: lxm
 * @Last Modified time: 2019-10-25 15:06:28
 * @tron node list  
 */
<template>
    <div class="app-container">
        <div class="tron-content">
            <div class="tron-filter-section">
                <el-button
                    size="mini"
                    @click="addBranchFun()"
                    type="primary"
                >{{$t('tronNewBranch')}}</el-button>
            </div>
            <div class="filter-container tron-table">
                <!--tron table-->
                <el-table
                    v-loading="listLoading"
                    ref="multipleTable"
                    empty-text="暂无数据"
                    :data="list"
                    tooltip-effect="dark"
                    style="width: 100%"
                    border
                    @selection-change="handleSelectionChange"
                >
                    <!-- <el-table-column
                        prop="branch_code"
                        :label="$t('tronBranchCode')"
                        align="center"
                    ></el-table-column>-->
                    <el-table-column prop="branch" :label="$t('tronBranchName')" align="center"></el-table-column>
                    <el-table-column prop="branchNote" :label="$t('tronBranchNote')" align="center"></el-table-column>
                    <el-table-column
                        prop="branch_status"
                        :label="$t('tronBranchStatus')"
                        align="center"
                    ></el-table-column>
                    <el-table-column :label="$t('tronBranchOperate')" align="center">
                        <el-button
                            size="mini"
                            type="info"
                            @click="modifyBranchFun"
                        >{{$t('tronBranchPperateModify')}}</el-button>
                    </el-table-column>
                </el-table>
                <!-- <div v-show="!listLoading" class="pagination-container mgt20 pdb10">
                    <el-pagination
                        background
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page.sync="listQuery.page"
                        style="text-align:right"
                        :page-size="listQuery.limit"
                        :page-sizes="[20, 50, 100]"
                        layout="total, prev, pager,next,sizes,jumper"
                        :total="listQuery.total"
                    ></el-pagination>
                </div>-->
            </div>
        </div>
        <operate-branch
            :branchDialogVisible="branchObj.visible"
            :detailInfoData="branchObj.detail"
            @dialog="branchDetailFun"
            @addBranchSuccess="addBranchSuccessFun"
        ></operate-branch>
    </div>
</template>
<script>
import { branchGetApi } from "@/api/branchApi";
import operateBranch from "./operateBranch";
export default {
    name: "nodelist",
    components: {
        operateBranch
    },
    data() {
        return {
            list: [],
            listLoading: false,
            filterItem: {
                name: ""
            },
            listQuery: {
                limit: 20,
                page: 1,
                total: 0
            },
            branchObj: {
                visible: false,
                detail: {}
            }
        };
    },
    computed: {
        parames() {
            return Object.assign(this.filterItem, this.listQuery);
        }
    },
    created() {
        this.getDataListFun();
    },
    methods: {
        addBranchFun() {
            if (this.list.length === 1) {
                this.$message(this.$t("tronBranchTipsInfo"));
                return;
            }
            this.branchObj.visible = true;
        },
        modifyBranchFun() {
            this.branchObj.detail = this.list[0];
            this.branchObj.visible = true;
        },
        getDataListFun() {
            branchGetApi()
                .then(response => {
                    return response.data;
                })
                .then(res => {
                    this.list.push(res);
                })
                .catch(error => {
                    console.log(error);
                });
        },
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        // handleSizeChange(val) {
        //     this.listQuery.limit = val;
        //     this.getDataListFun();
        // },
        handleCurrentChange(val) {
            console.log(`当前页: ${val}`);
            this.listQuery.page = val;
            this.getDataListFun();
        },
        branchDetailFun(val) {
            this.branchObj.visible = val;
        },
        addBranchSuccessFun(val) {
            if (val) {
                this.list = [];
                this.getDataListFun();
            }
        }
    }
};
</script>
<style lang="scss" rel="stylesheet/scss" scoped>
.license {
    display: block;
    width: 120px;
    height: 80px;
}
</style>
