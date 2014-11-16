package ar.exa.edu.unicen.compiler.cg;

import java.util.List;

import ar.exa.edu.unicen.compiler.lexical.utils.Triplet;

public class AssemblerTranslator {
	
	private static AssemblerTranslator as = null;
	
	public static AssemblerTranslator getInstance(){
		return as == null? new AssemblerTranslator(): as;
	}
	
	private AssemblerTranslator(){
		
	}
	
	public String getAssemblerCode(Triplet triplet, int position){
//		return TripletUtils.getCodeForTriplet(triplet, position);
		return "";
	}
	
	public String getAssemblerCode(List<Triplet> tripletList){
		String assemblerCode = "";
		assemblerCode = loadProgramStructure();
		for (int i =0; i<tripletList.size(); i++){
			assemblerCode += getAssemblerCode(tripletList.get(i), i);
		}
		
		return assemblerCode;
	}

	private String loadProgramStructure() {
		// TODO Load the structure need to run the file that represents the program
		return "";
	}

}
