<template>
  <div class="help-page flex flex-col items-center px-0 py-0 bg-gradient-to-b to-white h-[calc(100dvh-220px)] overflow-hidden">
    <div class="w-full max-w-6xl bg-white rounded-2xl shadow-xl p-8 flex flex-col gap-8 mt-8 mb-8">
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8 w-full">
        <!-- Trang chủ -->
        <div class="flex flex-col items-center bg-gradient-to-br from-blue-100 to-blue-50 rounded-xl shadow p-6 h-full min-h-[160px]">
          <span class="mb-3 text-blue-500">
            <svg class="w-10 h-10" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" d="M3 7h18M3 12h18M3 17h18"/></svg>
          </span>
          <p class="text-gray-600 text-center">Hiển thị tổng quan các công việc, trạng thái và các thông báo quan trọng.</p>
        </div>
        <!-- Thêm công việc -->
        <div class="flex flex-col items-center bg-gradient-to-br from-green-100 to-green-50 rounded-xl shadow p-6 h-full min-h-[160px]">
          <span class="mb-3 text-green-500">
            <svg class="w-10 h-10" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" d="M12 4v16m8-8H4"/></svg>
          </span>
          <p class="text-gray-600 text-center">Tạo mới một công việc bằng cách điền thông tin chi tiết và lưu lại.</p>
        </div>
        <!-- Danh sách công việc -->
        <div class="flex flex-col items-center bg-gradient-to-br from-yellow-100 to-yellow-50 rounded-xl shadow p-6 h-full min-h-[160px]">
          <span class="mb-3 text-yellow-500">
            <svg class="w-10 h-10" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" d="M5 13l4 4L19 7"/></svg>
          </span>
          <p class="text-gray-600 text-center">Xem, chỉnh sửa, xóa hoặc lọc công việc theo trạng thái (Đang làm, Đã hoàn thành, Đang chờ, v.v.).</p>
        </div>
        <!-- Thông báo -->
        <div class="flex flex-col items-center bg-gradient-to-br from-red-100 to-red-50 rounded-xl shadow p-6 h-full min-h-[160px]">
          <span class="mb-3 text-red-500">
            <svg class="w-10 h-10" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" d="M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6.002 6.002 0 00-4-5.659V4a2 2 0 10-4 0v1.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 11-6 0v-1m6 0H9"/></svg>
          </span>
          <p class="text-gray-600 text-center">Xem các thông báo hệ thống, cập nhật mới nhất và nhắc nhở quan trọng.</p>
        </div>
        <!-- Tài khoản -->
        <div class="flex flex-col items-center bg-gradient-to-br from-purple-100 to-purple-50 rounded-xl shadow p-6 h-full min-h-[160px]">
          <span class="mb-3 text-purple-500">
            <svg class="w-10 h-10" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" d="M5.121 17.804A13.937 13.937 0 0112 15c2.5 0 4.847.655 6.879 1.804M15 11a3 3 0 11-6 0 3 3 0 016 0z"/></svg>
          </span>
          <p class="text-gray-600 text-center">Quản lý thông tin cá nhân, đổi mật khẩu và đăng xuất khỏi hệ thống.</p>
        </div>
        <!-- Hướng dẫn thao tác nhanh -->
        <div class="flex flex-col items-center bg-gradient-to-br from-indigo-100 to-indigo-50 rounded-xl shadow p-6 h-full min-h-[160px]">
          <span class="mb-3 text-indigo-500">
            <svg class="w-10 h-10" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" d="M9 17v-2a4 4 0 014-4h2m4 0a9 9 0 11-18 0 9 9 0 0118 0z"/></svg>
          </span>
          <ul class="list-disc list-inside text-gray-600 space-y-1 text-left">
            <li><strong>Tạo công việc mới:</strong> Vào menu <span class="font-semibold text-blue-500">Thêm công việc</span>, nhập thông tin và nhấn <span class="font-semibold text-green-600">Lưu</span>.</li>
            <li><strong>Chỉnh sửa/Xóa công việc:</strong> Trong <span class="font-semibold text-blue-500">Danh sách công việc</span>, chọn biểu tượng <span class="font-semibold text-yellow-500">Chỉnh sửa</span> hoặc <span class="font-semibold text-red-500">Xóa</span> bên cạnh công việc.</li>
            <li><strong>Lọc công việc:</strong> Sử dụng bộ lọc phía trên danh sách để chọn trạng thái mong muốn.</li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>