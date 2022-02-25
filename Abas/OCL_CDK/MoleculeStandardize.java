
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

import com.actelion.research.chem.MoleculeStandardizer;
import com.actelion.research.chem.SmilesCreator;
import com.actelion.research.chem.StereoMolecule;
import com.actelion.research.chem.IsomericSmilesCreator;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesGenerator;

/*
 * Author@ Shadrack Jabes., B
 * Company@ OntoChem, GmbH
 *
 * Date@ Jan 2022
 *
 * Description@
 * perform standardization of target smiles using cheminformatics libraries
 */
public class MoleculeStandardize {

	public static String generate(String smiles, String module) throws Exception {
		try {
			String CdkModule = new String("Cdk");
			String OclModule = new String("Ocl");
			if(module.equals(CdkModule)) 
			{	
				String stdSmiles = CdkStandardizer(smiles);
				return stdSmiles;
			}
			if(module.equals(OclModule)) 
			{	
				String stdSmiles = OclStandardizer(smiles);
				return stdSmiles;
			}
		} catch (Exception e) {
			//System.out.println("InvalidSmiles "+ e);
			return "InvalidSmiles ";
		}
	return null;
	}

	public static String OclStandardizer(String smiles) {
		try {

			int mode = com.actelion.research.chem.SmilesParser.SMARTS_MODE_IS_SMILES
         	| com.actelion.research.chem.SmilesParser.MODE_SKIP_COORDINATE_TEMPLATES;
                        
         	StereoMolecule mol = new StereoMolecule(); 
         	new com.actelion.research.chem.SmilesParser( mode, false ).parse( mol, smiles.getBytes() );
         	MoleculeStandardizer.standardize( mol, 0 );
			String NewSmiles = new IsomericSmilesCreator( mol, mode ).getSmiles( );   
            return NewSmiles;
		}catch (Exception e) {
			//System.err.println("wrongSmiles:Ocl "+smiles_input+" "+e);
			return "InvalidSmiles";
		}
	}

	public static String CdkStandardizer(String smiles_input) {
		try {

			org.openscience.cdk.smiles.SmilesParser smilesparser = new org.openscience.cdk.smiles.SmilesParser(SilentChemObjectBuilder.getInstance());
			@SuppressWarnings("deprecation")
			SmilesGenerator smilesgenerator = new SmilesGenerator();
			smilesparser.kekulise(true);
			IAtomContainer target = smilesparser.parseSmiles(smiles_input);
			@SuppressWarnings("deprecation")
			String NewSmiles = smilesgenerator.createSMILES(target);
			return NewSmiles;
		}catch (Exception e) {
			//System.err.println("wrongSmiles:Ocl "+smiles_input+" "+e);
			return "InvalidSmiles";
		}
	}
}
