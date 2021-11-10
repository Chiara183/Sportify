package com.example.sportify;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ReadFile {

	public static void main(String[] args){
		FileReader reader = null;
		try {
			reader = new FileReader("file.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assert reader != null;
		Scanner input = new Scanner(reader);
		while(input.hasNextLine())
		{
			String line = input.nextLine();
			//logger.log(line);
		}
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		input.close();
	}

}
