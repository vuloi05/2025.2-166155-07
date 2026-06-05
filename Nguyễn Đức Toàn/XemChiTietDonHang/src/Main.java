// Tac gia    : Nguyen Duc Toan - 20235846
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import presentation.TrangChuView;

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

        SwingUtilities.invokeLater(() -> new TrangChuView().setVisible(true));
    }
}
