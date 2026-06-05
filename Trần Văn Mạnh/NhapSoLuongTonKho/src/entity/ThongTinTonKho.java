package entity;

import java.time.LocalDateTime;

/**
 * Lớp thực thể đại diện cho Thông tin tồn kho tại một Site.
 * Tương ứng với class <<entity>> ThongTinTonKho trong Class Diagram.
 *
 * Thuộc tính: maSite, maHang, soLuong, thoiGianCapNhat
 * Phương thức: taoMoiTonKho()
 */
public class ThongTinTonKho {

    private int maSite;
    private int maHang;
    private int soLuong;
    private LocalDateTime thoiGianCapNhat;

    public ThongTinTonKho() {}

    public ThongTinTonKho(int maSite, int maHang, int soLuong, LocalDateTime thoiGianCapNhat) {
        this.maSite = maSite;
        this.maHang = maHang;
        this.soLuong = soLuong;
        this.thoiGianCapNhat = thoiGianCapNhat;
    }

    /**
     * Tạo bản ghi tồn kho mới với thời gian cập nhật = hiện tại.
     * Tương ứng: taoMoiTonKho() : void trong Class Diagram.
     */
    public static ThongTinTonKho taoMoiTonKho(int maSite, int maHang, int soLuong) {
        return new ThongTinTonKho(maSite, maHang, soLuong, LocalDateTime.now());
    }

    // Getters & Setters
    public int getMaSite() { return maSite; }
    public void setMaSite(int maSite) { this.maSite = maSite; }

    public int getMaHang() { return maHang; }
    public void setMaHang(int maHang) { this.maHang = maHang; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    public LocalDateTime getThoiGianCapNhat() { return thoiGianCapNhat; }
    public void setThoiGianCapNhat(LocalDateTime thoiGianCapNhat) { this.thoiGianCapNhat = thoiGianCapNhat; }

    @Override
    public String toString() {
        return "Site: " + maSite + " | Hàng: " + maHang + " | SL: " + soLuong + " | Cập nhật: " + thoiGianCapNhat;
    }
}
