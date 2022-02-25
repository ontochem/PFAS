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
from rdkit import Chem
from rdkit import RDLogger
from classifier import classify
from rdkit.Chem.MolStandardize import rdMolStandardize

f = open('./input','r+')
smiles_Array = [line for line in f.readlines()]
f.close()
smarts_input = 'C(*)(*)(F)C(*)(F)(F)'

classify(smiles_Array,smarts_input)
