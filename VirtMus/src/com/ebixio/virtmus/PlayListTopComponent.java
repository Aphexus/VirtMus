/*
 * Copyright (C) 2006-2007  Gabriel Burca (gburca dash virtmus at ebixio dot com)
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

package com.ebixio.virtmus;

import java.io.Serializable;
import javax.swing.ActionMap;
import javax.swing.text.DefaultEditorKit;
import org.openide.ErrorManager;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import org.openide.explorer.*;
import org.openide.nodes.*;
import org.openide.util.ImageUtilities;

/**
 * Top component which displays something.
 */
final class PlayListTopComponent extends TopComponent
        implements ExplorerManager.Provider, Lookup.Provider {
    
    private static PlayListTopComponent instance;
    /** path to the icon used by the component and its open action */
    static final String ICON_PATH = "com/ebixio/virtmus/resources/PlayListTopComponent.png";
    
    private static final String PREFERRED_ID = "PlayListTopComponent";
    
    private PlayListTopComponent() {
        initComponents();
        setName(NbBundle.getMessage(PlayListTopComponent.class, "CTL_PlayListTopComponent"));
        setToolTipText(NbBundle.getMessage(PlayListTopComponent.class, "HINT_PlayListTopComponent"));
        setIcon(ImageUtilities.loadImage(ICON_PATH, true));
        
        ExplorerManager manager = MainApp.findInstance().getExplorerManager();
        ActionMap map = this.getActionMap();
        map.put(DefaultEditorKit.copyAction, ExplorerUtils.actionCopy(manager));
        map.put(DefaultEditorKit.cutAction, ExplorerUtils.actionCut(manager));
        map.put(DefaultEditorKit.pasteAction, ExplorerUtils.actionPaste(manager));
        map.put("delete", ExplorerUtils.actionDelete(manager, true)); // or false
        
        // Place the Explorer Manager in the TopComponent's Lookup
        associateLookup(ExplorerUtils.createLookup(manager, map));
        
        MainApp.log("PlayListTopComponent::constructor before addAllPlayLists thread:" + Thread.currentThread().getName());
        //MainApp.findInstance().addAllPlayLists(NbPreferences.forModule(MainApp.class));
        MainApp.log("PlayListTopComponent::constructor before new PlayLists");
        manager.setRootContext(new AbstractNode(new PlayLists(MainApp.findInstance())));
        manager.getRootContext().setDisplayName("Playlists");
        this.beanTreeView1.setRootVisible(false);
    }

     
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        beanTreeView1 = new org.openide.explorer.view.BeanTreeView();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(beanTreeView1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(beanTreeView1, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.openide.explorer.view.BeanTreeView beanTreeView1;
    // End of variables declaration//GEN-END:variables
    
    /**
     * Gets default instance. Do not use directly: reserved for *.settings files only,
     * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
     * To obtain the singleton instance, use {@link findInstance}.
     */
    public static synchronized PlayListTopComponent getDefault() {
        if (instance == null) {
            instance = new PlayListTopComponent();
        }
        return instance;
    }
    
    /**
     * Obtain the PlayListTopComponent instance. Never call {@link #getDefault} directly!
     */
    public static synchronized PlayListTopComponent findInstance() {
        TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
        if (win == null) {
            ErrorManager.getDefault().log(ErrorManager.WARNING,
                    "Cannot find MyWindow component. It will not be located properly in the window system.");
            return getDefault();
        }
        if (win instanceof PlayListTopComponent) {
            return (PlayListTopComponent)win;
        }
        ErrorManager.getDefault().log(ErrorManager.WARNING,
                "There seem to be multiple components with the '" + PREFERRED_ID +
                "' ID. That is a potential source of errors and unexpected behavior.");
        return getDefault();
    }
    
    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_ALWAYS;
    }
    
    @Override
    protected void componentActivated() {
        ExplorerManager manager = MainApp.findInstance().getExplorerManager();
        ExplorerUtils.activateActions(manager, true);
    }
    
    @Override
    protected void componentDeactivated() {
        ExplorerManager manager = MainApp.findInstance().getExplorerManager();
        ExplorerUtils.activateActions(manager, false);
    }
    
    /** replaces this in object stream */
    @Override
    public Object writeReplace() {
        return new ResolvableHelper();
    }
    
    @Override
    protected String preferredID() {
        return PREFERRED_ID;
    }

    public ExplorerManager getExplorerManager() {
        return MainApp.findInstance().getExplorerManager();
    }
    
    final static class ResolvableHelper implements Serializable {
        private static final long serialVersionUID = 1L;
        public Object readResolve() {
            return PlayListTopComponent.getDefault();
        }
    }
    
}
