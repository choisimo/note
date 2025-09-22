import java.util.Scanner;

public class mergeSort{
	private static int comparisonCount = 0;
	//private static Logger log = Logger.getLogger(mergeSort.class.getName());
	public static void main(String[] args) {
		Scanner inputNumber = new Scanner(System.in);
		// 원소의 크기 입력
		int arrSize = inputNumber.nextInt();
		if (arrSize < 1 | arrSize > 10000) {
			//log.info("1~10000 사이의 수를 입력해주세요.");
			System.out.println("1~10000 사이의 수를 입력해주세요.");
			return;
		}

		// 원소 입력 및 배열에 저장
		int[] arr = new int[arrSize];
		for (int i = 0; i < arrSize; i++) {
			arr[i] = inputNumber.nextInt();
		}
		// Scanner 닫기 - 더 이상 입력 없음. 메모리 누수 방지
		inputNumber.close();

		// divide
		divide(arr, 0, arrSize - 1);
		
		// // 정렬된 배열 출력
		// for (int i = 0; i < arrSize; i++) {
		// 	System.out.println(arr[i]);
		// }

		// 비교 횟수 출력
		System.out.println(comparisonCount);
	}

	// divide
	private static void divide(int[] arr, int left, int right) {
		// left < right 인 경우에만 divide 수행. 즉, 2개 이상의 원소가 있는 경우에만
		if (left < right) {
			// mid 설정
			int mid = (left + right) / 2;
			// LEFT divide
			divide(arr, left, mid);
			// RIGHT divide
			divide(arr, mid + 1, right);
			// conquer
			merge(arr, left, mid, right);
		}
	}

	private static void merge(int[] arr, int left, int mid, int right) {
		// left ~ mid : LEFT
		// mid+1 ~ right : RIGHT

		// LEFT, RIGHT 배열 크기 설정
		int leftSize = mid - left + 1;
		int rightSize = right - mid;

		// LEFT, RIGHT 배열 생성
		int[] leftArr = new int[leftSize];
		int[] rightArr = new int[rightSize];

		// LEFT, RIGHT 배열에 값 복사
		for (int i = 0; i < leftSize; i++) {
			leftArr[i] = arr[left + i];
		}
		for (int j = 0; j < rightSize; j++) {
			rightArr[j] = arr[mid + 1 + j];
		}

		// LEFT, RIGHT 배열 병합
		int i = 0; // LEFT 배열 인덱스
		int j = 0; // RIGHT 배열 인덱스
		int k = left; // 원본 배열 인덱스

		while (i < leftSize && j < rightSize) {
			comparisonCount++; // 비교 횟수 증가
			if (leftArr[i] < rightArr[j]) {
				arr[k] = leftArr[i];
				i++;
			} else {
				arr[k] = rightArr[j];
				j++;
			}
			k++;
		}

		// LEFT 배열에 남은 원소 복사
		while (i < leftSize) {
			arr[k] = leftArr[i];
			i++;
			k++;
		}

		// RIGHT 배열에 남은 원소 복사
		while (j < rightSize) {
			arr[k] = rightArr[j];
			j++;
			k++;
		}
	}
}
