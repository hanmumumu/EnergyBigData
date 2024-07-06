<template>
  <el-card class="user-container">
    <template #header>
      <div class="header">
        <el-button type="primary" :icon="Plus" @click="handleAddUser">添加教师</el-button>
        <el-button type="success" :icon="Check" @click="handleSolve">解除禁用</el-button>
        <el-button type="danger" :icon="Delete" @click="handleForbid">禁用教师</el-button>
        <el-popconfirm
            title="确定删除吗？"
            confirmButtonText='确定'
            cancelButtonText='取消'
            @confirm="handleDelete"
        >
          <template #reference>
            <el-button type="danger" :icon="Delete">批量删除</el-button>
          </template>
        </el-popconfirm>
      </div>
    </template>
    <Table  action='/teachers/list' ref="table">
      <template #column>
        <el-table-column
            type="selection"
            width="55">
        </el-table-column>
        <el-table-column
            prop="idteacher"
            label="教师id"
        >
        </el-table-column>
        <el-table-column
            prop="teachername"
            label="教师姓名"
        >
        </el-table-column>
        <el-table-column
            label="身份状态"
        >
          <template #default="scope">
            <span :style="scope.row.locked == 0 ? 'color: green;' : 'color: red;'">
              {{ scope.row.locked == 0 ? '正常' : '禁用' }}
            </span>
          </template>
        </el-table-column>
        <el-table-column
            label="是否注销"
        >
          <template #default="scope">
            <span :style="scope.row.deleted == 0 ? 'color: green;' : 'color: red;'">
              {{ scope.row.deleted == 0 ? '正常' : '注销' }}
            </span>
          </template>
        </el-table-column>
        <el-table-column
            label="操作"
            width="220"
        >
          <template #default="scope">
            <a style="cursor: pointer; margin-right: 10px" @click="handleEdit(scope.row.idteacher)">修改</a>
            <el-popconfirm
                title="确定删除吗？"
                confirmButtonText='确定'
                cancelButtonText='取消'
                @confirm="handleDeleteOne(scope.row.idteacher)"
            >
              <template #reference>
                <a style="cursor: pointer">删除</a>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </template>
    </Table>
  </el-card>
  <bbAddUser ref='addUser' :reload="reload_list" ></bbAddUser>
  <bbUpdateUser ref='updateUser' :reload="reload_list" ></bbUpdateUser>
</template>

<script setup>
import { ref } from 'vue'
import Table from '@/components/Table.vue'
import { ElMessage } from 'element-plus'
import { Plus, Delete, Check } from '@element-plus/icons-vue'
import axios from '@/utils/axios'
import bbAddUser from "@/components/bbAddUser.vue";
import bbUpdateUser from "@/components/bbUpdateUser.vue";
let addUser = ref(null)
let updateUser = ref(null)
let table = ref(null) //响应式引用
const handleSolve = () => {
  if (!table.value.state.multipleSelection.length) {
    ElMessage.error('请选择项')
    return
  }
  axios.put(`/teachers/0`, {
    ids: table.value.state.multipleSelection.map(item => item.idteacher)
  }).then(() => {
    ElMessage.success('解除成功')
    table.value.getList()
  })
}
const handleForbid = () => {
  if (!table.value.state.multipleSelection.length) {
    ElMessage.error('请选择项')
    return
  }
  axios.put(`/teachers/1`, {
    ids: table.value.state.multipleSelection.map(item => item.idteacher)
  }).then(() => {
    ElMessage.success('禁用成功')
    table.value.getList()
  })
}


const handleAddUser = () => {
  addUser.value.open()
}
const reload_list = () => {
  table.value.getList()
}
// 批量删除
const handleDelete = () => {
  if (!table.value.state.multipleSelection.length) {
    ElMessage.error('请选择项')
    return
  }
  axios.delete('/teachers', {
    data: {
      ids: table.value.state.multipleSelection.map(i => i.idteacher)
    }
  }).then(() => {
    ElMessage.success('删除成功')
    table.value.getList()
  })
}
// 单个删除
const handleDeleteOne = (idteacher) => {
  axios.delete('/teachers', {
    data: {
      ids: [idteacher]
    }
  }).then(() => {
    ElMessage.success('删除成功')
    table.value.getList()
  })
}
// 修改分类
const handleEdit = (idteacher) => {
  updateUser.value.open(idteacher)
}
</script>

<style>

</style>