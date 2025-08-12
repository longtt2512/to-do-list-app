<template>
  <div class="h-full bg-gray-50 p-6">
    <!-- Header Section -->
    <div class="flex justify-between items-center mb-8">
      <div class="flex items-center">
        <h1 class="text-[36px] font-semibold mr-3">Welcome back, Sundar</h1>
        <img src="../assets/icons/hand-wave.svg" alt="hand-wave" class="w-10 h-10">
      </div>
      
      <div class="flex items-center space-x-4">
        <!-- User Avatars -->
        <div class="flex space-x-1">
          <div v-for="(member, index) in members.slice(0, 4)" :key="index" 
               class="w-10 h-10 rounded-md overflow-hidden">
            <img :src="member.avatar" :alt="member.name" class="w-full h-full object-cover">
          </div>
          <div v-if="members.length > 4" 
               class="relative w-10 h-10 rounded-md overflow-hidden flex items-center justify-center text-sm font-medium text-gray-600">
               <img src="https://i2-vnexpress.vnecdn.net/2019/07/30/anh-thien-nhien-dep-thang-7-1564483719.jpg?w=1200&h=0&q=100&dpr=1&fit=crop&s=Nl3znv-VRtPyhJYhLwwRfA" alt="plus" class="w-full h-full object-cover blur-[1px]">
            <span class="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 rounded-full text-sm font-medium text-white">
              +{{ members.length - 4 }}
            </span>
          </div>
        </div>
        
        <!-- Invite Button -->
        <button class="bg-white border border-[#FF6767] text-[#FF6767] px-4 py-2 rounded-lg flex items-center space-x-2 transition-colors">
          <img src="../assets/icons/invite-user.svg" alt="invite-user" class="w-4 h-4">
          <span>Invite</span>
        </button>
      </div>
    </div>

    <!-- Main Content -->
    <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
      <!-- Left Column - To-Do Section -->
      <div class="lg:col-span-2">
        <div class="bg-white rounded-xl border border-gray-200 p-6">
          <div class="flex justify-between items-center mb-6">
            <div class="flex items-center space-x-3">
              <div class="w-10 h-10 bg-blue-100 rounded-lg flex items-center justify-center">
                <img src="../assets/icons/pending.svg" alt="pending" class="w-4 h-4">
              </div>
              <div>
                <h2 class="text-xl font-semibold text-gray-900">To-Do</h2>
                <p class="text-sm text-gray-500">20 June • Today</p>
              </div>
            </div>
            <button class="hover:bg-[#b4b4b475] hover:text-white px-4 py-2 rounded-lg flex items-center space-x-2 transition-colors">
              <svg class="w-3 h-3" fill="none" stroke="#F24E1E" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4"></path>
              </svg>
              <span class="text-[12px] text-[#A1A3AB]">Add task</span>
            </button>
          </div>

          <!-- Task Cards -->
          <div class="space-y-4">
            <TaskCard 
              v-for="task in tasks" 
              :key="task.id" 
              :task="task"
              @view="view"
              @edit="edit"
            />
          </div>
        </div>
      </div>

      <!-- Right Column -->
      <div class="space-y-6">
        <!-- Task Status Section -->
        <div class="bg-white rounded-xl border border-gray-200 p-6">
          <div class="flex items-center space-x-3 mb-6">
            <div class="w-10 h-10 bg-green-100 rounded-lg flex items-center justify-center">
              <svg class="w-5 h-5 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"></path>
              </svg>
            </div>
            <h2 class="text-xl font-semibold text-gray-900">Task Status</h2>
          </div>

          <!-- Progress Indicators -->
          <div class="grid grid-cols-3 gap-4 mb-6">
            <div class="text-center">
              <div class="relative w-16 h-16 mx-auto mb-2">
                <svg class="w-16 h-16 transform -rotate-90" viewBox="0 0 36 36">
                  <path class="text-gray-200" stroke="currentColor" stroke-width="2" fill="none" d="M18 2.0845 a 15.9155 15.9155 0 0 1 0 31.831 a 15.9155 15.9155 0 0 1 0 -31.831"/>
                  <path class="text-green-500" stroke="currentColor" stroke-width="2" fill="none" stroke-dasharray="84, 100" d="M18 2.0845 a 15.9155 15.9155 0 0 1 0 31.831 a 15.9155 15.9155 0 0 1 0 -31.831"/>
                </svg>
                <div class="absolute inset-0 flex items-center justify-center">
                  <span class="text-sm font-semibold text-green-600">84%</span>
                </div>
              </div>
              <p class="text-sm text-green-600 font-medium">Completed</p>
            </div>

            <div class="text-center">
              <div class="relative w-16 h-16 mx-auto mb-2">
                <svg class="w-16 h-16 transform -rotate-90" viewBox="0 0 36 36">
                  <path class="text-gray-200" stroke="currentColor" stroke-width="2" fill="none" d="M18 2.0845 a 15.9155 15.9155 0 0 1 0 31.831 a 15.9155 15.9155 0 0 1 0 -31.831"/>
                  <path class="text-blue-500" stroke="currentColor" stroke-width="2" fill="none" stroke-dasharray="46, 100" d="M18 2.0845 a 15.9155 15.9155 0 0 1 0 31.831 a 15.9155 15.9155 0 0 1 0 -31.831"/>
                </svg>
                <div class="absolute inset-0 flex items-center justify-center">
                  <span class="text-sm font-semibold text-blue-600">46%</span>
                </div>
              </div>
              <p class="text-sm text-blue-600 font-medium">In Progress</p>
            </div>

            <div class="text-center">
              <div class="relative w-16 h-16 mx-auto mb-2">
                <svg class="w-16 h-16 transform -rotate-90" viewBox="0 0 36 36">
                  <path class="text-gray-200" stroke="currentColor" stroke-width="2" fill="none" d="M18 2.0845 a 15.9155 15.9155 0 0 1 0 31.831 a 15.9155 15.9155 0 0 1 0 -31.831"/>
                  <path class="text-red-500" stroke="currentColor" stroke-width="2" fill="none" stroke-dasharray="13, 100" d="M18 2.0845 a 15.9155 15.9155 0 0 1 0 31.831 a 15.9155 15.9155 0 0 1 0 -31.831"/>
                </svg>
                <div class="absolute inset-0 flex items-center justify-center">
                  <span class="text-sm font-semibold text-red-600">13%</span>
                </div>
              </div>
              <p class="text-sm text-red-600 font-medium">Not Started</p>
            </div>
          </div>

          <!-- Legend -->
          <div class="flex justify-center space-x-6 text-sm">
            <div class="flex items-center space-x-2">
              <div class="w-2 h-2 bg-green-500 rounded-full"></div>
              <span class="text-gray-600">Completed</span>
            </div>
            <div class="flex items-center space-x-2">
              <div class="w-2 h-2 bg-blue-500 rounded-full"></div>
              <span class="text-gray-600">In Progress</span>
            </div>
            <div class="flex items-center space-x-2">
              <div class="w-2 h-2 bg-red-500 rounded-full"></div>
              <span class="text-gray-600">Not Started</span>
            </div>
          </div>
        </div>

        <!-- Completed Tasks Section -->
        <div class="bg-white rounded-xl border border-gray-200 p-6">
          <div class="flex items-center space-x-3 mb-6">
            <div class="w-10 h-10 bg-green-100 rounded-lg flex items-center justify-center">
              <svg class="w-5 h-5 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"></path>
              </svg>
            </div>
            <h2 class="text-xl font-semibold text-gray-900">Completed Task</h2>
          </div>

          <!-- Completed Task Cards -->
          <div class="space-y-4">
            <TaskCardCompleted 
              v-for="task in completedTasks" 
              :key="task.id" 
              :task="task"
            />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import TaskCard from '../components/TaskCard.vue'
