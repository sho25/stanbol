begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* * Licensed to the Apache Software Foundation (ASF) under one or more * contributor license agreements.  See the NOTICE file distributed with * this work for additional information regarding copyright ownership. * The ASF licenses this file to You under the Apache License, Version 2.0 * (the "License"); you may not use this file except in compliance with * the License.  You may obtain a copy of the License at * *     http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|rules
operator|.
name|manager
operator|.
name|atoms
package|;
end_package

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|rules
operator|.
name|base
operator|.
name|api
operator|.
name|SPARQLObject
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|rules
operator|.
name|manager
operator|.
name|SPARQLFunction
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
name|OWLDataFactory
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
name|SWRLAtom
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|rdf
operator|.
name|model
operator|.
name|Model
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|rdf
operator|.
name|model
operator|.
name|Resource
import|;
end_import

begin_class
specifier|public
class|class
name|PropStringAtom
extends|extends
name|StringFunctionAtom
block|{
specifier|private
name|StringFunctionAtom
name|namespaceArg
decl_stmt|;
specifier|private
name|StringFunctionAtom
name|labelArg
decl_stmt|;
specifier|public
name|PropStringAtom
parameter_list|(
name|StringFunctionAtom
name|namespaceArg
parameter_list|,
name|StringFunctionAtom
name|labelArg
parameter_list|)
block|{
name|this
operator|.
name|namespaceArg
operator|=
name|namespaceArg
expr_stmt|;
name|this
operator|.
name|labelArg
operator|=
name|labelArg
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Resource
name|toSWRL
parameter_list|(
name|Model
name|model
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|SPARQLObject
name|toSPARQL
parameter_list|()
block|{
name|String
name|ns
init|=
name|namespaceArg
operator|.
name|toSPARQL
argument_list|()
operator|.
name|getObject
argument_list|()
decl_stmt|;
name|String
name|label
init|=
name|labelArg
operator|.
name|toSPARQL
argument_list|()
operator|.
name|getObject
argument_list|()
decl_stmt|;
name|String
name|sparql
init|=
literal|"<http://www.stlab.istc.cnr.it/semion/function#propString>("
operator|+
name|ns
operator|+
literal|", "
operator|+
name|label
operator|+
literal|")"
decl_stmt|;
return|return
operator|new
name|SPARQLFunction
argument_list|(
name|sparql
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|SWRLAtom
name|toSWRL
parameter_list|(
name|OWLDataFactory
name|factory
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toKReSSyntax
parameter_list|()
block|{
name|String
name|ns
init|=
name|namespaceArg
operator|.
name|toKReSSyntax
argument_list|()
decl_stmt|;
name|String
name|label
init|=
name|labelArg
operator|.
name|toKReSSyntax
argument_list|()
decl_stmt|;
name|String
name|kReS
init|=
literal|"propString("
operator|+
name|ns
operator|+
literal|", "
operator|+
name|label
operator|+
literal|")"
decl_stmt|;
return|return
name|kReS
return|;
block|}
block|}
end_class

end_unit

