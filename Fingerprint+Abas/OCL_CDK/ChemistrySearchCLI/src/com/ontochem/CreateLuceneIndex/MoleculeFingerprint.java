/**
 * Copyright  2022
 * OntoChem GmbH (main office)
 * Blücherstrasse 24
 * 06120 Halle (Saale)
 * Germany

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ontochem.CreateLuceneIndex;
import java.util.BitSet;

import com.actelion.research.chem.MoleculeStandardizer;
import com.actelion.research.chem.StereoMolecule;
import com.actelion.research.chem.descriptor.DescriptorHandlerLongFFP512;
import com.actelion.research.chem.SSSearcherWithIndex;
import com.actelion.research.chem.descriptor.DescriptorHandlerFFP512;
import com.actelion.research.chem.MoleculeStandardizer;
import com.actelion.research.chem.SSSearcher;
import com.actelion.research.chem.StereoMolecule;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.aromaticity.Aromaticity;
import org.openscience.cdk.aromaticity.ElectronDonation;
import org.openscience.cdk.fingerprint.AbstractFingerprinter;
import org.openscience.cdk.fingerprint.Fingerprinter;
import org.openscience.cdk.fingerprint.CircularFingerprinter;
import org.openscience.cdk.graph.CycleFinder;
import org.openscience.cdk.graph.Cycles;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.tools.CDKHydrogenAdder;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import ambit2.core.data.MoleculeTools;
import ambit2.core.helper.CDKHueckelAromaticityDetector;
import ambit2.core.processors.structure.HydrogenAdderProcessor;
import ambit2.smarts.SmartsFingerprinter;
import ambit2.smarts.SmartsParser;

public class MoleculeFingerprint {
	public static BitSet generate(String smi, String module) throws Exception {
		try {
			String CdkModule = new String("Cdk");
			String OclModule = new String("Ocl");
			if(module.equals(CdkModule)) 
			{	
				BitSet bs = CdkMoleculeFingerprint(smi);
				return bs;
			}
			if(module.equals(OclModule)) 
			{	
				BitSet bs = OclMoleculeFingerprint(smi);
				return bs;
			}
		} catch (Exception e) {
			//System.out.println("InvalidSmiles "+ e);
		}
		return null;
	}

    public static BitSet OclMoleculeFingerprint(String Input){
        try {
                StereoMolecule mol = OclConvertSmilesToMolecule(Input);
                DescriptorHandlerLongFFP512 descriptor = new DescriptorHandlerLongFFP512();
                long[] index = descriptor.createDescriptor(mol);
                //System.out.println(BitSet.valueOf(index));
                return BitSet.valueOf(index);
        } catch (Exception e) {
                //System.out.println("Error OclMoleculeFingerprint "+e);
        }
		return null;
    }
	 /*
	  * convert smiles to stereomolecule using open chem lib (ocl)
	  */
	 public static StereoMolecule OclConvertSmilesToMolecule(String _smiles){
         try{
                     int mode = com.actelion.research.chem.SmilesParser.SMARTS_MODE_IS_SMILES
                                     | com.actelion.research.chem.SmilesParser.MODE_SKIP_COORDINATE_TEMPLATES;

                     StereoMolecule mol = new StereoMolecule();
                     com.actelion.research.chem.SmilesParser sp = new com.actelion.research.chem.SmilesParser( mode, false);
                     sp.parse( mol, _smiles );
                     MoleculeStandardizer.standardize(mol, 0);
                     return mol;
         }catch (Exception e){
                     //System.err.println("wrongSmiles:OCL "+_smiles+" "+e);
         }
         return null;
     }

	public static BitSet CdkMoleculeFingerprint(String smiles) throws Exception 
	{
		try {
				IChemObjectBuilder bldr = SilentChemObjectBuilder.getInstance();
				org.openscience.cdk.smiles.SmilesParser smipar = new org.openscience.cdk.smiles.SmilesParser(bldr);
				IAtomContainer mol = smipar.parseSmiles(smiles);
				preProcess(mol);
				//cdk fingerprint
				Fingerprinter fpr = new Fingerprinter();
				BitSet bs = fpr.getBitFingerprint(mol).asBitSet();
				//String fp = bs.toString().replaceAll("\\{", "").replaceAll("\\}", "").replaceAll(",", "");
				//System.out.println("Target FP: "+fp);
				return bs;
		} catch (Exception e) {
			//System.out.println("Error CdkMolecularFingerprint "+e);
		}
	return null;
	}

	public static void preProcess(IAtomContainer mol) throws Exception 
	{
		ElectronDonation model = ElectronDonation.daylight();
		CycleFinder cycles = Cycles.all();
		Aromaticity arom = new Aromaticity(model,cycles);
		arom.apply(mol);

		/* explicit hydrogen */
		HydrogenAdderProcessor adder = new HydrogenAdderProcessor();
		adder.convertImplicitToExplicitHydrogens(mol);
	}
}
