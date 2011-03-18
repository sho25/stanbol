begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|reengineer
operator|.
name|base
operator|.
name|api
operator|.
name|impl
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
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
name|Collection
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
name|Hashtable
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
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Activate
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Component
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Deactivate
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|felix
operator|.
name|scr
operator|.
name|annotations
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
name|felix
operator|.
name|scr
operator|.
name|annotations
operator|.
name|Service
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
name|ontologymanager
operator|.
name|ontonet
operator|.
name|api
operator|.
name|KReSONManager
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
name|ontologymanager
operator|.
name|store
operator|.
name|api
operator|.
name|NoSuchOntologyInStoreException
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
name|ontologymanager
operator|.
name|store
operator|.
name|api
operator|.
name|OntologyStorage
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
name|reengineer
operator|.
name|base
operator|.
name|api
operator|.
name|DataSource
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
name|reengineer
operator|.
name|base
operator|.
name|api
operator|.
name|ReengineeringException
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
name|reengineer
operator|.
name|base
operator|.
name|api
operator|.
name|SemionManager
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
name|reengineer
operator|.
name|base
operator|.
name|api
operator|.
name|SemionReengineer
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
name|component
operator|.
name|ComponentContext
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
name|OWLOntology
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
comment|/**  * Concrete implementation of the  * {@link eu.iksproject.kres.api.semion.SemionManager} interface defined in the  * KReS APIs.  *   * @author andrea.nuzzolese  *  */
end_comment

