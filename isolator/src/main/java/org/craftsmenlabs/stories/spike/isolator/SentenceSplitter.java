package org.craftsmenlabs.stories.spike.isolator;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public class SentenceSplitter {

    public Optional<Stream<String>> detectSentence(String input) {
        InputStream modelIn = null;
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("en-sent.bin").getFile());

        try {
            modelIn = new FileInputStream(file);
            SentenceModel model = new SentenceModel(modelIn);
            SentenceDetectorME sentenceDetector = new SentenceDetectorME(model);

            return Optional.of(Arrays.stream(sentenceDetector.sentDetect(input)));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (modelIn != null) {
                try {
                    modelIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Optional.empty();
    }
}