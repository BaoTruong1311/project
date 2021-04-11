public class SmartPhone {
private String MaSp,TenSP,hangSP;
private double Vrom, Vram;
private double gia;
public SmartPhone(String maSp, String tenSP, String hangSP, double vrom,
		double vram, double gia) {
	super();
	MaSp = maSp;
	TenSP = tenSP;
	this.hangSP = hangSP;
	Vrom = vrom;
	Vram = vram;
	this.gia = gia;
}
public String getMaSp() {
	return MaSp;
}
public void setMaSp(String maSp) {
	MaSp = maSp;
}
public String getTenSP() {
	return TenSP;
}
public void setTenSP(String tenSP) {
	TenSP = tenSP;
}
public String getHangSP() {
	return hangSP;
}
public void setHangSP(String hangSP) {
	this.hangSP = hangSP;
}
public double getVrom() {
	return Vrom;
}
public void setVrom(double vrom) {
	Vrom = vrom;
}
public double getVram() {
	return Vram;
}
public void setVram(double vram) {
	Vram = vram;
}
public double getGia() {
	return gia;
}
public void setGia(double gia) {
	this.gia = gia;
}
}
