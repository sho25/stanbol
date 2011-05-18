begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|helper
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|StringReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|StringWriter
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
name|List
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|bind
operator|.
name|JAXBContext
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|bind
operator|.
name|JAXBException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|bind
operator|.
name|Marshaller
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|bind
operator|.
name|Unmarshaller
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|mapping
operator|.
name|BridgeDefinitions
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|mapping
operator|.
name|ConceptBridge
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|mapping
operator|.
name|InstanceBridge
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|mapping
operator|.
name|ObjectFactory
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
name|cmsadapter
operator|.
name|servicesapi
operator|.
name|model
operator|.
name|mapping
operator|.
name|SubsumptionBridge
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_comment
comment|/**  * Helper class for serializing and deserializing bridge definitions  *   */
end_comment

begin_class
specifier|public
class|class
name|MappingModelParser
block|{
specifier|private
specifier|static
specifier|final
name|Logger
name|logger
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|MappingModelParser
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
name|BridgeDefinitions
name|deserializeObject
parameter_list|(
name|String
name|xmlContent
parameter_list|)
block|{
name|BridgeDefinitions
name|bridgeDefinitions
init|=
literal|null
decl_stmt|;
try|try
block|{
name|ClassLoader
name|cl
init|=
name|ObjectFactory
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
decl_stmt|;
name|JAXBContext
name|jc
init|=
name|JAXBContext
operator|.
name|newInstance
argument_list|(
name|ObjectFactory
operator|.
name|class
operator|.
name|getPackage
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|,
name|cl
argument_list|)
decl_stmt|;
name|Unmarshaller
name|um
init|=
name|jc
operator|.
name|createUnmarshaller
argument_list|()
decl_stmt|;
name|StringReader
name|stringReader
init|=
operator|new
name|StringReader
argument_list|(
name|xmlContent
argument_list|)
decl_stmt|;
name|bridgeDefinitions
operator|=
operator|(
name|BridgeDefinitions
operator|)
name|um
operator|.
name|unmarshal
argument_list|(
name|stringReader
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|JAXBException
name|e
parameter_list|)
block|{
name|logger
operator|.
name|error
argument_list|(
literal|"JAXB Exception when parsing serialized BridgeDefinitions"
argument_list|)
expr_stmt|;
block|}
return|return
name|bridgeDefinitions
return|;
block|}
specifier|public
specifier|static
name|String
name|serializeObject
parameter_list|(
name|Object
name|object
parameter_list|)
block|{
name|String
name|bridgeDefinitions
init|=
literal|null
decl_stmt|;
try|try
block|{
name|ClassLoader
name|cl
init|=
name|ObjectFactory
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
decl_stmt|;
name|JAXBContext
name|jc
init|=
name|JAXBContext
operator|.
name|newInstance
argument_list|(
name|ObjectFactory
operator|.
name|class
operator|.
name|getPackage
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|,
name|cl
argument_list|)
decl_stmt|;
name|Marshaller
name|m
init|=
name|jc
operator|.
name|createMarshaller
argument_list|()
decl_stmt|;
name|StringWriter
name|stringWriter
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
name|m
operator|.
name|marshal
argument_list|(
name|object
argument_list|,
name|stringWriter
argument_list|)
expr_stmt|;
name|bridgeDefinitions
operator|=
name|stringWriter
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|JAXBException
name|e
parameter_list|)
block|{
name|logger
operator|.
name|error
argument_list|(
literal|"JAXB Exception when parsing serialized BridgeDefinitions"
argument_list|)
expr_stmt|;
block|}
return|return
name|bridgeDefinitions
return|;
block|}
comment|/**      * Gets {@link ConceptBridge}s from inside the given {@link BridgeDefinitions} object.      *       * @param bridgeDefinitions      * @return      */
specifier|public
specifier|static
name|List
argument_list|<
name|ConceptBridge
argument_list|>
name|getConceptBridges
parameter_list|(
name|BridgeDefinitions
name|bridgeDefinitions
parameter_list|)
block|{
name|List
argument_list|<
name|ConceptBridge
argument_list|>
name|cList
init|=
operator|new
name|ArrayList
argument_list|<
name|ConceptBridge
argument_list|>
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|Object
argument_list|>
name|aList
init|=
name|bridgeDefinitions
operator|.
name|getConceptBridgeOrSubsumptionBridgeOrInstanceBridge
argument_list|()
decl_stmt|;
for|for
control|(
name|Object
name|bridge
range|:
name|aList
control|)
block|{
if|if
condition|(
name|bridge
operator|instanceof
name|ConceptBridge
condition|)
block|{
name|cList
operator|.
name|add
argument_list|(
operator|(
name|ConceptBridge
operator|)
name|bridge
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|cList
return|;
block|}
comment|/**      * Gets {@link SubsumptionBridge}s from inside the given {@link BridgeDefinitions} object.      *       * @param bridgeDefinitions      * @return      */
specifier|public
specifier|static
name|List
argument_list|<
name|SubsumptionBridge
argument_list|>
name|getSubsumptionBridges
parameter_list|(
name|BridgeDefinitions
name|bridgeDefinitions
parameter_list|)
block|{
name|List
argument_list|<
name|SubsumptionBridge
argument_list|>
name|sList
init|=
operator|new
name|ArrayList
argument_list|<
name|SubsumptionBridge
argument_list|>
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|Object
argument_list|>
name|aList
init|=
name|bridgeDefinitions
operator|.
name|getConceptBridgeOrSubsumptionBridgeOrInstanceBridge
argument_list|()
decl_stmt|;
for|for
control|(
name|Object
name|bridge
range|:
name|aList
control|)
block|{
if|if
condition|(
name|bridge
operator|instanceof
name|SubsumptionBridge
condition|)
block|{
name|sList
operator|.
name|add
argument_list|(
operator|(
name|SubsumptionBridge
operator|)
name|bridge
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|sList
return|;
block|}
comment|/**      * Gets {@link InstanceBridge}s from inside the given {@link BridgeDefinitions} object.      *       * @param bridgeDefinitions      * @return      */
specifier|public
specifier|static
name|List
argument_list|<
name|InstanceBridge
argument_list|>
name|getInstanceBridges
parameter_list|(
name|BridgeDefinitions
name|bridgeDefinitions
parameter_list|)
block|{
name|List
argument_list|<
name|InstanceBridge
argument_list|>
name|sList
init|=
operator|new
name|ArrayList
argument_list|<
name|InstanceBridge
argument_list|>
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|Object
argument_list|>
name|aList
init|=
name|bridgeDefinitions
operator|.
name|getConceptBridgeOrSubsumptionBridgeOrInstanceBridge
argument_list|()
decl_stmt|;
for|for
control|(
name|Object
name|bridge
range|:
name|aList
control|)
block|{
if|if
condition|(
name|bridge
operator|instanceof
name|InstanceBridge
condition|)
block|{
name|sList
operator|.
name|add
argument_list|(
operator|(
name|InstanceBridge
operator|)
name|bridge
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|sList
return|;
block|}
block|}
end_class

end_unit

