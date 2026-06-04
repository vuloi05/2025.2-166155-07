# BT7 — Tài liệu Kiểm thử đơn vị (Unit Testing)

**Sinh viên:** Nguyễn Đức Toàn — 20235846
**Use case:** UC006 — Xem chi tiết đơn hàng
**Project:** `XemChiTietDonHang` (Eclipse)

---

## 1. Mô tả module được chọn kiểm thử

| Thuộc tính | Giá trị |
|------------|---------|
| Lớp | `businesslogic.DonHangService` |
| Phương thức | `ChiTietDonHangDTO layChiTiet(String maDonHang)` |
| Vai trò trong UC006 | Lấy chi tiết một đơn hàng (luồng chính bước 7–8 và luồng thay thế 7a) |
| Lớp kiểm thử tự động (full name) | **`test.DonHangServiceTest`** |

### Đặc tả (oracle) của phương thức

`layChiTiet(maDonHang)` nhận mã đơn hàng và trả về DTO chi tiết, theo 4 quy tắc:

| # | Điều kiện đầu vào | Kết quả mong đợi |
|---|------------------|------------------|
| 1 | `maDonHang` null hoặc rỗng (sau `trim`) | Ném `IllegalArgumentException` |
| 2 | Không tìm thấy đơn theo mã | Trả về `null` |
| 3 | Đơn tồn tại nhưng đã bị hủy (`laDaHuy() == true`) | Ném `DonHangDaHuyException` (luồng 7a) |
| 4 | Đơn tồn tại và hợp lệ | Trả về `ChiTietDonHangDTO` đầy đủ |

### Mã nguồn module (tóm tắt logic)

```java
public ChiTietDonHangDTO layChiTiet(String maDonHang) {
    if (maDonHang == null || maDonHang.trim().isEmpty()) {     // B1
        throw new IllegalArgumentException("Ma don hang khong duoc rong");
    }
    DonHang donHang = donHangDAO.findByCode(maDonHang);
    if (donHang == null) {                                     // B2
        return null;
    }
    if (donHang.laDaHuy()) {                                   // B3
        throw new DonHangDaHuyException(maDonHang);
    }
    List<MatHangDonHang> dsMatHang = matHangDAO.findByOrderCode(maDonHang);
    Site site = donHangDAO.findSiteByCode(donHang.getMaSite());
    return ChiTietDonHangDTO.createFrom(donHang, dsMatHang, site);
}
```

> **Lý do chọn module này:** đây là phương thức nghiệp vụ cốt lõi của UC006, có
> đủ 3 điểm quyết định (B1, B2, B3) nên minh họa rõ cả kiểm thử hộp đen lẫn hộp
> trắng C1; đồng thời nhờ DIP (phụ thuộc `IDonHangDAO`) có thể tiêm DAO giả để
> kiểm thử độc lập, không cần CSDL thật.

---

## 2. Kiểm thử HỘP ĐEN (Black-box) — làm TRƯỚC

Không quan tâm cấu trúc bên trong, chỉ dựa trên **đặc tả** ở mục 1. Áp dụng
hai kỹ thuật:

### 2.1. Equivalence Partitioning (Phân vùng tương đương)

Chia miền giá trị của `maDonHang` thành các lớp tương đương:

| Lớp tương đương | Đại diện | Kết quả mong đợi |
|-----------------|----------|------------------|
| EP1 — Hợp lệ, đơn còn hiệu lực | `"DH-VALID"` | DTO đầy đủ |
| EP2 — Mã không tồn tại | `"DH-KHONG-CO"` | `null` |
| EP3 — Mã tồn tại nhưng đã hủy | `"DH-HUY"` | `DonHangDaHuyException` |
| EP4 — Không hợp lệ (null) | `null` | `IllegalArgumentException` |

Mỗi lớp chỉ cần một đại diện vì mọi giá trị trong cùng lớp cho cùng kết quả.

### 2.2. Boundary-Value Analysis (Phân tích giá trị biên)

Biên của "chuỗi rỗng" là điểm dễ sai (`isEmpty()` vs `trim().isEmpty()`):

| Biên | Đại diện | Kết quả mong đợi |
|------|----------|------------------|
| BVA1 — Độ dài 0 (rỗng) | `""` | `IllegalArgumentException` |
| BVA2 — Chỉ chứa khoảng trắng | `"   "` | `IllegalArgumentException` (kiểm tra dùng `trim`) |

### 2.3. Bảng test case hộp đen

