// ============================================================
// Tac gia    : Nguyen Duc Toan - 20235846
// Ten file   : MatHangDonHang.java
// Goi        : domainmodel (tang DomainModel)
// Mo ta      : Lop thuc the <<entity>> dai dien cho mot dong mat hang
//              nam trong mot Don hang. Khop Bieu do lop thiet ke BT6.
// Phu thuoc  : (khong)
// ============================================================
package domainmodel;

/**
 * Thuc the Mat hang trong don hang (mot dong trong bang chi tiet).
 *
 * <p>Tuong ung class &lt;&lt;entity&gt;&gt; MatHangDonHang trong Bieu do lop BT6.
 * Thuoc tinh: maMatHang, tenMatHang, soLuong, donVi, phuongTienVC.</p>
 *
 * <p>Cac truong nay khop bang "Danh sach mat hang" o Man hinh 3 (muc 7.8 SRS):
 * Ma hang, Ten mat hang, So luong dat, Don vi, Phuong tien VT.</p>
 */
public class MatHangDonHang {

    private String maMatHang;
    private String tenMatHang;
    private int soLuong;
    private String donVi;
    private String phuongTienVC;

    public MatHangDonHang() {
    }

    public MatHangDonHang(String maMatHang, String tenMatHang, int soLuong,
                          String donVi, String phuongTienVC) {
        this.maMatHang = maMatHang;
        this.tenMatHang = tenMatHang;
        this.soLuong = soLuong;
        this.donVi = donVi;
        this.phuongTienVC = phuongTienVC;
    }

    public String getMaMatHang() {
        return maMatHang;
    }

    public void setMaMatHang(String maMatHang) {
        this.maMatHang = maMatHang;
    }

    public String getTenMatHang() {
        return tenMatHang;
    }

    public void setTenMatHang(String tenMatHang) {
        this.tenMatHang = tenMatHang;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getDonVi() {
        return donVi;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
    }

    public String getPhuongTienVC() {
        return phuongTienVC;
    }

    public void setPhuongTienVC(String phuongTienVC) {
        this.phuongTienVC = phuongTienVC;
    }
}
