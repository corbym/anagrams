package org.corbym.anagrams;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class AnagramTest {

    private final AnagramFinder underTest = new AnagramFinder();

    @Test
    public void onlyTwoWordsAreAnagramsOfEachOther() {
        assertThat(underTest.anagramsIn(asList("man", "gainly", "laying")), equalTo("gainly laying"));
    }

    @Test
    public void onlyThreeWordsAreAnagramsOfEachOther() {
        assertThat(underTest.anagramsIn(asList("boaster", "boaters", "borates")), equalTo("boaster boaters borates"));
    }

    @Test
    public void twoSubsetsOfWordsAreAnagramsOfEachOther() {
        assertThat(underTest.anagramsIn(asList("gainly", "boaster", "boaters", "laying", "borates")), equalTo(
                "gainly laying\n" +
                        "boaster boaters borates")
        );
    }

    @Test
    public void upperCaseCharactersAreIgnored() {
        assertThat(underTest.anagramsIn(asList("Abes", "base")), equalTo("Abes base"));
    }

    @Test
    public void specialCharactersAreIgnored() {
        assertThat(underTest.anagramsIn(asList("Abe!@$%^&*()'s", "base")), equalTo("Abe!@$%^&*()'s base"));
    }

    @Test(timeout = 2000)
    public void handlesLargeList() throws FileNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("wordlist.txt").getFile());
        final List<String> wordList = scanInputFile(file);
        final long start = System.currentTimeMillis();
        System.out.println("started anagramming " + start);
        final String anagramString = underTest.anagramsIn(wordList);
        final long stop = System.currentTimeMillis();

        System.out.println("completed in " + (stop - start) + "ms");
        assertThat(anagramString, containsString("crepitus cuprite's cuprites picture's pictures piecrust"));
    }

    private List<String> scanInputFile(File file) throws FileNotFoundException {
        List<String> fileInput = new LinkedList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                fileInput.add(scanner.nextLine());
            }
        }
        return fileInput;
    }

}
