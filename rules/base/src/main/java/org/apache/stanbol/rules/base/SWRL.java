begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|rules
operator|.
name|base
package|;
end_package

begin_comment
comment|/* CVS $Id: SWRL.java 1082452 2011-03-17 12:21:50Z concelvio $ */
end_comment

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
name|rdf
operator|.
name|model
operator|.
name|Model
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
name|rdf
operator|.
name|model
operator|.
name|ModelFactory
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
name|rdf
operator|.
name|model
operator|.
name|Property
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
name|rdf
operator|.
name|model
operator|.
name|Resource
import|;
end_import

begin_comment
comment|/**  * Vocabulary definitions from http://www.w3.org/Submission/SWRL/swrl.owl   * @author Auto-generated by schemagen on 15 feb 2010 15:40   */
end_comment

begin_class
specifier|public
class|class
name|SWRL
block|{
comment|/**<p>The RDF model that holds the vocabulary terms</p> */
specifier|private
specifier|static
name|Model
name|m_model
init|=
name|ModelFactory
operator|.
name|createDefaultModel
argument_list|()
decl_stmt|;
comment|/**<p>The namespace of the vocabulary as a string</p> */
specifier|public
specifier|static
specifier|final
name|String
name|NS
init|=
literal|"http://www.w3.org/2003/11/swrl#"
decl_stmt|;
comment|/**<p>The namespace of the vocabulary as a string</p>      *  @see #NS */
specifier|public
specifier|static
name|String
name|getURI
parameter_list|()
block|{
return|return
literal|"http://www.w3.org/Submission/SWRL/swrl.owl"
return|;
block|}
comment|/**<p>The namespace of the vocabulary as a resource</p> */
specifier|public
specifier|static
specifier|final
name|Resource
name|NAMESPACE
init|=
name|m_model
operator|.
name|createResource
argument_list|(
name|NS
argument_list|)
decl_stmt|;
comment|/**<p>can be a Literal or Resource</p> */
specifier|public
specifier|static
specifier|final
name|Property
name|argument1
init|=
name|m_model
operator|.
name|createProperty
argument_list|(
literal|"http://www.w3.org/2003/11/swrl#argument1"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Property
name|argument2
init|=
name|m_model
operator|.
name|createProperty
argument_list|(
literal|"http://www.w3.org/2003/11/swrl#argument2"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Property
name|classPredicate
init|=
name|m_model
operator|.
name|createProperty
argument_list|(
literal|"http://www.w3.org/2003/11/swrl#classPredicate"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Property
name|propertyPredicate
init|=
name|m_model
operator|.
name|createProperty
argument_list|(
literal|"http://www.w3.org/2003/11/swrl#propertyPredicate"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Property
name|body
init|=
name|m_model
operator|.
name|createProperty
argument_list|(
literal|"http://www.w3.org/2003/11/swrl#body"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Property
name|head
init|=
name|m_model
operator|.
name|createProperty
argument_list|(
literal|"http://www.w3.org/2003/11/swrl#head"
argument_list|)
decl_stmt|;
comment|/**<p>common superclass</p> */
specifier|public
specifier|static
specifier|final
name|Resource
name|Atom
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://www.w3.org/2003/11/swrl#Atom"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|AtomList
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://www.w3.org/2003/11/swrl#AtomList"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|Builtin
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://www.w3.org/2003/11/swrl#Builtin"
argument_list|)
decl_stmt|;
comment|/**<p>consists of a builtin and a List of arguments</p> */
specifier|public
specifier|static
specifier|final
name|Resource
name|BuiltinAtom
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://www.w3.org/2003/11/swrl#BuiltinAtom"
argument_list|)
decl_stmt|;
comment|/**<p>consists of a classPredicate and argument1</p> */
specifier|public
specifier|static
specifier|final
name|Resource
name|ClassAtom
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://www.w3.org/2003/11/swrl#ClassAtom"
argument_list|)
decl_stmt|;
comment|/**<p>consists of a dataRange and argument1</p> */
specifier|public
specifier|static
specifier|final
name|Resource
name|DataRangeAtom
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://www.w3.org/2003/11/swrl#DataRangeAtom"
argument_list|)
decl_stmt|;
comment|/**<p>consists of a propertyPredicate (owl:DatatypeProperty), argument1 (owl:Thing),       *  and argument2 (rdfs:Literal)</p>      */
specifier|public
specifier|static
specifier|final
name|Resource
name|DatavaluedPropertyAtom
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://www.w3.org/2003/11/swrl#DatavaluedPropertyAtom"
argument_list|)
decl_stmt|;
comment|/**<p>consists of argument1 (owl:Thing) and argument2 (owl:Thing)</p> */
specifier|public
specifier|static
specifier|final
name|Resource
name|DifferentIndividualsAtom
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://www.w3.org/2003/11/swrl#DifferentIndividualsAtom"
argument_list|)
decl_stmt|;
comment|/**<p>implication (rule)</p> */
specifier|public
specifier|static
specifier|final
name|Resource
name|Imp
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://www.w3.org/2003/11/swrl#Imp"
argument_list|)
decl_stmt|;
comment|/**<p>consists of a propertyPredicate (owl:ObjectProperty), argument1 (owl:Thing),       *  and argument2 (owl:Thing)</p>      */
specifier|public
specifier|static
specifier|final
name|Resource
name|IndividualPropertyAtom
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://www.w3.org/2003/11/swrl#IndividualPropertyAtom"
argument_list|)
decl_stmt|;
comment|/**<p>consists of argument1 (owl:Thing) and argument2 (owl:Thing)</p> */
specifier|public
specifier|static
specifier|final
name|Resource
name|SameIndividualAtom
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://www.w3.org/2003/11/swrl#SameIndividualAtom"
argument_list|)
decl_stmt|;
comment|/**<p>indicate that a URI is being used as a variable</p> */
specifier|public
specifier|static
specifier|final
name|Resource
name|Variable
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://www.w3.org/2003/11/swrl#Variable"
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|Resource
name|nil
init|=
name|m_model
operator|.
name|createResource
argument_list|(
literal|"http://www.w3.org/1999/02/22-rdf-syntax-ns#nil"
argument_list|)
decl_stmt|;
block|}
end_class

end_unit

