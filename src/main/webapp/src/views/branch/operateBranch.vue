/* * @Author: lxm * @Date: 2019-10-15 11:03:42 * @Last Modified by: lxm * @Last
Modified time: 2019-10-25 14:40:07 * @operation branch */

<template>
  <div class="viewBranchDialog">
    <el-dialog
      :title="$t('tronNewBranch')"
      @open="openDialogFun"
      @close="closeFun"
      :visible.sync="dialogVisible"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      v-loading="classLoading"
      width="600px"
      center
    >
      <el-form
        ref="branchDialogForm"
        :rules="branchRules"
        :model="branchForm"
        label-width="120px"
        class="tronBranchForm"
        label-position="left"
      >
        <!-- <el-form-item :label="$t('tronBranchCode')" prop="branchCode">
                    <el-input
                        :maxlength="20"
                        v-model="branchForm.branchCode"
                        :placeholder="$t('tronBranchCodePlaceholder')"
                    ></el-input>
                </el-form-item>-->
        <el-form-item :label="$t('tronBranchName')" prop="branch">
          <el-select
            v-model="branchForm.branch"
            :placeholder="$t('tronBranchNamePlaceholder')"
          >
            <el-option
              v-for="item in branchAry"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('tronBranchNote')" prop="branchNote">
          <el-input
            type="textarea"
            :maxlength="20"
            v-model="branchForm.branchNote"
            :placeholder="$t('tronBranchNotePlaceholder')"
          ></el-input>
        </el-form-item>
        <el-form-item label-width="0" class="textCenter">
          <el-button type="primary" @click="saveData('branchDialogForm')">{{
            $t("tronBranchSave")
          }}</el-button>
          <el-button @click="cancelFun">{{ $t("tronBranchCancel") }}</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
import { branchSaveApi, branchGetApi } from "@/api/branchApi";
export default {
  name: "operationBranch",
  props: ["branchDialogVisible", "detailInfoData", "editStatus"],
  data() {
    return {
      classLoading: false,
      dialogVisible: this.branchDialogVisible,
      branchForm: {
        branch: "",
        branchNote: ""
      },
      branchAry: [
        { id: 0, label: "master", value: "master" },
        { id: 1, label: "develop", value: "develop" }
      ],
      branchRules: {
        branch: [
          {
            required: true,
            message: this.$t("tronBranchCodePlaceholder"),
            trigger: "change"
          }
        ],
        branchNote: {
          required: true,
          message: this.$t("tronBranchNotePlaceholder"),
          trigger: "change"
        }
      }
    };
  },
  methods: {
    openDialogFun() {},
    closeFun() {
      this.$refs.branchDialogForm.resetFields();
      this.dialogVisible = false;
    },
    cancelFun() {
      this.$refs.branchDialogForm.resetFields();
      this.dialogVisible = false;
    },
    saveData(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          branchSaveApi(this.branchForm)
            .then(response => {
              this.$emit("addBranchSuccess", true);
              this.$refs.branchDialogForm.resetFields();
              this.$message.success(this.$t("tronBranchAddSuccess"));
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
      this.branchForm = this.detailInfoData;
    },
    branchDialogVisible(val) {
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
.tronBranchForm {
  padding: 0 80px 0 0;
}
</style>
