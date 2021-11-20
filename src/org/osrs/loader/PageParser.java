package org.osrs.loader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageParser {
	public static String BASE_LINK = "runescape.com";
	public static String ARCHIVE_PARAM = "archive=gamepack";
	public static Pattern PARAMS = Pattern.compile("<param name=\"([^\\s]+)\"\\s+value=\"([^>]*)\">");
	public String currentLink;
	public String jarLocation;
	public HashMap<String, String> parameters = new HashMap<String, String>();
	public PageParser(){
		long start = System.currentTimeMillis();
		System.out.println("[ - Parameter Parser - ]");
		for(int i=0;i<20;++i){
			if(i==0 || !parseParams())
				System.out.println("Failed to connect, retrying...");
			else{
				System.out.println("Succesfully parsed parameters in : "+(System.currentTimeMillis()-start)+"ms");
				return;
			}
		}
		System.err.println("Connection issue, failed to connect to a server.");
	}
	private boolean parseParams() {
		try {
			System.out.println("Parsing parameters...");
			currentLink=getNewBaseLink();
			System.out.println("Current Link : "+currentLink);
			String HTML = getContent(currentLink);
			if(HTML.equals("")){
				return false;
			}
			Matcher param = PARAMS.matcher(HTML);
			while(param.find()){
				parameters.put(param.group(1), param.group(2));
				System.out.println(param.group(1) +":"+ param.group(2));
			}
			if(HTML.contains(ARCHIVE_PARAM)){
				jarLocation=currentLink+HTML.substring(HTML.indexOf(ARCHIVE_PARAM)+8, HTML.indexOf(".jar")+4);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	private String getContent(String link) {
		try{
			URL url = new URL(link);
			URLConnection uc = url.openConnection();
			uc.setConnectTimeout(5000);
	        BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
	        String src = "";
	        String inputLine;
	        while ((inputLine = in.readLine()) != null)
	        	src += inputLine;
	        in.close();
	        return src;
		}
		catch(@SuppressWarnings("unused") Exception e){
			return "";
		}
	}
	private String getNewBaseLink(){
		@SuppressWarnings("unused")
		int[] allWorlds = {1,2,3,4,5,6,8,9,10,
				11,12,13,14,16,17,18,19,20,
				21,22,26,27,28,29,30,33,
				34,35,36,38,41,42,43,44,
				45,46,49,50,51,52,53,54,57,
				58,59,60,61,62,65,66,67,68,
				69,70,73,74,75,76,77,78,81,
				82,83,84,93,94};
		int[] freeWorlds = {8, 16, 81, 82, 83, 84, 93, 94};
		int[] worlds = freeWorlds;//Change if your member
		int rand = new Random().nextInt(worlds.length);
		if(rand==5 || rand==6 || rand==8 || rand==13 || rand==14 || rand==16 || rand==21 || 
				rand==22 || rand==29 || rand==30 || rand==37 || rand==38 || rand==45 || 
				rand==46 ||	rand==53 || rand==54 || rand==61 || rand==62 || rand==69 || 
				rand==70 || rand==77 || rand==78 ||	rand==93 || rand==94){
			return "http://oldschool"+worlds[rand]+"a.runescape.com/";
		}
		return "http://oldschool"+worlds[rand]+".runescape.com/";
	}
}
