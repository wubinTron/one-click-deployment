/* * @Author: lxm * @Date: 2019-10-21 15:25:02 * @Last Modified by: lxm * @Last
Modified time: 2019-11-13 16:12:45 * @dashboard-index */

<template>
  <div>
    <el-row :gutter="0" class="panel-group">
      <el-col :xs="16" :sm="16" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-description">
            <div class="card-panel-text">
              <i class="num">1</i>{{ $t("tronDashboardClickAddNode") }}
            </div>
            <div class="descCont">
              {{ $t("tronDashboardClickAddNodeTips") }}
            </div>
          </div>
        </div>
      </el-col>
      <el-col :xs="16" :sm="16" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-description">
            <div class="card-panel-text">
              <i class="num">2</i> {{ $t("tronDashboardClickSetting") }}
            </div>
            <div class="descCont">
              {{ $t("tronDashboardClickSettingTips") }}
            </div>
          </div>
        </div>
      </el-col>
      <el-col :xs="16" :sm="16" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-description">
            <div class="card-panel-text">
              <i class="num">3</i>{{ $t("tronDashboardClickPlugin") }}
            </div>
            <div class="descCont">{{ $t("tronDashboardClickPluginTips") }}</div>
          </div>
          <div class="card-panel-icon-wrapper icon-money">
            <svg-icon icon-class="money" class-name="card-panel-icon" />
          </div>
        </div>
      </el-col>
      <el-col :xs="16" :sm="16" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-description">
            <div class="card-panel-text">
              <i class="num">4</i>{{ $t("tronDashboardClickDeploy") }}
            </div>
            <div class="descCont">{{ $t("tronDashboardClickDeployTips") }}</div>
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
  // box-shadow: 0 0 1px rgba(0, 0, 0, 0.2);
  .card-panel-col {
    // margin-bottom: 32px;
  }
  .card-panel {
    height: 121px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, 0.05);
    border-color: rgba(0, 0, 0, 0.05);
    &:hover {
      background: #f0f0f7;
    }
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
        font-size: 14px;
        color: #373d41;
        font-weight: 600;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        line-height: 18px;
        margin-bottom: 15px;
        i {
          position: relative;
          width: 30px;
          height: 30px;
          display: inline-block;
          line-height: 28px;
          margin-right: 9px;
          text-align: center;
          color: #ff6a00;
          font-size: 30px;
          font-style: italic;
          font-weight: 700;
          &::after {
            content: "";
            position: absolute;
            top: 4px;
            left: 16px;
            width: 10px;
            height: 40px;
            border-left: 1px solid #ff6a00;
            background-color: #fff;
            transform: rotate(45deg);
          }
        }
      }
      .descCont {
        margin-left: 40px;
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
