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
package com.ontochem.CreateLuceneIndex;
import java.util.List;
import java.util.Map;
import java.util.BitSet;

public class LuceneIndexForChemicalFingerprints {
	public static void generate(Map<String,List> TargetContainer, String indexPath){
		Map<String,List> NewTargetContainer = LuceneIndexCreator.generate(TargetContainer, indexPath);
	}

}
