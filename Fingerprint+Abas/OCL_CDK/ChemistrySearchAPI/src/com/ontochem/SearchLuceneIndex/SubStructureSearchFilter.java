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
package com.ontochem.SearchLuceneIndex;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class SubStructureSearchFilter {
	public static Map<String,List>   generate(Map<String, List> TargetContainer, String smarts, String module){
	    String trueResponse = new String("true");
		List<String> TargetOcids = TargetContainer.get("NewTargetOcids");
		List<Integer> TargetPositions = TargetContainer.get("NewTargetPositions");
		List<String> TargetSmiles = TargetContainer.get("NewTargetSmiles");
		String CdkModule = new String("Cdk");
		String OclModule = new String("Ocl");
		
		Map<String,List> NewTargetContainer = new HashMap<String, List>();
		List<String> NewTargetOcids = Collections.synchronizedList(new ArrayList<>());
		List<String> NewTargetSmiles = Collections.synchronizedList(new ArrayList<>());

            	TargetPositions.parallelStream().forEach( pos -> {
					try {
							String response = StructureSearchEngine.searchBySubstructure(TargetSmiles.get(pos), smarts, module);
							if(response.equals(trueResponse)) {
								NewTargetOcids.add(TargetOcids.get(pos));
								NewTargetSmiles.add(TargetSmiles.get(pos));
							}
					}catch(Exception e) {
						System.out.println("Error SSS assignment"+e);
					}
				});
            	NewTargetContainer.put("NewTargetSmiles", NewTargetSmiles);
        		NewTargetContainer.put("NewTargetOcids", NewTargetOcids);
		return NewTargetContainer;
	}
}

