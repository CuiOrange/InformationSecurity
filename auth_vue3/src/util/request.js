import axios from 'axios';
import { ElMessage } from 'element-plus';
import router from '@/router';  // 直接引入 router 实例

const request = axios.create({
    baseURL: `http://localhost:8088`,  // 替换为你的API基础路径
    timeout: 30000,
    withCredentials: true  // 设置跨域携带 cookie
});

// request 拦截器
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';
    const token = sessionStorage.getItem("token");
    if (token) {
        config.headers['token'] = token;  // 设置请求头携带 token
    }
    return config;
}, error => {
    return Promise.reject(error);
});

// response 拦截器
request.interceptors.response.use(
    response => {
        let res = response.data;
        
        // 如果是返回的文件
        if (response.config.responseType === 'blob') {
            return res;
        }

        // 兼容服务端返回的字符串数据
        if (typeof res === 'string') {
            res = res ? JSON.parse(res) : res;
        }

        // 当权限验证不通过的时候给出提示，并跳转到登录页
        if (res.code === 401) {
            ElMessage.error(res.msg || '权限验证失败，请重新登录');
            router.replace("/login");  // 使用 router 实例跳转
        }
        return res;
    },
    error => {
        console.log('err' + error);  // for debug
        ElMessage.error(error.message || '请求错误');
        return Promise.reject(error);
    }
);

export default request;
