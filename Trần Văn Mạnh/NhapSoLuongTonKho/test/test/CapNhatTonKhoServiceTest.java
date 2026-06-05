package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import dao.ThongTinTonKhoDAO;
import dao.YeuCauKiemTraDAO;
import entity.MatHangYeuCau;
import entity.ThongTinTonKho;
import entity.YeuCauKiemTra;
import service.CapNhatTonKhoService;
import service.CapNhatTonKhoService.KetQuaLuuTonKho;

/**
 * Kiểm thử tự động cho CapNhatTonKhoService (nghiệp vụ nhập tồn kho).
 *
 * Bao gồm:
 *   - Kiểm thử hộp đen (Black-box): Dựa trên đặc tả Use Case, kiểm tra input/output
 *   - Kiểm thử hộp trắng (White-box, C1): Bao phủ tất cả các nhánh quyết định
 *
 * Module được chọn kiểm thử: CapNhatTonKhoService.kiemTraHopLe() và xuLyLuuTonKho()
 */
public class CapNhatTonKhoServiceTest {

    private CapNhatTonKhoService service;
    private YeuCauKiemTraDAO yeuCauDAO;
    private ThongTinTonKhoDAO tonKhoDAO;

    private List<MatHangYeuCau> dsMatHang;

    @Before
    public void setUp() {
        yeuCauDAO = new YeuCauKiemTraDAO();
        tonKhoDAO = new ThongTinTonKhoDAO();
        service = new CapNhatTonKhoService(yeuCauDAO, tonKhoDAO);

        // Sample item list for validation tests
        dsMatHang = new ArrayList<>(Arrays.asList(
                new MatHangYeuCau(1001, "Linh kiện IC-7805", "Cái"),
                new MatHangYeuCau(1002, "Tụ điện 100uF", "Cái")
        ));
    }

    // ========================================================================
    // KIỂM THỬ HỘP ĐEN (Black-box Testing)
    // Dựa trên Luồng sự kiện chính & luồng thay thế trong Use Case Specification
    // ========================================================================

    /**
     * TC01 - Luồng chính: Nhập số lượng hợp lệ, lưu thành công.
     * Input: Số lượng dương cho tất cả mặt hàng
     * Expected: Validation OK, lưu thành công, trạng thái = "DA_PHAN_HOI"
     */
    @Test
    public void testTC01_NhapHopLeLuuThanhCong() {
        Map<Integer, String> danhSachNhap = new LinkedHashMap<>();
        danhSachNhap.put(1001, "150");
        danhSachNhap.put(1002, "200");

        // Validate
        KetQuaLuuTonKho kq = service.kiemTraHopLe(danhSachNhap, dsMatHang);
        assertTrue("Dữ liệu hợp lệ phải pass validation", kq.isHopLe());

        // Save
        KetQuaLuuTonKho result = service.xuLyLuuTonKho(danhSachNhap, 1, 101, dsMatHang);
        assertTrue("Lưu phải thành công", result.isLuuThanhCong());

        // Verify status updated
        YeuCauKiemTra yc = yeuCauDAO.findById(1);
        assertEquals("Trạng thái phải chuyển sang DA_PHAN_HOI", "DA_PHAN_HOI", yc.getTrangThai());
    }

    /**
     * TC02 - Nhập số lượng = 0 (hợp lệ — mặt hàng không có tồn kho).
     * Input: soLuong = "0"
     * Expected: Validation OK
     */
    @Test
    public void testTC02_NhapSoLuongBangKhong() {
        Map<Integer, String> danhSachNhap = new LinkedHashMap<>();
        danhSachNhap.put(1001, "0");
        danhSachNhap.put(1002, "0");

        KetQuaLuuTonKho kq = service.kiemTraHopLe(danhSachNhap, dsMatHang);
        assertTrue("Số lượng = 0 là hợp lệ (mặt hàng không có tồn kho)", kq.isHopLe());
    }

    /**
     * TC03 - Luồng thay thế: Nhập số âm.
     * Input: soLuong = "-5"
     * Expected: Validation FAIL với thông báo lỗi cụ thể
     */
    @Test
    public void testTC03_NhapSoAm() {
        Map<Integer, String> danhSachNhap = new LinkedHashMap<>();
        danhSachNhap.put(1001, "-5");
        danhSachNhap.put(1002, "100");

        KetQuaLuuTonKho kq = service.kiemTraHopLe(danhSachNhap, dsMatHang);
        assertFalse("Số âm phải bị từ chối", kq.isHopLe());
        assertTrue("Thông báo lỗi phải chứa tên mặt hàng",
                kq.getThongBao().contains("Linh kiện IC-7805"));
    }

