package dijkstra;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Dijkstra {
	final static Integer Inf = 10000;
	Edge[] edgeArr = new Edge[5]; //���� ���� �迭
	Vector<Integer> stackV;
	int stack[]; // ���������� ���������� ���� ����
	int[] prev = new int[5]; //���� ���� ���� ����
	
	public void doDijkstra(int[][] array) {
		int keep = 0;
		int stack[]= new int[5];// ���� ����
		boolean[] visit = new boolean [5]; //������ ���� Ȯ��
		int min=0; //�ּڰ�
		
		for(int i=0;i<5;i++) {
			edgeArr[i] = new Edge(i); //���� ���� �� �ʱ�ȭ(distance�� �����ڷ�)
			prev[i]=-1;
		}
		prev[0]=0;
		edgeArr[0].distance=0;//���� ������ �ʱ�ȭ(������ �Ÿ��� 0)
		visit[0] = true; //�������ٰ� ǥ��
		
		for(int i=0;i<5;i++) {
			
			min=Inf; //���Ѵ�� ����
			int[] adjArr= getAdj(array, edgeArr[i].data);//getAdj���� �Ѱ��� �����迭 �ޱ�

			for(int j=0;j<5;j++) { //min-heap���� �Ÿ��� ���� ª�� ������ ã��
				minHeapSort(adjArr); //�����迭�� minHeapSort()�� ������ ���� ���� �� ����(��Ʈ��)
				min=adjArr[0]; //min-heap�Ŀ� �迭�� ������ ���������̹Ƿ� ù��°�� ���� ����ġ ��
				if(visit[j]==false && min<edgeArr[j].distance) {
					keep = j;
				}
			}
			visit[keep]=true;//�ش� ���� üũ
			if(min==10000) break; //����� ���� ������ ����
			
			relax(keep,array);//�ִܰŸ� ��
			
			
		}
		
		//���!!
		for(int p=0;p<5;p++) {
			inverseFind(0,p);
		}
	}
	
	public void relax(int keep,int[][] array) {
		for(int j=0;j<5;j++) {
			if(edgeArr[j].distance > edgeArr[keep].distance + array[keep][j]) {
				edgeArr[j].distance = edgeArr[keep].distance + array[keep][j]; //�ִܰŸ� ����
				prev[j] = keep; //min���� ������ keep�� ���ľ���
			}
		}
	}
	
	/**** �ִ� ��θ� ���� ****/
	public void inverseFind(int s, int e)
	{
		
		int tmp = 0;
		int top = -1;
		tmp = e - 1;
		while (true) {
			System.out.println("tmp+1:"+(tmp+1));
			stack[++top] = tmp + 1;
			if (tmp == s - 1)break; /* �������� �̸������� ���� */
			tmp = prev[tmp];
		}
		
		/* ������ ��� ��� */
		stackV.removeAllElements();
		for (int i = top; i > -1; i--) {
			System.out.printf("%d", stack[i]);
			stackV.add(stack[i]);
			if (i != 0)System.out.printf(" -> ");
		}
		System.out.print(edgeArr[e].distance);
		System.out.printf("\n");
	}
	
	public Vector<Integer> getStack()
	{
		return stackV;
	}

	// ������ ���ҵ��� ã�Ƽ� ����Ʈ�� ������ �� �����ϴ� �Լ�
	public int[] getAdj(int[][] array, int u) {
		ArrayList<Integer> adjList = new ArrayList();
		
		for (int i = 0; i < 5; i++) {
			
			if (array[u][i]>0 && array[u][i]<10000) { // u�� ����������
				if(u==0) { //( 0�̶�� �������� 0 �̹Ƿ� ����ġ ����) 
					edgeArr[i].distance=array[u][i];
				}
				adjList.add(array[u][i]);// ����ġ ���� ��������Ʈ�� �߰�
			}
		}
		
		int[] adjArr = new int[adjList.size()]; //����Ʈ��ŭ�� �迭 ����
		
		for(int j=0;j<adjList.size();j++) {
			adjArr[j]=adjList.get(j); //����Ʈ�� �ִ� ���� �迭�� �ű�
		}
		
		return adjArr;
	}
	
	public void minHeapSort(int[] arr) {
		int size = arr.length - 1;
		buildMinHeap(arr);
//		for (int i = size; i > 0; i--) {
//			swap(arr, 0, i);
//			size -= 1;
//			min_Heapify(arr, 0, size);
//		}
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
		Dijkstra dijk = new Dijkstra();
		List<Integer> fileList = new ArrayList<Integer>();
		int[][] array = new int[5][5];
		
		
		//���� ���� �迭 �ʱ�ȭ
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if(i==j) { //�밢���� 0���� �ʱ�ȭ
					array[i][j]=0;
				}else { //�������� ���Ѵ�� �ʱ�ȭ
					array[i][j]=Inf; 
				}
			}
		}
		
		BufferedReader br = new BufferedReader(new FileReader("C:/Users/Administrator/Desktop/graph_sample_dijkstra.txt"));
		String num = br.readLine();
		for (int i = 0; i < 10; i++) {
			String line = br.readLine();
			if (line == null) {
				break;
			} else {
				String[] list = line.split(" ");
				array[Integer.parseInt(list[0])][Integer.parseInt(list[1])]=Integer.parseInt(list[2]);
			
			}
		}

//		for (int i = 0; i < 5; i++) {
//			for(int j=0;j<5;j++) {
//				System.out.print(array[i][j]+" ");
//			}
//			System.out.println();
//		}
		
		dijk.doDijkstra(array);
		br.close();
	}

}
