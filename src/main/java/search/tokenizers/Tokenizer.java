package search.tokenizers;

import search.Document;

public interface Tokenizer {
    String nextToken();

    boolean hasNextToken();

    void setDocument(Document document);

    void init();
}
