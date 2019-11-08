import router from './router'
import store from './store'
import {
  Message
} from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import {
  getToken
} from '@/utils/auth' // get token from cookie
import getPageTitle from '@/utils/get-page-title'

NProgress.configure({
  showSpinner: false
}) // NProgress Configuration

// no redirect whitelist

router.beforeEach(async (to, from, next) => {
  // start progress bar
  NProgress.start()

  // set page title
  document.title = getPageTitle(to.meta.title)

  // determine whether the user has logged in

  // const hasGetUserInfo = store.getters.name
  const hasToken = getToken()

  // if (hasToken) {
  //   try {
  //     const newRoleAry = [];
  //     const roleAry = ["node", "setting", "super", ]; // 权限控制
  //     newRoleAry.push(hasToken)
  //     // get user info
  //     // const accessRoutes = await store.dispatch(
  //     //   "permission/generateRoutes",
  //     //   newRoleAry
  //     // );
  //     // console.log(accessRoutes)
  //     // router.addRoutes(accessRoutes);
  //     next({
  //       ...to,
  //       replace: true
  //     });
  //   } catch (error) {
  //     // remove token and go to login page to re-login
  //     Message.error(error || 'Has Error')
  //     NProgress.done()
  //   }
  // } else {
  next();
  NProgress.done()
  // }

})

router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})
