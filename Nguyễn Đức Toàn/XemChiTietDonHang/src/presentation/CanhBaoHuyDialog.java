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

    private static final Color DANGER_COLOR = new Color(244, 67, 54);
    private static final Color TEXT_PRIMARY = new Color(33, 37, 41);
    private static final Color BG_COLOR = Color.WHITE;
    private static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 14);

    private final transient DonHangController controller;
    private final JLabel lblThongBao;

    public CanhBaoHuyDialog(Frame chuSoHuu, DonHangController controller) {
        super(chuSoHuu, "Canh bao", true);
        this.controller = controller;
        this.lblThongBao = new JLabel("", SwingConstants.CENTER);
        khoiTaoGiaoDien();
    }

    private void khoiTaoGiaoDien() {
        setSize(480, 320);
        setLocationRelativeTo(getOwner());
        setResizable(false);

        JPanel noiDung = new JPanel();
        noiDung.setLayout(new BoxLayout(noiDung, BoxLayout.Y_AXIS));
        noiDung.setBackground(BG_COLOR);
        noiDung.setBorder(new EmptyBorder(28, 24, 20, 24));
        JLabel lblIcon = new JLabel("\u26A0", SwingConstants.CENTER);
        lblIcon.setFont(new Font("Segoe UI", Font.PLAIN, 48));
        lblIcon.setForeground(DANGER_COLOR);
        lblIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        noiDung.add(lblIcon);

        noiDung.add(Box.createVerticalStrut(10));

        JLabel lblTieuDe = new JLabel("Khong the xem chi tiet", SwingConstants.CENTER);
        lblTieuDe.setFont(FONT_TITLE);
        lblTieuDe.setForeground(TEXT_PRIMARY);
        lblTieuDe.setAlignmentX(Component.CENTER_ALIGNMENT);
        noiDung.add(lblTieuDe);

        noiDung.add(Box.createVerticalStrut(8));

        lblThongBao.setFont(FONT_BODY);
        lblThongBao.setForeground(TEXT_PRIMARY);
        lblThongBao.setAlignmentX(Component.CENTER_ALIGNMENT);
        noiDung.add(lblThongBao);

        // Day phan noi dung len tren, tranh dong chu cuoi bi nut che mat
        noiDung.add(Box.createVerticalGlue());

        add(noiDung, BorderLayout.CENTER);
        JPanel chanTrang = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 12));
        chanTrang.setBackground(BG_COLOR);
        JButton btnXacNhan = taoNut("Xac nhan & Quay lai", DANGER_COLOR);
        btnXacNhan.addActionListener(e -> xacNhanVaQuayLai());
        chanTrang.add(btnXacNhan);
        add(chanTrang, BorderLayout.SOUTH);
    }

    public void hienThiCanhBaoDaHuy(String maDonHang) {
        lblThongBao.setText("<html><div style='text-align:center;'>Don hang <b>"
                + maDonHang + "</b> da bi huy,<br>khong the xem chi tiet.</div></html>");
        setVisible(true);
    }

    public void xacNhanVaQuayLai() {
        controller.xacNhanVaQuayLai();
        dispose();
    }

    private JButton taoNut(String chu, Color mauNen) {
        JButton btn = new JButton(chu);
        btn.setFont(FONT_BUTTON);
        btn.setForeground(Color.WHITE);
        btn.setBackground(mauNen);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setBorder(new EmptyBorder(8, 22, 8, 22));
        btn.setPreferredSize(new Dimension(200, 40));
        return btn;
    }
}
