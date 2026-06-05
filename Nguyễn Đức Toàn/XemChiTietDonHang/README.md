# XemChiTietDonHang — UC006: Xem chi tiết đơn hàng

**Sinh viên:** Nguyễn Đức Toàn — 20235846  
**Hệ thống:** Đặt hàng nhập khẩu (IT4490 — ITSS)  
**Bài tập 7:** Lập trình và kiểm thử

Mã nguồn được viết **khớp với thiết kế** trong SRS cá nhân:

- **Biểu đồ lớp mức thiết kế BT6** (mục 13) — cấu trúc lớp & quan hệ UC006.
- **Biểu đồ phụ thuộc gói BT6** (mục 14) — phân tầng package.
- **Giao diện** (mục 7) — 6 màn hình (M1–M5) & luồng chuyển màn hình BT4.
- **Đặc tả UC006** (mục 2) — luồng chính + luồng thay thế 5a, 7a/7b.

---

## 1. Cấu trúc project

```
XemChiTietDonHang/
├── src/
│   ├── Main.java                              # Điểm khởi chạy (mở Màn hình 1 — Trang chủ)
│   ├── presentation/                          # Tầng Presentation (<<boundary>>)
│   │   ├── TrangChuView.java                  # Màn hình 1 (Trang chủ / Home)
│   │   ├── DonHangListView.java               # Màn hình 2 (danh sách) + Màn hình 4 (không tìm thấy)
│   │   ├── ChiTietDonHangView.java            # Màn hình 3 (chi tiết)
│   │   └── CanhBaoHuyDialog.java              # Màn hình 5 (cảnh báo đơn đã hủy)
│   ├── businesslogic/                         # Tầng BusinessLogic (<<control>> + service + DTO)
│   │   ├── DonHangController.java             # Điều phối View ↔ Service
│   │   ├── DonHangService.java                # Nghiệp vụ UC006 (★ chứa layChiTiet)
│   │   ├── ChiTietDonHangDTO.java             # DTO cho màn hình chi tiết (BT6)
│   │   └── DonHangDaHuyException.java         # Ngoại lệ hỗ trợ luồng 7a
│   ├── dataaccess/                            # Tầng DataAccess (DAO)
│   │   ├── IDonHangDAO.java                   # Interface DAO (DIP — BT6)
│   │   ├── DonHangDAO.java                    # Triển khai DAO (mock data)
│   │   └── MatHangDonHangDAO.java             # DAO mặt hàng (findByOrderCode)
│   ├── domainmodel/                           # Tầng DomainModel (<<entity>>)
│   │   ├── DonHang.java                       # Có laDaHuy(); BT6 đã bỏ layDSMatHang()
│   │   ├── MatHangDonHang.java
│   │   └── Site.java                          # layThoiGianVanChuyen()
│   └── database/                              # Tầng Database (placeholder, mục 12 & 14)
│       └── package-info.java                  # Gói rỗng — nơi đặt lớp CSDL khi nối DB thật
├── test/
│   └── test/
│       └── DonHangServiceTest.java            # JUnit 4 — full name: test.DonHangServiceTest
├── docs/
│   ├── BT7-KiemThuDonVi-DonHangService-layChiTiet.md   # Tài liệu kiểm thử đơn vị
│   └── BT7-KiemThuUseCase-UC006.md                     # Tài liệu kiểm thử use case
└── README.md
```

> **Tên package = tên tầng trong Biểu đồ phụ thuộc gói BT6** (Presentation,
> BusinessLogic, DataAccess, DomainModel, Database) để khi đọc cấu trúc package
> là tái hiện được đúng biểu đồ gói trong SRS. Gói `database` để rỗng (chỉ có
> `package-info.java`) đúng như biểu đồ — là nơi đặt lớp CSDL khi nối DB thật.
>
> **`TrangChuView` (M1)** là màn hình hệ thống / điểm vào, **không nằm trong
> Biểu đồ lớp BT6** (chỉ mô tả boundary của UC006). M1 tái sử dụng
> `DonHangController` để hiển thị thống kê và điều hướng sang M2.

---

## 2. Luồng điều hướng màn hình (BT4)

```
[*] ──► M1 Trang chủ
          │
          ├── Menu "Quản lý đơn hàng" / Nút "Xem danh sách đơn hàng"
          │         │
          │         ▼
          │   M2 Danh sách đơn hàng ◄── breadcrumb "Trang chủ" / đóng cửa sổ (X)
          │         │
          │         ├── Tra cứu / Lọc (ở lại M2)
          │         ├── Chọn đơn (≠ Đã hủy) ──► M3 Chi tiết ──► Quay lại ──► M2
          │         └── Chọn đơn (= Đã hủy)  ──► M4 Cảnh báo ──► Xác nhận ──► M2
          │
          └── Bảng "Đơn hàng gần đây": chỉ hiển thị (read-only), không mở chi tiết
```

