## jsnark

jsnark Reference : [jsnark GitHub](https://github.com/akosba/jsnark)

<br/>

### 📍 Example explanation

> Create a **buildcircuit** that Prover receives an age input value and proves to Verifier that **the age is 20 years or older**.
<br/>

#### 🧑‍💻 Prover
  He knows someone's age and hashed age values. He should set the age to witness so that the verifier does not know the age and at the same time delivers a proof to the verifier that proves whether the age is older than 20.
<br/>
#### 👩‍💻 Verifier
  She receives the hash value of the age and the prof from the prover. Proof determines whether the hash value of the age is the same as the value directly hashed by the prover to verify that the prover knows the information about the age.
<br/>
<hr/>
<br/>
<br/>
This is a Java library for building circuits for preprocessing zk-SNARKs. The library uses libsnark as a backend (https://github.com/scipr-lab/libsnark), and can integrate circuits produced by the Pinocchio compiler (https://vc.codeplex.com/SourceControl/latest) when needed by the programmer. The code consists of two main parts:
- `JsnarkCircuitBuilder`: A Java project that has a Gadget library for building/augmenting circuits. (Check the `src/examples` package)
- `libsnark/jsnark_interface`: A C++ interface to libsnark which accepts circuits produced by either the circuit builder or by Pinocchio's compiler directly.
<br/>
