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

import com.ebixio.virtmus.*;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;
import org.openide.util.actions.SystemAction;

@ActionID(id = "com.ebixio.virtmus.actions.MusicPageRemoveAction", category = "MusicPage")
@ActionRegistration(displayName = "#CTL_MusicPageRemoveAction", lazy = false)
@ActionReference(path = "Toolbars/MusicPage", name = "RemovePagesAction", position = 300)
public final class MusicPageRemoveAction extends CookieAction {
    
    @Override
    protected void performAction(Node[] activatedNodes) {
        //DataObject dataObject = (DataObject) activatedNodes[0].getLookup().lookup(DataObject.class);
        for (Node n: activatedNodes) {
            MusicPage mp = (MusicPage) n.getLookup().lookup(MusicPage.class);
            if (mp.song.removePage(new MusicPage[] {mp})) {
                SystemAction.get(SongSaveAction.class).setEnabled(true);
            }
        }
    }
    
    @Override
    protected int mode() {
        return CookieAction.MODE_ALL;
    }
    
    @Override
    public String getName() {
        return NbBundle.getMessage(MusicPageRemoveAction.class, "CTL_MusicPageRemoveAction");
    }
    
    @Override
    protected Class[] cookieClasses() {
        return new Class[] {
            MusicPage.class
        };
    }
    
    @Override
    protected String iconResource() {
        return "com/ebixio/virtmus/resources/RemovePagesAction.gif";
    }
    
    @Override
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }
    
    @Override
    protected boolean asynchronous() {
        return false;
    }
    
}

