public class MaximumSubarray {

	static int maxLeft, maxRight;

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
				// 같은cccc값은 원소 n개 순차 포함
				{ 3, -4, -4, -4, 0, 5, 8, 11, 11, 11, 11, 9 },

				// MaximumSubarray가 중간에 있을 경우
				{ -3, -2, 3, 2, 9, -4, 7, 12, -5, -3, 1, -2 },
				// MaximumSubarray가 왼쪽 끝에 있을 경우
				{ 4, 9, -1, 3, -2, -11, -4, 5, -2 },
				// MaximumSubarray가 오른쪽 끝에 있을 경우
				{ -2, 5, -4, -11, -2, 3, -1, 9, 4 }

		};

		MaximumSubarray m = new MaximumSubarray();
		MaximumSubarrayN2 m2 = new MaximumSubarrayN2();

		SubArray s = null;
		for (int i = 0; i < arr.length; i++) {
			// 배열 넘버가 아닌 인덱스 넘버 기준. 그래서 length뒤에 -1
			//s = m.FindMaximumSubarray(arr[i], 0, arr[i].length - 1);
			s = m2.maxSubArray(arr[i]);
			
			if (s != null) {
				System.out.print("Sum : " + s.sum);
				System.out.print("  LeftIndex : " + s.leftIndex);
				System.out.println("  RightIndex : " + s.rightIndex);
			}
		}

	}

	// 3개의 인자를 위한 max함수
	public SubArray max(SubArray a, SubArray b) {
		return (a.sum > b.sum) ? a : b;
	}

	public SubArray max(SubArray a, SubArray b, SubArray c) {
		return max(max(a, b), c);
	}

	SubArray findMaxCrossingSubarray(int[] arr, int low, int mid, int high) {
		SubArray s = new SubArray();
		// 코드의 폼을 맞추기 위해 따로 선언.
		int sum;

		// leftSum은 마이너스 무한 값으로 초기화.
		int leftSum = Integer.MIN_VALUE;
		sum = 0;
		// 왼쪽으로 한칸씩 이동하면서 누적.
		for (int i = mid; i >= low; i--) {
			sum += arr[i];
			// 가장 큰 경우를 담고 인덱스를 저장한다.
			if (sum > leftSum) {
				leftSum = sum;
				s.leftIndex = i;
			}
		}

		int rightSum = Integer.MIN_VALUE;
		sum = 0;
		for (int i = mid + 1; i <= high; i++) {
			sum += arr[i];
			if (sum > rightSum) {
				rightSum = sum;
				s.rightIndex = i;
			}
		}
		s.sum = leftSum + rightSum;
		return s;

	}

	SubArray FindMaximumSubarray(int[] arr, int low, int high) {

		SubArray s = new SubArray();

		// 방어코드. 원소가 없을때 문제가 된다.
		if (arr.length == 0)
			return null;

		// 위 아래가 같은 경우 subarray의 원소가 1개인 경우, 원래 배열의 2개의 차
		if (low == high) {
			s.sum = arr[low];
			s.leftIndex = low;
			s.rightIndex = high;
			return s;
		}

		// 중간값을 생성한다.
		int mid = (low + high) / 2;

		// max함수를 통해 sum들의 가장 큰 값을 리턴한다.
		return max(FindMaximumSubarray(arr, low, mid),
				FindMaximumSubarray(arr, mid + 1, high),
				findMaxCrossingSubarray(arr, low, mid, high));
	}
}

class SubArray {
	int sum;
	int leftIndex;
	int rightIndex;
}

class MaximumSubarrayN2 {
	public SubArray maxSubArray(int[] arr) {
		SubArray s = new SubArray();
		int maxSum = Integer.MIN_VALUE;
		int sum = 0;
		for (int i = 0; i < arr.length; i++) {
			for (int j = i; j < arr.length; j++) {
				sum += arr[j];
				if(sum > maxSum){
					maxSum = sum;
					s.sum = maxSum;
					s.leftIndex = i;
					s.rightIndex = j;
				}
			}
			sum = 0;
		}
		return s;
	}
}
