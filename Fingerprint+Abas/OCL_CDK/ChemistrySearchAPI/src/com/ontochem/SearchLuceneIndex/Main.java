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
import java.io.FileWriter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "Main", mixinStandardHelpOptions = true, version = "1.0", description = "Main")
public class Main implements Callable<String> {  
    @Option(names = {"-read_Smarts"}, description = "read SMARTS", required = true)
    private String smarts_file_name;
    @Option(names = {"-read_IndexDirectory"}, description = "path of Index Directory", required = true)
    private String index_file_name;
    @Option(names = {"-read_Module"}, description = "read substructure module", required = true)
    private String module;
    @Option(names = {"-write_Output"}, description = "output", required = true)
    private String out_file_name;

    	public static void main(String... args) {
    	    int exitCode = new CommandLine(new Main()).execute(args);
    	    System.exit(exitCode);
    	}

      public String call() throws Exception {
		
		FileWriter outWriter = new FileWriter(out_file_name);
		
		Map<String, List> QueryContainer = loadQuery.generate(smarts_file_name);
		
		long startTime = System.nanoTime();
		List<Integer> queryPositions = QueryContainer.get("QueryPositions");
		List<String> querySmarts = QueryContainer.get("QuerySmarts");

		queryPositions.stream().forEach( pos -> {
		try {
			// Fingerprint filer
			Map<String,List> NewTargetContainer = FingerprintFilter.generate(querySmarts.get(pos), index_file_name, module);
			List<String> NewTargetSmiles = NewTargetContainer.get("NewTargetSmiles");
			List<String> NewTargetOcids = NewTargetContainer.get("NewTargetOcids");
			System.out.println("#hits FP:" +NewTargetSmiles.size() +" " + querySmarts.get(pos) + "\n" + NewTargetOcids);
			System.out.println("");
			try {	
				outWriter.write("#hits FP:" +NewTargetSmiles.size() +" " + querySmarts.get(pos) + "\n" + NewTargetOcids +"\n");
	            outWriter.flush();
			}catch(Exception e){
				System.out.println("Error FP"+e);
			}

			// Substructure Search filter
            Map<String,List> NewTargetContainer1 = SubStructureSearchFilter.generate(NewTargetContainer, querySmarts.get(pos), module);
            List<String> NewTargetSmiles1 = NewTargetContainer1.get("NewTargetSmiles");
            List<String> NewTargetOcids1 = NewTargetContainer1.get("NewTargetOcids");
            System.out.println("#hits SSS:" +NewTargetSmiles1.size() +" " + querySmarts.get(pos) + "\n" + NewTargetOcids1);
            System.out.println("---");
            try {
            	outWriter.write("#hits SSS:" +NewTargetSmiles1.size() +" " + querySmarts.get(pos) + "\n" + NewTargetOcids1 +"\n");
				outWriter.flush();
            }catch(Exception e){
            	System.out.println("Error SSS"+e);
            }
            }catch(Exception e){
            	System.out.println("Error Main "+e);
            }

		});

		long endTime = System.nanoTime();
		long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
		double duration_sec = duration * 1.0e-9;
		System.out.println("Elapsed time: " + duration_sec);
		return null;
	}
	
}

