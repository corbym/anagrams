package org.corbym.anagrams;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.reverseOrder;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

public class AnagramFinder {


    public String anagramsIn(final List<String> wordList) {

        long start = System.currentTimeMillis();
        System.out.println("started anagramming loop " + start);

        final Map<String, Set<String>> allAnagramLists = wordList.stream()
                .collect(groupingBy(this::sortedCharsIgnoringCase, () -> new TreeMap<>(reverseOrder()),
                        mapping(identity(), toCollection(TreeSet::new))));

        long stop = System.currentTimeMillis();
        System.out.println("completed loop in " + (stop - start) + "ms");
        return reportOnAnagrams(new ArrayList<>(allAnagramLists.values()));
    }

    private String reportOnAnagrams(List<Set<String>> allAnagrams) {
        return allAnagrams.stream()
                .filter(list -> list.size() > 1)
                .map(anagramList ->
                        anagramList.stream().collect(Collectors.joining(" "))
                )
                .collect(Collectors.joining("\n"));
    }

    public String sortedCharsIgnoringCase(final String wordFromList) {
        final StringBuilder stringBuilder = wordFromList.toLowerCase().chars()
                .filter(Character::isAlphabetic)
                .sorted()
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append);
        return stringBuilder.toString();
    }


}
