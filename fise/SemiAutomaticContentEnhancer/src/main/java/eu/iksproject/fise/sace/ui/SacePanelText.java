begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|eu
operator|.
name|iksproject
operator|.
name|fise
operator|.
name|sace
operator|.
name|ui
package|;
end_package

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
name|Color
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|MenuItem
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|PopupMenu
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|datatransfer
operator|.
name|Transferable
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
name|javax
operator|.
name|swing
operator|.
name|JComponent
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
name|JTextArea
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|TransferHandler
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|text
operator|.
name|DefaultHighlighter
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|text
operator|.
name|Highlighter
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
name|sace
operator|.
name|util
operator|.
name|DocumentAnnotation
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
name|sace
operator|.
name|util
operator|.
name|EntityAnnotation
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
name|sace
operator|.
name|util
operator|.
name|TextAnnotation
import|;
end_import

begin_class
specifier|public
class|class
name|SacePanelText
extends|extends
name|JPanel
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|3407471836299691149L
decl_stmt|;
specifier|private
name|SaceGUI
name|saceGUI
decl_stmt|;
specifier|private
name|List
argument_list|<
name|TextAnnotation
argument_list|>
name|textAnnotations
decl_stmt|;
specifier|private
name|List
argument_list|<
name|DocumentAnnotation
argument_list|>
name|docAnnotations
decl_stmt|;
specifier|private
name|JTextArea
name|editor
decl_stmt|;
specifier|private
name|Highlighter
name|highlighter
decl_stmt|;
specifier|private
name|Highlighter
operator|.
name|HighlightPainter
name|myHighlightPainter
init|=
operator|new
name|MyHighlightPainter
argument_list|()
decl_stmt|;
specifier|public
name|SacePanelText
parameter_list|(
name|SaceGUI
name|gui
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|saceGUI
operator|=
name|gui
expr_stmt|;
name|init
argument_list|()
expr_stmt|;
block|}
specifier|public
name|void
name|clear
parameter_list|()
block|{
name|editor
operator|.
name|getHighlighter
argument_list|()
operator|.
name|removeAllHighlights
argument_list|()
expr_stmt|;
name|editor
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|textAnnotations
operator|.
name|clear
argument_list|()
expr_stmt|;
name|docAnnotations
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
specifier|private
name|void
name|init
parameter_list|()
block|{
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|textAnnotations
operator|=
operator|new
name|LinkedList
argument_list|<
name|TextAnnotation
argument_list|>
argument_list|()
expr_stmt|;
name|docAnnotations
operator|=
operator|new
name|LinkedList
argument_list|<
name|DocumentAnnotation
argument_list|>
argument_list|()
expr_stmt|;
name|editor
operator|=
operator|new
name|JTextArea
argument_list|()
expr_stmt|;
name|editor
operator|.
name|setDisabledTextColor
argument_list|(
name|editor
operator|.
name|getForeground
argument_list|()
argument_list|)
expr_stmt|;
name|editor
operator|.
name|setLineWrap
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|editor
operator|.
name|setSelectionColor
argument_list|(
name|editor
operator|.
name|getBackground
argument_list|()
argument_list|)
expr_stmt|;
name|editor
operator|.
name|setWrapStyleWord
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|highlighter
operator|=
name|editor
operator|.
name|getHighlighter
argument_list|()
expr_stmt|;
name|editor
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
block|{
if|if
condition|(
name|e
operator|.
name|isPopupTrigger
argument_list|()
operator|||
name|e
operator|.
name|isMetaDown
argument_list|()
condition|)
block|{
name|int
name|index
init|=
name|editor
operator|.
name|viewToModel
argument_list|(
name|e
operator|.
name|getPoint
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|index
operator|>=
literal|0
operator|&&
name|index
operator|<
name|editor
operator|.
name|getText
argument_list|()
operator|.
name|length
argument_list|()
condition|)
block|{
comment|// check if there are text-annotations!
name|TextAnnotation
name|annot
init|=
name|findAnnotationForIndex
argument_list|(
name|index
argument_list|)
decl_stmt|;
if|if
condition|(
name|annot
operator|!=
literal|null
condition|)
block|{
comment|// open the popup menu
name|PopupMenu
name|pm
init|=
name|getPopupMenuForTextAnnotation
argument_list|(
name|annot
argument_list|)
decl_stmt|;
name|pm
operator|.
name|show
argument_list|(
name|editor
argument_list|,
name|e
operator|.
name|getX
argument_list|()
argument_list|,
name|e
operator|.
name|getY
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
comment|// present document annotations if available!
if|if
condition|(
operator|!
name|docAnnotations
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// open the popup menu
name|PopupMenu
name|pm
init|=
name|getPopupMenuForDocumentAnnotations
argument_list|(
name|docAnnotations
argument_list|)
decl_stmt|;
name|pm
operator|.
name|show
argument_list|(
name|editor
argument_list|,
name|e
operator|.
name|getX
argument_list|()
argument_list|,
name|e
operator|.
name|getY
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
specifier|public
name|void
name|mouseEntered
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{             }
specifier|public
name|void
name|mouseExited
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{             }
specifier|public
name|void
name|mousePressed
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{
if|if
condition|(
operator|!
name|e
operator|.
name|isPopupTrigger
argument_list|()
operator|&&
operator|!
name|e
operator|.
name|isMetaDown
argument_list|()
condition|)
block|{
name|int
name|index
init|=
name|editor
operator|.
name|viewToModel
argument_list|(
name|e
operator|.
name|getPoint
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|index
operator|>=
literal|0
operator|&&
name|index
operator|<
name|editor
operator|.
name|getText
argument_list|()
operator|.
name|length
argument_list|()
condition|)
block|{
comment|// check if there are text-annotations!
name|TextAnnotation
name|annot
init|=
name|findAnnotationForIndex
argument_list|(
name|index
argument_list|)
decl_stmt|;
if|if
condition|(
name|annot
operator|!=
literal|null
condition|)
block|{
operator|(
operator|(
name|MyTransferHandler
operator|)
name|editor
operator|.
name|getTransferHandler
argument_list|()
operator|)
operator|.
name|exportAsDrag
argument_list|(
name|editor
argument_list|,
name|e
argument_list|,
name|TransferHandler
operator|.
name|COPY
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
specifier|public
name|void
name|mouseReleased
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{             }
block|}
argument_list|)
expr_stmt|;
name|editor
operator|.
name|setDragEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|editor
operator|.
name|setTransferHandler
argument_list|(
operator|new
name|MyTransferHandler
argument_list|()
argument_list|)
expr_stmt|;
comment|// DEBUG //
name|editor
operator|.
name|setText
argument_list|(
literal|"In the picture on the right, you can see the line-up of the German football team. This picture seems to be an older version as Michael Ballack is not able to participate this year's World Cup championship, due to an injury."
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|BorderLayout
operator|.
name|CENTER
argument_list|,
operator|new
name|JScrollPane
argument_list|(
name|editor
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
block|}
specifier|private
name|TextAnnotation
name|findAnnotationForIndex
parameter_list|(
name|int
name|index
parameter_list|)
block|{
for|for
control|(
name|TextAnnotation
name|ta
range|:
name|textAnnotations
control|)
block|{
if|if
condition|(
name|ta
operator|.
name|getStartIndex
argument_list|()
operator|<=
name|index
operator|&&
name|index
operator|<=
name|ta
operator|.
name|getEndIndex
argument_list|()
condition|)
return|return
name|ta
return|;
block|}
return|return
literal|null
return|;
block|}
specifier|private
name|PopupMenu
name|getPopupMenuForTextAnnotation
parameter_list|(
specifier|final
name|TextAnnotation
name|annotation
parameter_list|)
block|{
specifier|final
name|PopupMenu
name|pm
init|=
operator|new
name|PopupMenu
argument_list|()
decl_stmt|;
name|MenuItem
name|mi
init|=
operator|new
name|MenuItem
argument_list|(
literal|"Please choose one annotation for '"
operator|+
name|annotation
operator|.
name|getSelectedText
argument_list|()
operator|+
literal|"'!"
argument_list|)
decl_stmt|;
name|mi
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|pm
operator|.
name|add
argument_list|(
name|mi
argument_list|)
expr_stmt|;
name|pm
operator|.
name|addSeparator
argument_list|()
expr_stmt|;
name|String
name|creatorTA
init|=
name|annotation
operator|.
name|getCreator
argument_list|()
operator|.
name|replaceFirst
argument_list|(
literal|".*\\.(.*)$"
argument_list|,
literal|"$1"
argument_list|)
decl_stmt|;
if|if
condition|(
name|annotation
operator|.
name|getAttribute
argument_list|(
literal|"type"
argument_list|)
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|String
name|type
range|:
name|annotation
operator|.
name|getAttribute
argument_list|(
literal|"type"
argument_list|)
control|)
block|{
specifier|final
name|String
name|typeTmp
init|=
name|type
decl_stmt|;
if|if
condition|(
operator|!
name|type
operator|.
name|endsWith
argument_list|(
literal|"Thing"
argument_list|)
condition|)
block|{
name|String
name|label
init|=
name|type
operator|.
name|replaceFirst
argument_list|(
literal|".*\\/(.*)$"
argument_list|,
literal|"$1"
argument_list|)
operator|+
literal|"<-- "
operator|+
name|creatorTA
decl_stmt|;
name|boolean
name|alreadyThere
init|=
literal|false
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
name|pm
operator|.
name|getItemCount
argument_list|()
condition|;
name|i
operator|++
control|)
name|alreadyThere
operator||=
operator|(
name|pm
operator|.
name|getItem
argument_list|(
name|i
argument_list|)
operator|.
name|getLabel
argument_list|()
operator|.
name|equals
argument_list|(
name|label
argument_list|)
operator|)
expr_stmt|;
if|if
condition|(
operator|!
name|alreadyThere
condition|)
block|{
name|mi
operator|=
operator|new
name|MenuItem
argument_list|(
name|label
argument_list|)
expr_stmt|;
name|mi
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
name|SacePanelText
operator|.
name|this
operator|.
name|remove
argument_list|(
name|pm
argument_list|)
expr_stmt|;
name|retainTextTypeAnnotation
argument_list|(
name|annotation
argument_list|,
name|typeTmp
argument_list|)
expr_stmt|;
name|submitAnnotationToFISE
argument_list|(
name|annotation
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|pm
operator|.
name|add
argument_list|(
name|mi
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|annotation
operator|.
name|getEntityAnnotations
argument_list|()
operator|.
name|size
argument_list|()
condition|;
name|j
operator|++
control|)
block|{
specifier|final
name|EntityAnnotation
name|ea
init|=
name|annotation
operator|.
name|getEntityAnnotations
argument_list|()
operator|.
name|get
argument_list|(
name|j
argument_list|)
decl_stmt|;
name|String
name|creatorEA
init|=
name|ea
operator|.
name|getCreator
argument_list|()
operator|.
name|replaceFirst
argument_list|(
literal|".*\\.(.*)$"
argument_list|,
literal|"$1"
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|type
range|:
name|ea
operator|.
name|getAttribute
argument_list|(
literal|"entity-type"
argument_list|)
control|)
block|{
specifier|final
name|String
name|typeTmp
init|=
name|type
decl_stmt|;
if|if
condition|(
operator|!
name|type
operator|.
name|endsWith
argument_list|(
literal|"Thing"
argument_list|)
condition|)
block|{
name|String
name|label
init|=
name|type
operator|.
name|replaceFirst
argument_list|(
literal|".*\\/(.*)$"
argument_list|,
literal|"$1"
argument_list|)
operator|+
literal|"<-- "
operator|+
name|creatorEA
decl_stmt|;
name|boolean
name|alreadyThere
init|=
literal|false
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
name|pm
operator|.
name|getItemCount
argument_list|()
condition|;
name|i
operator|++
control|)
name|alreadyThere
operator||=
operator|(
name|pm
operator|.
name|getItem
argument_list|(
name|i
argument_list|)
operator|.
name|getLabel
argument_list|()
operator|.
name|equals
argument_list|(
name|label
argument_list|)
operator|)
expr_stmt|;
if|if
condition|(
operator|!
name|alreadyThere
condition|)
block|{
name|mi
operator|=
operator|new
name|MenuItem
argument_list|(
name|label
argument_list|)
expr_stmt|;
name|mi
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
name|SacePanelText
operator|.
name|this
operator|.
name|remove
argument_list|(
name|pm
argument_list|)
expr_stmt|;
name|retainTextEntityTypeAnnotation
argument_list|(
name|annotation
argument_list|,
name|ea
argument_list|,
name|typeTmp
argument_list|)
expr_stmt|;
name|submitAnnotationToFISE
argument_list|(
name|annotation
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|pm
operator|.
name|add
argument_list|(
name|mi
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
name|this
operator|.
name|add
argument_list|(
name|pm
argument_list|)
expr_stmt|;
return|return
name|pm
return|;
block|}
specifier|private
name|void
name|retainTextTypeAnnotation
parameter_list|(
name|TextAnnotation
name|textAnnot
parameter_list|,
name|String
name|type
parameter_list|)
block|{
name|textAnnot
operator|.
name|clearEntityAnnotation
argument_list|()
expr_stmt|;
name|textAnnot
operator|.
name|retainTypeAnnotation
argument_list|(
name|type
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|retainTextEntityTypeAnnotation
parameter_list|(
name|TextAnnotation
name|textAnnot
parameter_list|,
name|EntityAnnotation
name|ea
parameter_list|,
name|String
name|entityType
parameter_list|)
block|{
name|textAnnot
operator|.
name|clearTypeAnnotation
argument_list|()
expr_stmt|;
name|ea
operator|.
name|retainTypeAnnotation
argument_list|(
name|entityType
argument_list|)
expr_stmt|;
block|}
specifier|private
name|PopupMenu
name|getPopupMenuForDocumentAnnotations
parameter_list|(
name|List
argument_list|<
name|DocumentAnnotation
argument_list|>
name|docAnnotations
parameter_list|)
block|{
specifier|final
name|PopupMenu
name|pm
init|=
operator|new
name|PopupMenu
argument_list|()
decl_stmt|;
name|MenuItem
name|mi
init|=
operator|new
name|MenuItem
argument_list|(
literal|"Please choose one annotation for the document!"
argument_list|)
decl_stmt|;
name|mi
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|pm
operator|.
name|add
argument_list|(
name|mi
argument_list|)
expr_stmt|;
name|pm
operator|.
name|addSeparator
argument_list|()
expr_stmt|;
for|for
control|(
name|DocumentAnnotation
name|da
range|:
name|docAnnotations
control|)
block|{
name|mi
operator|=
operator|new
name|MenuItem
argument_list|(
name|da
operator|.
name|getAttribute
argument_list|(
literal|"lang"
argument_list|)
operator|+
literal|"<-- LanguageIdentifier"
argument_list|)
expr_stmt|;
name|mi
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
name|SacePanelText
operator|.
name|this
operator|.
name|remove
argument_list|(
name|pm
argument_list|)
expr_stmt|;
name|submitAnnotationToFISE
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|pm
operator|.
name|add
argument_list|(
name|mi
argument_list|)
expr_stmt|;
block|}
name|this
operator|.
name|add
argument_list|(
name|pm
argument_list|)
expr_stmt|;
return|return
name|pm
return|;
block|}
specifier|private
name|void
name|submitAnnotationToFISE
parameter_list|(
name|TextAnnotation
name|ta
parameter_list|)
block|{
name|saceGUI
operator|.
name|submitTextAnnotationToFISE
argument_list|(
name|ta
argument_list|)
expr_stmt|;
block|}
class|class
name|MyHighlightPainter
extends|extends
name|DefaultHighlighter
operator|.
name|DefaultHighlightPainter
block|{
specifier|public
name|MyHighlightPainter
parameter_list|()
block|{
name|super
argument_list|(
operator|new
name|Color
argument_list|(
literal|65
argument_list|,
literal|137
argument_list|,
literal|77
argument_list|)
argument_list|)
expr_stmt|;
comment|// super(Color.red);
block|}
block|}
specifier|public
name|void
name|addDocumentAnnotation
parameter_list|(
name|DocumentAnnotation
name|annot
parameter_list|)
block|{
name|docAnnotations
operator|.
name|add
argument_list|(
name|annot
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|addTextAnnotation
parameter_list|(
name|TextAnnotation
name|ta
parameter_list|)
block|{
name|textAnnotations
operator|.
name|add
argument_list|(
name|ta
argument_list|)
expr_stmt|;
try|try
block|{
name|highlighter
operator|.
name|addHighlight
argument_list|(
name|ta
operator|.
name|getStartIndex
argument_list|()
argument_list|,
name|ta
operator|.
name|getEndIndex
argument_list|()
argument_list|,
name|this
operator|.
name|myHighlightPainter
argument_list|)
expr_stmt|;
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
specifier|public
name|String
name|getText
parameter_list|()
block|{
return|return
name|editor
operator|.
name|getText
argument_list|()
return|;
block|}
class|class
name|MyTransferHandler
extends|extends
name|TransferHandler
block|{
comment|/**          *          */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|7247031090902543381L
decl_stmt|;
annotation|@
name|Override
specifier|public
name|Transferable
name|createTransferable
parameter_list|(
name|JComponent
name|c
parameter_list|)
block|{
if|if
condition|(
name|c
operator|instanceof
name|JTextArea
condition|)
block|{
name|JTextArea
name|jta
init|=
operator|(
name|JTextArea
operator|)
name|c
decl_stmt|;
name|int
name|index
init|=
name|editor
operator|.
name|viewToModel
argument_list|(
name|jta
operator|.
name|getMousePosition
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|index
operator|>=
literal|0
operator|&&
name|index
operator|<
name|editor
operator|.
name|getText
argument_list|()
operator|.
name|length
argument_list|()
condition|)
block|{
comment|// check if there are text-annotations!
name|TextAnnotation
name|annot
init|=
name|findAnnotationForIndex
argument_list|(
name|index
argument_list|)
decl_stmt|;
if|if
condition|(
name|annot
operator|!=
literal|null
condition|)
block|{
return|return
name|annot
return|;
block|}
block|}
block|}
return|return
literal|null
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|getSourceActions
parameter_list|(
name|JComponent
name|c
parameter_list|)
block|{
return|return
name|COPY
return|;
block|}
block|}
specifier|public
name|void
name|finalizeText
parameter_list|()
block|{     }
block|}
end_class

end_unit

