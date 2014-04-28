import java.util.LinkedList;
import java.util.Queue;


public class ContainerProblem {

	static int steps = -1;
	
	//UTILITY FUNC: Computes gcd of container values.
	static int gcd(int a,int b){
	    if(b==0)
	        return a;
	    return gcd(b,a%b);
	}
	
	//UTILITY FUNC: Computes possiblity of solving w/ given values.
	/*static int checkPossibility(int a, int b, int target){
		
		if(a < 1 || b < 1)
			return -1;
		
		if(target > a && target > b)
			return -1;
		
		return (target % gcd(a,b));
	}
	*/
	static int checkPossibility(Container A, Container B, int target){
		
		if(A.getCapacity() < 1 || B.getCapacity() < 1)
			return -1;
		
		if(target > A.getCapacity() && target > B.getCapacity())
			return -1;
		
		return (target % gcd(A.getCapacity(),B.getCapacity()));
	}
	
	//UTILTY FUNCS: Overloaded funcs for printing to terminal.
	static void printLog(String columnOne, String columnTwo, String columnThree){
		System.out.println(columnOne+"        "+columnTwo+"     "+columnThree);
	}
	
	static void printLog(int steps, Container A, Container B){
		System.out.println(steps+"            "+A.getFilled()+"        "+B.getFilled());
	}
	
	
	/*
	 * A more efficient greedy technique algorithm to compute steps, rather than
	 * generating all values through BFS
	 */
	public static void computeSteps(int containerA, int containerB, int target){
		
		
		Container A = new Container("A", Math.max(containerA, containerB));
		Container B = new Container("B", Math.min(containerA, containerB));
		
		if(checkPossibility(A, B, target) < 0){
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
		steps = -1;

	}
	
	
	//HELPER FUNC: Utility for BFS to find next possible states.
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

	/*
	 * Breadth-First Search Algorithm: A way to generate all possible states the system can take.
	 * It stops when the first soln. is found.
	 * 
	 * Since it is BFS, it will automatically find the best possible soln, anyway.
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
				//System.out.println("BFS :"+child.A.getFilled() +"    "+child.B.getFilled());
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
	
	
	public static void main(String[] args){				
		
		computeSteps(3, 5, 4);
		
		System.out.println();
		
		computeSteps(5, 33, 20);
		
		Container A = new Container("A", 3);
		Container B = new Container("B", 5);
				
		//bfsSearch(new State(A, B), 4);
}

}
