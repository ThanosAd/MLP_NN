import java.util.*;

public class NeuralNetwork {
	
	int FirstLayerNeurons  =  0;
	int SecondLayerNeurons =  0;
	int NumberOfInputs = 0;
	double bias = 0;
	int activationFunction = 0;
	int NumberOfCategories=0;
	double n=0;
	double[] target= {};
	
	ArrayList<Double> FirstInput = new ArrayList<Double>();
	
	ArrayList<Double> FirstLayerOutputs  = new ArrayList<Double>();
	ArrayList<Double> SecondLayerOutputs = new ArrayList<Double>();
	ArrayList<Double> FinalLayerOutputs = new ArrayList<Double>();
	
	ArrayList<Double> FirstLayerWeights  = new ArrayList<Double>();
	ArrayList<Double> SecondLayerWeights = new ArrayList<Double>();
	ArrayList<Double> FinalLayerWeights = new ArrayList<Double>();
	
	ArrayList<Double> FirstLayerDelta = new ArrayList<Double>();
	ArrayList<Double> SecondLayerDelta = new ArrayList<Double>();
	ArrayList<Double> FinalLayerDelta = new ArrayList<Double>();
	
	ArrayList<Double> FirstLayerWeightsPartialSum = new ArrayList<Double>();
	ArrayList<Double> SecondLayerWeightsPartialSum = new ArrayList<Double>();
	ArrayList<Double> FinalLayerWeightsPartialSum = new ArrayList<Double>();

	public NeuralNetwork(int numberOfNeuronsInTheFirstLayer, int numberOfNeuronsInTheSecondLayer,int numberOfInputs,double aBias,int aF, double x1, double x2,int NoC,double aN,double[] atarget) {
		
		FirstLayerNeurons = numberOfNeuronsInTheFirstLayer;
		SecondLayerNeurons = numberOfNeuronsInTheSecondLayer;
		NumberOfInputs = numberOfInputs ;
		bias= aBias;
		activationFunction=aF;
		NumberOfCategories = NoC;
		n=aN;
		target = atarget;
		
		//Initialize Arrays
		
		for(int x=0;x<NumberOfInputs;x++) {
			FirstInput.add(x1);
			FirstInput.add(x2);
		}
		
		for(int t=0;t<FirstLayerNeurons;t++) {
			FirstLayerOutputs.add(0.0);
			FirstLayerDelta.add(0.0);
		}
		
		for(int q=0;q<SecondLayerNeurons;q++) {
			SecondLayerOutputs.add(0.0);
			SecondLayerDelta.add(0.0);
		}
		
		for(int w=0;w<NumberOfCategories;w++) {
			FinalLayerOutputs.add(0.0);
			FinalLayerDelta.add(0.0);
		}
		
		for(int y=0;y<(FirstLayerNeurons*NumberOfInputs);y++) {
			double r = (Math.random()*((2)))-1;
			FirstLayerWeights.add(r);
			FirstLayerWeightsPartialSum.add(0.0);
		}
		
		for(int i=0;i<(SecondLayerNeurons*FirstLayerNeurons);i++) {
			double r = (Math.random()*((2)))-1;
			SecondLayerWeights.add(r);
			SecondLayerWeightsPartialSum.add(0.0);
		}
		
		for(int j=0;j<(NumberOfCategories*SecondLayerNeurons);j++) {
			double r = (Math.random()*((2)))-1;
			FinalLayerWeights.add(r);
			FinalLayerWeightsPartialSum.add(0.0);
		}
		
	}
	
