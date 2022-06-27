<template>
  <div id="admin-users">
    <el-table :data="tableData" border style="width: 100%">
      <el-table-column prop="id" label="用户ID" width="70px"> </el-table-column>
      <el-table-column prop="username" label="账号"> </el-table-column>
      <el-table-column prop="phone" label="电话"> </el-table-column>
      <el-table-column prop="sex" label="性别" width="50px">
      </el-table-column>
      <el-table-column prop="nickname" label="昵称"> </el-table-column>
      <el-table-column prop="address" label="住址"> </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            @click="
              drawer = true;
              flag = true;
              handleClick(scope.row);
            "
            type="primary"
            size="small"
            >查看</el-button
          >
          <el-button
            type="primary"
            size="small"
            @click="
              drawer = true;
              flag = false;
              handleClick(scope.row);
            "
            >编辑</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <el-drawer
      title="编辑信息"
      ref="asidedrawer"
      :visible.sync="drawer"
      :direction="direction"
      :before-close="handleClose"
      :destroy-on-close="true"
    >
      <el-form
        :model="ruleForm"
        status-icon
        :rules="rules"
        ref="ruleForm"
        label-width="60px"
        class="demo-ruleForm"
        :disabled="flag"
      >
        <el-form-item label="账号" prop="name">
          <el-input
            type="text"
            v-model="ruleForm.name"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="昵称" prop="nickname">
          <el-input
            type="text"
            v-model="ruleForm.nickname"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="ruleForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="sex"  v-if=flag>
          <el-input v-model="ruleForm.sex"></el-input>
        </el-form-item>
        <el-form-item label="住址" prop="address"  v-if=flag>
          <el-input v-model="ruleForm.address"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button v-if="!flag" type="primary" @click="submitForm()"
            >提交</el-button
          >
        </el-form-item>
      </el-form>
    </el-drawer>
  </div>
</template>

<script>
import { Drawer } from "element-ui";
import { getAllUsers, changeUserInfo } from "../../../api/index";
export default {
  data() {
    var validateUsername = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入账户名"));
      } else if (this.ruleForm.username === value) {
        callback(new Error("账户名不能与原账户名一致"));
      }
      callback();
    };
    return {
      flag: false,
      tableData: [],
      drawer: false,
      direction: "rtl",
      ruleForm: {
        name: "",
        nickname: "",
        phone: "",
        sex: "",
        address: "",
      },
      userForm: {},
      rules: {
        name: [{ validator: validateUsername, trigger: "blur" }],
      },
    };
  },
  mounted() {
    this.getUsers();
  },
  methods: {
    async getUsers() {
      const results = await getAllUsers();
      if (results != null) {
        this.tableData = results;
      }
    },
    handleClick(row) {
      this.ruleForm.id = row.id;
      this.ruleForm.name = row.username;
      this.ruleForm.phone = row.phone;
      this.ruleForm.nickname = row.nickname;
      this.ruleForm.sex = row.sex;
      this.ruleForm.address = row.address;
    },
    handleClose(done) {
      done();
    },
    async submitForm() {
      var jsons = {
        id:this.ruleForm.id,
        username:this.ruleForm.username,
        sex:this.ruleForm.sex,
        nickname:this.ruleForm.nickname,
        address:this.ruleForm.address,
        phone:this.ruleForm.phone
      }
      // let formData = new FormData();
      // formData.append("id", this.ruleForm.id);
      // formData.append("nickname", this.ruleForm.nickname);
      // formData.append("name", this.ruleForm.name);
      // formData.append("sex", this.ruleForm.sex);
      // formData.append("address", this.ruleForm.address);
      console.log(jsons);
      let result = await changeUserInfo(jsons);
      if (result == true) {
        this.$message({
          type: "success",
          message: "修改成功",
        });
        this.$refs.asidedrawer.closeDrawer();
        this.getUsers();
      }
    },
  },
};
</script>

<style>
</style>
