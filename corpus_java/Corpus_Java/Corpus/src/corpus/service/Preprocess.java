package corpus.service;

public class Preprocess {
	public Preprocess(){}

	public String preprocessing(String word) {		//전처리 클레스에서 유일하게 밖으로 호출되는 클레스.
		word = makeLowerCase(word);		//단어의 모든 글자를 소문자로 변경 (문서 내에   소문자 양> 대문자 양  이므로)
		if (isSign(word)) {			//읽어온 단어가 순수하게 특수문자로만 이루어져 있을 경우 return null;
			return null;
		} else if (isLetter(word)) {		//읽어온 단어가 순수하게 알파벳으로만 이루어져 있을 경우
			if (isArticle(word)) {				//관사 인지 체크, 관사면 return null;
				return null;
			} else if (isBeVerb(word)) {		//be동사 인지 체크, be동사 이면 return null;
				return null;
			}
			return word;
		}else{
			word = removeSign(word);	//읽어온 단어에 특수부호와 알파벳이 섞여 있는 경우, 특수부호 제거.
			if (isArticle(word)) {				//관사 인지 체크, 관사면 return null;
				return null;
			} else if (isBeVerb(word)) {		//be동사 인지 체크, be동사 이면 return null;
				return null;
			}
			return word;
		}
	}

	//관사인지 체크.
	private boolean isArticle(String str) {
		if (str.equals("a") || str.equals("an") || str.equals("the")) {
			return true;
		} else {
			return false;
		}
	}

	//be동사인지 체크.
	private boolean isBeVerb(String str) {
		if (str.equals("is") || str.equals("was") || str.equals("are") || str.equals("were") || str.equals("been")
				|| str.equals("am")) {
			return true;
		} else {
			return false;
		}
	}

	//순수하게 알파벳으로만 이루어져 있는 지 체크.
	private boolean isLetter(String str) {
		if (str.matches("[a-z]+")) {
			return true;
		} else {
			return false;
		}
	}

	//순수하게 특수부호로만 이루어져 있는 지 체크.
	private boolean isSign(String str) {
		if (str.matches("[-:;\"\'?!\\.,0-9]+")) {
			return true;
		} else {
			return false;
		}
	}

	//소문자로 바꿈.
	private String makeLowerCase(String str) {
		return str.toLowerCase();
	}

	// 문자와 특수문자가 함께 있을때, 특수문자 제거.
	private String removeSign(String str) {
		int startpoint = 0;
		int endpoint = str.length() - 1;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			if (String.valueOf(str.charAt(i)).matches("[a-z]")) {		//단어에서 알파벳이 시작 되는 위치에서 멈춤.
				startpoint = i;
				break;
			}
		}
		for (int i = str.length() - 1; i >= startpoint; i--) {
			if (String.valueOf(str.charAt(i)).matches("[a-z]")) {		//단어에서 알파벳이 끝나는 위치에서 멈춤.
				endpoint = i;
				break;
			}
		}
		for (int i = startpoint; i <= endpoint; i++) {		//단어에서 알파벳이 시작하고 끝나는 위치까지 StringBuilder에 담음.

			sb.append(str.charAt(i));
		}
		return sb.toString();		//결과적으로 단어 앞뒤의  특수부호를 제거한 단어를 반환
	}

}