begin_class
annotation|@
name|Component
argument_list|(
name|immediate
operator|=
literal|true
argument_list|,
name|metatype
operator|=
literal|true
argument_list|)
annotation|@
name|Service
argument_list|(
name|SemionManager
operator|.
name|class
argument_list|)
specifier|public
class|class
name|SemionManagerImpl
implements|implements
name|SemionManager
block|{
specifier|private
specifier|final
name|Logger
name|log
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|getClass
argument_list|()
argument_list|)
decl_stmt|;
annotation|@
name|Reference
name|KReSONManager
name|onManager
decl_stmt|;
specifier|private
name|ArrayList
argument_list|<
name|SemionReengineer
argument_list|>
name|reengineers
decl_stmt|;
comment|//
comment|//	private SemionRefactorer semionRefactorer;
comment|/** 	 * This default constructor is<b>only</b> intended to be used by the OSGI 	 * environment with Service Component Runtime support. 	 *<p> 	 * DO NOT USE to manually create instances - the SemionManagerImpl instances 	 * do need to be configured! YOU NEED TO USE 	 * {@link #SemionManagerImpl(KReSONManager)} or its overloads, to parse the 	 * configuration and then initialise the rule store if running outside a 	 * OSGI environment. 	 */
specifier|public
name|SemionManagerImpl
parameter_list|()
block|{
name|reengineers
operator|=
operator|new
name|ArrayList
argument_list|<
name|SemionReengineer
argument_list|>
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Basic constructor to be used if outside of an OSGi environment. Invokes 	 * default constructor. 	 *  	 * @param onm 	 */
specifier|public
name|SemionManagerImpl
parameter_list|(
name|KReSONManager
name|onManager
parameter_list|)
block|{
name|this
argument_list|()
expr_stmt|;
name|this
operator|.
name|onManager
operator|=
name|onManager
expr_stmt|;
name|activate
argument_list|(
operator|new
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Used to configure an instance within an OSGi container. 	 *  	 * @throws IOException 	 */
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
annotation|@
name|Activate
specifier|protected
name|void
name|activate
parameter_list|(
name|ComponentContext
name|context
parameter_list|)
throws|throws
name|IOException
block|{
name|log
operator|.
name|info
argument_list|(
literal|"in "
operator|+
name|SemionManagerImpl
operator|.
name|class
operator|+
literal|" activate with context "
operator|+
name|context
argument_list|)
expr_stmt|;
if|if
condition|(
name|context
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"No valid"
operator|+
name|ComponentContext
operator|.
name|class
operator|+
literal|" parsed in activate!"
argument_list|)
throw|;
block|}
name|activate
argument_list|(
operator|(
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
operator|)
name|context
operator|.
name|getProperties
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|void
name|activate
parameter_list|(
name|Dictionary
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|configuration
parameter_list|)
block|{
name|reengineers
operator|=
operator|new
name|ArrayList
argument_list|<
name|SemionReengineer
argument_list|>
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * @param semionReengineer 	 *            {@link eu.iksproject.kres.api.semion.SemionReengineer} 	 * @return true if the reengineer is bound, false otherwise 	 */
annotation|@
name|Override
specifier|public
name|boolean
name|bindReengineer
parameter_list|(
name|SemionReengineer
name|semionReengineer
parameter_list|)
block|{
name|boolean
name|found
init|=
literal|false
decl_stmt|;
name|Iterator
argument_list|<
name|SemionReengineer
argument_list|>
name|it
init|=
name|reengineers
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
name|found
condition|)
block|{
name|SemionReengineer
name|reengineer
init|=
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|reengineer
operator|.
name|getReengineerType
argument_list|()
operator|==
name|semionReengineer
operator|.
name|getReengineerType
argument_list|()
condition|)
block|{
name|found
operator|=
literal|true
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|!
name|found
condition|)
block|{
name|reengineers
operator|.
name|add
argument_list|(
name|semionReengineer
argument_list|)
expr_stmt|;
name|String
name|info
init|=
literal|"Reengineering Manager : "
operator|+
name|reengineers
operator|.
name|size
argument_list|()
operator|+
literal|" reengineers"
decl_stmt|;
name|log
operator|.
name|info
argument_list|(
name|info
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
else|else
block|{
name|log
operator|.
name|info
argument_list|(
literal|"Reengineer already existing"
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|int
name|countReengineers
parameter_list|()
block|{
return|return
name|reengineers
operator|.
name|size
argument_list|()
return|;
block|}
annotation|@
name|Deactivate
specifier|protected
name|void
name|deactivate
parameter_list|(
name|ComponentContext
name|context
parameter_list|)
block|{
name|log
operator|.
name|info
argument_list|(
literal|"in "
operator|+
name|SemionManagerImpl
operator|.
name|class
operator|+
literal|" deactivate with context "
operator|+
name|context
argument_list|)
expr_stmt|;
name|reengineers
operator|=
literal|null
expr_stmt|;
block|}
comment|//	@Override
comment|//	public SemionRefactorer getRegisteredRefactorer() {
comment|//		return semionRefactorer;
comment|//		}
annotation|@
name|Override
specifier|public
name|Collection
argument_list|<
name|SemionReengineer
argument_list|>
name|listReengineers
parameter_list|()
block|{
return|return
name|reengineers
return|;
block|}
annotation|@
name|Override
specifier|public
name|OWLOntology
name|performDataReengineering
parameter_list|(
name|String
name|graphNS
parameter_list|,
name|IRI
name|outputIRI
parameter_list|,
name|DataSource
name|dataSource
parameter_list|,
name|IRI
name|schemaOntologyIRI
parameter_list|)
throws|throws
name|ReengineeringException
throws|,
name|NoSuchOntologyInStoreException
block|{
name|OWLOntology
name|reengineeredDataOntology
init|=
literal|null
decl_stmt|;
name|OntologyStorage
name|ontologyStorage
init|=
name|onManager
operator|.
name|getOntologyStore
argument_list|()
decl_stmt|;
name|OWLOntology
name|schemaOntology
init|=
name|ontologyStorage
operator|.
name|load
argument_list|(
name|schemaOntologyIRI
argument_list|)
decl_stmt|;
if|if
condition|(
name|schemaOntology
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|NoSuchOntologyInStoreException
argument_list|(
name|schemaOntologyIRI
argument_list|)
throw|;
block|}
else|else
block|{
name|boolean
name|reengineered
init|=
literal|false
decl_stmt|;
name|Iterator
argument_list|<
name|SemionReengineer
argument_list|>
name|it
init|=
name|reengineers
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
name|reengineered
condition|)
block|{
name|SemionReengineer
name|semionReengineer
init|=
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|semionReengineer
operator|.
name|canPerformReengineering
argument_list|(
name|schemaOntology
argument_list|)
condition|)
block|{
name|reengineeredDataOntology
operator|=
name|semionReengineer
operator|.
name|dataReengineering
argument_list|(
name|graphNS
argument_list|,
name|outputIRI
argument_list|,
name|dataSource
argument_list|,
name|schemaOntology
argument_list|)
expr_stmt|;
name|reengineered
operator|=
literal|true
expr_stmt|;
block|}
block|}
block|}
return|return
name|reengineeredDataOntology
return|;
block|}
annotation|@
name|Override
specifier|public
name|OWLOntology
name|performDataReengineering
parameter_list|(
name|String
name|graphNS
parameter_list|,
name|IRI
name|outputIRI
parameter_list|,
name|DataSource
name|dataSource
parameter_list|,
name|OWLOntology
name|schemaOntology
parameter_list|)
throws|throws
name|ReengineeringException
block|{
name|OWLOntology
name|reengineeredDataOntology
init|=
literal|null
decl_stmt|;
name|boolean
name|reengineered
init|=
literal|false
decl_stmt|;
name|Iterator
argument_list|<
name|SemionReengineer
argument_list|>
name|it
init|=
name|reengineers
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
name|reengineered
condition|)
block|{
name|SemionReengineer
name|semionReengineer
init|=
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|semionReengineer
operator|.
name|canPerformReengineering
argument_list|(
name|schemaOntology
argument_list|)
condition|)
block|{
name|reengineeredDataOntology
operator|=
name|semionReengineer
operator|.
name|dataReengineering
argument_list|(
name|graphNS
argument_list|,
name|outputIRI
argument_list|,
name|dataSource
argument_list|,
name|schemaOntology
argument_list|)
expr_stmt|;
name|reengineered
operator|=
literal|true
expr_stmt|;
block|}
block|}
return|return
name|reengineeredDataOntology
return|;
block|}
specifier|public
name|OWLOntology
name|performReengineering
parameter_list|(
name|String
name|graphNS
parameter_list|,
name|IRI
name|outputIRI
parameter_list|,
name|DataSource
name|dataSource
parameter_list|)
throws|throws
name|ReengineeringException
block|{
name|OWLOntology
name|reengineeredOntology
init|=
literal|null
decl_stmt|;
name|boolean
name|reengineered
init|=
literal|false
decl_stmt|;
name|Iterator
argument_list|<
name|SemionReengineer
argument_list|>
name|it
init|=
name|reengineers
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
name|reengineered
condition|)
block|{
name|SemionReengineer
name|semionReengineer
init|=
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|semionReengineer
operator|.
name|canPerformReengineering
argument_list|(
name|dataSource
argument_list|)
condition|)
block|{
name|log
operator|.
name|debug
argument_list|(
name|semionReengineer
operator|.
name|getClass
argument_list|()
operator|.
name|getCanonicalName
argument_list|()
operator|+
literal|" can perform the reengineering"
argument_list|)
expr_stmt|;
name|reengineeredOntology
operator|=
name|semionReengineer
operator|.
name|reengineering
argument_list|(
name|graphNS
argument_list|,
name|outputIRI
argument_list|,
name|dataSource
argument_list|)
expr_stmt|;
name|reengineered
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
name|log
operator|.
name|debug
argument_list|(
name|semionReengineer
operator|.
name|getClass
argument_list|()
operator|.
name|getCanonicalName
argument_list|()
operator|+
literal|" cannot perform the reengineering"
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|reengineeredOntology
return|;
block|}
specifier|public
name|OWLOntology
name|performSchemaReengineering
parameter_list|(
name|String
name|graphNS
parameter_list|,
name|IRI
name|outputIRI
parameter_list|,
name|DataSource
name|dataSource
parameter_list|)
throws|throws
name|ReengineeringException
block|{
name|OWLOntology
name|reengineeredSchemaOntology
init|=
literal|null
decl_stmt|;
name|boolean
name|reengineered
init|=
literal|false
decl_stmt|;
name|Iterator
argument_list|<
name|SemionReengineer
argument_list|>
name|it
init|=
name|reengineers
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
name|reengineered
condition|)
block|{
name|SemionReengineer
name|semionReengineer
init|=
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|semionReengineer
operator|.
name|canPerformReengineering
argument_list|(
name|dataSource
argument_list|)
condition|)
block|{
name|reengineeredSchemaOntology
operator|=
name|semionReengineer
operator|.
name|schemaReengineering
argument_list|(
name|graphNS
argument_list|,
name|outputIRI
argument_list|,
name|dataSource
argument_list|)
expr_stmt|;
if|if
condition|(
name|reengineeredSchemaOntology
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|ReengineeringException
argument_list|()
throw|;
block|}
name|reengineered
operator|=
literal|true
expr_stmt|;
block|}
block|}
return|return
name|reengineeredSchemaOntology
return|;
block|}
comment|//	@Override
comment|//	public void registerRefactorer(SemionRefactorer semionRefactorer) {
comment|//		this.semionRefactorer = semionRefactorer;
comment|//	}
annotation|@
name|Override
specifier|public
name|boolean
name|unbindReengineer
parameter_list|(
name|int
name|reenginnerType
parameter_list|)
block|{
name|boolean
name|found
init|=
literal|false
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|,
name|j
init|=
name|reengineers
operator|.
name|size
argument_list|()
init|;
name|i
operator|<
name|j
operator|&&
operator|!
name|found
condition|;
name|i
operator|++
control|)
block|{
name|SemionReengineer
name|reengineer
init|=
name|reengineers
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|reengineer
operator|.
name|getReengineerType
argument_list|()
operator|==
name|reenginnerType
condition|)
block|{
name|reengineers
operator|.
name|remove
argument_list|(
name|i
argument_list|)
expr_stmt|;
name|found
operator|=
literal|true
expr_stmt|;
block|}
block|}
return|return
name|found
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|unbindReengineer
parameter_list|(
name|SemionReengineer
name|semionReengineer
parameter_list|)
block|{
name|boolean
name|found
init|=
literal|false
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|,
name|j
init|=
name|reengineers
operator|.
name|size
argument_list|()
init|;
name|i
operator|<
name|j
operator|&&
operator|!
name|found
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|semionReengineer
operator|.
name|equals
argument_list|(
name|reengineers
operator|.
name|get
argument_list|(
name|i
argument_list|)
argument_list|)
condition|)
block|{
name|reengineers
operator|.
name|remove
argument_list|(
name|i
argument_list|)
expr_stmt|;
name|found
operator|=
literal|true
expr_stmt|;
block|}
block|}
return|return
name|found
return|;
block|}
comment|//	@Override
comment|//	public void unregisterRefactorer() {
comment|//		this.semionRefactorer = null;
comment|//	}
block|}
end_class

end_unit

