package search.tokenizers;

import search.Document;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class SpaceTokenizer implements Tokenizer {
    private File file;
    private List<String> tokens = new ArrayList<String>();

    public SpaceTokenizer(Document document) {
        this.file = document.getFile();
        Scanner scanner = null;
        try {
            scanner = new Scanner(new FileInputStream(file));
            while (scanner.hasNext()) {
                tokens.add(scanner.next());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

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
}
