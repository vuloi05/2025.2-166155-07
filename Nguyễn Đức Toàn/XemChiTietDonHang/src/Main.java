// ============================================================
// Tac gia    : Nguyen Duc Toan - 20235846
// Ten file   : Main.java
// Goi        : (mac dinh)
// Mo ta      : Diem khoi chay ung dung UC006 - Xem chi tiet don hang.
//              Mo Man hinh danh sach don hang (Man hinh 2 - muc 7 SRS).
// Phu thuoc  : presentation.DonHangListView, javax.swing
// ============================================================
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import presentation.DonHangListView;

/**
 * Lop khoi chay ung dung Swing tren Event Dispatch Thread (EDT).
 */
public final class Main {

    private Main() {
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            // Khong dung duoc giao dien he thong -> dung mac dinh, van chay binh thuong
            System.err.println("[Main] Khong nap duoc Look&Feel he thong: " + ex.getMessage());
        }

        SwingUtilities.invokeLater(() -> new DonHangListView().setVisible(true));
    }
}
