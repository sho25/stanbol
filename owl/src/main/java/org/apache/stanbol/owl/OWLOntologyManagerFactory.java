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
name|owl
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
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
name|apibinding
operator|.
name|OWLManager
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
name|OWLOntologyIRIMapper
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
name|OWLOntologyManager
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
name|util
operator|.
name|AutoIRIMapper
import|;
end_import

begin_class
specifier|public
class|class
name|OWLOntologyManagerFactory
block|{
comment|/**      *       * @param localDirs      *            . If null or empty, no offline support will be added      * @return      */
specifier|public
specifier|static
name|OWLOntologyManager
name|createOWLOntologyManager
parameter_list|(
name|IRI
index|[]
name|locations
parameter_list|)
block|{
name|OWLOntologyManager
name|mgr
init|=
name|OWLManager
operator|.
name|createOWLOntologyManager
argument_list|()
decl_stmt|;
for|for
control|(
name|OWLOntologyIRIMapper
name|mapper
range|:
name|getMappers
argument_list|(
name|locations
argument_list|)
control|)
block|{
name|mgr
operator|.
name|addIRIMapper
argument_list|(
name|mapper
argument_list|)
expr_stmt|;
block|}
return|return
name|mgr
return|;
block|}
specifier|private
specifier|static
name|List
argument_list|<
name|OWLOntologyIRIMapper
argument_list|>
name|getMappers
parameter_list|(
name|IRI
index|[]
name|dirs
parameter_list|)
block|{
name|List
argument_list|<
name|OWLOntologyIRIMapper
argument_list|>
name|mappers
init|=
operator|new
name|ArrayList
argument_list|<
name|OWLOntologyIRIMapper
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|dirs
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|IRI
name|path
range|:
name|dirs
control|)
block|{
name|File
name|dir
init|=
literal|null
decl_stmt|;
try|try
block|{
name|dir
operator|=
operator|new
name|File
argument_list|(
name|path
operator|.
name|toURI
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
comment|// Keep dir null
block|}
if|if
condition|(
name|dir
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|dir
operator|.
name|isDirectory
argument_list|()
condition|)
name|mappers
operator|.
name|add
argument_list|(
operator|new
name|AutoIRIMapper
argument_list|(
name|dir
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
comment|// We might want to construct other IRI mappers for regular files in the future...
block|}
block|}
block|}
return|return
name|mappers
return|;
block|}
block|}
end_class

end_unit

