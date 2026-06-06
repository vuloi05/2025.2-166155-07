// Tac gia    : Nguyen Duc Toan - 20235846
package presentation;

import domainmodel.DonHang;
import domainmodel.Site;

public final class UiLabels {

    private UiLabels() {
    }

    public static String trangThai(String maTrangThai) {
        if (maTrangThai == null) {
            return "";
        }
        switch (maTrangThai) {
            case "NHAP":
                return "Nháp";
            case "DANG_XU_LY":
                return "Đang xử lý";
            case "DA_GUI":
                return "Đã xử lý";
            case DonHang.TRANG_THAI_DA_HUY:
                return "Đã hủy";
            default:
                return maTrangThai;
        }
    }

    public static String phuongTien(String maPhuongTien) {
        if (Site.PT_TAU.equals(maPhuongTien)) {
            return "Tàu";
        }
        if (Site.PT_HANG_KHONG.equals(maPhuongTien)) {
            return "Hàng không";
        }
        return maPhuongTien != null ? maPhuongTien : "";
    }
}
