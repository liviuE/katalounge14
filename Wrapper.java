
import java.security.InvalidParameterException;

public class Wrapper {
	public static String wrap(String text, int columnNumber) {
		if (text == null || text.length() == 0 || columnNumber < 1) {
			throw new InvalidParameterException();
		}

		return wrapNotNull(text, columnNumber);
	}

	private static String wrapNotNull(String text, int columnNumber) {
		StringBuilder result = new StringBuilder(text);

		insertBreaks(result, columnNumber);

		return result.toString();
	}

	private static void insertBreaks(StringBuilder result, int columnNumber) {
		int nextSplitPostion = findNextSplitPosition(result, columnNumber, 0);

		while (nextSplitPostion > 0) {
			result.insert(nextSplitPostion, '\n');

			nextSplitPostion = findNextSplitPosition(result, columnNumber, nextSplitPostion + 1);
		}
	}

	private static int findNextSplitPosition(StringBuilder result, int columnNumber, int startIndex) {
		while (startIndex < result.length() && Character.isSpaceChar(result.charAt(startIndex))) {
			startIndex++;
		}

		if (startIndex >= result.length()) {
			return -1;
		}
		
		for (int column = 0; column < columnNumber; column++) {
			while (startIndex < result.length() && !Character.isSpaceChar(result.charAt(startIndex))) {
				startIndex++;
			}

			if (startIndex >= result.length()) {
				return -1;
			}
			
			while (startIndex < result.length() && Character.isSpaceChar(result.charAt(startIndex))) {
				startIndex++;
			}

			if (startIndex >= result.length()) {
				return -1;
			}
		}

		return startIndex;
	}
}
