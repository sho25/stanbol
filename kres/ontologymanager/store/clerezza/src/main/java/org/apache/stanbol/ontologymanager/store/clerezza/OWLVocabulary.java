begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|ontologymanager
operator|.
name|store
operator|.
name|clerezza
package|;
end_package

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|clerezza
operator|.
name|rdf
operator|.
name|core
operator|.
name|UriRef
import|;
end_import

begin_class
specifier|public
class|class
name|OWLVocabulary
block|{
specifier|private
specifier|static
specifier|final
name|String
name|RDF
init|=
literal|"http://www.w3.org/1999/02/22-rdf-syntax-ns#"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|OWL
init|=
literal|"http://www.w3.org/2002/07/owl#"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|RDF_TYPE
init|=
operator|new
name|UriRef
argument_list|(
name|RDF
operator|+
literal|"type"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|OWL_CLASS
init|=
operator|new
name|UriRef
argument_list|(
name|OWL
operator|+
literal|"Class"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|OWL_DATATYPE_PROPERTY
init|=
operator|new
name|UriRef
argument_list|(
name|OWL
operator|+
literal|"DatatypeProperty"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|OWL_OBJECT_PROPERTY
init|=
operator|new
name|UriRef
argument_list|(
name|OWL
operator|+
literal|"ObjectProperty"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|UriRef
name|OWL_INDIVIDUAL
init|=
operator|new
name|UriRef
argument_list|(
name|OWL
operator|+
literal|"Individual"
argument_list|)
decl_stmt|;
block|}
end_class

end_unit

