# Hệ thống Phân bổ Tự động & Kiểm quản Kho - Bài Tập Lớn
## Các Use Case phụ trách: 
1. Tính toán phân bổ tự động (Nghiệp vụ Greedy)
2. Đối chiếu hàng thực tế với đơn hàng (Nghiệp vụ Kiểm hàng nhập kho)

---

## Cấu trúc Project tổng thể (Đã tích hợp)
PhanBoTuDong/
├── src/
│   ├── Main.java                          # Điểm khởi chạy ứng dụng (Mở khóa giao diện tổng)
│   ├── entity/                            # Tầng Entity (Thực thể)
│   │   ├── MatHang.java                   # Mặt hàng trong yêu cầu nhập
│   │   ├── YeuCauNhapHang.java            # Yêu cầu nhập hàng
│   │   ├── ThongTinSite.java              # Thông tin Site nhập khẩu
│   │   ├── ThongTinKho.java               # Thông tin tồn kho tại Site
│   │   ├── KetQuaPhanBo.java              # Kết quả phân bổ
│   │   ├── DonHang.java                   # Đơn hàng (dự thảo)
│   │   │   // --- Cập nhật cấu trúc đối chiếu kho (UC007) ---
│   │   ├── DonDatHang.java                # [Mới] Đơn đặt hàng gốc cần đối chiếu
│   │   ├── ChiTietDonDatHang.java         # [Mới] Dòng mặt hàng đặt ban đầu
│   │   ├── BienBanDoiChieu.java           # [Mới] Biên bản lưu vết kết quả đối chiếu kho
│   │   └── ChiTietDoiChieu.java           # [Mới] Chi tiết số lượng thực nhận và chênh lệch từng dòng
│   ├── dao/                               # Tầng DAO (Truy xuất dữ liệu - Mock Data)
│   │   ├── YeuCauNhapHangDAO.java         # DAO cho Yêu cầu nhập hàng
│   │   ├── ThongTinKhoDAO.java            # DAO cho Thông tin tồn kho
│   │   ├── ThongTinSiteDAO.java           # DAO cho Thông tin Site
│   │   └── KetQuaPhanBoDAO.java           # DAO cho Kết quả phân bổ
│   ├── service/                           # Tầng Service (Nghiệp vụ - Thuật toán)
│   │   ├── PhanBoService.java             # ★ CORE: Thuật toán Greedy phân bổ tự động
│   │   │   // --- Gói chiến lược và nhà máy áp dụng Design Patterns (UC007) ---
│   │   ├── strategy/                      # [Mới] Cô lập thuật toán phân loại chênh lệch hàng
│   │   │   ├── XuLyTrangThaiStrategy.java # Interface Chiến lược chung
│   │   │   ├── ManhXuLyKhopStrategy.java  # Chiến lược xử lý khi thực nhận == đặt hàng
│   │   │   ├── ManhXuLyThieuStrategy.java # Chiến lược xử lý khi thực nhận < đặt hàng
│   │   │   └── ManhXuLyThuaStrategy.java  # Chiến lược xử lý khi thực nhận > đặt hàng
│   │   └── factory/                       # [Mới] Nhà máy đóng gói và khởi tạo đối tượng biên bản
│   │       └── ManhBienBanFactory.java    # Khởi tạo Biên bản theo chế độ Lưu tạm / Xác nhận
│   ├── controller/                        # Tầng Controller (Điều khiển)
│   │   ├── PhanBoController.java          # Điều phối View <-> Service phân bổ
│   │   └── BoDieuKhienDoiChieu.java       # [Mới] ★ CORE: Bộ điều phối kiểm hàng (Singleton Pattern)
│   └── view/                              # Tầng View (Giao diện Swing)
│       ├── PhanBoView.java                # Giao diện chính (Đã mở khóa kết nối nút "Kiểm hàng")
│       └── ManHinhDoiChieuNhapKho.java    # [Mới] Form lưới nhập liệu, kiểm toán chênh lệch thực tế
├── test/
│   └── test/
│       ├── PhanBoServiceTest.java         # JUnit Test cho module Phân bổ tự động
│       └── BoDieuKhienDoiChieuTest.java   # [Mới] JUnit 4 Test độc lập cho module Đối chiếu kho
└── README.md                              # File hướng dẫn tổng hợp này
Rõ rồi ông Thắng! Dưới đây là toàn bộ nội dung file được đóng gói gọn gàng trong một khối mã Markdown (Code Block).

