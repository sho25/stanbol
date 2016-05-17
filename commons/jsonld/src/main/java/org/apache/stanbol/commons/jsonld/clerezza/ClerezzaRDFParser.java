begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|commons
operator|.
name|jsonld
operator|.
name|clerezza
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
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
name|clerezza
operator|.
name|commons
operator|.
name|rdf
operator|.
name|BlankNode
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|commons
operator|.
name|rdf
operator|.
name|Language
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|commons
operator|.
name|rdf
operator|.
name|Literal
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|commons
operator|.
name|rdf
operator|.
name|BlankNodeOrIRI
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|commons
operator|.
name|rdf
operator|.
name|RDFTerm
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|commons
operator|.
name|rdf
operator|.
name|Triple
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|commons
operator|.
name|rdf
operator|.
name|Graph
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|commons
operator|.
name|rdf
operator|.
name|IRI
import|;
end_import

begin_import
import|import
name|com
operator|.
name|github
operator|.
name|jsonldjava
operator|.
name|core
operator|.
name|JsonLdError
import|;
end_import

begin_import
import|import
name|com
operator|.
name|github
operator|.
name|jsonldjava
operator|.
name|core
operator|.
name|JsonLdProcessor
import|;
end_import

begin_import
import|import
name|com
operator|.
name|github
operator|.
name|jsonldjava
operator|.
name|core
operator|.
name|RDFDataset
import|;
end_import

begin_import
import|import
name|com
operator|.
name|github
operator|.
name|jsonldjava
operator|.
name|core
operator|.
name|RDFParser
import|;
end_import

