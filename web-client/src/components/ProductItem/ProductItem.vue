<template>
  <div class="pro">
    <img v-lazy="pro.thumburl" class="pro_img" />
    <div class="pro_text">
      <p>
        <em>{{ pro.price | moneyFormat }}</em>
      </p>
      <p>{{ pro.shortname }}</p>
      <p>网上餐厅</p>
    </div>
    <div class="add_btn">
      <a @click="goDetail(pro.goodsid)"
        ><el-button type="danger">查看详情</el-button></a
      >
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import { MessageBox } from "element-ui";

export default {
  props: {
    pro: {
      type: Object,
      required: true,
    },
  },
  computed: {
    ...mapState(["userInfo"]),
  },
  methods: {
    goDetail(id) {
      if (this.userInfo.username != undefined) {
        this.$router.replace("/goods/" + id);
      } else {
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
.pro {
  float: left;
  position: relative;
  margin-right: 20px;
  width: 300px;
  height: 100%;
  background: white;

  display: flex;
}
.pro > .pro_img {
  margin: 20px;
  width: 120px;
  height: 120px;
}
.pro > .pro_text {
  margin-top: 30px;
  height: 130px;
  width: 140px;
  line-height: 25px;
}
.pro_text > p {
  margin-bottom: 10px;
}
.pro_text > p:first-child {
  color: red;
  font-weight: 900;
}
.pro_text > p:nth-child(2) {
  font-size: 13px;
}
.pro_text > p:nth-child(3) {
  font-size: 12px;
  color: #999;
}
.pro > .add_btn {
  position: absolute;
  bottom: 15px;
  right: 20px;
}
.add_btn > a button {
  padding: 0;
  width: 90px;
  height: 30px;
  outline: none;

  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
