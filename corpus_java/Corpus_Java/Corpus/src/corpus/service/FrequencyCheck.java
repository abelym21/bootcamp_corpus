package corpus.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class FrequencyCheck {

	private Map<String, Integer> map = new TreeMap<String, Integer>();		//Treemap 사용
	private Preprocess preprocess = new Preprocess();		//전처리 클레스
	private int count = 0;

	public FrequencyCheck() {
	}

	//frequencyChecking의 결과를 정렬하여 파일 출력을 해주는 메소드.
	public void fileWrite(String outputFileName) throws IOException {
		FileWriter fw = new FileWriter(outputFileName);
		Iterator<String> iterator = map.keySet().iterator();
		StringBuilder next = new StringBuilder();
		while(iterator.hasNext()){
			next.append(iterator.next());
			fw.write( next + " : " +map.get(next.toString()));
			fw.write('\n');
			next.delete(0, next.length());
		}
		fw.close();
	}

	//단어 빈도수 체크를 하는 메소드.
	public void frequencyChecking(String inputFileName)
			throws FileNotFoundException {
		Scanner sc = new Scanner(new File(inputFileName));
		StringBuilder word = new StringBuilder();		//파일을 word by word로 읽음. 단어의 개수가 많으므로 String이 아닌 StringBuilder 사용.
		while (sc.hasNext()) {
			word.append(preprocess.preprocessing(sc.next()));		//읽어온 단어를 preprocess 함.
			if (word.length() == 0) {		//preprocess한 단어가 null일 경우(관사/비동사/특수문자) null이 반환되므로 이러할 경우 continue;
				continue;
			}
			if (map.containsKey(word.toString())) {		//이미 단어가 있을 경우 그 단어(key)의 빈도수(value)++
				count = map.get(word.toString());
				map.put(word.toString(), count + 1);
			} else {		//새로운 단어일 경우, 단어(key)와 빈도수(value)추가. 
				map.put(word.toString(), 1);
			}
			word.delete(0, word.length());		//다음 단어를 받기 위해, StringBuilder안의 단어 제거.
		}
		sc.close();
	}
}
