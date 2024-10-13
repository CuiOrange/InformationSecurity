<template>
    <div style="text-align: center;position: relative;top: 100px">
        <h1>欢迎{{ username }}来到首页</h1>
        <div style="margin: 20px">
            <el-button type="primary" @click="getUserInfo">查询用户信息</el-button>
            <el-button type="danger" @click="logout">退出登录</el-button>
        </div>
        <div style="width: 600px; height:100px;margin: 40px auto">
            <el-descriptions title="认证系统">
                <el-descriptions-item label="作者">{{ nickname }}</el-descriptions-item>
                <el-descriptions-item label="CSDN">
                    <el-link v-show="url" :href="url" type="success">主页地址</el-link>
                </el-descriptions-item>
                <el-descriptions-item label="居住地">{{ address }}</el-descriptions-item>
                <el-descriptions-item label="简介">
                    <el-tag size="small">{{ text }}</el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="联系qq">{{ qq }}</el-descriptions-item>
            </el-descriptions>
        </div>
    </div>
</template>

<script>
    export default {
        name: "IndexPage",
        data() {
            return {
                username: '',
                nickname: '',
                qq: '',
                address: '',
                url: '',
                text: ''
            }
        },
        methods: {
            getUserInfo() {
                this.$request.get("/getUserInfo").then(res => {
                    if (res.code === 200) {
                        console.log(res)
                        this.nickname = res.data.nickname
                        this.qq = res.data.qq
                        this.address = res.data.address
                        this.url = res.data.url
                        this.text = res.data.text
                    }
                })
            },
            logout() {
                sessionStorage.clear()
                this.$router.replace("/login")
            },
        },
        created() {
            this.username = sessionStorage.getItem("sysUser");
        }
    }
</script>

<style scoped>
</style>
