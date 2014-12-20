import java.nio.file.FileVisitOption;

import javax.print.attribute.standard.Fidelity;

public class RedBlackTree {
	public static void main(String[] args) {

	}

	// 노드는 RED or BLACK
	// ROOT = BLACK
	// LEAF = BLACK
	// RED는 CHILD로 BLACK을 둘 수 있음 (즉 RED RED는 불가능)
	// 단순경로상의 BLACK갯수는 동일
	public void leftRotate(Tree tree, Node x) {
		Node y = x.rightChild;
		x.rightChild = y.leftChild; // 떨거지 처리
		if (y.leftChild != tree.nil)
			y.leftChild.parent = x;
		y.parent = x.parent; // y의 부모처리
		if (x.parent == tree.nil) // x의 부모처리
			tree.root = y;
		else if (x == x.parent.leftChild)
			x.parent.leftChild = y;
		else
			x.parent.rightChild = y;
		y.leftChild = x;
		x.parent = y;
	}

	public void rightRotate(Tree tree, Node y) {
		Node x = y.rightChild;
		y.rightChild = x.leftChild; // 떨거지 처리
		if (x.leftChild != tree.nil)
			x.leftChild.parent = y;
		x.parent = y.parent; // y의 부모처리
		if (y.parent == tree.nil) // x의 부모처리
			tree.root = x;
		else if (y == y.parent.leftChild)
			y.parent.leftChild = x;
		else
			y.parent.rightChild = x;
		x.leftChild = y;
		y.parent = x;
	}

	public void insert(Tree tree, Node node) {
		Node parentNode = tree.nil;
		Node curNode = tree.root;
		while (curNode != tree.nil) {
			parentNode = curNode;
			if (node.key < curNode.key)
				curNode = curNode.leftChild;
			else
				curNode = curNode.rightChild;
		}
		node.parent = parentNode;
		if (parentNode == tree.nil)
			tree.root = node;
		else if (node.key < parentNode.key)
			parentNode.leftChild = node;
		else
			parentNode.rightChild = node;
		node.leftChild = tree.nil;
		node.rightChild = tree.nil;
		node.color = Color.RED;
		insertFixup(tree, node);
	}

	public void insertFixup(Tree tree, Node node) {
		while (node.parent.color == Color.RED) {
			if (node.parent == node.parent.parent.leftChild) {
				Node uncleNode = node.parent.parent.rightChild;
				if (uncleNode.color == Color.RED) {
					node.parent.color = Color.BLACK;
					uncleNode.color = Color.BLACK;
					node.parent.parent.color = Color.RED;
					node = node.parent.parent;
				} else {
					if (node == node.parent.rightChild) {
						node = node.parent;
						leftRotate(tree, node);
					}
					node.parent.color = Color.BLACK;
					node.parent.parent.color = Color.RED;
					rightRotate(tree, node.parent.parent);
				}
			} else {
				Node uncleNode = node.parent.parent.leftChild;
				if (uncleNode.color == Color.RED) {
					node.parent.color = Color.BLACK;
					uncleNode.color = Color.BLACK;
					node.parent.parent.color = Color.RED;
					node = node.parent.parent;
				} else {
					if (node == node.parent.leftChild) {
						node = node.parent;
						rightRotate(tree, node);
					}
					node.parent.color = Color.BLACK;
					node.parent.parent.color = Color.RED;
					leftRotate(tree, node.parent.parent);
				}
			}
		}
		tree.root.color = Color.BLACK;
	}

	public void transPlant(Tree tree, Node oldNode, Node plantNode) {
		if (oldNode.parent == tree.nil)
			tree.root = plantNode;
		else if (oldNode == oldNode.parent.leftChild)
			oldNode.parent.leftChild = plantNode;
		else
			oldNode.parent.rightChild = plantNode;
		plantNode.parent = oldNode.parent;
	}

	public void delete(Tree tree, Node node) {
		Color erasedColor = node.color;
		if (node.leftChild == tree.nil) {
			Node fixupNode = node.rightChild;
			transPlant(tree, node, node.rightChild);
		} else if (node.rightChild == tree.nil) {
			Node fixupNode = node.leftChild;
			transPlant(tree, node, node.leftChild);
		} else {
			Node succesor = treeMinimun(node.rightChild);
			erasedColor = succesor.color;
			Node fixupNode = succesor.rightChild;
			if (succesor.parent == deleteNode) {
				fixupNode.parent = succesor;
			} else {
				transPlant(tree, succesor, succesor.rightChild);
				succesor.rightChild = node.rightChild;
				succesor.rightChild.parent = succesor;
			}
			transPlant(tree, node, succesor);
			succesor.leftChild = node.leftChild;
			succesor.leftChild.parent = succesor;
			succesor.color = node.color;
		}
		if (erasedColor == Color.BLACK)
			deleteFixup(tree, fixupNode);
	}

	public void deleteFixup(Tree tree, Node fixupNode) {
		while (fixupNode != tree.root && fixupNode.color == Color.BLACK) {
			if (fixupNode == fixupNode.parent.leftChild) {
				Node sibling = fixupNode.parent.leftChild;
				if (sibling.color == Color.RED) {
					sibling.color = Color.BLACK;
					fixupNode.parent.color = Color.RED;
					leftRotate(tree, fixupNode);
					sibling = fixupNode.parent.rightChild;
				}
				if (sibling.leftChild.color == Color.BLACK
						&& sibling.rightChild.color == Color.BLACK) {
					sibling.color = Color.RED;
					fixupNode = fixupNode.parent;
				} else {
					if (sibling.rightChild.color == Color.BLACK) {
						sibling.leftChild.color = Color.BLACK;
						sibling.color = Color.RED;
						rightRotate(tree, sibling);
						sibling = fixupNode.parent.rightChild;
					}
					sibling.color = fixupNode.parent.rightChild.color;
					fixupNode.parent.color = Color.BLACK;
					sibling.rightChild.color = Color.BLACK;
					leftRotate(tree, fixupNode.parent);
					fixupNode = tree.root;
				}
			} else {
				//Right에 대하여 그대로 반복
				Node sibling = fixupNode.parent.leftChild;
				if (sibling.color == Color.RED) {
					sibling.color = Color.BLACK;
					fixupNode.parent.color = Color.RED;
					leftRotate(tree, fixupNode);
					sibling = fixupNode.parent.rightChild;
				}
				if (sibling.leftChild.color == Color.BLACK
						&& sibling.rightChild.color == Color.BLACK) {
					sibling.color = Color.RED;
					fixupNode = fixupNode.parent;
				} else {
					if (sibling.rightChild.color == Color.BLACK) {
						sibling.leftChild.color = Color.BLACK;
						sibling.color = Color.RED;
						rightRotate(tree, sibling);
						sibling = fixupNode.parent.rightChild;
					}
					sibling.color = fixupNode.parent.rightChild.color;
					fixupNode.parent.color = Color.BLACK;
					sibling.rightChild.color = Color.BLACK;
					leftRotate(tree, fixupNode.parent);
					fixupNode = tree.root;
				}
			}
		}
		fixupNode.color = Color.BLACK;
	}
}

class Node {
	Color color;
	int key;
	Node leftChild;
	Node rightChild;
	Node parent;

}

class Tree {
	Node root;
	Node nil;
}

enum Color {
	RED, BLACK
};


// 케이스에 대한 테스트 함수
// 레드블랙 트리 특성이 맞는지 확인하는 함수
// ㄴ 경로상의 블랙 수가 같는지 등등.
