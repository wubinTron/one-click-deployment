import request from '@/utils/request'

// get pluginConfig

export function pluginConfigApi() {
  return request({
    url: '/pluginConfig',
    method: 'get',
  })
}


// dbEngine setting
export function dbEngineApi(params) {
  return request({
    url: '/dbEngine',
    method: 'post',
    params
  })
}

// /transaction setting
export function transactionApi(params) {
  return request({
    url: '/transaction',
    headers: {
      'Content-Type': 'application/json'
    },
    transformRequest: [function (data) {
      data = JSON.stringify(data)
      return data
    }],
    method: 'post',
    data: params
  })
}

// /transaction setting
export function consensusApi(params) {
  return request({
    url: '/consensus',
    method: 'post',
    params
  })
}


// /crypto setting
export function cryptoApi(params) {
  return request({
    url: '/crypto',
    method: 'post',
    params
  })
}
