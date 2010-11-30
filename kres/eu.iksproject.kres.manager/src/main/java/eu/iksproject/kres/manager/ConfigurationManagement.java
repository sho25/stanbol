begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|manager
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
name|Iterator
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
name|java
operator|.
name|util
operator|.
name|Set
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
name|apibinding
operator|.
name|OWLManager
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
name|OWLClass
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
name|OWLDataFactory
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
name|OWLDataProperty
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
name|OWLIndividual
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
name|OWLLiteral
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
name|OWLNamedIndividual
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

begin_class
specifier|public
class|class
name|ConfigurationManagement
block|{
specifier|private
specifier|static
name|OWLDataFactory
name|_df
init|=
name|OWLManager
operator|.
name|getOWLDataFactory
argument_list|()
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
index|[]
name|EMPTY_IRI_ARRAY
init|=
operator|new
name|String
index|[
literal|0
index|]
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|OWLClass
name|cScope
init|=
name|_df
operator|.
name|getOWLClass
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/onm.owl#Scope"
argument_list|)
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|OWLDataProperty
name|activateOnStart
init|=
name|_df
operator|.
name|getOWLDataProperty
argument_list|(
name|IRI
operator|.
name|create
argument_list|(
literal|"http://kres.iks-project.eu/ontology/meta/onm.owl#activateOnStart"
argument_list|)
argument_list|)
decl_stmt|;
specifier|public
specifier|static
name|String
index|[]
name|getScopesToActivate
parameter_list|(
name|OWLOntology
name|config
parameter_list|)
block|{
name|Set
argument_list|<
name|OWLIndividual
argument_list|>
name|scopes
init|=
name|cScope
operator|.
name|getIndividuals
argument_list|(
name|config
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|result
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|boolean
name|doActivate
init|=
literal|false
decl_stmt|;
for|for
control|(
name|OWLIndividual
name|iScope
range|:
name|scopes
control|)
block|{
name|Set
argument_list|<
name|OWLLiteral
argument_list|>
name|activate
init|=
name|iScope
operator|.
name|getDataPropertyValues
argument_list|(
name|activateOnStart
argument_list|,
name|config
argument_list|)
decl_stmt|;
name|Iterator
argument_list|<
name|OWLLiteral
argument_list|>
name|it
init|=
name|activate
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
operator|&&
operator|!
name|doActivate
condition|)
block|{
name|OWLLiteral
name|l
init|=
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|l
operator|.
name|isOWLTypedLiteral
argument_list|()
condition|)
name|doActivate
operator||=
name|Boolean
operator|.
name|parseBoolean
argument_list|(
name|l
operator|.
name|asOWLTypedLiteral
argument_list|()
operator|.
name|getLiteral
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|iScope
operator|.
name|isNamed
argument_list|()
operator|&&
name|doActivate
condition|)
name|result
operator|.
name|add
argument_list|(
operator|(
operator|(
name|OWLNamedIndividual
operator|)
name|iScope
operator|)
operator|.
name|getIRI
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|result
operator|.
name|toArray
argument_list|(
name|EMPTY_IRI_ARRAY
argument_list|)
return|;
block|}
block|}
end_class

end_unit