import TaskCardCompleted from '../components/TaskCardCompleted.vue'
export default {
  components: { TaskCard, TaskCardCompleted },
  data(){ return { 
    members: [
      {
        name: 'Nguyen Van A',
        avatar: 'https://i2-vnexpress.vnecdn.net/2019/07/30/anh-thien-nhien-dep-thang-7-1564483719.jpg?w=1200&h=0&q=100&dpr=1&fit=crop&s=Nl3znv-VRtPyhJYhLwwRfA',
        email: 'nguyenvan@example.com',
        phone: '0123456789',
        address: '123 Nguyen Van Linh, Q9, TP.HCM',
        dob: '1990-01-01',
        gender: 'Male',
      },
      {
        name: 'Nguyen Van A',
        avatar: 'https://i2-vnexpress.vnecdn.net/2019/07/30/anh-thien-nhien-dep-thang-7-1564483719.jpg?w=1200&h=0&q=100&dpr=1&fit=crop&s=Nl3znv-VRtPyhJYhLwwRfA',
        email: 'nguyenvan@example.com',
        phone: '0123456789',
        address: '123 Nguyen Van Linh, Q9, TP.HCM',
        dob: '1990-01-01',
        gender: 'Male',
      },
      {
        name: 'Nguyen Van A',
        avatar: 'https://i2-vnexpress.vnecdn.net/2019/07/30/anh-thien-nhien-dep-thang-7-1564483719.jpg?w=1200&h=0&q=100&dpr=1&fit=crop&s=Nl3znv-VRtPyhJYhLwwRfA',
        email: 'nguyenvan@example.com',
        phone: '0123456789',
        address: '123 Nguyen Van Linh, Q9, TP.HCM',
        dob: '1990-01-01',
        gender: 'Male',
      },
      {
        name: 'Nguyen Van A',
        avatar: 'https://i2-vnexpress.vnecdn.net/2019/07/30/anh-thien-nhien-dep-thang-7-1564483719.jpg?w=1200&h=0&q=100&dpr=1&fit=crop&s=Nl3znv-VRtPyhJYhLwwRfA',
        email: 'nguyenvan@example.com',
        phone: '0123456789',
        address: '123 Nguyen Van Linh, Q9, TP.HCM',
        dob: '1990-01-01',
        gender: 'Male',
      },
      {
        name: 'Nguyen Van A',
        avatar: 'https://i2-vnexpress.vnecdn.net/2019/07/30/anh-thien-nhien-dep-thang-7-1564483719.jpg?w=1200&h=0&q=100&dpr=1&fit=crop&s=Nl3znv-VRtPyhJYhLwwRfA',
        email: 'nguyenvan@example.com',
        phone: '0123456789',
        address: '123 Nguyen Van Linh, Q9, TP.HCM',
        dob: '1990-01-01',
        gender: 'Male',
      },
      {
        name: 'Nguyen Van A',
        avatar: 'https://i2-vnexpress.vnecdn.net/2019/07/30/anh-thien-nhien-dep-thang-7-1564483719.jpg?w=1200&h=0&q=100&dpr=1&fit=crop&s=Nl3znv-VRtPyhJYhLwwRfA',
        email: 'nguyenvan@example.com',
        phone: '0123456789',
        address: '123 Nguyen Van Linh, Q9, TP.HCM',
        dob: '1990-01-01',
        gender: 'Male',
      }
    ],
    tasks: [
      {
        id: 1,
        title: 'Attend Nischal’s Birthday Party',
        description: 'Buy gifts on the way and pick up cake from the bakery. (6 PM | Fresh Elements) Buy gifts on the way and pick up cake from the bakery',
        image: 'https://i2-vnexpress.vnecdn.net/2019/07/30/anh-thien-nhien-dep-thang-7-1564483719.jpg?w=1200&h=0&q=100&dpr=1&fit=crop&s=Nl3znv-VRtPyhJYhLwwRfA',
        status: 'completed',
        createdAt: '2021-01-01',
        dueDate: '2021-01-01',
      },
      
      {
        id: 2,
        title: 'Landing Page Design for TravelDays',
        description: 'Get the work done by EOD and discuss with client before leaving. (4 PM | Meeting Room)',
        image: 'https://i2-vnexpress.vnecdn.net/2019/07/30/anh-thien-nhien-dep-thang-7-1564483719.jpg?w=1200&h=0&q=100&dpr=1&fit=crop&s=Nl3znv-VRtPyhJYhLwwRfA',
        status: 'completed',
        createdAt: '2021-01-01',
        dueDate: '2021-01-01',
      },
      
      {
        id: 3,
        title: 'Presentation on Final Product',
        description: 'Make sure everything is functioning and all the necessities are properly met. Prepare the team and get the documents ready for...',
        image: 'https://i2-vnexpress.vnecdn.net/2019/07/30/anh-thien-nhien-dep-thang-7-1564483719.jpg?w=1200&h=0&q=100&dpr=1&fit=crop&s=Nl3znv-VRtPyhJYhLwwRfA',
        status: 'completed',
        createdAt: '2021-01-01',
        dueDate: '2021-01-01',
      } 
    ],
    completedTasks: [
      {
        id: 1,
        title: 'Attend Nischal’s Birthday Party',
        description: 'Buy gifts on the way and pick up cake from the bakery. (6 PM | Fresh Elements) Buy gifts on the way and pick up cake from the bakery',
        image: 'https://i2-vnexpress.vnecdn.net/2019/07/30/anh-thien-nhien-dep-thang-7-1564483719.jpg?w=1200&h=0&q=100&dpr=1&fit=crop&s=Nl3znv-VRtPyhJYhLwwRfA',
        status: 'completed',
        createdAt: '2021-01-01',
        dueDate: '2021-01-01',
      }
    ]
 } },
  created(){ },
  computed:{
    completedCount(){ return this.tasks.filter(t=>t.done).length },
    activeCount(){ return this.tasks.length - this.completedCount },
    upcoming(){ return this.tasks.filter(t=>t.dueDate).sort((a,b)=> new Date(a.dueDate)-new Date(b.dueDate)) },
    recent(){ return this.tasks.slice().reverse().slice(0,5) },
    todoTasks() {
      return this.tasks.filter(t => !t.done).slice(0, 3);
    },
  },
  methods:{ view(id){ this.$router.push(`/tasks/${id}`) }, edit(id){ this.$router.push(`/tasks/${id}?edit=1`) }
  }
}
</script>
