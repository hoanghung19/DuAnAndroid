package hung.com.bookmanager.model;

public class HoaDonChiTiet {
    public int maHDCT;
    public String maHD;
    public String maSach;
    public int soLuongMua;

    public HoaDonChiTiet(){

    }

    public HoaDonChiTiet(int maHDCT, String maHD, String maSach, int soLuongMua) {
        this.maHDCT = maHDCT;
        this.maHD = maHD;
        this.maSach = maSach;
        this.soLuongMua = soLuongMua;
    }

    public int getMaHDCT() {
        return maHDCT;
    }

    public void setMaHDCT(int maHDCT) {
        this.maHDCT = maHDCT;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public int getSoLuongMua() {
        return soLuongMua;
    }

    public void setSoLuongMua(int soLuongMua) {
        this.soLuongMua = soLuongMua;
    }
}
