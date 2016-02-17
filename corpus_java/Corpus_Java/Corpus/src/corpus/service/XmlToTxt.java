package corpus.service;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class XmlToTxt {

	public XmlToTxt() {
	}

	private String removeTag(String str) {		//xml테그를 제거하는 메소드.
		return str.replaceAll("\\<.*?\\>", "");
	}
		
	//테그르 제거할 xml 파일을 line by line으로 읽으면서, 테그를 제거하고 line by line으로 파일 출력을 하는 메소드.
	public void makeXmlToTxt(String inputFileName, String outputFileName) throws IOException {
		Scanner sc = new Scanner(new File(inputFileName));
		FileWriter fw = new FileWriter(outputFileName);
		StringBuilder line = new StringBuilder();
		while (sc.hasNextLine()) {
			line.append(removeTag(sc.nextLine()));
			fw.write(line.toString());
			fw.write('\n');
			line.delete(0, line.length());
		}
		sc.close();
		fw.close();
	}

}
