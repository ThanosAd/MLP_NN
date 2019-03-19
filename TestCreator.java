import java.util.*;  

public class TestCreator {
	
	Random rand = new Random();
	ArrayList<Double[]> C1 = new ArrayList<Double[]>();
	ArrayList<Double[]> C2 = new ArrayList<Double[]>();
	ArrayList<Double[]> C3 = new ArrayList<Double[]>();
	
	ArrayList<Double[][]> TrainingSet=new ArrayList<Double[][]>();
	ArrayList<Double[][]> CheckingSet = new ArrayList<Double[][]>();
	
	ArrayList<Double> tx1 = new ArrayList<Double>();
	ArrayList<Double> tx2 = new ArrayList<Double>();
	ArrayList<Double[]> tar = new ArrayList<Double[]>();
	
	ArrayList<Double> cx1 = new ArrayList<Double>();
	ArrayList<Double> cx2 = new ArrayList<Double>();
	ArrayList<Double[]> ctar = new ArrayList<Double[]>();

	public TestCreator() {
		int i=0;
		int trainingCounter=0;
		Double[][] point_target= {{0.0,0.0},{0.0,0.0,0.0}};
		Double[] point= {0.0,0.0};
		Double[] point2= {0.0,0.0};
		Double[] target1= {1.0,0.0,0.0};
		Double[] target2= {0.0,1.0,0.0};
		Double[] target3= {0.0,0.0,1.0};
		while(i<6000) {
			double x1 = (Math.random()*((2-0)));
			double x2 = (Math.random()*((2-0)));
			point[0] = x1;
			point[1] = x2;
			
			System.out.println(String.valueOf(i)+" "+String.valueOf(trainingCounter));
			System.out.println("x1 : "+String.valueOf(x1)+" | x2 : "+String.valueOf(x2)+" "+String.valueOf(trainingCounter));
			
			if( (Math.pow((x1-1), 2) + Math.pow((x2-1), 2)<= 0.16) | (Math.pow((x1+1), 2) + Math.pow((x2+1), 2)<= 0.16)) {
				if(Arrays.asList(C1).contains(point)== false) {
					C1.add(point);
					i++;
					point_target[0] = point;
					point_target[1] = target1;
					if(trainingCounter<3000) {
						TrainingSet.add(point_target);
						tx1.add(x1);
						tx2.add(x2);
						tar.add(target1);
						System.out.println(String.valueOf(TrainingSet.get(trainingCounter)[0][0])+"=================");
						trainingCounter++;
						
					}else {
						CheckingSet.add(point_target);
						System.out.println(String.valueOf(CheckingSet.get(i-3000-1)[0][0])+"=================");
						cx1.add(x1);
						cx2.add(x2);
						ctar.add(target1);
					}
				}
				
			}else if(((Math.pow((x1-1), 2) + Math.pow((x2-1), 2)> 0.16) && (Math.pow((x1-1), 2) + Math.pow((x2-1), 2)<= 0.64)) | 
					((Math.pow((x1+1), 2) + Math.pow((x2+1), 2)> 0.16) && (Math.pow((x1+1), 2) + Math.pow((x2+1), 2)<= 0.64))) {
				if(Arrays.asList(C2).contains(point)== false) {
					C2.add(point);
					i++;
					point_target[0] = point;
					point_target[1] = target2;
					if(trainingCounter<3000) {
						tx1.add(x1);
						tx2.add(x2);
						tar.add(target2);
						TrainingSet.add(point_target);
						System.out.println(String.valueOf(TrainingSet.get(trainingCounter)[0][0])+"=================");
						trainingCounter++;
					}else {
						CheckingSet.add(point_target);
						System.out.println(String.valueOf(CheckingSet.get(i-3000-1)[0][0])+"=================");
						cx1.add(x1);
						cx2.add(x2);
						ctar.add(target2);
					}
				}
				
			}else {
				if(Arrays.asList(C3).contains(point)== false) {
					C3.add(point);
					i++;
					point_target[0] = point;
					point_target[1] = target3;
					if(trainingCounter<3000) {
						tx1.add(x1);
						tx2.add(x2);
						tar.add(target3);
						TrainingSet.add(point_target);
						System.out.println(String.valueOf(TrainingSet.get(trainingCounter)[0][0])+"=================");
						trainingCounter++;
					}else {
						CheckingSet.add(point_target);
						System.out.println(String.valueOf(CheckingSet.get(i-3000-1)[0][0])+"=================");
						cx1.add(x1);
						cx2.add(x2);
						ctar.add(target3);
					}
				}
			}
			
			double x3 = (Math.random()*((-2-0)));
			double x4 = (Math.random()*((-2-0)));
			point2[0] = x3;
			point2[1] = x4;
			System.out.println("x3 : "+String.valueOf(x3)+" | x4 : "+String.valueOf(x4)+" "+String.valueOf(trainingCounter));
			
			if( (Math.pow((x3-1), 2) + Math.pow((x4-1), 2)<= 0.16) | (Math.pow((x3+1), 2) + Math.pow((x4+1), 2)<= 0.16)) {
				if(Arrays.asList(C1).contains(point2)== false) {
					C1.add(point2);
					i++;
					point_target[0] = point2;
					point_target[1] = target1;
					if(trainingCounter<3000) {
						TrainingSet.add(point_target);
						tx1.add(x3);
						tx2.add(x4);
						tar.add(target1);
						System.out.println(String.valueOf(TrainingSet.get(trainingCounter)[0][0])+"=================");
						trainingCounter++;
					}else {
						CheckingSet.add(point_target);
						System.out.println(String.valueOf(CheckingSet.get(i-3000-1)[0][0])+"=================");
						cx1.add(x3);
						cx2.add(x4);
						ctar.add(target1);
					}
				}
				
			
			}else if(((Math.pow((x3-1), 2) + Math.pow((x4-1), 2)> 0.16) && (Math.pow((x3-1), 2) + Math.pow((x4-1), 2)<= 0.64)) | 
					((Math.pow((x3+1), 2) + Math.pow((x4+1), 2)> 0.16) && (Math.pow((x3+1), 2) + Math.pow((x4+1), 2)<= 0.64))) {
				if(Arrays.asList(C2).contains(point2)== false) {
					C2.add(point2);
					i++;
					point_target[0] = point2;
					point_target[1] = target2;
					if(trainingCounter<3000) {
						TrainingSet.add(point_target);
						tx1.add(x3);
						tx2.add(x4);
						tar.add(target2);
						System.out.println(String.valueOf(TrainingSet.get(trainingCounter)[0][0])+"=================");
						trainingCounter++;
						
					}else {
						CheckingSet.add(point_target);
						System.out.println(String.valueOf(CheckingSet.get(i-3000-1)[0][0])+"=================");
						cx1.add(x3);
						cx2.add(x4);
						ctar.add(target2);
					}
				}
				
				
			}else {
				if(Arrays.asList(C3).contains(point2)== false) {
					C3.add(point2);
					i++;
					point_target[0] = point2;
					point_target[1] = target3;
					if(trainingCounter<3000) {
						TrainingSet.add(point_target);
						tx1.add(x3);
						tx2.add(x4);
						tar.add(target3);
						System.out.println(String.valueOf(TrainingSet.get(trainingCounter)[0][0])+"=================");
						trainingCounter++;
					}else {
						CheckingSet.add(point_target);
						System.out.println(String.valueOf(CheckingSet.get(i-3000-1)[0][0])+"=================");
						cx1.add(x3);
						cx2.add(x4);
						ctar.add(target3);
						
					}
				}
			}
		}
	}
	
	public ArrayList<Double[]> getC1() {
		return C1;
	}
	
	public ArrayList<Double[]> getC2() {
		return C2;
	}
	
	public ArrayList<Double[]> getC3() {
		return C3;
	}
	
	public ArrayList<Double[][]> getTrainingSet() {
		return TrainingSet;
	}
	
	public ArrayList<Double[][]> getCheckingSet() {
		return CheckingSet;
	}
	
	public ArrayList<Double> getCx1(){
		return cx1;
	}
	
	public ArrayList<Double> getCx2(){
		return cx2;
	}
	
	public ArrayList<Double[]> getCTar(){
		return ctar;
	}
	
	public ArrayList<Double> getTx1(){
		return tx1;
	}
	
	public ArrayList<Double> getTx2(){
		return tx2;
	}
	
	public ArrayList<Double[]> getTar(){
		return tar;
	}
}
