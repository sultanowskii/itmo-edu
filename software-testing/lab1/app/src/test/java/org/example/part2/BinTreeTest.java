package org.example.part2;

import static org.junit.Assert.*;

import org.junit.Test;

public class BinTreeTest {
    @Test public void insert() {
        record TestCase(String message, BinTree<Integer> initial, int value, BinTree<Integer> expected) {};

        TestCase[] cases = {
            new TestCase(
                "empty",
                new BinTree<Integer>(),
                2,
                new BinTree<Integer>(new BinTreeNode<Integer>(2))
            ),
            new TestCase(
                "with new bigger than root",
                new BinTree<Integer>(new BinTreeNode<Integer>(2)),
                3,
                new BinTree<Integer>(new BinTreeNode<Integer>(2).setRightValueTo(3))
            ),
            new TestCase(
                "with new less than root",
                new BinTree<Integer>(new BinTreeNode<Integer>(2)),
                1,
                new BinTree<Integer>(new BinTreeNode<Integer>(2).setLeftValueTo(1))
            ),
            new TestCase(
                "with simple tree",
                new BinTree<Integer>(
                    new BinTreeNode<Integer>(2)
                        .setLeftValueTo(1)
                        .setRightValueTo(3)
                ),
                4,
                new BinTree<Integer>(
                    new BinTreeNode<Integer>(2)
                        .setLeftValueTo(1)
                        .setRight(new BinTreeNode<Integer>(3).setRightValueTo(4))
                )
            ),
            new TestCase(
                "with complex example",
                new BinTree<Integer>(
                    new BinTreeNode<Integer>(8)
                    .setLeftValueTo(7)
                    .setRight(
                        new BinTreeNode<Integer>(10)
                            .setLeftValueTo(9)
                    )
                ),
                11,
                new BinTree<Integer>(
                    new BinTreeNode<Integer>(8)
                    .setLeftValueTo(7)
                    .setRight(
                        new BinTreeNode<Integer>(10)
                            .setLeftValueTo(9)
                            .setRightValueTo(11)
                    )
                )
            ),
        };

        for (var v : cases) {
            var actual = v.initial.insert(v.value);

            assertArrayEquals(v.message, v.expected.toList().toArray(), actual.toList().toArray());
        }
    }


    @Test public void delete() {
        record TestCase(String message, BinTree<Integer> initial, int value, BinTree<Integer> expected) {};

        TestCase[] cases = {
            new TestCase(
                "empty",
                new BinTree<Integer>(),
                2,
                new BinTree<Integer>()
            ),
            new TestCase(
                "single element",
                new BinTree<Integer>(new BinTreeNode<Integer>(2)),
                2,
                new BinTree<Integer>()
            ),
            new TestCase(
                "non-existent",
                new BinTree<Integer>(
                    new BinTreeNode<Integer>(2)
                        .setLeftValueTo(1)
                        .setRightValueTo(3)
                ),
                456,
                new BinTree<Integer>(
                    new BinTreeNode<Integer>(2)
                        .setLeftValueTo(1)
                        .setRightValueTo(3)
                )
            ),
            new TestCase(
                "right",
                new BinTree<Integer>(new BinTreeNode<Integer>(2).setRightValueTo(3)),
                3,
                new BinTree<Integer>(new BinTreeNode<Integer>(2))
            ),
            new TestCase(
                "left",
                new BinTree<Integer>(new BinTreeNode<Integer>(2).setLeftValueTo(1)),
                1,
                new BinTree<Integer>(new BinTreeNode<Integer>(2))
            ),
            new TestCase(
                "full",
                new BinTree<Integer>(
                    new BinTreeNode<Integer>(2)
                        .setLeftValueTo(1)
                        .setRightValueTo(3)
                ),
                2,
                new BinTree<Integer>()
            ),
            new TestCase(
                "with complex example",
                new BinTree<Integer>(
                    new BinTreeNode<Integer>(8)
                    .setLeftValueTo(7)
                    .setRight(
                        new BinTreeNode<Integer>(10)
                            .setLeftValueTo(9)
                            .setRightValueTo(11)
                    )
                ),
                11,
                new BinTree<Integer>(
                    new BinTreeNode<Integer>(8)
                    .setLeftValueTo(7)
                    .setRight(
                        new BinTreeNode<Integer>(10)
                            .setLeftValueTo(9)
                    )
                )
            ),
        };

        for (var v : cases) {
            var actual = v.initial.delete(v.value);

            assertArrayEquals(v.message, v.expected.toList().toArray(), actual.toList().toArray());
        }
    }
}