begin_comment
comment|/**  * Converts a Clerezza {@link Graph} to the {@link RDFDataset} used  * by the {@link JsonLdProcessor}  *   * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
class|class
name|ClerezzaRDFParser
implements|implements
name|RDFParser
block|{
specifier|private
specifier|static
name|String
name|RDF_LANG_STRING
init|=
literal|"http://www.w3.org/1999/02/22-rdf-syntax-ns#langString"
decl_stmt|;
specifier|private
name|long
name|count
init|=
literal|0
decl_stmt|;
annotation|@
name|Override
specifier|public
name|RDFDataset
name|parse
parameter_list|(
name|Object
name|input
parameter_list|)
throws|throws
name|JsonLdError
block|{
name|count
operator|=
literal|0
expr_stmt|;
name|Map
argument_list|<
name|BlankNode
argument_list|,
name|String
argument_list|>
name|bNodeMap
init|=
operator|new
name|HashMap
argument_list|<
name|BlankNode
argument_list|,
name|String
argument_list|>
argument_list|(
literal|1024
argument_list|)
decl_stmt|;
specifier|final
name|RDFDataset
name|result
init|=
operator|new
name|RDFDataset
argument_list|()
decl_stmt|;
if|if
condition|(
name|input
operator|instanceof
name|Graph
condition|)
block|{
for|for
control|(
name|Triple
name|t
range|:
operator|(
operator|(
name|Graph
operator|)
name|input
operator|)
control|)
block|{
name|handleStatement
argument_list|(
name|result
argument_list|,
name|t
argument_list|,
name|bNodeMap
argument_list|)
expr_stmt|;
block|}
block|}
name|bNodeMap
operator|.
name|clear
argument_list|()
expr_stmt|;
comment|//help gc
return|return
name|result
return|;
block|}
specifier|private
name|void
name|handleStatement
parameter_list|(
name|RDFDataset
name|result
parameter_list|,
name|Triple
name|t
parameter_list|,
name|Map
argument_list|<
name|BlankNode
argument_list|,
name|String
argument_list|>
name|bNodeMap
parameter_list|)
block|{
specifier|final
name|String
name|subject
init|=
name|getResourceValue
argument_list|(
name|t
operator|.
name|getSubject
argument_list|()
argument_list|,
name|bNodeMap
argument_list|)
decl_stmt|;
specifier|final
name|String
name|predicate
init|=
name|getResourceValue
argument_list|(
name|t
operator|.
name|getPredicate
argument_list|()
argument_list|,
name|bNodeMap
argument_list|)
decl_stmt|;
specifier|final
name|RDFTerm
name|object
init|=
name|t
operator|.
name|getObject
argument_list|()
decl_stmt|;
if|if
condition|(
name|object
operator|instanceof
name|Literal
condition|)
block|{
specifier|final
name|String
name|value
init|=
operator|(
operator|(
name|Literal
operator|)
name|object
operator|)
operator|.
name|getLexicalForm
argument_list|()
decl_stmt|;
specifier|final
name|String
name|language
decl_stmt|;
specifier|final
name|String
name|datatype
decl_stmt|;
name|datatype
operator|=
name|getResourceValue
argument_list|(
operator|(
operator|(
name|Literal
operator|)
name|object
operator|)
operator|.
name|getDataType
argument_list|()
argument_list|,
name|bNodeMap
argument_list|)
expr_stmt|;
name|Language
name|l
init|=
operator|(
operator|(
name|Literal
operator|)
name|object
operator|)
operator|.
name|getLanguage
argument_list|()
decl_stmt|;
if|if
condition|(
name|l
operator|==
literal|null
condition|)
block|{
name|language
operator|=
literal|null
expr_stmt|;
block|}
else|else
block|{
name|language
operator|=
name|l
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
name|result
operator|.
name|addTriple
argument_list|(
name|subject
argument_list|,
name|predicate
argument_list|,
name|value
argument_list|,
name|datatype
argument_list|,
name|language
argument_list|)
expr_stmt|;
name|count
operator|++
expr_stmt|;
block|}
else|else
block|{
name|result
operator|.
name|addTriple
argument_list|(
name|subject
argument_list|,
name|predicate
argument_list|,
name|getResourceValue
argument_list|(
operator|(
name|BlankNodeOrIRI
operator|)
name|object
argument_list|,
name|bNodeMap
argument_list|)
argument_list|)
expr_stmt|;
name|count
operator|++
expr_stmt|;
block|}
block|}
comment|/**      * The count of processed triples (not thread save)      * @return the count of triples processed by the last {@link #parse(Object)} call      */
specifier|public
name|long
name|getCount
parameter_list|()
block|{
return|return
name|count
return|;
block|}
specifier|private
name|String
name|getResourceValue
parameter_list|(
name|BlankNodeOrIRI
name|nl
parameter_list|,
name|Map
argument_list|<
name|BlankNode
argument_list|,
name|String
argument_list|>
name|bNodeMap
parameter_list|)
block|{
if|if
condition|(
name|nl
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
elseif|else
if|if
condition|(
name|nl
operator|instanceof
name|IRI
condition|)
block|{
return|return
operator|(
operator|(
name|IRI
operator|)
name|nl
operator|)
operator|.
name|getUnicodeString
argument_list|()
return|;
block|}
elseif|else
if|if
condition|(
name|nl
operator|instanceof
name|BlankNode
condition|)
block|{
name|String
name|bNodeId
init|=
name|bNodeMap
operator|.
name|get
argument_list|(
name|nl
argument_list|)
decl_stmt|;
if|if
condition|(
name|bNodeId
operator|==
literal|null
condition|)
block|{
name|bNodeId
operator|=
name|Integer
operator|.
name|toString
argument_list|(
name|bNodeMap
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|bNodeMap
operator|.
name|put
argument_list|(
operator|(
name|BlankNode
operator|)
name|nl
argument_list|,
name|bNodeId
argument_list|)
expr_stmt|;
block|}
return|return
operator|new
name|StringBuilder
argument_list|(
literal|"_:b"
argument_list|)
operator|.
name|append
argument_list|(
name|bNodeId
argument_list|)
operator|.
name|toString
argument_list|()
return|;
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Unknwon BlankNodeOrIRI type "
operator|+
name|nl
operator|.
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|"!"
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit

