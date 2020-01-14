package nowy;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject; 
import org.json.simple.parser.*; 

public class JsonParser {
	
	double[] elevation;
	double[] distance;
	Reader reader;
	JsonParser(String name){
		try {
			reader = new FileReader(name+".json");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void findPoints(int size) throws IOException, ParseException{
		int index = 0;
		
		elevation = new double[size+1];
		distance = new double[size+1];
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = (JSONObject) parser.parse(reader);

		JSONArray points = (JSONArray) jsonObject.get("geoPoints");
		for(Object o: points){
			if ( o instanceof JSONObject ) {
				JSONObject json = (JSONObject) o;
	            distance[index] = (double) json.get("distance");
		        elevation[index] = (double) json.get("elevation");
			}
			if(++index == size+1) 
				break;
		}
		
	}
}
