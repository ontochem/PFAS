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
import java.util.List;
import java.util.Map;
import java.util.BitSet;

public class FingerprintFilter {
	public static Map<String,List> generate(String smarts, String indexPath, String module) throws Exception{
			BitSet QueryFp = QueryFingerprint.generate(smarts, module);
			//System.out.println(smarts + " " + QueryFp);	
			Map<String,List> NewTargetContainer = LuceneIndexSearcher.generate(QueryFp, indexPath);
			return NewTargetContainer;
		}
}
