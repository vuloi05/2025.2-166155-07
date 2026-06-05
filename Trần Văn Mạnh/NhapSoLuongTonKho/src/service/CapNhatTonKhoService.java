package service;

import dao.ThongTinTonKhoDAO;
import dao.YeuCauKiemTraDAO;
import entity.MatHangYeuCau;
import entity.ThongTinTonKho;
import entity.YeuCauKiemTra;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Lớp xử lý nghiệp vụ chính cho chức năng Nhập số lượng tồn kho.
 *
 * Phương thức:
 *   + layDSYeuCauChoPhanHoi() : List
 *   + layChiTietYeuCau(idYeuCau) : YeuCauKiemTra
 *   + kiemTraHopLe(danhSachNhap) : KetQuaLuuTonKho
 *   + xuLyLuuTonKho(danhSachNhap, idYeuCau, maSite) : KetQuaLuuTonKho
 */
public class CapNhatTonKhoService {

    private YeuCauKiemTraDAO yeuCauDAO;
    private ThongTinTonKhoDAO tonKhoDAO;

    public CapNhatTonKhoService() {
        this.yeuCauDAO = new YeuCauKiemTraDAO();
        this.tonKhoDAO = new ThongTinTonKhoDAO();
    }

    // Constructor cho phép inject DAO (hỗ trợ Unit Test)
    public CapNhatTonKhoService(YeuCauKiemTraDAO yeuCauDAO, ThongTinTonKhoDAO tonKhoDAO) {
        this.yeuCauDAO = yeuCauDAO;
        this.tonKhoDAO = tonKhoDAO;
    }

    /**
     * Lấy danh sách yêu cầu đang chờ phản hồi (trạng thái = "CHO_PHAN_HOI").
     */
    public List<YeuCauKiemTra> layDSYeuCauChoPhanHoi() {
        return yeuCauDAO.findByTrangThai("CHO_PHAN_HOI");
    }

    /**
     * Lấy chi tiết một yêu cầu kiểm tra theo ID.
     * Tương ứng Sequence Diagram: layThongTinYeuCau(idYeuCau) -> getChiTietYeuCau()
     */
    public YeuCauKiemTra layChiTietYeuCau(int idYeuCau) {
        return yeuCauDAO.findById(idYeuCau);
    }

    /**
     * Kiểm tra tính hợp lệ của dữ liệu nhập.
     * Được gọi nội bộ bởi xuLyLuuTonKho() theo đúng Sequence Diagram.
     *
     * Validation rules:
     *   1. Danh sách không được null/rỗng
     *   2. soLuong phải là số nguyên >= 0
     *   3. soLuong không được để trống
     *
     * @param danhSachNhap Map<maHang, soLuongNhap> — dữ liệu Site đã nhập
     * @param dsMatHang danh sách mặt hàng gốc từ yêu cầu
     * @return KetQuaLuuTonKho chứa trạng thái hợp lệ và thông báo lỗi
     */
    public KetQuaLuuTonKho kiemTraHopLe(Map<Integer, String> danhSachNhap, List<MatHangYeuCau> dsMatHang) {
        if (danhSachNhap == null || danhSachNhap.isEmpty()) {
            return new KetQuaLuuTonKho(false, "Danh sách nhập không được rỗng.");
        }

        for (MatHangYeuCau mh : dsMatHang) {
            String giaTriNhap = danhSachNhap.get(mh.getMaHang());

            if (giaTriNhap == null || giaTriNhap.trim().isEmpty()) {
                return new KetQuaLuuTonKho(false,
                        "Mặt hàng \"" + mh.getTenHang() + "\" chưa được nhập số lượng.");
            }

            try {
                int soLuong = Integer.parseInt(giaTriNhap.trim());
                if (soLuong < 0) {
                    return new KetQuaLuuTonKho(false,
                            "Mặt hàng \"" + mh.getTenHang() + "\": số lượng không được âm (đã nhập: " + soLuong + ").");
                }
            } catch (NumberFormatException e) {
                return new KetQuaLuuTonKho(false,
                        "Mặt hàng \"" + mh.getTenHang() + "\": giá trị \"" + giaTriNhap + "\" không phải số nguyên hợp lệ.");
            }
        }

        return new KetQuaLuuTonKho(true, "Dữ liệu hợp lệ.");
    }

