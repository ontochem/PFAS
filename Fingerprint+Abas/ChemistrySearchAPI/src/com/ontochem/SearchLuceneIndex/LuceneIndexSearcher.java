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

