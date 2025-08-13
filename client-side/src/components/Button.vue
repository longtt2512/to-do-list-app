<template>
  <button
    class="button"
    v-bind="$attrs"
  >
    <slot />
  </button>
</template>

<script>
export default {
  name: 'Button',
  inheritAttrs: false
}
</script>

<style scoped>
.button {
  height: 60px;
  background-color: #FF9090;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-weight: 500;
  transition: all 0.2s ease;
}

.button:hover {
  opacity: 0.8;
}

.button:active {
  transform: scale(0.95);
}
</style>