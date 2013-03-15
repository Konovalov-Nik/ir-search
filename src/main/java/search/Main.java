package search;

import com.google.common.base.Joiner;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        printHello();
        String line;
        Scanner scanner = new Scanner(System.in);
        SearchEngine engine = new SearchEngine();
        while (true) {
            System.out.print(">> ");
            line = scanner.nextLine();
            if ("exit".equals(line)) {
                break;
            }
            process(line, engine);
        }
    }

    private static void process(String line, SearchEngine engine) {
        String[] splt = line.split("\\s");
        if (splt.length == 0) {
            return;
        }
        if (splt[0].equals("index")) {
            engine.doIndex(splt[1]);
            System.out.println("Indexing done.");
            return;
        }
        if (splt[0].equals("search")) {
            String[] queryTokens = Arrays.copyOfRange(splt, 1, splt.length);
            String query = Joiner.on(" ").join(queryTokens);
            Set<Document> documents = engine.doSearch(query);
            System.out.println("Results for query: " + query);
            for (Document document : documents) {
                System.out.println(document.getFile().getPath());
            }
            return;
        }

        if (splt[0].equals("save")) {
            String path = splt[1];
            engine.doSave(path);
            System.out.println("Saved dictionary to " + path + ".");
            return;
        }

        if (splt[0].equals("load")) {
            String path = splt[1];
            engine.doLoad(path);
            System.out.println("Loaded dictionary from " + path + ".");
            return;
        }
    }

    private static void printHello() {
        System.out.println("Search v0.1 (draft)");
    }
}