| Màn hình | Lớp code | Ghi chú |
|----------|----------|---------|
| M1 — Trang chủ | `TrangChuView` | Dashboard: card thống kê + bảng đơn gần đây (chỉ xem) |
| M2 — Danh sách | `DonHangListView` | Breadcrumb `Trang chủ > Danh sách đơn hàng`; trạng thái không tìm thấy = M4 |
| M3 — Chi tiết | `ChiTietDonHangView` | JDialog modal; nút **Quay lại danh sách** |
| M4 — Không tìm thấy | *(trong `DonHangListView`)* | CardLayout — không mở cửa sổ riêng |
| M5 — Đã hủy | `CanhBaoHuyDialog` | JDialog modal từ M2 |

---

## 3. Ánh xạ Code ↔ Biểu đồ lớp BT6 (mục 13)

| Lớp trong biểu đồ | Lớp trong code | Phương thức khớp biểu đồ |
|-------------------|----------------|--------------------------|
| `DonHangListView` | `presentation.DonHangListView` | `hienThiDanhSachDonHang`, `hienThiKetQuaTimKiem`, `hienThiThongBao` |
| `ChiTietDonHangView` | `presentation.ChiTietDonHangView` | `hienThiChiTietDonHang`, `quayLaiDanhSach` |
| `CanhBaoHuyDialog` | `presentation.CanhBaoHuyDialog` | `hienThiCanhBaoDaHuy`, `xacNhanVaQuayLai` |
| `DonHangController` | `businesslogic.DonHangController` | `yeuCauDSDonHang`, `traCuuDonHang`, `yeuCauChiTietDonHang`, `xacNhanVaQuayLai` |
| `DonHangService` | `businesslogic.DonHangService` | `layDSDonHang`, `timKiemDonHang`, `layChiTiet`, `demTongSoDonHang` |
| `ChiTietDonHangDTO` | `businesslogic.ChiTietDonHangDTO` | `createFrom` (Factory Method) |
| `IDonHangDAO` | `dataaccess.IDonHangDAO` | `findAll`, `findByCode`, `findByFilters`, `countAll`, `findSiteByCode` |
| `DonHangDAO` | `dataaccess.DonHangDAO` | `..|>` IDonHangDAO |
| `MatHangDonHangDAO` | `dataaccess.MatHangDonHangDAO` | `findByOrderCode` |
| `DonHang` | `domainmodel.DonHang` | `laDaHuy()` |
| `MatHangDonHang` | `domainmodel.MatHangDonHang` | — |
| `Site` | `domainmodel.Site` | `layThoiGianVanChuyen` |

**5 cải tiến BT6 đã hiện diện trong code:**

1. Tách 1 `DonHangView` → 3 lớp boundary (SRP).
2. Thêm `ChiTietDonHangDTO` — View không nhận entity thô (Low Coupling).
3. Service phụ thuộc `IDonHangDAO` thay vì `DonHangDAO` (DIP).
4. Bỏ `DonHang.layDSMatHang()` — lấy mặt hàng qua `MatHangDonHangDAO` (Information Expert).
5. Dùng `DonHang.laDaHuy()` thay cho `kiemTraTrangThai()` ở Service.

---

## 4. Hướng dẫn Import & Chạy bằng Eclipse

1. `File → Import → General → Existing Projects into Workspace`, trỏ tới thư mục `XemChiTietDonHang`.
2. Đảm bảo `src` và `test` đều là **Source Folder** (đã cấu hình sẵn trong `.classpath`).
3. Thêm thư viện JUnit 4: chuột phải project → `Build Path → Add Library → JUnit → JUnit 4`.
4. Chạy ứng dụng: mở `src/Main.java` → `Run As → Java Application` → mở **M1 Trang chủ**.
5. Từ Home: chọn **Quản lý đơn hàng** (sidebar) hoặc **Xem danh sách đơn hàng** → **M2**.
6. Từ M2: click breadcrumb **Trang chu** hoặc đóng cửa sổ (X) → quay **M1**.
7. Chạy test: chuột phải `test/test/DonHangServiceTest.java` → `Run As → JUnit Test`.

---

## 5. Kiểm thử (BT7)

| Loại | Vị trí | Kết quả |
|------|--------|---------|
| Kiểm thử đơn vị — module `DonHangService.layChiTiet` (hộp đen + hộp trắng C1) | `test.DonHangServiceTest` (11 test) | ✅ 11/11 PASS, C1 = 100% |
| Kiểm thử use case UC006 (5 scenario) | `docs/BT7-KiemThuUseCase-UC006.md` | ✅ 5/5 Pass |

Xem chi tiết phân tích kỹ thuật trong thư mục `docs/`.

---

## 6. Ghi chú về dữ liệu

Project dùng **mock data** trong tầng DataAccess (chưa nối CSDL thật), gồm 5 đơn hàng:

- `DH-2025-001` (Đã gửi, Tàu), `DH-2025-002` (Đang xử lý, Hàng không),
  `DH-2025-003` (Nháp, Tàu), `DH-2025-004` (Đã gửi, Hàng không),
  `DH-2025-005` (**Đã hủy**, Tàu — phục vụ luồng 7a).

Nhờ DIP (`IDonHangDAO`), khi cần nối MySQL chỉ phải viết một lớp DAO mới, không
phải sửa tầng nghiệp vụ.
