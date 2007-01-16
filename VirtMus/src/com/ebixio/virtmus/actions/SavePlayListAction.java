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

package com.ebixio.virtmus.actions;

import com.ebixio.virtmus.PlayList;
import org.openide.loaders.DataObject;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;
import org.openide.util.actions.NodeAction;

public final class SavePlayListAction extends NodeAction {
    
    protected void performAction(Node[] activatedNodes) {
        for (Node n: activatedNodes) {
            PlayList pl = (PlayList) n.getLookup().lookup(PlayList.class);
            if (pl.isDirty()) pl.save();
            setEnabled(false);
        }
    }
    
    public String getName() {
        return NbBundle.getMessage(SavePlayListAction.class, "CTL_SavePlayListAction");
    }
    
    protected String iconResource() {
        return "com/ebixio/virtmus/resources/SavePlayListAction.png";
    }
    
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }
    
    protected boolean asynchronous() {
        return false;
    }

    protected boolean enable(Node[] node) {
        for (Node n: node) {
            PlayList pl = (PlayList) n.getLookup().lookup(PlayList.class);
            if (pl != null && pl.isDirty()) return true;
        }

        return false;
    }
    
}

