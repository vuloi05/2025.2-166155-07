package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controller.CapNhatTonKhoController;
import entity.MatHangYeuCau;
import entity.YeuCauKiemTra;
import service.CapNhatTonKhoService.KetQuaLuuTonKho;

/**
 * Lớp giao diện chính - Hiển thị các màn hình cho Use Case "Nhập số lượng tồn kho".
 * Tương ứng với class <<boundary>> FormNhapTonKho trong Class Diagram.
 *
 * Sơ đồ chuyển màn hình (Screen Transition):
 *   Màn hình 1 (DS yêu cầu kiểm tra) --> [Chọn 1 yêu cầu] --> Màn hình 2 (Nhập tồn kho)
 *   Màn hình 2 --> [Nhấn "Gửi" + dữ liệu hợp lệ] --> Màn hình 3 (Thông báo thành công)
 *   Màn hình 2 --> [Nhấn "Gửi" + dữ liệu không hợp lệ] --> Hiện cảnh báo lỗi (ở lại MH2)
 *   Màn hình 2 --> [Nhấn "Hủy"] --> Quay lại Màn hình 1
 *   Màn hình 3 --> [Nhấn "Đóng"] --> Quay lại Màn hình 1
 *
 * Phương thức (theo Class Diagram):
 *   + hienThiDanhSachMatHang() : void
 *   + hienThiThongBaoThanhCong() : void
 */
public class FormNhapTonKho extends JFrame {

    private static final long serialVersionUID = 1L;

    private CapNhatTonKhoController controller;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    // Panels for each screen
    private JPanel panelDSYeuCau;    // Screen 1
    private JPanel panelNhapTonKho;  // Screen 2
    private JPanel panelThanhCong;   // Screen 3

    // Current state
    private YeuCauKiemTra currentYeuCau;

    // Tables
    private JTable tableYeuCau;
    private JTable tableNhapTonKho;

    // Input fields map: maHang -> JTextField
    private Map<Integer, JTextField> inputFields;

    public FormNhapTonKho() {
        this.controller = new CapNhatTonKhoController();
        this.inputFields = new HashMap<>();
        initUI();
    }

    private void initUI() {
        setTitle("Hệ thống đặt hàng nhập khẩu - Nhập số lượng tồn kho");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(750, 500));

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        panelDSYeuCau = createPanelDSYeuCau();
        panelNhapTonKho = createPanelNhapTonKho();
        panelThanhCong = createPanelThanhCong();

        mainPanel.add(panelDSYeuCau, "DS_YEU_CAU");
        mainPanel.add(panelNhapTonKho, "NHAP_TON_KHO");
        mainPanel.add(panelThanhCong, "THANH_CONG");

        add(mainPanel);

