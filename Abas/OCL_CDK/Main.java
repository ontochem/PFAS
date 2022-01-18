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
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Scanner;

import com.actelion.research.chem.IsomericSmilesCreator;
import com.actelion.research.chem.SSSearcher;
import com.actelion.research.chem.SmilesParser;
import com.actelion.research.chem.StereoMolecule;
import com.actelion.research.chem.descriptor.DescriptorHandlerLongCFP;

/*
 * Author@ Shadrack Jabes., B
 * Company@ OntoChem, GmbH
 *
 * Date@ Jan 2022
 *
 * Description@
 * perform standardization + atom by atom search (abas) using cheminformatics libraries
 */
public class Main {

	        public static void main(String[] args) throws Exception{
	                FileReader txt = new FileReader("smiles-set.txt");
	                FileWriter fwout = new FileWriter("output.txt");
	        		
	                BufferedWriter bw = new BufferedWriter(fwout);

	                Scanner scan = null;
	                try {
	                        scan = new Scanner(txt);
	                } catch (Exception e) {
	                        e.printStackTrace();
	                }
	                ArrayList<String> data = new ArrayList<String>() ;
	                while(scan.hasNextLine()){
	                    data.add(scan.nextLine());
	                }
					/*
					 * cheminformatics modules, Cdk or Ocl
					 * Cdk - chemistry development kit
					 * Ocl - open chem lib
					 */
					String module = new String("Cdk");
	                String[] simpleArray = data.toArray(new String[]{});
	                for(int i = 0;i < simpleArray.length;i++){
	                	try {
	                		String smiles = simpleArray[i];
							/*
							 * enter smarts query. Both Cdk and Ocl can handle SMARTS query features.
							 */
	                		String query = "EnterPfasQueryHere";
/*
							 * standardization of target molecules. To perform cross-standardization (ie) standardization with a different library and performing substructure search with another library, choose the appropriate module to standardize.
							 */
	                		String standardizedSmiles = MoleculeStandardize.generate(smiles, module);
	                		
							/*
							 * perform substructure search. Note: standardization is turned off deliberately while performing substructure search, so as to study the effect of standardization. 
							 */
	                		String response = StructureSearchEngine.searchBySubstructure(standardizedSmiles, query, module);
	                		
	                		System.out.println(response);
	                		bw.write(status);
	                		bw.newLine();
	                	}catch (Exception e){
	            			//System.err.println(" Error "+e);
	            	    }
                }
                bw.close();
  
                }
}


