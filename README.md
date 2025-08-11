📌 Tên dự án
Ứng dụng Quản lý Công việc – Todo List Web App

🎯 Mục tiêu dự án
Xây dựng một ứng dụng web hỗ trợ người dùng quản lý công việc cá nhân đơn giản, hiệu quả và trực quan.
Ứng dụng cho phép:

Tạo, chỉnh sửa, xóa, tìm kiếm, phân loại công việc.

Lưu trữ dữ liệu để truy cập lại bất cứ lúc nào.

Thống kê tiến độ và hiệu suất làm việc.

🚀 Chức năng chính
1. Quản lý tài khoản
Đăng ký (Register)

Form: Họ tên, Email, Mật khẩu, Xác nhận mật khẩu, Số điện thoại.

Nút Tạo tài khoản mới, kiểm tra dữ liệu bằng JavaScript.

Đăng nhập (Login)

Email + mật khẩu.

Tùy chọn đăng nhập bằng Google / Facebook.

Đổi mật khẩu (Change Password)

Nhập mật khẩu cũ, mật khẩu mới, xác nhận mật khẩu mới.

Validate dữ liệu trước khi lưu.

Thông tin tài khoản (Account Info)

Xem & chỉnh sửa thông tin cá nhân (Tên, Email, SĐT, ảnh đại diện).

Lưu vào file bằng Java I/O.

2. Quản lý công việc
Bảng điều khiển (Dashboard)

Chào mừng người dùng.

Thống kê nhanh: số lượng công việc, tỉ lệ hoàn thành, biểu đồ tiến độ.

Danh sách công việc quan trọng hoặc gần hết hạn.

Danh sách công việc (My Task)

Hiển thị toàn bộ task.

Lọc/sắp xếp theo trạng thái, danh mục, ngày.

Thêm công việc (Add Task)

Form: Tên task, Mô tả, Ngày hết hạn, Danh mục, File đính kèm.

Kiểm tra dữ liệu nhập, lưu vào file (Java I/O).

Xem chi tiết công việc (View Task)

Hiển thị toàn bộ nội dung, trạng thái, file/ảnh đính kèm.

Vitals

Thông tin tổng hợp nhanh.

3. Quản lý danh mục công việc
Danh sách danh mục

Bảng danh mục + số lượng task trong từng danh mục.

Chức năng sửa / xóa danh mục.

Tạo danh mục mới

Form nhập tên danh mục.

Lưu dữ liệu vào file.

4. Giới thiệu nhóm (Theo yêu cầu bài tập)
Thông tin thành viên:

Họ tên

Ngày sinh

Mã sinh viên

Tên lớp

Số điện thoại

Email

Đơn vị công tác + link website

Ảnh cá nhân (click phóng to)

Hiển thị dạng card hoặc bảng.

Dữ liệu đọc từ file (Java I/O).

5. Chức năng giao diện
Sidebar Menu: Dashboard, My Task, Categories, Account, Change Password, About Group, Logout.

Header: Avatar người dùng, thông báo, cài đặt.

Popup Modal khi thêm/sửa task hoặc danh mục.

🛠️ Công nghệ sử dụng
Frontend: Vue 3, Vite, TailwindCSS

Lưu trữ tạm: localStorage (phiên bản demo)

Backend (dự kiến): Java với Java I/O để đọc/ghi dữ liệu

📂 Cấu trúc dự án
css
Copy
Edit
todo-list-app/
│── public/
│── src/
│   ├── assets/          # Ảnh, icon, style tĩnh
│   ├── components/      # Component tái sử dụng (Sidebar, Header, Modal…)
│   ├── pages/           # Các trang chính (Dashboard.vue, MyTask.vue…)
│   ├── App.vue
│   └── main.js
│── package.json
│── README.md