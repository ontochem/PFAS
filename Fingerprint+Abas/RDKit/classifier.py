#*
# Copyright 
# OntoChem GmbH (main office)
# Blücherstrasse 24
# 06120 Halle (Saale)
# Germany
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
#*
import rdkit
from rdkit import Chem
from molvs import Standardizer
from rdkit import RDLogger
from rdkit import DataStructs
from rdkit.Chem import AllChem

def getFp(smiles_Array, smarts_input):
    fpHits = 0;
    for i in range(len(smiles_Array)):
        try:
            s = Standardizer();
            smiles_input = smiles_Array[i];
            targetMolecule = Chem.MolFromSmiles(smiles_input);
            stdTargetMolecule = s.standardize(targetMolecule);
            fragmentMolecule = Chem.MolFromSmarts(smarts_input);
            stdFragmentMolecule = s.standardize(fragmentMolecule);
            mfps = Chem.PatternFingerprint(stdTargetMolecule);
            qfps = Chem.PatternFingerprint(stdFragmentMolecule, 2048);
            if DataStructs.AllProbeBitsMatch(qfps, mfps):
                fpHits += 1;
        except:
            print("wrongSmiles");
    return fpHits;

def classify(smiles_Array, smarts_input):
    for i in range(len(smiles_Array)):
        try:
            s = Standardizer();
            smiles_input = smiles_Array[i];
            targetMolecule = Chem.MolFromSmiles(smiles_input);
            stdTargetMolecule = s.standardize(targetMolecule);
            fragmentMolecule = Chem.MolFromSmarts(smarts_input);
            stdFragmentMolecule = s.standardize(fragmentMolecule);
            mfps = Chem.PatternFingerprint(stdTargetMolecule);
            qfps = Chem.PatternFingerprint(stdFragmentMolecule, 2048);
            if DataStructs.AllProbeBitsMatch(qfps, mfps):
                found = len(stdTargetMolecule.GetSubstructMatches(stdFragmentMolecule));
                RDLogger.DisableLog('rdApp.*')
                if(found != 0):
                    print(smiles_input + " isFragmentInMolecule:true ");
                else:
                    print(smiles_input + " isFragmentInMolecule:false ");
        except:
            print(smiles_input + " wrongSmiles ");

