import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
// import Layout from '@/layout'
import Layout from '@/newLayout';
/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [

    {
        path: '/',
        component: Layout,
        redirect: '/dashboard',
        children: [{
            path: 'dashboard',
            name: 'Dashboard',
            component: () =>
                import ('@/views/dashboard/index'),
            meta: {
                title: 'tronSettingMenuDashboard',
                icon: 'dashboard'
            }
        }]
    },
    {
        path: '/404',
        component: () =>
            import ('@/views/404'),
        hidden: true
    },

]
export const asyncRoutes = [

    {
        path: '/node',
        component: Layout,
        redirect: '/node/list',
        name: 'node',
        meta: {
            title: 'tronSettingMenuNode',
            icon: 'tree',
            roles: ['admin', 'node', 'setting', 'plugin', 'deploy']
        },
        children: [

            {
                path: 'list',
                name: 'nodeMenu',
                component: () =>
                    import ('@/views/node/index'),
                meta: {
                    title: 'tronSettingMenuNodeList',
                    icon: 'tree',
                    roles: ['admin', 'node', 'setting', 'plugin', 'deploy']
                }
            },

        ]
    },
    {
        path: '/setting',
        component: Layout,
        redirect: '/setting/list',
        name: 'setting',
        // hidden: true,
        meta: {
            title: '配置',
            icon: 'example',
            roles: ['admin', 'setting', 'plugin', 'deploy']
        },
        children: [

            {
                path: 'list',
                name: 'settinglist',
                component: () =>
                    import ('@/views/setting/setting'),
                meta: {
                    title: 'tronSettingMenuSetting',
                    icon: 'setting',
                    roles: ['admin', 'setting', 'plugin', 'deploy']
                },

            },
            {
                path: 'config',
                name: 'settingconfig',
                hidden: true,
                component: () =>
                    import ('@/views/setting/index'),
                meta: {
                    title: 'tronSettingMenuSetting',
                    icon: 'setting',
                    roles: ['admin', 'setting', 'plugin', 'deploy']
                }
            }
        ]
    },

    {
        path: '/plugin',
        component: Layout,
        // hidden: true, 
        redirect: '/plugin/list',
        name: 'pluginMenu',
        meta: {
            title: '插件管理',
            icon: 'example',
            roles: ['admin', 'plugin', 'deploy']
        },
        children: [{
            path: 'list',
            name: 'pluginlist',
            component: () =>
                import ('@/views/pluginList/index'),
            meta: {
                title: 'tronSettingMenuPlugin',
                icon: 'plugins',
                roles: ['admin', 'plugin', 'deploy']
            }
        }]
    },

    {
        path: '/deploy',
        component: Layout,
        redirect: '/deploy/list',
        name: 'deployMenu',
        meta: {
            title: '部署管理',
            icon: 'example',
            roles: ['admin', 'deploy']
        },
        children: [{
            path: 'list',
            name: 'pluginlist',
            component: () =>
                import ('@/views/node/deployment'),
            meta: {
                title: 'tronSettingMenuDeployment',
                icon: 'deployment',
                roles: ['admin', 'deploy']
            }
        }]
    },

    {
        path: '/tronscan',
        component: Layout,
        redirect: '/tronscan/list',
        name: 'tronscanMenu',
        meta: {
            title: 'tronscan浏览器',
            icon: 'example',
            roles: ['admin', 'deploy']
        },
        children: [{
            path: 'https://tronscan.org/#/',
            meta: {
                title: 'tronscanMenu',
                icon: 'link',
                roles: ['admin', 'deploy']
            }
        }]

    },
    // 404 page must be placed at the end !!!
    {
        path: '*',
        redirect: '/404',
        hidden: true
    }
]

const createRouter = () => new Router({
    // mode: 'history', // require service support
    scrollBehavior: () => ({
        y: 0
    }),
    routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
    const newRouter = createRouter()
    router.matcher = newRouter.matcher // reset router
}

export default router