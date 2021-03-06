/*
 * Copyright (C) 2006-2014  Gabriel Burca (gburca dash virtmus at ebixio dot com)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package com.ebixio.annotations;

import com.ebixio.annotations.tools.DrawingTool;
import com.ebixio.annotations.tools.ToolFreehand;
import com.ebixio.annotations.tools.ToolLine;
import com.ebixio.annotations.tools.ToolRect;
import com.ebixio.virtmus.CommonExplorers;
import com.ebixio.virtmus.MusicPage;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.CancellationException;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSlider;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.openide.ErrorManager;
import org.openide.awt.UndoRedo;
import org.openide.explorer.ExplorerManager;
import org.openide.nodes.Node;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;


/**
 * Top component which displays the music page annotations.
 */
public final class AnnotTopComponent extends TopComponent
        implements ComponentListener {

    private static AnnotTopComponent instance;
    /** path to the icon used by the component and its open action */
    static final String ICON_PATH = "com/ebixio/annotations/annot-tab-icon.png";

    private static final String PREFERRED_ID = "AnnotTopComponent";
    private PlanarImage source = null;
    private PlanarImage scaledSource = null;
    private MusicPage currentlyShowing = null;

    transient private final PropertyChangeListener eListener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (ExplorerManager.PROP_SELECTED_NODES.equals(evt.getPropertyName())) {
                Node[] selectedNodes = (Node[]) evt.getNewValue();
                updateSelection(selectedNodes);
            }
        }
    };

    static {
        // Linux paints the slider value above the slider throwing off the toolbar size
        UIManager.put("Slider.paintValue", Boolean.FALSE);
    }

    private AnnotTopComponent() {

        initComponents();
        setName(NbBundle.getMessage(AnnotTopComponent.class, "CTL_AnnotTopComponent"));
        setToolTipText(NbBundle.getMessage(AnnotTopComponent.class, "HINT_AnnotTopComponent"));
        setIcon(ImageUtilities.loadImage(ICON_PATH, true));

        panner.setBorder(new LineBorder(Color.RED, 2));
        panner.setVisible(false);

        this.addComponentListener(this);

        // Initialize the canvas with the default alpha value
        jsAlphaStateChanged(null);

        // Initialize the color chooser with a random bright color
        colorChooser.setColor(Color.getHSBColor((float)Math.random(), 0.9F, 0.9F));
        colorChooserActionPerformed(null);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar = new javax.swing.JToolBar();
        toolChooser = new javax.swing.JComboBox<DrawingTool>();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        jLabel1 = new javax.swing.JLabel();
        colorChooser = new net.java.dev.colorchooser.ColorChooser();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        jLabel2 = new javax.swing.JLabel();
        jsBrushSize = new javax.swing.JSlider();
        brushPreview = new com.ebixio.annotations.BrushPreview();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        jLabel4 = new javax.swing.JLabel();
        jsAlpha = new javax.swing.JSlider();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jbClear = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jLabel3 = new javax.swing.JLabel();
        jsZoom = new javax.swing.JSlider();
        canvasPanel = new javax.swing.JPanel();
        canvas = new com.ebixio.annotations.AnnotCanvas();
        panner = new com.ebixio.jai.Panner();

        setBackground(new java.awt.Color(153, 255, 153));

        jToolBar.setFloatable(false);

        toolChooser.setModel(getTools());
        toolChooser.setMaximumSize(new java.awt.Dimension(100, 20));
        toolChooser.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                toolChooserItemStateChanged(evt);
            }
        });
        jToolBar.add(toolChooser);
        jToolBar.add(jSeparator6);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, "Color:");
        jToolBar.add(jLabel1);

        colorChooser.setToolTipText("Foreground color");
        colorChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorChooserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout colorChooserLayout = new javax.swing.GroupLayout(colorChooser);
        colorChooser.setLayout(colorChooserLayout);
        colorChooserLayout.setHorizontalGroup(
            colorChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );
        colorChooserLayout.setVerticalGroup(
            colorChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jToolBar.add(colorChooser);
        jToolBar.add(jSeparator4);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, "Size: ");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jToolBar.add(jLabel2);

        jsBrushSize.setMajorTickSpacing(4);
        jsBrushSize.setMaximum(24);
        jsBrushSize.setMinimum(1);
        jsBrushSize.setToolTipText("Brush size");
        jsBrushSize.setMaximumSize(new java.awt.Dimension(100, 25));
        jsBrushSize.setPreferredSize(new java.awt.Dimension(85, 38));
        jsBrushSize.setValue(canvas.getDiam());
        jsBrushSize.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jsBrushSizeStateChanged(evt);
            }
        });
        jToolBar.add(jsBrushSize);

        brushPreview.setMaximumSize(new java.awt.Dimension(32, 32));

        javax.swing.GroupLayout brushPreviewLayout = new javax.swing.GroupLayout(brushPreview);
        brushPreview.setLayout(brushPreviewLayout);
        brushPreviewLayout.setHorizontalGroup(
            brushPreviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
        );
        brushPreviewLayout.setVerticalGroup(
            brushPreviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
        );

        jToolBar.add(brushPreview);
        jToolBar.add(jSeparator5);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, "Opacity:");
        jToolBar.add(jLabel4);

        jsAlpha.setMinimum(1);
        jsAlpha.setToolTipText("Opacity");
        jsAlpha.setValue(70);
        jsAlpha.setMaximumSize(new java.awt.Dimension(100, 25));
        jsAlpha.setPreferredSize(new java.awt.Dimension(85, 38));
        jsAlpha.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jsAlphaStateChanged(evt);
            }
        });
        jToolBar.add(jsAlpha);
        jToolBar.add(jSeparator2);

        org.openide.awt.Mnemonics.setLocalizedText(jbClear, "Clear");
        jbClear.setToolTipText("Remove all annotations");
        jbClear.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jbClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbClearActionPerformed(evt);
            }
        });
        jToolBar.add(jbClear);
        jToolBar.add(jSeparator1);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, "Zoom:");
        jLabel3.setToolTipText("Zoom");
        jToolBar.add(jLabel3);

        jsZoom.setMajorTickSpacing(1);
        jsZoom.setMaximum(1000);
        jsZoom.setMinimum(10);
        jsZoom.setMinorTickSpacing(1);
        jsZoom.setToolTipText("Zoom");
        jsZoom.setValue(1000);
        jsZoom.setMaximumSize(new java.awt.Dimension(100, 38));
        jsZoom.setPreferredSize(new java.awt.Dimension(85, 38));
        jsZoom.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jsZoomStateChanged(evt);
            }
        });
        jToolBar.add(jsZoom);

        canvasPanel.setBackground(new java.awt.Color(0, 0, 0));

        canvas.setOpaque(true);

        panner.setBackground(new java.awt.Color(204, 204, 255));
        panner.setMaximumSize(new java.awt.Dimension(64, 100));
        panner.setMinimumSize(new java.awt.Dimension(64, 100));
        panner.setPreferredSize(new java.awt.Dimension(64, 100));

        javax.swing.GroupLayout canvasLayout = new javax.swing.GroupLayout(canvas);
        canvas.setLayout(canvasLayout);
        canvasLayout.setHorizontalGroup(
            canvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, canvasLayout.createSequentialGroup()
                .addContainerGap(713, Short.MAX_VALUE)
                .addComponent(panner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        canvasLayout.setVerticalGroup(
            canvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(canvasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(271, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout canvasPanelLayout = new javax.swing.GroupLayout(canvasPanel);
        canvasPanel.setLayout(canvasPanelLayout);
        canvasPanelLayout.setHorizontalGroup(
            canvasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(canvas, javax.swing.GroupLayout.DEFAULT_SIZE, 787, Short.MAX_VALUE)
        );
        canvasPanelLayout.setVerticalGroup(
            canvasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(canvas, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar, javax.swing.GroupLayout.DEFAULT_SIZE, 787, Short.MAX_VALUE)
            .addComponent(canvasPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(canvasPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jsZoomStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jsZoomStateChanged
        resizeImage((float)jsZoom.getValue() / (float)jsZoom.getMaximum());
    }//GEN-LAST:event_jsZoomStateChanged

    private void jsBrushSizeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jsBrushSizeStateChanged
        if (evt == null) return;
        JSlider js = (JSlider) evt.getSource();
        int newValue = js.getValue();
        canvas.setDiam( newValue );
        canvas.setThreshold(255 - newValue);
        brushPreview.setDiam(newValue);
    }//GEN-LAST:event_jsBrushSizeStateChanged

    private void jbClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbClearActionPerformed
        canvas.clear();
        canvas.undoManager.discardAllEdits();
        //canvas.musicPage.popAnnotation();
    }//GEN-LAST:event_jbClearActionPerformed

    private void colorChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorChooserActionPerformed
        canvas.setPaint (colorChooser.getColor());
        brushPreview.setColor(colorChooser.getColor());
    }//GEN-LAST:event_colorChooserActionPerformed

    private void jsAlphaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jsAlphaStateChanged
        canvas.setAlpha(jsAlpha.getValue() / 100.0F);
    }//GEN-LAST:event_jsAlphaStateChanged

    private void toolChooserItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_toolChooserItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            canvas.tool = (DrawingTool)evt.getItem();
        }
    }//GEN-LAST:event_toolChooserItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.ebixio.annotations.BrushPreview brushPreview;
    private com.ebixio.annotations.AnnotCanvas canvas;
    private javax.swing.JPanel canvasPanel;
    private net.java.dev.colorchooser.ColorChooser colorChooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar jToolBar;
    private javax.swing.JButton jbClear;
    private javax.swing.JSlider jsAlpha;
    private javax.swing.JSlider jsBrushSize;
    private javax.swing.JSlider jsZoom;
    private com.ebixio.jai.Panner panner;
    private javax.swing.JComboBox<DrawingTool> toolChooser;
    // End of variables declaration//GEN-END:variables

    // <editor-fold defaultstate="collapsed" desc=" Singleton ">
    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link findInstance}.
     * @return The AnnotTopComponent singleton
     */
    public static synchronized AnnotTopComponent getDefault() {
        if (instance == null) {
            instance = new AnnotTopComponent();
        }
        return instance;
    }

    /**
     * Obtain the AnnotTopComponent instance. Never call {@link #getDefault} directly!
     * @return The AnnotTopComponent singleton
     */
    public static synchronized AnnotTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            ErrorManager.getDefault().log(ErrorManager.WARNING,
                    "Cannot find AnnotTopComponent component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof AnnotTopComponent) {
            return (AnnotTopComponent)win;
        }
        ErrorManager.getDefault().log(ErrorManager.WARNING,
                "There seem to be multiple components with the '" + PREFERRED_ID +
                "' ID. That is a potential source of errors and unexpected behavior.");
        return getDefault();
    }
    // </editor-fold>

    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_ALWAYS;
    }

    @Override
    public void addNotify() {
        CommonExplorers.MainExplorerManager.addPropertyChangeListener(eListener);
        CommonExplorers.TagsExplorerManager.addPropertyChangeListener(eListener);
        super.addNotify();
    }
    @Override
    public void removeNotify() {
        super.removeNotify();
        CommonExplorers.MainExplorerManager.removePropertyChangeListener(eListener);
        CommonExplorers.TagsExplorerManager.removePropertyChangeListener(eListener);
    }

    @Override
    public UndoRedo getUndoRedo() {
        return canvas.undoManager;
    }

    /**
     * This function gets called every time the selected nodes change.
     * The results change to "nothing" when the focus moves away from the TopComponent
     * that contains the nodes. It gets changed to the selected node when focus returns
     * to the TopComponent, etc...
     *
     * Since setImage is very time-consuming for large images, we want to do it only if
     * it is different from the image being currently displayed.
     */
    private void updateSelection(Node[] nodes) {
        if (nodes.length > 0) {
            MusicPage mp = nodes[0].getLookup().lookup(MusicPage.class);
            // Only change the currently showing node if another MusicPage was
            // selected. Ignore Song or PlayList nodes.
            if (mp != null && currentlyShowing != mp) {
                currentlyShowing = mp;
                this.showPage(mp);
            }
        } else {
            currentlyShowing = null;
            this.showPage(null);
        }
    }

    /**
     *
     * @param scale A scaling percentage between [0.1 .. 1]
     */
    public void resizeImage(float scale) {
        if (source == null) {
            return;
        }

        if (scale < 0.1) {
            scale = 0.1F;
        } else if (scale > 1) {
            scale = 1.0F;
        }

        canvas.setScale(scale);

        // Use "SubsampleAverage" because it looks much better.
        RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        scaledSource = JAI.create("SubsampleAverage", source, (double)scale, (double)scale, qualityHints);

        showImage(scaledSource);
    }

    public void showPage(MusicPage page) {
        source = scaledSource = null;
        // Reset zoom so the change property fires when the page is scaled to fit.
        this.jsZoom.setValue(100 * 10);
        canvas.setMusicPage(page);
        if (page == null) {
            canvas.repaint();
            return;
        }

        page.setChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                canvas.repaint();
            }
        });

        PlanarImage src = currentlyShowing.imgSrc.getFullImg();
        if (src != null) {
            source = src;
        } else {
            return;
        }

        // Loading an image takes some time. We do it on a separate thread.
        SwingWorker w = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() {
                try {
                    // This forces the image to load.
                    canvas.imgBounds = source.getBounds();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return new Boolean(true);
            }

            @Override
            public void done() {
                resizeImgToFit();
            }
        };

        w.execute();
    }

    public void showImage(PlanarImage imgSource) {
        canvas.set(imgSource);

        /* We need to wait for the imgLoader in canvas.set to finish before attempting to configure
         * the panner, or else the image won't be fully loaded when we do source.getHeight() in
         * configurePanner() and we'll block the UI thread waiting for it to load.
         */
        SwingWorker w = new SwingWorker<Boolean, Void>() {
            @Override
            public Boolean doInBackground() {
                try {
                    if (canvas.imgLoader != null) {
                        // This call will block until imgLoader.doInBackground is finished
                        canvas.imgLoader.get();    // The returned value is irrelevant
                    }
                } catch (Exception ex) {
                    if (! ex.getClass().equals(CancellationException.class)) {
                        ex.printStackTrace();
                    }
                }
                return new Boolean(true);
            }

            @Override
            public void done() {
                configurePanner();
            }
        };

        w.execute();

        // Need to re-paint the areas outside the canvas when the canvas shrinks
        this.canvasPanel.repaint();
    }

    private void resizeImgToFit() {
        if (source == null) return;

        double dScale = 1000 * com.ebixio.virtmus.Utils.scaleProportional(canvasPanel.getBounds(), source.getBounds());
        int scale;

        if (dScale < 1) {
            scale = 1;
        } else if (dScale >= 1000) {
            scale = 999;
        } else {
            scale = (int)dScale;
        }

        // If the zoom value changes, the listeners will be notified.
        this.jsZoom.setValue(scale);
    }

    /**
     * The panner should only be shown if the image is larger than the canvas.
     */
    private void configurePanner() {
        PlanarImage currentSource = (scaledSource != null) ? scaledSource : source;

        if (currentSource == null) {
            panner.setVisible(false);
            return;
        }

        // source.getWidth() (or Height) could throw an exception if the source is invalid
        try {
            /* source.getWidth/Height() will block until the image is decoded, so this function
             * should only be called after "source" has loaded its image.
             * See: showPage and ImageDisplay.set
             */
            if (currentSource.getWidth() > canvas.getWidth() || currentSource.getHeight() > canvas.getHeight()) {
                panner.set(canvas, currentSource, 128);
                panner.setVisible(true);
                canvas.revalidate();
            } else {
                panner.setVisible(false);
                canvas.setOrigin(0, 0);
                canvas.revalidate();
            }
        } catch (Exception e) {

        }
    }


    /**
     * The list of drawing tools we will use with the toolChooser JComboBox
     * @return A set of drawing tools
     */
    public ComboBoxModel<DrawingTool> getTools() {
        DefaultComboBoxModel<DrawingTool> cbm = new DefaultComboBoxModel<>();
        cbm.addElement(new ToolRect(canvas));
        cbm.addElement(new ToolLine(canvas));
        cbm.addElement(new ToolFreehand(canvas));
        //cbm.addElement(new ToolDot(canvas));  // The line tool makes this redundant
        return cbm;
    }

    // <editor-fold defaultstate="collapsed" desc=" ComponentListener interface ">
    @Override
    public void componentResized(ComponentEvent e) {
        resizeImgToFit();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" Wizzard generated ">
    final static class ResolvableHelper implements Serializable {
        private static final long serialVersionUID = 1L;
        public Object readResolve() {
            return AnnotTopComponent.getDefault();
        }
    }

    /** replaces this in object stream
     * @return Something or another.
     */
    @Override
    public Object writeReplace() {
        return new ResolvableHelper();
    }

    @Override
    protected String preferredID() {
        return PREFERRED_ID;
    }
    // </editor-fold>

}
