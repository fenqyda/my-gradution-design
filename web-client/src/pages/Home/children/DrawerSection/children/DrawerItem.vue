<template>
  <div class="pro_item">
    <!-- <h4 class="item_title"><span>网上订餐</span><span class="item_one">{{goods.buycount *goods.price | moneyFormat}}</span></h4> -->
    <div class="item_content">
      <div class="pro_Chk">
        <input type="checkbox" class="pro_ChkElem" :checked="goods.checked" @click="singleSelected(goods)">
      </div>
      <div class="item-pic">
        <img :src="goods.thumburl"/>
      </div>
      <div class="item-info">{{goods.goodsname}}</div>
        <div class="item-amout">
          <el-input-number size="mini" v-model="goods.buycount" :min="0"  @change="updateGoodsCount(goods, goods.buycount)"></el-input-number>
        </div>
      <div class="item-sum"><strong>{{goods.buycount * goods.price | moneyFormat}}</strong></div>
    </div>
  </div>
</template>

<script>
  import {mapState} from 'vuex'

  export default {
    props: {
      goods: {
        type: Object,
        required: true
      }
    },
    computed: {
      ...mapState(['userInfo']),
    },
    methods:{
      // 更新单个商品数量
      updateGoodsCount(goods, count){
        // let userid = this.userInfo.id;
        if(count === 0){
          // 是否删除
          this.$confirm('是否删除该商品?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.$store.dispatch('delSingleGoods', {goods});
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
          }).catch(() => {
            this.$store.dispatch('updateGoodsCount', {goods, count: 1});
            this.$message({
              type: 'info',
              message: '已取消删除'
            });
          });
          return;
        }
        this.$store.dispatch('updateGoodsCount', {goods, count});
        this.$emit('computeShopCar');
      },
      // 单个商品的选中与否
      singleSelected(goods){
        this.$store.dispatch('singleSelected', {goods});
        this.$emit('computeShopCar');
      },
    },
  }
</script>

<style scoped>
	.pro_item{
		width: 100%;
		height: 90px;
		margin-bottom: 15px;
	}
	.pro_item>.item_title{
		padding: 0 15px;
		width: 100%;
		font-size: 12px;
    color: #666666;
		background: white;
    box-sizing: border-box;

    display: flex;
    align-items: center;
    justify-content: space-between;
	}
	.pro_item>.item_content{
		width: 100%;
		height: 60px;
		background: #f6f6f6;
    box-sizing: border-box;

    display: flex;
    align-items: center;
	}
	.item_content>.pro_Chk{
    flex: 2;

    display: flex;
    justify-content: center;
    align-items: center;
	}
	.item_content div{
    flex: 7;

    display: flex;
    align-items: center;
	}
	.item-pic{
		flex: 3;

    display: flex;
    justify-content: center;
    align-items: center;
	}
	.item-pic img{
		width: 45px;
		height: 45px;
	}
	.item-info{
		flex: 6;
		font-size: 12px;
		color: #999;
		overflow: hidden;
		text-overflow: ellipsis;
    -webkit-clamp: 2;

    display: flex;
    justify-content: center;
	}
	.item-amout{
    flex: 3;

    display: flex;
    justify-content: center;
	}
  .item-amout .el-input-number{
    width: 95px;
  }
	.item-sum{
		flex: 3;

    display: flex;
    justify-content: center;
    align-items: center;
	}
	.item-sum>strong{
		color: #f40;
		font-size: 13px;
	}
</style>
