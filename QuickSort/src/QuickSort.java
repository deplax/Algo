public class QuickSort {
	public static void main(String[] args) {

		int[] arr = { 2, 8, 7, 1, 3, 5, 6, 4 };
		QuickSort q = new QuickSort();

		// q.partition(arr, 0, arr.length - 1);
		q.printArr(arr);
		// System.out.println(q.partition(arr, 0, arr.length - 1));
		q.randomizeQuickSort(arr, 0, arr.length - 1);
		// q.quickSort(arr, 0, arr.length - 1);
		q.printArr(arr);

	}

	public void printArr(int[] arr) {
		for (int i = 0; i < arr.length; i++)
			System.out.print(arr[i] + "  ");
		System.out.println();
	}

	public int partition(int[] arr, int start, int end) {
		int pivotValue = arr[end];
		int endOfLowBlock = start - 1;

		for (int i = start; i < end; i++) {
			if (arr[i] <= pivotValue) {
				endOfLowBlock++;
				int temp = arr[endOfLowBlock];
				arr[endOfLowBlock] = arr[i];
				arr[i] = temp;
			}
		}
		int temp = arr[endOfLowBlock + 1];
		arr[endOfLowBlock + 1] = arr[end];
		arr[end] = temp;
		return endOfLowBlock + 1;
	}

	public void quickSort(int[] arr, int start, int end) {
		if (start < end) {
			int key = partition(arr, start, end);
			quickSort(arr, start, key - 1);
			quickSort(arr, key + 1, end);
		}
	}

	public int randomizePartition(int[] arr, int start, int end) {
		int i = randNum(start, end);
		int temp = arr[end];
		arr[end] = arr[i];
		arr[i] = temp;
		return partition(arr, start, end);
	}

	public void randomizeQuickSort(int[] arr, int start, int end) {
		if (start < end) {
			int key = randomizePartition(arr, start, end);
			randomizeQuickSort(arr, start, key - 1);
			randomizeQuickSort(arr, key + 1, end);
		}
	}

	public int randNum(int a, int b) {
		return (int) (Math.random() * (b - a + 1)) + a;
	}
}
