import request from '@/utils/request'

// 查询营养推荐列表
export function listRecommend(query) {
  return request({
    url: '/canteen/recommend/list',
    method: 'get',
    params: query
  })
}

// 查询营养推荐详细
export function getRecommend(recommendId) {
  return request({
    url: '/canteen/recommend/' + recommendId,
    method: 'get'
  })
}

// 新增营养推荐
export function addRecommend(data) {
  return request({
    url: '/canteen/recommend',
    method: 'post',
    data: data
  })
}

// 修改营养推荐
export function updateRecommend(data) {
  return request({
    url: '/canteen/recommend',
    method: 'put',
    data: data
  })
}

// 删除营养推荐
export function delRecommend(recommendId) {
  return request({
    url: '/canteen/recommend/' + recommendId,
    method: 'delete'
  })
}
