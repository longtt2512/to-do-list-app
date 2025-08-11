# 📌 Ứng dụng Quản lý Công việc – Todo List Web App

## 🎯 Mục tiêu
Dự án nhằm xây dựng một ứng dụng web giúp người dùng quản lý công việc cá nhân một cách **đơn giản và hiệu quả**.  
Ứng dụng cho phép:
- Tạo, chỉnh sửa, xóa, tìm kiếm và phân loại công việc.
- Lưu trữ dữ liệu để người dùng có thể truy cập lại khi cần.

---

## ⚙️ Chức năng chính

### 1. Quản lý tài khoản
#### 📝 Đăng ký (Register)
- Form: **Họ tên, Email, Mật khẩu, Xác nhận mật khẩu, Số điện thoại**.
- Nút **Tạo tài khoản mới**.
- Kiểm tra dữ liệu bằng **JavaScript**.

#### 🔑 Đăng nhập (Login)
- Email + mật khẩu.
- Hỗ trợ đăng nhập bằng **Google** / **Facebook**.

#### 🔄 Đổi mật khẩu (Change Password)
- Nhập **mật khẩu cũ**, **mật khẩu mới**, **xác nhận mật khẩu mới**.
- Kiểm tra dữ liệu hợp lệ.

#### 👤 Thông tin tài khoản (Account Info)
- Xem & chỉnh sửa thông tin cá nhân (**tên, email, số điện thoại, ảnh đại diện**).
- Lưu thông tin vào file bằng **Java I/O**.

---

### 2. Quản lý công việc
#### 📊 Bảng điều khiển (Dashboard)
- Chào mừng người dùng.
- Thống kê nhanh: **số lượng công việc, tỉ lệ hoàn thành, biểu đồ tiến độ**.
- Danh sách công việc **quan trọng** hoặc **gần hết hạn**.

#### 📋 Danh sách công việc (My Task)
- Hiển thị danh sách task.
- Lọc hoặc sắp xếp theo **trạng thái / danh mục / ngày**.

#### ➕ Thêm công việc (Add Task)
- Form: **Tên task, Mô tả, Ngày hết hạn, Danh mục, File đính kèm**.
- Kiểm tra dữ liệu nhập.
- Lưu vào file bằng **Java I/O**.

#### 🔍 Xem chi tiết công việc (View Task)
- Toàn bộ nội dung, file/ảnh đính kèm, trạng thái.

#### 📌 Vitals
- Hiển thị thông tin quan trọng hoặc tổng hợp nhanh.

---

### 3. Quản lý danh mục công việc
#### 📂 Danh sách danh mục (Task Categories)
- Hiển thị bảng danh mục hiện có, số task trong mỗi danh mục.
- Nút **Sửa / Xóa** danh mục.

#### 🆕 Tạo danh mục mới (Create Categories)
- Form nhập tên danh mục.
- Lưu vào file dữ liệu.

---

### 4. Giới thiệu nhóm
Trang giới thiệu thành viên gồm:
- Họ tên
- Ngày sinh
- Mã sinh viên
- Tên lớp
- Số điện thoại
- Email
- Đơn vị công tác hiện tại + link đến website
- Ảnh cá nhân (click để phóng to)  
📂 **Dữ liệu đọc từ file (Java I/O)** để luyện phần backend.

---

### 5. Chức năng giao diện
- **Sidebar Menu**: Dashboard, My Task, Categories, Account, Change Password, About Group, Logout.
- **Header**: Avatar người dùng, icon thông báo, icon cài đặt.
- **Popup Modal** khi thêm/sửa task hoặc danh mục.

---

## 🛠 Công nghệ sử dụng
- **Frontend**: Vue 3, Vite, TailwindCSS.
- **Lưu trữ tạm thời**: `localStorage` (phiên bản demo).
- **Backend (dự kiến)**: Java với Java I/O để đọc/ghi dữ liệu.

---

## 📦 Cấu trúc thư mục dự kiến
```plaintext
todo-app/
│── src/
│   ├── components/
│   ├── pages/
│   ├── assets/
│   ├── App.vue
│   ├── main.js
│── public/
│── package.json
│── README.md
