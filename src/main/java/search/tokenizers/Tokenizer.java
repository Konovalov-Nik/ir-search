package search.tokenizers;

public interface Tokenizer {
    String nextToken();

    boolean hasNextToken();
}
