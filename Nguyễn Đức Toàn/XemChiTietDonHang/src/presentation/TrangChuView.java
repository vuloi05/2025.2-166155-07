// Tac gia    : Nguyen Duc Toan - 20235846
package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businesslogic.DonHangController;

public class TrangChuView extends JFrame implements DonHangNavigation {

    private static final long serialVersionUID = 1L;

    private final transient DonHangController controller;
    private final java.awt.CardLayout cardLayout;
    private final JPanel mainPanel;

    private DashboardDonHangView dashboardView;
    private DonHangListView listView;

    public TrangChuView() {
        this(new DonHangController());
    }

    public TrangChuView(DonHangController controller) {
        this.controller = controller;
        this.cardLayout = new java.awt.CardLayout();
        this.mainPanel = new JPanel(cardLayout);
        khoiTaoGiaoDien();
    }

    private void khoiTaoGiaoDien() {
        setTitle("Hệ thống đặt hàng nhập khẩu - Phân bổ tự động");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 750);
        setMinimumSize(new Dimension(1000, 600));
        setLocationRelativeTo(null);

        mainPanel.add(taoPanelTrangChu(), "TRANG_CHU");
        dashboardView = new DashboardDonHangView(this, controller);
        listView = new DonHangListView(this, controller);
        mainPanel.add(dashboardView, "DASHBOARD");
        mainPanel.add(listView, "DANH_SACH");

        add(mainPanel);
        cardLayout.show(mainPanel, "TRANG_CHU");
    }

    private JPanel taoPanelTrangChu() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(GroupUiTheme.BG_COLOR);
        panel.add(taoHeader("HỆ THỐNG ĐẶT HÀNG NHẬP KHẨU - TRANG CHỦ - HỆ THỐNG ĐẶT HÀNG NHẬP KHẨU"), BorderLayout.NORTH);

        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(GroupUiTheme.BG_COLOR);
        content.setBorder(new EmptyBorder(30, 60, 30, 60));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        Color tileColor = GroupUiTheme.PRIMARY_COLOR;
        Color disabledColor = new Color(108, 117, 125);

        themTile(content, gbc, 0, 0, "📊  Phân bổ đơn đặt hàng",
                "Tính toán phân bổ tự động cho yêu cầu nhập hàng", disabledColor, false, null);
        themTile(content, gbc, 1, 0, "📋  Tạo yêu cầu nhập hàng",
                "Tạo mới và xem danh sách yêu cầu nhập hàng", disabledColor, false, null);
        themTile(content, gbc, 0, 1, "🔍  Lọc Site theo mặt hàng",
                "Phân nhóm mặt hàng và chọn Site cung cấp", disabledColor, false, null);
        themTile(content, gbc, 1, 1, "📦  Nhập số lượng tồn kho",
                "Cập nhật số lượng mặt hàng tại Site", disabledColor, false, null);
        themTile(content, gbc, 0, 2, "🚚  Xem chi tiết đơn hàng",
                "Quản lý và theo dõi đơn đặt hàng", tileColor, true, this::showDashboard);
        themTile(content, gbc, 1, 2, "✅  Kiểm hàng",
                "Đối chiếu số lượng nhập kho thực tế", disabledColor, false, null);

        panel.add(content, BorderLayout.CENTER);
        return panel;
    }

    private void themTile(JPanel content, GridBagConstraints gbc, int x, int y,
            String title, String subtitle, Color color, boolean enabled, Runnable action) {
        JButton btn = GroupUiTheme.createMenuButton(title, subtitle, color);
        btn.setEnabled(enabled);
        if (enabled && action != null) {
            btn.addActionListener(e -> action.run());
        }
        gbc.gridx = x;
        gbc.gridy = y;
        content.add(btn, gbc);
    }

    private JPanel taoHeader(String title) {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(GroupUiTheme.PRIMARY_COLOR);
        header.setBorder(new EmptyBorder(15, 30, 15, 30));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(GroupUiTheme.FONT_TITLE);
        lblTitle.setForeground(Color.WHITE);
        header.add(lblTitle, BorderLayout.WEST);

        JLabel lblUser = new JLabel("👤 NV Đặt hàng quốc tế");
        lblUser.setFont(GroupUiTheme.FONT_BODY);
        lblUser.setForeground(new Color(200, 220, 255));
        header.add(lblUser, BorderLayout.EAST);
        return header;
    }

    @Override
    public void showTrangChu() {
        cardLayout.show(mainPanel, "TRANG_CHU");
    }

    @Override
    public void showDashboard() {
        dashboardView.lamMoi();
        cardLayout.show(mainPanel, "DASHBOARD");
    }

    @Override
    public void showDanhSach() {
        listView.lamMoi();
        cardLayout.show(mainPanel, "DANH_SACH");
    }

    public DonHangController getController() {
        return controller;
    }
}
