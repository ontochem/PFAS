/**
 * Copyright 2022
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

public class LuceneIndexCreator {

	public static final String FIELD_FINGERPRINT = "fingerprint";
	public static final String FIELD_SMILES = "smiles";
	public static final String FIELD_OCIDS = "ocids";

	public static Map<String,List> generate(Map<String,List> TargetContainer, String indexDirectory) {
			Map<String,List> NewTargetContainer = new HashMap<String, List>();

			try {
				createIndex(TargetContainer, indexDirectory);
			} catch (Exception e) {
				//System.out.println("Error in creating lucene target index "+e);
			};		
			return NewTargetContainer;
	}

	public static void createIndex(Map<String,List> TargetContainer, String indexDirectory) throws CorruptIndexException, LockObtainFailedException, IOException {
		String INDEX_DIRECTORY = indexDirectory;
		List<String> TargetOcids = TargetContainer.get("TargetOcids");
		List<BitSet> TargetFps = TargetContainer.get("TargetFps");
		List<String> TargetSmiles = TargetContainer.get("TargetSmiles");
		List<Integer> TargetPositions = TargetContainer.get("TargetPositions");
		Analyzer analyzer = new StandardAnalyzer();
		boolean recreateIndexIfExists = true;
		 
		IndexWriter indexWriter = new IndexWriter(INDEX_DIRECTORY, analyzer, recreateIndexIfExists);
		TargetPositions.parallelStream().forEach( pos -> {
		try {
			Document document = new Document();
			document.add(new Field(FIELD_FINGERPRINT, TargetFps.get(pos).toString(), Field.Store.YES, Field.Index.TOKENIZED));
			document.add(new Field(FIELD_SMILES, TargetSmiles.get(pos), Field.Store.YES, Field.Index.UN_TOKENIZED));
			document.add(new Field(FIELD_OCIDS, TargetOcids.get(pos), Field.Store.YES, Field.Index.UN_TOKENIZED));
			indexWriter.addDocument(document);
		}catch(Exception e) {
			//System.out.println("Error create Lucene Index " + e);
		}
		});
		indexWriter.close();
	}

}

