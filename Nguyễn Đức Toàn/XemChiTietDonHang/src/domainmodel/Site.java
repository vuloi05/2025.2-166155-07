// ============================================================
// Tac gia    : Nguyen Duc Toan - 20235846
// Ten file   : Site.java
// Goi        : domainmodel (tang DomainModel)
// Mo ta      : Lop thuc the <<entity>> dai dien cho mot Site (doi tac
//              nhap khau). Khop Bieu do lop thiet ke BT6.
// Phu thuoc  : (khong)
// ============================================================
package domainmodel;

/**
 * Thuc the Site nhap khau.
 *
 * <p>Tuong ung class &lt;&lt;entity&gt;&gt; Site trong Bieu do lop BT6.
 * Thuoc tinh: maSite, tenSite, soNgayTau, soNgayMayBay.
 * Hanh vi: layThoiGianVanChuyen(phuongTien) — Information Expert: Site
 * la chuyen gia ve thoi gian van chuyen cua chinh no.</p>
 */
public class Site {

    /** Ma phuong tien van chuyen bang tau. */
    public static final String PT_TAU = "TAU";

    /** Ma phuong tien van chuyen bang hang khong. */
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

    /**
     * Lay so ngay van chuyen tuong ung voi phuong tien.
     *
     * <p>Tuong ung hanh vi layThoiGianVanChuyen(phuongTien): int trong
     * Bieu do lop BT6. Dung de hien thi "So ngay van chuyen" o Man hinh
     * chi tiet (muc 7.8 SRS).</p>
     *
     * @param phuongTien ma phuong tien ({@link #PT_TAU} hoac {@link #PT_HANG_KHONG})
     * @return so ngay giao bang tau neu phuongTien la TAU, nguoc lai so ngay giao
     *         bang hang khong
     */
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
