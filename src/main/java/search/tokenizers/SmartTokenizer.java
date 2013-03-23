package search.tokenizers;

import search.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * @author Konovalov_Nik
 */
public class SmartTokenizer implements Tokenizer {
    private File file;
    private List<String> tokens = new ArrayList<String>();


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
                Collections.addAll(tokens, normalize(line).split("\\s"));
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
