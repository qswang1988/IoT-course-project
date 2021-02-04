package com.example.implementation.reasoning;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

/*
 *   @Author: Shu Zhang
 *   Description:
 *   use Stanford NLP functions to tag words of a sentence based on their part of speech
 * */
public class CommandAnalyzer {
    private static final String modelPath = "edu\\stanford\\nlp\\models\\pos-tagger\\english-left3words\\english-left3words-distsim.tagger";
    private static MaxentTagger tagger = new MaxentTagger(modelPath);
    /**
     * Extract tagged info from primitive text by nlp tagger
     * @param txtString primitive text
     * @return
     */
    public static String tagged(String txtString)
    {
        return tagger.tagString(txtString);
    }

    /**
     * Extract nouns
     * @param taggedText tagged text
     * @return SortedSet<String> nouns
     */
    public static SortedSet<String> analyzeNouns(String taggedText)
    {
        SortedSet<String> taggedSet = CommandAnalyzer.extractWord(taggedText,"^NN.?");
        return taggedSet;
    }

    /**
     * Extract verbs
     * @param taggedText tagged text
     * @return SortedSet<String> verbs
     */
    public static SortedSet<String> analyzeVerbs(String taggedText)
    {
        SortedSet<String> taggedSet = CommandAnalyzer.extractWord(taggedText,"^VB.?");
        return taggedSet;
    }

    /**
     * Extract numbers
     * @param taggedText tagged text
     * @return SortedSet<String> numbers
     */
    public static SortedSet<String> analyzeNums(String taggedText)
    {
        SortedSet<String> taggedSet = CommandAnalyzer.extractWord(taggedText,"^CD.?");
        return taggedSet;
    }

    /**
     * Extract props
     * @param taggedText tagged text
     * @return SortedSet<String> props
     */
    public static SortedSet<String> analyzeProps(String taggedText)
    {
        SortedSet<String> taggedSet = CommandAnalyzer.extractWord(taggedText,"^RP.?");
        return taggedSet;
    }

    /**
     * Check if the target list is included in the extracted set
     * @param targetList target list
     * @param extractedSet extracted set
     * @return boolean result
     */
    public static boolean isCommandIncluded(String[] targetList, SortedSet<String> extractedSet)
    {
        HashSet<String> targetSet = new HashSet<String>(Arrays.asList(targetList));
        return !Collections.disjoint(targetSet, extractedSet);
    }

    /**
     *
     * @param taggedText tagged text
     * @param tagPattern tag pattern defined in nlp
     * @return SortedSet<String> sorted set matched tag pattern
     */
    public static SortedSet<String> extractWord(String taggedText, String tagPattern) {
        SortedSet<String> theSet = new TreeSet<String>();
        String[] split = taggedText.split(" ");
        List<String> theList = new ArrayList<String>();
        for (String token: split){
            String[] splitTokens = token.split("_");
            if(splitTokens.length < 2) {
                continue;
            }
            boolean isMatch = Pattern.matches(tagPattern, splitTokens[1]);
            if(isMatch){
                theList.add(splitTokens[0].toLowerCase());
            } else {
                addToSet(theSet, theList);
            }
        }
        addToSet(theSet, theList);
        return theSet;
    }

    /**
     * add the list to the sorted set
     * @param theSet target set
     * @param theList list to be added
     */
    private static void addToSet(SortedSet<String> theSet, List<String> theList) {
        if (!theList.isEmpty()) {
            theSet.add(StringUtils.join(theList, " "));
            theList.clear();
        }
    }
}
