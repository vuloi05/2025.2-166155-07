package entity;

/**
 * Lớp thực thể phụ trợ đại diện cho một mặt hàng cần báo cáo tồn kho.
 * Mỗi MatHangYeuCau thuộc về một YeuCauKiemTra.
 */
public class MatHangYeuCau {

    private int maHang;
    private String tenHang;
    private String donVi;

    public MatHangYeuCau() {}

    public MatHangYeuCau(int maHang, String tenHang, String donVi) {
        this.maHang = maHang;
        this.tenHang = tenHang;
        this.donVi = donVi;
    }

    // Getters & Setters
    public int getMaHang() { return maHang; }
    public void setMaHang(int maHang) { this.maHang = maHang; }

    public String getTenHang() { return tenHang; }
    public void setTenHang(String tenHang) { this.tenHang = tenHang; }

    public String getDonVi() { return donVi; }
    public void setDonVi(String donVi) { this.donVi = donVi; }

    @Override
    public String toString() {
        return maHang + " - " + tenHang + " (" + donVi + ")";
    }
}
