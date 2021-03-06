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
name|enhancer
operator|.
name|engines
operator|.
name|dbpspotlight
operator|.
name|utils
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ByteArrayInputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
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
name|io
operator|.
name|InputStream
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|parsers
operator|.
name|DocumentBuilder
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|parsers
operator|.
name|DocumentBuilderFactory
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|parsers
operator|.
name|ParserConfigurationException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|Document
import|;
end_import

begin_import
import|import
name|org
operator|.
name|w3c
operator|.
name|dom
operator|.
name|NodeList
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

begin_comment
comment|/**  * Parses the XML results given by DBPedia Spotlight.  *   * @author<a href="mailto:iavor.jelev@babelmonkeys.com">Iavor Jelev</a>  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|XMLParser
block|{
comment|/** 	 * Do not create instances of Utility Classes 	 */
specifier|private
name|XMLParser
parameter_list|()
block|{}
empty_stmt|;
specifier|public
specifier|static
name|NodeList
name|getElementsByTagName
parameter_list|(
name|Document
name|doc
parameter_list|,
name|String
name|tagName
parameter_list|)
block|{
return|return
name|doc
operator|.
name|getElementsByTagName
argument_list|(
name|tagName
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|Document
name|loadXMLFromString
parameter_list|(
name|String
name|xml
parameter_list|)
throws|throws
name|SAXException
throws|,
name|IOException
block|{
name|Document
name|doc
init|=
name|loadXMLFromInputStream
argument_list|(
operator|new
name|ByteArrayInputStream
argument_list|(
name|xml
operator|.
name|getBytes
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|doc
operator|.
name|getDocumentElement
argument_list|()
operator|.
name|normalize
argument_list|()
expr_stmt|;
return|return
name|doc
return|;
block|}
specifier|public
specifier|static
name|Document
name|loadXMLFromInputStream
parameter_list|(
name|InputStream
name|is
parameter_list|)
throws|throws
name|SAXException
throws|,
name|IOException
block|{
name|DocumentBuilderFactory
name|factory
init|=
name|DocumentBuilderFactory
operator|.
name|newInstance
argument_list|()
decl_stmt|;
name|factory
operator|.
name|setNamespaceAware
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|DocumentBuilder
name|builder
init|=
literal|null
decl_stmt|;
try|try
block|{
name|builder
operator|=
name|factory
operator|.
name|newDocumentBuilder
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ParserConfigurationException
name|ex
parameter_list|)
block|{ 		}
name|Document
name|doc
init|=
name|builder
operator|.
name|parse
argument_list|(
name|is
argument_list|)
decl_stmt|;
name|is
operator|.
name|close
argument_list|()
expr_stmt|;
name|doc
operator|.
name|getDocumentElement
argument_list|()
operator|.
name|normalize
argument_list|()
expr_stmt|;
return|return
name|doc
return|;
block|}
specifier|public
specifier|static
name|Document
name|loadXMLFromFile
parameter_list|(
name|String
name|filePath
parameter_list|)
throws|throws
name|ParserConfigurationException
throws|,
name|SAXException
throws|,
name|IOException
block|{
name|File
name|fXmlFile
init|=
operator|new
name|File
argument_list|(
name|filePath
argument_list|)
decl_stmt|;
name|DocumentBuilderFactory
name|dbFactory
init|=
name|DocumentBuilderFactory
operator|.
name|newInstance
argument_list|()
decl_stmt|;
name|DocumentBuilder
name|dBuilder
init|=
name|dbFactory
operator|.
name|newDocumentBuilder
argument_list|()
decl_stmt|;
name|Document
name|doc
init|=
name|dBuilder
operator|.
name|parse
argument_list|(
name|fXmlFile
argument_list|)
decl_stmt|;
name|doc
operator|.
name|getDocumentElement
argument_list|()
operator|.
name|normalize
argument_list|()
expr_stmt|;
return|return
name|doc
return|;
block|}
block|}
end_class

end_unit

