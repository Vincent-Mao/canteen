import request from '@/utils/request'

// 查询健康小贴士列表
export function listTip(query) {
  return request({
    url: '/canteen/tip/list',
    method: 'get',
    params: query
  })
}

// 查询健康小贴士详细
export function getTip(tipId) {
  return request({
    url: '/canteen/tip/' + tipId,
    method: 'get'
  })
}

// 新增健康小贴士
export function addTip(data) {
  return request({
    url: '/canteen/tip',
    method: 'post',
    data: data
  })
}

// 修改健康小贴士
export function updateTip(data) {
  return request({
    url: '/canteen/tip',
    method: 'put',
    data: data
  })
}

// 删除健康小贴士
export function delTip(tipId) {
  return request({
    url: '/canteen/tip/' + tipId,
    method: 'delete'
  })
}
