public class HeapSort {

	static int size;

	public static void main(String[] args) {

		HeapSort h = new HeapSort();

		int[] arr = { 0, 4, 1, 3, 2, 16, 9, 10, 14, 8, 7, -1 };
		size = arr.length - 2;
		h.printArr(arr);
		h.bulidMaxHeap(arr);
		h.printArr(arr);
		// h.heapSort(arr);
		h.heapIncreaseKey(arr, 9, 15);
		h.printArr(arr);
		h.maxHeapInsertion(arr, 11);
		h.printArr(arr);
	}

	// 아예 클래스화 시키면 size라는 변수가 필요없을 듯 한데 고민필요.
	// Node로 만들어볼까.. 그게 나으려나.

	public void heapSort(int[] arr) {
		bulidMaxHeap(arr);
		// 제일 큰 블럭을 맨 뒤로 스왑!
		for (int i = size; i > 1; i--) {
			int temp = arr[1];
			arr[1] = arr[i];
			arr[i] = temp;
			size--;
			maxHeapify(arr, 1);
		}
	}

	public void printArr(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + "  ");
		}
		System.out.println();
	}

	public void bulidMaxHeap(int[] arr) {
		for (int i = size / 2; i > 0; i--) {
			maxHeapify(arr, i);
		}
	}

	public void maxHeapify(int[] arr, int i) {
		int left = 2 * i;
		int right = 2 * i + 1;

		int largest;

		if (left <= size && arr[left] > arr[i])
			largest = left;
		else
			largest = i;

		if (right <= size && arr[right] > arr[largest])
			largest = right;

		if (largest != i) {
			int temp = arr[i];
			arr[i] = arr[largest];
			arr[largest] = temp;

			maxHeapify(arr, largest);
		}

	}

	// 힙에서 최대값 제거
	public int heapExtractMaximum(int[] arr) {
		if (size < 1) {
			System.out.println("heap underflow");
			System.exit(1);
		}
		int max = arr[1];
		arr[1] = arr[size--];
		maxHeapify(arr, 1);
		return max;
	}

	// 힙에 있는 원소 증가 (교체되는 값이 항상 커야함)
	public void heapIncreaseKey(int[] arr, int i, int key) {
		if (key < arr[i])
			System.out.println("new key smaller then current key");
		arr[i] = key;
		while (i > 1 && arr[i / 2] < arr[i]) {
			int temp = arr[i];
			arr[i] = arr[i / 2];
			arr[i / 2] = temp;
			i = i / 2;
		}
	}

	// 힙에 원소 추가
	public void maxHeapInsertion(int[] arr, int key) {
		size++;
		arr[size] = Integer.MIN_VALUE;
		heapIncreaseKey(arr, size, key);
	}
}

/*
 * public Node[] setNodeValue(int[] arrInt) { Node[] arrNode = new
 * Node[arrInt.length + 1]; for (int i = 0; i < arrInt.length; i++){ arrNode[i +
 * 1] = new Node(); arrNode[i + 1].value = arrInt[i]; } return arrNode; }
 */

/*
 * public void printNodeValue(Node[] arrNode) { for(int i = 1; i <
 * arrNode.length; i++){ System.out.print(arrNode[i].value + "  "); }
 * System.out.println(); }
 */

/*
 * class Node { Node left; Node right; int value; }
 */