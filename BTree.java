package cse214hw3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BTree<E extends Comparable<E>> implements AbstractBTree<E> {
    public BTree(int minimumDegree){
        this.root = new Node<>(minimumDegree);
        this.minimumDegree=minimumDegree;

    }
    private final int minimumDegree;
    Node<E> root;
    public class Node<E> {
        @Override
        public String toString() {
            return toString(0);
        }
        private String toString(int depth) {
            StringBuilder builder = new StringBuilder();
            String blankPrefix = new String(new char[depth]).replace("\0", "\t");
            List<String> printedElements = new LinkedList<>();
            for (E e : elements) printedElements.add(e.toString());
            String eString = String.join(" :: ", printedElements);
            builder.append(blankPrefix).append(eString).append("\n");
              children.forEach(c -> builder.append(c.toString(depth + 1)));

            return builder.toString();
        }
        int min;
        List<E> elements;
        List<Node<E>> children;
        public Node(int min){
            this.min = min;
            elements = new ArrayList<>();
            children= new ArrayList<>();
        }


        public int medianIndex(){
            return minimumDegree-1;
        }
        public boolean isLeaf() {
            return this.children.isEmpty();
        }

        public boolean isFull() {
            return (elements.size() == 2*this.min-1);
        }
    }

    @Override
    public NodeIndexPair<E> contains(E element) {
        return contains(element, this.root);
    }
    private NodeIndexPair<E> contains(E element, Node<E> node){
        int i=0;
        int keys= node.elements.size();
        while(i<keys&&element.compareTo(node.elements.get(i))>0)
            i++;
        if(i<keys&&element.compareTo(node.elements.get(i))==0)
            return new NodeIndexPair<>(node, i);
        if(node.isLeaf())
            return null;
        return contains(element, node.children.get(i));
    }

    @Override
    public void add(E element) {
        if(contains(element)!=null)
            return;
        if(root.isFull())
            splitRoot();
        add(root, element);
    }
    private void splitChild(Node<E> node, int index) {
        Node<E> child = node.children.get(index);
        Node<E> n = new Node<>(minimumDegree);
        node.children.add(index+1, n);
        E val = child.elements.get(child.medianIndex());
        node.elements.add(index, val);
        ArrayList<E> leftSide = new ArrayList<>();
        ArrayList<E> rightSide = new ArrayList<>();
        child.elements.remove(val);
        for(int i=0; i<child.elements.size();i++){
            if(i<child.medianIndex())
                leftSide.add(child.elements.get(i));
            else
                rightSide.add(child.elements.get(i+1));
        }

        child.elements=leftSide;
        n.elements=rightSide;
        if (!child.isLeaf()) {
            List<Node<E>> leftChildren = new ArrayList<>();
            List<Node<E>> rightChildren = new ArrayList<>();
            for(int i=0;i<child.children.size(); i++){
                if (i <= child.medianIndex()) {
                    leftChildren.add(child.children.get(i));
                } else {
                    rightChildren.add(child.children.get(i));
                }
            }
            child.children = leftChildren;
            n.children = rightChildren;
        }
    }
    private void add(Node<E> node, E element) {
        if (node.isLeaf()) {
            int i;
            for (i = 0; i < node.elements.size(); i++) {
                if (element.compareTo(node.elements.get(i)) < 0)
                    break;
            }
            node.elements.add(i, element);
        } else {
            int i = node.elements.size() - 1;
            while (i >= 0 && element.compareTo(node.elements.get(i)) < 0) {
                i--; }
            i++;
            if (node.children.get(i).isFull()) {
                splitChild(node, i);
                if (element.compareTo(node.elements.get(i)) > 0) {
                    i++;
                }
            }
            add(node.children.get(i), element);
        }
    }

    private void splitRoot(){
        Node<E> node = new Node<>(minimumDegree);
        Node<E> oldRoot = this.root;
        this.root = node;
        node.children.add(oldRoot);
        splitChild(node, 0);
    }

    public String toString(){
        return root.toString();
    }
    @SafeVarargs
    private static <T extends Comparable<T>> void addAllInThisOrder(BTree<T> theTree, T... items) {
        for (T item : items)
            theTree.add(item);
    }

    public static void main(String[] args) {
        BTree<Integer> integerBTree = new BTree<>(3);
        addAllInThisOrder(integerBTree, 10, 20, 30, 40, 50);
        System.out.println(integerBTree);
        integerBTree.add(60);
        integerBTree.add(70);
        integerBTree.add(80);
        integerBTree.add(90);
        System.out.println(integerBTree);
        NodeIndexPair<Integer> foundNumber = integerBTree.contains(80);
        System.out.printf("Element %d found at index %d of the following node:\n%s%n",

                80,
                foundNumber.index,
                foundNumber.nodeLocation);
    }

}
