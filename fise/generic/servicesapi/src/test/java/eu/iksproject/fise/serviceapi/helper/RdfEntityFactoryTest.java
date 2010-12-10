begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|serviceapi
operator|.
name|helper
package|;
end_package

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertEquals
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertTrue
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URI
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
import|;
end_import

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
name|Arrays
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Calendar
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Date
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
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
name|Set
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
name|core
operator|.
name|MGraph
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
name|core
operator|.
name|Resource
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
name|core
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
name|rdf
operator|.
name|core
operator|.
name|UriRef
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
name|core
operator|.
name|impl
operator|.
name|SimpleMGraph
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

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|servicesapi
operator|.
name|helper
operator|.
name|Rdf
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|servicesapi
operator|.
name|helper
operator|.
name|RdfEntity
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|servicesapi
operator|.
name|helper
operator|.
name|RdfEntityFactory
import|;
end_import

begin_import
import|import
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|servicesapi
operator|.
name|rdf
operator|.
name|Properties
import|;
end_import

begin_comment
comment|/**  * Tests the Factory, basic RdfEntity Methods and all features supported for  * Interfaces.  *  * @author Rupert Westenthaler  */
end_comment

begin_class
specifier|public
class|class
name|RdfEntityFactoryTest
block|{
annotation|@
name|Test
specifier|public
name|void
name|testRdfEntity
parameter_list|()
throws|throws
name|Exception
block|{
name|MGraph
name|graph
init|=
operator|new
name|SimpleMGraph
argument_list|()
decl_stmt|;
name|RdfEntityFactory
name|factory
init|=
name|RdfEntityFactory
operator|.
name|createInstance
argument_list|(
name|graph
argument_list|)
decl_stmt|;
name|String
name|testUri
init|=
literal|"urn:RdfEntityFactoryTest:TestEntity"
decl_stmt|;
name|UriRef
name|node
init|=
operator|new
name|UriRef
argument_list|(
name|testUri
argument_list|)
decl_stmt|;
name|RdfEntity
name|rdfEntity
init|=
name|factory
operator|.
name|getProxy
argument_list|(
name|node
argument_list|,
name|RdfEntity
operator|.
name|class
argument_list|)
decl_stmt|;
comment|//TODO: Test type statement
comment|//TODO: test getID Method
name|assertEquals
argument_list|(
name|rdfEntity
operator|.
name|getId
argument_list|()
argument_list|,
name|node
argument_list|)
expr_stmt|;
comment|//TODO: Test equals
name|RdfEntity
name|rdfEntity2
init|=
name|factory
operator|.
name|getProxy
argument_list|(
name|node
argument_list|,
name|RdfEntity
operator|.
name|class
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|rdfEntity
argument_list|,
name|rdfEntity2
argument_list|)
expr_stmt|;
comment|//TODO: Test hashCode
name|assertEquals
argument_list|(
name|rdfEntity
operator|.
name|hashCode
argument_list|()
argument_list|,
name|rdfEntity2
operator|.
name|hashCode
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testPrimitiveDataTypes
parameter_list|()
throws|throws
name|Exception
block|{
name|MGraph
name|graph
init|=
operator|new
name|SimpleMGraph
argument_list|()
decl_stmt|;
name|RdfEntityFactory
name|factory
init|=
name|RdfEntityFactory
operator|.
name|createInstance
argument_list|(
name|graph
argument_list|)
decl_stmt|;
name|String
name|testUri
init|=
literal|"urn:RdfEntityFactoryTest:TestEntity"
decl_stmt|;
name|UriRef
name|node
init|=
operator|new
name|UriRef
argument_list|(
name|testUri
argument_list|)
decl_stmt|;
name|TestRdfEntity
name|testEntity
init|=
name|factory
operator|.
name|getProxy
argument_list|(
name|node
argument_list|,
name|TestRdfEntity
operator|.
name|class
argument_list|)
decl_stmt|;
name|testEntity
operator|.
name|setBoolean
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|testEntity
operator|.
name|getBoolean
argument_list|()
argument_list|)
expr_stmt|;
name|testEntity
operator|.
name|setInteger
argument_list|(
literal|10
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
operator|new
name|Integer
argument_list|(
literal|10
argument_list|)
argument_list|,
name|testEntity
operator|.
name|getInteger
argument_list|()
argument_list|)
expr_stmt|;
name|testEntity
operator|.
name|setLong
argument_list|(
literal|20l
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
operator|new
name|Long
argument_list|(
literal|20
argument_list|)
argument_list|,
name|testEntity
operator|.
name|getLong
argument_list|()
argument_list|)
expr_stmt|;
comment|//TODO: Not supported by org.apache.clerezza.rdf.core.impl.SimpleLiteralFactory!
comment|//testEntity.setFloat(0.1f);
comment|//assertTrue(new Float(0.1f).equals(testEntity.getFloat()));
name|testEntity
operator|.
name|setDouble
argument_list|(
literal|0.2
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
operator|new
name|Double
argument_list|(
literal|0.2
argument_list|)
argument_list|,
name|testEntity
operator|.
name|getDouble
argument_list|()
argument_list|)
expr_stmt|;
name|testEntity
operator|.
name|setString
argument_list|(
literal|"Test!"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Test!"
argument_list|,
name|testEntity
operator|.
name|getString
argument_list|()
argument_list|)
expr_stmt|;
name|Date
name|currentDate
init|=
operator|new
name|Date
argument_list|()
decl_stmt|;
name|testEntity
operator|.
name|setDate
argument_list|(
name|currentDate
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|currentDate
argument_list|,
name|testEntity
operator|.
name|getDate
argument_list|()
argument_list|)
expr_stmt|;
name|testEntity
operator|.
name|setIntegers
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|Integer
argument_list|(
literal|1
argument_list|)
argument_list|,
operator|new
name|Integer
argument_list|(
literal|2
argument_list|)
argument_list|,
operator|new
name|Integer
argument_list|(
literal|3
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|Collection
argument_list|<
name|Integer
argument_list|>
name|integers
init|=
name|testEntity
operator|.
name|getIntegers
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|integers
operator|.
name|contains
argument_list|(
operator|new
name|Integer
argument_list|(
literal|1
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|integers
operator|.
name|contains
argument_list|(
operator|new
name|Integer
argument_list|(
literal|2
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|integers
operator|.
name|contains
argument_list|(
operator|new
name|Integer
argument_list|(
literal|3
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|//test Remove
name|integers
operator|.
name|remove
argument_list|(
operator|new
name|Integer
argument_list|(
literal|2
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|integers
operator|.
name|contains
argument_list|(
operator|new
name|Integer
argument_list|(
literal|1
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
operator|!
name|integers
operator|.
name|contains
argument_list|(
operator|new
name|Integer
argument_list|(
literal|2
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|integers
operator|.
name|contains
argument_list|(
operator|new
name|Integer
argument_list|(
literal|3
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|//get an new Collection and repeat the test
name|integers
operator|=
name|testEntity
operator|.
name|getIntegers
argument_list|()
expr_stmt|;
name|assertTrue
argument_list|(
name|integers
operator|.
name|contains
argument_list|(
operator|new
name|Integer
argument_list|(
literal|1
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
operator|!
name|integers
operator|.
name|contains
argument_list|(
operator|new
name|Integer
argument_list|(
literal|2
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|integers
operator|.
name|contains
argument_list|(
operator|new
name|Integer
argument_list|(
literal|3
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|//test Add
name|integers
operator|.
name|add
argument_list|(
operator|new
name|Integer
argument_list|(
operator|-
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|integers
operator|.
name|contains
argument_list|(
operator|new
name|Integer
argument_list|(
operator|-
literal|1
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|integers
operator|.
name|contains
argument_list|(
operator|new
name|Integer
argument_list|(
literal|1
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|integers
operator|.
name|contains
argument_list|(
operator|new
name|Integer
argument_list|(
literal|3
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|//again get a new collection
name|Collection
argument_list|<
name|Integer
argument_list|>
name|integers2
init|=
name|testEntity
operator|.
name|getIntegers
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|integers2
operator|.
name|contains
argument_list|(
operator|new
name|Integer
argument_list|(
operator|-
literal|1
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|integers2
operator|.
name|contains
argument_list|(
operator|new
name|Integer
argument_list|(
literal|1
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|integers2
operator|.
name|contains
argument_list|(
operator|new
name|Integer
argument_list|(
literal|3
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|//remove/add an value in integers and test in integers2
name|integers
operator|.
name|remove
argument_list|(
operator|new
name|Integer
argument_list|(
literal|3
argument_list|)
argument_list|)
expr_stmt|;
name|integers
operator|.
name|add
argument_list|(
operator|new
name|Integer
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|integers2
operator|.
name|contains
argument_list|(
operator|new
name|Integer
argument_list|(
operator|-
literal|1
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|integers2
operator|.
name|contains
argument_list|(
operator|new
name|Integer
argument_list|(
literal|0
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|integers2
operator|.
name|contains
argument_list|(
operator|new
name|Integer
argument_list|(
literal|1
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
operator|!
name|integers2
operator|.
name|contains
argument_list|(
operator|new
name|Integer
argument_list|(
literal|2
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
operator|!
name|integers2
operator|.
name|contains
argument_list|(
operator|new
name|Integer
argument_list|(
literal|3
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testTypeStatements
parameter_list|()
throws|throws
name|Exception
block|{
name|MGraph
name|graph
init|=
operator|new
name|SimpleMGraph
argument_list|()
decl_stmt|;
name|RdfEntityFactory
name|factory
init|=
name|RdfEntityFactory
operator|.
name|createInstance
argument_list|(
name|graph
argument_list|)
decl_stmt|;
name|String
name|testUri
init|=
literal|"urn:RdfEntityFactoryTest:TestEntity"
decl_stmt|;
name|UriRef
name|node
init|=
operator|new
name|UriRef
argument_list|(
name|testUri
argument_list|)
decl_stmt|;
name|TestRdfEntity
name|entity
init|=
name|factory
operator|.
name|getProxy
argument_list|(
name|node
argument_list|,
name|TestRdfEntity
operator|.
name|class
argument_list|,
operator|new
name|Class
index|[]
block|{
name|TestRdfEntity2
operator|.
name|class
block|}
argument_list|)
decl_stmt|;
comment|// test the if the proxy implements both interfaces
name|assertTrue
argument_list|(
name|entity
operator|instanceof
name|TestRdfEntity
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|entity
operator|instanceof
name|TestRdfEntity2
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|typeStrings
init|=
name|getRdfTypes
argument_list|(
name|graph
argument_list|,
name|node
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|typeStrings
operator|.
name|contains
argument_list|(
name|TestRdfEntity
operator|.
name|class
operator|.
name|getAnnotation
argument_list|(
name|Rdf
operator|.
name|class
argument_list|)
operator|.
name|id
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|typeStrings
operator|.
name|contains
argument_list|(
name|TestRdfEntity2
operator|.
name|class
operator|.
name|getAnnotation
argument_list|(
name|Rdf
operator|.
name|class
argument_list|)
operator|.
name|id
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testObjectProperties
parameter_list|()
throws|throws
name|Exception
block|{
name|MGraph
name|graph
init|=
operator|new
name|SimpleMGraph
argument_list|()
decl_stmt|;
name|RdfEntityFactory
name|factory
init|=
name|RdfEntityFactory
operator|.
name|createInstance
argument_list|(
name|graph
argument_list|)
decl_stmt|;
name|String
name|testUri
init|=
literal|"urn:RdfEntityFactoryTest:TestEntity"
decl_stmt|;
name|String
name|testUri2
init|=
literal|"urn:RdfEntityFactoryTest:TestEntity2"
decl_stmt|;
name|UriRef
name|node
init|=
operator|new
name|UriRef
argument_list|(
name|testUri
argument_list|)
decl_stmt|;
name|UriRef
name|node2
init|=
operator|new
name|UriRef
argument_list|(
name|testUri2
argument_list|)
decl_stmt|;
name|TestRdfEntity
name|entity
init|=
name|factory
operator|.
name|getProxy
argument_list|(
name|node
argument_list|,
name|TestRdfEntity
operator|.
name|class
argument_list|)
decl_stmt|;
name|TestRdfEntity2
name|entity2
init|=
name|factory
operator|.
name|getProxy
argument_list|(
name|node2
argument_list|,
name|TestRdfEntity2
operator|.
name|class
argument_list|)
decl_stmt|;
name|URI
name|testURI
init|=
operator|new
name|URI
argument_list|(
literal|"urn:test:URI"
argument_list|)
decl_stmt|;
name|entity
operator|.
name|setURI
argument_list|(
name|testURI
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|testURI
argument_list|,
name|entity
operator|.
name|getURI
argument_list|()
argument_list|)
expr_stmt|;
name|URL
name|testURL
init|=
operator|new
name|URL
argument_list|(
literal|"http://www.iks-project.eu"
argument_list|)
decl_stmt|;
name|entity
operator|.
name|setURL
argument_list|(
name|testURL
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|testURL
argument_list|,
name|entity
operator|.
name|getURL
argument_list|()
argument_list|)
expr_stmt|;
name|entity
operator|.
name|setUriRef
argument_list|(
name|node2
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|node2
argument_list|,
name|entity
operator|.
name|getUriRef
argument_list|()
argument_list|)
expr_stmt|;
name|entity2
operator|.
name|setTestEntity
argument_list|(
name|entity
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|entity
argument_list|,
name|entity2
operator|.
name|getTestEntity
argument_list|()
argument_list|)
expr_stmt|;
name|Collection
argument_list|<
name|TestRdfEntity
argument_list|>
name|testEntities
init|=
name|entity2
operator|.
name|getTestEntities
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|testEntities
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
comment|//check that entity is not in the collection
name|Set
argument_list|<
name|UriRef
argument_list|>
name|testUriRefs
init|=
operator|new
name|HashSet
argument_list|<
name|UriRef
argument_list|>
argument_list|()
decl_stmt|;
name|int
name|NUM
init|=
literal|10
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|NUM
condition|;
name|i
operator|++
control|)
block|{
name|UriRef
name|testNode
init|=
operator|new
name|UriRef
argument_list|(
name|testUri
operator|+
literal|':'
operator|+
literal|'_'
operator|+
name|i
argument_list|)
decl_stmt|;
name|testUriRefs
operator|.
name|add
argument_list|(
name|testNode
argument_list|)
expr_stmt|;
name|testEntities
operator|.
name|add
argument_list|(
name|factory
operator|.
name|getProxy
argument_list|(
name|testNode
argument_list|,
name|TestRdfEntity
operator|.
name|class
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|//now get a new collection and test if the added entities are there
name|Collection
argument_list|<
name|UriRef
argument_list|>
name|resultUriRefs
init|=
operator|new
name|ArrayList
argument_list|<
name|UriRef
argument_list|>
argument_list|()
decl_stmt|;
comment|//add to a list to check for duplicates
for|for
control|(
name|TestRdfEntity
name|e
range|:
name|entity2
operator|.
name|getTestEntities
argument_list|()
control|)
block|{
name|assertTrue
argument_list|(
name|e
operator|.
name|getId
argument_list|()
operator|instanceof
name|UriRef
argument_list|)
expr_stmt|;
comment|//I used UriRefs for the generation ...
name|resultUriRefs
operator|.
name|add
argument_list|(
operator|(
name|UriRef
operator|)
name|e
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|//now cross check
name|assertTrue
argument_list|(
name|testUriRefs
operator|.
name|containsAll
argument_list|(
name|resultUriRefs
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|resultUriRefs
operator|.
name|containsAll
argument_list|(
name|testUriRefs
argument_list|)
argument_list|)
expr_stmt|;
comment|//now one could try to remove some Elements ...
comment|// ... but things like that are already tested for Integers in testPrimitiveDataTypes
block|}
annotation|@
name|Test
specifier|public
name|void
name|testInterfaceHierarchies
parameter_list|()
throws|throws
name|Exception
block|{
name|MGraph
name|graph
init|=
operator|new
name|SimpleMGraph
argument_list|()
decl_stmt|;
name|RdfEntityFactory
name|factory
init|=
name|RdfEntityFactory
operator|.
name|createInstance
argument_list|(
name|graph
argument_list|)
decl_stmt|;
name|String
name|testUri
init|=
literal|"urn:RdfEntityFactoryTest:SubTestEntity"
decl_stmt|;
name|String
name|testUri2
init|=
literal|"urn:RdfEntityFactoryTest:TestEntity2"
decl_stmt|;
name|String
name|testUri3
init|=
literal|"urn:RdfEntityFactoryTest:TestEntity"
decl_stmt|;
name|UriRef
name|node
init|=
operator|new
name|UriRef
argument_list|(
name|testUri
argument_list|)
decl_stmt|;
name|UriRef
name|node2
init|=
operator|new
name|UriRef
argument_list|(
name|testUri2
argument_list|)
decl_stmt|;
name|UriRef
name|node3
init|=
operator|new
name|UriRef
argument_list|(
name|testUri3
argument_list|)
decl_stmt|;
name|SubTestRdfEntity
name|entity
init|=
name|factory
operator|.
name|getProxy
argument_list|(
name|node
argument_list|,
name|SubTestRdfEntity
operator|.
name|class
argument_list|)
decl_stmt|;
name|TestRdfEntity
name|entity2
init|=
name|factory
operator|.
name|getProxy
argument_list|(
name|node2
argument_list|,
name|TestRdfEntity
operator|.
name|class
argument_list|,
operator|new
name|Class
argument_list|<
name|?
argument_list|>
index|[]
block|{
name|SubTestRdfEntity
operator|.
name|class
operator|,
name|TestRdfEntity2
operator|.
name|class
block|}
block|)
function|;
name|TestRdfEntity
name|entity3
init|=
name|factory
operator|.
name|getProxy
argument_list|(
name|node3
argument_list|,
name|TestRdfEntity
operator|.
name|class
argument_list|)
decl_stmt|;
comment|//Start with checking the types for entity2
comment|//first type cast to the hierarchy
name|assertTrue
argument_list|(
name|entity
operator|instanceof
name|TestRdfEntity
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|entity
operator|instanceof
name|RdfEntity
argument_list|)
expr_stmt|;
comment|// test if the rdf:type triples are present in the MGraph
name|Set
argument_list|<
name|String
argument_list|>
name|typeStrings
init|=
name|getRdfTypes
argument_list|(
name|graph
argument_list|,
name|node
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|typeStrings
operator|.
name|contains
argument_list|(
name|SubTestRdfEntity
operator|.
name|class
operator|.
name|getAnnotation
argument_list|(
name|Rdf
operator|.
name|class
argument_list|)
operator|.
name|id
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|typeStrings
operator|.
name|contains
argument_list|(
name|TestRdfEntity
operator|.
name|class
operator|.
name|getAnnotation
argument_list|(
name|Rdf
operator|.
name|class
argument_list|)
operator|.
name|id
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|typeStrings
operator|=
literal|null
expr_stmt|;
comment|//now the same for entity2
comment|//first type cast to the hierarchy
name|assertTrue
argument_list|(
name|entity2
operator|instanceof
name|SubTestRdfEntity
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|entity2
operator|instanceof
name|TestRdfEntity2
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|entity2
operator|instanceof
name|RdfEntity
argument_list|)
expr_stmt|;
comment|// test if the rdf:type triples are present in the MGraph
name|typeStrings
operator|=
name|getRdfTypes
argument_list|(
name|graph
argument_list|,
name|node2
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|typeStrings
operator|.
name|contains
argument_list|(
name|SubTestRdfEntity
operator|.
name|class
operator|.
name|getAnnotation
argument_list|(
name|Rdf
operator|.
name|class
argument_list|)
operator|.
name|id
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|typeStrings
operator|.
name|contains
argument_list|(
name|TestRdfEntity
operator|.
name|class
operator|.
name|getAnnotation
argument_list|(
name|Rdf
operator|.
name|class
argument_list|)
operator|.
name|id
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|typeStrings
operator|.
name|contains
argument_list|(
name|TestRdfEntity2
operator|.
name|class
operator|.
name|getAnnotation
argument_list|(
name|Rdf
operator|.
name|class
argument_list|)
operator|.
name|id
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|typeStrings
operator|=
literal|null
expr_stmt|;
comment|//Now check Entity3
name|assertTrue
argument_list|(
operator|!
operator|(
name|entity3
operator|instanceof
name|SubTestRdfEntity
operator|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|entity3
operator|instanceof
name|TestRdfEntity
argument_list|)
expr_stmt|;
comment|//Now create an new Entity for the same Node that implements SubEntity2
name|SubTestRdfEntity
name|entity4
init|=
name|factory
operator|.
name|getProxy
argument_list|(
name|node3
argument_list|,
name|SubTestRdfEntity
operator|.
name|class
argument_list|)
decl_stmt|;
comment|//check if entity4 implements SubTestRefEntity
name|assertTrue
argument_list|(
name|entity4
operator|instanceof
name|SubTestRdfEntity
argument_list|)
expr_stmt|;
comment|//now check if the additional type was added to node3
name|typeStrings
operator|=
name|getRdfTypes
argument_list|(
name|graph
argument_list|,
name|node3
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|typeStrings
operator|.
name|contains
argument_list|(
name|SubTestRdfEntity
operator|.
name|class
operator|.
name|getAnnotation
argument_list|(
name|Rdf
operator|.
name|class
argument_list|)
operator|.
name|id
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|typeStrings
operator|.
name|contains
argument_list|(
name|TestRdfEntity
operator|.
name|class
operator|.
name|getAnnotation
argument_list|(
name|Rdf
operator|.
name|class
argument_list|)
operator|.
name|id
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|//and that entity3 still dose not implement SubTestEntity
comment|// ... because adding/removing rdf:type triples in the graph can not affect existing proxy instances!
name|assertTrue
argument_list|(
operator|!
operator|(
name|entity3
operator|instanceof
name|SubTestRdfEntity
operator|)
argument_list|)
expr_stmt|;
block|}
end_class

begin_function
specifier|private
specifier|static
name|Set
argument_list|<
name|String
argument_list|>
name|getRdfTypes
parameter_list|(
name|MGraph
name|graph
parameter_list|,
name|UriRef
name|node
parameter_list|)
block|{
name|Iterator
argument_list|<
name|Triple
argument_list|>
name|typeStatements
init|=
name|graph
operator|.
name|filter
argument_list|(
name|node
argument_list|,
name|Properties
operator|.
name|RDF_TYPE
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|typeStrings
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
while|while
condition|(
name|typeStatements
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Resource
name|type
init|=
name|typeStatements
operator|.
name|next
argument_list|()
operator|.
name|getObject
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|type
operator|instanceof
name|UriRef
argument_list|)
expr_stmt|;
name|typeStrings
operator|.
name|add
argument_list|(
operator|(
operator|(
name|UriRef
operator|)
name|type
operator|)
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|typeStrings
return|;
block|}
end_function

begin_comment
comment|/** 	 * Interface to test primitive Datatypes and Uri links.      * 	 * @author westei 	 */
end_comment

begin_interface
annotation|@
name|Rdf
argument_list|(
name|id
operator|=
literal|"urn:test:TestRdfEntity"
argument_list|)
specifier|public
specifier|static
interface|interface
name|TestRdfEntity
extends|extends
name|RdfEntity
block|{
annotation|@
name|Rdf
argument_list|(
name|id
operator|=
literal|"urn:test:Integer"
argument_list|)
name|Integer
name|getInteger
parameter_list|()
function_decl|;
annotation|@
name|Rdf
argument_list|(
name|id
operator|=
literal|"urn:test:Integer"
argument_list|)
name|void
name|setInteger
parameter_list|(
name|Integer
name|i
parameter_list|)
function_decl|;
annotation|@
name|Rdf
argument_list|(
name|id
operator|=
literal|"urn:test:Integers"
argument_list|)
name|Collection
argument_list|<
name|Integer
argument_list|>
name|getIntegers
parameter_list|()
function_decl|;
annotation|@
name|Rdf
argument_list|(
name|id
operator|=
literal|"urn:test:Integers"
argument_list|)
name|void
name|setIntegers
parameter_list|(
name|Collection
argument_list|<
name|Integer
argument_list|>
name|is
parameter_list|)
function_decl|;
annotation|@
name|Rdf
argument_list|(
name|id
operator|=
literal|"urn:test:Long"
argument_list|)
name|Long
name|getLong
parameter_list|()
function_decl|;
annotation|@
name|Rdf
argument_list|(
name|id
operator|=
literal|"urn:test:Long"
argument_list|)
name|void
name|setLong
parameter_list|(
name|Long
name|l
parameter_list|)
function_decl|;
annotation|@
name|Rdf
argument_list|(
name|id
operator|=
literal|"urn:test:Float"
argument_list|)
name|Float
name|getFloat
parameter_list|()
function_decl|;
annotation|@
name|Rdf
argument_list|(
name|id
operator|=
literal|"urn:test:Float"
argument_list|)
name|void
name|setFloat
parameter_list|(
name|Float
name|f
parameter_list|)
function_decl|;
annotation|@
name|Rdf
argument_list|(
name|id
operator|=
literal|"urn:test:Double"
argument_list|)
name|Double
name|getDouble
parameter_list|()
function_decl|;
annotation|@
name|Rdf
argument_list|(
name|id
operator|=
literal|"urn:test:Double"
argument_list|)
name|void
name|setDouble
parameter_list|(
name|Double
name|d
parameter_list|)
function_decl|;
annotation|@
name|Rdf
argument_list|(
name|id
operator|=
literal|"urn:test:Boolean"
argument_list|)
name|Boolean
name|getBoolean
parameter_list|()
function_decl|;
annotation|@
name|Rdf
argument_list|(
name|id
operator|=
literal|"urn:test:Boolean"
argument_list|)
name|void
name|setBoolean
parameter_list|(
name|Boolean
name|b
parameter_list|)
function_decl|;
annotation|@
name|Rdf
argument_list|(
name|id
operator|=
literal|"urn:test:Date"
argument_list|)
name|Date
name|getDate
parameter_list|()
function_decl|;
annotation|@
name|Rdf
argument_list|(
name|id
operator|=
literal|"urn:test:Date"
argument_list|)
name|void
name|setDate
parameter_list|(
name|Date
name|d
parameter_list|)
function_decl|;
annotation|@
name|Rdf
argument_list|(
name|id
operator|=
literal|"urn:test:String"
argument_list|)
name|String
name|getString
parameter_list|()
function_decl|;
annotation|@
name|Rdf
argument_list|(
name|id
operator|=
literal|"urn:test:String"
argument_list|)
name|void
name|setString
parameter_list|(
name|String
name|string
parameter_list|)
function_decl|;
annotation|@
name|Rdf
argument_list|(
name|id
operator|=
literal|"urn:test:Calendar"
argument_list|)
name|Calendar
name|getCalendar
parameter_list|()
function_decl|;
annotation|@
name|Rdf
argument_list|(
name|id
operator|=
literal|"urn:test:Calendar"
argument_list|)
name|void
name|setCalendar
parameter_list|(
name|Calendar
name|d
parameter_list|)
function_decl|;
annotation|@
name|Rdf
argument_list|(
name|id
operator|=
literal|"urn:test:URI"
argument_list|)
name|URI
name|getURI
parameter_list|()
function_decl|;
annotation|@
name|Rdf
argument_list|(
name|id
operator|=
literal|"urn:test:URI"
argument_list|)
name|void
name|setURI
parameter_list|(
name|URI
name|uri
parameter_list|)
function_decl|;
annotation|@
name|Rdf
argument_list|(
name|id
operator|=
literal|"urn:test:URL"
argument_list|)
name|URL
name|getURL
parameter_list|()
function_decl|;
annotation|@
name|Rdf
argument_list|(
name|id
operator|=
literal|"urn:test:URL"
argument_list|)
name|void
name|setURL
parameter_list|(
name|URL
name|uri
parameter_list|)
function_decl|;
annotation|@
name|Rdf
argument_list|(
name|id
operator|=
literal|"urn:test:UriRef"
argument_list|)
name|UriRef
name|getUriRef
parameter_list|()
function_decl|;
annotation|@
name|Rdf
argument_list|(
name|id
operator|=
literal|"urn:test:UriRef"
argument_list|)
name|void
name|setUriRef
parameter_list|(
name|UriRef
name|uriRef
parameter_list|)
function_decl|;
block|}
end_interface

begin_comment
comment|/** 	 * Interface to test relations to other RdfEntities.      * 	 * @author westei 	 */
end_comment

begin_interface
annotation|@
name|Rdf
argument_list|(
name|id
operator|=
literal|"urn:test:TestRdfEntity2"
argument_list|)
specifier|public
specifier|static
interface|interface
name|TestRdfEntity2
extends|extends
name|RdfEntity
block|{
annotation|@
name|Rdf
argument_list|(
name|id
operator|=
literal|"urn:test:RdfEntity"
argument_list|)
name|TestRdfEntity
name|getTestEntity
parameter_list|()
function_decl|;
annotation|@
name|Rdf
argument_list|(
name|id
operator|=
literal|"urn:test:RdfEntity"
argument_list|)
name|void
name|setTestEntity
parameter_list|(
name|TestRdfEntity
name|testRdfEntity
parameter_list|)
function_decl|;
annotation|@
name|Rdf
argument_list|(
name|id
operator|=
literal|"urn:test:RdfEntities"
argument_list|)
name|Collection
argument_list|<
name|TestRdfEntity
argument_list|>
name|getTestEntities
parameter_list|()
function_decl|;
annotation|@
name|Rdf
argument_list|(
name|id
operator|=
literal|"urn:test:RdfEntities"
argument_list|)
name|void
name|setTestEntities
parameter_list|(
name|Collection
argument_list|<
name|TestRdfEntity
argument_list|>
name|entities
parameter_list|)
function_decl|;
block|}
end_interface

begin_comment
comment|/** 	 * Interface to test extends relations between Interfaces.      * 	 * @author westei 	 */
end_comment

begin_interface
annotation|@
name|Rdf
argument_list|(
name|id
operator|=
literal|"urn:test:SubTestRdfEntity"
argument_list|)
specifier|public
specifier|static
interface|interface
name|SubTestRdfEntity
extends|extends
name|TestRdfEntity
block|{
annotation|@
name|Rdf
argument_list|(
name|id
operator|=
literal|"urn:test:RdfEntity2"
argument_list|)
name|TestRdfEntity2
name|getTestEntity2
parameter_list|()
function_decl|;
annotation|@
name|Rdf
argument_list|(
name|id
operator|=
literal|"urn:test:RdfEntity2"
argument_list|)
name|void
name|setTestEntity2
parameter_list|(
name|TestRdfEntity2
name|entity2
parameter_list|)
function_decl|;
block|}
end_interface

begin_comment
comment|//	public static void main(String[] args) throws Exception{
end_comment

begin_comment
comment|//		new RdfEntityFactoryTest().testTypeStatements();
end_comment

begin_comment
comment|//	}
end_comment

unit|}
end_unit

