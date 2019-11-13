<template>
  <div class="dashboard-container">
    <el-row>
      <div class="tronContent">
        <h1 class="tronTitle">one-click-deployment</h1>
        <p class="action">
          <el-button type="warning" @click="startOneClickFun">{{
            $t("startTronOneClickDeploy")
          }}</el-button>
        </p>
      </div>
    </el-row>
    <el-row>
      <div class="help-body-title">一键发链</div>
      <p class="desc">
        一键发链服务（One Click
        Deployment，简称OCD）是一种基于主流区块链技术的企业级PaaS（Platform as a
        Service）平台服务，帮助您快速构建更稳定、安全的生产级区块链环境，减少在区块链部署、运维、管理、应用开发等方面的挑战，使您更专注于核心业务创新，并实现业务快速上链.
      </p>
      <div class="help-body-title">新手必看</div>
      <div id="first-element-introduction">
        <panel-group />
      </div>
      <div class="help-body-title">帮助文档</div>
      <div class="help">
        <el-button type="text" size="small">点击查看帮助文档</el-button>
      </div>
    </el-row>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

import { setToken } from "@/utils/auth"; // get token from cookie
import PanelGroup from "./components/PanelGroup";
import { oneClickApi } from "@/api/dashboard";
export default {
  name: "Dashboard",
  created() {},
  components: {
    PanelGroup
  },
  methods: {
    startOneClickFun() {
      // if (this.token == "plugin") {
      //     setToken("plugin");
      // } else {
      //     setToken("node");
      // }
      this.newp2pTimestampFun();
    },
    newp2pTimestampFun() {
      oneClickApi()
        .then(async response => {
          console.log(response);
          await this.$store.dispatch("user/changeRoles", "node").then(res => {
            console.log(res);
          });
          this.$router.push("/node/list");
        })
        .catch(error => {
          console.log(error);
        });
    }
  },
  computed: {
    ...mapGetters(["name", "token"])
  }
};
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
.desc {
  color: #373d41;
  font-size: 14px;
  font-weight: 400;
  line-height: 24px;
}

.help-body-title {
  margin: 28px 0 14px;
  padding-bottom: 5px;
  line-height: 33px;
  font-size: 20px;
  font-weight: 600;
}
.tronContent {
  text-align: center;
  max-height: 280px;
  display: block;
  margin: 0.5rem auto 1.5rem;
}
.help {
  margin-left: 20px;
}
</style>
