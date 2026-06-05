package entity;

import java.util.List;

/**
 * Lớp thực thể đại diện cho Yêu cầu kiểm tra tồn kho.
 * Tương ứng với class <<entity>> YeuCauKiemTra trong Class Diagram.
 *
 * Thuộc tính: idYeuCau, trangThai
 * Phương thức: getChiTietYeuCau(), capNhatTrangThai()
 */
public class YeuCauKiemTra {

    private int idYeuCau;
    private String trangThai; // "CHO_PHAN_HOI", "DA_PHAN_HOI"
    private int maSite;
    private List<MatHangYeuCau> danhSachMatHang;

    public YeuCauKiemTra() {}

    public YeuCauKiemTra(int idYeuCau, String trangThai, int maSite, List<MatHangYeuCau> danhSachMatHang) {
        this.idYeuCau = idYeuCau;
        this.trangThai = trangThai;
        this.maSite = maSite;
        this.danhSachMatHang = danhSachMatHang;
    }

    /**
     * Lấy chi tiết danh sách mặt hàng của yêu cầu này.
     * Tương ứng: getChiTietYeuCau() : void trong Class Diagram.
     */
    public List<MatHangYeuCau> getChiTietYeuCau() {
        return this.danhSachMatHang;
    }

    /**
     * Cập nhật trạng thái yêu cầu.
     * Tương ứng: capNhatTrangThai() : void trong Class Diagram.
     */
    public void capNhatTrangThai(String trangThaiMoi) {
        this.trangThai = trangThaiMoi;
    }

    // Getters & Setters
    public int getIdYeuCau() { return idYeuCau; }
    public void setIdYeuCau(int idYeuCau) { this.idYeuCau = idYeuCau; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    public int getMaSite() { return maSite; }
    public void setMaSite(int maSite) { this.maSite = maSite; }

    public List<MatHangYeuCau> getDanhSachMatHang() { return danhSachMatHang; }
    public void setDanhSachMatHang(List<MatHangYeuCau> danhSachMatHang) { this.danhSachMatHang = danhSachMatHang; }

    public int getSoMatHang() {
        return danhSachMatHang != null ? danhSachMatHang.size() : 0;
    }

    @Override
    public String toString() {
        return "YC-" + idYeuCau + " | Site: " + maSite + " | Trạng thái: " + trangThai + " | Số MH: " + getSoMatHang();
    }
}
