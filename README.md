# 📌 Ứng dụng Quản lý Công việc – Todo List Web App

## 🎯 Mục tiêu

Xây dựng ứng dụng web giúp người dùng quản lý công việc cá nhân **đơn giản – hiệu quả**, với khả năng:

- Tạo, chỉnh sửa, xóa, tìm kiếm và phân loại công việc.
- Lưu trữ dữ liệu để truy cập lại khi cần.

---

## ⚙️ Chức năng chính

### 1️⃣ Quản lý tài khoản

#### 📝 Đăng ký (Register)

- **Form**: Họ tên, Email, UserName, Mật khẩu, Xác nhận mật khẩu, Số điện thoại.
- **Nút**: Tạo tài khoản mới.
- **Xử lý**: Kiểm tra dữ liệu bằng **JavaScript**.

#### 🔑 Đăng nhập (Login)

- UserName + Mật khẩu.
- Hỗ trợ đăng nhập bằng **Google / Facebook**.

#### 🔄 Đổi mật khẩu (Change Password)

- Nhập **mật khẩu cũ**, **mật khẩu mới**, **xác nhận mật khẩu mới**.
- Kiểm tra dữ liệu hợp lệ.

#### 👤 Thông tin tài khoản (Account Info)

- Xem & chỉnh sửa thông tin cá nhân: **tên, email, số điện thoại, ảnh đại diện**.

---

### 2️⃣ Quản lý công việc

#### 📊 Dashboard

- Chào mừng người dùng.
- Thống kê nhanh: **số lượng công việc, tỉ lệ hoàn thành, biểu đồ tiến độ**.
- Hiển thị công việc **quan trọng** hoặc **gần hết hạn**.

#### 📋 Danh sách công việc (My Task)

- Hiển thị danh sách task.
- Lọc/sắp xếp theo **trạng thái / danh mục / ngày**.

#### ➕ Thêm công việc (Add Task)

- Form: **Tên task, Mô tả, Ngày hết hạn, Danh mục, File đính kèm**.
- Kiểm tra dữ liệu nhập.

#### 🔍 Xem chi tiết công việc (View Task)

- Toàn bộ nội dung, file/ảnh đính kèm, trạng thái.

---

### 3️⃣ Quản lý danh mục công việc

#### 📂 Danh sách danh mục (Task Categories)

- Hiển thị bảng danh mục hiện có, số task trong mỗi danh mục.
- Nút **Sửa / Xóa** danh mục.

#### 🆕 Tạo danh mục mới (Create Categories)

- Form nhập tên danh mục.

---

### 4️⃣ Giới thiệu nhóm

- Họ tên, Ngày sinh, Mã sinh viên, Tên lớp, Số điện thoại, Email.
- Đơn vị công tác hiện tại + link website.
- Ảnh cá nhân (click để phóng to).

---

### 5️⃣ Chức năng giao diện

- **Sidebar Menu**: Dashboard, My Task, Categories, Account, Change Password, About Group, Logout.
- **Header**: Avatar người dùng, icon thông báo, icon cài đặt.
- **Popup Modal**: Khi thêm/sửa task hoặc danh mục.

---

## 🌐 Web Demo Online

- **Link demo**: [http://nhom4.choi.one:8082/](http://nhom4.choi.one:8082/)
- Cho phép trải nghiệm đầy đủ các chức năng: đăng ký, đăng nhập, quản lý công việc, danh mục và thông tin nhóm.

---

## 🛠 Công nghệ sử dụng

- **Frontend**: Vue 3 + Vite + TailwindCSS
- **Hệ thống lưu trữ**: MinIO
- **Backend**: SpringBoot

---

## 📦 Cấu trúc thư mục dự kiến

```plaintext
todo-app/
│── src/
│   ├── components/   # Component tái sử dụng
│   ├── pages/        # Các trang (Dashboard, MyTask, Categories…)
│   ├── assets/       # Hình ảnh, icon, CSS riêng
│   ├── App.vue
│   ├── main.js
│── public/           # File tĩnh
│── package.json
│── README.md
```

