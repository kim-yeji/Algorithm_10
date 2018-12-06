package dijkstra;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Dijkstra {
	final static Integer Inf = 10000;
	Edge[] edgeArr = new Edge[5]; //정점 집합 배열
	Vector<Integer> stackV;
	int stack[]; // 시작점부터 끝점까지의 순서 저장
	int[] prev = new int[5]; //도착 전의 정점 저장
	
	public void doDijkstra(int[][] array) {
		int keep = 0;
		int stack[]= new int[5];// 순서 저장
		boolean[] visit = new boolean [5]; //지나간 정점 확인
		int min=0; //최솟값
		
		for(int i=0;i<5;i++) {
			edgeArr[i] = new Edge(i); //정점 생성 후 초기화(distance는 생성자로)
			prev[i]=-1;
		}
		prev[0]=0;
		edgeArr[0].distance=0;//시작 정점값 초기화(시작점 거리는 0)
		visit[0] = true; //지나갔다고 표시
		
		for(int i=0;i<5;i++) {
			
			min=Inf; //무한대로 설정
			int[] adjArr= getAdj(array, edgeArr[i].data);//getAdj에서 넘겨진 인접배열 받기

			for(int j=0;j<5;j++) { //min-heap으로 거리가 가장 짧은 정점을 찾음
				minHeapSort(adjArr); //인접배열을 minHeapSort()로 보내서 가장 작은 값 추출(루트값)
				min=adjArr[0]; //min-heap후에 배열에 담긴것은 오름차순이므로 첫번째가 가장 가중치 값
				if(visit[j]==false && min<edgeArr[j].distance) {
					keep = j;
				}
			}
			visit[keep]=true;//해당 정점 체크
			if(min==10000) break; //연결된 곳이 없으면 종료
			
			relax(keep,array);//최단거리 비교
			
			
		}
		
		//출력!!
		for(int p=0;p<5;p++) {
			inverseFind(0,p);
		}
	}
	
	public void relax(int keep,int[][] array) {
		for(int j=0;j<5;j++) {
			if(edgeArr[j].distance > edgeArr[keep].distance + array[keep][j]) {
				edgeArr[j].distance = edgeArr[keep].distance + array[keep][j]; //최단거리 저장
				prev[j] = keep; //min으로 가려면 keep을 거쳐야함
			}
		}
	}
	
	/**** 최단 경로를 저장 ****/
	public void inverseFind(int s, int e)
	{
		
		int tmp = 0;
		int top = -1;
		tmp = e - 1;
		while (true) {
			System.out.println("tmp+1:"+(tmp+1));
			stack[++top] = tmp + 1;
			if (tmp == s - 1)break; /* 시작점에 이르렀으면 종료 */
			tmp = prev[tmp];
		}
		
		/* 역추적 결과 출력 */
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

	// 인접한 원소들을 찾아서 리스트로 정리한 후 리턴하는 함수
	public int[] getAdj(int[][] array, int u) {
		ArrayList<Integer> adjList = new ArrayList();
		
		for (int i = 0; i < 5; i++) {
			
			if (array[u][i]>0 && array[u][i]<10000) { // u와 인접했으면
				if(u==0) { //( 0이라면 시작점이 0 이므로 가중치 설정) 
					edgeArr[i].distance=array[u][i];
				}
				adjList.add(array[u][i]);// 가중치 값을 인접리스트에 추가
			}
		}
		
		int[] adjArr = new int[adjList.size()]; //리스트만큼의 배열 생성
		
		for(int j=0;j<adjList.size();j++) {
			adjArr[j]=adjList.get(j); //리스트에 있던 값을 배열로 옮김
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

		// 비교해서 더 작은 Child를 min에 할당한다.
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
		
		
		//정점 담을 배열 초기화
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if(i==j) { //대각선은 0으로 초기화
					array[i][j]=0;
				}else { //나머지는 무한대로 초기화
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
