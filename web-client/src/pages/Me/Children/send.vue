<template>
  <div id="sales">
    <el-table :data="tableData" border style="width: 100%">
      <el-table-column prop="id" label="订单ID" width="100px">
      </el-table-column>
      <el-table-column prop="goodsname" label="餐品名"> </el-table-column>
      <el-table-column prop="price" label="价格" width="120px"> </el-table-column>
      <el-table-column prop="buycount" label="数量" width="50px"> </el-table-column>
      <el-table-column prop="createtime" label="下单时间"> </el-table-column>
      <el-table-column prop="transittime" label="配送时间"> </el-table-column>
      <el-table-column label="操作" width="100px">
        <template slot-scope="scope">
          <el-button type="primary" size="small" 
          v-if="scope.row.ispay=='是'"
          @click="
            drawer = true;
          flag = false;
          handledelete(scope.row);
          ">取消订单</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { mapState } from 'vuex';
import { mapActions } from 'vuex'
import { getCartsGoodspayd, deleteGoods } from './../../../api/index'

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
      const results = await getCartsGoodspayd({ userid: user_id });
      if (results) {
        this.tableData = results;
        //element ui表格的渲染用的是数组 这里的tableData定义成了对象就会报错
      }
    },
    async handledelete(row) {
      this.$confirm('此操作将取消该订单，请先与商家联系, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const res = deleteGoods({
          id: row.id
        });
        if (res) {
          this.getCartsGoods();
        }
        this.$message({
          type: 'success',
          message: '取消成功!'
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '订单保留'
        });
      });

      // console.log(row);
    }
  },
}

</script>

<style scoped>
</style>