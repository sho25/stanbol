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
name|servicesapi
operator|.
name|model
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
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
name|entityhub
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|rdf
operator|.
name|RdfResourceEnum
import|;
end_import

begin_interface
specifier|public
interface|interface
name|Symbol
extends|extends
name|Sign
block|{
comment|/**      * The default state for new symbols if not defined otherwise      */
name|SymbolState
name|DEFAULT_SYMBOL_STATE
init|=
name|SymbolState
operator|.
name|proposed
decl_stmt|;
comment|/**      * Enumeration that defines the different states of Symbols      * @author Rupert Westenthaler      *      */
enum|enum
name|SymbolState
block|{
comment|/**          * This symbol is marked as removed          */
name|removed
argument_list|(
name|RdfResourceEnum
operator|.
name|symbolStateRemoved
operator|.
name|getUri
argument_list|()
argument_list|)
block|,
comment|/**          * This symbol should no longer be moved. Usually there are one or more          * new symbols that should be used instead of this one. See          * {@link Symbol#getSuccessors()} for more information          */
name|depreciated
argument_list|(
name|RdfResourceEnum
operator|.
name|symbolStateDepreciated
operator|.
name|getUri
argument_list|()
argument_list|)
block|,
comment|/**          * Indicates usually a newly created {@link Symbol} that needs some kind          * of confirmation.          */
name|proposed
argument_list|(
name|RdfResourceEnum
operator|.
name|symbolStateProposed
operator|.
name|getUri
argument_list|()
argument_list|)
block|,
comment|/**          * Symbols with that state are ready to be used.          */
name|active
argument_list|(
name|RdfResourceEnum
operator|.
name|symbolStateActive
operator|.
name|getUri
argument_list|()
argument_list|)
block|,         ;
specifier|private
name|String
name|uri
decl_stmt|;
name|SymbolState
parameter_list|(
name|String
name|uri
parameter_list|)
block|{
name|this
operator|.
name|uri
operator|=
name|uri
expr_stmt|;
block|}
specifier|public
name|String
name|getUri
parameter_list|()
block|{
return|return
name|uri
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
name|uri
return|;
block|}
block|}
empty_stmt|;
comment|/**      * The property to be used for the symbol label      */
name|String
name|LABEL
init|=
name|RdfResourceEnum
operator|.
name|label
operator|.
name|getUri
argument_list|()
decl_stmt|;
comment|/**      * The label of this Symbol in the default language      * @return the label      */
name|String
name|getLabel
parameter_list|()
function_decl|;
comment|/**      * Setter for the Label in the default Language      * @param label      */
name|void
name|setLabel
parameter_list|(
name|String
name|label
parameter_list|)
function_decl|;
comment|/**      * The preferred label of this Symbol in the given language or      *<code>null</code> if no label for this language is defined      * TODO: how to handle internationalisation.      * @param lang the language      * @return The preferred label of this Symbol in the given language or      *<code>null</code> if no label for this language is defined      */
name|String
name|getLabel
parameter_list|(
name|String
name|lang
parameter_list|)
function_decl|;
comment|/**      * Setter for a label of a specific language      * @param label the label      * @param language the language.<code>null</code> indicates to use no language tag      */
name|void
name|setLabel
parameter_list|(
name|String
name|label
parameter_list|,
name|String
name|language
parameter_list|)
function_decl|;
comment|/**      * The property to be used for the symbol description      */
name|String
name|DESCRIPTION
init|=
name|RdfResourceEnum
operator|.
name|description
operator|.
name|getUri
argument_list|()
decl_stmt|;
comment|/**      * Getter for the descriptions of this symbol in the default language.      * @return The descriptions or an empty collection.      */
name|Iterator
argument_list|<
name|Text
argument_list|>
name|getDescriptions
parameter_list|()
function_decl|;
comment|/**      * Removes the description in the default language from the Symbol      * @param description the description to remove      */
name|void
name|removeDescription
parameter_list|(
name|String
name|description
parameter_list|)
function_decl|;
comment|/**      * Adds a description in the default language to the Symbol      * @param description the description      */
name|void
name|addDescription
parameter_list|(
name|String
name|description
parameter_list|)
function_decl|;
comment|/**      * Getter for the short description as defined for the parsed language.      * @param lang The language. Parse<code>null</code> for values without language tags      * @return The description or<code>null</code> if no description is defined      * for the parsed language.      */
name|Iterator
argument_list|<
name|Text
argument_list|>
name|getDescriptions
parameter_list|(
name|String
name|lang
parameter_list|)
function_decl|;
comment|/**      * Removes the description in the parsed language from the Symbol      * @param description the description to remove      * @param language the language.<code>null</code> indicates to use no language tag      */
name|void
name|removeDescription
parameter_list|(
name|String
name|description
parameter_list|,
name|String
name|language
parameter_list|)
function_decl|;
comment|/**      * Adds a description in the parsed language to the Symbol      * @param description the description      * @param lanugage the language.<code>null</code> indicates to use no language tag      */
name|void
name|addDescription
parameter_list|(
name|String
name|description
parameter_list|,
name|String
name|lanugage
parameter_list|)
function_decl|;
comment|/**      * The property to be used for the symbol state      */
name|String
name|STATE
init|=
name|RdfResourceEnum
operator|.
name|hasSymbolState
operator|.
name|getUri
argument_list|()
decl_stmt|;
comment|/**      * Getter for the state of this symbol      * @return the state      */
name|SymbolState
name|getState
parameter_list|()
function_decl|;
comment|/**      * Setter for the state of the Symbol      * @param state the new state      * @throws IllegalArgumentException if the parsed state is<code>null</code>      */
name|void
name|setState
parameter_list|(
name|SymbolState
name|state
parameter_list|)
throws|throws
name|IllegalArgumentException
function_decl|;
comment|/**      * The property used for linking to successors      */
name|String
name|SUCCESSOR
init|=
name|RdfResourceEnum
operator|.
name|successor
operator|.
name|getUri
argument_list|()
decl_stmt|;
comment|/**      * Returns if this Symbols does have any successors      * @return Returns<code>true</code> if successors are defined for this      * symbol; otherwise<code>false</code>.      */
name|boolean
name|isSuccessor
parameter_list|()
function_decl|;
comment|/**      * Getter for the ID's of the symbols defined as successors of this one.      * @return The id's of the symbols defined as successors of this one or an      * empty list if there are no successors are defined.      */
name|Iterator
argument_list|<
name|String
argument_list|>
name|getSuccessors
parameter_list|()
function_decl|;
comment|/**      * Adds the symbol with the parsed ID as a successor      * @param successor the id of the successor      */
name|void
name|addSuccessor
parameter_list|(
name|String
name|successor
parameter_list|)
function_decl|;
comment|/**      * Removes the symbol with the parsed ID as a successor      * @param successor the id of the successor to remove      */
name|void
name|removeSuccessor
parameter_list|(
name|String
name|successor
parameter_list|)
function_decl|;
comment|/**      * The property used for linking to predecessors      */
name|String
name|PREDECESSOR
init|=
name|RdfResourceEnum
operator|.
name|predecessor
operator|.
name|getUri
argument_list|()
decl_stmt|;
comment|/**      * Returns if this Symbols does have any predecessors      * @return Returns<code>true</code> if predecessors are defined for this      * symbol; otherwise<code>false</code>.      */
name|boolean
name|isPredecessors
parameter_list|()
function_decl|;
comment|/**      * Getter for the ID's of the symbols defined as predecessors of this one.      * @return The id's of the symbols defined as predecessors of this one or an      * empty list if there are no predecessors are defined.      */
name|Iterator
argument_list|<
name|String
argument_list|>
name|getPredecessors
parameter_list|()
function_decl|;
comment|/**      * Adds the symbol with the parsed ID as a predecessor      * @param predecessor the id of the predecessors      */
name|void
name|addPredecessor
parameter_list|(
name|String
name|predecessor
parameter_list|)
function_decl|;
comment|/**      * Removes the symbol with the parsed ID as a predecessor      * @param predecessor the id of the predecessor to remove      */
name|void
name|removePredecessor
parameter_list|(
name|String
name|predecessor
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

