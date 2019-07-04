package com.example.lucene;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LuceneRW implements CommandLineRunner {

    @Autowired
    private LuceneReadIndex readIndex;
    @Autowired
    private LuceneWriteIndex writeIndex;

    public static void main(String[] args) {
        SpringApplication.run(LuceneRW.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        writeIndex.Write();
        readIndex.Read();

    }
}
