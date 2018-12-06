package prim;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {

	final static Integer Inf = 10000;

	public void Prim(int[][] array) {

	}

	public void union(Node a, Node b) {
		a = find(a);
		b = find(b);
		if (a.getKey() == b.getKey()) {
			b.setParent(a);

		}
	}
	
	public Node find(Node x) {
		if (x.getParent().equals(x)) { // ��Ʈ ����� ��ȯ
			return x;
		}
		return find(x.getParent()); // �ƴϸ� �θ� ��带 ã�� �ö󰣴�.
	}
	
	public void minHeapSort(int[] arr) {
		int size = arr.length - 1;
		buildMinHeap(arr);
		// for (int i = size; i > 0; i--) {
		// swap(arr, 0, i);
		// size -= 1;
		// min_Heapify(arr, 0, size);
		// }
	}

	public void buildMinHeap(int[] arr) {
		int size = arr.length - 1;
		for (int i = size / 2; i >= 0; i--) {
			min_Heapify(arr, i, size);
		}
	}

	public void min_Heapify(int[] arr, int i, int len) {
		int size = len;
		int leftChild = (i * 2) + 1;
		int rightChild = (i * 2) + 2;
		int min = 0;

		// ���ؼ� �� ���� Child�� min�� �Ҵ��Ѵ�.
		if (leftChild <= size && arr[leftChild] < arr[i]) {
			min = leftChild;
		} else {
			min = i;
		}

		if (rightChild <= size && arr[rightChild] < arr[min]) {
			min = rightChild;
		}
		if (min != i) {
			swap(arr, i, min);
			min_Heapify(arr, min, len);
		}
	}

	private void swap(int arr[], int idx1, int idx2) {
		int tmp = arr[idx1];
		arr[idx1] = arr[idx2];
		arr[idx2] = tmp;
	}

	public static void main(String[] args) throws IOException {
		Main main = new Main();
		List<Integer> fileList = new ArrayList<Integer>();
		int[][] array = new int[7][7];

		// ���� ���� �迭 �ʱ�ȭ
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				if (i == j) { // �밢���� 0���� �ʱ�ȭ
					array[i][j] = 0;
				} else { // �������� ���Ѵ�� �ʱ�ȭ
					array[i][j] = -1;
				}
			}
		}

		BufferedReader br = new BufferedReader(new FileReader("C:/Users/Administrator/Desktop/graph_sample_prim_kruskal.txt"));
		String num = br.readLine();
		while (true) {
			String line = br.readLine();
			if (line == null) {
				break;
			} else {
				String[] list = line.split(" ");
				array[Integer.parseInt(list[0])][Integer.parseInt(list[1])] = Integer.parseInt(list[2]);
				array[Integer.parseInt(list[1])][Integer.parseInt(list[0])] = Integer.parseInt(list[2]);
			}
		}

		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				System.out.print(array[i][j] + " ");
			}
			System.out.println();
		}

		main.Prim(array);
		br.close();
	}

}
