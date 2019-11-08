import request from '@/utils/request'

export function branchSaveApi(params) {
  return request({
    url: '/projectBranch',
    method: 'post',
    params
  })
}

export function branchGetApi(params) {
  return request({
    url: '/projectBranch',
    method: 'get',
    params
  })
}
