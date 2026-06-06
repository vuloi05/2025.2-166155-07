# BT7 — Tài liệu Kiểm thử Use Case (UC Testing)

**Sinh viên:** Nguyễn Đức Toàn — 20235846
**Use case:** UC006 — Xem chi tiết đơn hàng
**Tác nhân:** Bộ phận đặt hàng quốc tế

> Kiểm thử use case khác kiểm thử đơn vị: ở đây kiểm thử **toàn bộ luồng giao
> dịch** theo góc nhìn người dùng (preconditions → các bước → postconditions),
> dựa trên đặc tả UC006 (mục 2 SRS) và sơ đồ trình tự (mục 10 SRS).
> Giao diện: **M0 Trang chủ → M1 Dashboard → M2 Danh sách → M3/M4/M5**.

---

## 1. Xác định các scenario

Từ luồng chính và các luồng thay thế của UC006:

| Scenario | Mô tả | Luồng SRS |
|----------|-------|-----------|
| SC1 | Xem chi tiết đơn hàng thành công | Luồng chính (bước 1–9) |
| SC2 | Tìm kiếm không thấy đơn hàng | Luồng thay thế 5a → 6a |
| SC3 | Xem chi tiết đơn hàng đã bị hủy | Luồng thay thế 7a → 7b |
| SC4 | Tìm kiếm/lọc có kết quả rồi xem chi tiết | Luồng chính (bước 3–8) |
| SC5 | Quay lại danh sách sau khi xem chi tiết | Luồng chính (bước 9) |

⇒ Cần thiết kế **5 test case** (mỗi scenario một test case chính).

---

## 2. Các test case

### TC-UC-01 — Xem chi tiết đơn hàng thành công (SC1)

| | |
|---|---|
| **System** | Hệ thống đặt hàng nhập khẩu |
| **Subsystem** | UC006 — Xem chi tiết đơn hàng |
| **Pre-conditions** | Người dùng đã đăng nhập; hệ thống có 13 đơn hàng mock; tồn tại DH-2026-003 (Đã xử lý) |

| Bước | Hành động | Phản hồi mong đợi của hệ thống | Pass/Fail |
|------|-----------|-------------------------------|-----------|
| 1 | Khởi chạy ứng dụng | Hiển thị Trang chủ hệ thống (M0) | Pass |
| 2 | Click ô "Xem chi tiết đơn hàng" | Chuyển đến Dashboard (M1) | Pass |
| 3 | Click "Xem danh sách đơn hàng" | Hiển thị Màn hình danh sách (M2) | Pass |
| 4 | Click "Xem" tại dòng DH-2026-003 | Mở modal Chi tiết (M3) | Pass |
| 5 | Quan sát thông tin chung | Mã đơn, Trạng thái Đã xử lý, Site S02, PT Tàu, ngày tạo/gửi | Pass |
| 6 | Quan sát bảng mặt hàng | MH004 — Màn hình LCD 16x2, SL 50, Đơn vị Cái | Pass |
| **Post-conditions** | Chi tiết đơn hàng hợp lệ được hiển thị; không dữ liệu nào bị thay đổi | | Pass |

### TC-UC-02 — Tìm kiếm không thấy đơn hàng (SC2)

| | |
|---|---|
| **Pre-conditions** | Đang ở Màn hình danh sách đơn hàng (M2) |

| Bước | Hành động | Phản hồi mong đợi | Pass/Fail |
|------|-----------|-------------------|-----------|
| 1 | Nhập từ khóa không tồn tại, ví dụ `DH-9999` | Ô tìm kiếm nhận từ khóa | Pass |
| 2 | Nhấn "Tìm kiếm" | Hệ thống tra cứu, không có kết quả | Pass |
| 3 | Quan sát màn hình | Trạng thái rỗng "Không tìm thấy đơn hàng nào" (M4) | Pass |
| 4 | Nhấn "Làm mới" (6a) | Xóa bộ lọc, hiển thị lại toàn bộ danh sách | Pass |
| **Post-conditions** | Người dùng có thể tìm lại; không dữ liệu nào bị thay đổi | | Pass |

### TC-UC-03 — Xem chi tiết đơn hàng đã bị hủy (SC3)

| | |
|---|---|
| **Pre-conditions** | Trong danh sách có đơn DH-2026-008 trạng thái "Đã hủy" |

| Bước | Hành động | Phản hồi mong đợi | Pass/Fail |
|------|-----------|-------------------|-----------|
| 1 | Click "Xem" tại DH-2026-008 | Hệ thống phát hiện đơn đã hủy (7a) | Pass |
| 2 | Quan sát | Hộp thoại cảnh báo (M5): đơn đã bị hủy, không thể xem chi tiết | Pass |
| 3 | Nhấn "Xác nhận & Quay lại" (7b) | Đóng hộp thoại, quay về M2 | Pass |
| **Post-conditions** | Không mở M3; người dùng quay lại danh sách | | Pass |

### TC-UC-04 — Lọc theo trạng thái/phương tiện rồi xem chi tiết (SC4)

| | |
|---|---|
| **Pre-conditions** | Đang ở Màn hình danh sách đơn hàng (M2) |

| Bước | Hành động | Phản hồi mong đợi | Pass/Fail |
|------|-----------|-------------------|-----------|
| 1 | Chọn bộ lọc Trạng thái = "Đã xử lý" | Combo nhận giá trị lọc | Pass |
| 2 | Nhấn "Tìm kiếm" | Bảng chỉ còn DH-2026-003, DH-2026-011 | Pass |
| 3 | Click "Xem" một đơn trong kết quả | Mở modal chi tiết (M3) đúng đơn đã chọn | Pass |
| **Post-conditions** | Chi tiết đơn được hiển thị đúng | | Pass |

### TC-UC-05 — Quay lại danh sách sau khi xem chi tiết (SC5)

| | |
|---|---|
| **Pre-conditions** | Đang ở modal chi tiết (M3) của một đơn hợp lệ |

| Bước | Hành động | Phản hồi mong đợi | Pass/Fail |
|------|-----------|-------------------|-----------|
| 1 | Nhấn "← Quay lại danh sách" | Đóng modal chi tiết (M3) | Pass |
| 2 | Quan sát | Trở về Màn hình danh sách đơn hàng (M2) | Pass |
| **Post-conditions** | Người dùng ở lại M2; không dữ liệu nào bị thay đổi | | Pass |

---

## 3. Tổng hợp kết quả

| Test case | Scenario | Kết quả |
|-----------|----------|---------|
| UC_TC01 | Xem chi tiết thành công | ✅ Pass |
| UC_TC02 | Tìm kiếm không thấy | ✅ Pass |
| UC_TC03 | Đơn đã bị hủy | ✅ Pass |
| UC_TC04 | Lọc + xem chi tiết | ✅ Pass |
| UC_TC05 | Quay lại danh sách | ✅ Pass |

**5/5 test case use case đạt.** Các luồng của UC006 (chính + 5a + 7a/7b) đều hoạt
động đúng đặc tả trên ứng dụng `XemChiTietDonHang`.

> Dữ liệu kiểm thử dùng mock data trong `DonHangDAO` / `MatHangDonHangDAO`
> (13 đơn hàng; `DH-2026-003` — Đã xử lý; `DH-2026-008` — Đã hủy).

> Bảng tóm tắt cho SRS mục 15.4: `docs/15.4-KiemThu-UseCase-Scenario-Testing.xlsx`
