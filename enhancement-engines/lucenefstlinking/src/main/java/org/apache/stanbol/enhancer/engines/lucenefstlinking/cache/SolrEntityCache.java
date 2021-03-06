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
name|enhancer
operator|.
name|engines
operator|.
name|lucenefstlinking
operator|.
name|cache
package|;
end_package

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|lucene
operator|.
name|document
operator|.
name|Document
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|solr
operator|.
name|search
operator|.
name|SolrCache
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

begin_comment
comment|/**  * Implementation of the {@link EntityCache} interface by using the  * {@link SolrCache} API.  *   * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|SolrEntityCache
implements|implements
name|EntityCache
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
specifier|final
name|SolrCache
argument_list|<
name|Integer
argument_list|,
name|Document
argument_list|>
name|cache
decl_stmt|;
specifier|private
specifier|final
name|Object
name|version
decl_stmt|;
specifier|private
name|boolean
name|closed
decl_stmt|;
specifier|public
name|SolrEntityCache
parameter_list|(
name|Object
name|version
parameter_list|,
name|SolrCache
argument_list|<
name|Integer
argument_list|,
name|Document
argument_list|>
name|cache
parameter_list|)
block|{
name|log
operator|.
name|debug
argument_list|(
literal|"> create {} for version {}"
argument_list|,
name|getClass
argument_list|()
operator|.
name|getSimpleName
argument_list|()
argument_list|,
name|version
argument_list|)
expr_stmt|;
name|this
operator|.
name|cache
operator|=
name|cache
expr_stmt|;
name|this
operator|.
name|version
operator|=
name|version
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|" - initial Size: {}"
argument_list|,
name|cache
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|" - description: {}"
argument_list|,
name|cache
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
name|log
operator|.
name|debug
argument_list|(
literal|" - statistics: {}"
argument_list|,
name|cache
operator|.
name|getStatistics
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Object
name|getVersion
parameter_list|()
block|{
return|return
name|version
return|;
block|}
annotation|@
name|Override
specifier|public
name|Document
name|get
parameter_list|(
name|Integer
name|docId
parameter_list|)
block|{
return|return
operator|!
name|closed
condition|?
name|cache
operator|.
name|get
argument_list|(
name|docId
argument_list|)
else|:
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|cache
parameter_list|(
name|Integer
name|docId
parameter_list|,
name|Document
name|doc
parameter_list|)
block|{
if|if
condition|(
operator|!
name|closed
condition|)
block|{
name|log
operator|.
name|trace
argument_list|(
literal|" - cache id:{} | {}"
argument_list|,
name|docId
argument_list|,
name|doc
argument_list|)
expr_stmt|;
name|cache
operator|.
name|put
argument_list|(
name|docId
argument_list|,
name|doc
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|int
name|size
parameter_list|()
block|{
return|return
name|cache
operator|.
name|size
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|printStatistics
parameter_list|()
block|{
return|return
name|cache
operator|.
name|getStatistics
argument_list|()
operator|.
name|toString
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|cache
operator|.
name|getDescription
argument_list|()
return|;
block|}
name|void
name|close
parameter_list|()
block|{
name|log
operator|.
name|debug
argument_list|(
literal|" ... close EntityCache for version {} (size: {} | description: {})"
argument_list|,
operator|new
name|Object
index|[]
block|{
name|version
block|,
name|cache
operator|.
name|size
argument_list|()
block|,
name|cache
operator|.
name|getDescription
argument_list|()
block|}
argument_list|)
expr_stmt|;
name|closed
operator|=
literal|true
expr_stmt|;
name|cache
operator|.
name|clear
argument_list|()
expr_stmt|;
name|cache
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

