<template>
	<div class="wrapper">
		<el-form :model="form" :rules="rules" label-width="80px" class="login-box" status-icon
			style="background-color: #fff;border-radius: 10px;position: relative;top:100px" ref="loginForm">
			<h3 class="login-title">欢迎登录</h3>
			<el-form-item label="用户名" prop="username">
				<el-input prefix-icon="el-icon-user" type="text" placeholder="请输入用户名" v-model="form.username" />
			</el-form-item>
			<el-form-item label="密码" prop="password">
				<el-input prefix-icon="el-icon-lock" type="password" placeholder="请输入密码" v-model="form.password" />
			</el-form-item>
			<el-form-item>
				<el-button type="primary" @click="onSubmit('loginForm')">登录</el-button>
				<el-button type="primary" @click="$router.push('/register')">注册</el-button>
			</el-form-item>
		</el-form>
		<el-dialog title="温馨提示" v-model="dialogVisible" width="30%" :before-close="handleClose">
			<span>请输入用户名和密码</span>
			<template #footer>
				<el-button type="primary" @click="dialogVisible = false">确 定</el-button>
			</template>
		</el-dialog>
	</div>
</template>

<script>
export default {
	name: "LoginPage",
	data() {
		return {
			form: {
				username: 'admin',
				password: 'admin'
			},
			// 表单验证， 需要在el-form-item- 元素中增加prop属性
			rules: {
				username: [{ required: true, message: '账号不可为空', trigger: 'blur' }],
				password: [{ required: true, message: '密码不可为空', trigger: 'blur' }]
			},
			// 对话框显示和隐藏
			dialogVisible: false
		}
	},
	methods: {
		handleClose() {
			console.log("Handle Close，空函数");
		},
		onSubmit(formName) {
			// 为表单绑定验证功能
			this.$refs[formName].validate((valid) => {
				if (valid) {
					this.$request.post('/login', {
						username: this.form.username,
						password: this.form.password
					})
					.then(res => {
						if (res.code === 200) {
							console.log(res);
							sessionStorage.setItem("sysUser", res.data.username)
							sessionStorage.setItem("token", res.data.token)
							// 使用 vue-router 路由到指定页面
							this.$router.replace("/index")
							this.$message.success(res.msg)
						} else {
							this.$message.error(this.form.username + res.msg)
						}
					})
				} else {
					this.dialogVisible = true;
					return false;
				}
			});
		}
	}
}
</script>

<style scoped>
.wrapper {
	height: 100vh;
	background-image: linear-gradient(to bottom right, #FC466B, #3F5EFB);
	overflow: hidden;
	/* color: #303133; */
}

.login-box {
	border: 1px solid #DCDFE6;
	width: 350px;
	margin: 50px auto;
	padding: 35px 35px 15px 35px;
	border-radius: 5px;
	/* box-shadow: 0 0 25px #909399; */
}

.login-title {
	text-align: center;
	margin: 0 auto 40px auto;
	color: #303133;
}
</style>
