begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright 2013 The Apache Software Foundation.  *  * Licensed under the Apache License, Version 2.0 (the "License");  * you may not use this file except in compliance with the License.  * You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|commons
operator|.
name|web
operator|.
name|sparql
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
name|commons
operator|.
name|web
operator|.
name|base
operator|.
name|NavigationLink
import|;
end_import

begin_comment
comment|/**  *  */
end_comment

begin_class
specifier|public
class|class
name|SparqlMenuItem
extends|extends
name|NavigationLink
block|{
specifier|private
specifier|static
specifier|final
name|String
name|htmlDescription
init|=
literal|"This is the<strong>SPARQL endpoint</strong> for the Stanbol store."
operator|+
literal|"<a href=\"http://en.wikipedia.org/wiki/Sparql\">SPARQL</a> is the"
operator|+
literal|"standard query language the most commonly used to provide interactive"
operator|+
literal|"access to semantic knowledge bases."
decl_stmt|;
specifier|public
name|SparqlMenuItem
parameter_list|()
block|{
name|super
argument_list|(
literal|"sparql"
argument_list|,
literal|"/sparql"
argument_list|,
name|htmlDescription
argument_list|,
literal|50
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

