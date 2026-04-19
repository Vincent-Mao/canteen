import request from '@/utils/request'

/**
 * 获取商家个人信息
 * @returns {Promise}
 */
export const getProfile = () => {
    return request({
        url: '/diningHall/profile',
        method: 'get'
    })
}

/**
 * 修改商家基本信息
 * @param {Object} data - 个人信息参数
 * @param {string} data.shopName - 店铺名称
 * @param {string} data.phone - 联系电话
 * @param {string} [data.email] - 邮箱（可选）
 * @param {string} [data.avatar] - 头像地址（可选）
 * @param {string} [data.desc] - 店铺简介（可选）
 * @returns {Promise}
 */
export const updateProfile = (data) => {
    return request({
        url: '/diningHall/profile/update',
        method: 'post',
        data
    })
}

/**
 * 修改密码
 * @param {Object} data - 密码参数
 * @param {string} data.oldPassword - 原密码
 * @param {string} data.newPassword - 新密码
 * @param {string} data.confirmPassword - 确认新密码
 * @returns {Promise}
 */
export const updatePassword = (data) => {
    return request({
        url: '/diningHall/profile/updatePassword',
        method: 'post',
        data
    })
}