public class Sort {
	public static void main(String[] args) {

		Sort s = new Sort();
		
		int[][] arr = {
				//원소개수 0개
				{},
				//원소개수 1개
				{0},
				//원소개수 2개
				{3, 4},
				//원소개수 n개
				{3, 4, 7, 1, 0, 5, 8, 11, -3, 6, -7, 9},
				
				//원소 순정렬 상태 (음수, 0 포함, 오름차순)
				{-11, -8, -7, -3, -1, 0, 1, 2, 4, 6, 8, 12},
				//원소 역정렬 상태 (음수, 0 포함, 내림차순)
				{12, 8, 6, 4, 2, 1, 0, -1, -3, -7 ,-8, -11},
				//원소 무작위 상태
				{4, 2, 64, 31, 34, 5, 22, 423, -342, -43, 0, 41, -5, 8, 37},
				//같은값의 원소 n개 무작위 포함
				{64, 2, 64, 31, -43, 5, 22, 64, -342, -43, 0, 41, -5, -43, 37},
				//같은값은 원소 n개 순차 포함
				{3, -4, -4, -4, 0, 5, 8, 11, 11, 11, 11, 9},
		};

		for(int i = 0; i < arr.length; i++)
		{
			s.insertionSort(arr[i]);
			//s.printArr(arr[i]);
			System.out.println(s.isSorted(arr[i]));
		}
	}

	void insertionSort(int[] arr) {
		
		//진입할 때 삽입지점이 필요하므로 0이 아닌 1부터 끝까지 하나씩 뽑기
		for (int i = 1; i < arr.length; i++) {
			//일단 블럭을 하나 빼두고
			int temp = arr[i];
			//꺼낸 블럭 앞부터 첫 블럭까지 탐색. 여기에서 j초기화
			int j = i - 1;
			//블럭이 인텍스 0 이내이고, 블럭의 값이 뽑아둔 블럭보다 클때 루프
			while(j >= 0 && arr[j] > temp)
			{
				//한칸씩 앞으로 당긴다.
				arr[j + 1] = arr[j];
				j--;
			}
			
			//빠져나올 때 j-- 이므로 + 1 후에 temp를 넣어준다.
			arr[j + 1] = temp;
		}
	}

	int isSorted(int[] arr) {
		for (int i = 0; i < arr.length - 1; i++) {
			if (arr[i] > arr[i + 1])
				return -1;
		}
		return 0;
	}
	
	void printArr(int[] arr){
		for (int i : arr)
			System.out.print(i + ", ");
	}
}
