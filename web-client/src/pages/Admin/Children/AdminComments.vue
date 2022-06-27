<template>
  <div id="admin-sales">
    <el-table :data="tableData" border style="width: 100%">
      <el-table-column prop="id" label="评论ID" width="70px"> </el-table-column>
      <el-table-column prop="userid" label="用户ID" width="70px">
      </el-table-column>
      <el-table-column prop="username" label="用户名" width="120px">
      </el-table-column>
      <el-table-column prop="shortname" label="餐品名" width="120px">
      </el-table-column>
      <el-table-column prop="commentdetail" label="评论内容"> </el-table-column>
      <el-table-column prop="rating" label="评论等级" width="120px">
      </el-table-column>
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
import { getAllGoodsComment, deleteGoodscomment } from "../../../api/index";
export default {
  data() {
    return {
      tableData: [],
    };
  },
  mounted() {
    this.getAllGoodsComment();
  },
  methods: {
    async getAllGoodsComment() {
      const results = await getAllGoodsComment();
      if (results != null) {
        // console.log(results.message);
        this.tableData = results;
        //element ui表格的渲染用的是数组 这里的tableData定义成了对象就会报错
      } else {
        console.log(111);
      }
    },
    async deleteGoodscomment(row) {
      const res = await deleteGoodscomment({
        commentid: row.id,
      });
      if (res.success_code === 200) {
        this.getAllGoodsComment();
      }
    },
    handledelete(row) {
      this.$confirm("此操作将永久删除该条评论, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.deleteGoodscomment(row);
          this.$message({
            type: "success",
            message: "删除成功!",
          });
          this.getAllGoodsComment();
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
  },
};
</script>

<style scoped>
</style>
