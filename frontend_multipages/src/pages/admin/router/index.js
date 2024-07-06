import { createRouter, createWebHashHistory } from 'vue-router'
import Login from '@/pages/index/views/Login.vue'
import Home from '@/pages/index/views/HomeView.vue'
import About from '@/pages/index/views/ExcelExample.vue'

const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: '/admin/index'
    },
    {
      path: '/admin/index',
      name: 'index',
      component: () => import( '@/pages/admin/views/Index.vue')
    },
    {
      path: '/admin/mycharts',
      name: 'mycharts',
      component: () => import( '@/pages/admin/views/Chart_Example.vue')
    },
    {
      path: '/admin/file_upload',
      name: 'file_upload',
      component: () => import( '@/pages/admin/views/FileUpload.vue'),

    },
    {
      path: '/admin/references',
      name: 'references',
      component: () => import( '@/pages/admin/views/References.vue')
    },
    {
      path: '/admin/user_manage',
      name: 'user_manage',
      component: () => import('@/pages/admin/views/UserManage.vue')
    },
    {
      path: '/admin/account',
      name: 'account',
      component: () => import( '@/pages/admin/views/Account.vue')
    },
    {
      path: '/admin/bb',
      name: 'bb',
      component: () => import( '@/pages/admin/views/bb.vue')
    },
    {
      path: '/admin/bbFile',
      name: 'bbfile',
      component: () => import( '@/pages/admin/views/bbFile.vue'),

    }
  ],
})

export default router
