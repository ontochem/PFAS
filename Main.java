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
	                		String standardizedSmiles = MoleculeStandarize(smiles, module);
							/*
							 * perform substructure search. Note: standardization is turned off deliberatly while performing substructure search, so as to study the effect of standardization. 
							 */
	                		String response = StructureSearchEngine(standardizedSmiles, query, module);
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


