
public class State {

	static int step = -1;
	public Container A;
	public Container B;
	public State parent;
	
	State(Container A, Container B){
		this.A = new Container(A);
		this.B = new Container(B);
		this.parent = null;
	}
	State(State state){
		this.A = new Container(state.A);
		this.B = new Container(state.B);
		this.parent = null;
	}
}
