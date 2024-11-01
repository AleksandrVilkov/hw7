package org.example;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Treap<T extends Comparable<T>> {

    private Node<T> root;

    public void add(T key) {
        root = insert(root, key);
    }

    public void remove(T key) {
        root = deleteNode(root, key);
    }


    public List<T> inorder() {
        List<T> res = new ArrayList<>();
        inorder(root, res);
        return res;
    }

    private void inorder(Node<T> cur, List<T> res) {
        if (Objects.isNull(cur)) return;

        inorder(cur.leftNode, res);
        res.add(cur.key);
        inorder(cur.rightNode, res);
    }

    private Node<T> deleteNode(Node<T> cur, T key) {
        if (Objects.isNull(cur)) return cur;

        if (key.compareTo(cur.key) < 0)
            cur.leftNode = deleteNode(cur.leftNode, key);
        else if (key.compareTo(cur.key) > 0)
            cur.rightNode = deleteNode(cur.rightNode, key);

        else if (Objects.isNull(cur.leftNode)) {
            cur = cur.rightNode;
        } else if (Objects.isNull(cur.rightNode)) {
            cur = cur.leftNode;
        } else if (cur.leftNode.priority < cur.rightNode.priority) {
            cur = leftRotation(cur);
            cur.leftNode = deleteNode(cur.leftNode, key);
        } else {
            cur = rightRotation(cur);
            cur.rightNode = deleteNode(cur.rightNode, key);
        }

        return cur;
    }

    private Node<T> insert(Node<T> cur, T key) {
        if (Objects.isNull(cur)) return new Node<>(key);

        if (key.compareTo(cur.key) > 0) {
            cur.rightNode = insert(cur.rightNode, key);
            if (cur.rightNode.priority < cur.priority)
                cur = leftRotation(cur);
        } else {
            cur.leftNode = insert(cur.leftNode, key);
            if (cur.leftNode.priority < cur.priority)
                cur = rightRotation(cur);
        }
        return cur;
    }

    private Node<T> leftRotation(Node<T> x) {
        var y = x.rightNode;
        var t2 = y.leftNode;

        y.leftNode = x;
        x.rightNode = t2;

        return y;
    }

    private Node<T> rightRotation(Node<T> y) {
        var x = y.leftNode;
        var t2 = x.rightNode;

        x.rightNode = y;
        y.leftNode = t2;

        return x;
    }

    public List<Node<T>> getSubSet(T max) {
        var result = new ArrayList<Node<T>>();
        getSubSet(max, result, root);
        return result;
    }

    private void getSubSet(T max, List<Node<T>> result, Node<T> current) {
        if (null == current) return;

        getSubSet(max, result, current.leftNode);
        if (max.compareTo(current.key) >= 0) result.add(current);
        getSubSet(max, result, current.rightNode);
    }

    public static class Node<T extends Comparable<T>> {
        private static final Random random = new Random();
        private final T key;
        private final int priority;
        private Node<T> leftNode;
        private Node<T> rightNode;

        public Node(T key) {
            this(key, random.nextInt());
        }

        public T getKey() {
            return key;
        }

        public Node(T key, int priority) {
            this(key, priority, null, null);
        }

        public Node(T key, int priority, Node<T> leftNode, Node<T> rightNode) {
            this.key = key;
            this.priority = priority;
            this.leftNode = leftNode;
            this.rightNode = rightNode;
        }

        public Node<T>[] split(T key) {
            Node<T> tmp = null;

            var res = (Node<T>[]) Array.newInstance(Treap.class, 2);

            if (this.key.compareTo(key) <= 0) {
                if (Objects.isNull(this.rightNode))
                    res[1] = null;
                else {
                    var rightSplit = this.rightNode.split(key);
                    res[1] = rightSplit[1];
                    tmp = rightSplit[0];
                }
                res[0] = new Node<>(this.key, priority, this.leftNode, tmp);
            } else {
                if (Objects.isNull(this.leftNode))
                    res[0] = null;
                else {
                    var leftSplit = this.leftNode.split(key);
                    res[0] = leftSplit[0];
                    tmp = leftSplit[1];
                }
                res[1] = new Node<>(this.key, priority, tmp, this.rightNode);
            }
            return res;
        }
    }
}