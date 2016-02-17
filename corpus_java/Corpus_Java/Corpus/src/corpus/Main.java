package corpus;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import corpus.service.FrequencyCheck;
import corpus.service.Preprocess;
import corpus.service.XmlToTxt;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		XmlToTxt xtt = new XmlToTxt();		//xml 문서의 Tag를 제거하고 txt파일로 바꿔주는 클레스
		FrequencyCheck frequencyCheck = new FrequencyCheck();	//단어의 빈도수를 체크하고 파일출력을 하는 클레스

		xtt.makeXmlToTxt("test.xml","removedXmlTag.txt");		//xml파일을 input으로 넣어주면, 테그를 제거하고 txt파일을 output으로 내놓음.
		frequencyCheck.frequencyChecking("removedXmlTag.txt");		// 단어 빈도수를 체크할 txt파일을 넘겨주면, 단어빈도수 체크를 함.
		frequencyCheck.fileWrite("corpus.txt");		//output파일의 이름을 넘겨주면, 그 파일의 이름으로 frequencyChecking한 결과를 정렬해서 파일출력 해줌.

	}

}
