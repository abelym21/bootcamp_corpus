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

		XmlToTxt xtt = new XmlToTxt();		//xml ������ Tag�� �����ϰ� txt���Ϸ� �ٲ��ִ� Ŭ����
		FrequencyCheck frequencyCheck = new FrequencyCheck();	//�ܾ��� �󵵼��� üũ�ϰ� ��������� �ϴ� Ŭ����

		xtt.makeXmlToTxt("test.xml","removedXmlTag.txt");		//xml������ input���� �־��ָ�, �ױ׸� �����ϰ� txt������ output���� ������.
		frequencyCheck.frequencyChecking("removedXmlTag.txt");		// �ܾ� �󵵼��� üũ�� txt������ �Ѱ��ָ�, �ܾ�󵵼� üũ�� ��.
		frequencyCheck.fileWrite("corpus.txt");		//output������ �̸��� �Ѱ��ָ�, �� ������ �̸����� frequencyChecking�� ����� �����ؼ� ������� ����.

	}

}
