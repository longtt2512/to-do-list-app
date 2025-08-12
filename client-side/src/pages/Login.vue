<template>
  <div class="background"></div>
</template>

<script>
export default {
  data() {
    return { email: "", password: "" };
  },
  methods: {
    login() {
      localStorage.setItem("user", JSON.stringify({ email: this.email }));
      this.$router.push("/");
    },
  },
};
</script>

<style lang="css" scoped>
.background {
  background-image: url("@/assets/bg1.png");
  background-color: #FF6767;
  width: 100vw;
  height: 100vh;
  background-size: cover;
  background-position: center;
}
</style>
