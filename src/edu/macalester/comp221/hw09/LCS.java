package edu.macalester.comp221.hw09;

import java.util.ArrayList;
import java.util.List;

public class LCS {
    private final String STR_1 = "GCTACTCTCCTCATAAGCAGTCCGGTGTAT";
    private final String STR_2 = "CGAAAGAACAAGACTAGCCTTGCTAGCAACCGCGG";

    private List<List<List<String>>> matrix;
    private List<Character> strOneList;
    private List<Character> strTwoList;

    public LCS() {
        matrix = new ArrayList<>();

        strOneList = new ArrayList<>();
        strTwoList = new ArrayList<>();
        strOneList.add(null);
        strTwoList.add(null);

        for (char c : STR_1.toCharArray()) {
            strOneList.add(c);
        }

        for (char c : STR_2.toCharArray()) {
            strTwoList.add(c);
        }

        for (int i = 0; i < strOneList.size(); i++) {
            List<List<String>> outerList = new ArrayList<>();
            
            for (int j = 0; j < strTwoList.size(); j++) {
                List<String> charAtIndex = new ArrayList<>();

                if (i == 0 || j == 0) {
                    charAtIndex.add("");
                }

                outerList.add(charAtIndex);
            }

            matrix.add(outerList);
        }
    }

    private void longestCommonSubsequence() {
        for (int charSR1 = 1; charSR1 < strOneList.size(); charSR1++) {
            for (int charSR2 = 1; charSR2 < strTwoList.size(); charSR2++) {
                if (strOneList.get(charSR1) == strTwoList.get(charSR2)) {
                    List<String> diag = matrix.get(charSR1-1).get(charSR2-1);

                    for (String character : diag) {
                        matrix.get(charSR1).get(charSR2).add(character + strOneList.get(charSR1));
                    }
                } else {
                    List<String> upper = matrix.get(charSR1).get(charSR2 - 1);
                    List<String> left = matrix.get(charSR1-1).get(charSR2);
                    List<String> diag = matrix.get(charSR1-1).get(charSR2-1);

                    matrix.get(charSR1).get(charSR2).addAll(findLongest(upper, left, diag));
                }
            }
            
        }

        System.out.println(matrix.get(strOneList.size() - 1).get(strTwoList.size() - 1));
    }

    private List<String> findLongest(List<String> upper, List<String> left, List<String> diag) {
        List<String> allSubstrings = new ArrayList<>();

        addNoDuplicates(allSubstrings, upper);
        addNoDuplicates(allSubstrings, left);
        addNoDuplicates(allSubstrings, diag);

        int max = allSubstrings.get(0).length();

        for (String substring : allSubstrings) {
            max = Math.max(max, substring.length());
        }

        final int maximum = max;



        allSubstrings.removeIf(x -> {
            return x.length() != maximum;
        });

        return allSubstrings;
    }

    private void addNoDuplicates (List<String> target, List<String> source) {
        for (String element : source) {
            if (!target.contains(element)) {
                target.add(element);
            }
        }
    }

    public static void main(String[] args) {
        LCS l = new LCS();

        l.longestCommonSubsequence();

        // for (List<List<String>> inner : l.matrix) {
        //     System.out.println(inner);
        // }
    }
}