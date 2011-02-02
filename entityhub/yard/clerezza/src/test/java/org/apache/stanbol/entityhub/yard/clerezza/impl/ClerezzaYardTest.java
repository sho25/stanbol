begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|yard
operator|.
name|clerezza
operator|.
name|impl
package|;
end_package

begin_import
import|import
name|junit
operator|.
name|framework
operator|.
name|Assert
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
name|rdf
operator|.
name|ontologies
operator|.
name|RDF
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
name|core
operator|.
name|yard
operator|.
name|SimpleYardConfig
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
name|core
operator|.
name|yard
operator|.
name|AbstractYard
operator|.
name|YardConfig
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
name|Reference
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
name|Representation
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
name|ValueFactory
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
name|yard
operator|.
name|Yard
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
name|yard
operator|.
name|YardException
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
name|test
operator|.
name|yard
operator|.
name|YardTest
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Before
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Test
import|;
end_import

begin_class
specifier|public
class|class
name|ClerezzaYardTest
extends|extends
name|YardTest
block|{
specifier|private
name|Yard
name|yard
decl_stmt|;
annotation|@
name|Before
specifier|public
specifier|final
name|void
name|initYard
parameter_list|()
block|{
name|YardConfig
name|config
init|=
operator|new
name|SimpleYardConfig
argument_list|(
literal|"urn:yard.clerezza:testYardId"
argument_list|)
decl_stmt|;
name|config
operator|.
name|setName
argument_list|(
literal|"Clerezza Yard Test"
argument_list|)
expr_stmt|;
name|config
operator|.
name|setDescription
argument_list|(
literal|"The Clerezza Yard instance used to execute the Unit Tests defined for the Yard Interface"
argument_list|)
expr_stmt|;
name|yard
operator|=
operator|new
name|ClerezzaYard
argument_list|(
name|config
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|protected
name|Yard
name|getYard
parameter_list|()
block|{
return|return
name|yard
return|;
block|}
comment|/**      * The Clerezza Yard uses the Statement<br>      *<code>representationId -> rdf:type -> Representation</code><br>      * to identify that an UriRef in the RDF graph (MGraph) represents a      * Representation. This Triple is added when a Representation is stored and      * removed if retrieved from the Yard.<p>      * This tests if this functions as expected      * @throws YardException      */
annotation|@
name|Test
specifier|public
name|void
name|testRemovalOfTypeRepresentationStatement
parameter_list|()
throws|throws
name|YardException
block|{
name|Yard
name|yard
init|=
name|getYard
argument_list|()
decl_stmt|;
name|ValueFactory
name|vf
init|=
name|yard
operator|.
name|getValueFactory
argument_list|()
decl_stmt|;
name|Reference
name|representationType
init|=
name|vf
operator|.
name|createReference
argument_list|(
name|RdfResourceEnum
operator|.
name|Representation
operator|.
name|getUri
argument_list|()
argument_list|)
decl_stmt|;
name|Representation
name|test
init|=
name|create
argument_list|()
decl_stmt|;
comment|//the rdf:type Representation MUST NOT be within the Representation
name|Assert
operator|.
name|assertFalse
argument_list|(
name|test
operator|.
name|get
argument_list|(
name|RDF
operator|.
name|type
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
operator|.
name|hasNext
argument_list|()
argument_list|)
expr_stmt|;
comment|//now add the statement and see if an IllegalStateException is thrown
comment|/*          * The triple within this Statement is internally used to "mark" the          * URI of the Representation as           */
name|test
operator|.
name|add
argument_list|(
name|RDF
operator|.
name|type
operator|.
name|getUnicodeString
argument_list|()
argument_list|,
name|representationType
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

