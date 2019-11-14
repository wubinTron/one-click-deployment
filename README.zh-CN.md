<p align="center">
  <img width="320" src="https://tronscan.org/static/media/tron-banner-1.e40b3379.png">
</p>

简体中文 | [English](./README.md)

## 简介

[one-click-deployment](https://github.com/wubinTron/one-click-deployment/tree/develop) 是一个一键发链解决方案， 帮助您快速构建更稳定、 安全的生产级区块链环境， 减少在区块链部署、 运维、 管理、 应用开发等方面的挑战， 使您更专注于核心业务创新， 并实现业务快速上链.

## 前序准备

你需要在本地安装 [node](http://nodejs.org/) 、[yarn](https://yarn.bootcss.com/docs/install/#mac-stable)和 [git](https://git-scm.com/)（推荐当前官网稳定版本，不推荐旧版本 Node,否则项目无法正常启动）。

本项目前端技术栈基于 [ES2015+](http://es6.ruanyifeng.com/)、[vue](https://cn.vuejs.org/index.html)、[vuex](https://vuex.vuejs.org/zh-cn/)、[vue-router](https://router.vuejs.org/zh-cn/) 、[vue-cli](https://github.com/vuejs/vue-cli) 、[axios](https://github.com/axios/axios) 和 [element-ui](https://github.com/ElemeFE/element),提前了解和学习这些知识会对使用本项目有很大的帮助。

 <p align="center">
  <!-- <img width="900" src="https://wpimg.wallstcn.com/a5894c1b-f6af-456e-82df-1151da0839bf.png"> -->
</p>

## 功能

```
- 添加节点

- 部署配置
  - 创世信息配置
  - 基础配置
  - 网络链接配置(http/rpc)
  - 数据库基础配置
  - p2p配置
  - 跨链配置

- 插件配置
  - 共识模块
  - 交易模块
  - 数据库模块

- 节点部署
  - 部署日志

```

## 启动步骤

```bash
# 克隆项目
git clone https://github.com/wubinTron/one-click-deployment.git

# 进入项目目录
cd one-click-deployment

# 安装依赖
./install.sh

# 启动服务
./deploy.sh

```

## Changelog

Detailed changes for each release are documented in the [release notes](https://github.com/wubinTron/one-click-deployment/releases).

## 浏览器支持

Modern browsers and Internet Explorer 10+.

| [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/edge/edge_48x48.png" alt="IE / Edge" width="24px" height="24px" />](https://godban.github.io/browsers-support-badges/)</br>IE / Edge | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/firefox/firefox_48x48.png" alt="Firefox" width="24px" height="24px" />](https://godban.github.io/browsers-support-badges/)</br>Firefox | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/chrome/chrome_48x48.png" alt="Chrome" width="24px" height="24px" />](https://godban.github.io/browsers-support-badges/)</br>Chrome | [<img src="https://raw.githubusercontent.com/alrra/browser-logos/master/src/safari/safari_48x48.png" alt="Safari" width="24px" height="24px" />](https://godban.github.io/browsers-support-badges/)</br>Safari |
| ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| IE10, IE11, Edge                                                                                                                                                                                                 | last 2 versions                                                                                                                                                                                                    | last 2 versions                                                                                                                                                                                                | last 2 versions                                                                                                                                                                                                |

## License

Copyright (c) 2019 Tron
