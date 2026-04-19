<template>
  <div id="app">
    <!-- 根路由视图：登录/注册页直接显示，Home页显示MainLayout -->
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
html, body, #app {
  width: 100%;
  height: 100%;
  font-family: "Helvetica Neue", Helvetica, "Microsoft YaHei", sans-serif;
}
</style>