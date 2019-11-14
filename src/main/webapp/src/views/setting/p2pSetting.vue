/* * @Author: lxm * @Date: 2019-10-15 11:03:42 * @Last Modified by: lxm * @Last
Modified time: 2019-11-13 14:16:55 * @setting p2p setting */

<template>
  <div class="viewBranchDialog tronp2pSettingForm">
    <el-form
      ref="p2pSettingDialogForm"
      :rules="p2pSettingRules"
      :model="p2pSettingForm"
      label-width="250px"
      class="tronbaseSettingForm transcationDialogForm"
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
              {{ $t("tronSettingP2p") }}
            </div>
            <div v-if="baseContentShow">
              <el-form-item prop="node_p2p_version" class="baseFormItem mgt20">
                <span slot="label">
                  p2pVersion
                  <el-tooltip
                    class="item"
                    effect="dark"
                    :content="$t('tronp2pVersionPlaceholder')"
                    placement="top"
                  >
                    <i class="iconfont icon-iconset0143"></i>
                  </el-tooltip>
                </span>
                <el-input
                  size="small"
                  :maxlength="50"
                  v-model.trim="p2pSettingForm.node_p2p_version"
                  :placeholder="$t('tronP2pVersionPlaceholder')"
                ></el-input>
              </el-form-item>

              <el-form-item
                class="baseFormItem"
                label="seedNode"
                prop="defalutSelectedIp"
              >
                <el-checkbox-group
                  size="small"
                  v-model="p2pSettingForm.defalutSelectedIp"
                  @change="checkBoxChangeFun"
                >
                  <el-checkbox
                    class="checkBox"
                    v-for="(item, ind) in seedNodeIpList"
                    :key="ind"
                    :label="item.ip"
                  >
                    <el-input
                      size="small"
                      :placeholder="$t('tronSettingPortPlaceholder')"
                      v-model="item.port"
                      disabled
                    >
                      <template slot="prepend" style="width:100px">{{
                        item.ip
                      }}</template>
                    </el-input>
                  </el-checkbox>
                </el-checkbox-group>
              </el-form-item>
              <el-button
                type="text"
                size="small"
                @click="moreSetting = !moreSetting"
                >{{ $t("tronMoreSetting") }}</el-button
              >
              <div v-if="moreSetting">
                <el-form-item
                  label="maxActionNodes"
                  prop="node_maxActiveNodes"
                  class="baseFormItem"
                >
                  <el-input
                    size="small"
                    :maxlength="50"
                    v-model.trim="p2pSettingForm.node_maxActiveNodes"
                    :placeholder="$t('tronMaxActionNodesPlaceholder')"
                  ></el-input>
                </el-form-item>
                <el-form-item
                  class="baseFormItem"
                  label="maxActiveNodesWithSameIp"
                  prop="node_maxActiveNodesWithSameIp"
                >
                  <el-input
                    size="small"
                    :maxlength="50"
                    v-model.trim="p2pSettingForm.node_maxActiveNodesWithSameIp"
                    :placeholder="$t('tronMaxActiveNodesWithSameIpPlaceholder')"
                  ></el-input>
                </el-form-item>
                <el-form-item
                  class="baseFormItem"
                  label="activeConnectFactor"
                  prop="node_activeConnectFactor"
                >
                  <el-input-number
                    size="small"
                    controls-position="right"
                    :min="0"
                    :step="0.1"
                    :maxlength="50"
                    v-model.trim="p2pSettingForm.node_activeConnectFactor"
                    :placeholder="$t('tronSettingPlaceholder')"
                  ></el-input-number>
                </el-form-item>
                <el-form-item
                  class="baseFormItem"
                  label="connectFactor"
                  prop="connectFactor"
                >
                  <el-input-number
                    size="small"
                    controls-position="right"
                    :min="0"
                    :step="0.1"
                    :maxlength="50"
                    v-model.trim="p2pSettingForm.connectFactor"
                    :placeholder="$t('tronSettingPlaceholder')"
                  ></el-input-number>
                </el-form-item>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
      <el-form-item label-width="0" class="textRight">
        <el-button size="small" type="primary" @click="previousStepFun">{{
          $t("tronSettingPreviousStep")
        }}</el-button>
        <el-button
          size="small"
          type="primary"
          @click="saveData('p2pSettingDialogForm')"
          >{{ $t("tronSettingNextStep") }}</el-button
        >
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
  props: ["detailInfoData", "listenPort", "seedNodeIpList"],
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
    const validMainnet = (rule, value, callback) => {
      if (value == 11111) {
        callback(new Error(this.$t("tronp2pVersionMainnetPlaceholder")));
      } else {
        callback();
      }
    };
    const validTestNet = (rule, value, callback) => {
      if (value == 20180622) {
        callback(new Error(this.$t("tronp2pVersionTestnetPlaceholder")));
      } else {
        callback();
      }
    };
    const validSpecialNet = (rule, value, callback) => {
      if (value == 1) {
        callback(new Error(this.$t("tronp2pVersionSpecialPlaceholder")));
      } else {
        callback();
      }
    };
    return {
      baseContentShow: true,
      moreSetting: false,
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
          },
          {
            message: this.$t("tronp2pVersionMainnetPlaceholder"),
            validator: validMainnet,
            trigger: "blur"
          },
          {
            message: this.$t("tronp2pVersionTestnetPlaceholder"),
            validator: validTestNet,
            trigger: "blur"
          },
          {
            message: this.$t("tronp2pVersionSpecialPlaceholder"),
            validator: validSpecialNet,
            trigger: "blur"
          }
        ],
        defalutSelectedIp: [
          {
            required: true,
            message: this.$t("tronP2pSeedNodeSelectPlaceholder"),
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
  methods: {
    previousStepFun() {
      this.$emit("previousSettingStep", true);
    },

    checkBoxChangeFun(val) {
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
          // all node ary
          let allNodesAry = [];
          this.seedNodeIpList.forEach(item => {
            allNodesAry.push(item.ip);
          });
          // current checked Node ip

          const allNodesSet = new Set(allNodesAry);
          const currentCheckedSet = new Set(
            this.p2pSettingForm.defalutSelectedIp
          );

          // intersect
          let intersect = new Set(
            [...allNodesSet].filter(x => currentCheckedSet.has(x))
          );

          let passNodeData = [];
          Array.from(intersect).forEach(item => {
            passNodeData.push(`${item}":"${this.listenPort}`);
          });
          p2pSettingApi(newp2pForm, passNodeData)
            .then(response => {
              this.$emit("addSettingSuccess", true);
              this.$message.success(this.$t("tronSettingp2pSaveSuccess"));
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
.tronbaseSettingForm {
  padding: 0 80px 0 0;
}
.checkBox {
  min-width: 180px;
}
.baseFormItem {
  width: 620px;
}
</style>
