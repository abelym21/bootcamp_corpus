package corpus.service;

public class Preprocess {
	public Preprocess(){}

	public String preprocessing(String word) {		//��ó�� Ŭ�������� �����ϰ� ������ ȣ��Ǵ� Ŭ����.
		word = makeLowerCase(word);		//�ܾ��� ��� ���ڸ� �ҹ��ڷ� ���� (���� ����   �ҹ��� ��> �빮�� ��  �̹Ƿ�)
		if (isSign(word)) {			//�о�� �ܾ �����ϰ� Ư�����ڷθ� �̷���� ���� ��� return null;
			return null;
		} else if (isLetter(word)) {		//�о�� �ܾ �����ϰ� ���ĺ����θ� �̷���� ���� ���
			if (isArticle(word)) {				//���� ���� üũ, ����� return null;
				return null;
			} else if (isBeVerb(word)) {		//be���� ���� üũ, be���� �̸� return null;
				return null;
			}
			return word;
		}else{
			word = removeSign(word);	//�о�� �ܾ Ư����ȣ�� ���ĺ��� ���� �ִ� ���, Ư����ȣ ����.
			if (isArticle(word)) {				//���� ���� üũ, ����� return null;
				return null;
			} else if (isBeVerb(word)) {		//be���� ���� üũ, be���� �̸� return null;
				return null;
			}
			return word;
		}
	}

	//�������� üũ.
	private boolean isArticle(String str) {
		if (str.equals("a") || str.equals("an") || str.equals("the")) {
			return true;
		} else {
			return false;
		}
	}

	//be�������� üũ.
	private boolean isBeVerb(String str) {
		if (str.equals("is") || str.equals("was") || str.equals("are") || str.equals("were") || str.equals("been")
				|| str.equals("am")) {
			return true;
		} else {
			return false;
		}
	}

	//�����ϰ� ���ĺ����θ� �̷���� �ִ� �� üũ.
	private boolean isLetter(String str) {
		if (str.matches("[a-z]+")) {
			return true;
		} else {
			return false;
		}
	}

	//�����ϰ� Ư����ȣ�θ� �̷���� �ִ� �� üũ.
	private boolean isSign(String str) {
		if (str.matches("[-:;\"\'?!\\.,0-9]+")) {
			return true;
		} else {
			return false;
		}
	}

	//�ҹ��ڷ� �ٲ�.
	private String makeLowerCase(String str) {
		return str.toLowerCase();
	}

	// ���ڿ� Ư�����ڰ� �Բ� ������, Ư������ ����.
	private String removeSign(String str) {
		int startpoint = 0;
		int endpoint = str.length() - 1;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			if (String.valueOf(str.charAt(i)).matches("[a-z]")) {		//�ܾ�� ���ĺ��� ���� �Ǵ� ��ġ���� ����.
				startpoint = i;
				break;
			}
		}
		for (int i = str.length() - 1; i >= startpoint; i--) {
			if (String.valueOf(str.charAt(i)).matches("[a-z]")) {		//�ܾ�� ���ĺ��� ������ ��ġ���� ����.
				endpoint = i;
				break;
			}
		}
		for (int i = startpoint; i <= endpoint; i++) {		//�ܾ�� ���ĺ��� �����ϰ� ������ ��ġ���� StringBuilder�� ����.

			sb.append(str.charAt(i));
		}
		return sb.toString();		//��������� �ܾ� �յ���  Ư����ȣ�� ������ �ܾ ��ȯ
	}

}
