package search.processors;

import com.google.common.collect.Sets;
import search.Document;
import search.tokenizers.SmartTokenizer;

import java.util.*;

/**
 * @author Konovalov_Nik
 */
public class SmartProcessor implements SearchProcessor {
    @Override
    public Set<Document> search(String query, Map<String, TreeSet<Document>> dictionary) {
        String normalizedQuery = SmartTokenizer.normalize(query);
        List<String> words = Arrays.asList(normalizedQuery.split("\\s"));
        if (words.size() == 0) {
            return new TreeSet<Document>();
        }

        if (!dictionary.containsKey(words.get(0))) {
            return new TreeSet<Document>();
        }

        Set<Document> result = new TreeSet<Document>(dictionary.get(words.get(0)));

        for (String word : words) {
            TreeSet docs = dictionary.containsKey(word) ? dictionary.get(word) : new TreeSet();
            result = Sets.intersection(result, docs);
        }
        return result;
    }
}
