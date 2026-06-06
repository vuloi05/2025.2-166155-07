// Tac gia    : Nguyen Duc Toan - 20235846
package dataaccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domainmodel.MatHangDonHang;
import domainmodel.Site;

public class MatHangDonHangDAO {

    private final Map<String, List<MatHangDonHang>> mockData;

    public MatHangDonHangDAO() {
        this.mockData = new HashMap<>();
        khoiTaoMockData();
    }

    private void khoiTaoMockData() {
        mockData.put("DH-2026-001", new ArrayList<>(Arrays.asList(
                new MatHangDonHang("MH001", "Linh kiện bán dẫn IC-7805", 500, "Cái", Site.PT_TAU))));

        mockData.put("DH-2026-002", new ArrayList<>(Arrays.asList(
                new MatHangDonHang("MH002", "Tụ điện gốm 100uF", 1000, "Cái", Site.PT_HANG_KHONG),
                new MatHangDonHang("MH003", "Điện trở 10K Ohm", 2000, "Cái", Site.PT_HANG_KHONG))));

        mockData.put("DH-2026-003", new ArrayList<>(Arrays.asList(
                new MatHangDonHang("MH004", "Màn hình LCD 16x2", 50, "Cái", Site.PT_TAU))));

        mockData.put("DH-2026-004", new ArrayList<>(Arrays.asList(
                new MatHangDonHang("MH005", "Cảm biến nhiệt độ DS18B20", 300, "Cái", Site.PT_TAU))));

        mockData.put("DH-2026-005", new ArrayList<>(Arrays.asList(
                new MatHangDonHang("MH006", "Board mạch Arduino Uno R3", 200, "Cái", Site.PT_TAU),
                new MatHangDonHang("MH007", "Chip xử lý ARM Cortex-M4", 800, "Cái", Site.PT_TAU))));

        mockData.put("DH-2026-006", new ArrayList<>(Arrays.asList(
                new MatHangDonHang("MH008", "Thạch anh 16MHz", 1500, "Cái", Site.PT_HANG_KHONG))));

        mockData.put("DH-2026-007", new ArrayList<>(Arrays.asList(
                new MatHangDonHang("MH009", "Pin Lithium 18650", 400, "Viên", Site.PT_TAU),
                new MatHangDonHang("MH010", "Dây cáp USB Type-C", 100, "Cái", Site.PT_TAU))));

        mockData.put("DH-2026-008", new ArrayList<>(Arrays.asList(
                new MatHangDonHang("MH011", "Module WiFi ESP8266", 150, "Cái", Site.PT_TAU))));

        mockData.put("DH-2026-009", new ArrayList<>(Arrays.asList(
                new MatHangDonHang("MH012", "Relay 5V", 250, "Cái", Site.PT_TAU))));

        mockData.put("DH-2026-010", new ArrayList<>(Arrays.asList(
                new MatHangDonHang("MH013", "LED RGB 5mm", 5000, "Cái", Site.PT_HANG_KHONG),
                new MatHangDonHang("MH014", "Transistor NPN 2N2222", 3000, "Cái", Site.PT_HANG_KHONG))));

        mockData.put("DH-2026-011", new ArrayList<>(Arrays.asList(
                new MatHangDonHang("MH015", "Socket IC DIP-8", 2000, "Cái", Site.PT_HANG_KHONG))));

        mockData.put("DH-2026-012", new ArrayList<>(Arrays.asList(
                new MatHangDonHang("MH016", "Quartz crystal 32kHz", 800, "Cái", Site.PT_HANG_KHONG))));

        mockData.put("DH-0001", new ArrayList<>(Arrays.asList(
                new MatHangDonHang("MH017", "Công tắc DIP 8 bit", 600, "Cái", Site.PT_HANG_KHONG),
                new MatHangDonHang("MH018", "Header pin 2.54mm", 1200, "Cái", Site.PT_HANG_KHONG))));
    }

    public List<MatHangDonHang> findByOrderCode(String maDonHang) {
        List<MatHangDonHang> ds = mockData.get(maDonHang);
        if (ds == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(ds);
    }
}
