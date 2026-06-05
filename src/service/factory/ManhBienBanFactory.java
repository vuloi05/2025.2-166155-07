package service.factory;
import entity.BienBanDoiChieu;
import java.util.Date;

public class ManhBienBanFactory {
    public static BienBanDoiChieu taoBienBan(String loaiBienBan, String maDonHang, String nguoiThucHien) {
        String maBB = "BB-" + System.currentTimeMillis();
        if ("LUU_TAM".equals(loaiBienBan)) {
            return new BienBanDoiChieu(maBB, new Date(), nguoiThucHien, maDonHang, "Ban nhap tam thoi");
        } else if ("XAC_NHAN".equals(loaiBienBan)) {
            return new BienBanDoiChieu(maBB, new Date(), nguoiThucHien, maDonHang, "Bien ban chinh thuc khoa so");
        }
        return null;
    }
}