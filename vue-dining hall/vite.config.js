import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'
// https://vitejs.dev/config/
export default defineConfig({
    plugins: [vue()],
    resolve: {
        // 配置路径别名，方便导入（如@/views/xxx）
        alias: {
            '@': path.resolve(__dirname, 'src')
        }
    },
    // 开发服务器代理（解决跨域）
    server: {
        port: 3002, // 前端运行端口（避免和后端/其他模块冲突）
        proxy: {
            '/api': {
                target: 'http://localhost:8080', // 后端接口地址
                changeOrigin: true,
                rewrite: (path) => path.replace(/^\/api/, '')
            }
        }
    }
})