import java.util.LinkedList;
import java.util.Queue;


public class ContainerProblem {

	static int steps = -1;
	
	static int gcd(int a,int b){
	    if(b==0)
	        return a;
	    return gcd(b,a%b);
	}
	
	static int checkPossibility(int a, int b, int target){
		
		if(a < 1 || b < 1)
			return -1;
		
		if(target > a && target > b)
			return -1;
		
		return (target % gcd(a,b));
	}
	
	static int checkPossibility(Container A, Container B, int target){
		
		if(target > A.getCapacity() || target > B.getCapacity())
			return -1;
		
		return (target % gcd(A.getCapacity(),B.getCapacity()));
	}
	
	static void printLog(String columnOne, String columnTwo, String columnThree){
		System.out.println(columnOne+"        "+columnTwo+"     "+columnThree);
	}
	
	static void printLog(int steps, Container A, Container B){
		System.out.println(steps+"            "+A.getFilled()+"        "+B.getFilled());
	}
	
/*public static void computeSteps(int containerA, int containerB, int target){
		
		int steps = -1;
		//Buckets.
		int capA = Math.max(containerA, containerB);
		int capB = Math.min(containerA, containerB);
		int A = 0;
		int B = 0;
		
		if(checkPossibility(containerA, containerB, target) > 0){
			System.out.println("Soln. not possible");
			return;
		}
		
		System.out.println("Step     A     B");
		System.out.println("----     -     -");
		System.out.println(++steps+"        "+A+"     "+B);
		
		if(target == containerA){		
			A = containerA;
			System.out.println(++steps+"        "+A+"     "+B);
			return;
		}
		if(target == containerB){	
			B = containerB;
			System.out.println(++steps+"        "+A+"     "+B);
			return;
		}
		
		while(A != target && B != target && steps <= 100000){
			
			if(A == 0){
				A = capA;
				System.out.println(++steps+"        "+A+"     "+B);
			}else if(B == capB){
				
				B = 0;
				System.out.println(++steps+"        "+A+"     "+B);				
			}else{
				int leftover = Math.min(capB - B, A);
				B = B + leftover;
				A = A - leftover;
				System.out.println(++steps+"        "+A+"     "+B);
			}
		}
		

	}
*/
	
	public static void computeSteps(int containerA, int containerB, int target){
		
		
		Container A = new Container("A", Math.max(containerA, containerB));
		Container B = new Container("B", Math.min(containerA, containerB));
		
		if(checkPossibility(containerA, containerB, target) < 0){
			System.out.println("Soln. not possible");
			return;
		}
		
		printLog("Step", A.getContainerName()+"("+A.getCapacity()+")", B.getContainerName()+"("+B.getCapacity()+")");
		printLog("----","---"," ---");
		printLog(++steps, A, B);
		
		if(target == A.getCapacity()){
			A.setFilled(A.getCapacity());
			printLog(++steps, A, B);
			return;
		}
		if(target == B.getCapacity()){
			B.setFilled(B.getCapacity());
			printLog(++steps, A, B);
			return;
		}
		
		while(A.getFilled() != target && B.getFilled() != target && steps <= 100000){
			
			if(A.getFilled() == 0){
				A.setFilled(A.getCapacity());
				printLog(++steps, A, B);
			}else if(B.getFilled() == B.getCapacity()){
				
				B.setFilled(0);
				printLog(++steps, A, B);				
			}else{
				int leftover = Math.min(B.getCapacity() - B.getFilled(), A.getFilled());
				B.setFilled(B.getFilled() + leftover);
				A.setFilled(A.getFilled() - leftover);
				printLog(++steps, A, B);
			}
		}

	}
	
	public static void main(String[] args){				
			
			computeSteps(3, 5, 4);
			
			Container A = new Container("A", 3);
			Container B = new Container("B", 5);
			
			LinkedList<State> c = getPossibleMoves(new State(A, B));
			System.out.println(c.size());
			System.out.println(c.remove().A.getFilled());
			bfsSearch(new State(A, B), 4);
	}
	

	public static LinkedList<State> getPossibleMoves(State state){
		
		LinkedList<State> children = new LinkedList();
		
		if(state.A.getFilled() == 0){
			State child = new State(state);
			child.A.setFilled(child.A.getCapacity());
			child.parent = state;
			children.add(child);
		}
		
		if(state.B.getFilled() == 0){
			State child = new State(state);
			child.B.setFilled(child.B.getCapacity());
			child.parent = state;
			children.add(child);
		}
		
		if(state.A.getFilled() == state.A.getCapacity()){
			State child = new State(state);
			child.A.setFilled(0);
			child.parent = state;
			children.add(child);
		}
		
		if(state.A.getFilled() == state.A.getCapacity()){
			State child = new State(state);
			child.B.setFilled(0);
			child.parent = state;
			children.add(child);
		}
		
		if(state.A.getFilled() > 0){
			int leftover = Math.min(state.B.getCapacity() - state.B.getFilled(), state.A.getFilled());
			State child = new State(state);
			child.B.setFilled(state.B.getFilled() + leftover);
			child.A.setFilled(state.A.getFilled() - leftover);
			child.parent = state;
			children.add(child);
		}
		
		if(state.B.getFilled() > 0){
			int leftover = Math.min(state.A.getCapacity() - state.A.getFilled(), state.B.getFilled());
			State child = new State(state);
			child.A.setFilled(state.A.getFilled() + leftover);
			child.B.setFilled(state.B.getFilled() - leftover);
			child.parent = state;
			children.add(child);
		}
		
		return children;
		
		 
	}
	
	/*public void bfs(String startNode){
	
	Queue<Node> queue = new LinkedList();
	
	//Map<String, Boolean> visited = new HashMap<String, Boolean>();
	Node[] previous = new Node[tieList.size()+1];
	Node root = getNodeValue(startNode);
	root.depth = 0;
	queue.add(root);
	previous[root.priority] = null;
	
	tieList.get(root.priority).visited = true;   
	tieList.get(root.priority).cost = 0;
	//System.out.println(root.name+","+ root.depth +","+root.cost);
	logWrite.add(new Log(root.name, root.depth, root.cost));
	while(!queue.isEmpty()) {
		Node node = (Node)queue.remove();
		Node child=null;
		
		while((child=getUnvisitedChildrenNode(node))!=null) {
			tieList.get(child.priority).visited = true;   
			tieList.get(child.priority).cost = node.cost + getCost(node, child);
			child.depth = node.depth + 1;
			//System.out.println(child.name+","+ child.depth +","+child.cost);
			logWrite.add(new Log(child.name, child.depth, child.cost));
			previous[child.priority] = node;
			queue.add(child);
		
		}
	}
	printPath(previous, getNodeValue(goalNode).priority);
	generateOutputFile(generatedOutputPath);
	
	// Reset visited = false.
	//clearNodes();
}
*/
	
	public static void bfsSearch(State state, int target){
		
		Queue<State> queue = new LinkedList();
		
		queue.add(state);
		boolean flag = false;
		State child = null;
		
		while(!queue.isEmpty()){
			state.step += 1;
			State node = queue.remove();
			
			LinkedList<State> children = getPossibleMoves(node);
			
			while(!children.isEmpty()){
				child = children.remove();
				System.out.println("BFS :"+child.A.getFilled() +"    "+child.B.getFilled());
				if(child.A.getFilled() == target || child.B.getFilled() == target){
					flag = true;
					break;
				}
			queue.add(child);	
			}
			if(flag)
				break;			
		}
		System.out.println(state.step);
	}
}
