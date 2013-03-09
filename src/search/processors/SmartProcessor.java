package search.processors;

import search.Document;
import search.tokenizers.SmartTokenizer;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Konovalov_Nik
 */
public class SmartProcessor implements SearchProcessor {
    @Override
    public Set<Document> search(String query, Map<String, TreeSet<Document>> dictionary) {
        String normalizedQuery = SmartTokenizer.normalize(query);
        if (dictionary.containsKey(normalizedQuery)) {
            return dictionary.get(normalizedQuery);
        } else  {
            return new TreeSet<Document>();
        }
    }
}
