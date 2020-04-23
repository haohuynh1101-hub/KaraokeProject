package haohuynh.com.models;

public class BaiHat {
    private String ma;
    private  String ten;
    private String caSi;
    private int thich;

    public BaiHat() {
    }

    public BaiHat(String ma, String ten, String caSi, int thich) {
        this.ma = ma;
        this.ten = ten;
        this.caSi = caSi;
        this.thich = thich;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getCaSi() {
        return caSi;
    }

    public void setCaSi(String caSi) {
        this.caSi = caSi;
    }

    public int getThich() {
        return thich;
    }

    public void setThich(int thich) {
        this.thich = thich;
    }
}
