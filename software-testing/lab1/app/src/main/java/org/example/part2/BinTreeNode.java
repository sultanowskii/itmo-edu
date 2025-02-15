package org.example.part2;

import java.util.ArrayList;
import java.util.List;

public class BinTreeNode<T extends Comparable<T>> {
    private T value;
    private BinTreeNode<T> left;
    private BinTreeNode<T> right;

    public BinTreeNode(T value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }

    public T getValue() {
        return this.value;
    }

    public BinTreeNode<T> getLeft() {
        return this.left;
    }

    public BinTreeNode<T> getRight() {
        return this.right;
    }

    public BinTreeNode<T> setLeft(BinTreeNode<T> left) {
        this.left = left;
        return this;
    }

    public BinTreeNode<T> setRight(BinTreeNode<T> right) {
        this.right = right;
        return this;
    }

    public BinTreeNode<T> setLeftValueTo(T leftValue) {
        this.left = new BinTreeNode<T>(leftValue);
        return this;
    }

    public BinTreeNode<T> setRightValueTo(T rightValue) {
        this.right = new BinTreeNode<T>(rightValue);
        return this;
    }

    public void insert(T v) {
        final int cmp = v.compareTo(this.value);
        if (cmp >= 0) {
            if (this.right == null) {
                this.right = new BinTreeNode<T>(v);
                return;
            }
            this.right.insert(v);
        } else {
            if (this.left == null) {
                this.left = new BinTreeNode<T>(v);
                return;
            }
            this.left.insert(v);
        }
    }

    public boolean delete(T v) {
        final int cmp = v.compareTo(value);
        if (cmp > 0 && this.right != null) {
            if (v.compareTo(this.right.value) == 0) {
                this.right = null;
                return true;
            }
            this.right.delete(v);
        } else if (cmp < 0 && this.left != null) {
            if (v.compareTo(this.left.value) == 0) {
                this.left = null;
                return true;
            }
            this.left.delete(v);
        }
        return false;
    }

    public boolean find(T v) {
        final int cmp = v.compareTo(this.value);
        if (cmp == 0) {
            return true;
        } else if (cmp > 0) {
            if (this.right == null) {
                return false;
            }
            return this.right.find(v);
        } else {
            if (this.left == null) {
                return false;
            }
            return this.left.find(v);
        }
    }

    public List<T> toList() {
        ArrayList<T> res = new ArrayList<>();

        if (this.left != null) {
            res.addAll(this.left.toList());
        }

        res.add(this.value);

        if (this.right != null) {
            res.addAll(this.right.toList());
        }

        return res;
    } 
}
