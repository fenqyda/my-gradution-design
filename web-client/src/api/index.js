import ajax from './ajax'

// 1. 基础路径
const BASE_URL = '/api';  // 防跨域
// const BASE_URL = 'http://localhost:3000';  // 服务器真实地址

// 2. 请求方法

// ======================= 用户数据相关 ===============================

// 请求短信验证码
export const getPhoneCode = (Phone) => ajax(BASE_URL + '/getcode', {Phone});

// 注册
export const Userreg = (params) => ajax(BASE_URL + '/user/insert', params, 'POST');

// 用户名和密码登录
export const pwdLogin = (username, pwd) => ajax(BASE_URL + '/user/login', {username, pwd}, 'POST');

export const getLogout = () => ajax(BASE_URL + '/api/logout');

// 获取登录的用户信息
export const getUserInfo = (id) => ajax(BASE_URL + '/user/find',id);

// 根据给定的id获取用户信息
export const getUserInfobyId = (params)=> ajax(BASE_URL + '/api/user_infobyid',params);

// 修改用户信息
export const changeUserInfo = (params) => ajax(BASE_URL + '/user/insert', params, 'POST');

// 修改用户密码
export const changeUserPwd = (id, pwd, newpwd) => ajax(BASE_URL + '/user/updatepwd', {id, pwd, newpwd}, 'POST');

// 修改用户手机
export const changeUserPhone = (params) => ajax(BASE_URL + '/user/insertnewphone', params, 'POST');

// 请求所有用户
export const getAllUsers = () => ajax(BASE_URL + '/user/findAll');

//======================== 餐品数据相关 ================================

// 添加商品到数据库
export const addGoodsToRecom = (params) => ajax(BASE_URL + '/recommend/uploaddata', params, 'POSTF');

// 删除recommend单个商品
export const deleteRecomGoods = (id) => ajax(BASE_URL + '/recommend/delete', id, 'DELETE');

// 修改recommend单个商品
export const changeGoodsInfo = (params) => ajax(BASE_URL + '/recommend/insert', params, 'POST');

// 请求首页的轮播图
export const getHomeCasual = () => ajax(BASE_URL + '/homecasual/findAll');

// 请求首页的分类数
export const getCategory = () => ajax(BASE_URL + '/category/findAll');

// 请求首页的商品数据
export const getHomeShopList = () => ajax(BASE_URL + '/recommend/homeshoplist');

// 请求推荐的商品数据
export const getRecommendShopList = (params) => ajax(BASE_URL + '/recommend/page', params);

// 请求所有商品
export const getAllgoods = () => ajax(BASE_URL + '/recommend/findAll');

// 请求商品详细数据
export const getGoodsDetail = (params) => ajax(BASE_URL + '/recommend/findbyid', params);

// 关键词搜索
export const searchKeywords = (keywords) => ajax(BASE_URL + '/recommend/search', {keywords}, 'POST');

//============================ 订单数据相关 ==================================

// 加入购物车
export const addGoodsToCart = (userid, goodsid, goodsname, thumburl, price, buycount) => ajax(BASE_URL + '/cart/insert', {userid, goodsid, goodsname, thumburl, price, buycount}, 'POST');

// 单个购物车餐品数量的改变
export const changeGoodsCount = (params) => ajax(BASE_URL + '/cart/insert', params, 'POST');

// 删除单个购物车餐品
export const deleteGoods = (id) => ajax(BASE_URL + '/cart/delete', id, 'DELETE');

// 删除所有购物车餐品
export const deleteAllGoods = (userid) => ajax(BASE_URL + '/cart/deletebyuid', userid, 'DELETE');

//购物车餐品结算
export const payCartGoods = (paygoods) => ajax(BASE_URL + '/cart/paygoods', paygoods, 'POST');

// 请求所有未支付和已支付的订单数据
export const getCartsGoods = (params) => ajax(BASE_URL + '/cart/findbuid', params);

// 请求购物车的数据(未付款)
export const getCartsGoodsnpay = (params) => ajax(BASE_URL + '/cart/findbuidnp', params);

// 请求购物车的数据(已付款)
export const getCartsGoodspayd = (params) => ajax(BASE_URL + '/cart/findbuidpd', params);

// 修改购物车餐品信息
export const changesalesInfo = (params) => ajax(BASE_URL + '/cart/insert', params, 'POST');

//请求订单数据（正在配送中）
export const getCartsGoodspay = (params) => ajax(BASE_URL + '/cart/findbuidts', params);

//请求订单数据（配送完成）
export const getCartsGoodssended = (params) => ajax(BASE_URL + '/cart/findbuidsended', params);

//======================= 评论数据相关 =======================

// 请求商品评价
export const getGoodsComment = (params) => ajax(BASE_URL + '/comments/findbyid', params);

// 请求所有评论
export const getAllGoodsComment = () =>ajax(BASE_URL + '/comments/findAll');

// 发布评论
export const postComment = (params) => ajax(BASE_URL + '/comments/insert', params, 'POST');

// 请求所有订单信息
export const getAllSales = () => ajax(BASE_URL + '/cart/findAll');

// 删除评论
export const deleteGoodscomment = (commentid) => ajax(BASE_URL + '/comments/delete', commentid, 'DELETE');

//====================== 管理员 ===============================

// 管理员登录
export const adminLogin = (account, pwd) => ajax(BASE_URL + '/administrators/login', {account, pwd}, 'POST');


