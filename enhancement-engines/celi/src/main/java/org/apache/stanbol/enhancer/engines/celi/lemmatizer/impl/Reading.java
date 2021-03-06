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
name|enhancer
operator|.
name|engines
operator|.
name|celi
operator|.
name|lemmatizer
operator|.
name|impl
package|;
end_package

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
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_class
specifier|public
class|class
name|Reading
block|{
name|String
name|lemma
decl_stmt|;
name|Hashtable
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|lexicalFeatures
decl_stmt|;
specifier|public
name|Reading
parameter_list|(
name|String
name|lemma
parameter_list|,
name|Hashtable
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|lexicalFeatures
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|this
operator|.
name|lemma
operator|=
name|lemma
expr_stmt|;
name|this
operator|.
name|lexicalFeatures
operator|=
name|lexicalFeatures
expr_stmt|;
block|}
specifier|public
name|String
name|getLemma
parameter_list|()
block|{
return|return
name|lemma
return|;
block|}
specifier|public
name|void
name|setLemma
parameter_list|(
name|String
name|lemma
parameter_list|)
block|{
name|this
operator|.
name|lemma
operator|=
name|lemma
expr_stmt|;
block|}
specifier|public
name|Hashtable
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|getLexicalFeatures
parameter_list|()
block|{
return|return
name|lexicalFeatures
return|;
block|}
specifier|public
name|void
name|setLexicalFeatures
parameter_list|(
name|Hashtable
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|lexicalFeatures
parameter_list|)
block|{
name|this
operator|.
name|lexicalFeatures
operator|=
name|lexicalFeatures
expr_stmt|;
block|}
block|}
end_class

end_unit

