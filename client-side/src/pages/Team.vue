<template>
  <div>
    <h2 class="text-xl font-semibold mb-4">About our group</h2>
    <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
      <div v-for="m in members" :key="m.mssv" class="bg-white p-4 rounded shadow text-center">
        <img :src="m.photo" class="mx-auto w-28 h-28 rounded object-cover cursor-pointer" @click="open(m.photo)" />
        <h3 class="mt-3 font-semibold">{{ m.name }}</h3>
        <div class="text-sm text-slate-500">MSSV: {{ m.mssv }}</div>
        <div class="text-sm text-slate-500">DOB: {{ m.dob }}</div>
        <div class="text-sm text-slate-500">Class: {{ m.class }}</div>
        <div class="text-sm text-slate-500">Phone: {{ m.phone }}</div>
        <div class="text-sm text-slate-500">Email: {{ m.email }}</div>
        <div class="mt-2"><a :href="m.worklink" target="_blank" class="text-indigo-600">Company</a></div>
      </div>
    </div>
    <ModalImage :src="zoomSrc" @close="zoomSrc=null" />
  </div>
</template>

<script>
import ModalImage from '../components/ModalImage.vue'
export default {
  components:{ ModalImage },
  data(){ return { zoomSrc: null, members: JSON.parse(localStorage.getItem('members')||JSON.stringify([ { name:'Nguyen Van A', dob:'2001-01-10', mssv:'B20001', class:'IT1', phone:'0123456789', email:'a@mail.com', worklink:'https://example.com', photo:'/src/assets/avatar1.png' }, { name:'Tran Thi B', dob:'2001-02-20', mssv:'B20002', class:'IT1', phone:'0987654321', email:'b@mail.com', worklink:'https://example.com', photo:'/src/assets/avatar2.png' }, { name:'Le Van C', dob:'2001-03-15', mssv:'B20003', class:'IT1', phone:'0912345678', email:'c@mail.com', worklink:'https://example.com', photo:'/src/assets/avatar3.png' } ])) } },
  methods:{ open(src){ this.zoomSrc = src } }
}
</script>
