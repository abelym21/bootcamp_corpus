package corpus.service;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class XmlToTxt {

	public XmlToTxt() {
	}

	private String removeTag(String str) {		//xml�ױ׸� �����ϴ� �޼ҵ�.
		return str.replaceAll("\\<.*?\\>", "");
	}
		
	//�ױ׸� ������ xml ������ line by line���� �����鼭, �ױ׸� �����ϰ� line by line���� ���� ����� �ϴ� �޼ҵ�.
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