| TC | Tên test | Input | Expected | Kỹ thuật |
|----|----------|-------|----------|----------|
| TC01 | `testLayChiTiet_donHopLe_traVeDTODayDu` | `"DH-VALID"` | DTO: mã đúng, 2 mặt hàng, site khác null, 30 ngày VC | EP1 |
| TC02 | `testLayChiTiet_donKhongTonTai_traVeNull` | `"DH-KHONG-CO"` | `null` | EP2 |
| TC03 | `testLayChiTiet_donDaHuy_nemDonHangDaHuyException` | `"DH-HUY"` | `DonHangDaHuyException` | EP3 |
| TC04 | `testLayChiTiet_maNull_nemIllegalArgumentException` | `null` | `IllegalArgumentException` | EP4 |
| TC05 | `testLayChiTiet_maRong_nemIllegalArgumentException` | `""` | `IllegalArgumentException` | BVA1 |
| TC06 | `testLayChiTiet_maToanKhoangTrang_nemIllegalArgumentException` | `"   "` | `IllegalArgumentException` | BVA2 |
| TC07 | `testLayChiTiet_donDaHuy_exceptionChuaMaDon` | `"DH-HUY"` | exception chứa mã `"DH-HUY"` | EP3 (kiểm chứng nội dung) |

---

## 3. Kiểm thử HỘP TRẮNG (White-box, độ đo C1) — làm SAU

Dựa trên **cấu trúc code**. Mục tiêu: **C1 = Branch Coverage = 100%** (mọi nhánh
True/False của mọi điểm quyết định đều được đi qua).

### 3.1. Xác định các nhánh

| Nhánh | Điều kiện |
|-------|-----------|
| B1 | `maDonHang == null || maDonHang.trim().isEmpty()` |
| B2 | `donHang == null` |
| B3 | `donHang.laDaHuy()` |

### 3.2. Bảng phủ nhánh C1

(T = rẽ nhánh đúng, F = rẽ nhánh sai)

| TC trắng | B1 | B2 | B3 | Đường đi | Kết quả |
|----------|----|----|----|----------|---------|
| WB01 | **T** | – | – | input rỗng | `IllegalArgumentException` |
| WB02 | F | **T** | – | không tìm thấy | `null` |
| WB03 | F | F | **T** | đã hủy | `DonHangDaHuyException` |
| WB04 | F | F | **F** | hợp lệ | DTO |

**Kiểm tra độ phủ:** mỗi nhánh B1, B2, B3 đều xuất hiện cả giá trị **T** và **F**
trong bảng trên ⇒ **C1 = 100%**.

| Nhánh | Chiều TRUE | Chiều FALSE |
|-------|-----------|-------------|
| B1 | WB01 | WB02, WB03, WB04 |
| B2 | WB02 | WB03, WB04 |
| B3 | WB03 | WB04 |

### 3.3. Bảng test case hộp trắng

| TC | Tên test | Input | Expected |
|----|----------|-------|----------|
| WB01 | `testWB01_nhanhB1True_maRong` | `""` | `IllegalArgumentException` |
| WB02 | `testWB02_nhanhB2True_khongTimThay` | `"DH-KHONG-CO"` | `null` |
| WB03 | `testWB03_nhanhB3True_donDaHuy` | `"DH-HUY"` | `DonHangDaHuyException` |
| WB04 | `testWB04_taatCaNhanhFalse_donHopLe` | `"DH-VALID"` | DTO khác null |

> **Quan hệ hộp đen ↔ hộp trắng:** các TC hộp đen (EP/BVA) đã vô tình phủ hết các
> nhánh, nhưng phần hộp trắng vẫn được trình bày riêng để **chứng minh** độ phủ
> C1 = 100% một cách hệ thống (đúng yêu cầu "dùng lần lượt 2 kỹ thuật").

---

## 4. Cài đặt kiểm thử tự động (JUnit)

| Mục | Giá trị |
|-----|---------|
| Framework | JUnit 4 |
| File | `test/test/DonHangServiceTest.java` |
| **Full name lớp kiểm thử** | **`test.DonHangServiceTest`** |
| Tổng số test | 11 (`@Test`) |
| Kỹ thuật cô lập | DAO giả (stub) `FakeDonHangDAO implements IDonHangDAO` và `FakeMatHangDonHangDAO` tiêm qua constructor (DIP) |

### Cách chạy trong Eclipse
1. Chuột phải `DonHangServiceTest.java` → **Run As → JUnit Test**.

### Cách chạy bằng dòng lệnh
```bash
javac -encoding UTF-8 -cp "bin:junit-4.13.2.jar:hamcrest-core-1.3.jar" -d bin test/test/DonHangServiceTest.java
java -cp "bin:junit-4.13.2.jar:hamcrest-core-1.3.jar" org.junit.runner.JUnitCore test.DonHangServiceTest
```

### Kết quả chạy thực tế
```
JUnit version 4.13.2
...........
Time: 0.007

OK (11 tests)
```

✅ **11/11 test PASS.** Toàn bộ test hộp đen + hộp trắng (C1 = 100%) đều thành công.
