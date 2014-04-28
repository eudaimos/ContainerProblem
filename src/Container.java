
public class Container {

		private String name;
		private int capacity;
		private int fill;
		
		Container(){
			this.fill = 0;
		}
		Container(String name, int capacity){
			
			if(capacity <= 0){
				return;
			}
			this.name = name;
			this.capacity = capacity;
			this.fill = 0;
		}
		
		public Container(Container c) {
			this.name = c.name;
			this.capacity = c.capacity;
			this.fill = c.fill;
		}

		public String getContainerName(){
			return name;
		}
		
		public int getCapacity(){
			return this.capacity;
		}
		
		public int getFilled(){
			return this.fill;
		}
		
		public void setFilled(int value){
			this.fill = value;
		}				
}
