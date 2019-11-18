import request from '@/utils/request'

export function checkBalanceApi(params) {
    return request({
        url: '/checkBalance',
        method: 'get',
        params
    })
}



// origin setting
export function originSettingApi(params) {
    return request({
        url: '/originConfig',
        method: 'get',
        params
    })
}



// config setting
export function configSettingApi(params) {
    return request({
        url: '/config',
        method: 'get',
        params
    })
}


// genesis setting
export function genesisSettingApi(params) {
    return request({
        headers: {
            'Content-Type': 'application/json'
        },
        transformRequest: [function(data) {
            data = JSON.stringify(data)
            return data
        }],
        url: '/genesisAssetConfig',
        method: 'post',
        data: params
    })
}

// base setting
export function baseSettingApi(params) {
    return request({
        url: '/baseSettingConfig',
        method: 'post',
        params
    })
}


// p2p setting
export function p2pSettingApi(params, listAry) {
    return request({
        url: '/p2pconfig',
        method: 'post',
        params,
        data: listAry
    })
}

// dbconfig setting
export function dbconfigApi(params) {
    return request({
        url: '/dbconfig',
        method: 'post',
        params
    })
}



// http/rpc setting
export function networkSettingApi(params) {
    return request({
        url: '/networkconfig',
        method: 'post',
        params
    })
}


// cross chain setting
export function crossChainSettingApi(params) {
    return request({
        url: '/crossChainConfig',
        method: 'post',
        params
    })
}