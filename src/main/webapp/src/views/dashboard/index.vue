<template>
    <div class="dashboard-container">
        <el-row>
            <div class="tronContent">
                <h1 class="tronTitle">one-click-deployment</h1>
                <p class="action">
                    <el-button
                        type="warning"
                        @click="startOneClickFun"
                    >{{ $t("startTronOneClickDeploy") }}</el-button>
                </p>
            </div>
        </el-row>
        <el-row>
            <div class="help-body-title">{{ $t("tronDashboardOneClickChain") }}</div>
            <p class="desc">{{ $t("tronDashboardOneClickChainDsc") }}</p>
            <div class="help-body-title">{{ $t("tronDashboardNoviceSee") }}</div>
            <div id="first-element-introduction">
                <panel-group />
            </div>
            <div class="help-body-title">{{ $t("tronDashboardHelp") }}</div>
            <div class="help">
                <el-button
                    type="text"
                    size="small"
                    @click="jumpHelp"
                >{{ $t("tronDashboardHelpTips") }}</el-button>
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
                    await this.$store
                        .dispatch("user/changeRoles", "node")
                        .then(res => {
                            console.log(res);
                        });
                    this.$router.push("/node/list");
                })
                .catch(error => {
                    console.log(error);
                });
        },
        jumpHelp() {
            let currentLanguage = sessionStorage.getItem("currentLang");
            if (currentLanguage == "en-US") {
                window.open(
                    "http://39.106.174.213/wubin1/one-click-deployment/blob/master/README.md",
                    "_blank"
                );
            } else {
                window.open(
                    "http://39.106.174.213/wubin1/one-click-deployment/blob/master/README.zh-CN.md",
                    "_blank"
                );
            }
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
