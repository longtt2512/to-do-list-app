<template>
  <div v-if="src" class="fixed inset-0 bg-black/60 flex items-center justify-center" @click="$emit('close')">
    <img :src="src" class="max-w-full max-h-full rounded" />
  </div>
</template>
<script>
export default { props: ['src'] }
</script>
