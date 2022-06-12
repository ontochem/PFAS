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
import java.io.FileWriter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "Main", mixinStandardHelpOptions = true, version = "1.0", description = "Main")
public class Main implements Callable<String> {  
    @Option(names = {"-write_IndexDirectory"}, description = "path of Index Directory", required = true)
    private String index_file_name;
    @Option(names = {"-read_Module"}, description = "read substructure module", required = true)
    private String module;
    @Option(names = {"-read_Smiles"}, description = "read SMILES", required = true)
    private String smiles_file_name;

    	public static void main(String... args) {
    	    int exitCode = new CommandLine(new Main()).execute(args);
    	    System.exit(exitCode);
    	}

        public String call() throws Exception {
		
		Map<String, List> TargetContainer = loadSmiles.generate(smiles_file_name, module);
		long startTime = System.nanoTime();

		// Lucene index generator
		LuceneIndexForChemicalFingerprints.generate(TargetContainer, index_file_name);

		long endTime = System.nanoTime();
		long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
		double duration_sec = duration * 1.0e-9;
		System.out.println("Elapsed time: " + duration_sec);
		return null;
	}
	
}

