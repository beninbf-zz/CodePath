package main.java.com.codepath.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Trie {
    public Character rootCharacter;
    public boolean isWord;
    public Map<Character, Trie> characterTrieMap;
    public Trie(Character character) {
        this.rootCharacter = character;
        this.characterTrieMap = new HashMap<>();
    }

    public void addWord(String word) {
        if (word == null) {
            return;
        }
        if (word.trim().isEmpty()) {
            return;
        }
        int length = word.length();

        Trie trie = this;
        for (int i = 0; i < length; i++) {
            char ch = Character.toLowerCase(word.charAt(i));
            if (!trie.characterTrieMap.containsKey(ch)) {
                Trie childTrie = new Trie(ch);
                trie.characterTrieMap.put(ch, childTrie);
            }
            trie = trie.characterTrieMap.get(ch);
        }
        trie.isWord = true;
    }

    public boolean contains(String word) {
        if (word == null) {
            return false;
        }
        if (word.trim().isEmpty()) {
            return false;
        }
        int length = word.length();

        Trie trie = this;
        for (int i = 0; i < length; i++) {
            char ch = Character.toLowerCase(word.charAt(i));
            if (!trie.characterTrieMap.containsKey(ch)) {
                return false;
            }
            trie = trie.characterTrieMap.get(ch);
        }
        return true;
    }

    public boolean isPrefix(String word) {
        if (word == null) {
            return false;
        }
        if (word.trim().isEmpty()) {
            return false;
        }
        int length = word.length();

        Trie trie = this;
        for (int i = 0; i < length; i++) {
            char ch = Character.toLowerCase(word.charAt(i));
            if (!trie.characterTrieMap.containsKey(ch)) {
                return false;
            }
            trie = trie.characterTrieMap.get(ch);
        }
        return trie.characterTrieMap.size() > 0;
    }

    public List<String> getWordsThatBeginWithPrefix(String prefix) {
        if (prefix == null) {
            return null;
        }
        if (prefix.trim().isEmpty()) {
            return null;
        }

        List<String> results = new ArrayList<>();
        int length = prefix.length();

        Trie trie = this;
        for (int i = 0; i < length; i++) {
            char ch = Character.toLowerCase(prefix.charAt(i));
            if (!trie.characterTrieMap.containsKey(ch)) {
                return null;
            }
            trie = trie.characterTrieMap.get(ch);
        }

        if (!trie.characterTrieMap.isEmpty()) {
            List<Character> wordSegment = new ArrayList<>();
            List<List<Character>> allWordSegments = new ArrayList<>();
            getAllWordsAfterPrefix(trie, wordSegment, allWordSegments);
            if (!allWordSegments.isEmpty()) {
                for (List<Character> segment : allWordSegments) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(prefix);
                    for (Character character: segment) {
                        sb.append(character);
                    }
                    results.add(sb.toString());
                }
            }
        }
        return results;
    }

    private void getAllWordsAfterPrefix(Trie trie, List<Character> wordSegment, List<List<Character>> allWordSegments) {
        if (trie.characterTrieMap.isEmpty()) {
            allWordSegments.add(new ArrayList<>(wordSegment));
            return;
        }

        if (trie.isWord) {
            allWordSegments.add(new ArrayList<>(wordSegment));
        }
        Set<Character> keySet = trie.characterTrieMap.keySet();
        for (Character ch : keySet) {
            wordSegment.add(ch);
            Trie childTrie = trie.characterTrieMap.get(ch);
            getAllWordsAfterPrefix(childTrie, wordSegment, allWordSegments);
            wordSegment.remove(wordSegment.size() - 1);
        }
    }
}
