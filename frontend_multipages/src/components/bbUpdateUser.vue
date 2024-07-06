<template>
  <el-dialog
      :title= '教师修改'
      v-model="state.visible"
      width="400px"
  >
    <el-form :model="state.ruleForm" :rules="state.rules" ref="formRef" label-width="100px" class="user-form">
      <el-form-item label="用户名" prop="teachername">
        <el-input type="text" v-model="state.ruleForm.teachername"></el-input>
      </el-form-item>


    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="state.visible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import { reactive, ref } from 'vue'
import axios from '@/utils/axios'
import { ElMessage } from 'element-plus'


const props = defineProps({
  reload: Function
})
const formRef = ref(null)

const state = reactive({
  visible: false,
  ruleForm: {
    teachername: "",

  },
  rules: {
    teachername: [
      { required: 'true', message: '名称不能为空', trigger: ['change'] }
    ]
  },
})
// 获取详情
const getDetail = (idteacher) => {
  axios.get(`/teachers/${idteacher}`).then(res => {
    state.ruleForm = {
      teachername: res.teachername
    }
  })
}
// 开启弹窗
const open = (idteacher) => {
  state.visible = true
  if (idteacher) {
    state.idteacher=idteacher
    // 如果是有 id 传入，证明是修改模式
    getDetail(idteacher)
  }
}
// 关闭弹窗
const close = () => {
  state.visible = false
}
const submitForm = () => {
  formRef.value.validate((valid) => {
    if (valid) {
        axios.put('/teachers/update', {
          idteacher: state.idteacher,
          teachername: state.ruleForm.teachername

        }).then(res => {
          if (res == true) {
            ElMessage.success('修改成功')
            state.visible = false
            if (props.reload) props.reload()
          } else ElMessage.error("修改失败")
        })
      }
  })
}

defineExpose({ open, close })
</script>