        // Load initial data and show first screen
        loadDSYeuCau();
        cardLayout.show(mainPanel, "DS_YEU_CAU");
    }

    // ========================================================================
    // SCREEN 1: DANH SÁCH YÊU CẦU KIỂM TRA
    // ========================================================================
    private JPanel createPanelDSYeuCau() {
        JPanel panel = new JPanel(new BorderLayout(0, 5));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Header label
        JLabel lblTitle = new JLabel("DANH SÁCH YÊU CẦU KIỂM TRA TỒN KHO");
        lblTitle.setFont(new Font("Dialog", Font.BOLD, 16));
        lblTitle.setBorder(new EmptyBorder(0, 0, 8, 0));
        panel.add(lblTitle, BorderLayout.NORTH);

        // Table
        String[] columns = {"Mã YC", "Mã Site", "Số mặt hàng", "Trạng thái", "Thao tác"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };
        tableYeuCau = createTable(model);

        JScrollPane scrollPane = new JScrollPane(tableYeuCau);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Click handler for "Chọn" column
        tableYeuCau.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = tableYeuCau.rowAtPoint(e.getPoint());
                int col = tableYeuCau.columnAtPoint(e.getPoint());
                if (col == 4 && row >= 0) {
                    int idYeuCau = (int) tableYeuCau.getValueAt(row, 0);
                    YeuCauKiemTra yc = controller.layThongTinYeuCau(idYeuCau);

                    // 2a: Yêu cầu bị hủy hoặc quá hạn — không hiển thị form, báo lỗi và ở lại danh sách
                    if (yc == null || "HUY".equals(yc.getTrangThai()) || "QUA_HAN".equals(yc.getTrangThai())) {
                        JOptionPane.showMessageDialog(FormNhapTonKho.this,
                                "Yêu cầu này đã bị hủy hoặc không còn hiệu lực.",
                                "Thông báo",
                                JOptionPane.WARNING_MESSAGE);
                        loadDSYeuCau();
                        return;
                    }

                    currentYeuCau = yc;
                    hienThiDanhSachMatHang(yc);
                    cardLayout.show(mainPanel, "NHAP_TON_KHO");
                }
            }
        });

        // Footer
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        JLabel lblInfo = new JLabel("Chọn một yêu cầu để nhập số lượng tồn kho");
        lblInfo.setFont(new Font("Dialog", Font.ITALIC, 12));
        footer.add(lblInfo);
        panel.add(footer, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Tải danh sách yêu cầu chờ phản hồi lên bảng.
     */
    private void loadDSYeuCau() {
        DefaultTableModel model = (DefaultTableModel) tableYeuCau.getModel();
        model.setRowCount(0);

        List<YeuCauKiemTra> dsYeuCau = controller.layDSYeuCauChoPhanHoi();
        for (YeuCauKiemTra yc : dsYeuCau) {
            model.addRow(new Object[]{
                    yc.getIdYeuCau(),
                    yc.getMaSite(),
                    yc.getSoMatHang(),
                    yc.getTrangThai(),
                    "Chọn"
            });
        }
    }

    // ========================================================================
    // SCREEN 2: NHẬP SỐ LƯỢNG TỒN KHO
    // ========================================================================
    private JPanel createPanelNhapTonKho() {
        JPanel panel = new JPanel(new BorderLayout(0, 5));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Header label
        JLabel lblTitle = new JLabel("NHẬP SỐ LƯỢNG TỒN KHO");
        lblTitle.setFont(new Font("Dialog", Font.BOLD, 16));
        lblTitle.setBorder(new EmptyBorder(0, 0, 8, 0));
        panel.add(lblTitle, BorderLayout.NORTH);

        // Table with editable quantity column
        JPanel panelTable = new JPanel(new BorderLayout());
        panelTable.setBorder(new TitledBorder("Danh sách mặt hàng cần báo cáo tồn kho"));

        String[] colMH = {"Mã hàng", "Tên hàng", "Đơn vị", "Số lượng tồn kho"};
        DefaultTableModel modelMH = new DefaultTableModel(colMH, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Only allow editing of quantity column
                return column == 3;
            }
        };
        tableNhapTonKho = createTable(modelMH);

        JScrollPane scrollMH = new JScrollPane(tableNhapTonKho);
        panelTable.add(scrollMH, BorderLayout.CENTER);
        panel.add(panelTable, BorderLayout.CENTER);

        // Buttons
        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        JButton btnGuiPhanHoi = new JButton("Gửi phản hồi");
        btnGuiPhanHoi.addActionListener(e -> xuLyGuiPhanHoi());
        panelBtn.add(btnGuiPhanHoi);

        JButton btnHuy = new JButton("Hủy");
        btnHuy.addActionListener(e -> {
            // 4a: Hiển thị popup xác nhận trước khi hủy bỏ thao tác
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn hủy? Các thay đổi sẽ không được lưu.",
                    "Xác nhận hủy",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                currentYeuCau = null;
                inputFields.clear();
                cardLayout.show(mainPanel, "DS_YEU_CAU");
            }
        });
        panelBtn.add(btnHuy);

        panel.add(panelBtn, BorderLayout.SOUTH);
        return panel;
    }

    /**
     * Hiển thị danh sách mặt hàng cho Site nhập số lượng.
     * Tương ứng: hienThiDanhSachMatHang() : void trong Class Diagram.
     *
     * Mỗi hàng hiển thị: Mã hàng | Tên hàng | Đơn vị | [Input số lượng]
     */
    public void hienThiDanhSachMatHang(YeuCauKiemTra yeuCau) {
        inputFields.clear();

        DefaultTableModel editModel = new DefaultTableModel(
                new String[]{"Mã hàng", "Tên hàng", "Đơn vị", "Số lượng tồn kho"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };

        tableNhapTonKho.setModel(editModel);

        if (yeuCau.getChiTietYeuCau() != null) {
            for (MatHangYeuCau mh : yeuCau.getChiTietYeuCau()) {
                editModel.addRow(new Object[]{
                        mh.getMaHang(),
                        mh.getTenHang(),
                        mh.getDonVi(),
                        ""
                });
            }
        }

        // Use a simple DefaultCellEditor so quantity cells are editable text fields
        tableNhapTonKho.getColumnModel().getColumn(3).setCellEditor(
                new DefaultCellEditor(new JTextField()));
    }

    /**
     * Xử lý sự kiện nhấn "Gửi phản hồi".
     * Tương ứng Sequence Diagram: nhanGuiPhanHoi() -> xuLyLuuTonKho(danhSachNhap)
     * View chỉ gọi một lần; service tự validate nội bộ theo đúng SD_ChiTietLuuTonKho.
     */
    private void xuLyGuiPhanHoi() {
        if (currentYeuCau == null) return;

        // Stop any active cell editing to capture the value
        if (tableNhapTonKho.isEditing()) {
            tableNhapTonKho.getCellEditor().stopCellEditing();
        }

        // Collect input from table
        Map<Integer, String> danhSachNhap = new HashMap<>();
        for (int row = 0; row < tableNhapTonKho.getRowCount(); row++) {
            int maHang = (int) tableNhapTonKho.getValueAt(row, 0);
            Object val = tableNhapTonKho.getValueAt(row, 3);
            String soLuong = (val != null) ? val.toString() : "";
            danhSachNhap.put(maHang, soLuong);
        }

        // Single call to controller — validation is done internally in service (per Sequence Diagram)
        KetQuaLuuTonKho ketQua = controller.xuLyLuuTonKho(
                danhSachNhap,
                currentYeuCau.getIdYeuCau(),
                currentYeuCau.getMaSite(),
                currentYeuCau.getChiTietYeuCau()
        );

        if (!ketQua.isHopLe()) {
            // 5a: thongBaoLoiDinhDang -> yeuCauNhapLai (stay on this screen)
            JOptionPane.showMessageDialog(this,
                    "Số lượng phải là số nguyên lớn hơn hoặc bằng 0.\n\nChi tiết: " + ketQua.getThongBao(),
                    "Lỗi định dạng dữ liệu",
                    JOptionPane.WARNING_MESSAGE);
        } else if (!ketQua.isLuuThanhCong()) {
            // 6a: Lỗi kết nối/server — giữ nguyên dữ liệu đã nhập
            JOptionPane.showMessageDialog(this,
                    "Lỗi kết nối. Vui lòng thử lại sau.",
                    "Lỗi hệ thống",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            // Bước 8: Gửi thông báo đến Bộ phận đặt hàng quốc tế
            controller.guiThongBaoDenBoPhanDatHang(currentYeuCau.getIdYeuCau(), currentYeuCau.getMaSite());
            hienThiThongBaoThanhCong();
        }
    }

    // ========================================================================
    // SCREEN 3: THÔNG BÁO THÀNH CÔNG
    // ========================================================================
    private JPanel createPanelThanhCong() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Header label
        JLabel lblTitle = new JLabel("CẬP NHẬT THÀNH CÔNG");
        lblTitle.setFont(new Font("Dialog", Font.BOLD, 16));
        lblTitle.setBorder(new EmptyBorder(0, 0, 8, 0));
        panel.add(lblTitle, BorderLayout.NORTH);

        // Content — centered success message
        JPanel content = new JPanel(new GridBagLayout());

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEtchedBorder(),
                new EmptyBorder(30, 50, 30, 50)
        ));

        JLabel lblMsg = new JLabel("Cập nhật tồn kho thành công!");
        lblMsg.setFont(new Font("Dialog", Font.BOLD, 16));
        lblMsg.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblMsg);

        card.add(Box.createVerticalStrut(10));

        JLabel lblDetail = new JLabel("Trạng thái yêu cầu đã được chuyển thành \"Đã phản hồi\".");
        lblDetail.setFont(new Font("Dialog", Font.PLAIN, 13));
        lblDetail.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(lblDetail);

        card.add(Box.createVerticalStrut(20));

        JButton btnDong = new JButton("Đóng");
        btnDong.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDong.addActionListener(e -> {
            currentYeuCau = null;
            inputFields.clear();
            loadDSYeuCau(); // Refresh list — updated status will be reflected
            cardLayout.show(mainPanel, "DS_YEU_CAU");
        });
        card.add(btnDong);

        content.add(card);
        panel.add(content, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Hiển thị thông báo cập nhật thành công.
     * Tương ứng: hienThiThongBaoThanhCong() : void trong Class Diagram.
     */
    public void hienThiThongBaoThanhCong() {
        cardLayout.show(mainPanel, "THANH_CONG");
    }

    // ========================================================================
    // HELPER METHODS — UI COMPONENTS
    // ========================================================================

    /**
     * Creates a plain JTable with simple alternating row shading and a standard header.
     */
    private JTable createTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setRowHeight(24);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(true);
        table.getTableHeader().setReorderingAllowed(false);

        // Subtle alternating row background — avoids any heavy color theming
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                            boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(240, 240, 240));
                }
                return c;
            }
        });

        return table;
    }
}
