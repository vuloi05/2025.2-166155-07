// Tac gia    : Nguyen Duc Toan - 20235846
package domainmodel;

import java.util.Date;

public class DonHang {

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
