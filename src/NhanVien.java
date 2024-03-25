/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author khain
 */
public class NhanVien {

    /**
     * @param args the command line arguments
     */
    

    private String maNV;
    private String tenNV;
    private boolean gioiTinh;
    private int thamNien;

    public NhanVien() {
    }

    public NhanVien(String maNV, String tenNV, boolean gioiTinh, int thamNien) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.gioiTinh = gioiTinh;
        this.thamNien = thamNien;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public int getThamNien() {
        return thamNien;
    }

    public void setThamNien(int thamNien) {
        this.thamNien = thamNien;
    }

    public String getThuong() {
        if (this.thamNien > 12) {
            return "50000";
        }
        return "30000";
    }
    
}
