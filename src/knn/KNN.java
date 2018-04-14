package knn;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KNN {
	//data
	static class Feature_var{
		private int label;
		int [] pixels;
	}

	private static List<Feature_var> readFile(String file)throws IOException {
		List<Feature_var> features = new ArrayList<Feature_var>();
		BufferedReader reader = new BufferedReader(new FileReader(file));
		try{
			String line = reader.readLine();
			while((line =reader.readLine()) != null){
				String[] tokens = line.split(",");
				Feature_var feature = new Feature_var();
				feature.label =Integer.parseInt(tokens[0]);
				feature.pixels = new int[tokens.length -1];
				for(int i =1;i<tokens.length;i++){
					feature.pixels[i-1] = Integer.parseInt(tokens[i]);
				}
				features.add(feature);
			}
		}finally {
			reader.close();
		}
		return features;
	}

	// euclidean distance
	private static int distance(int[] a,int[] b){
		int sum = 0;
		for(int i = 0 ; i < a.length ; i++){
			sum += (a[i] - b[i]) * (a[i] - b[i]);
		}
		return (int)Math.sqrt(sum);
	}

	private static int classify(List<Feature_var> trainSet , int[] pixels){
		int label = 0,bestDistance = Integer.MAX_VALUE;
		for(Feature_var f:trainSet){
			int dist = distance(f.pixels, pixels);
			if(dist <bestDistance){
				bestDistance = dist;
				label = f.label;
			}
		}
		return label;
	}
	public static void main(String[] argv) throws IOException {
		List<Feature_var> traininSet = readFile("traininsample.csv");
		List<Feature_var> validationSet = readFile("validationsample.csv");
		int counter = 0;
		for(Feature_var sample:validationSet) {
			if(classify(traininSet, sample.pixels) == sample.label) counter++;
		}
		System.out.println("Accuracy: " + (double)counter / validationSet.size() * 100 + "%");
	}
}
