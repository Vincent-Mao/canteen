import request from '@/utils/request'

// 查询收藏列表
export function listCollect(query) {
  return request({
    url: '/canteen/collect/list',
    method: 'get',
    params: query
  })
}

// 查询收藏详细
export function getCollect(collectId) {
  return request({
    url: '/canteen/collect/' + collectId,
    method: 'get'
  })
}

// 新增收藏
export function addCollect(data) {
  return request({
    url: '/canteen/collect',
    method: 'post',
    data: data
  })
}

// 修改收藏
export function updateCollect(data) {
  return request({
    url: '/canteen/collect',
    method: 'put',
    data: data
  })
}

// 删除收藏
export function delCollect(collectId) {
  return request({
    url: '/canteen/collect/' + collectId,
    method: 'delete'
  })
}

// 查询当前用户的菜品收藏列表
export function selectMyCollectDishes() {
  return request({
    url: '/canteen/collect/selectMyCollectDishes',
    method: 'get'
  })
}

// 根据菜品ID和用户ID查询收藏ID
export function selectCollectIdByDishesIdAndUserId(dishesId) {
  return request({
    url: '/canteen/collect/selectCollectIdByDishesIdAndUserId/' + dishesId,
    method: 'get'
  })
}
