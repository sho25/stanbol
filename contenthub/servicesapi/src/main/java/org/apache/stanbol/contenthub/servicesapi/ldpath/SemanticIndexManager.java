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
name|contenthub
operator|.
name|servicesapi
operator|.
name|ldpath
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Reader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|at
operator|.
name|newmedialab
operator|.
name|ldpath
operator|.
name|model
operator|.
name|programs
operator|.
name|Program
import|;
end_import

begin_comment
comment|/**  * This interface provides create, retrieve, delete operations for LDPath  * programs to be managed in the scope of Contenthub.  *   * @author anil.sinaci  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|SemanticIndexManager
block|{
comment|/** 	 * Saves LDPath program to persistent storage with given name and 	 * initializes a new Solr core with the same name 	 *  	 * @param programName 	 *            name of the submitted program, also will be name of 	 *            corresponding Solr Core 	 * @param ldPathProgram 	 *            LDPath Program in the form of {@link String} 	 * @throws LDPathException 	 *             is thrown while parsing program and creating Solr Core 	 */
specifier|public
name|void
name|submitProgram
parameter_list|(
name|String
name|programName
parameter_list|,
name|String
name|ldPathProgram
parameter_list|)
throws|throws
name|LDPathException
function_decl|;
comment|/** 	 * Saves LDPath program to persistent storage with given name and 	 * initializes a new Solr core with the same name 	 *  	 * @param programName 	 *            name of the submitted program, also will be name of 	 *            corresponding Solr Core 	 * @param ldPathProgram 	 *            LDPath Program in the form of {@link java.io.Reader} 	 * @throws LDPathException 	 *             is thrown while parsing program and creating Solr Core 	 */
specifier|public
name|void
name|submitProgram
parameter_list|(
name|String
name|programName
parameter_list|,
name|Reader
name|ldPathProgram
parameter_list|)
throws|throws
name|LDPathException
function_decl|;
comment|/** 	 * Checks whether a program-core pair exists with given name or not 	 *  	 * @param programName 	 *            name of the program/core 	 * @return {@link true} if a program with given name exists; {@link false} 	 *         otherwise 	 */
specifier|public
name|boolean
name|isManagedProgram
parameter_list|(
name|String
name|programName
parameter_list|)
function_decl|;
comment|/** 	 * Retrieves the program managed by {@link ProgramManager} with given name 	 *  	 * @param programName 	 *            name of the program that will be retrieved 	 * @return requested program as String, if such program does not exist, 	 *         returns {@link false} 	 */
specifier|public
name|String
name|getProgramByName
parameter_list|(
name|String
name|programName
parameter_list|)
function_decl|;
comment|/** 	 * Retrieves the program managed by {@link ProgramManager} with given 	 name, 	 * parses it, and returns the {@link Progra} 	 * 	 * @param programName 	 * @return 	 * @throws LDPathException 	 */
specifier|public
name|Program
argument_list|<
name|Object
argument_list|>
name|getParsedProgramByName
parameter_list|(
name|String
name|programName
parameter_list|)
function_decl|;
comment|/** 	 * Deletes both the program and the corresponding Solr Core 	 *  	 * @param programName 	 *            name of the program-core pair to be deleted 	 */
specifier|public
name|void
name|deleteProgram
parameter_list|(
name|String
name|programName
parameter_list|)
function_decl|;
comment|/** 	 * Used to retrieve names and programs of all currently managed program-core 	 * pairs 	 *  	 * @return All managed programs as {@link LDProgramCollection} 	 */
specifier|public
name|LDProgramCollection
name|retrieveAllPrograms
parameter_list|()
function_decl|;
comment|/** 	 * This method first tries to obtain the program itself through the given 	 *<code>programName</code> and if the program is obtained it is executed on 	 * the given<code>graph</code>. 	 *  	 * @param programName 	 *            name of the program to be executed 	 * @param contexts 	 *            a {@link Set} of URIs (string representations) that are used 	 *            as starting nodes to execute LDPath program specified by 	 *            {@code programName} on the given {@code program} 	 * @param graph 	 *            a Clerezza graph on which the specified program will be 	 *            executed 	 * @return the {@link Map} containing the results obtained by executing the 	 *         given program on the given graph. Keys of the map corresponds to 	 *         fields in the program and values of the map corresponds to 	 *         results obtained for the field specified in the key. 	 * @throws LDPathException 	 */
specifier|public
name|Map
argument_list|<
name|String
argument_list|,
name|Collection
argument_list|<
name|?
argument_list|>
argument_list|>
name|executeProgram
parameter_list|(
name|String
name|programName
parameter_list|,
name|Set
argument_list|<
name|String
argument_list|>
name|contexts
parameter_list|)
throws|throws
name|LDPathException
function_decl|;
block|}
end_interface

end_unit

