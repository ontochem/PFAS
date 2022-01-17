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

