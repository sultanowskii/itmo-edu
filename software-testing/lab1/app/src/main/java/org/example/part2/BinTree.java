package org.example.part2;

import java.util.List;

public class BinTree<T extends Comparable<T>> {
    private BinTreeNode<T> root;
    
    public BinTree() {

    }

    public BinTree(BinTreeNode<T> root) {
        this.root = root;
    }

    public BinTree<T> insert(T v) {
        if (this.root == null) {
            this.root = new BinTreeNode<T>(v);
            return this;
        }
        
        this.root.insert(v);

        return this;
    }

    public BinTree<T> delete(T v) {
        if (this.root == null) {
            return this;
        }

        if (this.root.getValue().compareTo(v) == 0) {
            this.root = null;
            return this;
        }

        this.root.delete(v);
        return this;
    }

    public boolean find(T v) {
        if (this.root == null) {
            return false;
        }

        return this.root.find(v);
    }

    public List<T> toList() {
        if (this.root == null) {
            return List.of();
        }

        return this.root.toList();
    }
}
