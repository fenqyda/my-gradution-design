<template>
  <div>
    <el-table
      :data="tempData"
      style="width: 100%">
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="餐品名称">
              <span>{{ props.row.shortname }}</span>
            </el-form-item>
            <el-form-item label="餐品 ID">
              <span>{{ props.row.goodsid }}</span>
            </el-form-item>
            <el-form-item label="餐品分类">
              <span>{{ category[props.row.category - 1] }}</span>
            </el-form-item>
            <el-form-item label="餐品价格">
              <span>{{ (props.row.price) | priceFormat}}</span>
            </el-form-item>
            <el-form-item label="餐品描述">
              <span>{{ props.row.goodsname }}</span>
            </el-form-item>
            <el-form-item label="餐品图片">
              <img :src="props.row.thumburl" style="width:70px"/>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column
        label="餐品 ID"
        prop="goodsid">
      </el-table-column>
      <el-table-column
        label="餐品名称"
        prop="shortname">
      </el-table-column>
      <el-table-column
        label="描述"
        prop="goodsname">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="props">
          <el-button
            size="mini"
            @click="handleEdit(props.$index, props.row)">编辑</el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(props.$index, props.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      background
      layout="prev, pager, next"
      :page-count="pageNum"
      @current-change="handleCurrentChange">
    </el-pagination>
  </div>
</template>

<script>
  import {getAllgoods,deleteRecomGoods} from './../../../api/index';

  export default {
    data() {
      return {
        category: ['经典中餐','甜点饮品','便捷快餐','精致西餐','其它'],
        currentIndex: 1,
        pageSize: 10,
        tableData: [],
        tempData: [],
      }
    },
    mounted(){
      this.getAllGoods();
    },
    methods: {
      handleEdit(index, row) {
        console.log(index, row);
        window.localStorage.setItem('goodsInfo',JSON.stringify(row));
        this.$router.replace('/admin/adminupdate');
      },
      async handleDelete(index, row) {
        this.$confirm('您确定永久删除该餐品吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then( async() => {
		  let result = await deleteRecomGoods({id:row.goodsid});
          if(result == true){
            this.$message({
              type: 'success',
              message: '已删除'
            });
            this.tempData.splice(0);
            this.getAllGoods();
          }
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      handleCurrentChange(val) {
        this.currentIndex = val;
        this.tempData = [];
        this.tableData.forEach((data,index)=>{
          if(index >= (this.currentIndex-1) * this.pageSize && index < (this.currentIndex) * this.pageSize){
            this.tempData.push(data);
          }
        });
      },
      async getAllGoods(){
        let result = await getAllgoods();
        if(result != null){
          this.tableData = result;
          this.tableData.forEach((data,index)=>{
            if(index >= (this.currentIndex-1) * this.pageSize && index < (this.currentIndex) * this.pageSize){
              this.tempData.push(data);
            }
          });
        }
      },
    },
    computed: {
      pageNum(){
        return Math.ceil(this.tableData.length / this.pageSize);
      }
    },
    filters: {
      priceFormat(price) {
        return price.toFixed(2);
      },
    },
  }
</script>

<style scoped>
  .el-table{
    margin: 20px auto 50px;
  }
  .demo-table-expand {
    font-size: 0;
  }
  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }
  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }
  .el-pagination{
    text-align: center;
  }
</style>

