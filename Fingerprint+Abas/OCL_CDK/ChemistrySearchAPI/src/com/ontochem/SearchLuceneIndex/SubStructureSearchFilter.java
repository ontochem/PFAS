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

