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
name|core
operator|.
name|utils
package|;
end_package

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|MalformedURLException
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
name|URISyntaxException
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
name|Dictionary
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Enumeration
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Hashtable
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|framework
operator|.
name|Constants
import|;
end_import

begin_import
import|import
name|org
operator|.
name|osgi
operator|.
name|service
operator|.
name|cm
operator|.
name|ConfigurationException
import|;
end_import

begin_comment
comment|/**  * This class contains some utilities for osgi  * TODO: Check if they are not available in some std. library  * @author Rupert Westenthaler  *  */
end_comment

begin_class
specifier|public
specifier|final
class|class
name|OsgiUtils
block|{
comment|//private static final Logger log = LoggerFactory.getLogger(OsgiUtils.class);
specifier|private
name|OsgiUtils
parameter_list|()
block|{
comment|/* do not create instances of utility classes*/
block|}
comment|/**      * Checks if a value is present      * @param propertyName The key for the property      * @return the value      * @throws ConfigurationException if the property is not present      */
specifier|public
specifier|final
specifier|static
name|Object
name|checkProperty
parameter_list|(
name|Dictionary
argument_list|<
name|?
argument_list|,
name|?
argument_list|>
name|properties
parameter_list|,
name|String
name|propertyName
parameter_list|)
throws|throws
name|ConfigurationException
block|{
return|return
name|checkProperty
argument_list|(
name|properties
argument_list|,
name|propertyName
argument_list|,
literal|null
argument_list|)
return|;
block|}
comment|/**      * Checks if the value is present. If not it returns the parse defaultValue.      * If the value and the default value is null, it throws an {@link ConfigurationException}      * @param properties the properties to search      * @param propertyName the name of the proeprty      * @param defaultValue the default value or<code>null</code> if none      * @return the value of the property (guaranteed NOT<code>null</code>)      * @throws ConfigurationException In case the property is not present and no default value was parsed      */
specifier|public
specifier|final
specifier|static
name|Object
name|checkProperty
parameter_list|(
name|Dictionary
argument_list|<
name|?
argument_list|,
name|?
argument_list|>
name|properties
parameter_list|,
name|String
name|propertyName
parameter_list|,
name|Object
name|defaultValue
parameter_list|)
throws|throws
name|ConfigurationException
block|{
name|Object
name|value
init|=
name|properties
operator|.
name|get
argument_list|(
name|propertyName
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|defaultValue
operator|!=
literal|null
condition|)
block|{
return|return
name|defaultValue
return|;
block|}
else|else
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|propertyName
argument_list|,
literal|"No value found for this required property"
argument_list|)
throw|;
block|}
block|}
else|else
block|{
return|return
name|value
return|;
block|}
block|}
comment|/**      * Checks if the property is present and the value can be converted to an {@link URI}      * @param propertyName The key for the property      * @return the value      * @throws ConfigurationException if the property is not present or the      * configured value is no valid URI      */
specifier|public
specifier|final
specifier|static
name|URI
name|checkUriProperty
parameter_list|(
name|Dictionary
argument_list|<
name|?
argument_list|,
name|?
argument_list|>
name|properties
parameter_list|,
name|String
name|propertyName
parameter_list|)
throws|throws
name|ConfigurationException
block|{
name|Object
name|uri
init|=
name|checkProperty
argument_list|(
name|properties
argument_list|,
name|propertyName
argument_list|)
decl_stmt|;
try|try
block|{
return|return
operator|new
name|URI
argument_list|(
name|uri
operator|.
name|toString
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|URISyntaxException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|propertyName
argument_list|,
literal|"Property needs to be a valid URI"
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
comment|/**      * Checks if the property is present and the value can be converted to an {@link URL}      * @param propertyName The key for the property      * @return the value      * @throws ConfigurationException if the property is not present or the      * configured value is no valid URL      */
specifier|public
specifier|final
specifier|static
name|URL
name|checkUrlProperty
parameter_list|(
name|Dictionary
argument_list|<
name|?
argument_list|,
name|?
argument_list|>
name|properties
parameter_list|,
name|String
name|propertyName
parameter_list|)
throws|throws
name|ConfigurationException
block|{
name|Object
name|uri
init|=
name|checkProperty
argument_list|(
name|properties
argument_list|,
name|propertyName
argument_list|)
decl_stmt|;
try|try
block|{
return|return
operator|new
name|URL
argument_list|(
name|uri
operator|.
name|toString
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|MalformedURLException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|propertyName
argument_list|,
literal|"Property value needs to be a valid URL"
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
comment|/**      * Checks if the value of a property is a member of the parsed Enumeration      * @param<T> the Enumeration      * @param enumeration the class of the enumeration      * @param properties the configuration      * @param propertyName the name of the property to check      * @return the member of the enumeration      * @throws ConfigurationException if the property is missing or the value is      * not a member of the parsed enumeration      */
specifier|public
specifier|final
specifier|static
parameter_list|<
name|T
extends|extends
name|Enum
argument_list|<
name|T
argument_list|>
parameter_list|>
name|T
name|checkEnumProperty
parameter_list|(
name|Class
argument_list|<
name|T
argument_list|>
name|enumeration
parameter_list|,
name|Dictionary
argument_list|<
name|?
argument_list|,
name|?
argument_list|>
name|properties
parameter_list|,
name|String
name|propertyName
parameter_list|)
throws|throws
name|ConfigurationException
block|{
name|Object
name|value
init|=
name|checkProperty
argument_list|(
name|properties
argument_list|,
name|propertyName
argument_list|)
decl_stmt|;
try|try
block|{
return|return
name|Enum
operator|.
name|valueOf
argument_list|(
name|enumeration
argument_list|,
name|value
operator|.
name|toString
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|ConfigurationException
argument_list|(
name|propertyName
argument_list|,
name|String
operator|.
name|format
argument_list|(
literal|"Property value %s is not a member of Enumeration %s!"
argument_list|,
name|value
argument_list|,
name|enumeration
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
comment|//    /**
comment|//     * search for a {@link ComponentFactory} that has the component.name property
comment|//     * as configured by {@link ConfiguredSite#DEREFERENCER_TYPE}. Than creates
comment|//     * an new instance of an {@link EntityDereferencer} and configures it with
comment|//     * all the properties present for this instance of {@link ReferencedSite} (
comment|//     * only component.* and service.* properties are ignored).<br>
comment|//     * The {@link ComponentInstance} and the {@link EntityDereferencer} are
comment|//     * stored in the according memeber variables.
comment|//     * @return the ComponentInstance of<code>null</code> if no ComponentFactory
comment|//     *    was found for the parsed componentService
comment|//     * @throws ConfigurationException if the {@link ConfiguredSite#DEREFERENCER_TYPE}
comment|//     * is not present or it's value does not allow to create a {@link EntityDereferencer}
comment|//     * instance.
comment|//     */
comment|//    public static ComponentInstance createComonentInstance(ComponentContext context, String property,Object componentName,Class<?> componentService) throws ConfigurationException {
comment|//        //Object value = checkProperty(DEREFERENCER_TYPE);
comment|//        final ServiceReference[] refs;
comment|//        try {
comment|//            refs = context.getBundleContext().getServiceReferences(
comment|//                    ComponentFactory.class.getName(),
comment|//                    "(component.name="+componentName+")");
comment|//
comment|//        } catch (InvalidSyntaxException e) {
comment|//            throw new ConfigurationException(property, "Unable to get ComponentFactory for parsed value "+componentName.toString(),e);
comment|//        }
comment|//        if(refs != null&& refs.length>0){
comment|//            if(refs.length>1){ //log some warning if more than one Service Reference was found by the query!
comment|//                log.warn("Multiple ComponentFactories found for the property "+property+"="+componentName+"! -> First one was used to instantiate the "+componentService+" Service");
comment|//            }
comment|//            Object dereferencerFactorySerivceObject = context.getBundleContext().getService(refs[0]);
comment|//            if(dereferencerFactorySerivceObject != null){
comment|//                try {
comment|//                    // I trust the OSGI framework, that the returned service implements the requested Interface
comment|//                    ComponentFactory dereferencerFactory = (ComponentFactory)dereferencerFactorySerivceObject;
comment|//                    //log.debug("build configuration for "+EntityDereferencer.class.getSimpleName()+" "+componentName.toString());
comment|//                    Dictionary<String, Object> config = copyConfig(context.getProperties());
comment|//                    ComponentInstance dereferencerComponentInstance = dereferencerFactory.newInstance(config);
comment|//                    dereferencerFactory = null;
comment|//                    //now
comment|//                    if(dereferencerComponentInstance == null){
comment|//                        throw new IllegalStateException("Unable to create ComponentInstance for Property value "+componentName+"!");
comment|//                    }
comment|//                    if(componentService.isAssignableFrom(dereferencerComponentInstance.getInstance().getClass())){
comment|//                        return dereferencerComponentInstance;
comment|//                    } else {
comment|//                        dereferencerComponentInstance.dispose(); //we can not use it -> so dispose it!
comment|//                        dereferencerComponentInstance = null;
comment|//                        throw new IllegalStateException("ComponentInstance created for Property value "+componentName+" does not provide the "+componentService+" Service!");
comment|//                    }
comment|//                } finally {
comment|//                    //we need to unget the ComponentFactory!
comment|//                    context.getBundleContext().ungetService(refs[0]);
comment|//                    dereferencerFactorySerivceObject=null;
comment|//                }
comment|//            } else {
comment|//                return null;
comment|//            }
comment|//        } else {
comment|//            return null;
comment|//        }
comment|//    }
comment|/**      * Copy all properties excluding "{@value Constants#OBJECTCLASS}",      * "component.*" and "service.*" to the returned Dictionary      * @param source the source      * @return the target      */
specifier|public
specifier|static
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|copyConfig
parameter_list|(
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|source
parameter_list|)
block|{
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|config
init|=
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Enumeration
argument_list|<
name|?
argument_list|>
name|keys
init|=
name|source
operator|.
name|keys
argument_list|()
init|;
name|keys
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|String
name|key
init|=
name|keys
operator|.
name|nextElement
argument_list|()
operator|.
name|toString
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|key
operator|.
name|startsWith
argument_list|(
literal|"component."
argument_list|)
operator|&&
operator|!
name|key
operator|.
name|startsWith
argument_list|(
literal|"service."
argument_list|)
operator|&&
operator|!
name|key
operator|.
name|equals
argument_list|(
name|Constants
operator|.
name|OBJECTCLASS
argument_list|)
condition|)
block|{
comment|//log.debug("> copy key" + key);
name|config
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|source
operator|.
name|get
argument_list|(
name|key
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|//log.debug("> ignore key" + key);
block|}
block|}
return|return
name|config
return|;
block|}
block|}
end_class

end_unit

