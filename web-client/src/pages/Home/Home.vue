<template>
  <div id="home">
    <div id="container">
      <el-row>
        <!-- 一级菜单 -->
        <el-col :span="4" :offset="1" class="cateList">
          <div class="cate_img">
            <i class="el-icon-office-building"></i>
            餐品分类
          </div>
          <el-menu
            class="el-menu-vertical-demo"
            background-color="#545c64"
            text-color="#fff"
          >
            <el-menu-item
              index="cate.cateId"
              v-for="cate in categoryList"
              :key="cate.cateId"
              @click="getRList(cate.cateId)"
			  text-color="#fff"
            >
              <i :class="cate.cateIcon"></i>
              <span slot="title">{{ cate.cateName }}</span>
            </el-menu-item>
          </el-menu>
        </el-col>
        <!--轮播图-->
        <el-col :span="14">
          <el-carousel v-if="homecasual.length > 0">
            <el-carousel-item v-for="casual in homecasual" :key="casual.id">
              <img :src="casual.imgurl" alt="" />
            </el-carousel-item>
          </el-carousel>
        </el-col>
        <!-- 登录信息板块 -->
        <el-col :span="4" class="con_log">
          <div>
            <img :src="userInfo.avatar" v-if="userInfo.avatar" />
            <img src="./img/no_login.jpg" v-else />
          </div>
          <p>Hi~ 欢迎来到网上餐厅</p>
          <p v-if="!userInfo.id">
            <router-link to="/login">登录</router-link
            ><router-link to="/login">注册</router-link>
          </p>
          <p v-else class="userIn">
            <a v-if="userInfo.nickname"
              >您好,{{ userInfo.nickname }}</a
            >
            <a v-else>您好,{{ userInfo.username | nameFormat }}</a>
            <router-link to="/me">个人中心</router-link>
          </p>
          <button class="welfare">新人福利</button>
          <button class="vip">加入会员</button>
        </el-col>
      </el-row>

      <!-- 分类产品展示区域 -->
      <div class="product" v-for="cate in categoryList" :key="cate.cateId">
        <div class="pro_line">
          <h3>{{ cate.cateName }}</h3>
          <div><a @click.prevent="getRList(cate.cateId)">MORE</a></div>
        </div>
        <div class="pro_show">
          <ProductItem
            v-for="pro in homeshoplist[cate.cateId - 1]"
            :key="pro.goodsid"
            :pro="pro"
          />
        </div>
      </div>
    </div>
    <div class="end_show"></div>

    <!-- 尾部 -->
    <div id="footer">
      <!-- <img src="./img/footer.png" /> -->
      <div class="footer_text"></div>
    </div>

    <!--左侧微型购物车区域-->
      <!-- v-if="this.$route.path.indexOf('/home') != -1" -->
    <DrawerSection
      @goShopCar="goShopCar"
    ></DrawerSection>
  </div>
</template>

<script>
import { mapState } from "vuex";
import DrawerSection from "./children/DrawerSection/DrawerSection";
import ProductItem from "../../components/ProductItem/ProductItem";
import { MessageBox } from "element-ui";
import { getHomeShopList } from '../../api';

export default {
  computed: {
    ...mapState(["homecasual", "categoryList", "homeshoplist", "userInfo"]),
  },
  components: {
    DrawerSection,
    ProductItem,
  },
  methods: {
    getRList(cateId) {
      if (this.userInfo.id != undefined) {
        this.$router.replace("/search/" + cateId + "/1");
      } else {
        MessageBox({
          type: "info",
          message: "请登录",
          showClose: true,
        });
      }
    },
    goShopCar() {
      if (this.userInfo.id) {
        this.$router.replace("/shopcar");
      } else {
        MessageBox({
          type: "info",
          message: "请先登录!",
          showClose: true,
        });
      }
      // console.log(this.userInfo.id);
    },
  },
  mounted() {
    // 请求轮播图数据
    this.$store.dispatch("reqHomeCasual");
    // 请求分类数据
    this.$store.dispatch("reqCategory");
    // 请求数据
    // console.log(this.userInfo);
    if (this.userInfo && this.userInfo.id) {
      let userid = this.userInfo.id;
      this.$store.dispatch("reqCartsGoods", { userid });
    }
  },
  watch: {
    categoryList() {
      this.$nextTick(() => {
        // 请求首页商品数据
        this.$store.dispatch("reqHomeShopList");
      });
    },
  },
};
</script>

