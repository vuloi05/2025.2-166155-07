package test; // Giữ nguyên package test của nhóm

// Đổi toàn bộ import sang chuẩn JUnit 4
import static org.junit.Assert.*; 
import org.junit.Before;          // JUnit 4 dùng @Before thay vì @BeforeEach
import org.junit.Test;            // JUnit 4 dùng org.junit.Test

import controller.BoDieuKhienDoiChieu;
import entity.*;
import java.util.*;

public class BoDieuKhienDoiChieuTest {
    private BoDieuKhienDoiChieu controller;
    private DonDatHang donHangMau;

    @Before // Cú pháp JUnit 4 thiết lập dữ liệu trước khi test
    public void setUp() {
        controller = BoDieuKhienDoiChieu.getInstance();
        donHangMau = new DonDatHang("DH-007", new Date(), "CHO_VE_KHO");
        donHangMau.addChiTiet(new ChiTietDonDatHang("MH-XYZ", "Mat Hang Thang", 100));
    }

    @Test // Ca kiểm thữ hộp đen giá trị biên
    public void testKiemTraMaDinhDanh() {
        assertTrue(controller.thucThiKiemTraTinhHopLeMaDinhDanh("SERIAL-OK-111"));
        assertFalse(controller.thucThiKiemTraTinhHopLeMaDinhDanh("ERR_TRUNG_LAP"));
        assertFalse(controller.thucThiKiemTraTinhHopLeMaDinhDanh(""));
    }

    @Test // Ca kiểm thử hộp trắng độ bao phủ nhánh C1 - Luồng thiếu hàng
    public void testDoiChieuLuonGiaoThieu() {
        List<ChiTietDoiChieu> duLieuNhap = new ArrayList<ChiTietDoiChieu>();
        ChiTietDoiChieu dongNhap = new ChiTietDoiChieu("MH-XYZ", 85, "Nguyen ven", "LOT-999");
        dongNhap.khoiTaoVaTinhToan("MH-XYZ", 85, 100); 
        duLieuNhap.add(dongNhap);

        List<ChiTietDoiChieu> ketQua = controller.xuLyTinhToanDoiChieu(donHangMau, duLieuNhap);
        assertEquals(1, ketQua.size());
        assertEquals("Giao thieu", ketQua.get(0).getKetQuaTrangThai());
        assertEquals(-15, ketQua.get(0).getSoLuongChenhLech());
    }

    @Test // Ca kiểm thử hộp trắng độ bao phủ nhánh C1 - Luồng khớp hàng & sinh biên bản
    public void testDoiChieuKhopVaSinhBienBan() {
        List<ChiTietDoiChieu> duLieuNhap = new ArrayList<ChiTietDoiChieu>();
        ChiTietDoiChieu dongNhap = new ChiTietDoiChieu("MH-XYZ", 100, "Nguyen ven", "LOT-111");
        dongNhap.khoiTaoVaTinhToan("MH-XYZ", 100, 100); 
        duLieuNhap.add(dongNhap);

        controller.xuLyTinhToanDoiChieu(donHangMau, duLieuNhap);
        BienBanDoiChieu bb = controller.xacNhanNhapKho("DH-007", "NhanVienThang");
        
        assertNotNull(bb);
        assertTrue(bb.getMaBienBanDoiChieu().startsWith("BB-"));
        assertEquals(1, bb.getBangChiTiet().size());
        assertEquals("Khop", bb.getBangChiTiet().get(0).getKetQuaTrangThai());
    }
}