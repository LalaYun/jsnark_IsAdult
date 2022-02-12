## jsnark

jsnark Reference : [jsnark GitHub](https://github.com/akosba/jsnark)

### 📍 Example explanation

> Create a **buildcircuit** that Prover receives an age input value and proves to Verifier that **the age is 20 years or older**.

#### 🧑‍💻 Prover
  He knows someone's age and hashed age values. He should set the age to witness so that the verifier does not know the age and at the same time delivers a proof to the verifier that proves whether the age is older than 20.
<br/>
#### 👩‍💻 Verifier
  She receives the hash value of the age and the prof from the prover. Proof determines whether the hash value of the age is the same as the value directly hashed by the prover to verify that the prover knows the information about the age.
<br/>
<hr/>

### 📍 Solution explanation

* Route : *jsnark_IsAdult > JsnarkCircuitBuilder > src > examples > generators > IsAdultCircuitGenerator.java* <br/>

**1. age, hashAge, hashAns, isEqual 선언**
> age : prover가 알고 있는 age (witness)<br/>
> hashAge : prover가 알고 있는 age를 hash한 wire 배열<br/>
> hashAns : 실제 age를 hash한 wire 배열<br/>
> isEqual : hashAge와 hashAns가 동일한지 판별하는 wire
> 
```java
  public class IsAdultCircuitGenerator extends CircuitGenerator {
  
  private Wire age;
	private Wire[] hashAge;
	private Wire[] hashAns;
	private Wire isEqual;
```
<br/>
<br/>

**2. CircuitGenerator 생성자 초기화**

```java
public IsAdultCircuitGenerator(String circuitName) {
		super(circuitName);
	}
```
<br/>
<br/>

**3. buldCircuit**

```java
protected void buildCircuit() {

    isEqual = getZeroWire();

		// declare input wire of length 1.
		age = createProverWitnessWire();

		// isAdult = ageInput >= 20 
		Wire isAdult = age.isGreaterThanOrEqual(20, 32);
		
		// the value of isAdult to hash
 		hashAns = createInputWireArray(8);
		hashAge = createProverWitnessWireArray(2);
		Wire[] ageDigest = new SHA256Gadget(hashAge, 8, 2, false, true).getOutputWires();
    
    // update isEqual
		for (int i = 0; i < ageDigest.length; ++i){
			isEqual = isEqual.add(ageDigest[i].isEqualTo(hashAns[i]));
		}
    
    // if isEqual == 8 : Correct
		makeOutput(isAdult, "I am adult");
		makeOutput(isEqual.isEqualTo(new BigInteger("8")), "You are Correct");
	}
```
<br/>
<br/>

**4. generateSampleInput**

```java
public void generateSampleInput(CircuitEvaluator circuitEvaluator) {
		
   //you can change input
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
```
<br/>
<hr/>
<br/>

This is a Java library for building circuits for preprocessing zk-SNARKs. The library uses libsnark as a backend (https://github.com/scipr-lab/libsnark), and can integrate circuits produced by the Pinocchio compiler (https://vc.codeplex.com/SourceControl/latest) when needed by the programmer. The code consists of two main parts:
- `JsnarkCircuitBuilder`: A Java project that has a Gadget library for building/augmenting circuits. (Check the `src/examples` package)
- `libsnark/jsnark_interface`: A C++ interface to libsnark which accepts circuits produced by either the circuit builder or by Pinocchio's compiler directly.
<br/>
