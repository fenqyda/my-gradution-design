<template>
  <div id="admin-sales">
    <el-table :data="tableData" border style="width: 100%">
      <el-table-column prop="id" label="ID" width="40px">
      </el-table-column>
      <el-table-column prop="goodsid" label="餐品ID" width="65px">
      </el-table-column>
      <el-table-column prop="userid" label="用户ID" width="65px">
      </el-table-column>
      <el-table-column prop="username" label="用户名" width="120px"> </el-table-column>
      <el-table-column prop="phone" label="电话" width="110px"> </el-table-column>
      <el-table-column prop="goodsname" label="餐品名"> </el-table-column>
      <el-table-column prop="price" label="价格" width="50px"> </el-table-column>
      <el-table-column prop="buycount" label="数量" width="50px"> </el-table-column>
      <el-table-column prop="ispay" label="是否付款" width="80px"> </el-table-column>
      <el-table-column prop="address" label="地址" width="180px"> </el-table-column>
      <el-table-column prop="transittime" label="配送开始时间" width="140px"> </el-table-column>
      <el-table-column label="操作" width="280px">
        <template slot-scope="scope">
          <el-button
            :disabled="scope.row.ispay"
            type="primary"
            size="small"
            @click="
              drawer = true;
              flag = false;
              handledelete(scope.row);
            "
            >删除</el-button
          >
          <el-button
            type="primary"
            size="small"
            v-if="scope.row.transit == 0"
            :disabled="scope.row.transit != 0"
            @click="
              drawer = true;
              flag = false;
              handletransit(scope.row);
            "
            >开始配送</el-button
          >
          <el-button
            type="primary"
            size="small"
            v-if="scope.row.transit != 2"
            :disabled="scope.row.transit == 2"
            @click="
              drawer = true;
              flag = false;
              handletransited(scope.row);
            "
            >已送达</el-button
          >
          <el-tag v-if="scope.row.transit == 2" type="success">订单已完成</el-tag>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import {MessageBox} from "element-ui";
import { deleteGoods,getAllSales,changesalesInfo } from "../../../api/index";
export default {
  data() {
    return {
      tableData: [],
      total:0,
      nowtime:"",
    };
  },
  mounted() {
    this.getAllSales();
  },
  methods: {
    async getAllSales() {
      const results = await getAllSales();
      if (results) {
        console.log(results);
        this.tableData = results;
        this.total = results.length;
        console.log(this.tableData);
        // console.log(this.total);
        //element ui表格的渲染用的是数组 这里的tableData定义成了对象就会报错
      }
    },
    async handledelete(row){
      const res =await deleteGoods({
          id:row.id
      });
      if (res === true) {
        MessageBox({
              type: 'success',
              message: "删除成功",
			        showClose: true,
          });
        this.getAllSales();
      }
      // console.log(row);
    },
    async handletransited(row){
      row.transit=2;
      var time = new Date().toLocaleString();
      row.transiedtime = time;
      console.log(row);
      const res = await changesalesInfo(row)
        if (res == true) {
          this.$message({
            type: "success",
            message: "订单已完成！",
          });
          this.getAllSales();
        }
      console.log(row);
    },
    async handletransit(row){
      if(row.ispay == "否"){
          this.$message({
            type: "error",
            message: "订单未支付",
          });
      }else{
        this.nowtime = new Date().toLocaleString()
        var jsons = {
          id:row.id,
          transit:1,
          transittime:this.nowtime
        }
        const res = await changesalesInfo(jsons)
        if (res == true) {
          this.$message({
            type: "success",
            message: "开始配送",
          });
          this.getAllSales();
        }
      }
    }
  },
};
</script>

<style scoped>
</style>
