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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.BitSet;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hit;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.openscience.cdk.similarity.Tanimoto;

public class LuceneIndexSearcher {

	public static final String FIELD_FINGERPRINT = "fingerprint";
	public static final String FIELD_SMILES = "smiles";
	public static final String FIELD_OCIDS = "ocids";

	public static Map<String,List> generate(BitSet QueryFP, String indexDirectory) {
			Map<String,List> NewTargetContainer = new HashMap<String, List>();
			try {
				String QueryFP1 = QueryFP.toString().replaceAll("\\{", "").replaceAll("\\}", "").replaceAll(",", " AND");
				NewTargetContainer = searchIndex(QueryFP1,indexDirectory);
			} catch (Exception e) {
				//System.out.println("Error in creating lucene target index "+e);
			};		
			return NewTargetContainer;
	}

	public static Map<String,List> searchIndex(String searchString, String indexDirectory) throws IOException, ParseException {
		String INDEX_DIRECTORY = indexDirectory;

		//System.out.println("Searching for '" + searchString + "'");
		Directory directory = FSDirectory.getDirectory(INDEX_DIRECTORY);
		IndexReader indexReader = IndexReader.open(directory);
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		Analyzer analyzer = new StandardAnalyzer();

			QueryParser queryParser = new QueryParser(FIELD_FINGERPRINT, analyzer);
			Query query = queryParser.parse(searchString);
			Hits hits = indexSearcher.search(query);
			//System.out.println("Number of hits: " + hits.length());
			@SuppressWarnings("unchecked")
			Iterator<Hit> it = hits.iterator();
			Map<String,List> NewTargetContainer = new HashMap<String, List>();
			List<String> NewTargetSmiles = new ArrayList<String>();
			List<String> NewTargetOcids = new ArrayList<String>();
			List<Integer> NewTargetPositions = new ArrayList<Integer>();
			String response = null;
			int curLine = 0;
			while (it.hasNext()) {
				Hit hit = it.next();
				Document document = hit.getDocument();
				NewTargetSmiles.add(document.get(FIELD_SMILES));
				NewTargetOcids.add(document.get(FIELD_OCIDS));
				NewTargetPositions.add(curLine);
				curLine++;
			}
			NewTargetContainer.put("NewTargetSmiles", NewTargetSmiles);
			NewTargetContainer.put("NewTargetOcids", NewTargetOcids);
			NewTargetContainer.put("NewTargetPositions", NewTargetPositions);
			return NewTargetContainer;
	}

}

