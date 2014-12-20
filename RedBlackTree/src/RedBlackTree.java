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
			}else{
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
