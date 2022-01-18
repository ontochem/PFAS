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

