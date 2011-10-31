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
name|commons
operator|.
name|solr
operator|.
name|web
operator|.
name|utils
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Comparator
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|framework
operator|.
name|Constants
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|framework
operator|.
name|ServiceReference
import|;
end_import

begin_comment
comment|/**  * Compares {@link ServiceReference}s based on the {@link Constants#SERVICE_RANKING}  * property value. Highest Rank will be listed first.  */
end_comment

begin_class
specifier|public
class|class
name|ServiceReferenceRankingComparator
implements|implements
name|Comparator
argument_list|<
name|ServiceReference
argument_list|>
block|{
comment|/**      * Singelton instance      */
specifier|public
specifier|static
name|ServiceReferenceRankingComparator
name|INSTANCE
init|=
operator|new
name|ServiceReferenceRankingComparator
argument_list|()
decl_stmt|;
annotation|@
name|Override
specifier|public
name|int
name|compare
parameter_list|(
name|ServiceReference
name|r1
parameter_list|,
name|ServiceReference
name|r2
parameter_list|)
block|{
name|int
name|ranking1
decl_stmt|,
name|ranking2
decl_stmt|;
name|Integer
name|tmp
init|=
operator|(
name|Integer
operator|)
name|r1
operator|.
name|getProperty
argument_list|(
name|Constants
operator|.
name|SERVICE_RANKING
argument_list|)
decl_stmt|;
name|ranking1
operator|=
name|tmp
operator|!=
literal|null
condition|?
name|tmp
else|:
literal|0
expr_stmt|;
name|tmp
operator|=
operator|(
name|Integer
operator|)
name|r2
operator|.
name|getProperty
argument_list|(
name|Constants
operator|.
name|SERVICE_RANKING
argument_list|)
expr_stmt|;
name|ranking2
operator|=
name|tmp
operator|!=
literal|null
condition|?
name|tmp
else|:
literal|0
expr_stmt|;
return|return
name|ranking2
operator|-
name|ranking1
return|;
comment|//highest rank first
block|}
block|}
end_class

end_unit

