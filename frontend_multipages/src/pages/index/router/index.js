import { createRouter, createWebHashHistory } from 'vue-router'
import Login from '@/pages/index/views/Login.vue'
import Home from '@/pages/index/views/HomeView.vue'
import ExcelExample from '@/pages/index/views/ExcelExample.vue'
import AAA from '@/pages/index/views/AAA.vue'
const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: '/home'
    },
    {
      path: '/login',
      name: 'login',
      component: Login,
    },
    {
      path: '/home',
      name: 'home',
      component: Home,
    },
    {
      path: '/excelExample',
      name: 'excelExample',
      component: ExcelExample,
    },
    {
      path: '/AA',
      name: 'AA',
      component: AAA,  //跟文件名一样
    },

  ],
})

export default router
