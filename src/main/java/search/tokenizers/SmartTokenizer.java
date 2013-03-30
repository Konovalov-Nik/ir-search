package search.tokenizers;

import com.google.common.collect.Sets;
import search.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author Konovalov_Nik
 */
public class SmartTokenizer implements Tokenizer {
    private File file;
    private List<String> tokens = new ArrayList<String>();

    public static Set<String> stopWords = Sets.newHashSet(
            "a", "the", "in", "on", "of", "as", "is", "are", "am", "has", "have", "had", "will", "would", "should");

    static {
        try {
            Scanner scanner = new Scanner("stopWords");
            while (scanner.hasNext()) {
                String word = scanner.next();
                stopWords.add(word);
            }
        } catch (Exception e) {
            // no op
        }

    }
    public static String normalize(String line) {
        return line
                .replaceAll(" - ", " ")
                .replaceAll("-", "")
                .replaceAll("[\\.\\,\\?\\!\\:\\;\\'\\\\/]", " ")
                .toLowerCase();
    }

    private int lastTokenIdx = 0;

    @Override
    public String nextToken() {
        return tokens.get(lastTokenIdx++);
    }

    @Override
    public boolean hasNextToken() {
        return lastTokenIdx < tokens.size();
    }

    @Override
    public void setDocument(Document document) {
        this.file = document.getFile();
        init();
    }

    @Override
    public void init() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] words = normalize(line).split("\\s");
                for (String word : words) {
                    if (!stopWords.contains(word)) {
                        tokens.add(word);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

}
