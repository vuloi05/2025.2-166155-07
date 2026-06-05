package dao;

import entity.ThongTinTonKho;

import java.util.ArrayList;
import java.util.List;

/**
 * Lớp truy xuất dữ liệu cho ThongTinTonKho (Mock Data - lưu trong bộ nhớ).
 * Phương thức: save(), saveAll(), findByMaSite()
 */
public class ThongTinTonKhoDAO {

    private List<ThongTinTonKho> savedData;

    public ThongTinTonKhoDAO() {
        savedData = new ArrayList<>();
    }

    /**
     * Lưu một bản ghi tồn kho.
     */
    public void save(ThongTinTonKho tonKho) {
        savedData.add(tonKho);
        System.out.println("[ThongTinTonKhoDAO] Đã lưu: " + tonKho);
    }

    /**
     * Lưu danh sách bản ghi tồn kho.
     */
    public void saveAll(List<ThongTinTonKho> dsTonKho) {
        savedData.addAll(dsTonKho);
        System.out.println("[ThongTinTonKhoDAO] Đã lưu " + dsTonKho.size() + " bản ghi tồn kho.");
    }

    /**
     * Lấy toàn bộ dữ liệu đã lưu (dùng để kiểm tra trong test).
     */
    public List<ThongTinTonKho> findAll() {
        return new ArrayList<>(savedData);
    }

    /**
     * Tìm tồn kho theo mã Site.
     */
    public List<ThongTinTonKho> findByMaSite(int maSite) {
        List<ThongTinTonKho> result = new ArrayList<>();
        for (ThongTinTonKho tk : savedData) {
            if (tk.getMaSite() == maSite) {
                result.add(tk);
            }
        }
        return result;
    }

    /**
     * Xóa toàn bộ dữ liệu (reset cho test).
     */
    public void clear() {
        savedData.clear();
    }
}
