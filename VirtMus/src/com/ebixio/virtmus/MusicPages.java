/*
 * MusicPages.java
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

import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.openide.nodes.Children;
import org.openide.nodes.Index;
import org.openide.nodes.Node;
import org.openide.util.WeakListeners;

/**
 *
 * @author Gabriel Burca &lt;gburca dash virtmus at ebixio dot com&gt;
 */
public class MusicPages extends Children.Keys<MusicPage> implements ChangeListener {
    private final Song song;

    /** Creates a new instance of MusicPages
     * @param song The song to create the pages for.
     */
    public MusicPages(Song song) {
        this.song = song;
        // We want to be notified when the MusicPage order changes within a Song
        // so that we can re-create the UI with nodes in the (new) proper order.
        song.addChangeListener(WeakListeners.change(this, song));
    }

    @Override
    protected void addNotify() {
        ArrayList<MusicPage> pageKeys = new ArrayList<>();
        int i = 0;
        synchronized(song.pageOrder) {
            for (MusicPage mp: song.pageOrder) {
                pageKeys.add(mp);
            }
        }
        setKeys(pageKeys);
    }

    @Override
    protected Node[] createNodes(MusicPage page) {
        return new Node[] {new MusicPageNode(page)};
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        addNotify();
    }

    public Index getIndex() {
        return new MusicPageIndexer();
    }


    public class MusicPageIndexer extends Index.Support {

        @Override
        public Node[] getNodes() {
            return MusicPages.this.getNodes();
        }

        @Override
        public int getNodesCount() {
            return getNodes().length;
        }

        @Override
        public void reorder(int[] order) {
            song.reorder(order);
            fireChangeEvent(new ChangeEvent(MusicPageIndexer.this));
        }
    }
}
