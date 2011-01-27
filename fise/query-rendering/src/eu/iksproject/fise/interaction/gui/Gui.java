begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|stanbol
operator|.
name|enhancer
operator|.
name|interaction
operator|.
name|gui
package|;
end_package

begin_comment
comment|/*  * Copyright 2010  * German Research Center for Artificial Intelligence (DFKI)  * Department of Intelligent User Interfaces  * Germany  *  *     http://www.dfki.de/web/forschung/iui  *  * Licensed under the Apache License, Version 2.0 (the "License");  * you may not use this file except in compliance with the License.  * You may obtain a copy of the License at  *  *     http://www.apache.org/licenses/LICENSE-2.0  *  * Unless required by applicable law or agreed to in writing, software  * distributed under the License is distributed on an "AS IS" BASIS,  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  * See the License for the specific language governing permissions and  * limitations under the License.  *  * Authors:  *     Sebastian Germesin  *     Massimo Romanelli  *     Tilman Becker  */
end_comment

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|BorderLayout
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Dimension
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Toolkit
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionEvent
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionListener
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|MouseEvent
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|MouseListener
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|BufferedReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStreamReader
import|;
end_import

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
name|URL
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
name|LinkedList
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
name|concurrent
operator|.
name|ExecutionException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|ExecutorService
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|Executors
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|Future
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|BorderFactory
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|Box
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|Icon
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|ImageIcon
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JButton
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JFrame
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JLabel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JMenu
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JMenuBar
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JPanel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JScrollPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JTabbedPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JTextArea
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
name|enhancer
operator|.
name|interaction
operator|.
name|event
operator|.
name|ClerezzaResultEvent
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
name|enhancer
operator|.
name|interaction
operator|.
name|event
operator|.
name|Event
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
name|enhancer
operator|.
name|interaction
operator|.
name|event
operator|.
name|EventListener
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
name|enhancer
operator|.
name|interaction
operator|.
name|event
operator|.
name|EventManager
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
name|enhancer
operator|.
name|interaction
operator|.
name|event
operator|.
name|NotUnderstoodEvent
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
name|enhancer
operator|.
name|interaction
operator|.
name|event
operator|.
name|PlaybackAudioEvent
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
name|enhancer
operator|.
name|interaction
operator|.
name|event
operator|.
name|QueryEvent
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
name|enhancer
operator|.
name|interaction
operator|.
name|event
operator|.
name|RecognizedSpeechEvent
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
name|enhancer
operator|.
name|interaction
operator|.
name|event
operator|.
name|RecordedAudioEvent
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
name|enhancer
operator|.
name|interaction
operator|.
name|event
operator|.
name|SparqlEvent
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
name|enhancer
operator|.
name|interaction
operator|.
name|speech
operator|.
name|Playback
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
name|enhancer
operator|.
name|interaction
operator|.
name|speech
operator|.
name|PlaybackInProgressException
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
name|enhancer
operator|.
name|interaction
operator|.
name|speech
operator|.
name|Recording
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
name|enhancer
operator|.
name|interaction
operator|.
name|speech
operator|.
name|RecordingInProgressException
import|;
end_import

