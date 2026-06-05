package dao;

import entity.MatHangYeuCau;
import entity.YeuCauKiemTra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Lớp truy xuất dữ liệu cho YeuCauKiemTra (Mock Data).
 * Phương thức: findByTrangThai(), findById(), updateTrangThai()
 */
public class YeuCauKiemTraDAO {

    private List<YeuCauKiemTra> mockData;

    public YeuCauKiemTraDAO() {
        mockData = new ArrayList<>();
        initMockData();
    }

    private void initMockData() {
        // ===== Yêu cầu 1: Site 101 — 3 mặt hàng cần báo cáo =====
        List<MatHangYeuCau> dsMH1 = new ArrayList<>(Arrays.asList(
                new MatHangYeuCau(1001, "Linh kiện bán dẫn IC-7805", "Cái"),
                new MatHangYeuCau(1002, "Tụ điện gốm 100uF", "Cái"),
                new MatHangYeuCau(1003, "Điện trở 10K Ohm", "Cái")
        ));
        mockData.add(new YeuCauKiemTra(1, "CHO_PHAN_HOI", 101, dsMH1));

        // ===== Yêu cầu 2: Site 102 — 2 mặt hàng =====
        List<MatHangYeuCau> dsMH2 = new ArrayList<>(Arrays.asList(
                new MatHangYeuCau(1004, "Board mạch Arduino Uno R3", "Cái"),
                new MatHangYeuCau(1005, "Cảm biến nhiệt độ DS18B20", "Cái")
        ));
        mockData.add(new YeuCauKiemTra(2, "CHO_PHAN_HOI", 102, dsMH2));

        // ===== Yêu cầu 3: Site 103 — đã phản hồi (không hiển thị) =====
        List<MatHangYeuCau> dsMH3 = new ArrayList<>(Arrays.asList(
                new MatHangYeuCau(1006, "Dây cáp USB Type-C", "Cái")
        ));
        mockData.add(new YeuCauKiemTra(3, "DA_PHAN_HOI", 103, dsMH3));

        // ===== Yêu cầu 4: Site 101 — 1 mặt hàng (test case đơn giản) =====
        List<MatHangYeuCau> dsMH4 = new ArrayList<>(Arrays.asList(
                new MatHangYeuCau(1007, "Chip xử lý ARM Cortex-M4", "Cái")
        ));
        mockData.add(new YeuCauKiemTra(4, "CHO_PHAN_HOI", 101, dsMH4));
    }

    /**
     * Tìm danh sách yêu cầu theo trạng thái.
     * Chỉ lấy các yêu cầu có trạng thái "CHO_PHAN_HOI".
     */
    public List<YeuCauKiemTra> findByTrangThai(String trangThai) {
        List<YeuCauKiemTra> result = new ArrayList<>();
        for (YeuCauKiemTra yc : mockData) {
            if (yc.getTrangThai().equals(trangThai)) {
                result.add(yc);
            }
        }
        return result;
    }

    /**
     * Tìm yêu cầu theo ID.
     */
    public YeuCauKiemTra findById(int idYeuCau) {
        for (YeuCauKiemTra yc : mockData) {
            if (yc.getIdYeuCau() == idYeuCau) {
                return yc;
            }
        }
        return null;
    }

    /**
     * Cập nhật trạng thái yêu cầu.
     */
    public boolean updateTrangThai(int idYeuCau, String trangThaiMoi) {
        YeuCauKiemTra yc = findById(idYeuCau);
        if (yc != null) {
            yc.capNhatTrangThai(trangThaiMoi);
            return true;
        }
        return false;
    }
}