Ông chỉ cần nhấn vào nút **Copy** (Sao chép) ở góc phải của khối mã dưới đây, sau đó mở file `README.md` trong thư mục dự án ra, dán đè (Paste) vào là xong luôn nhé:

```markdown
# Hệ thống Phân bổ Tự động & Kiểm quản Kho - Bài Tập Lớn
## Sinh viên thực hiện: Vũ Tiến Lợi
## Các Use Case phụ trách: 
1. Tính toán phân bổ tự động (Nghiệp vụ Greedy)
2. UC007: Đối chiếu hàng thực tế với đơn hàng (Nghiệp vụ Kiểm hàng nhập kho)

---

## Cấu trúc Project tổng thể (Đã tích hợp)


```

PhanBoTuDong/
├── src/
│   ├── Main.java                          # Điểm khởi chạy ứng dụng (Mở khóa giao diện tổng)
│   ├── entity/                            # Tầng Entity (Thực thể)
│   │   ├── MatHang.java                   # Mặt hàng trong yêu cầu nhập
│   │   ├── YeuCauNhapHang.java            # Yêu cầu nhập hàng
│   │   ├── ThongTinSite.java              # Thông tin Site nhập khẩu
│   │   ├── ThongTinKho.java               # Thông tin tồn kho tại Site
│   │   ├── KetQuaPhanBo.java              # Kết quả phân bổ
│   │   ├── DonHang.java                   # Đơn hàng (dự thảo)
│   │   │   // --- Cập nhật cấu trúc đối chiếu kho (UC007) ---
│   │   ├── DonDatHang.java                # [Mới] Đơn đặt hàng gốc cần đối chiếu
│   │   ├── ChiTietDonDatHang.java         # [Mới] Dòng mặt hàng đặt ban đầu
│   │   ├── BienBanDoiChieu.java           # [Mới] Biên bản lưu vết kết quả đối chiếu kho
│   │   └── ChiTietDoiChieu.java           # [Mới] Chi tiết số lượng thực nhận và chênh lệch từng dòng
│   ├── dao/                               # Tầng DAO (Truy xuất dữ liệu - Mock Data)
│   │   ├── YeuCauNhapHangDAO.java         # DAO cho Yêu cầu nhập hàng
│   │   ├── ThongTinKhoDAO.java            # DAO cho Thông tin tồn kho
│   │   ├── ThongTinSiteDAO.java           # DAO cho Thông tin Site
│   │   └── KetQuaPhanBoDAO.java           # DAO cho Kết quả phân bổ
│   ├── service/                           # Tầng Service (Nghiệp vụ - Thuật toán)
│   │   ├── PhanBoService.java             # ★ CORE: Thuật toán Greedy phân bổ tự động
│   │   │   // --- Gói chiến lược và nhà máy áp dụng Design Patterns (UC007) ---
│   │   ├── strategy/                      # [Mới] Cô lập thuật toán phân loại chênh lệch hàng
│   │   │   ├── XuLyTrangThaiStrategy.java # Interface Chiến lược chung
│   │   │   ├── ManhXuLyKhopStrategy.java  # Chiến lược xử lý khi thực nhận == đặt hàng
│   │   │   ├── ManhXuLyThieuStrategy.java # Chiến lược xử lý khi thực nhận < đặt hàng
│   │   │   └── ManhXuLyThuaStrategy.java  # Chiến lược xử lý khi thực nhận > đặt hàng
│   │   └── factory/                       # [Mới] Nhà máy đóng gói và khởi tạo đối tượng biên bản
│   │       └── ManhBienBanFactory.java    # Khởi tạo Biên bản theo chế độ Lưu tạm / Xác nhận
│   ├── controller/                        # Tầng Controller (Điều khiển)
│   │   ├── PhanBoController.java          # Điều phối View <-> Service phân bổ
│   │   └── BoDieuKhienDoiChieu.java       # [Mới] ★ CORE: Bộ điều phối kiểm hàng (Singleton Pattern)
│   └── view/                              # Tầng View (Giao diện Swing)
│       ├── PhanBoView.java                # Giao diện chính (Đã mở khóa kết nối nút "Kiểm hàng")
│       └── ManHinhDoiChieuNhapKho.java    # [Mới] Form lưới nhập liệu, kiểm toán chênh lệch thực tế
├── test/
│   └── test/
│       ├── PhanBoServiceTest.java         # JUnit Test cho module Phân bổ tự động
│       └── BoDieuKhienDoiChieuTest.java   # [Mới] JUnit 4 Test độc lập cho module Đối chiếu kho
└── README.md                              # File hướng dẫn tổng hợp này

