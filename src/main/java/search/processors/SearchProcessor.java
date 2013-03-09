package search.processors;

import search.Document;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public interface SearchProcessor {
    Set<Document> search(String query, Map<String, TreeSet<Document>> dictionary);
}
