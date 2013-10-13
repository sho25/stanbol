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
name|reasoners
operator|.
name|jena
package|;
end_package

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertTrue
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
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|reasoners
operator|.
name|jena
operator|.
name|filters
operator|.
name|PropertyFilter
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Test
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
name|rdf
operator|.
name|model
operator|.
name|Statement
import|;
end_import

begin_comment
comment|/**  * This class tests the PropertyFilter  */
end_comment

begin_class
specifier|public
class|class
name|PropertyFilterTest
block|{
specifier|private
specifier|static
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|PropertyFilterTest
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|Test
specifier|public
name|void
name|test
parameter_list|()
block|{
name|PropertyFilter
name|filter
init|=
operator|new
name|PropertyFilter
argument_list|(
name|TestData
operator|.
name|foaf_firstname
argument_list|)
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
literal|"Testing the {} class"
argument_list|,
name|filter
operator|.
name|getClass
argument_list|()
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|Statement
argument_list|>
name|output
init|=
name|TestData
operator|.
name|alexdma
operator|.
name|getModel
argument_list|()
operator|.
name|listStatements
argument_list|()
operator|.
name|filterKeep
argument_list|(
name|filter
argument_list|)
operator|.
name|toSet
argument_list|()
decl_stmt|;
for|for
control|(
name|Statement
name|statement
range|:
name|output
control|)
block|{
name|assertTrue
argument_list|(
name|statement
operator|.
name|getPredicate
argument_list|()
operator|.
name|equals
argument_list|(
name|TestData
operator|.
name|foaf_firstname
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

