<template>
    <div id="sales">
        <el-table :data="tableData" border style="width: 100%">
            <el-table-column prop="id" label="订单ID" width="100px">
            </el-table-column>
            <el-table-column prop="goodsname" label="餐品名"> </el-table-column>
            <el-table-column prop="price" label="价格" width="120px"> </el-table-column>
            <el-table-column prop="buycount" label="数量" width="50px"> </el-table-column>
            <el-table-column prop="transittime" label="配送开始时间"> </el-table-column>
            <el-table-column prop="transiedtime" label="配送完成时间"> </el-table-column>
            <el-table-column label="操作" width="200px">
                <template slot-scope="scope">
                    <el-button type="primary" size="small" 
                    :disabled="scope.row.flag==1"
                    @click="
                        drawer = true;
                        flag = false;
                        handleend(scope.$index, scope.row);
                    ">已接收</el-button>
                    <el-button type="primary" size="small" 
                    :disabled="scope.row.flag==1"
                    @click="
                        drawer = true;
                        flag = false;
                        handledelete(scope.$index, scope.row);
                    ">退款</el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>

<script>
import { mapState } from 'vuex';
import { mapActions } from 'vuex'
import { getCartsGoodssended, deleteGoods, changesalesInfo } from './../../../api/index'

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
            let userid = this.userInfo.id;
            const results = await getCartsGoodssended({ userid: userid });
            if (results) {
                this.tableData = results;
                //element ui表格的渲染用的是数组 这里的tableData定义成了对象就会报错
            }
        },
        async handleend(index, row) {
                this.$confirm('请确认订单已收到, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.tableData[index].flag += 1;
                    const res = changesalesInfo(this.tableData[index]);
                    if (res) {
                        this.getCartsGoods();
                    }
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '取消'
                    });
                });
           this.tableData[index];
        },
        async handledelete(index, row) {
                this.$confirm('此操作将取消该订单，请先与商家联系, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.tableData[index].flag += 1;
                    const result = changesalesInfo(this.tableData[index]);
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
        }
    },
}

</script>

<style scoped>
</style>