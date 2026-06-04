// ============================================================
// Tac gia    : Nguyen Duc Toan - 20235846
// Ten file   : MatHangDonHangDAO.java
// Goi        : dataaccess (tang DataAccess)
// Mo ta      : DAO truy xuat danh sach mat hang theo ma don hang.
//              Khop Bieu do lop thiet ke BT6 - phuong thuc findByOrderCode().
//              BT6: viec lay danh sach mat hang da chuyen tu entity DonHang
//              sang DAO nay (bo DonHang.layDSMatHang()).
// Phu thuoc  : domainmodel.MatHangDonHang, domainmodel.Site
// ============================================================
package dataaccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domainmodel.MatHangDonHang;
import domainmodel.Site;

/**
 * DAO cho cac dong mat hang trong don hang (du lieu gia lap - mock data).
 *
 * <p>Tuong ung class MatHangDonHangDAO trong Bieu do lop BT6,
 * voi phuong thuc findByOrderCode(maDonHang): List.</p>
 */
public class MatHangDonHangDAO {

    /** Ban do: maDonHang -> danh sach mat hang cua don hang do. */
    private final Map<String, List<MatHangDonHang>> mockData;

    public MatHangDonHangDAO() {
        this.mockData = new HashMap<>();
        khoiTaoMockData();
    }

    private void khoiTaoMockData() {
        // DH-2025-001 (Tau) - 3 mat hang
        mockData.put("DH-2025-001", new ArrayList<>(Arrays.asList(
                new MatHangDonHang("MH001", "Linh kien ban dan IC-7805", 500, "Cai", Site.PT_TAU),
                new MatHangDonHang("MH002", "Tu dien gom 100uF", 1000, "Cai", Site.PT_TAU),
                new MatHangDonHang("MH003", "Dien tro 10K Ohm", 2000, "Cai", Site.PT_TAU))));

        // DH-2025-002 (Hang khong) - 2 mat hang
        mockData.put("DH-2025-002", new ArrayList<>(Arrays.asList(
                new MatHangDonHang("MH004", "Board mach Arduino Uno R3", 200, "Cai", Site.PT_HANG_KHONG),
                new MatHangDonHang("MH005", "Cam bien nhiet do DS18B20", 300, "Cai", Site.PT_HANG_KHONG))));

        // DH-2025-003 (Tau) - 1 mat hang
        mockData.put("DH-2025-003", new ArrayList<>(Arrays.asList(
                new MatHangDonHang("MH006", "Day cap USB Type-C", 100, "Cai", Site.PT_TAU))));

        // DH-2025-004 (Hang khong) - 2 mat hang
        mockData.put("DH-2025-004", new ArrayList<>(Arrays.asList(
                new MatHangDonHang("MH007", "Chip xu ly ARM Cortex-M4", 800, "Cai", Site.PT_HANG_KHONG),
                new MatHangDonHang("MH008", "Thach anh 16MHz", 1500, "Cai", Site.PT_HANG_KHONG))));

        // DH-2025-005 (Tau) - DON HANG DA HUY - 1 mat hang
        mockData.put("DH-2025-005", new ArrayList<>(Arrays.asList(
                new MatHangDonHang("MH009", "Pin Lithium 18650", 400, "Vien", Site.PT_TAU))));
    }

    /**
     * Lay danh sach mat hang cua mot don hang theo ma don.
     *
     * <p>Tuong ung findByOrderCode(maDonHang): List trong Bieu do lop BT6.</p>
     *
     * @param maDonHang ma don hang
     * @return danh sach mat hang; danh sach rong neu don khong co mat hang nao
     */
    public List<MatHangDonHang> findByOrderCode(String maDonHang) {
        List<MatHangDonHang> ds = mockData.get(maDonHang);
        if (ds == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(ds);
    }
}
