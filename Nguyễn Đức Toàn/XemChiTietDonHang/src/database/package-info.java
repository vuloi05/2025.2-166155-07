// ============================================================
// Tac gia    : Nguyen Duc Toan - 20235846
// Ten file   : package-info.java
// Goi        : database (tang Database)
// Mo ta      : Goi placeholder cho tang Database trong Bieu do phu thuoc goi
//              BT5 (muc 12) & BT6 (muc 14). Hien chua co lop nao vi du an dung
//              du lieu gia lap (mock) trong tang DataAccess; khi noi CSDL that
//              (vd MySQL), cac lop ket noi/CSDL se duoc dat tai day.
//              Quan he "DataAccess ..> Database" trong bieu do la phu thuoc
//              khai niem (DAO la cong duy nhat xuong CSDL).
// Phu thuoc  : (khong)
// ============================================================

/**
 * Tang <b>Database</b> (CSDL) - tuong ung goi Database trong Bieu do phu thuoc
 * goi BT5 (muc 12) va BT6 (muc 14) cua SRS.
 *
 * <p>Goi nay hien <b>de trong</b> (chua co lop) dung nhu trong bieu do: du an
 * dang dung du lieu gia lap (mock data) o tang DataAccess. Khi trien khai ket
 * noi CSDL that, cac lop nhu ket noi JDBC / cau hinh DataSource se nam o day,
 * va chi tang DataAccess (DAO) moi truy cap xuong tang nay.</p>
 */
package database;
