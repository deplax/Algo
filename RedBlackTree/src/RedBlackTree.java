import java.util.LinkedList;
import java.util.Queue;

public class RedBlackTree {
	public static void main(String[] args) {
		Tree tree = new Tree();
		Node node0 = new Node();
		Node node1 = new Node();
		Node node2 = new Node();
		Node node3 = new Node();
		Node node4 = new Node();
		Node node5 = new Node();

		node0.key = 10;
		node1.key = 50;
		node2.key = 20;
		node3.key = 20;
		node4.key = 70;
		node5.key = 30;

		node0.color = Color.BLACK;
		node1.color = Color.RED;
		node2.color = Color.BLACK;
		node3.color = Color.RED;
		node4.color = Color.BLACK;
		node5.color = Color.RED;

		node0.leftChild = node1;
		node0.rightChild = node2;
		node1.leftChild = node3;
		node1.rightChild = node4;
		node2.leftChild = tree.nil;
		node2.rightChild = node5;
		node3.leftChild = tree.nil;
		node3.rightChild = tree.nil;
		node4.leftChild = tree.nil;
		node4.rightChild = tree.nil;
		node5.leftChild = tree.nil;
		node5.rightChild = tree.nil;

		tree.root = node0;
		tree.nodeCnt = 6;

		RedBlackTree rbt = new RedBlackTree();
		LinkedList<Node> nodeList = rbt.createLinkedList(tree);
		rbt.printTree(tree, 2);
	}

	public void printTree(Tree tree, int pad) {
		LinkedList<Node> nodeList = createLinkedList(tree);
		int maxLevel = maxLevel(nodeList);
		int maxLevelCnt = (int) Math.pow(2, maxLevel) + ((int) Math.pow(2, maxLevel) / 2);

		int nodeSize = nodeSize(nodeList) + pad;
		int[] nodeSizeArr = createNodeSizeArray(nodeList, pad);
		System.out.println("nodeSize : " + nodeSize);

		int maxWidth = (nodeSize + 1) * maxLevelCnt;

		int nodeNum = 0;
		boolean tictok = false; // true = node;
		for (int level = 0; level <= maxLevel; level++) {
			int prevSpace = 0;
			for (int col = 0; col < Math.pow(2, level) * 2; col++) {
				if (tictok == false) {
					// space
					int space = (int)(maxWidth / Math.pow(2, level + 1) * (col + 1));
					int nodeHalf = nodeSizeArr[nodeNum + (col / 2)] / 2;
					int prevNodeSize = (col / 2) * (nodeSize);
					printSpace(space - nodeHalf - prevNodeSize - prevSpace);
					prevSpace += space - nodeHalf - prevNodeSize - prevSpace;
					tictok = true;
				} else {
					// node
					Node node = nodeList.get(nodeNum + (col / 2));
					if (Color.BLACK == node.color)
						System.out.print("B:" + node.key);
					else if (Color.RED == node.color)
						System.out.print("R:" + node.key);
					else
						printSpace(pad + 2);
					tictok = false;
				}
			}
			nodeNum += Math.pow(2, level);
			System.out.println();
		}

	}

	// 출력을 위한 함수. 트리의 최대 깊이를 구한다. [0부터 시작]
	public int maxLevel(LinkedList<Node> nodeList) {
		int nodeCnt = nodeList.size();
		int level = 0;
		while (nodeCnt > 0) {
			nodeCnt /= 2;
			level++;
		}
		return level - 1;
	}

	// 출력을 위한 함수. 각 노드의 평균길이를 구한다.
	public int nodeSize(LinkedList<Node> nodeList) {
		int cnt = 0;
		int sum = 0;
		for (Node n : nodeList) {
			if (n.key != 0) {
				String str = String.valueOf(n.key);
				cnt++;
				sum += str.length();
			}
		}
		return (int) sum / cnt;
	}