<style scoped>
#home {
  font-family: "Microsoft YaHei";
}
/*产品展示、购买、分类简介区*/
#container {
  position: relative;
  margin: 0px auto;
  width: 100%;
  background-color: #f4f4f4;
  padding-top: 40px;
  overflow: scroll;
}

#container .el-row .el-col {
  height: 100%;
}
.el-row .cateList .cate_img {
  width: 100%;
  text-indent: 14px;
  height: 40px;
  color: white;
  line-height: 40px;
  font-size: 16px;
  background: #f10215;
}
.el-menu-item.is-active{
	color: white;
}
/*自动播放图片区域*/
.el-col .el-carousel {
  height: 100%;
  border-top: 2px solid #ff0036;
}
.el-carousel .el-carousel__item img {
  height: 100%;
  width: 100%;
}
/*一级菜单*/

/*展示登录信息区域*/
#container .el-row .con_log.el-col {
  background: white;
  text-align: center;
  font-size: 15px;
  color: #989898;
  border: 1px solid #ccc;
  height: 320px;
}
.con_log > div {
  margin-top: -30px;
  margin-left: 75px;
  margin-bottom: 60px;
  width: 70px;
  height: 70px;
}
.con_log > div > img {
  width: 100%;
  height: 100%;
  border-radius: 50%;
  box-shadow: 3px 3px 5px 5px #e3e3e3;
}
.con_log > p {
  margin-bottom: 20px;
}
.con_log > p > a {
  font-size: 13px;
  margin-right: 10px;
  margin-bottom: 5px;
  color: #666;
  cursor: pointer;
  text-decoration: none;
}
.con_log > .userIn > a {
  display: block;
}
.con_log > p > a:hover {
  color: red;
  font-weight: 700;
}
.con_log > button {
  margin-right: 5px;
  width: 70px;
  height: 25px;
  line-height: 25px;
  font-size: 12px;
  text-align: center;
  border-radius: 13px;
  box-shadow: 6px 8px 20px rgba(45, 45, 45, 0.15);
  transition: background 0.3s ease, color 0.3s ease;
  outline: none;
  border: none;
}
.con_log > button.welfare {
  color: #e43f3b;
  background: white;
}
.con_log > button.vip {
  color: #e5d790;
  background: #2d2d2a;
}
.con_log > button:hover {
  color: white;
  background: #e43f3b;
}
/*每类产品的简单展示*/
#container > .product {
  margin-top: 50px;
  width: 100%;
  height: 300px;
}
.product > .pro_line {
  width: 100%;
  height: 50px;
  text-align: center;
  line-height: 50px;
  font-size: 26px;
  font-family: sans-serif;
  font-weight: normal;
  color: #222;
  margin-bottom: 20px;
}
.pro_line > h3 {
  display: inline-block;
}
.pro_line > div {
  display: inline-block;
  margin-left: 15px;
  cursor: pointer;
}
.pro_line > div > a {
  color: red;
  font-weight: 700;
  font-size: 17px;
  text-decoration: none;
  transition: 0.5s;
}
.pro_line > div > a:after {
  content: "»";
  opacity: 0; /*opacity属性不占用文档流的空间*/
  transition: 0.5s;
}
.pro_line > div:hover a:after {
  opacity: 1;
}
.product > .pro_show {
  margin: 0 auto;
  padding-left: 20px;
  width: 980px;
  height: 200px;
}
.pro_1 {
  width: 100%;
  text-align: center;
  margin-top: 300px;
}
.pro_1 > .item_content {
  width: 100%;
  text-align: center;
}
/*底部对商城的补充说明*/
#footer {
  margin-top: -80px;
  width: 100%;
  height: 200px;
  background: #f0f3ef;
}
#footer > img {
  display: block;
  margin: 0 auto;
}
#footer > .footer_text {
  width: 100%;
  border-top: 1px solid #dedede;
}
</style>
