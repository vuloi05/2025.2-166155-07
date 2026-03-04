# Ứng Dụng Máy Tính Bỏ Túi (JavaFX MVC)

Chào mừng bạn đến với dự án **Máy tính đơn giản** được xây dựng bằng **JavaFX**! Dự án này áp dụng chặt chẽ mẫu thiết kế **MVC (Model - View - Controller)**, giúp mã nguồn trở nên rõ ràng, dễ bảo trì và mở rộng.

---

## Giới Thiệu Chức Năng

Ứng dụng cung cấp giao diện trực quan để người dùng thực hiện các phép toán cơ bản giữa hai số:

- **Cộng** (+)
- **Trừ** (-)
- **Nhân** (*)
- **Chia** (/)

Đặc biệt, ứng dụng có tích hợp **xử lý lỗi thông minh**:

- Cảnh báo "Invalid input" nếu người dùng điền sai định dạng số.
- Cảnh báo "Cannot divide by 0" nếu chia một số cho 0.

---

## Yêu Cầu Môi Trường Cài Đặt

Để chạy dự án này một cách mượt mà nhất, máy tính của bạn cần có:

- **Java (JDK)**: Phiên bản 11 trở lên (khuyến nghị sử dụng JDK 17+).
- **Maven**: Phiên bản 3.6.0 trở lên.
- **JavaFX**: Phiên bản 21.0.2 (được quản lý trực tiếp qua Maven).

---

## Hướng Dẫn Cài Đặt & Khởi Chạy

### Cài Đặt

Mở terminal/command line và chạy các lệnh sau:

```bash
# 1. Di chuyển vào thư mục dự án
cd calculator_JavaFX

# 2. Cài đặt các gói phụ thuộc (dependencies)
# Maven sẽ tự động tải các dependencies cần thiết lúc bạn build/chạy dự án.
```

### Chạy Ứng Dụng (Môi trường Phát triển)

Cách nhanh nhất để khởi chạy ứng dụng là thông qua Maven Plugin:

```bash
mvn clean javafx:run
```

### Đóng Gói và Chạy (Bản Production)

Nếu bạn muốn đóng gói thành một tệp `.jar` để có thể chạy độc lập:

```bash
mvn clean package
java -jar target/calculator-javafx-1.0.jar
```

---

## Kiến Trúc Ứng Dụng (MVC)

Dự án được phân chia thành 3 lớp riêng biệt:

### 1. Model (`CalculatorModel.java`)

Trái tim của ứng dụng, nơi xử lý toàn bộ logic và thuật toán tính toán (`add`, `sub`, `mul`, `div`). Không chứa bất kỳ liên kết trực tiếp nào với giao diện.

### 2. View (`calculator.fxml`)

Được thiết kế thông qua Scene Builder/FXML. Giao diện bao gồm:

- **2 ô nhập liệu** cho 2 toán hạng \(A và B\).
- **4 nút chức năng** tương ứng với 4 phép toán cơ bản.
- **1 ô kết quả** chỉ hiển thị (read-only).

### 3. Controller (`CalculatorController.java`)

Cầu nối giữa View và Model:

- Bắt các sự kiện click nút từ người dùng trên View.
- Truyền dữ liệu xuống Model để tính toán.
- Nhận kết quả từ Model và hiển thị ngược lên giao diện (View).

---

## Cấu Trúc Thư Mục

```text
calculator_JavaFX/
├── src/main/
│   ├── java/
│   │   ├── main.java                   # Điểm khởi chạy của ứng dụng
│   │   ├── controller/
│   │   │   └── CalculatorController.java # Lớp Controller
│   │   └── model/
│   │       └── CalculatorModel.java      # Lớp Model
│   └── resources/
│       └── view/
│           └── calculator.fxml           # Giao diện FXML
├── pom.xml                               # Tệp cấu hình Maven
└── README.md                             # Tài liệu bạn đang đọc (File này)
```

---

## Công Nghệ Sử Dụng

- **Ngôn ngữ:** Java 11+
- **Giao diện:** JavaFX 21.0.2 (Sử dụng `javafx-controls` & `https://github.com/openjfx/javafx-maven-plugin`)
- **Quản lý dự án / Build Tool:** Maven (sử dụng các plugin: `maven-compiler-plugin`, `javafx-maven-plugin`, `maven-shade-plugin`)

---

## Thông Tin Bổ Sung & Lời Kết

Dự án này là một minh chứng hoàn hảo cho mục đích học tập phát triển phần mềm giao diện trên máy tính bằng Java, đồng thời làm quen với kiến trúc MVC hiện đại.

- **Dự Án:** Homework 01 - Calculator Project
- **Giấy Phép:** Dành cho mục đích học tập nghiên cứu (Educational Purpose)
