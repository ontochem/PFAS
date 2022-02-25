/**
 * Copyright 
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
package com.ontochem.SearchLuceneIndex;
import java.util.BitSet;

import com.actelion.research.chem.MoleculeStandardizer;
import com.actelion.research.chem.StereoMolecule;
import com.actelion.research.chem.descriptor.DescriptorHandlerLongFFP512;
import com.actelion.research.chem.SSSearcherWithIndex;
import com.actelion.research.chem.descriptor.DescriptorHandlerFFP512;
import com.actelion.research.chem.IsomericSmilesCreator;
import com.actelion.research.chem.MoleculeStandardizer;
import com.actelion.research.chem.MolfileCreator;
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

public class QueryFingerprint {
	public static BitSet generate(String query, String module) throws Exception {
		try {
			String CdkModule = new String("Cdk");
			String OclModule = new String("Ocl");
			if(module.equals(CdkModule)) 
			{	
				//smarts query
				BitSet bs = CdkQueryFingerprint(query);
				return bs;
			}
			if(module.equals(OclModule)) 
			{	
				// smiles query with smarts feature
				BitSet bs = OclQueryFingerprint(query);
				return bs;
			}
		} catch (Exception e) {
			System.out.println("InvalidSmiles "+ e);
		}
		return null;
	}

	 public static BitSet OclQueryFingerprint(String Input){
        try {             
                	StereoMolecule mol = OclConvertQueryToMolecule(Input);
                	DescriptorHandlerLongFFP512 descriptor = new DescriptorHandlerLongFFP512();
                	long[] index = descriptor.createDescriptor(mol);
                	//System.out.println(BitSet.valueOf(index));
                	return BitSet.valueOf(index);
        } catch (Exception e) {
                System.out.println("Error OclQueryFingerprint "+e);
        }
        return null;
    }
	 /*
	  * convert smiles with smarts features to stereomolecule using open chem lib (ocl)
	  */
     public static StereoMolecule OclConvertQueryToMolecule(String query){
         try{
             StereoMolecule mol1 = new com.actelion.research.chem.SmilesParser(com.actelion.research.chem.SmilesParser.SMARTS_MODE_IS_SMARTS, false).parseMolecule(query);
             return mol1;

         }catch (Exception e){
                     //System.err.println("wrongQuery:OCL "+query+" "+e);
         }
         return null;
     }

	public static BitSet CdkQueryFingerprint(String SmartsInput){
		try {
			IChemObjectBuilder bldr = SilentChemObjectBuilder.getInstance();
			SmartsParser smartsParser = new SmartsParser();
			smartsParser.mSupportSmirksSyntax = true;
			smartsParser.hasRecursiveSmarts = true;
			smartsParser.mSupportDoubleBondAromaticityNotSpecified = true;
			smartsParser.mSupportSingleBondAromaticityNotSpecified = true;
			smartsParser.needExplicitHData();
			smartsParser.setComponentLevelGrouping(true);
			org.openscience.cdk.isomorphism.matchers.IQueryAtomContainer query = smartsParser.parse(SmartsInput);
			SmartsFingerprinter fpr = new SmartsFingerprinter(bldr);
			BitSet bs = fpr.getFingerprint(query);
			//String fp = bs.toString().replaceAll("\\{", "").replaceAll("\\}", "").replaceAll(",", " OR");
			//System.out.println("Query FP: "+fp);
			return bs;
		} catch (Exception e) {
			//System.out.println("Error CdkQueryFingerprint "+e);
		}
		return null;
	}
}