    /**
     * Xử lý lưu tồn kho và cập nhật trạng thái yêu cầu.
     * Tương ứng Sequence Diagram (SD_ChiTietLuuTonKho):
     *   View -> xuLyLuuTonKho(danhSachNhap)
     *     -> kiemTraHopLe()          [validate nội bộ]
     *     -> taoMoiTonKho(maHang, soLuong)
     *     -> capNhatTrangThai("Đã phản hồi")
     *     <- traVeKetQua
     *
     * @param danhSachNhap Map<maHang, soLuongNhap>
     * @param idYeuCau ID của yêu cầu
     * @param maSite mã Site đang phản hồi
     * @param dsMatHang danh sách mặt hàng gốc từ yêu cầu (dùng để validate)
     * @return KetQuaLuuTonKho — chứa trạng thái hợp lệ, thông báo lỗi/thành công
     */
    public KetQuaLuuTonKho xuLyLuuTonKho(Map<Integer, String> danhSachNhap, int idYeuCau,
                                          int maSite, List<MatHangYeuCau> dsMatHang) {
        // Step 3 in SD: kiemTraHopLe() — validate internally
        KetQuaLuuTonKho validationResult = kiemTraHopLe(danhSachNhap, dsMatHang);
        if (!validationResult.isHopLe()) {
            return validationResult; // thongBaoLoiDinhDang -> yeuCauNhapLai
        }

        // Step 4 in SD: taoMoiTonKho(maHang, soLuong) for each item
        List<ThongTinTonKho> dsTonKhoMoi = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : danhSachNhap.entrySet()) {
            int maHang = entry.getKey();
            int soLuong = Integer.parseInt(entry.getValue().trim());
            ThongTinTonKho tonKho = ThongTinTonKho.taoMoiTonKho(maSite, maHang, soLuong);
            dsTonKhoMoi.add(tonKho);
        }
        tonKhoDAO.saveAll(dsTonKhoMoi);

        // Step 2.1 in SD: capNhatTrangThai("Đã phản hồi")
        boolean updated = yeuCauDAO.updateTrangThai(idYeuCau, "DA_PHAN_HOI");

        System.out.println("[CapNhatTonKhoService] Đã lưu " + dsTonKhoMoi.size()
                + " bản ghi tồn kho cho yêu cầu " + idYeuCau);

        if (updated) {
            return new KetQuaLuuTonKho(true, true, "Cập nhật tồn kho thành công.");
        } else {
            return new KetQuaLuuTonKho(true, false, "Lỗi khi cập nhật trạng thái yêu cầu.");
        }
    }

    /**
     * Gửi thông báo đến Bộ phận đặt hàng quốc tế sau khi Site phản hồi tồn kho.
     * Tương ứng: Bước 8 trong đặc tả Use Case "Nhập số lượng tồn kho".
     * (Mock: ghi log — trong hệ thống thực sẽ gửi event/message đến module đặt hàng)
     *
     * @param idYeuCau ID yêu cầu đã được phản hồi
     * @param maSite   mã Site vừa cung cấp thông tin tồn kho
     */
    public void guiThongBaoDenBoPhanDatHang(int idYeuCau, int maSite) {
        System.out.println("[CapNhatTonKhoService] Thông báo: Site " + maSite
                + " đã phản hồi tồn kho cho yêu cầu " + idYeuCau
                + " → Gửi thông báo đến Bộ phận đặt hàng quốc tế.");
    }

    // ========================================================================
    // INNER CLASS — Kết quả lưu tồn kho (gộp validation + save result)
    // ========================================================================

    /**
     * Value object thống nhất chứa kết quả của toàn bộ quá trình:
     * validate -> lưu -> cập nhật trạng thái.
     * Cho phép View hiển thị đúng thông báo mà không cần biết chi tiết nghiệp vụ.
     */
    public static class KetQuaLuuTonKho {
        private final boolean hopLe;         // dữ liệu đầu vào có hợp lệ không
        private final boolean luuThanhCong;  // đã lưu DB thành công chưa
        private final String thongBao;       // thông báo lỗi hoặc thành công

        /** Dùng cho trường hợp validation thất bại (chưa đến bước lưu). */
        public KetQuaLuuTonKho(boolean hopLe, String thongBao) {
            this.hopLe = hopLe;
            this.luuThanhCong = false;
            this.thongBao = thongBao;
        }

        /** Dùng cho trường hợp đã qua validation, trả về kết quả lưu. */
        public KetQuaLuuTonKho(boolean hopLe, boolean luuThanhCong, String thongBao) {
            this.hopLe = hopLe;
            this.luuThanhCong = luuThanhCong;
            this.thongBao = thongBao;
        }

        public boolean isHopLe()        { return hopLe; }
        public boolean isLuuThanhCong() { return luuThanhCong; }
        public String getThongBao()     { return thongBao; }
    }
}
