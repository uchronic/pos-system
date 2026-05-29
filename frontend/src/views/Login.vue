<template>
  <div class="login-container">
    <div class="login-left">
      <div class="login-left-content">
        <h2>POS 收银管理系统</h2>
        <p>一站式零售管理解决方案</p>
        <div class="features">
          <div class="feature-item"><el-icon><Goods /></el-icon><span>商品管理</span></div>
          <div class="feature-item"><el-icon><User /></el-icon><span>会员管理</span></div>
          <div class="feature-item"><el-icon><DataAnalysis /></el-icon><span>数据报表</span></div>
          <div class="feature-item"><el-icon><ShoppingCart /></el-icon><span>连锁管理</span></div>
        </div>
      </div>
    </div>
    <div class="login-right">
      <div class="login-card">
        <div class="login-header">
          <div class="logo-icon"><el-icon size="32" color="#409eff"><Shop /></el-icon></div>
          <h1>欢迎登录</h1>
          <p>零售收银管理系统 v1.0</p>
        </div>
        <el-form :model="form" :rules="rules" ref="formRef" @submit.prevent="handleLogin">
          <el-form-item prop="username">
            <el-input v-model="form.username" prefix-icon="User" placeholder="请输入用户名" size="large" />
          </el-form-item>
          <el-form-item prop="password">
            <el-input v-model="form.password" prefix-icon="Lock" type="password" placeholder="请输入密码" size="large" show-password />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" size="large" style="width:100%" :loading="loading" @click="handleLogin">登 录</el-button>
          </el-form-item>
        </el-form>
        <div class="login-tips">演示账号: admin / admin123</div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { login } from '../api'

const router = useRouter()
const formRef = ref(null)
const loading = ref(false)
const form = ref({ username: 'admin', password: 'admin123' })
const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const handleLogin = async () => {
  await formRef.value.validate()
  loading.value = true
  try {
    const res = await login(form.value)
    localStorage.setItem('token', res.data.token)
    localStorage.setItem('user', JSON.stringify(res.data))
    ElMessage.success('登录成功')
    router.push('/dashboard')
  } catch (e) {
    ElMessage.error(e.message || '登录失败')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-container { height: 100vh; display: flex; }
.login-left {
  flex: 1; display: flex; align-items: center; justify-content: center;
  background: linear-gradient(135deg, rgba(64,158,255,0.9) 0%, rgba(102,126,234,0.9) 100%),
              url('/images/login-bg.jpg') center/cover;
  position: relative; overflow: hidden;
}
.login-left::before {
  content: ''; position: absolute; top: -50%; left: -50%; width: 200%; height: 200%;
  background: radial-gradient(circle, rgba(255,255,255,0.1) 0%, transparent 60%);
  animation: pulse 8s ease-in-out infinite;
}
@keyframes pulse { 0%,100% { transform: scale(1); } 50% { transform: scale(1.1); } }
.login-left-content { position: relative; z-index: 1; color: #fff; padding: 40px; }
.login-left-content h2 { font-size: 36px; margin-bottom: 12px; }
.login-left-content p { font-size: 16px; opacity: 0.9; margin-bottom: 40px; }
.features { display: grid; grid-template-columns: 1fr 1fr; gap: 20px; }
.feature-item { display: flex; align-items: center; gap: 10px; background: rgba(255,255,255,0.15); padding: 16px 20px; border-radius: 10px; backdrop-filter: blur(10px); }
.feature-item span { font-size: 14px; }
.login-right { width: 480px; display: flex; align-items: center; justify-content: center; background: #f5f7fa; }
.login-card { width: 380px; padding: 40px; background: #fff; border-radius: 12px; box-shadow: 0 8px 40px rgba(0,0,0,0.08); }
.login-header { text-align: center; margin-bottom: 32px; }
.logo-icon { width: 64px; height: 64px; background: #ecf5ff; border-radius: 16px; display: flex; align-items: center; justify-content: center; margin: 0 auto 16px; }
.login-header h1 { font-size: 22px; color: #303133; margin-bottom: 6px; }
.login-header p { color: #909399; font-size: 13px; }
.login-tips { text-align: center; color: #909399; font-size: 12px; margin-top: 16px; }
</style>
