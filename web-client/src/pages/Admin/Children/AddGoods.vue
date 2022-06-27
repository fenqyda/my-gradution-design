<template>
  <div id="addgoods">
    <h3>餐品上架</h3>
    <el-form
      :model="ruleForm"
      :rules="rules"
      ref="ruleForm"
      label-width="100px"
      class="demo-ruleForm"
    >
      <el-form-item label="餐品ID" prop="goodsid" style="width: 250px">
        <el-input v-model="ruleForm.goodsid" type="number"></el-input>
      </el-form-item>
      <el-form-item label="餐品名称" prop="shortname" style="width: 500px">
        <el-input v-model="ruleForm.shortname"></el-input>
      </el-form-item>
      <el-form-item label="餐品图片" style="width: 500px">
        <el-upload
          list-type="picture-card"
          :on-preview="handlePictureCardPreview"
          :on-change="handleChange"
          :on-success="handleAvatarSuccess"
          :auto-upload="false"
          :multiple="false"
          limit:1
          action="http://localhost:3000/recommend/uprecommendthumb"
        >
          <i class="el-icon-plus"></i>
        </el-upload>
        <el-dialog :visible.sync="dialogVisible">
          <img width="100%" :src="dialogImageUrl" alt="" />
        </el-dialog>
      </el-form-item>
      <el-form-item label="餐品特价" prop="price" style="width: 250px">
        <el-input v-model="ruleForm.price" type="number"></el-input>
      </el-form-item>
      <el-form-item label="餐品分类" prop="category">
        <el-select v-model="ruleForm.category" placeholder="请选择">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="餐品标签" prop="salestip" style="width: 500px">
        <el-input v-model="ruleForm.salestip"></el-input>
      </el-form-item>
      <el-form-item label="餐品详情" prop="goodsname" style="width: 500px">
        <el-input
          type="textarea"
          v-model="ruleForm.goodsname"
          autosize
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')"
          >立即创建</el-button
        >
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { mapState } from "vuex";
import { addGoodsToRecom, getAllgoods } from "./../../../api/index";

export default {
  data() {
    return {
      goodsinfo: {},
      fileList: [],
      dialogImageUrl: "",
      dialogVisible: false,
      ruleForm: {
        goodsid: null,
        shortname: "",
        goodsname: "",
        price: null,
        salestip: "",
        category: 1,
      },
      rules: {
        goodsid: [{ required: true, message: "请输入餐品ID", trigger: "blur" }],
        shortname: [
          { required: true, message: "请输入餐品名称", trigger: "blur" },
        ],
        goodsname: [
          { required: true, message: "请填写餐品详情", trigger: "blur" },
        ],
        price: [{ required: true, message: "请输入餐品特价", trigger: "blur" }],
        category: [
          { required: true, message: "请选择餐品分类号", trigger: "blur" },
        ],
        salestip: [
          { required: true, message: "请输入餐品标签", trigger: "blur" },
        ],
      },
      options: [
        {
          value: 1,
          label: "经典中餐",
        },
        {
          value: 2,
          label: "甜点饮品",
        },
        {
          value: 3,
          label: "便捷快餐",
        },
        {
          value: 4,
          label: "精致西餐",
        },
        {
          value: 5,
          label: "其它",
        },
      ],
    };
  },
  methods: {
    handleChange(file, fileList) {
      if (fileList.length > 1) {
        fileList = fileList.slice(-1);
      }
      this.fileList = fileList;
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handleAvatarSuccess(res) {
      console.log(res);
    },
    async submitForm(formName) {
      let flag = false;
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$refs[formName].model.goodsid = Number(
            this.$refs[formName].model.goodsid
          );
          // this.$refs[formName].model.price = Number(this.$refs[formName].model.price);
          // console.log(this.$refs[formName].model);
          flag = true;
        }
      });
      if (!this.fileList.length) {
        flag = false;
      }
      const formData = new FormData();
      this.fileList.forEach((file) => {
        formData.append("file", file.raw);
      });
      if (flag) {
        formData.append("goodsid",this.$refs[formName].model.goodsid);
        formData.append("shortname",this.$refs[formName].model.shortname);
        formData.append("goodsname",this.$refs[formName].model.goodsname);
        formData.append("price",this.$refs[formName].model.price);
        formData.append("salestip",this.$refs[formName].model.salestip);
        formData.append("category",this.$refs[formName].model.category);
        // this.goodsinfo = {
        //   goodsid: this.$refs[formName].model.goodsid,
        //   shortname: this.$refs[formName].model.shortname,
        //   goodsname: this.$refs[formName].model.goodsname,
        //   price: this.$refs[formName].model.price,
        //   salestip: this.$refs[formName].model.salestip,
        //   category: this.$refs[formName].model.category,
        //   file: formData
        // };

        let result = await addGoodsToRecom(formData);
        /*
              let result = await addGoodsToRecom({
                goods_id: this.$refs[formName].model.goods_id,
                short_name: this.$refs[formName].model.short_name,
                goods_name: this.$refs[formName].model.goods_name,
                counts: this.$refs[formName].model.counts,
                price: this.$refs[formName].model.price,
                sales_tip: this.$refs[formName].model.sales_tip,
                category: this.$refs[formName].model.category
              });
          */
        if (result.status === 100) {
          this.$message({
            type: "success",
            message: result.message,
          });
          this.$router.replace("/admin");
          getAllgoods();
        } else if (result.status === 999) {
          this.$message({
            type: "info",
            message: result.message,
          });
        } else {
          this.$message({
            type: "error",
            message: result.message,
          });
        }
      }
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
  },
};
</script>

<style scoped>
#addgoods {
  padding-top: 20px;
}
#addgoods > h3 {
  font-weight: bolder;
  margin: 10px 10px 30px 10px;
}
</style>

