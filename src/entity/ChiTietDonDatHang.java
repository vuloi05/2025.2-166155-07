package entity;

public class ChiTietDonDatHang {
    private String maMatHang;
    private String tenMatHang;
    private int soLuongDatHang;

    public ChiTietDonDatHang(String maMatHang, String tenMatHang, int soLuongDatHang) {
        this.maMatHang = maMatHang;
        this.tenMatHang = tenMatHang;
        this.soLuongDatHang = soLuongDatHang;
    }

    public String getMaMatHang() { return maMatHang; }
    public String getTenMatHang() { return tenMatHang; }
    public int getSoLuongDatHang() { return soLuongDatHang; }
}