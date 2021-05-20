import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class Solution {

    static class TrieNode {

        private int sum;

        private TrieNode[] childs;

        public TrieNode() {
            this.sum = 0;
            this.childs = new TrieNode['z' - 'a' + 1];
        }

        public int sum() {
            return sum;
        }

        public void increase() {
            sum += 1;
        }

        public TrieNode getChild(char c) {
            return childs[c - 'a'];
        }

        public void setChild(char c, TrieNode node) {
            childs[c - 'a'] = node;
        }
    }

    static class Trie {

        private TrieNode root;

        public Trie() {
            this.root = new TrieNode();
        }

        public void add(String str) {
            TrieNode node = root;
            for (int i = 0; i < str.length(); ++i) {
                char c = str.charAt(i);
                node.setChild(c, Optional.ofNullable(node.getChild(c)).orElse(new TrieNode()));
                node = node.getChild(c);
                node.increase();
            }
        }

        public int find(String prefix) {
            TrieNode node = root;
            for (int i = 0; i < prefix.length(); ++i) {
                char c = prefix.charAt(i);
                if (node.getChild(c) == null) {
                    return 0;
                }
                node = node.getChild(c);
            }
            return node.sum();
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        Trie trie = new Trie();

        IntStream.range(0, n).forEach(nItr -> {
            try {
                String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                String op = firstMultipleInput[0];

                String contact = firstMultipleInput[1];

                if (op.equals("add")) {
                    trie.add(contact);
                } else {
                    System.out.println(trie.find(contact));
                }

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
    }
}
