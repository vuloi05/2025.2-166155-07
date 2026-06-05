package entity;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class DonDatHang {
    private String maDonHang;
    private Date ngayKhoiTaoDon;
    private String trangThaiDonHang;
    private List<ChiTietDonDatHang> dsChiTiet = new ArrayList<>();

    public DonDatHang(String maDonHang, Date ngayKhoiTaoDon, String trangThaiDonHang) {
        this.maDonHang = maDonHang;
        this.ngayKhoiTaoDon = ngayKhoiTaoDon;
        this.trangThaiDonHang = trangThaiDonHang;
    }

    public List<DonDatHang> timDonHangTheoTrangThai(String trangThai) {
        return new ArrayList<>();
    }

    public void capNhatTrangThaiDonHang(String trangThaiMoi) {
        this.trangThaiDonHang = trangThaiMoi;
    }

    public String getMaDonHang() { return maDonHang; }
    public String getTrangThaiDonHang() { return trangThaiDonHang; }
    public List<ChiTietDonDatHang> getDsChiTiet() { return dsChiTiet; }
    public void addChiTiet(ChiTietDonDatHang chiTiet) { this.dsChiTiet.add(chiTiet); }
}