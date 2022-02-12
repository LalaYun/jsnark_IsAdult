/*******************************************************************************
 * Author: Ahmed Kosba <akosba@cs.umd.edu>
 * Modifier : Lala Yun
 *******************************************************************************/
package examples.generators;

import circuit.eval.CircuitEvaluator;
import circuit.structure.CircuitGenerator;
import circuit.structure.Wire;

import examples.gadgets.hash.SHA256Gadget;
import java.math.BigInteger;

public class IsAdultCircuitGenerator extends CircuitGenerator {

	private Wire age;
	private Wire[] hashAge;
	private Wire[] hashAns;
	private Wire isEqual;

	public IsAdultCircuitGenerator(String circuitName) {
		super(circuitName);
	}

	@Override
	protected void buildCircuit() {
		isEqual = getZeroWire();

		// declare input wire of length 1.
		age = createProverWitnessWire();

		// isAdult = ageInput >= 20 
		Wire isAdult = age.isGreaterThanOrEqual(20, 32);
		
		// isAdult에 대한 hash값
		hashAns = createInputWireArray(8);
		hashAge = createProverWitnessWireArray(2);
		Wire[] ageDigest = new SHA256Gadget(hashAge, 8, 2, false, true).getOutputWires();

		for (int i = 0; i < ageDigest.length; ++i){
			isEqual = isEqual.add(ageDigest[i].isEqualTo(hashAns[i]));
		}

		makeOutput(isAdult, "I am adult");
		makeOutput(isEqual.isEqualTo(new BigInteger("8")), "You are Correct");
		// makeOutputArray(ageDigest);
	}

	@Override
	public void generateSampleInput(CircuitEvaluator circuitEvaluator) {
		
		final long ageInput = 24;
		final String ageStr = "24";
	
		circuitEvaluator.setWireValue(age, ageInput);
		
		// "C2356069E9D1E79CA924378153CFBBFB4D4416B1F99D41A2940BFDB66C5319DB";
		circuitEvaluator.setWireValue(hashAns[0], new BigInteger("3258277993"));
		circuitEvaluator.setWireValue(hashAns[1], new BigInteger("3922847644"));
		circuitEvaluator.setWireValue(hashAns[2], new BigInteger("2837723009"));
		circuitEvaluator.setWireValue(hashAns[3], new BigInteger("1406123003"));
		circuitEvaluator.setWireValue(hashAns[4], new BigInteger("1296307889"));
		circuitEvaluator.setWireValue(hashAns[5], new BigInteger("4187832738"));
		circuitEvaluator.setWireValue(hashAns[6], new BigInteger("2483813814"));
		circuitEvaluator.setWireValue(hashAns[7], new BigInteger("1817385435"));

		for (int i = 0; i < hashAge.length; i++) {
			circuitEvaluator.setWireValue(hashAge[i], ageStr.charAt(i));
		}
	}

	public static void main(String[] args) throws Exception {

		IsAdultCircuitGenerator generator = new IsAdultCircuitGenerator("age_example");
		generator.generateCircuit();
		generator.evalCircuit();
		generator.prepFiles();
		generator.runLibsnark();
	}

}
