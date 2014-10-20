import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/* Name : Geetish Nayak
 * This class is used to calculate cosine similarity
 * of two documents
 * Date of creation : 10/03/2013
 * Date of last modification : 10/03/2013
 * 
 */
public class Similarity {
	// Map
	private Map<String,Integer> countStrings;
	private int countLines;
	
	/*
	 *  Constructor of  Similarity
	 *  @param The string for whom map is to be made
	 */
	
	public Similarity(String str){
		countStrings = new HashMap<String,Integer>();
		addToMap(str);
		countLines = 0;
	}
	
	/*
	 * Overloaded Constructor
	 * @param newFile The file whose map we wish to create
	 * 
	 */
	public Similarity(File newFile){
		countStrings = new HashMap<String,Integer>();
		if(newFile!=null){
			readFile(newFile);
		}
		else{
			try {
				throw new Exception("File is Null");
			} catch (Exception e) {
				e.getMessage();
			}
		}
		
	}
	
	/* This is a helper function which is used to read the 
	 * lines from the file
	 * @param File The newFile
	 * 
	 */
	private void readFile(File newFile){
		Scanner scannerObj = null;
		try {
			scannerObj = new Scanner(newFile);
			while(scannerObj.hasNext()){
				String line = scannerObj.nextLine();
				addToMap(line);
				countLines++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally{
			scannerObj.close();
		}	
	}
	
	/*
	 * This function is used to store words into the map
	 * @param line The line from the file
	 * 
	 */
	 private void addToMap(String line){ 
		 String[] words= line.split("\\W+");
		 for(String word: words){
			 word=word.toLowerCase();
			 if(word.matches("^[a-z]+$")){
				 word=word.toLowerCase().trim();
				 if(word!=null && word.length()>0){
					 if(countStrings.containsKey(word)){
						 int count= countStrings.get(word);
						 count++;
						 countStrings.put(word, count);
					 }
					 else{
						 countStrings.put(word, 1);
					 }
				 }
			 }
		 }
	 }
	 
	 /* This function returns the number of  lines
	  * @return the number of lines
	  */
	 public int numberOfLines(){
		 return countLines;
	 }
	 
	 /* Function to get the number of words
	  * @return The number of words
	  * 
	  */
	 public int numOfWords(){
		 int count_words = 0;
		 for(Integer i:countStrings.values()){
			 count_words+=i;
		 }
		 return count_words;
	 }
	 
	 /* function to get number of unique words
	  * @return number of unique words
	  * 
	  */
	 public int numOfWordsNoDups(){
		 return countStrings.size();
	 }
	 
	 /* Function to get Euclidean norm
	  * @return euclidean norm
	  * 
	  */
	  public double euclideanNorm(){
		 long euclideanSquare = 0;
		 for(Integer i:countStrings.values()){
			  euclideanSquare +=Math.pow(i, 2);
		 }
	     return Math.sqrt(euclideanSquare);
	  }
	  
	  /* Function to calculate dot product
	   * @param text2Map The second map
	   * @return the doc product of 2 vectors
	   * 
	   * 
	   */
	   public double dotProduct(Map<String,Integer> text2Map){
		   double dot_prod=0;
		   for(String i : countStrings.keySet()){
			   if(text2Map.containsKey(i)){
				   dot_prod += countStrings.get(i) * text2Map.get(i);
			   }
		   }
		   return dot_prod;
	   }
	   
	   /* Function to find the distance between 2 vectors
	    * @param text2Map the second map
	    * @return the distance
	    */
	   public double distance(Map<String,Integer> text2Map){
		   double euclideanDistMap1 = this.euclideanNorm();
		  Map<String,Integer> tempMap = this.countStrings;
		   this.countStrings = text2Map;
		   // Mistake
		   double euclideanDistMap2 = this.euclideanNorm();
		   //Reassigning
		   this.countStrings =tempMap;
		   double dotProduct= this.dotProduct(text2Map);
		   if(dotProduct==0) return Math.PI/2;
		   double distance = Math.acos((dotProduct /(euclideanDistMap1*euclideanDistMap2)));
		   return distance;
		  
		   
	   }
	   
	   /* This function is used to return the map
	    * @return the map
	    * 
	    */
	   public Map<String,Integer> getMap(){
		   return this.countStrings;
	   }
	   
	
}