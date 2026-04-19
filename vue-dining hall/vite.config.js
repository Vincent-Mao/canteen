import { defineConfig, loadEnv } from 'vite'
import vue from '@vitejs/plugin-vue2'
import path from 'path'

const baseUrl = 'http://localhost:8080'

export default defineConfig(({ mode, command }) => {
    const env = loadEnv(mode, process.cwd())
    const { VITE_APP_ENV } = env

    return {
        base: VITE_APP_ENV === 'production' ? '/' : '/',

        plugins: [
            vue()
        ],

        resolve: {
            alias: {
                '~': path.resolve(__dirname, './'),
                '@': path.resolve(__dirname, './src')
            },
            extensions: ['.mjs', '.js', '.ts', '.jsx', '.tsx', '.json', '.vue']
        },

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

        server: {
            port: 3002,
            host: '0.0.0.0',
            open: true,
            proxy: {
                '/dev-api': {
                    target: baseUrl,
                    changeOrigin: true,
                    rewrite: (p) => p.replace(/^\/dev-api/, '')
                },
                '^/v3/api-docs/(.*)': {
                    target: baseUrl,
                    changeOrigin: true,
                }
            }
        },

        css: {
            // ✅【只加了这段】消除 Sass 旧 API 警告，不影响任何功能
            preprocessorOptions: {
                scss: {
                    api: 'modern-compiler',
                    silenceDeprecations: ['legacy-js-api']
                }
            },
            // 你原来的 postcss 保持不动
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