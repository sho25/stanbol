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
name|reengineer
operator|.
name|base
operator|.
name|api
package|;
end_package

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
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
import|;
end_import

begin_import
import|import
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|OWLOntology
import|;
end_import

begin_comment
comment|/**  *   * The {@code ReengineerManager} is responsible of the coordination of all the tasks performed by Stanbol  * Reengineer.  *   * @author andrea.nuzzolese  *   */
end_comment

begin_interface
specifier|public
interface|interface
name|ReengineerManager
block|{
comment|/**      * The {@link ReengineerManager} can add a new reengineer to the list of available reengineers. This is      * performed through the method {@cod bindReengineer}.      *       * @param semionReengineer      *            {@link Reengineer}      * @return true if the reengineer is bound, false otherwise      */
name|boolean
name|bindReengineer
parameter_list|(
name|Reengineer
name|semionReengineer
parameter_list|)
function_decl|;
comment|/**      * Gets the number of active reengineers.      *       * @return the number of active reengineers.      */
name|int
name|countReengineers
parameter_list|()
function_decl|;
comment|/**      * Gets the active reengineers of KReS.      *       * @return the {@link Collection< Reengineer>} of active reengineers.      */
name|Collection
argument_list|<
name|Reengineer
argument_list|>
name|listReengineers
parameter_list|()
function_decl|;
comment|// /**
comment|// * The {@link ReengineerManager} can register a single instance of {@link SemionRefactorer}.
comment|// *
comment|// * @param semionRefactorer {@link SemionRefactorer}
comment|// */
comment|// public void registerRefactorer(SemionRefactorer semionRefactorer);
comment|//
comment|// /**
comment|// * Unregisters the instance of {@link SemionRefactorer}. After the call of this method Semion has no
comment|// refactorer.
comment|// */
comment|// public void unregisterRefactorer();
comment|//
comment|// /**
comment|// * The instance of the refactored is returned back if it exists.
comment|// *
comment|// * @return the active {@link SemionRefactorer}
comment|// */
comment|// public SemionRefactorer getRegisteredRefactorer();
name|OWLOntology
name|performDataReengineering
parameter_list|(
name|String
name|graphNS
parameter_list|,
name|IRI
name|outputIRI
parameter_list|,
name|DataSource
name|dataSource
parameter_list|,
name|OWLOntology
name|schemaOntology
parameter_list|)
throws|throws
name|ReengineeringException
function_decl|;
name|OWLOntology
name|performReengineering
parameter_list|(
name|String
name|graphNS
parameter_list|,
name|IRI
name|outputIRI
parameter_list|,
name|DataSource
name|dataSource
parameter_list|)
throws|throws
name|ReengineeringException
function_decl|;
name|OWLOntology
name|performSchemaReengineering
parameter_list|(
name|String
name|graphNS
parameter_list|,
name|IRI
name|outputIRI
parameter_list|,
name|DataSource
name|dataSource
parameter_list|)
throws|throws
name|ReengineeringException
function_decl|;
comment|/**      * The {@link ReengineerManager} can remove a reengineer from the list of available reengineers. This is      * performed through the method {@cod unbindReengineer}.      *       * @param reenginnerType      *            {@code int}      * @return true if the reengineer is unbound, false otherwise      */
name|boolean
name|unbindReengineer
parameter_list|(
name|int
name|reenginnerType
parameter_list|)
function_decl|;
comment|/**      * The {@link ReengineerManager} can remove a reengineer from the list of available reengineers. This is      * performed through the method {@cod unbindReengineer}.      *       * @param semionReengineer      *            {@link Reengineer}      * @return true if the reengineer is unbound, false otherwise      */
name|boolean
name|unbindReengineer
parameter_list|(
name|Reengineer
name|semionReengineer
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