begin_class
specifier|public
class|class
name|Gui
extends|extends
name|JFrame
implements|implements
name|EventListener
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|8791127586299692743L
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|Dimension
name|GUI_SIZE
init|=
operator|new
name|Dimension
argument_list|(
literal|600
argument_list|,
literal|400
argument_list|)
decl_stmt|;
specifier|private
specifier|static
name|Icon
name|sparqlIcon
decl_stmt|;
specifier|private
specifier|static
name|Icon
name|speechIcon
decl_stmt|;
specifier|private
specifier|static
name|Icon
name|uploadIcon
decl_stmt|;
specifier|private
name|JTabbedPane
name|mainPane
decl_stmt|;
specifier|private
name|JTextArea
name|writingArea
decl_stmt|;
specifier|private
name|JTextArea
name|sparqlArea
decl_stmt|;
specifier|private
name|ConfigurationPanel
name|configurationPanel
decl_stmt|;
specifier|private
name|UploadPanel
name|uploadPanel
decl_stmt|;
specifier|private
name|Recording
name|recording
decl_stmt|;
specifier|private
name|Playback
name|playback
decl_stmt|;
static|static
block|{
name|sparqlIcon
operator|=
name|createImageIcon
argument_list|(
literal|"/icons/sparql.jpg"
argument_list|)
expr_stmt|;
name|speechIcon
operator|=
name|createImageIcon
argument_list|(
literal|"/icons/speech.png"
argument_list|)
expr_stmt|;
name|uploadIcon
operator|=
name|createImageIcon
argument_list|(
literal|"/icons/upload.png"
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Gui
parameter_list|(
name|String
name|clerezzaServerHost
parameter_list|,
name|int
name|clerezzaServerPort
parameter_list|,
name|String
name|username
parameter_list|,
name|String
name|password
parameter_list|)
block|{
name|super
argument_list|(
literal|"IKS [T5.1] -Semantic Search Engine Hackathon PROTOTYPE"
argument_list|)
expr_stmt|;
name|recording
operator|=
operator|new
name|Recording
argument_list|()
expr_stmt|;
name|playback
operator|=
operator|new
name|Playback
argument_list|()
expr_stmt|;
name|init
argument_list|(
name|clerezzaServerHost
argument_list|,
name|clerezzaServerPort
argument_list|,
name|username
argument_list|,
name|password
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|init
parameter_list|(
name|String
name|clerezzaServerHost
parameter_list|,
name|int
name|clerezzaServerPort
parameter_list|,
name|String
name|username
parameter_list|,
name|String
name|password
parameter_list|)
block|{
name|setSize
argument_list|(
name|GUI_SIZE
argument_list|)
expr_stmt|;
name|setLocationRelativeTo
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|setDefaultCloseOperation
argument_list|(
name|JFrame
operator|.
name|DO_NOTHING_ON_CLOSE
argument_list|)
expr_stmt|;
comment|// init writing panel //
name|JPanel
name|writingPanel
init|=
name|createWritingPanel
argument_list|()
decl_stmt|;
name|writingPanel
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createTitledBorder
argument_list|(
literal|"Text"
argument_list|)
argument_list|)
expr_stmt|;
comment|// init speech panel //
name|JPanel
name|speechPanel
init|=
name|createSpeechPanel
argument_list|()
decl_stmt|;
name|speechPanel
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createTitledBorder
argument_list|(
literal|"Speech"
argument_list|)
argument_list|)
expr_stmt|;
comment|// init SPARQL panel //
name|JPanel
name|sparqlPanel
init|=
name|createSPARQLPanel
argument_list|()
decl_stmt|;
name|sparqlPanel
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createTitledBorder
argument_list|(
literal|"SPARQL"
argument_list|)
argument_list|)
expr_stmt|;
comment|// init tabbed main panel //
name|mainPane
operator|=
operator|new
name|JTabbedPane
argument_list|()
expr_stmt|;
name|Box
name|textBox
init|=
name|Box
operator|.
name|createVerticalBox
argument_list|()
decl_stmt|;
name|textBox
operator|.
name|add
argument_list|(
name|writingPanel
argument_list|)
expr_stmt|;
name|textBox
operator|.
name|add
argument_list|(
name|sparqlPanel
argument_list|)
expr_stmt|;
name|Box
name|mainBox
init|=
name|Box
operator|.
name|createHorizontalBox
argument_list|()
decl_stmt|;
name|mainBox
operator|.
name|add
argument_list|(
name|textBox
argument_list|)
expr_stmt|;
name|mainBox
operator|.
name|add
argument_list|(
name|speechPanel
argument_list|)
expr_stmt|;
name|uploadPanel
operator|=
operator|new
name|UploadPanel
argument_list|(
name|clerezzaServerHost
argument_list|,
name|clerezzaServerPort
argument_list|)
expr_stmt|;
name|mainPane
operator|.
name|addTab
argument_list|(
literal|"Upload"
argument_list|,
name|uploadIcon
argument_list|,
name|uploadPanel
argument_list|)
expr_stmt|;
name|mainPane
operator|.
name|addTab
argument_list|(
literal|"Query"
argument_list|,
name|sparqlIcon
argument_list|,
name|mainBox
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|configurationPanel
operator|=
operator|new
name|ConfigurationPanel
argument_list|(
name|clerezzaServerHost
argument_list|,
name|clerezzaServerPort
argument_list|,
name|username
argument_list|,
name|password
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|BorderLayout
operator|.
name|NORTH
argument_list|,
name|configurationPanel
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|BorderLayout
operator|.
name|CENTER
argument_list|,
name|mainPane
argument_list|)
expr_stmt|;
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
specifier|private
name|JPanel
name|createWritingPanel
parameter_list|()
block|{
name|JPanel
name|writingPanel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|writingArea
operator|=
operator|new
name|JTextArea
argument_list|(
literal|5
argument_list|,
literal|100
argument_list|)
expr_stmt|;
name|writingArea
operator|.
name|setToolTipText
argument_list|(
literal|"Please insert a (valid) query using natural language!"
argument_list|)
expr_stmt|;
name|JButton
name|submitButton
init|=
operator|new
name|JButton
argument_list|(
literal|"Submit"
argument_list|)
decl_stmt|;
name|submitButton
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionListener
argument_list|()
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|writingArea
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|String
name|text
init|=
name|writingArea
operator|.
name|getText
argument_list|()
decl_stmt|;
name|writingArea
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|RecognizedSpeechEvent
name|rse
init|=
operator|new
name|RecognizedSpeechEvent
argument_list|(
name|text
argument_list|)
decl_stmt|;
name|EventManager
operator|.
name|eventOccurred
argument_list|(
name|rse
argument_list|)
expr_stmt|;
name|writingArea
operator|.
name|setEditable
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|JButton
name|clearButton
init|=
operator|new
name|JButton
argument_list|(
literal|"Clear"
argument_list|)
decl_stmt|;
name|clearButton
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionListener
argument_list|()
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|clearButtonPressed
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|Box
name|writingButtonBox
init|=
name|Box
operator|.
name|createHorizontalBox
argument_list|()
decl_stmt|;
name|writingButtonBox
operator|.
name|add
argument_list|(
name|submitButton
argument_list|)
expr_stmt|;
name|writingButtonBox
operator|.
name|add
argument_list|(
name|clearButton
argument_list|)
expr_stmt|;
name|Box
name|sparqlWholeBox
init|=
name|Box
operator|.
name|createVerticalBox
argument_list|()
decl_stmt|;
name|sparqlWholeBox
operator|.
name|add
argument_list|(
operator|new
name|JScrollPane
argument_list|(
name|writingArea
argument_list|,
name|JScrollPane
operator|.
name|VERTICAL_SCROLLBAR_AS_NEEDED
argument_list|,
name|JScrollPane
operator|.
name|HORIZONTAL_SCROLLBAR_NEVER
argument_list|)
argument_list|)
expr_stmt|;
name|sparqlWholeBox
operator|.
name|add
argument_list|(
name|writingButtonBox
argument_list|)
expr_stmt|;
name|writingPanel
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|writingPanel
operator|.
name|add
argument_list|(
name|BorderLayout
operator|.
name|CENTER
argument_list|,
name|sparqlWholeBox
argument_list|)
expr_stmt|;
return|return
name|writingPanel
return|;
block|}
specifier|private
name|JPanel
name|createSpeechPanel
parameter_list|()
block|{
name|JPanel
name|speechPanel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|speechPanel
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
specifier|final
name|JLabel
name|pttButton
init|=
operator|new
name|JLabel
argument_list|(
name|speechIcon
argument_list|)
decl_stmt|;
name|pttButton
operator|.
name|setToolTipText
argument_list|(
literal|"Push button to talk and release it when you're finished!"
argument_list|)
expr_stmt|;
name|pttButton
operator|.
name|addMouseListener
argument_list|(
operator|new
name|MouseListener
argument_list|()
block|{
specifier|public
name|void
name|mouseClicked
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{}
specifier|public
name|void
name|mouseEntered
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{}
specifier|public
name|void
name|mouseExited
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{}
specifier|public
name|void
name|mousePressed
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{
name|pttButton
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|clearButtonPressed
argument_list|()
expr_stmt|;
name|startRecording
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|mouseReleased
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{
name|stopRecording
argument_list|()
expr_stmt|;
name|pttButton
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|speechPanel
operator|.
name|add
argument_list|(
name|BorderLayout
operator|.
name|CENTER
argument_list|,
name|pttButton
argument_list|)
expr_stmt|;
return|return
name|speechPanel
return|;
block|}
specifier|private
name|JPanel
name|createSPARQLPanel
parameter_list|()
block|{
name|JPanel
name|sparqlPanel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|sparqlArea
operator|=
operator|new
name|JTextArea
argument_list|(
literal|5
argument_list|,
literal|100
argument_list|)
expr_stmt|;
name|sparqlArea
operator|.
name|setToolTipText
argument_list|(
literal|"Please insert a (valid) SPARQL Query!"
argument_list|)
expr_stmt|;
comment|//TODO: sparqlArea.add
name|JButton
name|submitButton
init|=
operator|new
name|JButton
argument_list|(
literal|"Submit"
argument_list|)
decl_stmt|;
name|submitButton
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionListener
argument_list|()
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|sparqlArea
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|String
name|sparqlQuery
init|=
name|sparqlArea
operator|.
name|getText
argument_list|()
decl_stmt|;
name|sparqlArea
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|SparqlEvent
name|se
init|=
operator|new
name|SparqlEvent
argument_list|(
name|sparqlQuery
argument_list|)
decl_stmt|;
name|EventManager
operator|.
name|eventOccurred
argument_list|(
name|se
argument_list|)
expr_stmt|;
name|sparqlArea
operator|.
name|setEditable
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|JButton
name|clearButton
init|=
operator|new
name|JButton
argument_list|(
literal|"Clear"
argument_list|)
decl_stmt|;
name|clearButton
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionListener
argument_list|()
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|clearButtonPressed
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|Box
name|sparqlButtonBox
init|=
name|Box
operator|.
name|createHorizontalBox
argument_list|()
decl_stmt|;
name|sparqlButtonBox
operator|.
name|add
argument_list|(
name|submitButton
argument_list|)
expr_stmt|;
name|sparqlButtonBox
operator|.
name|add
argument_list|(
name|clearButton
argument_list|)
expr_stmt|;
name|Box
name|sparqlWholeBox
init|=
name|Box
operator|.
name|createVerticalBox
argument_list|()
decl_stmt|;
name|sparqlWholeBox
operator|.
name|add
argument_list|(
operator|new
name|JScrollPane
argument_list|(
name|sparqlArea
argument_list|,
name|JScrollPane
operator|.
name|VERTICAL_SCROLLBAR_AS_NEEDED
argument_list|,
name|JScrollPane
operator|.
name|HORIZONTAL_SCROLLBAR_NEVER
argument_list|)
argument_list|)
expr_stmt|;
name|sparqlWholeBox
operator|.
name|add
argument_list|(
name|sparqlButtonBox
argument_list|)
expr_stmt|;
name|sparqlPanel
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|sparqlPanel
operator|.
name|add
argument_list|(
name|BorderLayout
operator|.
name|CENTER
argument_list|,
name|sparqlWholeBox
argument_list|)
expr_stmt|;
return|return
name|sparqlPanel
return|;
block|}
specifier|private
specifier|static
name|ImageIcon
name|createImageIcon
parameter_list|(
name|String
name|path
parameter_list|)
block|{
name|java
operator|.
name|net
operator|.
name|URL
name|imgURL
init|=
name|Gui
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|path
argument_list|)
decl_stmt|;
if|if
condition|(
name|imgURL
operator|!=
literal|null
condition|)
block|{
return|return
operator|new
name|ImageIcon
argument_list|(
name|imgURL
argument_list|)
return|;
block|}
else|else
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Couldn't find file: "
operator|+
name|path
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
specifier|private
name|void
name|startRecording
parameter_list|()
block|{
name|ExecutorService
name|es
init|=
name|Executors
operator|.
name|newSingleThreadExecutor
argument_list|()
decl_stmt|;
name|Runnable
name|runner
init|=
operator|new
name|Runnable
argument_list|()
block|{
specifier|public
name|void
name|run
parameter_list|()
block|{
try|try
block|{
name|Future
argument_list|<
name|byte
index|[]
argument_list|>
name|capturedAudioFuture
init|=
name|recording
operator|.
name|startRecording
argument_list|()
decl_stmt|;
name|byte
index|[]
name|capturedAudio
init|=
name|capturedAudioFuture
operator|.
name|get
argument_list|()
decl_stmt|;
name|RecordedAudioEvent
name|rae
init|=
operator|new
name|RecordedAudioEvent
argument_list|(
name|capturedAudio
argument_list|)
decl_stmt|;
name|EventManager
operator|.
name|eventOccurred
argument_list|(
name|rae
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|RecordingInProgressException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ExecutionException
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
block|}
decl_stmt|;
name|es
operator|.
name|execute
argument_list|(
name|runner
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|stopRecording
parameter_list|()
block|{
name|ExecutorService
name|es
init|=
name|Executors
operator|.
name|newSingleThreadExecutor
argument_list|()
decl_stmt|;
name|Runnable
name|runner
init|=
operator|new
name|Runnable
argument_list|()
block|{
specifier|public
name|void
name|run
parameter_list|()
block|{
name|recording
operator|.
name|stopRecording
argument_list|()
expr_stmt|;
block|}
block|}
decl_stmt|;
name|es
operator|.
name|execute
argument_list|(
name|runner
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|clearButtonPressed
parameter_list|()
block|{
name|sparqlArea
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|writingArea
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|eventOccurred
parameter_list|(
name|Event
name|e
parameter_list|)
block|{
if|if
condition|(
name|e
operator|instanceof
name|QueryEvent
condition|)
block|{
name|QueryEvent
name|qe
init|=
operator|(
name|QueryEvent
operator|)
name|e
decl_stmt|;
name|this
operator|.
name|sparqlArea
operator|.
name|setText
argument_list|(
name|qe
operator|.
name|getSparqlQuery
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|writingArea
operator|.
name|setText
argument_list|(
name|qe
operator|.
name|getTextQuery
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|e
operator|instanceof
name|PlaybackAudioEvent
condition|)
block|{
name|PlaybackAudioEvent
name|pae
init|=
operator|(
name|PlaybackAudioEvent
operator|)
name|e
decl_stmt|;
try|try
block|{
name|playback
operator|.
name|playAudio
argument_list|(
name|pae
operator|.
name|getData
argument_list|()
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|PlaybackInProgressException
name|e1
parameter_list|)
block|{
name|e1
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|e
operator|instanceof
name|NotUnderstoodEvent
condition|)
block|{
name|this
operator|.
name|writingArea
operator|.
name|setText
argument_list|(
literal|"'I did not understand you!'"
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|e
operator|instanceof
name|ClerezzaResultEvent
condition|)
block|{
name|ClerezzaResultEvent
name|cre
init|=
operator|(
name|ClerezzaResultEvent
operator|)
name|e
decl_stmt|;
name|ResultPanel
name|resultPanel
init|=
operator|new
name|ResultPanel
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|uris
init|=
name|extractURLs
argument_list|(
name|cre
operator|.
name|getData
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|uri
range|:
name|uris
control|)
block|{
try|try
block|{
name|String
name|content
init|=
name|readFileFromURL
argument_list|(
operator|new
name|URL
argument_list|(
name|uri
argument_list|)
argument_list|)
decl_stmt|;
name|resultPanel
operator|.
name|addContent
argument_list|(
name|uri
argument_list|,
name|content
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|MalformedURLException
name|e1
parameter_list|)
block|{
name|e1
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|eee
parameter_list|)
block|{
comment|//TODO
block|}
block|}
name|mainPane
operator|.
name|addTab
argument_list|(
literal|"Result: "
operator|+
operator|new
name|Date
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|,
name|resultPanel
argument_list|)
expr_stmt|;
block|}
block|}
specifier|private
specifier|static
name|List
argument_list|<
name|String
argument_list|>
name|extractURLs
parameter_list|(
name|String
name|result
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|retVal
init|=
operator|new
name|LinkedList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
while|while
condition|(
literal|true
condition|)
block|{
name|String
name|uri
init|=
name|result
operator|.
name|replaceFirst
argument_list|(
literal|"(.*?)<binding name=\"content\"><uri>(.*?)</uri></binding>(.*)"
argument_list|,
literal|"$2"
argument_list|)
decl_stmt|;
if|if
condition|(
name|uri
operator|.
name|equals
argument_list|(
name|result
argument_list|)
condition|)
block|{
break|break;
block|}
else|else
block|{
if|if
condition|(
operator|!
name|retVal
operator|.
name|contains
argument_list|(
name|uri
argument_list|)
condition|)
name|retVal
operator|.
name|add
argument_list|(
name|uri
argument_list|)
expr_stmt|;
name|result
operator|=
name|result
operator|.
name|replaceFirst
argument_list|(
literal|"(.*?)<binding name=\"content\"><uri>(.*?)</uri></binding>(.*?)"
argument_list|,
literal|"$1$3"
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|retVal
return|;
block|}
specifier|private
specifier|static
name|String
name|readFileFromURL
parameter_list|(
name|URL
name|url
parameter_list|)
throws|throws
name|Exception
block|{
name|BufferedReader
name|in
init|=
operator|new
name|BufferedReader
argument_list|(
operator|new
name|InputStreamReader
argument_list|(
name|url
operator|.
name|openStream
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|returnValue
init|=
literal|""
decl_stmt|;
name|String
name|inputLine
decl_stmt|;
while|while
condition|(
operator|(
name|inputLine
operator|=
name|in
operator|.
name|readLine
argument_list|()
operator|)
operator|!=
literal|null
condition|)
block|{
name|returnValue
operator|+=
operator|(
name|inputLine
operator|+
literal|"\n"
operator|)
expr_stmt|;
block|}
name|in
operator|.
name|close
argument_list|()
expr_stmt|;
return|return
name|returnValue
return|;
block|}
block|}
end_class

end_unit

