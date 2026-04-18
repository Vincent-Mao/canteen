import request from '@/utils/request'

// 查询菜品列表
export function listDishes(query) {
  return request({
    url: '/canteen/dishes/list',
    method: 'get',
    params: query
  })
}

// 查询菜品详细
export function getDishes(dishesId) {
  return request({
    url: '/canteen/dishes/' + dishesId,
    method: 'get'
  })
}

// 新增菜品
export function addDishes(data) {
  return request({
    url: '/canteen/dishes',
    method: 'post',
    data: data
  })
}

// 修改菜品
export function updateDishes(data) {
  return request({
    url: '/canteen/dishes',
    method: 'put',
    data: data
  })
}

// 删除菜品
export function delDishes(dishesId) {
  return request({
    url: '/canteen/dishes/' + dishesId,
    method: 'delete'
  })
}

// 查询所有菜品
export function selectAllDishes() {
  return request({
    url: '/canteen/dishes/selectAllDishes',
    method: 'get'
  })
}
