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

	private Map<String, Integer> map = new TreeMap<String, Integer>();		//Treemap ���
	private Preprocess preprocess = new Preprocess();		//��ó�� Ŭ����
	private int count = 0;

	public FrequencyCheck() {
	}

	//frequencyChecking�� ����� �����Ͽ� ���� ����� ���ִ� �޼ҵ�.
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

	//�ܾ� �󵵼� üũ�� �ϴ� �޼ҵ�.
	public void frequencyChecking(String inputFileName)
			throws FileNotFoundException {
		Scanner sc = new Scanner(new File(inputFileName));
		StringBuilder word = new StringBuilder();		//������ word by word�� ����. �ܾ��� ������ �����Ƿ� String�� �ƴ� StringBuilder ���.
		while (sc.hasNext()) {
			word.append(preprocess.preprocessing(sc.next()));		//�о�� �ܾ preprocess ��.
			if (word.length() == 0) {		//preprocess�� �ܾ null�� ���(����/�񵿻�/Ư������) null�� ��ȯ�ǹǷ� �̷��� ��� continue;
				continue;
			}
			if (map.containsKey(word.toString())) {		//�̹� �ܾ ���� ��� �� �ܾ�(key)�� �󵵼�(value)++
				count = map.get(word.toString());
				map.put(word.toString(), count + 1);
			} else {		//���ο� �ܾ��� ���, �ܾ�(key)�� �󵵼�(value)�߰�. 
				map.put(word.toString(), 1);
			}
			word.delete(0, word.length());		//���� �ܾ �ޱ� ����, StringBuilder���� �ܾ� ����.
		}
		sc.close();
	}
}
