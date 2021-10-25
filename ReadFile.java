
import java.util.*;
import java.io.*;
/**
 * @author Chiara
 *
 */
public class ReadFile {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args){
		FileReader reader = null;
		try {
			reader = new FileReader("file.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scanner input = new Scanner(reader);
		while(input.hasNextLine())
		{
			String line = input.nextLine();
			System.out.println(line);
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