```

---

## Hướng dẫn Import & Vận hành trên Eclipse

### Bước 1: Mở Project clone vào Eclipse
1. Mở Eclipse → `File` → `Import...` → chọn `General` → `Existing Projects into Workspace` → `Next`.
2. Tại ô *Select root directory*, nhấn `Browse...` trỏ đến thư mục `PhanBoTuDong/`.
3. Nhấn `Finish`.

### Bước 2: Kích hoạt Thư viện JUnit 4
1. Chuột phải vào tên Project → `Properties` → `Java Build Path`.
2. Chọn thẻ `Libraries` → Click chọn vùng `Classpath`.
3. Nhấn nút `Add Library...` ở cạnh phải → Chọn `JUnit` → Chọn phiên bản **JUnit 4** → `Finish`.
4. Nhấn `Apply and Close`.

### Bước 3: Khởi chạy Ứng dụng tích hợp (Hàm Main)
1. Mở file `src/Main.java`.
2. Click chuột phải → `Run As` → `Java Application`.
3. Hệ thống sẽ hiển thị bảng điều khiển tổng quan của nhóm. Người dùng nhấn nút **`✅ Kiểm hàng`** (đã được mở khóa liên kết hoạt động) để khởi chạy trực tiếp Form giao diện đối chiếu thực tế `ManHinhDoiChieuNhapKho`.

### Bước 4: Chạy trọn bộ Unit Test Nghiệp vụ
1. Tìm đến thư mục `test/test/`.
2. Click chuột phải vào file test mong muốn (`PhanBoServiceTest.java` hoặc `BoDieuKhienDoiChieuTest.java`) → Chọn `Run As` → `JUnit Test`.
3. Tab JUnit của Eclipse sẽ hiển thị **Thanh màu xanh lá cây (Green Bar)** chứng minh toàn bộ logic thuật toán vượt qua các ca kiểm thử.

---

## Nghiệp vụ Module 1: Tính toán phân bổ tự động (Greedy)

### Tiêu chí ưu tiên:
1. **Ưu tiên đường Tàu** → Chi phí thấp, thời gian dài.
2. **Ưu tiên Site có SL tồn kho lớn** → Sắp xếp giảm dần.
3. **Số lượng Site ít nhất** → Lấy từ trên xuống theo chiến lược Greedy đến khi đủ.

### Mock Data kiểm thử module 1:
* `YC-2025-001`: Mặt hàng IC-7805, Tụ điện, Điện trở → Đủ hàng phân bổ.
* `YC-2025-002`: Cảm biến (Yêu cầu 300, kho có 250) → Kích hoạt cảnh báo thiếu 50 sản phẩm.

---

## Nghiệp vụ Module 2 (Cập nhật): UC007 - Đối chiếu hàng thực tế với đơn hàng

Module này giải quyết bài toán kiểm toán kho khi hàng về tới cổng: Đối chiếu giữa số lượng khai báo trên giấy tờ đặt hàng và số lượng đếm được ngoài thực tế, quản lý mã Serial/Lot định danh.

### Luồng xử lý chi tiết (Runtime Flow):
1. **Tải thông tin đơn hàng:** Màn hình tự động nạp danh sách các mặt hàng cần nhập thuộc đơn hàng được chọn. Cột thực nhận mặc định bằng 0.
2. **Nhập liệu & Đọc định danh:** Nhân viên kho tiến hành gõ số lượng kiểm đếm thực tế và điền mã định danh Serial/Lot của lô hàng.
3. **Tính toán chênh lệch tự động:** Khi nhấn nút `Tính Toán Đối Chiếu`, hệ thống thực hiện phép tính: `Số lượng chênh lệch = Thực nhận - Đặt hàng`.
4. **Cảnh báo lệch hàng (Luồng phụ 7a):** Nếu phát hiện chênh lệch dòng hàng khác 0, một Popup bắt buộc giải trình xuất hiện, yêu cầu nhân viên nhập lý do (Ví dụ: Hao hụt vận chuyển, giao dư hàng...) để lưu vết lịch sử kiểm toán.
5. **Kiểm tra mã định danh (Luồng phụ 11a):** Hệ thống chặn và cảnh báo nếu phát hiện chuỗi mã Serial/Lot bị trống, lỗi định dạng hoặc trùng lặp trên database.
6. **Đóng gói sinh biên bản:** Khi hoàn tất, người dùng có thể chọn `Lưu tạm` để tạo bản nháp dữ liệu hoặc chọn `Xác nhận nhập kho` để khóa số, sinh mã Biên bản tự động dạng `BB-[Timestamp]` phục vụ kế toán kho.

---

## Các Mẫu Thiết Kế Áp Dụng (Design Patterns) cho UC007

Nhằm đảm bảo tính mở rộng và tuân thủ các nguyên lý kỹ nghệ phần mềm (SOLID), module 2 áp dụng 3 mẫu thiết kế GoF:

1. **Singleton Pattern (Áp dụng tại `BoDieuKhienDoiChieu`):**
   * Khóa hàm khởi tạo private, truy cập thông qua phương thức tĩnh `getInstance()`.
   * *Lợi ích:* Đảm bảo duy nhất một bộ điều khiển chạy ngầm trong suốt vòng đời phiên làm việc, tránh việc cấp phát/giải phóng RAM vô tội vạ khi xử lý lượng lớn dữ liệu dòng hàng.
2. **Strategy Pattern (Áp dụng xử lý Trạng thái Dòng hàng):**
   * Tách logic phân loại kết quả chênh lệch phức tạp ra khỏi thực thể tĩnh. Sử dụng Interface chung `XuLyTrangThaiStrategy` và 3 chiến lược độc lập: `ManhXuLyKhopStrategy`, `ManhXuLyThieuStrategy`, `ManhXuLyThuaStrategy`.
   * *Lợi ích:* Tuân thủ nguyên lý Đóng/Mở (Open/Closed). Khi doanh nghiệp thay đổi quy định phân loại kho (ví dụ thêm hàng lỗi, hàng bù đơn sau), chỉ cần viết thêm class Strategy mới mà không làm ảnh hưởng đến code cốt lõi hiện tại.
3. **Factory Method Pattern (Áp dụng sinh Biên bản):**
   * Lớp nhà máy `ManhBienBanFactory` chịu trách nhiệm cô lập logic khởi tạo đối tượng `BienBanDoiChieu`.
   * *Lợi ích:* Phân tách rõ ràng giữa việc sử dụng đối tượng và việc tạo mới đối tượng dựa trên tham số hành vi tương tác đầu vào (`LUU_TAM` hoặc `XAC_NHAN`).

---

## Kịch bản Kiểm thử Tự động Module 2 (`BoDieuKhienDoiChieuTest`)

Kịch bản kiểm thử được thiết kế độc lập trên nền tảng **JUnit 4**, bao phủ trọn vẹn các ca kiểm thử nghiệp vụ nghiêm ngặt:

* **Ca 1: Kiểm thử Hộp đen (Giá trị biên cho mã định danh):**
  * Đầu vào: Một chuỗi Serial chuẩn, một chuỗi trống `""`, và một chuỗi chứa mã lỗi giả định `"ERR_TRUNG_LAP"`.
  * Kết quả khẳng định: `assertTrue` nhận diện chính xác mã hợp lệ, `assertFalse` loại bỏ và chặn đứng các mã lỗi để kích hoạt popup giao diện.
* **Ca 2: Kiểm thử Hộp trắng (Độ bao phủ nhánh C1 - Luồng thiếu hàng):**
  * Giả lập: Mặt hàng đặt 100 sản phẩm, nhân viên thực nhận 85 sản phẩm ngoài thực tế.
  * Kết quả khẳng định: Hàm `assertEquals` chứng minh hệ thống tính chính xác số chênh lệch `-15` và ép chuỗi trạng thái kết quả đầu ra về đúng nhãn `"Giao thieu"` do `ManhXuLyThieuStrategy` xử lý.
* **Ca 3: Kiểm thử Hộp trắng (Độ bao phủ nhánh C1 - Luồng khớp hàng & Sinh biên bản Factory):**
  * Giả lập: Số lượng thực nhận khớp khít 100% số lượng đặt ban đầu. Thực hiện lệnh chốt khóa sổ.
  * Kết quả khẳng định: Kiểm tra đối tượng Biên bản sinh ra không được null (`assertNotNull`), mã biên bản phải tự sinh đúng định dạng tiền tố (`startsWith("BB-")`), và trạng thái nạp dòng hàng chi tiết phải trả ra nhãn `"Khop"`.

```