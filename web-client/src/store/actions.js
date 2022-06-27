import {
  getHomeCasual,
  getCategory,
  getHomeShopList,
  getRecommendShopList,
  getGoodsDetail,
  getGoodsComment,
  getUserInfo,
  getLogout,
  getCartsGoodsnpay,
  getCartsGoodspayd,
  deleteGoods,
  changeGoodsCount,
  deleteAllGoods,
  searchKeywords
} from '../api'

import {
  HOME_CASUAL,
  CATEGORY_LIST,
  HOME_SHOP_LIST,
  RECOMMEND_SHOP_LIST,
  GOODS_DETAIL,
  GOODS_COMMENT,
  USER_INFO,
  RESET_USER_INFO,
  CART_GOODS_LIST,
  CHANGE_GOODS_COUNT,
  SELECTED_ALL_GOODS,
  SELECTED_SINGLE_GOODS,
  DEL_SINGLE_GOODS,
  DEL_ALL_GOODS,
  SEARCH_KEYWORDS
} from './mutation-types'

export default {
  // 获取首页的轮播图
  async reqHomeCasual({commit}) {
    const result = await getHomeCasual();
    commit(HOME_CASUAL, {homecasual: result})
  },

  // 获取分类数组
  async reqCategory({commit}) {
    const result = await getCategory();
    commit(CATEGORY_LIST, {categoryList: result})
  },

  // 获取首页的商品数据
  async reqHomeShopList({commit}) {
    const result = await getHomeShopList();
    // console.log(result);
	commit(HOME_SHOP_LIST, {homeshoplist: result});
  },

  // 获取推荐的商品数据
  async reqRecommendShopList({commit}, params) {
    if (isNaN(params.category) || isNaN(params.pageNum)) {
      params.category = 1;
      params.pageNum = 1;
    }
    const result = await getRecommendShopList(params);
    commit(RECOMMEND_SHOP_LIST, {recommendshoplist: result});
  },

  // 获取商品详细数据
  async reqGoodsDetail({commit}, params) {
      const result = await getGoodsDetail(params);
      commit(GOODS_DETAIL, {goodsDetail: result});
  },

  // 获取商品评论
  async reqGoodsComment({commit},params) {
    const result = await getGoodsComment(params);
    commit(GOODS_COMMENT, {goodsComment: result});
  },

  // 同步用户的信息
  syncUserInfo({commit}, userInfo){
    commit(USER_INFO, {userInfo});
  },

  // 异步获取用户信息
  async getUserInfo({commit},params){
    let userInfo = {};
    const result = await getUserInfo(params);
    if(result){
      // console.log(result);
      userInfo = result;
      window.localStorage.setItem("userInfo",JSON.stringify(userInfo));
    }else{
      userInfo = JSON.parse(window.localStorage.getItem("userInfo"));
    }
    commit(USER_INFO, {userInfo});
  },

  // 退出登录
  // async logOut({commit}){
  //   const result = await getLogout();
  //   if(result){
  //     commit(RESET_USER_INFO);
  //   }
  // },

  // 请求购物车数据
  async reqCartsGoods({commit},params) {
    const result = await getCartsGoodsnpay(params);
    // console.log(result);
    if(result){
      commit(CART_GOODS_LIST, {cartgoods: result})
    }
  },

  // 单个商品数量的改变
  async updateGoodsCount({commit}, {goods, count}){
    let params = {
      id:goods.id,
      buycount:count,
    }
    const result = await changeGoodsCount(params);
    if(result){
      commit(CHANGE_GOODS_COUNT, {goods,count});
    }
  },

  // 是否选中所有商品
  selectedAll({commit}, {isSelectedAll}){
    commit(SELECTED_ALL_GOODS, {isSelectedAll});
  },

  // 是否选中单个商品
  singleSelected({commit}, {goods}){
    commit(SELECTED_SINGLE_GOODS, {goods});
  },

  // 删除单个商品
  async delSingleGoods({commit}, {goods}){
    const result = await deleteGoods({id:goods.id});
    console.log(goods.id);
    if(result){
      commit(DEL_SINGLE_GOODS, {goods});
    }
  },

  // 删除所有商品
  async delAllGoods({commit}, {userid}){
    console.log({'userid':userid});
    // console.log({userid:userid});
    const result = await deleteAllGoods({'userid':userid});
    if(result){
      commit(DEL_ALL_GOODS);
    }
  },

  // 模糊搜索
  async reqSearch({commit}, {keywords}){
    // console.log(keywords);
    // console.log(params);
    const result = await searchKeywords(keywords);
    if(result){
      let searchresults = result;
      commit(SEARCH_KEYWORDS, {searchresults});
    }
  }
}
