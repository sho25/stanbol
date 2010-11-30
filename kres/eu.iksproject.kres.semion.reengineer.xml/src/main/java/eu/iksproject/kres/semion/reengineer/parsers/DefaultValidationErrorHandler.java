begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright 2000-2002,2004 The Apache Software Foundation.  *   * Licensed under the Apache License, Version 2.0 (the "License");  * you may not use this file except in compliance with the License.  * You may obtain a copy of the License at  *   *      http://www.apache.org/licenses/LICENSE-2.0  *   * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  */
end_comment

begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|semion
operator|.
name|reengineer
operator|.
name|parsers
package|;
end_package

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
name|SAXParseException
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
comment|/**  * @version $Id$  */
end_comment

begin_class
class|class
name|DefaultValidationErrorHandler
extends|extends
name|DefaultHandler
block|{
specifier|static
specifier|private
name|int
name|ERROR_COUNT_LIMIT
init|=
literal|10
decl_stmt|;
specifier|private
name|int
name|errorCount
init|=
literal|0
decl_stmt|;
comment|// XXX Fix message i18n
specifier|public
name|void
name|error
parameter_list|(
name|SAXParseException
name|e
parameter_list|)
throws|throws
name|SAXException
block|{
if|if
condition|(
name|errorCount
operator|>=
name|ERROR_COUNT_LIMIT
condition|)
block|{
comment|// Ignore all errors after reaching the limit
return|return;
block|}
elseif|else
if|if
condition|(
name|errorCount
operator|==
literal|0
condition|)
block|{
comment|// Print a warning before the first error
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Warning: validation was turned on but an org.xml.sax.ErrorHandler was not"
argument_list|)
expr_stmt|;
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"set, which is probably not what is desired.  Parser will use a default"
argument_list|)
expr_stmt|;
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"ErrorHandler to print the first "
operator|+
name|ERROR_COUNT_LIMIT
operator|+
literal|" errors.  Please call"
argument_list|)
expr_stmt|;
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"the 'setErrorHandler' method to fix this."
argument_list|)
expr_stmt|;
block|}
name|String
name|systemId
init|=
name|e
operator|.
name|getSystemId
argument_list|()
decl_stmt|;
if|if
condition|(
name|systemId
operator|==
literal|null
condition|)
block|{
name|systemId
operator|=
literal|"null"
expr_stmt|;
block|}
name|String
name|message
init|=
literal|"Error: URI="
operator|+
name|systemId
operator|+
literal|" Line="
operator|+
name|e
operator|.
name|getLineNumber
argument_list|()
operator|+
literal|": "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
decl_stmt|;
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|message
argument_list|)
expr_stmt|;
name|errorCount
operator|++
expr_stmt|;
block|}
block|}
end_class

end_unit

