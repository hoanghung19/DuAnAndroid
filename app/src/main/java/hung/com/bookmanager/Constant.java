package hung.com.bookmanager;

public interface Constant {

    //bang USER

    //ten Bang
    String TABLE_USER = "User";

    //ten Cot
    String COLUMN_USER = "username";
    String COLUMN_PASSWORD = "Password";
    String COLUMN_PHONE = "Phone";
    String COLUMN_HOTEN = "hoTen";

    String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "(" +

            COLUMN_USER + " NVARCHAR(50) PRIMARY KEY," +

            COLUMN_PASSWORD + " NVARCHAR(50) NOT NULL ," +

            COLUMN_PHONE + " NCHAR(50) NOT NULL  ," +

            COLUMN_HOTEN + " NVARCHAR(50) " +

            ")";

    //bang BOOK

    //ten Bang
    String TABLE_BOOK = "Sach";

    //ten Cot
    String COLUMN_MASACH = "MaSach";
    String COLUMN_MATHELOAI = "MaTheLoai";
    String COLUMN_TIEUDE = "TieuDe";
    String COLUMN_TACGIA = "TacGia";
    String COLUMN_NXB = "NXB";
    String COLUMN_GIABIA = "giaBia";
    String COLUMN_SOLUONG = "soLuong";


    String CREATE_TABLE_BOOK = "CREATE TABLE " + TABLE_BOOK + "(" +

            COLUMN_MASACH + " NCHAR(5) NOT NULL PRIMARY KEY ," +

            COLUMN_MATHELOAI + " NCHAR(50) NOT NULL," +

            COLUMN_TIEUDE + " NVARCHAR(50)," +

            COLUMN_TACGIA + " NVARCHAR(50)," +

            COLUMN_NXB + " NVARCHAR(50) ," +

            COLUMN_GIABIA + " FLOAT NOT NULL," +

            COLUMN_SOLUONG + " INT NOT NULL" +

            ")";

    //bang TYPE

    //ten Bang
    String TABLE_TYPE_BOOK = "TypeBook";

    //ten Cot

    // CREATE TABLE TypeBook(MaTheLoai Char(5) PRIMARYKEY NOT NULL, typeName NVARCHAR(50) NOT NULL, Description NVARCHAR(255), Position INT)

    String COLUMN_ID = "MaTheLoai";
    String COLUMN_NAME = "TenTheLoai";
    String COLUMN_DES = "Description";
    String COLUMN_POS = "Position";

    String CREATE_TABLE_TYPE_BOOK = "CREATE TABLE " + TABLE_TYPE_BOOK + "(" +

            COLUMN_ID + " NCHAR(5) PRIMARY KEY NOT NULL ," +

            COLUMN_NAME + " NVARCHAR(50) NOT NULL," +

            COLUMN_DES + " NVARCHAR(255)," +

            COLUMN_POS + " INT" +

            ")";

    //bang Bill Table

    //ten Bang
    String TABLE_BILL = "Bill";

    //ten Cot

    String B_ID = "MaHoaDon";
    String B_DATE = "NgayMua";

    String CREATE_TABLE_BILL = "CREATE TABLE " + TABLE_BILL + "(" +
            B_ID + " NCHAR(7) PRIMARY KEY NOT NULL ," +
            B_DATE + " NOT NULL" +
            ")";


    //bang Hoa don chi tiet

    //ten Bang
    String TABLE_HOA_DON_CHI_TIET = "HoaDonChiTiet";

    //ten Cot
    String CL_ID = "MaHoaDonChiTiet";
    String CL_MA_HOA_DON = "MaHoaDon";
    String CL_MA_SACH = "MaSach";
    String CL_SO_LUONG = "SoLuongMua";

    String CREATE_TABLE_HOA_DON_CHI_TIET = "CREATE TABLE " + TABLE_HOA_DON_CHI_TIET + "(" +
            CL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            CL_MA_HOA_DON + " NCHAR(7) NOT NULL ," +
            CL_MA_SACH + " NCHAR(7) NOT NULL ," +
            CL_SO_LUONG + " INT NOT NULL" +
            ")";

}
