/*
 * @Author: lxm 
 * @Date: 2019-10-15 11:03:42 
 * @Last Modified by: lxm
 * @Last Modified time: 2019-11-07 12:19:54
 * @setting db setting
 */

<template>
    <div class="viewBranchDialog">
        <div class="tronp2pSettingForm">
            <el-form
                ref="dbSettingDialogForm"
                :rules="dbSettingRules"
                :model="dbSettingForm"
                label-width="250px"
                class="trondbSettingForm"
                label-position="left"
            >
                <el-row :gutter="12">
                    <el-col :span="24">
                        <el-card shadow="hover">
                            <div @click="baseContentShow = !baseContentShow">
                                <i
                                    :class="baseContentShow?'el-icon-arrow-down': 'el-icon-arrow-right'"
                                ></i>
                                {{$t('tronSettingDb')}}
                            </div>
                            <div v-if="baseContentShow">
                                <el-form-item
                                    class="mgt20"
                                    :label="$t('tronSettingwriteSynchronously')"
                                    prop="storage_db_sync"
                                >
                                    <el-switch v-model="dbSettingForm.storage_db_sync"></el-switch>
                                </el-form-item>
                                <el-form-item
                                    :label="$t('tronSettingOpenTransaction')"
                                    prop="storage_transHistory_switch"
                                >
                                    <el-switch
                                        active-value="on"
                                        inactive-value="off"
                                        v-model="dbSettingForm.storage_transHistory_switch"
                                    ></el-switch>
                                </el-form-item>
                                <el-form-item
                                    :label="$t('tronSelectDatabaseConfiguration')"
                                    prop="storage_db_engine"
                                >
                                    <el-radio-group v-model="dbSettingForm.storage_db_engine">
                                        <el-radio :label="'LEVELDB'">leveldb</el-radio>
                                        <el-radio :label="'ROCKSDB'">rocksdb</el-radio>
                                    </el-radio-group>
                                </el-form-item>
                                <el-form-item
                                    :label="$t('tronSettingIndexDirectory')"
                                    prop="storage_index_directory"
                                >
                                    <el-switch
                                        active-value="index"
                                        v-model="dbSettingForm.storage_index_directory"
                                    ></el-switch>
                                </el-form-item>
                                <el-form-item
                                    :label="$t('tronSettingNeedToUpdateAsset')"
                                    prop="storage_needToUpdateAsset"
                                >
                                    <el-switch v-model="dbSettingForm.storage_needToUpdateAsset"></el-switch>
                                </el-form-item>
                            </div>
                        </el-card>
                    </el-col>
                </el-row>
                <el-form-item label-width="0" class="textRight">
                    <el-button
                        size="small"
                        type="primary"
                        @click="previousStepFun"
                    >{{$t('tronSettingPreviousStep')}}</el-button>
                    <el-button
                        size="small"
                        type="primary"
                        @click="saveData('dbSettingDialogForm')"
                    >{{$t('tronSettingNextStep')}}</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>
<script>
import { dbconfigApi } from "@/api/settingApi";

export default {
    name: "dbetting",
    props: ["detailInfoData"],
    data() {
        return {
            baseContentShow: true,
            dbSettingForm: {},
            radioVal: 0,
            dbSettingRules: {
                storage_db_sync: [
                    {
                        required: true,
                        message: this.$t("tronSettingPlaceholder"),
                        trigger: "change"
                    }
                ],
                storage_transHistory_switch: {
                    required: true,
                    message: this.$t("tronSettingPlaceholder"),
                    trigger: "change"
                },
                storage_db_engine: {
                    required: true,
                    message: this.$t("tronSettingPlaceholder"),
                    trigger: "change"
                },
                storage_index_directory: {
                    required: true,
                    message: this.$t("tronSettingPlaceholder"),
                    trigger: "change"
                },
                storage_needToUpdateAsset: {
                    required: true,
                    message: this.$t("tronSettingPlaceholder"),
                    trigger: "change"
                }
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
                    const newForm = {
                        isDBSync: this.dbSettingForm.storage_db_sync,
                        isOpenTransaction: this.dbSettingForm
                            .storage_transHistory_switch,
                        dbEnine: this.dbSettingForm.storage_db_engine,
                        indexDirectory: this.dbSettingForm
                            .storage_index_directory,
                        needToUpdateAsset: this.dbSettingForm
                            .storage_needToUpdateAsset
                    };
                    dbconfigApi(newForm)
                        .then(response => {
                            this.$emit("addSettingSuccess", true);

                            this.$message.success(
                                this.$t("tronSettingDBSaveSuccess")
                            );
                        })
                        .catch(error => {
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
            this.dbSettingForm = this.detailInfoData;
        }
    }
};
</script>
<style lang="scss" rel="stylesheet/scss" scoped>
.textRight {
    margin-top: 40px;
    text-align: right;
}
.textCenter {
    text-align: center;
}
.trondbSettingForm {
    padding: 0 80px 0 0;
}
</style>

