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
name|owl
operator|.
name|util
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|UnsupportedEncodingException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URLEncoder
import|;
end_import

begin_import
import|import
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|IRI
import|;
end_import

begin_import
import|import
name|org
operator|.
name|semanticweb
operator|.
name|owlapi
operator|.
name|model
operator|.
name|OWLOntology
import|;
end_import

begin_comment
comment|/**  * A set of utility methods for the manipulation of OWL API objects.  */
end_comment

begin_class
specifier|public
class|class
name|OWLUtils
block|{
comment|/**      * If the ontology is named, this method will return its logical ID, otherwise it will return the location      * it was retrieved from (which is still unique).      *       * @param o      * @return      */
specifier|public
specifier|static
name|IRI
name|getIdentifyingIRI
parameter_list|(
name|OWLOntology
name|o
parameter_list|)
block|{
name|String
name|originalIri
decl_stmt|;
if|if
condition|(
name|o
operator|.
name|isAnonymous
argument_list|()
condition|)
block|{
name|originalIri
operator|=
name|o
operator|.
name|getOWLOntologyManager
argument_list|()
operator|.
name|getOntologyDocumentIRI
argument_list|(
name|o
argument_list|)
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|originalIri
operator|=
name|o
operator|.
name|getOntologyID
argument_list|()
operator|.
name|getOntologyIRI
argument_list|()
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
while|while
condition|(
name|originalIri
operator|.
name|endsWith
argument_list|(
literal|"#"
argument_list|)
operator|||
name|originalIri
operator|.
name|endsWith
argument_list|(
literal|"?"
argument_list|)
condition|)
name|originalIri
operator|=
name|originalIri
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|originalIri
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
comment|// try {
comment|// if (originalIri.endsWith("#")) originalIri = originalIri.substring(0,
comment|// originalIri.length() - 1) + URLEncoder.encode("#", "UTF-8");
comment|// else if (originalIri.endsWith("?")) originalIri = originalIri.substring(0,
comment|// originalIri.length() - 1)
comment|// + URLEncoder.encode("?", "UTF-8");
comment|// } catch (UnsupportedEncodingException e) {
comment|// // That cannot be.
comment|// }
return|return
name|IRI
operator|.
name|create
argument_list|(
name|originalIri
argument_list|)
return|;
block|}
block|}
end_class

end_unit

