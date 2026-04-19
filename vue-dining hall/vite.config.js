import { defineConfig, loadEnv } from 'vite'
import path from 'path'
// 如果 vue-user 有 plugins 目录，直接复制过来（没有则注释这行）
// import createVitePlugins from './vite/plugins'
import vue from '@vitejs/plugin-vue2' // Vue2 需用这个插件（Vue3 用 @vitejs/plugin-vue）

// 后端接口地址（和 vue-user 保持一致）
const baseUrl = 'http://localhost:8080'

export default defineConfig(({ mode, command }) => {
    const env = loadEnv(mode, process.cwd())
    const { VITE_APP_ENV } = env

    return {
        // 部署路径（和 vue-user 一致）
        base: VITE_APP_ENV === 'production' ? '/' : '/',

        // 插件（对齐 vue-user，没有plugins就只保留vue2插件）
        plugins: [
            vue(),
            // createVitePlugins(env, command === 'build') // 有plugins目录则解开注释
        ],

        // 路径别名（和 vue-user 完全一致）
        resolve: {
            alias: {
                '~': path.resolve(__dirname, './'),
                '@': path.resolve(__dirname, './src')
            },
            extensions: ['.mjs', '.js', '.ts', '.jsx', '.tsx', '.json', '.vue']
        },

        // 打包配置（复刻 vue-user）
        build: {
            sourcemap: command === 'build' ? false : 'inline',
            outDir: 'dist',
            assetsDir: 'assets',
            chunkSizeWarningLimit: 2000,
            rollupOptions: {
                output: {
                    chunkFileNames: 'static/js/[name]-[hash].js',
                    entryFileNames: 'static/js/[name]-[hash].js',
                    assetFileNames: 'static/[ext]/[name]-[hash].[ext]'
                }
            }
        },

        // 开发服务器（对齐 vue-user，仅改端口避免冲突）
        server: {
            port: 3002, // 商家端端口（和用户端区分，比如用户端用80，商家端用3002）
            host: '127.0.0.1',
            open: true, // 启动自动打开浏览器
            proxy: {
                // 复刻 vue-user 的代理规则
                '/dev-api': {
                    target: baseUrl,
                    changeOrigin: true,
                    rewrite: (p) => p.replace(/^\/dev-api/, '')
                },
                // springdoc 接口文档代理（和 vue-user 一致）
                '^/v3/api-docs/(.*)': {
                    target: baseUrl,
                    changeOrigin: true,
                }
            }
        },

        // CSS 配置（完全复刻 vue-user）
        css: {
            postcss: {
                plugins: [
                    {
                        postcssPlugin: 'internal:charset-removal',
                        AtRule: {
                            charset: (atRule) => {
                                if (atRule.name === 'charset') {
                                    atRule.remove()
                                }
                            }
                        }
                    }
                ]
            }
        }
    }
})