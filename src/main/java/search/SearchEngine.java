package search;

import search.processors.AndOrProcessor;
import search.processors.SearchProcessor;
import search.processors.SingleWordSearchProcessor;
import search.processors.SmartProcessor;
import search.tokenizers.SmartTokenizer;
import search.tokenizers.SpaceTokenizer;
import search.tokenizers.Tokenizer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class SearchEngine {
    private Map<String, TreeSet<Document>> dictionary = new HashMap<String, TreeSet<Document>>();

    public void doIndex(String path) {
        Document document = new Document(new File(path));
        Tokenizer tokenizer = new SmartTokenizer(document);

        while (tokenizer.hasNextToken()) {
            String token  = tokenizer.nextToken();
            if (!dictionary.containsKey(token)) {
                dictionary.put(token, new TreeSet<Document>());
            }
            dictionary.get(token).add(document);
        }
    }

    public Set<Document> doSearch (String query) {
        SearchProcessor processor = new AndOrProcessor();
        return processor.search(query, dictionary);
    }

    public void doSave(String path) {
        FileOutputStream outputStream = null;
        ObjectOutputStream serializer = null;
        try {
            outputStream = new FileOutputStream(path);
            serializer = new ObjectOutputStream(outputStream);
            serializer.writeObject(dictionary);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            try {
                if (serializer != null) {
                    serializer.close();
                }
            } catch (IOException e) {
                // no op
            }
            try{
                if (outputStream!= null) {
                    outputStream.close();
                }
            }  catch (IOException e) {
                // no op
            }

        }

    }

    public void doLoad(String path) {
        FileInputStream inputStream = null;
        ObjectInputStream deserializer = null;
        try {
            inputStream = new FileInputStream(path);
            deserializer = new ObjectInputStream(inputStream);
            dictionary = (Map<String, TreeSet<Document>>) deserializer.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // no op
                }
            }

            if (deserializer != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // no op
                }
            }
        }
    }
}
