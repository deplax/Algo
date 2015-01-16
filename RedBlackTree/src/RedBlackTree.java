import java.util.LinkedList;
import java.util.Queue;

public class RedBlackTree {
	public static void main(String[] args) {

		RedBlackTree rbt = new RedBlackTree();
		Tree tree = new Tree();

		rbt.insert(tree, new Node(10));
		rbt.insert(tree, new Node(12));
		rbt.insert(tree, new Node(-6));
		rbt.insert(tree, new Node(2));
		rbt.insert(tree, new Node(3));
		rbt.insert(tree, new Node(54));
		rbt.insert(tree, new Node(65));
		rbt.insert(tree, new Node(72));
		rbt.insert(tree, new Node(99));
		rbt.insert(tree, new Node(100));
		rbt.delete(tree, tree.root);
		rbt.printTree(tree, 2);

		// 1.노드는 RED or BLACK
		// 2.ROOT = BLACK
		// 3.LEAF = BLACK
		// 4.RED는 CHILD로 BLACK을 둘 수 있음 (즉 RED RED는 불가능)
		// 5.단순경로상의 BLACK갯수는 동일
		
		System.out.println("--------------------------------------------");
		
		System.out.println("1.Red and Black? : " + rbt.isRedBlack(tree));
		System.out.println("2.Root = BLACK? : " + rbt.isRootBlack(tree));
		System.out.println("3.NilNode is Black? : " + rbt.isNilBlack(tree));
		System.out.println("4.RedNode child Black? : " + rbt.isRedChildBlack(tree));
		System.out.println("5.SimplePath Nodecount same? : " + rbt.isRedChildBlack(tree));
		
	}
	
	public boolean isRedBlack(Tree tree){
		LinkedList<Node> nodeList = createLinkedList(tree);
		for(Node n : nodeList){
			if(!(n.color == Color.BLACK || n.color == Color.RED) && !(n.color == null))
				// 마지막 null 조건은 사실상 없어야 하나 트리 출력과정에서의 emptyNode를 거르기 위해 추가
				return false;
		}
		return true;
	}

	public boolean isRootBlack(Tree tree) {
		if (tree.nodeCnt > 0)
			if (tree.root.color == Color.BLACK)
				return true;
			else
				return false;
		return false;
	}
	
	public boolean isNilBlack(Tree tree){
		if(tree.nil.color == Color.BLACK)
			return true;
		return false;
	}
	
	public boolean isRedChildBlack(Tree tree){
		LinkedList<Node> nodeList = createLinkedList(tree);
		for(Node n : nodeList){
			if(n.color == Color.RED)
				if((n.leftChild.color == Color.RED) || (n.rightChild.color == Color.RED))
					return false;
		}
		return true;
	}
	
	public boolean isBlackHeightSame(Tree tree){
		if(checkBlackHeight(tree.root) != -1)
			return false;
		return true;
	}
	
	public int checkBlackHeight(Node node){
		Tree tree = new Tree();
		if(node == tree.nil)
			return -1;
		
		int leftBlackHeight = 0;
		int rightBlackHeight = 0;
		
		if(node.leftChild != tree.nil){
			int cbh = checkBlackHeight(node.leftChild);
			if(cbh == -1)
				return -1;
			leftBlackHeight += cbh;
		}
		
		if(node.rightChild != tree.nil){
			int cbh = checkBlackHeight(node.rightChild);
			if(cbh == -1)
				return -1;
			rightBlackHeight += cbh;
		}
		
		if(leftBlackHeight != rightBlackHeight)
			return -1;
		
		if(node.color == Color.BLACK)
			return leftBlackHeight + 1;
		else
			return leftBlackHeight;
		
	}
	


	// 출력을 위한 함수. 트리를 간격에 맞춰 콘솔에 뿌린다.
	public void printTree(Tree tree, int pad) {
		LinkedList<Node> nodeList = createLinkedList(tree);
		int maxLevel = maxLevel(nodeList);
		int maxLevelCnt = (int) Math.pow(2, maxLevel) + ((int) Math.pow(2, maxLevel) / 2);

		int nodeSize = nodeSize(nodeList) + pad;
		int[] nodeSizeArr = createNodeSizeArray(nodeList, pad);
		int maxWidth = (nodeSize + 1) * maxLevelCnt;

		int nodeNum = 0;
		int nodeCnt = nodeSizeArr.length;
		boolean tictok = false; // true = node;
		for (int level = 0; level <= maxLevel; level++) {
			int prevSpace = 0;
			for (int col = 0; col < Math.pow(2, level) * 2; col++) {
				if (nodeCnt == 0)
					break;
				if (tictok == false) {
					// space
					int space = (int) (maxWidth / Math.pow(2, level + 1) * (col + 1));
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
					nodeCnt--;
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
			System.out.print(" ");
	}

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
		Node x = y.leftChild;
		y.leftChild = x.rightChild; // 떨거지 처리
		if (x.rightChild != tree.nil)
			x.rightChild.parent = y;
		x.parent = y.parent; // y의 부모처리
		if (y.parent == tree.nil) // x의 부모처리
			tree.root = x;
		else if (y == y.parent.rightChild)
			y.parent.rightChild = x;
		else
			y.parent.leftChild = x;
		x.rightChild = y;
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
		tree.nodeCnt++;
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
		tree.nodeCnt--;
		if (erasedColor == Color.BLACK) // BLACK이면 height문제 처리
			deleteFixup(tree, fixupNode);
	}

	// succesorNode Finder
	public Node treeMinimum(Node node) {
		Tree tree = new Tree();
		while (!node.leftChild.equals(tree.nil))
			node = node.leftChild;
		return node;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + key;
		result = prime * result + ((leftChild == null) ? 0 : leftChild.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result + ((rightChild == null) ? 0 : rightChild.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (color != other.color)
			return false;
		if (key != other.key)
			return false;
		if (leftChild == null) {
			if (other.leftChild != null)
				return false;
		} else if (!leftChild.equals(other.leftChild))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		if (rightChild == null) {
			if (other.rightChild != null)
				return false;
		} else if (!rightChild.equals(other.rightChild))
			return false;
		return true;
	}

	Node(int key) {
		this.key = key;
		this.color = Color.RED;
	}

	Node() {
	};

	Color color;
	int key;
	Node leftChild;
	Node rightChild;
	Node parent;

}

class Tree {
	Tree() {
		nil = new Node();
		nil.color = Color.BLACK;
		nil.leftChild = null;
		nil.rightChild = null;
		nil.parent = null;

		root = nil;
	}

	Node root;
	Node nil;
	int nodeCnt;
}

enum Color {
	RED, BLACK
};