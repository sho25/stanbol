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
name|uimaremote
operator|.
name|tools
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
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
name|caslight
operator|.
name|Feature
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
name|commons
operator|.
name|caslight
operator|.
name|FeatureStructure
import|;
end_import

begin_import
import|import
name|org
operator|.
name|xml
operator|.
name|sax
operator|.
name|Attributes
import|;
end_import

begin_import
import|import
name|org
operator|.
name|xml
operator|.
name|sax
operator|.
name|SAXException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|xml
operator|.
name|sax
operator|.
name|helpers
operator|.
name|DefaultHandler
import|;
end_import

begin_comment
comment|/**  * A SAX processor for processing UIMA Servlet result XML  *  * @author Mihály Héder  */
end_comment

begin_class
specifier|public
class|class
name|SaxUIMAServletResult2Offsets
extends|extends
name|DefaultHandler
block|{
specifier|private
name|int
name|elementCounter
init|=
literal|0
decl_stmt|;
specifier|private
name|List
argument_list|<
name|FeatureStructure
argument_list|>
name|fsList
init|=
operator|new
name|ArrayList
argument_list|<
name|FeatureStructure
argument_list|>
argument_list|()
decl_stmt|;
specifier|private
name|String
name|sourceName
decl_stmt|;
comment|/**      * Returns the Feature List built from the UIMA result.      *      * @return      */
specifier|public
name|List
argument_list|<
name|FeatureStructure
argument_list|>
name|getFsList
parameter_list|()
block|{
return|return
name|fsList
return|;
block|}
comment|/**      * Sets the source name of this processor. The source name is incorporated      * into the given identifiers      *      * @param sourceName      */
specifier|public
name|void
name|setSourceName
parameter_list|(
name|String
name|sourceName
parameter_list|)
block|{
name|this
operator|.
name|sourceName
operator|=
name|sourceName
expr_stmt|;
block|}
comment|/*      * (non-Javadoc)      *      * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,      * java.lang.String, java.lang.String, org.xml.sax.Attributes)      */
annotation|@
name|Override
specifier|public
name|void
name|startElement
parameter_list|(
name|String
name|uri
parameter_list|,
name|String
name|localName
parameter_list|,
name|String
name|qname
parameter_list|,
name|Attributes
name|attrs
parameter_list|)
throws|throws
name|SAXException
block|{
if|if
condition|(
operator|!
name|localName
operator|.
name|equals
argument_list|(
literal|"result"
argument_list|)
condition|)
block|{
name|elementCounter
operator|++
expr_stmt|;
name|String
name|type
init|=
name|localName
decl_stmt|;
name|FeatureStructure
name|fs
init|=
operator|new
name|FeatureStructure
argument_list|(
name|sourceName
operator|+
literal|"."
operator|+
name|localName
operator|+
literal|"#"
operator|+
name|elementCounter
argument_list|,
name|type
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|attrs
operator|.
name|getLength
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|String
name|name
init|=
name|attrs
operator|.
name|getQName
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|String
name|value
init|=
name|attrs
operator|.
name|getValue
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|checkIfInteger
argument_list|(
name|value
argument_list|)
condition|)
block|{
name|Feature
argument_list|<
name|Integer
argument_list|>
name|f
init|=
operator|new
name|Feature
argument_list|(
name|name
argument_list|,
name|value
argument_list|)
decl_stmt|;
name|fs
operator|.
name|addFeature
argument_list|(
name|f
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|Feature
argument_list|<
name|String
argument_list|>
name|f
init|=
operator|new
name|Feature
argument_list|(
name|name
argument_list|,
name|value
argument_list|)
decl_stmt|;
name|fs
operator|.
name|addFeature
argument_list|(
name|f
argument_list|)
expr_stmt|;
block|}
block|}
name|fsList
operator|.
name|add
argument_list|(
name|fs
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
name|boolean
name|checkIfInteger
parameter_list|(
name|String
name|value
parameter_list|)
block|{
name|boolean
name|ret
init|=
literal|true
decl_stmt|;
try|try
block|{
name|Integer
operator|.
name|parseInt
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
name|ret
operator|=
literal|false
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
block|}
end_class

end_unit

