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
name|contenthub
operator|.
name|core
operator|.
name|search
operator|.
name|execution
package|;
end_package

begin_import
import|import
name|java
operator|.
name|text
operator|.
name|DecimalFormat
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
name|contenthub
operator|.
name|servicesapi
operator|.
name|search
operator|.
name|execution
operator|.
name|Scored
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
name|contenthub
operator|.
name|servicesapi
operator|.
name|search
operator|.
name|vocabulary
operator|.
name|SearchVocabulary
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
name|enhanced
operator|.
name|EnhGraph
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
name|graph
operator|.
name|Node
import|;
end_import

begin_comment
comment|/**  *   * @author cihan  *   */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|AbstractScored
extends|extends
name|AbstractWeighted
implements|implements
name|Scored
block|{
specifier|private
specifier|static
specifier|final
name|DecimalFormat
name|df
init|=
operator|new
name|DecimalFormat
argument_list|(
literal|"#.###"
argument_list|)
decl_stmt|;
name|AbstractScored
parameter_list|(
name|Node
name|n
parameter_list|,
name|EnhGraph
name|g
parameter_list|,
name|Double
name|weight
parameter_list|,
name|Double
name|score
parameter_list|)
block|{
name|super
argument_list|(
name|n
argument_list|,
name|g
argument_list|,
name|weight
argument_list|)
expr_stmt|;
name|checkScoreRange
argument_list|(
name|score
argument_list|)
expr_stmt|;
if|if
condition|(
name|this
operator|.
name|hasProperty
argument_list|(
name|SearchVocabulary
operator|.
name|SCORE
argument_list|)
condition|)
block|{
name|updateScore
argument_list|(
name|score
argument_list|,
name|weight
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// setScoreLiteral(weight * score);
name|setScoreLiteral
argument_list|(
name|score
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|double
name|getScore
parameter_list|()
block|{
return|return
name|getScoreLiteral
argument_list|()
operator|/
name|getWeight
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getScoreString
parameter_list|()
block|{
return|return
name|df
operator|.
name|format
argument_list|(
name|getScore
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
specifier|final
name|void
name|updateScore
parameter_list|(
name|Double
name|score
parameter_list|,
name|Double
name|weight
parameter_list|)
block|{
name|checkScoreRange
argument_list|(
name|score
argument_list|)
expr_stmt|;
name|double
name|currentScore
init|=
name|getScoreLiteral
argument_list|()
decl_stmt|;
name|currentScore
operator|+=
name|score
operator|*
name|weight
expr_stmt|;
name|addWeight
argument_list|(
name|weight
argument_list|)
expr_stmt|;
name|setScoreLiteral
argument_list|(
name|currentScore
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|checkScoreRange
parameter_list|(
name|Double
name|score
parameter_list|)
block|{
if|if
condition|(
name|score
operator|>
literal|1
operator|||
name|score
operator|<
literal|0
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Score can not be less than 0 or greater than 1.0"
argument_list|)
throw|;
block|}
block|}
specifier|private
name|double
name|getScoreLiteral
parameter_list|()
block|{
return|return
name|this
operator|.
name|getPropertyValue
argument_list|(
name|SearchVocabulary
operator|.
name|SCORE
argument_list|)
operator|.
name|asLiteral
argument_list|()
operator|.
name|getDouble
argument_list|()
return|;
block|}
specifier|private
name|void
name|setScoreLiteral
parameter_list|(
name|double
name|score
parameter_list|)
block|{
comment|// First remove any assertions
name|this
operator|.
name|removeAll
argument_list|(
name|SearchVocabulary
operator|.
name|SCORE
argument_list|)
expr_stmt|;
comment|// Then add current value
name|this
operator|.
name|addLiteral
argument_list|(
name|SearchVocabulary
operator|.
name|SCORE
argument_list|,
name|score
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
