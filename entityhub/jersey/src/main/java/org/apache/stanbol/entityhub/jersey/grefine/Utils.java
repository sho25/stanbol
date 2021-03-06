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
name|entityhub
operator|.
name|jersey
operator|.
name|grefine
package|;
end_package

begin_import
import|import static
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|lang
operator|.
name|StringUtils
operator|.
name|getLevenshteinDistance
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|lang
operator|.
name|StringUtils
import|;
end_import

begin_class
specifier|public
specifier|final
class|class
name|Utils
block|{
comment|/**      * Restrict instantiation      */
specifier|private
name|Utils
parameter_list|()
block|{}
comment|/**      * Compares two strings (after {@link StringUtils#trim(String) trimming}       * by using the Levenshtein's Edit Distance of the two      * strings. Does not return the {@link Integer} number of changes but      *<code>1-(changes/maxStringSizeAfterTrim)</code><p>      * @param s1 the first string      * @param s2 the second string      * @return the distance      * @throws IllegalArgumentException if any of the two parsed strings is NULL      */
specifier|public
specifier|static
name|double
name|levenshtein
parameter_list|(
name|String
name|s1
parameter_list|,
name|String
name|s2
parameter_list|)
block|{
if|if
condition|(
name|s1
operator|==
literal|null
operator|||
name|s2
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"NONE of the parsed String MUST BE NULL!"
argument_list|)
throw|;
block|}
name|s1
operator|=
name|StringUtils
operator|.
name|trim
argument_list|(
name|s1
argument_list|)
expr_stmt|;
name|s2
operator|=
name|StringUtils
operator|.
name|trim
argument_list|(
name|s2
argument_list|)
expr_stmt|;
return|return
name|s1
operator|.
name|isEmpty
argument_list|()
operator|||
name|s2
operator|.
name|isEmpty
argument_list|()
condition|?
literal|0
else|:
literal|1.0
operator|-
operator|(
operator|(
operator|(
name|double
operator|)
name|getLevenshteinDistance
argument_list|(
name|s1
argument_list|,
name|s2
argument_list|)
operator|)
operator|/
operator|(
call|(
name|double
call|)
argument_list|(
name|Math
operator|.
name|max
argument_list|(
name|s1
operator|.
name|length
argument_list|()
argument_list|,
name|s2
operator|.
name|length
argument_list|()
argument_list|)
argument_list|)
operator|)
operator|)
return|;
block|}
block|}
end_class

end_unit

