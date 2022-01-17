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
import java.io.FileWriter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "Main", mixinStandardHelpOptions = true, version = "1.0", description = "Main")
public class Main implements Callable<String> {  
    @Option(names = {"-read_IndexDirectory"}, description = "path of Index Directory", required = true)
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

