package entity;
import service.strategy.XuLyTrangThaiStrategy;

public class ChiTietDoiChieu {
    private String maMatHang;
    private int soLuongThucNhan;
    private int soLuongChenhLech;
    private String tinhTrangVatLy;
    private String soSerialLot;
    private String ketQuaTrangThai;
    private String lyDoChenhLech;
    
    private XuLyTrangThaiStrategy strategy;

    public ChiTietDoiChieu(String maMatHang, int soLuongThucNhan, String tinhTrangVatLy, String soSerialLot) {
        this.maMatHang = maMatHang;
        this.soLuongThucNhan = soLuongThucNhan;
        this.tinhTrangVatLy = tinhTrangVatLy;
        this.soSerialLot = soSerialLot;
        this.lyDoChenhLech = "";
    }

    public int khoiTaoVaTinhToan(String maMatHang, int soLuongThucNhan, int soLuongDat) {
        this.soLuongChenhLech = tinhToanSoLuongChenhLech(soLuongThucNhan, soLuongDat);
        return this.soLuongChenhLech;
    }

    public int tinhToanSoLuongChenhLech(int thucNhan, int datHang) {
        return thucNhan - datHang;
    }

    public String tuDongXacDinhKetQuaTrangThai() {
        if (strategy != null) {
            this.ketQuaTrangThai = strategy.xacDinhTrangThai(this.soLuongChenhLech);
        }
        return this.ketQuaTrangThai;
    }

    public void setStrategy(XuLyTrangThaiStrategy strategy) { this.strategy = strategy; }
    public void setLyDoChenhLech(String lyDoChenhLech) { this.lyDoChenhLech = lyDoChenhLech; }
    public String getMaMatHang() { return maMatHang; }
    public int getSoLuongThucNhan() { return soLuongThucNhan; } // HÀM MỚI BỔ SUNG Ở ĐÂY
    public int getSoLuongChenhLech() { return soLuongChenhLech; }
    public String getKetQuaTrangThai() { return ketQuaTrangThai; }
    public String getSoSerialLot() { return soSerialLot; }
}