	// 출력을 위한 함수. BSF를 통해 LinkedList로 변환한다.
	public LinkedList<Node> createLinkedList(Tree tree) {
		LinkedList<Node> nodeList = new LinkedList<Node>();
		int cnt = tree.nodeCnt;

		// tree -> queue -> LinkedList
		Queue<Node> queue = new LinkedList<Node>();
		if (tree.root == null)
			return null;
		queue.offer(tree.root);

		Node emptyNode = new Node();

		while (!queue.isEmpty() && cnt != 0) {
			Node tempNode = queue.poll();
			nodeList.addLast(tempNode);
			if (!tempNode.equals(tree.nil) && !tempNode.equals(emptyNode))
				cnt--;

			if (tempNode == emptyNode)
				continue;

			if (tempNode.leftChild == tree.nil)
				queue.offer(emptyNode);
			else
				queue.offer(tempNode.leftChild);
			if (tempNode.rightChild == tree.nil)
				queue.offer(emptyNode);
			else
				queue.offer(tempNode.rightChild);
		}

		return nodeList;
	}

	// 출력을 위한 함수. 각 노드의 길이를 배열에 저장한다.
	public int[] createNodeSizeArray(LinkedList<Node> nodeList, int pad) {
		int[] nodeSizeArr = new int[nodeList.size()];
		for (int i = 0; i < nodeList.size(); i++) {
			String str = String.valueOf(nodeList.get(i).key);
			nodeSizeArr[i] = str.length() + pad;
		}
		return nodeSizeArr;
	}

	// 출력을 위한 함수. 공백을 출력한다.
	public void printSpace(int num) {
		for (int i = 0; i < num; i++)
			System.out.print("*");
	}

