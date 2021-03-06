package Algorithms;

import GIS.Element;
import GIS.GIS_element;
import GIS.Layer;
import GIS.Project;

import java.io.*;
import java.util.Iterator;

public class MultiCSV
{

	/**
	 * @authors
	 * This function creates the KML file and writes the header.
	 */
	
	public void print(File MultiCSV)
	{
		try 
		{
			Print = new PrintWriter(MultiCSV);
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			return;
		}
		StringBuilder Builder = new StringBuilder();
		Builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		Builder.append("<kml xmlns=\"http://www.opengis.net/kml/2.2\">");
		Builder.append("<Document>");
		Builder.append("<Style id=\"red\"><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/red-dot.png</href></Icon></IconStyle></Style>");
		Builder.append("<Style id=\"yellow\"><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/yellow-dot.png</href></Icon></IconStyle></Style>");
		Builder.append("<Style id=\"green\"><IconStyle><Icon><href>http://maps.google.com/mapfiles/ms/icons/green-dot.png</href></Icon></IconStyle></Style>");
		Print.write(Builder.toString());
	}

	/*
	 * This function Writes down the elements from the Layer into the KML file.
	 */

	public void write(Layer layer, File MultiCSV)
	{
		StringBuilder Builder = new StringBuilder();
		Iterator<GIS_element> it = layer.iterator();
		while(it.hasNext()) {
			Element Replace=(Element) it.next();
			Builder.append("<Placemark>");
			Builder.append("<name>");
			Builder.append(Replace.getName());
			Builder.append("</name>");
			Builder.append("<description>");
			Builder.append("Ethernet: " + Replace.getDescript() + "<br/>");
			Builder.append("Date: " + Replace.getTime() + "<br/>");
			Builder.append("Time Stamp: " + Replace.getData().toString());
			Builder.append("</description>");
			Builder.append("<styleUrl>");
			Builder.append("</styleUrl>");
			Builder.append("<Point>");
			Builder.append("<coordinates>");
			Builder.append(Replace.getPoint());
			Builder.append("</coordinates>");
			Builder.append("</Point>");
			Builder.append("</Placemark>");
		}
		Print.write(Builder.toString());
	}
	
	/*
	 * This function closes the KML file and sends it to the directory path you declared.
	 */
	
	public void Close(File MultiCSV)
	{
		StringBuilder Builder = new StringBuilder();
		Builder.append("</Document>");
		Builder.append("</kml>");
		Print.write(Builder.toString());
		Print.close();
		System.out.println("Congrats! You did it!");
	}

	/*
	 * This function reads the CSV file.
	 * For each row in the file it creates a new element and inserts it into the Layer Collection.
	 * At the end of the function, it takes the Layer and inserts it into the Project Collection.
	 * @return this function returns the Layer and sends it to the Write function.
	 */
	
	public Layer read(File Csv) {
		String L = "";
		String S = ",";
		Layer _List = new Layer();
		try (BufferedReader Buffer = new BufferedReader(new FileReader(Csv))) {
			Buffer.readLine();
			Buffer.readLine();
			while ((L = Buffer.readLine()) != null) {
				String[] Data = L.split(S);
				Element place = new Element();
				place.ElementSet(Data[1], Data[10], Data[7]+","+Data[6]+","+Data[8], Data[3]);
				_List.add(place);
			}
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return _List;
	}

	/* 
	 * This function gets the location of the CSV files.
	 * In order to do that you need to define your CSV folder path.
	 * For example: /home/eli/eclipse-workspace/OOP_EX2-EX4-master/data
	 * This function accepts .csv files ONLY!
	 */
	public Project ListOfCSV(File MultiCSV , String FolderLocation)
	{
		Project _pList = new Project();
		File[] files = new File(FolderLocation).listFiles(
				new FilenameFilter() { @Override public boolean accept(File dir, String name) { return name.endsWith(".csv"); } });
		print(MultiCSV);
		for(int i=0; i<files.length; i++) {
			File name = files[i];
			Layer temp = read(name);
			write(temp, MultiCSV);
			_pList.add(temp);
		}
		Close(MultiCSV);
		return _pList;
	}

	//**********Private Methods**********//

	private PrintWriter Print;

	//**********Constructor**********//

	public MultiCSV() {}

	//**********Getters**********//

}
