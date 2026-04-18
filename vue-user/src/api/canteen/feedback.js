import request from '@/utils/request'

// 查询意见反馈列表
export function listFeedback(query) {
  return request({
    url: '/canteen/feedback/list',
    method: 'get',
    params: query
  })
}

// 查询意见反馈详细
export function getFeedback(feedbackId) {
  return request({
    url: '/canteen/feedback/' + feedbackId,
    method: 'get'
  })
}

// 新增意见反馈
export function addFeedback(data) {
  return request({
    url: '/canteen/feedback',
    method: 'post',
    data: data
  })
}

// 修改意见反馈
export function updateFeedback(data) {
  return request({
    url: '/canteen/feedback',
    method: 'put',
    data: data
  })
}

// 删除意见反馈
export function delFeedback(feedbackId) {
  return request({
    url: '/canteen/feedback/' + feedbackId,
    method: 'delete'
  })
}
