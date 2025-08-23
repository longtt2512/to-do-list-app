<template>
  <div class="h-[calc(100dvh-220px)] flex gap-6 px-16 overflow-hidden">
    <div class="h-full flex-1 flex flex-col w-[55%] border border-[#A1A3ABA1] rounded-xl overflow-hidden p-6 pr-2">
      <div class="flex items-center space-x-3 mb-3">
        <div class="w-10 h-10 bg-blue-100 rounded-lg flex items-center justify-center">
          <img src="../assets/icons/pending.svg" alt="pending" class="w-4 h-4">
        </div>
        <div>
          <h2 class="text-xl font-semibold text-[#FF6767]">My Tasks</h2>
          <div class="text-sm">
            <span class="text-[#000] mr-2">{{ todayDate }}</span>
            <span class="text-gray-500">.Today</span>
          </div>
        </div>
      </div>

      <!-- Task Cards -->
      <div class="space-y-4 flex-1 overflow-y-auto min-h-0 pr-4 py-2">
        <TaskCard v-for="task in tasks" :key="task.id" :task="task" @click="view(task.id)" />
      </div>
    </div>

    <!-- Right Column -->
    <div
      class="h-full flex-1 flex flex-col w-[45%] border border-[#A1A3ABA1] rounded-xl overflow-hidden p-6 pr-2">
      <div class="flex gap-4 mb-3">
        <img :src="task.image || '/src/assets/avatar.png'" :alt="task.title" class="w-20 h-20 rounded-lg object-cover border border-gray-200" />
        <div class="flex flex-col justify-center">
          <h3 class="font-semibold text-[16px] mb-1">{{ task.title }}</h3>
          <div class="text-[13px] mb-1">
            <span class="font-medium">Priority:</span>
            <span class="text-[#FF6767] font-semibold ml-1">{{ task.priority }}</span>
          </div>
          <div class="text-[13px] mb-1">
            <span class="font-medium">Status:</span>
            <span class="text-[#FF6767] font-semibold ml-1">{{ task.status }}</span>
          </div>
          <div class="text-[11px] text-gray-400">Created on: {{ task.createdAt }}</div>
        </div>
      </div>
      <div class="text-[13px] leading-relaxed text-[#222] flex-1">
        <div class="mb-2">
          <span class="font-semibold">Task Title:</span> {{ task.title }}
        </div>
        <div class="mb-2">
          <span class="font-semibold">Objective:</span>
          To submit required documents for something important
        </div>
        <div class="mb-2">
          <span class="font-semibold">Task Description:</span>
          Review the list of documents required for submission and ensure all necessary documents are ready.
          Organize the documents accordingly and scan them if physical copies need to be submitted digitally.
          Rename the scanned files appropriately for easy identification and verify the accepted file formats.
          Upload the documents securely to the designated platform, double-check for accuracy, and obtain confirmation
          of successful submission.
          Follow up if necessary to ensure proper processing.
        </div>
        <div class="mb-2">
          <span class="font-semibold">Additional Notes:</span>
          <ul class="list-disc pl-5 mt-1 space-y-1">
            <li>Ensure that the documents are authentic and up-to-date.</li>
            <li>Maintain confidentiality and security of sensitive information during the submission process.</li>
            <li>If there are specific guidelines or deadlines for submission, adhere to them diligently.</li>
          </ul>
        </div>
        <div class="mb-2">
          <span class="font-semibold">Deadline for Submission:</span> End of Day
        </div>
      </div>
      <div class="flex justify-end gap-2 mt-4">
        <button
          class="w-9 h-9 flex items-center justify-center bg-[#FF6767] rounded-lg hover:bg-[#e05555] transition-colors">
          <svg xmlns="http://www.w3.org/2000/svg" class="w-5 h-5 text-white" fill="none" viewBox="0 0 24 24"
            stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M15.232 5.232l3.536 3.536M9 13l6-6m2 2l-6 6m2 2H7a2 2 0 01-2-2V7a2 2 0 012-2h7a2 2 0 012 2v7a2 2 0 01-2 2z" />
          </svg>
        </button>
        <button
          class="w-9 h-9 flex items-center justify-center bg-[#FF6767] rounded-lg hover:bg-[#e05555] transition-colors">
          <svg xmlns="http://www.w3.org/2000/svg" class="w-5 h-5 text-white" fill="none" viewBox="0 0 24 24"
            stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M15.232 5.232l3.536 3.536M9 13l6-6m2 2l-6 6m2 2H7a2 2 0 01-2-2V7a2 2 0 012-2h7a2 2 0 012 2v7a2 2 0 01-2 2z" />
          </svg>
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import TaskCard from '../components/TaskCard.vue'
import TaskCardCompleted from '../components/TaskCardCompleted.vue'
import { taskService } from '../services/task-service'

export default {
  components: { TaskCard, TaskCardCompleted },
  data() {
    return {
      tasks: [],
      task: null,
      todayDate: (() => {
        const date = new Date();
        const day = date.getDate();
        const month = date.toLocaleString('en-US', { month: 'long' });
        return `${day} ${month}`;
      })(),
    }
  },
  created() {
    taskService.getAll().then(res => {
      this.tasks = res.data
      if(this.tasks.length > 0) {
        this.task = this.tasks[0]
      }
    })
   },
  computed: {
  },
  methods: {
    view(id) {
      this.task = this.tasks.find(t => t.id === id)
    }
  }
}
</script>