    /**
     * TC04 - Luồng thay thế: Nhập không phải số (chữ).
     * Input: soLuong = "abc"
     * Expected: Validation FAIL
     */
    @Test
    public void testTC04_NhapChuKhongPhaiSo() {
        Map<Integer, String> danhSachNhap = new LinkedHashMap<>();
        danhSachNhap.put(1001, "abc");
        danhSachNhap.put(1002, "100");

        KetQuaLuuTonKho kq = service.kiemTraHopLe(danhSachNhap, dsMatHang);
        assertFalse("Giá trị chữ phải bị từ chối", kq.isHopLe());
        assertTrue("Thông báo lỗi phải chứa giá trị đã nhập",
                kq.getThongBao().contains("abc"));
    }

    /**
     * TC05 - Luồng thay thế: Bỏ trống ô nhập.
     * Input: soLuong = ""
     * Expected: Validation FAIL
     */
    @Test
    public void testTC05_BoTrongONhap() {
        Map<Integer, String> danhSachNhap = new LinkedHashMap<>();
        danhSachNhap.put(1001, "");
        danhSachNhap.put(1002, "100");

        KetQuaLuuTonKho kq = service.kiemTraHopLe(danhSachNhap, dsMatHang);
        assertFalse("Ô trống phải bị từ chối", kq.isHopLe());
        assertTrue("Thông báo lỗi phải nói rõ chưa nhập",
                kq.getThongBao().contains("chưa được nhập"));
    }

    /**
     * TC06 - Kiểm tra danh sách yêu cầu chờ phản hồi.
     * Expected: Chỉ trả về yêu cầu có trạng thái "CHO_PHAN_HOI"
     */
    @Test
    public void testTC06_LayDSYeuCauChoPhanHoi() {
        List<YeuCauKiemTra> dsYC = service.layDSYeuCauChoPhanHoi();

        assertNotNull("DS yêu cầu không được null", dsYC);
        assertFalse("DS yêu cầu không được rỗng", dsYC.isEmpty());

        for (YeuCauKiemTra yc : dsYC) {
            assertEquals("Chỉ trả về yêu cầu chờ phản hồi",
                    "CHO_PHAN_HOI", yc.getTrangThai());
        }
    }

    /**
     * TC07 - Lấy chi tiết yêu cầu không tồn tại.
     * Input: ID = 999
     * Expected: Trả về null
     */
    @Test
    public void testTC07_YeuCauKhongTonTai() {
        YeuCauKiemTra yc = service.layChiTietYeuCau(999);
        assertNull("Phải trả về null khi yêu cầu không tồn tại", yc);
    }

    /**
     * TC08 - Kiểm tra dữ liệu tồn kho được lưu đúng số lượng bản ghi.
     * Input: 2 mặt hàng
     * Expected: DAO lưu đúng 2 bản ghi
     */
    @Test
    public void testTC08_SoLuongBanGhiDuocLuu() {
        Map<Integer, String> danhSachNhap = new LinkedHashMap<>();
        danhSachNhap.put(1001, "100");
        danhSachNhap.put(1002, "200");

        service.xuLyLuuTonKho(danhSachNhap, 2, 102, dsMatHang);

        List<ThongTinTonKho> saved = tonKhoDAO.findAll();
        assertEquals("Phải lưu đúng 2 bản ghi tồn kho", 2, saved.size());
    }

    // ========================================================================
    // KIỂM THỬ HỘP TRẮNG (White-box Testing, C1 - Branch Coverage)
    // Bao phủ các nhánh quyết định trong kiemTraHopLe() và xuLyLuuTonKho()
    // ========================================================================

    /**
     * TC-WB01 - Nhánh: danhSachNhap == null.
     * Bao phủ: if (danhSachNhap == null || danhSachNhap.isEmpty()) --> TRUE
     */
    @Test
    public void testWB01_DanhSachNull() {
        KetQuaLuuTonKho kq = service.kiemTraHopLe(null, dsMatHang);
        assertFalse("Null input phải bị từ chối", kq.isHopLe());
    }

