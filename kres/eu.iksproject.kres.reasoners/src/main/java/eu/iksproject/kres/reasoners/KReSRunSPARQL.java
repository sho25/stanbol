begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * To change this template, choose Tools | Templates  * and open the template in the editor.  */
end_comment

begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|reasoners
package|;
end_package

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|ontology
operator|.
name|OntModel
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|query
operator|.
name|QueryExecution
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|query
operator|.
name|QueryExecutionFactory
import|;
end_import

begin_import
import|import
name|com
operator|.
name|hp
operator|.
name|hpl
operator|.
name|jena
operator|.
name|query
operator|.
name|ResultSet
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|kres
operator|.
name|shared
operator|.
name|transformation
operator|.
name|JenaToOwlConvert
import|;
end_import

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
name|Iterator
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
comment|/**  *  * @author elvio  */
end_comment

begin_class
specifier|public
class|class
name|KReSRunSPARQL
block|{
specifier|private
name|OWLOntology
name|owlmodel
decl_stmt|;
specifier|private
name|OntModel
name|jenamodel
decl_stmt|;
specifier|private
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|sparqlprefix
decl_stmt|;
comment|/**      * Constructor to create an OWLOntology object where run the SPARQL query.      *      * @param owl {The OWLOntology to be querying.}      */
specifier|public
name|KReSRunSPARQL
parameter_list|(
name|OWLOntology
name|owl
parameter_list|)
block|{
name|this
operator|.
name|owlmodel
operator|=
name|owl
expr_stmt|;
try|try
block|{
name|JenaToOwlConvert
name|j2o
init|=
operator|new
name|JenaToOwlConvert
argument_list|()
decl_stmt|;
name|this
operator|.
name|jenamodel
operator|=
name|j2o
operator|.
name|ModelOwlToJenaConvert
argument_list|(
name|owlmodel
argument_list|,
literal|"RDF/XML"
argument_list|)
expr_stmt|;
name|Iterator
argument_list|<
name|String
argument_list|>
name|iter
init|=
name|jenamodel
operator|.
name|getNsPrefixMap
argument_list|()
operator|.
name|keySet
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|map
init|=
name|jenamodel
operator|.
name|getNsPrefixMap
argument_list|()
decl_stmt|;
name|this
operator|.
name|sparqlprefix
operator|=
operator|(
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
operator|)
name|map
expr_stmt|;
while|while
condition|(
name|iter
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|String
name|k
init|=
name|iter
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|k
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|this
operator|.
name|sparqlprefix
operator|.
name|put
argument_list|(
name|k
argument_list|,
literal|"<"
operator|+
name|map
operator|.
name|get
argument_list|(
name|k
argument_list|)
operator|+
literal|">"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|String
name|base
init|=
name|map
operator|.
name|get
argument_list|(
name|k
argument_list|)
decl_stmt|;
name|this
operator|.
name|sparqlprefix
operator|.
name|put
argument_list|(
literal|"base"
argument_list|,
literal|"<"
operator|+
name|base
operator|+
literal|">"
argument_list|)
expr_stmt|;
name|this
operator|.
name|sparqlprefix
operator|.
name|remove
argument_list|(
name|k
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
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**      * Constructor to create an OWLOntology object where run the SPARQL query.      *      * @param owl {The OWLOntology to be querying}      * @param prefix {The map where the keys are the prefix label and the value the IRI of the prefix on the form: http://www.w3.org/2000/01/rdf-schema#.}      */
specifier|public
name|KReSRunSPARQL
parameter_list|(
name|OWLOntology
name|owl
parameter_list|,
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|prefix
parameter_list|)
block|{
name|this
operator|.
name|owlmodel
operator|=
name|owl
expr_stmt|;
name|JenaToOwlConvert
name|j2o
init|=
operator|new
name|JenaToOwlConvert
argument_list|()
decl_stmt|;
name|this
operator|.
name|jenamodel
operator|=
name|j2o
operator|.
name|ModelOwlToJenaConvert
argument_list|(
name|owlmodel
argument_list|,
literal|"RDF/XML"
argument_list|)
expr_stmt|;
name|Iterator
argument_list|<
name|String
argument_list|>
name|keys
init|=
name|prefix
operator|.
name|keySet
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|this
operator|.
name|sparqlprefix
operator|=
operator|new
name|HashMap
argument_list|()
expr_stmt|;
while|while
condition|(
name|keys
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|String
name|key
init|=
name|keys
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|pre
init|=
name|prefix
operator|.
name|get
argument_list|(
name|key
argument_list|)
decl_stmt|;
if|if
condition|(
name|pre
operator|.
name|contains
argument_list|(
literal|"<"
argument_list|)
condition|)
name|this
operator|.
name|sparqlprefix
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|pre
argument_list|)
expr_stmt|;
else|else
name|this
operator|.
name|sparqlprefix
operator|.
name|put
argument_list|(
name|key
argument_list|,
literal|"<"
operator|+
name|pre
operator|+
literal|">"
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * To get the prefix mapping      *      * @return {Return a prefix mapping.}      */
specifier|public
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|getSPARQLprefix
parameter_list|()
block|{
return|return
name|this
operator|.
name|sparqlprefix
return|;
block|}
comment|/**      * To add a new prefix.      *      * @param label {The prefix label}      * @param prefix {The new prefix to be added in the form: http://www.w3.org/2000/01/rdf-schema#}.      * @return {A boolean that is true if process finish without errors.}      */
specifier|public
name|boolean
name|addSPARQLprefix
parameter_list|(
name|String
name|label
parameter_list|,
name|String
name|prefix
parameter_list|)
block|{
name|boolean
name|ok
init|=
literal|false
decl_stmt|;
if|if
condition|(
operator|!
name|sparqlprefix
operator|.
name|containsKey
argument_list|(
name|label
argument_list|)
condition|)
block|{
if|if
condition|(
operator|!
name|prefix
operator|.
name|contains
argument_list|(
literal|"<"
argument_list|)
condition|)
name|sparqlprefix
operator|.
name|put
argument_list|(
name|label
argument_list|,
literal|"<"
operator|+
name|prefix
operator|+
literal|">"
argument_list|)
expr_stmt|;
else|else
name|sparqlprefix
operator|.
name|put
argument_list|(
name|label
argument_list|,
name|prefix
argument_list|)
expr_stmt|;
if|if
condition|(
name|sparqlprefix
operator|.
name|containsKey
argument_list|(
name|label
argument_list|)
condition|)
if|if
condition|(
name|sparqlprefix
operator|.
name|get
argument_list|(
name|label
argument_list|)
operator|.
name|contains
argument_list|(
name|prefix
argument_list|)
condition|)
name|ok
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"The prefix with "
operator|+
name|label
operator|+
literal|" already exists. Prefix: "
operator|+
name|sparqlprefix
operator|.
name|get
argument_list|(
name|label
argument_list|)
argument_list|)
expr_stmt|;
name|ok
operator|=
literal|false
expr_stmt|;
block|}
return|return
name|ok
return|;
block|}
comment|/**      * To remove a prefix with a particular label from the prefix mapping.      *      * @param label {The label of the prefix to be removed.}      * @return {A boolean that is true if process finish without errors.}      */
specifier|public
name|boolean
name|removeSPARQLprefix
parameter_list|(
name|String
name|label
parameter_list|)
block|{
name|boolean
name|ok
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|sparqlprefix
operator|.
name|containsKey
argument_list|(
name|label
argument_list|)
condition|)
block|{
name|sparqlprefix
operator|.
name|remove
argument_list|(
name|label
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|sparqlprefix
operator|.
name|containsKey
argument_list|(
name|label
argument_list|)
condition|)
name|ok
operator|=
literal|true
expr_stmt|;
else|else
name|ok
operator|=
literal|false
expr_stmt|;
block|}
else|else
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"No prefix with name "
operator|+
name|label
argument_list|)
expr_stmt|;
name|ok
operator|=
literal|false
expr_stmt|;
block|}
return|return
name|ok
return|;
block|}
comment|/**      * To run a SPARQL query      *      * @param query {The query string without the declaration of the prefixes.}      * @return {Return a Jena Result Set Object.}      */
specifier|public
name|ResultSet
name|runSPARQL
parameter_list|(
name|String
name|query
parameter_list|)
block|{
if|if
condition|(
operator|!
name|sparqlprefix
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|Iterator
argument_list|<
name|String
argument_list|>
name|keys
init|=
name|sparqlprefix
operator|.
name|keySet
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|String
name|prefix
init|=
literal|""
decl_stmt|;
while|while
condition|(
name|keys
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|String
name|key
init|=
name|keys
operator|.
name|next
argument_list|()
decl_stmt|;
name|prefix
operator|=
name|prefix
operator|+
literal|"PREFIX "
operator|+
name|key
operator|+
literal|": "
operator|+
name|sparqlprefix
operator|.
name|get
argument_list|(
name|key
argument_list|)
operator|+
literal|"\n"
expr_stmt|;
block|}
name|query
operator|=
name|prefix
operator|+
name|query
expr_stmt|;
try|try
block|{
name|QueryExecution
name|qexec
init|=
name|QueryExecutionFactory
operator|.
name|create
argument_list|(
name|query
argument_list|,
name|jenamodel
argument_list|)
decl_stmt|;
name|ResultSet
name|results
init|=
name|qexec
operator|.
name|execSelect
argument_list|()
decl_stmt|;
return|return
name|results
return|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
else|else
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"There is not prefix defined in sparqlprefix."
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
block|}
end_class

end_unit

