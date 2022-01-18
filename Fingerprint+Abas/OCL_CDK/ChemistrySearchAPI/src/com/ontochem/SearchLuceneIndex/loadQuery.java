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
package com.ontochem.SearchLuceneIndex;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openscience.cdk.silent.SilentChemObjectBuilder;

import ambit2.smarts.SmartsHelper;
import ambit2.smarts.SmartsParser;
import chemaxon.formats.MolExporter;
import chemaxon.formats.MolImporter;
import chemaxon.struc.Molecule;
import chemaxon.struc.MoleculeGraph;

public class loadQuery {
	
	public static Map<String,List> generate(String fileName) throws Exception
	{
		try {
		Map<String,List> QueryContainer = new HashMap<String, List>();
		List<String> QuerySmarts = new ArrayList<String>();
		List<Integer> QueryPositions = new ArrayList<Integer>();

		
		File file = new File(fileName);		
		RandomAccessFile f = new RandomAccessFile(file,"r");			
		long length = f.length();
		int curLine = 0;
		while (f.getFilePointer() < length)
		{		
			String line = f.readLine();
			
			//load smarts
			String sma = line.split("\t")[0];
			//System.out.println(sma);
			
			QuerySmarts.add(sma);
			QueryPositions.add(curLine);

			//System.out.println("loading smarts "+curLine);
			curLine++;
			
		}
		QueryContainer.put("QuerySmarts", QuerySmarts);
		QueryContainer.put("QueryPositions", QueryPositions);

		f.close();
		return QueryContainer;
		}
		catch (Exception e) {
			System.out.println("Error loading Smarts " + e);
		}
		return null;
	}
}
