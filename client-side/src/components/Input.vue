<template>
  <div class="input-wrapper">
    <div v-if="icon" class="input-icon">
      <img :src="icon" :alt="placeholder" />
    </div>
    <input
      :type="type"
      :placeholder="placeholder"
      :value="modelValue"
      @input="$emit('update:modelValue', $event.target.value)"
      :class="['input', { 'with-icon': icon }]"
      v-bind="$attrs"
    />
  </div>
</template>

<script>
export default {
  name: 'Input',
  inheritAttrs: false,
  emits: ['update:modelValue'],
  props: {
    modelValue: {
      type: String,
      default: ''
    },
    type: {
      type: String,
      default: 'text'
    },
    placeholder: {
      type: String,
      default: ''
    },
    icon: {
      type: String,
      default: null
    }
  }
}
</script>

<style scoped>
.input-wrapper {
  position: relative;
  width: 100%;
  margin-bottom: 20px;
}

.input-icon {
  position: absolute;
  left: 16px;
  top: 50%;
  transform: translateY(-50%);
  z-index: 1;
  pointer-events: none;
}

.input-icon img {
  width: 20px;
  height: 20px;
  opacity: 1;
}

.input {
  width: 100%;
  padding: 16px;
  border: 1px solid #565454;
  border-radius: 5px;
  font-size: 16px;
  color: #000;
  box-sizing: border-box;
  transition: border-color 0.2s ease;
}

.input.with-icon {
  padding-left: 52px;
}

.input:focus {
  outline: none;
  border-color: #FF6767;
}

.input::placeholder {
  color: #565454;
  opacity: 0.7 ;
}
</style>