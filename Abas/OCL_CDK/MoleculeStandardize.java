/*
* Copyright (c)
* OntoChem GmbH (main office)
* Blücherstrasse 24
* 06120 Halle (Saale)
* Germany
*
* All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*
* 1. Redistributions of source code must retain the above copyright notice, this
*    list of conditions and the following disclaimer.
* 2. Redistributions in binary form must reproduce the above copyright notice,
*    this list of conditions and the following disclaimer in the documentation
*    and/or other materials provided with the distribution.
* 3. Neither the name of the the copyright holder nor the
*    names of its contributors may be used to endorse or promote products
*    derived from this software without specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
* ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
* WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
* ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
* (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
* ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
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
