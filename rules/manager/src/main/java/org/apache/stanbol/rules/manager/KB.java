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
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|OutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Hashtable
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
name|base
operator|.
name|api
operator|.
name|Rule
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
name|base
operator|.
name|api
operator|.
name|util
operator|.
name|RuleList
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
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
name|ontology
operator|.
name|OntModel
import|;
end_import

begin_comment
comment|/**  *   * FIXME  * Missing description  *  */
end_comment

begin_class
specifier|public
class|class
name|KB
block|{
specifier|private
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|getClass
argument_list|()
argument_list|)
decl_stmt|;
specifier|private
name|Hashtable
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|prefixes
decl_stmt|;
comment|/** 	 * FIXME Why is this here? 	 */
specifier|private
name|Hashtable
argument_list|<
name|String
argument_list|,
name|OntModel
argument_list|>
name|ontologies
decl_stmt|;
specifier|private
name|RuleList
name|kReSRuleList
decl_stmt|;
specifier|public
name|KB
parameter_list|()
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"Setting up a KReSKB"
argument_list|)
expr_stmt|;
name|prefixes
operator|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
expr_stmt|;
name|prefixes
operator|.
name|put
argument_list|(
literal|"var"
argument_list|,
literal|"http://kres.iks-project.eu/ontology/meta/variables#"
argument_list|)
expr_stmt|;
name|kReSRuleList
operator|=
operator|new
name|RuleList
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|addPrefix
parameter_list|(
name|String
name|prefixString
parameter_list|,
name|String
name|prefixURI
parameter_list|)
block|{
name|prefixes
operator|.
name|put
argument_list|(
name|prefixString
argument_list|,
name|prefixURI
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getPrefixURI
parameter_list|(
name|String
name|prefixString
parameter_list|)
block|{
return|return
name|prefixes
operator|.
name|get
argument_list|(
name|prefixString
argument_list|)
return|;
block|}
specifier|public
name|void
name|addRule
parameter_list|(
name|Rule
name|kReSRule
parameter_list|)
block|{
name|kReSRuleList
operator|.
name|add
argument_list|(
name|kReSRule
argument_list|)
expr_stmt|;
block|}
specifier|public
name|RuleList
name|getkReSRuleList
parameter_list|()
block|{
return|return
name|kReSRuleList
return|;
block|}
specifier|public
name|String
name|toSPARQL
parameter_list|()
block|{
name|String
name|sparql
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|kReSRuleList
operator|!=
literal|null
condition|)
block|{
name|boolean
name|firstIteration
init|=
literal|true
decl_stmt|;
for|for
control|(
name|Rule
name|kReSRule
range|:
name|kReSRuleList
control|)
block|{
if|if
condition|(
name|firstIteration
condition|)
block|{
name|firstIteration
operator|=
literal|false
expr_stmt|;
block|}
else|else
block|{
name|sparql
operator|+=
literal|" . "
expr_stmt|;
block|}
name|sparql
operator|+=
name|kReSRule
operator|.
name|toSPARQL
argument_list|()
expr_stmt|;
block|}
block|}
return|return
name|sparql
return|;
block|}
specifier|public
name|void
name|write
parameter_list|(
name|OutputStream
name|outputStream
parameter_list|)
throws|throws
name|IOException
block|{
name|boolean
name|firstIt
init|=
literal|true
decl_stmt|;
for|for
control|(
name|Rule
name|kReSRule
range|:
name|kReSRuleList
control|)
block|{
name|String
name|rule
decl_stmt|;
if|if
condition|(
name|firstIt
condition|)
block|{
name|rule
operator|=
name|kReSRule
operator|.
name|toKReSSyntax
argument_list|()
expr_stmt|;
name|firstIt
operator|=
literal|false
expr_stmt|;
block|}
else|else
block|{
name|rule
operator|=
literal|" . "
operator|+
name|System
operator|.
name|getProperty
argument_list|(
literal|"line.separator"
argument_list|)
operator|+
name|kReSRule
operator|.
name|toKReSSyntax
argument_list|()
expr_stmt|;
block|}
name|outputStream
operator|.
name|write
argument_list|(
name|rule
operator|.
name|getBytes
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|outputStream
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|write
parameter_list|(
name|FileWriter
name|fileWriter
parameter_list|)
throws|throws
name|IOException
block|{
name|boolean
name|write
init|=
literal|true
decl_stmt|;
for|for
control|(
name|Rule
name|kReSRule
range|:
name|kReSRuleList
control|)
block|{
if|if
condition|(
name|write
condition|)
block|{
name|fileWriter
operator|.
name|write
argument_list|(
name|kReSRule
operator|.
name|toKReSSyntax
argument_list|()
argument_list|)
expr_stmt|;
name|write
operator|=
literal|false
expr_stmt|;
block|}
else|else
block|{
name|fileWriter
operator|.
name|write
argument_list|(
literal|" . "
operator|+
name|System
operator|.
name|getProperty
argument_list|(
literal|"line.separator"
argument_list|)
operator|+
name|kReSRule
operator|.
name|toKReSSyntax
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|fileWriter
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