	public void forward_pass() {
		int weightCounter=0;
		
		// First Layer Forward Pass Calculation
		
		for(int i=0;i<FirstLayerNeurons;i++) {
			double sum=0;
			for(int synapse=0;synapse<NumberOfInputs;synapse++) {
				sum=sum+FirstInput.get(synapse)*FirstLayerWeights.get(weightCounter);
				weightCounter = weightCounter+1;
			}
			sum = sum + bias;
			if(activationFunction ==1) {
				double finalResult= this.logistic(sum);
				FirstLayerOutputs.set(i,finalResult);
			}else {
				double finalResult= this.tanH(sum);
				FirstLayerOutputs.set(i,finalResult);
			}
		}
		
		//Second Layer Forward Pass Calculation
		
		weightCounter=0;
		for(int j=0;j<SecondLayerNeurons;j++) {
			double sum=0;
			for(int synapse=0;synapse<FirstLayerNeurons;synapse++) {
				sum=sum+FirstLayerOutputs.get(synapse)*SecondLayerWeights.get(weightCounter);
				weightCounter = weightCounter+1;
			}
			sum = sum + bias;
			if(activationFunction ==1) {
				double finalResult= this.logistic(sum);
				SecondLayerOutputs.set(j,finalResult);
			}else {
				double finalResult= this.tanH(sum);
				SecondLayerOutputs.set(j,finalResult);
			}
		}
		
		//Final Layer Forward Pass Calculation
		
		weightCounter=0;
		for(int j=0;j<NumberOfCategories;j++) {
			double sum=0;
			for(int synapse=0;synapse<SecondLayerNeurons;synapse++) {
				sum=sum+SecondLayerOutputs.get(synapse)*FinalLayerWeights.get(weightCounter);
				weightCounter = weightCounter+1;
			}
			sum = sum + bias;
			if(activationFunction ==1) {
				double finalResult= this.logistic(sum);
				FinalLayerOutputs.set(j,finalResult);
			}else {
				double finalResult= this.tanH(sum);
				FinalLayerOutputs.set(j,finalResult);
			}
		}
		
		
	}
	
	public void backprop() {
		
		//Output Layer Backpropagation Calculation
		
		int weightcounter=0;
		
		for(int x=0;x<NumberOfCategories;x++) {
			
			for(int synapse=0;synapse<SecondLayerNeurons;synapse++) {
				
				double delta=0;
				if(activationFunction==1) {
					delta = this.partialDerivativeOfErrorWRTReal(target[x], FinalLayerOutputs.get(x))*
							this.partialDerivativeOfRealWRTSumLogistic(FinalLayerOutputs.get(x));
				}else {
					delta = this.partialDerivativeOfErrorWRTReal(target[x], FinalLayerOutputs.get(x))*
							this.partialDerivativeOfRealWRTSumTanh(FinalLayerOutputs.get(x));
				}
				
				
				double derErrWei = 0;
				derErrWei = delta * this.partialDerivativeOfSumWRTWeight(SecondLayerOutputs.get(synapse));
				
				double partialSum = 0;
				partialSum = FinalLayerWeightsPartialSum.get(weightcounter)+derErrWei;
				
				FinalLayerDelta.set(x,delta);
				FinalLayerWeightsPartialSum.set(weightcounter, partialSum);
				weightcounter = weightcounter + 1;
				
			}
			
		}
		
		//Second Hidden Layer Backpropagation Calculation
		
		int weightcounter2=0;
		
		for(int x=0;x<SecondLayerNeurons;x++) {
			
			double sum= 0;
			weightcounter = x;
			
			for(int d=0;d<NumberOfCategories;d++) {
				
				sum=sum+(FinalLayerWeights.get(weightcounter)*FinalLayerDelta.get(d));
				weightcounter=weightcounter+SecondLayerNeurons;
			}
			
			double delta=0;
			if(activationFunction==1) {
				delta = sum * SecondLayerOutputs.get(x)*(1-SecondLayerOutputs.get(x));
			}else {
				delta = sum * (1- Math.pow(SecondLayerOutputs.get(x), 2));
			}
			
			SecondLayerDelta.set(x,delta);
			for(int synapse=0;synapse<FirstLayerNeurons;synapse++) {
				
				double derErrWei=0;
				derErrWei=delta*FirstLayerOutputs.get(synapse);
				
				double partialSum = 0;
				partialSum = SecondLayerWeightsPartialSum.get(weightcounter2)+derErrWei;
				
				SecondLayerWeightsPartialSum.set(weightcounter2, partialSum);
				weightcounter2 = weightcounter2 + 1;
				
			}
		}
		
		//First Hidden Layer Backpropagation Calculation
		
		weightcounter2=0;
		
		
		for(int x=0;x<FirstLayerNeurons;x++) {
			
			double sum= 0;
			weightcounter = x;
			
			for(int d=0;d<SecondLayerNeurons;d++) {
				sum=sum+(SecondLayerWeights.get(weightcounter)*SecondLayerDelta.get(d));
				weightcounter=weightcounter+FirstLayerNeurons;
			}
			
			double delta=0;
			if(activationFunction==1) {
				delta = sum * FirstLayerOutputs.get(x)*(1-FirstLayerOutputs.get(x));
			}else {
				delta = sum  * (1- Math.pow(FirstLayerOutputs.get(x), 2));
			}
			
			FirstLayerDelta.set(x,delta);
			
			for(int synapse=0;synapse<NumberOfInputs;synapse++) {
				
				double derErrWei=0;
				derErrWei=delta*FirstInput.get(synapse);
				
				double partialSum = 0;
				partialSum = FirstLayerWeightsPartialSum.get(weightcounter2)+derErrWei;
				
				FirstLayerWeightsPartialSum.set(weightcounter2, partialSum);
				weightcounter2 = weightcounter2 + 1;
				
			}
		}
		
	}
	
