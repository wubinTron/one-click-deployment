<template>
    <div class="navbar">
        <hamburger
            :is-active="sidebar.opened"
            class="hamburger-container"
            @toggleClick="toggleSideBar"
        />
        <div class="breadcrumb-logo">
            <router-link to="/">
                <img src="../../assets/logo.png" alt />
            </router-link>
        </div>
        <div class="breadcrumb-title">{{title}}</div>
        <div class="right-menu">
            <el-dropdown
                class="avatar-container right-menu-item hover-effect"
                trigger="click"
                @command="handleCommand"
            >
                <div class="avatar-wrapper">
                    {{currentLanguage }}
                    <!-- {{$t('tronSettingChooseLanguage')}} -->
                    <i class="el-icon-caret-bottom" />
                </div>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item command="en">English</el-dropdown-item>
                    <el-dropdown-item command="zh">简体中文</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
    </div>
</template>

<script>
import { mapGetters } from "vuex";
import Breadcrumb from "@/components/Breadcrumb";
import Hamburger from "@/components/Hamburger";

export default {
    data() {
        return {
            title: "",
            currentLanguage:
                sessionStorage.getItem("currentLang") == "zh-CN"
                    ? "简体中文"
                    : "English"
        };
    },
    created() {
        const currrent = this.$store.state.user.role;
    },
    components: {
        Breadcrumb,
        Hamburger
    },
    computed: {
        ...mapGetters(["sidebar", "avatar"])
    },
    methods: {
        toggleSideBar() {
            this.$store.dispatch("app/toggleSideBar");
        },
        // async logout() {
        //     await this.$store.dispatch("user/logout");
        //     this.$router.push(`/login`);
        // },
        handleCommand(val) {
            if (val === "zh") {
                this.$i18n.locale = "zh-CN"; //关键语句
                sessionStorage.setItem("currentLang", "zh-CN");
                this.currentLanguage = "简体中文";
            } else {
                this.$i18n.locale = "en-US"; //关键语句
                sessionStorage.setItem("currentLang", "en-US");
                this.currentLanguage = "English";
            }
        }
    }
};
</script>

<style lang="scss" scoped>
.navbar {
    width: 100%;
    height: 50px;
    overflow: hidden;
    position: relative;
    //   background: #001529;
    // color: #fff;
    background: #f5f6f6;
    box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
    .hamburger-container {
        line-height: 46px;
        height: 100%;
        float: left;
        cursor: pointer;
        transition: background 0.3s;
        -webkit-tap-highlight-color: transparent;
        &:hover {
            background: rgba(0, 0, 0, 0.025);
        }
    }
    .breadcrumb-logo {
        height: 100%;
        float: left;
        position: relative;
        width: 132px;
        img {
            width: 132px;
            display: block;
            position: absolute;
            top: 0;
            bottom: 0;
            margin: auto;
        }
    }
    .breadcrumb-title {
        height: 100%;
        float: left;
        min-width: 132px;
        color: #fff;
        line-height: 50px;
        font-size: 16px;
        font-family: Helvetica Neue, Helvetica, PingFang SC, Hiragino Sans GB,
            Microsoft YaHei, SimSun, sans-serif;
    }
    .breadcrumb-container {
        float: left;
    }
    .right-menu {
        height: 100%;
        margin-right: 20px;
        float: right;
        &:focus {
            outline: none;
        }
        .right-menu-item {
            display: inline-block;
            padding: 0 8px;
            height: 100%;
            font-size: 18px;
            color: #5a5e66;
            vertical-align: text-bottom;
            &.hover-effect {
                cursor: pointer;
                transition: background 0.3s;
                &:hover {
                    background: rgba(0, 0, 0, 0.025);
                }
            }
        }
        .avatar-container {
            margin-right: 30px;
            .avatar-wrapper {
                margin-top: 5px;
                // position: relative;
                line-height: 40px;
                font-size: 0.9rem;
                height: 40px;
                padding: 0 15px;
                cursor: pointer;
                .el-icon-caret-bottom {
                    cursor: pointer;
                    position: absolute;
                    right: 0px;
                    top: 25px;
                    color: #aaa;
                    font-size: 12px;
                }
            }
        }
    }
}
</style>
