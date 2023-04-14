package edu.macalester.comp221.hw09;

import java.util.ArrayList;
import java.util.List;

public class LCS {
    private final String STR_1 = "GCTACTCTCCTCATAAGCAGTCCGGTGTAT";
    private final String STR_2 = "CGAAAGAACAAGACTAGCCTTGCTAGCAACCGCGG";

    private List<List<String>> matrix;
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
            // lateral, outer
            List<String> outerList = new ArrayList<>();
            outerList.add("");

            matrix.add(outerList);
        }

        for (int i = 0; i < strTwoList.size(); i++) {
            // lateral, inner
            matrix.get(0).add("");
        }
    }

    private void longestCommonSubsequence() {
        for (int charSR1 = 1; charSR1 < strOneList.size(); charSR1++) {
            for (int charSR2 = 1; charSR2 < strTwoList.size(); charSR2++) {
                if (strOneList.get(charSR1) == strTwoList.get(charSR2)) {
                    String diag = matrix.get(charSR1-1).get(charSR2-1);
                    diag += strOneList.get(charSR1);
                            // Matched character

                    matrix.get(charSR1).add(diag);
                } else {
                    String upper = matrix.get(charSR1).get(charSR2 - 1);
                    String left = matrix.get(charSR1-1).get(charSR2);
                    String diag = matrix.get(charSR1-1).get(charSR2-1);

                    // Find the longest substring
                    int max = upper.length();
                    max = Math.max(max, left.length());
                    final int maximum = Math.max(max, diag.length());

                    List<String> largestString = new ArrayList<>();
                    largestString.add(upper);
                    largestString.add(left);
                    largestString.add(diag);

                    largestString.removeIf(x -> {
                        return x.length() != maximum;
                    });

                    matrix.get(charSR1).add(largestString.get(0));
                }
            }
        }

        System.out.println(matrix.get(strOneList.size() - 1).get(strTwoList.size() - 1));
    }

    public static void main(String[] args) {
        LCS l = new LCS();

        l.longestCommonSubsequence();

        for (List<String> inner : l.matrix) {
            System.out.println(inner);
        }
    }
}
