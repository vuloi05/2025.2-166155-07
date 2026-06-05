package controller;
import entity.*;
import service.factory.ManhBienBanFactory;
import service.strategy.*;
import java.util.*;

public class BoDieuKhienDoiChieu {
    private static BoDieuKhienDoiChieu instance;
    private List<ChiTietDoiChieu> vungNhoTam = new ArrayList<ChiTietDoiChieu>();

    private BoDieuKhienDoiChieu() {}

    public static synchronized BoDieuKhienDoiChieu getInstance() {
        if (instance == null) {
            instance = new BoDieuKhienDoiChieu();
        }
        return instance;
    }

    public boolean thucThiKiemTraTinhHopLeMaDinhDanh(String soSerialLot) {
        if (soSerialLot == null || soSerialLot.trim().isEmpty() || soSerialLot.contains("ERR")) {
            return false;
        }
        return true;
    }

    public List<ChiTietDoiChieu> xuLyTinhToanDoiChieu(DonDatHang donHang, List<ChiTietDoiChieu> duLieuNhap) {
        vungNhoTam.clear();
        for (ChiTietDoiChieu dongNhap : duLieuNhap) {
            for (ChiTietDonDatHang dongDat : donHang.getDsChiTiet()) {
                if (dongNhap.getMaMatHang().equals(dongDat.getMaMatHang())) {
                    // ĐÃ SỬA: Thay getSoLuongChenhLech() thành getSoLuongThucNhan() để tính toán chính xác
                    int lech = dongNhap.khoiTaoVaTinhToan(dongNhap.getMaMatHang(), dongNhap.getSoLuongThucNhan(), dongDat.getSoLuongDatHang());
                    
                    if (lech == 0) dongNhap.setStrategy(new ManhXuLyKhopStrategy());
                    else if (lech < 0) dongNhap.setStrategy(new ManhXuLyThieuStrategy());
                    else dongNhap.setStrategy(new ManhXuLyThuaStrategy());
                    
                    dongNhap.tuDongXacDinhKetQuaTrangThai();
                    vungNhoTam.add(dongNhap);
                }
            }
        }
        return vungNhoTam;
    }

    public BienBanDoiChieu xacNhanNhapKho(String maDonHang, String nguoiDung) {
        BienBanDoiChieu bb = ManhBienBanFactory.taoBienBan("XAC_NHAN", maDonHang, nguoiDung);
        bb.setBangChiTiet(new ArrayList<ChiTietDoiChieu>(vungNhoTam));
        vungNhoTam.clear();
        return bb;
    }

    public BienBanDoiChieu xuLyLuuTamBanNhap(String maDonHang, String nguoiDung) {
        BienBanDoiChieu bb = ManhBienBanFactory.taoBienBan("LUU_TAM", maDonHang, nguoiDung);
        bb.setBangChiTiet(new ArrayList<ChiTietDoiChieu>(vungNhoTam));
        return bb;
    }

    public void giaiPhongVungNhoTam() {
        vungNhoTam.clear();
    }
}