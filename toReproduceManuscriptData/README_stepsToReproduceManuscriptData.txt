#######################################################################################
#######################################################################################
########## input data to reproduce part of the table 4 ################################
#######################################################################################
#######################################################################################
The input files (smarts-set.txt, smiles-set.csv) to reproduce part of the table 4 are placed in the folder named "toReproduceManuscriptData/input"
1. PFAS hits from the 818,280 compound (CORE) dataset is saved as smiles-set.csv
2. smarts definition:  C(F)(F), C(F)(F)C(F), C(*)(F)(F)C(F)(*)(*) is saved as smarts-set.txt

#######################################################################################
#######################################################################################
########## OpenChemLib (OCL) / Chemistry Development Kit (CDK) ########################
#######################################################################################
#######################################################################################

Table 4: Efficacy of different fingerprints in pre-selection for substructure searching
Definition A: FP FP+ABAS
OCL 58,132 27,287
CDK 45,632 27,287

Definition B: FP FP+ABAS
OCL 23,830 4,192
CDK 16,922 4,192

Definition C: FP FP+ABAS
OCL 9,043 3,507
CDK 16,922 3,507

##################################
// STEP 1: clone the git repository
##################################
git clone https://github.com/ontochem/PFAS.git

##################################
// STEP 2:  create lucene index
##################################
cd Fingerprint+Abas/OCL_CDK/ChemistrySearchCLI
the external libraries necessary for compiling the java code is mentioned in the Manifest.txt
compile and execute the java program using the exe.sh bash script

for example: to reproduce the table 4 values for definition A,B and C using Chemistry Development Kit (CDK), edit the exe.sh script and choose the right module as shown below.
time /usr/lib64/jvm/java-11-openjdk-11/bin/java -jar build.jar -read_Module Cdk -read_Smiles smiles-set.csv -read_IndexDirectory ./indexDirectory_Cdk

for example: to reproduce the table 4 values for definition A,B and C using OpenChemLib (OCL), edit the exe.sh script and choose the right module as shown below.
time /usr/lib64/jvm/java-11-openjdk-11/bin/java -jar build.jar -read_Module Ocl -read_Smiles smiles-set.csv -read_IndexDirectory ./indexDirectory_Ocl

##################################
// STEP 3: search lucene index
##################################
cd Fingerprint+Abas/OCL_CDK/ChemistrySearchAPI
the external libraries necessary for compiling the java code is mentioned in the Manifest.txt
compile and execute the java program using the exe.sh bash script

for example: to reproduce the table 4 values for definition A,B and C using Chemistry Development Kit (CDK), edit the exe.sh script and choose the right module as shown below. Edit the smarts-set.txt with the definition mentioned in the paper or as above

time /usr/lib64/jvm/java-11-openjdk-11/bin/java -jar build.jar -read_Module Cdk -read_Smarts smarts-set.txt  -write_Output output.txt -read_IndexDirectory ../ChemistrySearchCLI/indexDirectory_Cdk

for example: to reproduce the table 4 values for definition A,B and C using OpenChemLib (OCL), edit the exe.sh script and choose the right module as shown below. Edit the smarts-set.txt with the definition mentioned in the paper or as above
time /usr/lib64/jvm/java-11-openjdk-11/bin/java -jar build.jar -read_Module Ocl -read_Smarts smarts-set.txt  -write_Output output.txt -read_IndexDirectory ../ChemistrySearchCLI/indexDirectory_Ocl

##################################
// STEP 4: the output is stored in the default file "output.txt". The output format is as follow
##################################
the output file is formated as follows: This is the output using the module "Cdk"
#hits FP:45632 C(F)(F) // here FP stands for fingerprint 
<a list of the compound Ids are provided following the above line for details refer the output file>
#hits SSS:27287 C(F)(F) // here SSS stands for substructure search or atom by atom search (Abas)
<a list of the compound Ids are provided following the above line for details refer the output file>
#hits FP:16922 C(F)(F)C(F)// here FP stands for fingerprint
<a list of the compound Ids are provided following the above line for details refer the output file>
#hits SSS:4192 C(F)(F)C(F)// here SSS stands for substructure search or atom by atom search (Abas)
<a list of the compound Ids are provided following the above line for details refer the output file>
#hits FP:16922 C(*)(F)(F)C(F)(*)(*)// here FP stands for fingerprint
<a list of the compound Ids are provided following the above line for details refer the output file>
#hits SSS:3507 C(*)(F)(F)C(F)(*)(*)// here SSS stands for substructure search or atom by atom search (Abas)
<a list of the compound Ids are provided following the above line for details refer the output file>

##################################
// STEP 5: final results
##################################
to get the values mentioned in the table 4, simply type "grep hits output.txt" in the terminal

module: CDK
#hits FP:45632 C(F)(F)
#hits SSS:27287 C(F)(F)
#hits FP:16922 C(F)(F)C(F)
#hits SSS:4192 C(F)(F)C(F)
#hits FP:16922 C(*)(F)(F)C(F)(*)(*)
#hits SSS:3507 C(*)(F)(F)C(F)(*)(*)

module: OCL
#hits FP:58132 C(F)(F)
#hits SSS:27287 C(F)(F)
#hits FP:23830 C(F)(F)C(F)
#hits SSS:4192 C(F)(F)C(F)
#hits FP:9043 C(*)(F)(F)C(F)(*)(*)
#hits SSS:3507 C(*)(F)(F)C(F)(*)(*)

#######################################################################################
#######################################################################################
########################## RDKIT ######################################################
#######################################################################################
#######################################################################################

Table 4: Efficacy of different fingerprints in pre-selection for substructure searching
Definition C FP+ABAS
RDKit 3,502

##################################
STEP 1: set the rdkit environment and execute the code. Edit the main.py to read the smiles-set.csv. Edit the appropriate smarts query in the main.py
##################################
cd Fingerprint+Abas/RDKit
conda activate my-rdkit-env
python main.py > output.txt 

##################################
STEP 2: output format: if the query substructure is embedded in the reference compound then the compound is flagged 
as true or else false. 
##################################
grep true output.txt|wc -l gives 3,502


