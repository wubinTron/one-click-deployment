import request from '@/utils/request'

export function oneClickApi(params) {
    return request({
        url: '/oneClick',
        method: 'post',
    })
}