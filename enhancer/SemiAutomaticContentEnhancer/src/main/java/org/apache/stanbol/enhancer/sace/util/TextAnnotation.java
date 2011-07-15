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
name|sace
operator|.
name|util
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|datatransfer
operator|.
name|DataFlavor
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|datatransfer
operator|.
name|UnsupportedFlavorException
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
name|LinkedList
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
name|TextAnnotation
implements|implements
name|IAnnotation
block|{
comment|/**      *      */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|5865576334353943605L
decl_stmt|;
specifier|private
name|String
name|name
decl_stmt|;
specifier|private
name|String
name|creator
decl_stmt|;
specifier|private
name|String
name|created
decl_stmt|;
specifier|private
name|String
name|selectionContext
decl_stmt|;
specifier|private
name|String
name|selectedText
decl_stmt|;
specifier|private
name|int
name|startIndex
decl_stmt|;
specifier|private
name|int
name|endIndex
decl_stmt|;
specifier|private
name|List
argument_list|<
name|EntityAnnotation
argument_list|>
name|entityAnnotations
decl_stmt|;
specifier|private
name|Hashtable
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|attributes
decl_stmt|;
specifier|public
name|TextAnnotation
parameter_list|()
block|{
name|entityAnnotations
operator|=
operator|new
name|LinkedList
argument_list|<
name|EntityAnnotation
argument_list|>
argument_list|()
expr_stmt|;
name|attributes
operator|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|setName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
block|}
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|name
return|;
block|}
specifier|public
name|void
name|setCreator
parameter_list|(
name|String
name|creator
parameter_list|)
block|{
name|this
operator|.
name|creator
operator|=
name|creator
expr_stmt|;
block|}
specifier|public
name|String
name|getCreator
parameter_list|()
block|{
return|return
name|creator
return|;
block|}
specifier|public
name|void
name|setCreated
parameter_list|(
name|String
name|created
parameter_list|)
block|{
name|this
operator|.
name|created
operator|=
name|created
expr_stmt|;
block|}
specifier|public
name|String
name|getCreated
parameter_list|()
block|{
return|return
name|created
return|;
block|}
specifier|public
name|void
name|setSelectionContext
parameter_list|(
name|String
name|selectionContext
parameter_list|)
block|{
name|this
operator|.
name|selectionContext
operator|=
name|selectionContext
expr_stmt|;
block|}
specifier|public
name|String
name|getSelectionContext
parameter_list|()
block|{
return|return
name|selectionContext
return|;
block|}
specifier|public
name|void
name|setSelectedText
parameter_list|(
name|String
name|selectedText
parameter_list|)
block|{
name|this
operator|.
name|selectedText
operator|=
name|selectedText
expr_stmt|;
block|}
specifier|public
name|String
name|getSelectedText
parameter_list|()
block|{
return|return
name|selectedText
return|;
block|}
specifier|public
name|void
name|addEntityAnnotation
parameter_list|(
name|EntityAnnotation
name|ea
parameter_list|)
block|{
name|entityAnnotations
operator|.
name|add
argument_list|(
name|ea
argument_list|)
expr_stmt|;
block|}
specifier|public
name|List
argument_list|<
name|EntityAnnotation
argument_list|>
name|getEntityAnnotations
parameter_list|()
block|{
return|return
name|entityAnnotations
return|;
block|}
specifier|public
name|void
name|addAttribute
parameter_list|(
name|String
name|key
parameter_list|,
name|String
name|value
parameter_list|)
block|{
if|if
condition|(
name|attributes
operator|.
name|containsKey
argument_list|(
name|key
argument_list|)
condition|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|val
init|=
name|attributes
operator|.
name|remove
argument_list|(
name|key
argument_list|)
decl_stmt|;
name|val
operator|.
name|add
argument_list|(
name|value
argument_list|)
expr_stmt|;
name|attributes
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|val
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|List
argument_list|<
name|String
argument_list|>
name|val
init|=
operator|new
name|LinkedList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|val
operator|.
name|add
argument_list|(
name|value
argument_list|)
expr_stmt|;
name|attributes
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|val
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getAttribute
parameter_list|(
name|String
name|key
parameter_list|)
block|{
return|return
name|attributes
operator|.
name|get
argument_list|(
name|key
argument_list|)
return|;
block|}
specifier|public
name|void
name|setStartIndex
parameter_list|(
name|int
name|startIndex
parameter_list|)
block|{
name|this
operator|.
name|startIndex
operator|=
name|startIndex
expr_stmt|;
block|}
specifier|public
name|int
name|getStartIndex
parameter_list|()
block|{
return|return
name|startIndex
return|;
block|}
specifier|public
name|void
name|setEndIndex
parameter_list|(
name|int
name|endIndex
parameter_list|)
block|{
name|this
operator|.
name|endIndex
operator|=
name|endIndex
expr_stmt|;
block|}
specifier|public
name|int
name|getEndIndex
parameter_list|()
block|{
return|return
name|endIndex
return|;
block|}
specifier|public
name|Object
name|getTransferData
parameter_list|(
name|DataFlavor
name|flavor
parameter_list|)
throws|throws
name|UnsupportedFlavorException
throws|,
name|IOException
block|{
return|return
name|this
return|;
block|}
specifier|public
name|DataFlavor
index|[]
name|getTransferDataFlavors
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
specifier|public
name|boolean
name|isDataFlavorSupported
parameter_list|(
name|DataFlavor
name|flavor
parameter_list|)
block|{
return|return
literal|true
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
name|o
operator|instanceof
name|TextAnnotation
condition|)
block|{
return|return
operator|(
operator|(
name|TextAnnotation
operator|)
name|o
operator|)
operator|.
name|name
operator|.
name|equals
argument_list|(
name|name
argument_list|)
return|;
block|}
return|return
literal|false
return|;
block|}
specifier|public
name|void
name|retainTypeAnnotation
parameter_list|(
name|String
name|type
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|t
init|=
operator|new
name|LinkedList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|t
operator|.
name|add
argument_list|(
name|type
argument_list|)
expr_stmt|;
name|attributes
operator|.
name|put
argument_list|(
literal|"type"
argument_list|,
name|t
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|clearEntityAnnotation
parameter_list|()
block|{
name|entityAnnotations
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|clearTypeAnnotation
parameter_list|()
block|{
name|attributes
operator|.
name|remove
argument_list|(
literal|"type"
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

