import {
    // originSettingApi,
    configSettingApi
} from '@/api/settingApi'


const state = {
    // originSetting: {},
    configSetting: {},
    settingStep: 0,
    pluginStep: 0,
}

const mutations = {
    // SET_ORIGINCONFIG: (state, data) => {
    //     state.originSetting = data
    // },
    SET_CONFIGSETTING: (state, data) => {
        state.configSetting = data
    },
    SET_CURRENTSTEP: (state, step) => {
        state.settingStep = step
    },
    SET_PLUGINSTEP: (state, step) => {
        state.pluginStep = step
    },

}

const actions = {

    // get setting info
    // getOriginConfig({
    //     commit,
    // }) {
    //     return new Promise((resolve, reject) => {
    //         originSettingApi().then(response => {
    //             const {
    //                 data
    //             } = response

    //             if (!data) {
    //                 reject('Verification failed, please Login again.')
    //             }
    //             commit('SET_ORIGINCONFIG', data)
    //             resolve(data)
    //         }).catch(error => {
    //             reject(error)
    //         })
    //     })
    // },
    getConfigSetting({
        commit,
    }) {
        return new Promise((resolve, reject) => {
            configSettingApi().then(response => {
                const {
                    data
                } = response

                if (!data) {
                    reject('Verification failed, please Login again.')
                }
                commit('SET_CONFIGSETTING', data)
                resolve(data)
            }).catch(error => {
                reject(error)
            })
        })
    },

    getCurrentStepConfig({
        commit,
    }, stepInfo) {
        return new Promise((resolve, reject) => {
            const {
                step
            } = stepInfo;
            sessionStorage.setItem('currentstep', step)
            commit('SET_CURRENTSTEP', step)

        })
    },

    getCurrentPluginStepConfig({
        commit,
    }, stepInfo) {
        return new Promise((resolve, reject) => {
            // console.log(stepInfo, 'stepInfo')
            const {
                step
            } = stepInfo;
            sessionStorage.setItem('pluginstep', step)
            commit('SET_PLUGINSTEP', step)

        })
    },



}

export default {
    namespaced: true,
    state,
    mutations,
    actions
}