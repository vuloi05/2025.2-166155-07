// Tac gia    : Nguyen Duc Toan - 20235846
package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import businesslogic.DonHangController;

public class CanhBaoHuyDialog extends JDialog {

    private static final long serialVersionUID = 1L;

    private final transient DonHangController controller;
    private final JLabel lblThongBao;

    public CanhBaoHuyDialog(Frame chuSoHuu, DonHangController controller) {
        super(chuSoHuu, true);
        this.controller = controller;
        this.lblThongBao = new JLabel("", SwingConstants.CENTER);
        khoiTaoGiaoDien();
    }

    private void khoiTaoGiaoDien() {
        setSize(520, 340);
        setLocationRelativeTo(getOwner());
        setResizable(false);

        JPanel goc = new JPanel(new BorderLayout());
        goc.setBackground(Color.WHITE);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(new EmptyBorder(12, 16, 8, 16));
        JLabel lblHeader = new JLabel("Cảnh báo");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblHeader.setForeground(GroupUiTheme.TEXT_PRIMARY);
        header.add(lblHeader, BorderLayout.WEST);

        JButton btnDong = new JButton("✕");
        btnDong.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnDong.setForeground(GroupUiTheme.TEXT_SECONDARY);
        btnDong.setBackground(Color.WHITE);
        btnDong.setBorderPainted(false);
        btnDong.setFocusPainted(false);
        btnDong.setOpaque(false);
        btnDong.addActionListener(e -> xacNhanVaQuayLai());
        header.add(btnDong, BorderLayout.EAST);
        goc.add(header, BorderLayout.NORTH);

        JPanel noiDung = new JPanel();
        noiDung.setLayout(new BoxLayout(noiDung, BoxLayout.Y_AXIS));
        noiDung.setBackground(Color.WHITE);
        noiDung.setBorder(new EmptyBorder(8, 24, 8, 24));

        JLabel lblIcon = new JLabel("\u25A1", SwingConstants.CENTER);
        lblIcon.setFont(new Font("Segoe UI", Font.PLAIN, 56));
        lblIcon.setForeground(GroupUiTheme.DANGER_COLOR);
        lblIcon.setBorder(BorderFactory.createLineBorder(GroupUiTheme.DANGER_COLOR, 3));
        lblIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblIcon.setPreferredSize(new Dimension(64, 64));
        lblIcon.setMaximumSize(new Dimension(64, 64));
        noiDung.add(lblIcon);

        noiDung.add(Box.createVerticalStrut(16));

        JLabel lblTieuDe = new JLabel("Không thể xem chi tiết", SwingConstants.CENTER);
        lblTieuDe.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTieuDe.setForeground(GroupUiTheme.TEXT_PRIMARY);
        lblTieuDe.setAlignmentX(Component.CENTER_ALIGNMENT);
        noiDung.add(lblTieuDe);

        noiDung.add(Box.createVerticalStrut(8));

        lblThongBao.setFont(GroupUiTheme.FONT_BODY);
        lblThongBao.setForeground(GroupUiTheme.TEXT_PRIMARY);
        lblThongBao.setAlignmentX(Component.CENTER_ALIGNMENT);
        noiDung.add(lblThongBao);
        noiDung.add(Box.createVerticalGlue());

        goc.add(noiDung, BorderLayout.CENTER);

        JPanel chanTrang = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 16));
        chanTrang.setBackground(Color.WHITE);
        chanTrang.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, GroupUiTheme.BORDER_COLOR));
        JButton btnXacNhan = taoNut("Xác nhận & Quay lại", GroupUiTheme.DANGER_COLOR);
        btnXacNhan.addActionListener(e -> xacNhanVaQuayLai());
        chanTrang.add(btnXacNhan);
        goc.add(chanTrang, BorderLayout.SOUTH);

        setContentPane(goc);
    }

    public void hienThiCanhBaoDaHuy(String maDonHang) {
        lblThongBao.setText("<html><div style='text-align:center;'>Đơn hàng <b>"
                + maDonHang + "</b> đã bị hủy,<br>không thể xem chi tiết.</div></html>");
        setVisible(true);
    }

    public void xacNhanVaQuayLai() {
        controller.xacNhanVaQuayLai();
        dispose();
    }

    private JButton taoNut(String chu, Color mauNen) {
        JButton btn = new JButton(chu);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(mauNen);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setBorder(new EmptyBorder(10, 24, 10, 24));
        btn.setPreferredSize(new Dimension(220, 44));
        return btn;
    }
}
