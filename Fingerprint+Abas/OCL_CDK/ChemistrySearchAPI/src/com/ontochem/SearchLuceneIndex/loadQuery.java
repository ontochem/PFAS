/**
 * Copyright  2022
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
