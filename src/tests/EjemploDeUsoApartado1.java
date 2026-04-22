package tests;

import java.util.Collections;

import dataset.Dataset;
import features.Feature;
import features.Person;
import features.PersonFeaturizer;

public class EjemploDeUsoApartado1 {
	public static void main(String[] args) {
		Dataset<Person> dataSet = buildDataSet(); System.out.println("dataset: "+dataSet);
		dataSet.removeDuplicates();
		System.out.println("dataset w/o duplicates: "+dataSet);
		Feature<Integer> ages = dataSet.feature("age");
		System.out.println("Ages: "+ages);
		
		Collections.sort(ages);
		
		System.out.println("Ages sorted: "+ages); System.out.println("Min age: "+ages.min());
		System.out.println("Gender distribution: "+dataSet.feature("gender").distribution());
	}
	
	public static Dataset<Person> buildDataSet() {
	Person people [] = { 
			new Person("Pedro", 66, 75, 180, true), 
			new Person("Ana", 47, 54, 158, false),
			new Person("Luis", 34, 75, 176, true), 
			new Person("Rosa", 47, 54, 158, false)
	};

	
	Dataset<Person> dataSet = new Dataset<>(new PersonFeaturizer()); // A Featurizer for Person objects 
	dataSet.addAll(people);
	return dataSet;
	}
}
