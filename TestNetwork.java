import java.util.ArrayList;

public class TestNetwork {

	public static void main(String[] args) {
		
		TestCreator test = new TestCreator();
		
		
		ArrayList<Double> tx1=test.getTx1();
		ArrayList<Double> tx2=test.getTx2();
		ArrayList<Double[]> tar=test.getTar();
        
		ArrayList<Double> cx1=test.getCx1();
		ArrayList<Double> cx2=test.getCx2();
		ArrayList<Double[]> ctar=test.getCTar();
		
		double x1 = tx1.get(0);
		double x2 = tx2.get(0);
		double[] target = {tar.get(0)[0],tar.get(0)[1],tar.get(0)[2]};
		
		double x1c = cx1.get(0);
		double x2c = cx2.get(0);
		double[] ctarget = {ctar.get(0)[0],ctar.get(0)[1],ctar.get(0)[2]};
		
		NeuralNetwork NN = new NeuralNetwork(15,10,2,0.1,1,x1,x2,3,0.05,target);
		
		double sumOfSError = 0 ;
		int epochCounter = 0 ;
		double newE = 0 ;
		double old = 1 ;
		int miniBatchCounter = 0 ;
		int L = 1 ;

		while(epochCounter<500||(Math.abs(old-newE)>0.0002 )) {
			old=newE;
			for(int i=0;i<tx1.size();i++) {
				
				x1 = tx1.get(i);
				x2 = tx2.get(i);
				target[0] = tar.get(i)[0];
				target[1] = tar.get(i)[1];
				target[2] = tar.get(i)[2];
				
				NN.updateInput(x1, x2);
				NN.updateTarget(target);
				
				NN.forward_pass();
				NN.backprop();
				
				
				sumOfSError=sumOfSError+NN.squaredError();
				miniBatchCounter++;
				
				if( miniBatchCounter == L ) {
					NN.updateWeights();
					NN.initializePartialSum();
					miniBatchCounter=0;
				}
			}
			
			epochCounter++;
			System.out.println(sumOfSError+"--------------------------------"+epochCounter);
			newE=sumOfSError;
			sumOfSError=0;
			
		}
		
		NN.forward_pass();
		System.out.println(NN.getOutput());
		int correctCounter=0;
		for(int i =0; i<cx1.size();i++) {
			x1c = cx1.get(i);
			x2c = cx2.get(i);
			ctarget[0] = ctar.get(i)[0];
			ctarget[1] = ctar.get(i)[1];
			ctarget[2] = ctar.get(i)[2];
			
			NN.updateInput(x1c, x2c);
			NN.updateTarget(ctarget);
			
			NN.forward_pass();
			
			System.out.println(NN.getOutput());
			System.out.println(ctarget[0]+" "+ctarget[1]+" "+ctarget[2]);
			System.out.println("----------------");
			
			double max=0;
			int index=0;
			int index2=0;
			for(int y=0;y<NN.getOutput().size();y++) {
				if(NN.getOutput().get(y)>max) {
					max = NN.getOutput().get(y);
					index=y;
				}
				if(ctarget[y]==1) {
					index2=y;
				}
			}
			if(index==index2) {
				correctCounter++;
				System.out.println("++\n");
			}else {
				System.out.println("--\n");
			}
		}
		System.out.println(correctCounter);
		
	}
}
