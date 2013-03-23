package search.injection;

import com.google.inject.AbstractModule;
import search.processors.AndOrProcessor;
import search.processors.SearchProcessor;
import search.tokenizers.SmartTokenizer;
import search.tokenizers.Tokenizer;

/**
 * @author Konovalov_Nik
 */
public class SearchModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Tokenizer.class).to(SmartTokenizer.class);
        bind(SearchProcessor.class).to(AndOrProcessor.class);
    }
}
