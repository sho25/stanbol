begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* * Licensed to the Apache Software Foundation (ASF) under one or more * contributor license agreements.  See the NOTICE file distributed with * this work for additional information regarding copyright ownership. * The ASF licenses this file to You under the Apache License, Version 2.0 * (the "License"); you may not use this file except in compliance with * the License.  You may obtain a copy of the License at * *     http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable law or agreed to in writing, software * distributed under the License is distributed on an "AS IS" BASIS, * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * See the License for the specific language governing permissions and * limitations under the License. */
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
name|model
operator|.
name|clerezza
operator|.
name|impl
package|;
end_package

begin_import
import|import
name|java
operator|.
name|math
operator|.
name|BigDecimal
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
name|impl
operator|.
name|utils
operator|.
name|TripleImpl
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
name|LiteralFactory
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
name|commons
operator|.
name|indexedgraph
operator|.
name|IndexedGraph
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
name|model
operator|.
name|clerezza
operator|.
name|RdfValueFactory
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
name|junit
operator|.
name|Assert
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
name|ResourceAdapterTest
block|{
comment|/**      * Test related to STANBOL-698      */
annotation|@
name|Test
specifier|public
name|void
name|testDouble
parameter_list|()
block|{
name|Graph
name|graph
init|=
operator|new
name|IndexedGraph
argument_list|()
decl_stmt|;
name|IRI
name|id
init|=
operator|new
name|IRI
argument_list|(
literal|"http://www.example.org/test"
argument_list|)
decl_stmt|;
name|IRI
name|doubleTestField
init|=
operator|new
name|IRI
argument_list|(
literal|"http://www.example.org/field/double"
argument_list|)
decl_stmt|;
name|LiteralFactory
name|lf
init|=
name|LiteralFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|graph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|id
argument_list|,
name|doubleTestField
argument_list|,
name|lf
operator|.
name|createTypedLiteral
argument_list|(
name|Double
operator|.
name|NaN
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|graph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|id
argument_list|,
name|doubleTestField
argument_list|,
name|lf
operator|.
name|createTypedLiteral
argument_list|(
name|Double
operator|.
name|POSITIVE_INFINITY
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|graph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|id
argument_list|,
name|doubleTestField
argument_list|,
name|lf
operator|.
name|createTypedLiteral
argument_list|(
name|Double
operator|.
name|NEGATIVE_INFINITY
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|RdfValueFactory
name|vf
init|=
operator|new
name|RdfValueFactory
argument_list|(
name|graph
argument_list|)
decl_stmt|;
name|Representation
name|r
init|=
name|vf
operator|.
name|createRepresentation
argument_list|(
name|id
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|Double
argument_list|>
name|expected
init|=
operator|new
name|HashSet
argument_list|<
name|Double
argument_list|>
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|Double
operator|.
name|NaN
argument_list|,
name|Double
operator|.
name|POSITIVE_INFINITY
argument_list|,
name|Double
operator|.
name|NEGATIVE_INFINITY
argument_list|)
argument_list|)
decl_stmt|;
name|Iterator
argument_list|<
name|Double
argument_list|>
name|dit
init|=
name|r
operator|.
name|get
argument_list|(
name|doubleTestField
operator|.
name|getUnicodeString
argument_list|()
argument_list|,
name|Double
operator|.
name|class
argument_list|)
decl_stmt|;
while|while
condition|(
name|dit
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Double
name|val
init|=
name|dit
operator|.
name|next
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|val
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|expected
operator|.
name|remove
argument_list|(
name|val
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|Assert
operator|.
name|assertTrue
argument_list|(
name|expected
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|testFloat
parameter_list|()
block|{
name|Graph
name|graph
init|=
operator|new
name|IndexedGraph
argument_list|()
decl_stmt|;
name|IRI
name|id
init|=
operator|new
name|IRI
argument_list|(
literal|"http://www.example.org/test"
argument_list|)
decl_stmt|;
name|IRI
name|doubleTestField
init|=
operator|new
name|IRI
argument_list|(
literal|"http://www.example.org/field/double"
argument_list|)
decl_stmt|;
name|LiteralFactory
name|lf
init|=
name|LiteralFactory
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|graph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|id
argument_list|,
name|doubleTestField
argument_list|,
name|lf
operator|.
name|createTypedLiteral
argument_list|(
name|Float
operator|.
name|NaN
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|graph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|id
argument_list|,
name|doubleTestField
argument_list|,
name|lf
operator|.
name|createTypedLiteral
argument_list|(
name|Float
operator|.
name|POSITIVE_INFINITY
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|graph
operator|.
name|add
argument_list|(
operator|new
name|TripleImpl
argument_list|(
name|id
argument_list|,
name|doubleTestField
argument_list|,
name|lf
operator|.
name|createTypedLiteral
argument_list|(
name|Float
operator|.
name|NEGATIVE_INFINITY
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|RdfValueFactory
name|vf
init|=
operator|new
name|RdfValueFactory
argument_list|(
name|graph
argument_list|)
decl_stmt|;
name|Representation
name|r
init|=
name|vf
operator|.
name|createRepresentation
argument_list|(
name|id
operator|.
name|getUnicodeString
argument_list|()
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|Float
argument_list|>
name|expected
init|=
operator|new
name|HashSet
argument_list|<
name|Float
argument_list|>
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|Float
operator|.
name|NaN
argument_list|,
name|Float
operator|.
name|POSITIVE_INFINITY
argument_list|,
name|Float
operator|.
name|NEGATIVE_INFINITY
argument_list|)
argument_list|)
decl_stmt|;
name|Iterator
argument_list|<
name|Float
argument_list|>
name|dit
init|=
name|r
operator|.
name|get
argument_list|(
name|doubleTestField
operator|.
name|getUnicodeString
argument_list|()
argument_list|,
name|Float
operator|.
name|class
argument_list|)
decl_stmt|;
while|while
condition|(
name|dit
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Float
name|val
init|=
name|dit
operator|.
name|next
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|val
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|expected
operator|.
name|remove
argument_list|(
name|val
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|Assert
operator|.
name|assertTrue
argument_list|(
name|expected
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// TODO: how to create NAN, POSITIVE_INFINITY, NEGATIVE_INVINITY instances for BigDecimal
comment|//    @Test
comment|//    public void testBigDecimal(){
comment|//        Graph graph = new IndexedGraph();
comment|//        IRI id = new IRI("http://www.example.org/test");
comment|//        IRI doubleTestField = new IRI("http://www.example.org/field/double");
comment|//        LiteralFactory lf = LiteralFactory.getInstance();
comment|//        graph.add(new TripleImpl(id, doubleTestField, lf.createTypedLiteral(BigDecimal.valueOf(Double.NaN))));
comment|//        graph.add(new TripleImpl(id, doubleTestField, lf.createTypedLiteral(BigDecimal.valueOf(Double.POSITIVE_INFINITY))));
comment|//        graph.add(new TripleImpl(id, doubleTestField, lf.createTypedLiteral(BigDecimal.valueOf(Double.NEGATIVE_INFINITY))));
comment|//
comment|//        RdfValueFactory vf = new RdfValueFactory(graph);
comment|//        Representation r = vf.createRepresentation(id.getUnicodeString());
comment|//        Set<BigDecimal> expected = new HashSet<BigDecimal>(Arrays.asList(
comment|//            BigDecimal.valueOf(Double.NaN), BigDecimal.valueOf(Double.POSITIVE_INFINITY),
comment|//            BigDecimal.valueOf(Double.NEGATIVE_INFINITY)));
comment|//        Iterator<BigDecimal> dit = r.get(doubleTestField.getUnicodeString(), BigDecimal.class);
comment|//        while(dit.hasNext()){
comment|//            BigDecimal val = dit.next();
comment|//            Assert.assertNotNull(val);
comment|//            Assert.assertTrue(expected.remove(val));
comment|//        }
comment|//        Assert.assertTrue(expected.isEmpty());
comment|//    }
block|}
end_class

end_unit

