begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *     http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|ontologymanager
operator|.
name|servicesapi
operator|.
name|collector
package|;
end_package

begin_comment
comment|/**  * The policies that determine how OntoNet should handle OWL import statements and/or the imported ontologies  * (i.e. import targets) when performing operations such as load/export of ontologies, and rewriting of import  * statements.  *   * @author alexdma  *   */
end_comment

begin_enum
specifier|public
enum|enum
name|ImportManagementPolicy
block|{
comment|/**      * The root ontology contains all the import statements that point to (recursively) imported ontologies,      * which instead are cleared of all import statements. Can be used to minimize the number of statements      * and remove cycles.      */
name|FLATTEN
block|,
comment|/**      * Remove all import statements from the root ontology's import closure, and recursively copy all the      * axioms from imported ontologies into the root one.      */
name|MERGE
block|,
comment|/**      * Keep the import structure as it is. Note that the import targets can still be rewritten even by      * following this policy.      */
name|PRESERVE
block|;  }
end_enum

end_unit

