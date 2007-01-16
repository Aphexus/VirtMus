/*
 * PlayLists.java
 *
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

import java.util.Vector;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.NbPreferences;
import org.openide.util.WeakListeners;

/**
 *
 * @author gburca
 */
public class PlayLists extends Children.Keys<Integer> implements ChangeListener {
    
    /**
     * Creates a new instance of PlayLists
     */
    public PlayLists(MainApp ma) {
        MainApp.log("PlayLists::constructor thread: " + Thread.currentThread().getName());
        //ma.addAllPlayLists(NbPreferences.forModule(MainApp.class));
        ma.addPLChangeListener(WeakListeners.change(this, ma));
        //ma.addPLChangeListener(this);
    }

    /**
     * This method is called whenever a list of available PlayLists is about to be
     * displayed. We need to create here a bunch of "keys" to represent each playlist.
     * The framework will then call createNode with each "key" in turn to obtain the
     * actual PlayList object to be displayed.
     * 
     * TODO: Where do we get the list of playlists from?
     * See the "Recognizing a File Type" tutorial
     */
    protected void addNotify() {
        MainApp.log("PlayLists::addNotify " + Thread.currentThread().getName());
        Vector<Integer> plKeys = new Vector<Integer>();
        for (int i = 0; i < MainApp.findInstance().playLists.size(); i++) {
            plKeys.add(i);
        }
        setKeys(plKeys);
    }

    /**
     * This method will get called with one of the items passed to setKeys in 
     * addNotify above.
     */
    protected Node[] createNodes(Integer key) {
        MainApp.log("PlayLists::createNodes " + Thread.currentThread().getName());
        PlayListNode pln = new PlayListNode(
                MainApp.findInstance().playLists.get(key)
        );
        return new Node[] {pln};
    }

    public void stateChanged(ChangeEvent e) {
        MainApp.log("PlayLists::stateChanged");
        addNotify();
    }
    
}
