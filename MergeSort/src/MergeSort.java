public class MergeSort {
	public static void main(String[] args) {
		int[][] arr = {
				// 원소개수 0개
				{},
				// 원소개수 1개
				{ 0 },
				// 원소개수 2개
				{ 3, 4 },
				// 원소개수 n개
				{ 3, 4, 7, 1, 0, 5, 8, 11, -3, 6, -7, 9 },

				// 원소 순정렬 상태 (음수, 0 포함, 오름차순)
				{ -11, -8, -7, -3, -1, 0, 1, 2, 4, 6, 8, 12 },
				// 원소 역정렬 상태 (음수, 0 포함, 내림차순)
				{ 12, 8, 6, 4, 2, 1, 0, -1, -3, -7, -8, -11 },
				// 원소 무작위 상태
				{ 4, 2, 64, 31, 34, 5, 22, 423, -342, -43, 0, 41, -5, 8, 37 },
				// 같은값의 원소 n개 무작위 포함
				{ 64, 2, 64, 31, -43, 5, 22, 64, -342, -43, 0, 41, -5, -43, 37 },
				// 같은값은 원소 n개 순차 포함
				{ 3, -4, -4, -4, 0, 5, 8, 11, 11, 11, 11, 9 }, };

		MergeSort m = new MergeSort();
		for (int i = 0; i < arr.length; i++){
			m.mergeSort(arr[i], 0, arr[i].length);
			System.out.println(m.isSorted(arr[i]));
			
//			for (int j = 0; j < arr[i].length; j++) {
//				System.out.print(arr[i][j] + " ");
//			}
//			System.out.println();
		}
		
	}

	public void merge(int[] arr, int first, int last) {
		int leftIndex, rightIndex;
		int[] buffer = new int[last - first];

		copyBlock(arr, buffer, first, last);

		// 버퍼의 인덱스 이므로 왼쪽은 무조건 0
		leftIndex = 0;
		// 오른쪽 인덱스는 중앙부터 시작한다.
		rightIndex = (last - first) / 2;

		for (int index = first; index < last; index++) {
			// 왼쪽 인덱스가 오른쪽 인덱스를 침범하면 오른쪽꺼 넣고 ++
			if (leftIndex >= (last - first) / 2) {
				arr[index] = buffer[rightIndex++];
				// 오른쪽 인덱스가 버퍼의 크기를 벗어나면 왼쪽꺼 넣고 ++
			} else if (rightIndex >= last - first) {
				arr[index] = buffer[leftIndex++];
				// 양쪽 비교해서 왼쪽이 크면 왼쪽꺼
			} else if (buffer[leftIndex] < buffer[rightIndex]) {
				arr[index] = buffer[leftIndex++];
				// 오른쪽이 크면 오른쪽꺼
			} else {
				arr[index] = buffer[rightIndex++];
			}
		}
	}

	public void copyBlock(int[] arr, int[] buffer, int first, int last) {
		for (int i = 0; i < last - first; i++) {
			//카피하는 과정에서 배열 갯수만큼 이므로 버퍼는 0부터 시작한다.
			buffer[i] = arr[i + first];
		}
	}

	public void mergeSort(int[] arr, int first, int last) {
		// 종료조건으로 빠져나가야 하는데 인덱스 범위를 배열 크기로 정했으니 
		// (이 경우가 처음 넣는 값과 분할된 인덱스가 합리적이다.)
		// 인덱스 차이가 1이면 원소가 1개이므로 1보다 큰 경우에만 진입하도록 한다. 
		if (last - first > 1) {
			int middle = (last + first) / 2;
			mergeSort(arr, first, middle);
			// 쪼갠 뒤에 middle 뒤에 +1이 붙지 않는 이유는 인덱스 범위가 배열크기
			mergeSort(arr, middle, last);
			//2개 이상만 넣고 병합한다.
			merge(arr, first, last);
		}
	}
	
	int isSorted(int[] arr) {
		for (int i = 0; i < arr.length - 1; i++) {
			if (arr[i] > arr[i + 1])
				return -1;
		}
		return 0;
	}

}
