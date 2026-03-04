# Hướng dẫn thao tác GIT cho Bài tập 1

Đây là hướng dẫn chi tiết để bạn (từng thành viên trong nhóm) thực hiện các thao tác trên GIT để hoàn thành yêu cầu BTVN01 và **nhớ chụp ảnh màn hình** sau mỗi bước nhé.

### Bước 1: Khởi tạo repository / Clone repo
Nếu bạn chưa có repository:
```bash
git init
```
Nếu bạn sử dụng repository nhóm đã có sẵn:
```bash
git clone <URL_CUA_REPO>
cd <Ten_Thu_Muc>
```

### Bước 2: Thêm file và Commit (+ Chụp ảnh)
Gộp các ứng dụng vào project và commit.
```bash
# Thêm file HelloWorld
git add HelloWorld/HelloWorld.java
git status
# CHỤP ẢNH màn hình git status ở đây!

git commit -m "Thêm chức năng HelloWorld"
# CHỤP ẢNH màn hình lệnh commit thành công
```

### Bước 3: Push code (+ Chụp ảnh)
```bash
git push origin master  # hoặc git push origin main
# CHỤP ẢNH lệnh push
```

### Bước 4: Tạo nhánh (Branch), Sửa đổi, Thêm file (+ Chụp ảnh)
```bash
git branch update-helloworld
git checkout update-helloworld
# (hoặc git checkout -b update-helloworld)

# Mở code HelloWorld.java, thay đổi câu chào "Xin chào" thành "Welcome"
git add HelloWorld/HelloWorld.java
git commit -m "Update loi chao HelloWorld"
# CHỤP ẢNH việc tạo nhánh và commit trên nhánh mới
```

### Bước 5: Pull, Merge và Xử lý xung đột (Conflict)
Bây giờ giả sử nhánh main có một cập nhật khác (bạn có thể tự sửa đổi `HelloWorld.java` trực tiếp trên github giao diện web để tạo conflict).
Sau đó về lại nhánh main:
```bash
git checkout main
git pull origin main

# Mở và merge nhánh mới vào main
git merge update-helloworld
```

**[Để tạo Xung đột chủ ý]**: Bạn sửa `HelloWorld` trên `main` thành "Chao mung" ở cùng 1 dòng. Thay đổi trên `update-helloworld` là "Welcome". Khi `git merge` chắc chắn sẽ báo CONFLICT.
```bash
# CHỤP ẢNH có dòng chữ báo Merge Conflict!
```
Mở file `HelloWorld.java` ra và tự quyết định hợp nhất, sau đó:
```bash
git add HelloWorld/HelloWorld.java
git commit -m "Giai quyet xung dot tren HelloWorld"
# CHỤP ẢNH commit merge thành công
```

### Bước 6: Xóa file (+ Chụp ảnh)
```bash
# Giả sử bạn tao 1 file rac.txt
touch rac.txt
git add rac.txt
git commit -m "Them file rac"

# Thao tác Xóa
git rm rac.txt
git status
git commit -m "Xoa file rac.txt"
# CHỤP ẢNH thao tác xóa và commit
```
