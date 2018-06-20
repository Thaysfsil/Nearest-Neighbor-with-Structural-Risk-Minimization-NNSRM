import java.util.ArrayList;
import java.util.Collections;


public class Main {
	


	public static void main(String[] args) {
		Arquivo file = new Arquivo(Òtrainset.txt");
		Arquivo fileTest = new Arquivo(ÒtrainTest.txt");
		double x,y,distance;
		int type,error = 0;
		//int j=0;
		int k=0;
		double rEmp=100.0;
		VectorObject solution = null;
		VectorObject objTest;
		ArrayList<VectorObject> vector = new ArrayList<VectorObject>();
		ArrayList<VectorObject> vectorTest = new ArrayList<VectorObject>();
		//MatrixObject[][] matrix;
		VectorPartner partner;
		ArrayList<VectorPartner> partnerList = new ArrayList<VectorPartner>();
		ArrayList<VectorPartner> partnerListTest = new ArrayList<VectorPartner>();
		ArrayList<VectorObject> result = new ArrayList<VectorObject>();
		ArrayList<VectorPartner> sortList = new ArrayList<VectorPartner>();
		
		// read file save as a object 
		while(!file.isEndOfFile()){
			do{
				x = file.readDouble();
				y = file.readDouble();
				type = file.readInt();
				vector.add(new VectorObject(x,y,type));
			}while(!file.isEndOfLine());
		}
		file.close();
		
		
		//the same to testiest
		while(!fileTest.isEndOfFile()){
			do{
				x = fileTest.readDouble();
				y = fileTest.readDouble();
				type = fileTest.readInt();
				vectorTest.add(new VectorObject(x,y,type));
			}while(!fileTest.isEndOfLine());
		}
		fileTest.close();
		
		
		// Create a list of Cria partner, saving partner objects only and its distance
		for (int i = 0; i < vector.size(); i++) {
			for (int l = 0; l < vector.size()-1; l++) {
				if(i != l){
					VectorObject a,b;
					a = vector.get(i);
					b = vector.get(l);
					partner= new VectorPartner(a,b,distance(a,b));
					partnerList.add(partner);			
					//partner.getResult();
				}
			}
		}
		
		
		for (int i = 0; i < vectorTest.size(); i++) {
			for (int l = 0; l < vectorTest.size()-1; l++) {
				if(i != l){
					VectorObject a,b;
					a = vectorTest.get(i);
					b = vectorTest.get(l);
					partner= new VectorPartner(a,b,distance(a,b));
					partnerListTest.add(partner);			
					//partner.getResult();
				}
			}
		}
		
		
		// sort list according with distance;
		sortList=partnerList;
		Collections.sort(sortList);
		/*
		for(int i = 0; i < sortList.size(); i++){
			System.out.println(sortList.get(i).distance+"");
			}
		*/// save in s={}, the first value of sorted list
		
		
		ArrayList<Double> distanceResult=new ArrayList<Double>();
		double keyDistance;
		while (rEmp>0){
			solution =sortList.get(k).vector1;
			objTest=sortList.get(k).vector1;
			distance=sortList.get(k).distance;
			keyDistance=sortList.get(k).distance;
			distanceResult.add(distance);
			
			int key=0;
		for (int i = 0; i < sortList.size(); i++){
			
			VectorObject vector1 = sortList.get(i).vector1;
			VectorObject vector2 = sortList.get(i).vector2;
			double partnerDistance=sortList.get(i).distance;
			if(objTest.equals(vector1)){
				if (distance>partnerDistance){
					solution=vector2;
					keyDistance=partnerDistance;
					key++;				}
				}
			else if(objTest.equals(vector2)){
				if (distance>partnerDistance){
					solution=vector1;
					keyDistance=partnerDistance;
					key++;
				}
			} 
			}
			if(!result.contains(solution)){
				result.add(solution);
				distanceResult.add(keyDistance);
			}
			rEmp = 100-((100*key)/vector.size());
			/*System.out.print("\n"+ rEmp);	
			System.out.print("\n x= "+ solution.x +"\n");
			System.out.print("y= "+ solution.y +"\n");
			System.out.print("type= "+ solution.type +"\n");
			System.out.print("___---___---___---___---___---___");*/
			k++;
			}
		
		
		//check hit rate
		
		for (int l = 0; l < result.size(); l++) {
			objTest=result.get(l);
			distance=distanceResult.get(l);
			for (int i = 0; i < partnerListTest.size(); i++){
				VectorObject vector1 = partnerListTest.get(i).vector1;
				VectorObject vector2 = partnerListTest.get(i).vector2;
				double partnerDistance=partnerListTest.get(i).distance;
				if(objTest.equals(vector1)){
				if (distance>partnerDistance){
					solution=vector2;
					error++;
				}
				}
				else if(objTest.equals(vector2)){
					if (distance>partnerDistance){
						solution=vector1;
						error++;
				}
			} 
			}
			if(!result.contains(solution)){
				double errorRate = vectorTest.size()/(error*100); 
				System.out.print(" _____Erro___\n");
				System.out.print("x= "+ solution.x+"\n");
				System.out.print("y= " +solution.y+"\n");
				System.out.print("Tipo= "+ solution.type+"\n");
				System.out.print("Taxa de erro: "+errorRate+"\n");
				System.out.print("---___---___---___---___---___---___---___---___\n");
				
			}
			else{
				
				System.out.print(" _____Acerto___\n");
				System.out.print("x= " +objTest.x+"\n");
				System.out.print("y= " +objTest.y+"\n");
				System.out.print("Tipo= " +objTest.type+"\n");
				System.out.print("---___---___---___---___---___---___---___---___\n");
			}
		}
		
				
				}
		
	
		

	public static double distance(VectorObject a, VectorObject b){
		return (double) Math.sqrt(Math.pow((a.x - b.x), 2) + Math.pow((a.y - b.y), 2));
	}

		
}
