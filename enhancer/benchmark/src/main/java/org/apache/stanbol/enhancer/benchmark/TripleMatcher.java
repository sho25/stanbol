begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements. See the NOTICE file distributed with this  * work for additional information regarding copyright ownership. The ASF  * licenses this file to You under the Apache License, Version 2.0 (the  * "License"); you may not use this file except in compliance with the License.  * You may obtain a copy of the License at  *  * http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT  * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the  * License for the specific language governing permissions and limitations under  * the License.  */
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
name|benchmark
package|;
end_package

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|Triple
import|;
end_import

begin_comment
comment|/** TripleMatcher is used to count how many Triples  *  match a given statement in the benchmark tool.  *    *  TripleMatchers are usually parsed from expressions like  *<pre>  *  http://somePredicate URI http://someURI  *</pre>  *  or  *<pre>  *  http://somePredicate REGEXP someRegularExpression  *</pre>  *    *  Which look for triples which have the specified predicate  *  and an Object that matches the rest of the expression using  *  the specified matching operator (URI, REGEXP etc).  *    *  The parsing is not part of this interface, it's an implementation  *  concern.  */
end_comment

begin_interface
specifier|public
interface|interface
name|TripleMatcher
block|{
comment|/** True if this matches suppplied Triple */
name|boolean
name|matches
parameter_list|(
name|Triple
name|t
parameter_list|)
function_decl|;
comment|/** Get the expression used to build this matcher */
name|String
name|getExpression
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