	// 1.노드는 RED or BLACK
	// 2.ROOT = BLACK
	// 3.LEAF = BLACK
	// 4.RED는 CHILD로 BLACK을 둘 수 있음 (즉 RED RED는 불가능)
	// 5.단순경로상의 BLACK갯수는 동일
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
		Node parentNode = tree.nil; // 초기화
		Node curNode = tree.root; // 시작이니 루트로 잡고
		while (curNode != tree.nil) { // 리프 노드 찾아가기
			parentNode = curNode;
			if (node.key < curNode.key)
				curNode = curNode.leftChild;
			else
				curNode = curNode.rightChild;
		}
		node.parent = parentNode; // 찾으면 부모노드 저장
		if (parentNode == tree.nil) // 루트 처리
			tree.root = node;
		else if (node.key < parentNode.key) // 오른쪽 왼쪽 판별해서 삽입
			parentNode.leftChild = node;
		else
			parentNode.rightChild = node;
		node.leftChild = tree.nil; // 삽입후 초기화
		node.rightChild = tree.nil;
		node.color = Color.RED;
		insertFixup(tree, node); // FIXUP
	}

	public void insertFixup(Tree tree, Node node) {
		while (node.parent.color == Color.RED) { // 초기화를 RED로 했으니 무조건 진입
			if (node.parent == node.parent.parent.leftChild) { // node의 위치 판단
				Node uncleNode = node.parent.parent.rightChild; // uncle 저장
				if (uncleNode.color == Color.RED) { // uncle이 RED면 CASE1
					node.parent.color = Color.BLACK;
					uncleNode.color = Color.BLACK;
					node.parent.parent.color = Color.RED;
					node = node.parent.parent;
				} else { // uncle이 BLACK
					if (node == node.parent.rightChild) { // 노드가 RIGHT CASE3
						node = node.parent;
						leftRotate(tree, node);
					}
					node.parent.color = Color.BLACK; // 노드가 LEFT CASE2
					node.parent.parent.color = Color.RED;
					rightRotate(tree, node.parent.parent);
				}
			} else { // LEFT RIGHT 대칭
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
		tree.root.color = Color.BLACK; // 모두 처리후 ROOT BLACK
	}

	// 이 함수는 서브트리 덩어리(plantNode)를 특정 노드(oldNode)에 붙인다.
	public void transPlant(Tree tree, Node oldNode, Node plantNode) {
		if (oldNode.parent == tree.nil) // 루트일 경우
			tree.root = plantNode; // 루트 임명
		else if (oldNode == oldNode.parent.leftChild) // 왼쪽이면
			oldNode.parent.leftChild = plantNode; // 왼쪽에 붙인다.
		else
			// 오른쪽이면
			oldNode.parent.rightChild = plantNode; // 오른쪽에 붙인다.
		plantNode.parent = oldNode.parent; // 부모 처리
	}

	public void delete(Tree tree, Node node) {
		Node fixupNode;
		Color erasedColor = node.color; // 색상 저장
		if (node.leftChild == tree.nil) { // 왼쪽이 비었으면
			fixupNode = node.rightChild;
			transPlant(tree, node, node.rightChild); // 오른쪽을 올린다.
		} else if (node.rightChild == tree.nil) { // 오른쪽이 비었으면
			fixupNode = node.leftChild;
			transPlant(tree, node, node.leftChild); // 왼쪽을 올린다.
		} else { // 양쪽다 있으면
			Node succesor = treeMinimum(node.rightChild); // succesor을 찾는다.
			erasedColor = succesor.color; // 색상 저장
			fixupNode = succesor.rightChild;
			if (succesor.parent == node) { // 삭제할 노드가 인접일 경우
				fixupNode.parent = succesor; // 이건 구조상 NIL이 하나라서
			} else {
				transPlant(tree, succesor, succesor.rightChild); // 인접이 아닐경우
				succesor.rightChild = node.rightChild;
				succesor.rightChild.parent = succesor;
			}
			transPlant(tree, node, succesor); // 옮김옮김
			succesor.leftChild = node.leftChild;
			succesor.leftChild.parent = succesor;
			succesor.color = node.color;
		}
		if (erasedColor == Color.BLACK) // BLACK이면 height문제 처리
			deleteFixup(tree, fixupNode);
	}

	// succesorNode Finder
	public Node treeMinimum(Node node) {
		Tree tree = new Tree();
		while (node != tree.nil)
			node = node.leftChild;
		return node.parent;
	}

	public void deleteFixup(Tree tree, Node fixupNode) {
		// 루트가 아니고 블랙일 때
		while (fixupNode != tree.root && fixupNode.color == Color.BLACK) {
			// 난 왼쪽자식이야
			if (fixupNode == fixupNode.parent.leftChild) {
				Node sibling = fixupNode.parent.rightChild;
				if (sibling.color == Color.RED) { // case5
					sibling.color = Color.BLACK;
					fixupNode.parent.color = Color.RED;
					leftRotate(tree, fixupNode);
					sibling = fixupNode.parent.rightChild;
				}
				if (sibling.leftChild.color == Color.BLACK
						&& sibling.rightChild.color == Color.BLACK) { // case1
					sibling.color = Color.RED;
					fixupNode = fixupNode.parent;
				} else {
					if (sibling.rightChild.color == Color.BLACK) { // case3
						sibling.leftChild.color = Color.BLACK;
						sibling.color = Color.RED;
						rightRotate(tree, sibling);
						sibling = fixupNode.parent.rightChild;
					}
					sibling.color = fixupNode.parent.rightChild.color; // case2
					fixupNode.parent.color = Color.BLACK;
					sibling.rightChild.color = Color.BLACK;
					leftRotate(tree, fixupNode.parent);
					fixupNode = tree.root;
				}
			} else {
				// Right에 대하여 그대로 반복
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
	int nodeCnt;
}

enum Color {
	RED, BLACK
};

// 케이스에 대한 테스트 함수
// 레드블랙 트리 특성이 맞는지 확인하는 함수
// ㄴ 경로상의 블랙 수가 같는지 등등.