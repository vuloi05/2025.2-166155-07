package controller;

import entity.MatHangYeuCau;
import entity.YeuCauKiemTra;
import service.CapNhatTonKhoService;
import service.CapNhatTonKhoService.KetQuaLuuTonKho;

import java.util.List;
import java.util.Map;

/**
 * Lớp điều khiển - Nhận yêu cầu từ View, gọi Service xử lý, trả kết quả về View.
 * Tương ứng với class <<control>> CapNhatTonKhoController trong Class Diagram.
 *
 * Phương thức:
 *   + layThongTinYeuCau() : void
 *   + xuLyLuuTonKho() : void
 *   + kiemTraHopLe() : void
 */
public class CapNhatTonKhoController {

    private CapNhatTonKhoService service;

    public CapNhatTonKhoController() {
        this.service = new CapNhatTonKhoService();
    }

    public CapNhatTonKhoController(CapNhatTonKhoService service) {
        this.service = service;
    }

    /**
     * Lấy danh sách yêu cầu chờ phản hồi.
     */
    public List<YeuCauKiemTra> layDSYeuCauChoPhanHoi() {
        return service.layDSYeuCauChoPhanHoi();
    }

    /**
     * Lấy chi tiết yêu cầu kiểm tra.
     * Tương ứng: layThongTinYeuCau(idYeuCau) trong Sequence Diagram.
     */
    public YeuCauKiemTra layThongTinYeuCau(int idYeuCau) {
        return service.layChiTietYeuCau(idYeuCau);
    }

    /**
     * Xử lý lưu tồn kho — validation được thực hiện nội bộ trong service.
     * Tương ứng: xuLyLuuTonKho(danhSachNhap) trong Sequence Diagram (SD_ChiTietLuuTonKho).
     * View chỉ gọi một lần duy nhất; service tự validate trước khi lưu.
     *
     * @return KetQuaLuuTonKho — chứa trạng thái hợp lệ, kết quả lưu, và thông báo
     */
    public KetQuaLuuTonKho xuLyLuuTonKho(Map<Integer, String> danhSachNhap,
                                          int idYeuCau, int maSite,
                                          List<MatHangYeuCau> dsMatHang) {
        return service.xuLyLuuTonKho(danhSachNhap, idYeuCau, maSite, dsMatHang);
    }

    /**
     * Gửi thông báo đến Bộ phận đặt hàng quốc tế sau khi lưu thành công.
     * Tương ứng: Bước 8 trong đặc tả Use Case.
     */
    public void guiThongBaoDenBoPhanDatHang(int idYeuCau, int maSite) {
        service.guiThongBaoDenBoPhanDatHang(idYeuCau, maSite);
    }
}
