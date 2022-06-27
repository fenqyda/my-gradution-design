<template>
  <div id="sales">
      <el-table :data="tableData" border style="width: 100%">
      <el-table-column prop="id" label="订单ID" width="100px">
      </el-table-column>
      <el-table-column prop="goodsname" label="餐品名"> </el-table-column>
      <el-table-column prop="price" label="价格" width="120px"> </el-table-column>
      <el-table-column prop="buycount" label="数量" width="50px"> </el-table-column>
      <!-- <el-table-column prop="ispay" label="是否付款" width="80px"> </el-table-column> -->
      <el-table-column label="操作" width="100px">
        <template slot-scope="scope">
          <el-button
            type="primary"
            size="small"
            @click="
              drawer = true;
              flag = false;
              handledelete(scope.row);
            "
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
  import {mapState} from 'vuex';
  import {mapActions} from 'vuex'
  import {getCartsGoodsnpay,deleteGoods} from './../../../api/index'

  export default {
    data() {
    return {
      tableData: [],
    };
  },
  computed: {
    ...mapState(["userInfo", "cartgoods"]),
  },
  mounted() {
    this.getCartsGoods();
  },
  methods: {
    async getCartsGoods() {
      let user_id = this.userInfo.id;
      const results = await getCartsGoodsnpay({userid:user_id});
      if (results) {
        this.tableData = results;
        //element ui表格的渲染用的是数组 这里的tableData定义成了对象就会报错
      }
    },
    async handledelete(row){
      const res =await deleteGoods({
          id:row.id
      });
      if (res) {
        this.getCartsGoods();
      }
      // console.log(row);
    }
  },
  }
  
</script>

<style scoped>

</style>
