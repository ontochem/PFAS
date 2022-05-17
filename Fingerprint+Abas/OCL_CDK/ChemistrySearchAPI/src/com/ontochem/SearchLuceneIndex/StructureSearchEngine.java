/**
 * Copyright 2022
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
			smilesparser.kekulise(true);			
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
                     MoleculeStandardizer.standardize(mol, 0);
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

