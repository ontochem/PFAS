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
package com.ontochem.CreateLuceneIndex;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.actelion.research.chem.Molecule;
import com.actelion.research.chem.StereoMolecule;

public class loadSmiles {
	
	public static Map<String,List> generate(String fileName, String module) throws Exception
	{
	try {
		Map<String,List> TargetContainer = new HashMap<String, List>();
		List<String> TargetSmiles = new ArrayList<String>();
		List<String> TargetOcids = new ArrayList<String>();
		List<BitSet> TargetFps = new ArrayList<BitSet>();
		List<Integer> TargetPositions = new ArrayList<Integer>();
		
		File file = new File(fileName);		
		RandomAccessFile f = new RandomAccessFile(file,"r");			
		long length = f.length();
		int curLine = 0;
		while (f.getFilePointer() < length)
		{		
			String CdkModule = new String("Cdk");
			String OclModule = new String("Ocl");
			String line = f.readLine();
			
			/* load smiles */
			String smi = line.split("\t")[0];

			/* fingerprint for smiles */
			BitSet bs = MoleculeFingerprint.generate(smi,module);

			TargetSmiles.add(smi);
			TargetOcids.add(line.split("\t")[1]);
			TargetFps.add(bs);
			TargetPositions.add(curLine);

			System.out.println("loading smiles "+curLine);
			curLine++;

		}
		TargetContainer.put("TargetSmiles", TargetSmiles);
		TargetContainer.put("TargetOcids", TargetOcids);
		TargetContainer.put("TargetFps", TargetFps);
		TargetContainer.put("TargetPositions", TargetPositions);

		f.close();
		return TargetContainer;
		}
		catch (Exception e) {
			//System.out.println("Error loading Smiles " + e);
		}
		return null;

	}
}

