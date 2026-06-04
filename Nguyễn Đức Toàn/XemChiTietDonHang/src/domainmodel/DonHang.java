// ============================================================
// Tac gia    : Nguyen Duc Toan - 20235846
// Ten file   : DonHang.java
// Goi        : domainmodel (tang DomainModel)
// Mo ta      : Lop thuc the <<entity>> dai dien cho mot Don hang.
//              Khop "Bieu do lop muc thiet ke BT6" (muc 13 SRS):
//              entity giu du lieu + trang thai, KHONG truy xuat CSDL.
// Phu thuoc  : java.util.Date
// ============================================================
package domainmodel;

import java.util.Date;

/**
 * Thuc the Don hang (UC006 - Xem chi tiet don hang).
 *
 * <p>Tuong ung class &lt;&lt;entity&gt;&gt; DonHang trong Bieu do lop thiet ke BT6:</p>
 * <ul>
 *   <li>Thuoc tinh: maDonHang, maSite, tenSite, soLuongMatHang,
 *       phuongTienVC, ngayTao, trangThai</li>
 *   <li>Hanh vi: laDaHuy() — Information Expert (GRASP): entity la chuyen gia
 *       ve trang thai cua chinh no, nen tu tra loi "da bi huy hay chua".</li>
 * </ul>
 *
 * <p>BT6 da BO phuong thuc layDSMatHang() khoi entity (viec truy xuat danh sach
 * mat hang da chuyen sang MatHangDonHangDAO).</p>
 */
public class DonHang {

    /** Ma trang thai don hang da bi huy (dung cho laDaHuy()). */
    public static final String TRANG_THAI_DA_HUY = "DA_HUY";

    private String maDonHang;
    private String maSite;
    private String tenSite;
    private int soLuongMatHang;
    private String phuongTienVC;
    private Date ngayTao;
    private Date ngayGui;
    private String trangThai;

    public DonHang() {
    }

    public DonHang(String maDonHang, String maSite, String tenSite, int soLuongMatHang,
                   String phuongTienVC, Date ngayTao, Date ngayGui, String trangThai) {
        this.maDonHang = maDonHang;
        this.maSite = maSite;
        this.tenSite = tenSite;
        this.soLuongMatHang = soLuongMatHang;
        this.phuongTienVC = phuongTienVC;
        this.ngayTao = ngayTao;
        this.ngayGui = ngayGui;
        this.trangThai = trangThai;
    }

    /**
     * Kiem tra don hang da bi huy hay chua.
     *
     * <p>Tuong ung hanh vi laDaHuy(): boolean trong Bieu do lop BT6.
     * Day la diem cai tien so voi BT5 (truoc do logic kiem tra trang thai
     * nam o Service - kiemTraTrangThai()). Theo Information Expert,
     * entity tu kiem tra trang thai cua chinh no.</p>
     *
     * @return true neu trang thai = "DA_HUY", nguoc lai false
     */
    public boolean laDaHuy() {
        return TRANG_THAI_DA_HUY.equals(this.trangThai);
    }

    public String getMaDonHang() {
        return maDonHang;
    }

    public void setMaDonHang(String maDonHang) {
        this.maDonHang = maDonHang;
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

    public int getSoLuongMatHang() {
        return soLuongMatHang;
    }

    public void setSoLuongMatHang(int soLuongMatHang) {
        this.soLuongMatHang = soLuongMatHang;
    }

    public String getPhuongTienVC() {
        return phuongTienVC;
    }

    public void setPhuongTienVC(String phuongTienVC) {
        this.phuongTienVC = phuongTienVC;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    /**
     * Ngay gui don toi Site.
     *
     * @return ngay gui; {@code null} neu don chua gui (Nhap / Dang xu ly)
     */
    public Date getNgayGui() {
        return ngayGui;
    }

    public void setNgayGui(Date ngayGui) {
        this.ngayGui = ngayGui;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
