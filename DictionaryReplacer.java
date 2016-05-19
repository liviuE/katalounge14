import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Map;

public class DictionaryReplacer {
	public static String replace(String text, Map<String, String> dictionary) {
		if (text == null || text.length() == 0 || dictionary == null) {
			throw new InvalidParameterException();
		}
		
		StringBuilder result = new StringBuilder(text);
		
		replaceDictionaryWords(result, dictionary);
		
		return result.toString();
	}

	private static void replaceDictionaryWords(StringBuilder result, Map<String, String> dictionary) {
		int nextIndex;
		int[] toBeReplaced = findNextWord(result, 0);
			
		while (toBeReplaced != null) {
			nextIndex = replaceWord(toBeReplaced[0], toBeReplaced[1], result, dictionary);
			
			toBeReplaced = findNextWord(result, nextIndex);
		}
	}

	private static int replaceWord(int wordStartIndex, int wordLength, StringBuilder text, Map<String, String> dictionary) {
		String word = text.substring(wordStartIndex + 1, wordStartIndex + wordLength - 1);
		
		if (dictionary.containsKey(word)) {
			text.delete(wordStartIndex, wordStartIndex + wordLength);
			
			text.insert(wordStartIndex, dictionary.get(word));
			return wordStartIndex + dictionary.get(word).length();
		}
		
		return wordStartIndex + 1;
	}

	private static int[] findNextWord(StringBuilder text, int startIndex) {
		while (startIndex < text.length() && text.charAt(startIndex) != '$') {
			startIndex++;
		}
		
		if (startIndex >= text.length()) {
			return null;
		}
		
		int endIndex = startIndex + 1;
		
		while (endIndex < text.length() && text.charAt(endIndex) != '$') {
			endIndex++;
		}
		
		if (endIndex >= text.length()) {
			return null;
		}
		
		int result[] = new int[2];
		result[0] = startIndex;
		result[1] = endIndex - startIndex + 1;
		
		return result;
	}
}
