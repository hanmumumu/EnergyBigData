<script setup>
import { useRouter } from 'vue-router'
import {reactive} from "vue";
import axios from '@/utils/axios'
import {localGet} from "@/utils/index.js";
const router = useRouter()
const gotoHome = () => {
  router.push('/home')
}

const stt = reactive({
   username:''

})

// 初始化执行方法
const getUserInfo= () => {
  axios.get('/aa/bb').then(res => { //axios命令找controller文件
    stt.username = res
  })
}

</script>

<template>

  <h1>This is an AA page</h1>
  <button @click="gotoHome"> 返回主页 </button>
  <el-input style="width: 200px" v-model="stt.username"/>
  <el-button type="primary" @click="getUserInfo"> 获取用户名 </el-button>

</template>

<style scoped>

</style>