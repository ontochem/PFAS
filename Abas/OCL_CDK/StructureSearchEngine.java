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
import org.openscience.cdk.aromaticity.Aromaticity;
import org.openscience.cdk.aromaticity.ElectronDonation;
import org.openscience.cdk.graph.CycleFinder;
import org.openscience.cdk.graph.Cycles;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.smarts.SMARTSQueryTool;

import com.actelion.research.chem.MoleculeStandardizer;
import com.actelion.research.chem.SSSearcher;
import com.actelion.research.chem.SmilesParser;
import com.actelion.research.chem.StereoMolecule;

import ambit2.core.processors.structure.HydrogenAdderProcessor;
import ambit2.smarts.SmartsHelper;
import ambit2.smarts.SmartsManager;
import ambit2.smarts.SmartsParser;
import ambit2.smarts.query.SmartsPatternCDK;
import chemaxon.formats.MolExporter;
import chemaxon.formats.MolImporter;
import chemaxon.sss.SearchConstants;
import chemaxon.sss.search.MolSearchOptions;
import chemaxon.sss.search.StandardizedMolSearch;
import chemaxon.struc.Molecule;
import chemaxon.struc.MoleculeGraph;
/*
 * Author@ Shadrack Jabes., B
 * Company@ OntoChem, GmbH
 *
 * Date@ Jan 2022
 *
 * Description@
 * atom by atom search (abas) using cheminformatics libraries
 */

public class StructureSearchEngine {
	public static String searchBySubstructure(String smi, String query, String module) throws Exception {
		try {
			String CdkModule = new String("Cdk");
			String OclModule = new String("Ocl");
			if(module.equals(CdkModule)) 
			{	
				String rsp = searchBySubstructureCdk(smi, query);
				return rsp;
			}
			if(module.equals(OclModule)) 
			{	
				String rsp = searchBySubstructureOcl(smi, query);
				return rsp;
			}
			
		} catch (Exception e) {
			//System.out.println("Error "+ e);
		}
		return null;
	}

	/*
	 * chemistry development kit (cdk) substructure searcher
	 */
	public static String searchBySubstructureCdk(String smiles, String sma) throws Exception{ 
		try {
			org.openscience.cdk.smiles.SmilesParser smilesparser = new org.openscience.cdk.smiles.SmilesParser(SilentChemObjectBuilder.getInstance());
			// The SmilesParser by default kekulise smiles, but since we want to study the effect of standardization we turn off the kekulisation explicitly. We have a seperate module through which we standardized/cross-standardize/no-standardize smiles following which substructure search is performed. 
			smilesparser.kekulise(false);			
			IAtomContainer target = smilesparser.parseSmiles(smiles);
			org.openscience.cdk.smarts.SmartsPattern ptrn = org.openscience.cdk.smarts.SmartsPattern.create(sma);
			
			int nUniqueHits = ptrn.matchAll(target).countUnique();
				
			if(nUniqueHits!=0) {
				return("true");
			}
			else {
				return("false");
			}
		    }catch (Exception e) {
				//System.out.println("Cdk Error "+e);
			}
			return null;
		}
	
	/*
	 * open chem lib (ocl) substructure searcher
	 */
	 public static String searchBySubstructureOcl(String Target,String Fragment){
         try {
                         StereoMolecule TargetsmilesToMolecule = OclConvertSmilesToMolecule(Target);
                         StereoMolecule FragmentsmilesToMolecule = OclConvertQueryToMolecule(Fragment);

                         FragmentsmilesToMolecule.setFragment(true);
                         TargetsmilesToMolecule.setFragment(false);

                         SSSearcher searcher = new SSSearcher();
                         searcher.setFragment(FragmentsmilesToMolecule);
                         searcher.setMolecule(TargetsmilesToMolecule);
                         return (""+searcher.isFragmentInMolecule());
         } catch ( Exception e)  {
                         //System.err.println("wrongSmiles:OCL "+Target+" "+e);
         }
	   	 return null;
	 }
	 
	 /*
	  * convert smiles to stereomolecule using open chem lib (ocl)
	  */
	 public static StereoMolecule OclConvertSmilesToMolecule(String _smiles){
         try{
                     int mode = SmilesParser.SMARTS_MODE_IS_SMILES
                                     | SmilesParser.MODE_SKIP_COORDINATE_TEMPLATES;

                     StereoMolecule mol = new StereoMolecule();
                     SmilesParser sp = new SmilesParser( mode, false);
                     sp.parse( mol, _smiles );
					 // to study the effect of standardization, we turn off standardization. We have a seperate module through which we standardized/cross-standardize/no-standardize smiles following which substructure search is performed. 
                     // MoleculeStandardizer.standardize(mol, 0);
                     return mol;
         }catch (Exception e){
                     //System.err.println("wrongSmiles:OCL "+_smiles+" "+e);
         }
         return null;
     }
	 /*
	  * convert smiles with smarts features to stereomolecule using open chem lib (ocl)
	  */
     public static StereoMolecule OclConvertQueryToMolecule(String query){
         try{
             StereoMolecule mol1 = new SmilesParser(SmilesParser.SMARTS_MODE_IS_SMARTS, false).parseMolecule(query);
             return mol1;

         }catch (Exception e){
                     //System.err.println("wrongQuery:OCL "+query+" "+e);
         }
         return null;
     }
}

