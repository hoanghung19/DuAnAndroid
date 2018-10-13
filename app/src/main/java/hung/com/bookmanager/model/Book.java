package hung.com.bookmanager.model;

public class Book {
    private String maSach;
    private String maTheLoai;
    private String tieuDe;
    private String tacGia;
    private String nXB;
    private Float giaBia;
    private int soLuong;

    public Book(){

    }

    public Book(String maSach, String maTheLoai, String tieuDe, String tacGia, String nXB, Float giaBia, int soLuong) {
        this.maSach = maSach;
        this.maTheLoai = maTheLoai;
        this.tieuDe = tieuDe;
        this.tacGia = tacGia;
        this.nXB = nXB;
        this.giaBia = giaBia;
        this.soLuong = soLuong;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getMaTheLoai() {
        return maTheLoai;
    }

    public void setMaTheLoai(String maTheLoai) {
        this.maTheLoai = maTheLoai;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public String getnXB() {
        return nXB;
    }

    public void setnXB(String nXB) {
        this.nXB = nXB;
    }

    public Float getGiaBia() {
        return giaBia;
    }

    public void setGiaBia(Float giaBia) {
        this.giaBia = giaBia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
