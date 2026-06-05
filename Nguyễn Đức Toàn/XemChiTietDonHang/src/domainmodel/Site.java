// Tac gia    : Nguyen Duc Toan - 20235846
package domainmodel;

public class Site {

    public static final String PT_TAU = "TAU";

    public static final String PT_HANG_KHONG = "HANG_KHONG";

    private String maSite;
    private String tenSite;
    private int soNgayTau;
    private int soNgayMayBay;

    public Site() {
    }

    public Site(String maSite, String tenSite, int soNgayTau, int soNgayMayBay) {
        this.maSite = maSite;
        this.tenSite = tenSite;
        this.soNgayTau = soNgayTau;
        this.soNgayMayBay = soNgayMayBay;
    }

    public int layThoiGianVanChuyen(String phuongTien) {
        if (PT_TAU.equals(phuongTien)) {
            return soNgayTau;
        }
        return soNgayMayBay;
    }

    public String getMaSite() {
        return maSite;
    }

    public void setMaSite(String maSite) {
        this.maSite = maSite;
    }

    public String getTenSite() {
        return tenSite;
    }

    public void setTenSite(String tenSite) {
        this.tenSite = tenSite;
    }

    public int getSoNgayTau() {
        return soNgayTau;
    }

    public void setSoNgayTau(int soNgayTau) {
        this.soNgayTau = soNgayTau;
    }

    public int getSoNgayMayBay() {
        return soNgayMayBay;
    }

    public void setSoNgayMayBay(int soNgayMayBay) {
        this.soNgayMayBay = soNgayMayBay;
    }
}
