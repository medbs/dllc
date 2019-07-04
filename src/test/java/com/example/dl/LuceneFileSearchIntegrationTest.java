//package com.example.dl;
//
//import org.apache.lucene.analysis.standard.StandardAnalyzer;
//import org.apache.lucene.document.Document;
//import org.apache.lucene.store.Directory;
//import org.apache.lucene.store.FSDirectory;
//import org.junit.Assert;
//import org.junit.Test;
//
//import java.io.IOException;
//import java.net.URISyntaxException;
//import java.nio.file.Paths;
//import java.util.List;
//
//public class LuceneFileSearchIntegrationTest {
//
//    @Test
//    public void givenSearchQueryWhenFetchedFileNamehenCorrect() throws IOException, URISyntaxException {
//        String indexPath = "/home/medbs/lunx/index";
//        String dataPath = "/home/medbs/lunx/lucenex/test.txt";
//
//        Directory directory = FSDirectory.open(Paths.get(indexPath));
//        LuceneFileSearch luceneFileSearch = new LuceneFileSearch(directory, new StandardAnalyzer());
//
//        luceneFileSearch.addFileToIndex(dataPath);
//
//        List<Document> docs = luceneFileSearch.searchFiles("contents", "consectetur");
//
//        Assert.assertEquals("file1.txt", docs.get(0).get("filename"));
//    }
//}
