/* * @Author: lxm * @Date: 2019-10-15 11:03:42 * @Last Modified by: lxm * @Last
Modified time: 2019-11-12 19:01:59 * @setting genesis setting */

<template>
  <div class="viewBranchDialog">
    <div class="tronbaseSettingForm">
      <!-- <el-row :gutter="12">
            <el-col :span="24">-->
      <el-card shadow="hover">
        <div @click="genesisContentShow = !genesisContentShow">
          <i
            :class="
              genesisContentShow ? 'el-icon-arrow-down' : 'el-icon-arrow-right'
            "
          ></i>
          {{ $t("tronSettingGenesis") }}
        </div>
        <el-form
          v-if="genesisContentShow"
          ref="genesisSettingDialogForm"
          label-width="200px"
          class="tronbaseSettingForm"
          label-position="left"
        >
          <el-row :gutter="30">
            <el-col :span="12">
              <div class="asset">
                <el-button class="newAsset" @click="newAssetFun()"
                  >new asset</el-button
                >
                <el-row
                  v-for="(item, index) in detailInfoData.genesis_block_assets"
                  :key="index"
                >
                  <el-button
                    @click="currentAssetFun(item, index)"
                    style="width:100%"
                    >{{ item.accountName }}</el-button
                  >
                </el-row>
              </div>
            </el-col>
            <el-col :span="12">
              <div class="witeness">
                <el-button class="newWiteness">witeness</el-button>
                <el-row
                  v-for="(item,
                  index) in detailInfoData.genesis_block_witnesses"
                  :key="index"
                >
                  <el-button
                    @click="currenWitenessFun(item, index)"
                    style="width:100%"
                    >{{ item.address }}</el-button
                  >
                </el-row>
              </div>
            </el-col>
          </el-row>
        </el-form>
      </el-card>
      <!-- </el-col>
            </el-row>-->
      <div label-width="0" class="textRight">
        <el-button size="small" type="primary" @click="saveAllData()">{{
          $t("tronSettingNextStep")
        }}</el-button>
      </div>
    </div>

    <el-dialog
      center
      width="700px"
      :title="$t('tronAssetSetting')"
      :visible.sync="innerAssetVisible"
    >
      <el-form
        class="assetDialogForm"
        ref="assetDialogForm"
        :rules="assetRules"
        :model="assetForm"
        label-width="120px"
        label-position="left"
      >
        <el-form-item label="accountName" prop="accountName">
          <el-input
            size="small"
            :maxlength="50"
            v-model.trim="assetForm.accountName"
            :placeholder="$t('tronAccountNamePlaceholder')"
          ></el-input>
        </el-form-item>
        <el-form-item label="accountType" prop="accountType">
          <el-select
            size="small"
            v-model="assetForm.accountType"
            :placeholder="$t('tronAccountTypePlaceholder')"
          >
            <el-option
              v-for="item in accountTypeOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="address" prop="address">
          <el-input
            size="small"
            :maxlength="50"
            v-model.trim="assetForm.address"
            :placeholder="$t('tronAddressPlaceholder')"
          ></el-input>
        </el-form-item>
        <el-form-item label="balance" prop="balance">
          <el-input
            size="small"
            :maxlength="50"
            v-model.trim="assetForm.balance"
            :placeholder="$t('tronBalancePlaceholder')"
          ></el-input>
        </el-form-item>
        <el-form-item label-width="0" class="textRight">
          <el-button
            size="small"
            type="primary"
            @click="saveData('assetDialogForm')"
            >{{ $t("tronSettingSave") }}</el-button
          >
          <el-button size="small" @click="innerAssetVisible = false">{{
            $t("tronSettingCancel")
          }}</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <!-- witeness -->
    <el-dialog
      center
      width="700px"
      :title="$t('tronWitenessSetting')"
      :visible.sync="innerWitenessVisible"
    >
      <el-form
        ref="witenessDialogForm"
        :rules="witenessRules"
        :model="witenessForm"
        label-width="120px"
        label-position="left"
      >
        <el-form-item label="address" prop="address">
          {{ witenessForm.address }}
          <!-- <el-input
                        disabled
                        :maxlength="50"
                        v-model="witenessForm.address"
                        :placeholder="$t('tronSettingPlaceholder')"
                    ></el-input>-->
        </el-form-item>
        <el-form-item label="url" prop="url">
          {{ witenessForm.url }}
          <!-- <el-input
                        disabled
                        :maxlength="50"
                        v-model="witenessForm.url"
                        :placeholder="$t('tronSettingPlaceholder')"
                    ></el-input>-->
        </el-form-item>
        <el-form-item label="voteCount" prop="voteCount">
          {{ witenessForm.voteCount }}
          <!-- <el-input
                        disabled
                        :maxlength="50"
                        v-model=""
                        :placeholder="$t('tronSettingPlaceholder')"
                    ></el-input>-->
        </el-form-item>
        <!-- <el-form-item label-width="0" class="textCenter">
                    <el-button
                        type="primary"
                        @click="saveWitenessData('witenessDialogForm')"
                    >{{$t('tronSettingSave')}}</el-button>
                    <el-button @click="innerWitenessVisible = false">{{$t('tronSettingCancel')}}</el-button>
                </el-form-item>-->
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
import { genesisSettingApi } from "@/api/settingApi";
import { isvalidateNum } from "@/utils/validate.js";
import TronWeb from "tronweb";
export default {
  name: "genesisSetting",
  props: ["detailInfoData"],
  data() {
    const validNormalNum = (rule, value, callback) => {
      if (!isvalidateNum(value)) {
        callback(new Error(this.$t("tronSettingNumberPlaceholder")));
      } else {
        callback();
      }
    };
    const validNum = (rule, value, callback) => {
      if (value > 9223372036854775807 || value < -9223372036854775808) {
        callback(new Error(this.$t("tronSettingNumberPlaceholder")));
      } else {
        callback();
      }
    };
    const validAddress = (rule, value, callback) => {
      if (!TronWeb.isAddress(value)) {
        callback(new Error(this.$t("tronSettingAddressPlaceholder")));
      } else {
        callback();
      }
    };
    const validPrivateKey = (rule, value, callback) => {
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
      genesisContentShow: true,
      assetEditStatus: 0,
      currentAssetEditInd: "",
      currentWitenessEditInd: "",
      witenessEditStatus: 0,
      innerAssetVisible: false,
      innerWitenessVisible: false,
      genesisSetting: {
        genesis_block_assets: [],
        genesis_block_witnesses: []
      },
      accountTypeOptions: [
        {
          value: "AssetIssue",
          label: "AssetIssue"
        },
        {
          value: "Contract",
          label: "Contract"
        }
      ],
      assetRules: {
        accountName: [
          {
            required: true,
            message: this.$t("tronAccountNamePlaceholder"),
            trigger: "blur"
          }
        ],
        accountType: [
          {
            required: true,
            message: this.$t("tronAccountTypePlaceholder"),
            trigger: "blur"
          }
        ],
        address: [
          {
            required: true,
            message: this.$t("tronAddressPlaceholder"),
            trigger: "blur"
          },
          {
            required: true,
            validator: validAddress,
            trigger: "blur"
          }
        ],
        balance: [
          {
            required: true,
            message: this.$t("tronBalancePlaceholder"),
            trigger: "blur"
          },
          {
            required: true,
            validator: validNum,
            trigger: "blur"
          }
        ]
      },
      assetForm: {
        accountName: "",
        accountType: "",
        address: "",
        balance: ""
      },
      witenessRules: {
        address: [
          {
            required: true,
            message: this.$t("tronSettingPlaceholder"),
            trigger: "blur"
          },
          {
            required: true,
            validator: validAddress,
            trigger: "blur"
          }
        ],
        url: {
          required: true,
          message: this.$t("tronSettingPlaceholder"),
          trigger: "blur"
        },
        voteCount: [
          {
            required: true,
            message: this.$t("tronSettingPlaceholder"),
            trigger: "blur"
          },
          {
            required: true,
            validator: validNormalNum,
            trigger: "blur"
          }
        ],
        privateKey: [
          {
            required: true,
            message: this.$t("tronSettingPlaceholder"),
            trigger: "blur"
          },
          {
            required: true,
            validator: validPrivateKey,
            trigger: "blur"
          }
        ]
      },
      witenessForm: {
        address: "",
        url: "",
        voteCount: "",
        privateKey: ""
      }
    };
  },
  methods: {
    newAssetFun() {
      this.assetForm = {
        accountName: "",
        accountType: "",
        address: "",
        balance: ""
      };
      this.innerAssetVisible = true;
    },
    newWitenessFun() {
      this.witenessForm = {
        address: "",
        url: "",
        voteCount: "",
        privateKey: ""
      };
      this.innerWitenessVisible = true;
    },
    currentAssetFun(item, ind) {
      this.assetForm = item;
      this.assetEditStatus = 1;
      this.currentAssetEditInd = ind;
      this.innerAssetVisible = true;
    },
    currenWitenessFun(item, ind) {
      this.witenessForm = item;
      this.witenessEditStatus = 1;
      this.currentWitenessEditInd = ind;
      this.innerWitenessVisible = true;
    },
    saveData(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          console.log(this.assetForm);
          if (this.assetEditStatus == 0) {
            this.genesisSetting.genesis_block_assets.push(this.assetForm);
          } else {
            this.genesisSetting.genesis_block_assets[
              this.currentAssetEditInd
            ] = this.assetForm;
          }

          const newSettingForm = {
            assets: this.genesisSetting.genesis_block_assets
          };

          // newSettingForm.witness.forEach(item => {
          //     console.log(`"${item.url}"`);
          //     item.url = `"${item.url}"`;
          // });
          console.log(newSettingForm);
          genesisSettingApi(newSettingForm)
            .then(response => {
              this.$emit("addSettingSuccess", true);
              this.$message.success(this.$t("tronSettingGenesisSaveSuccess"));
              this.innerAssetVisible = false;
              this.assetEditStatus = 0;
            })
            .catch(error => {
              console.log(error);
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    saveWitenessData(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          console.log(this.witenessForm);
          if (this.witenessEditStatus == 0) {
            this.genesisSetting.genesis_block_witnesses.push(this.witenessForm);
          } else {
            this.genesisSetting.genesis_block_witnesses[
              this.currentWitenessEditInd
            ] = this.witenessForm;
          }
          console.log(this.genesisSetting, "genesisSetting");
          let passAssetsAry = this.genesisSetting.genesis_block_assets;
          let passWitnessAry = this.genesisSetting.genesis_block_witnesses;

          passWitnessAry.forEach(item => {
            item.url = `"${item.url}"`;
          });
          let newSettingForm = {
            assets: passAssetsAry,
            witness: passWitnessAry
          };
          console.log(newSettingForm);

          // genesisSettingApi(newSettingForm)
          //     .then(response => {
          //         this.$emit("addSettingSuccess", true);

          //         this.$message.success(
          //             this.$t("tronSettingGenesisSaveSuccess")
          //         );
          //         this.witenessEditStatus = 0;
          //         this.innerWitenessVisible = false;
          //     })
          //     .catch(error => {
          //         // this.listLoading = false;
          //         console.log(error);
          //     });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    saveAllData() {
      // save current data
      const newSettingForm = {
        assets: this.genesisSetting.genesis_block_assets
        // witness: this.genesisSetting.genesis_block_witnesses
      };
      // newSettingForm.witness.forEach(item => {
      //     console.log(`"${item.url}"`);
      //     item.url = `"${item.url}"`;
      // });
      genesisSettingApi(newSettingForm)
        .then(response => {
          this.$emit("addSettingSuccess", true);
          this.$message.success(this.$t("tronSettingGenesisSaveSuccess"));
        })
        .catch(error => {
          console.log(error);
        });
    }
  },
  watch: {
    detailInfoData(val) {
      this.baseSettingForm = this.detailInfoData;
      this.genesisSetting = this.detailInfoData;
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
  margin-top: 40px;
  padding: 0 80px 0 0;
}

.asset,
.witeness {
  position: relative;
  margin: 0 auto;
  // max-width: 600px;
  min-height: 200px;
  padding: 20px 15px;
  border: 1px solid #ccc;
  button {
    margin-top: 15px;
  }
}

.asset {
  .newAsset {
    position: absolute;
    left: 0;
    right: 0;
    margin: auto;
    top: -20px;
  }
}
.witeness {
  .newWiteness {
    position: absolute;
    left: 0;
    right: 0;
    margin: auto;
    top: -20px;
  }
}
.assetDialogForm {
  padding: 0 30px 0 20px;
}
</style>
