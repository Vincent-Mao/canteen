<template>
  <div id="app">
    <!-- 恢复路由视图，路由会自动渲染登录页 -->
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

html,
body {
  width: 100%;
  height: 100%;
  background-color: #f5f7fa;
}

#app {
  width: 100%;
  height: 100%;
  font-family: "Helvetica Neue", Helvetica, "Microsoft YaHei", sans-serif;
}
</style>