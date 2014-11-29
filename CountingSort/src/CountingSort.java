public class CountingSort {
	public static void main(String[] args) {

		CountingSort c = new CountingSort();
		int[] originArr = { 2, 5, 3, 0, 2, 3, 0, 3 };
		int[] outputArr = new int[originArr.length];
		int k = 5;
		c.printArr(originArr);
		c.countingSort(originArr, outputArr, k);
		c.printArr(outputArr);
	}

	public void printArr(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + "  ");
		}
		System.out.println();
	}

	public void countingSort(int[] originArr, int[] outputArr, int k) {
		int[] tempArr = new int[k + 1];

		for (int i = 0; i < originArr.length; i++) {
			tempArr[originArr[i]]++;
		}

		for (int i = 1; i <= k; i++) {
			tempArr[i] += tempArr[i - 1];
		}
		printArr(tempArr);

		for (int i = originArr.length - 1; i > 0; i--) {
			tempArr[originArr[i]]--;
			outputArr[tempArr[originArr[i]]] = originArr[i];
		}
	}
}