	public void updateWeights() {
		int weightCounter=0;
		
		for(int i=0;i<FirstLayerNeurons;i++) {
			for(int synapse=0;synapse<NumberOfInputs;synapse++) {
				
				double newWeight= FirstLayerWeights.get(weightCounter)-(n*FirstLayerWeightsPartialSum.get(weightCounter));
				FirstLayerWeights.set(weightCounter, newWeight);
				weightCounter = weightCounter+1;
			}		
		}
				
		weightCounter=0;
		for(int j=0;j<SecondLayerNeurons;j++) {
			for(int synapse=0;synapse<FirstLayerNeurons;synapse++) {
				
				double newWeight= SecondLayerWeights.get(weightCounter)-(n*SecondLayerWeightsPartialSum.get(weightCounter));
				SecondLayerWeights.set(weightCounter, newWeight);
				weightCounter = weightCounter+1;
				
			}		
		}		
				
		weightCounter=0;
		for(int j=0;j<NumberOfCategories;j++) {
			for(int synapse=0;synapse<SecondLayerNeurons;synapse++) {

				double newWeight= FinalLayerWeights.get(weightCounter)-(n*FinalLayerWeightsPartialSum.get(weightCounter));
				FinalLayerWeights.set(weightCounter, newWeight);
				weightCounter = weightCounter+1;
				
			}	
		}
	}
	
	public double partialDerivativeOfErrorWRTReal(double Target,double RealOutput) {
		double out = 0;
		out = RealOutput-Target;
		return out;
	}
	
	public double partialDerivativeOfRealWRTSumLogistic(double RealOutput) {
		double out = 0;
		out = RealOutput*(1-RealOutput);
		return out;
	}
	
	public double partialDerivativeOfRealWRTSumTanh(double RealOutput) {
		
		double out=0;
		out= 1- Math.pow(RealOutput, 2);
		return out;
		
	}
	
	public double partialDerivativeOfSumWRTWeight(double PreviousRealOutput) {
		double out = 0;
		out = PreviousRealOutput;
		return out;
	}
	
	public double squaredError() {
		
		double out = 0;
		double sum = 0;
		
		for(int x=0;x<target.length;x++) {
			
			sum = sum + Math.pow(target[x]-FinalLayerOutputs.get(x), 2);
		}
		
		out = sum/2 ;
				
		return out;
	}
	
	public double logistic(double x) {
		double out=0;
		out = 1/(1+Math.exp(-x));
		return out;
	}
	
	public double tanH(double x) {
		double out=0;
		out=(Math.exp(x)-Math.exp(-x))/(Math.exp(x)+Math.exp(-x));
		return out;
	}
	
	public void updateInput(double x1, double x2) {
		FirstInput.set(0,x1);
		FirstInput.set(1,x2);
	}
	
	public void updateTarget(double[] aTarget) {
		target[0] = aTarget[0];
		target[1] = aTarget[1];
		target[2] = aTarget[2];
	}
	
	public ArrayList<Double> getOutput(){
		return FinalLayerOutputs;
	}
	
	public void initializePartialSum() {
		for(int y=0;y<(FirstLayerNeurons*NumberOfInputs);y++) {
			
			FirstLayerWeightsPartialSum.set(y,0.0);
		}
		
		for(int i=0;i<(SecondLayerNeurons*FirstLayerNeurons);i++) {
			
			SecondLayerWeightsPartialSum.set(i,0.0);
		}
		
		for(int j=0;j<(NumberOfCategories*SecondLayerNeurons);j++) {
			
			FinalLayerWeightsPartialSum.set(j,0.0);
		}
	}
}