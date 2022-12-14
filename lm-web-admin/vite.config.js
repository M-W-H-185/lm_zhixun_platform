import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import WindiCSS from 'vite-plugin-windicss'
import path from 'path'

// https://vitejs.dev/config/
export default defineConfig({
  resolve: {
    // 配置路径别名
    alias: {
      '@': path.resolve(__dirname, 'src'),
    },
  },
  server: {
    // 请求代理
    proxy: {
      '/admin': {
        // 这里的地址是后端数据接口的地址
        target: 'http://localhost:8082/',
        //rewrite: (path) => path.replace(/^\/admin/, ''),
        // 允许跨域
        changeOrigin: true
      }
    }
  },
  plugins: [
    vue(),
    WindiCSS(),
  ]
})
