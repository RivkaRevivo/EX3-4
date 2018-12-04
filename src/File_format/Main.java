package File_format;

public class Main {

	//a commit to test commit and push by sela

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Csv2kml no=new Csv2kml("C:\\file\\WigleWifi_20171201110209.csv");
		Csv2kml no1=new Csv2kml("C:\\file\\WigleWifi_20171203085618.csv");
		//no.read();
		no.write();
		no1.write();
		
	}

}
