<template>
  <div class="feedback">
    <h2>用户意见反馈</h2>
    <p>该功能正在开发中，敬请期待！</p>
  </div>
</template>

<script>
export default {
  name: 'Feedback'
}
</script>

<style scoped>
.feedback {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0,0,0,0.1);
  padding: 40px;
}
.feedback h2 {
  color: #2c3e50;
  margin-bottom: 20px;
}
.feedback p {
  color: #666;
  font-size: 18px;
}
</style>