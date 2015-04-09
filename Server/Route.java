
public class Route {
	private String rname;
	private String color;
	private String savepoints;
	private String lines;
	private String curves;
	
	public Route(String rname, String color, String savepoints, String lines, String curves) {
		this.rname = rname;
		this.color = color;
		this.savepoints = savepoints;
		this.lines = lines;
		this.curves = curves;
	}
	
	public String getRname() {
		return rname;
	}
	public void setRname(String rname) {
		this.rname = rname;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getSavepoints() {
		return savepoints;
	}
	public void setSavepoints(String savepoints) {
		this.savepoints = savepoints;
	}
	public String getLines() {
		return lines;
	}
	public void setLines(String lines) {
		this.lines = lines;
	}
	public String getCurves() {
		return curves;
	}
	public void setCurves(String curves) {
		this.curves = curves;
	}
}
