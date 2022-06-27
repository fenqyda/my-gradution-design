<template>
  <div class="header_container">
    <div class="backg">
      <div class="header_img">
        <img src="../../common/img/logo-round.png" />
      </div>
      <div class="header_search">
        <!-- <div>
        <input type="text" placeholder="搜索  商品名称" v-model="keywords" />
        <el-button
          type="danger"
          icon="el-icon-search"
          @click="postSearch"
        ></el-button>
      </div> -->
        <div class="search_input">
          <input type="text" placeholder="搜索  商品名称" v-model="keywords" />
          <el-button
            type="danger"
            icon="el-icon-search"
            @click="postSearch"
          ></el-button>
        </div>
        <p>
          <a>限时满减</a>
          <a>美食</a>
          <a>甜点饮品</a>
          <a>蔬菜水果</a>
          <a>超市便利</a>
        </p>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { MessageBox } from "element-ui";
import { mapState } from "vuex";
import { mapActions } from "vuex";

export default {
  data() {
    return {
      keywords: "",
    };
  },
  computed: {
    ...mapState(["userInfo"]),
  },
  methods: {
    async postSearch() {
       if(this.userInfo.id != undefined){
        if (this.keywords) {
          let keywords = this.keywords;
          axios.get(`http://127.0.0.1:3000/recommend/search?keywords=${keywords}`)
          // this.$store.dispatch("reqSearch", { keywords });
          this.$router.replace("/searchdetail");
        }else{
          MessageBox({
              type: 'error',
              message: "请输入",
			        showClose: true,
          });
        }
      }else{
        MessageBox({
              type: "info",
              message: "请登录",
			        showClose: true,
          });
      }
    },
  },
};
</script>

<style scoped>
/*头部搜索区*/
.header_container {
  position: relative;
  width: 100%;
  height: 200px;
  margin: 0;
  padding: 0;
  background: url(./img/home_section_1.jpg) center center no-repeat;
}
.backg{
	background-color: rgba(0, 0, 0, 0.6);
	width: 100%;
	height: 200px;
}
/*商城标识图片区域*/
.header_container div> .header_img {
  position: absolute;
  left: 100px;
  margin-top: 10px;
  width: 140px;
  height: 140px;
  border: 1px solid darkgray;
  box-shadow: 2px 4px 15px rgba(45, 45, 45, 0.15);
  border-radius: 50%;
}
.header_img > img {
  width: 100%;
  height: 100%;
  border-radius: 50%;
}
/*鼠标移入图片实现3d动画*/
.header_img:hover {
  transform-style: preserve-3d;
  transform: rotateY(360deg);
  transition: all 1500ms;
  -moz-transform-style: preserve-3d;
  -moz-transform: rotateY(45deg);
  -moz-transition: all 1500ms;
}
.header_search {
  position: absolute;
  left: 50%;
  margin-left: -490px;
  margin-top: 70px;
}
.header_search .search_input {
  display: flex;
  justify-content: space-between;
  width: 1000px;
  height: 60px;
  border: none;
  border-radius: 5px;
  background-color: white;
}
.header_search div {
  display: flex;
  align-items: center;
}
.header_search input {
  padding-left: 10px;
  width: 900px;
  height: 55px;
  margin-left: 10px;
  font-size: 14px;
  line-height: 35px;
  border: none;
  border-right: none;
  outline: none;
}
.header_search button.el-button.el-button--danger {
  padding: 0;
  width: 150px;
  height: 50px;
  margin: 0 5px 0 10px;
  font-size: 20px;
  border-radius: 5px;
  outline: none;
  background-color: green;
  display: flex;
  justify-content: center;
  align-items: center;
}
.header_search > p > a {
  display: inline-block;
  padding: 5px 10px;
  font-size: 18px;
  color: white;
  cursor: pointer;
}
.header_search > p > a:hover {
  color: red;
}
</style>
