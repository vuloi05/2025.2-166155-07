package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import entity.*;
import controller.*;

public class ManHinhDoiChieuNhapKho extends JFrame {
    private BoDieuKhienDoiChieu controller;
    private DonDatHang donHangHienTai;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtMaDinhDanh;

    public ManHinhDoiChieuNhapKho() {
        controller = BoDieuKhienDoiChieu.getInstance();
        
        // Giả lập dữ liệu một đơn hàng mẫu ở trạng thái chờ về kho để hiển thị lên bảng
        donHangHienTai = new DonDatHang("DH-007", new Date(), "CHO_VE_KHO");
        donHangHienTai.addChiTiet(new ChiTietDonDatHang("MH-XYZ", "Mat Hang Cua Thang", 100));
        donHangHienTai.addChiTiet(new ChiTietDonDatHang("MH-ABC", "San Pham Thu Nghiem", 50));

        setTitle("UC007 - MAN HINH DOI CHIEU NHAP KHO");
        setSize(750, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 1. Panel trên cùng: Hiển thị thông tin tổng quan đơn hàng
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(230, 242, 255));
        topPanel.add(new JLabel("<html><b>Ma Don Hang:</b> " + donHangHienTai.getMaDonHang() + "</html>"));
        topPanel.add(new JLabel("  |  "));
        topPanel.add(new JLabel("<html><b>Trang thai:</b> " + donHangHienTai.getTrangThaiDonHang() + "</html>"));
        add(topPanel, BorderLayout.NORTH);

        // 2. Bảng hiển thị danh sách dòng mặt hàng đối chiếu
        String[] columns = {"Ma Mat Hang", "Ten Mat Hang", "So Luong Dat", "So Luong Thuc Nhan (Sua tai day)", "Chenh Lech", "Trang Thai Ket Qua"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Chỉ cho phép kích đúp sửa dữ liệu tại cột Số Lượng Thực Nhận
            }
        };
        
        // Đổ dữ liệu mặt hàng gốc vào lưới hiển thị ban đầu
        for (ChiTietDonDatHang ct : donHangHienTai.getDsChiTiet()) {
            tableModel.addRow(new Object[]{ct.getMaMatHang(), ct.getTenMatHang(), ct.getSoLuongDatHang(), 0, 0, "Chua doi chieu"});
        }
        table = new JTable(tableModel);
        table.setRowHeight(25);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // 3. Panel dưới cùng: Nhập mã định danh và các nút điều phối hành vi
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1));
        
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.add(new JLabel("Nhap ma dinh danh Serial/Lot: "));
        txtMaDinhDanh = new JTextField("SERIAL-OK-111", 15);
        inputPanel.add(txtMaDinhDanh);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnTinhToan = new JButton("Tinh Toan Doi Chieu");
        JButton btnXacNhan = new JButton("Xac Nhan Nhap Kho");
        JButton btnLuuTam = new JButton("Luu Tam Ban Nhap");

        buttonPanel.add(btnTinhToan);
        buttonPanel.add(btnXacNhan);
        buttonPanel.add(btnLuuTam);
        
        bottomPanel.add(inputPanel);
        bottomPanel.add(buttonPanel);
        add(bottomPanel, BorderLayout.SOUTH);

        // ================= XỬ LÝ SỰ KIỆN NÚT: TÍNH TOÁN ĐỐI CHIẾU =================
        btnTinhToan.addActionListener(e -> {
            String serial = txtMaDinhDanh.getText();
            // Gọi hàm kiểm tra mã định danh (Luồng phụ 11a báo lỗi)
            if (!controller.thucThiKiemTraTinhHopLeMaDinhDanh(serial)) {
                JOptionPane.showMessageDialog(this, "LOI: Ma dinh danh Serial/Lot khong hop le hoac bi trung lap!", "PP_LoiMaDinhDanh", JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<ChiTietDoiChieu> duLieuNhap = new ArrayList<ChiTietDoiChieu>();
            try {
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    String maMH = tableModel.getValueAt(i, 0).toString();
                    int thucNhan = Integer.parseInt(tableModel.getValueAt(i, 3).toString());
                    
                    ChiTietDoiChieu dong = new ChiTietDoiChieu(maMH, thucNhan, "Nguyen ven", serial);
                    duLieuNhap.add(dong);
                }
                
                // Đẩy dữ liệu xuống Bộ điều khiển xử lý thuật toán Strategy
                List<ChiTietDoiChieu> ketQua = controller.xuLyTinhToanDoiChieu(donHangHienTai, duLieuNhap);
                
                // Cập nhật ngược kết quả tính toán lên lưới hiển thị giao diện
                for (int i = 0; i < ketQua.size(); i++) {
                    ChiTietDoiChieu res = ketQua.get(i);
                    tableModel.setValueAt(res.getSoLuongChenhLech(), i, 4);
                    tableModel.setValueAt(res.getKetQuaTrangThai(), i, 5);
                    
                    // Kích hoạt Popup bắt giải trình nếu phát hiện thừa/thiếu hàng (Luồng phụ 7a)
                    if (res.getSoLuongChenhLech() != 0) {
                        String lyDo = JOptionPane.showInputDialog(this, 
                            "PP_CanhBaoChenhLech: Mat hang " + res.getMaMatHang() + " bi lenh " + res.getSoLuongChenhLech() + ".\nVui long nhap ly do chenh lech:", 
                            "Canh Bao Lech Hang", JOptionPane.WARNING_MESSAGE);
                        res.setLyDoChenhLech(lyDo != null ? lyDo : "Chua ro ly do");
                    }
                }
                JOptionPane.showMessageDialog(this, "MH_BangKetQuaChiTiet: Tinh toan doi chieu hoan tat!", "Thong Bao", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui long nhap so nguyen hop le vao cot So Luong Thuc Nhan!", "Loi Nhap Lieu", JOptionPane.ERROR_MESSAGE);
            }
        });

        // ================= XỬ LÝ SỰ KIỆN NÚT: XÁC NHẬN NHẬP KHO =================
        btnXacNhan.addActionListener(e -> {
            if (tableModel.getRowCount() > 0 && tableModel.getValueAt(0, 5).toString().equals("Chua doi chieu")) {
                JOptionPane.showMessageDialog(this, "Vui long nhan nut 'Tinh Toan Doi Chieu' truoc khi xac nhan!", "Canh Bao", JOptionPane.WARNING_MESSAGE);
                return;
            }
            BienBanDoiChieu bb = controller.xacNhanNhapKho(donHangHienTai.getMaDonHang(), "NhanVienThang");
            JOptionPane.showMessageDialog(this, "PP_ThongBaoThanhCong!\nDa sinh bien ban doi chieu chinh thuc: " + bb.getMaBienBanDoiChieu() + "\nDon hang " + donHangHienTai.getMaDonHang() + " da duoc chot khoa nhap kho.", "Thanh Cong", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        });

        // ================= XỬ LÝ SỰ KIỆN NÚT: LƯU TẠM =================
        btnLuuTam.addActionListener(e -> {
            BienBanDoiChieu bb = controller.xuLyLuuTamBanNhap(donHangHienTai.getMaDonHang(), "NhanVienThang");
            JOptionPane.showMessageDialog(this, "PP_ThongBaoLuuTam!\nDa dong goi va luu ban nhap thanh cong.\nMa bien ban tam: " + bb.getMaBienBanDoiChieu(), "Thong Bao", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        });
    }
}