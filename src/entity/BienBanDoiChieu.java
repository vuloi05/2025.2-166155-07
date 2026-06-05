package entity;
import java.util.Date;
import java.util.List;

public class BienBanDoiChieu {
    private String maBienBanDoiChieu;
    private Date thoiGianDoiChieu;
    private String nguoiThucHien;
    private String maDonHangDoiChieu;
    private String ghiChuChungCuaBienBan;
    private List<ChiTietDoiChieu> bangChiTiet;

    public BienBanDoiChieu() {}

    public BienBanDoiChieu(String maBienBanDoiChieu, Date thoiGianDoiChieu, String nguoiThucHien, String maDonHangDoiChieu, String ghiChuChungCuaBienBan) {
        this.maBienBanDoiChieu = maBienBanDoiChieu;
        this.thoiGianDoiChieu = thoiGianDoiChieu;
        this.nguoiThucHien = nguoiThucHien;
        this.maDonHangDoiChieu = maDonHangDoiChieu;
        this.ghiChuChungCuaBienBan = ghiChuChungCuaBienBan;
    }

    public void setBangChiTiet(List<ChiTietDoiChieu> bangChiTiet) {
        this.bangChiTiet = bangChiTiet;
    }

    public String getMaBienBanDoiChieu() { return maBienBanDoiChieu; }
    public List<ChiTietDoiChieu> getBangChiTiet() { return bangChiTiet; }
}