    /**
     * TC-WB02 - Nhánh: danhSachNhap rỗng.
     * Bao phủ: if (danhSachNhap == null || danhSachNhap.isEmpty()) --> TRUE
     */
    @Test
    public void testWB02_DanhSachRong() {
        Map<Integer, String> danhSachNhap = new HashMap<>();
        KetQuaLuuTonKho kq = service.kiemTraHopLe(danhSachNhap, dsMatHang);
        assertFalse("Danh sách rỗng phải bị từ chối", kq.isHopLe());
    }

    /**
     * TC-WB03 - Nhánh: giaTriNhap chỉ chứa khoảng trắng.
     * Bao phủ: if (giaTriNhap == null || giaTriNhap.trim().isEmpty()) --> TRUE
     */
    @Test
    public void testWB03_ChiChuaKhoangTrang() {
        Map<Integer, String> danhSachNhap = new LinkedHashMap<>();
        danhSachNhap.put(1001, "   ");
        danhSachNhap.put(1002, "100");

        KetQuaLuuTonKho kq = service.kiemTraHopLe(danhSachNhap, dsMatHang);
        assertFalse("Khoảng trắng phải bị từ chối", kq.isHopLe());
    }

    /**
     * TC-WB04 - Nhánh: soLuong < 0 (số âm).
     * Bao phủ: if (soLuong < 0) --> TRUE
     */
    @Test
    public void testWB04_SoAm() {
        Map<Integer, String> danhSachNhap = new LinkedHashMap<>();
        danhSachNhap.put(1001, "50");
        danhSachNhap.put(1002, "-1");

        KetQuaLuuTonKho kq = service.kiemTraHopLe(danhSachNhap, dsMatHang);
        assertFalse("Số âm phải bị từ chối", kq.isHopLe());
        assertTrue("Thông báo phải chứa tên mặt hàng bị lỗi",
                kq.getThongBao().contains("Tụ điện 100uF"));
    }

    /**
     * TC-WB05 - Nhánh: NumberFormatException khi parse.
     * Bao phủ: catch (NumberFormatException e) --> vào nhánh exception
     */
    @Test
    public void testWB05_NumberFormatException() {
        Map<Integer, String> danhSachNhap = new LinkedHashMap<>();
        danhSachNhap.put(1001, "12.5"); // Số thập phân, không phải integer
        danhSachNhap.put(1002, "100");

        KetQuaLuuTonKho kq = service.kiemTraHopLe(danhSachNhap, dsMatHang);
        assertFalse("Số thập phân phải bị từ chối khi yêu cầu integer", kq.isHopLe());
    }

    /**
     * TC-WB06 - Nhánh: lưu cho yêu cầu không tồn tại.
     * Bao phủ: if (yc != null) trong updateTrangThai --> FALSE
     */
    @Test
    public void testWB06_LuuChoYeuCauKhongTonTai() {
        Map<Integer, String> danhSachNhap = new LinkedHashMap<>();
        danhSachNhap.put(1001, "100");

        KetQuaLuuTonKho result = service.xuLyLuuTonKho(danhSachNhap, 999, 101, dsMatHang);
        assertFalse("Lưu cho yêu cầu không tồn tại phải trả về false", result.isLuuThanhCong());
    }

    /**
     * TC-WB07 - Nhánh: tất cả dữ liệu hợp lệ (happy path hoàn chỉnh).
     * Bao phủ: tất cả nhánh FALSE (không vào lỗi nào) --> return true
     */
    @Test
    public void testWB07_HappyPathHoanChinh() {
        Map<Integer, String> danhSachNhap = new LinkedHashMap<>();
        danhSachNhap.put(1001, "500");
        danhSachNhap.put(1002, "1000");

        // Validate
        KetQuaLuuTonKho kq = service.kiemTraHopLe(danhSachNhap, dsMatHang);
        assertTrue("Happy path phải hợp lệ", kq.isHopLe());
        assertEquals("Thông báo phải là 'Dữ liệu hợp lệ.'", "Dữ liệu hợp lệ.", kq.getThongBao());

        // Save
        KetQuaLuuTonKho saveResult = service.xuLyLuuTonKho(danhSachNhap, 4, 101, dsMatHang);
        assertTrue("Lưu phải thành công", saveResult.isLuuThanhCong());

        // Verify data was persisted correctly
        List<ThongTinTonKho> saved = tonKhoDAO.findAll();
        assertEquals("Phải lưu đúng 2 bản ghi", 2, saved.size());

        // Verify status changed
        YeuCauKiemTra yc = yeuCauDAO.findById(4);
        assertEquals("Trạng thái phải chuyển", "DA_PHAN_HOI", yc.getTrangThai());
    }
}
