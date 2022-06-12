rm build.jar;
rm -rvf classes indexDirectory;
mkdir classes indexDirectory;

javac -d classes -cp lib/cdk-2.5.jar:lib/lucene-core-2.3.0.jar:lib/lucene-core-2.3.0-javadoc.jar:lib/lucene-core-2.3.0-sources.jar:lib/activation-1.1.jar:lib/ambit2-balloon-3.1.0.jar:lib/ambit2-base-3.1.0.jar:lib/ambit2-chebi-3.1.0.jar:lib/ambit2-core-3.1.0.jar:lib/ambit2-db-3.1.0.jar:lib/ambit2-dbi5-3.1.0.jar:lib/ambit2-descriptors-3.1.0.jar:lib/ambit2-dragon-3.1.0.jar:lib/ambit2-export-3.1.0.jar:lib/ambit2-fp-3.1.0.jar:lib/ambit2-loom-3.1.0.jar:lib/ambit2-mlcommon-3.1.0.jar:lib/ambit2-model-3.1.0.jar:lib/ambit2-mopac-3.1.0.jar:lib/ambit2-namestructure-3.1.0.jar:lib/ambit2-poi-3.1.0.jar:lib/ambit2-pubchem-3.1.0.jar:lib/ambit2-rendering-3.1.0.jar:lib/ambit2-rest-3.1.0.jar:lib/ambit2-rules-3.1.0.jar:lib/ambit2-smarts-3.1.0.jar:lib/ambit2-smi23d-3.1.0.jar:lib/ambit2-some-3.1.0.jar:lib/ambit2-tautomers-3.1.0.jar:lib/ambit2-user-3.1.0.jar:lib/ambit2-weka-3.1.0.jar:lib/automaton-1.11-8.jar:lib/beam-core-0.9.1.jar:lib/beam-func-0.9.1.jar:lib/c3p0-0.9.5.1.jar:lib/cdk-atomtype-1.5.13.jar:lib/cdk-charges-1.5.13.jar:lib/cdk-cip-1.5.13.jar:lib/cdk-core-1.5.13.jar:lib/cdk-data-1.5.13.jar:lib/cdk-dict-1.5.13.jar:lib/cdk-extra-1.5.13.jar:lib/cdk-fingerprint-1.5.13.jar:lib/cdk-formula-1.5.13.jar:lib/cdk-fragment-1.5.13.jar:lib/cdk-hash-1.5.13.jar:lib/cdk-inchi-1.5.13.jar:lib/cdk-interfaces-1.5.13.jar:lib/cdk-io-1.5.13.jar:lib/cdk-ioformats-1.5.13.jar:lib/cdk-isomorphism-1.5.13.jar:lib/cdk-jchempaint-26.jar:lib/cdk-legacy-1.5.13.jar:lib/cdk-libiocml-1.5.13.jar:lib/cdk-log4j-1.5.13.jar:lib/cdk-pdb-1.5.13.jar:lib/cdk-pdbcml-1.5.13.jar:lib/cdk-qsar-1.5.13.jar:lib/cdk-qsarmolecular-1.5.13.jar:lib/cdk-qsarprotein-1.5.13.jar:lib/cdk-reaction-1.5.13.jar:lib/cdk-render-1.5.13.jar:lib/cdk-renderawt-1.5.13.jar:lib/cdk-renderbasic-1.5.13.jar:lib/cdk-sdg-1.5.13.jar:lib/cdk-signature-1.5.13.jar:lib/cdk-silent-1.5.13.jar:lib/cdk-smarts-1.5.13.jar:lib/cdk-smiles-1.5.13.jar:lib/cdk-standard-1.5.13.jar:lib/cdk-structgen-1.5.13.jar:lib/cdk-valencycheck-1.5.13.jar:lib/chebiWS-client-2.0.jar:lib/cmlxom-3.1.jar:lib/commons-cli-1.2.jar:lib/commons-codec-1.9.jar:lib/commons-csv-1.2.jar:lib/commons-fileupload-1.3.1.jar:lib/commons-io-2.4.jar:lib/commons-jexl-2.1.1.jar:lib/commons-lang3-3.3.2.jar:lib/commons-logging-1.2.jar:lib/commons-math3-3.1.1.jar:lib/commons-math-2.2.jar:lib/commons-pool-1.6.jar:lib/dnsjava-2.1.1.jar:lib/encoder-1.1.1.jar:lib/enmexcelparser-1.1.0.jar:lib/enmtemplates-1.1.0.jar:lib/euclid-1.0.1.jar:lib/freemarker-2.3.27-incubating.jar:lib/guava-17.0.jar:lib/httpclient-4.5.jar:lib/httpclient-cache-4.2.6.jar:lib/httpcore-4.4.1.jar:lib/httpmime-4.5.jar:lib/i5z-2.0.0.jar:lib/itext-1.4.8.jar:lib/iuclid_5_0-io-2.0.0.jar:lib/iuclid_5_4-io-2.0.0.jar:lib/iuclid_5_5-io-2.0.0.jar:lib/iuclid_5_common-2.0.0.jar:lib/iuclid_5_sections-2.0.0.jar:lib/iuclid_6_2-io-2.0.0.jar:lib/iuclid_6_sections-2.0.0.jar:lib/jackson-annotations-2.4.0.jar:lib/jackson-core-2.4.2.jar:lib/jackson-databind-2.4.2.jar:lib/jackson-module-jsonSchema-2.4.2.jar:lib/jama-1.0.3.jar:lib/java-cup-0.11a.jar:lib/jaxb-api-2.1.jar:lib/jaxb-impl-2.1.11.jar:lib/jaxb-xjc-2.1.6.jar:lib/jaxws-api-2.1.jar:lib/jaxws-rt-2.1.7.jar:lib/jaxws-tools-2.1.3.jar:lib/jcl-over-slf4j-1.7.6.jar:lib/jcommon-1.0.15.jar:lib/jena-arq-2.13.0.jar:lib/jena-core-2.13.0.jar:lib/jena-iri-1.1.2.jar:lib/jfreechart-1.0.12.jar:lib/jgrapht-0.6.0.jar:lib/jnati-core-0.4.jar:lib/jnati-deploy-0.4.jar:lib/jni-inchi-0.8.jar:lib/joda-time-1.6.2.jar:lib/joelib-1.0.jar:lib/jsonld-java-0.5.1.jar:lib/jsslutils-0.5.1.jar:lib/libthrift-0.9.2.jar:lib/log4j-1.2.17.jar:lib/loom-common-1.2.1.jar:lib/loom-i5ws-1.2.1.jar:lib/loom-i6ws-1.2.1.jar:lib/loom-ixws-1.2.1.jar:lib/loom-nm-1.2.1.jar:lib/loom-ops-cli-1.2.1.jar:lib/loom-tb-wiki-1.2.1.jar:lib/mail-1.4.5.jar:lib/mchange-commons-java-0.2.10.jar:lib/mimepull-1.3.jar:lib/modbcum-c-1.0.9.jar:lib/modbcum-e-1.0.9.jar:lib/modbcum-i-1.0.9.jar:lib/modbcum-p-1.0.9.jar:lib/modbcum-q-1.0.9.jar:lib/modbcum-r-1.0.9.jar:lib/mysql-connector-java-5.1.36.jar:lib/opentox-client-3.1.0.jar:lib/opentox-opensso-2.0.0.jar:lib/opsin-core-2.3.1.jar:lib/opsin-inchi-2.3.1.jar:lib/org.osgi.core-4.0.0.jar:lib/org.restlet.ext.crypto-2.0-M6.jar:lib/org.restlet.ext.fileupload-2.0-M6.jar:lib/org.restlet.ext.freemarker-2.0-M6.jar:lib/org.restlet.ext.servlet-2.0-M6.jar:lib/org.restlet.ext.ssl-2.0-M6.jar:lib/org.restlet.ext.xml-2.0-M6.jar:lib/org.restlet-2.0-M6.jar:lib/poi-3.13.jar:lib/poi-ooxml-3.13.jar:lib/poi-ooxml-schemas-3.13.jar:lib/poi-scratchpad-3.13.jar:lib/prefuse-beta-20060220.jar:lib/resolver-20050927.jar:lib/restnet-a-1.1.3.jar:lib/restnet-b-1.1.3.jar:lib/restnet-c-1.1.3.jar:lib/restnet-db-1.1.3.jar:lib/restnet-i-1.1.3.jar:lib/restnet-rdf-1.1.3.jar:lib/restnet-u-1.1.3.jar:lib/restnet-userdb-1.1.3.jar:lib/saaj-api-1.3.jar:lib/saaj-impl-1.3.2.jar:lib/saxon-8.7.jar:lib/saxon-dom-8.7.jar:lib/signatures-1.1.jar:lib/sjsxp-1.0.1.jar:lib/slf4j-api-1.7.12.jar:lib/slf4j-log4j12-1.7.6.jar:lib/slf4j-nop-1.7.12.jar:lib/smartcyp-core-3.0.1.jar:lib/stax2-api-3.1.4.jar:lib/stax-api-1.0.1.jar:lib/stax-api-1.0.jar:lib/stax-ex-1.2.jar:lib/streambuffer-0.9.jar:lib/tagsoup-1.2.jar:lib/t-digest-3.2.jar:lib/toxtree-ames-3.0.2.jar:lib/toxtree-biodegradation-3.0.2.jar:lib/toxtree-bundle-3.0.2.jar:lib/toxtree-core-3.0.2.jar:lib/toxtree-cramer2-3.0.2.jar:lib/toxtree-cramer-3.0.2.jar:lib/toxtree-dnabinding-3.0.2.jar:lib/toxtree-eye-3.0.2.jar:lib/toxtree-kroes-3.0.2.jar:lib/toxtree-mic-3.0.2.jar:lib/toxtree-michaelacceptors-3.0.2.jar:lib/toxtree-mutant-3.0.2.jar:lib/toxtree-proteinbinding-3.0.2.jar:lib/toxtree-sicret-3.0.2.jar:lib/toxtree-skinsensitisation-3.0.2.jar:lib/toxtree-smartcyp-3.0.2.jar:lib/toxtree-verhaar2-3.0.2.jar:lib/toxtree-verhaar-3.0.2.jar:lib/vecmath-1.5.2.jar:lib/weka-stable-3.6.9.jar:lib/woodstox-core-asl-4.4.1.jar:lib/wstx-asl-3.2.3.jar:lib/xalan-2.7.0.jar:lib/xercesImpl-2.11.0.jar:lib/xml-apis-1.4.01.jar:lib/xmlbeans-2.6.0.jar:lib/xom-1.2.5.jar:lib/xpp3-1.1.4c.jar:lib/openchemlib-2021.11.3-SNAPSHOT_2021-11-23-1525.jar:lib/picocli-4.6.1.jar ./src/com/ontochem/CreateLuceneIndex/*java;

jar cfm build.jar Manifest.txt -C classes com

time /usr/lib64/jvm/java-11-openjdk-11/bin/java -jar build.jar -read_Module Ocl -read_Smiles smiles-set.csv -write_IndexDirectory ./indexDirectory
