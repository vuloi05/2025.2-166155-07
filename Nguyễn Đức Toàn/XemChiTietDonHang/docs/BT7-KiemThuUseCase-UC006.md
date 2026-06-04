# BT7 — Tài liệu Kiểm thử Use Case (UC Testing)

**Sinh viên:** Nguyễn Đức Toàn — 20235846
**Use case:** UC006 — Xem chi tiết đơn hàng
**Tác nhân:** Bộ phận đặt hàng quốc tế

> Kiểm thử use case khác kiểm thử đơn vị: ở đây kiểm thử **toàn bộ luồng giao
> dịch** theo góc nhìn người dùng (preconditions → các bước → postconditions),
> dựa trên đặc tả UC006 (mục 2 SRS) và sơ đồ trình tự (mục 10 SRS).

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
| **Pre-conditions** | Người dùng đã đăng nhập; hệ thống có dữ liệu đơn hàng |

| Bước | Hành động | Phản hồi mong đợi của hệ thống | Pass/Fail |
|------|-----------|-------------------------------|-----------|
| 1 | Mở Màn hình danh sách đơn hàng | Hiển thị bảng đơn hàng + ô tìm kiếm + bộ lọc (Màn hình 2) | Pass |
| 2 | Click dòng đơn `DH-2025-001` (đang "Đã gửi") hoặc nút "Xem" | Mở Màn hình chi tiết (Màn hình 3) | Pass |
| 3 | Quan sát thông tin chung | Hiển thị: Mã đơn, Trạng thái, Mã/Tên Site, Phương tiện VT, Số ngày VC, Ngày tạo, Ngày gửi | Pass |
| 4 | Quan sát bảng mặt hàng | Hiển thị STT, Mã hàng, Tên hàng, SL đặt, Đơn vị, Phương tiện VT (3 dòng) | Pass |
| **Post-conditions** | Chi tiết đơn hàng hợp lệ được hiển thị; không dữ liệu nào bị thay đổi | | Pass |

### TC-UC-02 — Tìm kiếm không thấy đơn hàng (SC2)

| | |
|---|---|
| **Pre-conditions** | Đang ở Màn hình danh sách đơn hàng |

| Bước | Hành động | Phản hồi mong đợi | Pass/Fail |
|------|-----------|-------------------|-----------|
| 1 | Nhập từ khóa không tồn tại, ví dụ `DH-9999` | Ô tìm kiếm nhận từ khóa | Pass |
| 2 | Nhấn "Tìm kiếm" | Hệ thống tra cứu, không có kết quả | Pass |
| 3 | Quan sát màn hình | Hiển thị trạng thái rỗng "Không tìm thấy đơn hàng nào" + gợi ý (Màn hình 4) | Pass |
| 4 | Nhấn "Đặt lại" (6a) | Xóa bộ lọc, hiển thị lại toàn bộ danh sách | Pass |
| **Post-conditions** | Người dùng có thể tìm lại; không dữ liệu nào bị thay đổi | | Pass |

### TC-UC-03 — Xem chi tiết đơn hàng đã bị hủy (SC3)

| | |
|---|---|
| **Pre-conditions** | Trong danh sách có đơn `DH-2025-005` trạng thái "Đã hủy" |

| Bước | Hành động | Phản hồi mong đợi | Pass/Fail |
|------|-----------|-------------------|-----------|
| 1 | Click đơn `DH-2025-005` (Đã hủy) | Hệ thống phát hiện đơn đã hủy (7a) | Pass |
| 2 | Quan sát | Hiển thị hộp thoại cảnh báo "Đơn hàng DH-2025-005 đã bị hủy, không thể xem chi tiết" (Màn hình 5) | Pass |
| 3 | Nhấn "Xác nhận & Quay lại" (7b) | Đóng hộp thoại, quay về Màn hình danh sách | Pass |
| **Post-conditions** | Không mở màn hình chi tiết; người dùng quay lại để chọn đơn khác | | Pass |

### TC-UC-04 — Lọc theo trạng thái/phương tiện rồi xem chi tiết (SC4)

| | |
|---|---|
| **Pre-conditions** | Đang ở Màn hình danh sách đơn hàng |

| Bước | Hành động | Phản hồi mong đợi | Pass/Fail |
|------|-----------|-------------------|-----------|
| 1 | Chọn bộ lọc Trạng thái = "Đã gửi" | Combo nhận giá trị lọc | Pass |
| 2 | Nhấn "Tìm kiếm" | Bảng chỉ còn các đơn "Đã gửi" (`DH-2025-001`, `DH-2025-004`) | Pass |
| 3 | Click một đơn trong kết quả | Mở Màn hình chi tiết đúng đơn đã chọn | Pass |
| **Post-conditions** | Chi tiết đơn được hiển thị đúng | | Pass |

### TC-UC-05 — Quay lại danh sách sau khi xem chi tiết (SC5)

| | |
|---|---|
| **Pre-conditions** | Đang ở Màn hình chi tiết của một đơn hợp lệ |

| Bước | Hành động | Phản hồi mong đợi | Pass/Fail |
|------|-----------|-------------------|-----------|
| 1 | Nhấn "Quay lại danh sách" | Đóng Màn hình chi tiết | Pass |
| 2 | Quan sát | Trở về Màn hình danh sách đơn hàng (Màn hình 2) | Pass |
| **Post-conditions** | Người dùng ở lại Màn hình danh sách; không dữ liệu nào bị thay đổi | | Pass |

---

## 3. Tổng hợp kết quả

| Test case | Scenario | Kết quả |
|-----------|----------|---------|
| TC-UC-01 | Xem chi tiết thành công | ✅ Pass |
| TC-UC-02 | Tìm kiếm không thấy | ✅ Pass |
| TC-UC-03 | Đơn đã bị hủy | ✅ Pass |
| TC-UC-04 | Lọc + xem chi tiết | ✅ Pass |
| TC-UC-05 | Quay lại danh sách | ✅ Pass |

**5/5 test case use case đạt.** Các luồng của UC006 (chính + 5a + 7a/7b) đều hoạt
động đúng đặc tả trên ứng dụng `XemChiTietDonHang`.

> Dữ liệu kiểm thử dùng mock data trong `DonHangDAO` / `MatHangDonHangDAO`
> (5 đơn hàng, trong đó `DH-2025-005` ở trạng thái "Đã hủy" phục vụ TC-UC-03).
