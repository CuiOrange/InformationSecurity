import { createRouter, createWebHistory } from 'vue-router'
import request from "@/util/request";

const routes = [
	{
		path: '/',
		redirect: '/index'
	},
	{
		path: '/login',
		name: 'Login',
		component: () => import("@/views/Login.vue")
	},
	{
		path: '/register',
		name: 'Register',
		component: () => import("@/views/Register.vue")
	},
	{
		path: '/index',
		name: 'Index',
		component: () => import("@/views/Index.vue")
	}
]

// 创建路由实例
const router = createRouter({
	history: createWebHistory(),
	routes
})

// 检查是否存在于免登陆白名单
function inWhiteList(toPath) {
	const whiteList = ['/login', '/register', '/404']
	const path = whiteList.find((value) => {
		// 使用正则匹配
		const reg = new RegExp('^' + value)
		return reg.test(toPath)
	})
	return !!path
}

// 全局路由守卫
router.beforeEach((to, from, next) => {
	const token = sessionStorage.getItem("token")

	if (inWhiteList(to.path)) {
		next()
	} else {
		// 用户已登录
		if (token) {
			request.post("/authentication").then(res => {
				if (res.code === 200) {
					next()
				} else {
					next("/login")
				}
			})
		} else {
			next("/login")
		}
	}
})

export default router
