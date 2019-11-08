/*
 * @Author: lxm 
 * @Date: 2019-10-21 15:25:02 
 * @Last Modified by: lxm
 * @Last Modified time: 2019-10-24 15:45:31
 * @dashboard-index
 */

<template>
    <div>
        <el-row :gutter="10" class="panel-group">
            <el-col :xs="16" :sm="16" :lg="8" class="card-panel-col">
                <div class="card-panel" @click="handleSetLineChartData('newVisitis')">
                    <div class="card-panel-description">
                        <div class="card-panel-text">All Node</div>
                        <count-to
                            :start-val="0"
                            :end-val="102400"
                            :duration="2600"
                            class="card-panel-num"
                        />
                    </div>
                    <div class="card-panel-icon-wrapper icon-people">
                        <svg-icon icon-class="peoples" class-name="card-panel-icon" />
                    </div>
                </div>
            </el-col>
            <el-col :xs="16" :sm="16" :lg="8" class="card-panel-col">
                <div class="card-panel" @click="handleSetLineChartData('messages')">
                    <div class="card-panel-description">
                        <div class="card-panel-text">Witeness</div>
                        <count-to
                            :start-val="0"
                            :end-val="81212"
                            :duration="3000"
                            class="card-panel-num"
                        />
                    </div>
                    <div class="card-panel-icon-wrapper icon-message">
                        <svg-icon icon-class="message" class-name="card-panel-icon" />
                    </div>
                </div>
            </el-col>
            <el-col :xs="16" :sm="16" :lg="8" class="card-panel-col">
                <div class="card-panel" @click="handleSetLineChartData('purchases')">
                    <div class="card-panel-description">
                        <div class="card-panel-text">FullNode</div>
                        <count-to
                            :start-val="0"
                            :end-val="9280"
                            :duration="3200"
                            class="card-panel-num"
                        />
                    </div>
                    <div class="card-panel-icon-wrapper icon-money">
                        <svg-icon icon-class="money" class-name="card-panel-icon" />
                    </div>
                </div>
            </el-col>
        </el-row>
        <el-row :gutter="10" class="panel-group">
            <el-col :xs="16" :sm="16" :lg="8" class="card-panel-col">
                <div class="card-panel" @click="handleSetLineChartData('newVisitis')">
                    <div class="card-panel-description">
                        <div class="card-panel-text">Version</div>
                        <count-to
                            :start-val="0"
                            :end-val="3.5"
                            :duration="2600"
                            class="card-panel-num"
                        />
                    </div>
                    <div class="card-panel-icon-wrapper icon-people">
                        <svg-icon icon-class="peoples" class-name="card-panel-icon" />
                    </div>
                </div>
            </el-col>
            <el-col :xs="16" :sm="16" :lg="8" class="card-panel-col">
                <div class="card-panel" @click="handleSetLineChartData('messages')">
                    <div class="card-panel-description">
                        <div class="card-panel-text">{{$t('tronSettingNormalNode')}}</div>
                        <count-to
                            :start-val="0"
                            :end-val="81212"
                            :duration="3000"
                            class="card-panel-num"
                        />
                    </div>
                    <div class="card-panel-icon-wrapper icon-message">
                        <svg-icon icon-class="message" class-name="card-panel-icon" />
                    </div>
                </div>
            </el-col>
            <el-col :xs="16" :sm="16" :lg="8" class="card-panel-col">
                <div class="card-panel" @click="handleSetLineChartData('purchases')">
                    <div class="card-panel-description">
                        <div class="card-panel-text">{{$t('tronSettingUnnormalNode')}}</div>
                        <count-to
                            :start-val="0"
                            :end-val="9280"
                            :duration="3200"
                            class="card-panel-num"
                        />
                    </div>
                    <div class="card-panel-icon-wrapper icon-money">
                        <svg-icon icon-class="money" class-name="card-panel-icon" />
                    </div>
                </div>
            </el-col>
        </el-row>
    </div>
</template>

<script>
import CountTo from "vue-count-to";
export default {
    components: {
        CountTo
    },
    methods: {
        handleSetLineChartData(type) {
            this.$emit("handleSetLineChartData", type);
        }
    }
};
</script>

<style lang="scss" scoped>
.panel-group {
    margin-top: 18px;
    .card-panel-col {
        margin-bottom: 32px;
    }
    .card-panel {
        height: 108px;
        cursor: pointer;
        font-size: 12px;
        position: relative;
        overflow: hidden;
        color: #666;
        background: #fff;
        box-shadow: 4px 4px 40px rgba(0, 0, 0, 0.05);
        border-color: rgba(0, 0, 0, 0.05);
        .card-panel-icon-wrapper {
            float: right;
            margin: 14px 0 0 14px;
            padding: 16px;
            transition: all 0.38s ease-out;
            border-radius: 6px;
        }
        .card-panel-icon {
            float: left;
            font-size: 48px;
        }
        .card-panel-description {
            float: left;
            font-weight: bold;
            margin: 26px;
            .card-panel-text {
                line-height: 18px;
                color: rgba(0, 0, 0, 0.45);
                font-size: 16px;
                margin-bottom: 12px;
            }
            .card-panel-num {
                font-size: 20px;
            }
        }
    }
}
@media (max-width: 550px) {
    .card-panel-description {
        display: none;
    }
    .card-panel-icon-wrapper {
        float: none !important;
        width: 100%;
        height: 100%;
        margin: 0 !important;
        .svg-icon {
            display: block;
            margin: 14px auto !important;
            float: none !important;
        }
    }
}
</style>