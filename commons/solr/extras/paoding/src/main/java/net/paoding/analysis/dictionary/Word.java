begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* * Licensed to the Apache Software Foundation (ASF) under one or more * contributor license agreements.  See the NOTICE file distributed with * this work for additional information regarding copyright ownership. * The ASF licenses this file to You under the Apache License, Version 2.0 * (the "License"); you may not use this file except in compliance with * the License.  You may obtain a copy of the License at * *     http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */
end_comment

begin_package
package|package
name|net
operator|.
name|paoding
operator|.
name|analysis
operator|.
name|dictionary
package|;
end_package

begin_class
specifier|public
class|class
name|Word
implements|implements
name|Comparable
implements|,
name|CharSequence
block|{
specifier|public
specifier|static
specifier|final
name|int
name|DEFAUL
init|=
literal|0
decl_stmt|;
specifier|private
name|String
name|text
decl_stmt|;
specifier|private
name|int
name|modifiers
init|=
name|DEFAUL
decl_stmt|;
specifier|public
name|Word
parameter_list|()
block|{ 	}
specifier|public
name|Word
parameter_list|(
name|String
name|text
parameter_list|)
block|{
name|this
operator|.
name|text
operator|=
name|text
expr_stmt|;
block|}
specifier|public
name|Word
parameter_list|(
name|String
name|text
parameter_list|,
name|int
name|modifiers
parameter_list|)
block|{
name|this
operator|.
name|text
operator|=
name|text
expr_stmt|;
name|this
operator|.
name|modifiers
operator|=
name|modifiers
expr_stmt|;
block|}
specifier|public
name|String
name|getText
parameter_list|()
block|{
return|return
name|text
return|;
block|}
specifier|public
name|void
name|setText
parameter_list|(
name|String
name|text
parameter_list|)
block|{
name|this
operator|.
name|text
operator|=
name|text
expr_stmt|;
block|}
specifier|public
name|int
name|getModifiers
parameter_list|()
block|{
return|return
name|modifiers
return|;
block|}
specifier|public
name|void
name|setModifiers
parameter_list|(
name|int
name|modifiers
parameter_list|)
block|{
name|this
operator|.
name|modifiers
operator|=
name|modifiers
expr_stmt|;
block|}
specifier|public
name|int
name|compareTo
parameter_list|(
name|Object
name|obj
parameter_list|)
block|{
return|return
name|this
operator|.
name|text
operator|.
name|compareTo
argument_list|(
operator|(
operator|(
name|Word
operator|)
name|obj
operator|)
operator|.
name|text
argument_list|)
return|;
block|}
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|text
return|;
block|}
specifier|public
name|int
name|length
parameter_list|()
block|{
return|return
name|text
operator|.
name|length
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|startsWith
parameter_list|(
name|Word
name|word
parameter_list|)
block|{
return|return
name|text
operator|.
name|startsWith
argument_list|(
name|word
operator|.
name|text
argument_list|)
return|;
block|}
specifier|public
name|char
name|charAt
parameter_list|(
name|int
name|j
parameter_list|)
block|{
return|return
name|text
operator|.
name|charAt
argument_list|(
name|j
argument_list|)
return|;
block|}
specifier|public
name|CharSequence
name|subSequence
parameter_list|(
name|int
name|start
parameter_list|,
name|int
name|end
parameter_list|)
block|{
throw|throw
operator|new
name|UnsupportedOperationException
argument_list|()
throw|;
block|}
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|text
operator|.
name|hashCode
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|obj
parameter_list|)
block|{
return|return
name|text
operator|.
name|equals
argument_list|(
operator|(
operator|(
name|Word
operator|)
name|obj
operator|)
operator|.
name|text
argument_list|)
return|;
block|}
specifier|public
name|void
name|setNoiseCharactor
parameter_list|()
block|{
name|modifiers
operator||=
literal|1
expr_stmt|;
block|}
specifier|public
name|void
name|setNoiseWord
parameter_list|()
block|{
name|modifiers
operator||=
operator|(
literal|1
operator|<<
literal|1
operator|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|isNoiseCharactor
parameter_list|()
block|{
return|return
operator|(
name|modifiers
operator|&
literal|1
operator|)
operator|==
literal|1
return|;
block|}
specifier|public
name|boolean
name|isNoise
parameter_list|()
block|{
return|return
name|isNoiseCharactor
argument_list|()
operator|||
name|isNoiseWord
argument_list|()
return|;
block|}
specifier|public
name|boolean
name|isNoiseWord
parameter_list|()
block|{
return|return
operator|(
name|modifiers
operator|>>
literal|1
operator|&
literal|1
operator|)
operator|==
literal|1
return|;
block|}
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
index|[]
name|args
parameter_list|)
block|{
name|Word
name|w
init|=
operator|new
name|Word
argument_list|(
literal|""
argument_list|)
decl_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|w
operator|.
name|isNoiseCharactor
argument_list|()
argument_list|)
expr_stmt|;
name|w
operator|.
name|setNoiseCharactor
argument_list|()
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|w
operator|.
name|isNoiseCharactor
argument_list|()
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|w
operator|.
name|isNoiseWord
argument_list|()
argument_list|)
expr_stmt|;
name|w
operator|.
name|setNoiseWord
argument_list|()
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|w
operator|.
name|isNoiseWord
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

