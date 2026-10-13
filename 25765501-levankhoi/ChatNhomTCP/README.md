# CHƯƠNG TRÌNH CHAT NHÓM QUA GIAO THỨC TCP (JAVA SOCKET)

## 1. Giới thiệu đề tài

Xây dựng chương trình chat nhóm sử dụng Java Socket và giao thức TCP. Chương trình cho phép nhiều client kết nối đến server, tham gia các phòng chat và trao đổi tin nhắn với những người dùng trong cùng phòng.

## 2. Chức năng chính

- Kết nối nhiều client đến server TCP.
- Nhập tên người dùng khi kết nối.
- Hiển thị danh sách phòng chat.
- Tham gia các phòng chat: Phòng Học Nhóm, Phòng Game, Phòng Làm Việc Nhóm và Phòng Dân Cư.
- Gửi và nhận tin nhắn trong cùng phòng.
- Không gửi tin nhắn riêng của phòng này đến thành viên phòng khác.
- Rời phòng bằng lệnh `/quit`.
- Ngắt kết nối và thoát chương trình bằng lệnh `/exit`.
- Server tiếp tục hoạt động khi một client thoát.

## 3. Công nghệ sử dụng

- Ngôn ngữ lập trình: Java.
- Giao tiếp mạng: TCP Socket.
- Xử lý nhiều client: Multithreading.
- Trao đổi dữ liệu văn bản: BufferedReader, PrintWriter và UTF-8.
- Môi trường lập trình: Visual Studio Code và JDK.

## 4. Cấu trúc chương trình

Các file mã nguồn chính:

- `ChatServer.java`: Khởi tạo server và chờ client kết nối.
- `ClientHandler.java`: Xử lý kết nối và yêu cầu của từng client.
- `ChatRoom.java`: Quản lý thành viên phòng chat và phát tin nhắn trong phòng.
- `ChatClient.java`: Giao diện dòng lệnh phía client, gửi yêu cầu và tin nhắn.
- `ClientReceiver.java`: Nhận và hiển thị tin nhắn từ server.

## 5. Hướng dẫn biên dịch và chạy

Mở Terminal tại thư mục chứa các file Java.

### Bước 1: Biên dịch

```powershell
javac -encoding UTF-8 -d out *.java
```

### Bước 2: Khởi động server

```powershell
java -cp out ChatServer
```

Giữ Terminal server hoạt động trong suốt quá trình kiểm thử.

### Bước 3: Khởi động client

Mở Terminal mới tại cùng thư mục và chạy:

```powershell
java -cp out ChatClient
```

Lặp lại bước này trên các Terminal khác để mở nhiều client.

### Bước 4: Sử dụng chương trình

- Nhập tên người dùng theo yêu cầu.
- Nhập `/rooms` để xem danh sách phòng nếu chương trình hỗ trợ lệnh này.
- Nhập `/join 4` để tham gia Phòng Dân Cư.
- Nhập `/join 2` để tham gia Phòng Game.
- Nhập `/quit` để rời phòng và quay lại danh sách phòng.
- Nhập `/exit` để ngắt kết nối và thoát chương trình.

## 6. Các trường hợp kiểm thử

1. Hai client cùng phòng nhận được tin nhắn
2. Client gửi tin nhắn trong phòng chat
3. Client rời phòng bằng lệnh /quit
4. Client thoát chương trình bằng lệnh /exit
5. Nhập lệnh /join trong PowerShell sau khi chương trình đã thoát
6. Kiểm tra khả năng hoạt động độc lập của server
7. Client ở Phòng Game không nhận được tin nhắn từ Phòng Dân Cư

## 7. Kết luận

Chương trình vận dụng kiến thức lập trình mạng với Java Socket, giao thức TCP, xử lý đa luồng và quản lý phòng chat. Các trường hợp kiểm thử được sử dụng để đánh giá khả năng kết nối, trao đổi thông điệp, phân tách phòng và xử lý ngắt kết nối.
