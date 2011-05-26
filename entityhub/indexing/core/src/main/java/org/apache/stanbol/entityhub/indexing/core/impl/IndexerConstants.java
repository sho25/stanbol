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
name|indexing
operator|.
name|core
operator|.
name|impl
package|;
end_package

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
name|indexing
operator|.
name|core
operator|.
name|IndexingComponent
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
name|indexing
operator|.
name|core
operator|.
name|IndexingDestination
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

begin_comment
comment|/**  * Interface with the constants used by the {@link IndexerImpl} part of this  * package. This Constants can be assumed as private and SHOULD NOT be used by  * other components.  * @author Rupert Westenthaler  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|IndexerConstants
block|{
comment|/**      * The field used to store the score of an entity if not<code>null</code>      * and&gt;= 0.      */
name|String
name|SCORE_FIELD
init|=
name|RdfResourceEnum
operator|.
name|entityRank
operator|.
name|getUri
argument_list|()
decl_stmt|;
comment|/**      * Key used to store the time when the reading from the source started      */
name|String
name|SOURCE_STARTED
init|=
literal|"entity.source.started"
decl_stmt|;
comment|/**      * Key used to store the time when the reading from the source completed      */
name|String
name|SOURCE_COMPLETE
init|=
literal|"entity.source.complete"
decl_stmt|;
comment|/**      * Key used to store the time needed to read the entity from the source.      * ({@link Float})      */
name|String
name|SOURCE_DURATION
init|=
literal|"entity.source.duration"
decl_stmt|;
comment|/**      * Key used to store the time when the processing of the entity started      */
name|String
name|PROCESS_STARTED
init|=
literal|"entity.process.started"
decl_stmt|;
comment|/**      * Key used to store the time when the processing of the entity completed      */
name|String
name|PROCESS_COMPLETE
init|=
literal|"entity.process.complete"
decl_stmt|;
comment|/**      * Key used to store the time needed for processing an entity. ({@link Float})      */
name|String
name|PROCESS_DURATION
init|=
literal|"entity.process.duration"
decl_stmt|;
comment|/**      * Key used to store the time when the storing of the entity started      */
name|String
name|STORE_STARTED
init|=
literal|"entity.store.started"
decl_stmt|;
comment|/**      * Key used to store the time when the storing of the entity completed      */
name|String
name|STORE_COMPLETE
init|=
literal|"entity.store.complete"
decl_stmt|;
comment|/**      * Key used to store the time needed to store the entity. ({@link Float})      */
name|String
name|STORE_DURATION
init|=
literal|"entity.store.duration"
decl_stmt|;
comment|/**      * Key used to store the time stamp when the error occurred      */
name|String
name|ERROR_TIME
init|=
literal|"entity.error.time"
decl_stmt|;
comment|/**      * Item used by the consumers to recognise that the Queue has finished.      * See http://stackoverflow.com/questions/1956526/under-what-conditions-will-blockingqueue-take-throw-interrupted-exception Thread}      * for an Example.      */
comment|//ignore the Type safety because the item is of
comment|//INDEXING_COMPLETED_QUEUE_ITEM is anyway null
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
name|QueueItem
name|INDEXING_COMPLETED_QUEUE_ITEM
init|=
operator|new
name|QueueItem
argument_list|(
literal|null
argument_list|)
decl_stmt|;
comment|/**      * The sequence number for {@link IndexingDaemon}s that read from the       * {@link IndexingComponent}s       */
name|Integer
name|SEQUENCE_NUMBER_SOURCE_DAEMON
init|=
literal|0
decl_stmt|;
comment|/**      * The sequence number for {@link IndexingDaemon}s that process Entities      */
name|Integer
name|SEQUENCE_NUMBER_PROCESSOR_DAEMON
init|=
literal|1
decl_stmt|;
comment|/**      * The sequence number for {@link IndexingDaemon}s that persist Entities to      * the {@link IndexingDestination}      */
name|Integer
name|SEQUENCE_NUMBER_PERSIT_DAEMON
init|=
literal|2
decl_stmt|;
comment|/**      * The sequence number for {@link IndexingDaemon}s that indexed Entities      */
name|Integer
name|SEQUENCE_NUMBER_FINISHED_DAEMON
init|=
literal|3
decl_stmt|;
comment|/**      * The sequence number for {@link IndexingDaemon}s that handle errors      */
name|Integer
name|SEQUENCE_NUMBER_ERROR_HANDLING_DAEMON
init|=
literal|4
decl_stmt|;
block|}
end_interface

end_unit

