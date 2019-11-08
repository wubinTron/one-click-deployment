/*
 * @Author: lxm 
 * @Date: 2019-08-28 15:27:13 
 * @Last Modified by: lxm
 * @Last Modified time: 2019-10-15 18:23:23
 * @tron node status  
 */
<template>
    <div class="app-container">
        <div class="tron-content">
            <div class="tron-filter-section">
                <el-button size="mini" @click="showAddUser()" type="primary">添加节点</el-button>
                <el-button size="mini" type="info" @click>批量部署</el-button>
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
                    <el-table-column prop="agent_id" label="ID" align="center"></el-table-column>
                    <el-table-column prop="mobile" label="节点名称" align="center"></el-table-column>
                    <el-table-column prop="name" label="IP/HOST" align="center"></el-table-column>
                    <el-table-column prop="address" label="SSH PORT" align="center"></el-table-column>
                    <el-table-column prop="created_at" label="是否为SR" align="center"></el-table-column>
                    <el-table-column prop="created_at" label="节点状态" align="center"></el-table-column>
                    <el-table-column label="操作" align="center">
                        <el-button size="mini" type="warning">修改</el-button>
                        <el-button size="mini" type="danger" @click="deleteNodeListFun()">删除</el-button>
                    </el-table-column>
                </el-table>
                <div v-show="!listLoading" class="pagination-container mgt20 pdb10">
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
                </div>
            </div>
        </div>
    </div>
</template>
<script>
// import { getNodeList, deleteNode, updateNode } from "@/api/nodeApi.js";
export default {
    name: "nodelist",
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
        getDataListFun() {
            // getAgentList(this.parames)
            //     .then(response => {
            //         let resBody = response.items || [];
            //         resBody.forEach(element => {
            //             if (element.status == 1) {
            //                 element.statusName = true;
            //             } else {
            //                 element.statusName = false;
            //             }
            //         });
            //         this.list = resBody;
            //         this.listQuery.total = response.total;
            //         this.listLoading = false;
            //     })
            //     .catch(error => {
            //         console.log(error);
            //     });
        },
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        handleSizeChange(val) {
            this.listQuery.limit = val;
            this.getDataListFun();
        },
        handleCurrentChange(val) {
            console.log(`当前页: ${val}`);
            this.listQuery.page = val;
            this.getDataListFun();
        },
        deleteNodeListFun(_id) {
            this.$confirm("此操作将删除该节点信息, 是否继续?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"
            })
                .then(() => {
                    // deleteAgent(_id)
                    //     .then(response => {
                    //         this.$message.success("删除节点信息成功");
                    //         this.getDataListFun();
                    //     })
                    //     .catch(err => {});
                })
                .catch(() => {
                    this.$message({
                        type: "info",
                        message: "已取消删除"
                    });
                });
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
