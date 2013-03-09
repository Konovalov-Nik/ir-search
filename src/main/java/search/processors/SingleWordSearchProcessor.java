package search.processors;

import search.Document;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class SingleWordSearchProcessor implements SearchProcessor {
    @Override
    public Set<Document> search(String query, Map<String, TreeSet<Document>> dictionary) {
        if (dictionary.containsKey(query)) {
            return dictionary.get(query);
        }
        return new TreeSet<Document>();
    }
}
