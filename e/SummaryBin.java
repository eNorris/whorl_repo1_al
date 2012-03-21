package edu.mst.cs206.e;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;


public class SummaryBin {
	public Vector<String> name;
	public Vector<Boolean> isClass;
	
	public SummaryBin(){
		name = new Vector<String>();
		isClass = new Vector<Boolean>();
	}
	
	public boolean parseSummary(String filename) throws IOException{
		
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		
		String line = null;
		
		while((line=reader.readLine()) != null){
			
			String[] tokens = line.split(" ");
			
			if(tokens.length != 2){
				System.out.print("ERROR: SummaryBin::parseSummary(): did not get 2 tokens\n\n");
				return false;
			}
			
			if(tokens[0].equals("Class")){
				isClass.add(true);
			}else{
				isClass.add(false);
			}
			
			name.add(tokens[1]);
		}
		
		return true;
	}
	
	public String toString(){
		String toReturn = new String();
		
		for(int i = 0; i < name.size(); i++){
			if(isClass.elementAt(i)){
				toReturn += "Class ";
			}else{
				toReturn += "Method ";
			}
			toReturn += (name.elementAt(i) + "\n");
		}
		
		return toReturn;
	}
}
