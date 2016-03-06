package org.java.algorithms;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 */
public class Autocomplete {

    public static void main(String[] args) {
        RadixTree rtree = new RadixTree();
        rtree.insert("pork and meat");

        rtree.insert("test");
        rtree.insert("tost");
        rtree.insert("park");
        rtree.insert("pork");
        rtree.insert("porke");
        rtree.insert("porke");
        rtree.insert("por");
        rtree.insert("p");
        rtree.insert("pork only");
        rtree.insert("pork and mates");
//        rtree.printTree();
        rtree.getByPrefix("pol").forEach(w -> System.out.println(w));
    }

    private class Query {
        private String prefix;
    }

    private static class RadixTree {

        private class RNode {
            String prefix;
            List<RNode> nodes;
            RNode leaf;

            public RNode(String prefix, List<RNode> nodes, RNode leaf) {
                this.prefix = prefix;
                this.nodes = nodes;
                this.leaf = leaf;
            }

            public RNode(String prefix, List<RNode> nodes) {
                this.prefix = prefix;
                this.nodes = nodes;
            }

            public RNode(String prefix, boolean buildLeaf) {
                this.prefix = prefix;
                this.nodes = new ArrayList<>();
                if (buildLeaf)
                    leaf = new RNode("");
            }

            public RNode(String prefix) {
                this(prefix, false);
            }
        }

        private RNode root;

        public void insert(String phrase) {
            if (root == null) {
                root = new RNode(phrase, true);
                return;
            }
            insert(phrase, root);
        }

        private void insert(String phrase, RNode node) {
            if (phrase.isEmpty() && node.prefix.isEmpty())
                return;
            int mutualPrefixIndex = findMutualPrefix(node.prefix, phrase);
            String mutualPrefix = phrase.substring(0, mutualPrefixIndex);
            if (!mutualPrefix.equals(node.prefix)) {
                RNode newNode = new RNode(phrase.substring(mutualPrefixIndex), true);
                RNode newChild = new RNode(node.prefix.substring(mutualPrefixIndex), node.nodes, node.leaf);
                node.leaf = null;
                node.prefix = mutualPrefix;
                List<RNode> nodes = new LinkedList<>();
                nodes.add(newChild);
                nodes.add(newNode);
                node.nodes = nodes;
                return;
            } else {
                String suffix = phrase.substring(mutualPrefixIndex);
                if (suffix.isEmpty()) {
                    node.leaf = new RNode("");
                    return;
                }
                for (RNode cr : node.nodes) {
                    if (findMutualPrefix(suffix, cr.prefix) > 0) {
                        insert(suffix, cr);
                        return;
                    }
                }
                RNode newNode = new RNode(suffix, true);
                node.nodes.add(newNode);
            }
        }

        public List<String> getByPrefix(String prefix) {
            RNode curr = root;
            StringBuilder currPrefix = new StringBuilder();
            while (curr != null) {
                int mutualPrefixIndex = findMutualPrefix(curr.prefix, prefix);
                if ((mutualPrefixIndex != curr.prefix.length() && prefix.length() == mutualPrefixIndex)
                    || prefix.isEmpty()) {
                    return gatherWords(curr, currPrefix);
                }
                if (curr.nodes == null) {
                    return new ArrayList<>();
                }
                currPrefix.append(curr.prefix);
                prefix = prefix.substring(mutualPrefixIndex);
                curr = getNextNode(curr, prefix);
            }

            return new ArrayList<>();
        }

        private RNode getNextNode(RNode curr, String prefix) {
            for (RNode next : curr.nodes) {
                if (findMutualPrefix(next.prefix, prefix) > 0)
                    return next;
            }
            return null;
        }

        private List<String> gatherWords(RNode node, StringBuilder prefix) {
            return gatherWords(node, prefix, new LinkedList<>());
        }

        private List<String> gatherWords(RNode node, StringBuilder bldr, List<String> words) {
            if (node == null)
                return words;
            bldr.append(node.prefix);
            if (node.leaf != null) {
                words.add(bldr.toString().trim());
            }
            if (node.nodes == null)
                return words;
            for (RNode next : node.nodes) {
                gatherWords(next, bldr, words);
                bldr.delete(bldr.length() - next.prefix.length(), bldr.length());
            }
            return words;
        }

        public void printTree() {
            printTree(root, 1, new Stack<>());
        }

        private void printTree(RNode node, int depth, Stack<String> stems) {
            if (node == null)
                return;
            System.out.println("Node: " + node.prefix + " ; depth: " + depth);
            stems.push(node.prefix);
            if (node.leaf != null) {
                System.out.println("Stored word: " + stems.toString());
            }
            if (node.nodes == null)
                return;
            for (RNode next : node.nodes) {
                printTree(next, depth + 1, stems);
                stems.pop();
            }
        }

        private int findMutualPrefix(String s1, String s2) {
            for (int i = 0; i < Math.min(s1.length(), s2.length()); ++i) {
                if (s1.charAt(i) != s2.charAt(i))
                    return i;
            }
            return Math.min(s1.length(), s2.length());
        }
    }
}
