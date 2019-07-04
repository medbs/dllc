package com.example.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.synonym.SynonymGraphFilter;
import org.apache.lucene.analysis.synonym.SynonymMap;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.CharsRef;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Configuration
public class LuceneWriteIndex {

    private static String INDEX_DIR = "index";

    public void Write() throws Exception {
        IndexWriter writer = createWriter();
        List<Document> documents = new ArrayList<>();

        Document document1 = createDocument(1, "karkadan", "originalfann", "karkadance.com");
        documents.add(document1);

        Document document2 = createDocument(2, "mamadou", "segpa", "segpa.com");
        documents.add(document2);

        //clean everything first
        writer.deleteAll();

        writer.addDocuments(documents);
        writer.commit();
        writer.close();
    }

    private static Document createDocument(Integer id, String firstName, String lastName, String website) {
        Document document = new Document();
        document.add(new StringField("id", id.toString(), Field.Store.YES));
        document.add(new TextField("firstName", firstName, Field.Store.YES));
        document.add(new TextField("lastName", lastName, Field.Store.YES));
        document.add(new TextField("website", website, Field.Store.YES));
        return document;
    }

    private static IndexWriter createWriter() throws Exception {
        FSDirectory dir = FSDirectory.open(Paths.get(INDEX_DIR));
        Analyzer analyzer = createAnalyzer();
        //IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer()); //default analyzer
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(dir, config);
        return writer;
    }


    private static Analyzer createAnalyzer() throws Exception {
        SynonymMap.Builder builder = new SynonymMap.Builder();
        builder.add(new CharsRef("karkadan"), new CharsRef("wahidel9arn"), true);
        final SynonymMap map = builder.build();

        Analyzer indexTimeAnalyzer = new Analyzer() {
            @Override
            protected TokenStreamComponents createComponents(String fieldName) {
                Tokenizer tokenizer = new WhitespaceTokenizer();
                SynonymGraphFilter synFilter = new SynonymGraphFilter(tokenizer, map, true);
                return new TokenStreamComponents(tokenizer, synFilter);
            }
        };

        Map<String, Analyzer> perFieldAnalyzers = new HashMap<>();
        perFieldAnalyzers.put("year", new KeywordAnalyzer());
        Analyzer analyzer = new PerFieldAnalyzerWrapper(indexTimeAnalyzer, perFieldAnalyzers);
        return analyzer;

    }

}
