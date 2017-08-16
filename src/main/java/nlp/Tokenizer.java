package nlp;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;

/**
 * Wrapper class that uses the stanford core nlp PTBTokenizer.
 * @author jbergeron
 *
 */
public class Tokenizer {
	
	public static List<String> tokenize(String text){
		List<String> tokens = new ArrayList<String>();
		PTBTokenizer<CoreLabel> tokenizer = new PTBTokenizer<>(new StringReader(text), new CoreLabelTokenFactory(), "");
		while(tokenizer.hasNext()) {
			tokens.add(tokenizer.next().word());
		}
		return tokens;
	}
	
	public static void main(String[] args) {
		System.out.println(Tokenizer.tokenize("Wow this is tokenized").stream().collect(Collectors.joining("|")));
	}
}
