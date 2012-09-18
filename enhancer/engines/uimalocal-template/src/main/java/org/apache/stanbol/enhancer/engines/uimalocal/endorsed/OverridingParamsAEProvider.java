begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|uimalocal
operator|.
name|endorsed
package|;
end_package

begin_comment
comment|/**  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements. See the NOTICE file distributed with this  * work for additional information regarding copyright ownership. The ASF  * licenses this file to You under the Apache License, Version 2.0 (the  * "License"); you may not use this file except in compliance with the License.  * You may obtain a copy of the License at  *  * http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT  * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the  * License for the specific language governing permissions and limitations under  * the License.  */
end_comment

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
name|util
operator|.
name|Map
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|uima
operator|.
name|UIMAFramework
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|uima
operator|.
name|analysis_engine
operator|.
name|AnalysisEngine
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|uima
operator|.
name|analysis_engine
operator|.
name|AnalysisEngineDescription
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|uima
operator|.
name|resource
operator|.
name|ResourceInitializationException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|uima
operator|.
name|util
operator|.
name|XMLInputSource
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
comment|/**  * {@link AEProvider} implementation that creates an Aggregate AE from the given  * path, also injecting runtime parameters defined in the solrconfig.xml Solr  * configuration file and assigning them as overriding parameters in the  * aggregate AE  *  * @version $Id$  */
end_comment

begin_class
specifier|public
class|class
name|OverridingParamsAEProvider
implements|implements
name|AEProvider
block|{
specifier|private
specifier|static
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|OverridingParamsAEProvider
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
name|String
name|aeFilePath
decl_stmt|;
specifier|private
name|AnalysisEngine
name|cachedAE
decl_stmt|;
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|runtimeParameters
decl_stmt|;
specifier|public
name|OverridingParamsAEProvider
parameter_list|(
name|String
name|aeFilePath
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|runtimeParameters
parameter_list|)
block|{
name|this
operator|.
name|aeFilePath
operator|=
name|aeFilePath
expr_stmt|;
name|this
operator|.
name|runtimeParameters
operator|=
name|runtimeParameters
expr_stmt|;
block|}
specifier|public
specifier|synchronized
name|AnalysisEngine
name|getAE
parameter_list|()
throws|throws
name|ResourceInitializationException
block|{
try|try
block|{
if|if
condition|(
name|cachedAE
operator|==
literal|null
condition|)
block|{
name|XMLInputSource
name|in
init|=
operator|new
name|XMLInputSource
argument_list|(
operator|new
name|File
argument_list|(
name|aeFilePath
argument_list|)
argument_list|)
decl_stmt|;
comment|// get AE description
name|AnalysisEngineDescription
name|desc
init|=
name|UIMAFramework
operator|.
name|getXMLParser
argument_list|()
operator|.
name|parseAnalysisEngineDescription
argument_list|(
name|in
argument_list|)
decl_stmt|;
comment|/*                  * iterate over each AE (to set runtime parameters)                  */
for|for
control|(
name|String
name|attributeName
range|:
name|runtimeParameters
operator|.
name|keySet
argument_list|()
control|)
block|{
name|Object
name|val
init|=
name|getRuntimeValue
argument_list|(
name|desc
argument_list|,
name|attributeName
argument_list|)
decl_stmt|;
name|desc
operator|.
name|getAnalysisEngineMetaData
argument_list|()
operator|.
name|getConfigurationParameterSettings
argument_list|()
operator|.
name|setParameterValue
argument_list|(
name|attributeName
argument_list|,
name|val
argument_list|)
expr_stmt|;
if|if
condition|(
name|log
operator|.
name|isDebugEnabled
argument_list|()
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
operator|new
name|StringBuilder
argument_list|(
literal|"setting "
argument_list|)
operator|.
name|append
argument_list|(
name|attributeName
argument_list|)
operator|.
name|append
argument_list|(
literal|" : "
argument_list|)
operator|.
name|append
argument_list|(
name|runtimeParameters
operator|.
name|get
argument_list|(
name|attributeName
argument_list|)
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|//cachedAE = UIMAFramework.produceAnalysisEngine(desc,rscmgr,null);
name|cachedAE
operator|=
name|UIMAFramework
operator|.
name|produceAnalysisEngine
argument_list|(
name|desc
argument_list|)
expr_stmt|;
if|if
condition|(
name|log
operator|.
name|isDebugEnabled
argument_list|()
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
operator|new
name|StringBuilder
argument_list|(
literal|"AE "
argument_list|)
operator|.
name|append
argument_list|(
name|cachedAE
operator|.
name|getAnalysisEngineMetaData
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|" created from descriptor "
argument_list|)
operator|.
name|append
argument_list|(
name|aeFilePath
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|cachedAE
operator|.
name|reconfigure
argument_list|()
expr_stmt|;
if|if
condition|(
name|log
operator|.
name|isDebugEnabled
argument_list|()
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
operator|new
name|StringBuilder
argument_list|(
literal|"AE "
argument_list|)
operator|.
name|append
argument_list|(
name|cachedAE
operator|.
name|getAnalysisEngineMetaData
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|" at path "
argument_list|)
operator|.
name|append
argument_list|(
name|aeFilePath
argument_list|)
operator|.
name|append
argument_list|(
literal|" reconfigured "
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|cachedAE
operator|=
literal|null
expr_stmt|;
throw|throw
operator|new
name|ResourceInitializationException
argument_list|(
name|e
argument_list|)
throw|;
block|}
return|return
name|cachedAE
return|;
block|}
comment|/*      * create the value to inject in the runtime parameter depending on its      * declared type      */
specifier|private
name|Object
name|getRuntimeValue
parameter_list|(
name|AnalysisEngineDescription
name|desc
parameter_list|,
name|String
name|attributeName
parameter_list|)
throws|throws
name|ClassNotFoundException
block|{
name|String
name|type
init|=
name|desc
operator|.
name|getAnalysisEngineMetaData
argument_list|()
operator|.
name|getConfigurationParameterDeclarations
argument_list|()
operator|.
name|getConfigurationParameter
argument_list|(
literal|null
argument_list|,
name|attributeName
argument_list|)
operator|.
name|getType
argument_list|()
decl_stmt|;
comment|// TODO : do it via reflection ? i.e. Class paramType = Class.forName(type)...
name|Object
name|val
init|=
literal|null
decl_stmt|;
name|Object
name|runtimeValue
init|=
name|runtimeParameters
operator|.
name|get
argument_list|(
name|attributeName
argument_list|)
decl_stmt|;
if|if
condition|(
name|runtimeValue
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
literal|"String"
operator|.
name|equals
argument_list|(
name|type
argument_list|)
condition|)
block|{
name|val
operator|=
name|String
operator|.
name|valueOf
argument_list|(
name|runtimeValue
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"Integer"
operator|.
name|equals
argument_list|(
name|type
argument_list|)
condition|)
block|{
name|val
operator|=
name|Integer
operator|.
name|valueOf
argument_list|(
name|runtimeValue
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"Boolean"
operator|.
name|equals
argument_list|(
name|type
argument_list|)
condition|)
block|{
name|val
operator|=
name|Boolean
operator|.
name|valueOf
argument_list|(
name|runtimeValue
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"Float"
operator|.
name|equals
argument_list|(
name|type
argument_list|)
condition|)
block|{
name|val
operator|=
name|Float
operator|.
name|valueOf
argument_list|(
name|runtimeValue
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|val
return|;
block|}
block|}
end_class

end_unit

