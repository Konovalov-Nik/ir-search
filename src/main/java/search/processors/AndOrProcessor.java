package search.processors;

import com.google.common.collect.Sets;
import search.Document;
import search.tokenizers.SmartTokenizer;

import java.lang.reflect.Array;
import java.util.*;

/**
 * @author Konovalov_Nik
 */
public class AndOrProcessor implements SearchProcessor {
    private Map<String, TreeSet<Document>> dictionary;
    @Override
    public Set<Document> search(String query, Map<String, TreeSet<Document>> dictionary) {
        this.dictionary = dictionary;
        String normalizedQuery = SmartTokenizer.normalize(query);
        List<String> queryTokens = Arrays.asList(normalizedQuery.split("\\s"));
        return processOrs(normalizedQuery);
    }

    private Set<Document> processOrs(String query) {
        List<String> splittedQuery = splitTopLevel(query.trim(), " or ");
        if (splittedQuery.size() == 0) {
            return new TreeSet<Document>();
        }

        Set<Document> result = processAnds(splittedQuery.get(0));

        for (String token : splittedQuery) {
            result = Sets.union(result, processAnds(token));
        }

        return result;
    }

    private Set<Document> processAnds(String query) {
        List<String> splittedQuery = splitTopLevel(query.trim(), " and ");
        if (splittedQuery.size() == 0) {
            return new TreeSet<Document>();
        }

        String firstToken = splittedQuery.get(0).trim();
        Set<Document> result;
        if (firstToken.startsWith("(") && firstToken.endsWith(")")) {
            result = processOrs(firstToken.substring(1, firstToken.length() - 1));
        } else {
            result = dictionary.get(firstToken);
        }

        for (String token : splittedQuery) {
            if (token.startsWith("(") && token.endsWith(")")) {
                result = processOrs(token.substring(1, token.length() - 1));
            } else {
                result = Sets.intersection(result, dictionary.get(token));
            }
        }
        return result;
    }

    private static List<String> splitTopLevel(String s, String separator) {
        int balance = 0;
        int[] balances = new int[s.length()];
        List<String> result = new ArrayList<String>();
        StringBuilder token = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                balance++;
            }
            if (s.charAt(i) == ')') {
                balance--;
            }
            balances[i] = balance;
        }

        for (int i = 0; i < s.length(); i++) {
            if (s.substring(i).startsWith(separator) && balances[i] == 0) {
                result.add(token.toString());
                token = new StringBuilder();
                i += separator.length() - 1;
            } else {
                token.append(s.charAt(i));
            }
        }
        if (token.length() > 0) {
            result.add(token.toString());
        }
        return result;
    }
}
