
public class VectorPartner implements Comparable<VectorPartner>{
	VectorObject vector1;
	VectorObject vector2;
	double distance;
	
	public VectorPartner (VectorObject vector1, VectorObject vector2, double distance) {
		this.vector1 = vector1;
		this.vector2 = vector2;
		this.distance = distance;
		
	}
	
	public void getResult(){
		
		String x = "Vector 1 x= "+ this.vector1.x +"   y = " + this.vector1.y+ "   type=  "+ this.vector1.type;
		System.out.print(x+"\n");
		
		String y = " Vector 2  x= "+ this.vector2.x + "   y = " + this.vector2.y + "   type=  "+ this.vector2.type;
		System.out.print(y+"\n");
		
		String z=" distance=" + this.distance;
		System.out.print(z+"\n");
		System.out.print(" --___--"+"\n");
							
	}
	
	public int compareTo(VectorPartner partner) {
		        if(this.distance> partner.distance){	
		            return 1;	
		        }
	
		        else{	
		            return -1;	
		        }
		    }

	
	
}


