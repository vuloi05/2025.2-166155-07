// Tac gia    : Nguyen Duc Toan - 20235846
package domainmodel;

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
